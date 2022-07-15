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

import EmptyState from '../../components/EmptyState';
import {APIResponse} from '../../graphql/queries';
import {useFetch} from '../../hooks/useFetch';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import {TestrayProject} from '../../services/rest';

const ProjectOutlet = () => {
	const {projectId, ...otherParams} = useParams();
	const {pathname} = useLocation();
	const {setActions, setDropdown, setHeading, setTabs} = useHeader();

	const {data: testrayProject, error} = useFetch<TestrayProject>(
		`/projects/${projectId}`
	);

	const {data: dataTestrayProjects} = useFetch<APIResponse<TestrayProject>>(
		'/projects?pageSize=100'
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
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/routines`,
						title: testrayProject.name,
					},
				]);
			}, 10);
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

	if (error) {
		return (
			<EmptyState
				description={error.message}
				title={i18n.translate('error')}
				type="EMPTY_SEARCH"
			/>
		);
	}

	if (testrayProject) {
		return <Outlet context={{testrayProject}} />;
	}

	return null;
};

export default ProjectOutlet;
