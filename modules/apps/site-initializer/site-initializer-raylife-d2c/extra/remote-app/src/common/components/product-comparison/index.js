/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const currencyIntl = new Intl.NumberFormat('en-US', {
	currency: 'USD',
});

const formatValue = (value) => {
	if (value === 'true' || value === 'false' || typeof value === 'boolean') {
		return JSON.parse(value);
	}

	return `$${currencyIntl.format(value)}`;
};

const getClassName = (value) =>
	classNames('d-flex align-items-center', {
		'checkIcon': !!value,
		'text-neutral-8 timesIcon': !value,
	});

const ListItems = ({
	aggregateLimit,
	businessPersonalProperty,
	moneyAndSecurities,
	perOccuranceLimit,
	productRecallOrReplacement,
}) => {
	const values = [
		{
			title: 'Per Occurrence Limit',
			value: perOccuranceLimit,
		},
		{
			title: 'Aggregate Limit',
			value: aggregateLimit,
		},
		{
			title: 'Business Personal Property',
			value: businessPersonalProperty,
		},
		{
			title: 'Product Recall or Replacement',
			value: productRecallOrReplacement,
		},
		{
			title: 'Money & Securities',
			value: moneyAndSecurities || false,
		},
	];

	return (
		<ul className="m-0 p-0">
			{values.map(({title, value}, index) => {
				const formattedValue = formatValue(value);

				return (
					<li
						className="d-flex font-weight-normal justify-content-between text-neutral-8 text-paragraph-sm"
						key={index}
					>
						<div className={getClassName(formattedValue)}>
							<div className="inline-item inline-item-before">
								<div
									className={classNames(
										'align-items-center d-flex icon justify-content-center',
										{
											'border border-primary rounded-circle': !!value,
										}
									)}
								>
									<ClayIcon
										className={classNames({
											'text-brand-primary ': !!value,
											'text-neutral-8': !value,
										})}
										symbol={
											formattedValue ? 'check' : 'times'
										}
										width={50}
									/>
								</div>
							</div>

							<span>{title}</span>
						</div>

						{formattedValue}
					</li>
				);
			})}
		</ul>
	);
};

const ProductComparison = ({
	highlightMostPopularText = 'Most Popular',
	isMobileDevice = false,
	onClickPolicyDetails,
	onClickPurchase,
	product,
	purchasable = true,
}) => {
	const {category, mostPopular, price, promo, ...productDetails} = JSON.parse(
		product.dataJSON
	);

	const promoPrice = Number(promo);

	return (
		<div
			className={classNames('flex-shrink-0 rounded bg-neutral-0', {
				'col-12 mx-auto p-0': !purchasable,
				'ml-auto': purchasable && !isMobileDevice,
				'mt-4': !mostPopular && !isMobileDevice,
				'mx-2': isMobileDevice,
			})}
			id="quote-comparison-item"
		>
			<div
				className={classNames(
					'align-items-center d-flex header-size justify-content-center rounded-top',
					{
						'bg-brand-secondary ': mostPopular,
						'bg-neutral-0': !mostPopular,
					}
				)}
			>
				{mostPopular && (
					<p className="font-weight-bold text-paragraph text-small-caps text-white">
						{highlightMostPopularText}
					</p>
				)}
			</div>

			<div className="d-flex flex-column justify-content-between pb-5 pt-4 px-4 quote-content">
				<div className="quote-header text-center">
					<h4 className="font-weight-bolder text-brand-primary text-capitalize">
						{category}
					</h4>

					<div className="d-flex display-3 flex-row font-weight-bolder justify-content-center text-neutral-10 value">
						<span>
							&#36;{Number(price).toLocaleString('en-US')}
						</span>

						<div className="font-weight-light text-neutral-9">
							/yr
						</div>
					</div>

					<div className="font-weight-normal m-auto mt-1 quote-subtitle text-neutral-8 text-paragraph-xs">
						<span>Minimum payment of </span>
						<></>
						<span className="text-brand-primary">
							&#36;
							{promoPrice % 1 === 0
								? promoPrice
								: promoPrice.toFixed(2)}{' '}
						</span>{' '}
						to get coverage today
					</div>
				</div>

				<div
					className={classNames('quote-body', {
						'select-quote-mobile': !purchasable,
					})}
				>
					<ListItems {...productDetails} />
				</div>

				<div
					className={classNames('border-0 p-0', {
						'select-quote-mobile': !purchasable,
					})}
				>
					{purchasable && (
						<div className="bg-transparent d-flex justify-content-center">
							<ClayButton
								className={classNames('px-4 py-3', {
									'btn-outline-primary': !mostPopular,
									'btn-primary': mostPopular,
								})}
								displayType={null}
								id="purchase"
								onClick={() => onClickPurchase(product)}
							>
								PURCHASE THIS POLICY
							</ClayButton>
						</div>
					)}

					<div className="d-flex justify-content-center">
						<ClayButton
							className="bg-transparent border-0 font-weight-normal mt-3 p-0 text-capitalize text-neutral-8"
							displayType="unstyled"
							id="details"
							onClick={onClickPolicyDetails}
						>
							<u>Policy Details</u>
						</ClayButton>
					</div>
				</div>
			</div>
		</div>
	);
};

export default ProductComparison;
