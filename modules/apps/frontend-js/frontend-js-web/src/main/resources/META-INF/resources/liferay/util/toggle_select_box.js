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

const toggle = (selectBox, toggleBox, dynamicValue, value) => {
	const currentValue = selectBox.value;

	const visible = dynamicValue
		? value(currentValue, value)
		: value === currentValue;

	toggleBox.classList.toggle('hide', !visible);
};

export default function toggleSelectBox(selectBoxId, value, toggleBoxId) {
	const selectBox = document.getElementById(selectBoxId);
	const toggleBox = document.getElementById(toggleBoxId);

	if (!selectBox || !toggleBox) {
		return;
	}

	const dynamicValue = typeof value === 'function';

	toggle(selectBox, toggleBox, dynamicValue, value);

	selectBox.addEventListener('change', () =>
		toggle(selectBox, toggleBox, dynamicValue, value)
	);
}
