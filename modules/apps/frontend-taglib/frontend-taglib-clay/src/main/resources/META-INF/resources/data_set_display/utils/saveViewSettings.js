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

import {fetch, openToast} from 'frontend-js-web';

import {logError} from './logError';

export const saveViewSettings = ({appURL, id, portletId, settings}) => {
	const url = new URL(`${appURL}/data-set/${id}/save-active-view-settings`);

	url.searchParams.append('groupId', themeDisplay.getScopeGroupId());
	url.searchParams.append('plid', themeDisplay.getPlid());
	url.searchParams.append('portletId', portletId);

	return fetch(url, {
		body: JSON.stringify(settings),
		headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json',
		},
		method: 'POST',
	}).catch((error) => {
		logError(error);

		openToast({
			message: Liferay.Language.get('unexpected-error'),
			type: 'danger',
		});
	});
};
