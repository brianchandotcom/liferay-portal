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
	contentTitle?: string;
	pageNumber: string;
	title?: string;
	widgets?: Array<string>;
};

export interface JournalContentPage {
	contentTitle: string;
	friendlyUrlPath: string;
	pageNumber: string;
	title: string;
}

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
}): Promise<Array<JournalContentPage>> {
	const layouts: Array<JournalContentPage> = [];

	for (const node of pageNodes) {
		const widgetDefinitions = (node.widgets || []).map((widgetName) =>
			getWidgetDefinition({
				id: getRandomString(),
				widgetName,
			})
		);

		const title = node.title || `Page ${node.pageNumber}`;
		const contentTitle = node.contentTitle || `Title-${node.pageNumber}`;

		const layout = (await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: widgetDefinitions.length
				? getPageDefinition(widgetDefinitions)
				: undefined,
			parentSitePage,
			siteId,
			title,
		})) as Layout & {title: string};

		const journalContentPage: JournalContentPage = {
			contentTitle,
			friendlyUrlPath: layout.friendlyUrlPath,
			pageNumber: node.pageNumber,
			title: layout.title,
		};

		layouts.push(journalContentPage);

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
