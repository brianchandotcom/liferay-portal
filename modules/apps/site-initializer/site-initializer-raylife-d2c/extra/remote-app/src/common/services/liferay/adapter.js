/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '../../../types';
import {allowedProductQuote} from '../../../routes/get-a-quote/utils/webContents';
import {toSlug} from '../../utils';

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

/**
 * @param {{
 * 	  description: {
 *      en_US: string
 *    }
 *    name: {
 *      en_US: string
 *    }
 *    skus: {
 *      price: number
 *      promoPrice: number
 *    }[]
 * }[]}  data Array of products
 * @returns {ProductQuote[]} Array of business types
 */
const adaptToProductQuote = (data = []) =>
	data.map(({description, name, productId, skus}) => ({
		description,
		id: productId,
		period: `($${_formatCommerceProductPrice(
			skus[0].price.promoPrice
		)}-${_formatCommerceProductPrice(skus[0].price.price)}/mo)`,
		template: {
			allowed: allowedProductQuote(name),
			name: toSlug(name),
		},
		title: name,
	}));

export const LiferayAdapt = {
	adaptToFormApplicationRequest,
	adaptToProductQuote,
	adaptToRaylifeApplicationToForm,
};
