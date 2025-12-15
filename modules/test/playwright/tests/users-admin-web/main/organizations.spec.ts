/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {countriesManagementPageTest} from '../../../fixtures/CountriesManagementPageTest';
import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {createCategories} from '../../../helpers/CreateCategories';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	countriesManagementPageTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-35443': {enabled: true},
		'LPD-35914': {enabled: true},
	}),
	loginTest(),
	usersAndOrganizationsPagesTest
);

test(
	'Add multiple suborganizations to parent organization',
	{tag: ['@LPD-57824', '@LPD-70012']},
	async ({apiHelpers, usersAndOrganizationsPage}) => {
		const parentOrganization =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Parent Organization' + getRandomInt(),
			});

		const subOrganizations = [];

		for (let count = 1; count <= 5; count++) {
			const subOrganization =
				await apiHelpers.headlessAdminUser.postOrganization({
					name: `Suborganization Name ${count}`,
					parentOrganization: {
						externalReferenceCode:
							parentOrganization.externalReferenceCode,
					},
				});

			subOrganizations.push(subOrganization);
		}

		await usersAndOrganizationsPage.goToOrganizations();

		await usersAndOrganizationsPage.organizationsTable
			.valueLink(parentOrganization.name)
			.click();

		for (const subOrganization of subOrganizations) {
			await expect(
				usersAndOrganizationsPage.organizationsTable.cell(
					subOrganization.name
				)
			).toBeVisible();
		}

		await expect(
			usersAndOrganizationsPage.organizationsTable.cell('Approved')
		).toBeVisible();

		await usersAndOrganizationsPage.goToOrganizations();

		await usersAndOrganizationsPage.organizationsTable.search(
			'Suborganization'
		);

		for (const subOrganization of subOrganizations) {
			await expect(
				usersAndOrganizationsPage.organizationsTable.cell(
					subOrganization.name
				)
			).toBeVisible();
		}
	}
);

