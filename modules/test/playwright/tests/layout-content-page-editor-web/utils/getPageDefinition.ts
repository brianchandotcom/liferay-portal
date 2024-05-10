/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../../../utils/getRandomString';

export default function getPageDefinition(
	pageElements: PageElement[] = []
): PageDefinition {
	return {
		pageElement: {
			id: getRandomString(),
			pageElements,
			type: 'Root',
		},
	};
}
