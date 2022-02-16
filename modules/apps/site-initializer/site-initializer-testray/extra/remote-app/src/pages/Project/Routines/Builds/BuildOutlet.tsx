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

import {useQuery} from '@apollo/client';
import ClayChart from '@clayui/charts';
import {useEffect, useRef} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';

import Container from '../../../../components/Layout/Container';
import QATable from '../../../../components/Table/QATable';
import {
	CType,
	TestrayBuild,
	getTestrayBuild,
} from '../../../../graphql/queries';
import useHeader from '../../../../hooks/useHeader';
import {DATA_COLORS} from '../../../../util/constants';
import {getDonutLegend} from '../../../../util/graph';
import {TotalTestCases, getRandomMaximumValue} from '../../../../util/mock';

type BuildOverviewProps = {
	testrayBuild: TestrayBuild;
};

const BuildOverview: React.FC<BuildOverviewProps> = ({testrayBuild}) => {
	const ref = useRef<any>();

	const total = TotalTestCases.map(([, totalCase]) => totalCase).reduce(
		(prevValue, currentValue) => Number(prevValue) + Number(currentValue)
	);

	return (
		<>
			<Container title="Details">
				<QATable
					items={[
						{title: 'product version', value: '7.0.x'},
						{
							title: 'description',
							value: testrayBuild.description,
						},
						{
							title: 'git hash',
							value:
								testrayBuild.gitHash ||
								'c33e85e8b067d805a45956c76ad053ca98ffcc8a',
						},
						{title: 'create date', value: testrayBuild.dateCreated},
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
					<div className="col-2">
						<ClayChart
							data={{
								colors: {
									'BLOCKED': DATA_COLORS['metrics.blocked'],
									'FAILED': DATA_COLORS['metrics.failed'],
									'INCOMPLETE':
										DATA_COLORS['metrics.incomplete'],
									'PASSED': DATA_COLORS['metrics.passed'],
									'TEST FIX': DATA_COLORS['metrics.test-fix'],
								},
								columns: TotalTestCases,
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
								title: total.toString(),
								width: 15,
							}}
							legend={{show: false}}
							onafterinit={() => {
								getDonutLegend(ref.current, {
									data: TotalTestCases.map(([name]) => name),
									elementId: 'testrayTotalMetricsGraphLegend',
									total: total as number,
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
										text: 'TESTS',
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
									'BLOCKED': DATA_COLORS['metrics.blocked'],
									'FAILED': DATA_COLORS['metrics.failed'],
									'INCOMPLETE':
										DATA_COLORS['metrics.incomplete'],
									'PASSED': DATA_COLORS['metrics.passed'],
									'TEST FIX': DATA_COLORS['metrics.test-fix'],
								},
								columns: [
									[
										'PASSED',
										...getRandomMaximumValue(20, 1000),
									],
									[
										'FAILED',
										...getRandomMaximumValue(20, 500),
									],
									[
										'BLOCKED',
										...getRandomMaximumValue(20, 100),
									],
									[
										'TEST FIX',
										...getRandomMaximumValue(20, 100),
									],
									[
										'INCOMPLETE',
										...getRandomMaximumValue(20, 100),
									],
								],
								groups: [
									[
										'PASSED',
										'FAILED',
										'BLOCKED',
										'TEST FIX',
										'INCOMPLETE',
									],
								],
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

const BuildOutlet = () => {
	const {pathname} = useLocation();
	const {projectId, routineId, testrayBuildId} = useParams();
	const {testrayProject, testrayRoutine}: any = useOutletContext();
	const {data} = useQuery<CType<'testrayBuild', TestrayBuild>>(
		getTestrayBuild,
		{
			variables: {
				testrayBuildId,
			},
		}
	);

	const testrayBuild = data?.c?.testrayBuild;

	const basePath = `/project/${projectId}/routines/${routineId}/build/${testrayBuildId}`;

	const {setHeading} = useHeader({
		timeout: 5,
		useTabs: [
			{
				active: pathname === basePath,
				path: basePath,
				title: 'Results',
			},
			{
				active: pathname === `${basePath}/runs`,
				path: `${basePath}/runs`,
				title: 'Runs',
			},
			{
				active: pathname === `${basePath}/teams`,
				path: `${basePath}/teams`,
				title: 'Teams',
			},
			{
				active: pathname === `${basePath}/components`,
				path: `${basePath}/components`,
				title: 'Components',
			},
			{
				active: pathname === `${basePath}/case-types`,
				path: `${basePath}/case-types`,
				title: 'Case Types',
			},
		],
	});

	useEffect(() => {
		if (testrayProject && testrayRoutine && testrayBuild) {
			setHeading([
				{category: 'PROJECT', title: testrayProject.name},
				{category: 'ROUTINE', title: testrayRoutine.name},
				{category: 'BUILD', title: testrayBuild.name},
			]);
		}
	}, [setHeading, testrayProject, testrayRoutine, testrayBuild]);

	if (testrayProject && testrayRoutine && testrayBuild) {
		return (
			<>
				<BuildOverview testrayBuild={testrayBuild} />

				<Outlet />
			</>
		);
	}

	return null;
};

export default BuildOutlet;
