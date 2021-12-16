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

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import PreviewSidebar from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/edit_sxp_blueprint/PreviewSidebar';
import {mockSearchResults} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

jest.mock(
	'../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/shared/CodeMirrorEditor',
	() => ({onChange, value}) => (
		<textarea aria-label="text-area" onChange={onChange} value={value} />
	)
);

Liferay.ThemeDisplay.getDefaultLanguageId = () => 'en_US';

const SEARCH_RESULTS = mockSearchResults();

const SEARCH_RESULTS_HITS = JSON.parse(SEARCH_RESULTS.responseString).hits.hits;

function renderPreviewSidebar(props) {
	return render(
		<PreviewSidebar
			loading={false}
			onFetchResults={jest.fn()}
			visible={true}
			{...props}
		/>
	);
}

describe('PreviewSidebar', () => {
	global.URL.createObjectURL = jest.fn();

	it('renders the preview', () => {
		const {container} = renderPreviewSidebar();

		expect(container).not.toBeNull();
	});

	it('renders the introduction', () => {
		const {getByText} = renderPreviewSidebar();

		getByText('perform-a-search-to-preview-your-blueprints-search-results');
	});

	it('renders the loading icon', () => {
		const {container} = renderPreviewSidebar({
			loading: true,
			responseString: SEARCH_RESULTS.responseString,
			totalHits: SEARCH_RESULTS.totalHits,
		});

		container.querySelector('.loading-animation');
	});

	it('renders the titles for the search results', () => {
		const {getByText} = renderPreviewSidebar({
			responseString: SEARCH_RESULTS.responseString,
			totalHits: SEARCH_RESULTS.totalHits,
		});

		SEARCH_RESULTS_HITS.map((result) => {
			getByText(result.fields.title_en_US[0]);
		});
	});

	it('expands the result when clicked on', () => {
		const {getAllByLabelText, queryAllByText} = renderPreviewSidebar({
			responseString: SEARCH_RESULTS.responseString,
			totalHits: SEARCH_RESULTS.totalHits,
		});

		fireEvent.click(getAllByLabelText('expand')[0]);

		Object.keys(SEARCH_RESULTS_HITS[0].fields).map((key) => {
			queryAllByText(`${SEARCH_RESULTS_HITS[0].fields[key][0]}`);
		});
	});

	it('renders warning messages', () => {
		const errors = [
			{
				msg: 'invalid',
				severity: 'error',
			},
			{
				msg: 'missing text',
				severity: 'error',
			},
		];

		const {getByText} = renderPreviewSidebar({
			errors,
			loading: false,
		});

		errors.map((error) => getByText(error.msg));
	});
});
