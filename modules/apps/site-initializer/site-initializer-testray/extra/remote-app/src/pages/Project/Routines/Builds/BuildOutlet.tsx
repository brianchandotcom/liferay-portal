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

import {
	TestrayBuild,
	TypePagination,
	getBuild,
} from '../../../../graphql/queries';
import {TestrayTask, getTasks} from '../../../../graphql/queries/testrayTask';
import useHeader from '../../../../hooks/useHeader';
import i18n from '../../../../i18n';
import BuildAlertBar from './BuildAlertBar';
import BuildOverview from './BuildOverview';

type BuildOutletProps = {
	ignorePath: string;
};

const BuildOutlet: React.FC<BuildOutletProps> = ({ignorePath}) => {
	const {pathname} = useLocation();
	const {buildId, projectId, routineId} = useParams();
	const {testrayProject, testrayRoutine}: any = useOutletContext();
	const {data} = useQuery<{build: TestrayBuild}>(getBuild, {
		variables: {
			buildId,
		},
	});

	const {data: testrayTasksData} = useQuery<
		TypePagination<'tasks', TestrayTask>
	>(getTasks);

	const testrayBuild = data?.build;
	const testrayTasks = testrayTasksData?.tasks.items || [];
	const testrayTask = testrayTasks.find(
		(testrayTask) => testrayTask?.build?.id === Number(buildId)
	);

	const isCurrentPathIgnored = pathname.includes(ignorePath);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}`;

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (testrayBuild) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/routines`,
						title: testrayProject.name,
					},
					{
						category: i18n.translate('routine').toUpperCase(),
						path: `/project/${testrayProject.id}/routines/${testrayRoutine.id}`,
						title: testrayRoutine.name,
					},
					{
						category: i18n.translate('build').toUpperCase(),
						path: basePath,
						title: testrayBuild.name,
					},
				]);
			});
		}
	}, [basePath, setHeading, testrayBuild, testrayProject, testrayRoutine]);

	useEffect(() => {
		if (!isCurrentPathIgnored) {
			setTimeout(() => {
				setTabs([
					{
						active: pathname === basePath,
						path: basePath,
						title: i18n.translate('results'),
					},
					{
						active: pathname === `${basePath}/runs`,
						path: `${basePath}/runs`,
						title: i18n.translate('runs'),
					},
					{
						active: pathname === `${basePath}/teams`,
						path: `${basePath}/teams`,
						title: i18n.translate('teams'),
					},
					{
						active: pathname === `${basePath}/components`,
						path: `${basePath}/components`,
						title: i18n.translate('components'),
					},
					{
						active: pathname === `${basePath}/case-types`,
						path: `${basePath}/case-types`,
						title: i18n.translate('case-types'),
					},
				]);
			}, 5);
		}
	}, [basePath, isCurrentPathIgnored, pathname, setTabs]);

	if (testrayBuild) {
		return (
			<>
				{!isCurrentPathIgnored && (
					<>
						{testrayTask && (
							<BuildAlertBar testrayTask={testrayTask} />
						)}

						<BuildOverview
							testrayBuild={testrayBuild}
							testrayTask={testrayTask}
						/>
					</>
				)}

				<Outlet />
			</>
		);
	}

	return null;
};

export default BuildOutlet;
