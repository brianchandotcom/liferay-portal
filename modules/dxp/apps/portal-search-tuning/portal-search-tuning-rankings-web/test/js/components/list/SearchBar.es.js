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
import SearchBar from '../../../../src/main/resources/META-INF/resources/js/components/list/SearchBar.es';
import {fireEvent, render} from '@testing-library/react';
import {FETCH_SEARCH_DOCUMENTS_URL, getMockResultsData} from '../../mock-data';
import {resultsDataToMap} from '../../../../src/main/resources/META-INF/resources/js/utils/util.es';

jest.mock('../../../../src/main/resources/META-INF/resources/js/utils/api.es');

const DATA_MAP = resultsDataToMap(
	getMockResultsData(10, 0, '', false).documents
);

const DROPDOWN_TOGGLE_ID = 'dropdown-toggle';

describe('SearchBar', () => {
	it('has an add result button when onAddResultSubmit is defined', () => {
		const {queryByText} = render(
			<SearchBar
				dataMap={DATA_MAP}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onSelectAll={jest.fn()}
				onSelectClear={jest.fn()}
				resultIds={[102, 104, 103]}
				selectedIds={[]}
			/>
		);

		expect(queryByText('Add Result')).not.toBeNull();
	});

	it('does not have an add result button when onAddResultSubmit is not defined', () => {
		const {queryByText} = render(
			<SearchBar
				dataMap={DATA_MAP}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onSelectAll={jest.fn()}
				onSelectClear={jest.fn()}
				resultIds={[102, 104, 103]}
				searchBarTerm={'test'}
				selectedIds={[]}
			/>
		);

		expect(queryByText('Add Result')).toBeNull();
	});

	it('shows what is selected using selectedIds', () => {
		const {queryByText} = render(
			<SearchBar
				dataMap={DATA_MAP}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onSelectAll={jest.fn()}
				onSelectClear={jest.fn()}
				resultIds={[102, 104, 103]}
				selectedIds={[102, 103]}
			/>
		);

		expect(queryByText('2 of 3 Items Selected')).not.toBeNull();
		expect(queryByText('Add Result')).toBeNull();
	});

	it('shows the dropdown when clicked on', () => {
		const {container, getByTestId} = render(
			<SearchBar
				dataMap={DATA_MAP}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onSelectAll={jest.fn()}
				onSelectClear={jest.fn()}
				resultIds={[102, 104, 103]}
				selectedIds={[102, 103]}
			/>
		);

		fireEvent.click(getByTestId(DROPDOWN_TOGGLE_ID));

		expect(container.querySelector('.dropdown-menu')).toHaveClass('show');
	});

	it('shows no items selected with empty selectedIds', () => {
		const {queryByText} = render(
			<SearchBar
				dataMap={DATA_MAP}
				fetchDocumentsSearchUrl={FETCH_SEARCH_DOCUMENTS_URL}
				onAddResultSubmit={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onSelectAll={jest.fn()}
				onSelectClear={jest.fn()}
				resultIds={[102, 104, 103]}
				selectedIds={[]}
			/>
		);

		expect(queryByText('Items Selected')).toBeNull();
		expect(queryByText('Add Result')).not.toBeNull();
	});
});
