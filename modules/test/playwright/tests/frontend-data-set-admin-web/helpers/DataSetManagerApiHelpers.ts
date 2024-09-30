/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {
	CARDS_SECTION_DATA_SET_RELATIONSHIP,
	CLIENT_EXTENSION_FILTER_DATA_SET_RELATIONSHIP,
	CREATION_ACTION_DATA_SET_RELATIONSHIP,
	DATE_FILTER_DATA_SET_RELATIONSHIP,
	DEFAULT_LABEL,
	ITEM_ACTION_DATA_SET_RELATIONSHIP,
	LIST_SECTION_DATA_SET_RELATIONSHIP,
	SELECTION_FILTER_DATA_SET_RELATIONSHIP,
	SORT_DATA_SET_RELATIONSHIP,
	TABLE_SECTION_DATA_SET_RELATIONSHIP,
} from '../utils/constants';
import {
	EAsyncActionMethod,
	ECreationActionType,
	EItemActionType,
	EModalActionVariant,
} from '../utils/types';

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';
export class DataSetManagerApiHelpers extends ApiHelpers {
	async createDataSet({
		additionalAPIURLParameters,
		defaultItemsPerPage = 20,
		defaultVisualizationMode,
		description = 'Sample description',
		erc = 'sampleDataSetERC',
		label = DEFAULT_LABEL.DATA_SET,
		listOfItemsPerPage = '4, 8, 20, 40, 60',
		restApplication = '/data-set-admin/table-sections',
		restEndpoint = '/',
		restSchema = 'DataSetTableSection',
	}: {
		additionalAPIURLParameters?: string;
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
		const url = `${this.baseUrl}data-set-admin/data-sets`;

		const data = {
			additionalAPIURLParameters,
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
		fieldName = 'fieldName',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/cards-sections`;

		const data = {
			[CARDS_SECTION_DATA_SET_RELATIONSHIP]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async createDataSetClientExtensionFilter({
		clientExtensionEntryERC,
		dataSetId,
		fieldName,
		label_i18n = {en_US: 'Title'},
	}: {
		clientExtensionEntryERC: string;
		dataSetId: string;
		fieldName: string;
		label_i18n?: {[key: string]: string};
	}) {
		const url = `${this.baseUrl}data-set-admin/client-extension-filters`;

		const data = {
			[CLIENT_EXTENSION_FILTER_DATA_SET_RELATIONSHIP]: dataSetId,
			clientExtensionEntryERC,
			fieldName,
			label_i18n,
		};

		return this.post(url, {data});
	}

	async createDataSetCreationAction({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = EModalActionVariant.FULL_SCREEN,
		permissionKey,
		title_i18n,
		type = ECreationActionType.LINK,
		url = liferayConfig.environment.baseUrl,
	}: {
		dataSetERC?: string;
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: EModalActionVariant;
		permissionKey?;
		title_i18n?: {[key: string]: string};
		type?: ECreationActionType;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-admin/actions`;

		const data = {
			[CREATION_ACTION_DATA_SET_RELATIONSHIP]: dataSetERC,
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

	async createDataSetTableSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		extraBodyParams = {},
		fieldName = 'title',
		label_i18n = {en_US: 'Title'},
		renderer = 'default',
		rendererType = 'internal',
		sortable = false,
		type = 'string',
	}: {
		dataSetERC?: string;
		extraBodyParams?: any;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		renderer?: string;
		rendererType?: string;
		sortable?: boolean;
		type?: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/table-sections`;

		const data = {
			[TABLE_SECTION_DATA_SET_RELATIONSHIP]: dataSetERC,
			fieldName,
			label_i18n,
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
		const url = `${this.baseUrl}data-set-admin/date-filters`;

		const data = {
			[DATE_FILTER_DATA_SET_RELATIONSHIP]: dataSetERC,
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
		itemKey,
		itemLabel,
		label_i18n,
		multiple = false,
		preselectedValues = '[]',
		source,
		sourceType,
	}: {
		dataSetERC?: string;
		fieldName: string;
		include?: boolean;
		itemKey?: string;
		itemLabel?: string;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		source: string;
		sourceType: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/selection-filters`;

		const data = {
			[SELECTION_FILTER_DATA_SET_RELATIONSHIP]: dataSetERC,
			fieldName,
			include,
			itemKey,
			itemLabel,
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
		modalSize = EModalActionVariant.FULL_SCREEN,
		permissionKey,
		requestBody = '{}',
		successMessage_i18n,
		title_i18n,
		type = EItemActionType.LINK,
		url = liferayConfig.environment.baseUrl,
	}: {
		confirmationMessageType?: string;
		confirmationMessage_i18n?: {[key: string]: string};
		dataSetERC?: string;
		errorMessage_i18n?: {[key: string]: string};
		icon?: string;
		label_i18n?: {[key: string]: string};
		method?: EAsyncActionMethod;
		modalSize?: EModalActionVariant;
		permissionKey?: string;
		requestBody?: string;
		successMessage_i18n?: {[key: string]: string};
		title_i18n?: {[key: string]: string};
		type?: EItemActionType;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-admin/actions`;

		const data = {
			[ITEM_ACTION_DATA_SET_RELATIONSHIP]: dataSetERC,
			confirmationMessage_i18n,
			confirmationMessageType,
			errorMessage_i18n,
			icon,
			label_i18n,
			method,
			modalSize,
			permissionKey,
			requestBody,
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
		const url = `${this.baseUrl}data-set-admin/sorts`;

		const data = {
			[SORT_DATA_SET_RELATIONSHIP]: dataSetERC,
			default: defaultValue,
			fieldName,
			label_i18n,
			orderType,
		};

		return this.post(url, {data});
	}

	async createDataSetListSection({
		dataSetERC = DEFAULT_DATA_SET_ERC,
		fieldName = 'fieldName',
		name = 'title',
	}: {
		dataSetERC?: string;
		fieldName?: string;
		name?: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/list-sections`;

		const data = {
			[LIST_SECTION_DATA_SET_RELATIONSHIP]: dataSetERC,
			fieldName,
			name,
		};

		return this.post(url, {data});
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrl}data-set-admin/data-sets/by-external-reference-code/${erc}`;

		return this.delete(url);
	}

	async updateDataSet({
		additionalAPIURLParameters,
		defaultItemsPerPage,
		defaultVisualizationMode,
		erc = DEFAULT_DATA_SET_ERC,
		label,
		listOfItemsPerPage,
	}: {
		additionalAPIURLParameters?: string;
		defaultItemsPerPage?: number;
		defaultVisualizationMode?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/data-sets/by-external-reference-code/${erc}`;

		const data = {
			additionalAPIURLParameters,
			defaultItemsPerPage,
			defaultVisualizationMode,
			label,
			listOfItemsPerPage,
		};

		return this.patch(url, data);
	}

	async updateDataSetSelectionFilter({
		erc,
		fieldName,
		include,
		itemKey,
		itemLabel,
		label_i18n,
		multiple,
		preselectedValues,
	}: {
		erc: string;
		fieldName?: string;
		include?: boolean;
		itemKey?: string;
		itemLabel?: string;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
	}) {
		const url = `${this.baseUrl}data-set-admin/selection-filters/by-external-reference-code/${erc}`;

		const data = {
			fieldName,
			include,
			itemKey,
			itemLabel,
			label_i18n,
			multiple,
			preselectedValues,
		};

		return this.patch(url, data);
	}
}
