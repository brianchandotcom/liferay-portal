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

import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView/ListView';
import {initialState} from '../../context/HeaderContext';
import {getTestrayProjects} from '../../graphql/queries';
import useHeader from '../../hooks/useHeader';
import i18n from '../../i18n';
import ProjectModal from './ProjectModal';
import useProjectActions from './useProjectActions';

const Home = () => {
	const {actions, formModal} = useProjectActions();

	useHeader({useHeading: initialState.heading});

	return (
		<>
			<Container title={i18n.translate('projects')}>
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{
						addButton: formModal.modal.open,
					}}
					query={getTestrayProjects}
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
					transformData={(data) => data?.c?.testrayProjects}
				/>
			</Container>

			<ProjectModal modal={formModal.modal} />
		</>
	);
};

export default Home;
