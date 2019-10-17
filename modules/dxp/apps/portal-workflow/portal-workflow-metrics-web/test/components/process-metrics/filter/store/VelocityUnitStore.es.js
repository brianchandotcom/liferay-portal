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

import {act, renderHook} from '@testing-library/react-hooks';
import {create} from 'react-test-renderer';
import React, {useContext} from 'react';

import {TimeRangeContext} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';
import {
	daysUnit,
	hoursUnit,
	monthsUnit,
	weeksUnit,
	yearsUnit
} from '../../../../../src/main/resources/META-INF/resources/js/shared/components/filter/util/filterUtil.es';
import {
	useVelocityUnit,
	VelocityUnitProvider,
	VelocityUnitContext
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/VelocityUnitStore.es';

describe('The default Velocity Unit should', () => {
	test('Be "Inst/Hour" when the time range is less than 1 day', async () => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 0, 1, 12),
			new Date(2018, 0, 1, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(hoursUnit.key);

		unmount();
	});

	test('Be "Inst/Day" when the time range diff is less than 30 days', () => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 0, 30),
			new Date(2018, 0, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(daysUnit.key);

		unmount();
	});

	test('Be "Inst/Week" when the time range diff is less than 90 days', () => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 2, 1),
			new Date(2018, 0, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(weeksUnit.key);

		unmount();
	});

	test('Be "Inst/Month" when the time range diff is less than 730 days', () => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 11, 30),
			new Date(2017, 0, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(monthsUnit.key);

		unmount();
	});

	test('Be "Inst/Year" when the time range diff is more than 730 days', () => {
		const wrapper = mockTimeRangeContext(
			new Date(2019, 1, 1),
			new Date(2017, 0, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(yearsUnit.key);

		unmount();
	});
});

describe('When the time range is less than 90 days, the selected Velocity Unit should', () => {
	let wrapper;

	beforeEach(() => {
		wrapper = mockTimeRangeContext(
			new Date(2018, 2, 1),
			new Date(2018, 0, 1)
		);
	});

	afterEach(() => {
		wrapper = null;
	});

	test('Be "Inst/Days" when the initial key is "Days"', async () => {
		const {result, unmount} = renderHook(
			() => useVelocityUnit([daysUnit.key]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(daysUnit.key);

		unmount();
	});

	test('Be "Inst/Days" when the initial key is "Weeks"', async () => {
		const {result, unmount} = renderHook(
			() => useVelocityUnit([weeksUnit.key]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(
			weeksUnit.key
		);

		unmount();
	});

	test('Be "Inst/Days" when the initial key is "Months"', async () => {
		const {result, unmount} = renderHook(
			() => useVelocityUnit([monthsUnit.key]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(
			monthsUnit.key
		);

		unmount();
	});
});

describe('The velocity unit store should', () => {
	let renderer;

	beforeEach(() => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 2, 1),
			new Date(2018, 0, 1)
		);

		renderer = renderHook(
			({velocityUnitKeys}) => useVelocityUnit(velocityUnitKeys),
			{
				initialProps: {
					velocityUnitKeys: [daysUnit.key]
				},
				wrapper
			}
		);
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected velocity unit when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			velocityUnitKeys: [daysUnit.key]
		});

		const selectedVelocityUnit = result.current.getSelectedVelocityUnit();

		expect(selectedVelocityUnit.key).toBe(daysUnit.key);
	});

	test('Update the selected velocity unit when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			velocityUnitKeys: [weeksUnit.key]
		});

		const selectedVelocityUnit = result.current.getSelectedVelocityUnit();

		expect(selectedVelocityUnit.key).toBe(weeksUnit.key);
	});
});

test('The velocity unit provider should be rendered', () => {
	const {root, unmount} = create(
		<TimeRangeContext.Provider
			value={{
				getSelectedTimeRange: () => ({
					dateEnd: new Date(2018, 2, 1),
					dateStart: new Date(2018, 0, 1)
				})
			}}
		>
			<VelocityUnitProvider velocityUnitKeys={[daysUnit.key]}>
				<MockVelocityUnitConsumer />
			</VelocityUnitProvider>
		</TimeRangeContext.Provider>
	);

	let consumer;

	act(() => {
		consumer = root.findByType(MockVelocityUnitConsumer);
	});

	expect(consumer.children[0]).toBe('Days, Weeks, Months');

	unmount();
});

const mockTimeRangeContext = (dateEnd, dateStart) => ({children}) => (
	<TimeRangeContext.Provider
		value={{
			getSelectedTimeRange: () => ({dateEnd, dateStart})
		}}
	>
		{children}
	</TimeRangeContext.Provider>
);

const MockVelocityUnitConsumer = () => {
	const {velocityUnits} = useContext(VelocityUnitContext);

	return velocityUnits.map(velocityUnit => velocityUnit.key).join(', ');
};
