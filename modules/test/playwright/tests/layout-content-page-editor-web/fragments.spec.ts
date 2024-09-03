/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../fixtures/displayPageTemplatesPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {masterPagesPagesTest} from '../../fixtures/masterPagesPagesTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageManagementSiteTest} from '../../fixtures/pageManagementSiteTest';
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import {checkAccessibility} from '../../utils/checkAccessibility';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import dragAndDropElement from '../../utils/dragAndDropElement';
import getGlobalSiteId from '../../utils/getGlobalSiteId';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';
import getBasicWebContentStructureId, {
	getWebContentStructureId,
} from '../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {
	ANIMAL_DDM_STRUCTURE_KEY,
	ANIMAL_DDM_TEMPLATE_KEY,
	LEMON_BASKET_OBJECT_ERC,
	LEMON_OBJECT_ERC,
} from '../setup/page-management-site/constants';
import getContainerDefinition from './utils/getContainerDefinition';
import getFormContainerDefinition from './utils/getFormContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	displayPageTemplatesPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	masterPagesPagesTest,
	objectPagesTest,
	pageEditorPagesTest,
	pageManagementSiteTest
);

const ENTER_KEY = 'Enter';

test.describe('Content Display Fragment', () => {
	const CONTENT_DISPLAY_FRAGMENT_HTML = `<div class="content-display-fragment">
		[#if itemSelectorNameObject??]
			\${itemSelectorNameObject.getTitle()}
		[#else]
			<div class="portlet-msg-info">The selected content will be shown here.</div>
		[/#if]
	</div>`;

	function getContentDisplayFragmentConfiguration({
		itemSubtype,
		itemType,
	}: {
		itemSubtype?: string;
		itemType: string;
	}): FragmentConfiguration {
		return {
			fieldSets: [
				{
					fields: [
						{
							label: 'Item',
							name: 'itemSelectorName',
							type: 'itemSelector',
							typeOptions: {
								enableSelectTemplate: true,
								itemSubtype,
								itemType,
							},
						},
					],
				},
			],
		};
	}

	test('Does not show alert when accessing a page with a web content display mapped to a restricted web content', async ({
		apiHelpers,
		browser,
		journalPage,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a web content restricted to site members

		await journalPage.goto(site.friendlyUrlPath);
		await journalPage.goToCreateArticle();

		await journalPage.setArticleViewableBy('Site Members');

		const articleTitle = getRandomString();
		const articleContent = 'My article';

		await journalPage.fillArticleData(articleTitle, articleContent);
		await journalPage.publishArticle();

		await expect(
			page.getByLabel('Not Visible to Guest Users')
		).toBeVisible();

		// Create a page with a content display fragment and go to edit mode

		const contentDisplayId = getRandomString();

		const contentDisplayDefinition = getFragmentDefinition({
			fragmentConfig: {
				itemSelector: {
					template: {
						infoItemRendererKey:
							'com.liferay.journal.web.internal.info.item.renderer.JournalArticleFullContentInfoItemRenderer',
					},
				},
			},
			id: contentDisplayId,
			key: 'com.liferay.fragment.internal.renderer.ContentObjectFragmentRenderer',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([contentDisplayDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Map the content display fragment to the created web content and publish the page

		await pageEditorPage.selectFragment(contentDisplayId);

		await pageEditorPage.setMappedItem({
			entity: 'Web Content',
			entry: articleTitle,
		});

		await pageEditorPage.publishPage();

		// Navigate to page in incognito mode to simulate not logged user

		const context = await browser.newContext();

		const incognitoPage = await context.newPage();

		await incognitoPage.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		// Check the content is not displayed and no alert is shown

		await expect(incognitoPage.getByText(articleContent)).not.toBeVisible();
		await expect(incognitoPage.getByRole('alert')).not.toBeVisible();
	});

	test(
		'Can only select Documents and Media when set itemType to FileEntry',
		{
			tag: ['@LPS-97182', '@LPS-100545', '@LPS-101249'],
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create a fragment with itemSelector configuration for file entries

			const {fragmentCollectionId} =
				await apiHelpers.jsonWebServicesFragmentCollection.addFragmentCollection(
					{
						groupId: pageManagementSite.id,
						name: getRandomString(),
					}
				);

			const fragmentEntryName = getRandomString();

			await apiHelpers.jsonWebServicesFragmentEntry.addFragmentEntry({
				configuration: getContentDisplayFragmentConfiguration({
					itemType:
						'com.liferay.portal.kernel.repository.model.FileEntry',
				}),
				fragmentCollectionId,
				groupId: pageManagementSite.id,
				html: CONTENT_DISPLAY_FRAGMENT_HTML,
				name: fragmentEntryName,
			});

			// Create a content page with Wem Site's Apple fragment

			const fragmentName = getRandomString();

			const fragmentDefinition = getFragmentDefinition({
				id: fragmentName,
				key: fragmentEntryName,
			});

			// Create a content page and go to edit mode

			const layoutTitle = getRandomString();

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([fragmentDefinition]),
				siteId: pageManagementSite.id,
				title: layoutTitle,
			});

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Assert only documents and media is shown

			await pageEditorPage.selectFragment(fragmentName);

			await pageEditorPage.openMappingSelector();

			const iframe = page.frameLocator('iframe[title="Select"]');

			await expect(iframe.getByRole('menubar')).not.toBeVisible();

			await expect(
				iframe.getByTitle('poodle.jpg', {exact: true})
			).toBeVisible();

			await page
				.getByRole('dialog')
				.getByLabel('close', {exact: true})
				.click();

			// Select specific document and media file

			await pageEditorPage.setMappedItem({
				entity: 'Documents and Media',
				entry: 'poodle.jpg',
				entryLocator: page
					.frameLocator('iframe[title="Select"]')
					.getByText('poodle.jpg', {exact: false}),
			});

			await expect(
				page
					.locator('.content-display-fragment')
					.getByText('poodle.jpg')
			).toBeVisible();

			// Remove the page

			await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);

			// Remove fragment set

			await apiHelpers.jsonWebServicesFragmentCollection.deleteFragmentCollection(
				fragmentCollectionId
			);
		}
	);

	test(
		'Can only select web content of type animal when set itemSubtype to animal',
		{
			tag: ['@LPS-97182', '@LPS-100545', '@LPS-101249'],
		},
		async ({apiHelpers, page, pageEditorPage, pageManagementSite}) => {

			// Create animal web content

			const animalWebContentTitle = getRandomString();

			const animalWebContentStructureId = await getWebContentStructureId(
				apiHelpers,
				pageManagementSite.id,
				ANIMAL_DDM_STRUCTURE_KEY
			);

			const {articleId: animalWebContentId} =
				await apiHelpers.jsonWebServicesJournal.addWebContent({
					ddmStructureId: animalWebContentStructureId,
					ddmTemplateKey: ANIMAL_DDM_TEMPLATE_KEY,
					groupId: pageManagementSite.id,
					titleMap: {en_US: animalWebContentTitle},
				});

			// Create basic web content

			const basicWebContentTitle = getRandomString();

			const basicWebContentStructureId =
				await getBasicWebContentStructureId(apiHelpers);

			const {articleId: basicWebContentId} =
				await apiHelpers.jsonWebServicesJournal.addWebContent({
					ddmStructureId: basicWebContentStructureId,
					groupId: pageManagementSite.id,
					titleMap: {en_US: basicWebContentTitle},
				});

			// Create a fragment with itemSelector configuration for animals

			const {fragmentCollectionId} =
				await apiHelpers.jsonWebServicesFragmentCollection.addFragmentCollection(
					{
						groupId: pageManagementSite.id,
						name: getRandomString(),
					}
				);

			const fragmentEntryName = getRandomString();

			await apiHelpers.jsonWebServicesFragmentEntry.addFragmentEntry({
				configuration: getContentDisplayFragmentConfiguration({
					itemSubtype: ANIMAL_DDM_STRUCTURE_KEY,
					itemType: 'com.liferay.journal.model.JournalArticle',
				}),
				fragmentCollectionId,
				groupId: pageManagementSite.id,
				html: CONTENT_DISPLAY_FRAGMENT_HTML,
				name: fragmentEntryName,
			});

			// Create a content page with Wem Site's Apple fragment

			const fragmentName = getRandomString();

			const fragmentDefinition = getFragmentDefinition({
				id: fragmentName,
				key: fragmentEntryName,
			});

			// Create a content page and go to edit mode

			const layoutTitle = getRandomString();

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition([fragmentDefinition]),
				siteId: pageManagementSite.id,
				title: layoutTitle,
			});

			await pageEditorPage.goto(
				layout,
				pageManagementSite.friendlyUrlPath
			);

			// Assert basic web content is not showed and animal is showed

			await pageEditorPage.selectFragment(fragmentName);

			await pageEditorPage.openMappingSelector();

			const iframe = page.frameLocator('iframe[title="Select"]');

			await expect(iframe.getByRole('menubar')).not.toBeVisible();

			await expect(
				iframe
					.getByRole('paragraph')
					.filter({hasText: animalWebContentTitle})
			).toBeVisible();

			await expect(
				iframe
					.getByRole('paragraph')
					.filter({hasText: basicWebContentTitle})
			).not.toBeVisible();

			await page
				.getByRole('dialog')
				.getByLabel('close', {exact: true})
				.click();

			// Select animal

			await pageEditorPage.setMappedItem({
				entity: 'Web Content',
				entry: animalWebContentTitle,
			});

			await expect(page.locator('.content-display-fragment')).toHaveText(
				animalWebContentTitle
			);

			await pageEditorPage.publishPage();

			// Go to view mode of the created page

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			await expect(page.locator('.content-display-fragment')).toHaveText(
				animalWebContentTitle
			);

			// Remove web contents

			expect(
				await apiHelpers.jsonWebServicesJournal.moveArticleToTrash(
					pageManagementSite.id,
					animalWebContentId
				)
			).toHaveProperty('articleId');

			expect(
				await apiHelpers.jsonWebServicesJournal.moveArticleToTrash(
					pageManagementSite.id,
					basicWebContentId
				)
			).toHaveProperty('articleId');

			// Remove the page

			await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);

			// Remove fragment set

			await apiHelpers.jsonWebServicesFragmentCollection.deleteFragmentCollection(
				fragmentCollectionId
			);
		}
	);
});

