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
				nodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectFolderName: string;
			};
			type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				newObjectField: ObjectField;
				nodes: Node<ObjectDefinitionNodeData>[];
				objectDefinitionExternalReferenceCode: string;
			};
			type: TYPES.ADD_NEW_OBJECT_FIELD;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				hiddenObjectFolderObjectDefinitionNodes: boolean;
				leftSidebarItem: LeftSidebarItem;
				nodes: Node<ObjectDefinitionNodeData>[];
			};
			type: TYPES.BULK_CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				edges: Edge<ObjectRelationshipEdgeData>[];
				hiddenObjectDefinitionNode: boolean;
				nodes: Node<ObjectDefinitionNodeData>[];
				objectDefinitionId: number;
				objectDefinitionName: string;
				selectedSidebarItem: LeftSidebarItem;
			};
			type: TYPES.CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				objectFolders: ObjectFolder[];
				rightSidebarType?: RightSidebarType;
				selectedObjectFolder: ObjectFolder;
				selectedObjectRelationshipEdgeId?: number;
			};
			type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE;
	  }
	| {
			payload: {
				newElements: Elements<
					ObjectDefinitionNodeData | ObjectRelationshipEdgeData
				>;
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
				selectedFieldDefinitionName: string;
				selectedObjectDefinitionId: number;
			};
			type: TYPES.SET_SELECTED_FIELD;
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
	  }
	| {
			payload: {
				objectFolders: ObjectFolder[];
				selectedObjectFolder: ObjectFolder;
			};
			type: TYPES.ADD_NEW_OBJECT_RELATIONSHIP;
	  };
export declare type TState = {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	isLoadingObjectFolder: boolean;
	filterOperators: TFilterOperators;
	leftSidebarItems: LeftSidebarItem[];
	objectDefinitionPermissionsURL: string;
	objectDefinitions: ObjectDefinition[];
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderName: string;
	objectFolders: ObjectFolder[];
	rightSidebarType: RightSidebarType;
	selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData> | null;
	selectedObjectFolder: ObjectFolder;
	selectedObjectRelationship: ObjectRelationship;
	showChangesSaved: boolean;
	workflowStatusJSONArray: LabelValueObject[];
};
export interface LeftSidebarItem {
	hiddenObjectFolderObjectDefinitionNodes: boolean;
	id?: string;
	leftSidebarObjectDefinitionItems?: LeftSidebarObjectDefinitionItem[];
	name: string;
	objectFolderName: string;
	type: 'objectFolder' | 'objectDefinition';
}
export interface LeftSidebarObjectDefinitionItem {
	externalReferenceCode?: string;
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
	| 'objectFieldDetails'
	| 'objectDefinitionDetails'
	| 'objectRelationshipDetails';
export {};
