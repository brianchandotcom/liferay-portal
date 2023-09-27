/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayNavigate} from 'commerce-frontend-js/utilities/index';
import {createPortletURL} from 'frontend-js-web';

export default function ({currentURL, namespace, searchParam, selector}) {
	const portletURL = createPortletURL(currentURL);

	const radioButtons = document.querySelectorAll(
		`[name=${namespace}${selector}]`
	);

	const handleSelectChange = (event) => {
		if (event.target.checked) {
			portletURL.searchParams.set(
				`${namespace}${searchParam}`,
				event.target.value
			);

			liferayNavigate(portletURL.toString());
		}
	};

	radioButtons.forEach((radioButton) => {
		radioButton.addEventListener('change', handleSelectChange);
	});

	return {
		dispose() {
			radioButtons.forEach((radioButton) => {
				radioButton.removeEventListener('change', handleSelectChange);
			});
		},
	};
}
