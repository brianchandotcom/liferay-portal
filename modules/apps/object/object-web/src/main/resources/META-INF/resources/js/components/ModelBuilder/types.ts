/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Edge, Elements, Node} from 'react-flow-renderer';

import {TYPES} from './ModelBuilderContext/typesEnum';

declare type TDropDownType =
	| 'checkbox'
	| 'contextual'
	| 'group'
	| 'item'
	| 'radio'
	| 'radiogroup'
	| 'divider';

export type DropDownItems = {
	active?: boolean;
	checked?: boolean;
	disabled?: boolean;
	href?: string;
	items?: Array<IItem>;
	label?: string;
	name?: string;
	onChange?: Function;
	onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
	symbolLeft?: string;
	symbolRight?: string;
	type?: TDropDownType;
	value?: string;
};

export type TAction =
	| {
			payload: {
				newObjectDefinition: ObjectDefinition;
				selectedFolderName: string;
			};
			type: TYPES.ADD_NEW_NODE_TO_FOLDER;
	  }
	| {
			payload: {
				hiddenFolderNodes: boolean;
				leftSidebarItem: LeftSidebarItemType;
			};
			type: TYPES.BULK_CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				definitionId: number;
				definitionName: string;
				hiddenNode: boolean;
				leftSidebarItem: LeftSidebarItemType;
			};
			type: TYPES.CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				objectFolders: ObjectFolder[];
			};
			type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
	  }
	| {
			payload: {
				currentFolderName: string;
				deletedNodeName: string;
			};
			type: TYPES.DELETE_FOLDER_NODE;
	  }
	| {
			payload: {
				newElements: any;
			};
			type: TYPES.SET_ELEMENTS;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				nodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectDefinitionId: string;
			};
			type: TYPES.SET_SELECTED_NODE;
	  }
	| {
			payload: {
				currentFolderName: string;
				updatedNode: Partial<ObjectDefinition>;
			};
			type: TYPES.UPDATE_FOLDER_NODE;
	  };

export type TState = {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	leftSidebarItems: LeftSidebarItemType[];
	objectDefinitionPermissionsURL: string;
	objectDefinitions: ObjectDefinition[];
	objectFolders: ObjectFolder[];
	rightSidebarType: RightSidebarType;
	selectedDefinitionNode: Node<ObjectDefinitionNodeData>;
	selectedFolderERC: string;
	selectedObjectRelationship: ObjectRelationship;
	showChangesSaved: boolean;
	storages: LabelValueObject[];
	viewApiURL: string;
};

export type LeftSidebarItemType = {
	folderName: string;
	hiddenFolderNodes: boolean;
	name: string;
	objectDefinitions?: LeftSidebarDefinitionItemType[];
	type: 'objectFolder' | 'objectDefinition';
};

export type LeftSidebarDefinitionItemType = {
	definitionId: number;
	definitionName: string;
	hiddenNode: boolean;
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
	defaultLanguageId: Liferay.Language.Locale;
	editObjectDefinitionURL: string;
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionUpdateResourcePermission: boolean;
	hasObjectDefinitionViewResourcePermission: boolean;
	id: number;
	isLinkedNode: boolean;
	label: string;
	name: string;
	nodeSelected: boolean;
	objectDefinitionPermissionsURL: string;
	objectFields: ObjectFieldNode[];
	objectRelationships: ObjectRelationship[];
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	system: boolean;
}

export interface ObjectRelationshipEdgeData {
	label: string;
	markerEndId: string;
	markerStartId: string;
	sourceY: number;
	targetY: number;
	type: string;
}

export type nonRelationshipObjectFieldsInfo = {
	label: LocalizedValue<string>;
	name: string;
};

export type RightSidebarType =
	| 'empty'
	| 'objectDefinitionDetails'
	| 'objectRelationshipDetails';
