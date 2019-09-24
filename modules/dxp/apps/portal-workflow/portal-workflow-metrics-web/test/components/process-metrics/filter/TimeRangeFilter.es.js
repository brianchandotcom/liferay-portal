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
import {TimeRangeContext} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import {TimeRangeFilter} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/TimeRangeFilter.es';
import {MockRouter} from '../../../mock/MockRouter.es';
import React from 'react';

const defaultTimeRange = {
	active: true,
	dateEnd: new Date('2019-01-31'),
	dateStart: new Date('2019-01-24'),
	id: 1,
	key: '1',
	name: 'Last 7 Days'
};

const timeRanges = [
	{
		dateEnd: new Date('2019-01-15'),
		dateStart: new Date('2019-01-01'),
		id: 0,
		key: 'custom',
		name: 'Custom Range'
	},
	defaultTimeRange,
	{
		dateEnd: new Date('2019-01-31'),
		dateStart: new Date('2019-01-01'),
		id: 2,
		key: '2',
		name: 'Last Month'
	}
];

const getContextMock = () => ({
	defaultTimeRange,
	getSelectedTimeRange: jest.fn(),
	setShowCustomForm: jest.fn(),
	showCustomForm: jest.fn(),
	timeRanges
});

describe('The time range filter should', () => {
	let contextMock;
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		contextMock = getContextMock();

		const renderResult = render(
			<MockRouter>
				<TimeRangeContext.Provider value={contextMock}>
					<TimeRangeFilter />
				</TimeRangeContext.Provider>
			</MockRouter>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Be rendered with "Custom Range", "Last 7 Days", and "Last Month" items', () => {
		const filterItemNames = getAllByTestId('filterItemName');

		expect(filterItemNames[0].innerHTML).toBe('Custom Range');
		expect(filterItemNames[1].innerHTML).toBe('Last 7 Days');
		expect(filterItemNames[2].innerHTML).toBe('Last Month');
	});

	test('Display the custom time range dialog when "Custom Range" item is clicked', () => {
		const filterItemInputs = getAllByTestId('filterItemInput');

		const customRangeInput = filterItemInputs[0];

		fireEvent.click(customRangeInput);

		expect(contextMock.setShowCustomForm).toHaveBeenCalledWith(true);
	});

	test('Not display the custom time range dialog when "Last 7 Days" is clicked', () => {
		const filterItemInputs = getAllByTestId('filterItemInput');

		const last7DaysInput = filterItemInputs[1];

		fireEvent.click(last7DaysInput);

		expect(contextMock.setShowCustomForm).toHaveBeenCalledWith(false);
	});
});

describe('With showFilterName as false, the time range filter name should', () => {
	const contextMock = getContextMock();

	afterEach(cleanup);

	test('Be rendered with "Custom" value', () => {
		contextMock.getSelectedTimeRange.mockReturnValue(timeRanges[0]);

		const {getByTestId} = render(
			<MockRouter>
				<TimeRangeContext.Provider value={contextMock}>
					<TimeRangeFilter showFilterName={false} />
				</TimeRangeContext.Provider>
			</MockRouter>
		);

		const filterName = getByTestId('filterName');

		expect(filterName.innerHTML).toBe('Jan 1, 2019 - Jan 15, 2019');
	});

	test('Be rendered with "Last 7 Days" value', () => {
		contextMock.getSelectedTimeRange.mockReturnValue(timeRanges[1]);

		const {getByTestId} = render(
			<MockRouter>
				<TimeRangeContext.Provider value={contextMock}>
					<TimeRangeFilter showFilterName={false} />
				</TimeRangeContext.Provider>
			</MockRouter>
		);

		const filterName = getByTestId('filterName');

		expect(filterName.innerHTML).toBe('Last 7 Days');
	});

	test('Be rendered with empty value', () => {
		contextMock.getSelectedTimeRange.mockReturnValue(null);

		const {getByTestId} = render(
			<MockRouter>
				<TimeRangeContext.Provider value={contextMock}>
					<TimeRangeFilter showFilterName={false} />
				</TimeRangeContext.Provider>
			</MockRouter>
		);

		const filterName = getByTestId('filterName');

		expect(filterName.innerHTML).toBe('');
	});
});
