/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen, waitFor} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import fetch from 'jest-fetch-mock';
import React from 'react';

import '@testing-library/jest-dom';

import {NewPublish} from '../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/NewPublish';
import {ScheduledPublishProcess} from '../../../../../src/main/resources/META-INF/resources/revamp/js/types/exportImportProcess';
import {mockExportPreview} from '../../mocks/mockExportPreview';

jest.mock('staging-taglib', () => ({
	PagesTree: require('../../mocks/MockPagesTree').MockPagesTree,
}));

const SCHEDULED_PUBLISH_PROCESS: ScheduledPublishProcess = {
	cronExpression: '0 30 9 ? * MON/1 *',
	id: 1234,
	name: 'Weekly Content Sync',
	publishParameters: {
		DELETIONS: ['true'],
		PERMISSIONS: ['false'],
		PORTLET_DATA_com_liferay_journal_web_portlet_JournalPortlet: ['true'],
		range: ['fromLastPublishDate'],
		timeZoneId: ['UTC'],
	},
	scheduleStartDate: '2026-08-22T09:30:00Z',
};

const DEFAULT_PROPS = {
	backURL: '/some/back/url',
	pageTreeModalConfiguration: {
		groupId: 20121,
		pageSize: 20,
		privateLayoutsAvailable: false,
	},
	publishPreviewAPIURL:
		'/o/export-import/v1.0/sites/site-erc/publish-preview',
	publishProcessAPIURL:
		'/o/export-import/v1.0/sites/site-erc/publish-processes',
	scheduledBackURL: '/some/back/url?tab=scheduled',
	scheduledPublishProcessesAPIURL:
		'/o/export-import/v1.0/sites/site-erc/scheduled-publish-processes',
	timeZoneId: 'UTC',
	timeZones: [{label: '(UTC) Coordinated Universal Time', value: 'UTC'}],
};

const getPublishProcessCall = () =>
	fetch.mock.calls.find(
		([url, init]) =>
			String(url).endsWith('publish-processes') && init?.method === 'POST'
	);

const getUnscheduleCall = () =>
	fetch.mock.calls.find(([, init]) => init?.method === 'DELETE');

const mockAPIRoutes = ({
	deleteStatus = 204,
	scheduledPublishProcess = SCHEDULED_PUBLISH_PROCESS,
} = {}) => {
	fetch.mockResponse(async (request) => {
		if (request.method === 'DELETE') {
			return {
				body: deleteStatus === 204 ? '' : JSON.stringify({}),
				status: deleteStatus,
			};
		}

		if (request.url.includes('scheduled-publish-processes')) {
			return {body: JSON.stringify(scheduledPublishProcess)};
		}

		if (request.url.includes('publish-preview')) {
			return {body: JSON.stringify(mockExportPreview)};
		}

		return {body: JSON.stringify({})};
	});
};

const user = userEvent.setup({delay: null});

const fillRequiredFields = async () => {
	await screen.findByText('loaded');

	await user.click(screen.getByRole('textbox', {name: /^name/i}));

	await user.paste('My Publication');
};

const renderComponent = (
	props: Partial<React.ComponentProps<typeof NewPublish>> = {}
) => render(<NewPublish {...DEFAULT_PROPS} {...props} />);

