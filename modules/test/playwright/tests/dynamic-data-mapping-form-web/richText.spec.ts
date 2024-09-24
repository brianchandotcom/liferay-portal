/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {deleteItems} from './utils/deleteItems';

export const xssBypassTest = mergeTests(
	featureFlagsTest({
		'LPD-31212': true,
	}),
	loginTest(),
	formsPagesTest
);

export const xssDisabledTest = mergeTests(
	featureFlagsTest({
		'LPD-31212': false,
	}),
	loginTest(),
	formsPagesTest
);

[xssBypassTest, xssDisabledTest].forEach((testSuite) => {
	testSuite.afterEach(async ({formsPage, page}) => {
		await formsPage.goTo();

		await deleteItems(formsPage, page);
	});
});

const content = '<script>alert("Hello! I am an alert box!");</script>';
const sanitizedContent = '<script>;</script>';

const assertRichTextContent = async (
	formBuilderPage,
	formBuilderSidePanelPage,
	content,
	expected
) => {
	await formBuilderPage.goToNew();

	await expect(formBuilderPage.newFormHeading).toBeVisible();

	await formBuilderPage.fillFormTitle('Form' + getRandomInt());

	await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

	const newTabPagePromise = new Promise<Page>((resolve) =>
		formBuilderPage.page.once('popup', resolve)
	);

	await formBuilderPage.previewButton.click();

	const newTabPage = await newTabPagePromise;

	await newTabPage.waitForLoadState('domcontentloaded');

	const sourceButton = newTabPage.getByTitle('Source');

	await expect(sourceButton).toBeVisible();

	await sourceButton.click();

	const codeMirror = newTabPage.locator('.CodeMirror-scroll');

	await codeMirror.click();

	const textArea = newTabPage
		.getByLabel(
			'Rich Text Editor, _com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_ddm$$RichText'
		)
		.getByRole('textbox');

	await textArea.fill(content);

	const input = newTabPage
		.locator(
			'xpath=//input[starts-with(@name, "_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_ddm$$RichText") and @type="hidden"]'
		)
		.first();

	await expect(input).toHaveValue(expected);

	await newTabPage.close();
};

xssBypassTest(
	'Can add scripts to the rich text field @LPD-31212',
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		await assertRichTextContent(
			formBuilderPage,
			formBuilderSidePanelPage,
			content,
			content
		);
	}
);

xssDisabledTest(
	'Can not add scripts to the rich text field @LPD-31212',
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		await assertRichTextContent(
			formBuilderPage,
			formBuilderSidePanelPage,
			content,
			sanitizedContent
		);
	}
);
