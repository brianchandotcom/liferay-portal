/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Edge, Elements, Node} from 'react-flow-renderer';
import {TYPES} from './ModelBuilderContext/typesEnum';
export declare type TAction =
	| {
			payload: {
				objectFolders: ObjectFolder[];
			};
			type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				nodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectDefinitionName: string;
			};
			type: TYPES.SET_SELECTED_NODE;
	  }
	| {
			payload: {
				newElements: any;
			};
			type: TYPES.SET_ELEMENTS;
	  };
export declare type TState = {
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	leftSidebarItems: LeftSidebarItemType[];
	objectDefinitions: ObjectDefinition[];
	objectFolders: ObjectFolder[];
	rightSidebarType: RightSidebarType;
	selectedDefinitionNode: Node<ObjectDefinitionNodeData>;
	selectedFolderERC: string;
	selectedObjectRelationship: ObjectRelationship;
};
export declare type LeftSidebarItemType = {
	folderName: string;
	name: string;
	objectDefinitions?: LeftSidebarDefinitionItemType[];
	type: 'objectFolder' | 'objectDefinition';
};
export declare type LeftSidebarDefinitionItemType = {
	definitionName: string;
	name: string;
	selected: boolean;
	type: 'objectDefinition';
};
export declare type ObjectDefinitionNodeTypes = 'objectDefinition';
export interface ObjectFieldNode extends Partial<ObjectField> {
	primaryKey: boolean;
	required: boolean;
	selected: boolean;
}
export interface ObjectDefinitionNodeData
	extends Partial<Omit<ObjectDefinition, 'objectFields' | 'label'>> {
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionUpdateResourcePermission: boolean;
	hasObjectDefinitionViewResourcePermission: boolean;
	isLinkedNode: boolean;
	label: string;
	nodeSelected: boolean;
	objectFields: ObjectFieldNode[];
}
export interface ObjectRelationshipEdgeData {
	label: string;
	markerEndId: string;
	markerStartId: string;
	sourceY: number;
	targetY: number;
	type: string;
}
export declare type nonRelationshipObjectFieldsInfo = {
	label: LocalizedValue<string>;
	name: string;
};
export declare type RightSidebarType =
	| 'empty'
	| 'objectDefinitionDetails'
	| 'objectRelationshipDetails';
