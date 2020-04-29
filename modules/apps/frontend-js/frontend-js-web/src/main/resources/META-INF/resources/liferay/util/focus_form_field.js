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

function getDisabledParents(element) {
	let result = [];

	if (element.parentElement) {
		if (element.parentElement.getAttribute('disabled')) {
			result = [element.parentElement];
		}

		result = [...result, ...getDisabledParents(element.parentElement)];
	}

	return result;
}

export default function focusFormField(element) {
	let interacting = false;

	element = getElement(element);

	const handler = () => {
		interacting = true;

		document.body.removeEventListener('click', handler);
	};

	document.body.addEventListener('click', handler);

	if (!interacting && inBrowserView(element)) {
		const disabledParents = getDisabledParents(element);

		const focusable =
			!element.getAttribute('disabled') &&
			element.offsetWidth > 0 &&
			element.offsetHeight > 0 &&
			!disabledParents.length;

		const form = element.closest('form');

		if (!form || focusable) {
			element.focus();
		}
		else if (form) {
			const portletName = form.getAttribute('data-fm-namespace');

			const formReadyEventName = portletName + 'formReady';

			const formReadyHandler = (event) => {
				const elFormName = form.getAttribute('name');

				const formName = event.formName;

				if (elFormName === formName) {
					element.focus();

					Liferay.detach(formReadyEventName, formReadyHandler);
				}
			};

			Liferay.on(formReadyEventName, formReadyHandler);
		}
	}
}
