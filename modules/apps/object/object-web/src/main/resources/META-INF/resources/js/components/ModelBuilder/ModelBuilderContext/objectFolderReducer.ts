/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {Edge, Node} from 'react-flow-renderer';

import {defaultLanguageId} from '../../../utils/constants';
import {manyMarkerId} from '../Edges/ManyMarkerEnd';
import {oneMarkerId} from '../Edges/OneMarkerEnd';
import {
	LeftSidebarItemType,
	ObjectDefinitionNodeData,
	ObjectFieldNode,
	ObjectRelationshipEdgeData,
	RightSidebarType,
	TAction,
	TState,
} from '../types';
import {
	fieldsCustomSort,
	getNonOverlappingEdges,
} from './objectFolderReducerUtil';
import {TYPES} from './typesEnum';

export function objectFolderReducer(state: TState, action: TAction) {
	switch (action.type) {
		case TYPES.CREATE_MODEL_BUILDER_STRUCTURE: {
			const {objectFolders} = action.payload;
			const {selectedFolderERC} = state;

			const newLeftSidebar = objectFolders.map((folder) => {
				const folderDefinitions = folder.definitions?.map(
					(definition) => {
						return {
							definitionName: definition.name,
							name: getLocalizableLabel(
								definition.defaultLanguageId,
								definition.label,
								definition.name
							),
							selected: false,
							type: 'objectDefinition',
						};
					}
				);

				return {
					folderName: folder.name,
					name: getLocalizableLabel(
						defaultLanguageId,
						folder.label,
						folder.name
					),
					objectDefinitions: folderDefinitions,
					type: 'objectFolder',
				} as LeftSidebarItemType;
			});

			const currentFolder = objectFolders.find(
				(folder) => folder.externalReferenceCode === selectedFolderERC
			);

			let newObjectDefinitionNodes: Node<ObjectDefinitionNodeData>[] = [];
			const allEdges: Edge<ObjectRelationshipEdgeData>[] = [];

			if (currentFolder) {
				const positionColumn = {x: 1, y: 0};

				newObjectDefinitionNodes = currentFolder.definitions!.map(
					(objectDefinition, index) => {
						const objectFields = objectDefinition.objectFields.map(
							(field) => {
								return {
									businessType: field.businessType,
									externalReferenceCode:
										field.externalReferenceCode,
									label: getLocalizableLabel(
										objectDefinition.defaultLanguageId,
										field.label,
										field.name
									),
									name: field.name,
									primaryKey: field.name === 'id',
									required: field.required,
									selected: false,
								} as ObjectFieldNode;
							}
						);

						if (objectDefinition.objectRelationships.length) {
							objectDefinition.objectRelationships.forEach(
								(relationship) => {
									if (!relationship.reverse) {
										allEdges.push({
											data: {
												label: getLocalizableLabel(
													objectDefinition.defaultLanguageId,
													relationship.label,
													relationship.name
												),
												markerEndId: manyMarkerId,
												markerStartId:
													relationship.type ===
													'manyToMany'
														? manyMarkerId
														: oneMarkerId,
												sourceY: 0,
												targetY: 0,
												type: relationship.type,
											},
											id: `reactflow__edge-object-relationship-${relationship.name}-parent-${relationship.objectDefinitionExternalReferenceCode1}-child-${relationship.objectDefinitionExternalReferenceCode2}`,
											source: `${objectDefinition.name}`,
											sourceHandle: `${objectDefinition.name}`,
											target: `${relationship.objectDefinitionName2}`,
											targetHandle: `${relationship.objectDefinitionName2}`,
											type: 'floating',
										});
									}
								}
							);
						}

						if (index % 4 === 0) {
							positionColumn.y++;
							positionColumn.x = 1;
						}

						positionColumn.x++;

						return {
							data: {
								defaultLanguageId:
									objectDefinition.defaultLanguageId,
								externalReferenceCode:
									objectDefinition.externalReferenceCode,
								hasObjectDefinitionDeleteResourcePermission: true,
								hasObjectDefinitionManagePermissionsResourcePermission: true,
								hasObjectDefinitionUpdateResourcePermission: true,
								hasObjectDefinitionViewResourcePermission: true,
								isLinkedNode: false,
								label: getLocalizableLabel(
									objectDefinition.defaultLanguageId,
									objectDefinition.label,
									objectDefinition.name
								),
								name: objectDefinition.name,
								nodeSelected: false,
								objectFields: fieldsCustomSort(objectFields),
								status: objectDefinition.status,
								system: objectDefinition.system,
							},
							id: objectDefinition.name,
							position: {
								x: positionColumn.x * 300,
								y: positionColumn.y * 400,
							},
							type: 'objectDefinition',
						} as Node<ObjectDefinitionNodeData>;
					}
				);
			}

			const newEdges = getNonOverlappingEdges(allEdges);

			return {
				...state,
				elements: [...newObjectDefinitionNodes, ...newEdges],
				leftSidebarItems: newLeftSidebar,
			};
		}
		case TYPES.SET_SELECTED_NODE: {
			const {edges, nodes, selectedObjectDefinitionName} = action.payload;

			const {leftSidebarItems} = state;

			const newObjectDefinitionNodes = nodes.map((definitionNode) => ({
				...definitionNode,
				data: {
					...definitionNode.data,
					nodeSelected:
						definitionNode.data?.name ===
						selectedObjectDefinitionName,
				},
			}));

			const newLeftSidebarItems = leftSidebarItems.map((sidebarItem) => {
				const newLeftSidebarDefinitions = sidebarItem.objectDefinitions?.map(
					(sidebarDefinition) => ({
						...sidebarDefinition,
						selected:
							selectedObjectDefinitionName ===
							sidebarDefinition.definitionName,
					})
				);

				return {
					...sidebarItem,
					objectDefinitions: newLeftSidebarDefinitions,
				};
			});

			return {
				...state,
				elements: [...edges, ...newObjectDefinitionNodes],
				leftSidebarItems: newLeftSidebarItems,
				rightSidebarType: 'objectDefinitionDetails' as RightSidebarType,
			};
		}
		case TYPES.SET_ELEMENTS: {
			const {newElements} = action.payload;

			return {
				...state,
				elements: newElements,
			};
		}
		default:
			return state;
	}
}
