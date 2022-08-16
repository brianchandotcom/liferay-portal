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

import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import Header from '../../../common/components/header';
import ProductList, {
	ProductCell,
} from '../../../common/components/product-list';

const PRODUCT_SALES_GOAL = [
	{
		active: false,
		productName: 'Auto',
		salesGoal: 102000,
		totalSales: 120000,
	},
	{
		active: false,
		productName: 'Business Owner Policy',
		salesGoal: 102000,
		totalSales: 90000,
	},
	{
		active: false,
		productName: 'General Liability',
		salesGoal: 102000,
		totalSales: 10000,
	},
	{
		active: false,
		productName: 'Professional Liability',
		salesGoal: 102000,
		totalSales: 150000,
	},
	{
		active: false,
		productName: 'Workers Compensation',
		salesGoal: 102000,
		totalSales: 60000,
	},
];

const TIME_PERIODS = ['YTD', '3 MO', '6 MO'];

const ProductPerformance = () => {
	const [products, setProducts] = useState<ProductCell[]>([]);
	const [timePeriod, setTimePeriod] = useState(TIME_PERIODS[0]);

	useEffect(() => {
		setProducts(PRODUCT_SALES_GOAL);
	}, []);

	const isFilterAllActive = (product: ProductCell) => !product.active;
	const findActiveProduct = products.find((product) => product.active)
		?.productName;

	const handleProductFilterToggle = (productName: string) => {
		const newProducts = products.map((product) => {
			product.productName === productName
				? (product.active = true)
				: (product.active = false);

			return product;
		});

		setProducts(newProducts);
	};

	return (
		<div className="d-flex flex-wrap ray-dashboard-product-performance">
			<div className="col-md-5 left-container px-0">
				<Header
					className="header-row px-4 py-3"
					title="Product Performance"
				/>

				<ProductList
					onSelect={handleProductFilterToggle}
					productList={products}
				/>
			</div>

			<div className="col-md-7 px-0 right-container">
				<div className="align-items-center d-flex header-row justify-content-between px-4 py-3">
					<p className="m-0 text-paragraph">
						<ClayButton
							className={classNames('general-filter mr-1', {
								'disabled font-weight-bolder': products.every(
									isFilterAllActive
								),
							})}
							displayType="unstyled"
							onClick={() => {
								if (!products.every(isFilterAllActive)) {
									handleProductFilterToggle('All');
								}
							}}
						>
							All
						</ClayButton>

						{!products.every(isFilterAllActive) && (
							<>
								<ClayIcon
									className="mr-1"
									symbol="angle-right-small"
								/>

								<span className="font-weight-bolder">{`${findActiveProduct}`}</span>
							</>
						)}
					</p>

					<ClaySelect
						className="product-performance-select"
						onChange={({target}) => {
							setTimePeriod(target.value);
						}}
						sizing="sm"
						value={timePeriod}
					>
						{TIME_PERIODS.map((timePeriod, index) => (
							<ClaySelect.Option
								key={index}
								label={timePeriod}
								value={timePeriod}
							/>
						))}
					</ClaySelect>
				</div>

				<div className="p-5"></div>
			</div>
		</div>
	);
};

export default ProductPerformance;
