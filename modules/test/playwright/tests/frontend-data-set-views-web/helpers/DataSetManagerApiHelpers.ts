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
		restSchema = 'FDSField',
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
		r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC = DEFAULT_DATA_SET_ERC,
	}: {
		fieldName?: string;
		name?: string;
		r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/cards-sections`;

		const data = {
			fieldName,
			name,
			r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC,
		};

		return this.post(url, {data});
	}

	async createDataSetCreationAction({
		icon,
		label_i18n = {en_US: 'Default Creation Action'},
		modalSize = 'full-screen',
		permissionKey,
		r_fdsViewFDSCreationActionRelationship_c_fdsViewERC = DEFAULT_DATA_SET_ERC,
		title_i18n,
		type = 'link',
		url = liferayConfig.environment.baseUrl,
	}: {
		icon?: string;
		label_i18n?: {[key: string]: string};
		modalSize?: ModalVariantTypes;
		permissionKey?;
		r_fdsViewFDSCreationActionRelationship_c_fdsViewERC: string;
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
			r_fdsViewFDSCreationActionRelationship_c_fdsViewERC,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetField({
		extraBodyParams = {},
		label_i18n = {en_US: 'Title'},
		name = 'title',
		r_fdsViewFDSFieldRelationship_c_fdsViewERC = DEFAULT_DATA_SET_ERC,
		renderer = 'default',
		rendererType = 'internal',
		type = 'string',
	}: {
		extraBodyParams?: any;
		label_i18n?: {[key: string]: string};
		name?: string;
		r_fdsViewFDSFieldRelationship_c_fdsViewERC?: string;
		renderer?: string;
		rendererType?: string;
		type?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/table-sections`;

		const data = {
			label_i18n,
			name,
			r_fdsViewFDSFieldRelationship_c_fdsViewERC,
			renderer,
			rendererType,
			type,
			...extraBodyParams,
		};

		return this.post(url, {data});
	}

	async createDataSetDateFilter({
		fieldName,
		from = '',
		label_i18n = {en_US: 'Title'},
		r_fdsViewFDSDateFilterRelationship_c_fdsViewERC,
		to = '',
		type,
	}: {
		fieldName: string;
		from?: string;
		label_i18n?: {[key: string]: string};
		r_fdsViewFDSDateFilterRelationship_c_fdsViewERC: string;
		to?: string;
		type: 'date' | 'date-time';
	}) {
		const url = `${this.baseUrl}data-set-manager/date-filters`;

		const data = {
			fieldName,
			from,
			label_i18n,
			r_fdsViewFDSDateFilterRelationship_c_fdsViewERC,
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
		r_fdsViewFDSDynamicFilterRelationship_c_fdsViewERC,
		source,
		sourceType,
	}: {
		fieldName: string;
		include?: boolean;
		label_i18n?: {[key: string]: string};
		multiple?: boolean;
		preselectedValues?: string;
		r_fdsViewFDSDynamicFilterRelationship_c_fdsViewERC?: string;
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
			r_fdsViewFDSDynamicFilterRelationship_c_fdsViewERC,
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
		r_fdsViewFDSItemActionRelationship_c_fdsViewERC = DEFAULT_DATA_SET_ERC,
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
		r_fdsViewFDSItemActionRelationship_c_fdsViewERC: string;
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
			r_fdsViewFDSItemActionRelationship_c_fdsViewERC,
			successMessage_i18n,
			title_i18n,
			type,
			url,
		};

		return this.post(endpointUrl, {data});
	}

	async createDataSetListSection({
		fieldName = 'name',
		name = 'title',
		r_fdsViewFDSListSectionRelationship_c_fdsViewERC = DEFAULT_DATA_SET_ERC,
	}: {
		fieldName?: string;
		name?: string;
		r_fdsViewFDSListSectionRelationship_c_fdsViewERC?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/list-sections`;

		const data = {
			fieldName,
			name,
			r_fdsViewFDSListSectionRelationship_c_fdsViewERC,
		};

		return this.post(url, {data});
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrl}data-set-manager/data-sets/by-external-reference-code/${erc}`;

		return this.delete(url);
	}

	async updateDataSet({
		defaultVisualizationMode,
		erc = DEFAULT_DATA_SET_ERC,
		label,
	}: {
		defaultVisualizationMode?: string;
		erc?: string;
		label?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/data-sets/by-external-reference-code/${erc}`;

		const data = {
			defaultVisualizationMode,
			label,
		};

		return this.patch(url, data);
	}
}
