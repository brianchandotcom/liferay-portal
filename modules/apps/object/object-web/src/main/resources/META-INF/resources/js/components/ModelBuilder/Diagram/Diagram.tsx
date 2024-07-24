/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {LearnMessage, LearnResourcesContext} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useState} from 'react';
import ReactFlow, {
	Background,
	Connection,
	ConnectionLineType,
	ConnectionMode,
	Controls,
	Edge,
	MiniMap,
	Node,
	isEdge,
	isNode,
} from 'react-flow-renderer';

import {ModalAddObjectRelationship} from '../../ObjectRelationship/ModalAddObjectRelationship';
import {getUpdatedModelBuilderStructurePayload} from '../../ViewObjectDefinitions/objectDefinitionUtil';
import DefaultObjectRelationshipEdge from '../Edges/DefaultObjectRelationshipEdge';
import SelfObjectRelationshipEdge from '../Edges/SelfObjectRelationshipEdge';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectDefinitionNode} from '../ObjectDefinitionNode/ObjectDefinitionNode';
import {ObjectRelationshipEdgeData} from '../types';
import {getUnsupportedObjectRelationshipErrorMessage} from '../utils';

import './Diagram.scss';

let ReactFlowDefault = ReactFlow;

// `react-flow-renderer` provides both a commonjs and ESM version.
// We need this logic here so that both work. Unit tests rely on commonjs and
// our DXP runtime uses ESM.

// @ts-ignore

if (ReactFlowDefault.default) {

	// @ts-ignore

	ReactFlowDefault = ReactFlowDefault.default;
}

const NODE_TYPES = {
	objectDefinitionNode: ObjectDefinitionNode,
};

const EDGE_TYPES = {
	defaultObjectRelationshipEdge: DefaultObjectRelationshipEdge,
	selfObjectRelationshipEdge: SelfObjectRelationshipEdge,
};

