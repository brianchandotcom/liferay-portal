/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';
import {NodeProps} from 'react-flow-renderer';

import './DefinitionNode.scss';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectDefinitionNodeData} from '../types';
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
					hasObjectDefinitionDeleteResourcePermission
				}
				hasObjectDefinitionManagePermissionsResourcePermission={
					hasObjectDefinitionManagePermissionsResourcePermission
				}
				isLinkedNode={isLinkedNode}
				objectDefinitionLabel={label}
				status={status!}
				system={system as boolean}
			/>

			<NodeFields
				defaultLanguageId={defaultLanguageId as Liferay.Language.Locale}
				objectFields={objectFields}
				showAll={showAllFields}
			/>

			<NodeFooter
				setShowAllFields={setShowAllFields}
				showAllFields={showAllFields}
			/>
		</div>
	);
}
