/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const messageReceived = localStorage.getItem('userAccountData');

if (messageReceived) {
	const data = JSON.parse(messageReceived);
	const loggedUserContainer = document.querySelector('.logged-user');
	const accountNameAssociated = document.querySelector('.account-name');

	if (loggedUserContainer) {
		loggedUserContainer.textContent = data.userName;
	}

	if (accountNameAssociated) {
		accountNameAssociated.textContent = data.accountName;
	}

	if (data) {
		const modal = document.querySelector('.notification-welcome');
		modal.classList.replace('d-none', 'd-show');

		setTimeout(() => {
			modal.classList.replace('d-show', 'd-none');
		}, 4000);
		localStorage.removeItem('userAccountData');
	}
}
