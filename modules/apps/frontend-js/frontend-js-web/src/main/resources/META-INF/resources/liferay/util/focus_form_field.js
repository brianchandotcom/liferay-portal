/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getElement from './get_element';
import inBrowserView from './in_browser_view';

function getDisabledParents(element) {
	const result = [];

	while (element.parentElement) {
		if (element.parentElement.getAttribute('disabled')) {
			result.push(element.parentElement);
		}

		element = element.parentElement;
	}

	return result;
}

export default function focusFormField(element) {
	element = getElement(element);

	if (inBrowserView(element)) {
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
			const portletName = form.dataset.fmNamespace;

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
