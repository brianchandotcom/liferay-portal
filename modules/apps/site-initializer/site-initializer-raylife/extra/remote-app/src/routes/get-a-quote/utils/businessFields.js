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

export const NAICS_ALLOWED = {
	OWN_BRAND_LABEL: ['453910', '722513', '424930'],
	PERCENT_SALES: ['453910', '424930'],
};
export const SEGMENT_ALLOWED = {
	OVERALL_SALES: ['Retail'],
};

export function businessTotalFields(properties) {
	let fieldCount = 4;

	if (NAICS_ALLOWED.OWN_BRAND_LABEL.includes(properties?.naics)) {
		fieldCount++;
	}
	if (NAICS_ALLOWED.PERCENT_SALES.includes(properties?.naics)) {
		fieldCount++;
	}
	if (SEGMENT_ALLOWED.OVERALL_SALES.includes(properties?.segment)) {
		fieldCount++;
	}

	return fieldCount;
}

export function validatePercentSales(naics) {
	return NAICS_ALLOWED.PERCENT_SALES.includes(naics);
}
export function validateOwnBrandLabel(naics) {
	return NAICS_ALLOWED.OWN_BRAND_LABEL.includes(naics);
}
export function validateOverallSales(segment) {
	return SEGMENT_ALLOWED.OVERALL_SALES.includes(segment);
}
