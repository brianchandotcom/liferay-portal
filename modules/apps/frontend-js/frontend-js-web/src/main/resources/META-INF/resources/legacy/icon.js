/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ICON_REGISTRY = {};

let _docClickHandler;
let _mouseOverEvent;
let _mouseOutEvent;

function forcePost(event) {
	if (!Liferay.SPA || !Liferay.SPA.app) {
		const currentElement = Liferay.Util.getElement(event.currentTarget);

		if (currentElement) {
			const url = currentElement.getAttribute('href');

			// LPS-127302

			if (url === 'javascript:void(0);') {
				return;
			}

			const newWindow =
				currentElement.getAttribute('target') === '_blank';

			const hrefFm = document.hrefFm;

			if (newWindow) {
				hrefFm.setAttribute('target', '_blank');
			}

			submitForm(hrefFm, url, !newWindow);

			Liferay.Util._submitLocked = null;
		}

		event.preventDefault();
	}
}

function getConfig(event) {
	return ICON_REGISTRY[event.currentTarget.attr('id')];
}

function handleDocClick(event) {
	const config = getConfig(event);

	if (config) {
		event.preventDefault();

		if (config.useDialog) {
			Liferay.Util.openInDialog(event, {
				dialog: {
					destroyOnHide: true,
				},
				dialogIframe: {
					bodyCssClass: 'cadmin dialog-with-footer',
				},
			});
		}
		else {
			forcePost(event);
		}
	}
}

function handleDocMouseOut(event) {
	const config = getConfig(event);

	if (config && config.srcHover) {
		onMouseHover(event, config.src);
	}
}

function handleDocMouseOver(event) {
	const config = getConfig(event);

	if (config && config.srcHover) {
		onMouseHover(event, config.srcHover);
	}
}

function onMouseHover(event, src) {
	const image = event.currentTarget.one('img');

	if (image) {
		image.attr('src', src);
	}
}

export function registerIcon(config) {
	ICON_REGISTRY[config.id] = config;

	if (!_docClickHandler) {
		_docClickHandler = document
			.getElementById('<portlet:namespace /><%= id %>')
			.addEventListener('click', handleDocClick);
	}

	if (!_mouseOverEvent) {
		_mouseOverEvent = document
			.getElementById('<portlet:namespace /><%= id %>')
			.addEventListener('mouseover', handleDocMouseOver);
	}

	if (!_mouseOutEvent) {
		_mouseOutEvent = document
			.getElementById('<portlet:namespace /><%= id %>')
			.addEventListener('mouseout', handleDocMouseOut);
	}

	Liferay.once('screenLoad', () => {
		delete ICON_REGISTRY[config.id];
	});
}
