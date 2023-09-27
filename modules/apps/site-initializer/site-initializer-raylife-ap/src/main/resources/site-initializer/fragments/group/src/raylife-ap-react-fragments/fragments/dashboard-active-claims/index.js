/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import BarChart from '../../../common/components/bar-chart';
import Table from '../../../common/components/table';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import formatDate from '../../../common/utils/dateFormatter';
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
					height={200}
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
