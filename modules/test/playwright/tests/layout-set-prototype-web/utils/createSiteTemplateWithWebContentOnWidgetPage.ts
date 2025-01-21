/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {LayoutSetPrototype} from '../../../helpers/json-web-services/JSONWebServicesLayoutSetPrototypeApiHelper';
import {WebContentDisplayPage} from '../../../pages/journal-content-web/WebContentDisplayPage';
import {PagesAdminPage} from '../../../pages/layout-admin-web/PagesAdminPage';
import {WidgetPagePage} from '../../../pages/layout-admin-web/WidgetPagePage';
import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {UIElementsPage} from '../../../pages/uielements/UIElementsPage';
import createSiteTemplate from './createSiteTemplate';

export default async function createSiteTemplateWithWebContentOnWidgetPage({
	apiHelpers,
	page,
	pagesAdminPage,
	productMenuPage,
	templateName,
	text,
	uiElementsPage,
	webContentDisplayPage,
	webContentName,
	widgetPagePage,
}: {
	apiHelpers: ApiHelpers;
	page: Page;
	pagesAdminPage: PagesAdminPage;
	productMenuPage: ProductMenuPage;
	templateName: string;
	text: string;
	uiElementsPage: UIElementsPage;
	webContentDisplayPage: WebContentDisplayPage;
	webContentName: string;
	widgetPagePage: WidgetPagePage;
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
	await pagesAdminPage.addWidgetPage({
		name: templateName,
	});

	await page.goto(
		`group/template-${layoutSetPrototype.layoutSetPrototypeId}/${templateName}`
	);

	await widgetPagePage.addButton.click();
	await webContentDisplayPage.addWebContentWithWidget(webContentName);
	await uiElementsPage.setupUpdatedAlert.waitFor({state: 'hidden'});
	await uiElementsPage.closeClickable.click();
	await uiElementsPage.closeClickable.waitFor({
		state: 'hidden',
	});

	return layoutSetPrototype;
}
