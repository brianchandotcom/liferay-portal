/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TYPES} from './ModelBuilderContext/typesEnum';

export type TAction =
	| {
			payload: {
				objectFolders: ObjectFolder[];
			};
			type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
	  }
	| {
			payload: {
				selectedObjectDefinitionName: string;
			};
			type: TYPES.SET_SELECTED_NODE;
	  };

export type TState = {
	leftSidebarItems: LeftSidebarItemType[];
	objectDefinitionNodes: ObjectDefinitionNode[];
	objectDefinitions: ObjectDefinition[];
	objectFolders: ObjectFolder[];
	rightSidebarType:
		| 'objectDefinitionDetails'
		| 'objectRelationshipDetails'
		| 'empty';
	selectedDefinitionNode: ObjectDefinitionNode;
	selectedFolderERC: string;
	selectedObjectRelationship: ObjectRelationship;
};

export type LeftSidebarItemType = {
	folderName: string;
	name: string;
	objectDefinitions?: LeftSidebarDefinitionItemType[];
	type: 'objectFolder' | 'objectDefinition';
};

export type LeftSidebarDefinitionItemType = {
	definitionName: string;
	name: string;
	selected: boolean;
	type: 'objectDefinition';
};

export type ObjectDefinitionNodeTypes = 'objectDefinition';

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

export type ObjectDefinitionNode = {
	data: ObjectDefinitionNodeData;
	id: string;
	position: {
		x: number;
		y: number;
	};
	type: ObjectDefinitionNodeTypes;
};
