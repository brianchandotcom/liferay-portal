/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {DEFAULT_MODEL_ARMOR_TEMPLATE} from '../constants';
import {
	FilterLevel,
	ModelArmorTemplate,
	RAILevel,
} from '../types/ModelArmorTemplate';

const MODEL_ARMOR_TEMPLATE_BASE_URI = '/o/ai-hub/model-armor-templates';

const normalizeKey = <T extends string>(
	value: {key: T} | T | null | undefined,
	fallback: T
): T => {
	if (typeof value === 'object' && value !== null) {
		return value.key;
	}

	return value ?? fallback;
};

async function getModelArmorTemplate(
	externalReferenceCode: string
): Promise<ModelArmorTemplate> {
	const response = await fetch(
		`${MODEL_ARMOR_TEMPLATE_BASE_URI}/by-external-reference-code/${externalReferenceCode}`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	const raw = await response.json();

	return {
		...DEFAULT_MODEL_ARMOR_TEMPLATE,
		...raw,
		guardrailType: normalizeKey(raw.guardrailType, ''),
		piAndJailbreakConfidenceLevel: normalizeKey<FilterLevel>(
			raw.piAndJailbreakConfidenceLevel,
			'mediumAndAbove'
		),
		raiDangerousLevel: normalizeKey<RAILevel>(
			raw.raiDangerousLevel,
			'none'
		),
		raiHarassmentLevel: normalizeKey<RAILevel>(
			raw.raiHarassmentLevel,
			'none'
		),
		raiHateSpeechLevel: normalizeKey<RAILevel>(
			raw.raiHateSpeechLevel,
			'none'
		),
		raiSexuallyExplicitLevel: normalizeKey<RAILevel>(
			raw.raiSexuallyExplicitLevel,
			'none'
		),
	};
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
