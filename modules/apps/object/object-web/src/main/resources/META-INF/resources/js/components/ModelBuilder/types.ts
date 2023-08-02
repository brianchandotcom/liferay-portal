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
	rightSidebarType:
		| 'objectDefinitionDetails'
		| 'objectRelationshipDetails'
		| 'empty';
	selectedDefinitionNode: ObjectDefinitionNode;
	selectedObjectRelationship: ObjectRelationship;
	objectFolders: ObjectFolder[];
	selectedFolderERC: string;
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

export type ObjectFieldNode = {
	businessType: ObjectFieldBusinessType;
	externalReferenceCode: string;
	label: string;
	name: string;
	primaryKey: boolean;
	required: boolean;
	selected: boolean;
};

export type ObjectDefinitionNode = {
	data: {
		creationLanguageId: Liferay.Language.Locale;
		hasDeleteResourcePermission: boolean;
		hasManagePermissionsResourcePermission: boolean;
		hasObjectDefinitionPublished: boolean;
		isLinkedNode: boolean;
		nodeSelected: boolean;
		objectDefinitionLabel: string;
		objectDefinitionName: string;
		objectFields: ObjectFieldNode[];
		system: boolean;
	};
	id: string;
	position: {
		x: number;
		y: number;
	};
	type: ObjectDefinitionNodeTypes;
};
