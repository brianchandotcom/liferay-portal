/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {Edge, Node, useStore} from 'react-flow-renderer';

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

export function ObjectFolderReducer(state: TState, action: TAction) {
	const store = useStore();

	switch (action.type) {
		case TYPES.ADD_NEW_NODE_TO_FOLDER: {
			const {newObjectDefinition, selectedFolderName} = action.payload;
			const {nodes} = store.getState();
			const {
				editObjectDefinitionURL,
				elements,
				leftSidebarItems,
				objectDefinitionPermissionsURL,
			} = state;
			let newPosition = {
				x: 2 * 300,
				y: 2 * 400,
			};
			if (nodes.length) {
				const yPositions = nodes.map((node) => node.position.y);
				const maximumY = Math.max(...yPositions);
				const maximumNodesYPosition = nodes.filter(
					(node) => node.position.y === maximumY
				);
				const xPositions = maximumNodesYPosition.map(
					(node) => node.position.x
				);
				const maximumX = Math.max(...xPositions);
				const mostBottomRightNodePosition = maximumNodesYPosition.find(
					(node) => node.position.x === maximumX
				)!.position;
				newPosition = {
					x: mostBottomRightNodePosition!.x + 300,
					y: mostBottomRightNodePosition!.y,
				};
			}
			const newLeftSidebarItems = leftSidebarItems.map((item) => {
				let newDefinition;

				if (item.folderName === selectedFolderName) {
					newDefinition = {
						definitionId: newObjectDefinition.id,
						definitionName: newObjectDefinition.name,
						name: getLocalizableLabel(
							newObjectDefinition.defaultLanguageId,
							newObjectDefinition.label,
							newObjectDefinition.name
						),
						selected: true,
						type: 'objectDefinition',
					};
					const updatedObjectDefinitions = item.objectDefinitions?.map(
						(objectDefinition) => {
							return {
								...objectDefinition,
								selected: false,
							};
						}
					);

					return {
						...item,
						objectDefinitions: [
							...updatedObjectDefinitions!,
							newDefinition,
						],
					};
				}
				else {
					return {
						...item,
					};
				}
			}) as LeftSidebarItemType[];
			const objectFields = newObjectDefinition.objectFields.map(
				(field) => {
					return {
						businessType: field.businessType,
						externalReferenceCode: field.externalReferenceCode,
						label: getLocalizableLabel(
							newObjectDefinition.defaultLanguageId,
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
			const updatedObjectDefinitionsNodes = elements.map((node) => {
				return {
					...node,
					data: {
						...node.data,
						nodeSelected: false,
					},
				};
			});
			const newNode = {
				data: {
					defaultLanguageId: newObjectDefinition.defaultLanguageId,
					editObjectDefinitionURL,
					externalReferenceCode:
						newObjectDefinition.externalReferenceCode,
					hasObjectDefinitionDeleteResourcePermission: !!newObjectDefinition
						.actions.delete,
					hasObjectDefinitionManagePermissionsResourcePermission: !!newObjectDefinition
						.actions.permissions,
					id: newObjectDefinition.id,
					isLinkedNode: false,
					label: getLocalizableLabel(
						newObjectDefinition.defaultLanguageId!,
						newObjectDefinition.label,
						newObjectDefinition.name
					),
					name: newObjectDefinition.name,
					nodeSelected: true,
					objectDefinitionPermissionsURL,
					objectFields: fieldsCustomSort(objectFields),
					status: newObjectDefinition.status,
					system: newObjectDefinition.system,
				},
				id: newObjectDefinition.id.toString(),
				position: newPosition,
				type: 'objectDefinition',
			} as Node<ObjectDefinitionNodeData>;
			const newObjectDefinitionNodes = [
				...updatedObjectDefinitionsNodes,
				newNode,
			] as Node<ObjectDefinitionNodeData>[];

			return {
				...state,
				elements: [...newObjectDefinitionNodes],
				leftSidebarItems: newLeftSidebarItems,
				selectedDefinitionNode: newNode,
				showChangesSaved: true,
			};
		}

		case TYPES.BULK_CHANGE_NODE_VIEW: {
			const {hiddenFolderNodes, leftSidebarItem} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;

			const updatedNodes = nodes.map(
				(node: Node<ObjectDefinitionNodeData>) => {
					return {
						...node,
						data: {...node.data, nodeSelected: false},
						isHidden: !hiddenFolderNodes,
					};
				}
			);

			const updatedEdges = edges.map(
				(edge: Edge<ObjectRelationshipEdgeData>) => {
					return {
						...edge,
						isHidden: !hiddenFolderNodes,
					};
				}
			);

			const updatedLeftSidebarItems = leftSidebarItems.map(
				(sidebarItem: LeftSidebarItemType) => {
					const updatedObjectDefinitions = sidebarItem.objectDefinitions?.map(
						(objectDefinition) => {
							return {
								...objectDefinition,
								hiddenNode: !hiddenFolderNodes,
								selected: false,
							};
						}
					);
					if (sidebarItem.folderName === leftSidebarItem.folderName) {
						return {
							...sidebarItem,
							hiddenFolderNodes: !hiddenFolderNodes,
							objectDefinitions: updatedObjectDefinitions,
						};
					}

					return sidebarItem;
				}
			);

			return {
				...state,
				elements: [...updatedEdges, ...updatedNodes],
				leftSidebarItems: updatedLeftSidebarItems,
				rightSidebarType: 'empty' as RightSidebarType,
			};
		}

		case TYPES.CHANGE_NODE_VIEW: {
			const {
				definitionId,
				definitionName,
				hiddenNode,
				leftSidebarItem,
			} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;
			let isNodeSelected = false;

			const updatedEdges = edges.map(
				(edge: Edge<ObjectRelationshipEdgeData>) => {
					if (
						edge.source === definitionId.toString() ||
						edge.target === definitionId.toString()
					) {
						return {
							...edge,
							isHidden: !hiddenNode,
						};
					}

					return edge;
				}
			);

			const updatedNodes = nodes.map(
				(node: Node<ObjectDefinitionNodeData>) => {
					if (node.data?.id === definitionId) {
						return {
							...node,
							data: {...node.data, nodeSelected: false},
							isHidden: !hiddenNode,
						};
					}

					return node;
				}
			);

			const updatedLeftSidebarItems = leftSidebarItems.map(
				(sidebarItem) => {
					if (sidebarItem.folderName === leftSidebarItem.folderName) {
						const updatedObjectDefinitions = sidebarItem.objectDefinitions?.map(
							(objectDefinition) => {
								if (
									objectDefinition.definitionName ===
									definitionName
								) {
									isNodeSelected = objectDefinition.selected;

									return {
										...objectDefinition,
										hiddenNode: !hiddenNode,
										selected: false,
									};
								}

								return objectDefinition;
							}
						);

						return {
							...sidebarItem,
							objectDefinitions: updatedObjectDefinitions,
						};
					}

					return sidebarItem;
				}
			);

			return {
				...state,
				elements: [...updatedEdges, ...updatedNodes],
				leftSidebarItems: updatedLeftSidebarItems,
				rightSidebarType: isNodeSelected
					? 'empty'
					: state.rightSidebarType,
			};
		}

		case TYPES.CREATE_MODEL_BUILDER_STRUCTURE: {
			const {objectFolders} = action.payload;
			const {
				editObjectDefinitionURL,
				objectDefinitionPermissionsURL,
				selectedFolderERC,
			} = state;

			const newLeftSidebar = objectFolders.map((folder) => {
				const folderDefinitions = folder.definitions?.map(
					(definition) => {
						return {
							definitionId: definition.id,
							definitionName: definition.name,
							hiddenNode: false,
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
					hiddenFolderNodes: false,
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
											source: `${objectDefinition.id}`,
											sourceHandle: `${objectDefinition.id}`,
											target: `${relationship.objectDefinitionId2}`,
											targetHandle: `${relationship.objectDefinitionId2}`,
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
								editObjectDefinitionURL,
								externalReferenceCode:
									objectDefinition.externalReferenceCode,
								hasObjectDefinitionDeleteResourcePermission: !!objectDefinition
									.actions.delete,
								hasObjectDefinitionManagePermissionsResourcePermission: !!objectDefinition
									.actions.permissions,
								id: objectDefinition.id,
								isLinkedNode: false,
								label: getLocalizableLabel(
									objectDefinition.defaultLanguageId,
									objectDefinition.label,
									objectDefinition.name
								),
								name: objectDefinition.name,
								nodeSelected: false,
								objectDefinitionPermissionsURL,
								objectFields: fieldsCustomSort(objectFields),
								status: objectDefinition.status,
								system: objectDefinition.system,
							},
							id: objectDefinition.id.toString(),
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
		case TYPES.DELETE_FOLDER_NODE: {
			const {currentFolderName, deletedNodeName} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map((item) => {
				if (item.folderName === currentFolderName) {
					updatedObjectDefinitions = item.objectDefinitions?.filter(
						(definition) =>
							definition.definitionName !== deletedNodeName
					);

					return {
						...item,
						objectDefinitions: [...updatedObjectDefinitions!],
					};
				}
				else {
					return {
						...item,
					};
				}
			}) as LeftSidebarItemType[];

			return {
				...state,
				leftSidebarItems: newLeftSidebarItems,
			};
		}
		case TYPES.SET_ELEMENTS: {
			const {newElements} = action.payload;

			return {
				...state,
				elements: newElements,
			};
		}
		case TYPES.SET_SELECTED_NODE: {
			const {edges, nodes, selectedObjectDefinitionId} = action.payload;

			const {leftSidebarItems} = state;

			const newObjectDefinitionNodes = nodes.map((definitionNode) => ({
				...definitionNode,
				data: {
					...definitionNode.data,
					nodeSelected:
						definitionNode.id ===
						selectedObjectDefinitionId.toString(),
				},
			}));

			const newLeftSidebarItems = leftSidebarItems.map((sidebarItem) => {
				const newLeftSidebarDefinitions = sidebarItem.objectDefinitions?.map(
					(sidebarDefinition) => ({
						...sidebarDefinition,
						selected:
							selectedObjectDefinitionId ===
							sidebarDefinition.definitionId.toString(),
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
		case TYPES.UPDATE_FOLDER_NODE: {
			const {currentFolderName, updatedNode} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map(
				(item: LeftSidebarItemType) => {
					if (item.folderName === currentFolderName) {
						updatedObjectDefinitions = item.objectDefinitions?.map(
							(definition) => {
								if (
									definition.definitionId.toString() ===
									updatedNode.id?.toString()
								) {
									return {
										...definition,
										name: updatedNode.label,
									};
								}

								return definition;
							}
						);

						return {
							...item,
							objectDefinitions: [...updatedObjectDefinitions!],
						};
					}
					else {
						return {
							...item,
						};
					}
				}
			) as LeftSidebarItemType[];

			return {
				...state,
				leftSidebarItems: newLeftSidebarItems,
			};
		}
		default:
			return state;
	}
}
