/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import openWindow from './util/open_window';

/**
 * Hides layout pane
 * @param {Object} options
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
export function hideLayoutPane(options) {
	options = options || {};

	const object = options.obj;
	let pane = options.pane;

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

	const namespace = options.namespace;
	const reviewers = options.reviewers;

	let contents = '<div><form action="' + options.url + '" method="post">';

	if (reviewers.length) {
		contents +=
			'<textarea name="' +
			namespace +
			'description" style="height: 100px; width: 284px;"></textarea><br /><br />' +
			Liferay.Language.get('reviewer') +
			' <select name="' +
			namespace +
			'reviewUserId">';

		for (let i = 0; i < reviewers.length; i++) {
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

	openWindow({
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

	openWindow({
		dialog: {
			constrain: true,
			modal: true,
			on: {
				visibleChange(event) {
					const instance = this;

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

	const object = options.obj;
	let pane = options.pane;

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

	const detail = document.querySelector(options.detail);
	const image = document.querySelector(options.toggle);

	if (detail && image) {
		let icon = themeDisplay.getPathThemeImages() + '/arrows/01_plus.png';

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
