/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {LayoutSetPrototype} from '../../../helpers/json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {WebContentDisplayPage} from '../../../pages/journal-content-web/WebContentDisplayPage';
import {PagesAdminPage} from '../../../pages/layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {UIElementsPage} from '../../../pages/uielements/UIElementsPage';
import createSiteTemplate from './createSiteTemplate';

export default async function createSiteTemplateWithWebContentOnContentPage({
	apiHelpers,
	page,
	pageEditorPage,
	pagesAdminPage,
	productMenuPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
}: {
	apiHelpers: ApiHelpers;
	page: Page;
	pageEditorPage: PageEditorPage;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
}): Promise<LayoutSetPrototype> {
	const layoutSetPrototype = await createSiteTemplate({
		apiHelpers,
		page,
		productMenuPage,
		templateName,
		text,
		webContentName,
	});

	await productMenuPage.goToPages();
	await pagesAdminPage.newButton.click();
	await pagesAdminPage.addPage({
		name: templateName,
	});

	await pageEditorPage.addWidget('Content Management', 'Web Content Display');
	await webContentDisplayPage.addWebContentWithDisplay(webContentName);
	await uiElementsPage.publishButton.click();

	return layoutSetPrototype;
}
