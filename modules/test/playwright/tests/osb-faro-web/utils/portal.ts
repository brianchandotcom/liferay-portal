/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import getRandomString from '../../../utils/getRandomString';
import getFragmentDefinition from '../../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';

export async function acceptsCookiesBanner(page: Page) {
	const cookiesBannerButton = page.getByRole('button', {name: 'Accept All'});

	if (await cookiesBannerButton.isVisible()) {
		await cookiesBannerButton.click();
	}
}

export const createSitePage = async function ({
	apiHelpers,
	pageTitle,
	siteName = 'Guest',
}: {
	apiHelpers: ApiHelpers;
	pageTitle: string;
	siteName?: string;
}) {
	const company =
		await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
			'liferay.com'
		);

	const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
		company.companyId,
		siteName
	);

	return await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
		]),
		siteId: group.groupId,
		title: pageTitle,
	});
}
