/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {
	Collaborator as GenericCollaborator,
	CollaboratorPayload,
	PermissionOption,
	ShareModalContent as GenericShareModalContent,
	ShareModalUserAccount,
	ShareModalUserGroup,
	formatDateToISO,
} from 'frontend-js-components-web';
import React from 'react';

import {
	ExternalUser,
	UserAccount,
	UserGroup,
} from '../../../common/types/UserAccount';
import {
	COLLABORATOR_TYPE,
	CollaboratorType,
	OBJECT_ENTRY_FOLDER_CLASS_NAME,
} from '../../../common/utils/constants';
import isEmailAddressValid from '../../../common/utils/isEmailAddressValid';

export interface Collaborator {
	actionIds: string;
	dateExpired?: string;
	error?: string;
	share: boolean;
	toBeShared?: boolean;
	type: CollaboratorType;
	user: ExternalUser | UserAccount | UserGroup;
}

const EXTERNAL_USER_PERMISSION_OPTIONS: PermissionOption[] = [
	{
		label: Liferay.Language.get('view-and-download'),
		value: 'VIEW',
	},
];

const FOLDER_PERMISSION_OPTIONS: PermissionOption[] = [
	{
		label: Liferay.Language.get('view-and-download'),
		value: 'VIEW',
	},
	{
		label: Liferay.Language.get('view-download-and-update'),
		value: ['UPDATE', 'VIEW'].join(','),
	},
];

const PERMISSION_OPTIONS: PermissionOption[] = [
	{
		label: Liferay.Language.get('view-and-download'),
		value: 'VIEW',
	},
	{
		label: Liferay.Language.get('view-download-and-comment'),
		value: ['ADD_DISCUSSION', 'VIEW'].join(','),
	},
	{
		label: Liferay.Language.get('view-download-comment-and-update'),
		value: ['ADD_DISCUSSION', 'UPDATE', 'VIEW'].join(','),
	},
];

