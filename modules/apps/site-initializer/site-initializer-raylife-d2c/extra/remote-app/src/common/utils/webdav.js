/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLiferaySiteName} from './liferay';

export function getWebDavUrl() {
	const siteName = getLiferaySiteName().replace('/web/', '');

	return `${Liferay.ThemeDisplay.getPathContext()}/webdav/${siteName}/document_library/`;
}
