/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';

import ShareModalContent, {collaborator} from '../../modal/ShareModalContent';

export default function shareAction({
	autocompleteUserURL,
	shareActionURL,
	title,
}: {
	autocompleteUserURL: string;
	shareActionURL: string;
	title: string;
}) {
	fetch(shareActionURL, {method: 'GET'})
		.then((response) => response.json())
		.then(({items}) => {
			const initialCollaborators: collaborator[] = items.map(
				(collaboratorItem: any) =>
					({
						allowResharing: collaboratorItem.share,
						expirationDate:
							collaboratorItem.dateExpired?.split('T')?.[0] || '',
						isOwner:
							collaboratorItem.creator.id === collaboratorItem.id,
						permission: collaboratorItem.actionIds.includes(
							'DOWNLOAD'
						)
							? 'VIEW-AND-DOWNLOAD'
							: 'VIEW',
						type: collaboratorItem.type,
						user: {
							id: collaboratorItem.id,
							name: collaboratorItem.name,
						},
					}) as collaborator
			);

			openModal({
				className: 'share-modal',
				contentComponent: ({closeModal}: {closeModal: () => void}) =>
					ShareModalContent({
						autocompleteUserURL,
						closeModal,
						initialCollaborators,
						shareActionURL,
						title,
					}),
				size: 'md',
			});
		})
		.catch((error) => {
			openToast({
				message:
					error.message ||
					Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		});
}
