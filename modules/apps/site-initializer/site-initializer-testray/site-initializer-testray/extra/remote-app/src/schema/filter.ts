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

import {RendererFields} from '../components/Form/Renderer';
import i18n from '../i18n';
import {
	TestrayCaseType,
	TestrayComponent,
	TestrayProductVersion,
	TestrayRoutine,
	TestrayRun,
	TestrayTeam,
	UserAccount,
} from '../services/rest';
import {SearchBuilder} from '../util/search';

export type Filters = {
	[key: string]: RendererFields[];
};

type Filter = {
	[key: string]: RendererFields;
};

export type FilterVariables = {
	appliedFilter: {
		[key: string]: string;
	};
	defaultFilter: string | SearchBuilder;
	filterSchema: FilterSchema;
};

export type FilterSchema = {
	fields: RendererFields[];
	name?: string;
	onApply?: (filterVariables: FilterVariables) => string;
};

export type FilterSchemas = {
	[key: string]: FilterSchema;
};

export type FilterSchemaOption = keyof typeof filterSchema;

const transformData = <T = any>(response: any): T[] => {
	return response?.items || [];
};

const dataToOptions = <T = any>(
	entries: T[],
	transformAction?: (entry: T) => {label: string; value: number}
) =>
	entries.map((entry: any) =>
		transformAction
			? transformAction(entry)
			: {label: entry.name, value: entry.id}
	);

const baseFilters: Filter = {
	assignee: {
		label: i18n.translate('assignee'),
		name: 'assignee',
		resource: '/user-accounts',
		transformData(item) {
			return dataToOptions(
				transformData<UserAccount>(item),
				(userAccount) => ({
					label: `${userAccount.givenName} ${userAccount.additionalName}`,
					value: userAccount.id,
				})
			);
		},
		type: 'select',
	},
	caseType: {
		label: i18n.translate('case-type'),
		name: 'caseType',
		resource: '/casetypes?fields=id,name&sort=name:asc&pageSize=100',
		transformData(item) {
			return dataToOptions(transformData<TestrayCaseType>(item));
		},
		type: 'multiselect',
	},
	component: {
		label: i18n.translate('Component'),
		name: 'componentId',
		resource: ({projectId}) =>
			`/components?fields=id,name&sort=name:asc&pageSize=100&filter=${SearchBuilder.eq(
				'projectId',
				projectId as string
			)}`,
		transformData(item) {
			return dataToOptions(transformData<TestrayComponent>(item));
		},
		type: 'select',
	},
	priority: {
		label: 'Priority',
		name: 'priority',
		options: ['1', '2', '3', '4', '5'],
		type: 'multiselect',
	},
	productVersion: {
		label: i18n.translate('product-version'),
		name: 'productVersion',
		resource: '/productversions?fields=id,name&sort=name:asc&pageSize=100',
		transformData(item) {
			return dataToOptions(transformData<TestrayProductVersion>(item));
		},
		type: 'select',
	},
	routine: {
		label: i18n.translate('routines'),
		name: 'routines',
		options: [{label: 'Solutions', value: 'solutions'}],
		resource: ({projectId}) =>
			`/routines?fields=id,name&pageSize=100&filter=${SearchBuilder.eq(
				'projectId',
				projectId as string
			)}`,
		transformData(item) {
			return dataToOptions(transformData<TestrayRoutine>(item));
		},
		type: 'select',
	},
	run: {
		label: i18n.translate('run'),
		name: 'run',
		resource: '/runs?fields=id,name',
		transformData(item) {
			return dataToOptions(transformData<TestrayRun>(item));
		},
		type: 'select',
	},
	team: {
		label: i18n.translate('team'),
		name: 'teamId',
		options: [{label: 'Solutions', value: 'solutions'}],
		resource: ({projectId}) =>
			`/teams?fields=id,name&sort=name:asc&pageSize=100&filter=${SearchBuilder.eq(
				'projectId',
				projectId as string
			)}`,
		transformData(item) {
			return dataToOptions(transformData<TestrayTeam>(item));
		},
		type: 'select',
	},
};

const overrides = (
	object: RendererFields,
	newObject: Partial<RendererFields>
) => ({
	...object,
	...newObject,
});

