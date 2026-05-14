/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const MODEL_ARMOR_TEMPLATE_BASE_URI = '/o/ai-hub/model-armor-templates';

async function getModelArmorTemplates() {
	const response = await fetch(
		`${MODEL_ARMOR_TEMPLATE_BASE_URI}?fields=externalReferenceCode,name`,
		{
			method: 'GET',
		}
	);

	return response.json();
}

export {getModelArmorTemplates};
