/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Collaborator,
	CollaboratorService,
	PermissionOption,
	ShareModalContent,
	openModal,
	openToast,
} from 'frontend-js-components-web';

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

		const initialCollaborators: Collaborator[] = items.reverse().map(
			({actionIds, dateExpired, id, name, portrait, share, type}) =>
				({
					actionIds: actionIds.sort().join(','),
					dateExpired,
					share,
					type,
					user: {
						id: id.toString(),
						image: portrait,
						name,
					},
				}) as Collaborator
		);

		openModal({
			contentComponent: ({closeModal}: {closeModal: () => void}) =>
				ShareModalContent({
					autocompleteHelpText: Liferay.Language.get(
						'this-view-can-be-used-by-users-with-whom-you-have-shared-it-but-only-you-can-modify-it'
					),
					autocompleteLabel: Liferay.Language.get('add-people'),
					autocompleteURL: AUTOCOMPLETE_URL,
					closeModal,
					collaboratorURL: COLLABORATOR_URL,
					collaboratorsListTitle: Liferay.Language.get(
						'who-can-see-this-view'
					),
					creator: {
						contentType: 'UserAccount',
						id: Liferay.ThemeDisplay.getUserId().toString(),
						name: Liferay.ThemeDisplay.getUserName(),
					},
					initialCollaborators,
					itemId,
					permissionOptions: PERMISSION_OPTIONS,
					showExpirationDate: false,
					title,
				}),
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
