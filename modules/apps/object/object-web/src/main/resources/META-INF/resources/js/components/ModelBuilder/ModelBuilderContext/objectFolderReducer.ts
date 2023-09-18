/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {Edge, Node, useStore} from 'react-flow-renderer';

import {defaultLanguageId} from '../../../utils/constants';
import {manyMarkerId} from '../Edges/ManyMarker';
import {oneMarkerId} from '../Edges/OneMarker';
import {
	LeftSidebarItem,
	LeftSidebarObjectDefinitionItem,
	ObjectRelationshipEdgeData,
	TAction,
	TState,
} from '../types';
import {updateURLParam} from '../utils';
import {
	fieldsCustomSort,
	getNonOverlappingEdges,
} from './objectFolderReducerUtil';
import {TYPES} from './typesEnum';

export function ObjectFolderReducer(state: TState, action: TAction): TState {
	const store = useStore();

	switch (action.type) {
		case TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER: {
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

			const newLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem) => {
					let newLeftSidebarObjectDefinitionItem;

					if (
						leftSidebarItem.objectFolderName ===
						selectedObjectFolderName
					) {
						linkedObjectDefinition =
							leftSidebarItem.leftSidebarObjectDefinitionItems?.find(
								(leftSidebarObjectDefinitionItem) =>
									leftSidebarObjectDefinitionItem.id ===
									newObjectDefinition.id
							)?.type === 'linkedObjectDefinition';

						if (!linkedObjectDefinition) {
							newLeftSidebarObjectDefinitionItem = {
								id: newObjectDefinition.id,
								label: getLocalizableLabel(
									newObjectDefinition.defaultLanguageId,
									newObjectDefinition.label,
									newObjectDefinition.name
								),
								name: newObjectDefinition.name,
								selected: true,
								type: 'objectDefinition',
							} as LeftSidebarObjectDefinitionItem;
						}

						const updatedObjectDefinitions = leftSidebarItem.leftSidebarObjectDefinitionItems?.map(
							(leftSidebarObjectDefinitionItem) => {
								if (
									linkedObjectDefinition &&
									leftSidebarObjectDefinitionItem.id ===
										newObjectDefinition.id
								) {
									return {
										...leftSidebarObjectDefinitionItem,
										selected: true,
										type: 'objectDefinitionNode',
									};
								}

								return {
									...leftSidebarObjectDefinitionItem,
									selected: false,
								};
							}
						);

						return {
							...leftSidebarItem,
							leftSidebarObjectDefinitionItems: newLeftSidebarObjectDefinitionItem
								? [
										...updatedObjectDefinitions!,
										newLeftSidebarObjectDefinitionItem,
								  ]
								: [...updatedObjectDefinitions!],
						};
					}
					else {
						return {
							...leftSidebarItem,
						};
					}
				}
			) as LeftSidebarItem[];
			const objectFields = newObjectDefinition.objectFields.map(
				(objectField) => {
					return {
						businessType: objectField.businessType,
						externalReferenceCode:
							objectField.externalReferenceCode,
						label: getLocalizableLabel(
							newObjectDefinition.defaultLanguageId,
							objectField.label,
							objectField.name
						),
						name: objectField.name,
						primaryKey: objectField.name === 'id',
						required: objectField.required,
						selected: false,
					} as ObjectFieldNode;
				}
			);
			const updatedObjectDefinitionsNodes = elements.map((node) => {
				return {
					...node,
					data: {
						...node.data,
						selected: false,
					},
				};
			});

			let newObjectDefinitionNodes = [];

			let newObjectDefinitionNode = {} as Node<ObjectDefinitionNodeData>;

			if (linkedObjectDefinition) {
				const objectDefinitionNodes = updatedObjectDefinitionsNodes.map(
					(objectDefinitionNode) => {
						if (
							objectDefinitionNode.id ===
							newObjectDefinition.id.toString()
						) {
							return {
								...objectDefinitionNode,
								data: {
									...objectDefinitionNode.data,
									linkedObjectDefinition: false,
									selected: true,
								},
							} as Node<ObjectDefinitionNodeData>;
						}

						return objectDefinitionNode;
					}
				);

				newObjectDefinitionNodes = [...objectDefinitionNodes] as Node<
					ObjectDefinitionNodeData
				>[];
			}
			else {
				newObjectDefinitionNode = {
					data: {
						...newObjectDefinition,
						hasObjectDefinitionDeleteResourcePermission: !!newObjectDefinition
							.actions.delete,
						hasObjectDefinitionManagePermissionsResourcePermission: !!newObjectDefinition
							.actions.permissions,
						hasObjectDefinitionUpdateResourcePermission: !!newObjectDefinition
							.actions.update,
						hasObjectDefinitionViewResourcePermission: false,
						hasSelfObjectRelationships: false,
						label: getLocalizableLabel(
							newObjectDefinition.defaultLanguageId,
							newObjectDefinition.label,
							newObjectDefinition.name
						),
						linkedObjectDefinition: false,
						objectFields: fieldsCustomSort(objectFields),
						selected: true,
					},
					id: newObjectDefinition.id.toString(),
					position: newPosition,
					type: 'objectDefinitionNode',
				} as Node<ObjectDefinitionNodeData>;

				newObjectDefinitionNodes = [
					...updatedObjectDefinitionsNodes,
					newObjectDefinitionNode,
				] as Node<ObjectDefinitionNodeData>[];
			}

			return {
				...state,
				elements: [...newObjectDefinitionNodes],
				leftSidebarItems: newLeftSidebarItems,
				selectedObjectDefinitionNode: newObjectDefinitionNode,
				showChangesSaved: true,
			};
		}

		case TYPES.BULK_CHANGE_NODE_VIEW: {
			const {hiddenObjectFolderObjectDefinitionNodes} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;

			const updatedObjectDefinitionNodes = nodes.map(
				(node: Node<ObjectDefinitionNodeData>) => {
					return {
						...node,
						data: {...node.data, selected: false},
						isHidden: !hiddenObjectFolderObjectDefinitionNodes,
					};
				}
			) as Node<ObjectDefinitionNodeData>[];

			const updatedObjectRelationshipEdges = edges.map(
				(objectRelationshipEdge: Edge<ObjectRelationshipEdgeData>) => {
					return {
						...objectRelationshipEdge,
						isHidden: !hiddenObjectFolderObjectDefinitionNodes,
					};
				}
			) as Edge<ObjectRelationshipEdgeData>[];

			const updatedLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem: LeftSidebarItem) => {
					const updateLeftSidebarObjectDefinitionItems = leftSidebarItem.leftSidebarObjectDefinitionItems?.map(
						(leftSidebarObjectDefinitionItem) => {
							return {
								...leftSidebarObjectDefinitionItem,
								hiddenObjectDefinitionNode: !hiddenObjectFolderObjectDefinitionNodes,
								selected: false,
							};
						}
					);
					if (
						leftSidebarItem.objectFolderName ===
						leftSidebarItem.objectFolderName
					) {
						return {
							...leftSidebarItem,
							hiddenObjectFolderObjectDefinitionNodes: !hiddenObjectFolderObjectDefinitionNodes,
							leftSidebarObjectDefinitionItems: updateLeftSidebarObjectDefinitionItems,
						};
					}

					return leftSidebarItem;
				}
			);

			return {
				...state,
				elements: [
					...updatedObjectRelationshipEdges,
					...updatedObjectDefinitionNodes,
				],
				leftSidebarItems: updatedLeftSidebarItems,
				rightSidebarType: 'empty',
			};
		}

		case TYPES.CHANGE_NODE_VIEW: {
			const {
				hiddenObjectDefinitionNode,
				objectDefinitionId,
				objectDefinitionName,
				selectedSidebarItem,
			} = action.payload;
			const {edges, nodes} = store.getState();
			const {leftSidebarItems} = state;
			let isObjectDefinitionNodeSelected = false;

			const updatedObjectRelationshipEdges = edges.map(
				(objectRelationshipEdge: Edge<ObjectRelationshipEdgeData>) => {
					if (
						objectRelationshipEdge.source ===
							objectDefinitionId.toString() ||
						objectRelationshipEdge.target ===
							objectDefinitionId.toString()
					) {
						return {
							...objectRelationshipEdge,
							isHidden: !hiddenObjectDefinitionNode,
						};
					}

					return objectRelationshipEdge;
				}
			);

			const updatedObjectDefinitionNodes = nodes.map(
				(objectDefinitionNode: Node<ObjectDefinitionNodeData>) => {
					if (objectDefinitionNode.data?.id === objectDefinitionId) {
						return {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								selected: false,
							},
							isHidden: !hiddenObjectDefinitionNode,
						};
					}

					return objectDefinitionNode;
				}
			);

			const updatedLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem) => {
					if (
						leftSidebarItem.objectFolderName ===
						selectedSidebarItem.objectFolderName
					) {
						const updatedObjectDefinitions = leftSidebarItem.leftSidebarObjectDefinitionItems?.map(
							(leftSidebarObjectDefinitionItem) => {
								if (
									leftSidebarObjectDefinitionItem.name ===
									objectDefinitionName
								) {
									isObjectDefinitionNodeSelected =
										leftSidebarObjectDefinitionItem.selected;

									return {
										...leftSidebarObjectDefinitionItem,
										hiddenObjectDefinitionNode: !hiddenObjectDefinitionNode,
										selected: false,
									};
								}

								return leftSidebarObjectDefinitionItem;
							}
						);

						return {
							...leftSidebarItem,
							leftSidebarObjectDefinitionItems: updatedObjectDefinitions,
						};
					}

					return leftSidebarItem;
				}
			);

			return {
				...state,
				elements: [
					...updatedObjectRelationshipEdges,
					...updatedObjectDefinitionNodes,
				],
				leftSidebarItems: updatedLeftSidebarItems,
				rightSidebarType: isObjectDefinitionNodeSelected
					? 'empty'
					: state.rightSidebarType,
			};
		}

		case TYPES.CREATE_MODEL_BUILDER_STRUCTURE: {
			const {objectFolders, selectedObjectFolder} = action.payload;

			const newLeftSidebarItems = objectFolders.map((objectFolder) => {
				const leftSidebarObjectDefinitionItems = objectFolder.objectDefinitions?.map(
					(objectDefinition) => {
						return {
							hiddenObjectDefinitionNode: false,
							id: objectDefinition.id,
							label: getLocalizableLabel(
								objectDefinition.defaultLanguageId,
								objectDefinition.label,
								objectDefinition.name
							),
							name: objectDefinition.name,
							selected: false,
							type: objectDefinition.linkedObjectDefinition
								? 'linkedObjectDefinition'
								: 'objectDefinition',
						} as LeftSidebarObjectDefinitionItem;
					}
				);

				return {
					hiddenObjectFolderObjectDefinitionNodes: false,
					leftSidebarObjectDefinitionItems,
					name: getLocalizableLabel(
						defaultLanguageId,
						objectFolder.label,
						objectFolder.name
					),
					objectFolderName: objectFolder.name,
					type: 'objectFolder',
				} as LeftSidebarItem;
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
						let selfObjectRelationships: ObjectRelationship[] = objectDefinition.objectRelationships.filter(
							(objectRelationship) =>
								objectRelationship.objectDefinitionName2 ===
								objectDefinition.name
						);

						selfObjectRelationships = selfObjectRelationships.filter(
							(selfObjectRelationship) =>
								!selfObjectRelationship.reverse
						);

						const hasOneSelfObjectRelationship =
							selfObjectRelationships?.length === 1;

						if (objectDefinition.objectRelationships.length) {
							objectDefinition.objectRelationships.forEach(
								(objectRelationship) => {
									if (!objectRelationship.reverse) {
										const isSelfObjectRelationship =
											objectDefinition.name ===
											objectRelationship.objectDefinitionName2;

										allEdges.push({
											data: {
												defaultLanguageId:
													objectDefinition.defaultLanguageId,
												label:
													!isSelfObjectRelationship ||
													(isSelfObjectRelationship &&
														hasOneSelfObjectRelationship)
														? getLocalizableLabel(
																objectDefinition.defaultLanguageId,
																objectRelationship.label,
																objectRelationship.name
														  )
														: selfObjectRelationships.length.toString(),
												markerEndId: manyMarkerId,
												markerStartId:
													objectRelationship.type ===
													'manyToMany'
														? manyMarkerId
														: oneMarkerId,
												objectRelationshipId:
													objectRelationship.id,
												selected: false,
												selfObjectRelationships,
												sourceY: 0,
												targetY: 0,
												type: objectRelationship.type,
											},
											id: `reactflow__edge-object-relationship-${objectRelationship.name}-parent-${objectRelationship.objectDefinitionId1}-child-${objectRelationship.objectDefinitionId2}`,
											source: `${objectDefinition.id}`,
											sourceHandle: isSelfObjectRelationship
												? 'fixedLeftHandle'
												: `${objectDefinition.id}`,
											target: `${objectRelationship.objectDefinitionId2}`,
											targetHandle: isSelfObjectRelationship
												? 'fixedRightHandle'
												: `${objectRelationship.objectDefinitionId2}`,
											type: isSelfObjectRelationship
												? 'selfObjectRelationshipEdge'
												: 'defaultObjectRelationshipEdge',
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
								hasSelfObjectRelationships:
									selfObjectRelationships?.length > 0,
								objectFields: fieldsCustomSort(
									objectDefinition.objectFields
								),
							},
							id: objectDefinition.id.toString(),
							position: {
								x: positionX,
								y: positionY,
							},
							type: 'objectDefinitionNode',
						} as Node<ObjectDefinitionNodeData>;
					}
				);
			}

			const newObjectRelationshipEdges = getNonOverlappingEdges(allEdges);

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...newObjectRelationshipEdges,
				],
				leftSidebarItems: newLeftSidebarItems,
				selectedObjectFolder,
			};
		}

		case TYPES.DELETE_OBJECT_DEFINITION: {
			const {
				currentObjectFolderName,
				deletedObjectDefinitionName,
			} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem) => {
					if (
						leftSidebarItem.objectFolderName ===
						currentObjectFolderName
					) {
						updatedObjectDefinitions = leftSidebarItem.leftSidebarObjectDefinitionItems?.filter(
							(leftSidebarObjectDefinitionItem) =>
								leftSidebarObjectDefinitionItem.name !==
								deletedObjectDefinitionName
						);

						return {
							...leftSidebarItem,
							objectDefinitions: [...updatedObjectDefinitions!],
						};
					}
					else {
						return {
							...leftSidebarItem,
						};
					}
				}
			) as LeftSidebarItem[];

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

		case TYPES.SET_OBJECT_FOLDER_NAME: {
			const {objectFolderName} = action.payload;

			updateURLParam('objectFolderName', objectFolderName);

			return {
				...state,
				objectFolderName,
				rightSidebarType: 'empty',
			};
		}

		case TYPES.SET_LOADING_OBJECT_FOLDER: {
			const {isLoadingObjectFolder} = action.payload;

			return {
				...state,
				isLoadingObjectFolder,
			};
		}

		case TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE: {
			const {edges, nodes, selectedObjectDefinitionId} = action.payload;

			const {leftSidebarItems} = state;

			const newObjectDefinitionNodes = nodes.map(
				(objectDefinitionNode) => ({
					...objectDefinitionNode,
					data: {
						...objectDefinitionNode.data,
						selected:
							objectDefinitionNode.id ===
							selectedObjectDefinitionId,
					},
				})
			) as Node<ObjectDefinitionNodeData>[];

			const newLeftSidebarItems = leftSidebarItems.map((sidebarItem) => {
				const newLeftSidebarObjectDefinitions = sidebarItem.leftSidebarObjectDefinitionItems?.map(
					(leftSidebarObjectDefinitionItem) => ({
						...leftSidebarObjectDefinitionItem,
						selected:
							selectedObjectDefinitionId ===
							leftSidebarObjectDefinitionItem.id.toString(),
					})
				);

				return {
					...sidebarItem,
					leftSidebarObjectDefinitionItems: newLeftSidebarObjectDefinitions,
				};
			});

			const selectedObjectRelationshipEdge = edges.find(
				(objectRelationshipEdge) =>
					objectRelationshipEdge.data?.selected
			);

			const newObjectRelationshipEdges = edges;

			if (selectedObjectRelationshipEdge?.data) {
				const selectedEdgeIndex = nodes.findIndex(
					(objectDefinitionNode) =>
						objectDefinitionNode.data?.selected
				);

				selectedObjectRelationshipEdge.data.selected = false;

				newObjectRelationshipEdges[
					selectedEdgeIndex
				] = selectedObjectRelationshipEdge;
			}

			return {
				...state,
				elements: [
					...newObjectRelationshipEdges,
					...newObjectDefinitionNodes,
				],
				leftSidebarItems: newLeftSidebarItems,
				rightSidebarType: 'objectDefinitionDetails',
			};
		}

		case TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE: {
			const {edges, nodes, selectedObjectRelationshipId} = action.payload;

			const newObjectRelationshipEdges = edges.map(
				(objectRelationshipEdge) => ({
					...objectRelationshipEdge,
					data: {
						...objectRelationshipEdge.data,
						selected:
							objectRelationshipEdge.data?.objectRelationshipId.toString() ===
							selectedObjectRelationshipId,
					},
				})
			) as Edge<ObjectRelationshipEdgeData>[];

			const selectedObjectDefinitionNode = nodes.find(
				(objectDefinitionNode) => objectDefinitionNode.data?.selected
			);

			const newObjectDefinitionNodes = nodes;

			if (selectedObjectDefinitionNode?.data) {
				const selectedNodeIndex = nodes.findIndex(
					(objectDefinitionNode) =>
						objectDefinitionNode.data?.selected
				);

				selectedObjectDefinitionNode.data.selected = false;

				newObjectDefinitionNodes[
					selectedNodeIndex
				] = selectedObjectDefinitionNode;
			}

			return {
				...state,
				elements: [
					...newObjectRelationshipEdges,
					...newObjectDefinitionNodes,
				],
				rightSidebarType: 'objectRelationshipDetails',
			};
		}

		case TYPES.SET_SHOW_CHANGES_SAVED: {
			const {updatedShowChangesSaved} = action.payload;

			return {
				...state,
				showChangesSaved: updatedShowChangesSaved,
			};
		}

		case TYPES.UPDATE_OBJECT_DEFINITION_NODE: {
			const {
				currentObjectFolderName,
				updatedObjectDefinitionNode,
			} = action.payload;

			const {leftSidebarItems} = state;

			let updatedObjectDefinitions;

			const newLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem: LeftSidebarItem) => {
					if (
						leftSidebarItem.objectFolderName ===
						currentObjectFolderName
					) {
						updatedObjectDefinitions = leftSidebarItem.leftSidebarObjectDefinitionItems?.map(
							(leftSidebarObjectDefinitionItem) => {
								if (
									leftSidebarObjectDefinitionItem.id.toString() ===
									updatedObjectDefinitionNode.id?.toString()
								) {
									return {
										...leftSidebarObjectDefinitionItem,
										name: updatedObjectDefinitionNode.label,
									};
								}

								return leftSidebarObjectDefinitionItem;
							}
						);

						return {
							...leftSidebarItem,
							objectDefinitions: [...updatedObjectDefinitions!],
						};
					}
					else {
						return {
							...leftSidebarItem,
						};
					}
				}
			) as LeftSidebarItem[];

			return {
				...state,
				leftSidebarItems: newLeftSidebarItems,
			};
		}

		default:
			return state;
	}
}
