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

import {CType, CTypePagination} from '../../graphql/queries';
import {
	TestrayProject,
	getProject,
	getProjects,
} from '../../graphql/queries/testrayProject';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';

const ProjectOutlet = () => {
	const {projectId, ...otherParams} = useParams();
	const {pathname} = useLocation();
	const {setActions, setDropdown, setHeading, setTabs} = useHeader();

	const {data} = useQuery<CType<'project', TestrayProject>>(getProject, {
		variables: {projectId},
	});

	const {data: dataTestrayProjects} = useQuery<
		CTypePagination<'projects', TestrayProject>
	>(getProjects, {
		variables: {
			pageSize: 100,
		},
	});

	const testrayProjects = dataTestrayProjects?.c?.projects?.items;

	const hasOtherParams = !!Object.values(otherParams).length;
	const testrayProject = data?.c.project;

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
		setActions([
			{
				items: [
					{
						label: i18n.translate('edit-project'),
					},
					{
						label: i18n.translate('delete-project'),
					},
				],
				title: i18n.translate('project'),
			},
			{
				items: [
					{
						label: i18n.translate('manage-components'),
					},
					{
						label: i18n.translate('manage-teams'),
					},
					{
						label: i18n.translate('manage-product-version'),
					},
				],
				title: i18n.translate('manage'),
			},
			{
				items: [
					{
						label: i18n.translate('export-cases'),
					},
				],
				title: i18n.translate('reports'),
			},
		]);
	}, [setActions]);

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
			setHeading([
				{
					category: i18n.translate('project').toUpperCase(),
					path: `/project/${testrayProject.id}/routines`,
					title: testrayProject.name,
				},
			]);
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

	if (testrayProject) {
		return <Outlet context={{testrayProject}} />;
	}

	return null;
};

export default ProjectOutlet;
