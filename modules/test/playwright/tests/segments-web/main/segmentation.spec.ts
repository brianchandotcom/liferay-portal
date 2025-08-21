/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {goToSegmentsAdmin} from '../../change-tracking-web/main/utils/segments';
import {segmentsPageTest} from './fixtures/segmentsPageTest';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	pageEditorPagesTest,
	productMenuPageTest,
	segmentsPageTest,
	usersAndOrganizationsPagesTest,
	loginTest()
);

const randomString = getRandomString();

const siteName = 'My Site ' + randomString;

let site;

test.beforeEach(async ({apiHelpers}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete site on the DXP side', async () => {
		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.headlessSite.deleteSite(String(site.id));
	});
});

test(
	`Can validate a segment can be created using the "Organization > Country" criterion`,

	{
		tag: '@LPS-130281',
	},

	async ({
		apiHelpers,
		editOrganizationPage,
		page,
		pageEditorPage,
		segmentsPage,
		usersAndOrganizationsPage,
	}) => {
		const segmentName = 'AddSegmentByOrganizationCountry Test';

		await test.step('Given a user and an organization were created and the user was assigned to the organization', async () => {
			const orgName = await apiHelpers.headlessAdminUser.postOrganization(
				{
					name: 'Organization1',
				}
			);

			const user = await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: 'userea@liferay.com',
			});

			await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
				orgName.id,
				user.emailAddress
			);

			await usersAndOrganizationsPage.goToOrganizations();
			await (
				await usersAndOrganizationsPage.organizationsTable.rowActions(
					'Organization1'
				)
			).click();
			await usersAndOrganizationsPage.editOrganizationMenuItem.click();
			await editOrganizationPage.countrySelect.selectOption('Spain');
			await editOrganizationPage.regionSelect.selectOption('Madrid');
			await editOrganizationPage.saveButton.click();
		});

		await test.step('When a segment designer adds a segment and checks the user belongs to the segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				'user-organization': ['Country'],
			});

			await segmentsPage.editSegmentsEntry(segmentName);

			await segmentsPage.selectOption('Spain');

			await segmentsPage.editSegmentsEntry(segmentName);

			await segmentsPage.viewMembers('userea@liferay.com');
		});

		await test.step('Then can assert the segment is correctly created', async () => {
			await goToSegmentsAdmin(page);

			const linkLocator = page.locator(`a:has-text('${segmentName}')`);
			await linkLocator.click();

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('spain');
		});
	}
);

test(
	`Can validate a segment can be created using the "Organization > Name" criterion`,

	{
		tag: '@LPS-130277',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'AddSegmentByOrganizationName Test';

		await test.step('Given a user and an organization were created and the user was assigned to the organization', async () => {
			const orgName = await apiHelpers.headlessAdminUser.postOrganization(
				{
					name: 'Organization Name',
				}
			);

			const user = await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: 'userea@liferay.com',
			});

			await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
				orgName.id,
				user.emailAddress
			);
		});

		await test.step('When a segment designer adds a segment and checks the user belongs to the segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				'user-organization': ['Name'],
			});

			await segmentsPage.editSegmentsEntry(segmentName);

			await segmentsPage.fillField('Organization Name');

			await segmentsPage.saveButton.click();

			await segmentsPage.clickLinkByText(segmentName);

			await segmentsPage.viewMembers('userea@liferay.com');
		});

		await test.step('Then can assert the segment is correctly created', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Organization Name');
		});
	}
);

test(
	`Can validate a segment can be created using the "Organization > Type" criterion`,

	{
		tag: '@LPS-130280',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'AddSegmentByOrganizationType Test';

		await test.step('Given a user and an organization were created and the user was assigned to the organization', async () => {
			const orgName = await apiHelpers.headlessAdminUser.postOrganization(
				{
					name: 'Organization Name',
				}
			);

			const user = await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: 'userea@liferay.com',
			});

			await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
				orgName.id,
				user.emailAddress
			);
		});

		await test.step('When a segment designer adds a segment and checks the user belongs to the segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				'user-organization': ['Type'],
			});

			await segmentsPage.fillField('organization');

			await segmentsPage.saveButton.click();

			await segmentsPage.clickLinkByText(segmentName);

			await segmentsPage.viewMembers('userea@liferay.com');
		});

		await test.step('Then can assert the segment is correctly created', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('organization');
		});
	}
);

