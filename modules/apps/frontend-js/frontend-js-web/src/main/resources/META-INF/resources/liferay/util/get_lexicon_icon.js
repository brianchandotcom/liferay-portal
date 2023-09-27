/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<use href="${Liferay.Icons.spritemap}#${iconName}" />
			</svg>`;
	}

	const lexiconIconTemplate = getLexiconIconHTML(icon, cssClass);
	const tempElement = document.createElement('div');

	tempElement.innerHTML = lexiconIconTemplate;

	return tempElement.firstChild;
}
