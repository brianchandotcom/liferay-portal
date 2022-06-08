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
import {useNavigate, useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import ProgressBar from '../../../components/ProgressBar';
import useBuildHistory from '../../../data/useBuildHistory';
import {getBuilds} from '../../../graphql/queries';
import i18n from '../../../i18n';
import {filters} from '../../../schema/filter';
import {BUILD_STATUS} from '../../../util/constants';
import dayjs from '../../../util/date';
import {searchUtil} from '../../../util/search';
import RoutineBuildModal from './RoutineBuildModal';
import useRoutineActions from './useRoutineActions';

const Routine = () => {
	const {actionsRoutine, formModal} = useRoutineActions();
	const {barChart, colors} = useBuildHistory();
	const {routineId} = useParams();
	const navigate = useNavigate();

	return (
		<Container>
			<ListView
				forceRefetch={formModal.forceRefetch}
				initialContext={{
					columns: {
						in_progress: false,
						passed: false,
						total: false,
						untested: false,
					},
				}}
				managementToolbarProps={{
					addButton: () => navigate('update'),
					filterFields: filters.build.index as any,
					title: i18n.translate('build-history'),
				}}
				query={getBuilds}
				tableProps={{
					actions: actionsRoutine,
					columns: [
						{
							key: 'status',
							render: (_, {dueStatus, promoted}) => {
								return (
									<>
										{promoted && (
											<span
												title={i18n.translate(
													'promoted'
												)}
											>
												<ClayIcon
													className="mr-3"
													color="darkblue"
													symbol="star"
												/>
											</span>
										)}

										<span
											title={
												(BUILD_STATUS as any)[dueStatus]
													?.label || ''
											}
										>
											<ClayIcon
												className={
													(BUILD_STATUS as any)[
														dueStatus
													]?.color
												}
												symbol="circle"
											/>
										</span>
									</>
								);
							},
							value: i18n.translate('status'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (dateCreated) =>
								dayjs(dateCreated).format('lll'),
							size: 'sm',
							value: 'Create Date',
						},
						{
							clickable: true,
							key: 'gitHash',
							value: 'Git Hash',
						},
						{
							clickable: true,
							key: 'product_version',
							render: (_, {productVersion}) =>
								productVersion?.name,
							value: 'Product Version',
						},
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							value: i18n.translate('build'),
						},
						{
							clickable: true,
							key: 'failed',
							render: () => 0,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: () => 0,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'untested',
							render: () => 0,
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'in_progress',
							render: () => 0,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'passed',
							render: () => 0,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'test_fix',
							render: () => 0,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: () => 0,
							value: i18n.translate('total'),
						},
						{
							clickable: true,
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
					navigateTo: ({id}) => `build/${id}`,
				}}
				transformData={(data) => data?.builds}
				variables={{
					filter: searchUtil.eq('routineId', routineId as string),
				}}
			>
				{() => (
					<div className="graph-container graph-container-sm">
						<ClayChart
							axis={{
								x: {
									label: {
										position: 'outer-center',
										text: i18n.translate(
											'builds-ordered-by-date'
										),
									},
								},
								y: {
									label: {
										position: 'outer-middle',
										text: i18n
											.translate('tests')
											.toUpperCase(),
									},
								},
							}}
							bar={{
								width: {
									max: 30,
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
							legend={{
								inset: {
									anchor: 'top-right',
									step: 1,
									x: 10,
									y: -30,
								},
								item: {
									tile: {
										height: 12,
										width: 12,
									},
								},
								position: 'inset',
							}}
							padding={{bottom: 5, top: 30}}
						/>
					</div>
				)}
			</ListView>

			<RoutineBuildModal modal={formModal.modal} />
		</Container>
	);
};

export default Routine;
