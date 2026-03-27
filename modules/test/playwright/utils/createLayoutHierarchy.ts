/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../helpers/ApiHelpers';
import getPageDefinition from '../tests/layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../tests/layout-content-page-editor-web/main/utils/getWidgetDefinition';
import getRandomString from './getRandomString';

export type PageNode = {
	children?: Array<PageNode>;
	title: string;
	widgets?: Array<string>;
};

export async function createLayoutHierarchy({
	apiHelpers,
	pageNodes,
	parentSitePage,
	siteId,
}: {
	apiHelpers: ApiHelpers;
	pageNodes: Array<PageNode>;
	parentSitePage?: {friendlyUrlPath: string};
	siteId: string;
}): Promise<Array<any>> {
	const layouts: Array<any> = [];

	for (const node of pageNodes) {
		const widgetDefinitions = (node.widgets || []).map((widgetName) =>
			getWidgetDefinition({
				id: getRandomString(),
				widgetName,
			})
		);

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: widgetDefinitions.length
				? getPageDefinition(widgetDefinitions)
				: undefined,
			parentSitePage,
			siteId,
			title: node.title,
		});

		layouts.push(layout);

		if (node.children) {
			const childLayouts = await createLayoutHierarchy({
				apiHelpers,
				pageNodes: node.children,
				parentSitePage: {friendlyUrlPath: layout.friendlyUrlPath},
				siteId,
			});

			layouts.push(...childLayouts);
		}
	}

	return layouts;
}
