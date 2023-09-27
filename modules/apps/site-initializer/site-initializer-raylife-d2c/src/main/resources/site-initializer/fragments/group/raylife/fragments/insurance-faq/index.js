/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const customIcons = fragmentElement.getElementsByClassName('custom-icon');
const faqList = fragmentElement.querySelector('#faq-list');
const minusIcon = fragmentElement.querySelector('#minus-icon');
const plusIcon = fragmentElement.querySelector('#plus-icon');

for (let i = 0; i < customIcons.length; i++) {
	const icon = customIcons[i];

	icon.onclick = function () {
		const flag = faqList.classList.contains('collapse');

		if (flag) {
			faqList.classList.remove('collapse');
			plusIcon.classList.add('d-none');
			minusIcon.classList.remove('d-none');
		}
		else {
			faqList.classList.add('collapse');
			plusIcon.classList.remove('d-none');
			minusIcon.classList.add('d-none');
		}
	};
}
