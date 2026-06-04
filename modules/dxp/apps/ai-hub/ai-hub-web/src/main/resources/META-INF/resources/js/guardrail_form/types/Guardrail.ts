/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FilterLevel = 'lowAndAbove' | 'mediumAndAbove' | 'high';

export type RAILevel = 'none' | FilterLevel;

export type Guardrail = {
	active: boolean;
	description: string;
	externalReferenceCode: string;
	guardrailType: string;
	maliciousUriFilterEnabled: boolean;
	multilanguageDetectionEnabled: boolean;
	piAndJailbreakConfidenceLevel: FilterLevel;
	piAndJailbreakFilterEnabled: boolean;
	raiDangerousLevel: RAILevel;
	raiHarassmentLevel: RAILevel;
	raiHateSpeechLevel: RAILevel;
	raiSexuallyExplicitLevel: RAILevel;
	sdpFilterEnabled: boolean;
	title_i18n: Liferay.Language.LocalizedValue<string>;
};
