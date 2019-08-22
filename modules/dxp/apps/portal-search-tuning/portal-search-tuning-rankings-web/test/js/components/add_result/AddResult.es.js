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

import AddResult from '../../../../src/main/resources/META-INF/resources/js/components/add_result/AddResult.es';
import React from 'react';
import {FETCH_SEARCH_DOCUMENTS_URL} from '../../mock-data';
import {
	fireEvent,
	render,
	wait,
	waitForElement,
	within
} from '@testing-library/react';

jest.mock('../../../../src/main/resources/META-INF/resources/js/utils/api.es');

/* eslint-disable no-unused-vars */
jest.mock('react-dnd', () => ({
	DragSource: el => el => el,
	DropTarget: el => el => el
}));
/* eslint-enable no-unused-vars */

const MODAL_ID = 'add-result-modal';
const RESULTS_LIST_ID = 'add-result-items';

describe('AddResult', () => {
	/*
	Console error occurs alongside message stating that "React state
	should be wrapped in 'act'." This can be removed after react-dom@16.9.0.
	Link for reference:
	https://github.com/testing-library/react-testing-library/issues/281#issuecomment-480349256
	 */

	const originalError = console.error;

	beforeAll(() => {
		console.error = (...args) => {
			if (
				/Warning.*not wrapped in act/.test(args[0]) ||
				/Warning: Can't perform a React state update/.test(args[0])
			) {
				return;
			}
			originalError.call(console, ...args);
		};
	});

	afterAll(() => {
		console.error = originalError;
	});

	it('shows a modal when the add a result button gets clicked', async () => {
		const {getByText, queryByTestId} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).toBeInTheDocument();
		});
	});

	it('closes the modal when the cancel button gets clicked', async () => {
		const {getByTestId, getByText, queryByTestId} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).toBeInTheDocument();
		});

		fireEvent.click(within(getByTestId(MODAL_ID)).getByText('Cancel'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).not.toBeInTheDocument();
		});
	});

	it('prompts a message to search in the modal', () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		expect(modal.querySelector('.empty-state-title')).toHaveTextContent(
			'Search your engine'
		);

		expect(
			modal.querySelector('.empty-state-description')
		).toHaveTextContent('Search your engine to display results.');
	});

	it('does not show the prompt in the modal after enter key is pressed', async () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		const input = modal.querySelector('.form-control');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(
			modal.querySelector('.empty-state-title')
		).not.toBeInTheDocument();
		expect(
			modal.querySelector('.empty-state-description')
		).not.toBeInTheDocument();
	});

	it('shows the results in the modal after enter key is pressed', async () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		const input = modal.querySelector('.form-control');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(modal).toHaveTextContent('300 This is a Document Example');
		expect(modal).toHaveTextContent('309 This is a Web Content Example');
	});

	it('calls the onAddResultSubmit function after add is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		const input = modal.querySelector('.form-control');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			getByTestId('300').querySelector('.custom-control-input')
		);

		fireEvent.click(getByText('Add'));

		expect(onAddResultSubmit.mock.calls.length).toBe(1);
	});

	it('shows next page results in the modal after navigation is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		const input = modal.querySelector('.form-control');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(modal.querySelector('.page-item-next a'));

		await waitForElement(() => getByTestId('310'));

		expect(modal).not.toHaveTextContent('300 This is a Document Example');
		expect(modal).not.toHaveTextContent(
			'309 This is a Web Content Example'
		);
		expect(modal).toHaveTextContent('310 This is a Document Example');
		expect(modal).toHaveTextContent('319 This is a Web Content Example');
	});

	it('updates results count in the modal after page delta is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add Result'));

		const modal = getByTestId(MODAL_ID);

		const input = modal.querySelector('.form-control');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(getByText('50'));

		await waitForElement(() => getByTestId('349'));

		expect(modal).toHaveTextContent('349 This is a Web Content Example');
	});
});
