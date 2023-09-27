/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import base64js from 'base64-js';

function base64UrlToMime(code) {
	return (
		code.replace(/-/g, '+').replace(/_/g, '/') +
		'===='.substring(0, (4 - (code.length % 4)) % 4)
	);
}

function mimeBase64ToUrl(code) {
	return code.replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
}

export function fromByteArray(bytes) {
	bytes = bytes instanceof ArrayBuffer ? new Uint8Array(bytes) : bytes;

	return mimeBase64ToUrl(base64js.fromByteArray(bytes));
}

export function toByteArray(code) {
	return base64js.toByteArray(base64UrlToMime(code));
}
