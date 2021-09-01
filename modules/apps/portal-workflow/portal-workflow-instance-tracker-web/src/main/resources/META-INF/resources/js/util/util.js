/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import dagre from 'dagre';
import {isEdge, isNode} from 'react-flow-renderer';

import TaskNode from '../components/nodes/TaskNode';
import ConditionNode from '../components/nodes/hexagon-nodes/ConditionNode';
import ForkNode from '../components/nodes/hexagon-nodes/ForkNode';
import JoinNode from '../components/nodes/hexagon-nodes/JoinNode';
import JoinXorNode from '../components/nodes/hexagon-nodes/JoinXorNode';
import BorderStateNode from '../components/nodes/state-node/BorderStateNode';
import StateNode from '../components/nodes/state-node/StateNode';
import Edge from '../components/transitions/Edge';

const edgeTypes = {
	transition: Edge,
};

const getLayoutedElements = (elements) => {
	const dagreGraph = new dagre.graphlib.Graph();

	dagreGraph.setDefaultEdgeLabel(() => ({}));
	const nodeWidth = 280;
	const nodeHeight = 400;

	dagreGraph.setGraph({rankdir: 'LR'});

	elements.forEach((element) => {
		if (isNode(element)) {
			dagreGraph.setNode(element.id, {
				height: nodeHeight,
				width: nodeWidth,
			});
		}
		else {
			dagreGraph.setEdge(element.source, element.target);
		}
	});

	dagre.layout(dagreGraph);

	elements = elements.map((element) => {
		if (isNode(element)) {
			const nodeWithPosition = dagreGraph.node(element.id);

			element.position = {
				x: nodeWithPosition.x - nodeWidth / 2 + Math.random() / 1000,
				y: nodeWithPosition.y - nodeHeight / 2,
			};
		}

		return element;
	});

	const nodes = elements.filter((element) => isNode(element));

	let transitions = elements.filter((element) => isEdge(element));

	transitions = transitions.map((transition) => {
		const sourceNode = nodes.find((node) => node.id == transition.source);

		const sourceX = sourceNode.position.x;
		const sourceY = sourceNode.position.y;

		const targetNode = nodes.find((node) => node.id == transition.target);

		const targetX = targetNode.position.x;
		const targetY = targetNode.position.y;

		if (sourceX >= targetX) {
			if (sourceX - targetX < 100) {
				if (sourceY > targetY) {
					transition.sourceHandle = 'topSource1';
					transition.targetHandle = 'bottomTarget2';
				}
				else {
					transition.sourceHandle = 'bottomSource1';
					transition.targetHandle = 'topTarget2';
				}
			}
			else {
				if (sourceY > targetY) {
					if (sourceY - targetY < 100) {
						transition.sourceHandle = 'leftSource1';
						transition.targetHandle = 'rightTarget2';
					}
					else {
						transition.sourceHandle = 'topSource1';
						transition.targetHandle = 'bottomTarget2';
					}
				}
				else {
					if (targetY - sourceY < 100) {
						transition.sourceHandle = 'leftSource2';
						transition.targetHandle = 'rightTarget1';
					}
					else {
						transition.sourceHandle = 'bottomSource1';
						transition.targetHandle = 'topTarget2';
					}
				}
			}
		}
		else {
			if (targetX - sourceX < 100) {
				if (sourceY > targetY) {
					transition.sourceHandle = 'topSource2';
					transition.targetHandle = 'bottomTarget1';
				}
				else {
					transition.sourceHandle = 'bottomSource2';
					transition.targetHandle = 'topTarget1';
				}
			}
			else {
				if (sourceY > targetY) {
					if (sourceY - targetY < 100) {
						transition.sourceHandle = 'rightSource1';
						transition.targetHandle = 'leftTarget2';
					}
					else {
						transition.sourceHandle = 'topSource2';
						transition.targetHandle = 'bottomTarget1';
					}
				}
				else {
					if (targetY - sourceY < 100) {
						transition.sourceHandle = 'rightSource2';
						transition.targetHandle = 'leftTarget1';
					}
					else {
						transition.sourceHandle = 'bottomSource2';
						transition.targetHandle = 'topTarget1';
					}
				}
			}
		}

		return transition;
	});

	elements = nodes.concat(transitions);

	return elements;
};

const getNodeType = (type) => {
	if (type === 'CONDITION') {
		return 'condition';
	}
	else if (type === 'FORK') {
		return 'fork';
	}
	else if (type === 'JOIN') {
		return 'join';
	}
	else if (type === 'JOIN_XOR') {
		return 'joinXor';
	}
	else if (type === 'INITIAL_STATE' || type === 'TERMINAL_STATE') {
		return 'borderState';
	}
	else if (type === 'STATE') {
		return 'state';
	}
	else {
		return 'task';
	}
};

const isCurrent = (currentNodes = [], node) => {
	return currentNodes.includes(node.name);
};

const isVisited = (visitedNodes = [], node) => {
	return visitedNodes.includes(node.name);
};

const nodeTypes = {
	borderState: BorderStateNode,
	condition: ConditionNode,
	fork: ForkNode,
	join: JoinNode,
	joinXor: JoinXorNode,
	state: StateNode,
	task: TaskNode,
};

export {
	edgeTypes,
	getLayoutedElements,
	getNodeType,
	isCurrent,
	isVisited,
	nodeTypes,
};
