import client from '../../../shared/rest/fetch';

class ProcessTaskStore {
	constructor(client) {
		this.client = client;
		this.state = {
			processTasks: []
		};
	}

	fetchProcessTasks(processId) {
		return this.client
			.get(`/processes/${processId}/tasks?page=0&pageSize=0`)
			.then(({ data }) =>
				this.setState({
					processTasks: data.items
				})
			);
	}

	getState() {
		return this.state;
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new ProcessTaskStore(client);
export { ProcessTaskStore };