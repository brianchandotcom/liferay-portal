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

const DISCOUNT_LEVEL_PREFIX = 'discountPercentageLevel';

export function adaptLegacyPriceModel(priceModel) {
	if (`${DISCOUNT_LEVEL_PREFIX}1` in priceModel) {
		return priceModel;
	}

	const {
		discountPercentage,
		discountPercentages,
		finalPrice,
		price,
		promoPrice,
	} = priceModel;

	return {
		discountPercentage: parseFloat(discountPercentage || 0),
		finalPriceFormatted: finalPrice || price,
		priceFormatted: price,

		/**
		 * The following matches numbers in the
		 * string value (e.g. "% 90.00" => "9")
		 * and is solely needed to check
		 * that the promoPrice value is non-null.
		 *
		 * Then the promoPriceFormatted must be used.
		 */
		promoPrice: promoPrice ? promoPrice.match(/\d/gi)[0] : '0',
		promoPriceFormatted: promoPrice,
		...(discountPercentages || ['0', '0', '0', '0']).reduce(
			(discountLevels, percentage, i) => ({
				...discountLevels,
				[`${DISCOUNT_LEVEL_PREFIX}${i + 1}`]: parseFloat(percentage),
			}),
			{}
		),
	};
}

export function collectDiscountLevels(price) {
	return Object.keys(price).reduce((levels, key) => {
		if (key.startsWith(DISCOUNT_LEVEL_PREFIX)) {
			levels.push(price[key].toFixed(2));
		}

		return levels;
	}, []);
}

export function isNonnull(...values) {
	return !!values.find((value) => parseFloat(value) > 0);
}
