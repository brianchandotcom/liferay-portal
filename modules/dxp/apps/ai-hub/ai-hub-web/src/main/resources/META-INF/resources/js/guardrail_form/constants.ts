/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Guardrail} from './types/Guardrail';

export const DEFAULT_GUARDRAIL: Guardrail = {
	active: true,
	description: '',
	externalReferenceCode: '',
	guardrailType: '',
	maliciousUriFilterEnabled: false,
	multilanguageDetectionEnabled: false,
	piAndJailbreakConfidenceLevel: 'mediumAndAbove',
	piAndJailbreakFilterEnabled: false,
	raiDangerousLevel: 'none',
	raiHarassmentLevel: 'none',
	raiHateSpeechLevel: 'none',
	raiSexuallyExplicitLevel: 'none',
	sdpFilterEnabled: false,
	title_i18n: {},
};

export type DetectionField = Extract<
	keyof Guardrail,
	| 'maliciousUriFilterEnabled'
	| 'piAndJailbreakFilterEnabled'
	| 'sdpFilterEnabled'
>;

export type RAIFilterField = Extract<
	keyof Guardrail,
	| 'raiDangerousLevel'
	| 'raiHarassmentLevel'
	| 'raiHateSpeechLevel'
	| 'raiSexuallyExplicitLevel'
>;

export const GUARDRAIL_TYPE_OPTIONS = [
	{label: Liferay.Language.get('input'), value: 'input'},
	{label: Liferay.Language.get('output'), value: 'output'},
];

export const RAI_LEVEL_OPTIONS = [
	{
		description: Liferay.Language.get('none-help'),
		label: Liferay.Language.get('none'),
		value: 'none',
	},
	{
		description: Liferay.Language.get('low-and-above-help'),
		label: Liferay.Language.get('low-and-above'),
		value: 'lowAndAbove',
	},
	{
		description: Liferay.Language.get('medium-and-above-help'),
		label: Liferay.Language.get('medium-and-above'),
		value: 'mediumAndAbove',
	},
	{
		description: Liferay.Language.get('high-help'),
		label: Liferay.Language.get('high'),
		value: 'high',
	},
];

export const RAI_FILTERS: Array<{
	field: RAIFilterField;
	helpText: string;
	label: string;
}> = [
	{
		field: 'raiHateSpeechLevel',
		helpText: Liferay.Language.get('hate-speech-help'),
		label: Liferay.Language.get('hate-speech'),
	},
	{
		field: 'raiDangerousLevel',
		helpText: Liferay.Language.get('dangerous-help'),
		label: Liferay.Language.get('dangerous'),
	},
	{
		field: 'raiSexuallyExplicitLevel',
		helpText: Liferay.Language.get('sexually-explicit-help'),
		label: Liferay.Language.get('sexually-explicit'),
	},
	{
		field: 'raiHarassmentLevel',
		helpText: Liferay.Language.get('harassment-help'),
		label: Liferay.Language.get('harassment'),
	},
];

export const DETECTIONS: Array<{
	description: string;
	field: DetectionField;
	label: string;
}> = [
	{
		description: Liferay.Language.get('malicious-uri-filter-help'),
		field: 'maliciousUriFilterEnabled',
		label: Liferay.Language.get('malicious-uri-filter'),
	},
	{
		description: Liferay.Language.get(
			'prompt-injection-and-jailbreak-filter-help'
		),
		field: 'piAndJailbreakFilterEnabled',
		label: Liferay.Language.get('prompt-injection-and-jailbreak-filter'),
	},
	{
		description: Liferay.Language.get('sensitive-data-protection-help'),
		field: 'sdpFilterEnabled',
		label: Liferay.Language.get('sensitive-data-protection'),
	},
];
