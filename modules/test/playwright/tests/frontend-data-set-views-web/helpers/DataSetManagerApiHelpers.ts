/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {DEFAULT_LABEL} from '../utils/constants';

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';
export class DataSetManagerApiHelpers extends ApiHelpers {
	async createDataSet({
		erc = DEFAULT_DATA_SET_ERC,
		label = DEFAULT_LABEL.DATA_SET,
		restApplication = '/data-set-manager/fields',
		restEndpoint = '/',
		restSchema = 'FDSField',
	}: {
		erc?: string;
		label?: string;
		restApplication?: string;
		restEndpoint?: string;
		restSchema?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/entries`;

		const data = {
			externalReferenceCode: erc,
			label,
			restApplication,
			restEndpoint,
			restSchema,
		};

		return this.post(url, data);
	}

	async createDataSetView({
		defaultItemsPerPage = 20,
		description = 'Sample description',
		erc = 'sampleDataSetERC',
		label = DEFAULT_LABEL.VIEW,
		listOfItemsPerPage = '4, 8, 20, 40, 60',
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC = DEFAULT_DATA_SET_ERC,
		symbol = 'catalog',
	}: {
		defaultItemsPerPage?: number;
		description?: string;
		erc?: string;
		label?: string;
		listOfItemsPerPage?: string;
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC?: string;
		symbol?: string;
	}) {
		const url = `${this.baseUrl}data-set-manager/views`;

		const data = {
			defaultItemsPerPage,
			description,
			externalReferenceCode: erc,
			label,
			listOfItemsPerPage,
			r_fdsEntryFDSViewRelationship_c_fdsEntryERC,
			symbol,
		};

		return this.post(url, data);
	}

	async createDataSetViewCardsSection({
		fieldName = 'name',
		name = 'Title',
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

		return this.post(url, data);
	}

	async createDataSetViewListSection({
		fieldName = 'name',
		name = 'Title',
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

		return this.post(url, data);
	}

	async deleteDataSet({erc = DEFAULT_DATA_SET_ERC}: {erc?: string}) {
		const url = `${this.baseUrl}data-set-manager/entries/by-external-reference-code/${erc}`;

		return this.delete(url);
	}
}
