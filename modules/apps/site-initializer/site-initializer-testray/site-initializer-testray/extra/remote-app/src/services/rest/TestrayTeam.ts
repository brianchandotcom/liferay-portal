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
import {testrayComponentImpl} from './TestrayComponent';
import {APIResponse, TestrayTeam} from './types';

type Team = typeof yupSchema.team.__outputType;

class TestrayTeamImpl extends Rest<Team, TestrayTeam> {
	constructor() {
		super({
			adapter: ({name, projectId: r_projectToTeams_c_projectId}) => ({
				name,
				r_projectToTeams_c_projectId,
			}),
			nestedFields: 'project',
			transformData: (team) => ({
				...team,
				project: team?.r_projectToTeams_c_project,
			}),
			uri: 'teams',
		});
	}

	protected async validate(team: Team, id?: number): Promise<void> {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder
			.eq('name', team.name)
			.and()
			.eq('projectId', team.projectId as string)
			.build();

		const response = await this.fetcher<APIResponse<TestrayTeam>>(
			`/teams?filter=${filter}`
		);

		if (response?.items?.length) {
			throw new Error(i18n.sub('the-x-name-already-exists', 'team'));
		}
	}

	protected async beforeCreate(team: Team) {
		await this.validate(team);
	}

	protected async beforeUpdate(id: number, team: Team): Promise<void> {
		await this.validate(team, id);
	}

	protected async beforeRemove(id: number): Promise<void> {
		const response = await testrayComponentImpl.getComponentsByTeamId(id);

		if (response?.totalCount) {
			throw new Error(
				i18n.translate(
					'the-team-cannot-be-deleted-because-it-has-associated-components'
				)
			);
		}
	}
}

export const testrayTeamImpl = new TestrayTeamImpl();
