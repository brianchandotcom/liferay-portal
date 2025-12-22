/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../helpers/ApiHelpers';

export type PageNode = {
	children?: Array<PageNode>;
	title: string;
};

export async function createLayoutHierarchy({
	apiHelpers,
	pageNodes,
	parentLayoutId = '0',
	siteId,
}: {
	apiHelpers: ApiHelpers;
	pageNodes: Array<PageNode>;
	parentLayoutId?: string;
	siteId: string;
}): Promise<Array<Layout>> {
	const layouts: Array<Layout> = [];

	for (const node of pageNodes) {
		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: siteId,
			parentLayoutId,
			title: node.title,
		});

		layouts.push(layout);

		if (node.children) {
			const childLayouts = await createLayoutHierarchy({
				apiHelpers,
				pageNodes: node.children,
				parentLayoutId: layout.layoutId,
				siteId,
			});
			layouts.push(...childLayouts);
		}
	}

	return layouts;
}
