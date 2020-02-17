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

import {useSLANodes} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/SLANodeStore.es';
import client from '../../../mock/fetch.es';

test('Should test fetch', () => {
	const defaultData = {
		items: [
			{
				id: 26603,
				initial: false,
				label: 'Approved',
				name: 'approved',
				terminal: true,
				type: 'STATE'
			},
			{
				id: 26605,
				initial: true,
				label: 'Created',
				name: 'created',
				terminal: false,
				type: 'STATE'
			},
			{
				id: 26610,
				initial: false,
				label: 'Review',
				name: 'review',
				terminal: false,
				type: 'TASK'
			},
			{
				id: 26625,
				initial: false,
				label: 'Update',
				name: 'update',
				terminal: false,
				type: 'TASK'
			}
		],
		lastPage: 1,
		page: 1,
		pageSize: 4,
		totalCount: 4
	};
	const pauseNodeKeys = [
		{
			executionType: 'leave',
			id: 26121
		}
	];
	const startNodeKeys = [
		{
			executionType: 'enter',
			id: 21125,
			type: 'TASK'
		}
	];
	const stopNodeKeys = [
		{
			executionType: 'leave',
			id: 26625
		}
	];
	const {result, waitForNextUpdate} = renderHook(() =>
		useSLANodes('123', client(defaultData))
	);

	act(() => result.current.fetchNodes('123'));

	return waitForNextUpdate().then(() => {
		const {
			getPauseNodes,
			getStartNodes,
			getStopNodes,
			nodes
		} = result.current;

		expect(nodes.map(({id}) => id)).toMatchObject([
			26605,
			26610,
			26625,
			26610,
			26625,
			26603
		]);

		expect(
			getPauseNodes(startNodeKeys, stopNodeKeys).map(
				({compositeId}) => compositeId
			)
		).toMatchObject(['26610:on']);

		expect(
			getStartNodes(pauseNodeKeys, stopNodeKeys).map(({id}) => id)
		).toMatchObject([26605, 26610, 26625, 26610]);

		expect(
			getStopNodes(pauseNodeKeys, startNodeKeys).map(({id}) => id)
		).toMatchObject([26610, 26625, 26610, 26625, 26603]);
	});
});

test('Should test fetch data', () => {
	const defaultData = {
		items: [
			{
				id: 26603,
				initial: false,
				label: 'Approved',
				name: 'approved',
				terminal: true,
				type: 'STATE'
			},
			{
				id: 26605,
				initial: true,
				label: 'Created',
				name: 'created',
				terminal: false,
				type: 'STATE'
			},
			{
				id: 26610,
				initial: false,
				label: 'Review',
				name: 'review',
				terminal: false,
				type: 'TASK'
			},
			{
				id: 26625,
				initial: false,
				label: 'Update',
				name: 'update',
				terminal: false,
				type: 'TASK'
			}
		],
		lastPage: 1,
		page: 1,
		pageSize: 4,
		totalCount: 4
	};
	const pauseNodeKeys = [
		{
			executionType: 'leave',
			id: 26121
		}
	];
	const startNodeKeys = [
		{
			executionType: 'enter',
			id: 21125,
			type: 'TASK'
		}
	];
	const stopNodeKeys = [
		{
			executionType: 'leave',
			id: 26625
		}
	];
	const {result, waitForNextUpdate} = renderHook(() =>
		useSLANodes('123', client(defaultData))
	);

	act(() => result.current.fetchNodes('123'));

	return waitForNextUpdate().then(() => {
		const {
			getPauseNodes,
			getStartNodes,
			getStopNodes,
			nodes
		} = result.current;

		expect(nodes.map(({id}) => id)).toMatchObject([
			26605,
			26610,
			26625,
			26610,
			26625,
			26603
		]);

		expect(
			getPauseNodes(startNodeKeys, stopNodeKeys).map(
				({compositeId}) => compositeId
			)
		).toMatchObject(['26610:on']);

		expect(
			getStartNodes(pauseNodeKeys, stopNodeKeys).map(({id}) => id)
		).toMatchObject([26605, 26610, 26625, 26610]);

		expect(
			getStopNodes(pauseNodeKeys, startNodeKeys).map(({id}) => id)
		).toMatchObject([26610, 26625, 26610, 26625, 26603]);
	});
});

test('Should test initial state', () => {
	const {result, waitForNextUpdate} = renderHook(() =>
		useSLANodes(
			'123',
			client({
				items: [],
				lastPage: 1,
				page: 1,
				pageSize: 1,
				totalCount: 0
			})
		)
	);

	act(() => result.current.fetchNodes('123'));

	return waitForNextUpdate().then(() => {
		const defaultData = {
			nodes: []
		};

		expect(result.current.nodes).toMatchObject(defaultData.nodes);
	});
});