test(
	`Can validate that a user cannot create a segment when no segments are available`,

	{
		tag: '@LPS-130346',
	},

	async ({page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'AddSegment Test';

		await test.step('When a segment designer adds a new segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				segments: ['Segments'],
			});

			await expect(segmentsPage.plusButton).not.toBeVisible();
		});

		await test.step(`Then asserts that segment creation is disabled within the segment selector`, async () => {
			await segmentsPage.selectButton.click();

			await expect(segmentsPage.plusButton).not.toBeVisible();
		});
	}
);

test(
	`Can validate a warning message is displayed when a non-existent entity name is entered in the segments editor.`,

	{
		tag: '@LPS-130347',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName1 = 'Segment With User1';
		const segmentName2 = 'Segment With User2';
		const segmentName3 = 'AddSegmentByOtherSegmentsWarning Test';

		page.on('dialog', async (dialog) => await dialog.accept());

		await test.step('Given 2 users were created', async () => {
			await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: `userea1@liferay.com`,
			});

			await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: `userea2@liferay.com`,
			});
		});

		await test.step('When a segment designer adds 2 segments', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName1, {
				user: ['Email Address'],
			});

			await segmentsPage.fillField('userea1@liferay.com');

			await segmentsPage.saveButton.click();

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName2, {
				user: ['Email Address'],
			});

			await segmentsPage.fillField('userea2@liferay.com');

			await segmentsPage.saveButton.click();
		});

		await test.step('And verifies a new segment correctly includes users from other segments', async () => {
			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName3, {
				segments: ['Segments'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectSegment('Segment With User1');

			await segmentsPage.clickDuplicateButton();

			await page.getByRole('button', {name: 'Select'}).nth(1).click();

			await segmentsPage.selectSegment('Segment With User2');

			await segmentsPage.chooseLogic('Or');

			await segmentsPage.saveButton.click();
		});

		await test.step('And deletes one of the segments', async () => {
			await segmentsPage.deleteSegment('Segment With User1');
		});

		await test.step('Then asserts that a warning is shown', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.editSegmentsEntry(segmentName3);

			await page.waitForLoadState('networkidle');

			const expectedMessage =
				'Delete this condition. It was created from an element that no longer exists.';

			await segmentsPage.assertErrorMessageIsVisible(expectedMessage);
		});
	}
);

test(
	`Can validate a segment can be created using the "Session > Browser" criterion`,

	{
		tag: '@LPS-130313',
	},

	async ({page, segmentsPage}) => {
		const segmentName = 'AddSegmentBySessionBrowser Test';

		await test.step('When a segment designer adds a segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await segmentsPage.addSessionSegment('Browser', segmentName);

			await segmentsPage.fillField('Chrome');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then asserts that the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Chrome');
		});
	}
);

test(
	`Can validate a segment can be created using the "Session > Language" criterion`,

	{
		tag: '@LPS-130351',
	},

	async ({page, segmentsPage}) => {
		const segmentName = 'AddSegmentBySessionLanguage Test';

		await test.step('When a segment designer adds a segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await segmentsPage.addSessionSegment('Language', segmentName);

			await segmentsPage.selectOption('Spanish (Spain)');

			await waitForAlert(page);
		});

		await test.step('Then asserts that the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('es_ES');
		});
	}
);

test(
	`Can validate a segment can be created using the "Session > URL" criterion`,

	{
		tag: '@LPS-130325',
	},

	async ({page, segmentsPage}) => {
		const segmentName = 'AddSegmentBySessionURL Test';

		await test.step('When a segment designer adds a segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await segmentsPage.addSessionSegment('URL', segmentName);

			await segmentsPage.fillField('http://localhost:8080');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then asserts that the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('http://localhost:8080');
		});
	}
);

