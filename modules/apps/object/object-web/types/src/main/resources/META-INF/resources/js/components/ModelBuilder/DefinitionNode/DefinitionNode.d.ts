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
export declare function DefinitionNode({
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
}: NodeProps<DefinitionNodeProps>): JSX.Element;
export {};
