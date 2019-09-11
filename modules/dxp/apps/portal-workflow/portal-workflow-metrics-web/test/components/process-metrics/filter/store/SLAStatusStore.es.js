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

import React, {useContext} from 'react';
import {
	slaStatusConstants,
	useSLAStatus,
	SLAStatusProvider,
	SLAStatusContext
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/SLAStatusStore.es';
import {act, create} from 'react-test-renderer';
import {renderHook} from '@testing-library/react-hooks';

describe('The selected SLA statuses should', () => {
	test('Be empty when there is no initial key', () => {
		const {result, unmount} = renderHook(() => useSLAStatus([]));

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses.length).toBe(0);

		unmount();
	});

	test('Be "On Time" when the initial key is "OnTime"', () => {
		const {result, unmount} = renderHook(() =>
			useSLAStatus([slaStatusConstants.onTime])
		);

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.onTime);

		unmount();
	});

	test('Be "Overdue" when the initial key is "Overdue"', () => {
		const {result, unmount} = renderHook(() =>
			useSLAStatus([slaStatusConstants.overdue])
		);

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.overdue);

		unmount();
	});

	test('Be "Untracked" when the initial key is "Untracked"', () => {
		const {result, unmount} = renderHook(() =>
			useSLAStatus([slaStatusConstants.untracked])
		);

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.untracked);

		unmount();
	});

	test('Be "On Time" and "Overdue" when the initial keys are "OnTime" and "Overdue"', () => {
		const {result, unmount} = renderHook(() =>
			useSLAStatus([
				slaStatusConstants.onTime,
				slaStatusConstants.overdue
			])
		);

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.onTime);
		expect(selectedSLAStatuses[1].key).toBe(slaStatusConstants.overdue);

		unmount();
	});
});

describe('The SLA status store should', () => {
	let renderer;

	beforeEach(() => {
		renderer = renderHook(
			({slaStatusKeys}) => useSLAStatus(slaStatusKeys),
			{
				initialProps: {
					slaStatusKeys: [slaStatusConstants.onTime]
				}
			}
		);
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected SLA statuses when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			slaStatusKeys: [slaStatusConstants.onTime]
		});

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.onTime);
	});

	test('Update the selected SLA statuses when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			slaStatusKeys: [slaStatusConstants.overdue]
		});

		const selectedSLAStatuses = result.current.getSelectedSLAStatuses();

		expect(selectedSLAStatuses[0].key).toBe(slaStatusConstants.overdue);
	});
});

test('The SLA status provider should be rendered', () => {
	const {root, unmount} = create(
		<SLAStatusProvider slaStatusKeys={[slaStatusConstants.onTime]}>
			<MockSLAStatusConsumer />
		</SLAStatusProvider>
	);

	let consumer;

	act(() => {
		consumer = root.findByType(MockSLAStatusConsumer);
	});

	expect(consumer.children[0]).toBe('OnTime, Overdue, Untracked');

	unmount();
});

const MockSLAStatusConsumer = () => {
	const {slaStatuses} = useContext(SLAStatusContext);

	return slaStatuses.map(slaStatus => slaStatus.key).join(', ');
};
