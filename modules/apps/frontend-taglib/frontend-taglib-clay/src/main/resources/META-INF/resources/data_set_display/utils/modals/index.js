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
