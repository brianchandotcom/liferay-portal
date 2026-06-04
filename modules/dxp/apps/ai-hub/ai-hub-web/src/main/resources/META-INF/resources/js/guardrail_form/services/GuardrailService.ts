/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {Guardrail} from '../types/Guardrail';

const normalizeKey = <T extends string>(value: {key: T}): T => value.key;

async function getGuardrail(externalReferenceCode: string): Promise<Guardrail> {
	const response = await fetch(
		`/o/ai-hub/guardrails/by-external-reference-code/${externalReferenceCode}`,
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

async function postGuardrail(guardrail: Guardrail): Promise<Guardrail> {
	const response = await fetch('/o/ai-hub/v1.0/guardrails', {
		body: JSON.stringify(guardrail),
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

async function putGuardrail(guardrail: Guardrail): Promise<Guardrail> {
	const response = await fetch(
		`/o/ai-hub/v1.0/guardrails/by-external-reference-code/${guardrail.externalReferenceCode}`,
		{
			body: JSON.stringify(guardrail),
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

export {getGuardrail, postGuardrail, putGuardrail};
