/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/main/utils/getWidgetDefinition';
import {CKEditor4SamplePage} from '../pages/CKEditor4SamplePage';

const ckeditor4SamplePageTest = isolatedSiteTest.extend<{
	ckeditor4SamplePage: CKEditor4SamplePage;
}>({
	ckeditor4SamplePage: async ({page, site}, use) => {
		const widgetDefinition = getWidgetDefinition({
			id: getRandomString(),
			widgetName:
				'com_liferay_editor_ckeditor4_sample_web_internal_portlet_CKEditorSamplePortlet',
		});

		const apiHelpers = new ApiHelpers(page);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([widgetDefinition]),
			siteId: site.id,
			title: getRandomString(),
		});

		const ckeditor4SamplePage = new CKEditor4SamplePage(
			page,
			`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
		);

		await ckeditor4SamplePage.goto();

		await use(ckeditor4SamplePage);
	},
});

export {ckeditor4SamplePageTest};
