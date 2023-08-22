/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {ArrowHeadType, Edge, Node, useStore} from 'react-flow-renderer';

import {defaultLanguageId} from '../../../utils/constants';
import {manyMarkerId} from '../Edges/ManyMarkerEnd';
import {oneMarkerId} from '../Edges/OneMarkerEnd';
import {
	LeftSidebarItemType,
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
			const {elements, leftSidebarItems} = state;
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
					...newObjectDefinition,
					hasObjectDefinitionDeleteResourcePermission: !!newObjectDefinition
						.actions.delete,
					hasObjectDefinitionManagePermissionsResourcePermission: !!newObjectDefinition
						.actions.permissions,
					hasObjectDefinitionUpdateResourcePermission: !!newObjectDefinition
						.actions.update,
					hasObjectDefinitionViewResourcePermission: false,
					hasSelfRelationships: false,
					label: getLocalizableLabel(
						newObjectDefinition.defaultLanguageId,
						newObjectDefinition.label,
						newObjectDefinition.name
					),
					linkedDefinition: false,
					nodeSelected: true,
					objectFields: fieldsCustomSort(objectFields),
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
			const {objectFolders, selectedFolder} = action.payload;

			const newLeftSidebar = objectFolders.map((folder) => {
				const folderDefinitions = folder.definitions?.map(
					(definition) => {
						return {
							definitionId: definition.id,
							definitionName: definition.name,
							hiddenNode: false,
							name: definition.label,
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
				(folder) => folder.name === selectedFolder.name
			);

			let newObjectDefinitionNodes: Node<ObjectDefinitionNodeData>[] = [];
			const allEdges: Edge<ObjectRelationshipEdgeData>[] = [];

			if (currentFolder) {
				const positionColumn = {x: 1, y: 0};

				newObjectDefinitionNodes = currentFolder.definitions!.map(
					(objectDefinition, index) => {
						let selfRelationships: ObjectRelationship[] = objectDefinition.objectRelationships.filter(
							(relationship) =>
								relationship.objectDefinitionName2 ===
								objectDefinition.name
						);

						selfRelationships = selfRelationships.filter(
							(relationship) => !relationship.reverse
						);

						const hasOneSelfRelationship =
							selfRelationships?.length === 1;

						if (objectDefinition.objectRelationships.length) {
							objectDefinition.objectRelationships.forEach(
								(relationship) => {
									if (!relationship.reverse) {
										const isSelfRelationship =
											objectDefinition.name ===
											relationship.objectDefinitionName2;

										allEdges.push({
											arrowHeadType: isSelfRelationship
												? ArrowHeadType.ArrowClosed
												: undefined,
											data: {
												defaultLanguageId:
													objectDefinition.defaultLanguageId,
												edgeSelected: false,
												label:
													!isSelfRelationship ||
													(isSelfRelationship &&
														hasOneSelfRelationship)
														? getLocalizableLabel(
																objectDefinition.defaultLanguageId,
																relationship.label,
																relationship.name
														  )
														: selfRelationships.length.toString(),
												markerEndId: manyMarkerId,
												markerStartId:
													relationship.type ===
													'manyToMany'
														? manyMarkerId
														: oneMarkerId,
												objectRelationshipId:
													relationship.id,
												selfRelationships,
												sourceY: 0,
												targetY: 0,
												type: relationship.type,
											},
											id: `reactflow__edge-object-relationship-${relationship.name}-parent-${relationship.objectDefinitionId1}-child-${relationship.objectDefinitionId2}`,
											source: `${objectDefinition.id}`,
											sourceHandle: isSelfRelationship
												? 'fixedLeftHandle'
												: `${objectDefinition.id}`,
											target: `${relationship.objectDefinitionId2}`,
											targetHandle: isSelfRelationship
												? 'fixedRightHandle'
												: `${relationship.objectDefinitionId2}`,
											type: isSelfRelationship
												? 'self'
												: 'default',
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
								...objectDefinition,
								hasSelfRelationships:
									selfRelationships?.length > 0,
								objectFields: fieldsCustomSort(
									objectDefinition.objectFields
								),
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
				selectedFolder,
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

		case TYPES.SET_SELECTED_EDGE: {
			const {edges, nodes, selectedObjectRelationshipId} = action.payload;

			const newObjectRelationshipEdges = edges.map(
				(relationshipEdge) => ({
					...relationshipEdge,
					data: {
						...relationshipEdge.data,
						edgeSelected:
							relationshipEdge.data?.objectRelationshipId.toString() ===
							selectedObjectRelationshipId,
					},
				})
			);

			const selectedNode = nodes.find(
				(definitionNode) => definitionNode.data?.nodeSelected
			);

			const newObjectDefinitionNodes = nodes;

			if (selectedNode?.data) {
				const selectedNodeIndex = nodes.findIndex(
					(definitionNode) => definitionNode.data?.nodeSelected
				);

				selectedNode.data.nodeSelected = false;

				newObjectDefinitionNodes[selectedNodeIndex] = selectedNode;
			}

			return {
				...state,
				elements: [
					...newObjectRelationshipEdges,
					...newObjectDefinitionNodes,
				],
				rightSidebarType: 'objectRelationshipDetails' as RightSidebarType,
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
						definitionNode.id === selectedObjectDefinitionId,
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

			const selectedEdge = edges.find(
				(relationshipEdge) => relationshipEdge.data?.edgeSelected
			);

			const newObjectRelationshipEdges = edges;

			if (selectedEdge?.data) {
				const selectedEdgeIndex = nodes.findIndex(
					(definitionNode) => definitionNode.data?.nodeSelected
				);

				selectedEdge.data.edgeSelected = false;

				newObjectRelationshipEdges[selectedEdgeIndex] = selectedEdge;
			}

			return {
				...state,
				elements: [
					...newObjectRelationshipEdges,
					...newObjectDefinitionNodes,
				],
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
