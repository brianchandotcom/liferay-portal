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

import {isEqualObjects} from 'app-builder-web/js/utils/utils.es';

export function canDeployApp(app, config) {
	const isValidSteps = config.steps.every((step) => {
		const assigneeRoles = step?.appWorkflowRoleAssignments || [{}];
		const duplicatedFields =
			step?.errors?.formViews?.duplicatedFields || [];

		return (
			assigneeRoles.length > 0 &&
			duplicatedFields.length === 0 &&
			step.name.trim().length > 0
		);
	});

	return (
		app.dataDefinitionId &&
		app.dataLayoutId &&
		app.dataListViewId &&
		app.appName?.trim().length > 0 &&
		isValidSteps
	);
}

export function getFormViewFields({dataLayoutPages = []}) {
	return dataLayoutPages.reduce(
		(fields, {dataLayoutRows}) => [
			...fields,
			...dataLayoutRows.reduce(
				(fields, {dataLayoutColumns}) => [
					...fields,
					...dataLayoutColumns.reduce(
						(fields, {fieldNames}) => [...fields, ...fieldNames],
						[]
					),
				],
				[]
			),
		],
		[]
	);
}

export function hasConfigBreakChanges({draftConfig, ...config}) {
	const props = ['dataObject', 'formView', 'steps'];

	return props
		.map((prop) => !isEqualObjects(draftConfig[prop], config[prop]))
		.some((isDifferent) => isDifferent);
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
