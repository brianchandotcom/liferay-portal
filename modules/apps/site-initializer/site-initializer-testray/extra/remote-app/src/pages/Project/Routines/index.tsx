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
import i18n from '../../../i18n';
import {DATA_COLORS} from '../../../util/constants';
import {getRandomMaximumValue} from '../../../util/mock';

const Routine = () => (
	<Container title={i18n.translate('build-history')}>
		<ClayChart
			axis={{
				y: {
					label: {
						position: 'outer-middle',
						text: i18n.translate('tests').toUpperCase(),
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
						key: 'dateCreated',
						size: 'sm',
						value: i18n.translate('create-date'),
					},
					{key: 'gitHash', value: i18n.translate('git-hash')},
					{
						key: 'product_version',
						render: (_, {testrayProductVersion}) =>
							testrayProductVersion?.name,
						value: i18n.translate('product-version'),
					},
					{
						clickable: true,
						key: 'name',
						size: 'md',
						value: i18n.translate('build'),
					},
					{key: 'failed', value: i18n.translate('failed')},
					{key: 'blocked', value: i18n.translate('blocked')},
					{key: 'test_fix', value: i18n.translate('test-fix')},
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
						size: 'md',
						value: i18n.translate('metrics'),
					},
				],
				navigateTo: ({testrayBuildId}) => `build/${testrayBuildId}`,
			}}
			transformData={(data) => data?.testrayBuilds || {}}
		/>
	</Container>
);

export default Routine;
