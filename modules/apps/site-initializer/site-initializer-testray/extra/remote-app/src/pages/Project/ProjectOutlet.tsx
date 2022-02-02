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
import {useCallback, useContext, useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';

import {
	HeaderContext,
	HeaderTypes,
	initialState,
} from '../../context/HeaderContext';
import {getTestrayProject} from '../../graphql/queries';

type TestrayProject = {
	c: {
		testrayProject: {
			description: string;
			name: string;
		};
	};
};

const ProjectOutlet = () => {
	const [, dispatch] = useContext(HeaderContext);
	const {projectId} = useParams();

	const {data} = useQuery<TestrayProject>(getTestrayProject, {
		variables: {testrayProjectId: projectId},
	});

	const testrayProject = data?.c.testrayProject;

	const {pathname} = useLocation();

	const currentPath = pathname
		.split('/')
		.filter(Boolean)
		.slice(0, -1)
		.join('/');

	const getPath = useCallback(
		(path: string) => {
			const relativePath = `/${currentPath}/${path}`;

			return {
				active: relativePath === pathname,
				path: relativePath,
			};
		},
		[currentPath, pathname]
	);

	useEffect(() => {
		dispatch({
			payload: [
				{
					...getPath('overview'),
					title: 'Overview',
				},
				{
					...getPath('routines'),
					title: 'Routines',
				},
				{
					...getPath('suites'),
					title: 'Suites',
				},
				{
					...getPath('cases'),
					title: 'Cases',
				},
				{
					...getPath('requirements'),
					title: 'Requirements',
				},
			],
			type: HeaderTypes.SET_TABS,
		});

		return () => dispatch({payload: [], type: HeaderTypes.SET_TABS});
	}, [pathname, dispatch, getPath]);

	useEffect(() => {
		if (testrayProject) {
			dispatch({
				payload: {category: 'PROJECT', title: testrayProject.name},
				type: HeaderTypes.SET_TITLE,
			});
		}

		return () =>
			dispatch({
				payload: initialState.title,
				type: HeaderTypes.SET_TITLE,
			});
	}, [testrayProject, dispatch]);

	return <Outlet />;
};

export default ProjectOutlet;
