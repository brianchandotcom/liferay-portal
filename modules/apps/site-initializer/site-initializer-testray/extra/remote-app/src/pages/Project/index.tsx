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
import ListView from '../../components/ListView/ListView';
import {getProjects} from '../../graphql/queries';
import {useAccountContext, useHeader} from '../../hooks';
import i18n from '../../i18n';
import {SecurityPermissions} from '../../types';
import ProjectModal from './ProjectModal';
import useProjectActions from './useProjectActions';

type ProjectsProps = {
	PageContainer?: React.FC;
	addHeading?: boolean;
};

const Projects: React.FC<ProjectsProps & SecurityPermissions> = ({
	addHeading = true,
	PageContainer = Container,
	security,
	permissions,
}) => {
	const {actions, formModal} = useProjectActions(security, permissions);

	const {setHeading} = useHeader({shouldUpdate: false});

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
		<>
			<PageContainer title={i18n.translate('projects')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{
						addButton: permissions.CREATE
							? () => formModal.modal.open()
							: undefined,
					}}
					query={getProjects}
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'name',
								value: i18n.translate('project'),
							},
							{
								key: 'description',
								value: i18n.translate('description'),
							},
						],
						navigateTo: (project) =>
							`/project/${project.id}/routines`,
					}}
					transformData={(data) => data?.c?.projects}
					viewPermission={permissions.INDEX}
				/>
			</PageContainer>

			<ProjectModal modal={formModal.modal} />
		</>
	);
};

const ProjectPermissions: React.FC<ProjectsProps> = (props) => {
	const {security} = useAccountContext();

	const permissions = security.permissions('TestrayProject', [
		'INDEX',
		'CREATE',
		'UPDATE',
		'DELETE',
	]);

	if (permissions) {
		return (
			<Projects
				{...props}
				permissions={permissions}
				security={security}
			/>
		);
	}

	return null;
};

export default ProjectPermissions;
