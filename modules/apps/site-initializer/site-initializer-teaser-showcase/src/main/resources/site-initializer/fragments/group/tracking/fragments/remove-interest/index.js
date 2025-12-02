/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, getCookie, removeCookie} from 'frontend-js-web';

function eraseCookie(name) {
	if (getCookie(name, COOKIE_TYPES.FUNCTIONAL)) {
		removeCookie(name);
	}
}

eraseCookie('Interest');
