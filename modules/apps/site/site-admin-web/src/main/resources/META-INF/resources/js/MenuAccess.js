/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
