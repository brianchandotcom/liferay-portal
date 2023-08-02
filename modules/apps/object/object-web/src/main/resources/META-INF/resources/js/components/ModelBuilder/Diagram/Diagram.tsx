/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactFlow, {Background, Controls, MiniMap} from 'react-flow-renderer';

import {DefinitionNode} from '../DefinitionNode/DefinitionNode';

import './Diagram.scss';

import React from 'react';

import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';

function DiagramBuilder() {
	const NODE_TYPES = {
		objectDefinition: DefinitionNode,
	};

	const [{objectDefinitionNodes}] = useFolderContext();

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			<ReactFlow
				elements={objectDefinitionNodes}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}
			>
				<Background size={1} />

				<Controls showInteractive={false} />

				<MiniMap />
			</ReactFlow>
		</div>
	);
}

export default DiagramBuilder;
