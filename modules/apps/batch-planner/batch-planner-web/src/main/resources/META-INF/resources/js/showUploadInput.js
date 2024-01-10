/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const radioInputs = document.querySelectorAll('input[name="selectFile"]');
	radioInputs.forEach((element) => {
		element.addEventListener('change', (event) => {
			const item = event.target.value;

			const showUploadFromComputer = document.getElementById(
				`${namespace}fileUpload`
			);

			if (item === 'server') {
				showUploadFromComputer.style.display = 'none';
			}
			else {
				showUploadFromComputer.style.display = 'block';
			}
		});
	});
}
