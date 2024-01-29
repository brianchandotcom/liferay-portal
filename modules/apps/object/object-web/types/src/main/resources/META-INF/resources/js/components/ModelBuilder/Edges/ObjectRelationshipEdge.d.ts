/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {EdgeProps} from 'react-flow-renderer';
import {ObjectRelationshipEdgeData} from '../types';
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
	style,
}: ObjectRelationshipEdge): JSX.Element;
export {};
