/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactFlow, {
	Background,
	Connection,
	ConnectionMode,
	Controls,
	Edge,
	MiniMap,
	Node,
	addEdge,
} from 'react-flow-renderer';

import {EmptyNode} from '../ObjectDefinitionNode/EmptyNode';
import {ObjectDefinitionNode} from '../ObjectDefinitionNode/ObjectDefinitionNode';

import './Diagram.scss';

import {API} from '@liferay/object-js-components-web';
import React, {MouseEvent, useCallback} from 'react';

import DefaultObjectRelationshipEdge from '../Edges/DefaultObjectRelationshipEdge';
import SelfObjectRelationshipEdge from '../Edges/SelfObjectRelationshipEdge';
import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

const NODE_TYPES = {
	emptyNode: EmptyNode,
	objectDefinitionNode: ObjectDefinitionNode,
};

const EDGE_TYPES = {
	defaultObjectRelationshipEdge: DefaultObjectRelationshipEdge,
	selfObjectRelationshipEdge: SelfObjectRelationshipEdge,
};

function DiagramBuilder({
	setShowModal,
}: {
	setShowModal: (value: ModelBuilderModals) => void;
}) {
	const [
		{
			elements,
			isLoadingObjectFolder,
			selectedObjectFolder,
			showChangesSaved,
		},
		dispatch,
	] = useObjectFolderContext();

	const emptyNode = [
		{
			data: {
				setShowModal,
			},
			id: 'empty',
			position: {
				x: 400,
				y: 400,
			},
			type: 'emptyNode',
		},
	];

	const onConnect = useCallback(
		(connection: Connection | Edge) => {
			const newElements = addEdge(connection, elements);

			dispatch({
				payload: {newElements},
				type: TYPES.SET_ELEMENTS,
			});
		},
		[dispatch, elements]
	);

	const onNodeDragStop = async (
		event: MouseEvent,
		node: Node<ObjectDefinitionNodeData>
	) => {
		const objectFolder = await API.getObjectFolderByExternalReferenceCode(
			selectedObjectFolder.externalReferenceCode
		);

		const updatedObjectFolderItems = objectFolder.objectFolderItems.map(
			(objectFolderItem) => {
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
			}
		);

		const updatedObjectFolder = {
			externalReferenceCode: selectedObjectFolder.externalReferenceCode,
			id: selectedObjectFolder.id,
			label: selectedObjectFolder.label,
			name: selectedObjectFolder.name,
			objectFolderItems: updatedObjectFolderItems,
		};

		API.putObjectFolderByExternalReferenceCode(updatedObjectFolder);

		if (!showChangesSaved) {
			dispatch({
				payload: {updatedShowChangesSaved: true},
				type: TYPES.SET_SHOW_CHANGES_SAVED,
			});
		}
	};

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			<ReactFlow
				connectionMode={ConnectionMode.Loose}
				edgeTypes={EDGE_TYPES}
				elements={
					!isLoadingObjectFolder
						? elements.length
							? elements
							: emptyNode
						: []
				}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}
				onConnect={onConnect}
				onNodeDragStop={onNodeDragStop}
			>
				<Background size={1} />

				{!isLoadingObjectFolder ? (
					<>
						<Controls showInteractive={false} />
						<MiniMap />
					</>
				) : (
					<div className="lfr-objects__model-builder-diagram-area-loading">
						<span
							aria-hidden="true"
							className="loading-animation-lg loading-animation-primary loading-animation-squares"
						/>
					</div>
				)}
			</ReactFlow>
		</div>
	);
}

export default DiagramBuilder;
