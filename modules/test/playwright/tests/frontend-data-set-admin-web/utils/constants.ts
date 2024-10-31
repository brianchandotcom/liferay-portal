/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../../liferay.config';

const API_ENDPOINT_PATH = '/data-set-admin/data-sets';

const ES_BASE_URL = `${liferayConfig.environment.baseUrl}/es`;
const EN_BASE_URL = `${liferayConfig.environment.baseUrl}/en`;
const PT_BASE_URL = `${liferayConfig.environment.baseUrl}/pt`;

const DEFAULT_LABEL = {
	DATA_SET: 'Sample Data Set',
};

const REL_PREFIX = 'r_dataSetTo';
const REL_POSTFIX = '_l_dataSetERC';

const CARDS_SECTION_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetCardsSections' + REL_POSTFIX;
const LIST_SECTION_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetListSections' + REL_POSTFIX;
const TABLE_SECTION_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetTableSections' + REL_POSTFIX;

const ACTION_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetActions' + REL_POSTFIX;

const CLIENT_EXTENSION_FILTER_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetClientExtensionFilters_l_dataSetId';
const DATE_FILTER_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetDateFilters' + REL_POSTFIX;
const SELECTION_FILTER_DATA_SET_RELATIONSHIP =
	REL_PREFIX + 'DataSetSelectionFilters' + REL_POSTFIX;

const SORT_DATA_SET_RELATIONSHIP = REL_PREFIX + 'DataSetSorts' + REL_POSTFIX;

export {
	API_ENDPOINT_PATH,
	ACTION_DATA_SET_RELATIONSHIP,
	CARDS_SECTION_DATA_SET_RELATIONSHIP,
	CLIENT_EXTENSION_FILTER_DATA_SET_RELATIONSHIP,
	LIST_SECTION_DATA_SET_RELATIONSHIP,
	TABLE_SECTION_DATA_SET_RELATIONSHIP,
	DATE_FILTER_DATA_SET_RELATIONSHIP,
	SELECTION_FILTER_DATA_SET_RELATIONSHIP,
	SORT_DATA_SET_RELATIONSHIP,
	DEFAULT_LABEL,
	ES_BASE_URL,
	EN_BASE_URL,
	PT_BASE_URL,
};
