/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloudViaAPI} from '../../analytics-settings-web/main/utils/analytics-settings';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-76864': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest(),
	pageViewModePagesTest
);

test(
	'Comment posted events fire with the expected file entry properties when commenting and replying on a document',
	{
		tag: ['@LRAC-8803', '@LRAC-8867'],
	},
	async ({
		analyticsChannel: channel,
		apiHelpers,
		page,
		project,
		site,
		widgetPagePage,
	}) => {
		const documentTitle = 'AC Document ' + getRandomString();

		const document = await apiHelpers.headlessDelivery.postDocument(
			site.id,
			createReadStream(
				path.join(__dirname, '/dependencies/Document.jpg')
			),
			{
				fileName: 'Document.jpg',
				title: documentTitle,
			}
		);

		// Use a widget page with the Documents and Media portlet

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {type: 'portlet'},
			title: getRandomString(),
		});

		await widgetPagePage.goto(layout, site.friendlyUrlPath);

		await widgetPagePage.addPortlet('Documents and Media');

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		const capturedEvents = captureAnalyticsEvents(page);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// Open the document detail page, which renders the comments section

		await page
			.getByRole('link', {exact: true, name: documentTitle})
			.first()
			.click();

		// Post a comment on the document

		const commentBody = 'This is for test';

		await page
			.locator('.add-comment')
			.frameLocator('iframe')
			.getByRole('textbox')
			.fill(commentBody);

		const postCommentButton = page
			.locator('.add-comment')
			.getByRole('button', {name: 'Reply'});

		await expect(postCommentButton).toBeEnabled();

		await postCommentButton.click();

		await expect(page.getByText(commentBody)).toBeVisible();

		// The comment fires a posted event for the document file entry

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) =>
						event.eventId === 'posted' &&
						String(event.properties.text).includes(commentBody)
				)
			)
			.toBeTruthy();

		const commentPosted = capturedEvents.find(
			(event) =>
				event.eventId === 'posted' &&
				String(event.properties.text).includes(commentBody)
		);

		expect(commentPosted.applicationId).toBe('Comment');
		expect(commentPosted.properties.className).toBe(
			'com.liferay.document.library.kernel.model.DLFileEntry'
		);
		expect(commentPosted.properties.classPK).toBe(String(document.id));
		expect(commentPosted.properties.commentId).toBeTruthy();
		expect(commentPosted.properties.text).toContain(
			`<p>${commentBody}</p>`
		);
		expect(commentPosted.channelId).toBe(channel.id);
		expect(commentPosted.dataSourceId).toBeTruthy();

		// Reply to the comment

		await page
			.locator('button[onclick*="showPostReplyEditor"]')
			.first()
			.click();

		const replyBody = 'This is a reply comment';

		const replyForm = page.locator('[id*="postReplyForm"]:visible');

		await replyForm
			.frameLocator('iframe')
			.getByRole('textbox')
			.fill(replyBody);

		const postReplyButton = replyForm.getByRole('button', {name: 'Reply'});

		await expect(postReplyButton).toBeEnabled();

		await postReplyButton.click();

		await expect(page.getByText(replyBody)).toBeVisible();

		// The reply fires a posted event for the same document file entry

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) =>
						event.eventId === 'posted' &&
						String(event.properties.text).includes(replyBody)
				)
			)
			.toBeTruthy();

		const replyPosted = capturedEvents.find(
			(event) =>
				event.eventId === 'posted' &&
				String(event.properties.text).includes(replyBody)
		);

		expect(replyPosted.applicationId).toBe('Comment');
		expect(replyPosted.properties.className).toBe(
			'com.liferay.document.library.kernel.model.DLFileEntry'
		);
		expect(replyPosted.properties.classPK).toBe(String(document.id));
		expect(replyPosted.properties.commentId).toBeTruthy();
		expect(replyPosted.properties.text).toContain(`<p>${replyBody}</p>`);
		expect(replyPosted.channelId).toBe(channel.id);
		expect(replyPosted.dataSourceId).toBeTruthy();
	}
);