function DiagramBuilder() {
	const [
		{
			baseResourceURL,
			elements,
			isLoadingObjectFolder,
			learnResourceContext,
			selectedObjectFolder,
			showChangesSaved,
			showSidebars,
		},
		dispatch,
	] = useObjectFolderContext();

	const [showAddObjectRelationshipModal, setShowAddObjectRelationshipModal] =
		useState(false);
	const [
		newObjectRelationshipSourceNodeProps,
		setNewObjectRelationshipSourceNodeProps,
	] = useState<{
		parameterRequired: boolean;
		sourceNode: {
			erc: string;
		};
		targetNode: {
			erc: string;
		};
	}>();

	const edges: Edge<ObjectRelationshipEdgeData[]>[] = [];

	const nodes: Node<ObjectDefinitionNodeData>[] = [];

	elements.forEach((element) => {
		if (isEdge(element)) {
			edges.push(element as Edge<ObjectRelationshipEdgeData[]>);
		}
		else {
			nodes.push(element as Node<ObjectDefinitionNodeData>);
		}
	});

	const onConnect = useCallback(
		(connection: Connection | Edge) => {
			if (connection.targetHandle === connection.sourceHandle) {
				return;
			}

			const sourceNode = elements.find(
				(node) => isNode(node) && node.id === connection.source
			) as Node<ObjectDefinitionNodeData>;

			const targetNode = elements.find(
				(node) => isNode(node) && node.id === connection.target
			) as Node<ObjectDefinitionNodeData>;

			const unsupportedObjectRelationship =
				getUnsupportedObjectRelationshipErrorMessage(
					nodes,
					sourceNode,
					targetNode
				);

			if (unsupportedObjectRelationship?.errorMessage) {
				openToast({
					message: unsupportedObjectRelationship?.errorMessage,
					toastProps: unsupportedObjectRelationship.learnMessage && {
						actions: (
							<LearnResourcesContext.Provider
								value={learnResourceContext}
							>
								<LearnMessage
									className="alert-link"
									resource="object-web"
									resourceKey={
										unsupportedObjectRelationship.learnMessage
									}
								/>
							</LearnResourcesContext.Provider>
						),
					},
					type: 'warning',
				});
			}
			else {
				setShowAddObjectRelationshipModal(true);
				setNewObjectRelationshipSourceNodeProps({
					parameterRequired: sourceNode?.data?.parameterRequired!,
					sourceNode: {
						erc: sourceNode?.data?.externalReferenceCode!,
					},
					targetNode: {
						erc: targetNode?.data?.externalReferenceCode!,
					},
				});
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[elements]
	);

	const onNodeDragStop = async (node: Node<ObjectDefinitionNodeData>) => {
		const updatedObjectFolderItems =
			selectedObjectFolder.objectFolderItems.map((objectFolderItem) => {
				if (
					objectFolderItem.objectDefinitionExternalReferenceCode ===
					node.data?.externalReferenceCode
				) {
					return {
						...objectFolderItem,
						positionX: node.position.x,
						positionY: node.position.y,
					};
				}

				return objectFolderItem;
			});

		const updatedObjectFolder = {
			...selectedObjectFolder,
			objectFolderItems: updatedObjectFolderItems,
		};

		await API.putObjectFolderByExternalReferenceCode({
			externalReferenceCode: updatedObjectFolder.externalReferenceCode,
			id: updatedObjectFolder.id,
			label: updatedObjectFolder.label,
			name: updatedObjectFolder.name,
			objectFolderItems: updatedObjectFolder.objectFolderItems,
		});

		dispatch({
			payload: {
				newObjectDefinitionNodePosition: {
					x: node.position.x,
					y: node.position.y,
				},
				objectDefinitionNodes: nodes,
				objectRelationshipEdges: edges,
				updatedObjectDefinitionNodeId: node.data?.id as number,
				updatedObjectFolder,
			},
			type: TYPES.SET_SELECTED_OBJECT_DEFINITION_NODE_POSITION,
		});

		if (!showChangesSaved) {
			dispatch({
				payload: {updatedShowChangesSaved: true},
				type: TYPES.SET_SHOW_CHANGES_SAVED,
			});
		}
	};

	const setNodeHandleConnection = (nodeHandleConnectable: boolean) => {
		dispatch({
			payload: {
				nodeHandleConnectable,
			},
			type: TYPES.SET_NODE_HANDLE_CONNECTION,
		});
	};

	const updateModelBuilderStructure = async (
		newObjectRelationshipId: number
	) => {
		const payload = await getUpdatedModelBuilderStructurePayload(
			baseResourceURL,
			selectedObjectFolder.name
		);

		dispatch({
			payload: {
				...payload,
				dispatch,
				rightSidebarType: 'objectRelationshipDetails',
				selectedObjectRelationshipId: newObjectRelationshipId,
			},
			type: TYPES.UPDATE_MODEL_BUILDER_STRUCTURE,
		});

		dispatch({
			payload: {
				selectedObjectRelationshipId: newObjectRelationshipId,
			},
			type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
		});
	};

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			{showAddObjectRelationshipModal && (
				<ModalAddObjectRelationship
					baseResourceURL={baseResourceURL}
					handleOnClose={() =>
						setShowAddObjectRelationshipModal(false)
					}
					hasDefinedObjectDefinitionTarget
					objectDefinitionExternalReferenceCode1={
						newObjectRelationshipSourceNodeProps?.sourceNode.erc!
					}
					objectDefinitionExternalReferenceCode2={
						newObjectRelationshipSourceNodeProps?.targetNode.erc!
					}
					objectRelationshipParameterRequired={
						newObjectRelationshipSourceNodeProps?.parameterRequired!
					}
					onAfterAddObjectRelationship={(newObjectRelationship) =>
						updateModelBuilderStructure(newObjectRelationship.id)
					}
					reload={false}
				/>
			)}

			<ReactFlowDefault
				connectionLineStyle={{stroke: '#0B5FFF'}}
				connectionLineType={ConnectionLineType.SmoothStep}
				connectionMode={ConnectionMode.Loose}
				dir="ltr"
				edgeTypes={EDGE_TYPES}
				elements={elements}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}
				onConnect={onConnect}
				onConnectStart={() => setNodeHandleConnection(true)}
				onConnectStop={() => setNodeHandleConnection(false)}
				onNodeDragStop={(_, node) => onNodeDragStop(node)}
			>
				<Background color="#C0C1C3" gap={18} size={1} />

				{!isLoadingObjectFolder ? (
					<div
						className={classNames(
							'lfr__object-model-builder-control-container',
							{
								'sidebars-closed': !showSidebars,
								'sidebars-open': showSidebars,
							}
						)}
					>
						<Controls
							className="lfr__object-model-builder-controls"
							showInteractive={false}
						/>

						<MiniMap
							className="lfr__object-model-builder-minimap"
							maskColor="none"
							nodeBorderRadius={8}
							nodeColor="#F7F8F9"
							nodeStrokeColor="#0B5FFF"
							nodeStrokeWidth={10}
							style={{
								backgroundColor: '#F7F8F9',
								border: '4px solid #A7A9BC',
								borderRadius: '8px',
							}}
						/>
					</div>
				) : (
					<div className="lfr-objects__model-builder-diagram-area-loading">
						<span
							aria-hidden="true"
							className="loading-animation-lg loading-animation-primary loading-animation-squares"
						/>
					</div>
				)}
			</ReactFlowDefault>
		</div>
	);
}

export default DiagramBuilder;
