/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useState} from 'react';
import {NodeProps} from 'react-flow-renderer';

import './DefinitionNode.scss';
import {FieldNode} from '../types';
import NodeFields from './NodeFields';
import NodeFooter from './NodeFooter';
import NodeHeader from './NodeHeader';

interface DefinitionNodeProps {
	creationLanguageId: Liferay.Language.Locale;
	hasDeleteResourcePermission: boolean;
	hasManagePermissionsResourcePermission: boolean;
	isLinkedNode: boolean;
	label: string;
	nodeSelected: boolean;
	objectFields: FieldNode[];
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	system: boolean;
}

export function DefinitionNode({
	data: {
		creationLanguageId,
		hasDeleteResourcePermission,
		hasManagePermissionsResourcePermission,
		isLinkedNode,
		label,
		nodeSelected,
		objectFields,
		status,
		system,
	},
}: NodeProps<DefinitionNodeProps>) {
	const [showAllFields, setShowAllFields] = useState<boolean>(false);

	return (
		<div
			className={classNames('lfr-objects__model-builder-node-container', {
				'lfr-objects__model-builder-node-container--selected': nodeSelected,
			})}
		>
			<NodeHeader
				hasObjectDefinitionDeleteResourcePermission={
					hasDeleteResourcePermission
				}
				hasObjectDefinitionManagePermissionsResourcePermission={
					hasManagePermissionsResourcePermission
				}
				isLinkedNode={isLinkedNode}
				objectDefinitionLabel={label}
				status={status}
				system={system}
			/>

			<NodeFields
				defaultLanguageId={creationLanguageId}
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
