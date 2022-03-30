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

const EMAIL_INPUT_ID = '_com_liferay_login_web_portlet_LoginPortlet_login';
const PASSWORD_INPUT_ID =
	'_com_liferay_login_web_portlet_LoginPortlet_password';

const emailInput = document.getElementById(EMAIL_INPUT_ID);
emailInput.setAttribute('placeholder', 'Email');
emailInput.removeAttribute('value');

const passwordInput = document.getElementById(PASSWORD_INPUT_ID);
passwordInput.setAttribute('placeholder', 'Password');

window.onload = function () {
	document.getElementById(EMAIL_INPUT_ID).focus();
};

document.getElementById(EMAIL_INPUT_ID).focus();

function inputValidation() {
	const EMAIL_REGEX = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g;

	const TEXT_VARIANT = {
		danger: 'border-danger',
		success: 'border-success',
	};

	const isEmailValid = (email) => {
		const regex = new RegExp(EMAIL_REGEX);

		return regex.test(email);
	};

	function onInputChange(element, validation) {
		if (validation) {
			element.classList.remove(TEXT_VARIANT.danger);
			element.classList.add(TEXT_VARIANT.success);

			return;
		}

		element.classList.remove(TEXT_VARIANT.success);
		element.classList.add(TEXT_VARIANT.danger);
	}

	emailInput.addEventListener('change', () =>
		onInputChange(emailInput, isEmailValid(emailInput.value))
	);

	passwordInput.addEventListener('change', () =>
		onInputChange(passwordInput, passwordInput.value !== '')
	);
}

inputValidation();
