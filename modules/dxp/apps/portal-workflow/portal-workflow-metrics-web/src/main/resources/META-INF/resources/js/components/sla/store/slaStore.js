import {
	durationAsMilliseconds,
	formatHours,
	getDurationValues
} from '../../../shared/util/duration';
import client from '../../../shared/rest/fetch';
import nodeStore from './nodeStore';

class SLAStore {
	constructor(client, nodeStore) {
		this.client = client;
		this.nodeStore = nodeStore;
		this.state = {};
		this.reset();
	}

	fetchData(slaId) {
		return this.client.get(`/slas/${slaId}`).then(({ data }) => {
			const { description = '', duration, name, status } = data;

			const { days, hours, minutes } = getDurationValues(duration);

			const formattedHours = formatHours(hours, minutes);
			const nodeKeys = this.nodeStore.getState().nodes.map(({ id }) => `${id}`);

			const addNodeKeys = node => {
				if (node.nodeKeys) {
					node.nodeKeys = node.nodeKeys.filter(({ id }) =>
						nodeKeys.includes(id)
					);
				}
				else {
					node.nodeKeys = [];
				}

				return node;
			};

			const [pauseNodeKeys, startNodeKeys, stopNodeKeys] = [
				data.pauseNodeKeys,
				data.startNodeKeys,
				data.stopNodeKeys
			].map(addNodeKeys);

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
				status,
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
			status: 0,
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

export default new SLAStore(client, nodeStore);
export { SLAStore };