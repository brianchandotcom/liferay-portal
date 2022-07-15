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

import {useFetch} from '../../../hooks/useFetch';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {TestrayRoutine} from '../../../services/rest';

const RoutineOutlet = () => {
	const {pathname} = useLocation();
	const {projectId, routineId, ...otherParams} = useParams();
	const {testrayProject}: any = useOutletContext();
	const {data: testrayRoutine} = useFetch<TestrayRoutine>(
		`/routines/${routineId}`
	);

	const basePath = `/project/${projectId}/routines/${routineId}`;

	const hasOtherParams = !!Object.values(otherParams).length;

	const {setHeading} = useHeader({
		shouldUpdate: true,
		useTabs: [
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
		],
	});

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
	}, [setHeading, testrayProject, testrayRoutine, hasOtherParams]);

	if (testrayProject && testrayRoutine) {
		return <Outlet context={{testrayProject, testrayRoutine}} />;
	}

	return null;
};

export default RoutineOutlet;
