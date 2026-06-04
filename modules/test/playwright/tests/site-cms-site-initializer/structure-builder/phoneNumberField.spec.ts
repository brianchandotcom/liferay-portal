/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from '../main/fixtures/cmsPagesTest';
import {structureBuilderPagesTest} from './fixtures/structureBuilderPagesTest';
import {FieldType} from './pages/StructureBuilderPage';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-70672': {enabled: true},
		'LPD-83570': {enabled: true},
	}),
	loginTest(),
	pageEditorPagesTest,
	structureBuilderPagesTest
);

test(
	'Phone field disambiguates +1 (US vs CA), supports fixed prefix, and respects customized fragment configuration',
	{tag: ['@LPD-91059', '@LPD-91575']},
	async ({contentsPage, page, pageEditorPage, structureBuilderPage}) => {
		const structureLabel = `Structure${getRandomInt()}`;
		const contentTitle = getRandomString();

		// Create structure with a Phone Number field

		await structureBuilderPage.createStructureFromData({
			label: structureLabel,
			name: structureLabel,
			page: structureBuilderPage,
		});

		await structureBuilderPage.addField('Phone Number' as FieldType);

		await structureBuilderPage.changeFieldSettings({label: 'Phone'});

		const structureId = await structureBuilderPage.publishStructure();

		// Create content selecting United States and an unambiguous US area
		// code (212 = NYC)

		await contentsPage.goto();

		await contentsPage.createContent(structureLabel);

		await page.getByLabel('Title').fill(contentTitle);

		const phoneFragmentTrigger = page.getByLabel('Country Code');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: /United States/}),
			trigger: phoneFragmentTrigger,
		});

		const phoneInput = page.locator('input[type="tel"]');

		// Typing letters is rejected — only digits, spaces, dashes,
		// parentheses and dots survive

		await phoneInput.pressSequentially('abc212-555 (1234)def');

		await expect(phoneInput).toHaveValue('212-555 (1234)');

		await phoneInput.fill('2125551234');

		await contentsPage.saveContent();

		// Edit content again — libphonenumber-js should resolve +12125551234
		// to US (not CA, which shares +1)

		await contentsPage.editContent(contentTitle);

		await expect(
			phoneFragmentTrigger.locator('.lexicon-icon-en-us')
		).toBeVisible();

		await contentsPage.saveContent();

		// While the user picks the country, the editor does not offer the "Show
		// Prefix" configuration because the prefix picker is mandatory

		await structureBuilderPage.editStructure(structureId);

		await structureBuilderPage.customizeEditor();

		await pageEditorPage.selectFragment(
			await pageEditorPage.getFragmentId('Phone Number')
		);

		await pageEditorPage.goToConfigurationTab('General');

		await expect(
			page
				.getByRole('tabpanel', {name: 'General'})
				.getByLabel('Show Country Flag', {exact: true})
		).toBeVisible();

		await expect(
			page
				.getByRole('tabpanel', {name: 'General'})
				.getByLabel('Show Prefix', {exact: true})
		).toBeHidden();

		await pageEditorPage.publishPage();

		// Switch the structure prefix to fixed +44 (UK)

		await structureBuilderPage.editStructure(structureId);

		await structureBuilderPage.selectFields([{label: 'Phone'}]);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: 'Fixed'}),
			trigger: page.getByLabel('Country Source'),
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('option', {name: /United Kingdom/}),
			trigger: page.locator('.form-group-autofit button'),
		});

		await structureBuilderPage.publishStructure();

		// Edit content — the dropdown is replaced with a static +44 prefix
		// because the structure now enforces a fixed prefix

		await contentsPage.goto();

		await contentsPage.editContent(contentTitle);

		await expect(phoneFragmentTrigger).not.toBeVisible();

		await expect(page.getByText('+44', {exact: true})).toBeVisible();

		// Customize the editor — now that the prefix is fixed, the "Show Prefix"
		// configuration is offered; turning it off hides the static prefix

		await structureBuilderPage.editStructure(structureId);

		await structureBuilderPage.customizeEditor();

		const fragmentId = await pageEditorPage.getFragmentId('Phone Number');

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Show Prefix',
			fragmentId,
			tab: 'General',
			value: false,
		});

		await pageEditorPage.publishPage();

		// Edit content — the prefix is no longer rendered, only the bare phone
		// input remains

		await contentsPage.goto();

		await contentsPage.editContent(contentTitle);

		await expect(phoneFragmentTrigger).not.toBeVisible();

		await expect(page.getByText('+44', {exact: true})).not.toBeVisible();

		await expect(phoneInput).toBeVisible();

		// Cleanup

		await contentsPage.goto();

		await contentsPage.deleteContent(contentTitle);
	}
);
