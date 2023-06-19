/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const API_URL = {
	FDS_DATE_FILTERS: '/o/c/fdsdatefilters',
	FDS_DYNAMIC_FILTERS: '/o/c/fdsdynamicfilters',
	FDS_ENTRIES: '/o/c/fdsentries',
	FDS_FIELDS: '/o/c/fdsfields',
	FDS_SORTS: '/o/c/fdssorts',
	FDS_VIEWS: '/o/c/fdsviews',
};

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const OBJECT_RELATIONSHIP = {
	FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship',
	FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId',
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

export {API_URL, FDS_DEFAULT_PROPS, FUZZY_OPTIONS, OBJECT_RELATIONSHIP};
