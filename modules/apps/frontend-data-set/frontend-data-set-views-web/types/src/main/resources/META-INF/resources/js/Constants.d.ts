/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare const API_URL: {
	FDS_ACTIONS: string;
	FDS_CLIENT_EXTENSION_FILTERS: string;
	FDS_DATE_FILTERS: string;
	FDS_DYNAMIC_FILTERS: string;
	FDS_ENTRIES: string;
	FDS_FIELDS: string;
	FDS_SORTS: string;
	FDS_VIEWS: string;
};
declare const FUZZY_OPTIONS: {
	post: string;
	pre: string;
};
declare const OBJECT_RELATIONSHIP: {
	readonly FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship';
	readonly FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId';
	readonly FDS_VIEW_FDS_ACTION: 'fdsViewFDSActionRelationship';
	readonly FDS_VIEW_FDS_ACTION_ID: 'r_fdsViewFDSActionRelationship_c_fdsViewId';
	readonly FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER: 'fdsViewFDSClientExtensionFilter';
	readonly FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER_ID: 'r_fdsViewFDSClientExtensionFilter_c_fdsViewId';
	readonly FDS_VIEW_FDS_DATE_FILTER: 'fdsViewFDSDateFilterRelationship';
	readonly FDS_VIEW_FDS_DATE_FILTER_ID: 'r_fdsViewFDSDateFilterRelationship_c_fdsViewId';
	readonly FDS_VIEW_FDS_DYNAMIC_FILTER: 'fdsViewFDSDynamicFilterRelationship';
	readonly FDS_VIEW_FDS_DYNAMIC_FILTER_ID: 'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId';
	readonly FDS_VIEW_FDS_FIELD: 'fdsViewFDSFieldRelationship';
	readonly FDS_VIEW_FDS_FIELD_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId';
	readonly FDS_VIEW_FDS_SORT: 'fdsViewFDSSortRelationship';
	readonly FDS_VIEW_FDS_SORT_ID: 'r_fdsViewFDSSortRelationship_c_fdsViewId';
};
declare const FDS_DEFAULT_PROPS: {
	pagination: {
		deltas: {
			label: number;
		}[];
		initialDelta: number;
	};
	style: 'fluid';
};
declare const ALLOWED_ENDPOINTS_PARAMETERS: string[];
export {
	API_URL,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	ALLOWED_ENDPOINTS_PARAMETERS,
};
