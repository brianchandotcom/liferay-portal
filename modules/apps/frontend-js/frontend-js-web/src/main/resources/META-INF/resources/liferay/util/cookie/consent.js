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

import {getCookie} from './cookie';

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
