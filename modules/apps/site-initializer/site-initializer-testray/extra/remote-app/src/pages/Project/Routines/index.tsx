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

import ClayChart from '@clayui/charts';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import ProgressBar from '../../../components/ProgressBar';
import {getTestrayBuilds} from '../../../graphql/queries';
import {DATA_COLORS} from '../../../util/constants';
import {getRandomMaximumValue} from '../../../util/mock';

const Routine = () => (
	<Container title="Build History">
		<ClayChart
			axis={{
				y: {
					label: {
						position: 'outer-middle',
						text: 'TESTS',
					},
				},
			}}
			data={{
				colors: {
					blocked: DATA_COLORS['metrics.blocked'],
					failed: DATA_COLORS['metrics.failed'],
					incomplete: DATA_COLORS['metrics.incomplete'],
					passed: DATA_COLORS['metrics.passed'],
					test_fix: DATA_COLORS['metrics.test-fix'],
				},
				columns: [
					['passed', ...getRandomMaximumValue(20, 1500)],
					['failed', ...getRandomMaximumValue(20, 100)],
					['blocked', ...getRandomMaximumValue(20, 100)],
					['incomplete', ...getRandomMaximumValue(20, 100)],
					['test_fix', ...getRandomMaximumValue(20, 100)],
				],
				stack: {
					normalize: true,
				},
				type: 'area',
			}}
			legend={{position: 'top-right'}}
		/>

		<ListView
			query={getTestrayBuilds}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'name',
						value: 'Build',
					},
					{key: 'dateCreated', value: 'Create Date'},
					{key: 'gitHash', value: 'Git Hash'},
					{key: 'product_version', value: 'Product Version'},
					{key: 'failed', value: 'Failed'},
					{key: 'blocked', value: 'Blocked'},
					{key: 'test_fix', value: 'Test Fix'},
					{
						key: 'metrics',
						render: () => (
							<ProgressBar
								items={{
									blocked: 0,
									failed: 2,
									incomplete: 0,
									passed: 30,
									test_fix: 0,
								}}
							/>
						),
						value: 'Metrics',
					},
				],
				navigateTo: ({testrayBuildId}) => `build/${testrayBuildId}`,
			}}
			transformData={(data) => data?.c?.testrayBuilds}
			variables={{}}
		/>
	</Container>
);

export default Routine;
