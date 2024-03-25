/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayNavigate} from 'commerce-frontend-js';
import {createPortletURL} from 'frontend-js-web';

export default function ({currentURL, namespace}) {
	const portletURL = createPortletURL(currentURL);

	const chooseAccountQualifiers = document.querySelectorAll(
		`[name='${namespace}chooseAccountQualifiers']`
	);

	const handleSelectChange = (event) => {
		if (event.target.checked) {
			portletURL.searchParams.set(
				`${namespace}accountQualifiers`,
				event.target.value
			);

			liferayNavigate(portletURL.toString());
		}
	};

	chooseAccountQualifiers.forEach((accountQualifier) => {
		accountQualifier.addEventListener('change', handleSelectChange);
	});

	return {
		dispose() {
			chooseAccountQualifiers.forEach((accountQualifier) => {
				accountQualifier.removeEventListener(
					'change',
					handleSelectChange
				);
			});
		},
	};
}
