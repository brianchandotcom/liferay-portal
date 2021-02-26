/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {getItem} from 'data-engine-js-components-web/js/utils/client.es';
import {getLocalizedValue} from 'data-engine-js-components-web/js/utils/lang.es';

import {
	checkRequiredFields,
	populateFormViewFields,
	validateSelectedFormViews,
} from './utils.es';

const PARAMS = {keywords: '', page: -1, pageSize: -1, sort: ''};

export function buildLocalizedItems(defaultLanguageId) {
	return (items) =>
		items.map((item) => ({
			...item,
			name: getLocalizedValue(defaultLanguageId, item.name),
		}));
}

export function getAssigneeRoles() {
	return getItem('/o/headless-admin-user/v1.0/roles').then(({items}) =>
		items.filter(({name}) => name !== 'Owner')
	);
}

export function getDataDefinition(dataDefinitionId) {
	return getItem(`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`);
}

export function getFormViews(dataDefinitionId, defaultLanguageId) {
	return getItem(
		`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-layouts`,
		PARAMS
	)
		.then(getItems)
		.then(buildLocalizedItems(defaultLanguageId))
		.then((formViews) =>
			formViews.map((formView) => populateFormViewFields(formView))
		);
}

function getItems({items}) {
	return items;
}

export function getTableViews(dataDefinitionId, defaultLanguageId) {
	return getItem(
		`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-list-views`,
		PARAMS
	)
		.then(getItems)
		.then(buildLocalizedItems(defaultLanguageId));
}

export function populateConfigData([
	app,
	appWorkflow,
	assigneeRoles,
	dataObjects,
	formViews,
	tableViews,
]) {
	appWorkflow.appWorkflowTasks.forEach((task) => {
		task.appWorkflowDataLayoutLinks = task.appWorkflowDataLayoutLinks.map(
			(item) => {
				const {fields, name} = formViews.find(
					({id}) => id === item.dataLayoutId
				);

				return {...item, fields, name};
			}
		);

		task.appWorkflowTransitions.sort(
			(actionA, actionB) => actionB.primary - actionA.primary
		);

		task.errors = {
			formViews: validateSelectedFormViews(
				task.appWorkflowDataLayoutLinks
			),
		};
	});

	const dataObject = dataObjects.find(({id}) => id === app.dataDefinitionId);

	const checkedFormViews = checkRequiredFields(formViews, dataObject);

	const {appWorkflowStates = [], appWorkflowTasks = []} = appWorkflow;

	const initialState = appWorkflowStates.find(({initial}) => initial);
	const finalState = appWorkflowStates.find(({initial}) => !initial);

	const config = {
		currentStep: initialState,
		dataObject,
		formView: checkedFormViews.find(({id}) => id === app.dataLayoutId),
		listItems: {
			assigneeRoles,
			dataObjects,
			fetching: false,
			formViews: checkedFormViews,
			tableViews,
		},
		steps: [initialState, ...appWorkflowTasks, finalState],
		tableView: tableViews.find(({id}) => id === app.dataListViewId),
	};

	if (config.formView.missingRequiredFields.nativeField) {
		app.dataLayoutId = null;
	}

	return [app, config];
}
