/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';
import path from 'node:path';

import {accountsPagesTest} from '../../../../fixtures/accountsPagesTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../../utils/getRandomInt';
import getRandomString from '../../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../../utils/performLogin';
import {waitForAlert} from '../../../../utils/waitForAlert';
import getPageDefinition from '../../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../../layout-content-page-editor-web/main/utils/getWidgetDefinition';

export const test = mergeTests(
	accountsPagesTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

async function waitForAnimationEnd(locator: Locator) {
	const handle = await locator.elementHandle();
	await handle?.waitForElementState('stable');

	return handle?.dispose();
}

test(
	'Can search an entry present multiple times in organization management',
	{tag: ['@LPD-44149']},
	async ({
		apiHelpers,
		organizationManagementPage,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const organization3 =
			await apiHelpers.headlessAdminUser.postOrganization({
				parentOrganization: {
					externalReferenceCode: organization1.externalReferenceCode,
				},
			});

		const accounts = [];
		const data = [
			{
				emailAddress: 'buyer@liferay.com',
				organizationId: organization3.id,
			},
			{
				emailAddress: 'test@liferay.com',
				organizationId: organization2.id,
			},
			{
				emailAddress: 'buyer@liferay.com',
				organizationId: organization2.id,
			},
		];

		for (const item of data) {
			const account = await apiHelpers.headlessAdminUser.postAccount();
			accounts.push(account);

			await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
				account.id,
				[item.emailAddress]
			);

			await apiHelpers.headlessAdminUser.postAccountOrganization(
				account.id,
				item.organizationId
			);
		}

		const userName1 = 'buyer';
		const userName2 = 'Test';

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await organizationManagementPage.searchInput.fill(userName1);
		await organizationManagementPage.searchedEntry(userName1).click();

		await waitForAnimationEnd(
			organizationManagementPage.userNode(userName1).first()
		);

		await expect(
			organizationManagementPage.userNode(userName1)
		).toHaveCount(2);
		await expect(
			organizationManagementPage.userNode(userName2)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.infoText(`2 Results for ${userName1}`)
		).toBeVisible();

		await organizationManagementPage.collapseAllButton.click();

		await organizationManagementPage.searchInput.fill(userName2);
		await organizationManagementPage.searchedEntry(userName2).click();

		await waitForAnimationEnd(
			organizationManagementPage.userNode(userName2)
		);

		await expect(
			organizationManagementPage.userNode(userName1)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.userNode(userName2)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.infoText(`1 Result for ${userName2}`)
		).toBeVisible();
	}
);

test(
	'Can move accounts and organizations in the widget',
	{tag: ['@LPD-30190']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});
		const organization3 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
				parentOrganization: {
					externalReferenceCode: organization2.externalReferenceCode,
				},
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization2.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization2.name)
		);
		await organizationManagementPage
			.organizationNode(organization2.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization3.name)
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(1);

		await organizationManagementPage
			.accountNode(account.name)
			.dragTo(
				organizationManagementPage.organizationNode(organization1.name)
			);
		await organizationManagementPage
			.organizationNode(organization3.name)
			.dragTo(
				organizationManagementPage.organizationNode(organization1.name)
			);

		await page.waitForTimeout(200);
		await page.reload();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization2.name)
		);
		await organizationManagementPage
			.organizationNode(organization2.name)
			.click();

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(0);

		await organizationManagementPage
			.organizationNode(organization1.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization3.name)
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(1);

		await organizationManagementPage
			.menuButton(
				organizationManagementPage.organizationNode(organization3.name)
			)
			.click();
		await organizationManagementPage.removeItem.click();

		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(0);

		await page.waitForTimeout(200);
		await page.reload();

		await expect(organizationManagementPage.chart).toBeVisible();
		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(1);

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization1.name)
		);
		await organizationManagementPage
			.organizationNode(organization1.name)
			.click();

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.organizationNode(organization3.name)
		).toHaveCount(1);
	}
);

test(
	'Can associate existing user using the widget',
	{tag: ['@LPD-31011']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await organizationManagementPage.addUserToOrganization();
		await waitForAlert(page, `1 user was added to ${organization.name}`);

		apiHelpers.data.push({
			id: `${organization.id}_test@liferay.com`,
			type: 'organizationUserAccountAssociation',
		});

		await page.reload();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await expect(
			organizationManagementPage.userNode('Test Test')
		).toHaveCount(1);
	}
);

