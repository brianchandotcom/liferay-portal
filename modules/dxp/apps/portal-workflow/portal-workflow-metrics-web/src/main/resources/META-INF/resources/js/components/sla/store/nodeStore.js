import client from '../../../shared/rest/fetch';

class NodeStore {
	constructor(client) {
		this.client = client;
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
			.then(({ data: { items } }) => {
				const entersTaskString = Liferay.Language.get('enters-task');
				const leavesTaskString = Liferay.Language.get('leaves-task');
				const nodeBegins = [];
				const nodeEnds = [];
				const nodeEnters = [];
				const nodeLeaves = [];
				const processBeginsString = Liferay.Language.get('process-begins');
				const processEndsString = Liferay.Language.get('process-ends');

				items.forEach(node => {
					if (node.type === 'STATE') {
						const newNode = {
							...node,
							desc: node.initial
								? processBeginsString
								: `${processEndsString} ${node.name}`,
							executionType: node.initial ? 'begin' : 'end'
						};

						if (node.initial) {
							nodeBegins.push(newNode);
						}
						else {
							nodeEnds.push(newNode);
						}
					}
					else if (node.type === 'TASK') {
						nodeEnters.push({
							...node,
							desc: `${entersTaskString} ${node.name}`,
							executionType: 'enter'
						});

						nodeLeaves.push({
							...node,
							desc: `${leavesTaskString} ${node.name}`,
							executionType: 'leave'
						});
					}
				});

				nodeEnters.sort(this.compareToName);
				nodeLeaves.sort(this.compareToName);

				const nodes = [
					...nodeBegins,
					...nodeEnters,
					...nodeLeaves,
					...nodeEnds
				].map(node => ({
					...node,
					compositeId: `${node.id}:${node.executionType}`
				}));

				this.setState({ nodes });

				return nodes;
			});
	}

	getPauseNodes(startNodeKeys, stopNodeKeys) {
		const selectedNodes = [...startNodeKeys, ...stopNodeKeys]
			.map(({ id }) => `${id}`)
			.filter((id, index, self) => self.indexOf(id) === index);

		const onTaskString = Liferay.Language.get('on-task');

		return this.getState()
			.nodes.filter(({ type }) => type === 'TASK')
			.filter(({ id }) => !selectedNodes.includes(`${id}`))
			.filter(
				(node, index, self) =>
					self.findIndex(({ id }) => id == node.id) === index
			)
			.map(node => ({
				...node,
				compositeId: `${node.id}:on`,
				desc: `${onTaskString} ${node.name}`,
				executionType: 'on'
			}));
	}

	getStartNodes(pauseNodeKeys, stopNodeKeys) {
		const pauseNodeKeyIds = pauseNodeKeys.map(({ id }) => `${id}`);

		const nodeKeyCompositeIds = stopNodeKeys.map(
			({ executionType, id }) => `${id}:${executionType}`
		);

		return this.getState()
			.nodes.filter(
				({ compositeId, id }) =>
					!nodeKeyCompositeIds.includes(`${compositeId}`) &&
					!pauseNodeKeyIds.includes(`${id}`)
			)
			.filter(
				({ initial, type }) =>
					(type === 'STATE' && initial === true) || type === 'TASK'
			);
	}

	getState() {
		return this.state;
	}

	getStopNodes(pauseNodeKeys, startNodeKeys) {
		const pauseNodeKeyIds = pauseNodeKeys.map(({ id }) => `${id}`);

		const nodeKeyCompositeIds = startNodeKeys.map(
			({ executionType, id }) => `${id}:${executionType}`
		);

		return this.getState()
			.nodes.filter(
				({ compositeId, id }) =>
					!nodeKeyCompositeIds.includes(`${compositeId}`) &&
					!pauseNodeKeyIds.includes(`${id}`)
			)
			.filter(
				({ terminal, type }) =>
					(type === 'STATE' && terminal === true) || type === 'TASK'
			);
	}

	setState(props) {
		this.state = Object.assign({}, this.getState(), props);
	}
}

export default new NodeStore(client);
export { NodeStore };