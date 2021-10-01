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

import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import ReactFlow, {Controls, ReactFlowProvider} from 'react-flow-renderer';

import '../../css/main.scss';
import EventObserver from '../util/EventObserver';
import {
	edgeTypes,
	getLayoutedElements,
	getNodeType,
	isCurrent,
	isVisited,
	nodeTypes,
} from '../util/util';
import CurrentNodes from './CurrentNodes';

const eventObserver = new EventObserver();

export default function WorkflowInstanceTracker({workflowInstanceId}) {
	const [currentNodes, setCurrentNodes] = useState([]);
	const [nodes, setNodes] = useState([]);
	const [transitions, setTransitions] = useState([]);
	const [visitedNodes, setVisitedNodes] = useState([]);
	const [definitionElements, setDefinitionElements] = useState({});

	useEffect(() => {
		fetch(
			`/o/headless-admin-workflow/v1.0/workflow-instances/${workflowInstanceId}`,
			{method: 'GET'}
		)
			.then((response) => response.json())
			.then((data) => {
				setCurrentNodes(data.currentNodeNames);

				fetch(
					`/o/headless-admin-workflow/v1.0/workflow-definitions/by-name/${data.workflowDefinitionName}`,
					{
						method: 'GET',
						params: {
							version: data.workflowDefinitionVersion,
						},
					}
				)
					.then((response) => response.json())
					.then((data) =>
						setDefinitionElements({
							nodes: data.nodes,
							transitions: data.transitions,
						})
					);
			});

		fetch(
			`/o/headless-admin-workflow/v1.0/workflow-instances/${workflowInstanceId}/workflow-logs?types=NodeEntry`,
			{method: 'GET'}
		)
			.then((response) => response.json())
			.then((data) => {
				const visitedNodes = data.items.map((item) => item.state);

				setVisitedNodes(visitedNodes);
			});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (definitionElements && visitedNodes) {
			const position = {x: 0, y: 0};
			const {
				nodes: nodeElements,
				transitions: transitionElements,
			} = definitionElements;

			if (nodeElements?.length && transitionElements?.length) {
				const nodes = nodeElements.map((node) => {
					return {
						data: {
							current: isCurrent(currentNodes, node),
							done: isVisited(visitedNodes, node),
							initial: node.type == 'INITIAL_STATE',
							label: node.label,
							notifyVisibilityChange: (visible) => () => {
								eventObserver.notify(node.name, () => visible);
							},
						},
						id: node.name,
						position,
						type: getNodeType(node.type),
					};
				});

				setNodes(nodes);

				const transitions = transitionElements.map((transition) => {
					return {
						arrowHeadType: 'arrowclosed',
						data: {
							eventObserver,
							text: transition.label,
						},
						id: transition.name,
						source: transition.sourceNodeName,
						target: transition.targetNodeName,
						type: 'transition',
					};
				});

				setTransitions(transitions);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [definitionElements, visitedNodes]);

	const elements = nodes.concat(transitions);

	const layoutedElements = getLayoutedElements(elements);

	const onLoad = (reactFlowInstance) => {
		reactFlowInstance.fitView();
	};

	return (
		<div className="workflow-instance-tracker">
			{!!layoutedElements.length && (
				<ReactFlowProvider>
					<ReactFlow
						edgeTypes={edgeTypes}
						elements={layoutedElements}
						minZoom="0.1"
						nodeTypes={nodeTypes}
						onLoad={onLoad}
					/>

					<Controls showInteractive={false} />

					<CurrentNodes nodesNames={currentNodes} />
				</ReactFlowProvider>
			)}
		</div>
	);
}
