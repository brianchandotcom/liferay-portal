/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../../fixtures/pageManagementSiteTest';
import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import getFormContainerDefinition from '../main/utils/getFormContainerDefinition';
import getFragmentDefinition from '../main/utils/getFragmentDefinition';
import getPageDefinition from '../main/utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	pageManagementSiteTest
);

test(
	'Can swap fragment and undo',
	{tag: '@LPD-62069'},
	async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

		// Create a page with a Form fragment

		const formId = getRandomString();

		const formDefinition = getFormContainerDefinition({
			id: formId,
		});

		const headingId = getRandomString();

		const headingDefinition = getFragmentDefinition({
			id: headingId,
			key: 'BASIC_COMPONENT-heading',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				formDefinition,
				headingDefinition,
			]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode and map the form to Lemon object, specifically to the "Lemon Size" field

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await pageEditorPage.mapFormFragment(formId, 'Lemon', ['Lemon Size']);

		// Change also fragment configuration

		const inputId = await pageEditorPage.getFragmentId('Text');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Label',
			fragmentId: inputId,
			tab: 'General',
			value: false,
		});

		await expect(
			page.locator('label', {hasText: 'Title'})
		).not.toBeVisible();

		// Check we can't swap the Heading

		await pageEditorPage.selectFragment(headingId);

		await expect(
			page
				.getByLabel('Configuration Panel')
				.locator('header', {hasText: 'Heading'})
		).toBeVisible();

		await expect(page.getByLabel('Swap Fragment')).not.toBeVisible();

		// Check swap fragment option is visible in topper

		await pageEditorPage.selectFragment(inputId);

		await clickAndExpectToBeVisible({
			target: page
				.locator('.dropdown-menu.show')
				.getByText('Swap Fragment', {exact: true}),
			trigger: page
				.locator('.page-editor__topper__item')
				.getByRole('button', {name: 'Options'}),
		});

		await clickAndExpectToBeHidden({
			target: page
				.locator('.dropdown-menu.show')
				.getByText('Swap Fragment', {exact: true}),
			trigger: page
				.locator('.page-editor__topper__item')
				.getByRole('button', {name: 'Options'}),
		});

		// Swap the text input fragment

		await pageEditorPage.swapFragment({
			folder: 'Form Components',
			fragmentId: inputId,
			fragmentName: 'Textarea',
		});

		// Check mapping and config persist

		const select = page
			.locator('.page-editor__item-configuration-sidebar')
			.getByLabel('Field', {exact: true});

		const selectedOption = select.locator('option:checked');

		await expect(selectedOption).toHaveText('Lemon Size');

		await expect(
			page.locator('label', {hasText: 'Title'})
		).not.toBeVisible();

		// Undo and check Text is visible again

		await pageEditorPage.undoAction();

		await expect(
			page
				.getByLabel('Configuration Panel')
				.locator('header', {hasText: 'Text'})
		).toBeVisible();
	}
);