test(
	'Can add new user using the widget',
	{tag: ['@LPD-31026']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		const userEmailAddress = `${getRandomInt()}@liferay.com`;

		await organizationManagementPage.addUserToOrganization({
			email: userEmailAddress,
		});
		await waitForAlert(page, `1 user was added to ${organization.name}`);

		const user =
			await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
				userEmailAddress
			);

		apiHelpers.data.push({
			id: user.id,
			type: 'userAccount',
		});
		apiHelpers.data.push({
			id: `${organization.id}_${userEmailAddress}`,
			type: 'organizationUserAccountAssociation',
		});

		await page.reload();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await expect(
			organizationManagementPage.userNode(
				userEmailAddress.substr(0, userEmailAddress.indexOf('@'))
			)
		).toHaveCount(1);
	}
);

test(
	'Can associate existing account using the widget',
	{tag: ['@LPD-31052']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await organizationManagementPage.addAccountToOrganization({
			accountName: account.name,
			isNew: false,
		});
		await waitForAlert(page, `1 account was added to ${organization.name}`);

		await page.reload();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);
	}
);

test(
	'Can add new account using the widget',
	{tag: ['@LPD-31052']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		const accountName = `Acc${getRandomInt()}`;

		await organizationManagementPage.addAccountToOrganization({
			accountName,
			isNew: true,
		});
		await waitForAlert(page, `1 account was added to ${organization.name}`);

		const account =
			await apiHelpers.headlessAdminUser.getAccountByName(accountName);

		apiHelpers.data.push({id: account.id, type: 'account'});

		await page.reload();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await expect(
			organizationManagementPage.accountNode(accountName)
		).toHaveCount(1);
	}
);

test(
	'Can add new organization using the widget',
	{tag: ['@LPD-31403']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization1.name)
		);
		await organizationManagementPage
			.organizationNode(organization1.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		const organizationName = `Org${getRandomInt()}`;

		await organizationManagementPage.addOrganizationToOrganization({
			organizationName,
		});
		await waitForAlert(
			page,
			`1 organization was added to ${organization1.name}`
		);

		const organization2 =
			await apiHelpers.headlessAdminUser.getOrganizationByName(
				organizationName
			);

		apiHelpers.data.push({id: organization2.id, type: 'organization'});

		await page.reload();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization1.name)
		);
		await organizationManagementPage
			.organizationNode(organization1.name)
			.click();
		await waitForAnimationEnd(organizationManagementPage.addNode);

		await expect(
			organizationManagementPage.organizationNode(organization2.name)
		).toHaveCount(1);
	}
);

test(
	'Can highlight the correct entry also if share the same name',
	{tag: ['@COMMERCE-12594']},
	async ({
		apiHelpers,
		organizationManagementPage,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'AccountA',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization1.id
		);
		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization2.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'AccountA',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account2.id,
			organization1.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await organizationManagementPage.searchInput.fill(account1.name);

		await expect(
			organizationManagementPage.searchedEntry(account1.name)
		).toHaveCount(2);

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account1.name)
		).toHaveCount(3);
		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(2);
		await expect(
			organizationManagementPage.infoText(
				`2 Results for ${account1.name}`
			)
		).toBeVisible();

		await organizationManagementPage.collapseAllButton.click();

		await organizationManagementPage.searchInput.fill(account2.name);

		await expect(
			organizationManagementPage.searchedEntry(account1.name)
		).toHaveCount(2);

		await organizationManagementPage
			.searchedEntry(account2.name)
			.last()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account2.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account2.name)
		).toHaveCount(2);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.infoText(`1 Result for ${account2.name}`)
		).toBeVisible();
	}
);

