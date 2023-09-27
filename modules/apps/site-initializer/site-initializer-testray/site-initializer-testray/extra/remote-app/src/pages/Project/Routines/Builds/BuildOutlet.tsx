/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {
	Outlet,
	useLocation,
	useOutletContext,
	useParams,
} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';

import {useFetch} from '../../../../hooks/useFetch';
import useHeader from '../../../../hooks/useHeader';
import i18n from '../../../../i18n';
import {
	TestrayBuild,
	TestrayProject,
	TestrayRoutine,
	testrayBuildImpl,
} from '../../../../services/rest';
import BuildOverview from './BuildOverview';
import useBuildActions from './useBuildActions';

type BuildOutletProps = {
	ignorePaths: string[];
};

type OutletContext = {
	testrayProject: TestrayProject;
	testrayRoutine: TestrayRoutine;
};

const BuildOutlet: React.FC<BuildOutletProps> = ({ignorePaths}) => {
	const {actions} = useBuildActions({isHeaderActions: true});
	const {buildId, projectId, routineId, ...otherParams} = useParams();
	const {pathname} = useLocation();
	const {testrayProject, testrayRoutine}: OutletContext = useOutletContext();

	const {data: testrayBuild, error, loading, mutate: mutateBuild} = useFetch<
		TestrayBuild
	>(testrayBuildImpl.getResource(buildId as string), {
		transformData: (response) => testrayBuildImpl.transformData(response),
	});

	const hasOtherParams = !!Object.values(otherParams).length;

	const {setHeaderActions, setHeading, setTabs} = useHeader({
		shouldUpdate: !hasOtherParams,
	});

	const isCurrentPathIgnored = ignorePaths.some((ignorePath) =>
		pathname.includes(ignorePath)
	);

	const basePath = `/project/${projectId}/routines/${routineId}/build/${buildId}`;

	useEffect(() => {
		setHeaderActions({actions, item: testrayBuild, mutate: mutateBuild});
	}, [actions, mutateBuild, setHeaderActions, testrayBuild]);

	useEffect(() => {
		if (testrayBuild?.name) {
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/routines`,
					title: testrayProject?.name,
				},
				{
					category: i18n.translate('routine').toUpperCase(),
					path: `/project/${testrayProject.id}/routines/${testrayRoutine.id}`,
					title: testrayRoutine?.name,
				},
				{
					category: i18n.translate('build').toUpperCase(),
					path: `/project/${testrayProject.id}/routines/${testrayRoutine.id}/build/${testrayBuild.id}`,
					title: testrayBuild?.name,
				},
			]);
		}
	}, [pathname, setHeading, testrayBuild, testrayProject, testrayRoutine]);

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

	return (
		<PageRenderer error={error} loading={loading}>
			<>
				{!isCurrentPathIgnored && testrayBuild && (
					<BuildOverview testrayBuild={testrayBuild} />
				)}

				<Outlet
					context={{
						actions: testrayBuild?.actions,
						mutateBuild,
						testrayBuild,
						testrayProject,
						testrayRoutine,
					}}
				/>
			</>
		</PageRenderer>
	);
};

export default BuildOutlet;
