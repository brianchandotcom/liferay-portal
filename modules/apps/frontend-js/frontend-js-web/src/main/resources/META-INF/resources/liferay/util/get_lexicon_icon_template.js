/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getLexiconIconTpl(icon, cssClass = '') {
	return (
		`<svg aria-hidden="true" class="lexicon-icon lexicon-icon-${icon} ${cssClass}" focusable="false" role="presentation">` +
		`<use href="${Liferay.Icons.spritemap}#${icon}" />` +
		'</svg>'
	);
}
