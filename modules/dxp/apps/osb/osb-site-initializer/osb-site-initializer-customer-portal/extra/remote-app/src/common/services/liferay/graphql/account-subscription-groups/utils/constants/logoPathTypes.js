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

import {
	AnalyticsIcon,
	CommerceIcon,
	DXPIcon,
	EnterpriseIcon,
	PortalIcon,
} from '../../../../../../icons/navigation-menu';

export const LOGO_PATH_TYPES = {
	[PRODUCT_TYPES.analyticsCloud]: AnalyticsIcon,
	[PRODUCT_TYPES.commerce]: CommerceIcon,
	[PRODUCT_TYPES.dxp]: DXPIcon,
	[PRODUCT_TYPES.enterpriseSearch]: EnterpriseIcon,
	[PRODUCT_TYPES.dxpCloud]: DXPIcon,
	[PRODUCT_TYPES.liferayExperienceCloud]: DXPIcon,
	[PRODUCT_TYPES.portal]: PortalIcon,
	[PRODUCT_TYPES.partnership]: PortalIcon,
	[PRODUCT_TYPES.socialOffice]: PortalIcon,
	[PRODUCT_TYPES.other]: PortalIcon,
};
