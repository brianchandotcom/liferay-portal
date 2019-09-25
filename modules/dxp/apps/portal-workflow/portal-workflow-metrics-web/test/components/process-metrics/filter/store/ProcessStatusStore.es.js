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

import {
	processStatusConstants,
	useProcessStatus,
	ProcessStatusProvider,
	ProcessStatusContext
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStatusStore.es';
import {act, create} from 'react-test-renderer';
import React, {useContext} from 'react';
import {renderHook} from '@testing-library/react-hooks';

describe('The selected process statuses should', () => {
	test('Be empty when there is no initial key', () => {
		const {result, unmount} = renderHook(() => useProcessStatus([]));

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses.length).toBe(0);

		unmount();
	});

	test('Be "Completed" when the initial key is "Completed"', () => {
		const {result, unmount} = renderHook(() =>
			useProcessStatus([processStatusConstants.completed])
		);

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses[0].key).toBe(
			processStatusConstants.completed
		);

		unmount();
	});

	test('Be "Completed" when the initial key is "Pending"', () => {
		const {result, unmount} = renderHook(() =>
			useProcessStatus([processStatusConstants.pending])
		);

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses[0].key).toBe(
			processStatusConstants.pending
		);

		unmount();
	});

	test('Be "Completed" and "Pending" when the initial keys are "Completed" and "Pending"', () => {
		const {result, unmount} = renderHook(() =>
			useProcessStatus([
				processStatusConstants.completed,
				processStatusConstants.pending
			])
		);

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses[0].key).toBe(
			processStatusConstants.completed
		);
		expect(selectedProcessStatuses[1].key).toBe(
			processStatusConstants.pending
		);

		unmount();
	});
});

describe('The process status store should', () => {
	let renderer;

	beforeEach(() => {
		renderer = renderHook(
			({processStatusKeys}) => useProcessStatus(processStatusKeys),
			{
				initialProps: {
					processStatusKeys: [processStatusConstants.completed]
				}
			}
		);
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected process statuses when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			processStatusKeys: [processStatusConstants.completed]
		});

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses[0].key).toBe(
			processStatusConstants.completed
		);
	});

	test('Update the selected process statuses when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			processStatusKeys: [processStatusConstants.pending]
		});

		const selectedProcessStatuses = result.current.getSelectedProcessStatuses();

		expect(selectedProcessStatuses[0].key).toBe(
			processStatusConstants.pending
		);
	});

	test('Return "false" when the pending status is selected', () => {
		const {rerender, result} = renderer;

		rerender({
			processStatusKeys: [processStatusConstants.pending]
		});

		const isCompletedStatusSelected = result.current.isCompletedStatusSelected();

		expect(isCompletedStatusSelected).toBe(false);
	});

	test('Return "true" when the completed status is selected', () => {
		const {rerender, result} = renderer;

		rerender({
			processStatusKeys: [processStatusConstants.completed]
		});

		const isCompletedStatusSelected = result.current.isCompletedStatusSelected();

		expect(isCompletedStatusSelected).toBe(true);
	});
});

test('The process status provider should be rendered', () => {
	const {root, unmount} = create(
		<ProcessStatusProvider
			processStatusKeys={[processStatusConstants.completed]}
		>
			<MockProcessStatusConsumer />
		</ProcessStatusProvider>
	);

	let consumer;

	act(() => {
		consumer = root.findByType(MockProcessStatusConsumer);
	});

	expect(consumer.children[0]).toBe('Completed, Pending');

	unmount();
});

const MockProcessStatusConsumer = () => {
	const {processStatuses} = useContext(ProcessStatusContext);

	return processStatuses.map(processStatus => processStatus.key).join(', ');
};
