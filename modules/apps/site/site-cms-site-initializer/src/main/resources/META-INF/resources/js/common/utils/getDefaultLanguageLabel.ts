/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type DefaultLanguageLabels = {
	locale: string;
	[key: string]: string;
};

let labels: DefaultLanguageLabels | null = null;

export function setDefaultLanguageLabels(
	defaultLanguageLabels: DefaultLanguageLabels
) {
	labels = defaultLanguageLabels;
}

export function getDefaultLanguageLabel(key: string): string {
	if (labels && labels[key] !== undefined) {
		return labels[key];
	}

	return Liferay.Language.get(key);
}
