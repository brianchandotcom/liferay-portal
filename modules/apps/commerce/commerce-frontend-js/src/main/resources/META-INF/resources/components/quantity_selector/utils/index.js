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

export const MAX_QUANTITY_CAP = 100;

export const UPDATE_AFTER = 500;

export const generateQuantityOptions = ({
	allowedQuantities = [],
	maxQuantity = 1,
	minQuantity = 1,
	multipleQuantity = 1,
}) => {
	if (allowedQuantities?.length > 0) {
		return allowedQuantities.map((value) => ({
			label: value.toString(),
			value,
		}));
	}

	const quantityOptions = [];

	/**
	 * As this is used to render a ClaySelectWithOption component,
	 * in order to avoid maximum call stack size errors, we temporarily need
	 * to put a cap to the number of options.
	 *
	 * @type {number}
	 */
	const quantityOptionsCap = Math.min(
		MAX_QUANTITY_CAP,
		maxQuantity / multipleQuantity
	);

	for (let i = minQuantity; i <= quantityOptionsCap; i++) {
		const value = i * multipleQuantity;

		quantityOptions.push({
			label: value.toString(),
			value,
		});
	}

	return quantityOptions;
};
