import client from '../../../../test/mock/fetch';
import { TimeRangeStore } from '../timeRangeStore';

test('Should fetch time ranges', () => {
	const data = {
		items: [
			{
				dateEnd: new Date(2019, 5, 13),
				dateStart: new Date(2019, 5, 12),
				defaultTimeRange: true,
				id: 0,
				key: 'today',
				name: 'Today'
			},
			{
				defaultTimeRange: false,
				key: 'all-time',
				name: 'All Time'
			}
		]
	};

	const timeRangeStore = new TimeRangeStore(client(data));

	timeRangeStore.fetchTimeRanges().then(() => {
		timeRangeStore
			.getState()
			.timeRanges.forEach((timeRange, index) =>
				expect(timeRange.name).toEqual(data.items[index].name)
			);
	});
});

test('Should get default time range', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const timeRanges = [
		{
			dateEnd: new Date(2019, 5, 13),
			dateStart: new Date(2019, 5, 12),
			defaultTimeRange: true,
			id: 0,
			key: 'today',
			name: 'Today'
		},
		{
			defaultTimeRange: false,
			key: 'all-time',
			name: 'All Time'
		}
	];

	timeRangeStore.setState({ timeRanges });

	expect(timeRangeStore.defaultTimeRange.key).toEqual(timeRanges[0].key);
});

test('Should init with default state', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const defaultState = {
		timeRanges: []
	};

	expect(timeRangeStore.getState()).toMatchObject(defaultState);
});

test('Should return empty json when there is no default time range', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const timeRanges = [
		{
			defaultTimeRange: false,
			key: 'all-time',
			name: 'All Time'
		}
	];

	timeRangeStore.setState({ timeRanges });

	expect(timeRangeStore.defaultTimeRange).toBeNull();
});

test('Should set state', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const newState = {
		timeRanges: [
			{
				defaultTimeRange: true,
				key: 'all-time',
				name: 'All Time'
			}
		]
	};

	timeRangeStore.setState(newState);

	expect(timeRangeStore.getState()).toMatchObject(newState);
});