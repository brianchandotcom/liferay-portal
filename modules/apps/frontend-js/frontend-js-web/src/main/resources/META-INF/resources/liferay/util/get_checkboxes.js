/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function getCheckboxes(form, except, name, state) {
	if (typeof form === 'string') {
		form = document.querySelector(form);
	}
	else {
		form = form._node || form;
	}

	let selector = 'input[type=checkbox]';

	if (name) {
		selector += `[name=${name}]`;
	}

	const checkboxes = Array.from(form.querySelectorAll(selector));

	if (!checkboxes.length) {
		return '';
	}

	return checkboxes
		.reduce((previous, item) => {
			const {checked, disabled, name, value} = item;

			if (value && name !== except && checked === state && !disabled) {
				previous.push(value);
			}

			return previous;
		}, [])
		.join();
}

export function getCheckedCheckboxes(form, except, name) {
	return getCheckboxes(form, except, name, true);
}

export function getUncheckedCheckboxes(form, except, name) {
	return getCheckboxes(form, except, name, false);
}