test(
	'Can use the suggestion into the autocomplete for the searching',
	{tag: ['@COMMERCE-12584', '@COMMERCE-12586']},
	async ({
		apiHelpers,
		organizationManagementPage,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization1.id
		);
		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization2.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account B',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account2.id,
			organization1.id
		);

		const account3 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account C',
		});

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await organizationManagementPage.searchInput.fill('Org');

		await expect(
			organizationManagementPage.searchedEntry(organization1.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(organization2.name)
		).toBeVisible();

		await organizationManagementPage
			.searchedEntry(organization1.name)
			.last()
			.click();
		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization1.name)
				.first()
		);

		await expect(
			organizationManagementPage.discoveredOrganizationNode(
				organization1.name
			)
		).toHaveCount(1);

		await organizationManagementPage.searchInput.fill(
			`"${organization1.name}"`
		);

		await expect(
			organizationManagementPage.searchedEntry(organization1.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(organization2.name)
		).toHaveCount(0);

		await organizationManagementPage.searchInput.fill('Account');

		await expect(
			organizationManagementPage.searchedEntry(account1.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account2.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account3.name)
		).toBeVisible();

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(2);
		await expect(
			organizationManagementPage.infoText(
				`2 Results for ${account1.name}`
			)
		).toBeVisible();

		await organizationManagementPage.searchInput.fill('Account');

		await expect(
			organizationManagementPage.searchedEntry(account1.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account2.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account3.name)
		).toBeVisible();

		await organizationManagementPage
			.searchedEntry(account2.name)
			.first()
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account2.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.infoText(`1 Result for ${account2.name}`)
		).toBeVisible();

		await organizationManagementPage.searchInput.fill('Account');

		await expect(
			organizationManagementPage.searchedEntry(account1.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account2.name)
		).toBeVisible();
		await expect(
			organizationManagementPage.searchedEntry(account3.name)
		).toBeVisible();

		await organizationManagementPage
			.searchedEntry(account3.name)
			.first()
			.click();

		await expect(
			organizationManagementPage.accountNode(account1.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.accountNode(account2.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.infoText(
				`0 Results for ${account3.name}`
			)
		).toBeVisible();
	}
);

test(
	'The chart focuses on the discovered entry',
	{tag: ['@COMMERCE-12582']},
	async ({
		apiHelpers,
		organizationManagementPage,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Org' + getRandomInt(),
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Org' + getRandomInt(),
			});

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization1.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account B',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account2.id,
			organization2.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		let transformAttribute = await organizationManagementPage.chart
			.locator('> g')
			.getAttribute('transform');
		let translate = transformAttribute.split(' ')[0];
		let scale = Number(
			transformAttribute.split(' ')[1].replace(/.*\((.*)\).*/, '$1')
		);

		await organizationManagementPage.searchInput.fill(account1.name);

		await organizationManagementPage
			.searchedEntry(account1.name)
			.last()
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		transformAttribute = await organizationManagementPage.chart
			.locator('> g')
			.getAttribute('transform');
		let newTranslate = transformAttribute.split(' ')[0];
		let newScale = Number(
			transformAttribute.split(' ')[1].replace(/.*\((.*)\).*/, '$1')
		);

		expect(newTranslate).not.toEqual(translate);
		expect(newScale).not.toEqual(scale);

		translate = newTranslate;
		scale = newScale;

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(0);

		await organizationManagementPage.searchInput.fill(account2.name);

		await organizationManagementPage
			.searchedEntry(account2.name)
			.first()
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account2.name).first()
		);

		transformAttribute = await organizationManagementPage.chart
			.locator('> g')
			.getAttribute('transform');
		newTranslate = transformAttribute.split(' ')[0];
		newScale = Number(
			transformAttribute.split(' ')[1].replace(/.*\((.*)\).*/, '$1')
		);

		expect(newTranslate).not.toEqual(translate);
		expect(newScale).toEqual(scale);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(1);
	}
);

test(
	'Can remove a user from an account using the widget',
	{tag: ['@COMMERCE-12876']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: String(getRandomInt()),
			givenName: 'U',
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[user.emailAddress]
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();
		await waitForAnimationEnd(
			organizationManagementPage.userNode(user.name)
		);

		await expect(
			organizationManagementPage.userNode(user.name)
		).toBeVisible();

		await organizationManagementPage
			.menuButton(organizationManagementPage.userNode(user.name))
			.click();
		await organizationManagementPage.removeItem.click();

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();

		await expect(
			organizationManagementPage.userNode(user.name)
		).toHaveCount(0);

		await usersAndOrganizationsPage.goToUsers();

		await expect(
			usersAndOrganizationsPage.usersTableCell(user.name)
		).toBeVisible();
	}
);

