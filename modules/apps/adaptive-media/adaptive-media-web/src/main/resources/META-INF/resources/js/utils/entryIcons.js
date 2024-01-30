/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function disableEntryIcon(element) {
	element.parentElement.classList.add('disabled');

	element.setAttribute('data-onclick', element.getAttribute('onclick'));

	element.removeAttribute('onclick');
}

export function enableEntryIcon(element) {
	element.parentElement.classList.remove('disabled');

	element.setAttribute('onclick', element.dataset.onclick);

	element.removeAttribute('data-onclick');
}
