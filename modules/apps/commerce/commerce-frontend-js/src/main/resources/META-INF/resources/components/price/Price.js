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

import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {CP_INSTANCE_CHANGED} from '../../utilities/eventsDefinitions';
import {
	adaptLegacyPriceModel,
	collectDiscountLevels,
	isNonnull,
} from './util/index';

function Price({
	compact,
	displayDiscountLevels,
	namespace,
	netPrice,
	price,
	standalone,
}) {
	const [activePrice, setActivePrice] = useState(
		adaptLegacyPriceModel(price)
	);

	const discountLevels = displayDiscountLevels
		? collectDiscountLevels(activePrice)
		: [];

	const hasDiscount = isNonnull(
		activePrice.discountPercentage,
		...discountLevels
	);
	const hasPromo = isNonnull(activePrice.promoPrice);

	const updatePrice = ({cpInstance}) =>
		setActivePrice((currentPrice) => ({
			...currentPrice,
			...adaptLegacyPriceModel(cpInstance.prices),
		}));

	useEffect(() => {
		if (namespace) {
			Liferay.on(`${namespace}${CP_INSTANCE_CHANGED}`, updatePrice);
		}

		return () => {
			if (namespace) {
				Liferay.detach(
					`${namespace}${CP_INSTANCE_CHANGED}`,
					updatePrice
				);
			}
		};
	}, [namespace]);

	useEffect(() => {
		setActivePrice(adaptLegacyPriceModel(price));
	}, [price]);

	const Component = (
		<>
			<span className="price-label">
				{Liferay.Language.get('list-price')}
			</span>
			<span
				className={classnames({
					'price-value': true,
					'price-value-inactive': hasPromo || hasDiscount,
				})}
			>
				{activePrice.priceFormatted}
			</span>

			{hasPromo && (
				<>
					<span className="price-label">
						{Liferay.Language.get('sale-price')}
					</span>
					<span
						className={classnames(
							'price-value price-value-promo',
							hasDiscount && 'price-value-inactive'
						)}
					>
						{activePrice.promoPriceFormatted}
					</span>
				</>
			)}

			{hasDiscount && (
				<>
					<span className="price-label">
						{Liferay.Language.get('discount')}
					</span>
					<span className="price-value price-value-discount">
						{displayDiscountLevels ? (
							discountLevels.map((level, index) => (
								<span
									className="price-value-percentages"
									key={index}
								>
									{level.slice(-2) === '00'
										? level.slice(0, level.length - 3)
										: level}
								</span>
							))
						) : (
							<span className="price-value-percentage">
								&ndash;{activePrice.discountPercentage}%
							</span>
						)}
					</span>
					<span className="price-label">
						{netPrice
							? Liferay.Language.get('net-price')
							: Liferay.Language.get('gross-price')}
					</span>
					<span className="price-value price-value-final">
						{activePrice.finalPriceFormatted}
					</span>
				</>
			)}
		</>
	);

	return standalone ? (
		Component
	) : (
		<div
			className={classnames({
				compact,
				price: true,
			})}
		>
			{Component}
		</div>
	);
}

Price.defaultProps = {
	compact: false,
	displayDiscountLevels: false,
	namespace: '',
	netPrice: true,
	standalone: false,
};

Price.propTypes = {
	compact: PropTypes.bool,
	displayDiscountLevels: PropTypes.bool.isRequired,
	namespace: PropTypes.string,
	netPrice: PropTypes.bool,
	price: PropTypes.shape({
		currency: PropTypes.string.isRequired,
		discount: PropTypes.number,
		discountFormatted: PropTypes.string,
		discountPercentageLevel1: PropTypes.number,
		discountPercentageLevel2: PropTypes.number,
		discountPercentageLevel3: PropTypes.number,
		discountPercentageLevel4: PropTypes.number,
		finalPrice: PropTypes.number,
		finalPriceFormatted: PropTypes.string,
		price: PropTypes.number.isRequired,
		priceFormatted: PropTypes.string.isRequired,
		promoPrice: PropTypes.number,
		promoPriceFormatted: PropTypes.string,
	}).isRequired,
	standalone: PropTypes.bool,
};

export default Price;
