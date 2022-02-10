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
import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';

import BarChartComponent from '../../../../components/Charts/BarChart';
import PieChartComponent from '../../../../components/Charts/PieChart';
import Container from '../../../../components/Layout/Container';
import QATable from '../../../../components/Table/QATable';
import {
	CType,
	TestrayBuild,
	getTestrayBuild,
} from '../../../../graphql/queries';
import useHeader from '../../../../hooks/useHeader';
import {DATA_COLORS} from '../../../../util/constants';
import {runs} from '../../../../util/mock';

type BuildOverviewProps = {
	testrayBuild: TestrayBuild;
};

const BuildOverview: React.FC<BuildOverviewProps> = ({testrayBuild}) => {
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
