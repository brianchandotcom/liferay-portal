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
import {FETCH_SEARCH_DOCUMENTS_URL} from '../../mocks/data.es';
import {
	fireEvent,
	getByPlaceholderText,
	render,
	wait,
	waitForElement,
	within
} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

const MODAL_ID = 'add-result-modal';
const RESULTS_LIST_ID = 'add-result-items';

describe('AddResult', () => {
	it('shows an add result button', () => {
		const {getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		expect(getByText('add-result')).toBeInTheDocument();
	});

	it('shows a modal when the add a result button gets clicked', async () => {
		const {getByText, queryByTestId} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('add-result'));

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

		fireEvent.click(getByText('add-result'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).toBeInTheDocument();
		});

		fireEvent.click(within(getByTestId(MODAL_ID)).getByText('cancel'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).toBeNull();
		});
	});

	it('prompts a message to search in the modal', async () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		expect(modal).toHaveTextContent('search-the-engine');

		expect(modal).toHaveTextContent('search-the-engine-to-display-results');
	});

	it('does not show the prompt in the modal after enter key is pressed', async () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(modal).not.toHaveTextContent('sorry-there-are-no-results-found');
	});

	it('shows the results in the modal after enter key is pressed', async () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

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

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			getByTestId('300').querySelector('.custom-control-input')
		);

		fireEvent.click(getByText('add'));

		expect(onAddResultSubmit.mock.calls.length).toBe(1);
	});

	/**
	 * Temporarily disable pagination test (LPS-101090) until 'show more
	 * results' bug (LPS-96397) is fixed.
	 */
	xit('shows next page results in the modal after navigation is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(modal.querySelector('.lexicon-icon-angle-right'));

		await waitForElement(() => getByTestId('350'));

		expect(modal).not.toHaveTextContent('300 This is a Document Example');
		expect(modal).not.toHaveTextContent(
			'349 This is a Web Content Example'
		);
		expect(modal).toHaveTextContent('350 This is a Document Example');
		expect(modal).toHaveTextContent('399 This is a Web Content Example');
	});

	/**
	 * Temporarily disable pagination test (LPS-101090) until 'show more
	 * results' bug (LPS-96397) is fixed.
	 */
	xit('updates results count in the modal after page delta is pressed', async () => {
		const onAddResultSubmit = jest.fn();

		const {getByTestId, getByText, queryAllByText} = render(
			<AddResult
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('add-result'));

		await waitForElement(() => getByTestId(MODAL_ID));

		const modal = getByTestId(MODAL_ID);

		const input = getByPlaceholderText(modal, 'search-the-engine');

		fireEvent.change(input, {target: {value: 'test'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(queryAllByText('x-items')[4]);

		await waitForElement(() => getByTestId('339'));

		expect(modal).not.toHaveTextContent(
			'349 This is a Web Content Example'
		);
	});
});
