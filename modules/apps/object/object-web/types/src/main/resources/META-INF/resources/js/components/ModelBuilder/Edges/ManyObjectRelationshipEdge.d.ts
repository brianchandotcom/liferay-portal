/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ObjectRelationshipEdgeData} from '../types';
import {BaseObjectRepationShipEdgeProps} from './ObjectRelationshipEdge';
import './Edge.scss';
interface ManyObjectRelationshipEdgeProps
	extends BaseObjectRepationShipEdgeProps {
	activePopover: boolean;
	data: ObjectRelationshipEdgeData[];
	labelBgStyle: React.CSSProperties;
	labelStyle: React.CSSProperties;
	objectRelationshipEdgeStyle: React.CSSProperties;
	setActivePopover: (value: boolean) => void;
}
export declare function ManyObjectRelationshipEdge({
	activePopover,
	data,
	edgeCenterX,
	edgeCenterY,
	edgeId,
	edgePath,
	labelBgStyle,
	labelStyle,
	objectRelationshipEdgeStyle,
	setActivePopover,
}: ManyObjectRelationshipEdgeProps): JSX.Element;
export {};
