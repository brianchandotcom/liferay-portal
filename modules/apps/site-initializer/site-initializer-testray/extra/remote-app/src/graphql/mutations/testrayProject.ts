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

import {gql} from '@apollo/client';

import {testrayProjectFragment} from '../fragments';

export const CreateProject = gql`
	${testrayProjectFragment}

	mutation CreateProject($data: InputC_Project!) {
		c {
			createProject(Project: $data) {
				...ProjectFragment
			}
		}
	}
`;

export const DeleteProject = gql`
	mutation deleteProject($id: Long) {
		c {
			deleteProject(projectId: $id)
		}
	}
`;

export const UpdateProject = gql`
	${testrayProjectFragment}

	mutation updateProject($id: Long!, $data: InputC_Project!) {
		c {
			updateProject(projectId: $id, Project: $data) {
				...ProjectFragment
			}
		}
	}
`;
