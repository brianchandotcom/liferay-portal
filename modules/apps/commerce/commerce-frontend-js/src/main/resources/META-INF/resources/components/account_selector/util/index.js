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

import {fetch} from 'frontend-js-web';

export function getInitials(name) {
	return name
		.split(' ')
		.map((chunk) => chunk.charAt(0).toUpperCase())
		.join('');
}

export function selectAccount(id, actionURL) {
	const endpointURL = new URL(actionURL, Liferay.ThemeDisplay.getPortalURL());

	endpointURL.searchParams.append(
		'groupId',
		Liferay.ThemeDisplay.getScopeGroupId()
	);
	endpointURL.searchParams.append('p_auth', Liferay.authToken);

	const body = new FormData();

	body.append('accountId', id);

	return fetch(endpointURL, {body, method: 'POST'});
}
