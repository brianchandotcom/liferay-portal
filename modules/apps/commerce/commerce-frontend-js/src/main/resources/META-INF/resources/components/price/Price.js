/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	const isPriceOnApplication = activePrice.priceOnApplication;

	const isUnitPricePriceOnApplication = () => {
		return (
			activePrice.price === 'price-on-application' ||
			(activePrice.price === 0 && hasPromo)
		);
	};

	const updatePrice = ({cpInstance}) =>
		setActivePrice((currentPrice) => ({
			...currentPrice,
			...adaptLegacyPriceModel(cpInstance.price),
		}));

	useEffect(() => {
		Liferay.on(`${namespace}${CP_INSTANCE_CHANGED}`, updatePrice);

		return () => {
			Liferay.detach(`${namespace}${CP_INSTANCE_CHANGED}`, updatePrice);
		};
	}, [namespace]);

	useEffect(() => {
		setActivePrice(adaptLegacyPriceModel(price));
	}, [price]);

	const Component = (
		<>
			{!isPriceOnApplication && (
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
						{isUnitPricePriceOnApplication() ? (
							<>{Liferay.Language.get('price-on-application')}</>
						) : (
							<>{activePrice.priceFormatted}</>
						)}
					</span>

					{hasPromo && (
						<>
							<span className="price-label">
								{Liferay.Language.get('promotion-price')}
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
												? level.slice(
														0,
														level.length - 3
												  )
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
			)}

			{isPriceOnApplication && (
				<>
					<span className="price-label">
						{Liferay.Language.get('list-price')}
					</span>
					<span className="price-on-application price-value">
						{Liferay.Language.get('price-on-application')}
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
		priceOnApplication: PropTypes.bool,
		promoPrice: PropTypes.number,
		promoPriceFormatted: PropTypes.string,
	}).isRequired,
	standalone: PropTypes.bool,
};

export default Price;
