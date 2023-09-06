/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {ArrowHeadType, Edge, Node, useStore} from 'react-flow-renderer';

import {defaultLanguageId} from '../../../utils/constants';
import {manyMarkerId} from '../Edges/ManyMarker';
import {oneMarkerId} from '../Edges/OneMarker';
import {
	LeftSidebarItemType,
	LeftSidebarObjectDefinitionItemType,
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
		case TYPES.ADD_NEW_NODE_TO_OBJECT_FOLDER: {
			const {
				newObjectDefinition,
				selectedObjectFolderName,
			} = action.payload;
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

			let linkedObjectDefinition = false;

			const newLeftSidebarItems = leftSidebarItems.map((item) => {
				let newDefinition;

				if (item.objectFolderName === selectedObjectFolderName) {
					linkedObjectDefinition =
						item.objectDefinitions?.find(
							(objectDefinition) =>
								objectDefinition.id === newObjectDefinition.id
						)?.type === 'objectLink';

					if (!linkedObjectDefinition) {
						newDefinition = {
							id: newObjectDefinition.id,
							label: getLocalizableLabel(
								newObjectDefinition.defaultLanguageId,
								newObjectDefinition.label,
								newObjectDefinition.name
							),
							name: newObjectDefinition.name,
							selected: true,
							type: 'objectDefinition',
						} as LeftSidebarObjectDefinitionItemType;
					}

					const updatedObjectDefinitions = item.objectDefinitions?.map(
						(objectDefinition) => {
							if (
								linkedObjectDefinition &&
								objectDefinition.id === newObjectDefinition.id
							) {
								return {
									...objectDefinition,
									selected: true,
									type: 'objectDefinition',
								};
							}

							return {
								...objectDefinition,
								selected: false,
							};
						}
					);

					return {
						...item,
						objectDefinitions: newDefinition
							? [...updatedObjectDefinitions!, newDefinition]
							: [...updatedObjectDefinitions!],
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

			let newObjectDefinitionNodes = [];

			let newNode = {} as Node<ObjectDefinitionNodeData>;

			if (linkedObjectDefinition) {
				const objectDefinitionNodes = updatedObjectDefinitionsNodes.map(
					(node) => {
						if (node.id === newObjectDefinition.id.toString()) {
							return {
								...node,
								data: {
									...node.data,
									linked: false,
									nodeSelected: true,
								},
							} as Node<ObjectDefinitionNodeData>;
						}

						return node;
					}
				);

				newObjectDefinitionNodes = [...objectDefinitionNodes] as Node<
					ObjectDefinitionNodeData
				>[];
			}
			else {
				newNode = {
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
						linked: false,
						nodeSelected: true,
						objectFields: fieldsCustomSort(objectFields),
					},
					id: newObjectDefinition.id.toString(),
					position: newPosition,
					type: 'objectDefinition',
				} as Node<ObjectDefinitionNodeData>;

				newObjectDefinitionNodes = [
					...updatedObjectDefinitionsNodes,
					newNode,
				] as Node<ObjectDefinitionNodeData>[];
			}

			return {
				...state,
				elements: [...newObjectDefinitionNodes],
				leftSidebarItems: newLeftSidebarItems,
				selectedDefinitionNode: newNode,
				showChangesSaved: true,
			};
		}

		case TYPES.BULK_CHANGE_NODE_VIEW: {
			const {hiddenObjectFolderNodes, leftSidebarItem} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;

			const updatedNodes = nodes.map(
				(node: Node<ObjectDefinitionNodeData>) => {
					return {
						...node,
						data: {...node.data, nodeSelected: false},
						isHidden: !hiddenObjectFolderNodes,
					};
				}
			);

			const updatedEdges = edges.map(
				(edge: Edge<ObjectRelationshipEdgeData>) => {
					return {
						...edge,
						isHidden: !hiddenObjectFolderNodes,
					};
				}
			);

			const updatedLeftSidebarItems = leftSidebarItems.map(
				(sidebarItem: LeftSidebarItemType) => {
					const updatedObjectDefinitions = sidebarItem.objectDefinitions?.map(
						(objectDefinition) => {
							return {
								...objectDefinition,
								hiddenNode: !hiddenObjectFolderNodes,
								selected: false,
							};
						}
					);
					if (
						sidebarItem.objectFolderName ===
						leftSidebarItem.objectFolderName
					) {
						return {
							...sidebarItem,
							hiddenObjectFolderNodes: !hiddenObjectFolderNodes,
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
				hiddenNode,
				leftSidebarItem,
				objectDefinitionId,
				objectDefinitionName,
			} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;
			let isNodeSelected = false;

			const updatedEdges = edges.map(
				(edge: Edge<ObjectRelationshipEdgeData>) => {
					if (
						edge.source === objectDefinitionId.toString() ||
						edge.target === objectDefinitionId.toString()
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
					if (node.data?.id === objectDefinitionId) {
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
					if (
						sidebarItem.objectFolderName ===
						leftSidebarItem.objectFolderName
					) {
						const updatedObjectDefinitions = sidebarItem.objectDefinitions?.map(
							(objectDefinition) => {
								if (
									objectDefinition.name ===
									objectDefinitionName
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
			const {objectFolders, selectedObjectFolder} = action.payload;

			const newLeftSidebar = objectFolders.map((objectFolder) => {
				const objectDefinitions = objectFolder.objectDefinitions?.map(
					(objectDefinition) => {
						return {
							hiddenNode: false,
							id: objectDefinition.id,
							label: getLocalizableLabel(
								objectDefinition.defaultLanguageId,
								objectDefinition.label,
								objectDefinition.name
							),
							name: objectDefinition.name,
							selected: false,
							type: objectDefinition.linked
								? 'objectLink'
								: 'objectDefinition',
						} as LeftSidebarObjectDefinitionItemType;
					}
				);

				return {
					hiddenObjectFolderNodes: false,
					name: getLocalizableLabel(
						defaultLanguageId,
						objectFolder.label,
						objectFolder.name
					),
					objectDefinitions,
					objectFolderName: objectFolder.name,
					type: 'objectFolder',
				} as LeftSidebarItemType;
			});

			const currentObjectFolder = objectFolders.find(
				(objectFolder) =>
					objectFolder.name === selectedObjectFolder.name
			);

			let newObjectDefinitionNodes: Node<ObjectDefinitionNodeData>[] = [];
			const allEdges: Edge<ObjectRelationshipEdgeData>[] = [];

			if (currentObjectFolder) {
				const positionColumn = {positionX: 0, positionY: 0};

				newObjectDefinitionNodes = currentObjectFolder.objectDefinitions!.map(
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

						const objectFolderItem = currentObjectFolder.objectFolderItems.find(
							(objectFolderItem) =>
								objectFolderItem.objectDefinitionExternalReferenceCode ===
								objectDefinition.externalReferenceCode
						);

						let {
							positionX,
							positionY,
						} = objectFolderItem as ObjectFolderItem;

						if (positionX === 0 && positionY === 0) {
							positionX = positionColumn.positionX * 300 + 200;
							positionY = positionColumn.positionY * 400 + 100;

							positionColumn.positionX++;
						}

						if (index % 4 === 0 && index !== 0) {
							positionColumn.positionY++;
							positionColumn.positionX = 0;
						}

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
								x: positionX,
								y: positionY,
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
				selectedObjectFolder,
			};
		}

		case TYPES.DELETE_OBJECT_FOLDER_NODE: {
			const {currentObjectFolderName, deletedNodeName} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map((item) => {
				if (item.objectFolderName === currentObjectFolderName) {
					updatedObjectDefinitions = item.objectDefinitions?.filter(
						(objectDefinition) =>
							objectDefinition.name !== deletedNodeName
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
				(objectDefinitionNode) =>
					objectDefinitionNode.data?.nodeSelected
			);

			const newObjectDefinitionNodes = nodes;

			if (selectedNode?.data) {
				const selectedNodeIndex = nodes.findIndex(
					(objectDefinitionNode) =>
						objectDefinitionNode.data?.nodeSelected
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

			const newObjectDefinitionNodes = nodes.map(
				(objectDefinitionNode) => ({
					...objectDefinitionNode,
					data: {
						...objectDefinitionNode.data,
						nodeSelected:
							objectDefinitionNode.id ===
							selectedObjectDefinitionId,
					},
				})
			);

			const newLeftSidebarItems = leftSidebarItems.map((sidebarItem) => {
				const newLeftSidebarObjectDefinitions = sidebarItem.objectDefinitions?.map(
					(sidebarObjectDefinition) => ({
						...sidebarObjectDefinition,
						selected:
							selectedObjectDefinitionId ===
							sidebarObjectDefinition.id.toString(),
					})
				);

				return {
					...sidebarItem,
					objectDefinitions: newLeftSidebarObjectDefinitions,
				};
			});

			const selectedEdge = edges.find(
				(relationshipEdge) => relationshipEdge.data?.edgeSelected
			);

			const newObjectRelationshipEdges = edges;

			if (selectedEdge?.data) {
				const selectedEdgeIndex = nodes.findIndex(
					(objectDefinitionNode) =>
						objectDefinitionNode.data?.nodeSelected
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

		case TYPES.SET_SHOW_CHANGES_SAVED: {
			const {updatedShowChangesSaved} = action.payload;

			return {
				...state,
				showChangesSaved: updatedShowChangesSaved,
			};
		}

		case TYPES.UPDATE_OBJECT_FOLDER_NODE: {
			const {currentObjectFolderName, updatedNode} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map(
				(item: LeftSidebarItemType) => {
					if (item.objectFolderName === currentObjectFolderName) {
						updatedObjectDefinitions = item.objectDefinitions?.map(
							(objectDefinition) => {
								if (
									objectDefinition.id.toString() ===
									updatedNode.id?.toString()
								) {
									return {
										...objectDefinition,
										name: updatedNode.label,
									};
								}

								return objectDefinition;
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
