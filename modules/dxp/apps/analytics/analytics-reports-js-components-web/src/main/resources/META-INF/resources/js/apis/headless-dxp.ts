/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const API_PREFIX = '/o/headless-delivery/v1.0';

export function fetchBlogPosting({assetId}: {assetId: string}) {
	return fetch(`${API_PREFIX}/blog-postings/${assetId}`, {
		method: 'GET',
	});
}
