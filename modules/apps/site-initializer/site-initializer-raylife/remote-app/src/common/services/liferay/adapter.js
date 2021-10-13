import '~/types';
import {toSlug} from '~/common/utils';
import {allowedProductQuote} from '~/routes/get-a-quote/utils/webContents';

const _formatCommerceProductPrice = (price) => parseInt(price, 10);

/**
 * @param {AssetCategoryResponse[]}  data Array of matched categories
 * @returns {BusinessType[]} Array of business types
 */
const adaptToBusinessType = (data) =>
	data.map(({description, id, name, taxonomyCategoryProperties}) => ({
		description,
		id,
		taxonomyCategoryProperties,
		title: name,
	}));

/**
 * @param {DataForm}  data Basics form object
 * @returns {BasicsFormApplicationRequest} Basics Form ready for application request
 */
const adaptToFormApplicationRequest = (form) => ({
	address: form?.basics?.businessInformation?.business?.location?.address,
	addressApt:
		form?.basics?.businessInformation?.business?.location?.addressApt,
	annualPayrollForEmployees: form?.employees?.annualPayrollForEmployees,
	annualPayrollForOwner: form?.employees?.annualPayrollForOwner,
	buildingSquareFeetOccupied: form?.property?.buildingSquareFeetOccupied,
	businessOperatesYearRound: form?.employees?.businessOperatesYearRound,
	city: form?.basics?.businessInformation?.business?.location?.city,
	doOwnBuildingAtAddress: form?.property?.doOwnBuildingAtAddress,
	email: form?.basics?.businessInformation?.business?.email,
	estimatedAnnualGrossRevenue: form?.employees?.estimatedAnnualGrossRevenue,
	fein: form?.employees?.fein,
	firstName: form?.basics?.businessInformation?.firstName,
	hasAutoPolicy: form?.business?.hasAutoPolicy,
	hasFein: form?.employees?.hasFein,
	hasSellProductsUnderOwnBrand: form?.business?.hasSellProductsUnderOwnBrand,
	hasStoredCustomerInformation: form?.business?.hasStoredCustomerInformation,
	isPrimaryBusinessLocation: form?.property?.isPrimaryBusinessLocation,
	isThereDivingBoards: form?.property?.isThereDivingBoards,
	isThereSwimming: form?.property?.isThereSwimming,
	lastName: form?.basics?.businessInformation?.lastName,
	legalEntity: form?.business?.legalEntity,
	overallSales: form?.business?.overallSales,
	partTimeEmployees: form?.employees?.partTimeEmployees,
	phone: form?.basics?.businessInformation?.business?.phone,
	salesMerchandise: form?.business?.salesMerchandise,
	startBusinessAtYear: form?.employees?.startBusinessAtYear,
	state: form?.basics?.businessInformation?.business?.location?.state,
	stories: form?.property?.stories,
	totalBuildingSquareFeet: form?.property?.totalBuildingSquareFeet,
	website: form?.basics?.businessInformation?.business?.website,
	yearBuilding: form?.property?.yearBuilding,
	yearsOfExperience: form?.business?.yearsOfExperience,
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
		description: description.en_US,
		id: productId,
		period: `($${_formatCommerceProductPrice(
			skus[0].promoPrice
		)}-${_formatCommerceProductPrice(skus[0].price)}/mo)`,
		template: {
			allowed: allowedProductQuote(name.en_US),
			name: toSlug(name.en_US),
		},
		title: name.en_US,
	}));

export const LiferayAdapt = {
	adaptToBusinessType,
	adaptToFormApplicationRequest,
	adaptToProductQuote,
};
