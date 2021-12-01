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

export function getProductMinQuantity(minQuantity = 1, multipleQuantity = 1) {
	if (multipleQuantity <= 1) {
		return minQuantity;
	}

	const minDifference = minQuantity % multipleQuantity;

	if (!minDifference) {
		return multipleQuantity;
	}

	return minQuantity + multipleQuantity - minDifference;
}

export function getProductMaxQuantity(maxQuantity, multipleQuantity = 1) {
	if (!maxQuantity) {
		return null;
	}

	if (multipleQuantity <= 1) {
		return maxQuantity;
	}

	const maxDifference = maxQuantity % multipleQuantity;

	return maxQuantity - maxDifference;
}
