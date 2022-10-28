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
	TestrayRun,
	TestrayTeam,
	UserAccount,
} from '../services/rest';

export type Filters = {
	[key: string]: RendererFields[];
};

type Filter = {
	[key: string]: RendererFields;
};

const transformData = <T = any>(response: any): T[] => {
	return response?.items || [];
};

const dataToOptions = <T = any>(
	entries: T[],
	transformAction?: (entry: T) => {label: string; value: number}
) => {
	return entries.map((entry: any) =>
		transformAction
			? transformAction(entry)
			: {label: entry.name, value: entry.id}
	);
};

const baseFilters: Filter = {
	assignee: {
		label: i18n.translate('assignee'),
		name: 'assignee',
		resource: '/headless-admin-user/v1.0/user-accounts',
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
		resource: '/casetypes?fields=id,name',
		transformData(item) {
			return dataToOptions(transformData<TestrayCaseType>(item));
		},
		type: 'multiselect',
	},
	component: {
		label: i18n.translate('Component'),
		name: 'componentId',
		resource: '/components?fields=id,name',
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
		resource: '/productversions?fields=id,name',
		transformData(item) {
			return dataToOptions(transformData<TestrayProductVersion>(item));
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
		resource: '/teams?fields=id,name',
		transformData(item) {
			return dataToOptions(transformData<TestrayTeam>(item));
		},
		type: 'select',
	},
};

const filters = {
	build: {
		caseTypes: [baseFilters.priority, baseFilters.team],
		components: [
			baseFilters.priority,
			baseFilters.caseType,
			baseFilters.team,
			baseFilters.run,
		],
		index: [
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
		],
		results: [
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
		],
		runs: [baseFilters.priority, baseFilters.caseType, baseFilters.team],
		teams: [
			baseFilters.priority,
			baseFilters.caseType,
			baseFilters.team,
			baseFilters.run,
		],
	},
	case: [
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
	],
	requirement: [
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
	],
	requirementCase: [
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
	],
	routines: [baseFilters.priority, baseFilters.caseType, baseFilters.team],
	subtasks: [
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
	],
	suites: [
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
	],
	team: [
		{
			label: i18n.translate('team-name'),
			name: 'team',
		},
	],
	template: [
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
	],
};

export {filters};