test(
	'Can delete a user from an account using the widget',
	{tag: ['@COMMERCE-12875']},
	async ({
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: String(getRandomInt()),
			givenName: 'U',
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[user.emailAddress]
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();
		await waitForAnimationEnd(
			organizationManagementPage.userNode(user.name)
		);

		await expect(
			organizationManagementPage.userNode(user.name)
		).toBeVisible();

		await organizationManagementPage
			.menuButton(organizationManagementPage.userNode(user.name))
			.click();
		await organizationManagementPage.deleteItem.click();

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();

		await expect(
			organizationManagementPage.userNode(user.name)
		).toHaveCount(0);

		await usersAndOrganizationsPage.goToUsers();

		await expect(
			usersAndOrganizationsPage.usersTableCell(user.name)
		).toHaveCount(0);
	}
);

test(
	'Can remove an account from an organization using the widget',
	{tag: ['@COMMERCE-12826']},
	async ({
		accountsPage,
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();

		await organizationManagementPage
			.menuButton(organizationManagementPage.accountNode(account.name))
			.click();
		await organizationManagementPage.removeItem.click();

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(0);

		await accountsPage.gotoAccountAdmin();

		await expect(accountsPage.accountNameLink(account.name)).toBeVisible();
	}
);

test(
	'Can delete an account from an organization using the widget',
	{tag: ['@COMMERCE-12826']},
	async ({
		accountsPage,
		apiHelpers,
		organizationManagementPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: `Acc${getRandomInt()}`,
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name)
		);
		await organizationManagementPage.accountNode(account.name).click();

		await organizationManagementPage
			.menuButton(organizationManagementPage.accountNode(account.name))
			.click();
		await organizationManagementPage.deleteItem.click();

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage.organizationNode(organization.name)
		);
		await organizationManagementPage
			.organizationNode(organization.name)
			.click();

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(0);

		await accountsPage.gotoAccountAdmin();

		await expect(accountsPage.accountNameLink(account.name)).toHaveCount(0);
	}
);

test(
	'Can search an entity two times in a row',
	{tag: ['@COMMERCE-12583']},
	async ({
		apiHelpers,
		organizationManagementPage,
		usersAndOrganizationsPage,
	}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization();
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization();

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization1.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account B',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account2.id,
			organization2.id
		);

		await usersAndOrganizationsPage.goToOrganizationChart();

		await expect(organizationManagementPage.chart).toBeVisible();

		await organizationManagementPage.searchInput.fill('Account');

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(0);

		await organizationManagementPage
			.discoveredAccountNode(account1.name)
			.click();

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(0);

		await organizationManagementPage.searchInput.focus();

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(0);

		await organizationManagementPage.searchInput.focus();

		await organizationManagementPage
			.searchedEntry(account2.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account2.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(1);
	}
);

test(
	'Changing the root configuration for the widget affect the search results',
	{tag: ['@COMMERCE-12586']},
	async ({apiHelpers, organizationManagementPage, page, site}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account1 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization1.id
		);
		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account1.id,
			organization2.id
		);

		const account2 = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account B',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account2.id,
			organization2.id
		);

		const layout1 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web/${site.name}/${layout1.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization1.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.organizationNode(organization2.name)
		).toHaveCount(1);

		await organizationManagementPage.searchInput.fill('Account');

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(2);
		await expect(
			organizationManagementPage.infoText(
				`2 Results for ${account1.name}`
			)
		).toBeVisible();

		await organizationManagementPage.searchInput.focus();

		await organizationManagementPage
			.searchedEntry(account2.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account2.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.infoText(`1 Result for ${account2.name}`)
		).toBeVisible();

		const layout2 = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetConfig: {
						rootOrganizationExternalReferenceCode:
							organization1.externalReferenceCode,
					},
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web/${site.name}/${layout2.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization1.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.organizationNode(organization2.name)
		).toHaveCount(0);

		await organizationManagementPage.searchInput.fill('Account');

		await organizationManagementPage
			.searchedEntry(account1.name)
			.first()
			.click();

		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account1.name).first()
		);

		await expect(
			organizationManagementPage.discoveredAccountNode(account1.name)
		).toHaveCount(1);
		await expect(
			organizationManagementPage.infoText(`1 Result for ${account1.name}`)
		).toBeVisible();

		await organizationManagementPage.searchInput.focus();

		await organizationManagementPage
			.searchedEntry(account2.name)
			.first()
			.click();

		await expect(
			organizationManagementPage.discoveredAccountNode(account2.name)
		).toHaveCount(0);
		await expect(
			organizationManagementPage.infoText(
				`0 Results for ${account2.name}`
			)
		).toBeVisible();
	}
);

