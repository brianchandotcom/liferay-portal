/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import ShareModalContent, {collaborator} from '../../modal/ShareModalContent';

export default function shareAction({
	autocompleteUserURL,
	classNameId,
	classPK,
	shareActionURL,
	title,
}: {
	autocompleteUserURL: string;
	classNameId: string;
	classPK: string;
	shareActionURL: string;
	title: string;
}) {

	// Fetch initial collaborators from API

	const initialCollaborators: collaborator[] = [];

	openModal({
		className: 'share-modal',
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			ShareModalContent({
				autocompleteUserURL,
				classNameId,
				classPK,
				closeModal,
				initialCollaborators,
				shareActionURL,
				title,
			}),
		size: 'md',
	});
}
