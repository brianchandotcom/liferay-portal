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

import yupSchema from '../../schema/yup';
import fetcher from '../fetcher';

type TestrayProject = {
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	id: number;
	name: string;
};

type Project = typeof yupSchema.project.__outputType;

const adapter = ({description, id, name}: Project) => ({description, id, name});

const createProject = (project: Project) =>
	fetcher.post('/projects', adapter(project));

const updateProject = (id: number, project: Project) =>
	fetcher.put(`/projects/${id}`, adapter(project));

export type {TestrayProject};

export {createProject, updateProject};
