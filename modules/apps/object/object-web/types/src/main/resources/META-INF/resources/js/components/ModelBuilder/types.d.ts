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
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				selectedObjectFolderName: string;
			};
			type: TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER;
	  }
	| {
			payload: {
				newObjectField: ObjectField;
				objectDefinitionExternalReferenceCode: string;
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData>;
			};
			type: TYPES.ADD_OBJECT_FIELD;
	  }
	| {
			payload: {
				hiddenObjectFolderObjectDefinitionNodes: boolean;
				leftSidebarItem: LeftSidebarItem;
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
			};
			type: TYPES.BULK_CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				hiddenObjectDefinitionNode: boolean;
				objectDefinitionId: number;
				objectDefinitionName: string;
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedSidebarItem: LeftSidebarItem;
			};
			type: TYPES.CHANGE_NODE_VIEW;
	  }
	| {
			payload: {
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectDefinitionNode: Node<
					ObjectDefinitionNodeData
				> | null;
				selectedObjectField: ObjectFieldNodeRow;
			};
			type: TYPES.DELETE_OBJECT_FIELD;
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
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectDefinitionId: string;
			};
			type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE;
	  }
	| {
			payload: {
				newObjectDefinitionNodePosition: {
					x: number;
					y: number;
				};
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				updatedObjectDefinitionNodeId: number;
			};
			type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE_POSITION;
	  }
	| {
			payload: {
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectDefinitionId: number;
				selectedObjectField: ObjectFieldNodeRow;
				selectedObjectFieldName: string;
			};
			type: TYPES.SET_SELECTED_OBJECT_FIELD;
	  }
	| {
			payload: {
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectRelationshipId: number;
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
				updatedShowSidebars: boolean;
			};
			type: TYPES.SET_SHOW_SIDEBARS;
	  }
	| {
			payload: {
				currentObjectFolderName: string;
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectDefinitionRelationshipEdges: Edge<
					ObjectRelationshipEdgeData
				>[];
				updatedObjectDefinition: Partial<ObjectDefinition>;
			};
			type: TYPES.UPDATE_OBJECT_DEFINITION_NODE;
	  }
	| {
			payload: {
				objectDefinitionNodes: Node<ObjectDefinitionNodeData>[];
				objectRelationshipEdges: Edge<ObjectRelationshipEdgeData>[];
				selectedObjectDefinitionNode: Node<
					ObjectDefinitionNodeData
				> | null;
				updatedObjectField: ObjectField;
			};
			type: TYPES.UPDATE_OBJECT_FIELD_NODE_ROW;
	  };
export declare type TState = {
	baseResourceURL: string;
	editObjectDefinitionURL: string;
	elements: Elements<ObjectDefinitionNodeData | ObjectRelationshipEdgeData>;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isLoadingObjectFolder: boolean;
	leftSidebarItems: LeftSidebarItem[];
	objectDefinitionPermissionsURL: string;
	objectDefinitions: ObjectDefinition[];
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderName: string;
	objectFolders: ObjectFolder[];
	objectWebLearnResources: ObjectWebLearnResources;
	rightSidebarType: RightSidebarType;
	selectedObjectDefinitionNode: Node<ObjectDefinitionNodeData> | null;
	selectedObjectField?: ObjectFieldNodeRow;
	selectedObjectFolder: ObjectFolder;
	selectedObjectRelationship?: Edge<ObjectRelationshipEdgeData>;
	showChangesSaved: boolean;
	showSidebars: boolean;
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
