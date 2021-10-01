/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useState} from 'react';

import {useFetch} from '../../../../shared/hooks/useFetch.es';

const useSLANodes = (processId) => {
	const [nodes, setNodes] = useState([]);

	const parseNodes = ({items}) => {
		const nodeBegins = [];
		const nodeEnds = [];
		const nodeEnters = [];
		const nodeLeaves = [];

		items.forEach((node) => {
			if (node.type === 'STATE') {
				const newNode = {
					...node,
					// eslint-disable-next-line @liferay/no-abbreviations
					desc: node.initial
						? Liferay.Language.get('process-begins')
						: `${Liferay.Language.get('process-ends')} ${
								node.label
						  }`,
					executionType: node.initial ? 'begin' : 'end',
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
					// eslint-disable-next-line @liferay/no-abbreviations
					desc: `${Liferay.Language.get('enters-task')} ${
						node.label
					}`,
					executionType: 'enter',
				});

				nodeLeaves.push({
					...node,
					// eslint-disable-next-line @liferay/no-abbreviations
					desc: `${Liferay.Language.get('leaves-task')} ${
						node.label
					}`,
					executionType: 'leave',
				});
			}
		});

		const compareToLabel = (curNode, nextNode) =>
			curNode.label.localeCompare(nextNode.label);

		nodeEnters.sort(compareToLabel);
		nodeLeaves.sort(compareToLabel);

		const nodes = [
			...nodeBegins,
			...nodeEnters,
			...nodeLeaves,
			...nodeEnds,
		].map((node) => ({
			...node,
			compositeId: `${node.id}:${node.executionType}`,
		}));

		setNodes(nodes);
	};

	const {fetchData: fetchNodes} = useFetch({
		callback: parseNodes,
		url: `/processes/${processId}/nodes`,
	});

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
			.map((node) => ({
				...node,
				compositeId: `${node.id}:on`,
				// eslint-disable-next-line @liferay/no-abbreviations
				desc: `${onTaskString} ${node.label}`,
				executionType: 'on',
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

	return {
		fetchNodes,
		getPauseNodes,
		getStartNodes,
		getStopNodes,
		nodes,
	};
};

export {useSLANodes};
