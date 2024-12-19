/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ACCOUNT_ENTRY_ID_DEFAULT} from './constants';

export function getInitials(name) {
	return name
		.split(' ')
		.map((chunk) => chunk.charAt(0).toUpperCase())
		.join('');
}

export function shouldSelectAccount(accountId, orderId) {
	return (
		(!accountId || parseInt(accountId, 10) === ACCOUNT_ENTRY_ID_DEFAULT) &&
		orderId
	);
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
