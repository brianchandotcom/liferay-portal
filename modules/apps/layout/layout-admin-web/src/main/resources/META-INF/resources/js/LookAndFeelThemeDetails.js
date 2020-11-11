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
