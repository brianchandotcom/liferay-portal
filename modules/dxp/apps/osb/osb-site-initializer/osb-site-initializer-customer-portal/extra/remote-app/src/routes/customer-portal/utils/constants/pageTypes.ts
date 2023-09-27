/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PRODUCT_TYPES} from './productTypes';

const productsFormatted = Object.entries(
	PRODUCT_TYPES
).map(([productKey, productName]) => [
	productKey,
	productName.replace(' ', '_').toLowerCase(),
]);

export const PAGE_TYPES = {
	dxpDeactivate: 'dxp_deactivate',
	dxpNew: 'dxp_new',
	home: 'home',
	liferayExperienceCloud: 'liferay_experience_cloud',
	overview: 'overview',
	portalNew: 'portal_new',
	teamMembers: 'team_members',
	...Object.fromEntries(productsFormatted),
};
