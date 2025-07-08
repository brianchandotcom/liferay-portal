/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import SpaceMembersModal from '../../spaces/SpaceMembersModal';

export interface AddMembersData {
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
	title: string;
}

export default function addMembersAction(
	data: AddMembersData,
	loadData?: () => void
) {
	const {assetLibraryCreatorUserId, assetLibraryId, title} = data;

	openModal({
		center: true,
		contentComponent: () =>
			SpaceMembersModal({
				assetLibraryCreatorUserId,
				assetLibraryId,
			}),
		onClose: loadData,
		size: 'md',
		title,
	});
}