const filterSchema = {
	buildCaseTypes: {
		fields: [baseFilters.priority, baseFilters.team] as RendererFields[],
	},
	buildComponents: {
		fields: [
			baseFilters.priority,
			overrides(baseFilters.caseType, {disabled: false}),
			baseFilters.team,
			baseFilters.run,
		] as RendererFields[],
	},
	buildResults: {
		fields: [
			baseFilters.caseType,
			baseFilters.priority,
			baseFilters.team,
			baseFilters.component,
			{
				label: i18n.translate('environment'),
				name: 'environment',
				type: 'text',
			},
			baseFilters.run,
			{
				label: i18n.translate('case-name'),
				name: 'caseName',
				type: 'text',
			},
			baseFilters.assignee,
			{
				label: i18n.translate('status'),
				name: 'status',
				options: [
					'Blocked',
					'Failed',
					'In Progress',
					'Passed',
					'Test Fix',
					'Untested',
				],
				type: 'checkbox',
			},
			{
				label: i18n.translate('issues'),
				name: 'issues',
				type: 'textarea',
			},
			{
				label: i18n.translate('errors'),
				name: 'errors',
				type: 'textarea',
			},
			{
				label: i18n.translate('comments'),
				name: 'comments',
				type: 'textarea',
			},
		] as RendererFields[],
	},
	buildResultsHistory: {
		fields: [
			{
				disabled: 'false',
				label: i18n.translate('product-version-name'),
				name:
					'buildToCaseResult/r_productVersionToBuilds_c_productVersion',
				type: 'text',
			},
			{
				label: i18n.translate('environment'),
				name: 'runToCaseResult/name',
				type: 'text',
			},
			baseFilters.routine,
			baseFilters.assignee,
			{
				label: i18n.translate('status'),
				name: 'dueStatus',
				options: [
					'Blocked',
					'Failed',
					'In Progress',
					'Passed',
					'Test Fix',
					'Untested',
				],
				type: 'checkbox',
			},
			{
				label: i18n.translate('issues'),
				name: 'issues',
				type: 'textarea',
			},
			{
				label: i18n.translate('errors'),
				name: 'errors',
				type: 'textarea',
			},
			{
				label: i18n.translate('case-result-warning'),
				name: 'warnings',
				type: 'text',
			},
			{
				label: i18n.sub('x-create-date', 'min'),
				name: 'minCreateDate',
				type: 'date',
			},
			{
				label: i18n.sub('x-create-date', 'max'),
				name: 'maxCreateDate',
				type: 'date',
			},

			baseFilters.team,
		] as RendererFields[],
	},
	buildRuns: {
		fields: [
			baseFilters.priority,
			baseFilters.caseType,
			baseFilters.team,
		] as RendererFields[],
	},
	buildTeams: {
		fields: [
			baseFilters.priority,
			baseFilters.caseType,
			baseFilters.team,
			baseFilters.run,
		] as RendererFields[],
	},
	buildTemplates: {
		fields: [
			{
				label: i18n.translate('template-name'),
				name: 'template-name',
				type: 'text',
			},
			{
				label: i18n.translate('status'),
				name: 'status',
				type: 'select',
			},
		] as RendererFields[],
	},
	builds: {
		fields: [
			baseFilters.priority,
			baseFilters.productVersion,
			baseFilters.caseType,
			{
				label: i18n.translate('build-name'),
				name: 'buildName',
				type: 'text',
			},
			{
				label: i18n.translate('status'),
				name: 'status',
				options: ['Open', 'Abandoned', 'Complete', 'In Analysis'],
				type: 'checkbox',
			},
			baseFilters.team,
		] as RendererFields[],
	},
	cases: {
		fields: [
			baseFilters.priority,
			overrides(baseFilters.caseType, {
				name: 'r_caseTypeToCases_c_caseTypeId',
			}),
			{
				label: i18n.translate('case-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			{...baseFilters.team},
			overrides(baseFilters.component, {name: 'componentId'}),
		] as RendererFields[],
	},
	requirementCases: {
		fields: [
			baseFilters.priority,
			baseFilters.caseType,
			{
				label: i18n.translate('case-name'),
				name: 'caseName',
				type: 'text',
			},
			baseFilters.team,
			{
				label: i18n.translate('component'),
				name: 'component',
				type: 'text',
			},
		] as RendererFields[],
	},
	requirements: {
		fields: [
			{
				label: i18n.translate('key'),
				name: 'key',
				type: 'text',
			},
			{
				label: i18n.translate('link'),
				name: 'linkURL',
				type: 'text',
			},
			baseFilters.team,
			baseFilters.component,
			{
				...baseFilters.component,
				label: i18n.translate('jira-components'),
				name: 'jira-components',
			},
			{
				label: i18n.translate('summary'),
				name: 'summary',
				type: 'text',
			},
			{
				label: i18n.translate('case'),
				name: 'case',
				type: 'textarea',
			},
		] as RendererFields[],
	},
	routines: {
		fields: [
			baseFilters.priority,
			baseFilters.caseType,
			baseFilters.team,
		] as RendererFields[],
	},
	subtasks: {
		fields: [
			{
				label: i18n.translate('subtask-name'),
				name: 'subtaskName',
				type: 'text',
			},
			{
				label: i18n.translate('errors'),
				name: 'errors',
				type: 'text',
			},
			baseFilters.assignee,
			{
				label: i18n.translate('status'),
				name: 'status',
				options: ['Complete', 'In Analysis', 'Open'],
				type: 'checkbox',
			},
			baseFilters.team,
			{
				label: i18n.translate('component'),
				name: 'commponent',
				type: 'text',
			},
		] as RendererFields[],
	},
	suites: {
		fields: [
			{
				label: i18n.translate('suite-name'),
				name: 'suiteName',
				type: 'text',
			},
			{
				label: i18n.translate('description'),
				name: 'description',
				type: 'text',
			},
		] as RendererFields[],
	},
	teams: {
		fields: [
			{
				label: i18n.translate('team-name'),
				name: 'team',
			},
		] as RendererFields[],
	},
} as const;

export {filterSchema};