test(
	'Can edit, delete, remove an account in the widget if the necessary permissions are given',
	{tag: ['@COMMERCE-12823']},
	async ({apiHelpers, organizationManagementPage, page, site}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role1.externalReferenceCode,
			user.id
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage
			.menuButton(organizationManagementPage.accountNode(account.name))
			.click();

		await expect(organizationManagementPage.deleteItem).toHaveCount(0);
		await expect(organizationManagementPage.editItem).toHaveCount(0);
		await expect(organizationManagementPage.removeItem).toHaveCount(0);
		await expect(organizationManagementPage.viewItem).toBeVisible();

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['DELETE', 'UPDATE'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role2.externalReferenceCode,
			user.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage
			.menuButton(organizationManagementPage.accountNode(account.name))
			.click();

		await expect(organizationManagementPage.deleteItem).toBeVisible();
		await expect(organizationManagementPage.editItem).toBeVisible();
		await expect(organizationManagementPage.removeItem).toBeVisible();
		await expect(organizationManagementPage.viewItem).toBeVisible();
	}
);

test(
	'Can edit, delete, remove a user in the widget if the necessary permissions are given',
	{tag: ['@COMMERCE-12823']},
	async ({apiHelpers, organizationManagementPage, page, site}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const userAccount = await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: 'A',
			givenName: 'User',
		});

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[userAccount.emailAddress]
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.User',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role1.externalReferenceCode,
			user.id
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage.accountNode(account.name).click();

		await waitForAnimationEnd(
			organizationManagementPage.userNode(userAccount.name).first()
		);

		await organizationManagementPage
			.menuButton(organizationManagementPage.userNode(userAccount.name))
			.click();

		await expect(organizationManagementPage.deleteItem).toHaveCount(0);
		await expect(organizationManagementPage.editItem).toHaveCount(0);
		await expect(organizationManagementPage.removeItem).toHaveCount(0);
		await expect(organizationManagementPage.viewItem).toBeVisible();

		await performLogout(page);
		await performLoginViaApi({page, screenName: 'test'});

		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['DELETE', 'UPDATE'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.User',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role2.externalReferenceCode,
			user.id
		);

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage.accountNode(account.name).click();

		await waitForAnimationEnd(
			organizationManagementPage.userNode(userAccount.name).first()
		);

		await organizationManagementPage
			.menuButton(organizationManagementPage.userNode(userAccount.name))
			.click();

		await expect(organizationManagementPage.deleteItem).toBeVisible();
		await expect(organizationManagementPage.editItem).toBeVisible();
		await expect(organizationManagementPage.removeItem).toBeVisible();
		await expect(organizationManagementPage.viewItem).toBeVisible();
	}
);

