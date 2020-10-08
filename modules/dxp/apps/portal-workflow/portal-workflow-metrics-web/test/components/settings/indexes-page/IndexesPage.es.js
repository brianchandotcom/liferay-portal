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

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {
	ALL_INDEXES_KEY,
	METRIC_INDEXES_KEY,
	SLA_INDEXES_KEY,
} from '../../../../src/main/resources/META-INF/resources/js/components/settings/indexes-page/IndexesConstants.es';
import IndexesPage from '../../../../src/main/resources/META-INF/resources/js/components/settings/indexes-page/IndexesPage.es';
import ToasterProvider from '../../../../src/main/resources/META-INF/resources/js/shared/components/toaster/ToasterProvider.es';
import {MockRouter} from '../../../mock/MockRouter.es';

describe('The IndexesPage component should', () => {
	describe('Render and dispatch actions', () => {
		jest.runAllTimers();

		let container, indexesItems, getAllByRole, getAllByText, getByText;

		const items = [
			{
				group: SLA_INDEXES_KEY,
				key: 'sla-results',
				label: 'sla-results',
			},
			{
				group: METRIC_INDEXES_KEY,
				key: 'metrics-instances',
				label: 'metrics-instances',
			},
		];

		const clientMock = {
			get: jest
				.fn()
				.mockResolvedValueOnce({data: {items}})
				.mockResolvedValue({data: {items: []}}),
			patch: jest.fn().mockRejectedValueOnce().mockResolvedValue(),
		};

		beforeAll(() => {
			jest.useFakeTimers();

			const renderResult = render(
				<MockRouter client={clientMock}>
					<ToasterProvider>
						<IndexesPage />
					</ToasterProvider>
				</MockRouter>
			);

			container = renderResult.container;
			getAllByRole = renderResult.getAllByRole;
			getAllByText = renderResult.getAllByText;
			getByText = renderResult.getByText;
			window.location.hash = '/settings/indexes';
		});

		test('Render information correctly ', () => {
			const pageTitle = getByText('workflow-index-actions');
			const reindexAllLabel = getByText('workflow-indexes');
			const reindexAllBtn = getAllByText('reindex-all')[0];

			expect(pageTitle).toBeTruthy();
			expect(reindexAllLabel).toBeTruthy();
			expect(reindexAllBtn).toBeTruthy();

			fireEvent.click(reindexAllBtn);
		});

		test('Render error toast when dispatch any reindex action with error', () => {
			const alertToast = getByText('your-request-has-failed');
			const alertClose = container.querySelector('button.close');

			expect(alertToast).toBeTruthy();

			fireEvent.click(alertClose);
		});

		test('Render with items correctly', () => {
			indexesItems = getAllByRole('listitem');

			expect(indexesItems[1]).toHaveTextContent('metrics');
			expect(indexesItems[2]).toHaveTextContent('reindex-all');
			expect(indexesItems[3]).toHaveTextContent('reindex');
			expect(indexesItems[2]).toHaveTextContent(
				'workflow-metrics-indexes'
			);
			expect(indexesItems[3]).toHaveTextContent('metrics-instances');
			expect(indexesItems[4]).toHaveTextContent('slas');
			expect(indexesItems[5]).toHaveTextContent('reindex-all');
			expect(indexesItems[6]).toHaveTextContent('reindex');
			expect(indexesItems[5]).toHaveTextContent('workflow-sla-indexes');
			expect(indexesItems[6]).toHaveTextContent('sla-results');
			expect(indexesItems[2].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).not.toBeDisabled();
		});

		test('Render progress status when dispatch any reindex action', async () => {
			const reindexAllBtn = getAllByText('reindex-all')[0];

			await fireEvent.click(reindexAllBtn);

			const reindexAllStatus = container.querySelectorAll(
				'.progress-group'
			)[0];

			expect(indexesItems[2].children[1].children[0]).toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).toBeDisabled();

			expect(reindexAllStatus.children[1]).toHaveTextContent('0%');

			await jest.runOnlyPendingTimers();

			await waitForElementToBeRemoved(() =>
				document.querySelector('div.progress')
			);

			let alertToast = getByText('all-x-have-reindexed-successfully');

			expect(alertToast).toBeTruthy();
			expect(indexesItems[2].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).not.toBeDisabled();

			await jest.runOnlyPendingTimers();

			const alertContainer = document.querySelector('.alert-container');
			expect(alertContainer.children[0].children.length).toBe(0);

			await fireEvent.click(indexesItems[2].children[1].children[0]);

			expect(indexesItems[3].children[1].children[0]).toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[2]).toHaveTextContent('0%');

			await jest.runOnlyPendingTimers();

			await waitForElementToBeRemoved(() =>
				document.querySelector('div.progress')
			);

			alertToast = getByText('all-x-have-reindexed-successfully');

			expect(alertToast).toBeTruthy();
			expect(indexesItems[2].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).not.toBeDisabled();

			await fireEvent.click(indexesItems[5].children[1].children[0]);

			expect(indexesItems[6].children[1].children[0]).toBeDisabled();
			expect(indexesItems[2].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[5]).toHaveTextContent('0%');

			await jest.runOnlyPendingTimers();

			await waitForElementToBeRemoved(() =>
				document.querySelector('div.progress')
			);

			alertToast = getByText('all-x-have-reindexed-successfully');

			expect(alertToast).toBeTruthy();
			expect(indexesItems[2].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[3].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[5].children[1].children[0]).not.toBeDisabled();
			expect(indexesItems[6].children[1].children[0]).not.toBeDisabled();
		});
	});

	describe('Render loading reindexes', () => {
		jest.runAllTimers();

		let container, getAllByRole;

		const items = [
			{
				group: SLA_INDEXES_KEY,
				key: 'sla-results',
				label: 'sla-results',
			},
			{
				group: METRIC_INDEXES_KEY,
				key: 'metrics-instances',
				label: 'metrics-instances',
			},
		];

		const clientMock = {
			get: jest.fn().mockResolvedValue({
				data: {items},
			}),
		};

		const mockStatuses = [
			{
				completionPercentage: 50,
				key: ALL_INDEXES_KEY,
			},
			{
				completionPercentage: 20,
				key: METRIC_INDEXES_KEY,
			},
			{
				completionPercentage: 40,
				key: 'metrics-instances',
			},
			{
				completionPercentage: 60,
				key: SLA_INDEXES_KEY,
			},
			{
				completionPercentage: 80,
				key: 'sla-results',
			},
		];

		beforeAll(() => {
			cleanup();

			const renderResult = render(
				<MockRouter
					client={clientMock}
					initialReindexStatuses={mockStatuses}
				>
					<ToasterProvider>
						<IndexesPage />
					</ToasterProvider>
				</MockRouter>
			);

			container = renderResult.container;
			getAllByRole = renderResult.getAllByRole;
		});

		test('Render all indexes on progress', () => {
			const indexActions = getAllByRole('listitem');

			const reindexAllStatus = container.querySelectorAll(
				'.progress-group'
			)[0];

			expect(reindexAllStatus.children[1]).toHaveTextContent('50%');

			expect(indexActions[2]).toHaveTextContent('0%');
			expect(indexActions[2]).toHaveTextContent('20%');
			expect(indexActions[3]).toHaveTextContent('40%');
			expect(indexActions[5]).toHaveTextContent('60%');
			expect(indexActions[6]).toHaveTextContent('80%');
		});
	});
});
