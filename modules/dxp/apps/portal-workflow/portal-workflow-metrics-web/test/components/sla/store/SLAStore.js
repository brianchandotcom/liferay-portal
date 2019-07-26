import {act, renderHook} from '@testing-library/react-hooks';
import {
	START_NODE_KEYS,
	STOP_NODE_KEYS
} from '../../../../src/main/resources/META-INF/resources/js/components/sla/Constants';
import client from '../../../mock/fetch';
import {useSLA} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/SLAStore';

test('Should change SLA form values', () => {
	const nodeKeys = [
		{
			compositeId: '123:enter',
			executionType: 'enter',
			id: 123
		},
		{
			compositeId: '321:enter',
			executionType: 'enter',
			id: 321
		},
		{
			compositeId: '1234:leave',
			executionType: 'leave',
			id: 1234
		}
	];

	const {result} = renderHook(() => useSLA(client({}), '1', '1'));

	const {
		changeNodesKeys,
		changePauseNodes,
		changeValue,
		filterNodeTagIds,
		pauseNodeTagIds
	} = result.current;

	act(() => changeValue('days', 123));
	expect(result.current.sla.days).toBe(123);
	expect(pauseNodeTagIds(nodeKeys, nodeKeys)).toMatchObject([
		'123:enter',
		'321:enter',
		'1234:leave'
	]);
	expect(filterNodeTagIds(nodeKeys, nodeKeys)).toMatchObject([
		'123:enter',
		'321:enter',
		'1234:leave'
	]);

	act(() =>
		changePauseNodes(nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.pauseNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);

	act(() =>
		changeNodesKeys(START_NODE_KEYS, nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.startNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);

	act(() =>
		changeNodesKeys(STOP_NODE_KEYS, nodeKeys, () => {})([
			'123:enter',
			'456:enter',
			'321:enter'
		])
	);
	expect(
		result.current.sla.stopNodeKeys.nodeKeys.map(({id}) => id)
	).toMatchObject([123, 321]);
});

test('Should test fetch data', () => {
	const data = {
		calendarKey: '123',
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client(data), '1', '1')
	);

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(data);
	});
});

test('Should test fetch data without some parts', () => {
	const data = {
		days: null,
		hours: '',
		name: 'test',
		processId: ''
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client(data), '1', '1')
	);

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject({
			calendarKey: undefined,
			days: null,
			description: '',
			hours: '',
			name: 'test',
			pauseNodeKeys: {
				nodeKeys: []
			},
			startNodeKeys: {
				nodeKeys: []
			},
			status: undefined,
			stopNodeKeys: {
				nodeKeys: []
			}
		});
	});
});

test('Should test initial state', () => {
	const defaultData = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: undefined,
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client({}), '1', '1')
	);

	act(() => result.current.fetchSLA('1'));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(defaultData);
	});
});

test('Should test reset', () => {
	const defaultData = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: undefined,
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client({}), '1', '1')
	);

	act(() => result.current.resetNodes());

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(defaultData);
	});
});

test('Should test save data', () => {
	const defaultData = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client(defaultData), '1', '1')
	);

	act(() => result.current.saveSLA('1', null, null));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(defaultData);
	});
});

test('Should test update data', () => {
	const defaultData = {
		calendarKey: undefined,
		days: null,
		description: '',
		hours: '',
		name: 'test',
		pauseNodeKeys: {nodeKeys: []},
		startNodeKeys: {nodeKeys: []},
		status: undefined,
		stopNodeKeys: {nodeKeys: []}
	};

	const {result, waitForNextUpdate} = renderHook(() =>
		useSLA(client(defaultData), '1', '1')
	);

	act(() => result.current.saveSLA('1', '1', null));

	return waitForNextUpdate().then(() => {
		expect(result.current.sla).toMatchObject(defaultData);
	});
});
