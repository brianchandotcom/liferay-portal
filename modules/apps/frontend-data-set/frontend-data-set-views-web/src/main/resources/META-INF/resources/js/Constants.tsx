/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const API_URL = {
	FDS_ACTIONS: '/o/data-set-manager/actions',
	FDS_CLIENT_EXTENSION_FILTERS:
		'/o/data-set-manager/client-extension-filters',
	FDS_DATE_FILTERS: '/o/data-set-manager/date-filters',
	FDS_DYNAMIC_FILTERS: '/o/data-set-manager/dynamic-filters',
	FDS_ENTRIES: '/o/data-set-manager/entries',
	FDS_FIELDS: '/o/data-set-manager/fields',
	FDS_SORTS: '/o/data-set-manager/sorts',
	FDS_VIEWS: '/o/data-set-manager/views',
};

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const OBJECT_RELATIONSHIP = {
	FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship',
	FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId',
	FDS_VIEW_FDS_ACTION: 'fdsViewFDSActionRelationship',
	FDS_VIEW_FDS_ACTION_ID: 'r_fdsViewFDSActionRelationship_c_fdsViewId',
	FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER: 'fdsViewFDSClientExtensionFilter',
	FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER_ID:
		'r_fdsViewFDSClientExtensionFilter_c_fdsViewId',
	FDS_VIEW_FDS_DATE_FILTER: 'fdsViewFDSDateFilterRelationship',
	FDS_VIEW_FDS_DATE_FILTER_ID:
		'r_fdsViewFDSDateFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_DYNAMIC_FILTER: 'fdsViewFDSDynamicFilterRelationship',
	FDS_VIEW_FDS_DYNAMIC_FILTER_ID:
		'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_FIELD: 'fdsViewFDSFieldRelationship',
	FDS_VIEW_FDS_FIELD_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId',
	FDS_VIEW_FDS_SORT: 'fdsViewFDSSortRelationship',
	FDS_VIEW_FDS_SORT_ID: 'r_fdsViewFDSSortRelationship_c_fdsViewId',
} as const;

const FDS_DEFAULT_PROPS = {
	pagination: {
		deltas: [{label: 4}, {label: 8}, {label: 20}, {label: 40}, {label: 60}],
		initialDelta: 8,
	},
	style: 'fluid' as const,
};

const ALLOWED_ENDPOINTS_PARAMETERS = ['scopeKey', 'siteId', 'userId'];

export {
	API_URL,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	ALLOWED_ENDPOINTS_PARAMETERS,
};
