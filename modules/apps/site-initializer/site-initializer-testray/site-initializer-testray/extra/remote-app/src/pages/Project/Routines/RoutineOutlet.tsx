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
