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

import dom from 'metal-dom';
import {ClayToast} from 'clay-alert';

/**
 * Function that implements the Toast pattern, which allows to present feedback
 * to user actions as a toast message in the lower left corner of the page
 *
 * @param {string} message The message to show in the toast notification
 * @param {string} title The title associated with the message
 * @param {string} type The type of notification to show. It can be one of the
 * following: 'danger', 'info', 'success', 'warning'
 * @return {ClayToast} The Alert toast created
 * @review
 */

function openToast({
	events = {},
	message = '',
	title = Liferay.Language.get('success'),
	type = 'success'
} = {}) {
	var alertContainer = document.getElementById('alertContainer');

	if (!alertContainer) {
		alertContainer = document.createElement('div');
		alertContainer.id = 'alertContainer';

		dom.addClasses(
			alertContainer,
			'alert-notifications alert-notifications-fixed'
		);
		dom.enterDocument(alertContainer);
	} else {
		dom.removeChildren(alertContainer);
	}

	const mergedEvents = Object.assign(
		{
			disposed() {
				if (!alertContainer.hasChildNodes()) {
					dom.exitDocument(alertContainer);
				}
			}
		},
		events
	);

	const clayToast = new ClayToast(
		{
			autoClose: true,
			destroyOnHide: true,
			events: mergedEvents,
			message,
			spritemap: themeDisplay.getPathThemeImages() + '/lexicon/icons.svg',
			style: type,
			title
		},
		alertContainer
	);

	dom.removeClasses(clayToast.element, 'show');

	requestAnimationFrame(function() {
		dom.addClasses(clayToast.element, 'show');
	});

	return clayToast;
}

export {openToast};
export default openToast;
