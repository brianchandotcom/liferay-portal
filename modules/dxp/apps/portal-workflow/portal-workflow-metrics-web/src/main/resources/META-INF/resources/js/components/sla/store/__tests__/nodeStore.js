import client from '../../../../test/mock/fetch';
import { NodeStore } from '../nodeStore';

test('Should test fetch', () => {
	const defaultData = {
		items: [
			{
				id: 26603,
				initial: false,
				name: 'approved',
				terminal: true,
				type: 'STATE'
			},
			{
				id: 26605,
				initial: true,
				name: 'created',
				terminal: false,
				type: 'STATE'
			},
			{
				id: 26610,
				initial: false,
				name: 'review',
				terminal: false,
				type: 'TASK'
			},
			{
				id: 26625,
				initial: false,
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

	const pauseNodeKeys = [];
	const startNodeKeys = [];
	const stopNodeKeys = [
		{
			executionType: 'leave',
			id: 26625
		}
	];

	const nodeStore = new NodeStore(client(defaultData));

	nodeStore.fetchNodes('1').then(() => {
		expect(nodeStore.getNodes().map(({ id }) => id)).toMatchObject([
			26603,
			26605,
			26610,
			26625
		]);

		nodeStore.getPauseNodes('teste:', startNodeKeys, stopNodeKeys);

		expect(
			nodeStore
				.getPauseNodes(startNodeKeys, stopNodeKeys)
				.map(({ compositeId }) => compositeId)
		).toMatchObject([26610]);

		expect(
			nodeStore.getStartNodes(pauseNodeKeys, stopNodeKeys).map(({ id }) => id)
		).toMatchObject([26605, '26610:enter', '26625:enter', '26610:leave']);

		expect(
			nodeStore.getStopNodes(pauseNodeKeys, startNodeKeys).map(({ id }) => id)
		).toMatchObject([
			26603,
			'26610:enter',
			'26625:enter',
			'26610:leave',
			'26625:leave'
		]);

		nodeStore.setState({ nodes: [] });

		expect(nodeStore.getState()).toMatchObject({ nodes: [] });

		expect(nodeStore.getNodes().map(({ id }) => id)).toMatchObject([]);

		expect(
			nodeStore.getPauseNodes(startNodeKeys, stopNodeKeys).map(({ id }) => id)
		).toMatchObject([]);

		expect(
			nodeStore.getStartNodes(pauseNodeKeys, stopNodeKeys).map(({ id }) => id)
		).toMatchObject([]);

		expect(
			nodeStore.getStopNodes(pauseNodeKeys, startNodeKeys).map(({ id }) => id)
		).toMatchObject([]);
	});
});

test('Should test initial state', () => {
	const nodeStore = new NodeStore(client());

	const defaultData = {
		nodes: []
	};

	expect(nodeStore.getState()).toMatchObject(defaultData);
});

test('Should test sort', () => {
	const nodeStore = new NodeStore(client());

	const nodeA = { name: 'a' };
	const nodeB = { name: 'b' };

	expect(nodeStore.compareToName(nodeA, nodeB)).toBe(-1);
	expect(nodeStore.compareToName(nodeB, nodeA)).toBe(1);
	expect(nodeStore.compareToName(nodeB, nodeB)).toBe(0);
});