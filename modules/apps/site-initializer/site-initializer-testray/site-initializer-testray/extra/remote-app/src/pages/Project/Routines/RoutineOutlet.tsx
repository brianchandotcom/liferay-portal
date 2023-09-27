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

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {TestrayProject, TestrayRoutine} from '../../../services/rest';
import useRoutineActions from './useRoutineActions';

const RoutineOutlet = () => {
	const {actions} = useRoutineActions({isHeaderActions: true});
	const {pathname} = useLocation();
	const {projectId, routineId, ...otherParams} = useParams();
	const {
		testrayProject,
	}: {testrayProject: TestrayProject} = useOutletContext();

	const {data: testrayRoutine, error, loading, mutate} = useFetch<
		TestrayRoutine
	>(`/routines/${routineId}`);

	const hasOtherParams = !!Object.values(otherParams).length;

	const {setHeaderActions, setHeading, setTabs} = useHeader({
		shouldUpdate: !hasOtherParams,
		timeout: 100,
	});

	useEffect(() => {
		setHeaderActions({actions, item: testrayRoutine, mutate});
	}, [actions, mutate, setHeaderActions, testrayRoutine, pathname]);

	const basePath = `/project/${projectId}/routines/${routineId}`;

	useEffect(() => {
		setTabs([
			{
				active: pathname === basePath,
				path: basePath,
				title: i18n.translate('current'),
			},
			{
				active: pathname !== basePath,
				path: `${basePath}/archived`,
				title: i18n.translate('archived'),
			},
		]);
	}, [basePath, pathname, setTabs]);

	useEffect(() => {
		if (testrayProject && testrayRoutine) {
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
			]);
		}
	}, [setHeading, testrayProject, testrayRoutine]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayRoutine?.actions,
					mutateTestrayRoutine: mutate,
					testrayProject,
					testrayRoutine,
				}}
			/>
		</PageRenderer>
	);
};

export default RoutineOutlet;
