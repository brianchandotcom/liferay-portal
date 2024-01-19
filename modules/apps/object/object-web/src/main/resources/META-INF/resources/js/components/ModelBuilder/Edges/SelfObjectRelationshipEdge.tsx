/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {EdgeProps, getEdgeCenter} from 'react-flow-renderer';

import {ObjectRelationshipEdgeData} from '../types';
import ObjectRelationshipEdge from './ObjectRelationshipEdge';

export default function SelfObjectRelationshipEdge({
	data,
	id: edgeId,
	sourceX,
	sourceY,
	targetX,
	targetY,
}: EdgeProps<ObjectRelationshipEdgeData[]>) {
	const hasManyObjectRelationships = data && data.length > 1;

	const radiusX = (sourceX - targetX) * 0.6;
	const radiusY = 150;

	const edgePath = `M ${
		sourceX - (hasManyObjectRelationships ? 11 : 17)
	} ${sourceY} A ${radiusX} ${radiusY} 0 1 0 ${
		targetX + (hasManyObjectRelationships ? 5 : 11)
	} ${targetY}`;

	const reverseEdgePath = `M ${
		targetX + (hasManyObjectRelationships ? 5 : 11)
	} ${targetY} A ${radiusX} ${radiusY} 0 1 1 ${
		sourceX - (hasManyObjectRelationships ? 11 : 17)
	} ${sourceY}`;

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY: sourceY - (hasManyObjectRelationships ? 0 : 40),
		targetX,
		targetY,
	});

	const INCREMENT_EDGE_CENTER_Y = 230;

	return (
		<ObjectRelationshipEdge
			data={data}
			edgeCenterX={edgeCenterX}
			edgeCenterY={edgeCenterY + INCREMENT_EDGE_CENTER_Y}
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