test.describe('Dropdown Fragment', () => {
	async function openDropdownAndCheckStyle(
		pageEditorPage: PageEditorPage,
		dropdownId: string,
		isDesktop = true,
		style: string,
		value: string
	) {

		// Select dropdown

		await pageEditorPage.selectFragment(dropdownId, isDesktop);

		const dropdownFragment = pageEditorPage.getFragment(
			dropdownId,
			isDesktop
		);

		const dropdownButton = dropdownFragment.locator(
			'.dropdown-fragment-toggle'
		);

		// Open dropdown

		await dropdownButton.press(ENTER_KEY);

		const dropdownMenu = dropdownFragment.locator(
			'.dropdown-fragment-menu'
		);

		await dropdownMenu.waitFor();

		// Check style

		expect(
			await dropdownMenu.evaluate((element, style) => {
				return window.getComputedStyle(element).getPropertyValue(style);
			}, style)
		).toBe(value);

		// Close dropdown

		await dropdownButton.press(ENTER_KEY);

		await dropdownMenu.waitFor({state: 'hidden'});
	}

	test('Check the functionality of the Dropdown fragment', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create content page with a Dropdown fragment inside a Container

		const dropdownId = getRandomString();

		const dropdownDefinition = getFragmentDefinition({
			id: dropdownId,
			key: 'BASIC_COMPONENT-dropdown',
		});

		const containerWidth = '300px';

		const containerDefinition = getContainerDefinition({
			fragmentStyle: {width: containerWidth},
			id: getRandomString(),
			pageElements: [dropdownDefinition],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([containerDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Change the dropdown button text

		await pageEditorPage.selectFragment(dropdownId);

		await pageEditorPage.editTextEditable(
			dropdownId,
			'dropdown-text',
			'My Dropdown'
		);

		const dropdownButton = page
			.locator('button')
			.filter({hasText: 'My Dropdown'});

		const dropdownMenu = page.locator('.dropdown-fragment-menu');

		// Check that the dropdown menu opens when hovering over the fragment

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Display on Hover',
			fragmentId: dropdownId,
			tab: 'General',
			value: true,
		});

		await dropdownButton.hover();

		await expect(dropdownButton).toHaveAttribute('aria-expanded', 'true');
		await expect(dropdownMenu).toBeVisible();

		await page.locator('#banner.page-editor__disabled-area').hover();

		await expect(dropdownButton).toHaveAttribute('aria-expanded', 'false');
		await expect(dropdownMenu).not.toBeVisible();

		// Check that the dropdown menu always keeps open in edit mode

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Keep Panel Open in Edit Mode',
			fragmentId: dropdownId,
			tab: 'General',
			value: true,
		});

		await pageEditorPage.publishPage();

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(dropdownButton).toHaveAttribute('aria-expanded', 'true');
		await expect(dropdownMenu).toBeVisible();

		// Change the Panel Type config and check that it works correctly

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Panel Type',
			fragmentId: dropdownId,
			tab: 'General',
			value: 'Full Width',
		});

		await expect(dropdownMenu).toHaveCSS('width', containerWidth);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Panel Type',
			fragmentId: dropdownId,
			tab: 'General',
			value: 'Mega Menu',
		});

		const layoutWidth = await page
			.locator('.page-editor__layout-viewport')
			.evaluate((element) => element.getBoundingClientRect().width);

		await expect(dropdownMenu).toHaveCSS('width', `${layoutWidth}px`);

		// Check that a fragment can be added to the dropzone

		await pageEditorPage.addFragment(
			'Basic Components',
			'Heading',
			page.getByText('Place fragments or widgets here.', {exact: true})
		);

		await expect(page.getByText('Heading Example')).toBeVisible();
	});

	test('Check dropdown menu is displayed correctly in all resolutions', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create content page with a Dropdown fragment and go to edit mode

		const dropdownId = getRandomString();

		const fragmentDefinition = getFragmentDefinition({
			cssClasses: ['d-flex', 'justify-content-end'],
			id: dropdownId,
			key: 'BASIC_COMPONENT-dropdown',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragmentDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Check dropdown style in all viewports

		await openDropdownAndCheckStyle(
			pageEditorPage,
			dropdownId,
			true,
			'right',
			'280px'
		);

		await pageEditorPage.switchViewport('Portrait Phone');

		await openDropdownAndCheckStyle(
			pageEditorPage,
			dropdownId,
			false,
			'right',
			'0px'
		);

		await pageEditorPage.goToConfigurationTab('Advanced');

		await page.getByRole('button', {name: 'Clear All'}).click();

		await pageEditorPage.waitForChangesSaved();

		await openDropdownAndCheckStyle(
			pageEditorPage,
			dropdownId,
			false,
			'left',
			'0px'
		);
	});
});

