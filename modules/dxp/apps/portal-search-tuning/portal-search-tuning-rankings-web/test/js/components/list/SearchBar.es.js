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

import {render} from '@testing-library/react';
import React from 'react';

import SearchBar from '../../../../src/main/resources/META-INF/resources/js/components/list/SearchBar.es';
import {resultsDataToMap} from '../../../../src/main/resources/META-INF/resources/js/utils/util.es';
import {
	FETCH_SEARCH_DOCUMENTS_URL,
	getMockResultsData,
} from '../../mocks/data.es';

import '@testing-library/jest-dom/extend-expect';

const DATA_MAP = resultsDataToMap(
	getMockResultsData(10, 0, '', false).documents
);

function renderTestSearchBar(props) {
	return render(
		<SearchBar
			dataMap={DATA_MAP}
			fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
			onClickHide={jest.fn()}
			onClickPin={jest.fn()}
			onSelectAll={jest.fn()}
			onSelectClear={jest.fn()}
			resultIds={[102, 104, 103]}
			selectedIds={[]}
			{...props}
		/>
	);
}

describe('SearchBar', () => {
	it('does has an add result button when onAddResultSubmit is defined', () => {
		const {getByText} = renderTestSearchBar({onAddResultSubmit: jest.fn()});

		expect(getByText('add-result')).toBeInTheDocument();
	});

	it('does not have an add result button when onAddResultSubmit is not defined', () => {
		const {queryByText} = renderTestSearchBar();

		expect(queryByText('add-result')).toBeNull();
	});

	it('hides the add result button with selectedIds', () => {
		const {queryByText} = renderTestSearchBar({
			onAddResultSubmit: jest.fn(),
			selectedIds: [102],
		});

		expect(queryByText('add-result')).toBeNull();
	});

	it('shows what is selected with selectedIds', () => {
		const {getByText} = renderTestSearchBar({
			selectedIds: [102, 103],
		});

		expect(getByText('x-items-selected')).toBeInTheDocument();
	});

	it('shows only one selected with selectedIds', () => {
		const {getByText} = renderTestSearchBar({
			selectedIds: [102],
		});

		expect(getByText('x-item-selected')).toBeInTheDocument();
	});

	it('shows no items selected with empty selectedIds', () => {
		const {getByText, queryByText} = renderTestSearchBar();

		expect(getByText('select-items')).toBeInTheDocument();
		expect(queryByText('x-items-selected')).toBeNull();
		expect(queryByText('x-item-selected')).toBeNull();
	});
});
