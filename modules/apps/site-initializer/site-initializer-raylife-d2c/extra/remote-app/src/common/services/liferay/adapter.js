/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../types';

import {allowedProductQuote} from '../../../routes/get-a-quote/utils/webContents';
import {toSlug} from '../../utils';
import {axios} from './api';

const headlessAPI = 'o/headless-commerce-delivery-catalog/v1.0';

const _formatCommerceProductPrice = (price) => parseInt(price, 10);

/**
 * @param {DataForm}  data Basics form object
 * @returns {BasicsFormApplicationRequest} Basics Form ready for application request
 */
const adaptToRaylifeApplicationToForm = (data) => {
	const basics = {
		applicationId: data.id,
		businessCategoryId: data.businessCategoryId,
		businessInformation: {
			business: {
				email: data.email,
				location: {
					address: data.address,
					addressApt: data.addressApt,
					city: data.city,
					state: data.state,
					zip: data.zip,
				},
				phone: data.phone,
				website: data.website,
			},
			firstName: data.firstName,
			lastName: data.lastName,
		},
		businessSearch: data.businessSearch,
		productId: Number(data.productId),
		productName: data.productName,
		properties: {
			naics: data.naics,
			segment: data.segment,
		},
	};

	const business = {
		hasAutoPolicy: data.hasAutoPolicy,
		hasSellProductsUnderOwnBrand: data.hasSellProductsUnderOwnBrand,
		hasStoredCustomerInformation: data.hasStoredCustomerInformation,
		legalEntity: data.legalEntity,
		overallSales: data.overallSales,
		salesMerchandise: data.salesMerchandise,
		yearsOfExperience: data.yearsOfExperience,
	};

	const employees = {
		annualPayrollForEmployees: data.annualPayrollForEmployees,
		annualPayrollForOwner: data.annualPayrollForOwner,
		businessOperatesYearRound: data.businessOperatesYearRound,
		estimatedAnnualGrossRevenue: data.estimatedAnnualGrossRevenue,
		fein: data.fein,
		hasFein: data.hasFein,
		partTimeEmployees: data.partTimeEmployees,
		startBusinessAtYear: data.startBusinessAtYear,
	};

	const property = {
		buildingSquareFeetOccupied: data.buildingSquareFeetOccupied,
		doOwnBuildingAtAddress: data.doOwnBuildingAtAddress,
		isPrimaryBusinessLocation: data.isPrimaryBusinessLocation,
		isThereDivingBoards: data.isThereDivingBoards,
		isThereSwimming: data.isThereSwimming,
		stories: data.stories,
		totalBuildingSquareFeet: data.totalBuildingSquareFeet,
		yearBuilding: data.yearBuilding,
	};

	const formState = {
		basics,
		business,
		employees,
		property,
	};

	for (const form in formState) {
		const formKeyHasAnyValue = Object.values(formState[form]).some(Boolean);

		if (!formKeyHasAnyValue) {
			delete formState[form];
		}
	}

	return formState;
};

/**
 * @param {DataForm}  data Basics form object
 * @returns {BasicsFormApplicationRequest} Basics Form ready for application request
 */
const adaptToFormApplicationRequest = (form, status) => ({
	address: form?.basics?.businessInformation?.business?.location?.address,
	addressApt:
		form?.basics?.businessInformation?.business?.location?.addressApt,
	applicationCreateDate: new Date().toISOString().split('T')[0],
	applicationId: form?.basics?.applicationId,
	applicationStatus: {
		key: status?.key,
		name: status?.name,
	},
	businessCategoryId: form?.basics?.businessCategoryId,
	city: form?.basics?.businessInformation?.business?.location?.city,
	dataJSON: JSON.stringify({
		annualPayrollForEmployees: form?.employees?.annualPayrollForEmployees,
		annualPayrollForOwner: form?.employees?.annualPayrollForOwner,
		buildingSquareFeetOccupied: form?.property?.buildingSquareFeetOccupied,
		businessOperatesYearRound: form?.employees?.businessOperatesYearRound,
		businessSearch: form?.basics?.businessSearch,
		doOwnBuildingAtAddress: form?.property?.doOwnBuildingAtAddress,
		estimatedAnnualGrossRevenue:
			form?.employees?.estimatedAnnualGrossRevenue,
		fein: form?.employees?.fein,
		hasAutoPolicy: form?.business?.hasAutoPolicy,
		hasFein: form?.employees?.hasFein,
		hasSellProductsUnderOwnBrand:
			form?.business?.hasSellProductsUnderOwnBrand,
		hasStoredCustomerInformation:
			form?.business?.hasStoredCustomerInformation,
		isPrimaryBusinessLocation: form?.property?.isPrimaryBusinessLocation,
		isThereDivingBoards: form?.property?.isThereDivingBoards,
		isThereSwimming: form?.property?.isThereSwimming,
		legalEntity: form?.business?.legalEntity,
		naics: form?.basics?.properties?.naics,
		overallSales: form?.business?.overallSales,
		partTimeEmployees: form?.employees?.partTimeEmployees,
		salesMerchandise: form?.business?.salesMerchandise,
		segment: form?.basics?.properties?.segment,
		startBusinessAtYear: form?.employees?.startBusinessAtYear,
		stories: form?.property?.stories,
		totalBuildingSquareFeet: form?.property?.totalBuildingSquareFeet,
		yearBuilding: form?.property?.yearBuilding,
		yearsOfExperience: form?.business?.yearsOfExperience,
	}),
	email: form?.basics?.businessInformation?.business?.email,
	firstName: form?.basics?.businessInformation?.firstName,
	lastName: form?.basics?.businessInformation?.lastName,
	phone: form?.basics?.businessInformation?.business?.phone,
	productCategory: form?.basics?.productCategory,
	productId: `${form?.basics?.productId}`,
	productName: form?.basics?.productName,
	state: form?.basics?.businessInformation?.business?.location?.state,
	website: form?.basics?.businessInformation?.business?.website,
	zip: form?.basics?.businessInformation?.business?.location?.zip,
});

async function getSkuPrice(channelId, productId) {
	const sku = await axios.get(
		`${headlessAPI}/channels/${channelId}/products/${productId}/skus`
	);

	return sku.data.items[0].price;
}

const adaptToProductQuote = async (channelId, items = []) => {
	const productsList = [];

	for (const item of items) {
		const {description, name, productId} = item;
		const {price, promoPrice} = await getSkuPrice(channelId, productId);
		productsList.push({
			description,
			id: productId,
			period: `($${_formatCommerceProductPrice(
				promoPrice
			)}-${_formatCommerceProductPrice(price)}/mo)`,
			template: {
				allowed: allowedProductQuote(name),
				name: toSlug(name),
			},
			title: name,
		});
	}

	return productsList;
};

export const LiferayAdapt = {
	adaptToFormApplicationRequest,
	adaptToProductQuote,
	adaptToRaylifeApplicationToForm,
};
