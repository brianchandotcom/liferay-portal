/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {BaseObjectRepationShipEdgeProps} from './ObjectRelationshipEdge';
import './Edge.scss';
interface SimpleObjectRelationshipEdgeProps
	extends BaseObjectRepationShipEdgeProps {
	id: number;
	label: string;
	markerEndId: string;
	markerStartId: string;
	reverseEdgePath?: string;
}
export declare function SimpleObjectRelationshipEdge({
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
}: SimpleObjectRelationshipEdgeProps): JSX.Element;
export {};
