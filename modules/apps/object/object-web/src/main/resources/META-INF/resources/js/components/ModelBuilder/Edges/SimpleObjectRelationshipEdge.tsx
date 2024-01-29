/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {EdgeText} from 'react-flow-renderer';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import ManyMarker from './ManyMarker';
import {BaseObjectRepationShipEdgeProps} from './ObjectRelationshipEdge';
import OneMarker from './OneMarker';

import './Edge.scss';

interface SimpleObjectRelationshipEdgeProps
	extends BaseObjectRepationShipEdgeProps {
	id: number;
	label: string;
	markerEndId: string;
	markerStartId: string;
	reverseEdgePath?: string;
}

export function SimpleObjectRelationshipEdge({
	edgeCenterX,
	edgeCenterY,
	edgeId,
	edgePath,
	id,
	label,
	labelBgStyle,
	labelStyle,
	markerEndId,
	markerStartId,
	objectRelationshipEdgeStyle,
	reverseEdgePath,
}: SimpleObjectRelationshipEdgeProps) {
	const [_, dispatch] = useObjectFolderContext();

	return (
		<g className="react-flow__connection">
			<OneMarker objectRelationshipId={id.toString()} />

			<ManyMarker objectRelationshipId={id.toString()} />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={edgeId}
				markerEnd={`url(#${markerEndId})`}
				style={objectRelationshipEdgeStyle}
			/>

			<path
				className="react-flow__edge-path"
				d={reverseEdgePath}
				id={edgeId + 'reverse'}
				markerEnd={`url(#${markerStartId})`}
				style={objectRelationshipEdgeStyle}
			/>

			<EdgeText
				label={label}
				labelBgBorderRadius={4}
				labelBgPadding={[8, 5]}
				labelBgStyle={labelBgStyle}
				labelShowBg
				labelStyle={labelStyle}
				onClick={() => {
					dispatch({
						payload: {
							selectedObjectRelationshipId: id,
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
