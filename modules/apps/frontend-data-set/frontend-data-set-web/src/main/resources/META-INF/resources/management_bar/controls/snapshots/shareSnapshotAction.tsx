/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AutocompleteItem,
	COLLABORATOR_TYPE,
	Collaborator,
	CollaboratorService,
	PermissionOption,
	ShareModalContent,
	openModal,
	openToast,
} from 'frontend-js-components-web';
import React from 'react';

const AUTOCOMPLETE_URL =
	'/o/search/v1.0/search?emptySearch=true' +
	'&entryClassNames=com.liferay.portal.kernel.model.User,' +
	'com.liferay.portal.kernel.model.UserGroup' +
	'&nestedFields=embedded';

const COLLABORATOR_URL =
	'/o/data-set-admin/snapshots/{objectEntryId}/collaborators';

const PERMISSION_OPTIONS: PermissionOption[] = [
	{
		label: Liferay.Language.get('view'),
		value: 'VIEW',
	},
];

const _transformSourceItems = (items: any[]): AutocompleteItem[] =>
	items.map((item) => {
		if (item.entryClassName?.includes(COLLABORATOR_TYPE.USER_GROUP)) {
			return {
				type: COLLABORATOR_TYPE.USER_GROUP,
				user: {
					id: item.embedded.id.toString(),
					name: item.embedded.name,
				},
			};
		}

		return {
			type: COLLABORATOR_TYPE.USER,
			user: {
				emailAddress: item.embedded.emailAddress,
				id: item.embedded.id.toString(),
				image: item.embedded.image,
				name: item.embedded.name,
			},
		};
	});

export default async function shareSnapshotAction({
	itemId,
	title,
}: {
	itemId: number;
	title: string;
}) {
	try {
		const items = await CollaboratorService.getCollaborators(
			COLLABORATOR_URL,
			itemId
		);

		const initialCollaborators: Collaborator[] = [...items].reverse().map(
			({actionIds, dateExpired, id, name, portrait, share, type}) =>
				({
					actionIds: actionIds.sort().join(','),
					dateExpired,
					share,
					type,
					user: {
						id: id?.toString() ?? '',
						image: portrait,
						name,
					},
				}) as Collaborator
		);

		openModal({
			contentComponent: ({closeModal}: {closeModal: () => void}) => (
				<ShareModalContent
					autocompleteHelpText={Liferay.Language.get(
						'this-view-can-be-used-by-users-with-whom-you-have-shared-it-but-only-you-can-modify-it'
					)}
					autocompleteLabel={Liferay.Language.get('add-people')}
					autocompleteURL={AUTOCOMPLETE_URL}
					closeModal={closeModal}
					collaboratorsListTitle={Liferay.Language.get(
						'who-can-see-this-view'
					)}
					creator={{
						contentType: 'UserAccount',
						id: Liferay.ThemeDisplay.getUserId(),
						name: Liferay.ThemeDisplay.getUserName(),
					}}
					initialCollaborators={initialCollaborators}
					onCollaboratorsUpdate={(collaborators) =>
						CollaboratorService.updateCollaborators(
							COLLABORATOR_URL,
							itemId,
							collaborators.map(
								({actionIds, share, type, user}) => ({
									actionIds: actionIds
										? actionIds.split(',')
										: [],
									id: user.id,
									share,
									type,
								})
							)
						)
					}
					permissionOptions={PERMISSION_OPTIONS}
					showAllowResharing={false}
					showExpirationDate={false}
					title={title}
					transformSourceItems={_transformSourceItems}
				/>
			),
			size: 'md',
		});
	}
	catch (error: any) {
		openToast({
			message:
				error.message ||
				Liferay.Language.get('an-unexpected-error-occurred'),
			type: 'danger',
		});
	}
}
