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

export default function getLexiconIcon(icon, cssClass = '') {
	if (!icon) {
		throw new TypeError('Parameter icon must be provided');
	}

	function getLexiconIconHTML(iconName, cssClass) {
		return `<svg
				aria-hidden="true"
				class="lexicon-icon lexicon-icon-${iconName} ${cssClass}"
				focusable="false"
				role="presentation"
			>
				<use href="${themeDisplay.getPathThemeImages()}/clay/icons.svg#${iconName}" />
			</svg>`;
	}

	const lexiconIconTemplate = getLexiconIconHTML(icon, cssClass);
	const tempElement = document.createElement('div');

	tempElement.innerHTML = lexiconIconTemplate;

	return tempElement.firstChild;
}
