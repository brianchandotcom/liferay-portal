/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TYPES} from './ModelBuilderContext/typesEnum';

export type TAction = {
	payload: {
		objectFolders: ObjectFolder[];
	};
	type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
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

export interface FieldNode extends ObjectField {
	selected: boolean;
}

export type LeftSidebarItemType = {
	folderName: string;
	name: string;
	objectDefinitions?: {
		definitionName: string;
		name: string;
		type: 'objectDefinition';
	}[];
	type: 'objectFolder';
};

export type ObjectDefinitionNodeTypes = 'objectDefinition';

export interface ObjectFieldNode extends Partial<ObjectField> {
	primaryKey: boolean;
	selected: boolean;
}

export interface ObjectDefinitionNodeData
	extends Partial<Omit<ObjectDefinition, 'objectFields'>> {
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionUpdateResourcePermission: boolean;
	hasObjectDefinitionViewResourcePermission: boolean;
	isLinkedNode: boolean;
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
