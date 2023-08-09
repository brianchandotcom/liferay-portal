/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactFlow, {Background, Controls, MiniMap} from 'react-flow-renderer';

import {DefinitionNode} from '../DefinitionNode/DefinitionNode';

import './Diagram.scss';

import React from 'react';

function DiagramBuilder() {
	const NODE_TYPES = {
		objectDefinition: DefinitionNode,
	};

	const INITIAL_NODES = [
		{
			data: {
				creationLanguageId: 'en_US',
				hasDeleteResourcePermission: true,
				hasManagePermissionsResourcePermission: true,
				hasObjectDefinitionPublished: false,
				isLinkedNode: false,
				nodeSelected: true,
				objectDefinitionLabel: 'Postal Address',
				objectDefinitionName: 'portalAddress',
				objectFields: [
					{
						businessType: 'LongInteger',
						label: {en_US: 'ID'},
						name: 'id',
						primaryKey: true,
						selected: false,
					},
					{
						businessType: 'Text',
						label: {en_US: 'External Reference Code'},
						name: 'erc',
						primaryKey: false,
						selected: true,
					},
					{
						businessType: 'Text',
						label: {en_US: 'Name'},
						name: 'name',
						primaryKey: false,
						selected: false,
					},
					{
						businessType: 'Text',
						label: {en_US: 'Street 1'},
						name: 'street1',
						primaryKey: false,
						selected: false,
					},
					{
						businessType: 'Text',
						label: {en_US: 'Author'},
						name: 'author',
						primaryKey: false,
						selected: false,
					},
					{
						businessType: 'Date',
						label: {en_US: 'Create Date'},
						name: 'createDate',
						primaryKey: false,
						selected: false,
					},
					{
						businessType: 'Date',
						label: {en_US: 'Modified Date'},
						name: 'modifiedDate',
						primaryKey: false,
						selected: false,
					},
					{
						businessType: 'Text',
						label: {en_US: 'Status'},
						name: 'status',
						primaryKey: false,
						selected: false,
					},
				],
				system: false,
			},
			id: 'A',
			position: {
				x: 450,
				y: 370,
			},
			type: 'objectDefinition',
		},
	];

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			<ReactFlow
				elements={INITIAL_NODES}
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
