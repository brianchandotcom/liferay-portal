/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createAssetPublisherAndConfigure} from './createAssetPublisherAndConfigure';
import {TCategory, createCategories} from '../../../helpers/createCategories';
import {createDPTandMarkAsDefault} from './createDPTandMarkAsDefault';

import type {ApiHelpers} from '../../../helpers/ApiHelpers';
import type {PageEditorPage} from '../../../pages/layout-content-page-editor-web/PageEditorPage';
import type {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';

export async function blogsCategorizedFriendlyUrlSetup({
	apiHelpers,
	displayPageTemplatesPage,
	friendlyUrlCategories,
	page,
	pageEditorPage,
	site,
	vocabularyName,
}: {
	apiHelpers: ApiHelpers;
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	friendlyUrlCategories: TCategory[];
	page;
	pageEditorPage: PageEditorPage;
	site: Site;
	vocabularyName: string;
}) {
	const categories = await createCategories({
		apiHelpers,
		friendlyUrlCategories,
		site,
		vocabularyName,
	});
	await createDPTandMarkAsDefault({displayPageTemplatesPage, site});
	await createAssetPublisherAndConfigure({
		apiHelpers,
		page,
		pageEditorPage,
		site,
	});

	return {categories};
}
