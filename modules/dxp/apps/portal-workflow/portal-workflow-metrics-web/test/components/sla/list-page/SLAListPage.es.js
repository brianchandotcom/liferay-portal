/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {SLAContext} from '../../../../src/main/resources/META-INF/resources/js/components/sla/SLAContainer.es';
import SLAListPage from '../../../../src/main/resources/META-INF/resources/js/components/sla/list-page/SLAListPage.es';
import ToasterProvider from '../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The SLAListPage component should', () => {
	describe('Be rendered correctly with no items', () => {
		let getByTestId, getByTitle;

		const clientMock = {
			get: jest.fn().mockResolvedValue({data: {items: []}}),
		};

		beforeAll(() => {
			const renderResult = render(
				<MockRouter client={clientMock}>
					<ToasterProvider>
						<SLAContext.Provider value={{}}>
							<SLAListPage
								page="1"
								pageSize="1"
								processId="36001"
							/>
						</SLAContext.Provider>
					</ToasterProvider>
				</MockRouter>
			);

			getByTestId = renderResult.getByTestId;
			getByTitle = renderResult.getByTitle;
		});

		test('Show navbar with New SLA button with correct link', () => {
			const newSLAButton = getByTitle('new-sla');
			const childLink = newSLAButton.children[0];

			expect(childLink.getAttribute('href')).toContain('/sla/36001/new');
		});

		test('Display empty state', () => {
			const emptyStateMessage = getByTestId('emptyStateMsg');

			expect(emptyStateMessage).toHaveTextContent(
				'sla-allows-to-define-and-measure-process-performance'
			);
		});
	});

	describe('Be rendered correctly with items', () => {
		let container, getAllByTestId, getByTestId, getByText;

		const data = {
			actions: {},
			items: [
				{
					dateModified: '2020-04-03T18:01:07Z',
					description: '',
					duration: 60000,
					id: 37975,
					name: 'SLA',
					processId: 36001,
				},
			],
			totalCount: 1,
		};

		const clientMock = {
			delete: jest.fn().mockRejectedValueOnce({}).mockResolvedValue({}),
			get: jest.fn().mockResolvedValue({data}),
		};

		const contextMock = {SLAUpdated: true, setSLAUpdated: jest.fn()};

		beforeAll(() => {
			cleanup();

			const renderResult = render(
				<MockRouter client={clientMock}>
					<ToasterProvider>
						<SLAContext.Provider value={contextMock}>
							<SLAListPage
								page="1"
								pageSize="1"
								processId="36001"
							/>
						</SLAContext.Provider>
					</ToasterProvider>
				</MockRouter>
			);

			container = renderResult.container;
			getAllByTestId = renderResult.getAllByTestId;
			getByTestId = renderResult.getByTestId;
			getByText = renderResult.getByText;
		});

		test('Show table columns', () => {
			const slaDateModifiedHead = getByText('last-modified');
			const slaDescriptionHead = getByText('description');
			const slaDurationHead = getByText('duration');
			const slaNameHead = getByText('sla-name');
			const slaStatusHead = getByText('status');

			expect(slaDateModifiedHead).toBeTruthy();
			expect(slaDescriptionHead).toBeTruthy();
			expect(slaDurationHead).toBeTruthy();
			expect(slaNameHead).toBeTruthy();
			expect(slaStatusHead).toBeTruthy();
		});

		test('Show items info and kebab menu', () => {
			const kebab = getByTestId('kebab');
			const slaDateModified = getByText('Apr 03');
			const slaDescription = slaDateModified.parentNode.children[1];
			const slaDuration = getByText('1min');
			const slaName = container.querySelector('.table-list-title');
			const slaStatus = getByText('running');

			expect(slaName).toHaveTextContent('SLA');
			expect(slaDescription).toHaveTextContent('');
			expect(slaStatus).toBeTruthy();
			expect(slaDuration).toBeTruthy();
			expect(slaDateModified).toBeTruthy();
			fireEvent.click(kebab);

			const dropDownItems = getAllByTestId('kebabDropItems');

			expect(dropDownItems[0]).toHaveTextContent('edit');
			expect(dropDownItems[1]).toHaveTextContent('delete');

			fireEvent.click(dropDownItems[1]);

			jest.runAllTimers();
		});

		test('Display modal after clicking on delete option of kebab menu', () => {
			const cancelButton = getByText('cancel');
			const deleteButton = getByText('ok');
			const deleteModal = getByText(
				'deleting-slas-will-reflect-on-report-data'
			);

			expect(deleteModal).toBeTruthy();
			expect(cancelButton).toBeTruthy();
			expect(deleteButton).toBeTruthy();

			fireEvent.click(deleteButton);
		});

		test('Display toast when failure occur while trying to confirm item delete', () => {
			const alertToast = getByTestId('alertToast');
			const alertClose = alertToast.children[1];
			const deleteButton = getByText('ok');

			expect(alertToast).toHaveTextContent('your-request-has-failed');

			fireEvent.click(alertClose);

			const alertContainer = getByTestId('alertContainer');

			expect(alertContainer.children[0].children.length).toBe(0);

			fireEvent.click(deleteButton);
		});

		test('Display toast when confirm item delete', () => {
			const alertToast = getByTestId('alertToast');
			const alertClose = alertToast.children[1];

			expect(alertToast).toHaveTextContent('sla-was-deleted');

			fireEvent.click(alertClose);

			const alertContainer = getByTestId('alertContainer');

			expect(alertContainer.children[0].children.length).toBe(0);
		});

		test('Display info alert and toast after a SLA is created or updated', () => {
			const updateAlert = container.querySelector('.alert-info');

			expect(updateAlert).toHaveTextContent(
				'one-or-more-slas-are-being-updated'
			);

			fireEvent.click(updateAlert.children[1]);

			expect(contextMock.setSLAUpdated).toHaveBeenCalledWith(false);
		});
	});

	describe('Be rendered correctly with blocked items', () => {
		let container, getByText;

		const data = {
			actions: {},
			items: [
				{
					calendarKey: '',
					dateModified: '2020-04-06T01:26:17Z',
					description: '',
					duration: 540000,
					id: 39409,
					name: 'SLAb',
					processId: 36001,
					status: 2,
					stopNodeKeys: {
						nodeKeys: [
							{
								executionType: 'end',
								id: '39522',
							},
						],
						status: 0,
					},
				},
				{
					calendarKey: '',
					dateModified: '2020-04-03T18:01:07Z',
					description: '',
					duration: 60000,
					id: 37975,
					name: 'SLA',
					processId: 36001,
					startNodeKeys: {
						nodeKeys: [
							{
								executionType: 'begin',
								id: '36005',
							},
						],
						status: 0,
					},
					status: 0,
					stopNodeKeys: {
						nodeKeys: [
							{
								executionType: 'end',
								id: '36003',
							},
						],
						status: 0,
					},
				},
			],
			lastPage: 1,
			page: 1,
			pageSize: 20,
			totalCount: 1,
		};

		const clientMock = {
			get: jest.fn().mockResolvedValue({data}),
		};

		beforeAll(() => {
			cleanup();

			const renderResult = render(
				<MockRouter client={clientMock}>
					<ToasterProvider>
						<SLAContext.Provider value={{}}>
							<SLAListPage
								page="1"
								pageSize="1"
								processId="36001"
							/>
						</SLAContext.Provider>
					</ToasterProvider>
				</MockRouter>
			);

			container = renderResult.container;
			getByText = renderResult.getByText;
		});

		test('Show alert error', () => {
			const alertBlockedSLA = getByText(
				'fix-blocked-slas-to-resume-accurate-reporting'
			);

			expect(alertBlockedSLA).toBeTruthy();

			const alertClose = container.querySelector('button.close');

			fireEvent.click(alertClose);
		});

		test('Show dividers', () => {
			const slaBlockedDivider = getByText('BLOCKED');
			const slaRunningDivider = getByText('RUNNING');

			expect(slaBlockedDivider).toBeTruthy();
			expect(slaRunningDivider).toBeTruthy();
		});

		test('Show blocked items info correctly', () => {
			const dangerIcon = container.querySelector(
				'.lexicon-icon-exclamation-full'
			);
			const slaStatusBlocked = getByText('blocked');

			expect(dangerIcon).toBeTruthy();
			expect(dangerIcon.classList).toContain('text-danger');
			expect(slaStatusBlocked.classList).toContain('text-danger');
		});
	});
});
