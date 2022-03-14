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
import ClayIcon from '@clayui/icon';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import ProgressBar from '../../../components/ProgressBar';
import useTotalTestCases from '../../../data/useTotalTestCases';
import {getTestrayBuilds} from '../../../graphql/queries';
import i18n from '../../../i18n';
import useRoutineActions from './useRoutineActions';

const Routine = () => {
	const {actionsRoutine} = useRoutineActions();
	const {barChart, colors} = useTotalTestCases();

	return (
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
					colors,
					columns: barChart.columns,
					stack: {
						normalize: true,
					},
					type: 'area',
				}}
				legend={{position: 'top-right'}}
			/>

			<ListView
				initialContext={{
					filters: {
						columns: {
							in_progress: false,
							passed: false,
							total: false,
							untested: false,
						},
					},
				}}
				query={getTestrayBuilds}
				tableProps={{
					actions: actionsRoutine,
					columns: [
						{
							key: 'status',
							render: (_, {promoted}) => {
								return (
									<>
										{promoted && (
											<ClayIcon
												className="mr-3"
												symbol="star"
											/>
										)}

										<ClayIcon
											color="darkblue"
											symbol="circle"
										/>
									</>
								);
							},
							value: i18n.translate('status'),
						},
						{key: 'dateCreated', size: 'sm', value: 'Create Date'},
						{key: 'gitHash', value: 'Git Hash'},
						{
							key: 'product_version',
							render: (_, {testrayProductVersion}) =>
								testrayProductVersion?.name,
							value: 'Product Version',
						},
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							value: i18n.translate('build'),
						},
						{key: 'failed', value: i18n.translate('failed')},
						{key: 'blocked', value: i18n.translate('blocked')},
						{key: 'untested', value: i18n.translate('untested')},
						{
							key: 'in_progress',
							value: i18n.translate('in-progress'),
						},
						{key: 'passed', value: i18n.translate('passed')},
						{key: 'test_fix', value: i18n.translate('test-fix')},
						{key: 'total', value: i18n.translate('total')},
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
				transformData={(data) => data?.testrayBuilds}
			/>
		</Container>
	);
};

export default Routine;
