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

export const CONTENT_TYPE = {
	JSON: 'application/json',
	XML: 'application/xml',
};

export const CUSTOM_EVENTS = {
	MENU_PAGE: 'customer-portal-menu-selected',
	PROJECT: 'customer-portal-project-loading',
	QUICK_LINKS: 'customer-portal-quick-links',
	SUBSCRIPTION_GROUPS: 'customer-portal-subscription-groups-loading',
	USER_ACCOUNT: 'customer-portal-select-user-loading',
};

export const EXTENSIONS_FILE_TYPE = {
	[CONTENT_TYPE.JSON]: '.json',
	[CONTENT_TYPE.XML]: '.xml',
};

export const SLA_NAMES = {
	limited_subscription: 'Limited Subscription',
};

export const SUBSCRIPTIONS_STATUS = {
	active: 'Active',
	expired: 'Expired',
	future: 'Future',
};

export const status = {
	active: 1,
	expired: 2,
	future: 3,
	inProgress: 4,
	notActivated: 5,
};

export const STATUS_CODE = {
	SUCCESS: 200,
};

export const WEB_CONTENTS_BY_LIFERAY_VERSION = {
	'7.0': 'WEB-CONTENT-ACTION-04',
	'7.1': 'WEB-CONTENT-ACTION-05',
	'7.2': 'WEB-CONTENT-ACTION-06',
	'7.3': 'WEB-CONTENT-ACTION-07',
	'7.4': 'WEB-CONTENT-ACTION-08',
};

export const pages = {
	COMMERCE: 'commerce',
	DXP: 'dxp',
	DXP_CLOUD: 'dxp_cloud',
	ENTERPRISE_SEARCH: 'enterprise_search',
	HOME: 'home',
	OVERVIEW: 'overview',
	TEAM_MEMBERS: 'team_members',
};

export const PRODUCTS = {
	analytics_cloud: 'Analytics Cloud',
	commerce: 'Commerce',
	dxp: 'DXP',
	dxp_cloud: 'DXP Cloud',
	partnership: 'Partnership',
	portal: 'Portal',
};
