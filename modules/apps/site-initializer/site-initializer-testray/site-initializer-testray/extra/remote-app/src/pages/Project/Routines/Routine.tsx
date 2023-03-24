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
import classNames from 'classnames';
import {useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import ProgressBar from '~/components/ProgressBar';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import i18n from '~/i18n';
import {TestrayBuild, testrayBuildImpl} from '~/services/rest';
import {DATA_COLORS, Statuses} from '~/util/constants';
import dayjs from '~/util/date';

import BuildAddButton from './Builds/BuildAddButton';
import useBuildActions from './Builds/useBuildActions';

type BuildChartProps = {
	builds: TestrayBuild[];
};

const BuildChart: React.FC<BuildChartProps> = ({builds}) => (
	<div className="graph-container graph-container-sm">
		<ClayChart
			axis={{
				x: {
					label: {
						position: 'outer-center',
						text: i18n.translate('builds-ordered-by-date'),
					},
				},
				y: {
					label: {
						position: 'outer-middle',
						text: i18n.translate('tests').toUpperCase(),
					},
				},
			}}
			bar={{
				width: {
					max: 30,
				},
			}}
			data={{
				colors: {
					[Statuses.BLOCKED]: DATA_COLORS['metrics.blocked'],
					[Statuses.FAILED]: DATA_COLORS['metrics.failed'],
					[Statuses.INCOMPLETE]: DATA_COLORS['metrics.incomplete'],
					[Statuses.PASSED]: DATA_COLORS['metrics.passed'],
					[Statuses.TEST_FIX]: DATA_COLORS['metrics.testfix'],
				},
				columns: [
					[
						Statuses.PASSED,
						...builds.map(({caseResultPassed = 0}) =>
							Number(caseResultPassed)
						),
					],
					[
						Statuses.FAILED,
						...builds.map(({caseResultFailed = 0}) =>
							Number(caseResultFailed)
						),
					],
					[
						Statuses.BLOCKED,
						...builds.map(({caseResultBlocked = 0}) =>
							Number(caseResultBlocked)
						),
					],
					[
						Statuses.TEST_FIX,
						...builds.map(({caseResultTestFix = 0}) =>
							Number(caseResultTestFix)
						),
					],
					[
						Statuses.INCOMPLETE,
						...builds.map(
							({
								caseResultInProgress = 0,
								caseResultUntested = 0,
							}) =>
								Number(caseResultInProgress) +
								Number(caseResultUntested)
						),
					],
				],
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
);

const Routine = () => {
	const {actions, formModal} = useBuildActions();
	const {routineId} = useParams();

	const searchBuilder = useSearchBuilder({useURIEncode: false});

	const routineFilter = searchBuilder
		.eq('routineId', routineId as string)
		.and()
		.eq('template', false)
		.and()
		.build();

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
					buttons: <BuildAddButton routineId={routineId as string} />,
					filterSchema: 'builds',
					title: i18n.translate('build-history'),
				}}
				resource={testrayBuildImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							key: 'status',
							render: (_, {promoted, tasks}: TestrayBuild) => {
								const [task] = tasks;

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

										{task && (
											<span title={task.dueStatus.name}>
												<ClayIcon
													className={classNames(
														'label-chart symbol',
														{
															[task.dueStatus.key.toLowerCase()]: task
																.dueStatus.key,
														}
													)}
													symbol="circle"
												/>
											</span>
										)}
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
							value: i18n.translate('create-date'),
						},
						{
							clickable: true,
							key: 'gitHash',
							value: i18n.translate('git-hash'),
						},
						{
							clickable: true,
							key: 'product_version',
							render: (_, {productVersion}) =>
								productVersion?.name,
							value: i18n.translate('product-version'),
						},
						{
							clickable: true,
							key: 'name',
							value: i18n.translate('build'),
						},
						{
							clickable: true,
							key: 'caseResultFailed',
							render: (failed = 0) => failed,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'caseResultBlocked',
							render: (blocked = 0) => blocked,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'caseResultUntested',
							render: (untested = 0) => untested,
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'caseResultInProgress',
							render: (inProgress = 0) => inProgress,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'caseResultPassed',
							render: (passed = 0) => passed,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'caseResultTestFix',
							render: (caseResultFailed = 0) => caseResultFailed,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, build: TestrayBuild) =>
								[
									build.caseResultBlocked,
									build.caseResultFailed,
									build.caseResultInProgress,
									build.caseResultIncomplete,
									build.caseResultPassed,
									build.caseResultTestFix,
									build.caseResultUntested,
								]
									.map((count) => (count ? Number(count) : 0))
									.reduce(
										(prevCount, currentCount) =>
											prevCount + currentCount
									),
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, build: TestrayBuild) => (
								<ProgressBar
									items={{
										blocked: Number(
											build.caseResultBlocked
										),
										failed: Number(build.caseResultFailed),
										incomplete: Number(
											build.caseResultIncomplete
										),
										passed: Number(build.caseResultPassed),
										test_fix: Number(
											build.caseResultTestFix
										),
									}}
								/>
							),
							size: 'xl',
							value: i18n.translate('metrics'),
						},
					],
					navigateTo: ({id}) => `build/${id}`,
				}}
				transformData={(response) =>
					testrayBuildImpl.transformDataFromList(response)
				}
				variables={{
					filter: routineFilter,
				}}
			>
				{({items, totalCount}) =>
					totalCount > 0 && <BuildChart builds={items} />
				}
			</ListView>
		</Container>
	);
};

export default Routine;
