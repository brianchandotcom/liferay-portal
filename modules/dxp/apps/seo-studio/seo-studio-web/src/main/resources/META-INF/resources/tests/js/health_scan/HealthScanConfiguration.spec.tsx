/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import HealthScanConfiguration from '../../../js/health_scan/components/HealthScanConfiguration';

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

function getSaveButton() {
	return screen.getByRole('button', {name: 'save'});
}

function getCancelButton() {
	return screen.getByRole('button', {name: 'cancel'});
}

function toggleContentTechnical() {
	fireEvent.click(screen.getByLabelText('crawler-insights'));
}

const TIME_ZONES = [
	{label: '(UTC) Greenwich Mean Time', value: 'UTC'},
	{label: '(UTC -05:00) Eastern Standard Time', value: 'America/New_York'},
];

type ConfigurationProps = React.ComponentProps<typeof HealthScanConfiguration>;

function renderConfiguration(props: Partial<ConfigurationProps> = {}) {
	return render(
		<HealthScanConfiguration
			defaultTimeZoneId="UTC"
			domainId={1}
			scanConfig={null}
			schedule={null}
			timeZones={TIME_ZONES}
			{...props}
		/>
	);
}

describe('HealthScanConfiguration', () => {
	beforeEach(() => {
		(openToast as jest.Mock).mockClear();

		(Liferay.Util as unknown) = {
			fetch: jest.fn().mockResolvedValue({ok: true}),
		};
	});

	describe('schedule section', () => {
		it('shows the schedule fields when Auto Scan is on', () => {
			renderConfiguration();

			expect(screen.getByLabelText('frequency')).toBeInTheDocument();
			expect(screen.getByLabelText('time')).toHaveValue('00:00');
			expect(screen.getByLabelText('time-zone')).toBeInTheDocument();
		});

		it('populates the time zone select with the provided time zones', () => {
			renderConfiguration();

			expect(
				screen.getByText('(UTC -05:00) Eastern Standard Time')
			).toBeInTheDocument();
		});

		it('reveals the day-of-week selector only for Weekly', () => {
			renderConfiguration();

			expect(
				screen.queryByLabelText('day-of-week')
			).not.toBeInTheDocument();

			fireEvent.change(screen.getByLabelText('frequency'), {
				target: {value: 'weekly'},
			});

			expect(screen.getByLabelText('day-of-week')).toBeInTheDocument();
			expect(
				screen.queryByLabelText('day-of-month')
			).not.toBeInTheDocument();
		});

		it('reveals the day-of-month selector only for Monthly', () => {
			renderConfiguration();

			fireEvent.change(screen.getByLabelText('frequency'), {
				target: {value: 'monthly'},
			});

			expect(screen.getByLabelText('day-of-month')).toBeInTheDocument();
			expect(
				screen.queryByLabelText('day-of-week')
			).not.toBeInTheDocument();
		});

		it('hides the schedule fields when Auto Scan is off', () => {
			renderConfiguration();

			fireEvent.click(
				screen.getByLabelText(/auto-scan/, {selector: 'input'})
			);

			expect(
				screen.queryByLabelText('frequency')
			).not.toBeInTheDocument();
			expect(screen.queryByLabelText('time')).not.toBeInTheDocument();
			expect(
				screen.queryByLabelText('time-zone')
			).not.toBeInTheDocument();
		});
	});

	describe('scope section', () => {
		it('renders a block for every engine, enabled by default', () => {
			renderConfiguration();

			expect(screen.getAllByLabelText('scope')).toHaveLength(4);
		});

		it("hides an engine's fields when its toggle is off", () => {
			renderConfiguration();

			toggleContentTechnical();

			expect(screen.getAllByLabelText('scope')).toHaveLength(3);
		});

		it('reveals the Included Path input only for "Included Paths Only"', () => {
			renderConfiguration();

			expect(
				screen.queryByLabelText(/included-path/, {selector: 'input'})
			).not.toBeInTheDocument();

			fireEvent.change(screen.getAllByLabelText('scope')[0], {
				target: {value: 'includedPathsOnly'},
			});

			expect(
				screen.getByLabelText(/included-path/, {selector: 'input'})
			).toBeInTheDocument();
		});

		it('shows no path input for "Public Sitemap Pages"', () => {
			renderConfiguration();

			fireEvent.change(screen.getAllByLabelText('scope')[0], {
				target: {value: 'sitemapOnly'},
			});

			expect(
				screen.queryByLabelText(/included-path/, {selector: 'input'})
			).not.toBeInTheDocument();
			expect(
				screen.queryByLabelText(/excluded-path/, {selector: 'input'})
			).not.toBeInTheDocument();
			expect(getSaveButton()).toBeEnabled();
		});
	});

	describe('path validation', () => {
		function selectIncludedPathsOnly() {
			renderConfiguration();

			fireEvent.change(screen.getAllByLabelText('scope')[0], {
				target: {value: 'includedPathsOnly'},
			});

			return screen.getByLabelText(/included-path/, {selector: 'input'});
		}

		it('shows an inline error and disables Save for an invalid path', () => {
			const input = selectIncludedPathsOnly();

			fireEvent.change(input, {target: {value: 'blog/*'}});

			expect(screen.getByText('invalid-path-format')).toBeInTheDocument();
			expect(getSaveButton()).toBeDisabled();
		});

		it('clears the error and enables Save for a valid path', () => {
			const input = selectIncludedPathsOnly();

			fireEvent.change(input, {target: {value: 'blog/*'}});
			fireEvent.change(input, {target: {value: '/blog/*'}});

			expect(
				screen.queryByText('invalid-path-format')
			).not.toBeInTheDocument();
			expect(getSaveButton()).toBeEnabled();
		});

		it('shows the required message and disables Save while Included Paths is empty', () => {
			const input = selectIncludedPathsOnly();

			expect(input).toHaveValue('');
			expect(
				screen.getByText('this-field-is-required')
			).toBeInTheDocument();
			expect(
				screen.queryByText('invalid-path-format')
			).not.toBeInTheDocument();
			expect(getSaveButton()).toBeDisabled();
		});

		it('requires an Excluded Path and disables Save while it is empty', () => {
			renderConfiguration();

			fireEvent.change(screen.getAllByLabelText('scope')[0], {
				target: {value: 'excludedPathsOnly'},
			});

			const input = screen.getByLabelText(/excluded-path/, {
				selector: 'input',
			});

			expect(input).toHaveValue('');
			expect(getSaveButton()).toBeDisabled();

			fireEvent.change(input, {target: {value: '/private/*'}});

			expect(getSaveButton()).toBeEnabled();
		});
	});

	describe('save and cancel', () => {
		it('keeps Save and Cancel enabled when no change has been made', () => {
			renderConfiguration();

			expect(getSaveButton()).toBeEnabled();
			expect(getCancelButton()).toBeEnabled();
		});

		it('reverts unsaved changes on Cancel', () => {
			renderConfiguration();

			toggleContentTechnical();

			expect(screen.getAllByLabelText('scope')).toHaveLength(3);

			fireEvent.click(getCancelButton());

			expect(screen.getAllByLabelText('scope')).toHaveLength(4);
			expect(getSaveButton()).toBeEnabled();
			expect(getCancelButton()).toBeEnabled();
		});
	});

	describe('persistence', () => {
		it('disables Save when no domain is available', () => {
			renderConfiguration({domainId: null});

			expect(getSaveButton()).toBeDisabled();
		});

		it('persists the configuration to the domain on Save', async () => {
			const fetchMock = Liferay.Util.fetch as jest.Mock;

			renderConfiguration({domainId: 42});

			fireEvent.click(getSaveButton());

			await waitFor(() => expect(fetchMock).toHaveBeenCalledTimes(1));

			const [url, init] = fetchMock.mock.calls[0];

			expect(url).toBe('/o/seo-studio/domains/42');
			expect(init.method).toBe('PATCH');

			const body = JSON.parse(init.body);

			expect(body.autoScanEnabled).toBe(true);
			expect(body.scanFrequency).toBe('daily');
			expect(body.scanTimeZone).toBe('UTC');

			const scanConfig = JSON.parse(body.scanConfig);

			expect(scanConfig.engines.crawler.enabled).toBe(true);

			await waitFor(() =>
				expect(openToast).toHaveBeenCalledWith(
					expect.objectContaining({type: 'success'})
				)
			);
		});

		it('shows an error toast when the save request fails', async () => {
			(Liferay.Util.fetch as jest.Mock).mockResolvedValue({ok: false});

			renderConfiguration();

			fireEvent.click(getSaveButton());

			await waitFor(() =>
				expect(openToast).toHaveBeenCalledWith(
					expect.objectContaining({type: 'danger'})
				)
			);
		});

		it('seeds the form from the persisted configuration', () => {
			renderConfiguration({
				scanConfig: JSON.stringify({
					engines: {
						crawler: {
							enabled: false,
							excludedPaths: '',
							includedPaths: '',
							maxPagesPerScan: 100,
							rankingMethod: 'topByPageVisit',
							scope: 'allPublishedPages',
						},
					},
				}),
				schedule: {
					autoScanEnabled: true,
					scanDayOfWeek: 'WE',
					scanFrequency: 'weekly',
				},
			});

			expect(screen.getByLabelText('frequency')).toHaveValue('weekly');
			expect(screen.getByLabelText('day-of-week')).toHaveValue('WE');
			expect(
				screen.queryByLabelText('crawler-insights')
			).not.toBeChecked();
		});

		it('merges a partial persisted engine over its defaults', () => {
			renderConfiguration({
				scanConfig: JSON.stringify({
					engines: {
						crawler: {
							scope: 'sitemapOnly',
						},
					},
				}),
			});

			expect(screen.getByLabelText('crawler-insights')).toBeChecked();
			expect(screen.getAllByLabelText('scope')).toHaveLength(4);
			expect(screen.getAllByLabelText('scope')[0]).toHaveValue(
				'sitemapOnly'
			);
		});
	});

	describe('collapsible sections', () => {
		it('toggles a section open and closed from its header', () => {
			renderConfiguration();

			const header = screen.getByRole('button', {name: 'schedule'});

			expect(header).toHaveAttribute('aria-expanded', 'true');

			fireEvent.click(header);

			expect(header).toHaveAttribute('aria-expanded', 'false');
		});
	});
});
