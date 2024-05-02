/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBaseVisualizationMode} from './types';

const API_URL = {
	FDS_ACTIONS: '/o/data-set-manager/actions',
	FDS_CARDS_SECTIONS: '/o/data-set-manager/cards-sections',
	FDS_CLIENT_EXTENSION_FILTERS:
		'/o/data-set-manager/client-extension-filters',
	FDS_DATE_FILTERS: '/o/data-set-manager/date-filters',
	FDS_DYNAMIC_FILTERS: '/o/data-set-manager/dynamic-filters',
	FDS_ENTRIES: '/o/data-set-manager/entries',
	FDS_FIELDS: '/o/data-set-manager/fields',
	FDS_LIST_SECTIONS: '/o/data-set-manager/list-sections',
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
	FDS_VIEW_FDS_CARDS_SECTION: 'fdsViewFDSCardsSectionRelationship',
	FDS_VIEW_FDS_CARDS_SECTION_ERC:
		'r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC',
	FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER: 'fdsViewFDSClientExtensionFilter',
	FDS_VIEW_FDS_CLIENT_EXTENSION_FILTER_ID:
		'r_fdsViewFDSClientExtensionFilter_c_fdsViewId',
	FDS_VIEW_FDS_CREATION_ACTION: 'fdsViewFDSCreationActionRelationship',
	FDS_VIEW_FDS_CREATION_ACTION_ID:
		'r_fdsViewFDSCreationActionRelationship_c_fdsViewId',
	FDS_VIEW_FDS_DATE_FILTER: 'fdsViewFDSDateFilterRelationship',
	FDS_VIEW_FDS_DATE_FILTER_ID:
		'r_fdsViewFDSDateFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_DYNAMIC_FILTER: 'fdsViewFDSDynamicFilterRelationship',
	FDS_VIEW_FDS_DYNAMIC_FILTER_ID:
		'r_fdsViewFDSDynamicFilterRelationship_c_fdsViewId',
	FDS_VIEW_FDS_FIELD: 'fdsViewFDSFieldRelationship',
	FDS_VIEW_FDS_FIELD_ID: 'r_fdsViewFDSFieldRelationship_c_fdsViewId',
	FDS_VIEW_FDS_ITEM_ACTION: 'fdsViewFDSItemActionRelationship',
	FDS_VIEW_FDS_ITEM_ACTION_ID:
		'r_fdsViewFDSItemActionRelationship_c_fdsViewId',
	FDS_VIEW_FDS_LIST_SECTION: 'fdsViewFDSListSectionRelationship',
	FDS_VIEW_FDS_LIST_SECTION_ERC:
		'r_fdsViewFDSListSectionRelationship_c_fdsViewERC',
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
