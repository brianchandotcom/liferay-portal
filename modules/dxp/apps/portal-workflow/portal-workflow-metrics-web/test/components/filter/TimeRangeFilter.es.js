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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import TimeRangeFilter from '../../../src/main/resources/META-INF/resources/js/components/filter/TimeRangeFilter.es';
import {stringify} from '../../../src/main/resources/META-INF/resources/js/shared/components/router/queryString.es';
import {jsonSessionStorage} from '../../../src/main/resources/META-INF/resources/js/shared/util/storage.es';
import {MockRouter} from '../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

const filters = {
	testDateEnd: '2019-12-09T00:00:00Z',
	testDateStart: '2019-12-03T00:00:00Z',
	testTimeRange: ['7'],
};

const query = stringify({filters});

const data = {
	items: [
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-12-03T00:00:00Z',
			defaultTimeRange: false,
			id: 7,
			name: 'Last 7 Days',
		},
		{
			dateEnd: '2019-12-09T00:00:00Z',
			dateStart: '2019-11-10T00:00:00Z',
			defaultTimeRange: true,
			id: 30,
			name: 'Last 30 Days',
		},
	],
};

const wrapper = ({children}) => (
	<MockRouter query={query}>{children}</MockRouter>
);

describe('The time range filter component should', () => {
	describe('Render without custom range date option selected', () => {
		afterEach(cleanup);

		beforeEach(() => {
			jsonSessionStorage.set('timeRanges', data);

			render(<TimeRangeFilter prefixKey="test" processId={12345} />, {
				wrapper,
			});
		});

		test('Be rendered with filter item names', () => {
			const filterItems = document.querySelectorAll('.dropdown-item');

			expect(filterItems[0].innerHTML).toContain('custom-range');
			expect(filterItems[1].innerHTML).toContain('Last 7 Days');
			expect(filterItems[2].innerHTML).toContain('Last 30 Days');
		});

		test('Be rendered with active option "Last 7 Days"', async () => {
			const activeItem = document.querySelector('.active');

			expect(activeItem).toHaveTextContent('Last 7 Days');
		});
	});

	describe('Render with custom date range option selected and', () => {
		let container, dateEndInput, dateStartInput, getAllByText, getByText;

		beforeAll(() => {
			jsonSessionStorage.set('timeRanges', data);

			const renderResult = render(
				<TimeRangeFilter prefixKey="test" processId={12345} />,
				{wrapper}
			);

			container = renderResult.container;
			getAllByText = renderResult.getAllByText;
			getByText = renderResult.getByText;
		});

		test('Show the date the href has as a suggestion', () => {
			const filterItems = document.querySelectorAll('.dropdown-item div');

			fireEvent.click(filterItems[0]);

			dateStartInput = document.querySelectorAll('.form-control')[0];
			dateEndInput = document.querySelectorAll('.form-control')[1];

			expect(dateStartInput.value).toEqual('12/03/2019');
			expect(dateEndInput.value).toEqual('12/09/2019');
		});

		test('Sho error span with invalid date input', () => {
			fireEvent.change(dateStartInput, {target: {value: '13/09/2020'}});
			fireEvent.blur(dateStartInput);

			fireEvent.change(dateEndInput, {target: {value: '13/09/2020'}});
			fireEvent.blur(dateEndInput);

			const errorSpan = document.querySelectorAll('.form-feedback-item');

			expect(errorSpan[0]).toHaveTextContent('please-enter-a-valid-date');
			expect(errorSpan[1]).toHaveTextContent('please-enter-a-valid-date');
		});

		test('Show error span with date early to 1970 input', () => {
			fireEvent.change(dateStartInput, {target: {value: '12/09/1960'}});
			fireEvent.blur(dateStartInput);

			fireEvent.change(dateEndInput, {target: {value: '12/09/1960'}});
			fireEvent.blur(dateEndInput);

			const errorSpan = document.querySelectorAll('.form-feedback-item');

			expect(errorSpan[0]).toHaveTextContent(
				'the-date-cannot-be-earlier-than-1970'
			);
			expect(errorSpan[1]).toHaveTextContent(
				'the-date-cannot-be-earlier-than-1970'
			);
		});

		test('Show error span to inconsistent date input', () => {
			fireEvent.change(dateStartInput, {target: {value: '12/09/2020'}});
			fireEvent.blur(dateStartInput);

			fireEvent.change(dateEndInput, {target: {value: '12/09/2019'}});
			fireEvent.blur(dateEndInput);

			const errorSpan = document.querySelectorAll('.form-feedback-item');

			expect(errorSpan[0]).toHaveTextContent(
				'the-start-date-cannot-be-later-than-the-end-date'
			);
			expect(errorSpan[1]).toHaveTextContent(
				'the-end-date-cannot-be-earlier-than-the-start-date'
			);
		});

		test('Change the filter value applying a custom time range', () => {
			const filterName = getAllByText('Last 7 Days')[0];

			expect(filterName).toBeTruthy();

			fireEvent.change(dateStartInput, {target: {value: '12/03/2019'}});
			fireEvent.blur(dateStartInput);

			fireEvent.change(dateEndInput, {target: {value: '12/09/2019'}});
			fireEvent.blur(dateEndInput);

			const applyButton = getByText('apply');

			const customTimeRangeForm = container.querySelector(
				'form.custom-range-form'
			);
			expect(customTimeRangeForm).not.toBeUndefined();

			fireEvent.click(applyButton);

			expect(filterName).toHaveTextContent('Dec 3, 2019 - Dec 9, 2019');
		});
	});
});
