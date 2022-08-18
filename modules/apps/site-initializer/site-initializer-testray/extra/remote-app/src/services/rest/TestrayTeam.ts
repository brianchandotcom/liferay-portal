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
import {searchUtil} from '../../util/search';
import Rest from './Rest';
import {TestrayTeam} from './types';

type Team = typeof yupSchema.team.__outputType;

class TestrayTeamRest extends Rest<Team, TestrayTeam> {
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

	protected async beforeCreate(team: Team) {
		const response = await this.fetcher(
			`/teams?filter=${searchUtil.eq('name', team.name)}`
		);

		if (response?.items.length) {
			throw new Error(i18n.translate('the-team-name-already-exists'));
		}
	}
}

export const testrayTeamRest = new TestrayTeamRest();
