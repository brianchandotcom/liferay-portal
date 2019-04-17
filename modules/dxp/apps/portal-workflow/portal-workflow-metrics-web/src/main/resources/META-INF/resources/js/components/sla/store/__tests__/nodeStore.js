import client from '../../../../test/mock/fetch';
import { NodeStore } from '../nodeStore';
import { SLAStore } from '../slaStore';

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

	const slaStore = new SLAStore(client());

	slaStore.setState({
		pauseNodeKeys: [],
		startNodeKeys: [],
		stopNodeKeys: ['26625:LEAVE']
	});

	const nodeStore = new NodeStore(client(defaultData), slaStore);

	nodeStore.fetchNodes('1').then(() => {
		expect(nodeStore.getState()).toMatchObject({ nodes: defaultData.items });

		expect(nodeStore.getNodes().map(({ id }) => id)).toMatchObject([
			26603,
			26605,
			26610,
			26625
		]);

		expect(nodeStore.getPauseNodes().map(({ id }) => id)).toMatchObject([
			26610
		]);

		expect(nodeStore.getStartNodes().map(({ id }) => id)).toMatchObject([
			26605,
			'26610:ENTER',
			'26625:ENTER',
			'26610:LEAVE'
		]);

		expect(nodeStore.getStopNodes().map(({ id }) => id)).toMatchObject([
			26603,
			'26610:ENTER',
			'26625:ENTER',
			'26610:LEAVE',
			'26625:LEAVE'
		]);

		nodeStore.setState({ nodes: [] });

		expect(nodeStore.getState()).toMatchObject({ nodes: [] });

		expect(nodeStore.getNodes().map(({ id }) => id)).toMatchObject([]);

		expect(nodeStore.getPauseNodes().map(({ id }) => id)).toMatchObject([]);

		expect(nodeStore.getStartNodes().map(({ id }) => id)).toMatchObject([]);

		expect(nodeStore.getStopNodes().map(({ id }) => id)).toMatchObject([]);
	});
});

test('Should test initial state', () => {
	const nodeStore = new NodeStore(client(), new SLAStore(client()));

	const defaultData = {
		nodes: []
	};

	expect(nodeStore.getState()).toMatchObject(defaultData);
});

test('Should test sort', () => {
	const nodeStore = new NodeStore(client(), new SLAStore(client()));

	const nodeA = { name: 'a' };
	const nodeB = { name: 'b' };

	expect(nodeStore.compareToName(nodeA, nodeB)).toBe(-1);
	expect(nodeStore.compareToName(nodeB, nodeA)).toBe(1);
	expect(nodeStore.compareToName(nodeB, nodeB)).toBe(0);
});