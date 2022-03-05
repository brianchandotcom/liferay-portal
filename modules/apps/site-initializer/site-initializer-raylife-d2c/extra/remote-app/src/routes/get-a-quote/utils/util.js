/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
