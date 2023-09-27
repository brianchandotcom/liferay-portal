/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {EdgeProps} from 'react-flow-renderer';
import {ObjectRelationshipEdgeData} from '../types';
export declare function getInitialObjectRelationshipEdgeStyle(
	edgeSelected: boolean
): {
	stroke: string;
	strokeWidth: string;
};
export declare function getInitialLabelBgStyle(
	edgeSelected: boolean
): {
	fill: string;
	height: string;
};
export default function DefaultObjectRelationshipEdge({
	data,
	id,
	source,
	style,
	target,
}: EdgeProps<ObjectRelationshipEdgeData>): JSX.Element | null;
