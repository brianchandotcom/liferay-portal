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
	fireEvent,
	render,
	waitForElement,
	within
} from '@testing-library/react';
import React from 'react';

import ResultRankingsForm from '../../../src/main/resources/META-INF/resources/js/components/ResultRankingsForm.es';
import {
	FETCH_HIDDEN_DOCUMENTS_URL,
	FETCH_SEARCH_DOCUMENTS_URL,
	FETCH_VISIBLE_DOCUMENTS_URL
} from '../mocks/data.es';

import '@testing-library/jest-dom/extend-expect';

const FORM_NAME = 'testForm';

const HIDDEN_IDS_ADDED_INPUT_SELECTOR = '#hiddenIdsAdded';

const HIDDEN_IDS_REMOVED_INPUT_SELECTOR = '#hiddenIdsRemoved';

const HIDE_BUTTON_LABEL = 'hide-result';

const INACTIVE_INPUT_SELECTOR = '#inactive';

const SHOW_BUTTON_LABEL = 'show-result';

describe('ResultRankingsForm', () => {
	it('renders the results ranking form', () => {
		const {container} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery="example"
			/>
		);

		expect(
			container.querySelector('.results-ranking-form-root')
		).toBeInTheDocument();
	});

	it('renders the results ranking items after loading', async () => {
		const {getByTestId} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId('100'));

		expect(getByTestId('100')).toBeInTheDocument();
		expect(getByTestId('109')).toBeInTheDocument();
	});

	it('renders the results ranking items after loading hidden tab', async () => {
		const {getByTestId, getByText} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('hidden'));

		await waitForElement(() => getByTestId('200'));

		expect(getByTestId('200')).toBeInTheDocument();
		expect(getByTestId('209')).toBeInTheDocument();
	});

	it('includes the initial aliases', async () => {
		const {container} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
				initialInactive={false}
				searchQuery=""
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement[0]).toHaveTextContent('one');
		expect(tagsElement[1]).toHaveTextContent('two');
		expect(tagsElement[2]).toHaveTextContent('three');
	});

	it('removes an initial alias after clicking delete', async () => {
		const {container} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
				initialInactive={false}
				searchQuery=""
			/>
		);

		const tagsElementClose = container.querySelectorAll(
			'.label-item-after button'
		);

		fireEvent.click(tagsElementClose[0]);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement[0]).not.toHaveTextContent('one');
	});

	it('renders blank aliases', () => {
		const {container} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		const input = container.querySelector('.form-control-inset');

		fireEvent.change(input, {target: {value: ' '}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement.length).toBe(0);

		expect(input.getAttribute('value')).not.toEqual(' ');
	});

	it('does not allow duplicate aliases', () => {
		const {container} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
				initialInactive={false}
				searchQuery=""
			/>
		);

		const input = container.querySelector('.form-control-inset');

		fireEvent.change(input, {target: {value: 'one'}});

		fireEvent.keyDown(input, {key: 'Enter', keyCode: 13, which: 13});

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement.length).toBe(3);

		expect(input.getAttribute('value')).not.toEqual('one');
	});

	it('updates the hiddenAdded', async () => {
		const {container, getByTestId} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId('100'));

		fireEvent.click(
			within(getByTestId('100')).getByTitle(HIDE_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('100');
	});

	it('updates the hiddenAdded back', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId('105'));

		fireEvent.click(
			within(getByTestId('105')).getByTitle(HIDE_BUTTON_LABEL)
		);

		fireEvent.click(getByText('hidden'));

		fireEvent.click(
			within(getByTestId('105')).getByTitle(SHOW_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('');
	});

	it('updates the hiddenRemoved', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('hidden'));

		await waitForElement(() => getByTestId('200'));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(SHOW_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_REMOVED_INPUT_SELECTOR).value
		).toEqual('200');
	});

	it('updates the hiddenRemoved back', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('hidden'));

		await waitForElement(() => getByTestId('200'));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(SHOW_BUTTON_LABEL)
		);

		fireEvent.click(getByText('visible'));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(HIDE_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_REMOVED_INPUT_SELECTOR).value
		).toEqual('');
	});

	it('fetches more results after clicking on load more button', async () => {
		const {container, getByTestId} = render(
			<ResultRankingsForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId('100'));

		fireEvent.click(container.querySelector('.load-more-button'));

		await waitForElement(() => getByTestId('110'));

		expect(getByTestId('110')).toHaveTextContent(
			'This is a Document Example'
		);
		expect(getByTestId('119')).toHaveTextContent(
			'This is a Web Content Example'
		);
	});

	it('has the same pinned end index if there are no additional pinned items loaded', async () => {
		const {container, getByTestId} = render(
			<ResultRankingsForm
				cancelUrl=""
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		const pinnedIdsEndIndexInput = container.querySelector(
			'#pinnedIdsEndIndex'
		);

		await waitForElement(() => getByTestId('100'));

		expect(pinnedIdsEndIndexInput.value).toBe('4');

		fireEvent.click(container.querySelector('.load-more-button'));

		await waitForElement(() => getByTestId('110'));

		expect(pinnedIdsEndIndexInput.value).toBe('4');
	});

	it('updates the state to inactive', async () => {
		const {container, getByLabelText} = render(
			<ResultRankingsForm
				cancelUrl=""
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={false}
				searchQuery=""
			/>
		);

		fireEvent.click(getByLabelText('active'));

		expect(getByLabelText('inactive')).toBeInTheDocument();

		expect(container.querySelector(INACTIVE_INPUT_SELECTOR).value).toEqual(
			'true'
		);
	});

	it('updates the state to active', async () => {
		const {container, getByLabelText} = render(
			<ResultRankingsForm
				cancelUrl=""
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				fetchDocumentsVisibleUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialInactive={true}
				searchQuery=""
			/>
		);

		fireEvent.click(getByLabelText('inactive'));

		expect(getByLabelText('active')).toBeInTheDocument();

		expect(container.querySelector(INACTIVE_INPUT_SELECTOR).value).toEqual(
			'false'
		);
	});
});
