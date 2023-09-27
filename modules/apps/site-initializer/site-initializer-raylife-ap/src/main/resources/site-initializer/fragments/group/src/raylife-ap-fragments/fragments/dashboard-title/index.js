/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line no-undef
const userNameTitle = fragmentElement.querySelector('#user-name');

userNameTitle.innerText = userNameTitle.innerText.replace(
	'{{name}}',
	Liferay.ThemeDisplay.getUserName()
);
