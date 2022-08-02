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
import {APIResponse, TestrayTeam} from './types';

type Team = typeof yupSchema.team.__outputType;

const adapter = ({name, projectId: r_projectToTeams_c_projectId}: Team) => ({
	name,
	r_projectToTeams_c_projectId,
});

const createTeam = (team: Team) => fetcher.post('/teams', adapter(team));

const updateTeam = (id: number, team: Team) =>
	fetcher.put(`/teams/${id}`, adapter(team));

const nestedFieldsParam = 'nestedFields=project';

const teamsResource = `/teams?${nestedFieldsParam}`;

const getTeamQuery = (teamId: number | string) =>
	`/teams/${teamId}?${nestedFieldsParam}`;

const getTeamTransformData = (testrayTeam: TestrayTeam): TestrayTeam => ({
	...testrayTeam,
	project: testrayTeam?.r_projectToTeams_c_project,
});

const getTeamsTransformData = (response: APIResponse<TestrayTeam>) => ({
	...response,
	items: response?.items?.map(getTeamTransformData),
});

export {
	teamsResource,
	createTeam,
	updateTeam,
	getTeamQuery,
	getTeamTransformData,
	getTeamsTransformData,
};
