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
