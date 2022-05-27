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
