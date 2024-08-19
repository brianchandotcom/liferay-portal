/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {expandSection} from '../../../utils/expandSection';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import {BlogsPage} from './BlogsPage';

import type {postTaxonomyVocabularyTaxonomyCategoryProps} from '../../../helpers/HeadlessAdminTaxonomyApiHelper';

type editBlogEntryAddfriendlyUrlType = {
	categories: Pick<postTaxonomyVocabularyTaxonomyCategoryProps, 'name'>[];
	vocabularyName: string;
};

export class BlogsEditBlogEntryPage {
	readonly page: Page;

	readonly blogsPage: BlogsPage;
	readonly publishButton: Locator;
	readonly contentEditor: Locator;
	readonly submitToWorkflowButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.blogsPage = new BlogsPage(page);
		this.contentEditor = page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_contentEditor.cke_editable'
		);
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.submitToWorkflowButton = page.getByRole('button', {
			name: 'Submit for Workflow',
		});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.blogsPage.goto(siteUrl);
		await this.blogsPage.goToCreateBlogEntry();
	}

	private async editBlogEntryAddCategories({
		categories,
		vocabularyName,
	}: editBlogEntryAddfriendlyUrlType) {
		const fieldset = await this.page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_categorization'
		);

		if (await fieldset.locator('.panel-body').isHidden()) {
			await fieldset
				.getByRole('button', {name: 'Categorization'})
				.click();
		}

		await fieldset.getByLabel(`Select ${vocabularyName}`).click();

		const categoriesSelectorIframe = await this.page.frameLocator(
			`iframe[title="Select ${vocabularyName}"]`
		);

		for (const {name} of categories) {
			await categoriesSelectorIframe
				.locator('.treeview-item')
				.filter({hasText: name})
				.getByRole('checkbox')
				.check();
		}

		await this.page.getByRole('button', {name: 'Done'}).click();
	}

	private async editBlogEntryAddfriendlyUrl({
		categories,
		vocabularyName,
	}: editBlogEntryAddfriendlyUrlType) {
		await this.editBlogEntryAddCategories({
			categories,
			vocabularyName,
		});

		const fieldset = await this.page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_friendly-url'
		);

		if (await fieldset.locator('.panel-body').isHidden()) {
			await fieldset.getByRole('button', {name: 'Friendly URL'}).click();
		}

		await fieldset.getByText('Use a Customized URL').click();

		await this.page
			.getByLabel('Available')
			.selectOption(categories.map(({name}) => ({label: name})));

		await this.page.getByLabel('Transfer Item Left to Right').click();
	}

	async editBlogEntry({
		content,
		friendlyUrl,
		publish = true,
		submitToWorkflow,
		title,
	}: {
		content: string;
		friendlyUrl?: editBlogEntryAddfriendlyUrlType;
		publish?: boolean;
		submitToWorkflow?: boolean;
		title: string;
	}) {
		await this.page.getByPlaceholder('Title *').fill(title);

		await this.contentEditor.fill(content);

		if (friendlyUrl) {
			const {categories, vocabularyName} = friendlyUrl;

			await this.editBlogEntryAddfriendlyUrl({
				categories,
				vocabularyName,
			});
		}

		if (submitToWorkflow) {
			await this.submitBlogEntryToWorkflow();
		}
		else if (publish) {
			await this.publishBlogEntry();
		}
	}

	async publishBlogEntry() {
		await this.publishButton.click();
		await waitForSuccessAlert(this.page);
	}

	async selectSpecificDisplayPage(displayPageName: string) {
		const displayPageFieldSet = this.page.locator('fieldset', {
			hasText: 'Display Page',
		});

		await expandSection(displayPageFieldSet);
		await displayPageFieldSet
			.getByLabel('Display Page Template')
			.selectOption('Specific');
		await displayPageFieldSet.getByRole('button', {name: 'Select'}).click();
		const selectDisplayPageModal = await this.page.frameLocator(
			'iframe[title*="Select Page"]'
		);
		await this.page
			.locator('.modal-title', {
				hasText: 'Select Page',
			})
			.waitFor({
				state: 'visible',
			});

		await clickAndExpectToBeHidden({
			target: this.page.locator('.modal-title', {
				hasText: 'Select Page',
			}),
			trigger: selectDisplayPageModal.getByLabel(
				'Select ' + displayPageName
			),
		});
	}

	async submitBlogEntryToWorkflow() {
		await this.submitToWorkflowButton.click();
		await waitForSuccessAlert(this.page);
	}
}
