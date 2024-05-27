/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseVisualizationMode} from './types';
declare const API_URL: {
	ACTIONS: string;
	CARDS_SECTIONS: string;
	CLIENT_EXTENSION_FILTERS: string;
	DATA_SETS: string;
	DATE_FILTERS: string;
	FDS_ENTRIES: string;
	LIST_SECTIONS: string;
	SELECTION_FILTERS: string;
	SORTS: string;
	TABLE_SECTIONS: string;
};
declare const FUZZY_OPTIONS: {
	post: string;
	pre: string;
};
declare const OBJECT_RELATIONSHIP: {
	readonly DATA_SET_CARDS_SECTION: 'fdsViewFDSCardsSectionRelationship';
	readonly DATA_SET_CARDS_SECTION_ERC: 'r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC';
	readonly DATA_SET_CLIENT_EXTENSION_FILTER: 'fdsViewFDSClientExtensionFilter';
	readonly DATA_SET_CLIENT_EXTENSION_FILTER_ID: 'r_fdsViewFDSClientExtensionFilter_c_fdsViewId';
	readonly DATA_SET_CREATION_ACTION: 'fdsViewFDSCreationActionRelationship';
	readonly DATA_SET_CREATION_ACTION_ID: 'r_fdsViewFDSCreationActionRelationship_c_fdsViewId';
	readonly DATA_SET_DATE_FILTER: 'fdsViewFDSDateFilterRelationship';
	readonly DATA_SET_DATE_FILTER_ID: 'r_fdsViewFDSDateFilterRelationship_c_fdsViewId';
	readonly DATA_SET_ITEM_ACTION: 'fdsViewFDSItemActionRelationship';
	readonly DATA_SET_ITEM_ACTION_ID: 'r_fdsViewFDSItemActionRelationship_c_fdsViewId';
	readonly DATA_SET_LIST_SECTION: 'fdsViewFDSListSectionRelationship';
	readonly DATA_SET_LIST_SECTION_ERC: 'r_fdsViewFDSListSectionRelationship_c_fdsViewERC';
	readonly DATA_SET_SELECTION_FILTER: 'fdsViewFDSDynamicFilterRelationship';
	readonly DATA_SET_SELECTION_FILTER_ID: 'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId';
	readonly DATA_SET_SORT: 'fdsViewFDSSortRelationship';
	readonly DATA_SET_SORT_ID: 'r_fdsViewFDSSortRelationship_c_fdsViewId';
	readonly DATA_SET_TABLE_SECTION: 'fdsViewFDSFieldRelationship';
	readonly DATA_SET_TABLE_SECTION_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId';
	readonly FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship';
	readonly FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId';
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
declare const DEFAULT_VISUALIZATION_MODES: Array<IBaseVisualizationMode<any>>;
declare const ALLOWED_ENDPOINTS_PARAMETERS: string[];
export {
	API_URL,
	DEFAULT_VISUALIZATION_MODES,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	ALLOWED_ENDPOINTS_PARAMETERS,
};
