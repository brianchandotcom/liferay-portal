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
import {APIResponse, TestrayRoutine} from './types';

type Routine = typeof yupSchema.routine.__outputType & {projectId: number};

const adapter = (routine: Routine) => ({
	autoanalyze: routine.autoanalyze,
	name: routine.name,
	r_routineToProjects_c_projectId: routine.projectId,
});

const createRoutine = (routine: Routine & {projectId: number}) =>
	fetcher.post('/routines', adapter(routine));

const updateRoutine = (id: number, routine: Routine) =>
	fetcher.put(`/routines/${id}`, adapter(routine));

const getRoutinesTransformData = (response: APIResponse<TestrayRoutine>) => ({
	...response,
	items: response?.items,
});

export {createRoutine, updateRoutine, getRoutinesTransformData};
