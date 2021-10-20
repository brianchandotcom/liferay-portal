import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

var currencyIntl = new Intl.NumberFormat('en-US', {
	currency: 'USD',
});

const formatValue = (value) => {
	if (value === 'true' || value === 'false' || typeof value === 'boolean') {
		return JSON.parse(value);
	}

	return `$${currencyIntl.format(value)}`;
};

const getClassName = (value) =>
	classNames({
		checkIcon: !!value,
		timesIcon: !value,
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
		<ul>
			{values.map(({title, value}, index) => {
				const formattedValue = formatValue(value);

				return (
					<li key={index}>
						<div className={getClassName(formattedValue)}>
							<div>
								<ClayIcon
									symbol={formattedValue ? 'check' : 'times'}
									width={50}
								/>
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
	onClickPolicyDetails,
	onClickPurchase,
	product,
	purchasable = true,
}) => {
	const {category, mostPopular, price, promo, ...productDetails} = product;

	return (
		<div
			className={classNames({
				'no-most-popular': !mostPopular,
			})}
			id="quote-comparison"
		>
			<div
				className={classNames({
					'most-popular': mostPopular,
					'no-most-popular': !mostPopular,
				})}
			>
				{mostPopular && highlightMostPopularText}
			</div>

			<div className="quote-content">
				<div className="quote-header">
					<div className="title">{category}</div>

					<div className="value">
						&#36;{Number(price).toLocaleString('en-US')}
						<div>/yr</div>
					</div>

					<div className="subtitle">
						Get covered for <span>&#36;{promo} today</span>
					</div>
				</div>

				<div className="quote-body">
					<ListItems {...productDetails} />
				</div>

				<div className="quote-footer">
					{purchasable && (
						<div className="d-flex justify-content-center">
							<button
								className={classNames({
									'most-popular': mostPopular,
									'no-most-popular': !mostPopular,
								})}
								id="purchase"
								onClick={() => onClickPurchase(product)}
								type="button"
							>
								PURCHASE THIS POLICY
							</button>
						</div>
					)}

					<div className="d-flex justify-content-center">
						<button
							id="details"
							onClick={onClickPolicyDetails}
							type="button"
						>
							Policy Details
						</button>
					</div>
				</div>
			</div>
		</div>
	);
};

export default ProductComparison;
