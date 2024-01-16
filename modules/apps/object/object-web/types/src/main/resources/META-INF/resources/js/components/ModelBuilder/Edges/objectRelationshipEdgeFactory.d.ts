/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectRelationshipEdgeData} from '../types';
import {ObjectRelationshipMap} from './ObjectRelationshipMap';
interface ObjectRelationshipEdgeFactory {
	objectDefinition: ObjectDefinitionNodeData;
	objectRelationship: ObjectRelationship;
	objectRelationshipMap: ObjectRelationshipMap;
	selectedObjectRelationshipId: number | undefined;
}
export declare function objectRelationshipEdgeFactory({
	objectDefinition,
	objectRelationship,
	objectRelationshipMap,
	selectedObjectRelationshipId,
}: ObjectRelationshipEdgeFactory):
	| {
			data: ObjectRelationshipEdgeData[];
			id: string;
			source: string;
			sourceHandle: string | null;
			target: string;
			targetHandle: string | null;
			type: string;
	  }
	| undefined;
export {};
