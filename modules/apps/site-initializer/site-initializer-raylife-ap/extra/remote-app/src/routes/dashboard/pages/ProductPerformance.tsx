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
import React, {useState} from 'react';

import Header from '../../../common/components/header';

const PRODUCT_LIST = [
	'All',
	'Auto',
	'General Liability',
	'Professional Liability',
	'Workers Compensation',
	'Business Owners Policy',
];

const TIME_PERIODS = ['YTD', '3 MO', '6 MO'];

const ProductPerformance = () => {
	const [choosenProduct, setChoosenProduct] = useState(PRODUCT_LIST[0]);
	const [timePeriod, setTimePeriod] = useState(TIME_PERIODS[0]);

	return (
		<div className="d-flex flex-wrap ray-dashboard-product-performance">
			<div className="col-5 left-container px-0">
				<Header
					className="header-row px-4 py-3"
					title="Product Performance"
				/>

				<div className="p-5"></div>
			</div>

			<div className="col-7 px-0 right-container">
				<div className="align-items-center d-flex header-row justify-content-between px-4 py-3">
					<p className="m-0 text-paragraph">
						<ClayButton
							className={classNames('general-filter mr-1', {
								'font-weight-bolder': choosenProduct === 'All',
							})}
							displayType="unstyled"
							onClick={() => setChoosenProduct('All')}
						>
							All
						</ClayButton>

						{choosenProduct !== 'All' && (
							<>
								<ClayIcon
									className="font-weight-bolder mr-1"
									symbol="angle-right-small"
								/>

								<span className="font-weight-bolder">{`${choosenProduct}`}</span>
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
