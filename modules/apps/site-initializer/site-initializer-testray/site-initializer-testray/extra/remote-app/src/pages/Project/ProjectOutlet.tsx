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

import {useCallback, useEffect} from 'react';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import PageRenderer from '~/components/PageRenderer';

import {useFetch} from '../../hooks/useFetch';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import {APIResponse, TestrayProject} from '../../services/rest';
import useProjectActions from './useProjectActions';

const ProjectOutlet = () => {
	const {projectId, ...otherParams} = useParams();
	const shouldUpdate = !Object.keys(otherParams).length;
	const {pathname} = useLocation();
	const {setDropdown, setHeaderActions, setHeading, setTabs} = useHeader({
		shouldUpdate,
	});

	const {actions} = useProjectActions({isHeaderActions: true});

	const {data: testrayProject, error, loading, mutate} = useFetch<
		TestrayProject
	>(`/projects/${projectId}`);

	const {data: dataTestrayProjects} = useFetch<APIResponse<TestrayProject>>(
		'/projects',
		{
			params: {
				fields: 'id,name',
				pageSize: 100,
			},
		}
	);

	const testrayProjects = dataTestrayProjects?.items;

	const hasOtherParams = !!Object.values(otherParams).length;

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
		if (shouldUpdate) {
			setHeaderActions({actions, item: testrayProject, mutate});
		}
	}, [actions, mutate, shouldUpdate, setHeaderActions, testrayProject]);

	useEffect(() => {
		if (testrayProjects) {
			setDropdown([
				{
					items: [
						{
							divider: true,
							label: i18n.translate('project-directory'),
							path: '/',
						},
						...testrayProjects.map((testrayProject) => ({
							label: testrayProject.name,
							path: `/project/${testrayProject.id}/routines`,
						})),
					],
				},
			]);
		}
	}, [setDropdown, testrayProjects]);

	useEffect(() => {
		if (testrayProject) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/routines`,
						title: testrayProject.name,
					},
				]);
			}, 0);
		}
	}, [setHeading, testrayProject, hasOtherParams]);

	useEffect(() => {
		if (!hasOtherParams) {
			setTimeout(() => {
				setTabs([
					{
						...getPath('overview'),
						title: i18n.translate('overview'),
					},
					{
						...getPath('routines'),
						title: i18n.translate('routines'),
					},
					{
						...getPath('suites'),
						title: i18n.translate('suites'),
					},
					{
						...getPath('cases'),
						title: i18n.translate('cases'),
					},
					{
						...getPath('requirements'),
						title: i18n.translate('requirements'),
					},
				]);
			}, 0);
		}
	}, [getPath, setTabs, hasOtherParams]);

	return (
		<PageRenderer error={error} loading={loading}>
			<Outlet
				context={{
					actions: testrayProject?.actions,
					mutateTestrayProject: mutate,
					testrayProject,
				}}
			/>
		</PageRenderer>
	);
};

export default ProjectOutlet;
