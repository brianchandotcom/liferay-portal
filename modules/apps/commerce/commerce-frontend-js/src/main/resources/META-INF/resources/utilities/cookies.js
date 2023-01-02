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
