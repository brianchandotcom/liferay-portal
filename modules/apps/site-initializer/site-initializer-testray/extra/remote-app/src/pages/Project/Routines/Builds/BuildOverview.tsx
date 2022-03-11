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
import {useRef} from 'react';

import Container from '../../../../components/Layout/Container';
import QATable from '../../../../components/Table/QATable';
import useTotalTestCases from '../../../../data/useTotalTestCases';
import {TestrayBuild} from '../../../../graphql/queries';
import i18n from '../../../../i18n';
import {getDonutLegend} from '../../../../util/graph';

type BuildOverviewProps = {
	testrayBuild: TestrayBuild;
};

const BuildOverview: React.FC<BuildOverviewProps> = ({testrayBuild}) => {
	const ref = useRef<any>();

	const totalTestCases = useTotalTestCases();

	return (
		<>
			<Container title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('product-version'),
							value: testrayBuild.testrayProductVersion?.name,
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
							value: testrayBuild.dateCreated,
						},
						{
							title: i18n.translate('created-by'),
							value: 'John Doe',
						},
						{title: i18n.translate('all-issues-found'), value: '-'},
					]}
				/>

				<div className="d-flex mt-4">
					<dl>
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="small-heading">
							{i18n.translate('total-estimated-time')}
						</dd>
					</dl>

					<dl className="ml-3">
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="small-heading">
							{i18n.translate('total-estimated-time')}
						</dd>
					</dl>

					<dl className="ml-3">
						<dd>{i18n.sub('x-minutes', '0')}</dd>

						<dd className="small-heading">
							{i18n.sub('time-x-total-issues', '0')}
						</dd>
					</dl>
				</div>
			</Container>

			<Container className="mt-4" title="Total Test Cases">
				<div className="row">
					<div className="col-2">
						<ClayChart
							data={{
								colors: totalTestCases.colors,
								columns: totalTestCases.donut.columns,
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
								title: totalTestCases.donut.total.toString(),
								width: 15,
							}}
							legend={{show: false}}
							onafterinit={() => {
								getDonutLegend(ref.current, {
									data: totalTestCases.donut.columns.map(
										([name]) => name
									),
									elementId: 'testrayTotalMetricsGraphLegend',
									total: totalTestCases.donut.total as number,
								});
							}}
							ref={ref}
							size={{
								height: 200,
							}}
						/>
					</div>

					<div className="col-2">
						<div id="testrayTotalMetricsGraphLegend" />
					</div>

					<div className="col-8">
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
								colors: totalTestCases.colors,
								columns: totalTestCases.barChart.columns,
								groups: [totalTestCases.statuses],
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
