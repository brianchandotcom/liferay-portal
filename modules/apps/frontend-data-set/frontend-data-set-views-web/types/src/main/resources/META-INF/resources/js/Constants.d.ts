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

declare const API_URL: {
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
export {API_URL, FDS_DEFAULT_PROPS, FUZZY_OPTIONS, OBJECT_RELATIONSHIP};
