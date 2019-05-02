import client from '../../../../test/mock/fetch';
import { NodeStore } from '../nodeStore';
import { SLAStore } from '../slaStore';

test('Should test fetch data', () => {
	const data = {
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	const slaStore = new SLAStore(client(data), new NodeStore(client({})));

	slaStore.fetchData('1').then(() => {
		expect(slaStore.getState()).toMatchObject(data);
	});
});

test('Should test fetch data without some parts', () => {
	const data = {
		days: null,
		hours: '',
		name: 'test',
		processId: ''
	};

	const slaStore = new SLAStore(client(data), new NodeStore(client({})));

	slaStore.fetchData('1').then(() => {
		expect(slaStore.getState()).toMatchObject(data);
	});
});

test('Should test initial state', () => {
	const slaStore = new SLAStore(client(), new NodeStore(client({})));

	const defaultData = {
		days: null,
		description: '',
		hours: '',
		name: '',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	expect(slaStore.getState()).toMatchObject(defaultData);
});

test('Should test reset', () => {
	const slaStore = new SLAStore(client(), new NodeStore(client({})));

	const defaultData = {
		days: null,
		description: '',
		hours: '',
		name: '',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	slaStore.setState({ description: 'test' });

	slaStore.reset();

	expect(slaStore.getState()).toMatchObject(defaultData);
});

test('Should test save data', () => {
	const data = {
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	const slaStore = new SLAStore(client(data), new NodeStore(client({})));

	slaStore.saveSLA('1').then(saved => {
		expect(saved.data).toMatchObject(data);
	});
});

test('Should test update data', () => {
	const data = {
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: { nodeKeys: [] },
		processId: '',
		startNodeKeys: { nodeKeys: [] },
		stopNodeKeys: { nodeKeys: [] }
	};

	const slaStore = new SLAStore(client(data), new NodeStore(client({})));

	slaStore.saveSLA('1', '1').then(saved => {
		expect(saved.data).toMatchObject(data);
	});
});