test(
	`Can validate a segment can be created using an 'Apostrophe' in segment property`,

	{
		tag: '@LPS-146550',
	},

	async ({
		editUserPage,
		page,
		pageEditorPage,
		segmentsPage,
		usersAndOrganizationsPage,
	}) => {
		const segmentName = 'Segment with Apostrophe';

		await test.step('Given a user is created', async () => {
			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.addUserButton.click();

			await editUserPage.emailAddressInput.fill('shaquille@liferay.com');
			await editUserPage.firstNameInput.fill('Shaquille');
			await editUserPage.lastNameInput.fill(`O'Neal`);
			await editUserPage.screenNameInput.fill('shaquille');

			await editUserPage.saveButton.click();
		});

		await test.step('When a segment designer adds a segment with last name property', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['Last Name'],
			});

			await segmentsPage.fillField(`O'Neal`);

			await segmentsPage.saveButton.click();
		});

		await test.step('Then asserts that the segment is correctly created including the user', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await segmentsPage.viewMembers(undefined, `Shaquille O'Neal`);
		});
	}
);

test(
	`Can delete unavailable segment criterion.`,

	{
		tag: '@LPS-152077',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName1 = 'First Segment';
		const segmentName2 = 'Second Segment';

		page.on('dialog', async (dialog) => await dialog.accept());

		await test.step('Given a user is created', async () => {
			await apiHelpers.headlessAdminUser.postUserAccount({
				emailAddress: `userea@liferay.com`,
			});
		});

		await test.step('And a segment designer adds the first segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName1, {
				user: ['Email Address'],
			});

			await segmentsPage.fillField('userea@liferay.com');

			await segmentsPage.saveButton.click();
		});

		await test.step('And adds the second segment with 2 criterion', async () => {
			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName2, {
				segments: ['Segments'],
				user: ['First Name'],
			});

			await segmentsPage.fillField('userea');

			await segmentsPage.selectButton.click();

			await segmentsPage.selectSegment('First Segment');

			await segmentsPage.saveButton.click();
		});

		await test.step('When deletes one of the segments', async () => {
			await segmentsPage.deleteSegment('First Segment');
		});

		await test.step('And removes from second segment the criterion related to the first segment', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.editSegmentsEntry(segmentName2);

			await segmentsPage.deleteUnavailableProperty();

			await segmentsPage.saveButton.click();
		});

		await test.step('Then asserts that the deleted criterion will not shown', async () => {
			await segmentsPage.editSegmentsEntry(segmentName2);

			await page.waitForLoadState('networkidle');

			await expect(segmentsPage.criterionLabel).not.toContainText(
				'Segment'
			);
		});
	}
);

test(
	`Can scroll down in segments editor sidebar.`,

	{
		tag: '@LPS-150511',
	},

	async ({page, segmentsPage}) => {
		await test.step('Given a segment designer goes to the segments editor page', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();
		});

		await test.step('Then can scroll down in different properties on the sidebar', async () => {
			await segmentsPage.selectAndScrollToProperty(
				'Organization',
				'Type'
			);

			await segmentsPage.selectAndScrollToProperty(
				'Session',
				'User Agent'
			);

			await segmentsPage.selectAndScrollToProperty('User', 'User Name');
		});
	}
);

test(
	`Can validate a segment can be created using special characters in segment property`,

	{
		tag: '@LPS-131815',
	},

	async ({
		editUserPage,
		page,
		pageEditorPage,
		segmentsPage,
		usersAndOrganizationsPage,
	}) => {
		const segmentName = 'Segment With Special Characters';

		await test.step('Given a user is created', async () => {
			await usersAndOrganizationsPage.goToUsers();
			await usersAndOrganizationsPage.addUserButton.click();

			await editUserPage.emailAddressInput.fill('u1@liferay.com');
			await editUserPage.firstNameInput.fill('User');
			await editUserPage.lastNameInput.fill(`1 + / ? # &`);
			await editUserPage.screenNameInput.fill('u1');

			await editUserPage.saveButton.click();
		});

		await test.step('When a segment designer adds a segment with last name property', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['Last Name'],
			});

			await segmentsPage.fillField(`+ / ? # &`);

			await segmentsPage.changeCriterionInput('contains');

			const memberCountLocator = page.getByText('1 Member', {
				exact: true,
			});

			await expect(memberCountLocator).toBeVisible();

			await segmentsPage.saveButton.click();
		});

		await test.step('Then asserts that the segment is correctly created including the user', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await segmentsPage.viewMembers(undefined, `User 1 + / ? # &`);
		});
	}
);