test(
	'Can edit an account in the widget',
	{tag: ['@COMMERCE-12824']},
	async ({apiHelpers, organizationManagementPage, page, site}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['UPDATE', 'VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			user.id
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage
			.menuButton(organizationManagementPage.accountNode(account.name))
			.click();
		await organizationManagementPage.viewItem.click();

		await expect(organizationManagementPage.sidebarTitle).toHaveText(
			account.name
		);
		await expect(
			organizationManagementPage.sidebarValue('Account ID')
		).toHaveText(String(account.id));
		await expect(
			organizationManagementPage.sidebarValue('Account Name')
		).toHaveText(account.name);
		await expect(
			organizationManagementPage.sidebarValue('Description')
		).toHaveText(account.description || '-');
		await expect(
			organizationManagementPage.sidebarValue('External Reference Code')
		).toHaveText(account.externalReferenceCode);
		await expect(
			organizationManagementPage.sidebarValue('Image')
		).toHaveText('Default');
		await expect(
			organizationManagementPage.sidebarValue('Tax ID')
		).toHaveText(account.taxID || '-');
		await expect(
			organizationManagementPage.sidebarValue('Type')
		).toHaveText('Business');

		await organizationManagementPage.sidebarMoreAction.click();
		await organizationManagementPage.editItem.click();

		account.description = getRandomString();
		account.externalReferenceCode = getRandomString();
		account.name = String(getRandomInt());
		account.taxID = getRandomString();

		await organizationManagementPage.sidebarAccountNameField.fill(
			account.name
		);
		await organizationManagementPage.sidebarDescriptionField.fill(
			account.description
		);
		await organizationManagementPage.sidebarExternalReferenceCodeField.fill(
			account.externalReferenceCode
		);
		await organizationManagementPage.sidebarTaxIDField.fill(account.taxID);

		const fileChooserPromise = page.waitForEvent('filechooser');

		await organizationManagementPage.sidebarChangeImageButton.click();
		await organizationManagementPage.selectImage.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(__dirname, '/dependencies/liferay.png')
		);

		await organizationManagementPage.selectImageDoneButton.click();

		await expect(
			organizationManagementPage.sidebarAccountIDField
		).toBeDisabled();
		await expect(
			organizationManagementPage.sidebarTypeField
		).toBeDisabled();

		await organizationManagementPage.sidebarSaveButton.click();

		await waitForAlert(page);

		await expect(organizationManagementPage.sidebarTitle).toHaveText(
			account.name
		);
		await expect(
			organizationManagementPage.sidebarValue('Account ID')
		).toHaveText(String(account.id));
		await expect(
			organizationManagementPage.sidebarValue('Account Name')
		).toHaveText(account.name);
		await expect(
			organizationManagementPage.sidebarValue('Description')
		).toHaveText(account.description || '-');
		await expect(
			organizationManagementPage.sidebarValue('External Reference Code')
		).toHaveText(account.externalReferenceCode);
		await expect(
			organizationManagementPage.sidebarValue('Image')
		).toHaveText('Custom Image');
		await expect(
			organizationManagementPage.sidebarValue('Tax ID')
		).toHaveText(account.taxID || '-');
		await expect(
			organizationManagementPage.sidebarValue('Type')
		).toHaveText('Business');
	}
);

