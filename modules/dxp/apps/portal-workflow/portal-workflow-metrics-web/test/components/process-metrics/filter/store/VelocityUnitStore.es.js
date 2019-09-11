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
import React, {useContext} from 'react';
import {
	velocityUnitConstants,
	useVelocityUnit,
	VelocityUnitProvider,
	VelocityUnitContext
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/VelocityUnitStore.es';
import {create} from 'react-test-renderer';
import {TimeRangeContext} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/TimeRangeStore.es';

describe('The default Velocity Unit should', () => {
	test('Be "Inst/Hour" when the time range is less than 1 day', async () => {
		const wrapper = mockTimeRangeContext(
			new Date(2018, 0, 1, 12),
			new Date(2018, 0, 1, 1)
		);

		const {result, unmount} = renderHook(() => useVelocityUnit([]), {
			wrapper
		});

		expect(result.current.defaultVelocityUnit.key).toBe(
			velocityUnitConstants.hours
		);

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

		expect(result.current.defaultVelocityUnit.key).toBe(
			velocityUnitConstants.days
		);

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

		expect(result.current.defaultVelocityUnit.key).toBe(
			velocityUnitConstants.weeks
		);

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

		expect(result.current.defaultVelocityUnit.key).toBe(
			velocityUnitConstants.months
		);

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

		expect(result.current.defaultVelocityUnit.key).toBe(
			velocityUnitConstants.years
		);

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
			() => useVelocityUnit([velocityUnitConstants.days]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(
			velocityUnitConstants.days
		);

		unmount();
	});

	test('Be "Inst/Days" when the initial key is "Weeks"', async () => {
		const {result, unmount} = renderHook(
			() => useVelocityUnit([velocityUnitConstants.weeks]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(
			velocityUnitConstants.weeks
		);

		unmount();
	});

	test('Be "Inst/Days" when the initial key is "Months"', async () => {
		const {result, unmount} = renderHook(
			() => useVelocityUnit([velocityUnitConstants.months]),
			{wrapper}
		);

		expect(result.current.getSelectedVelocityUnit().key).toBe(
			velocityUnitConstants.months
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
					velocityUnitKeys: [velocityUnitConstants.days]
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
			velocityUnitKeys: [velocityUnitConstants.days]
		});

		const selectedVelocityUnit = result.current.getSelectedVelocityUnit();

		expect(selectedVelocityUnit.key).toBe(velocityUnitConstants.days);
	});

	test('Update the selected velocity unit when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			velocityUnitKeys: [velocityUnitConstants.weeks]
		});

		const selectedVelocityUnit = result.current.getSelectedVelocityUnit();

		expect(selectedVelocityUnit.key).toBe(velocityUnitConstants.weeks);
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
			<VelocityUnitProvider
				velocityUnitKeys={[velocityUnitConstants.days]}
			>
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
