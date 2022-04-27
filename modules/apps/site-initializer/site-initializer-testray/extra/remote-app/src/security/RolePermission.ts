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

export enum TestrayRoles {
	OWNER = 'OWNER',
	TESTRAY_ADMINISTRATOR = 'TESTRAY_ADMINISTRATOR',
	TESTRAY_ANALYST = 'TESTRAY_ANALYST',
	TESTRAY_LEAD = 'TESTRAY_LEAD',
	TESTRAY_USER = 'TESTRAY_USER',
	USER = 'USER',
}

export enum TestrayActions {
	'AUTO_FILL',
	'COMPARE',
	'CONFIGURATION',
	'CREATE',
	'DELETE',
	'EDIT',
	'IMPORT',
	'INDEX',
	'MERGE',
	'METRICS',
	'PERMISSIONS',
	'REFRESH',
	'SELECT',
	'SPLIT',
	'SYNC',
	'UNSET',
	'UPDATE',
	'VIEW',
}

export type TestrayEntities = {
	TestrayBuild?: string[];
	TestrayCase?: string[];
	TestrayCaseResult?: string[];
	TestrayCaseType?: string[];
	TestrayComponent?: string[];
	TestrayFactor?: string[];
	TestrayFactorCategory?: string[];
	TestrayFactorOption?: string[];
	TestrayPortlet?: string[];
	TestrayProductVersion?: string[];
	TestrayProject?: string[];
	TestrayRequirement?: string[];
	TestrayRoutine?: string[];
	TestrayRun?: string[];
	TestraySubtask?: string[];
	TestraySuite?: string[];
	TestrayTask?: string[];
	TestrayTeam?: string[];
	User?: string[];
};

export type RolePermission = {
	[key in keyof typeof TestrayRoles]: TestrayEntities;
};

export const rolePermissions: RolePermission = {
	[TestrayRoles.TESTRAY_ADMINISTRATOR]: {
		TestrayBuild: [
			'ADD',
			'AUTO_FILL',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'UPDATE_ARCHIVED',
			'UPDATE_CASES',
			'UPDATE_PROMOTED',
			'UPDATE_STATUS',
			'VIEW',
		],
		TestrayCase: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'IMPORT_CASES',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'UPDATE_REQUIREMENTS',
			'VIEW',
		],
		TestrayCaseResult: [
			'ADD',
			'ADD_TESTRAY_CASE_RESULTS',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'METRICS',
			'PERMISSIONS',
			'UPDATE',
			'UPDATE_STATUS',
			'UPDATE_USER',
			'VIEW',
		],
		TestrayCaseType: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
		],
		TestrayComponent: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'VIEW',
		],
		TestrayFactor: [
			'ADD_TESTRAY_FACTORS',
			'ADD_TESTRAY_RUNS',
			'CONFIGURATION',
			'PERMISSIONS',
		],
		TestrayFactorCategory: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
		],
		TestrayFactorOption: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
		],
		TestrayPortlet: [
			'ADD#ATTACHMENTS',
			'CREATE#ATTACHMENTS',
			'EDIT_CASE_RESULTS#BULK',
			'UNSET_CASE_RESULT#BULK',
			'VIEW',
		],
		TestrayProductVersion: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
		],
		TestrayProject: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'DELETE_TREE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'VIEW',
		],
		TestrayRequirement: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'IMPORT',
			'IMPORT_ISSUES',
			'IMPORT_REQUIREMENTS',
			'INDEX',
			'PERMISSIONS',
			'REMOVE_CASE',
			'SELECT',
			'SYNC',
			'UPDATE',
			'UPDATE_CASES',
			'VIEW',
		],
		TestrayRoutine: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'VIEW',
		],
		TestrayRun: [
			'ADD',
			'AUTO_FILL',
			'COMPARE',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'VIEW',
		],
		TestraySubtask: [
			'EDIT_STATUS',
			'INDEX',
			'MERGE',
			'PERMISSIONS',
			'SPLIT',
			'UPDATE_USER',
		],
		TestraySuite: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'REFRESH_CASES',
			'REMOVE_CASE',
			'UPDATE',
			'UPDATE_CASE_PARAMETERS',
			'UPDATE_CASES',
			'VIEW',
		],
		TestrayTask: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'UPDATE_STATUS',
			'UPDATE_USERS',
		],
		TestrayTeam: [
			'ADD',
			'CONFIGURATION',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
		],
		User: [
			'ADD',
			'CREATE',
			'DELETE',
			'EDIT',
			'INDEX',
			'PERMISSIONS',
			'UPDATE',
			'VIEW',
		],
	},
	[TestrayRoles.OWNER]: {},
	[TestrayRoles.TESTRAY_ANALYST]: {},
	[TestrayRoles.TESTRAY_LEAD]: {},
	[TestrayRoles.TESTRAY_USER]: {
		TestrayProject: [],
	},
	USER: {},
};
