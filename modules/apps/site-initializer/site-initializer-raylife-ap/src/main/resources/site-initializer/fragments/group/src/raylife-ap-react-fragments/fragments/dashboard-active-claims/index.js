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
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import formatDate from '../../../../../../../../../../extra/remote-app/src/common/utils/dateFormater';
import BarChart from '../../../common/components/bar-chart';
import Table from '../../../common/components/table';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import {redirectTo} from '../../../common/utils/liferay';

export default function () {
	const [dataColumns] = useState([58, 32, 19, 24, 20]);

	dataColumns.unshift('data');

	const [labelColumns] = useState([
		'Auto',
		'Home',
		'Property',
		'Life',
		'Health',
	]);

	labelColumns.unshift('x');

	const colors = [
		'#55C2FF',
		'#FF9A24',
		'#EC676A',
		'#7785FF',
		'#2CAA7A',
		'#6A5ACD',
		'#00BFFF',
		'#20B2AA',
		'#9ACD32',
	];

	const headers = [
		{
			key: 'date',
			value: 'Date',
		},
		{
			key: 'product',
			value: 'Product',
		},
		{
			key: 'claimNumber',
			value: 'Claim Number',
		},
	];

	const dateCreated = 'Dec 10, 2021';

	const dataTable = [
		{
			claimNumber: '993212',
			date: formatDate(new Date(dateCreated)),
			product: 'Auto',
		},
		{
			claimNumber: '448323',
			date: formatDate(new Date(dateCreated)),
			product: 'Home',
		},
		{
			claimNumber: '566323',
			date: formatDate(new Date(dateCreated)),
			product: 'Life',
		},
	];
	const [titleTotal] = useState(true);

	const reducer = (accumulator, curr) => accumulator + curr;

	const totalSum = dataColumns.filter(Number.isInteger).reduce(reducer);

	return (
		<ClayIconProvider>
			<div className="active-claims-container d-flex flex-column px-3">
				<div className="active-claims-title align-items-center d-flex font-weight-bold h4 justify-content-between mt-3">
					<div>Active Claims</div>

					<ClayButton className="btn btn-active-claims-title btn-outline-primary text-uppercase">
						<span className="outline-primary text-paragraph">
							<ClayIcon className="mr-2" symbol="plus" />
						</span>
						Claims
					</ClayButton>
				</div>

				<BarChart
					colors={colors}
					dataColumns={dataColumns}
					labelColumns={labelColumns}
					titleTotal={titleTotal}
					totalSum={totalSum}
				/>

				<Table data={dataTable} headers={headers} />

				<div className="align-items-center bottom-container d-flex justify-content-end pb-3 px-3">
					<ClayButton
						className="bg-neutral-0 border-neutral-0 btn btn-inverted btn-solid btn-style-primary text-paragraph text-uppercase"
						onClick={() => redirectTo('claims')}
					>
						All Claims
						<ClayIcon className="ml-2" symbol="order-arrow-right" />
					</ClayButton>
				</div>
			</div>
		</ClayIconProvider>
	);
}
