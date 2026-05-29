/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getHeader} from '../../../helpers/ApiHelpers';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {faroConfig} from './faro.config';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';
import {searchByTerm, viewNameOnTableList} from './utils/utils';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

async function signInToAnalyticsCloud(page, login: string) {

	// Logout any existing session so the new login fully replaces it.

	await page.goto(`${faroConfig.environment.baseUrl}/c/portal/logout`);

	const response = await page.request.post(
		`${faroConfig.environment.baseUrl}/c/portal/login`,
		{
			data: new URLSearchParams({
				login,
				password: faroConfig.user.password,
			}).toString(),
			headers: await getHeader(page, 'application/x-www-form-urlencoded'),
		}
	);

	expect(response.status()).toBe(200);

	// Land on the workspace so the SPA picks up the new session.

	await page.goto(faroConfig.environment.baseUrl);

	await expect(
		page.getByRole('textbox', {name: 'Email Address'})
	).toHaveCount(0);
}

test(
	'A user only sees the properties they are invited to and loses access when one is deleted',
	{
		tag: ['@LRAC-8322', '@LRAC-8323'],
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		const corbinUser = await apiHelpers.jsonWebServicesOSBFaro.getUser(
			project.groupId,
			'corbin.murakami@faro.io'
		);
		const bryanUser = await apiHelpers.jsonWebServicesOSBFaro.getUser(
			project.groupId,
			'bryan.cheung@faro.io'
		);

		// Create a property restricted to corbin and another restricted to bryan

		const corbinProperty =
			await apiHelpers.jsonWebServicesOSBFaro.createChannel(
				'Corbin Property',
				project.groupId
			);
		const bryanProperty =
			await apiHelpers.jsonWebServicesOSBFaro.createChannel(
				'Bryan Property',
				project.groupId
			);

		const channelsToCleanup = [corbinProperty.id, bryanProperty.id];

		try {
			await apiHelpers.jsonWebServicesOSBFaro.updateChannelPermission(
				corbinProperty.id,
				project.groupId,
				1
			);
			await apiHelpers.jsonWebServicesOSBFaro.addChannelUsers(
				corbinProperty.id,
				project.groupId,
				[corbinUser.userId]
			);

			await apiHelpers.jsonWebServicesOSBFaro.updateChannelPermission(
				bryanProperty.id,
				project.groupId,
				1
			);
			await apiHelpers.jsonWebServicesOSBFaro.addChannelUsers(
				bryanProperty.id,
				project.groupId,
				[bryanUser.userId]
			);

			// Sign in as corbin and assert only the corbin property is listed

			await signInToAnalyticsCloud(page, 'corbin.murakami@faro.io');

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('link', {exact: true, name: 'Corbin Property'})
			).toBeVisible();
			await expect(
				page.getByRole('link', {exact: true, name: 'Bryan Property'})
			).toHaveCount(0);

			// Admin deletes the corbin property

			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${corbinProperty.id}]`,
				project.groupId
			);

			channelsToCleanup.splice(
				channelsToCleanup.indexOf(corbinProperty.id),
				1
			);

			// Sign back in as corbin and assert the property is no longer listed

			await signInToAnalyticsCloud(page, 'corbin.murakami@faro.io');

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('link', {exact: true, name: 'Corbin Property'})
			).toHaveCount(0);
		}
		finally {

			// Restore the admin session and remove the created properties

			await signInToAnalyticsCloud(page, faroConfig.user.login);

			for (const id of channelsToCleanup) {
				await apiHelpers.jsonWebServicesOSBFaro
					.deleteChannel(`[${id}]`, project.groupId)
					.catch(() => {});
			}
		}
	}
);

test(
	'Cannot create a property with its name in blank or null',
	{
		tag: '@LRAC-9126',
	},
	async ({apiHelpers, page}) => {

		// Go to the AC properties settings page

		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await navigateToACSettingsViaURL({
			acPage: ACPage.propertiesPage,
			page,
			projectID: project.groupId,
		});

		// Open the new property dialog and leave the name empty

		const nameInput = page.getByLabel('Property Name');

		await clickAndExpectToBeVisible({
			target: nameInput,
			trigger: page.getByRole('button', {name: 'New Property'}),
		});

		await nameInput.fill('x');
		await nameInput.clear();

		// Assert that the save button is disabled

		await expect(page.getByRole('button', {name: 'Save'})).toBeDisabled();
	}
);

test(
	'New Property dialog accepts a name with special characters and enables Save',
	{
		tag: '@LRAC-9105',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await navigateToACSettingsViaURL({
			acPage: ACPage.propertiesPage,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			target: page.getByLabel('Property Name'),
			trigger: page.getByRole('button', {name: 'New Property'}),
		});

		await page
			.getByLabel('Property Name')
			.fill(`Special ${getRandomString()} = @#$%&!`);

		await expect(page.getByRole('button', {name: 'Save'})).toBeEnabled();

		await page.getByRole('button', {name: 'Cancel'}).click();
	}
);

test(
	'Saving a New Property with an existing name appends an incrementing identifier',
	{
		tag: '@LRAC-9037',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		const propertyName = 'Duplicate Property ' + getRandomString();

		const existing = await apiHelpers.jsonWebServicesOSBFaro.createChannel(
			propertyName,
			project.groupId
		);

		try {
			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await clickAndExpectToBeVisible({
				target: page.getByLabel('Property Name'),
				trigger: page.getByRole('button', {name: 'New Property'}),
			});

			await page.getByLabel('Property Name').fill(propertyName);

			await page.getByRole('button', {name: 'Save'}).click();

			// Saving redirects to the new property detail page; navigate back to the list.

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await searchByTerm({page, searchTerm: propertyName});

			await expect(
				page.getByRole('link', {exact: true, name: propertyName})
			).toBeVisible();

			await expect(
				page.getByRole('link', {
					exact: true,
					name: `${propertyName} (1)`,
				})
			).toBeVisible();
		}
		finally {
			await apiHelpers.jsonWebServicesOSBFaro
				.deleteChannel(`[${existing.id}]`, project.groupId)
				.catch(() => {});
		}
	}
);