describe('NewPublish', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		fetch.resetMocks();

		mockAPIRoutes();
	});

	it('publishes immediately and navigates back', async () => {
		renderComponent();

		await fillRequiredFields();

		await user.click(
			screen.getByRole('button', {name: /publish-to-live/i})
		);

		await waitFor(() => {
			expect(getPublishProcessCall()).toBeDefined();
		});

		const body = JSON.parse(getPublishProcessCall()![1]!.body as string);

		expect(body.cronExpression).toBeUndefined();
		expect(body.deletions).toBe(false);
		expect(body.name).toBe('My Publication');
		expect(body.permissions).toBe(false);
		expect(body.requestPortletDataHandlers.length).toBeGreaterThan(0);

		expect(Liferay.Util.navigate).toHaveBeenCalledWith(
			DEFAULT_PROPS.backURL
		);
	});

	it('selects the schedule option when it is the default', async () => {
		renderComponent({defaultScheduled: true});

		expect(
			await screen.findByRole('radio', {name: /schedule-for-later/})
		).toBeChecked();
	});

	it('requires a start date to schedule the publication', async () => {
		renderComponent();

		await fillRequiredFields();

		await user.click(
			screen.getByRole('radio', {name: /schedule-for-later/})
		);

		const submitButton = screen.getByRole('button', {
			name: /schedule-publish-to-live/i,
		});

		await waitFor(() => {
			expect(submitButton).toBeDisabled();
		});

		await user.click(screen.getByRole('textbox', {name: 'start-date'}));

		await user.paste('2026-08-22 09:30');

		await waitFor(() => {
			expect(submitButton).toBeEnabled();
		});
	});

	it('schedules the publication with the cron fields', async () => {
		renderComponent();

		await fillRequiredFields();

		await user.click(
			screen.getByRole('radio', {name: /schedule-for-later/})
		);

		await user.click(screen.getByRole('textbox', {name: 'start-date'}));

		await user.paste('2026-08-22 09:30');

		await user.tab();

		await user.click(
			screen.getByRole('button', {name: /schedule-publish-to-live/i})
		);

		await waitFor(() => {
			expect(getPublishProcessCall()).toBeDefined();
		});

		const body = JSON.parse(getPublishProcessCall()![1]!.body as string);

		expect(body.cronExpression).toBe('0 30 9 22 8 ? 2026');
		expect(body.scheduleStartDate).toBe('2026-08-22T09:30:00.000Z');
		expect(body.timeZoneId).toBe('UTC');

		expect(Liferay.Util.navigate).toHaveBeenCalledWith(
			DEFAULT_PROPS.scheduledBackURL
		);
	});

	it('seeds the form from the scheduled process when editing', async () => {
		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		expect(
			await screen.findByRole('textbox', {name: /^name/i})
		).toHaveValue(SCHEDULED_PUBLISH_PROCESS.name);

		expect(
			screen.getByRole('radio', {name: /schedule-for-later/})
		).toBeChecked();
		expect(screen.getByRole('textbox', {name: 'start-date'})).toHaveValue(
			'2026-08-22 09:30'
		);
		expect(
			screen.getByRole('checkbox', {
				name: 'replicate-individual-deletions',
			})
		).toBeChecked();
	});

	it('replaces the scheduled process on submit when editing', async () => {
		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		await screen.findByRole('textbox', {name: /^name/i});
		await screen.findByText('loaded');

		await user.click(
			screen.getByRole('button', {name: /schedule-publish-to-live/i})
		);

		await waitFor(() => {
			expect(getUnscheduleCall()).toBeDefined();
		});

		expect(String(getUnscheduleCall()![0])).toContain(
			String(SCHEDULED_PUBLISH_PROCESS.id)
		);
		expect(Liferay.Util.navigate).toHaveBeenCalledWith(
			DEFAULT_PROPS.scheduledBackURL
		);
	});

	it('seeds the start date from the next fire date when the stored one is past', async () => {
		mockAPIRoutes({
			scheduledPublishProcess: {
				...SCHEDULED_PUBLISH_PROCESS,
				nextFireDate: '2027-01-04T09:30:00Z',
				scheduleStartDate: '2026-07-01T09:30:00Z',
			},
		});

		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		expect(
			await screen.findByRole('textbox', {name: 'start-date'})
		).toHaveValue('2027-01-04 09:30');
	});

	it('re-derives a one-time cron from the moved start date', async () => {
		mockAPIRoutes({
			scheduledPublishProcess: {
				...SCHEDULED_PUBLISH_PROCESS,
				cronExpression: '0 30 9 1 7 ? 2026',
				scheduleStartDate: '2026-07-01T09:30:00Z',
			},
		});

		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		await screen.findByRole('textbox', {name: /^name/i});
		await screen.findByText('loaded');

		await user.click(
			screen.getByRole('button', {name: /schedule-publish-to-live/i})
		);

		await waitFor(() => {
			expect(getPublishProcessCall()).toBeDefined();
		});

		const body = JSON.parse(getPublishProcessCall()![1]!.body as string);

		const startDate = new Date(body.scheduleStartDate);

		expect(startDate.getTime()).toBeGreaterThan(Date.now());
		expect(body.cronExpression).toBe(
			`0 ${startDate.getUTCMinutes()} ${startDate.getUTCHours()} ` +
				`${startDate.getUTCDate()} ${startDate.getUTCMonth() + 1} ? ` +
				`${startDate.getUTCFullYear()}`
		);
	});

	it('moves a stale start date forward when editing', async () => {
		mockAPIRoutes({
			scheduledPublishProcess: {
				...SCHEDULED_PUBLISH_PROCESS,
				scheduleStartDate: '2026-07-01T09:30:00Z',
			},
		});

		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		await screen.findByRole('textbox', {name: /^name/i});
		await screen.findByText('loaded');

		await user.click(
			screen.getByRole('button', {name: /schedule-publish-to-live/i})
		);

		await waitFor(() => {
			expect(getPublishProcessCall()).toBeDefined();
		});

		const body = JSON.parse(getPublishProcessCall()![1]!.body as string);

		expect(new Date(body.scheduleStartDate).getTime()).toBeGreaterThan(
			Date.now()
		);
	});

	it('does not reschedule when unscheduling the old process fails', async () => {
		mockAPIRoutes({deleteStatus: 500});

		renderComponent({
			scheduledPublishProcessId: SCHEDULED_PUBLISH_PROCESS.id,
		});

		await screen.findByRole('textbox', {name: /^name/i});
		await screen.findByText('loaded');

		await user.click(
			screen.getByRole('button', {name: /schedule-publish-to-live/i})
		);

		await waitFor(() => {
			expect(getUnscheduleCall()).toBeDefined();
		});

		expect(getPublishProcessCall()).toBeUndefined();
		expect(Liferay.Util.navigate).not.toHaveBeenCalled();
	});
});
