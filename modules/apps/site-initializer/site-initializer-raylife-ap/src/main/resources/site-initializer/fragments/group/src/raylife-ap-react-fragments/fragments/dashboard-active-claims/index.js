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

import BarChart from '../../../common/components/bar-chart';

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

	const [titleTotal] = useState(true);

	const reducer = (accumulator, curr) => accumulator + curr;

	const totalSum = dataColumns.filter(Number.isInteger).reduce(reducer);

	return (
		<>
			<div className="active-claims-container d-flex flex-column flex-shrink-0 pb-4 pt-2 px-3">
				<div className="justify-content-between p-2 row">
					<div className="active-claims-title font-weight-bold h4">
						<div>Active Claims</div>
					</div>

					<ClayButton className="btn btn-outline-primary text-uppercase">
						<span className="outline-primary text-paragraph">
							<ClayIcon symbol="plus" />
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
			</div>
		</>
	);
}
