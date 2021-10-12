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

export default function ({currentURL, namespace, searchParam, selector}) {
	const portletURL = createPortletURL(currentURL);

	const radioButtons = document.querySelectorAll(
		`[name=${namespace}${selector}]`
	);

	const handleSelectChange = (event) => {
		if (event.target.checked) {
			portletURL.searchParams.append(
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
