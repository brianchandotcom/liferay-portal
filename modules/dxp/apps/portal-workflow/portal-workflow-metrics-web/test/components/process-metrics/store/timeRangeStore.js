import client from '../../../mock/fetch';
import {completionPeriodKeys} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/filterConstants';
import {TimeRangeStore} from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/store/timeRangeStore';

test('Should fetch time ranges', () => {
	const data = {
		items: [
			{
				dateEnd: new Date(Date.UTC(2019, 5, 13)),
				dateStart: new Date(Date.UTC(2019, 5, 12)),
				defaultTimeRange: true,
				id: 0,
				key: 'today',
				name: 'Today'
			},
			{
				dateEnd: new Date(Date.UTC(2019, 4, 10)),
				dateStart: new Date(Date.UTC(2019, 1, 10)),
				defaultTimeRange: false,
				id: 1,
				key: 'four-months',
				name: 'Four Months'
			}
		]
	};

	const timeRangeStore = new TimeRangeStore(client(data));

	return timeRangeStore.fetchTimeRanges().then(() => {
		timeRangeStore
			.getState()
			.timeRanges.filter(
				timeRange => timeRange.key !== completionPeriodKeys.allTime
			)
			.forEach((timeRange, index) =>
				expect(timeRange.name).toEqual(data.items[index].name)
			);
	});
});

test('Should format time range description', () => {
	const data = {
		items: [
			{
				dateEnd: new Date(Date.UTC(2019, 4, 10)),
				dateStart: new Date(Date.UTC(2018, 4, 10)),
				defaultTimeRange: true,
				id: 0,
				key: 'one-year',
				name: 'One Year'
			},
			{
				dateEnd: new Date(Date.UTC(2019, 4, 10)),
				dateStart: new Date(Date.UTC(2019, 1, 10)),
				defaultTimeRange: false,
				id: 1,
				key: 'four-months',
				name: 'Four Months'
			},
			{
				dateEnd: new Date(Date.UTC(2019, 4, 10, 23)),
				dateStart: new Date(Date.UTC(2019, 4, 10, 0)),
				defaultTimeRange: false,
				id: 2,
				key: 'today',
				name: 'Today'
			}
		]
	};

	const timeRangeDescriptions = [
		'10 May, 2018 - 10 May, 2019',
		'10 Feb - 10 May',
		'10 May, 12 AM - 10 May, 11 PM',
		null
	];

	const timeRangeStore = new TimeRangeStore(client(data));

	return timeRangeStore.fetchTimeRanges().then(() => {
		timeRangeStore
			.getState()
			.timeRanges.filter(
				timeRange => timeRange.key !== completionPeriodKeys.allTime
			)
			.forEach((timeRange, index) =>
				expect(timeRange.description).toEqual(
					timeRangeDescriptions[index]
				)
			);
	});
});

test('Should get default time range', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const timeRanges = [
		{
			dateEnd: new Date(Date.UTC(2019, 5, 13)),
			dateStart: new Date(Date.UTC(2019, 5, 12)),
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

	timeRangeStore.setState({timeRanges});

	expect(timeRangeStore.defaultTimeRange.key).toEqual(timeRanges[0].key);
});

test('Should get selected time range', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const timeRanges = [
		{
			active: true,
			dateEnd: new Date(Date.UTC(2019, 5, 13)),
			dateStart: new Date(Date.UTC(2019, 5, 12)),
			defaultTimeRange: true,
			id: 0,
			key: 'today',
			name: 'Today'
		},
		{
			active: false,
			defaultTimeRange: false,
			key: 'all-time',
			name: 'All Time'
		}
	];

	timeRangeStore.setState({timeRanges});

	expect(timeRangeStore.selectedTimeRange.key).toEqual(timeRanges[0].key);
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

	timeRangeStore.setState({timeRanges});

	expect(timeRangeStore.defaultTimeRange).toBeNull();
});

test('Should return null when there is no selected time range', () => {
	const timeRangeStore = new TimeRangeStore(client());

	const timeRanges = [
		{
			active: false,
			dateEnd: new Date(Date.UTC(2019, 5, 13)),
			dateStart: new Date(Date.UTC(2019, 5, 12)),
			defaultTimeRange: true,
			id: 0,
			key: 'today',
			name: 'Today'
		}
	];

	timeRangeStore.setState({timeRanges});

	expect(timeRangeStore.selectedTimeRange).toBeNull();
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
