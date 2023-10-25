/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MODAL_HEIGHT_MAP} from './constants';

export function resolveModalHeight(size: keyof typeof MODAL_HEIGHT_MAP) {
	return !size || !(size in MODAL_HEIGHT_MAP)
		? MODAL_HEIGHT_MAP.INITIAL
		: MODAL_HEIGHT_MAP[size];
}
