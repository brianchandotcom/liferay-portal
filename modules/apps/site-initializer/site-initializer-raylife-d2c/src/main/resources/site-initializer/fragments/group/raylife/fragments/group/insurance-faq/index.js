/* eslint-disable no-undef */
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
