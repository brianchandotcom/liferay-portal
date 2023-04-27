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

import {StatusBadgeType} from '../../components/StatusBadge/StatusBadge';
import {DescriptionType} from '../../types';

export type ActionPermissionProperties = {
	href: string;
	method: string;
};

type Facets = {
	facetCriteria: string;
	facetValues: {
		numberOfOccurrences: number;
		term: string;
	}[];
};

export type FacetAggregation = {
	facets: Facets[];
};

type ObjectActions = {
	create?: ActionPermissionProperties;
	createBatch?: ActionPermissionProperties;
	deleteBatch?: ActionPermissionProperties;
	updateBatch?: ActionPermissionProperties;
};

export type ObjectActionsItems = {
	delete?: ActionPermissionProperties;
	get?: ActionPermissionProperties;
	replace?: ActionPermissionProperties;
	update?: ActionPermissionProperties;
};

export type APIResponse<Query = any> = {
	actions: ObjectActions;
	facets: Facets[];
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

export type UserGroup = {
	id: number;
	name: string;
};

export type UserActions = {
	'delete-user-account': ActionPermissionProperties;
	'get-my-user-account': ActionPermissionProperties;
	'patch-user-account': ActionPermissionProperties;
	'put-user-account': ActionPermissionProperties;
};

export type UserAccount = {
	actions: UserActions;
	additionalName: string;
	alternateName: string;
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: number;
	image: string;
	name: string;
	roleBriefs: Role[];
	userGroupBriefs: UserGroup[];
	uuid: number;
};

export type CaseResultAggregation = {
	caseResultBlocked: number | string;
	caseResultFailed: number | string;
	caseResultInProgress: number | string;
	caseResultIncomplete: number | string;
	caseResultPassed: number | string;
	caseResultTestFix: number | string;
	caseResultUntested: number | string;
};

export type UserRole = {
	roles: number;
	rolesBriefs: Role[];
	userId: number;
};

export type TestrayBuild = {
	actions: ObjectActionsItems;
	buildToTasks: TestrayTask[];
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
	tasks: TestrayTask[];
	template: boolean;
	templateTestrayBuildId: string;
} & CaseResultAggregation;

export type TestrayCase = {
	actions: ObjectActionsItems;
	caseResults?: TestrayCaseResult[];
	caseToCaseResult?: TestrayCaseResult[];
	caseType?: TestrayCaseType;
	component?: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	description: string;
	descriptionType: string;
	estimatedDuration: number;
	id: number;
	name: string;
	number: number;
	originationKey: string;
	priority: number;
	project?: TestrayProject;
	r_caseTypeToCases_c_caseType?: TestrayCaseType;
	r_componentToCases_c_component?: TestrayComponent;
	r_projectToCases_c_project?: TestrayProject;
	steps: string;
	stepsType: string;
};

export type TestrayCaseResult = {
	actions: ObjectActionsItems;
	assignedUserId: string;
	attachments: string;
	build?: TestrayBuild;
	case?: TestrayCase;
	caseResultToCaseResultsIssues: TestrayCaseResultIssue[];
	closedDate: string;
	comment: string;
	component?: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	dueStatus: PickList;
	errors: string;
	id: number;
	issues: TestrayCaseResultIssue[];
	key: string;
	mbMessageId: number;
	mbThreadId: number;
	r_buildToCaseResult_c_build?: TestrayBuild;
	r_buildToCaseResult_c_buildId?: number;
	r_caseToCaseResult_c_case?: TestrayCase;
	r_caseToCaseResult_c_caseId?: number;
	r_componentToCaseResult_c_component?: TestrayComponent;
	r_runToCaseResult_c_run?: TestrayRun;
	r_runToCaseResult_c_runId?: number;
	r_userToCaseResults_user?: UserAccount;
	run?: TestrayRun;
	runId?: number;
	startDate: string;
	user?: UserAccount;
	warnings: number;
} & CaseResultAggregation;

export type TestrayCaseResultIssue = {
	caseResult?: TestrayCaseResult;
	id: number;
	issue?: TestrayIssue;
	name: string;
	r_caseResultToCaseResultsIssues_c_caseResult?: TestrayCaseResult;
	r_issueToCaseResultsIssues_c_issue?: TestrayIssue;
};

export type TestrayCaseType = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

export type TestrayDyspatchTrigger = {
	creator: {
		name: string;
		urlImage?: string;
	};
	status: StatusBadgeType;
	type: string;
};

export type TestrayFactorOption = {
	dateCreated: string;
	dateModified: string;
	factorCategory?: TestrayFactorCategory;
	id: number;
	name: string;
	r_factorCategoryToOptions_c_factorCategory: TestrayFactorCategory;
};

export type TestrayIssue = {
	dateCreated: number;
	dateModified: number;
	id: number;
	name: string;
};

export type TestrayProductVersion = {
	id: number;
	name: string;
	project?: TestrayProject;
	r_projectToProductVersions_c_project?: TestrayProject;
};

export type TestrayProject = {
	actions: ObjectActionsItems;
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	id: number;
	name: string;
};

export type TestrayRequirement = {
	actions: ObjectActionsItems;
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
	url: string;
};

export type TestrayRequirementCase = {
	case?: TestrayCase;
	id: number;
	r_caseToRequirementsCases_c_case?: TestrayCase;
	r_requiremenToRequirementsCases_c_requirement?: TestrayRequirement;
	requirement?: TestrayRequirement;
};

export type TestrayRun = {
	build?: TestrayBuild;
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
	r_buildToRuns_c_build?: TestrayBuild;
	status: string;
} & CaseResultAggregation;

export type TestraySubTask = {
	actions: ObjectActionsItems;
	dateCreated: string;
	dateModified: string;
	dueStatus: PickList;
	errors: string;
	id: number;
	mbMessageId: number;
	mbThreadId: number;
	mergedToSubtaskId: TestraySubTask;
	name: string;
	number: number;
	r_mergedToTestraySubtask_c_subtaskId: TestraySubTask;
	r_splitFromTestraySubtask_c_subtask: TestraySubTask;
	r_taskToSubtasks_c_task: TestrayTask;
	r_userToSubtasks_user: UserAccount;
	r_userToSubtasks_userId: number;
	score: number;
	splitFromSubtask: TestraySubTask;
	statusUpdateDate: string;
	task: TestrayTask;
	user: UserAccount;
};

export type TestraySubTaskCaseResult = {
	caseResult?: TestrayCaseResult;
	id: number;
	name: string;
	r_caseResultToSubtasksCasesResults_c_caseResult?: TestrayCaseResult;
	r_subtaskToSubtasksCasesResults_c_subtask?: TestraySubTask;
	subTask?: TestraySubTask;
};

export type TestraySubTaskIssue = {
	id: number;
	issue?: TestrayIssue;
	name: string;
	r_issueToSubtasksIssues_c_issue?: TestrayIssue;
	r_subtaskToSubtasksIssues_c_subtask?: TestraySubTask;
	subTask?: TestraySubTask;
};

export type TestraySuite = {
	actions: ObjectActionsItems;
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
	actions: ObjectActionsItems;
	build?: TestrayBuild;
	dateCreated: string;
	dispatchTriggerId: number;
	dueStatus: PickList;
	id: number;
	name: string;
	r_buildToTasks_c_build?: TestrayBuild;
	subtaskScore: string;
	subtaskScoreCompleted: string;
	subtaskScoreSelfIncomplete: string;
	taskToTasksUsers: any;
};

export type TestrayTaskCaseTypes = {
	caseType?: TestrayCaseType;
	id: number;
	name: string;
	r_caseTypeToTasksCaseTypes_c_caseType?: TestrayCaseType;
	r_taskToTasksCaseTypes_c_taskId?: TestrayTask;
	task?: TestrayTask;
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
} & CaseResultAggregation;

export type TestrayFactorCategory = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: string;
};

export type TestrayRoutine = {
	actions: ObjectActionsItems;
	builds: TestrayBuild[];
	dateCreated: string;
	id: number;
	name: string;
	routineToBuilds: TestrayBuild[];
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

export type TestrayAttachment = {
	name: string;
	url: string;
	value: string;
};

export type TestrayDispatchTrigger = {
	creator: {
		image: string;
		name: string;
	};
	dateCreated: string;
	dispatchTriggerId: number;
	dueStatus: PickList;
	externalReferenceCode: string;
	id: number;
	name: string;
	output: string;
	type: string;
};

export type MessageBoardMessage = {
	articleBody: string;
	creator: {
		image: string;
		name: string;
	};
	dateCreated: string;
	headline: string;
	id: string;
};

export type StorageType = 'persisted' | 'temporary';
