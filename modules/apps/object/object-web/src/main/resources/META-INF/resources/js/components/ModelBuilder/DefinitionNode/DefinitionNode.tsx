/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';
import {Handle, NodeProps, Position} from 'react-flow-renderer';

import './DefinitionNode.scss';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectDefinitionNodeData, ObjectFieldNode} from '../types';
import NodeFields from './NodeFields';
import NodeFooter from './NodeFooter';
import NodeHeader from './NodeHeader';

export function DefinitionNode({
	data: {
		defaultLanguageId,
		hasObjectDefinitionDeleteResourcePermission,
		hasObjectDefinitionManagePermissionsResourcePermission,
		isLinkedNode,
		label,
		name,
		nodeSelected,
		objectFields,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>) {
	const [showAllFields, setShowAllFields] = useState<boolean>(false);
	const [_, dispatch] = useFolderContext();

	return (
		<div
			className={classNames('lfr-objects__model-builder-node-container', {
				'lfr-objects__model-builder-node-container--selected': nodeSelected,
			})}
			onClick={() => {
				dispatch({
					payload: {
						selectedObjectDefinitionName: name as string,
					},
					type: TYPES.SET_SELECTED_NODE,
				});
			}}
		>
			<NodeHeader
				hasObjectDefinitionDeleteResourcePermission={
					hasObjectDefinitionDeleteResourcePermission as boolean
				}
				hasObjectDefinitionManagePermissionsResourcePermission={
					hasObjectDefinitionManagePermissionsResourcePermission as boolean
				}
				isLinkedNode={isLinkedNode as boolean}
				objectDefinitionLabel={label as string}
				status={status!}
				system={system as boolean}
			/>

			<NodeFields
				defaultLanguageId={defaultLanguageId as Liferay.Language.Locale}
				objectFields={objectFields as ObjectFieldNode[]}
				showAll={showAllFields}
			/>

			<NodeFooter
				setShowAllFields={setShowAllFields}
				showAllFields={showAllFields}
			/>

			<Handle
				className="lfr-objects__model-builder-node-handle"
				hidden
				id={name}
				position={Position.Left}
				style={{
					background: '#80ACFF',
					height: '12px',
					left: '-30px',
					width: '12px',
				}}
				type="source"
			/>
		</div>
	);
}
