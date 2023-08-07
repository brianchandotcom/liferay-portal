/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactFlow, {
	Background,
	Connection,
	ConnectionMode,
	Controls,
	MiniMap,
	addEdge,
} from 'react-flow-renderer';

import {DefinitionNode} from '../DefinitionNode/DefinitionNode';

import './Diagram.scss';

import React, {useCallback} from 'react';

import DefaultEdge from '../Edges/DefaultEdge';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

const NODE_TYPES = {
	objectDefinition: DefinitionNode,
};

const EDGE_TYPES = {
	default: DefaultEdge,
};

function DiagramBuilder() {
	const [{objectDefinitionNodes}, dispatch] = useFolderContext();

	// @ts-ignore

	const onConnect = useCallback(
		(connection: Connection) => {
			const elements = addEdge(connection, objectDefinitionNodes);

			dispatch({
				payload: {newElements: elements},
				type: TYPES.SET_ELEMENTS,
			});
		},
		[dispatch, objectDefinitionNodes]
	);

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			<ReactFlow
				connectionMode={ConnectionMode.Loose}
				edgeTypes={EDGE_TYPES}
				elements={objectDefinitionNodes}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}

				// @ts-ignore

				onConnect={onConnect}
			>
				<Background size={1} />

				<Controls showInteractive={false} />

				<MiniMap />
			</ReactFlow>
		</div>
	);
}

export default DiagramBuilder;