test(
	'Add, view and delete organization, add comment, and assign global category to it.',
	{tag: '@LPD-57824'},
	async ({
		apiHelpers,
		editOrganizationPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		await page.on('dialog', (dialog) => dialog.accept());

		const orgName = 'Organization' + getRandomInt();

		const deleteOrganization = async () => {
			await usersAndOrganizationsPage.goToOrganizations();

			await usersAndOrganizationsPage.organizationsTable.search(orgName);
			await (
				await usersAndOrganizationsPage.organizationsTable.rowActions(
					orgName
				)
			).click();
			await usersAndOrganizationsPage.deleteOrganizationMenuItem.click();

			await waitForAlert(page);

			await usersAndOrganizationsPage.organizationsTable.search(orgName);

			await expect(
				usersAndOrganizationsPage.organizationsTableEmptyMessage
			).toBeVisible();
		};

		await test.step('Search and view organization created via API', async () => {
			await apiHelpers.headlessAdminUser.postOrganization({
				name: orgName,
			});

			await usersAndOrganizationsPage.goToOrganizations();

			await usersAndOrganizationsPage.organizationsTable.search(orgName);

			await expect(
				usersAndOrganizationsPage.organizationsTable.cell(orgName)
			).toBeVisible();
			await expect(
				usersAndOrganizationsPage.organizationsTable.cell(
					'Organization'
				)
			).toBeVisible();

			await (
				await usersAndOrganizationsPage.organizationsTable.rowActions(
					orgName
				)
			).click();
			await usersAndOrganizationsPage.editOrganizationMenuItem.click();

			await expect(editOrganizationPage.headerTitle).toHaveText(
				`Edit ${orgName}`
			);
			await expect(editOrganizationPage.nameInput).toHaveValue(orgName);
			await expect(editOrganizationPage.typeLabel).toHaveValue(
				'Organization'
			);

			await usersAndOrganizationsPage.goToOrganizations();

			await usersAndOrganizationsPage.organizationsTable.search(
				'Nonexistent Org'
			);

			await expect(
				usersAndOrganizationsPage.organizationsTable.cell(orgName)
			).not.toBeVisible();
		});

		await test.step('Delete organization created via API', async () => {
			await deleteOrganization();
		});

		await test.step('Set category and insert comment for organization created via UI', async () => {
			const companyId = await page.evaluate(() => {
				return Liferay.ThemeDisplay.getCompanyId();
			});

			const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
				companyId,
				companyId
			);

			const categoryName = 'Category' + getRandomInt();
			const vocabularyName = 'Vocabulary' + getRandomInt();

			try {
				await createCategories({
					apiHelpers,
					categoryNames: [{name: categoryName}],
					siteId: group.groupId,
					vocabularyName,
				});

				await usersAndOrganizationsPage.addOrganizationButton.click();
				await editOrganizationPage.nameInput.fill(orgName);
				await editOrganizationPage.countrySelect.selectOption(
					'United States'
				);
				await editOrganizationPage.regionSelect.selectOption(
					'California'
				);

				await expect(async () => {
					await editOrganizationPage
						.categoryInput(vocabularyName)
						.click();
					await editOrganizationPage
						.categoryOption(categoryName)
						.click({timeout: 1000});
				}).toPass();

				const comment = 'This is a test comment!';

				await editOrganizationPage.commentsInput.fill(comment);
				await editOrganizationPage.saveButton.click();

				await waitForAlert(page);

				await usersAndOrganizationsPage.goToOrganizations();

				await (
					await usersAndOrganizationsPage.organizationsTable.rowActions(
						orgName
					)
				).click();
				await usersAndOrganizationsPage.editOrganizationMenuItem.click();

				await expect(editOrganizationPage.commentsInput).toHaveValue(
					comment
				);
				await expect(
					editOrganizationPage.categoryGridCell(categoryName)
				).toBeVisible();
			}
			finally {
				const vocabulary =
					await apiHelpers.headlessAdminTaxonomy.getTaxonomyVocabularyBySiteId(
						group.groupId
					);

				await apiHelpers.headlessAdminTaxonomy.deleteTaxonomyVocabulary(
					vocabulary.id
				);
			}
		});

		await test.step('Delete organization created via UI', async () => {
			await deleteOrganization();
		});
	}
);

test(
	'Can view status when assigning an organization role',
	{tag: ['@@LPD-59032']},
	async ({apiHelpers, usersAndOrganizationsPage}) => {
		const organization =
			await apiHelpers.headlessAdminUser.postOrganization();

		await usersAndOrganizationsPage.goToOrganizations();
		await (
			await usersAndOrganizationsPage.organizationsTable.rowActions(
				organization.name
			)
		).click();
		await usersAndOrganizationsPage.assignOrganizationRolesMenuItem.click();

		await expect(
			await usersAndOrganizationsPage.assignOrganizationRolesTableStatus(
				'Account Manager',
				'Approved'
			)
		).toBeVisible();
	}
);

test(
	'Country and region should not be required for a default organization.',
	{tag: '@LPD-63206'},
	async ({editOrganizationPage, usersAndOrganizationsPage}) => {
		await usersAndOrganizationsPage.goToOrganizations();

		await usersAndOrganizationsPage.addOrganizationButton.click();

		await expect(editOrganizationPage.countrySelect).not.toHaveAttribute(
			'required'
		);
		await expect(editOrganizationPage.regionSelect).not.toHaveAttribute(
			'required'
		);
	}
);