test.describe('HTML Fragment', () => {
	test('Can edit html editable', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create page with a HTML fragment and go to edit mode

		const fragmentId = getRandomString();

		const fragment = getFragmentDefinition({
			id: fragmentId,
			key: 'BASIC_COMPONENT-html',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([fragment]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Check html editable can be edited

		await pageEditorPage.editHTMLEditable(
			fragmentId,
			'element-html',
			'<div class="text-success"><h1>test html</h1></div>'
		);

		await expect(page.getByText('test html')).toBeAttached();
	});
});

test.describe('Multiselect Fragment', () => {
	test(
		'Allow submit form if the field is required and at least one item is checked',
		{tag: '@LPD-32038'},
		async ({
			context,
			displayPageTemplatesPage,
			page,
			pageEditorPage,
			pageManagementSite,
		}) => {

			// Create a display page for the Lemon Basket objects

			await displayPageTemplatesPage.goto(
				pageManagementSite.friendlyUrlPath
			);

			const displayPageTemplateName = getRandomString();

			await displayPageTemplatesPage.createTemplate({
				contentType: 'Lemon Basket',
				name: displayPageTemplateName,
			});

			displayPageTemplatesPage.editTemplate(displayPageTemplateName);

			// Add a form container and map it

			await pageEditorPage.addFragment(
				'Form Components',
				'Form Container'
			);

			const fragmentId =
				await pageEditorPage.getFragmentId('Form Container');

			await pageEditorPage.mapFormFragment(
				fragmentId,
				'Lemon Basket (Default)'
			);

			// Preview the page with a created object

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {
					name: 'Select Other Item',
				}),
				trigger: page.getByLabel('Preview With'),
			});

			await clickAndExpectToBeHidden({
				target: page.locator('.modal-dialog'),
				trigger: page
					.frameLocator('iframe[title="Select"]')
					.getByText('plastic', {exact: false}),
			});

			const pagePromise = context.waitForEvent('page');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {
					name: 'Preview in a New Tab',
				}),
				trigger: page
					.locator('.control-menu-nav-item')
					.getByLabel('Options', {exact: true}),
			});

			// Extract classNameId and classPK to build the display page url

			const newPage = await pagePromise;

			const pageURL = new URL(newPage.url());

			const classNameId = pageURL.searchParams.get('classNameId');
			const classPK = pageURL.searchParams.get('classPK');

			await displayPageTemplatesPage.publishTemplate();

			// Go to display page url and try the form

			await page.goto(
				`/web${pageManagementSite.friendlyUrlPath}/e/${displayPageTemplateName}/${classNameId}/${classPK}`
			);

			await page.getByText('Submit', {exact: true}).click();

			await expect(
				page.getByText(
					'Thank you. Your information was successfully received.'
				)
			).toBeVisible();
		}
	);
});

