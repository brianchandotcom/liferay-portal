/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests, test} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {EditProjectPage} from '../pages/EditProjectPage';
import {EditTaskPage} from '../pages/EditTaskPage';
import {ProjectPage} from '../pages/ProjectPage';
import {ProjectsPage} from '../pages/ProjectsPage';
import {TasksPage} from '../pages/TasksPage';

const cmpPages = test.extend<{
	cmpSetup;
	editProjectPage: EditProjectPage;
	editTaskPage: EditTaskPage;
	projectPage: ProjectPage;
	projectsPage: ProjectsPage;
	tasksPage: TasksPage;
}>({
	cmpSetup: [
		async ({page}, use) => {
			const apiHelpers = new ApiHelpers(page);

			await apiHelpers.featureFlag.updateFeatureFlag('LPD-58677', true);

			const space =
				await apiHelpers.headlessAssetLibrary.createAssetLibrary({
					name: `Space ${getRandomString()}`,
					settings: {},
					type: 'Space',
				});

			await test.step('Create and delete CMP project to ensure that setup is ready', async () => {
				const projectsPage = new ProjectsPage(page);

				await projectsPage.goto();

				await projectsPage.newButton.click();

				const editProjectPage = new EditProjectPage(page);

				const projectTitle = 'Project ' + getRandomString();

				await editProjectPage.titleInput.fill(projectTitle);

				await editProjectPage.saveButton.click();

				await expect(
					page.getByRole('link', {name: projectTitle})
				).toBeVisible();

				const projectPage = new ProjectPage(page);

				await projectPage.moreActionsButton.click();

				await projectPage.getBreadcrumbAction('Delete').click();

				await projectPage.deleteButton.click();
			});

			await use();

			await apiHelpers.headlessAssetLibrary.deleteAssetLibrary(
				space.externalReferenceCode
			);

			await apiHelpers.featureFlag.updateFeatureFlag('LPD-58677', false);
		},
		{auto: true},
	],
	editProjectPage: async ({page}, use) => {
		await use(new EditTaskPage(page));
	},
	editTaskPage: async ({page}, use) => {
		await use(new EditTaskPage(page));
	},
	projectPage: async ({page}, use) => {
		await use(new ProjectPage(page));
	},
	projectsPage: async ({page}, use) => {
		await use(new ProjectsPage(page));
	},
	tasksPage: async ({page}, use) => {
		await use(new TasksPage(page));
	},
});

const cmpPagesTest = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-17564': {enabled: true},
	}),
	cmpPages
);

export {cmpPagesTest};
