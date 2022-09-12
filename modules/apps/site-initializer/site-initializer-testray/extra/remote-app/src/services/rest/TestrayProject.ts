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
import Rest from './Rest';
import {testrayBuildImpl} from './TestrayBuild';
import {TestrayProject} from './types';

type Project = typeof yupSchema.project.__outputType;

class TestrayProjectImpl extends Rest<Project, TestrayProject> {
	constructor() {
		super({
			uri: 'projects',
		});
	}

	protected async beforeRemove(id: number) {
		const hasBuildsInProjectId = await testrayBuildImpl.hasBuildsInProjectId(
			id
		);

		if (hasBuildsInProjectId) {
			throw new Error(
				i18n.translate(
					'the-project-cannot-be-deleted-because-it-has-associated-builds'
				)
			);
		}
	}
}
const testrayProjectImpl = new TestrayProjectImpl();

export {testrayProjectImpl};
