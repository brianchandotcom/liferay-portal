/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../types';

const AC_COOKIE_LIST: (Analytics.Queues | Analytics.Keys)[] = [
	Analytics.Queues.Events,
	Analytics.Queues.IdentityMessage,
	Analytics.Queues.Messages,
	Analytics.Keys.ChannelId,
	Analytics.Keys.Contexts,
	Analytics.Keys.Identity,
	Analytics.Keys.PrevEmailAddressHash,
	Analytics.Keys.StorageVersion,
];

export function removeCookiesFromUserBrowser() {
	const cookies: string[] = document.cookie.split(';');

	if (cookies.length) {
		for (const cookie of cookies) {
			const [name] = cookie.split('=');

			if (AC_COOKIE_LIST.includes(name.trim() as Analytics.Keys)) {
				document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC`;
			}
		}
	}
}
