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

const toBool = (value) =>
	value && value !== undefined && value !== null && value === 'true';

export function verifyInputAgentPage(properties, nextSection) {
	const auxBusiness = properties?.business?.hasSellProductsUnderOwnBrand;
	const auxEmployees = properties?.employees?.hasFein;
	const auxProperty = properties?.property?.isThereDivingBoards;
	let contextualMessage = '';

	if (toBool(auxBusiness) && nextSection?.section === 'employees') {
		contextualMessage =
			'We need to ask you for more information about your business.';
	}
	else if (!toBool(auxEmployees) && nextSection?.section === 'property') {
		contextualMessage =
			'We need to ask you for more information about your employees.';
	}
	else if (toBool(auxProperty)) {
		contextualMessage =
			'We need to ask you for more information about your business location.';
	}

	return contextualMessage;
}
