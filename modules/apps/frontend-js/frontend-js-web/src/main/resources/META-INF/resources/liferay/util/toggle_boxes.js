/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
