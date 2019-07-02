import client from '../../../shared/rest/fetch';
import {completionPeriodKeys} from '../instance-list/filterConstants';
import moment from 'moment';

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
				description: this.formatTimeRange(item),
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

	formatTimeRange(timeRange) {
		const {dateEnd, dateStart} = timeRange;

		if (!dateEnd && !dateStart) {
			return null;
		}

		const dateEndMoment = moment.utc(dateEnd);
		const dateStartMoment = moment.utc(dateStart);

		const formatPattern = this.getFormatPattern(
			dateEndMoment,
			dateStartMoment
		);

		return `${dateStartMoment.format(
			formatPattern
		)} - ${dateEndMoment.format(formatPattern)}`;
	}

	getFormatPattern(dateEndMoment, dateStartMoment) {
		const daysDiff = dateEndMoment.diff(dateStartMoment, 'days');

		if (daysDiff <= 1) {
			return 'DD MMM, hh A';
		}

		const monthsDiff = dateEndMoment.diff(dateStartMoment, 'months');

		if (monthsDiff < 5) {
			return 'DD MMM';
		}

		return 'DD MMM, YYYY';
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
