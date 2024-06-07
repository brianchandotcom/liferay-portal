/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';
import {EdgeProps} from 'react-flow-renderer';

import {ObjectRelationshipEdgeData} from '../types';
import {ManyObjectRelationshipEdge} from './ManyObjectRelationshipEdge';
import {SimpleObjectRelationshipEdge} from './SimpleObjectRelationshipEdge';

const labelStyle = {
	cursor: 'pointer',
	fill: '#FFF',
	fontSize: '12px',
	fontWeight: 600,
};

const DEFAULT_COLOR = '#80ACFF';
const HIGHLIGHT_COLOR = '#0B5FFF';

function getInitialObjectRelationshipEdgeStyle(edgeSelected: boolean) {
	return {
		stroke: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		strokeWidth: '2px',
	};
}

function getInitialLabelBgStyle(edgeSelected: boolean) {
	return {
		fill: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		height: '24px',
	};
}

interface ObjectRelationshipEdge
	extends Partial<EdgeProps<ObjectRelationshipEdgeData[]>> {
	edgeCenterX: number;
	edgeCenterY: number;
	edgePath: string;
	reverseEdgePath?: string;
}

export interface BaseObjectRepationShipEdgeProps {
	edgeCenterX: number;
	edgeCenterY: number;
	edgeId?: string;
	edgePath: string;
	labelBgStyle: React.CSSProperties;
	labelStyle: React.CSSProperties;
	objectRelationshipEdgeStyle: React.CSSProperties;
}

export default function ObjectRelationshipEdge({
	edgePath,
	edgeCenterX,
	edgeCenterY,
	data,
	id: edgeId,
	reverseEdgePath,
	style = {},
}: ObjectRelationshipEdge) {
	const [{id, label, markerEndId, markerStartId, selected}] = data!;

	const [activePopover, setActivePopover] = useState(false);
	const [objectRelationshipEdgeStyle, setObjectRelationshipEdgeStyle] =
		useState<React.CSSProperties>({
			...style,
			...getInitialObjectRelationshipEdgeStyle(selected),
		});
	const [labelBgStyle, setLabelBgStyle] = useState(
		getInitialLabelBgStyle(selected)
	);

	const hasManyObjectRelationships = data && data.length > 1;

	useEffect(() => {
		if (activePopover || selected) {
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
	}, [activePopover, selected]);

	return hasManyObjectRelationships ? (
		<ManyObjectRelationshipEdge
			activePopover={activePopover}
			data={data}
			edgeCenterX={edgeCenterX}
			edgeCenterY={edgeCenterY}
			edgeId={edgeId}
			edgePath={edgePath}
			labelBgStyle={labelBgStyle}
			labelStyle={labelStyle}
			objectRelationshipEdgeStyle={objectRelationshipEdgeStyle}
			setActivePopover={setActivePopover}
		/>
	) : (
		<SimpleObjectRelationshipEdge
			edgeCenterX={edgeCenterX}
			edgeCenterY={edgeCenterY}
			edgeId={edgeId}
			edgePath={edgePath}
			id={id}
			label={label}
			labelBgStyle={labelBgStyle}
			labelStyle={labelStyle}
			markerEndId={markerEndId}
			markerStartId={markerStartId}
			objectRelationshipEdgeStyle={objectRelationshipEdgeStyle}
			reverseEdgePath={reverseEdgePath}
		/>
	);
}
