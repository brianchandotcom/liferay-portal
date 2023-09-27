/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';

export function truncateSearch(text, maxLength) {
	if (!text || text.length <= maxLength) {
		return text;
	}

	return text.slice(0, maxLength) + '...';
}

export function getApplicationIdSearchParam() {
	const searchParams = new URLSearchParams(window.location.search);
	const applicationId = searchParams.get('applicationId');

	return applicationId;
}

export function getLoadedContentFlag() {
	return {
		applicationId: getApplicationIdSearchParam(),
		backToEdit:
			Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT) &&
			JSON.parse(Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT)),
	};
}
