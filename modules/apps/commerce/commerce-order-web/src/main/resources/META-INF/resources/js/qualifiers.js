/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayNavigate} from 'commerce-frontend-js/utilities/index';
import {createPortletURL} from 'frontend-js-web';

export default function ({currentURL, namespace}) {
	const portletURL = createPortletURL(currentURL);

	const chooseChannelQualifiers = document.querySelectorAll(
		`[name='${namespace}chooseChannelQualifiers']`
	);

	const handleSelectChange = (event) => {
		if (event.target.checked) {
			portletURL.searchParams.set(
				`${namespace}channelQualifiers`,
				event.target.value
			);

			liferayNavigate(portletURL.toString());
		}
	};

	chooseChannelQualifiers.forEach((channelQualifier) => {
		channelQualifier.addEventListener('change', handleSelectChange);
	});

	return {
		dispose() {
			chooseChannelQualifiers.forEach((channelQualifier) => {
				channelQualifier.removeEventListener(
					'change',
					handleSelectChange
				);
			});
		},
	};
}
