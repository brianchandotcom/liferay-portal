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
	getTestrayBuild,
} from '../../../../graphql/queries';
import {
	TestrayTask,
	getTestrayTasks,
} from '../../../../graphql/queries/testrayTask';
import useHeader from '../../../../hooks/useHeader';
import i18n from '../../../../i18n';
import BuildAlertBar from './BuildAlertBar';
import BuildOverview from './BuildOverview';

type BuildOutletProps = {
	ignorePath: string;
};

const BuildOutlet: React.FC<BuildOutletProps> = ({ignorePath}) => {
	const {pathname} = useLocation();
	const {projectId, routineId, testrayBuildId} = useParams();
	const {testrayProject, testrayRoutine}: any = useOutletContext();
	const {data} = useQuery<{testrayBuild: TestrayBuild}>(getTestrayBuild, {
		variables: {
			testrayBuildId,
		},
	});

	const {data: testrayTasksData} = useQuery<
		TypePagination<'testrayTasks', TestrayTask>
	>(getTestrayTasks);

	const testrayBuild = data?.testrayBuild;
	const testrayTasks = testrayTasksData?.testrayTasks.items || [];
	const testrayTask = testrayTasks.find(
		(testrayTask) =>
			testrayTask?.testrayBuild?.id === Number(testrayBuildId)
	);

	const isCurrentPathIgnored = pathname.includes(ignorePath);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${testrayBuildId}`;

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (testrayBuild) {
			setTimeout(() => {
				setHeading(
					[
						{
							category: 'BUILD',
							path: basePath,
							title: testrayBuild.name,
						},
					],
					true
				);
			}, 0);
		}
	}, [basePath, setHeading, testrayBuild]);

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

	if (testrayProject && testrayRoutine && testrayBuild) {
		return (
			<>
				{!isCurrentPathIgnored && (
					<>
						{testrayTask && (
							<BuildAlertBar testrayTask={testrayTask} />
						)}

						<BuildOverview testrayBuild={testrayBuild} />
					</>
				)}

				<Outlet />
			</>
		);
	}

	return null;
};

export default BuildOutlet;
