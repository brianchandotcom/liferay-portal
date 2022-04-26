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
