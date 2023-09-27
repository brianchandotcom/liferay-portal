/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const displayStyleSelect = document.getElementById(
		`${namespace}displayStyle`
	);

	function showHiddenFields() {
		const displayStyle = displayStyleSelect.value;

		const hiddenFields = document.querySelectorAll('.hidden-field');

		Array.from(hiddenFields).forEach((field) => {
			const fieldContainer = field.closest('.form-group');

			if (fieldContainer) {
				const fieldClassList = field.classList;
				const fieldContainerClassList = fieldContainer.classList;

				if (
					displayStyle === 'full-content' &&
					(fieldClassList.contains('show-asset-title') ||
						fieldClassList.contains('show-context-link') ||
						fieldClassList.contains('show-extra-info'))
				) {
					fieldContainerClassList.remove('hide');
				}
				else if (
					displayStyle === 'abstracts' &&
					fieldClassList.contains('abstract-length')
				) {
					fieldContainerClassList.remove('hide');
				}
				else {
					fieldContainerClassList.add('hide');
				}
			}
		});
	}

	showHiddenFields();

	displayStyleSelect.addEventListener('change', showHiddenFields);
}
