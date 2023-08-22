/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Edge} from 'react-flow-renderer';
import {ObjectRelationshipEdgeData} from '../types';
export declare function fieldsCustomSort(
	objectFields: ObjectFieldNode[]
): ObjectFieldNode[];
export declare function incrementEdgesYPosition(
	edges: Edge<ObjectRelationshipEdgeData>[],
	initialYPosition: number,
	yIncrement: number
): Edge<ObjectRelationshipEdgeData>[];
export declare function getNonOverlappingEdges(
	allEdges: Edge<ObjectRelationshipEdgeData>[]
): Edge<ObjectRelationshipEdgeData>[];