test(
	`Can validate the default segments is not displayed.`,

	{
		tag: '@LPS-136086',
	},

	async ({page}) => {
		await test.step('Given a segment designer goes to the segments editor page', async () => {
			await goToSegmentsAdmin(page);
		});

		await test.step('Then can assert that the default segment is not displayed', async () => {
			await expect(
				page.getByText('There are no segments.')
			).toBeVisible();
		});
	}
);

test(
	`Can validate the value input persist in a segment created with Organization criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'Validate Organization Segment';

		await test.step('Given an organization is created', async () => {
			const orgName = await apiHelpers.headlessAdminUser.postOrganization(
				{
					name: 'Organization Name',
				}
			);
		});

		await test.step('When a segment designer adds a segment with Organization criterion', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				'user-organization': ['Organization'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCheckboxItem('Organization Name');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Organization Name');
		});
	}
);

test(
	`Can validate the value input persist in a segment created with Parent Organization criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({apiHelpers, page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'Validate Parent Organization Segment';

		await test.step('Given 2 organizations are created, the first as the parent of the second', async () => {
			const organization1 =
				await apiHelpers.headlessAdminUser.postOrganization({
					name: 'Parent Organization Name',
				});
			const organization2 =
				await apiHelpers.headlessAdminUser.postOrganization({
					name: 'Organization Name',
					parentOrganization: {
						externalReferenceCode:
							organization1.externalReferenceCode,
					},
				});
		});

		await test.step('When a segment designer adds a segment with Parent Organization criterion', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				'user-organization': ['Parent Organization'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCheckboxItem('Parent Organization Name');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in the view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Parent Organization Name');
		});
	}
);

test(
	`Can validate the value input persist in a segment created with Role criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'Validate Role Segment';

		await test.step('When a segment designer adds a segment with Regular Role criterion', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['Regular Role'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCheckboxItem('Administrator');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Administrator');
		});
	}
);

test(
	`Can validate the value input persist in a segment created with Site criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'Validate Site Segment';

		await test.step('When a segment designer adds a segment with Site criterion', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['Site'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCardItem(siteName);

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue(siteName);
		});
	}
);

