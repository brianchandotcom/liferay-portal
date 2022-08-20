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

import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';

import {useFetch} from '../../../../hooks/useFetch';
import useHeader from '../../../../hooks/useHeader';
import i18n from '../../../../i18n';
import {
	getTasksTransformData,
	tasksResource,
	testrayBuildRest,
} from '../../../../services/rest';
import BuildAlertBar from './BuildAlertBar';
import BuildOverview from './BuildOverview';
import useBuildActions from './useBuildActions';

type BuildOutletProps = {
	ignorePaths: string[];
};

const BuildOutlet: React.FC<BuildOutletProps> = ({ignorePaths}) => {
	const {actions} = useBuildActions({isHeaderActions: true});
	const {buildId, projectId, routineId} = useParams();
	const {pathname} = useLocation();
	const {setHeaderActions, setHeading, setTabs} = useHeader({timeout: 200});
	const {testrayProject, testrayRoutine}: any = useOutletContext();

	const {
		data: testrayBuild,
		mutate: mutateBuild,
	} = useFetch(testrayBuildRest.getResource(buildId as string), (response) =>
		testrayBuildRest.transformData(response)
	);

	const {data: testrayTasksData} = useFetch(
		tasksResource,
		getTasksTransformData
	);

	const testrayTasks = testrayTasksData?.items || [];

	const testrayTask = testrayTasks.find(
		(testrayTask) => testrayTask?.build?.id === Number(buildId)
	);

	const isCurrentPathIgnored = ignorePaths.some((ignorePath) =>
		pathname.includes(ignorePath)
	);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}`;

	const buildName = testrayBuild?.name;

	useEffect(() => {
		setHeaderActions({actions, item: testrayBuild, mutate: mutateBuild});
	}, [actions, mutateBuild, setHeaderActions, testrayBuild]);

	useEffect(() => {
		if (buildName) {
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
					title: buildName,
				},
			]);
		}
	}, [basePath, setHeading, buildName, testrayProject, testrayRoutine]);

	useEffect(() => {
		if (!isCurrentPathIgnored) {
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

				<Outlet context={{mutateBuild, testrayBuild}} />
			</>
		);
	}

	return null;
};

export default BuildOutlet;
