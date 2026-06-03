/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import createUserWithPermissions from '../../../utils/createUserWithPermissions';
import getRandomString from '../../../utils/getRandomString';
import {performLogout, performUserSwitch} from '../../../utils/performLogin';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest);

test(
	'This is a test for LPS-129203. An anonymous user is redirected to sign in when asking a question.',
	{tag: '@LPS-129203'},
	async ({
		apiHelpers,
		page,
		questionsConfigurationPage,
		questionsTopicsPage,
		site,
		widgetPagePage,
	}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic with guest view permissions through the API

		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);
		const guestRole =
			await apiHelpers.headlessAdminUser.getRoleByName('Guest');

		await apiHelpers.jsonWebServicesResourcePermissionApiHelper.setIndividualResourcePermissions(
			['VIEW'],
			company.companyId,
			site.id,
			'com.liferay.message.boards.model.MBCategory',
			messageBoardSection.id,
			String(guestRole.id)
		);

		// Allow anonymous users to read questions and redirect them to sign in

		await questionsConfigurationPage.setEnableAnonymousRead(true);
		await questionsConfigurationPage.setEnableRedirectToLogin(true);

		try {

			// Ask a question as an anonymous user

			await performLogout(page);

			await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

			await questionsTopicsPage.goToTopic(topicName);

			await page.getByRole('button', {name: 'Ask Question'}).click();

			// The anonymous user is redirected to sign in

			await page.getByLabel('Email Address').fill('test@liferay.com');
			await page.getByLabel('Password').fill('test');

			await page.getByRole('button', {name: 'Sign In'}).last().click();

			// The question can be posted after signing in

			const questionTitle = getRandomString();

			await page
				.getByPlaceholder('What is your question?')
				.fill(questionTitle);
			await page.getByLabel('Source').click();
			await page
				.getByLabel(/Rich Text Editor/)
				.getByRole('textbox')
				.fill(getRandomString());
			await page.getByLabel('Source').click();
			await page.getByLabel('Post Your Question').click();

			await expect(
				page.getByRole('link', {name: questionTitle})
			).toBeVisible();
		}
		finally {
			await questionsConfigurationPage.setEnableAnonymousRead(false);
			await questionsConfigurationPage.setEnableRedirectToLogin(false);
		}
	}
);

test(
	'This is a test for LPS-116516, LPS-118481 and LPS-128823. A user without permissions cannot add topics or ask questions.',
	{tag: ['@LPS-116516', '@LPS-118481', '@LPS-128823']},
	async ({apiHelpers, page, questionsTopicsPage, site, widgetPagePage}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic through the API

		const topicName = getRandomString().replace(/-/g, '');

		await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
			siteId: site.id,
			title: topicName,
		});

		// Create a user with view permissions only

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName:
						'com_liferay_message_boards_web_portlet_MBAdminPortlet',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.message.boards',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.message.boards.model.MBCategory',
					scope: 1,
				},
			],
		});

		await performUserSwitch(page, user.alternateName);

		// The user cannot add topics or ask questions

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);

		await expect(page.getByRole('link', {name: topicName})).toBeVisible();
		await expect(page.getByText('New Topic', {exact: true})).toBeHidden();

		await questionsTopicsPage.goToTopic(topicName);

		await expect(page.getByText('This topic is empty.')).toBeVisible();
		await expect(
			page.getByRole('button', {name: 'Ask Question'})
		).toBeHidden();
	}
);
