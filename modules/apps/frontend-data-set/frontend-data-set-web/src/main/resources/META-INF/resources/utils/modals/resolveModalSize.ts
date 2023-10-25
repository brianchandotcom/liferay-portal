/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CLAY_MODAL_SIZES_MAP} from './constants';

export function resolveModalSize(modalTarget: string) {
	const modalSize = modalTarget.split(
		'-'
	)[1] as keyof typeof CLAY_MODAL_SIZES_MAP;

	if (!modalSize) {
		return CLAY_MODAL_SIZES_MAP.DEFAULT;
	}

	if (modalSize in CLAY_MODAL_SIZES_MAP) {
		return CLAY_MODAL_SIZES_MAP[modalSize];
	}

	return CLAY_MODAL_SIZES_MAP.full;
}
