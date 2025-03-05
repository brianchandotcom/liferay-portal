/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, getCookie, removeCookie} from 'frontend-js-web';

import CommerceCookie from '../../../utilities/cookies';

const CURRENCY_CODE_COOKIE_IDENTIFIER =
	'com.liferay.commerce.currency.model.CommerceCurrency#';

export function resetCommerceCurrency() {
	const {commerceChannelGroupId: groupId} = Liferay.CommerceContext;

	const cookieKey = `${CURRENCY_CODE_COOKIE_IDENTIFIER}${groupId}`;

	removeCookie(cookieKey);
}

export function retrieveCommerceCurrency() {
	const {commerceChannelGroupId: groupId} = Liferay.CommerceContext;

	const cookieKey = `${CURRENCY_CODE_COOKIE_IDENTIFIER}${groupId}`;

	return getCookie(cookieKey, COOKIE_TYPES.NECESSARY);
}

export function storeCommerceCurrency(currencyCode) {
	const {commerceChannelGroupId: groupId} = Liferay.CommerceContext;

	const cookie = new CommerceCookie(
		CURRENCY_CODE_COOKIE_IDENTIFIER,
		COOKIE_TYPES.NECESSARY
	);

	cookie.setValue(groupId, currencyCode);
}