test.describe('Slider Fragment', () => {
	test('Checks that the Slider fragment works correctly', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {
		const expectSlideIsActive = async (name: string) => {
			await expect(page.getByLabel(name, {exact: true})).toHaveClass(
				/active/
			);
			await expect(page.getByLabel(`Go to ${name}`)).toHaveAttribute(
				'aria-current',
				'true'
			);
		};

		const expectSlideIsNotActive = async (name: string) => {
			await expect(page.getByLabel(name, {exact: true})).not.toHaveClass(
				/active/
			);
			await expect(page.getByLabel(`Go to ${name}`)).toHaveAttribute(
				'aria-current',
				'false'
			);
		};

		// Create page with Slider fragment and go to edit mode

		const sliderId = getRandomString();

		const sliderDefinition = getFragmentDefinition({
			fragmentConfig: {
				numberOfSlides: 3,
			},
			fragmentFields: [
				{
					id: '02-02-title',
					value: {
						text: {
							value_i18n: {
								en_US: 'Slide 2',
							},
						},
					},
				},
				{
					id: '01-02-title',
					value: {
						text: {
							value_i18n: {
								en_US: 'Slide 1',
							},
						},
					},
				},
			],
			id: sliderId,
			key: 'BASIC_COMPONENT-slider',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([sliderDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Change the number of slides

		const slide = page.locator('[aria-roledescription="slide"]');

		expect(await slide.all()).toHaveLength(3);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Number of Slides',
			fragmentId: sliderId,
			tab: 'General',
			value: '2',
		});

		expect(await slide.all()).toHaveLength(2);

		// Check Auto Hide Play button

		const playButton = page.locator('.stopped');

		await expect(playButton).not.toHaveClass(
			/carousel-toggle-button--always-visible/
		);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Auto Hide Play Button',
			fragmentId: sliderId,
			tab: 'General',
			value: false,
		});

		await expect(playButton).toHaveClass(
			/carousel-toggle-button--always-visible/
		);

		await pageEditorPage.publishPage();

		// Go to view mode

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		// Pause the carousel

		await expect(page.getByText('Stop slide rotation')).toBeVisible();

		await page.locator('.carousel-toggle-button.playing').click();

		await expect(page.getByText('Start slide rotation')).toBeVisible();

		// Check the slide 1 is active

		await expectSlideIsActive('Slide 1');
		await expectSlideIsNotActive('Slide 2');

		// Click next slide and check the slide 2 is active

		await page.getByLabel('Next Slide').click();

		await expectSlideIsActive('Slide 2');
		await expectSlideIsNotActive('Slide 1');

		// Click previous slide and check the slide 1 is active

		await page.getByLabel('Previous Slide').click();

		await expectSlideIsActive('Slide 1');
		await expectSlideIsNotActive('Slide 2');

		// Check accessibility

		await checkAccessibility({page, selectors: ['.component-slider']});
	});
});

