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

import React, {useMemo} from 'react';
import {
	EdgeText,
	getBezierPath,
	getEdgeCenter,
	getSmoothStepPath,
	useStoreState,
} from 'react-flow-renderer';

import MarkerEndDefinition, {markerEndId} from './MarkerEndDefinition';
import {getEdgeParams} from './utils';

function Edge({
	data: {label},
	id,
	source,
	sourceX,
	sourceY,
	sourcePosition,
	style = {},
	target,
	targetPosition,
	targetX,
	targetY,
}) {
	const edgePath = getSmoothStepPath({
		sourcePosition,
		sourceX,
		sourceY,
		targetPosition,
		targetX,
		targetY,
	});

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY,
		targetX,
		targetY,
	});

	let labelPositionX = edgeCenterX;
	let labelPositionY = edgeCenterY;

	if (
		edgePath.indexOf(`${edgeCenterX}`) === -1 &&
		edgePath.indexOf(`${edgeCenterY}`) === -1
	) {
		const substring1 = edgePath.substring(0, edgePath.lastIndexOf(','));

		const substring2 = substring1.substring(0, substring1.lastIndexOf(','));

		const substring3 = substring2.substring(
			substring2.lastIndexOf(',') + 1
		);

		const index = substring3.indexOf(' ');

		const y = substring3.substring(0, index);

		const x = substring3.substring(index + 1);

		labelPositionX = parseFloat(x);
		labelPositionY = parseFloat(y);
	}

	const nodes = useStoreState((state) => state.nodes);

	const sourceNode = useMemo(() => nodes.find((n) => n.id === source), [
		source,
		nodes,
	]);
	const targetNode = useMemo(() => nodes.find((n) => n.id === target), [
		target,
		nodes,
	]);

	const {sourcePos, sx, sy, targetPos, tx, ty} = getEdgeParams(
		sourceNode,
		targetNode
	);

	const drawn = getBezierPath({
		sourcePosition: sourcePos,
		sourceX: sx,
		sourceY: sy,
		targetPosition: targetPos,
		targetX: tx,
		targetY: ty,
	});

	const edgeStyle = {
		...style,
		stroke: '#80ACFF',
		strokeWidth: 2,
	};

	return (
		<g className="react-flow__connection">
			<MarkerEndDefinition />

			<path
				className="react-flow__edge-path"
				d={drawn}
				id={id}
				markerEnd={`url(#${markerEndId})`}
				style={edgeStyle}
			/>

			<EdgeText
				label={label.toUpperCase()}
				labelBgBorderRadius="13px"
				labelBgPadding={[8, 4]}
				labelBgStyle={{
					fill: '#0B5FFF',
				}}
				labelShowBg={true}
				labelStyle={{fill: '#FFF', fontWeight: 600}}
				x={labelPositionX}
				y={labelPositionY}
			/>
		</g>
	);
}

const edgeTypes = {
	transition: Edge,
};

export default edgeTypes;
