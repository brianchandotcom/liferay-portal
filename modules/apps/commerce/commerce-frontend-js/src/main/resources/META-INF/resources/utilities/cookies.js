/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, getCookie, setCookie} from 'frontend-js-web';

class CommerceCookie {
	constructor(scope = null, cookieType) {
		if (!scope) {
			throw new Error('Scope must be defined');
		}

		this.scope = scope;
		this.cookieType = cookieType || COOKIE_TYPES.NECESSARY;
	}

	getValue(key) {
		return getCookie(this.scope + key, this.cookieType);
	}

	setValue(key, value, expires, path = '/') {
		const options = {path};

		if (expires) {
			const expirationDate =
				expires instanceof Date ? expires : new Date(expires);

			options.expires = expirationDate.toUTCString();
		}

		return setCookie(
			`${this.scope}${key}`,
			value,
			this.cookieType,
			options
		);
	}
}

export default CommerceCookie;
