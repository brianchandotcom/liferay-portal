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

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import {getEdgeParams} from '../utils';
import ManyMarker from './ManyMarker';
import OneMarker from './OneMarker';

const DEFAULT_COLOR = '#80ACFF';
const HIGHLIGHT_COLOR = '#0B5FFF';

export function getInitialObjectRelationshipEdgeStyle(edgeSelected: boolean) {
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

export default function DefaultObjectRelationshipEdge({
	data,
	id,
	source,
	style = {},
	target,
}: EdgeProps<ObjectRelationshipEdgeData>) {
	const {
		label,
		markerEndId,
		markerStartId,
		objectRelationshipId,
		selected,
	} = data!;

	const [_, dispatch] = useObjectFolderContext();
	const [
		objectRelationshipEdgeStyle,
		setObjectRelationshipEdgeStyle,
	] = useState({
		...style,
		...getInitialObjectRelationshipEdgeStyle(selected),
	});
	const [labelBgStyle, setLabelBgStyle] = useState(
		getInitialLabelBgStyle(selected)
	);
	const {nodes} = useStoreState((state) => state);

	const sourceNode = useMemo(() => nodes.find((node) => node.id === source), [
		source,
		nodes,
	]);
	const targetNode = useMemo(() => nodes.find((node) => node.id === target), [
		target,
		nodes,
	]);

	useEffect(() => {
		if (selected) {
			setObjectRelationshipEdgeStyle((style) => {
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
			setObjectRelationshipEdgeStyle((style) => {
				return {...style, stroke: DEFAULT_COLOR};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: DEFAULT_COLOR,
				};
			});
		}
	}, [selected]);

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
	} = getEdgeParams(sourceNode, targetNode);

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
		<g className="react-flow__connection">
			<OneMarker />

			<ManyMarker />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={`url(#${markerEndId})`}
				style={objectRelationshipEdgeStyle}
			/>

			<path
				className="react-flow__edge-path"
				d={reverseEdgePath}
				id={id + 'reverse'}
				markerEnd={`url(#${markerStartId})`}
				style={objectRelationshipEdgeStyle}
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
							selectedObjectRelationshipId: objectRelationshipId,
						},
						type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
					});
				}}
				x={edgeCenterX}
				y={edgeCenterY}
			/>
		</g>
	);
}