test(
	'User should not be able to trigger stored XSS using organization name via info panel',
	{tag: ['@LPD-70029']},
	async ({
		editOrganizationPage,
		page,
		sitesAdminPage,
		usersAndOrganizationsPage,
	}) => {
		await usersAndOrganizationsPage.goToOrganizations();

		await usersAndOrganizationsPage.organizationsLink.click();
		await usersAndOrganizationsPage.addOrganizationButton.click();

		const organizationName =
			'"><img src=x onerror=prompt(document.cookie)></img>';

		await editOrganizationPage.nameInput.fill(organizationName);
		await editOrganizationPage.saveButton.click();

		await waitForAlert(page);

		try {
			await editOrganizationPage.organizationSiteLink.click();
			await editOrganizationPage.createSiteToggle.click();
			await editOrganizationPage.organizationSiteSaveButton.click();

			await waitForAlert(page);

			await sitesAdminPage.goto();
			await sitesAdminPage.searchSite(organizationName);

			await sitesAdminPage.infoPanelButton.click();

			await page.getByTitle('Select', {exact: true}).check();

			let dialogTriggered = false;

			await page
				.waitForEvent('dialog', {timeout: 500})
				.then(async (dialog) => {
					dialogTriggered = true;
					await dialog.dismiss();
				})
				.catch(() => {});

			await expect(sitesAdminPage.componentTitle).toContainText(
				organizationName
			);

			expect(dialogTriggered).toBe(false);
		}
		finally {
			page.once('dialog', async (dialog) => {
				await dialog.accept();
			});

			await usersAndOrganizationsPage.goToOrganizations();

			await (
				await usersAndOrganizationsPage.organizationsTable.rowActions(
					organizationName
				)
			).click();
			await usersAndOrganizationsPage.deleteOrganizationMenuItem.click();
		}
	}
);

test(
	'Text XSS vulnerability in country and region key value',
	{tag: '@LPD-72270'},
	async ({
		countriesManagementPage,
		editCountryPage,
		editOrganizationPage,
		page,
		usersAndOrganizationsPage,
	}) => {
		const xssString = `AnyName<img src=x onerror="alert('xssCountry')">`;

		await countriesManagementPage.goto();

		const country = {
			key: `${xssString}`,
			number: String(getRandomInt()),
			priority: '0',
			threeLetterIsocode: getRandomString().substring(0, 3),
			title: '',
			twoLetterIsocode: getRandomString().substring(0, 2),
		};

		await expect(async () => {
			await expect(
				countriesManagementPage.countriesTable.searchInput
			).toBeEditable();

			await countriesManagementPage.countriesTable.newButton.click();

			await expect(editCountryPage.titleInput).toBeVisible();
		}).toPass();

		await editCountryPage.editCountry(country);

		await usersAndOrganizationsPage.goToOrganizations();
		await usersAndOrganizationsPage.addOrganizationButton.click();

		const xssOrgName = `AnyName<img src=x onerror="alert('xssOrg')">`;

		await editOrganizationPage.nameInput.fill(xssOrgName);
		await editOrganizationPage.countrySelect.selectOption(`${country.key}`);
		await editOrganizationPage.saveButton.click();

		try {
			await usersAndOrganizationsPage.goToOrganizations();

			await usersAndOrganizationsPage.changeView('List');

			page.on('dialog', async (dialog) => {
				if (dialog.type() === 'alert') {
					throw new Error('XSS');
				}
			});

			await expect(
				page.getByText(xssOrgName, {exact: true})
			).toBeVisible();
			await expect(
				page.getByText(xssString, {exact: true})
			).toBeVisible();
		}
		finally {
			page.on('dialog', async (dialog) => await dialog.accept());

			await countriesManagementPage.goto();

			await expect(async () => {
				await countriesManagementPage.countriesTable.search(
					country.key
				);
				await (
					await countriesManagementPage.countriesTable.rowActions(
						country.key
					)
				).click();
				await countriesManagementPage.deleteButton.click({
					timeout: 500,
				});

				await waitForAlert(page);
			}).toPass();

			await usersAndOrganizationsPage.goToOrganizations();
			await usersAndOrganizationsPage.changeView('Table');
			await (
				await usersAndOrganizationsPage.organizationsTable.rowCheckbox(
					xssOrgName
				)
			).check();
			await usersAndOrganizationsPage.deleteButton.click();

			await waitForAlert(page);
		}
	}
);
