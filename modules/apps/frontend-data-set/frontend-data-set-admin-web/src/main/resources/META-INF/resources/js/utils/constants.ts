/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseVisualizationMode} from './types';

const API_URL = {
	ACTIONS: '/o/data-set-manager/actions',
	CARDS_SECTIONS: '/o/data-set-manager/cards-sections',
	CLIENT_EXTENSION_FILTERS: '/o/data-set-manager/client-extension-filters',
	DATA_SETS: '/o/data-set-manager/data-sets',
	DATE_FILTERS: '/o/data-set-manager/date-filters',
	FDS_ENTRIES: '/o/data-set-manager/entries',
	LIST_SECTIONS: '/o/data-set-manager/list-sections',
	SELECTION_FILTERS: '/o/data-set-manager/selection-filters',
	SORTS: '/o/data-set-manager/sorts',
	TABLE_SECTIONS: '/o/data-set-manager/table-sections',
};

const FUZZY_OPTIONS = {
	post: '</strong>',
	pre: '<strong>',
};

const OBJECT_RELATIONSHIP = {
	DATA_SET_CARDS_SECTION: 'fdsViewFDSCardsSectionRelationship',
	DATA_SET_CARDS_SECTION_ERC:
		'r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC',
	DATA_SET_CLIENT_EXTENSION_FILTER: 'fdsViewFDSClientExtensionFilter',
	DATA_SET_CLIENT_EXTENSION_FILTER_ID:
		'r_fdsViewFDSClientExtensionFilter_c_fdsViewId',
	DATA_SET_CREATION_ACTION: 'fdsViewFDSCreationActionRelationship',
	DATA_SET_CREATION_ACTION_ID:
		'r_fdsViewFDSCreationActionRelationship_c_fdsViewId',
	DATA_SET_DATE_FILTER: 'fdsViewFDSDateFilterRelationship',
	DATA_SET_DATE_FILTER_ID: 'r_fdsViewFDSDateFilterRelationship_c_fdsViewId',
	DATA_SET_ITEM_ACTION: 'fdsViewFDSItemActionRelationship',
	DATA_SET_ITEM_ACTION_ID: 'r_fdsViewFDSItemActionRelationship_c_fdsViewId',
	DATA_SET_LIST_SECTION: 'fdsViewFDSListSectionRelationship',
	DATA_SET_LIST_SECTION_ERC:
		'r_fdsViewFDSListSectionRelationship_c_fdsViewERC',
	DATA_SET_SELECTION_FILTER: 'fdsViewFDSDynamicFilterRelationship',
	DATA_SET_SELECTION_FILTER_ID:
		'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId',
	DATA_SET_SORT: 'fdsViewFDSSortRelationship',
	DATA_SET_SORT_ID: 'r_fdsViewFDSSortRelationship_c_fdsViewId',
	DATA_SET_TABLE_SECTION: 'fdsViewFDSFieldRelationship',
	DATA_SET_TABLE_SECTION_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId',
	FDS_ENTRY_FDS_VIEW: 'fdsEntryFDSViewRelationship',
	FDS_ENTRY_FDS_VIEW_ID: 'r_fdsEntryFDSViewRelationship_c_fdsEntryId',
} as const;

const FDS_DEFAULT_PROPS = {
	pagination: {
		deltas: [{label: 4}, {label: 8}, {label: 20}, {label: 40}, {label: 60}],
		initialDelta: 8,
	},
	style: 'fluid' as const,
};

const DEFAULT_VISUALIZATION_MODES: Array<IBaseVisualizationMode<any>> = [
	{
		label: Liferay.Language.get('cards'),
		mode: 'cards',
		thumbnail: 'cards2',
		visualizationModeId: 'defaultCards',
	},
	{
		label: Liferay.Language.get('list'),
		mode: 'list',
		thumbnail: 'list',
		visualizationModeId: 'defaultList',
	},
	{
		label: Liferay.Language.get('table'),
		mode: 'table',
		thumbnail: 'table',
		visualizationModeId: 'defaultTable',
	},
];

const ALLOWED_ENDPOINTS_PARAMETERS = ['scopeKey', 'siteId', 'userId'];

export {
	API_URL,
	DEFAULT_VISUALIZATION_MODES,
	FDS_DEFAULT_PROPS,
	FUZZY_OPTIONS,
	OBJECT_RELATIONSHIP,
	ALLOWED_ENDPOINTS_PARAMETERS,
};
