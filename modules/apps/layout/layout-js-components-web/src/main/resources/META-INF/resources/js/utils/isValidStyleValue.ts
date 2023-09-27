/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function isValidStyleValue(cssProperty: string, value: string) {
	const element = document.createElement('div');

	element.style.setProperty(cssProperty, value);
	element.style.display = 'none';

	document.body.appendChild(element);

	const validValue = element.style.getPropertyValue(cssProperty);

	element.parentElement!.removeChild(element);

	return Boolean(validValue);
}
