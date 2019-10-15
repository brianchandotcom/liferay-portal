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
	ProcessStepContext,
	ProcessStepProvider,
	useProcessStep
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/ProcessStepStore.es';
import React, {useContext} from 'react';
import {ErrorContext} from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Error.es';
import {MockRouter} from '../../../../mock/MockRouter.es';
import {renderHook} from '@testing-library/react-hooks';
import Request from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';

const items = [
	{
		key: 'review',
		name: 'Review'
	},
	{
		key: 'update',
		name: 'Update'
	}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items}})
};

const MockProcessStepConsumer = () => {
	const {processSteps} = useContext(ProcessStepContext);

	return processSteps.map((processStep, index) => (
		<span data-testId="processStepKey" key={index}>
			{processStep.name}
		</span>
	));
};

const MockAppContext = ({children}) => (
	<MockRouter client={clientMock}>
		<Request>{children}</Request>
	</MockRouter>
);

describe('The selected process steps should', () => {
	test('Be empty when there is no initial key', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useProcessStep(12345, []),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedProcessSteps = result.current.getSelectedProcessSteps();

		expect(selectedProcessSteps.length).toBe(0);

		unmount();
	});

	test('Be "Review" when the initial key is "review"', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useProcessStep(12345, ['review']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedProcessSteps = result.current.getSelectedProcessSteps();

		expect(selectedProcessSteps[0].name).toBe('Review');

		unmount();
	});
});

describe('The process step store, when receiving "Review" and "Update" items, should', () => {
	let renderer;

	beforeEach(() => {
		renderer = renderHook(
			({processStepKeys}) => useProcessStep(12345, processStepKeys),
			{
				initialProps: {
					processStepKeys: ['review']
				},
				wrapper: MockAppContext
			}
		);
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected process steps when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			processStepKeys: ['review']
		});

		const selectedProcessSteps = result.current.getSelectedProcessSteps();

		expect(selectedProcessSteps[0].key).toBe('review');
	});

	test('Update the selected time range when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			processStepKeys: ['review', 'update']
		});

		const selectedProcessSteps = result.current.getSelectedProcessSteps();

		expect(selectedProcessSteps[0].key).toBe('review');
		expect(selectedProcessSteps[1].key).toBe('update');
	});
});

describe('The time range store, when receiving no items, should', () => {
	test('Call "setError" function when request fails', () => {
		const setError = jest.fn();

		const MockErrorContext = ({children}) => (
			<MockAppContext>
				<ErrorContext.Provider value={{setError}}>
					{children}
				</ErrorContext.Provider>
			</MockAppContext>
		);

		clientMock.get.mockRejectedValueOnce(new Error('request-failure'));

		const {unmount} = renderHook(
			({processStepKeys}) => useProcessStep(12345, processStepKeys),
			{
				initialProps: {
					processStepKeys: ['review']
				},
				wrapper: MockErrorContext
			}
		);

		expect(setError).toBeCalled();

		unmount();
	});

	test('Have "All Steps" item on processSteps array, when "withAllSteps" is true', async () => {
		clientMock.get.mockResolvedValueOnce({data: {items: []}});

		const {result, waitForNextUpdate, unmount} = renderHook(
			({processStepKeys}) => useProcessStep(12345, processStepKeys, true),
			{
				initialProps: {
					processStepKeys: []
				},
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		expect(result.current.processSteps[0].key).toBe('allSteps');

		unmount();
	});

	test('Have no items on processSteps array', async () => {
		clientMock.get.mockResolvedValueOnce({data: {items: []}});

		const {result, waitForNextUpdate, unmount} = renderHook(
			({processStepKeys}) => useProcessStep(12345, processStepKeys),
			{
				initialProps: {
					processStepKeys: []
				},
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		expect(result.current.processSteps.length).toBe(0);

		unmount();
	});

	test('Return a fallback array of selected items', () => {
		clientMock.get.mockResolvedValueOnce({data: {}});

		const {result, unmount} = renderHook(
			({processStepKeys}) => useProcessStep(12345, processStepKeys),
			{
				initialProps: {
					processStepKeys: ['review']
				},
				wrapper: MockAppContext
			}
		);

		const selectedProcessSteps = result.current.getSelectedProcessSteps([
			'review'
		]);

		expect(selectedProcessSteps[0].key).toBe('review');

		unmount();
	});
});

describe('The process step provider should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockAppContext>
				<ProcessStepProvider processId={12345} processStepKeys={[1, 2]}>
					<MockProcessStepConsumer />
				</ProcessStepProvider>
			</MockAppContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Render "Review" and "Update" items', async () => {
		const timeRangeKeys = await waitForElement(() =>
			getAllByTestId('processStepKey')
		);

		expect(timeRangeKeys[0].innerHTML).toBe('Review');
		expect(timeRangeKeys[1].innerHTML).toBe('Update');
	});
});
