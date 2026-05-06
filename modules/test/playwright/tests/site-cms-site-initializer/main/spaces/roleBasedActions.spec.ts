/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {
	performUserSwitchViaApi,
	userData,
} from '../../../../utils/performLogin';
import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {cmsPagesTest} from '../fixtures/cmsPagesTest';
import {SpaceSummaryPage} from '../pages/SpaceSummaryPage';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest()
);

type SpaceRole = 'Space Administrator' | 'Space Content Reviewer' | null;

async function addRoleMemberAndSwitch({
	apiHelpers,
	page,
	role,
	spaceName,
	spaceSummaryPage,
}: {
	apiHelpers: DataApiHelpers;
	page: Page;
	role: SpaceRole;
	spaceName: string;
	spaceSummaryPage: SpaceSummaryPage;
}) {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();
	const userFullName = `${user.givenName} ${user.familyName}`;

	registerUserCredentials(user);

	await spaceSummaryPage.goto(spaceName);
	await spaceSummaryPage.addUserOrUserGroup(userFullName, 'users');

	if (role) {
		await spaceSummaryPage.addRoleToSpaceMember(role, userFullName);
	}

	await performUserSwitchViaApi(page, user.alternateName);
	await spaceSummaryPage.goto(spaceName);

	return {user, userFullName};
}

function createSpace(apiHelpers, spaceName) {
	return apiHelpers.headlessAssetLibrary.createAssetLibrary({
		name: spaceName,
		settings: {},
		type: 'Space',
	});
}

function registerUserCredentials(user) {
	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};
}

test(
	'A Space Member sees a restricted set of actions in the All Spaces view',
	{tag: '@LPD-85670'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = `Space ${getRandomString()}`;

		await createSpace(apiHelpers, spaceName);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: null,
			spaceName,
			spaceSummaryPage,
		});

		await page.goto(PORTLET_URLS.cmsAllSpaces);

		const spaceRow = page.getByRole('row', {name: spaceName});

		await spaceRow.getByRole('button', {name: 'Actions'}).click();

		await expect(
			page.getByRole('menuitem', {name: 'View Members'})
		).toBeVisible();
		await expect(
			page.getByRole('menuitem', {name: 'View Connected Sites'})
		).toBeVisible();

		await expect(
			page.getByRole('menuitem', {name: 'Delete'})
		).not.toBeVisible();
		await expect(
			page.getByRole('menuitem', {name: 'Permissions'})
		).not.toBeVisible();
		await expect(
			page.getByRole('menuitem', {name: 'Settings'})
		).not.toBeVisible();
	}
);

test(
	'A Space Member cannot see the Add Content button in the space summary page',
	{tag: '@LPD-85670'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = `Space ${getRandomString()}`;

		await createSpace(apiHelpers, spaceName);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: null,
			spaceName,
			spaceSummaryPage,
		});

		await expect(spaceSummaryPage.addContentButton).not.toBeVisible();
		await expect(spaceSummaryPage.addFileButton).not.toBeVisible();
	}
);

test(
	'A Content Reviewer can create content in a space',
	{tag: '@LPD-85670'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = `Space ${getRandomString()}`;

		await createSpace(apiHelpers, spaceName);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: 'Space Content Reviewer',
			spaceName,
			spaceSummaryPage,
		});

		await expect(spaceSummaryPage.addContentButton).toBeVisible();

		const folderName = `Folder ${getRandomString()}`;

		await spaceSummaryPage.createContentFolder(folderName);

		await expect(page.getByRole('link', {name: folderName})).toBeVisible();
	}
);

test(
	'A Content Reviewer can edit content in a space',
	{tag: '@LPD-85670'},
	async ({apiHelpers, assetsPage, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentTitle = `Title ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		const space = await createSpace(apiHelpers, spaceName);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentTitle,
			},
			applicationName,
			space.name
		);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: 'Space Content Reviewer',
			spaceName,
			spaceSummaryPage,
		});

		await spaceSummaryPage.viewAllContentLink.click();

		await assetsPage.execItemAction({
			action: 'Edit',
			filter: contentTitle,
		});

		await expect(page.getByLabel('Title')).toBeEditable();
	}
);

test(
	'A Content Reviewer can delete content in a space',
	{tag: '@LPD-85670'},
	async ({apiHelpers, assetsPage, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-web-contents';
		const contentTitle = `Title ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		const space = await createSpace(apiHelpers, spaceName);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: contentTitle,
			},
			applicationName,
			space.name
		);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: 'Space Content Reviewer',
			spaceName,
			spaceSummaryPage,
		});

		await spaceSummaryPage.viewAllContentLink.click();

		await assetsPage.execItemAction({
			action: 'Delete',
			filter: contentTitle,
		});

		await waitForAlert(page, `${contentTitle} was moved`);

		await expect(page.getByText(contentTitle)).not.toBeVisible();
	}
);

test(
	'A Content Reviewer can delete a file in a space',
	{tag: '@LPD-85670'},
	async ({apiHelpers, assetsPage, page, spaceSummaryPage}) => {
		const applicationName = 'cms/basic-documents';
		const fileTitle = `File ${getRandomString()}`;
		const spaceName = `Space ${getRandomString()}`;

		const space = await createSpace(apiHelpers, spaceName);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				file: {
					fileBase64: 'SGVsbG8sIHdvcmxkIQ==',
					name: `file_${getRandomString()}.txt`,
				},
				objectEntryFolderExternalReferenceCode: 'L_FILES',
				title: fileTitle,
			},
			applicationName,
			space.name
		);

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: 'Space Content Reviewer',
			spaceName,
			spaceSummaryPage,
		});

		await spaceSummaryPage.viewAllFilesLink.click();

		await assetsPage.changeVisualizationMode('Table');

		await assetsPage.execItemAction({
			action: 'Delete',
			filter: fileTitle,
		});

		await waitForAlert(page, `${fileTitle} was moved`);

		await expect(page.getByText(fileTitle)).not.toBeVisible();
	}
);
