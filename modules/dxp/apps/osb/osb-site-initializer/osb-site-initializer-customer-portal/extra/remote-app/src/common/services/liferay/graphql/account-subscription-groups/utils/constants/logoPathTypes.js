/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {PRODUCT_TYPES} from '../../../../../../../routes/customer-portal/utils/constants/productTypes';

export const LOGO_PATH_TYPES = {
	[PRODUCT_TYPES.analyticsCloud]: 'analytics_icon.svg',
	[PRODUCT_TYPES.commerce]: 'commerce_icon.svg',
	[PRODUCT_TYPES.dxp]: 'dxp_icon.svg',
	[PRODUCT_TYPES.enterpriseSearch]: 'enterprise_icon.svg',
	[PRODUCT_TYPES.dxpCloud]: 'dxp_icon.svg',
	[PRODUCT_TYPES.liferayExperienceCloud]: 'dxp_icon.svg',
	[PRODUCT_TYPES.portal]: 'portal_icon.svg',
	[PRODUCT_TYPES.partnership]: 'portal_icon.svg',
	[PRODUCT_TYPES.socialOffice]: 'portal_icon.svg',
	[PRODUCT_TYPES.other]: 'portal_icon.svg',
};
