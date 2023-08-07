/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';
import {
	EdgeProps,
	EdgeText,
	getEdgeCenter,
	getSmoothStepPath,
	useStoreState,
} from 'react-flow-renderer';

import {getEdgeParams} from '../utils';
import ManyMarkerEnd from './ManyMarkerEnd';
import OneMarkerEnd from './OneMarkerEnd';

export default function DefaultEdge({
	id,
	source,
	target,
	style = {},
	data,
}: EdgeProps) {
	const nodes = useStoreState((state) => state.nodes);

	const sourceNode = useMemo(() => nodes.find((node) => node.id === source), [
		source,
		nodes,
	]);
	const targetNode = useMemo(() => nodes.find((node) => node.id === target), [
		target,
		nodes,
	]);

	if (!sourceNode || !targetNode) {
		return null;
	}

	const {sourcePos, sx, sy, targetPos, tx, ty} = getEdgeParams(
		sourceNode,
		targetNode
	);

	const edgePath = getSmoothStepPath({
		sourcePosition: sourcePos,
		sourceX: sx,
		sourceY: sy + data.sourceY,
		targetPosition: targetPos,
		targetX: tx,
		targetY: ty + data.targetY,
	});

	const reverseEdgePath = getSmoothStepPath({
		sourcePosition: targetPos,
		sourceX: tx,
		sourceY: ty + data.targetY,
		targetPosition: sourcePos,
		targetX: sx,
		targetY: sy + data.sourceY,
	});

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX: sx,
		sourceY: sy + data.sourceY,
		targetX: tx,
		targetY: ty + data.targetY,
	});

	return (
		<g className="react-flow__connection">
			<OneMarkerEnd />

			<ManyMarkerEnd />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={`url(#${data.markerEndId})`}
				style={{
					...style,
					stroke: '#0B5FFF',
					strokeWidth: '2px',
				}}
			/>

			<path
				className="react-flow__edge-path"
				d={reverseEdgePath}
				id={id + 'reverse'}
				markerEnd={`url(#${data.markerStartId})`}
				style={{
					...style,
					stroke: '#0B5FFF',
					strokeWidth: '2px',
				}}
			/>

			<EdgeText
				label={data.label}
				labelBgBorderRadius={4}
				labelBgPadding={[8, 5]}
				labelBgStyle={{
					fill: '#0B5FFF',
					height: '24px',
				}}
				labelShowBg
				labelStyle={{
					fill: '#FFF',
					fontSize: '12px',
					fontWeight: 600,
				}}
				x={edgeCenterX}
				y={edgeCenterY}
			/>
		</g>
	);
}
