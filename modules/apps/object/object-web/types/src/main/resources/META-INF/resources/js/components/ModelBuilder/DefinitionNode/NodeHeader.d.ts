/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './NodeHeader.scss';
interface NodeHeaderProps {
	hasDeleteResourcePermission: boolean;
	hasManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionPublished: boolean;
	isLinkedNode: boolean;
	objectDefinitionLabel: string;
	system: boolean;
}
export default function NodeHeader({
	hasDeleteResourcePermission,
	hasManagePermissionsResourcePermission,
	hasObjectDefinitionPublished,
	isLinkedNode,
	objectDefinitionLabel,
	system,
}: NodeHeaderProps): JSX.Element;
export {};
