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

export const POSSIBLE_SUBSCRIPTIONS_STATUS = {
	active: 'Active',
	expired: 'Expired',
	future: 'Future',
};

export const EXTENSIONS_FILE_TYPE = {
	[CONTENT_TYPE.JSON]: '.json',
	[CONTENT_TYPE.XML]: '.xml',
};

export const SLA_NAMES = {
	limited_subscription: 'Limited Subscription',
};

export const status = {
	active: 1,
	expired: 2,
	future: 3,
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
	DXP_CLOUD: 'dxp',
	ENTERPRISE_SEARCH: 'enterprise',
	HOME: 'home',
	OVERVIEW: 'overview',
	TEAM_MEMBERS: 'team',
};

export const PRODUCTS = {
	analytics_cloud: 'Analytics Cloud',
	commerce: 'Commerce',
	dxp: 'DXP',
	dxp_cloud: 'DXP Cloud',
	partnership: 'Partnership',
	portal: 'Portal',
};
