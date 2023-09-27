/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const messageReceived = localStorage.getItem('success');

if (messageReceived) {
	const modal = document.getElementsByClassName('alert-success')[0];
	modal.classList.replace('d-none', 'd-show');

	setTimeout(() => {
		modal.classList.replace('d-show', 'd-none');
	}, 4000);
	localStorage.removeItem('success');
}