export default function CMSShareModalContent({
	autocompleteURL,
	canManageCollaborators = true,
	closeModal,
	collaboratorURL,
	creator,
	entryClassName,
	externalUserSharingEnabled = false,
	initialCollaborators,
	itemId,
	title,
}: {
	autocompleteURL: string;
	canManageCollaborators?: boolean;
	closeModal: () => void;
	collaboratorURL: string;
	creator: {
		contentType: string;
		id: string;
		image?: string;
		name: string;
	};
	entryClassName?: string;
	externalUserSharingEnabled?: boolean;
	initialCollaborators: Collaborator[];
	itemId: number;
	title: string;
}) {
	const isFolder = entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME;

	const filterCollaborators = (collaborator: GenericCollaborator) =>
		externalUserSharingEnabled ||
		(collaborator.type as CollaboratorType) !==
			COLLABORATOR_TYPE.EXTERNAL_USER;

	const transformSourceItems = (
		items: Array<{
			type: CollaboratorType;
			user: ShareModalUserAccount | ShareModalUserGroup;
		}>,
		query: string
	) => {
		const trimmedQuery = query.trim();

		const offerExternalUserInvite =
			externalUserSharingEnabled &&
			!isFolder &&
			isEmailAddressValid(trimmedQuery) &&
			!items.some(
				({user}) =>
					(
						user as ShareModalUserAccount
					).emailAddress?.toLowerCase() ===
						trimmedQuery.toLowerCase() ||
					user.id.toLowerCase() === trimmedQuery.toLowerCase()
			);

		if (!offerExternalUserInvite) {
			return items;
		}

		return [
			...items,
			{
				type: COLLABORATOR_TYPE.EXTERNAL_USER as unknown as CollaboratorType,
				user: {
					emailAddress: trimmedQuery,
					id: trimmedQuery,
					name: trimmedQuery,
				},
			},
		];
	};

	const transformSubmitPayload = ({
		actionIds,
		dateExpired,
		share,
		type,
		user,
	}: GenericCollaborator): CollaboratorPayload => {
		const payload: CollaboratorPayload = {
			actionIds: actionIds ? actionIds.split(',') : [],
			share,
			type,
		};

		if (dateExpired) {
			payload.dateExpired = formatDateToISO(dateExpired);
		}

		if ((type as CollaboratorType) === COLLABORATOR_TYPE.EXTERNAL_USER) {
			payload.emailAddress = (user as ShareModalUserAccount).emailAddress;
		}
		else {
			payload.id = Number(user.id);
		}

		return payload;
	};

	const renderAutocompleteItem = ({
		type,
		user,
	}: {
		type: CollaboratorType;
		user: ShareModalUserAccount | ShareModalUserGroup;
	}) => (
		<div className="autofit-row autofit-row-center">
			<div className="autofit-col c-mr-1">
				<ClaySticker className="sticker-user-icon" size="sm">
					{type === COLLABORATOR_TYPE.USER ? (
						'image' in user && user.image ? (
							<div className="sticker-overlay">
								<img
									alt={user.name}
									className="sticker-img"
									src={user.image}
								/>
							</div>
						) : (
							<ClayIcon symbol="user" />
						)
					) : type === COLLABORATOR_TYPE.EXTERNAL_USER ? (
						<ClayIcon symbol="envelope-closed" />
					) : (
						<ClayIcon symbol="users" />
					)}
				</ClaySticker>
			</div>

			<div className="autofit-col">
				<span className="text-weight-semibold">
					{type === COLLABORATOR_TYPE.EXTERNAL_USER ? (
						<>
							<span className="c-mr-1">
								{Liferay.Language.get('invite-external-user')}
							</span>

							<span className="text-secondary text-weight-normal">
								{user.name}
							</span>
						</>
					) : (
						<>
							<span className="c-mr-1">{user.name}</span>

							{'emailAddress' in user && `(${user.emailAddress})`}
						</>
					)}
				</span>
			</div>
		</div>
	);

	const renderCollaboratorBadge = ({
		toBeShared,
		type,
	}: {
		toBeShared?: boolean;
		type: CollaboratorType;
		user: ShareModalUserAccount | ShareModalUserGroup;
	}) => {
		const isExternalUser =
			externalUserSharingEnabled &&
			type === COLLABORATOR_TYPE.EXTERNAL_USER;

		if (isExternalUser) {
			return (
				<span className="inline-item inline-item-after label label-inverse-light">
					<span className="label-item label-item-expand text-nowrap">
						{toBeShared
							? Liferay.Language.get('pending')
							: Liferay.Language.get('invited')}
					</span>
				</span>
			);
		}

		if (toBeShared) {
			return (
				<span className="inline-item inline-item-after label label-inverse-light">
					<span className="label-item label-item-expand text-nowrap">
						{Liferay.Language.get('to-be-shared')}
					</span>
				</span>
			);
		}

		return null;
	};

	const renderCollaboratorStickerIcon = ({
		type,
		user,
	}: {
		type: CollaboratorType;
		user: ShareModalUserAccount | ShareModalUserGroup;
	}) => {
		if (type === COLLABORATOR_TYPE.EXTERNAL_USER) {
			return <ClayIcon symbol="envelope-closed" />;
		}

		if (type === COLLABORATOR_TYPE.USER_GROUP) {
			return <ClayIcon symbol="users" />;
		}

		if ('image' in user && user.image) {
			return (
				<img
					alt={user.name}
					className="sticker-img"
					src={(user as ShareModalUserAccount).image}
				/>
			);
		}

		return <ClayIcon symbol="user" />;
	};

	const resolvePermissionOptions = (collaborator: GenericCollaborator) => {
		if (
			(collaborator.type as CollaboratorType) ===
			COLLABORATOR_TYPE.EXTERNAL_USER
		) {
			return EXTERNAL_USER_PERMISSION_OPTIONS;
		}

		if (isFolder) {
			return FOLDER_PERMISSION_OPTIONS;
		}

		return PERMISSION_OPTIONS;
	};

	return (
		<GenericShareModalContent
			alwaysShowPermissionSelector
			autocompleteURL={autocompleteURL}
			canManageCollaborators={canManageCollaborators}
			closeModal={closeModal}
			collaboratorURL={collaboratorURL}
			creator={creator}
			filterCollaborators={filterCollaborators}
			initialCollaborators={
				initialCollaborators as unknown as GenericCollaborator[]
			}
			itemId={itemId}
			permissionOptions={resolvePermissionOptions}
			renderAutocompleteItem={renderAutocompleteItem}
			renderCollaboratorBadge={renderCollaboratorBadge}
			renderCollaboratorStickerIcon={renderCollaboratorStickerIcon}
			title={title}
			transformSourceItems={
				transformSourceItems as unknown as (
					items: Array<{
						type: import('frontend-js-components-web').CollaboratorType;
						user: ShareModalUserAccount | ShareModalUserGroup;
					}>,
					query: string
				) => Array<{
					type: import('frontend-js-components-web').CollaboratorType;
					user: ShareModalUserAccount | ShareModalUserGroup;
				}>
			}
			transformSubmitPayload={transformSubmitPayload}
		/>
	);
}
