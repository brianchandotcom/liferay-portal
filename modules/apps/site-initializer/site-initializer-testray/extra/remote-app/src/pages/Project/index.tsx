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

import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView/ListViewRest';
import {useHeader} from '../../hooks';
import i18n from '../../i18n';
import ProjectModal from './ProjectModal';
import useProjectActions from './useProjectActions';

type ProjectsProps = {
	PageContainer?: React.FC;
	addHeading?: boolean;
};

const Projects: React.FC<ProjectsProps> = ({
	addHeading = true,
	PageContainer = Container,
}) => {
	const {actions, formModal} = useProjectActions();

	const {setDropdown, setDropdownIcon, setHeading} = useHeader({
		shouldUpdate: true,
	});

	useEffect(() => {
		setDropdownIcon('polls');
		setDropdown([]);
	}, [setDropdownIcon, setDropdown]);

	useEffect(() => {
		if (addHeading) {
			setHeading([
				{
					category: i18n.translate('project'),
					title: i18n.translate('project-directory'),
				},
			]);
		}
	}, [addHeading, setHeading]);

	return (
		<PageContainer>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					display: {columns: false},
					title: i18n.translate('projects'),
				}}
				resource="/projects"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							sorteable: true,
							value: i18n.translate('project'),
						},
						{
							key: 'description',
							value: i18n.translate('description'),
						},
					],
					navigateTo: (project) => `/project/${project.id}/routines`,
				}}
			/>

			<ProjectModal modal={formModal.modal} />
		</PageContainer>
	);
};

export default Projects;
