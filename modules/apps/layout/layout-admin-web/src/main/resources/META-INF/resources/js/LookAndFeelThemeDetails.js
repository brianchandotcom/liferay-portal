/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';

export default function ({
	buttonCssClass,
	containerId,
	regularColorSchemeInputId,
}) {
	const colorSchemesContainer = document.getElementById(containerId);

	const callback = (event) => {
		if (!event.key || event.key === ' ' || event.key === 'Enter') {
			event.preventDefault();

			const selectedColorScheme = event.target.closest(buttonCssClass);

			colorSchemesContainer
				.querySelectorAll(buttonCssClass)
				.forEach((node) => node.classList.remove('selected'));

			selectedColorScheme.classList.add('selected');

			const regularColorSchemeInput = document.getElementById(
				regularColorSchemeInputId
			);

			regularColorSchemeInput.value =
				selectedColorScheme.dataset.colorSchemeId;
		}
	};

	const clickDelegate = delegate(
		colorSchemesContainer,
		'click',
		buttonCssClass,
		callback
	);

	const keyDownDelegate = delegate(
		colorSchemesContainer,
		'keydown',
		buttonCssClass,
		callback
	);

	return {
		dispose() {
			clickDelegate.dispose();
			keyDownDelegate.dispose();
		},
	};
}
