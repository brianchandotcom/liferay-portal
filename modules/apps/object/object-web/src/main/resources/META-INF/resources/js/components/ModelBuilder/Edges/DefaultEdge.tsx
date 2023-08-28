/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useMemo, useState} from 'react';
import {
	EdgeProps,
	EdgeText,
	getEdgeCenter,
	getSmoothStepPath,
	useStoreState,
} from 'react-flow-renderer';

import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import {getEdgeParams} from '../utils';
import ManyMarker from './ManyMarker';
import OneMarker from './OneMarker';

const DEFAULT_COLOR = '#80ACFF';
const HIGHLIGHT_COLOR = '#0B5FFF';

export function getInitialEdgeStyle(edgeSelected: boolean) {
	return {
		stroke: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		strokeWidth: '2px',
	};
}

export function getInitialLabelBgStyle(edgeSelected: boolean) {
	return {
		fill: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		height: '24px',
	};
}

export default function DefaultEdge({
	data,
	id,
	source,
	style = {},
	target,
}: EdgeProps<ObjectRelationshipEdgeData>) {
	const {
		edgeSelected,
		label,
		markerEndId,
		markerStartId,
		objectRelationshipId,
		sourceY: currentSourceY,
		targetY: currentTargetY,
	} = data!;

	const [_, dispatch] = useFolderContext();
	const [edgeStyle, setEdgeStyle] = useState({
		...style,
		...getInitialEdgeStyle(edgeSelected),
	});
	const [labelBgStyle, setLabelBgStyle] = useState(
		getInitialLabelBgStyle(edgeSelected)
	);
	const {edges, nodes} = useStoreState((state) => state);

	const sourceNode = useMemo(() => nodes.find((node) => node.id === source), [
		source,
		nodes,
	]);
	const targetNode = useMemo(() => nodes.find((node) => node.id === target), [
		target,
		nodes,
	]);

	useEffect(() => {
		if (edgeSelected) {
			setEdgeStyle((style) => {
				return {...style, stroke: HIGHLIGHT_COLOR};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: HIGHLIGHT_COLOR,
				};
			});
		}
		else {
			setEdgeStyle((style) => {
				return {...style, stroke: DEFAULT_COLOR};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: DEFAULT_COLOR,
				};
			});
		}
	}, [edgeSelected]);

	if (!sourceNode || !targetNode) {
		return null;
	}

	const {
		sourcePos,
		sourceX,
		sourceY,
		targetPos,
		targetX,
		targetY,
	} = getEdgeParams(
		sourceNode,
		currentSourceY as number,
		targetNode,
		currentTargetY as number
	);

	const edgePath = getSmoothStepPath({
		sourcePosition: sourcePos,
		sourceX,
		sourceY: sourceY + currentSourceY,
		targetPosition: targetPos,
		targetX,
		targetY: targetY + currentTargetY,
	});

	const reverseEdgePath = getSmoothStepPath({
		sourcePosition: targetPos,
		sourceX: targetX,
		sourceY: targetY + currentTargetY,
		targetPosition: sourcePos,
		targetX: sourceX,
		targetY: sourceY + currentSourceY,
	});

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY: sourceY + currentSourceY,
		targetX,
		targetY: targetY + currentTargetY,
	});

	return (
		<g className="react-flow__connection">
			<OneMarker />

			<ManyMarker />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={`url(#${markerEndId})`}
				style={edgeStyle}
			/>

			<path
				className="react-flow__edge-path"
				d={reverseEdgePath}
				id={id + 'reverse'}
				markerEnd={`url(#${markerStartId})`}
				style={edgeStyle}
			/>

			<EdgeText
				label={label}
				labelBgBorderRadius={4}
				labelBgPadding={[8, 5]}
				labelBgStyle={labelBgStyle}
				labelShowBg
				labelStyle={{
					fill: '#FFF',
					fontSize: '12px',
					fontWeight: 600,
				}}
				onClick={() => {
					dispatch({
						payload: {
							edges,
							nodes,
							selectedObjectRelationshipId: objectRelationshipId.toString(),
						},
						type: TYPES.SET_SELECTED_EDGE,
					});
				}}
				x={edgeCenterX}
				y={edgeCenterY}
			/>
		</g>
	);
}
