/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function buildFragment(htmlString) {
	const div = document.createElement('div');

	div.innerHTML = `<br>${htmlString}`;

	div.removeChild(div.firstChild);

	const fragment = document.createDocumentFragment();

	while (div.firstChild) {
		fragment.appendChild(div.firstChild);
	}

	return fragment;
}