/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	var percentLabel = `${percentValue}%`;

	return percentLabel;
}
