/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
