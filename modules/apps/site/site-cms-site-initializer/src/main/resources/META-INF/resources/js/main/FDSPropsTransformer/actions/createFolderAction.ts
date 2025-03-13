/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import CreationModalContent from '../../components/modal/CreationModalContent';

type FolderData = {
	action: 'createFolder';
	assetLibraryId?: string;
};

export default function createFolderAction(data: FolderData) {
	openModal({
		center: true,
		contentComponent: ({closeModal}: {closeModal: () => void}) =>
			CreationModalContent({
				...data,
				closeModal,
				title: Liferay.Language.get('new-folder'),
			}),
		size: 'sm',
	});
}
