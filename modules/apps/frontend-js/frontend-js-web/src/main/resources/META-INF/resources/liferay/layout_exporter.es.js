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

/**
 * Hides layout pane
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function hideLayoutPane(options) {
	options = options || {};

	var object = options.obj;
	var pane = options.pane;

	if (object && object.checked) {
		pane = document.querySelector(pane);

		if (pane) {
			pane.classList.add('hide');
		}
	}
}

/**
 * Gets layout icons
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function getLayoutIcons() {
	return {
		minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
		plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png',
	};
}

/**
 * Proposes layout
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function proposeLayout(options) {
	options = options || {};

	var namespace = options.namespace;
	var reviewers = options.reviewers;

	var contents = '<div><form action="' + options.url + '" method="post">';

	if (reviewers.length > 0) {
		contents +=
			'<textarea name="' +
			namespace +
			'description" style="height: 100px; width: 284px;"></textarea><br /><br />' +
			Liferay.Language.get('reviewer') +
			' <select name="' +
			namespace +
			'reviewUserId">';

		for (var i = 0; i < reviewers.length; i++) {
			contents +=
				'<option value="' +
				reviewers[i].userId +
				'">' +
				reviewers[i].fullName +
				'</option>';
		}

		contents +=
			'</select><br /><br />' +
			'<input type="submit" value="' +
			Liferay.Language.get('proceed') +
			'" />';
	}
	else {
		contents +=
			Liferay.Language.get('no-reviewers-were-found') +
			'<br />' +
			Liferay.Language.get(
				'please-contact-the-administrator-to-assign-reviewers'
			) +
			'<br /><br />';
	}

	contents += '</form></div>';

	Liferay.Util.openWindow({
		dialog: {
			destroyOnHide: true,
		},
		title: contents,
	});
}

/**
 * Publishes to live
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function publishToLive(options) {
	options = options || {};

	Liferay.Util.openWindow({
		dialog: {
			constrain: true,
			modal: true,
			on: {
				visibleChange(event) {
					var instance = this;

					if (!event.newVal) {
						instance.destroy();
					}
				},
			},
		},
		title: options.title,
		uri: options.url,
	});
}

/**
 * Shows layout pane
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function showLayoutPane(options) {
	options = options || {};

	var object = options.obj;
	var pane = options.pane;

	if (object && object.checked) {
		pane = document.querySelector(pane);

		if (pane) {
			pane.classList.remove('hide');
		}
	}
}

/**
 * Toggles layout details
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function toggleLayoutDetails(options) {
	options = options || {};

	var detail = document.querySelector(options.detail);
	var image = document.querySelector(options.toggle);

	if (detail && image) {
		var icon = themeDisplay.getPathThemeImages() + '/arrows/01_plus.png';

		if (detail.classList.contains('hide')) {
			detail.classList.remove('hide');

			icon = themeDisplay.getPathThemeImages() + '/arrows/01_minus.png';
		}
		else {
			detail.classList.add('hide');
		}

		image.setAttribute('src', icon);
	}
}
