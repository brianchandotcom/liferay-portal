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

import getElement from './get_element';
import inBrowserView from './in_browser_view';

export default function focusFormField(el) {
	let interacting = false;

	el = getElement(el);

	const handler = () => {
		interacting = true;

		document.body.removeEventListener('click', handler);
	};

	document.body.addEventListener('click', handler);

	if (!interacting && inBrowserView(el)) {
		const getDisabledParents = function (el) {
			let result = [];

			if (el.parentElement) {
				if (el.parentElement.getAttribute('disabled')) {
					result = [el.parentElement];
				}

				result = [...result, ...getDisabledParents(el.parentElement)];
			}

			return result;
		};

		const disabledParents = getDisabledParents(el);

		const focusable =
			!el.getAttribute('disabled') &&
			el.offsetWidth > 0 &&
			el.offsetHeight > 0 &&
			!disabledParents.length;

		const form = el.closest('form');

		if (!form || focusable) {
			el.focus();
		}
		else if (form) {
			const portletName = form.getAttribute('data-fm-namespace');

			const formReadyEventName = portletName + 'formReady';

			const formReadyHandler = (event) => {
				const elFormName = form.getAttribute('name');

				const formName = event.formName;

				if (elFormName === formName) {
					el.focus();

					Liferay.detach(formReadyEventName, formReadyHandler);
				}
			};

			Liferay.on(formReadyEventName, formReadyHandler);
		}
	}
}
