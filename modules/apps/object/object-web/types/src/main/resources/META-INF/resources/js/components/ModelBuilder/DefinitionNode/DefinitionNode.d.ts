/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {NodeProps} from 'react-flow-renderer';
import './DefinitionNode.scss';
import {FieldNode} from '../types';
interface DefinitionNodeProps {
	creationLanguageId: Liferay.Language.Locale;
	hasDeleteResourcePermission: boolean;
	hasManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionPublished: boolean;
	isLinkedNode: boolean;
	nodeSelected: boolean;
	objectDefinitionLabel: string;
	objectFields: FieldNode[];
	system: boolean;
}
export declare function DefinitionNode({
	data: {
		creationLanguageId,
		hasDeleteResourcePermission,
		hasManagePermissionsResourcePermission,
		hasObjectDefinitionPublished,
		isLinkedNode,
		nodeSelected,
		objectDefinitionLabel,
		objectFields,
		system,
	},
}: NodeProps<DefinitionNodeProps>): JSX.Element;
export {};
