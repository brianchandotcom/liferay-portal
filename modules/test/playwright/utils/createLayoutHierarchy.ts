/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../helpers/ApiHelpers';
import getPageDefinition from '../tests/layout-content-page-editor-web/main/utils/getPageDefinition';

export type PageNode<T = {}> = {
	children?: Array<PageNode<T>>;
	contentTitle?: string;
	pageNumber: string;
	title?: string;
	widgetDefinitions?: Array<PageElement>;
} & T;

export type JournalContentPage<T = {}> = {
	contentTitle: string;
	friendlyUrlPath: string;
	pageNumber: string;
	title: string;
} & T;

export async function createLayoutHierarchy<T = {}>(options: {
	apiHelpers: ApiHelpers;
	pageNodes: Array<PageNode<T>>;
	parentSitePage?: {friendlyUrlPath: string};
	siteId: string;
}): Promise<Array<JournalContentPage<T>>> {
	const {apiHelpers, pageNodes, parentSitePage, siteId} = options;
	const layouts: Array<JournalContentPage<T>> = [];

	for (const node of pageNodes) {
		const {
			children,
			contentTitle: nodeContentTitle,
			pageNumber,
			title: nodeTitle,
			widgetDefinitions,
			...extraProps
		} = node;

		const title = nodeTitle || `Page ${pageNumber}`;
		const contentTitle = nodeContentTitle || `Title-${pageNumber}`;

		const layout = (await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: widgetDefinitions?.length
				? getPageDefinition(widgetDefinitions)
				: undefined,
			parentSitePage,
			siteId,
			title,
		})) as Layout & {title: string};

		const journalContentPage = {
			...(extraProps as unknown as T),
			contentTitle,
			friendlyUrlPath: layout.friendlyUrlPath,
			pageNumber,
			title: layout.title,
		} as JournalContentPage<T>;

		layouts.push(journalContentPage);

		if (children) {
			const childLayouts = await createLayoutHierarchy<T>({
				apiHelpers,
				pageNodes: children,
				parentSitePage: {friendlyUrlPath: layout.friendlyUrlPath},
				siteId,
			});

			layouts.push(...childLayouts);
		}
	}

	return layouts;
}
