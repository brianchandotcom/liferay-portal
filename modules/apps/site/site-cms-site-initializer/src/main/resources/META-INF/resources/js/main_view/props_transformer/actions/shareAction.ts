/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';

import ShareModalContent, {collaborator} from '../../modal/ShareModalContent';

export default function shareAction({
	autocompleteURL,
	collaboratorURL,
	creator,
	itemId,
	title,
}: {
	autocompleteURL: string;
	collaboratorURL: string;
	creator: {
		contentType: string;
		id: number;
		image?: string;
		name: string;
	};
	itemId: number;
	title: string;
}) {
	const collaboratorURLWithId = collaboratorURL.replace(
		'{objectEntryId}',
		itemId.toString()
	);

	fetch(collaboratorURLWithId, {
		headers: {
			'Accept': 'application/json',
			'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
			'Content-Type': 'application/json',
		},
		method: 'GET',
	})
		.then((response) => response.json())
		.then(({items}) => {
			const initialCollaborators: collaborator[] = items.reverse().map(
				(collaboratorItem: any) =>
					({
						actionIds: collaboratorItem.actionIds.includes('UPDATE')
							? 'UPDATE,ADD_DISCUSSION,VIEW'
							: collaboratorItem.actionIds.includes(
										'ADD_DISCUSSION'
								  )
								? 'ADD_DISCUSSION,VIEW'
								: 'VIEW',
						dateExpired: collaboratorItem.dateExpired,
						share: collaboratorItem.share,
						type: collaboratorItem.type,
						user: {
							id: collaboratorItem.id.toString(),
							image: collaboratorItem.portrait || '',
							name: collaboratorItem.name,
						},
					}) as collaborator
			);

			openModal({
				className: 'share-modal',
				contentComponent: ({closeModal}: {closeModal: () => void}) =>
					ShareModalContent({
						autocompleteURL,
						closeModal,
						collaboratorURL: collaboratorURLWithId,
						creator: {...creator, id: creator.id.toString()},
						initialCollaborators,
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
