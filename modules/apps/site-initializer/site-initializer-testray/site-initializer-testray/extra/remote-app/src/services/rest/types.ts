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

import {DescriptionType} from '../../types';

export type FacetAggregation = {
	facets: {
		facetCriteria: string;
		facetValues: {
			numberOfOccurrences: number;
			term: string;
		}[];
	}[];
};

export type APIResponse<Query = any> = {
	actions: Object;
	items: Query[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

// Objects Types

export type PickList = {
	key: string;
	name: string;
};

export type Role = {
	id: number;
	name: string;
};

export type UserAccount = {
	additionalName: string;
	alternateName: string;
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: number;
	image: string;
	roleBriefs: Role[];
	uuid: number;
};

export type UserRole = {
	roles: number;
	rolesBriefs: Role[];
	userId: number;
};

export type TestrayBuild = {
	active: boolean;
	caseResultBlocked: string;
	caseResultFailed: string;
	caseResultInProgress: string;
	caseResultIncomplete: string;
	caseResultPassed: string;
	caseResultTestFix: string;
	caseResultUntested: string;
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	dueStatus: PickList;
	gitHash: string;
	id: number;
	name: string;
	productVersion?: TestrayProductVersion;
	project?: TestrayProject;
	promoted: boolean;
	r_productVersionToBuilds_c_productVersion?: TestrayProductVersion;
	r_projectToBuilds_c_project?: TestrayProject;
	r_routineToBuilds_c_routine?: TestrayRoutine;
	routine?: TestrayRoutine;
	template: boolean;
	templateTestrayBuildId: string;
};

export type TestrayCase = {
	caseNumber: number;
	caseType?: TestrayCaseType;
	component?: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	description: string;
	descriptionType: string;
	estimatedDuration: number;
	id: number;
	name: string;
	originationKey: string;
	priority: number;
	r_caseTypeToCases_c_caseType?: TestrayCaseType;
	r_componentToCases_c_component?: TestrayComponent;
	steps: string;
	stepsType: string;
};

export type TestrayCaseResult = {
	assignedUserId: string;
	attachments: string;
	build?: TestrayBuild;
	case?: TestrayCase;
	closedDate: string;
	commentMBMessage: string;
	commentMBMessageId: string;
	component?: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	dueStatus: PickList;
	errors: string;
	id: number;
	issue: string;
	key: string;
	r_buildToCaseResult_c_build?: TestrayBuild;
	r_caseToCaseResult_c_case?: TestrayCase;
	r_caseToCaseResult_c_caseId?: number;
	r_componentToCaseResult_c_component?: TestrayComponent;
	r_runToCaseResult_c_run?: TestrayRun;
	r_userToCaseResults_user?: UserAccount;
	run?: TestrayRun;
	startDate: string;
	user?: UserAccount;
	warnings: number;
};

export type TestrayCaseType = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

export type TestrayFactorOption = {
	dateCreated: string;
	dateModified: string;
	factorCategory?: TestrayFactorCategory;
	id: number;
	name: string;
	r_factorCategoryToOptions_c_factorCategory: TestrayFactorCategory;
};

export type TestrayProductVersion = {
	id: number;
	name: string;
	project?: TestrayProject;
	r_projectToProductVersions_c_project?: TestrayProject;
};

export type TestrayProject = {
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	id: number;
	name: string;
};

export type TestrayRequirement = {
	component?: TestrayComponent;
	components: string;
	description: string;
	descriptionType: keyof typeof DescriptionType;
	id: number;
	key: string;
	linkTitle: string;
	linkURL: string;
	r_componentToRequirements_c_component?: TestrayComponent;
	summary: string;
};

export type TestrayRequirementCase = {
	case: TestrayCase;
	id: number;
	requirement: TestrayRequirement;
};

export type TestrayRun = {
	build: TestrayBuild;
	dateCreated: string;
	dateModified: string;
	description: string;
	environmentHash: string;
	externalReferenceCode: string;
	externalReferencePK: string;
	externalReferenceType: string;
	factorOption?: TestrayFactorOption;
	id: number;
	jenkinsJobKey: string;
	name: string;
	number: string;
	r_buildToRuns_c_build: TestrayBuild;
	status: string;
};

export type TestraySubTask = {
	dueStatus: PickList;
	id: number;
	name: string;
	r_taskToSubtasks_c_task: TestrayTask;
	r_userToSubtasks_user: UserAccount;
	score: number;
	statusUpdateDate: string;
	task: TestrayTask;
	user: UserAccount;
};

export type TestraySubTaskCaseResult = {
	caseResult?: TestrayCaseResult;
	id?: number;
	name?: string;
	r_caseResultToSubtasksCasesResults_c_caseResult?: TestrayCaseResult;
	r_subtaskToSubtasksCasesResults_c_subtask?: TestraySubTask;
	subTask?: TestraySubTask;
};

export type TestraySuite = {
	caseParameters: string;
	creator: {
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	description: string;
	id: number;
	name: string;
	type: string;
};

export type TestraySuiteCase = {
	case: TestrayCase;
	id: number;
	r_caseToSuitesCases_c_case: TestrayCase;
	r_caseToSuitesCases_c_caseId: number;
	r_caseToSuitesCases_c_suite: TestraySuite;
	suite: TestraySuite;
};

export type TestrayTask = {
	build?: TestrayBuild;
	dateCreated: string;
	dueStatus: PickList;
	id: number;
	name: string;
	r_buildToTasks_c_build?: TestrayBuild;
	subtaskScore: string;
	subtaskScoreCompleted: string;
	subtaskScoreIncomplete: string;
};

export type TestrayTaskUser = {
	id: number;
	name: string;
	r_taskToTasksUsers_c_task?: TestrayTask;
	r_userToTasksUsers_user?: UserAccount;
	task?: TestrayTask;
	user?: UserAccount;
};

export type TestrayTeam = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	project?: TestrayProject;
	r_projectToTeams_c_project?: TestrayProject;
};

export type TestrayComponent = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	originationKey: string;
	project?: TestrayProject;
	r_projectToComponents_c_project?: TestrayProject;
	r_teamToComponents_c_team?: TestrayTeam;
	r_teamToComponents_c_teamId: number;
	status: string;
	team?: TestrayTeam;
	teamId: number;
};

export type TestrayFactorCategory = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

export type TestrayRoutine = {
	dateCreated: string;
	id: number;
	name: string;
};

export type TestrayFactor = {
	dateCreated: string;
	dateModified: string;
	factorCategory?: TestrayFactorCategory;
	factorOption?: TestrayFactorOption;
	id: number;
	r_factorCategoryToFactors_c_factorCategory?: TestrayFactorCategory;
	r_factorOptionToFactors_c_factorOption?: TestrayFactorOption;
	r_runToFactors_c_run?: TestrayRun;
	run: TestrayRun;
};
