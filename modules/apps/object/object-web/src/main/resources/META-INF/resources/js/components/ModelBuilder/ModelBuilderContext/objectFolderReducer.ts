/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocalizableLabel} from '@liferay/object-js-components-web';
import {Edge, Node, isEdge, isNode} from 'react-flow-renderer';

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
	convertAllObjectFieldsToUnselected,
	getNonOverlappingEdges,
	objectFieldsCustomSort,
} from './objectFolderReducerUtil';
import {TYPES} from './typesEnum';

export function ObjectFolderReducer(state: TState, action: TAction): TState {
	switch (action.type) {
		case TYPES.ADD_OBJECT_DEFINITION_TO_OBJECT_FOLDER: {
			const {
				newObjectDefinition,
				objectDefinitionNodes,
				selectedObjectFolderName,
			} = action.payload;
			const {elements, leftSidebarItems} = state;
			let newPosition = {
				x: 2 * 300,
				y: 2 * 400,
			};

			if (objectDefinitionNodes.length) {
				const yPositions = objectDefinitionNodes.map(
					(objectDefinitionNode) => objectDefinitionNode.position.y
				);
				const maximumY = Math.max(...yPositions);
				const maximumNodesYPosition = objectDefinitionNodes.filter(
					(objectDefinitionNode) =>
						objectDefinitionNode.position.y === maximumY
				);
				const xPositions = maximumNodesYPosition.map(
					(objectDefinitionNode) => objectDefinitionNode.position.x
				);
				const maximumX = Math.max(...xPositions);
				const mostBottomRightNodePosition = maximumNodesYPosition.find(
					(objectDefinitionNode) =>
						objectDefinitionNode.position.x === maximumX
				)!.position;
				newPosition = {
					x: mostBottomRightNodePosition!.x + 300,
					y: mostBottomRightNodePosition!.y,
				};
			}

			const newLeftSidebarItems = leftSidebarItems.map(
				(leftSidebarItem) => {
					let newLeftSidebarObjectDefinitionItem;

					if (
						leftSidebarItem.objectFolderName ===
						selectedObjectFolderName
					) {
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

						const updatedObjectDefinitions = leftSidebarItem.leftSidebarObjectDefinitionItems?.map(
							(leftSidebarObjectDefinitionItem) => {
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
						id: objectField.id,
						label: objectField.label,
						name: objectField.name,
						primaryKey: objectField.name === 'id',
						required: objectField.required,
						selected: false,
					} as ObjectFieldNodeRow;
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

			const newObjectDefinitionNode = {
				data: {
					...newObjectDefinition,
					hasObjectDefinitionDeleteResourcePermission: !!newObjectDefinition
						.actions.delete,
					hasObjectDefinitionManagePermissionsResourcePermission: !!newObjectDefinition
						.actions.permissions,
					hasObjectDefinitionUpdateResourcePermission: !!newObjectDefinition
						.actions.update,
					hasObjectDefinitionViewResourcePermission: false,
					label: newObjectDefinition.label,
					linkedObjectDefinition: false,
					objectFields: objectFieldsCustomSort(objectFields),
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

			return {
				...state,
				elements: [...newObjectDefinitionNodes],
				leftSidebarItems: newLeftSidebarItems,
				selectedObjectDefinitionNode: newObjectDefinitionNode,
				showChangesSaved: true,
			};
		}

		case TYPES.ADD_OBJECT_FIELD: {
			const {
				newObjectField,
				objectDefinitionExternalReferenceCode,
				objectDefinitionNodes,
				objectRelationshipEdges,
			} = action.payload;

			const selectedObjectField: ObjectFieldNodeRow = {
				businessType: newObjectField.businessType,
				externalReferenceCode: newObjectField.externalReferenceCode,
				id: newObjectField.id,
				label: newObjectField.label,
				name: newObjectField.name,
				primaryKey: false,
				required: newObjectField.required,
				selected: true,
			};

			let selectedObjectDefinitionNode: Node<
				ObjectDefinitionNodeData
			> | null = null;

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode.data?.externalReferenceCode ===
						objectDefinitionExternalReferenceCode
					) {
						const {objectFields} = objectDefinitionNode.data;

						const newObjectFields = convertAllObjectFieldsToUnselected(
							objectFields
						);

						newObjectFields.push(selectedObjectField);

						selectedObjectDefinitionNode = {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								objectFields: newObjectFields,
								selected: true,
								showAllObjectFields: true,
							},
						};

						return selectedObjectDefinitionNode;
					}

					const unselectedObjectFields = convertAllObjectFieldsToUnselected(
						objectDefinitionNode.data
							?.objectFields as ObjectFieldNodeRow[]
					);

					return {
						...objectDefinitionNode,
						data: {
							...objectDefinitionNode.data,
							objectFields: unselectedObjectFields,
							selected: false,
						},
					};
				}
			) as Node<ObjectDefinitionNodeData>[];

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...objectRelationshipEdges,
				],
				rightSidebarType: 'objectFieldDetails',
				selectedObjectDefinitionNode,
				selectedObjectField,
			};
		}

		case TYPES.BULK_CHANGE_NODE_VIEW: {
			const {
				hiddenObjectFolderObjectDefinitionNodes,
				objectDefinitionNodes,
				objectRelationshipEdges,
			} = action.payload;
			const {leftSidebarItems} = state;

			const updatedObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode: Node<ObjectDefinitionNodeData>) => {
					return {
						...objectDefinitionNode,
						data: {...objectDefinitionNode.data, selected: false},
						isHidden: !hiddenObjectFolderObjectDefinitionNodes,
					};
				}
			) as Node<ObjectDefinitionNodeData>[];

			const updatedObjectRelationshipEdges = objectRelationshipEdges.map(
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
				objectDefinitionNodes,
				objectRelationshipEdges,
				selectedSidebarItem,
			} = action.payload;
			const {leftSidebarItems} = state;
			let isObjectDefinitionNodeSelected = false;

			const updatedObjectRelationshipEdges = objectRelationshipEdges.map(
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

			const updatedObjectDefinitionNodes = objectDefinitionNodes.map(
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

		case TYPES.UPDATE_MODEL_BUILDER_STRUCTURE: {
			const {
				objectFolders,
				rightSidebarType,
				selectedObjectFolder,
				selectedObjectRelationshipId,
			} = action.payload;

			const newLeftSidebarItems = objectFolders.map((objectFolder) => {
				const leftSidebarObjectDefinitionItems = objectFolder.objectDefinitions?.map(
					(objectDefinition) => {
						return {
							externalReferenceCode:
								objectDefinition.externalReferenceCode,
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
			const updatedObjectFolderItems: ObjectFolderItem[] = [];

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
												selected:
													selectedObjectRelationshipId ===
													objectRelationship.id,
												selfObjectRelationships,
												sourceY: 0,
												targetY: 0,
												type: objectRelationship.type,
											},
											id: `reactflow__edge-object-relationship-${objectRelationship.name}-parent-${objectRelationship.objectDefinitionId1}-child-${objectRelationship.objectDefinitionId2}`,
											source: `${objectDefinition.id}`,
											sourceHandle: isSelfObjectRelationship
												? 'fixedLeftHandle'
												: null,
											target: `${objectRelationship.objectDefinitionId2}`,
											targetHandle: isSelfObjectRelationship
												? 'fixedRightHandle'
												: null,
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
							positionX = positionColumn.positionX * 380 + 50;
							positionY = positionColumn.positionY * 450 + 100;

							positionColumn.positionX++;
						}

						if ((index + 1) % 4 === 0 && index !== 0) {
							positionColumn.positionY++;
							positionColumn.positionX = 0;
						}

						updatedObjectFolderItems.push({
							linkedObjectDefinition: objectFolderItem?.linkedObjectDefinition!,
							objectDefinitionExternalReferenceCode: objectFolderItem?.objectDefinitionExternalReferenceCode!,
							positionX,
							positionY,
						});

						return {
							data: {
								...objectDefinition,
								objectFields: objectFieldsCustomSort(
									objectDefinition.objectFields
								),
								showAllObjectFields: false,
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

			let newModelBuilderState = {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...newObjectRelationshipEdges,
				],
				leftSidebarItems: newLeftSidebarItems,
				selectedObjectFolder: {
					...selectedObjectFolder,
					objectFolderItems: updatedObjectFolderItems,
				},
				selectedObjectRelationship: null,
			};

			if (rightSidebarType) {
				newModelBuilderState = {
					...newModelBuilderState,
					rightSidebarType,
				};
			}

			return newModelBuilderState;
		}

		case TYPES.DELETE_OBJECT_FIELD: {
			const {
				objectDefinitionNodes,
				objectRelationshipEdges,
				selectedObjectDefinitionNode,
				selectedObjectField,
			} = action.payload;

			const newSelectedObjectDefinitionNodeObjectFields = selectedObjectDefinitionNode?.data?.objectFields.filter(
				(objectField) =>
					objectField.externalReferenceCode !==
					selectedObjectField.externalReferenceCode
			);

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode.data?.externalReferenceCode ===
						selectedObjectDefinitionNode?.data
							?.externalReferenceCode
					) {
						return {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								objectFields: newSelectedObjectDefinitionNodeObjectFields,
								selected: false,
							},
						} as Node<ObjectDefinitionNodeData>;
					}

					return objectDefinitionNode;
				}
			);

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...objectRelationshipEdges,
				],
				rightSidebarType: 'empty',
				selectedObjectField: undefined,
			};
		}

		case TYPES.SET_DELETE_OBJECT_DEFINITION: {
			const {newDeleteObjectDefinition} = action.payload;

			return {
				...state,
				deleteObjectDefinition: newDeleteObjectDefinition,
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

		case TYPES.SET_SELECTED_OBJECT_FIELD: {
			const {
				objectDefinitionNodes,
				objectRelationshipEdges,
				selectedObjectDefinitionId,
				selectedObjectField,
				selectedObjectFieldName,
			} = action.payload;

			let selectedObjectDefinitionNode: Node<
				ObjectDefinitionNodeData
			> | null = null;

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					const newObjectDefinitionNode = {
						...objectDefinitionNode,
						data: {
							...objectDefinitionNode.data,
							objectFields: objectDefinitionNode.data?.objectFields.map(
								(objectField) => ({
									...objectField,
									selected:
										objectDefinitionNode.data?.id ===
											selectedObjectDefinitionId &&
										objectField.name ===
											selectedObjectFieldName,
								})
							),
							selected:
								objectDefinitionNode.data?.id ===
								selectedObjectDefinitionId,
						},
					} as Node<ObjectDefinitionNodeData>;

					if (
						objectDefinitionNode.data?.id ===
						selectedObjectDefinitionId
					) {
						selectedObjectDefinitionNode = newObjectDefinitionNode;
					}

					return newObjectDefinitionNode;
				}
			) as Node<ObjectDefinitionNodeData>[];

			const newObjectRelationshipEdges = objectRelationshipEdges.map(
				(objectRelationshipEdge) => ({
					...objectRelationshipEdge,
					data: {...objectRelationshipEdge.data, selected: false},
				})
			) as Edge<ObjectRelationshipEdgeData>[];

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...newObjectRelationshipEdges,
				],
				rightSidebarType: 'objectFieldDetails',
				selectedObjectDefinitionNode,
				selectedObjectField,
			};
		}

		case TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE: {
			const {
				objectDefinitionNodes,
				objectRelationshipEdges,
				selectedObjectDefinitionId,
			} = action.payload;

			const {leftSidebarItems} = state;

			let selectedObjectDefinitionNode: Node<
				ObjectDefinitionNodeData
			> | null = null;

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					const newObjectFields = objectDefinitionNode.data?.objectFields.map(
						(objectField) => ({
							...objectField,
							selected: false,
						})
					);

					if (
						objectDefinitionNode.id === selectedObjectDefinitionId
					) {
						selectedObjectDefinitionNode = {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								objectFields: newObjectFields,
								selected: true,
							},
						} as Node<ObjectDefinitionNodeData>;

						return selectedObjectDefinitionNode;
					}

					return {
						...objectDefinitionNode,
						data: {
							...objectDefinitionNode.data,
							objectFields: newObjectFields,
							selected: false,
						},
					};
				}
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

			const selectedObjectRelationshipEdge = objectRelationshipEdges.find(
				(objectRelationshipEdge) =>
					objectRelationshipEdge.data?.selected
			);

			const newObjectRelationshipEdges = objectRelationshipEdges;

			if (selectedObjectRelationshipEdge?.data) {
				const selectedEdgeIndex = objectDefinitionNodes.findIndex(
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
					...newObjectDefinitionNodes,
					...newObjectRelationshipEdges,
				],
				leftSidebarItems: newLeftSidebarItems,
				rightSidebarType: 'objectDefinitionDetails',
				selectedObjectDefinitionNode,
				selectedObjectField: undefined,
			};
		}

		case TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE_POSITION: {
			const {
				newObjectDefinitionNodePosition,
				objectDefinitionNodes,
				objectRelationshipEdges,
				updatedObjectDefinitionNodeId,
				updatedObjectFolder,
			} = action.payload;

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode.data?.id ===
						updatedObjectDefinitionNodeId
					) {
						return {
							...objectDefinitionNode,
							position: newObjectDefinitionNodePosition,
						};
					}

					return objectDefinitionNode;
				}
			);

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...objectRelationshipEdges,
				],
				selectedObjectFolder: updatedObjectFolder,
			};
		}

		case TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE: {
			const {selectedObjectRelationshipId} = action.payload;

			const {elements} = state;

			const edges = elements.filter((element) => isEdge(element)) as Edge<
				ObjectRelationshipEdgeData
			>[];

			const nodes = elements.filter((element) => isNode(element)) as Node<
				ObjectDefinitionNodeData
			>[];

			const selectedObjectRelationshipEdge = edges.find(
				(objectRelationshipEdge) =>
					objectRelationshipEdge.data?.objectRelationshipId ===
					selectedObjectRelationshipId
			);

			const newObjectRelationshipEdges = edges.map(
				(objectRelationshipEdge) => ({
					...objectRelationshipEdge,
					data: {
						...objectRelationshipEdge.data,
						selected:
							objectRelationshipEdge.data
								?.objectRelationshipId ===
							selectedObjectRelationshipId,
					},
				})
			) as Edge<ObjectRelationshipEdgeData>[];

			const selectedObjectDefinitionNode = nodes.find(
				(objectDefinitionNode) => objectDefinitionNode.data?.selected
			);

			const newObjectDefinitionNodes = nodes;

			if (selectedObjectDefinitionNode?.data) {
				const {objectFields} = selectedObjectDefinitionNode.data;
				const selectedObjectDefinitionNodeIndex = nodes.findIndex(
					(objectDefinitionNode) =>
						objectDefinitionNode.data?.selected
				);

				const newObjectFields = objectFields.map((objectField) => ({
					...objectField,
					selected: false,
				}));

				selectedObjectDefinitionNode.data.selected = false;
				selectedObjectDefinitionNode.data.objectFields = newObjectFields;

				newObjectDefinitionNodes[
					selectedObjectDefinitionNodeIndex
				] = selectedObjectDefinitionNode;
			}

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...newObjectRelationshipEdges,
				],
				rightSidebarType: 'objectRelationshipDetails',
				selectedObjectField: undefined,
				selectedObjectRelationship: selectedObjectRelationshipEdge,
			};
		}

		case TYPES.SET_SHOW_ALL_OBJECT_FIELDS: {
			const {
				objectDefinitionExternalReferenceCode,
				showAllObjectFields,
			} = action.payload;

			const {elements} = state;

			const objectDefinitionNodes = elements.filter((element) =>
				isNode(element)
			) as Node<ObjectDefinitionNodeData>[];

			const objectRelationshipEdges = elements.filter((element) =>
				isEdge(element)
			) as Edge<ObjectRelationshipEdgeData>[];

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode?.data?.externalReferenceCode ===
						objectDefinitionExternalReferenceCode
					) {
						return {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								showAllObjectFields: !showAllObjectFields,
							},
						};
					}

					return objectDefinitionNode;
				}
			) as Node<ObjectDefinitionNodeData>[];

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...objectRelationshipEdges,
				],
			};
		}

		case TYPES.SET_SHOW_CHANGES_SAVED: {
			const {updatedShowChangesSaved} = action.payload;

			return {
				...state,
				showChangesSaved: updatedShowChangesSaved,
			};
		}

		case TYPES.SET_SHOW_SIDEBARS: {
			const {updatedShowSidebars} = action.payload;

			return {
				...state,
				showSidebars: updatedShowSidebars,
			};
		}

		case TYPES.UPDATE_OBJECT_DEFINITION_NODE: {
			const {
				currentObjectFolderName,
				objectDefinitionNodes,
				objectDefinitionRelationshipEdges,
				updatedObjectDefinition,
			} = action.payload;

			const {leftSidebarItems} = state;

			const updatedObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode.data?.id ===
						updatedObjectDefinition.id
					) {
						return {
							...objectDefinitionNode,
							data: {
								...objectDefinitionNode.data,
								label: updatedObjectDefinition.label,
								name: updatedObjectDefinition.name,
								pluralLabel: {
									[updatedObjectDefinition.defaultLanguageId!]: updatedObjectDefinition.pluralLabel,
								},
							},
						};
					}

					return objectDefinitionNode;
				}
			) as Node<ObjectDefinitionNodeData>[];

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
									leftSidebarObjectDefinitionItem.id ===
									updatedObjectDefinition.id
								) {
									return {
										...leftSidebarObjectDefinitionItem,
										label: getLocalizableLabel(
											defaultLanguageId,
											updatedObjectDefinition.label,
											updatedObjectDefinition.name
										),
									};
								}

								return leftSidebarObjectDefinitionItem;
							}
						);

						return {
							...leftSidebarItem,
							leftSidebarObjectDefinitionItems: [
								...updatedObjectDefinitions!,
							],
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
				elements: [
					...objectDefinitionRelationshipEdges,
					...updatedObjectDefinitionNodes,
				],
				leftSidebarItems: newLeftSidebarItems,
			};
		}

		case TYPES.UPDATE_OBJECT_FIELD_NODE_ROW: {
			const {
				objectDefinitionNodes,
				objectRelationshipEdges,
				selectedObjectDefinitionNode,
				updatedObjectField,
			} = action.payload;

			const newSelectedObjectFields = selectedObjectDefinitionNode?.data?.objectFields.map(
				(objectField) => {
					if (
						objectField.externalReferenceCode ===
						updatedObjectField.externalReferenceCode
					) {
						return {
							...updatedObjectField,
							primaryKey: false,
							selected: true,
						} as ObjectFieldNodeRow;
					}

					return objectField;
				}
			);

			const newObjectDefinitionNodes = objectDefinitionNodes.map(
				(objectDefinitionNode) => {
					if (
						objectDefinitionNode.data?.externalReferenceCode ===
						selectedObjectDefinitionNode?.data
							?.externalReferenceCode
					) {
						return {
							...selectedObjectDefinitionNode,
							data: {
								...selectedObjectDefinitionNode?.data,
								objectFields: newSelectedObjectFields,
							},
						} as Node<ObjectDefinitionNodeData>;
					}

					return objectDefinitionNode;
				}
			);

			return {
				...state,
				elements: [
					...newObjectDefinitionNodes,
					...objectRelationshipEdges,
				],
			};
		}

		case TYPES.UPDATE_VISIBILITY_MODEL_BUILDER_MODALS: {
			const {modelBuilderModals} = action.payload;

			return {
				...state,
				modelBuilderModals,
			};
		}

		default:
			return state;
	}
}
