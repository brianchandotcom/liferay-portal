/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {NodeProps} from 'react-flow-renderer';
import './NodeContainer.scss';
export declare function ObjectDefinitionNode({
	data: {
		defaultLanguageId,
		externalReferenceCode,
		hasObjectDefinitionDeleteResourcePermission,
		hasObjectDefinitionManagePermissionsResourcePermission,
		hasSelfObjectRelationships,
		id,
		label,
		linkedObjectDefinition,
		name,
		objectFields,
		selected,
		status,
		system,
	},
}: NodeProps<ObjectDefinitionNodeData>): JSX.Element;
