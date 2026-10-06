/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Generates a random RFC 9562 version 4 UUID.
 *
 * This is a copy of `uuidv4` in frontend-js-web, because the analytics
 * client is a standalone script that cannot depend on frontend-js-web.
 *
 * `crypto.randomUUID()` is not used because browsers only expose it in secure
 * contexts, while `crypto.getRandomValues()` is available everywhere.
 */
export function uuidv4(): string {
	const bytes = crypto.getRandomValues(new Uint8Array(16));

	// Set the version (4) and variant (10xx) bits

	bytes[6] = (bytes[6] & 0x0f) | 0x40;
	bytes[8] = (bytes[8] & 0x3f) | 0x80;

	const hex = Array.from(bytes, (byte) =>
		byte.toString(16).padStart(2, '0')
	).join('');

	return [
		hex.slice(0, 8),
		hex.slice(8, 12),
		hex.slice(12, 16),
		hex.slice(16, 20),
		hex.slice(20),
	].join('-');
}
