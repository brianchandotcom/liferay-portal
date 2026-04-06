/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const LAYOUT_TYPES = {
	displayPageTemplate: 'displayPageTemplate',
	fragmentCollection: 'fragmentCollection',
	master: 'master',
	page: 'page',
	pageTemplate: 'pageTemplate',
};

export const LAYOUT_TYPES_OPTIONS = [
	{
		label: Liferay.Language.get('display-page-templates'),
		type: LAYOUT_TYPES.displayPageTemplate,
	},
	{
		label: Liferay.Language.get('fragments'),
		type: LAYOUT_TYPES.fragmentCollection,
	},
	{
		label: Liferay.Language.get('masters'),
		type: LAYOUT_TYPES.master,
	},
	{
		label: Liferay.Language.get('pages'),
		type: LAYOUT_TYPES.page,
	},
	{
		label: Liferay.Language.get('page-templates'),
		type: LAYOUT_TYPES.pageTemplate,
	},
];
