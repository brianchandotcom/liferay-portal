/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function toggleRadio(radioId, showBoxIds, hideBoxIds) {
	const radioButton = document.getElementById(radioId);

	if (radioButton) {
		let showBoxes;

		if (showBoxIds) {
			if (Array.isArray(showBoxIds)) {
				showBoxIds = showBoxIds.join(',#');
			}

			showBoxes = document.querySelectorAll(`#${showBoxIds}`);

			showBoxes.forEach((showBox) => {
				if (radioButton.checked) {
					showBox.classList.remove('hide');
				}
				else {
					showBox.classList.add('hide');
				}
			});
		}

		radioButton.addEventListener('change', () => {
			if (showBoxes) {
				showBoxes.forEach((showBox) => {
					showBox.classList.remove('hide');
				});
			}

			if (hideBoxIds) {
				if (Array.isArray(hideBoxIds)) {
					hideBoxIds = hideBoxIds.join(',#');
				}

				const hideBoxes = document.querySelectorAll(`#${hideBoxIds}`);

				hideBoxes.forEach((hideBox) => {
					hideBox.classList.add('hide');
				});
			}
		});
	}
}
