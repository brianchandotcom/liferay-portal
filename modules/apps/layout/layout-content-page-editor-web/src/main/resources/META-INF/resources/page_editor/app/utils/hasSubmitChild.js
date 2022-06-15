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

import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';

function isVisible(element, globalContext) {
	const computedStyle = globalContext.window.getComputedStyle(element);
	const {parentElement} = element;

	return (
		computedStyle.display !== 'none' &&
		computedStyle.visibility !== 'collapse' &&
		computedStyle.visibility !== 'hidden' &&
		!element.hasAttribute('hidden') &&
		!element.hasAttribute('aria-hidden') &&
		(!parentElement || isVisible(parentElement, globalContext))
	);
}

export default function hasSubmitChild(itemId, globalContext) {
	const element = document.querySelector(
		`.${getLayoutDataItemUniqueClassName(itemId)}`
	);

	return Array.from(
		element.querySelectorAll(
			'input[type=submit], button[type=submit], button:not([type])'
		)
	).some((buttonElement) => {
		return isVisible(buttonElement, globalContext);
	});
}
