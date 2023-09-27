/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

import {CLAY_MODAL_SIZES_MAP, MODAL_HEIGHT_MAP} from './constants';

export function resolveModalSize(modalTarget) {
	const modalSize = modalTarget.split('-')[1];

	if (!modalSize) {
		return CLAY_MODAL_SIZES_MAP.DEFAULT;
	}

	if (modalSize in CLAY_MODAL_SIZES_MAP) {
		return CLAY_MODAL_SIZES_MAP[modalSize];
	}

	return CLAY_MODAL_SIZES_MAP.FULL_SCREEN;
}

export function resolveModalHeight(size) {
	return !size || !(size in MODAL_HEIGHT_MAP)
		? MODAL_HEIGHT_MAP.INITIAL
		: MODAL_HEIGHT_MAP[size];
}

export function openPermissionsModal(url) {
	openModal({
		title: Liferay.Language.get('permissions'),
		url,
	});
}
