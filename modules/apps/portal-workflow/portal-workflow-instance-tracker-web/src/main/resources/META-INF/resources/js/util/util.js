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
import BorderStateNode from '../components/nodes/state-node/BorderStateNode';
import StateNode from '../components/nodes/state-node/StateNode';

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

	let targetNodeIndex;

	transitions = transitions.map((transition) => {
		const sourceNode = nodes.find((node) => node.id == transition.source);

		const sourceX = sourceNode.position.x;
		const sourceY = sourceNode.position.y;

		const targetNode = nodes.find((node) => node.id == transition.target);

		targetNodeIndex = nodes.findIndex(
			(node) => node.id == transition.target
		);

		let targetX = targetNode.position.x;
		let targetY = targetNode.position.y;

		if (sourceX > targetX && sourceY < targetY) {
			const newX = sourceNode.position.x + nodeWidth;
			const newY = sourceNode.position.y + nodeHeight;

			targetX = newX;
			targetY = newY;

			nodes[targetNodeIndex] = {
				...nodes[targetNodeIndex],
				position: {x: newX, y: newY},
			};
		}

		if (sourceY > targetY) {
			transition.targetHandle = 'top';
		}
		else {
			transition.targetHandle = 'left';
		}

		if (sourceX > targetX) {
			transition.sourceHandle = 'bottom';
		}
		else {
			transition.sourceHandle = 'right';
		}

		return transition;
	});

	elements = nodes.concat(transitions);

	return elements;
};

const getNodeType = (type) => {
	switch (type) {
		case 'TASK':
			return 'task';
		case 'STATE':
			return 'state';
		case 'INITIAL_STATE':
		case 'TERMINAL_STATE':
			return 'borderState';
		default:
			return 'task';
	}
};

const isCurrent = (currentSteps = [], node) => {
	return currentSteps.includes(node.name);
};

const isVisited = (visitedNodes = [], node) => {
	return visitedNodes.includes(node.name);
};

const nodeTypes = {
	borderState: BorderStateNode,
	state: StateNode,
	task: TaskNode,
};

export {getLayoutedElements, getNodeType, isCurrent, isVisited, nodeTypes};
