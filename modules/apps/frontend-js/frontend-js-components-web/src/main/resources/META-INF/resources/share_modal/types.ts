/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const COLLABORATOR_TYPE = {
	USER: 'User',
	USER_GROUP: 'UserGroup',
} as const;

export type CollaboratorType =
	(typeof COLLABORATOR_TYPE)[keyof typeof COLLABORATOR_TYPE];

export interface ShareModalUserAccount {
	emailAddress?: string;
	id: string;
	image?: string;
	name: string;
}

export interface ShareModalUserGroup {
	id: string;
	name: string;
}

export interface Collaborator {
	actionIds: string;
	dateExpired?: string;
	error?: string;
	share: boolean;
	toBeShared?: boolean;
	type: CollaboratorType;
	user: ShareModalUserAccount | ShareModalUserGroup;
}

export interface PermissionOption {
	label: string;
	value: string;
}

export interface ShareModalCreator {
	contentType: string;
	id: string;
	image?: string;
	name: string;
}
