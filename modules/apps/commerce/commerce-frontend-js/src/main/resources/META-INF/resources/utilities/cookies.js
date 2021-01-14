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

class CookieUtils {
	constructor(scope = null) {
		if (!scope) {
			throw new Error('Scope must be defined');
		}
		this.scope = scope;
	}

	getValue(key) {
		const [, value] = document.cookie.split(`${this.scope}${key}=`);

		return !value ? null : value.split(';')[0];
	}

	setValue(key, value, expires, path = '/') {
		const cookieValue = `${this.scope}${key}=${value};`,
			cookiePath = `path=${path};`;

		let cookieExp = '';

		if (expires) {
			const expirationDate =
				expires instanceof Date ? expires : new Date(expires);

			cookieExp = `expires=${expirationDate.toUTCString()};`;
		}

		document.cookie = `${cookieValue}${cookieExp}${cookiePath}`;
	}
}
export default CookieUtils;
