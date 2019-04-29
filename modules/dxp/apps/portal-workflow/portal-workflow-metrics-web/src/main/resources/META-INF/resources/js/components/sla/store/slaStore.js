import {
	durationAsMilliseconds,
	formatHours,
	getDurationValues
} from '../../../shared/util/duration';
import client from '../../../shared/rest/fetch';

class SLAStore {
	constructor(client) {
		this.client = client;
		this.state = {};
		this.reset();
	}

	fetchData(slaId) {
		return this.client.get(`/slas/${slaId}`).then(result => {
			const {
				description = '',
				duration,
				name,
				pauseNodeKeys,
				startNodeKeys,
				stopNodeKeys
			} = result.data;

			const { days, hours, minutes } = getDurationValues(duration);

			const formattedHours = formatHours(hours, minutes);

			pauseNodeKeys.nodeKeys = pauseNodeKeys.nodeKeys || [];
			startNodeKeys.nodeKeys = startNodeKeys.nodeKeys || [];
			stopNodeKeys.nodeKeys = stopNodeKeys.nodeKeys || [];

			this.setState({
				days,
				description,
				hours: formattedHours,
				name,
				pauseNodeKeys,
				startNodeKeys,
				stopNodeKeys
			});

			return this.getState();
		});
	}

	getState() {
		return this.state;
	}

	reset() {
		this.setState({
			days: null,
			description: '',
			hours: '',
			name: '',
			pauseNodeKeys: {
				nodeKeys: [],
				status: 0
			},
			processId: '',
			startNodeKeys: {
				nodeKeys: [],
				status: 0
			},
			stopNodeKeys: {
				nodeKeys: [],
				status: 0
			}
		});
	}

	saveSLA(processId, slaId) {
		const {
			days,
			description,
			hours,
			name,
			pauseNodeKeys,
			startNodeKeys,
			stopNodeKeys
		} = this.getState();

		const duration = durationAsMilliseconds(days, hours);

		let submit = body => this.client.post(`/processes/${processId}/slas`, body);

		if (slaId) {
			submit = body => this.client.put(`/slas/${slaId}`, body);
		}

		return submit({
			description,
			duration,
			name,
			pauseNodeKeys: {
				nodeKeys: pauseNodeKeys.nodeKeys.map(({ executionType, id }) => ({
					executionType,
					id
				})),
				status: 0
			},
			processId,
			startNodeKeys: {
				nodeKeys: startNodeKeys.nodeKeys.map(({ executionType, id }) => ({
					executionType,
					id
				})),
				status: 0
			},
			status: 0,
			stopNodeKeys: {
				nodeKeys: stopNodeKeys.nodeKeys.map(({ executionType, id }) => ({
					executionType,
					id
				})),
				status: 0
			}
		});
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new SLAStore(client);
export { SLAStore };