/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {faroConfig} from './faro.config';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

test(
	'Workspace settings cancel reverts the unsaved workspace name change',
	{
		tag: '@LRAC-9134',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/workspace`
		);

		const nameField = page.getByLabel('Workspace Name');

		const originalName = await nameField.inputValue();

		await nameField.fill('UAT ' + Date.now());

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(nameField).toHaveValue(originalName);
	}
);
