/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {deleteItems} from './utils/deleteItems';

const baseTest = mergeTests(formsPagesTest, loginTest());

const ckeditor5Test = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
	})
);

const xssBypassTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-31212': {enabled: true},
	})
);

const xssDisabledTest = mergeTests(
	baseTest,
	featureFlagsTest({
		'LPD-31212': {enabled: false},
	})
);

[ckeditor5Test, xssBypassTest, xssDisabledTest].forEach((testSuite) => {
	testSuite.afterEach(async ({formsPage}) => {
		await formsPage.goTo();

		await deleteItems(formsPage);
	});
});

baseTest(
	'Cannot see source button nor interact with the editor content when rich text field is read only',
	{tag: ['@LPD-55278']},
	async ({formBuilderPage, formBuilderSidePanelPage, formFieldsPage}) => {

		// Create and enter a new form

		await formBuilderPage.goToNew();

		// Add a rich text field

		await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

		// Rich Text Field Source Button Not Visible nor Editable

		await expect(formFieldsPage.richTextToolbar).toBeVisible();

		await expect(formFieldsPage.richTextSourceButton).toBeHidden();

		await expect(
			formFieldsPage.richTextFrame.locator('body')
		).toHaveAttribute('contenteditable', 'false');
	}
);

ckeditor5Test(
	'Added "Rich Text" field includes preview of editor',
	{
		tag: ['@LPD-11235'],
	},
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

		const editable = formBuilderSidePanelPage.page.getByRole('textbox', {
			name: 'Rich Text Editor',
		});
		await expect(editable).toBeVisible();
	}
);

const content = '<script>alert("Hello! I am an alert box!");</script>';
const sanitizedContent = '<script>;</script>';

const assertRichTextContent = async (content, expected, newTabPage) => {
	const sourceButton = newTabPage.getByTitle('Source');

	await expect(sourceButton).toBeVisible();

	await sourceButton.click();

	const codeMirror = newTabPage.locator('.CodeMirror-scroll');

	await codeMirror.click();

	const textArea = newTabPage
		.getByLabel('Rich Text, help text')
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

const createRichText = async (
	formBuilderPage,
	formBuilderSidePanelPage,
	helpText = 'help text'
) => {
	await formBuilderPage.goToNew();

	await expect(formBuilderPage.newFormHeading).toBeVisible();

	await formBuilderPage.fillFormTitle('Form' + getRandomInt());

	await formBuilderSidePanelPage.addFieldByDoubleClick('Rich Text');

	await formBuilderPage.helpText.fill(helpText);

	const formEntryPagePromisse = new Promise<Page>((resolve) =>
		formBuilderPage.page.once('popup', resolve)
	);

	await formBuilderPage.previewButton.click();

	const formEntryPage = await formEntryPagePromisse;

	await formEntryPage.waitForLoadState('domcontentloaded');

	return formEntryPage;
};

xssBypassTest(
	'Can add scripts to the rich text field @LPD-31212',
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		const formEntryPage = await createRichText(
			formBuilderPage,
			formBuilderSidePanelPage
		);

		await assertRichTextContent(content, content, formEntryPage);
	}
);

xssDisabledTest(
	'Can add help text to the rich text field @LPD-52535',
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		const helpText = getRandomString();

		const formEntryPage = await createRichText(
			formBuilderPage,
			formBuilderSidePanelPage,
			helpText
		);

		expect(formEntryPage.getByText(helpText, {exact: true})).toBeVisible();
	}
);

xssDisabledTest(
	'Can not add scripts to the rich text field @LPD-31212',
	async ({formBuilderPage, formBuilderSidePanelPage}) => {
		const formEntryPage = await createRichText(
			formBuilderPage,
			formBuilderSidePanelPage
		);

		await assertRichTextContent(content, sanitizedContent, formEntryPage);
	}
);
