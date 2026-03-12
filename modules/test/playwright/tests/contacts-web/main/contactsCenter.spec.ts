/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {contactsCenterPagesTest} from '../../../fixtures/contactsCenterPagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	contactsCenterPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'Smoke',
	{tag: ['@LPD-81993', '@LPS-100624']},
	async ({apiHelpers, contactsCenterPage, page, site}) => {
		test.setTimeout(120000);

		const userNumber = getRandomInt();

		await apiHelpers.headlessAdminUser.postUserAccount({
			emailAddress: `user${userNumber}@liferay.com`,
			familyName: 'userln',
			givenName: 'userfn',
		});

		await contactsCenterPage.createPage(apiHelpers, site.id, {
			title: 'contacts-center',
			widgetConfig: {showEmailAddress: 'true'},
		});

		const contactName = `Contact${getRandomInt()}`;
		const contactNameEdit = `Edited${contactName}`;
		const contactEmail = `${contactName}@liferay.com`;

		await page.goto(`/web/${site.name}/contacts-center`);

		await contactsCenterPage.addContactButton.click();

		await expect(contactsCenterPage.nameInput).toBeVisible();

		await contactsCenterPage.nameInput.fill(contactName);
		await contactsCenterPage.emailAddressInput.fill(contactEmail);
		await contactsCenterPage.saveButton.click();

		await expect(contactsCenterPage.successMessage).toBeVisible();

		await expect(
			contactsCenterPage.contactContainerText(contactName)
		).toBeVisible();

		await expect(contactsCenterPage.editButton).toBeVisible();
		await expect(contactsCenterPage.deleteButton).toBeVisible();

		await contactsCenterPage.editButton.click();

		await expect(contactsCenterPage.nameInput).toBeVisible();

		await contactsCenterPage.nameInput.fill(contactNameEdit);
		await contactsCenterPage.saveButton.click();

		await expect(contactsCenterPage.updateSuccessMessage).toBeVisible();

		await contactsCenterPage.contactListItem('userln, userfn').click();

		await expect(
			contactsCenterPage.contactContainerText('userfn')
		).toBeVisible();

		for (const buttonName of ['Connect', 'Follow', 'Block', 'vCard']) {
			await expect(
				page.getByRole('button', {name: buttonName})
			).toBeVisible();
		}

		await page.goto(`/web/${site.name}/contacts-center`);

		await expect(contactsCenterPage.connectionsCount).toBeVisible();
		await expect(contactsCenterPage.followingCount).toBeVisible();
		await expect(contactsCenterPage.followersCount).toBeVisible();
		await expect(contactsCenterPage.myContactsCount).toBeVisible();
		await expect(contactsCenterPage.allUsersCount).toBeVisible();

		page.on('dialog', (dialog) => dialog.accept());

		await contactsCenterPage.contactListItem(contactNameEdit).click();
		await contactsCenterPage.deleteButton.click();
	}
);
