/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
