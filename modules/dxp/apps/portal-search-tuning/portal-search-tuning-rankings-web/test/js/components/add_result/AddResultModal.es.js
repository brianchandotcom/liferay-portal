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
import {
	fireEvent,
	getByPlaceholderText,
	render,
	wait,
	waitForElement
} from '@testing-library/react';
import {
	FETCH_SEARCH_DOCUMENTS_URL,
	getMockResultsData
} from '../../mocks/data.es';
import '@testing-library/jest-dom/extend-expect';

const MODAL_ID = 'add-result-modal';
const RESULTS_LIST_ID = 'add-result-items';

describe('AddResultModal', () => {
	beforeEach(() => {
		fetch.mockResponse(JSON.stringify(getMockResultsData()));
	});

	it('renders the modal', async () => {
		const {findByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		const modalElement = await findByTestId(MODAL_ID);

		expect(modalElement).toBeInTheDocument();
	});

	it('prompts a message to search in the modal', async () => {
		// This is a temporary mock to get the initial message to display.
		// There currently isn't a way to disable the initial fetch, so the
		// current workaround is showing an initial message if a refetch
		// hasn't been called.
		//
		// This should be removed after disabling the initial fetch.
		fetch.mockResponse(JSON.stringify({}));

		const {getByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		expect(modal).toHaveTextContent('search-the-engine');

		expect(modal).toHaveTextContent('search-the-engine-to-display-results');
	});

	it('searches for results and calls the onAddResultSubmit function after add is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			getByTestId('100').querySelector('.custom-control-input')
		);

		fireEvent.click(getByText('add'));

		expect(onAddResultSubmit.mock.calls.length).toBe(1);
	});

	it('disables the add button when the selected results are empty', async () => {
		const {getByTestId, getByText} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		expect(getByText('add')).toHaveAttribute('disabled');
	});

	it('shows the results in the modal after enter key is pressed', async () => {
		const {getByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(modal).toHaveTextContent('100 This is a Document Example');
		expect(modal).toHaveTextContent('109 This is a Web Content Example');
	});

	it('does not show the prompt in the modal after enter key is pressed', async () => {
		const {getByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(modal).not.toHaveTextContent('sorry-there-are-no-results-found');
	});

	it('closes the modal when the cancel button gets clicked', async () => {
		const {getByText, queryByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => queryByTestId(MODAL_ID));

		fireEvent.click(getByText('cancel'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).toBeNull();
		});
	});

	/**
	 * Temporarily disable pagination test until pagination is fixed
	 * in LPS-96397. (LPS-101090)
	 */
	xit('shows next page results in the modal after navigation is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(modal.querySelector('.lexicon-icon-angle-right'));

		await waitForElement(() => getByTestId('150'));

		expect(modal).not.toHaveTextContent('100 This is a Document Example');
		expect(modal).not.toHaveTextContent(
			'149 This is a Web Content Example'
		);
		expect(modal).toHaveTextContent('150 This is a Document Example');
		expect(modal).toHaveTextContent('199 This is a Web Content Example');
	});

	/**
	 * Temporarily disable pagination test until pagination is fixed
	 * in LPS-96397. (LPS-101090)
	 */
	xit('updates results count in the modal after page delta is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, queryAllByText} = render(
			<AddResultModal
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
				onCloseModal={jest.fn()}
			/>
		);

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(queryAllByText('x-items')[4]);

		await waitForElement(() => getByTestId('139'));

		expect(modal).not.toHaveTextContent(
			'149 This is a Web Content Example'
		);
	});
});
