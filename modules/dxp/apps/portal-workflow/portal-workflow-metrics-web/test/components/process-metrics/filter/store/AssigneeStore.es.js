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
	AssigneeContext,
	AssigneeProvider,
	useAssignee
} from '../../../../../src/main/resources/META-INF/resources/js/components/process-metrics/filter/store/AssigneeStore.es';
import {cleanup, render, waitForElement} from '@testing-library/react';
import React, {useContext} from 'react';
import {MockRouter} from '../../../../mock/MockRouter.es';
import {renderHook} from '@testing-library/react-hooks';
import Request from '../../../../../src/main/resources/META-INF/resources/js/shared/components/request/Request.es';

const items = [
	{
		active: true,
		id: 1,
		key: '1',
		name: 'User 1'
	},
	{
		id: 2,
		key: '2',
		name: 'User 2'
	}
];

const clientMock = {
	get: jest.fn().mockResolvedValue({data: {items}})
};

const MockAssigneeConsumer = () => {
	const {assignees} = useContext(AssigneeContext);

	return assignees.map((assignee, index) => (
		<span data-testId="assigneeKey" key={index}>
			{assignee.name}
		</span>
	));
};

const MockAppContext = ({children}) => (
	<MockRouter client={clientMock}>
		<Request>{children}</Request>
	</MockRouter>
);

describe('The selected assignees should', () => {
	test('Be empty when there is no initial key', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useAssignee([]),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedAssignees = result.current.getSelectedAssignees();

		expect(selectedAssignees.length).toBe(0);

		unmount();
	});

	test('Be "Unassigned" when the initial key is "-1"', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useAssignee(['-1']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedAssignees = result.current.getSelectedAssignees();

		expect(selectedAssignees[0].name).toBe('unassigned');

		unmount();
	});

	test('Be "User 1" and "User 2" when the initial key is "1" and "2"', async () => {
		const {result, unmount, waitForNextUpdate} = renderHook(
			() => useAssignee(['1', '2']),
			{wrapper: MockAppContext}
		);

		await waitForNextUpdate();

		const selectedAssignees = result.current.getSelectedAssignees();

		expect(selectedAssignees[0].name).toBe('User 1');
		expect(selectedAssignees[1].name).toBe('User 2');

		unmount();
	});
});

describe('The assignee store, when receiving "User 1" and "User 2" items, should', () => {
	let renderer;

	beforeEach(() => {
		renderer = renderHook(({assigneeKeys}) => useAssignee(assigneeKeys), {
			initialProps: {
				assigneeKeys: ['-1']
			},
			wrapper: MockAppContext
		});
	});

	afterEach(() => {
		renderer.unmount();
		renderer = null;
	});

	test('Keep the selected assignees when the keys are the same', () => {
		const {rerender, result} = renderer;

		rerender({
			assigneeKeys: ['-1']
		});

		const selectedAssignees = result.current.getSelectedAssignees();

		expect(selectedAssignees[0].key).toBe('-1');
	});

	test('Update the selected assignees when the keys changed', () => {
		const {rerender, result} = renderer;

		rerender({
			assigneeKeys: ['1', '2']
		});

		const selectedAssignees = result.current.getSelectedAssignees();

		expect(selectedAssignees[0].key).toBe('1');
		expect(selectedAssignees[1].key).toBe('2');
	});
});

describe('The assignee store, when receiving no items, should', () => {
	test('Have only the "Unassigned" item', async () => {
		clientMock.get.mockResolvedValueOnce({data: {}});

		const {result, waitForNextUpdate, unmount} = renderHook(
			({assigneeKeys}) => useAssignee(assigneeKeys),
			{
				initialProps: {
					assigneeKeys: [-1]
				},
				wrapper: MockAppContext
			}
		);

		await waitForNextUpdate();

		expect(result.current.assignees[0].key).toBe('-1');

		unmount();
	});

	test('Return a fallback array of selected items', () => {
		clientMock.get.mockResolvedValueOnce({data: {}});

		const {result, unmount} = renderHook(
			({assigneeKeys}) => useAssignee(assigneeKeys),
			{
				initialProps: {
					assigneeKeys: [-1]
				},
				wrapper: MockAppContext
			}
		);

		const selectedAssignees = result.current.getSelectedAssignees(['-1']);

		expect(selectedAssignees[0].key).toBe('-1');

		unmount();
	});
});

describe('The assignee provider should', () => {
	let getAllByTestId;

	afterEach(cleanup);

	beforeEach(() => {
		const renderResult = render(
			<MockAppContext>
				<AssigneeProvider assigneeKeys={[1, 2]}>
					<MockAssigneeConsumer />
				</AssigneeProvider>
			</MockAppContext>
		);

		getAllByTestId = renderResult.getAllByTestId;
	});

	test('Render "unassigned", "User 1", and "User 2" items', async () => {
		const assigneeKeys = await waitForElement(() =>
			getAllByTestId('assigneeKey')
		);

		expect(assigneeKeys[0].innerHTML).toBe('unassigned');
		expect(assigneeKeys[1].innerHTML).toBe('User 1');
		expect(assigneeKeys[2].innerHTML).toBe('User 2');
	});

	test('Render nothing when the request fails', async () => {
		clientMock.get.mockRejectedValueOnce(new Error('request-failure'));

		const assigneeKeys = await waitForElement(() =>
			getAllByTestId('assigneeKey')
		);

		expect(assigneeKeys[0].innerHTML).toBe('unassigned');
		expect(assigneeKeys[1].innerHTML).toBe('User 1');
		expect(assigneeKeys[2].innerHTML).toBe('User 2');
	});
});
