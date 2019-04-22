import client from '../../../shared/rest/fetch';
import slaStore from './slaStore';

class NodeStore {
	constructor(client, slaStore) {
		this.client = client;
		this.slaStore = slaStore;
		this.state = {
			nodes: []
		};
	}

	compareToName(curNode, nextNode) {
		if (curNode.name < nextNode.name) {
			return -1;
		}

		if (curNode.name > nextNode.name) {
			return 1;
		}

		return 0;
	}

	fetchNodes(processId) {
		return this.client
			.get(`/processes/${processId}/nodes`)
			.then(({ data: { items } }) => (this.setState({ nodes: items }), items));
	}

	getNodes() {
		return this.getState().nodes.map(node => ({ ...node, desc: node.name }));
	}

	getPauseNodes() {
		const { startNodeKeys, stopNodeKeys } = this.slaStore.getState();

		const selectedNodes = [...startNodeKeys, ...stopNodeKeys]
			.filter(id => {
				const splitedNode = id.split(':');

				return (
					splitedNode.length && ['ENTER', 'LEAVE'].includes(splitedNode[1])
				);
			})
			.map(id => id.split(':')[0]);

		const onTaskString = Liferay.Language.get('on-task');

		const nodes = this.getNodes()
			.filter(({ type }) => type === 'TASK')
			.filter(({ id }) => !selectedNodes.includes(`${id}`))
			.map(node => ({ ...node, desc: `${onTaskString} ${node.name}` }));

		nodes.sort(this.compareToName);

		return nodes;
	}

	getStartNodes() {
		const { stopNodeKeys } = this.slaStore.getState();

		const taskNodes = this.getTaskNodes().filter(
			({ id }) => !stopNodeKeys.includes(`${id}`)
		);

		return this.getNodes()
			.filter(({ initial, type }) => type === 'STATE' && initial === true)
			.map(node => ({ ...node, desc: Liferay.Language.get('process-begins') }))
			.concat(taskNodes);
	}

	getState() {
		return this.state;
	}

	getStopNodes() {
		const { startNodeKeys } = this.slaStore.getState();

		const taskNodes = this.getTaskNodes().filter(
			({ id }) => !startNodeKeys.includes(`${id}`)
		);

		return this.getNodes()
			.filter(({ terminal, type }) => type === 'STATE' && terminal === true)
			.map(node => ({
				...node,
				desc: `${Liferay.Language.get('process-ends')} ${node.name}`
			}))
			.concat(taskNodes);
	}

	getTaskNodes() {
		const entersTaskString = Liferay.Language.get('enters-task');
		const leavesTaskString = Liferay.Language.get('leaves-task');
		const nodeEnters = [];
		const nodeLeaves = [];
		const { pauseNodeKeys } = this.slaStore.getState();

		this.getNodes()
			.filter(
				({ id, type }) => type === 'TASK' && !pauseNodeKeys.includes(`${id}`)
			)
			.forEach(node => {
				nodeEnters.push({
					...node,
					desc: `${entersTaskString} ${node.name}`,
					id: `${node.id}:ENTER`
				});
				nodeLeaves.push({
					...node,
					desc: `${leavesTaskString} ${node.name}`,
					id: `${node.id}:LEAVE`
				});
			});

		nodeEnters.sort(this.compareToName);
		nodeLeaves.sort(this.compareToName);

		return [...nodeEnters, ...nodeLeaves];
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new NodeStore(client, slaStore);
export { NodeStore };