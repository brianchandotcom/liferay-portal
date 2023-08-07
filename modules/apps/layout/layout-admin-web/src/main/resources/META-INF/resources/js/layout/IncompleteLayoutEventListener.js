/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({deleteLayoutURL, enableLayoutURL, namespace}) {
	const deleteLayoutButton = document.getElementById(
		`${namespace}deleteLayoutButton`
	);
	const enableLayoutButton = document.getElementById(
		`${namespace}enableLayoutButton`
	);

	if (deleteLayoutButton) {
		deleteLayoutButton.addEventListener('click', () => {
			submitForm(document.hrefFm, deleteLayoutURL);
		});
	}
	if (enableLayoutButton) {
		enableLayoutButton.addEventListener('click', () => {
			submitForm(document.hrefFm, enableLayoutURL);
		});
	}
}
