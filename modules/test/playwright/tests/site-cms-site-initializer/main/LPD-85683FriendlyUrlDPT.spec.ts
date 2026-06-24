/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {fragmentsPagesTest} from '../../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {performLoginViaApi, userData} from '../../../utils/performLogin';

/**
 * LPD-85683 TC-1.a — A published Basic Web Content must be reachable at its
 * Display Page Template friendly URL, both as GUEST (not logged in) and as USER
 * (a logged-in regular site member).
 *
 * RECORDED FAILURE (candidate product bug — see RESULTS.md / BUG A):
 * Even with the full prerequisite setup in place — a published Basic Web Content
 * with a friendly URL, an active (default) "Basic Web Content" Display Page
 * Template, and the Space connected to the site — the public display URL returns
 * 404. This was confirmed manually against the running portal for every URL
 * form (separator-first, group-first, /web/<site>/..., root, and the
 * default-locale UUID slug). The faithful spec is kept and left failing per the
 * skill's honesty rule; do not water it down to make it pass.
 */

const test = mergeTests(
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-70672': {enabled: true},
		'LPD-83570': {enabled: true},
	}),
	fragmentsPagesTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

const APPLICATION_NAME = 'cms/basic-web-contents';

test(
	'[TC-1.a] A published Basic Web Content is reachable at its DPT friendly URL for GUEST and USER',
	{tag: '@LPD-85683'},
	async ({
		apiHelpers,
		browser,
		displayPageTemplatesPage,
		page,
		pageEditorPage,
		site,
	}) => {
		const dptName = `DPT ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;
		const contentTitle = `Content ${getRandomString()}`;
		const friendlyUrlPath = `tc1a-${getRandomString()}`.toLowerCase();

		// Create a Display Page Template for Basic Web Content, map its Title,
		// publish it, and mark it as the active default for the content type

		await test.step('Create and activate a Basic Web Content DPT', async () => {
			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.createTemplate({
				contentType: 'Basic Web Content',
				name: dptName,
			});

			await displayPageTemplatesPage.editTemplate(dptName);

			await pageEditorPage.addFragment('Basic Components', 'Heading');

			const headingId = await pageEditorPage.getFragmentId('Heading');

			await pageEditorPage.selectEditable(headingId, 'element-text');

			await page
				.getByRole('combobox', {exact: true, name: 'Field'})
				.selectOption('Title');

			await pageEditorPage.waitForChangesSaved();

			await pageEditorPage.publishPage();

			await displayPageTemplatesPage.goto(site.friendlyUrlPath);

			await displayPageTemplatesPage.markAsDefault(dptName);
		});

		// Create a Space, connect it to the site, and add a published Basic Web
		// Content with a friendly URL (connection done via the API — setup, not
		// the behavior under test)

		const space = await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			type: 'Space',
		});

		await apiHelpers.headlessAssetLibrary.connectSite(
			space.externalReferenceCode,
			site.externalReferenceCode
		);

		const entry = await apiHelpers.objectEntry.postObjectEntry(
			{
				content: `<p>Body ${getRandomString()}</p>`,
				friendlyUrlPath,
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentTitle,
			},
			APPLICATION_NAME,
			spaceName
		);

		// Grant VIEW on the published entry to both the Guest and the User
		// role, as the use case directs ("set the permission VIEW to GUEST" /
		// "to USER"). CMS content is a depot (asset-library) scoped object
		// entry, so the individual resource permission is set in the Space's
		// depot group (space.siteId) on the object definition's runtime class
		// name. Guest VIEW covers the anonymous GUEST; signed-in users do not
		// inherit Guest-role permissions, so the User role needs its own grant
		// for the logged-in USER to see the asset.

		const objectDefinition =
			await apiHelpers.objectAdmin.getObjectDefinitionByName(
				'CMSBasicWebContent'
			);

		const companyId = String(
			await page.evaluate(() => Liferay.ThemeDisplay.getCompanyId())
		);

		for (const roleName of ['Guest', 'User']) {
			const role = await apiHelpers.jsonWebServicesRole.getRole(
				companyId,
				roleName
			);

			await apiHelpers.jsonWebServicesResourcePermissionApiHelper.setIndividualResourcePermissions(
				['VIEW'],
				companyId,
				String(space.siteId),
				objectDefinition.className,
				String(entry.id),
				String(role.roleId)
			);
		}

		// The active DPT must render the published item at its friendly URL
		// (object-definition separator + asset-library numeric id + friendly URL)

		const displayUrl = `/cmsbasicwebcontent/asset-library-${space.id}/${friendlyUrlPath}`;

		// GUEST: access the friendly URL without logging in (a fresh, cookieless
		// browser context) and verify the URL resolves and the content renders

		const guestContext = await browser.newContext();

		const guestPage = await guestContext.newPage();

		try {
			await expect(async () => {
				await guestPage.goto(displayUrl);

				await expect(
					guestPage.getByText(contentTitle, {exact: true})
				).toBeVisible({timeout: 10000});
			}).toPass({timeout: 30000});
		}
		finally {
			await guestContext.close();
		}

		// USER: create a regular authenticated site member, log in as that user
		// in a fresh browser context (mirroring the GUEST check, which avoids
		// the fragile in-place identity switch on the admin page), and verify
		// the same friendly URL resolves and renders the content while logged in.

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.jsonWebServicesUser.agreeToTermsOfUse(user.id);
		await apiHelpers.jsonWebServicesUser.answerReminderQuery(user.id);

		await apiHelpers.jsonWebServicesUser.addGroupUsers(String(site.id), [
			user.id,
		]);

		const userContext = await browser.newContext();

		const userPage = await userContext.newPage();

		try {
			await performLoginViaApi({
				page: userPage,
				screenName: user.alternateName,
			});

			await expect(async () => {
				await userPage.goto(displayUrl);

				await expect(
					userPage.getByText(contentTitle, {exact: true})
				).toBeVisible({timeout: 10000});
			}).toPass({timeout: 30000});
		}
		finally {
			await userContext.close();
		}
	}
);
