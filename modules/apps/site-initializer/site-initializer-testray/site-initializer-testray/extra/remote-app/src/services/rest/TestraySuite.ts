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
import {APIResponse, TestraySuite} from './types';

type Suite = typeof yupSchema.suite.__outputType & {projectId: number};

class TestraySuiteRest extends Rest<Suite, TestraySuite> {
	constructor() {
		super({
			adapter: ({
				autoanalyze,
				caseParameters,
				description,
				id,
				name,
				projectId: r_projectToSuites_c_projectId,
				smartSuite,
			}) => ({
				autoanalyze,
				caseParameters,
				description,
				id,
				name,
				r_projectToSuites_c_projectId,
				smartSuite,
			}),
			uri: 'suites',
		});
	}
	protected async validate(suite: Suite, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filters = searchBuilder
			.eq('name', suite.name)
			.and()
			.eq('projectId', suite.projectId)
			.build();

		const response = await this.fetcher<APIResponse<TestraySuite>>(
			`/suites?filter=${filters}`
		);

		if (response?.totalCount) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'suite'));
		}
	}
	protected async beforeCreate(suite: Suite): Promise<void> {
		await this.validate(suite);
	}

	protected async beforeUpdate(id: number, suite: Suite): Promise<void> {
		await this.validate(suite, id);
	}
}

export const testraySuiteRest = new TestraySuiteRest();