test(
	`Can validate the value input persist in a segment created with Team criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({page, pageEditorPage, productMenuPage, segmentsPage, teamsPage}) => {
		const segmentName = 'Validate Site Segment';
		const teamName = "Test Team";		

		await test.step('Given a team is created', async () => {
			await teamsPage.goTo(site.friendlyUrlPath);
			
			await teamsPage.newTeamButton.click();

			await teamsPage.nameInput.fill(teamName);

			await teamsPage.saveButton.click();
	
			await waitForAlert(page);

			await expect(teamsPage.teamsTable.cell(teamName)).toBeVisible();
		});

		await test.step('When a segment designer adds a segment with Team criterion', async () => {
			await productMenuPage.goToSegments();

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['Team'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectSegment(teamName);

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue(teamName);
		});
	}
);

test(
	`Can validate the value input persist in a segment created with User criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({page, pageEditorPage, segmentsPage}) => {
		const segmentName = 'Validate User Segment';

		await test.step('When a segment designer adds a segment with User criterion', async () => {
			await goToSegmentsAdmin(page);

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['User'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCheckboxItem('Test Test');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('Test Test');
		});
	}
);

test(
	`Can validate the value input persist in a segment created with User Group criterion in view mode`,

	{
		tag: '@LPS-135880',
	},

	async ({apiHelpers, page, pageEditorPage, productMenuPage, segmentsPage}) => {
		const segmentName = 'Validate User Group Segment';	

		await test.step('Given a User Group is created', async () => {
			await apiHelpers.headlessAdminUser.postUserGroup(
				{
					name: 'User Group Name',
				}
			);
		});

		await test.step('When a segment designer adds a segment with User Group criterion', async () => {
			await productMenuPage.goToSegments();

			await segmentsPage.clickAddNewSegmentButton();

			await pageEditorPage.segmentEditorPage.createSegment(segmentName, {
				user: ['User Group'],
			});

			await segmentsPage.selectButton.click();

			await segmentsPage.selectCardItem('User Group Name');

			await segmentsPage.saveButton.click();

			await waitForAlert(page);
		});

		await test.step('Then can assert in view mode the segment is correctly created', async () => {
			await segmentsPage.clickLinkByText(segmentName);

			await page.waitForLoadState('networkidle');

			await segmentsPage.viewCriterionValue('User Group Name');
		});
	}
);

test(
	'Can understand the actions of keyboard from screen reader.',

	{
		tag: '@LPS-198108',
	},

	async ({page, productMenuPage}) => {
		await test.step('Given a segment designer accesses to the segment editor', async () => {
			await productMenuPage.openProductMenuIfClosed();

			await productMenuPage.goToSegments();

			await page
				.getByRole('link', {name: 'Add New User Segment'})
				.click();
		});

		const searchButton = page.getByTestId('search-button');
		const userTab = page.getByRole('button', {exact: true, name: 'User'});

		await test.step('When the segment designer focuses on the sidebar item via keyboard', async () => {
			await searchButton.focus();

			await searchButton.press('Tab');

			await expect(userTab).toBeFocused();
		});

		const categoryField = page
			.getByRole('application')
			.getByText('Category');
		const categoryPropertie = page.getByRole('menuitem', {
			name: 'Drag Category',
		});
		const dateModifiedField = page
			.getByRole('application')
			.getByText('Date Modified');
		const dateModifiedPropertie = page.getByRole('menuitem', {
			name: 'Drag Date Modified',
		});

		await test.step('When the segment designer drags the sidebar itens via keyboard', async () => {
			await userTab.press('ArrowDown');

			await expect(categoryPropertie).toBeFocused();

			await categoryPropertie.press('Enter');
			await categoryPropertie.press('Enter');

			await expect(
				page.getByText(
					'Category placed on middle of item 0 of group root.'
				)
			).toBeHidden();
			await expect(categoryField).toBeVisible();

			await categoryPropertie.focus();

			await categoryPropertie.press('ArrowDown');

			await expect(dateModifiedPropertie).toBeFocused();

			await dateModifiedPropertie.press('Enter');
			await dateModifiedPropertie.press('Enter');

			await expect(
				page.getByText(
					'Date Modified placed on bottom of item 0 of group 3.'
				)
			).toBeHidden();
			await expect(dateModifiedField).toBeVisible();
		});

		const viewMembersButton = page.getByRole('button', {
			name: 'View Members',
		});
		const firstDragIcon = page.locator('.drag-icon').first();

		await test.step('When the segment designer drags the sidebar itens via keyboard', async () => {
			await viewMembersButton.focus();

			await viewMembersButton.press('Tab');

			await expect(firstDragIcon).toBeFocused();

			const categoryFieldBox1 = await categoryField.boundingBox();
			const dateModifiedFieldBox1 = await dateModifiedField.boundingBox();

			await firstDragIcon.press('Enter');

			await firstDragIcon.press('ArrowDown');

			await expect(
				page.getByText('Targeting bottom of item 1 of group 3.')
			).toBeHidden();

			await firstDragIcon.press('Enter');

			await expect(
				page.getByText(
					'Category placed on middle of item 1 of group 3.'
				)
			).toBeHidden();

			const categoryFieldBox2 = await categoryField.boundingBox();
			const dateModifiedFieldBox2 = await dateModifiedField.boundingBox();

			expect(categoryFieldBox1).not.toBe(categoryFieldBox2);
			expect(dateModifiedFieldBox1).not.toBe(dateModifiedFieldBox2);
		});
	}
);
