import {createContext, useEffect, useState} from 'react';

const useSLANodes = (processId, fetchClient) => {
	const [nodes, setNodes] = useState([]);

	const fetchNodes = processId => {
		fetchClient
			.get(`/processes/${processId}/nodes`)
			.then(({data: {items}}) => {
				const entersTaskString = Liferay.Language.get('enters-task');
				const leavesTaskString = Liferay.Language.get('leaves-task');
				const nodeBegins = [];
				const nodeEnds = [];
				const nodeEnters = [];
				const nodeLeaves = [];
				const processBeginsString = Liferay.Language.get(
					'process-begins'
				);
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
						} else {
							nodeEnds.push(newNode);
						}
					} else if (node.type === 'TASK') {
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

				const compareToName = (curNode, nextNode) =>
					curNode.name.localeCompare(nextNode.name);

				nodeEnters.sort(compareToName);
				nodeLeaves.sort(compareToName);

				const nodes = [
					...nodeBegins,
					...nodeEnters,
					...nodeLeaves,
					...nodeEnds
				].map(node => ({
					...node,
					compositeId: `${node.id}:${node.executionType}`
				}));

				setNodes(nodes);
			});
	};

	const getPauseNodes = (startNodeKeys, stopNodeKeys) => {
		const selectedNodes = [...startNodeKeys, ...stopNodeKeys]
			.map(({id}) => `${id}`)
			.filter((id, index, self) => self.indexOf(id) === index);

		const onTaskString = Liferay.Language.get('on-task');

		return nodes
			.filter(({type}) => type === 'TASK')
			.filter(({id}) => !selectedNodes.includes(`${id}`))
			.filter(
				(node, index, self) =>
					self.findIndex(({id}) => id == node.id) === index
			)
			.map(node => ({
				...node,
				compositeId: `${node.id}:on`,
				desc: `${onTaskString} ${node.name}`,
				executionType: 'on'
			}));
	};

	const getStartNodes = (pauseNodeKeys, stopNodeKeys) => {
		const pauseNodeKeyIds = pauseNodeKeys.map(({id}) => `${id}`);

		const nodeKeyCompositeIds = stopNodeKeys.map(
			({executionType, id}) => `${id}:${executionType}`
		);

		return nodes
			.filter(
				({compositeId, id}) =>
					!nodeKeyCompositeIds.includes(`${compositeId}`) &&
					!pauseNodeKeyIds.includes(`${id}`)
			)
			.filter(
				({initial, type}) =>
					(type === 'STATE' && initial === true) || type === 'TASK'
			);
	};

	const getStopNodes = (pauseNodeKeys, startNodeKeys) => {
		const pauseNodeKeyIds = pauseNodeKeys.map(({id}) => `${id}`);

		const nodeKeyCompositeIds = startNodeKeys.map(
			({executionType, id}) => `${id}:${executionType}`
		);

		return nodes
			.filter(
				({compositeId, id}) =>
					!nodeKeyCompositeIds.includes(`${compositeId}`) &&
					!pauseNodeKeyIds.includes(`${id}`)
			)
			.filter(
				({terminal, type}) =>
					(type === 'STATE' && terminal === true) || type === 'TASK'
			);
	};

	useEffect(() => {
		fetchNodes(processId);
	}, []);

	return {
		fetchNodes,
		getPauseNodes,
		getStartNodes,
		getStopNodes,
		nodes
	};
};

const SLANodes = createContext({});

export {SLANodes, useSLANodes};
