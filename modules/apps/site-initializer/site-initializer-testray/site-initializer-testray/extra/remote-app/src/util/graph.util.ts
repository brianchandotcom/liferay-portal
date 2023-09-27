/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LABEL_GREATER_THAN_99, LABEL_LESS_THAN_1} from './constants';

export function findAndReplaceProperty(
	template: string,
	properties: any
): string {
	let finalTemplate = template;

	for (const property in properties) {
		finalTemplate = finalTemplate.replace(
			`{${property}}`,
			properties[property]
		);
	}

	return finalTemplate;
}

export function getPercentLabel(percent: number) {
	let percentValue: string | number = Math.round(percent) || 0;

	if (percent > 99 && percent < 100) {
		percentValue = LABEL_GREATER_THAN_99;
	}
	else if (percent > 0 && percent < 1) {
		percentValue = LABEL_LESS_THAN_1;
	}

	const percentLabel = `${percentValue}%`;

	return percentLabel;
}
