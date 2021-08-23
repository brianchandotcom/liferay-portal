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

import React, {useEffect, useState} from 'react';
import ReactFlow, {Controls, ReactFlowProvider} from 'react-flow-renderer';

import '../../css/main.scss';
import {useFetch} from '../hooks/useFetch';
import {
	getLayoutedElements,
	getNodeType,
	isCurrent,
	isVisited,
	nodeTypes,
} from '../util/util';

export default function WorkflowInstanceTracker({workflowInstanceId}) {
	const [currentSteps, setCurrentSteps] = useState([]);
	const [nodes, setNodes] = useState([]);
	const [transitions, setTransitions] = useState([]);
	const [visitedNodes, setVisitedNodes] = useState([]);
	const [definitionElements, setDefinitionElements] = useState({});

	const {data, fetchData} = useFetch({
		callback: (responses, client) => {
			client
				.get(
					`/workflow-definitions/by-name/${responses[0].data.workflowDefinitionName}`
				)
				.then((response) => {
					setDefinitionElements({
						nodes: response.data.nodes,
						transitions: response.data.transitions,
					});
				});
		},
		urls: [
			`/workflow-instances/${workflowInstanceId}`,
			`/workflow-instances/${workflowInstanceId}/workflow-logs?types=NodeEntry`,
		],
	});

	useEffect(() => {
		fetchData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (data.length) {
			setCurrentSteps(data[0].state.toLowerCase());
			const visitedNodes = data[1].items.map((item) => item.state);

			setVisitedNodes(visitedNodes);
		}
	}, [data]);

	useEffect(() => {
		const position = {x: 0, y: 0};
		const {
			nodes: nodeElements,
			transitions: transitionElements,
		} = definitionElements;

		if (nodeElements?.length && transitionElements?.length) {
			const nodes = nodeElements.map((node) => {
				return {
					data: {
						current: isCurrent(currentSteps, node),
						done: isVisited(visitedNodes, node),
						initial: node.type == 'INITIAL_STATE',
						label: node.label,
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
					id: transition.name,
					source: transition.sourceNodeName,
					target: transition.targetNodeName,
					type: 'smoothstep',
				};
			});

			setTransitions(transitions);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [definitionElements]);

	const elements = nodes.concat(transitions);

	const layoutedElements = getLayoutedElements(elements);

	const onLoad = (reactFlowInstance) => {
		reactFlowInstance.fitView();
	};

	return (
		<div className="workflow-instance-tracker">
			{layoutedElements.length && (
				<ReactFlowProvider>
					<ReactFlow
						elements={layoutedElements}
						minZoom="0.1"
						nodeTypes={nodeTypes}
						onLoad={onLoad}
					/>

					<Controls showInteractive={false} />
				</ReactFlowProvider>
			)}
		</div>
	);
}
