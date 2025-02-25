/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import CreationFolderModalContent from '../../components/modal/CreationFolderModalContent';

export default function createFolderAction(data: {assetLibraryId?: string}) {
	openModal({
		contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
			CreationFolderModalContent({
				...data,
				closeModal,
			}),
		size: 'sm',
	});
}
