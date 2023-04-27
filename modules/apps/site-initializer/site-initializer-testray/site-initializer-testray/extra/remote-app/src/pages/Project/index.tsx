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

import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import {useHeader} from '~/hooks';
import i18n from '~/i18n';

import useProjectActions from './useProjectActions';

type ProjectsProps = {
	PageContainer?: React.FC;
};

const Projects: React.FC<ProjectsProps> = ({PageContainer = Container}) => {
	const {actions, navigate} = useProjectActions();

	useHeader({
		dropdown: [],
		headerActions: {actions: []},
		heading: [
			{
				category: i18n.translate('project'),
				title: i18n.translate('project-directory'),
			},
		],
		icon: 'polls',
	});

	return (
		<PageContainer>
			<ListView
				managementToolbarProps={{
					addButton: () => navigate('/project/create'),
					display: {columns: false},
					title: i18n.translate('projects'),
				}}
				resource="/projects?fields=actions,description,id,name"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							sorteable: true,
							value: i18n.translate('project'),
						},
						{
							key: 'description',
							size: 'lg',
							value: i18n.translate('description'),
						},
					],
					navigateTo: (project) => `/project/${project.id}/routines`,
				}}
			/>
		</PageContainer>
	);
};

export default Projects;
