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
import PaginationBar from '../../../../src/main/resources/META-INF/resources/js/components/add_result/PaginationBar.es';
import {render, fireEvent} from '@testing-library/react';

const DELTAS = [5, 10, 20, 40, 50];

const PAGINATION_DELTA_ID = 'pagination-delta';
const PAGINATION_ID = 'pagination';

describe('PaginationBar', () => {
	it('has a dropdown for updating delta', () => {
		const {getByTestId} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={jest.fn()}
				onPageChange={jest.fn()}
				page={1}
				selectedDelta={DELTAS[0]}
				totalItems={250}
			/>
		);

		const deltaDropdown = getByTestId(PAGINATION_DELTA_ID);

		const dropdownItems = deltaDropdown.querySelectorAll('.dropdown-item');

		expect(dropdownItems.length).toEqual(5);

		expect(dropdownItems[0]).toHaveTextContent('5');
		expect(dropdownItems[1]).toHaveTextContent('10');
		expect(dropdownItems[2]).toHaveTextContent('20');
		expect(dropdownItems[3]).toHaveTextContent('40');
		expect(dropdownItems[4]).toHaveTextContent('50');
	});

	it('has correct pagination with 100 items and delta 50', () => {
		const {getByTestId, queryByText} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={jest.fn()}
				onPageChange={jest.fn()}
				page={1}
				selectedDelta={DELTAS[4]}
				totalItems={100}
			/>
		);

		expect(queryByText('Showing 1 to 50 of 100 entries')).toBeDefined();

		expect(getByTestId(PAGINATION_ID)).toHaveTextContent('2');
		expect(getByTestId(PAGINATION_ID)).not.toHaveTextContent('3');
	});

	it('has correct pagination with 105 items and delta 5', () => {
		const {getByTestId, queryByText} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={jest.fn()}
				onPageChange={jest.fn()}
				page={1}
				selectedDelta={DELTAS[0]}
				totalItems={105}
			/>
		);

		expect(queryByText('Showing 1 to 50 of 100 entries')).toBeDefined();

		expect(getByTestId(PAGINATION_ID)).toHaveTextContent('21');
		expect(getByTestId(PAGINATION_ID)).not.toHaveTextContent('22');
	});

	it('is on the correct page', () => {
		const {queryByText} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={jest.fn()}
				onPageChange={jest.fn()}
				page={7}
				selectedDelta={DELTAS[0]}
				totalItems={100}
			/>
		);

		expect(queryByText('Showing 31 to 35 of 100 entries.')).not.toBeNull();
	});

	it('shows the pagination dropdown menu when clicked on dropdown delta', () => {
		const {getByTestId, getByText} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={jest.fn()}
				onPageChange={jest.fn()}
				page={7}
				selectedDelta={DELTAS[0]}
				totalItems={100}
			/>
		);

		fireEvent.click(getByText('5 Items'));

		expect(
			getByTestId(PAGINATION_DELTA_ID).querySelector('.dropdown-menu')
		).toHaveClass('show');
	});

	it('calls the onDeltaChange function when selecting delta', () => {
		const onDeltaChange = jest.fn();

		const {getByTestId} = render(
			<PaginationBar
				deltas={DELTAS}
				onDeltaChange={onDeltaChange}
				onPageChange={jest.fn()}
				page={7}
				selectedDelta={DELTAS[0]}
				totalItems={100}
			/>
		);

		const deltaDropdown = getByTestId(PAGINATION_DELTA_ID);

		fireEvent.click(deltaDropdown.querySelector('.dropdown-item'));

		expect(onDeltaChange.mock.calls.length).toBe(1);
	});
});
