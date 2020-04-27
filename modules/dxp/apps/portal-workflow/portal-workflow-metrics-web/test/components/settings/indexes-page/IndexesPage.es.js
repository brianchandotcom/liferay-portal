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

import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
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

		let getAllByTestId, getByTestId;

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
				.mockResolvedValue({data: {items: [], totalCount: 0}}),
			patch: jest.fn().mockRejectedValueOnce().mockResolvedValue(),
		};

		beforeAll(() => {
			const renderResult = render(
				<MockRouter client={clientMock}>
					<ToasterProvider>
						<IndexesPage />
					</ToasterProvider>
				</MockRouter>
			);

			getAllByTestId = renderResult.getAllByTestId;
			getByTestId = renderResult.getByTestId;
		});

		test('Render information correctly ', () => {
			const pageTitle = getByTestId('pageTitle');
			const reindexAllLabel = getByTestId('reindexAllLabel');
			const reindexAllBtn = getByTestId('reindexAllBtn');

			expect(pageTitle).toHaveTextContent('workflow-index-actions');
			expect(reindexAllLabel).toHaveTextContent('workflow-indexes');
			expect(reindexAllBtn).toHaveTextContent('reindex-all');

			fireEvent.click(reindexAllBtn);
		});

		test('Render error toast when dispatch any reindex action with error', async () => {
			const alertToast = await getByTestId('alertToast');
			const alertClose = alertToast.children[1];

			expect(alertToast).toHaveTextContent(
				'error:your-request-has-failed'
			);

			fireEvent.click(alertClose);
		});

		test('Render with items correctly', () => {
			const indexesList = getAllByTestId('indexesList');
			const indexActions = getAllByTestId('indexAction');
			const indexLabels = getAllByTestId('indexLabel');

			expect(indexesList[0].children[0]).toHaveTextContent('metrics');
			expect(indexActions[0]).toHaveTextContent('reindex-all');
			expect(indexActions[1]).toHaveTextContent('reindex');
			expect(indexLabels[0]).toHaveTextContent(
				'workflow-metrics-indexes'
			);
			expect(indexLabels[1]).toHaveTextContent('metrics-instances');

			expect(indexesList[1].children[0]).toHaveTextContent('slas');
			expect(indexActions[2]).toHaveTextContent('reindex-all');
			expect(indexActions[3]).toHaveTextContent('reindex');
			expect(indexLabels[2]).toHaveTextContent('workflow-sla-indexes');
			expect(indexLabels[3]).toHaveTextContent('sla-results');

			expect(indexActions[0].children[0]).not.toBeDisabled();
			expect(indexActions[1].children[0]).not.toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0]).not.toBeDisabled();
		});

		test('Render progress status when dispatch any reindex action', async () => {
			const reindexAllBtn = getByTestId('reindexAllBtn');

			await fireEvent.click(reindexAllBtn);

			const indexActions = getAllByTestId('indexAction');
			const reindexAllStatus = getByTestId('reindexAllStatus');

			expect(indexActions[0].children[0]).toBeDisabled();
			expect(indexActions[1].children[0]).toBeDisabled();
			expect(indexActions[2].children[0]).toBeDisabled();
			expect(indexActions[3].children[0]).toBeDisabled();
			expect(reindexAllStatus.children[1]).toHaveTextContent('0%');

			await jest.runOnlyPendingTimers();

			let alertToast = await waitForElement(() =>
				getByTestId('alertToast')
			);

			expect(indexActions[0].children[0]).not.toBeDisabled();
			expect(indexActions[1].children[0]).not.toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0]).not.toBeDisabled();

			expect(alertToast).toHaveTextContent(
				'success:all-x-have-reindexed-successfully'
			);

			await jest.runOnlyPendingTimers();

			const alertContainer = getByTestId('alertContainer');
			expect(alertContainer.children[0].children.length).toBe(0);

			await fireEvent.click(indexActions[0].children[0]);

			expect(indexActions[1].children[0]).toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0]).not.toBeDisabled();
			expect(indexActions[0].children[0].children[1]).toHaveTextContent(
				'0%'
			);

			await jest.runOnlyPendingTimers();

			alertToast = await waitForElement(() => getByTestId('alertToast'));

			expect(indexActions[0].children[0]).not.toBeDisabled();
			expect(indexActions[1].children[0]).not.toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0]).not.toBeDisabled();

			expect(alertToast).toHaveTextContent(
				'success:all-x-have-reindexed-successfully'
			);

			await fireEvent.click(indexActions[3].children[0]);

			expect(indexActions[0].children[0]).not.toBeDisabled();
			expect(indexActions[1].children[0]).not.toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0].children[1]).toHaveTextContent(
				'0%'
			);

			await jest.runOnlyPendingTimers();

			alertToast = await waitForElement(() => getByTestId('alertToast'));

			expect(indexActions[0].children[0]).not.toBeDisabled();
			expect(indexActions[1].children[0]).not.toBeDisabled();
			expect(indexActions[2].children[0]).not.toBeDisabled();
			expect(indexActions[3].children[0]).not.toBeDisabled();

			expect(alertToast).toHaveTextContent(
				'success:x-has-reindexed-successfully'
			);
		});
	});

	describe('Render loading reindexes', () => {
		jest.runAllTimers();

		let getAllByTestId, getByTestId;

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

			getAllByTestId = renderResult.getAllByTestId;
			getByTestId = renderResult.getByTestId;
		});

		test('Render all indexes on progress', () => {
			const indexActions = getAllByTestId('indexAction');
			const reindexAllStatus = getByTestId('reindexAllStatus');

			expect(reindexAllStatus.children[1]).toHaveTextContent('50%');
			expect(indexActions[0].children[0].children[1]).toHaveTextContent(
				'0%'
			);
			expect(indexActions[0].children[0].children[1]).toHaveTextContent(
				'20%'
			);
			expect(indexActions[1].children[0].children[1]).toHaveTextContent(
				'40%'
			);
			expect(indexActions[2].children[0].children[1]).toHaveTextContent(
				'60%'
			);
			expect(indexActions[3].children[0].children[1]).toHaveTextContent(
				'80%'
			);
		});
	});
});
