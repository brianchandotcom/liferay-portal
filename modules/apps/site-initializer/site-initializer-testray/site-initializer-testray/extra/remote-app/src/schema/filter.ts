/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RendererFields} from '../components/Form/Renderer';
import SearchBuilder from '../core/SearchBuilder';
import i18n from '../i18n';
import {
	TestrayCaseType,
	TestrayComponent,
	TestrayProductVersion,
	TestrayProject,
	TestrayRoutine,
	TestrayRun,
	TestrayTeam,
	UserAccount,
} from '../services/rest';
import {
	BuildStatuses,
	CaseResultStatuses,
	SubTaskStatuses,
	TaskStatuses,
} from '../util/statuses';

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
	placeholder?: string;
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
	transformAction?: (entry: T) => {label: string; value: number | string}
) =>
	entries.map((entry: any) =>
		transformAction
			? transformAction(entry)
			: {label: entry.name, value: entry.id}
	);

const baseFilters: Filter = {
	assignee: {
		label: i18n.translate('assignee'),
		name: 'assignedUsers',
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
		label: i18n.translate('component'),
		name: 'componentId',
		resource: ({projectId}) =>
			`/components?fields=id,name&sort=name:asc&pageSize=200&filter=${SearchBuilder.eq(
				'projectId',
				projectId as string
			)}`,
		transformData(item) {
			return dataToOptions(transformData<TestrayComponent>(item));
		},
		type: 'select',
	},
	description: {
		label: i18n.translate('description'),
		name: 'description',
		type: 'textarea',
	},
	dueStatus: {
		label: i18n.translate('status'),
		name: 'dueStatus',
		type: 'checkbox',
	},
	erros: {
		label: i18n.translate('errors'),
		name: 'errors',
		type: 'textarea',
	},
	hasRequirements: {
		disabled: true,
		label: i18n.translate('has-requirements'),
		name: 'caseToRequirementsCases',
		options: ['true', 'false'],
		type: 'select',
	},
	issues: {
		label: i18n.translate('issues'),
		name: 'issues',
		type: 'textarea',
	},
	priority: {
		label: i18n.translate('priority'),
		name: 'priority',
		options: ['1', '2', '3', '4', '5'],
		type: 'multiselect',
	},
	productVersion: {
		label: i18n.translate('product-version'),
		name: 'productVersion',
		resource: ({projectId}) =>
			`/productversions?fields=id,name&sort=name:asc&pageSize=100&filter=${SearchBuilder.eq(
				'projectId',
				projectId as string
			)}`,
		transformData(item) {
			return dataToOptions(transformData<TestrayProductVersion>(item));
		},
		type: 'select',
	},
	project: {
		label: i18n.translate('project'),
		name: 'projectId',
		resource: '/projects?fields=id,name&pageSize=100',
		transformData(item) {
			return dataToOptions(transformData<TestrayProject>(item));
		},
		type: 'select',
	},
	routine: {
		label: i18n.translate('routines'),
		name: 'routines',
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
		resource: '/runs?fields=id,number',
		transformData(item) {
			return dataToOptions(transformData<TestrayRun>(item), (run) => ({
				label: run?.number?.toString().padStart(2, '0'),
				value: run.number,
			}));
		},
		type: 'select',
	},
	steps: {
		label: i18n.translate('steps'),
		name: 'steps',
		type: 'textarea',
	},
	team: {
		label: i18n.translate('team'),
		name: 'teamId',
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
	user: {label: i18n.translate('name'), name: 'name', type: 'text'},
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
			overrides(baseFilters.priority, {
				name: 'componentToCases/priority',
				removeQuoteMark: true,
				type: 'select',
			}),
			overrides(baseFilters.caseType, {
				name: 'componentToCases/caseTypeId',
			}),
			overrides(baseFilters.team, {
				type: 'multiselect',
			}),
			overrides(baseFilters.run, {
				name: 'componentToCaseResult/r_runToCaseResult_c_runId',
				transformData(item) {
					return dataToOptions(
						transformData<TestrayRun>(item),
						(run) => ({
							label: run?.number?.toString().padStart(2, '0'),
							value: run?.id,
						})
					);
				},
			}),
		] as RendererFields[],
	},
	buildResults: {
		fields: [
			overrides(baseFilters.caseType, {
				name: 'caseToCaseResult/r_caseTypeToCases_c_caseTypeId',
				type: 'multiselect',
			}),
			overrides(baseFilters.priority, {
				name: 'caseToCaseResult/priority',
				removeQuoteMark: true,
				type: 'select',
			}),
			overrides(baseFilters.team, {
				name: 'componentToCaseResult/r_teamToComponents_c_teamId',
				type: 'multiselect',
			}),
			overrides(baseFilters.component, {
				name: 'componentToCaseResult/id',
				type: 'multiselect',
			}),
			{
				label: i18n.translate('environment'),
				name: 'runToCaseResult/name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.run, {
				name: 'runToCaseResult/number',
				removeQuoteMark: true,
				type: 'select',
			}),
			{
				label: i18n.translate('case-name'),
				name: 'caseToCaseResult/name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.assignee, {name: 'userId'}),
			overrides(baseFilters.dueStatus, {
				options: [
					{
						label: i18n.translate('blocked'),
						value: CaseResultStatuses.BLOCKED,
					},
					{
						label: i18n.translate('failed'),
						value: CaseResultStatuses.FAILED,
					},
					{
						label: i18n.translate('in-progress'),
						value: CaseResultStatuses.IN_PROGRESS,
					},
					{
						label: i18n.translate('passed'),
						value: CaseResultStatuses.PASSED,
					},
					{
						label: i18n.translate('test-fix'),
						value: CaseResultStatuses.TEST_FIX,
					},
					{
						label: i18n.translate('untested'),
						value: CaseResultStatuses.UNTESTED,
					},
				],
			}),
			overrides(baseFilters.issues, {
				operator: 'contains',
			}),
			overrides(baseFilters.erros, {
				operator: 'contains',
			}),
			{
				label: i18n.translate('comments'),
				name: 'comment',
				operator: 'contains',
				type: 'textarea',
			},
		] as RendererFields[],
	},
	buildResultsHistory: {
		fields: [
			overrides(baseFilters.productVersion, {
				label: i18n.translate('product-version-name'),
				name:
					'buildToCaseResult/r_productVersionToBuilds_c_productVersionId',
				type: 'multiselect',
			}),
			{
				label: i18n.translate('environment'),
				name: 'runToCaseResult/name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.routine, {
				name: 'buildToCaseResult/routineId',
			}),
			overrides(baseFilters.assignee, {
				name: 'userId',
			}),
			overrides(baseFilters.dueStatus, {
				options: [
					{
						label: 'Blocked',
						value: CaseResultStatuses.BLOCKED,
					},
					{
						label: 'Failed',
						value: CaseResultStatuses.FAILED,
					},
					{
						label: 'In Progress',
						value: CaseResultStatuses.IN_PROGRESS,
					},
					{
						label: 'Passed',
						value: CaseResultStatuses.PASSED,
					},
					{
						label: 'Test Fix',
						value: CaseResultStatuses.TEST_FIX,
					},
					{
						label: 'Untested',
						value: CaseResultStatuses.UNTESTED,
					},
				],
			}),
			overrides(baseFilters.issues, {
				operator: 'contains',
			}),
			overrides(baseFilters.erros, {
				operator: 'contains',
			}),
			{
				label: i18n.translate('case-result-warning'),
				name: 'warnings',
				type: 'number',
			},
			{
				label: i18n.sub('x-create-date', 'min'),
				name: 'dateCreated',
				operator: 'gt',
				type: 'date',
			},
			{
				label: i18n.sub('x-create-date', 'max'),
				name: 'dateCreated$',
				operator: 'lt',
				type: 'date',
			},
			overrides(baseFilters.team, {
				name: 'componentToCaseResult/r_teamToComponents_c_teamId',
				type: 'multiselect',
			}),
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
			overrides(baseFilters.priority, {disabled: true, type: 'select'}),
			overrides(baseFilters.caseType, {disabled: true, type: 'select'}),
			overrides(baseFilters.team, {name: 'id', type: 'multiselect'}),
			overrides(baseFilters.run, {disabled: true}),
		] as RendererFields[],
	},
	buildTemplates: {
		fields: [
			{
				label: i18n.translate('template-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.dueStatus, {
				options: [
					{
						label: i18n.translate('activated'),
						value: BuildStatuses.ACTIVATED,
					},
					{
						label: i18n.translate('deactivated'),
						value: BuildStatuses.DEACTIVATED,
					},
				],
				type: 'select',
			}),
		] as RendererFields[],
	},
	builds: {
		fields: [
			overrides(baseFilters.priority, {
				disabled: true,
				type: 'select',
			}),
			overrides(baseFilters.productVersion, {
				name: 'productVersionToBuilds/id',
				type: 'select',
			}),
			overrides(baseFilters.caseType, {disabled: true, type: 'select'}),
			{
				label: i18n.translate('build-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('status'),
				name: 'buildToTasks/dueStatus',
				options: [
					{
						label: i18n.translate('abandoned'),
						value: TaskStatuses.ABANDONED,
					},
					{
						label: i18n.translate('complete'),
						value: TaskStatuses.COMPLETE,
					},
					{
						label: i18n.translate('in-analysis'),
						value: TaskStatuses.IN_ANALYSIS,
					},
					{
						label: i18n.translate('open'),
						value: TaskStatuses.OPEN,
					},
				],
				type: 'checkbox',
			},
			overrides(baseFilters.team, {disabled: true}),
		] as RendererFields[],
	},
	caseRequirements: {
		fields: [
			{
				label: i18n.translate('key'),
				name: 'requiremenToRequirementsCases/key',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('link'),
				name: 'requiremenToRequirementsCases/linkURL',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('jira-components'),
				name: 'requiremenToRequirementsCases/components',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('summary'),
				name: 'requiremenToRequirementsCases/summary',
				operator: 'contains',
				type: 'text',
			},
		] as RendererFields[],
	},
	cases: {
		fields: [
			overrides(baseFilters.priority, {
				removeQuoteMark: true,
				type: 'select',
			}),
			overrides(baseFilters.caseType, {
				name: 'r_caseTypeToCases_c_caseTypeId',
			}),
			{
				label: i18n.translate('case-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.team, {
				name: 'componentToCases/r_teamToComponents_c_teamId',
				type: 'multiselect',
			}),
			overrides(baseFilters.component, {
				name: 'componentId',
				type: 'multiselect',
			}),
			baseFilters.description,
			baseFilters.steps,
			overrides(baseFilters.issues, {disabled: true}),
			baseFilters.hasRequirements,
		] as RendererFields[],
	},
	compareRunsCases: {
		fields: [
			overrides(baseFilters.priority, {
				name: 'priority',
				removeQuoteMark: true,
				type: 'select',
			}),
			overrides(baseFilters.team, {
				disabled: true,
				name: 'componentToCaseResult/r_teamToComponents_c_teamId',
				type: 'multiselect',
			}),
			overrides(baseFilters.component, {
				disabled: true,
				name: 'componentToCaseResult/id',
				type: 'multiselect',
			}),
			{
				label: i18n.translate('case-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.dueStatus, {
				disabled: true,
				label: i18n.sub('status-in-x', 'run-a'),
				options: [
					{
						label: i18n.translate('blocked'),
						value: CaseResultStatuses.BLOCKED,
					},
					{
						label: i18n.translate('failed'),
						value: CaseResultStatuses.FAILED,
					},
					{
						label: i18n.translate('in-progress'),
						value: CaseResultStatuses.IN_PROGRESS,
					},
					{
						label: i18n.translate('passed'),
						value: CaseResultStatuses.PASSED,
					},
					{
						label: i18n.translate('test-fix'),
						value: CaseResultStatuses.TEST_FIX,
					},
					{
						label: i18n.translate('untested'),
						value: CaseResultStatuses.UNTESTED,
					},
				],
				type: 'select',
			}),
			overrides(baseFilters.dueStatus, {
				disabled: true,
				label: i18n.sub('status-in-x', 'run-b'),
				options: [
					{
						label: i18n.translate('blocked'),
						value: CaseResultStatuses.BLOCKED,
					},
					{
						label: i18n.translate('failed'),
						value: CaseResultStatuses.FAILED,
					},
					{
						label: i18n.translate('in-progress'),
						value: CaseResultStatuses.IN_PROGRESS,
					},
					{
						label: i18n.translate('passed'),
						value: CaseResultStatuses.PASSED,
					},
					{
						label: i18n.translate('test-fix'),
						value: CaseResultStatuses.TEST_FIX,
					},
					{
						label: i18n.translate('untested'),
						value: CaseResultStatuses.UNTESTED,
					},
				],
				type: 'select',
			}),
		] as RendererFields[],
	},
	components: {
		fields: [
			{
				label: i18n.translate('component-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
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
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('link'),
				name: 'linkTitle',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.team, {
				name: 'componentToRequirements/r_teamToComponents_c_teamId',
				type: 'multiselect',
			}),
			overrides(baseFilters.component, {type: 'multiselect'}),
			{
				label: i18n.translate('jira-components'),
				name: 'components',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('summary'),
				name: 'summary',
				operator: 'contains',
				type: 'text',
			},
			{
				disabled: true,
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
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			{
				label: i18n.translate('errors'),
				name: 'errors',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.assignee, {name: 'userId'}),
			{
				label: i18n.translate('status'),
				name: 'dueStatus',
				options: [
					{
						label: i18n.translate('complete'),
						value: SubTaskStatuses.COMPLETE,
					},
					{
						label: i18n.translate('in-analysis'),
						value: SubTaskStatuses.IN_ANALYSIS,
					},
					{
						label: i18n.translate('open'),
						value: SubTaskStatuses.OPEN,
					},
				],
				type: 'checkbox',
			},
			overrides(baseFilters.team, {
				disabled: true,
			}),
			{
				disabled: true,
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
				name: 'name',
				operator: 'contains',
				placeholder: i18n.translate('search'),
				type: 'text',
			},
		] as RendererFields[],
	},
	teams: {
		fields: [
			{
				label: i18n.translate('team-name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
		] as RendererFields[],
	},
	testflow: {
		fields: [
			{
				label: i18n.sub('task-x', 'name'),
				name: 'name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.project, {
				label: i18n.translate('project-name'),
				name: 'buildToTasks/r_projectToBuilds_c_projectId',
				type: 'multiselect',
			}),
			overrides(baseFilters.routine, {
				label: i18n.translate('routine-name'),
				name: 'buildToTasks/r_routineToBuilds_c_routineId',
				resource: '/routines?fields=id,name&sort=name:asc&pageSize=100',
				type: 'multiselect',
			}),
			{
				label: i18n.translate('build-name'),
				name: 'buildToTasks/name',
				operator: 'contains',
				type: 'text',
			},
			overrides(baseFilters.dueStatus, {
				options: [
					{
						label: i18n.translate('abandoned'),
						value: TaskStatuses.ABANDONED,
					},
					{
						label: i18n.translate('complete'),
						value: TaskStatuses.COMPLETE,
					},
					{
						label: i18n.translate('in-analysis'),
						value: TaskStatuses.IN_ANALYSIS,
					},
				],
			}),
			overrides(baseFilters.assignee, {
				name: 'taskToTasksUsers/r_userToTasksUsers_userId',
				type: 'select',
			}),
		] as RendererFields[],
	},
	user: {
		fields: [overrides(baseFilters.user, {operator: 'contains'})],
	},
} as const;

export {filterSchema};
