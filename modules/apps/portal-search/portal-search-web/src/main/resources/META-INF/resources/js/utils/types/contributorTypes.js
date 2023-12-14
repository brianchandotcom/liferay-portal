/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const CONTRIBUTOR_TYPES = {
	ASAH_RECENT_ASSETS_USER_ACTIVITY: 'recentAssetsUserActivity',
	ASAH_RECENT_PAGES_USER_ACTIVITY: 'recentPagesUserActivity',
	ASAH_RECENT_SEARCH_SITE_ACTIVITY: 'recentSearchSiteActivity',
	ASAH_RECENT_SEARCHES_USER_ACTIVITY: 'recentSearchesUserActivity',
	ASAH_RECENT_SITES_USER_ACTIVITY: 'recentSitesUserActivity',
	ASAH_TOP_SEARCH_SITE_ACTIVITY: 'topSearchSiteActivity',
	BASIC: 'basic',
	SXP_BLUEPRINT: 'sxpBlueprint',
};

export const CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES = {
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_SITE_ACTIVITY]: 'trending-searches',
	[CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_SITE_ACTIVITY]: 'top-searches',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES_USER_ACTIVITY]: 'recent-searches',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES_USER_ACTIVITY]: 'recent-pages',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SITES_USER_ACTIVITY]: 'recent-sites',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS_USER_ACTIVITY]: 'recently-viewed',
};

export const CONTRIBUTOR_TYPES_DEFAULT_ATTRIBUTES = {
	[CONTRIBUTOR_TYPES.BASIC]: {
		characterThreshold: '',
	},
	[CONTRIBUTOR_TYPES.SXP_BLUEPRINT]: {
		characterThreshold: '',
		fields: [],
		includeAssetSearchSummary: true,
		includeAssetURL: true,
		sxpBlueprintExternalReferenceCode: '',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_SITE_ACTIVITY]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '5',
	},
	[CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_SITE_ACTIVITY]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '5',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES_USER_ACTIVITY]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '0',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES_USER_ACTIVITY]: {
		characterThreshold: '0',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SITES_USER_ACTIVITY]: {
		characterThreshold: '0',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS_USER_ACTIVITY]: {
		characterThreshold: '0',
		contentTypes: '',
		rangeKey: '0',
	},
};
