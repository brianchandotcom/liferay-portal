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

import AddResultModal from '../../../../src/main/resources/META-INF/resources/js/components/add_result/AddResultModal.es';
import React from 'react';
import {fireEvent, render, waitForElement} from '@testing-library/react';
import {getMockResultsData} from '../../mocks/data.es';
import '@testing-library/jest-dom/extend-expect';

const DELTAS = [5, 10, 20, 30, 50];

const RESULTS_DATA = {
	items: getMockResultsData(10, 0, 'search', false).documents,
	total: 250
};

const MODAL_ID = 'add-result-modal';
const RESULTS_LIST_ID = 'add-result-items';

/* eslint-disable no-unused-vars */
jest.mock('react-dnd', () => ({
	DragSource: el => el => el,
	DropTarget: el => el => el
}));
/* eslint-enable no-unused-vars */

describe('AddResultModal', () => {
	it('shows a modal', async () => {
		const {getByTestId} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		expect(getByTestId(MODAL_ID)).toBeInTheDocument();
	});

	it('shows no results list when dataLoading is true', () => {
		const {queryByTestId} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={true}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		expect(queryByTestId(RESULTS_LIST_ID)).not.toBeInTheDocument();
	});

	it('has placeholder text for searching the engine', async () => {
		const {getByPlaceholderText, getByTestId} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		expect(getByPlaceholderText('search-the-engine')).toBeInTheDocument();
	});

	it('calls the handleSearchChange function when text is entered', async () => {
		const handleSearchChange = jest.fn();

		const {getByPlaceholderText, getByTestId} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={handleSearchChange}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const input = getByPlaceholderText('search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		expect(handleSearchChange.mock.calls.length).toBe(1);
	});

	it('calls the handleSearchKeyDown function when enter is pressed', async () => {
		const handleSearchKeyDown = jest.fn();

		const {getByPlaceholderText, getByTestId} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={handleSearchKeyDown}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const input = getByPlaceholderText('search-the-engine');

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		expect(handleSearchKeyDown.mock.calls.length).toBe(1);
	});

	it('lists the results', async () => {
		const {getByTestId, getByText} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		expect(getByText('300 This is a Document Example')).toBeInTheDocument();
		expect(
			getByText('305 This is a Web Content Example')
		).toBeInTheDocument();
	});

	it('calls the handleSubmit function when add button is pressed', async () => {
		const handleSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[1]} // Select at least 1 item to enable the add button.
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => ['300'])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={() => handleSubmit}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		fireEvent.click(getByText('add'));

		expect(handleSubmit.mock.calls.length).toBe(1);
	});

	it('disables the add button when the selected results are empty', async () => {
		const {getByTestId, getByText} = render(
			<AddResultModal
				DELTAS={DELTAS}
				addResultSearchQuery={''}
				addResultSelectedIds={[]}
				dataLoading={false}
				getCurrentResultSelectedIds={jest.fn(() => [])}
				handleAllCheckbox={jest.fn()}
				handleClearAllSelected={jest.fn()}
				handleClose={jest.fn()}
				handleDeltaChange={jest.fn()}
				handlePageChange={jest.fn()}
				handleSearchChange={jest.fn()}
				handleSearchEnter={jest.fn()}
				handleSearchKeyDown={jest.fn()}
				handleSelect={jest.fn()}
				handleSubmit={jest.fn()}
				page={1}
				renderEmptyState={jest.fn()}
				results={RESULTS_DATA}
				selectedDelta={DELTAS[2]}
				showModal={true}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		expect(getByText('add')).toHaveAttribute('disabled');
	});
});
