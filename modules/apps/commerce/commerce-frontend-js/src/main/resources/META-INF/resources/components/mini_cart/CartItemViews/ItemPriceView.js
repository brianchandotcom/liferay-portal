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
import React from 'react';

import {collectDiscountLevels, isNonnull} from '../util/index';

function ItemPriceView({displayDiscountLevels, price}) {
	const {
		discountPercentage,
		finalPriceFormatted,
		priceFormatted,
		promoPrice,
		promoPriceFormatted,
	} = price;

	const discountLevels = displayDiscountLevels
			? collectDiscountLevels(price)
			: [],
		hasPromo = isNonnull(promoPrice),
		hasDiscount = isNonnull(discountPercentage, ...discountLevels);

	return (
		<div className={'price'}>
			<span className="price-label">
				{Liferay.Language.get('catalog-price')}
			</span>
			<span
				className={classnames(
					'price-value',
					(hasPromo || hasDiscount) && 'price-value-inactive'
				)}
			>
				{priceFormatted}
			</span>

			{hasPromo && (
				<>
					<span className={'price-label'}>
						{Liferay.Language.get('promo-price')}
					</span>
					<span
						className={classnames(
							'price-value price-value-promo',
							hasDiscount && 'price-value-inactive'
						)}
					>
						{promoPriceFormatted}
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
									className={'price-value-percentages'}
									key={index}
								>
									{level.slice(-2) === '00'
										? level.slice(0, level.length - 3)
										: level}
								</span>
							))
						) : (
							<span>&ndash;{discountPercentage}%</span>
						)}
					</span>
					<span className={'price-label'}>
						{Liferay.Language.get('final-price')}
					</span>
					<span className={'price-value price-value-final'}>
						{finalPriceFormatted}
					</span>
				</>
			)}
		</div>
	);
}

ItemPriceView.defaultProps = {
	displayDiscountLevels: false,
};

ItemPriceView.propTypes = {
	displayDiscountLevels: PropTypes.bool,
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
};

export default ItemPriceView;
