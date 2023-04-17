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

import {toggleDisabled} from 'frontend-js-web';

export default function ({namespace}) {
	const checkbox = document.getElementById(
		`${namespace}showControlMenuByRole`
	);

	const onCheckboxChange = (event) => {
		const modifyLinks = document.querySelectorAll('.modify-link');
		const modifyTexts = document.querySelectorAll('.modify-text');

		toggleDisabled(modifyLinks, !event.target.checked);

		modifyTexts.forEach((text) => {
			text.classList.toggle('text-muted', !event.target.checked);
		});
	};

	checkbox.addEventListener('change', onCheckboxChange);

	return {
		dispose() {
			checkbox.removeEventListener('change', onCheckboxChange);
		},
	};
}
