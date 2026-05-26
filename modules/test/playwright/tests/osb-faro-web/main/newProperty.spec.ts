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
import {faroConfig} from './faro.config';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';

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
