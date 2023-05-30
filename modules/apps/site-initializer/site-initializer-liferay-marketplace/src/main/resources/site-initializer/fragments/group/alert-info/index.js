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

const messageReceived = localStorage.getItem('userAccountData');
const data = JSON.parse(messageReceived);

const loggedUser = document.querySelector('.logged-user');
loggedUser.textContent = data.userName;

const accountNameAssociated = document.querySelector('.account-name');
accountNameAssociated.textContent = data.accountName;

if (data) {
	const modal = document.getElementsByClassName('alert-info')[0];
	modal.classList.replace('d-none', 'd-show');

	setTimeout(() => {
		modal.classList.replace('d-show', 'd-none');
	}, 4000);
	localStorage.removeItem('userAccountData');
}
