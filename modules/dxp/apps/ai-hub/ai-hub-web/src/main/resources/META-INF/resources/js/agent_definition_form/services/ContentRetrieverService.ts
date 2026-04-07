/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const CONTENT_RETRIEVER_BASE_URI = '/o/ai-hub/content-retrievers';

async function getContentRetrievers() {
	const response = await fetch(
		`${CONTENT_RETRIEVER_BASE_URI}?fields=externalReferenceCode,title`,
		{
			method: 'GET',
		}
	);

	return response.json();
}

export {getContentRetrievers};
