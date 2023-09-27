/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {escapeHTML} from './util/html_util';
import toggleDisabled from './util/toggle_disabled';

function sortByValue(a, b) {
	let position = a.indexOf('">');

	const nameA = a.substring(position);

	position = b.indexOf('">');

	const nameB = b.substring(position);

	if (nameA < nameB) {
		return -1;
	}
	else if (nameA > nameB) {
		return 1;
	}
	else {
		return 0;
	}
}

/**
 * For backwards compatibility, same semantics as YUI's `Array.test()`.
 *
 * @see https://github.com/yui/yui3/blob/25264e3629b1c07fb779d203c4a25c0879ec862c/src/yui/js/yui-array.js#L271-L306
 */
function isArrayLike(value) {
	if (Array.isArray(value)) {
		return true;
	}

	return !!(
		value &&
		typeof value === 'object' &&
		typeof value.length === 'number' &&
		!value.tagName &&
		!value.scrollTo &&
		!value.document
	);
}

function toArray(value) {
	return isArrayLike(value) ? Array.from(value) : [value];
}

function updateSelect(array, index, list) {
	const options = array[index];

	const select = document.getElementById(options.select);

	if (!select) {
		return;
	}

	const selectVal = toArray(options.selectVal);

	const selectOptions = [];

	if (options.selectNullable !== false) {
		selectOptions.push('<option selected value="0"></option>');
	}

	list.forEach((item) => {
		const key = escapeHTML(item[options.selectId]);
		const value = escapeHTML(item[options.selectDesc]);

		let selected = '';

		if (selectVal.indexOf(key) > -1) {
			selected = 'selected="selected"';
		}

		selectOptions.push(
			`<option ${selected} value="${key}">${value}</option>`
		);
	});

	if (options.selectSort) {
		selectOptions.sort(sortByValue);
	}

	while (select.lastChild) {
		select.removeChild(select.lastChild);
	}

	select.innerHTML = selectOptions.join('');

	if (options.selectDisableOnEmpty) {
		toggleDisabled(select, !list.length);
	}
}

function callSelectData(array, index) {
	if (index + 1 < array.length) {
		const currentSelect = document.getElementById(array[index].select);
		const nextSelectData = array[index + 1].selectData;

		nextSelectData((list) => {
			updateSelect(array, index + 1, list);
		}, currentSelect && currentSelect.value);
	}
}

function process(array) {
	array.forEach((item, index) => {
		const id = item.select;
		const select = document.getElementById(id);
		const selectData = item.selectData;

		if (select) {
			select.setAttribute('data-componentType', 'dynamic_select');

			let prevSelectVal;

			if (index > 0) {
				prevSelectVal = array[index - 1].selectVal;
			}

			selectData((list) => {
				updateSelect(array, index, list);
			}, prevSelectVal);

			if (!select.getAttribute('name')) {
				select.setAttribute('name', id);
			}

			select.addEventListener('change', () => {
				callSelectData(array, index);
			});
		}
	});
}

/**
 * Ideally would be a function, but it is a class for backwards compatibility.
 */
export default class DynamicSelect {
	constructor(array) {
		process(array);
	}
}
