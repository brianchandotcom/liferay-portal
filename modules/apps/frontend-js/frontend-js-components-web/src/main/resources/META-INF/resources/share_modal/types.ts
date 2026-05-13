/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ReactNode} from 'react';

import type {CollaboratorPayload} from './CollaboratorService';

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

export interface AutocompleteItem {
	type: CollaboratorType;
	user: ShareModalUserAccount | ShareModalUserGroup;
}

export type CollaboratorIconProps = AutocompleteItem;

export interface CollaboratorBadgeProps extends AutocompleteItem {
	toBeShared?: boolean;
}

export interface ShareModalContentProps {
	alwaysShowPermissionSelector?: boolean;
	autocompleteHelpText?: string;
	autocompleteItem?: (props: CollaboratorIconProps) => ReactNode;
	autocompleteLabel?: string;
	autocompleteURL: string;
	canManageCollaborators?: boolean;
	closeModal: () => void;
	collaboratorBadge?: (props: CollaboratorBadgeProps) => ReactNode;
	collaboratorStickerIcon?: (props: CollaboratorIconProps) => ReactNode;
	collaboratorURL: string;
	collaboratorsListTitle?: string;
	creator: ShareModalCreator;
	filterCollaborators?: (collaborator: Collaborator) => boolean;
	initialCollaborators: Collaborator[];
	itemId: number;
	onAutocompleteChange?: (item: AutocompleteItem | undefined) => void;
	permissionOptions:
		| PermissionOption[]
		| ((collaborator: Collaborator) => PermissionOption[]);
	showAllowResharing?: boolean;
	showExpirationDate?: boolean;
	title: string;
	transformSourceItems?: (
		items: AutocompleteItem[],
		query: string
	) => AutocompleteItem[];
	transformSubmitPayload?: (
		collaborator: Collaborator
	) => CollaboratorPayload;
}
