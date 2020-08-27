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

import {CLOSE_MODAL, IS_LOADING_MODAL} from '../eventsDefinitions';
import {showErrorNotification} from '../notifications';
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

export function openPermissionsModal(uri) {
	Liferay.Util.openWindow({
		dialog: {
			destroyOnHide: true,
		},
		dialogIframe: {
			bodyCssClass: 'dialog-with-footer',
		},
		title: Liferay.Language.get('permissions'),
		uri,
	});
}

export function closeAndRedirect(redirectURL) {
	const t_Liferay = window.top.Liferay;

	const modalSettings = {
		successNotification: {
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			showSuccessNotification: true,
		},
	};

	if (redirectURL) {
		modalSettings.redirectURL = redirectURL.toString();
	}

	t_Liferay.fire(CLOSE_MODAL, modalSettings);
}

export function isSubmitting() {
	const t_Liferay = window.top.Liferay;

	t_Liferay.fire(IS_LOADING_MODAL, {isLoading: true});
}

export function onSubmitFail(reason = {}) {
	const t_Liferay = window.top.Liferay,
		{title = ''} = reason,
		errorMessage =
			title || Liferay.Language.get('an-unexpected-error-occurred');

	t_Liferay.fire(IS_LOADING_MODAL, {isLoading: false});

	showErrorNotification(errorMessage);
}
