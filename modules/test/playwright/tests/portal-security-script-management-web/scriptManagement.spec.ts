/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {scriptManagementPagesTest} from '../../fixtures/scriptManagementPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-11179': true,
	}),
	scriptManagementPagesTest
);

const createdEntities = {
	objectDefinitionsIds: [],
};

test.afterEach(async ({apiHelpers, scriptManagementPage}) => {
	if (createdEntities.objectDefinitionsIds.length) {
		for (const id of createdEntities.objectDefinitionsIds) {
			await apiHelpers.objectAdmin.deleteObjectDefinition(id);
		}
	}
	await scriptManagementPage.enableScriptManagementConfiguration();
});

test.describe('Script management container', () => {
	test('can disable and save the configuration', async ({
		scriptManagementPage,
	}) => {
		await scriptManagementPage.disableScriptManagementConfiguration();
	});

	test('cannot save the configuration with a active Object Action with Groovy Script', async ({
		apiHelpers,
		scriptManagementPage,
	}) => {
		const objectDefinition =
			(await apiHelpers.objectAdmin.postRandomObjectDefinition(
				'default'
			)) as ObjectDefinition;

		createdEntities.objectDefinitionsIds.push(objectDefinition.id);

		const groovyObjectActionName = 'groovyObjectAction' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectActionByExternalReferenceCode(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				errorMessage: {
					en_US: '',
				},
				label: {
					en_US: groovyObjectActionName,
				},
				name: groovyObjectActionName,
				objectActionExecutorKey: 'groovy',
				objectActionTriggerKey: 'onAfterAdd',
				parameters: {
					lineCount: 1,
					script: 'test',
				},
				system: false,
			}
		);

		await scriptManagementPage.disableScriptManagementConfiguration(false);

		await expect(
			scriptManagementPage.getActiveGroovyItemLocator(
				groovyObjectActionName
			)
		).toBeVisible();

		await scriptManagementPage.doneButton.click();

		await expect(scriptManagementPage.allowScriptCheckbox).toBeChecked();
	});

	test('cannot save the configuration with a active Object Validation with Groovy Script', async ({
		apiHelpers,
		scriptManagementPage,
	}) => {
		const objectDefinition =
			(await apiHelpers.objectAdmin.postRandomObjectDefinition(
				'default'
			)) as ObjectDefinition;

		createdEntities.objectDefinitionsIds.push(objectDefinition.id);

		const objectValidationName =
			'Groovy Object Validation' + getRandomInt();

		await apiHelpers.objectAdmin.postObjectValidation(
			objectDefinition.externalReferenceCode,
			{
				active: true,
				engine: 'groovy',
				engineLabel: 'Groovy',
				errorLabel: {
					en_US: 'Groovy Object Validation Error',
				},
				name: {
					en_US: objectValidationName,
				},
				objectValidationRuleSettings: [],
				outputType: 'fullValidation',
				script: 'test',
				system: false,
			}
		);

		await scriptManagementPage.disableScriptManagementConfiguration(false);

		await expect(
			scriptManagementPage.getActiveGroovyItemLocator(
				objectValidationName
			)
		).toBeVisible();

		await scriptManagementPage.doneButton.click();

		await expect(scriptManagementPage.allowScriptCheckbox).toBeChecked();
	});
});
