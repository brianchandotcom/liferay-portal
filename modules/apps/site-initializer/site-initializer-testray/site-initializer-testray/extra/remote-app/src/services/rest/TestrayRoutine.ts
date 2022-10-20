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

import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {SearchBuilder} from '../../util/search';
import Rest from './Rest';
import {APIResponse, TestrayRoutine} from './types';

type RoutineFormType = typeof yupSchema.routine.__outputType & {
	projectId: number;
};

class TestrayRoutineImpl extends Rest<RoutineFormType, TestrayRoutine> {
	constructor() {
		super({
			adapter: (routine) => ({
				autoanalyze: routine.autoanalyze,
				id: routine.id,
				name: routine.name,
				r_routineToProjects_c_projectId: routine.projectId,
			}),
			uri: 'routines',
		});
	}

	protected async validate(routine: RoutineFormType, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder
			.eq('name', routine.name)
			.and()
			.eq('projectId', routine.projectId)
			.build();

		const response = await this.fetcher<APIResponse<TestrayRoutine>>(
			`/routines?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'routine'));
		}
	}

	protected async beforeCreate(routine: RoutineFormType): Promise<void> {
		await this.validate(routine);
	}

	protected async beforeUpdate(
		id: number,
		routine: RoutineFormType
	): Promise<void> {
		await this.validate(routine, id);
	}
}

const testrayRoutineImpl = new TestrayRoutineImpl();

export {testrayRoutineImpl};
