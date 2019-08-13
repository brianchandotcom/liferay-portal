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
import ReactModal from 'react-modal';
import {FETCH_VISIBLE_DOCUMENTS_URL} from '../../mock-data';
import {fireEvent, render, waitForElement} from '@testing-library/react';

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
	beforeEach(() => {
		ReactModal.setAppElement('body');
	});

	it('shows a modal when the add a result button gets clicked', async () => {
		const {getByText, queryByTestId} = render(
			<AddResult
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

		expect(queryByTestId(MODAL_ID)).not.toBeNull();
	});

	it('closes the modal when the cancel button gets clicked', () => {
		const {getByText, queryByTestId} = render(
			<AddResult
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

		fireEvent.click(getByText('Cancel'));

		expect(queryByTestId(MODAL_ID)).toBeNull();
	});

	it('prompts a message to search in the modal', () => {
		const {getByTestId, getByText} = render(
			<AddResult
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				onAddResultSubmit={onAddResultSubmit}
			/>
		);

		fireEvent.click(getByText('Add a Result'));

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
