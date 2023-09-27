/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {testrayBuildImpl} from './TestrayBuild';
import {APIResponse, TestrayProject} from './types';

type Project = typeof yupSchema.project.__outputType;

class TestrayProjectImpl extends Rest<Project, TestrayProject> {
	constructor() {
		super({
			uri: 'projects',
		});
	}

	protected async validate(project: Project, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', project.name).and().build();

		const response = await this.fetcher<APIResponse<TestrayProject>>(
			`/projects?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'project')
			);
		}
	}

	protected async beforeCreate(project: Project): Promise<void> {
		await this.validate(project);
	}

	protected async beforeUpdate(id: number, project: Project): Promise<void> {
		await this.validate(project, id);
	}

	protected async beforeRemove(id: number) {
		const hasBuildsInProjectId = await testrayBuildImpl.hasBuildsInProjectId(
			id
		);

		if (hasBuildsInProjectId) {
			throw new TestrayError(
				i18n.translate(
					'the-project-cannot-be-deleted-because-it-has-associated-builds'
				)
			);
		}
	}
}

const testrayProjectImpl = new TestrayProjectImpl();

export {testrayProjectImpl};
