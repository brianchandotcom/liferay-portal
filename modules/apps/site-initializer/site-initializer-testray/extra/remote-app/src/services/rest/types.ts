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

export type TestrayBuild = {
	creator: {
		name: string;
	};
	dateCreated: string;
	description: string;
	dueStatus: number;
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
	component: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	dueStatus: number;
	errors: string;
	id: number;
	issue: string;
	r_buildToCaseResult_c_build?: TestrayBuild;
	r_caseToCaseResult_c_case?: TestrayCase;
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

export type TestrayFactorOptions = {
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
	id: number;
	jenkinsJobKey: string;
	name: string;
	number: string;
	status: string;
};

export type TestraySubTask = {
	dueStatus: number;
	name: string;
	score: number;
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
	suite: TestraySuite;
};

export type TestrayTask = {
	build?: TestrayBuild;
	dateCreated: string;
	dueStatus: number;
	id: number;
	name: string;
	r_buildToTasks_c_build?: TestrayBuild;
};

export type TestrayTeam = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
};

export type TestrayComponent = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	originationKey: string;
	r_teamToComponents_c_team?: TestrayTeam;
	status: string;
	team?: TestrayTeam;
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
