/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import {useRef} from 'react';
import Form from '~/components/Form';
import {useCaseResultsChart} from '~/hooks/useCaseResultsChart';

import JiraLink from '../../../../components/JiraLink';
import Container from '../../../../components/Layout/Container';
import QATable from '../../../../components/Table/QATable';
import {useTotalTestCases} from '../../../../hooks/data/useCaseResultGroupBy';
import useIssuesFound from '../../../../hooks/data/useIssuesFound';
import i18n from '../../../../i18n';
import {TestrayBuild, TestrayTask} from '../../../../services/rest';
import dayjs from '../../../../util/date';
import {getDonutLegend} from '../../../../util/graph';
import BuildAlertBar from './BuildAlertBar';

type BuildOverviewProps = {
	testrayBuild: TestrayBuild;
	testrayTask?: TestrayTask;
};

const BuildOverview: React.FC<BuildOverviewProps> = ({testrayBuild}) => {
	const totalTestCasesGroup = useTotalTestCases(testrayBuild);

	const {chart, chartSelectData, setEntity} = useCaseResultsChart({
		buildId: testrayBuild.id,
	});

	const issues = useIssuesFound({buildId: testrayBuild.id});

	const ref = useRef<any>();

	const [testrayTask] = testrayBuild?.tasks as TestrayTask[];

	return (
		<>
			<BuildAlertBar testrayTask={testrayTask} />

			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('product-version'),
							value: testrayBuild.productVersion?.name,
						},
						{
							title: i18n.translate('description'),
							value: testrayBuild.description,
						},
						{
							title: i18n.translate('git-hash'),
							value: testrayBuild.gitHash,
						},
						{
							title: i18n.translate('create-date'),
							value: dayjs(testrayBuild.dateCreated).format(
								'lll'
							),
						},
						{
							title: i18n.translate('created-by'),
							value: testrayBuild.creator.name,
						},
						{
							title: i18n.translate('all-issues-found'),
							value: issues.length ? (
								<JiraLink issue={issues} />
							) : (
								'-'
							),
						},
					]}
				/>

				<div className="d-flex mt-4">
					<dl>
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="tr-small-heading">
							{i18n.translate('total-estimated-time')}
						</dd>
					</dl>

					<dl className="ml-3">
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="tr-small-heading">
							{i18n.translate('total-estimated-time')}
						</dd>
					</dl>

					<dl className="ml-3">
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="tr-small-heading">
							{i18n.sub('time-x-total-issues', '0')}
						</dd>
					</dl>
				</div>
			</Container>

			<Container
				className="mt-4"
				collapsable
				title={i18n.translate('total-test-cases')}
			>
				<div className="d-flex justify-content-between row">
					<div className="align-items-center col-4 d-flex">
						{totalTestCasesGroup.ready && (
							<div className="col-8">
								<ClayChart
									data={{
										colors: totalTestCasesGroup.colors,
										columns:
											totalTestCasesGroup.donut.columns,
										type: 'donut',
									}}
									donut={{
										expand: false,
										label: {
											show: false,
										},
										legend: {
											show: false,
										},
										title: totalTestCasesGroup.donut.total.toString(),
										width: 15,
									}}
									legend={{show: false}}
									onafterinit={() => {
										getDonutLegend(ref.current, {
											data: totalTestCasesGroup.donut.columns.map(
												([name]) => name
											),
											elementId:
												'testrayTotalMetricsGraphLegend',
											total: totalTestCasesGroup.donut
												.total as number,
										});
									}}
									ref={ref}
									size={{
										height: 200,
									}}
								/>
							</div>
						)}

						<div className="col-">
							<div id="testrayTotalMetricsGraphLegend" />
						</div>
					</div>

					<div className="col-8">
						<Form.Select
							className="col-2 ml-6"
							defaultOption={false}
							name="priority"
							onChange={({target: {value}}) => setEntity(value)}
							options={chartSelectData}
						/>

						<ClayChart
							axis={{
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
								colors: chart.colors,
								columns: chart.columns,
								groups: [chart.statuses],
								type: 'bar',
							}}
							legend={{
								inset: {
									anchor: 'top-right',
									step: 1,
									x: 10,
									y: -20,
								},
								position: 'inset',
							}}
							padding={{
								bottom: 5,
								top: 20,
							}}
						/>
					</div>
				</div>
			</Container>
		</>
	);
};

export default BuildOverview;
