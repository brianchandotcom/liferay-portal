import client from 'shared/rest/fetch';
import {completionPeriodKeys} from '../instance-list/filterConstants';
import {formatTimeRange} from '../util/timeRangeUtil';

class TimeRangeStore {
	constructor(client) {
		this.client = client;
		this.state = {
			timeRanges: []
		};
	}

	get defaultTimeRange() {
		const defaultTimeRanges = this.state.timeRanges.filter(
			timeRange => timeRange.defaultTimeRange
		);

		return defaultTimeRanges.length ? defaultTimeRanges[0] : null;
	}

	fetchTimeRanges() {
		return this.client.get('/time-ranges').then(({data}) => {
			const timeRanges = data.items.map(item => ({
				...item,
				description: formatTimeRange(item),
				key: String(item.id)
			}));

			timeRanges.push({
				key: completionPeriodKeys.allTime,
				name: Liferay.Language.get('all-time')
			});

			this.setState({
				timeRanges
			});

			return Promise.resolve();
		});
	}

	getState() {
		return this.state;
	}

	get selectedTimeRange() {
		const {timeRanges} = this.state;

		if (!timeRanges || !timeRanges.length) {
			return null;
		}

		const selectedTimeRanges = timeRanges.filter(item => item.active);

		return selectedTimeRanges.length ? selectedTimeRanges[0] : null;
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new TimeRangeStore(client);
export {TimeRangeStore};
