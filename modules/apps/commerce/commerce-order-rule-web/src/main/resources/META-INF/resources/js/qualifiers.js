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

import {liferayNavigate} from 'commerce-frontend-js/utilities/index';
import {createPortletURL} from 'frontend-js-web';

export default function ({currentURL, namespace}) {
	const portletURL = createPortletURL(currentURL);

	const chooseChannelQualifiers = document.querySelectorAll(
		`[name='${namespace}chooseChannelQualifiers']`
	);

	const handleSelectChange = (event) => {
		if (event.target.checked) {
			portletURL.searchParams.append(
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
