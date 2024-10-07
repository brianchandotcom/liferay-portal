/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {liferayConfig} from '../../../liferay.config';
import {FormBuilderPage} from '../../../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormsPage} from '../../../pages/dynamic-data-mapping-form-web/FormsPage';
import performLogin from '../../../utils/performLogin';

export const test = mergeTests(
	loginTest(),
	formsPagesTest,
	virtualInstancesPagesTest
);

const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';

const defaultBaseUrl = liferayConfig.environment.baseUrl;

test.afterEach(async ({virtualInstancesPage}) => {
	liferayConfig.environment.baseUrl = defaultBaseUrl;

	await virtualInstancesPage.deleteVirtualInstance(
		DEFAULT_VIRTUAL_INSTANCE_NAME
	);
});

test.describe('Manage forms through submission page', () => {
	test('assert that data provider works on virtual instance', async ({
		browser,
		virtualInstancesPage,
	}) => {
		await virtualInstancesPage.addNewVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);

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
				'form-with-data-provider.portlet.lar'
			)
		);

		await newFormsPage.openForm(
			'Form with data provider on virtual instance'
		);

		const formBuilderPage = new FormBuilderPage(newPage);

		const pagePromise = newPage.waitForEvent('popup');

		await formBuilderPage.openFormSubmission();

		const formSubmissionPage = await pagePromise;

		await formSubmissionPage
			.getByLabel('Select from List', {exact: true})
			.click();

		await expect(
			formSubmissionPage.getByRole('option', {name: 'Algeria'})
		).toBeVisible();

		await formSubmissionPage.close();

		await newPage.close();
	});
});
