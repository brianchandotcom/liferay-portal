/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';
import {
	EdgeProps,
	Node,
	getEdgeCenter,
	getSmoothStepPath,
	useStoreState,
} from 'react-flow-renderer';

import {ObjectRelationshipEdgeData} from '../types';
import {getEdgeParams} from '../utils';
import ObjectRelationshipEdge from './ObjectRelationshipEdge';

export default function DefaultObjectRelationshipEdge({
	data,
	id: edgeId,
	source,
	target,
}: EdgeProps<ObjectRelationshipEdgeData[]>) {
	const {nodes} = useStoreState((state) => state);

	const sourceNode = useMemo(
		() => nodes.find((node) => node.id === source),
		[source, nodes]
	) as Node<ObjectDefinitionNodeData>;

	const targetNode = useMemo(
		() => nodes.find((node) => node.id === target),
		[target, nodes]
	) as Node<ObjectDefinitionNodeData>;

	const {sourcePos, sourceX, sourceY, targetPos, targetX, targetY} =
		getEdgeParams(sourceNode, targetNode);

	const edgePath = getSmoothStepPath({
		sourcePosition: sourcePos,
		sourceX,
		sourceY,
		targetPosition: targetPos,
		targetX,
		targetY,
	});

	const reverseEdgePath = getSmoothStepPath({
		sourcePosition: targetPos,
		sourceX: targetX,
		sourceY: targetY,
		targetPosition: sourcePos,
		targetX: sourceX,
		targetY: sourceY,
	});

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY,
		targetX,
		targetY,
	});

	return (
		<ObjectRelationshipEdge
			data={data}
			edgeCenterX={edgeCenterX}
			edgeCenterY={edgeCenterY}
			edgePath={edgePath}
			id={edgeId}
			reverseEdgePath={reverseEdgePath}
			sourceX={sourceX}
			sourceY={sourceY}
			targetX={targetX}
			targetY={targetY}
		/>
	);
}
