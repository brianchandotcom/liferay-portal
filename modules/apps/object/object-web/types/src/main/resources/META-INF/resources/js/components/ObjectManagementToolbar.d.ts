/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ObjectManagementToolbarProps {
	backURL: string;
	hasPublishObjectPermission: boolean;
	hasUpdateObjectDefinitionPermission: boolean;
	isApproved: boolean;
	isRootDescendantNode: boolean;
	label: string;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	onSubmit: (draft: boolean) => void;
	portletNamespace: string;
	screenNavigationCategoryKey: string;
	system: boolean;
}
export default function ObjectManagementToolbar({
	backURL,
	hasPublishObjectPermission,
	hasUpdateObjectDefinitionPermission,
	isApproved,
	isRootDescendantNode,
	label,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	onSubmit,
	portletNamespace,
	screenNavigationCategoryKey,
	system,
}: ObjectManagementToolbarProps): JSX.Element;
export {};