test(
	'New Property dialog enforces the 3-64 character length boundary',
	{
		tag: ['@LRAC-9102', '@LRAC-9106'],
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await navigateToACSettingsViaURL({
			acPage: ACPage.propertiesPage,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			target: page.getByLabel('Property Name'),
			trigger: page.getByRole('button', {name: 'New Property'}),
		});

		const nameInput = page.getByLabel('Property Name');

		const saveButton = page.getByRole('button', {name: 'Save'});

		// Below the 3-character minimum: Save is disabled.

		await nameInput.fill('Mi');

		await expect(saveButton).toBeDisabled();

		// At the 3-character minimum: Save is enabled.

		await nameInput.fill('Min');

		await expect(saveButton).toBeEnabled();

		// At the 64-character maximum: Save is enabled.

		await nameInput.fill('M'.repeat(64));

		await expect(saveButton).toBeEnabled();
	}
);

test(
	'New Property dialog disables Save and shows an alert when the name exceeds the maximum length',
	{
		tag: '@LRAC-9038',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await navigateToACSettingsViaURL({
			acPage: ACPage.propertiesPage,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			target: page.getByLabel('Property Name'),
			trigger: page.getByRole('button', {name: 'New Property'}),
		});

		const nameInput = page.getByLabel('Property Name');

		await nameInput.fill(
			'uZcHpcFx3abyBF4MtqVQFsbt9lrF7lV5A9xC7tujzqicCSHoscXb0sJV6q2alW7cli'
		);

		await nameInput.press('Tab');

		await expect(page.getByText('Exceeds maximum length.')).toBeVisible();

		await expect(page.getByRole('button', {name: 'Save'})).toBeDisabled();
	}
);

test(
	'Cancelling the New Property dialog via Cancel or Close does not create a property',
	{
		tag: '@LRAC-9107',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await navigateToACSettingsViaURL({
			acPage: ACPage.propertiesPage,
			page,
			projectID: project.groupId,
		});

		for (const dismissAction of ['Cancel', 'Close']) {
			const propertyName = `Cancelled ${dismissAction} ${getRandomString()}`;

			await clickAndExpectToBeVisible({
				target: page.getByLabel('Property Name'),
				trigger: page.getByRole('button', {name: 'New Property'}),
			});

			await page.getByLabel('Property Name').fill(propertyName);

			await page.getByRole('button', {name: dismissAction}).click();

			await searchByTerm({page, searchTerm: propertyName});

			await expect(
				page.getByRole('link', {exact: true, name: propertyName})
			).toHaveCount(0);
		}
	}
);

test(
	'Properties settings page search filters the list to matching properties',
	{
		tag: '@LRAC-9121',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		const runId = getRandomString();

		const propertyNames = Array.from(
			{length: 4},
			(_, index) => `Search Property ${runId} ${index + 1}`
		);

		const channelIds: string[] = [];

		try {
			for (const propertyName of propertyNames) {
				const channel =
					await apiHelpers.jsonWebServicesOSBFaro.createChannel(
						propertyName,
						project.groupId
					);

				channelIds.push(channel.id);
			}

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			for (const propertyName of propertyNames) {
				await searchByTerm({page, searchTerm: propertyName});

				await viewNameOnTableList({itemNames: propertyName, page});
			}
		}
		finally {
			for (const id of channelIds) {
				await apiHelpers.jsonWebServicesOSBFaro
					.deleteChannel(`[${id}]`, project.groupId)
					.catch(() => {});
			}
		}
	}
);

test(
	'Deleting a property requires the matching confirmation phrase and shows a success alert',
	{
		tag: ['@LRAC-14648', '@LRAC-14649'],
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		const propertyName = 'Delete Property ' + getRandomString();

		let channelId: string | null = null;

		try {
			const channel =
				await apiHelpers.jsonWebServicesOSBFaro.createChannel(
					propertyName,
					project.groupId
				);

			channelId = channel.id;

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await searchByTerm({page, searchTerm: propertyName});

			// Open the row kebab and click Delete

			await page
				.getByRole('row', {name: new RegExp(propertyName)})
				.getByRole('button', {name: 'Menu'})
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			const deleteDialog = page.getByRole('dialog');

			const confirmationInput = deleteDialog.getByRole('textbox');

			const deleteButton = deleteDialog.getByRole('button', {
				exact: true,
				name: 'Delete',
			});

			// Wrong value shows the validation warning after blur

			await confirmationInput.fill(propertyName);

			await confirmationInput.press('Tab');

			await expect(
				page.getByText('String does not match.')
			).toBeVisible();

			// Matching confirmation phrase clears the warning

			await confirmationInput.fill(`Delete ${propertyName}`);

			await expect(page.getByText('String does not match.')).toHaveCount(
				0
			);

			await deleteButton.click();

			await expect(
				page.getByText('Success:1 property has been deleted')
			).toBeVisible();

			channelId = null;

			await expect(
				page.getByRole('link', {exact: true, name: propertyName})
			).toHaveCount(0);
		}
		finally {
			if (channelId) {
				await apiHelpers.jsonWebServicesOSBFaro
					.deleteChannel(`[${channelId}]`, project.groupId)
					.catch(() => {});
			}
		}
	}
);
