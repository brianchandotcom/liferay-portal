/**
 * @typedef {{
 *  streetNumber: string
 *  street: string
 *  city: string
 *  state: string
 *  country: string
 *  zip: string
 * }} Address
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
