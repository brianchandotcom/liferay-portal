/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {virtualInstancesPagesTest} from '../../fixtures/virtualInstancesPagesTest';
import {liferayConfig} from '../../liferay.config';
import {FormBuilderPage} from '../../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormsPage} from '../../pages/dynamic-data-mapping-form-web/FormsPage';
import performLogin, {performLogout} from '../../utils/performLogin';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(
	loginTest(),
	formsPagesTest,
	virtualInstancesPagesTest
);

let hasDataProvider: boolean = false;

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

test.afterEach(async ({formsPage, page}) => {
	await formsPage.goTo();

	await deleteItems(formsPage, page);

	if (hasDataProvider) {
		await page.waitForLoadState();

		await formsPage.dataProvidersTab.click();

		await deleteItems(formsPage, page);

		hasDataProvider = false;
	}
});

test.describe('Manage forms through submission page', () => {
	test('assert that data provider works on virtual instance', async ({
		browser,
		virtualInstancesPage,
	}) => {
		hasDataProvider = true;

		await virtualInstancesPage.addNewVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);

		const defaultBaseUrl = liferayConfig.environment.baseUrl;

		liferayConfig.environment.baseUrl = `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`;

		const newPage = await browser.newPage({
			baseURL: `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`,
		});

		await performLogin(
			newPage,
			'test',
			'?p_p_id=com_liferay_login_web_portlet_LoginPortlet&' +
				'p_p_state=maximized',
			`@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`
		);

		const newFormsPage = new FormsPage(newPage);

		await newFormsPage.goTo();

		await newFormsPage.importForm(
			path.join(
				__dirname,
				'dependencies',
				'form-with-select-field-data-provider.portlet.lar'
			)
		);

		await newFormsPage.openForm(
			'Form with data provider on virtual instance'
		);

		const formBuilderPage = new FormBuilderPage(newPage);

		const pagePromise = newPage.waitForEvent('popup');

		await formBuilderPage.openFormSubmission();

		const formPreviewPage = await pagePromise;

		await formPreviewPage
			.getByLabel('Select from List', {exact: true})
			.click();

		await expect(
			formPreviewPage.getByRole('option', {name: 'Algeria'})
		).toBeVisible();

		await formPreviewPage.close();

		await performLogout(newPage);

		await newPage.close();

		liferayConfig.environment.baseUrl = defaultBaseUrl;

		await virtualInstancesPage.deleteVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);
	});

	test('can submit manual entry while using data provider autofill rule', async ({
		context,
		formBuilderPage,
		formsPage,
		page,
	}) => {
		hasDataProvider = true;

		await formsPage.goTo();

		await formsPage.importForm(
			path.join(
				__dirname,
				'dependencies',
				'form-with-data-provider.portlet.lar'
			)
		);

		await formsPage.openForm('Form with data provider');

		const pagePromise = context.waitForEvent('page');

		await formBuilderPage.openFormSubmission();

		const formSubmissionPage = await pagePromise;

		await formSubmissionPage.getByLabel('Population').fill('123456');

		await formSubmissionPage.getByRole('button', {name: 'Submit'}).click();

		await expect(
			formSubmissionPage.getByText(
				'Your information was successfully received. Thank you for filling out the form.'
			)
		).toBeVisible();

		await formSubmissionPage.close();

		await formBuilderPage.entriesTab.click();

		await expect(page.getByText('123456')).toBeVisible();
	});
});
