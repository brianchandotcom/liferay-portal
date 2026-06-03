/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {questionsTest} from './fixtures/questionsTest';

const test = mergeTests(questionsTest, workflowPagesTest);

test(
	'This is a test for LPS-130115 and LPS-130705. A question under moderation shows as pending and can be approved.',
	{tag: ['@LPS-130115', '@LPS-130705']},
	async ({
		apiHelpers,
		page,
		questionsTopicsPage,
		site,
		widgetPagePage,
		workflowPage,
	}) => {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		// Enable moderation for message boards messages in the site

		await workflowPage.goto(site.friendlyUrlPath);
		await workflowPage.changeWorkflow(
			'Message Boards Message',
			'Single Approver'
		);

		await page.goto('/web' + site.friendlyUrlPath + layout.friendlyURL);
		await widgetPagePage.addPortlet('Questions');

		// Seed a topic and a question through the API

		const topicName = getRandomString().replace(/-/g, '');

		const messageBoardSection =
			await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
				siteId: site.id,
				title: topicName,
			});

		const headline = getRandomString();

		const messageBoardThread =
			await apiHelpers.headlessDelivery.postMessageBoardSectionMessageBoardThread(
				{
					articleBody: getRandomString(),
					headline,
					messageBoardSectionId: messageBoardSection.id,
				}
			);

		expect(messageBoardThread.status).toBe('pending');

		// The question shows as pending

		await page.reload();

		await questionsTopicsPage.goToTopic(topicName);

		await expect(
			page.getByRole('link', {name: headline}).getByText('pending')
		).toBeVisible();

		// Approve the question through the workflow task API

		const userAccount =
			await apiHelpers.headlessAdminUser.getMyUserAccount();

		let workflowTask;

		await expect(async () => {
			const workflowTasksPage =
				await apiHelpers.headlessAdminWorkflow.getWorkflowTasksBySubmittingUser(
					userAccount.id
				);

			[workflowTask] = workflowTasksPage.items.filter(
				(item) => item.objectReviewed?.assetTitle === headline
			);

			expect(workflowTask).toBeTruthy();
		}).toPass();

		await apiHelpers.headlessAdminWorkflow.postAssignTaskToUser(
			workflowTask.id,
			userAccount.id
		);

		await apiHelpers.headlessAdminWorkflow.postWorkflowTaskChangeTransition(
			workflowTask.id,
			'approve'
		);

		await expect(async () => {
			const approvedMessageBoardThread =
				await apiHelpers.headlessDelivery.getMessageBoardThread(
					messageBoardThread.id
				);

			expect(approvedMessageBoardThread.status).toBe('approved');
		}).toPass();

		// The pending label is gone and an answer can be added

		await page.reload();

		await expect(page.getByRole('link', {name: headline})).toBeVisible();
		await expect(
			page.getByRole('link', {name: headline}).getByText('pending')
		).toBeHidden();

		const articleBody = getRandomString();

		const messageBoardMessage =
			await apiHelpers.headlessDelivery.postMessageBoardMessage({
				articleBody,
				messageBoardThreadId: messageBoardThread.id,
			});

		expect(messageBoardMessage.articleBody).toContain(articleBody);
	}
);
