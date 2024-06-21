/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {
	DATA_SET_CARDS_SECTIONS_RELATIONSHIP,
	DATA_SET_CREATION_ACTIONS_RELATIONSHIP,
	DATA_SET_DATE_FILTERS_RELATIONSHIP,
	DATA_SET_ITEM_ACTIONS_RELATIONSHIP,
	DATA_SET_LIST_SECTIONS_RELATIONSHIP,
	DATA_SET_SELECTION_FILTERS_RELATIONSHIP,
	DATA_SET_SORTS_RELATIONSHIP,
	DATA_SET_TABLE_SECTIONS_RELATIONSHIP,
	DEFAULT_LABEL,
} from '../utils/constants';
import {
	AsyncActionMethod,
	CreationActionTypes,
	ItemActionTypes,
	ModalVariantTypes,
} from '../utils/types';

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';
export class DataSetManagerApiHelpers extends ApiHelpers {
	async createDataSet({
		defaultItemsPerPage = 20,
		defaultVisualizationMode,
		description = 'Sample description',
		erc = 'sampleDataSetERC',
		label = DEFAULT_LABEL.DATA_SET,
		listOfItemsPerPage = '4, 8, 20, 40, 60',
		restApplication = '/data-set-manager/table-sections',
		restEndpoint = '/',
		restSchema = 'DataSetTableSection',
	}: {
		defaultItemsPerPage?: number;
		defaultVisualizationMode?: string;
		description?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
		restApplication?: string;
		restEndpoint?: string;
		restSchema?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/data-sets`;

		const data = {
			defaultItemsPerPage,
			defaultVisualizationMode,
			description,
			externalReferenceCode: erc,
			label,
			listOfItemsPerPage,
			restApplication,
			restEndpoint,
			restSchema,
		};

		return this.post(url, {data});
	}

	async createDataSetCardsSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'name',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/cards-sections`;

		const data = {
			[DATA_SET_CARDS_SECTIONS_RELATIONSHIP]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async createDataSetCreationAction({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = 'full-screen',
		permissionKey,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		dataSetERC?: string;
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: ModalVariantTypes;
		permissionKey?;
		title_i18n?: {[key: string]: string};
		type?: CreationActionTypes;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-manager/actions`;

		const data = {
			[DATA_SET_CREATION_ACTIONS_RELATIONSHIP]: dataSetERC,
			icon,
			label_i18n,
			modalSize,
			permissionKey,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetField({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		extraBodyParams = {},
		label_i18n = {en_US: 'Title'},
		name = 'title',
		renderer = 'default',
		rendererType = 'internal',
		sortable = false,
		type = 'string',
	}: {
		dataSetERC?: string;
		extraBodyParams?: any;
		label_i18n?: {[key: string]: string};
		name?: string;
		renderer?: string;
		rendererType?: string;
		sortable?: boolean;
		type?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/table-sections`;

		const data = {
			[DATA_SET_TABLE_SECTIONS_RELATIONSHIP]: dataSetERC,
			label_i18n,
			name,
			renderer,
			rendererType,
			sortable,
			type,
			...extraBodyParams,
		};

		return this.post(url, {data});
	}

	async createDataSetDateFilter({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName,
		from = '',
		label_i18n = {en_US: 'Title'},
		to = '',
		type,
	}: {
		dataSetERC?: string;
		fieldName: string;
		from?: string;
		label_i18n?: {[key: string]: string};
		to?: string;
		type: 'date' | 'date-time';
	}) {
		const url = `${this.baseUrl}data-set-manager/date-filters`;

		const data = {
			[DATA_SET_DATE_FILTERS_RELATIONSHIP]: dataSetERC,
			fieldName,
			from,
			label_i18n,
			to,
			type,
		};

		return this.post(url, {data});
	}

	async createDataSetSelectionFilter({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName,
		include = true,
		label_i18n,
		multiple = false,
		preselectedValues = '[]',
		source,
		sourceType,
	}: {
		dataSetERC?: string;
		fieldName: string;
		include?: boolean;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		source: string;
		sourceType: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/selection-filters`;

		const data = {
			[DATA_SET_SELECTION_FILTERS_RELATIONSHIP]: dataSetERC,
			fieldName,
			include,
			label_i18n,
			multiple,
			preselectedValues,
			source,
			sourceType,
		};

		return this.post(url, {data});
	}

	async createDataSetItemAction({
		confirmationMessage_i18n,
		confirmationMessageType,
		dataSetERC = DEFAULT_DATA_SET_ERC,
		errorMessage_i18n,
		icon,
		label_i18n = {en_US: 'Default Item Action'},
		method,
		modalSize = 'full-screen',
		permissionKey,
		successMessage_i18n,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		confirmationMessageType?: string;
		confirmationMessage_i18n?: {[key: string]: string};
		dataSetERC?: string;
		errorMessage_i18n?: {[key: string]: string};
		icon?: string;
		label_i18n?: {[key: string]: string};
		method?: AsyncActionMethod;
		modalSize?: ModalVariantTypes;
		permissionKey?;
		successMessage_i18n?: {[key: string]: string};
		title_i18n?: {[key: string]: string};
		type?: ItemActionTypes;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-manager/actions`;

		const data = {
			[DATA_SET_ITEM_ACTIONS_RELATIONSHIP]: dataSetERC,
			confirmationMessage_i18n,
			confirmationMessageType,
			errorMessage_i18n,
			icon,
			label_i18n,
			method,
			modalSize,
			permissionKey,
			successMessage_i18n,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetSort({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		defaultValue = false,
		fieldName = 'dateCreated',
		label_i18n = {en_US: 'Date Created'},
		orderType = 'asc',
	}: {
		dataSetERC?: string;
		defaultValue?: boolean;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		orderType?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/sorts`;

		const data = {
			[DATA_SET_SORTS_RELATIONSHIP]: dataSetERC,
			default: defaultValue,
			fieldName,
			label: label_i18n[Object.keys(label_i18n)[0]],
			label_i18n,
			orderType,
		};

		return this.post(url, {data});
	}

	async createDataSetListSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'name',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/list-sections`;

		const data = {
			[DATA_SET_LIST_SECTIONS_RELATIONSHIP]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrl}data-set-manager/data-sets/by-external-reference-code/${erc}`;

		return this.delete(url);
	}

	async updateDataSet({
		defaultItemsPerPage,
		defaultVisualizationMode,
		erc = DEFAULT_DATA_SET_ERC,
		label,
		listOfItemsPerPage,
	}: {
		defaultItemsPerPage?: number;
		defaultVisualizationMode?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/data-sets/by-external-reference-code/${erc}`;

		const data = {
			defaultItemsPerPage,
			defaultVisualizationMode,
			label,
			listOfItemsPerPage,
		};

		return this.patch(url, data);
	}
}
