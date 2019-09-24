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

import {cleanup, render, waitForElement} from '@testing-library/react';
import {
	TimeRangeContext,
	TimeRangeProvider,
	useTimeRange
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import React, {useContext} from 'react';
import {MockRouter} from '../../../../mock/MockRouter.es';
import {renderHook} from '@testing-library/react-hooks';
import Request from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';

const items = [
	{
		dateEnd: new Date('2019-01-31'),
		dateStart: new Date('2019-01-24'),
		defaultTimeRange: true,
		id: 1,
		key: '1',
		name: 'Last 7 Days'
	},
	{
		dateEnd: new Date('2019-01-31'),
		dateStart: new Date('2019-01-01'),
		id: 2,
		key: '2',
		name: 'Last Month'
	}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items}})
};

const MockTimeRangeConsumer = () => {
	const {timeRanges} = useContext(TimeRangeContext);

	return timeRanges.map((timeRange, index) => (
		<span data-testId="timeRangeKey" key={index}>
			{timeRange.name}
		</span>
	));
};

const MockAppContext = ({children}) => (
	<MockRouter client={clientMock}>
		<Request>{children}</Request>
	</MockRouter>
);

describe('The custom time range name should', () => {
	test('Be "invalid date" if the dates are null', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const customTimeRange = result.current.timeRanges[0];

		expect(customTimeRange.resultName(customTimeRange)).toBe(
			'Invalid date - Invalid date'
		);

		unmount();
	});

	test('Be the formatted dates if the dates are setted', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const {setTimeRanges, timeRanges} = result.current;

		const customTimeRange = {
			...timeRanges[0],
			dateEnd: new Date('2019-01-15'),
			dateStart: new Date('2019-01-09')
		};
		timeRanges.shift();

		setTimeRanges([customTimeRange, ...timeRanges]);

		expect(customTimeRange.resultName(customTimeRange)).toBe(
			'Jan 9, 2019 - Jan 15, 2019'
		);

		unmount();
	});
});

describe('The selected time range should', () => {
	test('Be empty when there is no initial key', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange).toBeNull();

		unmount();
	});

	test('Be "Custom Range" when the initial key is "custom"', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange(['custom']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.name).toBe('custom-range');

		unmount();
	});

	test('Be "Last Month" when the initial key is "2"', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useTimeRange(['2']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.name).toBe('Last Month');

		unmount();
	});
});

describe('The time range store, when receiving "Last 7 Days" and "Last Month" items, should', () => {
	let renderer;

	beforeEach(() => {
		renderer = renderHook(
			({timeRangeKeys}) => useTimeRange(timeRangeKeys),
			{
				initialProps: {
					timeRangeKeys: ['1']
				},
				wrapper: MockAppContext
			}
		);
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected time range when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			timeRangeKeys: ['1']
		});

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.key).toBe('1');
	});

	test('Update the selected time range when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			timeRangeKeys: ['2']
		});

		const selectedTimeRange = result.current.getSelectedTimeRange();

		expect(selectedTimeRange.key).toBe('2');
	});
});

describe('The time range store, when receiving no items, should', () => {
	test('Have only the "Custom Range" item', async () => {
		clientMock.get.mockResolvedValueOnce({data: {items: []}});

		const {result, waitForNextUpdate, unmount} = renderHook(
			({timeRangeKeys}) => useTimeRange(timeRangeKeys),
			{
				initialProps: {
					timeRangeKeys: ['custom']
				},
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		expect(result.current.timeRanges[0].key).toBe('custom');

		unmount();
	});

	test('Return a fallback object of selected item', () => {
		clientMock.get.mockResolvedValueOnce({data: {}});

		const {result, unmount} = renderHook(
			({timeRangeKeys}) => useTimeRange(timeRangeKeys),
			{
				initialProps: {
					timeRangeKeys: ['1']
				},
				wrapper: MockAppContext
			}
		);

		const selectedTimeRange = result.current.getSelectedTimeRange(['1']);

		expect(selectedTimeRange.key).toBe('1');

		unmount();
	});
});

describe('The time range provider should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockAppContext>
				<TimeRangeProvider timeRangeKeys={[1, 2]}>
					<MockTimeRangeConsumer />
				</TimeRangeProvider>
			</MockAppContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Render "custom-range", "Last 7 Days", and "Last Month" items', async () => {
		const timeRangeKeys = await waitForElement(() =>
			getAllByTestId('timeRangeKey')
		);

		expect(timeRangeKeys[0].innerHTML).toBe('custom-range');
		expect(timeRangeKeys[1].innerHTML).toBe('Last 7 Days');
		expect(timeRangeKeys[2].innerHTML).toBe('Last Month');
	});
});
