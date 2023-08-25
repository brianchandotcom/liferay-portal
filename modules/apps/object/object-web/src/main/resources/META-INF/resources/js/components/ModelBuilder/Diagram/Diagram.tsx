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

import {DefinitionNode} from '../DefinitionNode/DefinitionNode';
import {EmptyNode} from '../DefinitionNode/EmptyNode';

import './Diagram.scss';

import {API} from '@liferay/object-js-components-web';
import React, {MouseEvent, useCallback} from 'react';

import {ViewObjectDefinitionsModals} from '../../ViewObjectDefinitions/ViewObjectDefinitions';
import DefaultEdge from '../Edges/DefaultEdge';
import SelfEdge from '../Edges/SelfEdge';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

const NODE_TYPES = {
	emptyNode: EmptyNode,
	objectDefinition: DefinitionNode,
};

const EDGE_TYPES = {
	default: DefaultEdge,
	self: SelfEdge,
};

function DiagramBuilder({
	setShowModal,
}: {
	setShowModal: (value: ViewObjectDefinitionsModals) => void;
}) {
	const [
		{elements, selectedFolder, showChangesSaved},
		dispatch,
	] = useFolderContext();

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
		const folder = await API.getFolderByERC(
			selectedFolder.externalReferenceCode
		);

		const updatedObjectFolderItems = folder.objectFolderItems.map(
			(folderItem) => {
				if (
					folderItem.objectDefinitionExternalReferenceCode ===
					node.data?.externalReferenceCode
				) {
					return {
						...folderItem,
						positionX: node.position.x,
						positionY: node.position.y,
					};
				}

				return folderItem;
			}
		);

		const updatedFolder = {
			externalReferenceCode: selectedFolder.externalReferenceCode,
			id: selectedFolder.id,
			label: selectedFolder.label,
			name: selectedFolder.name,
			objectFolderItems: updatedObjectFolderItems,
		};

		API.putObjectFolderByERC(updatedFolder);

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
				elements={elements.length ? elements : emptyNode}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}
				onConnect={onConnect}
				onNodeDragStop={onNodeDragStop}
			>
				<Background size={1} />

				<Controls showInteractive={false} />

				<MiniMap />
			</ReactFlow>
		</div>
	);
}

export default DiagramBuilder;
