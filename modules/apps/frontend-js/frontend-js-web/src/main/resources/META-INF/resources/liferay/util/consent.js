/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCookie} from './cookie/cookie';

export const CONSENT_TYPES = {
	FUNCTIONAL: 'CONSENT_TYPE_FUNCTIONAL',
	NECESSARY: 'CONSENT_TYPE_NECESSARY',
	PERFORMANCE: 'CONSENT_TYPE_PERFORMANCE',
	PERSONALIZATION: 'CONSENT_TYPE_PERSONALIZATION',
};

/**
 * Checks whether the user has consented to a specific type of cookie by looking at the config cookie value.
 * - If it's 'true', the user has consented.
 * - If it's 'false', the user has rejected that specific consent type.
 * - If it doesn't exist, cookie consent doesn't apply and cookie can be set.
 *
 * @param {string} type Type of consent, from {@link CONSENT_TYPES}
 * @returns {boolean} Boolean representing whether we are allowed to set the cookie
 */
export function checkConsent(type) {
	return (
		type === CONSENT_TYPES.NECESSARY ||
		getCookie(type, CONSENT_TYPES.NECESSARY) !== 'false'
	);
}