test.describe('Tabs Fragment', () => {
	const getTab = (tabName: string, page: Page) =>
		page.locator('.nav-item button', {
			has: page.getByText(tabName),
		});

	test('Checks that the Tabs fragment works correctly and has the correct semantics in small resolution', async ({
		apiHelpers,
		page,
		site,
	}) => {

		// Create page with a Tabs fragment

		const tabsDefinition = getFragmentDefinition({
			fragmentConfig: {
				numberOfTabs: 2,
			},
			fragmentFields: [
				{
					id: 'title1',
					value: {},
				},
				{
					id: 'title2',
					value: {},
				},
			],
			id: getRandomString(),
			key: 'BASIC_COMPONENT-tabs',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([tabsDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Set small resolution and go to view mode

		await page.setViewportSize({height: 600, width: 600});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		let dropdownButton = page.getByLabel('Current Selection: Tab 1');

		await expect(dropdownButton).toHaveAttribute(
			'aria-activedescendant',
			''
		);
		await expect(dropdownButton).toHaveAttribute('aria-expanded', 'false');
		await expect(dropdownButton).toHaveAttribute(
			'aria-haspopup',
			'listbox'
		);
		await expect(dropdownButton).toHaveAttribute('role', 'combobox');

		// Open the dropdown and navigate by keyboard to select the Tab 2

		await dropdownButton.press('Enter');

		await expect(dropdownButton).toHaveAttribute('aria-expanded', 'true');

		await page.keyboard.press('Tab');
		await page.keyboard.press('Tab');
		await page.keyboard.press('Enter');

		dropdownButton = page.getByLabel('Current Selection: Tab 2');

		// Check that the button has the correct text and the focus when the tab is selected

		expect((await dropdownButton.textContent()).trim()).toBe('Tab 2');

		await expect(dropdownButton).toBeFocused();

		// Check accessibility

		await checkAccessibility({page, selectors: ['.component-tabs']});
	});

	test('Check the Persist Selected Tab configuration', async ({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create page with a Tabs fragment

		const tabsId = getRandomString();

		const tabsDefinition = getFragmentDefinition({
			fragmentConfig: {
				numberOfTabs: 2,
			},
			fragmentFields: [
				{
					id: 'title1',
					value: {},
				},
				{
					id: 'title2',
					value: {},
				},
			],
			id: tabsId,
			key: 'BASIC_COMPONENT-tabs',
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([tabsDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		const firstTab = getTab('Tab 1', page);

		const secondTab = getTab('Tab 2', page);

		// Check that the first tab is activated

		await expect(firstTab).toHaveAttribute('aria-selected', 'true');
		await expect(secondTab).toHaveAttribute('aria-selected', 'false');

		// Select the second tab and refresh the page to check if the selection persists

		await secondTab.press(ENTER_KEY);

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(firstTab).toHaveAttribute('aria-selected', 'false');
		await expect(secondTab).toHaveAttribute('aria-selected', 'true');

		// Change the configuration so that the selection does not persist

		await pageEditorPage.selectFragment(tabsId);

		await pageEditorPage.changeFragmentConfiguration({
			fieldLabel: 'Persist Selected Tab',
			fragmentId: tabsId,
			tab: 'General',
			value: false,
		});

		// Refresh the page and check that the selection does not persist

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await expect(secondTab).toHaveAttribute('aria-selected', 'false');
		await expect(firstTab).toHaveAttribute('aria-selected', 'true');
	});

	test('Check that tabs have their corresponding content on a master page', async ({
		apiHelpers,
		masterPagesPage,
		page,
		pageEditorPage,
		site,
	}) => {

		// Create a master page and add a Tabs fragment

		const masterPageName = getRandomString();

		await apiHelpers.jsonWebServicesLayoutPageTemplateEntry.addLayoutPageTemplateEntry(
			{
				groupId: site.id,
				name: masterPageName,
				type: 'master-layout',
			}
		);

		await masterPagesPage.goto(site.friendlyUrlPath);

		await masterPagesPage.editMaster(masterPageName);

		await pageEditorPage.addFragment('Basic Components', 'Tabs');

		// Add the main drop zone on the first tab and a Heading fragment in the second tab

		const tabDropZone = page
			.getByText('Place fragments or widgets here.')
			.first();

		await dragAndDropElement({
			dragTarget: page.locator('[data-name="Drop Zone"]'),
			dropTarget: tabDropZone,
			page,
		});

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		const secondTab = getTab('Tab 2', page);

		await secondTab.press('Enter');

		await tabDropZone.waitFor();

		await dragAndDropElement({
			dragTarget: page.locator('[data-name="Heading"]'),
			dropTarget: page
				.getByText('Place fragments or widgets here.')
				.first(),
			page,
		});

		// Check that each tab has the corresponding content

		const firstTab = getTab('Tab 1', page);

		await firstTab.press('Enter');

		await expect(
			page.getByText(
				'Fragments and widgets for pages based on this master will be placed here.'
			)
		).toBeVisible();

		await secondTab.press('Enter');

		await expect(page.getByText('Heading Example')).toBeVisible();
	});
});

test.describe('Tags Fragment', () => {
	test('Uses Tags fragment for Forms in a Content Page', async ({
		apiHelpers,
		page,
		pageManagementSite,
	}) => {

		// Get the id of Lemon object from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_OBJECT_ERC
			);

		// Create a Form Container with a Tags fragment and Submit fragment

		const firstTagsFragmentDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
		});

		const secondTagsFragmentDefinition = getFragmentDefinition({
			id: getRandomString(),
			key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
		});

		const submitFragmentDefinition = getFragmentDefinition({
			fragmentConfig: {
				buttonSize: 'nm',
				buttonType: 'primary',
				submittedEntryStatus: 'approved',
			},
			fragmentFields: [
				{
					id: 'submit-button-text',
					value: {
						fragmentLink: {},
					},
				},
			],
			id: getRandomString(),
			key: 'INPUTS-submit-button',
		});

		const formDefinition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [
				firstTagsFragmentDefinition,
				secondTagsFragmentDefinition,
				submitFragmentDefinition,
			],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Create two tags in Wem Site

		for (const tagName of ['Dogs', 'Cats']) {
			await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
				name: tagName,
				siteId: pageManagementSite.id,
			});
		}

		// Create one tag on Global

		const globalSiteId = await getGlobalSiteId(apiHelpers);

		const globalTag =
			await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
				name: 'Rabbits',
				siteId: globalSiteId,
			});

		// Go to view mode of the created page, select a tag for each fragment and submit the form

		await page.goto(
			`/web${pageManagementSite.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await page.getByRole('combobox').first().click();
		await page.getByRole('option', {exact: true, name: 'Dogs'}).click();

		await page.getByRole('combobox').first().click();
		await page.getByRole('option', {exact: true, name: 'Rabbits'}).click();

		await page.getByRole('combobox').nth(1).click();
		await page.getByRole('option', {exact: true, name: 'Cats'}).click();

		await page.getByRole('button', {name: 'Submit'}).click();

		await page
			.getByText('Thank you. Your information was successfully received.')
			.waitFor();

		// Go to the object definition page and check the Tags fragment

		await page.goto(
			`/group${pageManagementSite.friendlyUrlPath}${PORTLET_URLS.objects}_${objectDefinitionId}`
		);

		await page
			.locator('.table-list-title')
			.getByRole('link')
			.first()
			.click();

		const grid = page.getByRole('grid');

		await grid.waitFor();

		await expect(grid).toHaveText('RabbitsCatsDogs');

		// Remove the tag created on Global

		await apiHelpers.headlessAdminTaxonomy.deleteKeyword({
			id: globalTag.id,
		});
	});

	test('Checks that an info message appears when categorization is disabled', async ({
		apiHelpers,
		objectDetailsPage,
		page,
		pageEditorPage,
		pageManagementSite,
	}) => {

		// Get Lemon Basket object id from the site initializer

		const {id: objectDefinitionId} =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				LEMON_BASKET_OBJECT_ERC
			);

		// Set the "Enable Categorization of Object entries" configuration to false

		await objectDetailsPage.goto('Lemon Basket');

		await objectDetailsPage.updateConfiguration({
			fieldLabel: 'Enable Categorization of Object entries',
			value: false,
		});

		// Create a Form Container with a Tags fragment

		const formDefinition = getFormContainerDefinition({
			id: getRandomString(),
			objectDefinitionId,
			pageElements: [
				getFragmentDefinition({
					id: getRandomString(),
					key: 'com.liferay.fragment.renderer.categorization.inputs.internal.TagsInputFragmentRenderer',
				}),
			],
		});

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([formDefinition]),
			siteId: pageManagementSite.id,
			title: getRandomString(),
		});

		// Go to edit mode and check the info message

		await pageEditorPage.goto(layout, pageManagementSite.friendlyUrlPath);

		await expect(
			page.getByText(
				'Categorization is disabled for the selected content. To show categories in this fragment, categorization must be enabled.'
			)
		).toBeVisible();

		// Reset initial configuration

		await objectDetailsPage.goto('Lemon Basket');

		await objectDetailsPage.updateConfiguration({
			fieldLabel: 'Enable Categorization of Object entries',
			value: true,
		});
	});
});
