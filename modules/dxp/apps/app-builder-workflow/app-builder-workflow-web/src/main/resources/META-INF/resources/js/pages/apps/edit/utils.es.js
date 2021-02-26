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

import {isEqualObjects} from 'data-engine-js-components-web/js/utils/utils.es';

export function canDeployApp(app, config) {
	const isValidSteps = config.steps.every((step) => {
		const assigneeRoles = step.appWorkflowRoleAssignments || [{}];
		const duplicatedFields = step.errors?.formViews.duplicatedFields || [];
		const isValidFormViews = step.appWorkflowDataLayoutLinks?.every(
			({dataLayoutId}) => dataLayoutId
		);
		const transitions = step.appWorkflowTransitions || [];

		const isValidTransitionNames = transitions.every(
			({name}) => name.trim().length
		);

		return (
			assigneeRoles.length &&
			!duplicatedFields.length &&
			(isValidFormViews || step.initial !== undefined) &&
			isValidTransitionNames &&
			step.name.trim().length
		);
	});

	return (
		app.dataDefinitionId &&
		app.dataLayoutId &&
		app.dataListViewId &&
		app.appName?.trim().length &&
		isValidSteps
	);
}

export function checkRequiredFields(formViews = [], dataDefinition) {
	const requiredFields = dataDefinition.dataDefinitionFields
		.filter(({required}) => required)
		.map(({customProperties: {nativeField}, name}) => ({
			name,
			nativeField,
		}));

	const isMissingRequiredFields = (formView) => {
		const missingRequiredFields = {customField: false, nativeField: false};

		requiredFields.forEach(({name, nativeField}) => {
			if (!formView.fields.includes(name)) {
				missingRequiredFields[
					nativeField ? 'nativeField' : 'customField'
				] = true;
			}
		});

		return missingRequiredFields;
	};

	return formViews.map((formView) => ({
		...formView,
		missingRequiredFields: isMissingRequiredFields(formView),
	}));
}

export function hasConfigBreakChanges({draftConfig, ...config}) {
	const props = ['dataObject', 'formView', 'steps'];

	return props
		.map((prop) => !isEqualObjects(draftConfig[prop], config[prop]))
		.some((isDifferent) => isDifferent);
}

export function populateFormViewFields(formView) {
	return {
		...formView,
		fields: formView.dataLayoutPages?.reduce(
			(fields, {dataLayoutRows}) => [
				...fields,
				...dataLayoutRows.reduce(
					(fields, {dataLayoutColumns}) => [
						...fields,
						...dataLayoutColumns.reduce(
							(fields, {fieldNames}) => [
								...fields,
								...fieldNames,
							],
							[]
						),
					],
					[]
				),
			],
			[]
		),
	};
}

export function validateSelectedFormViews(formViews = []) {
	const duplicatedFields = [];
	const errorIndexes = [];

	formViews.forEach(({fields}, index) => {
		formViews.forEach(({fields: nextFields}, nextIndex) => {
			const isNotRepeated =
				!errorIndexes.includes(index) ||
				!errorIndexes.includes(nextIndex);

			if (isNotRepeated && index !== nextIndex) {
				const duplicated = fields.filter((field) =>
					nextFields.includes(field)
				);

				if (duplicated.length) {
					duplicatedFields.push(
						...duplicated.filter(
							(field) => !duplicatedFields.includes(field)
						)
					);
					errorIndexes.push(
						...[index, nextIndex].filter(
							(errorIndex) => !errorIndexes.includes(errorIndex)
						)
					);
				}
			}
		});
	});

	return {duplicatedFields, errorIndexes};
}
