/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ModelArmorTemplate} from '../types/ModelArmorTemplate';

const normalizeKey = <T extends string>(value: {key: T}): T => value.key;

async function getModelArmorTemplate(
	externalReferenceCode: string
): Promise<ModelArmorTemplate> {
	const response = await fetch(
		`/o/ai-hub/model-armor-templates/by-external-reference-code/${externalReferenceCode}`,
		{
			method: 'GET',
		}
	);

	if (!response.ok) {
		throw new Error();
	}

	const raw = await response.json();

	return {
		...raw,
		guardrailType: normalizeKey(raw.guardrailType),
		piAndJailbreakConfidenceLevel: normalizeKey(
			raw.piAndJailbreakConfidenceLevel
		),
		raiDangerousLevel: normalizeKey(raw.raiDangerousLevel),
		raiHarassmentLevel: normalizeKey(raw.raiHarassmentLevel),
		raiHateSpeechLevel: normalizeKey(raw.raiHateSpeechLevel),
		raiSexuallyExplicitLevel: normalizeKey(raw.raiSexuallyExplicitLevel),
	};
}

async function postModelArmorTemplate(
	modelArmorTemplate: ModelArmorTemplate
): Promise<ModelArmorTemplate> {
	const response = await fetch('/o/ai-hub/v1.0/model-armor-templates', {
		body: JSON.stringify(modelArmorTemplate),
		headers: {
			'Content-Type': 'application/json',
		},
		method: 'POST',
	});

	if (!response.ok) {
		const errorBody = await response.json().catch(() => ({}));

		throw new Error(errorBody?.detail || errorBody?.title || '');
	}

	return response.json();
}

async function putModelArmorTemplate(
	modelArmorTemplate: ModelArmorTemplate
): Promise<ModelArmorTemplate> {
	const response = await fetch(
		`/o/ai-hub/v1.0/model-armor-templates/by-external-reference-code/${modelArmorTemplate.externalReferenceCode}`,
		{
			body: JSON.stringify(modelArmorTemplate),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'PUT',
		}
	);

	if (!response.ok) {
		const errorBody = await response.json().catch(() => ({}));

		throw new Error(errorBody?.detail || errorBody?.title || '');
	}

	return response.json();
}

export {getModelArmorTemplate, postModelArmorTemplate, putModelArmorTemplate};
