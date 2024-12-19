/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TCategory, createCategories} from '../../../helpers/CreateCategories';
import {createAssetPublisherAndConfigure} from './createAssetPublisherAndConfigure';
import {createDPTandMarkAsDefault} from './createDPTandMarkAsDefault';

import type {ApiHelpers} from '../../../helpers/ApiHelpers';
import type {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';

export async function blogsCategorizedFriendlyUrlSetup({
	apiHelpers,
	displayPageTemplatesPage,
	friendlyUrlCategories,
	page,
	site,
	vocabularyName,
}: {
	apiHelpers: ApiHelpers;
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	friendlyUrlCategories: TCategory[];
	page;
	site: Site;
	vocabularyName: string;
}) {
	const categories = await createCategories({
		apiHelpers,
		categoryNames: friendlyUrlCategories,
		site,
		vocabularyName,
	});
	await createDPTandMarkAsDefault({displayPageTemplatesPage, site});
	await createAssetPublisherAndConfigure({
		apiHelpers,
		page,
		site,
	});

	return {categories};
}
