/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../layout-content-page-editor-web/utils/getWidgetDefinition';
import {announcementsPagesTest} from './fixtures/announcementsPagesTest';

export const test = mergeTests(
	announcementsPagesTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'Do not have a blank option for distribution scope',
	{tag: '@LPD-18804'},
	async ({announcementsPage, page}) => {
		await announcementsPage.goToCreateNewAnnouncement();

		await page.getByRole('button', {name: 'Configuration'}).click();
		await page.getByLabel('Distribution Scope').selectOption({index: 0});

		expect(
			await page
				.getByLabel('Distribution Scope')
				.evaluate(
					(select: HTMLSelectElement) =>
						select.options[select.selectedIndex].label
				)
		).toBe('General');
	}
);

test(
	'Content field is required',
	{tag: '@LPD-27067'},
	async ({announcementsPage, page}) => {
		await announcementsPage.goToCreateNewAnnouncement();

		const requiredField = page.locator(
			'#_com_liferay_announcements_web_portlet_AnnouncementsAdminPortlet_contentEditorContainer svg.lexicon-icon-asterisk'
		);

		await expect(requiredField).toBeVisible();
	}
);

test(
	'The distribution scopes of the Announcements widget can be configured',
	{tag: '@LPD-33318'},
	async ({
		announcementsWidgetConfigurationPage,
		announcementsWidgetPage,
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const [organization, role, site1, userGroup] =
			await test.step('Create new organization, role, site, and user group', async () => {
				const user =
					await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
						'test@liferay.com'
					);

				const organization =
					await apiHelpers.headlessAdminUser.postOrganization();

				await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
					organization.id,
					user.emailAddress
				);

				const role = await apiHelpers.headlessAdminUser.postRole({
					name: 'Role' + getRandomString(),
				});

				const site1 = await apiHelpers.headlessSite.createSite({
					name: 'Site' + getRandomString(),
				});

				const userGroup =
					await apiHelpers.headlessAdminUser.postUserGroup();

				await apiHelpers.headlessAdminUser.assignUsersToUserGroup(
					userGroup.id,
					[user.id]
				);

				return [organization, role, site1, userGroup];
			});

		const [layout, widgetId] =
			await test.step('Create a page with the Announcements widget', async () => {
				const widgetId = getRandomString();

				const widgetDefinition = getWidgetDefinition({
					id: widgetId,
					widgetName:
						'com_liferay_announcements_web_portlet_AnnouncementsPortlet',
				});

				const layout = await apiHelpers.headlessDelivery.createSitePage(
					{
						pageDefinition: getPageDefinition([widgetDefinition]),
						siteId: site.id,
						title: getRandomString(),
					}
				);

				return [layout, widgetId];
			});

		await test.step('Configure the distribution scope of the Announcements widget', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.goToWidgetConfiguration(widgetId);

			await expect(
				announcementsWidgetConfigurationPage.distributionScopeOrganizationsTab
			).toBeVisible();
			await expect(
				announcementsWidgetConfigurationPage.distributionScopeRolesTab
			).toBeVisible();
			await expect(
				announcementsWidgetConfigurationPage.distributionScopeSitesTab
			).toBeVisible();
			await expect(
				announcementsWidgetConfigurationPage.distributionScopeUserGroupsTab
			).toBeVisible();

			await announcementsWidgetConfigurationPage.distributionScopeMoveToCurrent(
				'Organizations',
				organization.name
			);
			await announcementsWidgetConfigurationPage.distributionScopeMoveToCurrent(
				'Roles',
				role.name
			);
			await announcementsWidgetConfigurationPage.distributionScopeMoveToCurrent(
				'Sites',
				site1.name
			);
			await announcementsWidgetConfigurationPage.distributionScopeMoveToCurrent(
				'User Groups',
				userGroup.name
			);

			await announcementsWidgetConfigurationPage.saveAndClose();

			await pageEditorPage.publishPage();
		});

		await test.step('Check that the configured distribution scopes can be selected when adding a new announcement', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await announcementsWidgetPage.addAnnouncementButton.click();

			await expect(
				announcementsWidgetPage.getDistributionScopeSelectOptions(
					'Organizations'
				)
			).toContainText([organization.name]);
			await expect(
				announcementsWidgetPage.getDistributionScopeSelectOptions(
					'Roles'
				)
			).toContainText([role.name]);
			await expect(
				announcementsWidgetPage.getDistributionScopeSelectOptions(
					'Sites'
				)
			).toContainText([site1.name]);
			await expect(
				announcementsWidgetPage.getDistributionScopeSelectOptions(
					'User Groups'
				)
			).toContainText([userGroup.name]);
		});
	}
);
