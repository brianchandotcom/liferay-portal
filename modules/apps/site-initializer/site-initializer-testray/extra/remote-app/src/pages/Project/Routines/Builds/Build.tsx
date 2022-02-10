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

import {Outlet} from 'react-router-dom';

import BarChartComponent from '../../../../components/Charts/BarChart';
import PieChartComponent from '../../../../components/Charts/PieChart';
import Container from '../../../../components/Layout/Container';
import QATable from '../../../../components/Table/QATable';
import {DATA_COLORS} from '../../../../util/constants';
import {runs} from '../../../../util/mock';

const Build = () => {
	return (
		<>
			<Container title="Details">
				<QATable
					items={[
						{title: 'product version', value: ''},
						{
							title: 'description',
							value:
								'Portal hash: df78f2f11fbfd7ff41825673dd9a89ee65c8b5c4; Plugins hash: 60a8e64c1db57588b9f1efda4ba94b9d827b4335; Portal branch: 7.1.x; Bundle: latest;',
						},
						{title: 'git hash', value: ''},
						{title: 'create date', value: 'fev 3, 2022 8:38 PM'},
						{title: 'created by', value: 'John Doe'},
						{title: 'all issues found', value: '-'},
					]}
				/>

				<div className="d-flex mt-4">
					<dl>
						<dd>0 minutes</dd>

						<dd className="small-heading">TOTAL ESTIMATED TIME</dd>
					</dl>

					<dl className="ml-3">
						<dd>0 minutes</dd>

						<dd className="small-heading">REMAINING ESTIMATED</dd>
					</dl>

					<dl className="ml-3">
						<dd>0 minutes</dd>

						<dd className="small-heading">TIME 0 TOTAL ISSUES</dd>
					</dl>
				</div>
			</Container>

			<Container className="mt-4" title="Total Test Cases">
				<div className="row">
					<div className="col-3">
						<PieChartComponent
							data={[
								{
									color: DATA_COLORS['metrics.passed'],
									name: 'passed',
									value: 30529,
								},
								{
									color: DATA_COLORS['metrics.failed'],
									name: 'failed',
									value: 5374,
								},
								{
									color: DATA_COLORS['metrics.blocked'],
									name: 'blocked',
									value: 0,
								},
								{
									color: DATA_COLORS['metrics.test-fix'],
									name: 'test fix',
									value: 0,
								},
								{
									color: DATA_COLORS['metrics.incomplete'],
									name: 'incomplete',
									value: 21,
								},
							]}
							pieProps={{dataKey: 'value'}}
						/>
					</div>

					<div className="col-8 ml-6">
						<BarChartComponent
							bars={[
								{
									dataKey: 'failed',
									fill: DATA_COLORS['metrics.failed'],
								},
								{
									dataKey: 'incomplete',
									fill: DATA_COLORS['metrics.incomplete'],
								},
								{
									dataKey: 'test_fix',
									fill: DATA_COLORS['metrics.test-fix'],
								},
								{
									dataKey: 'blocked',
									fill: DATA_COLORS['metrics.blocked'],
								},
								{
									dataKey: 'passed',
									fill: DATA_COLORS['metrics.passed'],
								},
							]}
							data={runs}
						/>
					</div>
				</div>
			</Container>

			<Outlet />
		</>
	);
};

export default Build;