test(
	'Can edit a user in the widget',
	{tag: ['@COMMERCE-12874']},
	async ({apiHelpers, organizationManagementPage, page, site}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: `Org${getRandomInt()}`,
			});

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: 'Account A',
		});

		await apiHelpers.headlessAdminUser.postAccountOrganization(
			account.id,
			organization.id
		);

		const userAccount = (await apiHelpers.headlessAdminUser.postUserAccount(
			{
				familyName: 'A',
				givenName: 'User',
			}
		)) as any;

		await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
			account.id,
			[userAccount.emailAddress]
		);

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: getRandomString(),
			rolePermissions: [
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.account.model.AccountEntry',
					scope: 1,
				},
				{
					actionIds: ['VIEW'],
					primaryKey: companyId,
					resourceName:
						'com.liferay.portal.kernel.model.Organization',
					scope: 1,
				},
				{
					actionIds: ['UPDATE', 'VIEW'],
					primaryKey: companyId,
					resourceName: 'com.liferay.portal.kernel.model.User',
					scope: 1,
				},
			],
		});

		await apiHelpers.headlessAdminUser.postRoleByExternalReferenceCodeUserAccountAssociation(
			role.externalReferenceCode,
			user.id
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_commerce_organization_web_internal_portlet_CommerceOrganizationPortlet',
				}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		await performLogout(page);
		await performLoginViaApi({page, screenName: user.alternateName});

		await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

		await expect(organizationManagementPage.chart).toBeVisible();

		await waitForAnimationEnd(
			organizationManagementPage
				.organizationNode(organization.name)
				.first()
		);

		await expect(
			organizationManagementPage.organizationNode(organization.name)
		).toHaveCount(1);

		await organizationManagementPage
			.organizationNode(organization.name)
			.click();
		await waitForAnimationEnd(
			organizationManagementPage.accountNode(account.name).first()
		);

		await expect(
			organizationManagementPage.accountNode(account.name)
		).toHaveCount(1);

		await organizationManagementPage.accountNode(account.name).click();

		await waitForAnimationEnd(
			organizationManagementPage.userNode(userAccount.name).first()
		);

		await organizationManagementPage
			.menuButton(organizationManagementPage.userNode(userAccount.name))
			.click();
		await organizationManagementPage.viewItem.click();

		await expect(organizationManagementPage.sidebarTitle).toHaveText(
			userAccount.alternateName
		);
		await expect(
			organizationManagementPage.sidebarValue('Email Address')
		).toHaveText(userAccount.emailAddress);
		await expect(
			organizationManagementPage.sidebarValue('First Name')
		).toHaveText(userAccount.givenName);
		await expect(
			organizationManagementPage.sidebarValue('Image')
		).toHaveText('Default');
		await expect(
			organizationManagementPage.sidebarValue('Job Title')
		).toHaveText(userAccount.jobTitle || '-');
		await expect(
			organizationManagementPage.sidebarValue('Language')
		).toHaveText('English (United States)');
		await expect(
			organizationManagementPage.sidebarValue('Last Name')
		).toHaveText(userAccount.familyName);
		await expect(
			organizationManagementPage.sidebarValue('Middle Name')
		).toHaveText(userAccount.middleName || '-');
		await expect(
			organizationManagementPage.sidebarValue('Prefix')
		).toHaveText(userAccount.prefix || '-');
		await expect(
			organizationManagementPage.sidebarValue('Screen Name')
		).toHaveText(userAccount.alternateName);
		await expect(
			organizationManagementPage.sidebarValue('Suffix')
		).toHaveText(userAccount.suffix || '-');
		await expect(
			organizationManagementPage.sidebarValue('User ID')
		).toHaveText(String(userAccount.id));

		await organizationManagementPage.sidebarMoreAction.click();
		await organizationManagementPage.editItem.click();

		userAccount.alternateName = String(getRandomInt());
		userAccount.emailAddress = `${getRandomString()}@liferay.com`;
		userAccount.givenName = getRandomString();
		userAccount.jobTitle = getRandomString();
		userAccount.familyName = getRandomString();
		userAccount.middleName = getRandomString();
		userAccount.prefix = 'Mr';
		userAccount.suffix = 'Jr';

		await organizationManagementPage.sidebarEmailAddressField.fill(
			userAccount.emailAddress
		);
		await organizationManagementPage.sidebarFirstNameField.fill(
			userAccount.givenName
		);
		await organizationManagementPage.sidebarJobTitleField.fill(
			userAccount.jobTitle
		);
		await organizationManagementPage.sidebarLanguageField.selectOption(
			'pt_BR'
		);
		await organizationManagementPage.sidebarLastNameField.fill(
			userAccount.familyName
		);
		await organizationManagementPage.sidebarMiddleNameField.fill(
			userAccount.middleName
		);
		await organizationManagementPage.sidebarPrefixField.selectOption(
			userAccount.prefix
		);
		await organizationManagementPage.sidebarScreenNameField.fill(
			userAccount.alternateName
		);
		await organizationManagementPage.sidebarSuffixField.selectOption(
			userAccount.suffix
		);

		const fileChooserPromise = page.waitForEvent('filechooser');

		await organizationManagementPage.sidebarChangeImageButton.click();
		await organizationManagementPage.selectImage.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(
			path.join(__dirname, '/dependencies/liferay.png')
		);

		await organizationManagementPage.selectImageDoneButton.click();

		await expect(
			organizationManagementPage.sidebarUserIDField
		).toBeDisabled();

		await organizationManagementPage.sidebarSaveButton.click();

		await waitForAlert(page);

		await expect(organizationManagementPage.sidebarTitle).toHaveText(
			userAccount.alternateName
		);
		await expect(
			organizationManagementPage.sidebarValue('Email Address')
		).toHaveText(userAccount.emailAddress);
		await expect(
			organizationManagementPage.sidebarValue('First Name')
		).toHaveText(userAccount.givenName);
		await expect(
			organizationManagementPage.sidebarValue('Image')
		).toHaveText('Custom Image');
		await expect(
			organizationManagementPage.sidebarValue('Job Title')
		).toHaveText(userAccount.jobTitle || '-');
		await expect(
			organizationManagementPage.sidebarValue('Language')
		).toHaveText('Portuguese (Brazil)');
		await expect(
			organizationManagementPage.sidebarValue('Last Name')
		).toHaveText(userAccount.familyName);
		await expect(
			organizationManagementPage.sidebarValue('Middle Name')
		).toHaveText(userAccount.middleName || '-');
		await expect(
			organizationManagementPage.sidebarValue('Prefix')
		).toHaveText(userAccount.prefix || '-');
		await expect(
			organizationManagementPage.sidebarValue('Screen Name')
		).toHaveText(userAccount.alternateName);
		await expect(
			organizationManagementPage.sidebarValue('Suffix')
		).toHaveText(userAccount.suffix || '-');
		await expect(
			organizationManagementPage.sidebarValue('User ID')
		).toHaveText(String(userAccount.id));
	}
);
