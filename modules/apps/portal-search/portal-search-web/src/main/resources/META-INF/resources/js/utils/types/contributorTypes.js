/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const CONTRIBUTOR_TYPES = {
	ASAH_RECENT_ASSETS: 'asahRecentAssets',
	ASAH_RECENT_PAGES: 'asahRecentPages',
	ASAH_RECENT_SEARCH_KEYWORDS: 'asahRecentSearchKeywords',
	ASAH_RECENT_SEARCHES: 'asahRecentSearches',
	ASAH_RECENT_SITES: 'asahRecentSites',
	ASAH_TOP_SEARCH_KEYWORDS: 'asahTopSearchKeywords',
	BASIC: 'basic',
	SXP_BLUEPRINT: 'sxpBlueprint',
};

export const CONTRIBUTOR_TYPES_ASAH_DEFAULT_DISPLAY_GROUP_NAMES = {
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS]: 'trending-searches',
	[CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS]: 'top-searches',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES]: 'recent-searches',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES]: 'recent-pages',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SITES]: 'recent-sites',
	[CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS]: 'recently-viewed',
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
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCH_KEYWORDS]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '5',
	},
	[CONTRIBUTOR_TYPES.ASAH_TOP_SEARCH_KEYWORDS]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '5',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SEARCHES]: {
		characterThreshold: '0',
		matchDisplayLanguageId: true,
		minCounts: '5',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_PAGES]: {
		characterThreshold: '0',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_SITES]: {
		characterThreshold: '0',
		rangeKey: '0',
	},
	[CONTRIBUTOR_TYPES.ASAH_RECENT_ASSETS]: {
		characterThreshold: '0',
		contentType: '',
		rangeKey: '0',
	},
};
