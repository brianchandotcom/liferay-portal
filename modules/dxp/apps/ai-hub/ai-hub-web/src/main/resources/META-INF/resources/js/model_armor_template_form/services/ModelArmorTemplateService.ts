/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ModelArmorTemplate} from '../types/ModelArmorTemplate';

const MODEL_ARMOR_TEMPLATE_BASE_URI = '/o/ai-hub/model-armor-templates';

async function getModelArmorTemplate(externalReferenceCode: string) {
	const response = await fetch(
		`${MODEL_ARMOR_TEMPLATE_BASE_URI}/by-external-reference-code/${externalReferenceCode}`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	return response.json();
}

async function putModelArmorTemplate(modelArmorTemplate: ModelArmorTemplate) {
	const response = await fetch(
		`${MODEL_ARMOR_TEMPLATE_BASE_URI}/by-external-reference-code/${modelArmorTemplate.externalReferenceCode}`,
		{
			body: JSON.stringify(modelArmorTemplate),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	return response.json();
}

export {getModelArmorTemplate, putModelArmorTemplate};
