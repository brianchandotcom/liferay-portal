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
import {APIResponse, TestrayCase} from './types';

type Case = typeof yupSchema.case.__outputType & {projectId: number};

class TestrayCaseRest extends Rest<Case, TestrayCase> {
	constructor() {
		super({
			adapter: ({
				caseTypeId: r_caseTypeToCases_c_caseTypeId,
				componentId: r_componentToCases_c_componentId,
				description,
				descriptionType,
				estimatedDuration,
				name,
				priority,
				projectId: r_projectToCases_c_projectId,
				steps,
				stepsType,
			}) => ({
				description,
				descriptionType,
				estimatedDuration,
				name,
				priority,
				r_caseTypeToCases_c_caseTypeId,
				r_componentToCases_c_componentId,
				r_projectToCases_c_projectId,
				steps,
				stepsType,
			}),
			nestedFields:
				'build.project,build.routine,caseType,component.team&nestedFieldsDepth=3',
			transformData: (testrayCase) => ({
				...testrayCase,
				caseType: testrayCase?.r_caseTypeToCases_c_caseType,
				component: testrayCase?.r_componentToCases_c_component
					? {
							...testrayCase?.r_componentToCases_c_component,
							team:
								testrayCase?.r_componentToCases_c_component
									?.r_teamToComponents_c_team,
					  }
					: undefined,
			}),
			uri: 'cases',
		});
	}

	protected async validate(Case: Case, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filters = searchBuilder
			.eq('name', Case.name)
			.and()
			.eq('projectId', Case.projectId)
			.build();

		const response = await this.fetcher<APIResponse<TestrayCase>>(
			`/cases?filter=${filters}`
		);

		if (response?.totalCount) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'case'));
		}
	}

	protected async beforeCreate(Case: Case): Promise<void> {
		await this.validate(Case);
	}

	protected async beforeUpdate(id: number, Case: Case): Promise<void> {
		await this.validate(Case, id);
	}
}

export const testrayCaseRest = new TestrayCaseRest();
