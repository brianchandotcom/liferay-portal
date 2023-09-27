/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLayoutDataItemUniqueClassName from './getLayoutDataItemUniqueClassName';

export default function hasDraftSubmitChild(itemId) {
	const element = document.querySelector(
		`.${getLayoutDataItemUniqueClassName(itemId)}`
	);

	return !!Array.from(element.querySelectorAll('[name="status"][value="2"]'))
		.length;
}
