import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

const currentFormatter = Intl.NumberFormat('en-US');

const getClassName = (value) =>
	classNames({
		checkIcon: !!value,
		timesIcon: !value,
	});

const getCurrency = (price) => currentFormatter.format(price);

const parseValue = (value) => {
	if (value === 'true' || value === 'false' || typeof value === 'boolean') {
		return null;
	}

	return `$${getCurrency(value)}`;
};

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
			value: moneyAndSecurities,
		},
	];

	return (
		<ul>
			{values.map(({title, value}, index) => (
				<li key={index}>
					<div className={getClassName(value)}>
						<div>
							<ClayIcon
								symbol={value ? 'check' : 'times'}
								width={50}
							/>
						</div>
						{title}
					</div>
					{parseValue(value)}
				</li>
			))}
		</ul>
	);
};

const ProductComparison = ({
	category,
	mostPopular,
	price,
	promo,
	...otherValues
}) => (
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
			{mostPopular && 'Most Popular'}
		</div>

		<div className="quote-content">
			<div className="quote-header">
				<div className="title">{category}</div>

				<div className="value">
					&#36;{price}
					<div>/yr</div>
				</div>

				<div className="subtitle">
					Get covered for <span>&#36;{promo} today</span>
				</div>
			</div>

			<div className="quote-body">
				<ListItems {...otherValues} />
			</div>

			<div className="quote-footer">
				<div className="d-flex justify-content-center">
					<button
						className={classNames({
							'most-popular': mostPopular,
							'no-most-popular': !mostPopular,
						})}
						id="purchase"
						type="button"
					>
						PURCHASE THIS POLICY
					</button>
				</div>

				<div className="d-flex justify-content-center">
					<button id="details" type="button">
						Policy Details
					</button>
				</div>
			</div>
		</div>
	</div>
);

export default ProductComparison;
