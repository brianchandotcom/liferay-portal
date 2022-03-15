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

import {CType, TestrayRoutine, getRoutine} from '../../../graphql/queries';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';

const RoutineOutlet = () => {
	const {pathname} = useLocation();
	const {projectId, routineId} = useParams();
	const {testrayProject}: any = useOutletContext();
	const {data} = useQuery<CType<'routine', TestrayRoutine>>(getRoutine, {
		variables: {
			routineId,
		},
	});

	const testrayRoutine = data?.c?.routine;

	const basePath = `/project/${projectId}/routines/${routineId}`;

	const {setHeading} = useHeader({
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
					title: testrayProject.name,
				},
				{
					category: i18n.translate('routine').toUpperCase(),
					title: testrayRoutine.name,
				},
			]);
		}
	}, [setHeading, testrayProject, testrayRoutine]);

	if (testrayProject && testrayRoutine) {
		return <Outlet context={{testrayProject, testrayRoutine}} />;
	}

	return null;
};

export default RoutineOutlet;
