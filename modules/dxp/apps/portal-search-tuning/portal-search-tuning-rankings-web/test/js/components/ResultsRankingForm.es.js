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

import React from 'react';
import ResultsRankingForm from '../../../src/main/resources/META-INF/resources/js/components/ResultsRankingForm.es';
import {
	fireEvent,
	render,
	waitForElement,
	within
} from '@testing-library/react';
import {
	FETCH_HIDDEN_DOCUMENTS_URL,
	FETCH_VISIBLE_DOCUMENTS_URL
} from '../mock-data';

jest.mock('../../../src/main/resources/META-INF/resources/js/utils/api.es');

const FORM_NAME = 'testForm';

const RESULTS_LIST_ID = 'results-list-group';

const HIDDEN_IDS_ADDED_INPUT_SELECTOR = '#hiddenIdsAdded';

const HIDDEN_IDS_REMOVED_INPUT_SELECTOR = '#hiddenIdsRemoved';

const HIDE_BUTTON_LABEL = 'Hide Result';

const SHOW_BUTTON_LABEL = 'Show Result';

describe('ResultsRankingForm', () => {
	it('renders the results ranking form', () => {
		const {container} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery="example"
			/>
		);

		expect(
			container.querySelector('.results-ranking-form-root')
		).toBeInTheDocument();
	});

	it('renders the results ranking form after loading', async () => {
		const {getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(getByTestId(RESULTS_LIST_ID)).toBeInTheDocument();
	});

	it('renders the results ranking form after loading hidden tab', async () => {
		const {getByTestId, getByText} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('Hidden'));

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		expect(getByTestId(RESULTS_LIST_ID)).toBeInTheDocument();
	});

	it('includes the initial aliases', async () => {
		const {container} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
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
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
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
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
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
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				initialAliases={['one', 'two', 'three']}
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

	xit('updates the pinnedAdded', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(getByTestId('109').querySelector('.result-pin button'));

		expect(container.querySelector('#pinnedAdded').value).toEqual('109');
	});

	xit('updates the pinnedAdded back', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(getByTestId('109').querySelector('.result-pin button'));

		fireEvent.click(getByTestId('109').querySelector('.result-pin button'));

		expect(container.querySelector('#pinnedAdded').value).toEqual('');
	});

	xit('updates the pinnedRemoved', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(getByTestId('100').querySelector('.result-pin button'));

		expect(container.querySelector('#pinnedRemoved').value).toEqual('100');
	});

	xit('updates the pinnedRemoved back', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(getByTestId('100').querySelector('.result-pin button'));

		fireEvent.click(getByTestId('100').querySelector('.result-pin button'));

		expect(container.querySelector('#pinnedRemoved').value).toEqual('');
	});

	it('updates the hiddenAdded', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			within(getByTestId('100')).getByTitle(HIDE_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('100');
	});

	it('updates the hiddenAdded back', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			within(getByTestId('105')).getByTitle(HIDE_BUTTON_LABEL)
		);

		fireEvent.click(getByText('Hidden'));

		fireEvent.click(
			within(getByTestId('105')).getByTitle(SHOW_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('');
	});

	it('updates the hiddenRemoved', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('Hidden'));

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(SHOW_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_REMOVED_INPUT_SELECTOR).value
		).toEqual('200');
	});

	it('updates the hiddenRemoved back', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		fireEvent.click(getByText('Hidden'));

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(SHOW_BUTTON_LABEL)
		);

		fireEvent.click(getByText('Visible'));

		fireEvent.click(
			within(getByTestId('200')).getByTitle(HIDE_BUTTON_LABEL)
		);

		expect(
			container.querySelector(HIDDEN_IDS_REMOVED_INPUT_SELECTOR).value
		).toEqual('');
	});

	xit('updates the pinnedRemoved from hiding a result', async () => {
		const {container, getByTestId, getByText} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(
			getByTestId('100').querySelector('.result-hide button')
		);

		expect(container.querySelector('#pinnedRemoved').value).toEqual('100');

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('100');

		fireEvent.click(getByText('Hidden'));

		fireEvent.click(
			getByTestId('100').querySelector('.result-hide button')
		);

		expect(container.querySelector('#pinnedRemoved').value).toEqual('100');

		expect(
			container.querySelector(HIDDEN_IDS_ADDED_INPUT_SELECTOR).value
		).toEqual('');

		expect(getByText('Publish')).not.toHaveAttribute('disabled');
	});

	it('fetches more results after clicking on load more button', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl="cancel"
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
				formName={FORM_NAME}
				searchQuery=""
			/>
		);

		await waitForElement(() => getByTestId(RESULTS_LIST_ID));

		fireEvent.click(container.querySelector('.load-more-button'));

		await waitForElement(() => getByTestId('110'));

		expect(getByTestId(RESULTS_LIST_ID)).toHaveTextContent(
			'110 This is a Document Example'
		);
		expect(getByTestId(RESULTS_LIST_ID)).toHaveTextContent(
			'119 This is a Web Content Example'
		);
	});

	it('has the same pinned end index if there are no additional pinned items loaded', async () => {
		const {container, getByTestId} = render(
			<ResultsRankingForm
				cancelUrl=""
				fetchDocumentsHiddenUrl={FETCH_HIDDEN_DOCUMENTS_URL}
				fetchDocumentsUrl={FETCH_VISIBLE_DOCUMENTS_URL}
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
});
