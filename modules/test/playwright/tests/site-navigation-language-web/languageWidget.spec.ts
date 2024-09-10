/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {WidgetPagePage} from '../../pages/layout-admin-web/WidgetPagePage';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {expandSection} from '../../utils/expandSection';
import getRandomString from '../../utils/getRandomString';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

const selectCustomTemplate = async (
	page: Page,
	templateName: string,
	widgetPagePage: WidgetPagePage
) => {
	await widgetPagePage.clickOnAction('Language Selector', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Language Selector"]'
	);

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: configurationIFrame.getByRole('option', {
			exact: true,
			name: templateName,
		}),
		trigger: configurationIFrame.getByLabel('Display Template'),
	});

	await widgetPagePage.saveAndClose('Language Selector');
};

test('The user can choose which languages will be available to site via language selector widget', async ({
	apiHelpers,
	page,
	site,
	widgetPagePage,
}) => {

	// Add widget page and navigate to view

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	// Add language widget

	await widgetPagePage.addPortlet('Language Selector');

	await widgetPagePage.clickOnAction('Language Selector', 'Configuration');

	const configurationIFrame = page.frameLocator(
		'iframe[title*="Language Selector"]'
	);

	// Configure available languages

	await expandSection(
		configurationIFrame.getByRole('link', {name: 'Languages'})
	);

	const leftSelector = configurationIFrame.locator('.left-selector');
	const rightSelector = configurationIFrame.locator('.right-selector');

	const allOptions = await leftSelector.evaluate(
		(element: HTMLSelectElement) =>
			Array.from(element.options).map(({value}) => value)
	);

	await leftSelector.selectOption(allOptions);

	await configurationIFrame
		.getByTitle('Move selected items from Current to Available.')
		.click();

	await rightSelector.selectOption(['de_DE', 'en_US', 'es_ES']);

	await configurationIFrame
		.getByTitle('Move selected items from Available to Current.')
		.click();

	await configurationIFrame.getByRole('button', {name: 'Save'}).click();

	await waitForSuccessAlert(
		configurationIFrame,
		'Success:You have successfully updated the setup.'
	);

	await page
		.locator('.modal-header')
		.getByLabel('close', {exact: true})
		.click();

	// Assert available languages

	await clickAndExpectToBeVisible({
		autoClick: false,
		target: page.getByRole('menuitem', {name: 'deutsch-Deutschland'}),
		trigger: page.getByTitle('Select a Language', {exact: true}),
	});

	await expect(
		page.getByRole('menuitem', {name: 'deutsch-Deutschland'})
	).toBeVisible();

	await expect(
		page.getByRole('menuitem', {name: 'español-España'})
	).toBeVisible();

	// Configure icon template

	await selectCustomTemplate(page, 'Icon', widgetPagePage);

	await expect(
		page.locator('.current-language .lexicon-icon-en-us')
	).toBeVisible();

	await expect(
		page.getByRole('link').locator('.lexicon-icon-de-de')
	).toBeVisible();

	await expect(
		page.getByRole('link').locator('.lexicon-icon-es-es')
	).toBeVisible();

	// Configure long text template

	await selectCustomTemplate(page, 'Long Text', widgetPagePage);

	await expect(
		page.locator('span.language-entry-long-text', {hasText: 'english'})
	).toBeVisible();

	await expect(page.getByRole('link', {name: 'deutsch'})).toBeVisible();

	await expect(page.getByRole('link', {name: 'español'})).toBeVisible();

	// Configure select box template

	await selectCustomTemplate(page, 'Select Box', widgetPagePage);

	await expect(
		page.locator('option[value="en_US"][selected]')
	).toBeAttached();

	await expect(page.locator('option[value="de_DE"]')).toBeAttached();

	await expect(page.locator('option[value="es_ES"]')).toBeAttached();

	// Configure short text template

	await selectCustomTemplate(page, 'Short Text', widgetPagePage);

	await expect(
		page.locator('span.language-entry-short-text', {hasText: 'en'})
	).toBeVisible();

	await expect(
		page.getByRole('link', {exact: true, name: 'de'})
	).toBeVisible();

	await expect(
		page.getByRole('link', {exact: true, name: 'es'})
	).toBeVisible();
});
