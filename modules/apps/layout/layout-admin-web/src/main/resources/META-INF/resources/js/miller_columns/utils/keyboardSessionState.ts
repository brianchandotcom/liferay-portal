/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MillerColumnItem} from '../types/MillerColumnItem';

export type KeyboardActionType = 'movement' | 'navigate';

export function clearSessionState() {
	sessionStorage.removeItem('miller-columns-keyboard-state');
}

export function getSessionState() {
	const state = sessionStorage.getItem('miller-columns-keyboard-state');

	return state ? JSON.parse(state) : {};
}

export function setSessionState(
	itemId: MillerColumnItem['id'],
	type: KeyboardActionType
) {
	sessionStorage.setItem(
		'miller-columns-keyboard-state',
		JSON.stringify({itemId, type})
	);
}
