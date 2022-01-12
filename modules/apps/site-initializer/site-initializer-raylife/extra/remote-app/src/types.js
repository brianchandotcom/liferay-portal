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

/**
 * @typedef {{
 * categoryId: string
 * titleCurrentValue: string
 * descriptionCurrentValue: string
 * parentCategoryId: string
 * }} AssetCategoryResponse
 */

/**
 * @typedef {{
 * id: string
 * title: string
 * description: string
 * }} BusinessType
 */

/**
 * @typedef {{
 * firstName: string
 * lastName: string
 * phone: string
 * website: string
 * address: string
 * addressApt: string
 * city: string
 * state: string
 * zip: string
 * }} BasicsFormApplicationRequest
 */

/**
 * @typedef {{
 * firstName: string
 * lastName: string
 * phone: string
 * website: string
 * address: string
 * addressApt: string
 * city: string
 * state: string
 * zip: string
 * }} BasicsFormApplicationRequest
 */

/**
 * @typedef {{
 * applicationId: number
 * businessSearch: string
 * businessCategoryId: string
 * properties: {
 *  businessClassCode: string
 *  naics: string
 *  segment: string
 * }
 * businessInformation: {
 *  firstName: string
 *  lastName: string
 *  business: {
 *    email: string
 *    website: string
 *    phone: string
 *    location: {
 *      address: string
 *      addressApt: string
 *      city: string
 *      state: string
 *      zip: string
 *    }
 *  }
 * }
 * }} BasicsForm
 */

// TODO COMPLETO

/**
 * @typedef {{
 * applicationId: number
 * businessCategoryId: string
 * businessSearch: string
 * ProductQuote: number
 * properties: {
 *  businessClassCode: string
 *  naics: string
 *  segment: string
 * }
 * businessInformation: {
 *  firstName: string
 *  lastName: string
 *  business: {
 *    email: string
 *    website: string
 *    phone: string
 *    location: {
 *      address: string
 *      addressApt: string
 *      city: string
 *      state: string
 *      zip: string
 *    }
 *  }
 * }
 * business:{
 *	hasAutoPolicy: string
 *	hasSellProductsUnderOwnBrand: string
 *	hasStoredCustomerInformation: string
 *	legalEntity: string
 *	overallSales: string
 *	salesMerchandise: string
 *	yearsOfExperience: string
 *  }
 * employees: {
 *  annualPayrollForEmployees: string
 *  annualPayrollForOwner: string
 *  businessOperatesYearRound: string
 *  estimatedAnnualGrossRevenue: string
 *  fein: string
 *  hasFein: string
 *  partTimeEmployees: string
 *  startBusinessAtYear: string
 * }
 * property:{
 *  buildingSquareFeetOccupied: string
 *  doOwnBuildingAtAddress: string
 *  isPrimaryBusinessLocation: string
 *  isThereDivingBoards: string
 *  isThereSwimming: string
 *  stories: string
 *  totalBuildingSquareFeet: string
 *  yearBuilding: string
 * }
 * }} DataForm
 */

/**
 * @typedef {{
 * id: string
 * title: string
 * period: string
 * description: string
 * }} ProductQuote
 */
