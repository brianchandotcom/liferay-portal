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

export default function toggleBoxes(
	checkBoxId,
	toggleBoxId,
	displayWhenUnchecked,
	toggleChildCheckboxes
) {
	const checkBox = document.getElementById(checkBoxId);
	const toggleBox = document.getElementById(toggleBoxId);

	if (checkBox && toggleBox) {
		let checked = checkBox.checked;

		if (displayWhenUnchecked) {
			checked = !checked;
		}

		if (checked) {
			toggleBox.classList.remove('hide');
		}
		else {
			toggleBox.classList.add('hide');
		}

		checkBox.addEventListener('click', () => {
			toggleBox.classList.toggle('hide');

			if (toggleChildCheckboxes) {
				const childCheckboxes = toggleBox.querySelectorAll(
					'input[type=checkbox]'
				);

				childCheckboxes.forEach((childCheckbox) => {
					childCheckbox.checked = checkBox.checked;
				});
			}
		});
	}
}
