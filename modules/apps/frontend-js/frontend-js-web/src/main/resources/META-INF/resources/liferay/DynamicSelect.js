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

import toggleDisabled from './util/toggle_disabled';

function sortByValue(a, b) {
	let position = a.indexOf('">');

	const nameA = a.substring(position);

	position = b.indexOf('">');

	const nameB = b.substring(position);

	let retVal = 0;

	if (nameA < nameB) {
		retVal = -1;
	}
	else if (nameA > nameB) {
		retVal = 1;
	}

	return retVal;
}

export default class DyamicSelect {
	constructor(array) {
		this.array = array;
		this._processArray(this.array);
	}

	_processArray(array) {
		array.forEach((item, index) => {
			const id = item.select;
			const select = document.querySelector(`#${id}`);
			const selectData = item.selectData;

			if (select) {
				select.setAttribute('data-componentType', 'dynamic_select');

				let prevSelectVal = null;

				if (index > 0) {
					prevSelectVal = array[index - 1].selectVal;
				}

				selectData((list) => {
					this._updateSelect(index, list);
				}, prevSelectVal);

				if (!select.getAttribute('name')) {
					select.setAttribute('name', id);
				}

				select.addEventListener('change', () => {
					this._callSelectData(index);
				});
			}
		});
	}

	_callSelectData(index) {
		if (index + 1 < this.array.length) {
			const curSelect = document.querySelector(
				`#${this.array[index].select}`
			);
			const nextSelectData = this.array[index + 1].selectData;

			nextSelectData((list) => {
				this._updateSelect(index + 1, list);
			}, curSelect && curSelect.value);
		}
	}

	_updateSelect(index, list) {
		const options = this.array[index];

		const select = document.querySelector(`#${options.select}`);

		const selectDesc = options.selectDesc;
		const selectDisableOnEmpty = options.selectDisableOnEmpty;
		const selectId = options.selectId;
		const selectNullable = options.selectNullable !== false;
		const selectSort = options.selectSort;

		const selectVal = [options.selectVal];

		let selectOptions = [];

		if (selectNullable) {
			selectOptions.push('<option selected value="0"></option>');
		}

		list.forEach((item) => {
			const key = item[selectId];
			const value = item[selectDesc];

			let selected = '';

			if (selectVal.indexOf(key) > -1) {
				selected = 'selected="selected"';
			}

			selectOptions.push(
				`<option ${selected} value="${key}">${value}</option>`
			);
		});

		if (selectSort) {
			selectOptions = selectOptions.sort(sortByValue);
		}

		selectOptions = selectOptions.join('');

		if (select) {
			while (select.firstChild) {
				select.removeChild(select.lastChild);
			}

			select.innerHTML = selectOptions;

			if (selectDisableOnEmpty) {
				toggleDisabled(select, !list.length);
			}
		}
	}
}
