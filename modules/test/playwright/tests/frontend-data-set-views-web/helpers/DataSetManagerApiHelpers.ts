/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {liferayConfig} from '../../../liferay.config';
import {DEFAULT_LABEL} from '../utils/constants';
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
		restSchema = 'DSMTableSection',
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
		fieldName = 'name',
		name = 'title',
		r_dsmDataSetCardsSectionRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
	}: {
		fieldName?: string;
		name?: string;
		r_dsmDataSetCardsSectionRelationship_c_dsmDataSetERC?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/cards-sections`;

		const data = {
			fieldName,
			name,
			r_dsmDataSetCardsSectionRelationship_c_dsmDataSetERC,
		};

		return this.post(url, {data});
	}

	async createDataSetCreationAction({
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = 'full-screen',
		permissionKey,
		r_dsmDataSetCreationActionRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: ModalVariantTypes;
		permissionKey?;
		r_dsmDataSetCreationActionRelationship_c_dsmDataSetERC: string;
		title_i18n?: {[key: string]: string};
		type?: CreationActionTypes;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-manager/actions`;

		const data = {
			icon,
			label_i18n,
			modalSize,
			permissionKey,
			r_dsmDataSetCreationActionRelationship_c_dsmDataSetERC,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetTableSection({
		extraBodyParams = {},
		label_i18n = {en_US: 'Title'},
		name = 'title',
		r_dsmDataSetTableSectionRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
		renderer = 'default',
		rendererType = 'internal',
		sortable = false,
		type = 'string',
	}: {
		extraBodyParams?: any;
		label_i18n?: {[key: string]: string};
		name?: string;
		r_dsmDataSetTableSectionRelationship_c_dsmDataSetERC?: string;
		renderer?: string;
		rendererType?: string;
		sortable?: boolean;
		type?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/table-sections`;

		const data = {
			label_i18n,
			name,
			r_dsmDataSetTableSectionRelationship_c_dsmDataSetERC,
			renderer,
			rendererType,
			sortable,
			type,
			...extraBodyParams,
		};

		return this.post(url, {data});
	}

	async createDataSetDateFilter({
		fieldName,
		from = '',
		label_i18n = {en_US: 'Title'},
		r_dsmDataSetDateFilterRelationship_c_dsmDataSetERC,
		to = '',
		type,
	}: {
		fieldName: string;
		from?: string;
		label_i18n?: {[key: string]: string};
		r_dsmDataSetDateFilterRelationship_c_dsmDataSetERC: string;
		to?: string;
		type: 'date' | 'date-time';
	}) {
		const url = `${this.baseUrl}data-set-manager/date-filters`;

		const data = {
			fieldName,
			from,
			label_i18n,
			r_dsmDataSetDateFilterRelationship_c_dsmDataSetERC,
			to,
			type,
		};

		return this.post(url, {data});
	}

	async createDataSetSelectionFilter({
		fieldName,
		include = true,
		label_i18n,
		multiple = false,
		preselectedValues = '[]',
		r_dsmDataSetSelectionFilterRelationship_c_dsmDataSetERC,
		source,
		sourceType,
	}: {
		fieldName: string;
		include?: boolean;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		r_dsmDataSetSelectionFilterRelationship_c_dsmDataSetERC?: string;
		source: string;
		sourceType: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/selection-filters`;

		const data = {
			fieldName,
			include,
			label_i18n,
			multiple,
			preselectedValues,
			r_dsmDataSetSelectionFilterRelationship_c_dsmDataSetERC,
			source,
			sourceType,
		};

		return this.post(url, {data});
	}

	async createDataSetItemAction({
		confirmationMessage_i18n,
		confirmationMessageType,
		errorMessage_i18n,
		icon,
		label_i18n = {en_US: 'Default Item Action'},
		method,
		modalSize = 'full-screen',
		permissionKey,
		r_dsmDataSetItemActionRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
		successMessage_i18n,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		confirmationMessageType?: string;
		confirmationMessage_i18n?: {[key: string]: string};
		errorMessage_i18n?: {[key: string]: string};
		icon?: string;
		label_i18n?: {[key: string]: string};
		method?: AsyncActionMethod;
		modalSize?: ModalVariantTypes;
		permissionKey?;
		r_dsmDataSetItemActionRelationship_c_dsmDataSetERC: string;
		successMessage_i18n?: {[key: string]: string};
		title_i18n?: {[key: string]: string};
		type?: ItemActionTypes;
		url?: string;
	}) {
		const endpointUrl = `${this.baseUrl}data-set-manager/actions`;

		const data = {
			confirmationMessage_i18n,
			confirmationMessageType,
			errorMessage_i18n,
			icon,
			label_i18n,
			method,
			modalSize,
			permissionKey,
			r_dsmDataSetItemActionRelationship_c_dsmDataSetERC,
			successMessage_i18n,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetSort({
		defaultValue = false,
		fieldName = 'dateCreated',
		label_i18n = {en_US: 'Date Created'},
		orderType = 'asc',
		r_dsmDataSetSortRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
	}: {
		defaultValue?: boolean;
		fieldName?: string;
		label_i18n?: {[key: string]: string};
		orderType?: string;
		r_dsmDataSetSortRelationship_c_dsmDataSetERC?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/sorts`;

		const data = {
			default: defaultValue,
			fieldName,
			label_i18n,
			orderType,
			r_dsmDataSetSortRelationship_c_dsmDataSetERC,
		};

		return this.post(url, {data});
	}

	async createDataSetListSection({
		fieldName = 'name',
		name = 'title',
		r_dsmDataSetListSectionRelationship_c_dsmDataSetERC = DEFAULT_DATA_SET_ERC,
	}: {
		fieldName?: string;
		name?: string;
		r_dsmDataSetListSectionRelationship_c_dsmDataSetERC?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/list-sections`;

		const data = {
			fieldName,
			name,
			r_dsmDataSetListSectionRelationship_c_dsmDataSetERC,
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
