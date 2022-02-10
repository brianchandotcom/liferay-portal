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
import {useCallback, useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';

import {
	TestrayProjectQuery,
	getTestrayProject,
} from '../../graphql/queries/testrayProject';
import useHeader from '../../hooks/useHeader';

const ProjectOutlet = () => {
	const {projectId, ...otherParams} = useParams();
	const {pathname} = useLocation();
	const {setHeading, setTabs} = useHeader();

	const {data} = useQuery<TestrayProjectQuery>(getTestrayProject, {
		variables: {testrayProjectId: projectId},
	});

	const hasOtherParams = !!Object.values(otherParams).length;
	const testrayProject = data?.c.testrayProject;

	const getPath = useCallback(
		(path: string) => {
			const relativePath = `/project/${projectId}/${path}`;

			return {
				active: relativePath === pathname,
				path: relativePath,
			};
		},
		[projectId, pathname]
	);

	useEffect(() => {
		if (testrayProject && !hasOtherParams) {
			setHeading([{category: 'PROJECT', title: testrayProject.name}]);
		}
	}, [setHeading, testrayProject, hasOtherParams]);

	useEffect(() => {
		if (!hasOtherParams) {
			setTimeout(() => {
				setTabs([
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
				]);
			}, 0);
		}
	}, [getPath, setTabs, hasOtherParams]);

	if (testrayProject) {
		return <Outlet context={{testrayProject}} />;
	}

	return null;
};

export default ProjectOutlet;
