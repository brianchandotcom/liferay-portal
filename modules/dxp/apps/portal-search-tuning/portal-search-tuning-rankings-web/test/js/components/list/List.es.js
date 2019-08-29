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
import List from '../../../../src/main/resources/META-INF/resources/js/components/list/List.es';
import {
	fireEvent,
	getByLabelText,
	getByText,
	render
} from '@testing-library/react';
import {getMockResultsData} from '../../mock-data';
import {resultsDataToMap} from '../../../../src/main/resources/META-INF/resources/js/utils/util.es';
import '@testing-library/jest-dom/extend-expect';

jest.mock('../../../../src/main/resources/META-INF/resources/js/utils/api.es');

const DATA_MAP = resultsDataToMap(
	getMockResultsData(10, 0, '', false).documents
);

describe('List', () => {
	it('lists out results in order with expected titles', () => {
		const {container} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={jest.fn()}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		const listItems = container.querySelectorAll('.text-truncate-inline');

		expect(listItems[0]).toHaveTextContent(
			'102 This is a Document Example'
		);
		expect(listItems[1]).toHaveTextContent(
			'104 This is a Document Example'
		);
		expect(listItems[2]).toHaveTextContent(
			'103 This is a Web Content Example'
		);
	});

	it('has no loading icon when dataLoading is false', () => {
		const {container} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				resultIds={[]}
				showLoadMore={true}
			/>
		);

		expect(container.querySelector('.loading-animation')).toBeNull();
		expect(container.querySelector('.load-more-button')).not.toBeNull();
	});

	it('has a loading icon when dataLoading is true', () => {
		const {container} = render(
			<List
				dataLoading={true}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				resultIds={[]}
				showLoadMore={true}
			/>
		);

		expect(container.querySelector('.loading-animation')).not.toBeNull();
		expect(container.querySelector('.load-more-button')).toBeNull();
	});

	it('calls the onLoadResults function when the loading button is clicked', () => {
		const mockLoad = jest.fn();

		const {getByText} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={mockLoad}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		const loadButton = getByText('load-more-results');

		fireEvent.click(loadButton);

		expect(mockLoad).toHaveBeenCalledTimes(1);
	});

	it('updates the selected ids', () => {
		const mockLoad = jest.fn();

		const {container, getByTestId} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={mockLoad}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		fireEvent.click(getByLabelText(getByTestId('102'), 'select'));
		fireEvent.click(getByLabelText(getByTestId('104'), 'select'));

		const navbar = getByText(container, 'x-of-x-items-selected', {
			exact: false
		});

		expect(getByText(navbar, '2', {exact: false})).toBeInTheDocument();
		expect(getByText(navbar, '3', {exact: false})).toBeInTheDocument();
	});

	it('updates the selected ids back', () => {
		const mockLoad = jest.fn();

		const {getByTestId, queryByText} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={mockLoad}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		fireEvent.click(
			getByTestId('102').querySelector('.custom-control-input')
		);
		fireEvent.click(
			getByTestId('102').querySelector('.custom-control-input')
		);

		expect(queryByText('items-selected')).not.toBeInTheDocument();
	});

	it('focuses on the id', () => {
		const mockLoad = jest.fn();

		const {getByTestId} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={mockLoad}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		fireEvent.focus(getByTestId('102'));

		const focusedElement = document.activeElement;

		expect(focusedElement).toBe(getByTestId('102'));
	});

	it('adds classes of focus and reorder on the id', () => {
		const mockLoad = jest.fn();

		const {getByTestId} = render(
			<List
				dataLoading={false}
				dataMap={DATA_MAP}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onLoadResults={mockLoad}
				resultIds={[102, 104, 103]}
				showLoadMore={true}
			/>
		);

		fireEvent.focus(getByTestId('102'));

		fireEvent.keyDown(getByTestId('102'), {code: 32, key: ' '});

		expect(getByTestId('102')).toHaveClass('results-ranking-item-focus');
		expect(getByTestId('102')).not.toHaveClass(
			'results-ranking-item-reorder'
		);
	});
});
