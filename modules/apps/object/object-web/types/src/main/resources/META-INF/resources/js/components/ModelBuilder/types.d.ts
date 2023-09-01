/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

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
export declare type DropDownItems = {
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
export declare type TAction =
	| {
			payload: {
				newObjectDefinition: ObjectDefinition;
				selectedObjectFolderName: string;
			};
			type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER;
	  }
	| {
			payload: {
				hiddenObjectFolderObjectDefinitionNodes: boolean;
				leftSidebarItem: LeftSidebarItem;
			};
			type: TYPES.BULK_CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				hiddenObjectDefinitionNode: boolean;
				objectDefinitionId: number;
				objectDefinitionName: string;
				selectedSidebarItem: LeftSidebarItem;
			};
			type: TYPES.CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				objectFolders: ObjectFolder[];
				selectedObjectFolder: ObjectFolder;
			};
			type: TYPES.CREATE_MODEL_BUILDER_STRUCTURE;
	  }
	| {
			payload: {
				currentObjectFolderName: string;
				deletedObjectDefinitionName: string;
			};
			type: TYPES.DELETE_OBJECT_DEFINITION;
	  }
	| {
			payload: {
				newElements: any;
			};
			type: TYPES.SET_ELEMENTS;
	  }
	| {
			payload: {
				isLoadingObjectFolder: boolean;
			};
			type: TYPES.SET_LOADING_OBJECT_FOLDER;
	  }
	| {
			payload: {
				objectFolderName: string;
			};
			type: TYPES.SET_OBJECT_FOLDER_NAME;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				nodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectDefinitionId: string;
			};
			type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				nodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectRelationshipId: string;
			};
			type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE;
	  }
	| {
			payload: {
				updatedShowChangesSaved: boolean;
			};
			type: TYPES.SET_SHOW_CHANGES_SAVED;
	  }
	| {
			payload: {
				currentObjectFolderName: string;
				updatedObjectDefinitionNode: Partial<ObjectDefinition>;
			};
			type: TYPES.UPDATE_OBJECT_DEFINITION_NODE;
	  };
export declare type TState = {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	isLoadingObjectFolder: boolean;
	leftSidebarItems: LeftSidebarItem[];
	objectDefinitionPermissionsURL: string;
	objectDefinitions: ObjectDefinition[];
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderName: string;
	objectFolders: ObjectFolder[];
	rightSidebarType: RightSidebarType;
	selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData>;
	selectedObjectFolder: ObjectFolder;
	selectedObjectRelationship: ObjectRelationship;
	showChangesSaved: boolean;
};
export interface LeftSidebarItem {
	hiddenObjectFolderObjectDefinitionNodes: boolean;
	leftSidebarObjectDefinitionItems?: LeftSidebarObjectDefinitionItem[];
	name: string;
	objectFolderName: string;
	type: 'objectFolder' | 'objectDefinition';
}
export interface LeftSidebarObjectDefinitionItem {
	hiddenObjectDefinitionNode: boolean;
	id: number;
	label: string;
	linked?: boolean;
	name: string;
	selected: boolean;
	type: 'linkedObjectDefinition' | 'objectDefinition';
}
export interface ObjectRelationshipEdgeData {
	defaultLanguageId?: Liferay.Language.Locale;
	label: string;
	markerEndId: string;
	markerStartId: string;
	objectRelationshipId: number;
	selected: boolean;
	selfObjectRelationships?: ObjectRelationship[];
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
export {};
