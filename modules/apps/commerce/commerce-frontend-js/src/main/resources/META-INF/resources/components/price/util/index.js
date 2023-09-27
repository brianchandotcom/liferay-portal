/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		priceFormatted,
		priceOnApplication,
		promoPrice,
		promoPriceFormatted,
	} = priceModel;

	return {
		discountPercentage: parseFloat(discountPercentage || 0),
		finalPriceFormatted: finalPrice || priceFormatted || price,
		price,
		priceFormatted: priceFormatted || price,
		priceOnApplication: priceOnApplication || false,

		/**
		 * The following matches numbers in the
		 * string value (e.g. "% 90.00" => "9")
		 * and is solely needed to check
		 * that the promoPrice value is non-null.
		 *
		 * Then the promoPriceFormatted must be used.
		 */
		promoPrice: promoPrice ? promoPrice.toString().match(/\d/gi)[0] : '0',
		promoPriceFormatted: promoPriceFormatted || promoPrice,
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
