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

import {fetch, objectToFormData, openSelectionModal} from 'frontend-js-web';

export default function ({
	changeThemeButtonId,
	initialSelectedThemeId,
	lookAndFeelDetailURL,
	namespace,
	selectThemeURL,
	themeContainerId,
}) {
	let selectedThemeId = initialSelectedThemeId;

	const themeContainer = document.getElementById(themeContainerId);

	const onChangeThemeButtonClick = (event) => {
		event.preventDefault();

		const url = new URL(selectThemeURL);

		url.searchParams.set(`${namespace}themeId`, selectedThemeId);

		openSelectionModal({
			onSelect: (selectedItem) => {
				const themeId = selectedItem.themeid;

				if (themeId && selectedThemeId !== themeId) {
					themeContainer.innerHTML = '';

					const loadingIndicator = document.createElement('div');

					loadingIndicator.classList.add('loading-animation');

					themeContainer.appendChild(loadingIndicator);

					fetch(lookAndFeelDetailURL, {
						body: objectToFormData({
							[`${namespace}themeId`]: themeId,
						}),
						method: 'POST',
					})
						.then((response) => response.text())
						.then((response) => {
							const range = document.createRange();
							const fragment = range.createContextualFragment(
								response
							);

							themeContainer.removeChild(loadingIndicator);
							themeContainer.appendChild(fragment);

							selectedThemeId = themeId;

							updateCheckboxNames(namespace, themeContainer);
						});
				}
			},
			selectEventName: `${namespace}selectTheme`,
			title: Liferay.Language.get('available-themes'),
			url: url.href,
		});
	};

	const changeThemeButton = document.getElementById(changeThemeButtonId);

	changeThemeButton.addEventListener('click', onChangeThemeButtonClick);

	return {
		dispose() {
			changeThemeButton.removeEventListener(
				'click',
				onChangeThemeButtonClick
			);
		},
	};
}

/**
 * Update checkboxNames input to reflect the actual checkbox in the page,
 * this value is used by PortletRequestImpl to set the parameters in the request
 */
function updateCheckboxNames(namespace, themeContainer) {
	const nextCheckboxNames = Array.from(
		themeContainer.querySelectorAll('input[type=checkbox]')
	)
		.map((input) => input.name.replace(namespace, ''))
		.join(',');

	const checkboxNamesInput = document.getElementById(
		`${namespace}checkboxNames`
	);

	if (checkboxNamesInput) {
		checkboxNamesInput.value = nextCheckboxNames;
	}
}
