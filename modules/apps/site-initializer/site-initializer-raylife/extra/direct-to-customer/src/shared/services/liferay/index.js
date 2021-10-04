import '~/types';

import {LiferayAdapt} from './adapter';
import LiferayFetchAPI, {getLiferayAuthenticationToken} from './api';
import {STORAGE_KEYS, Storage} from './storage';

const RaylifeApplicationAPI = 'o/c/raylifeapplications';
const quoteComparisonAPI = 'o/c/quotecomparisons';

/**
 * @param {DataForm}  data Basics form object
 * @returns {Promise<any>}  Status code
 */
const createOrUpdateRaylifeApplication = async (data) => {
	const payload = LiferayAdapt.adaptToFormApplicationRequest(data);

	if (data?.basics?.applicationId) {
		return _patchBasicsFormApplication(
			payload,
			data?.basics?.applicationId
		);
	}

	return _postBasicsFormApplication(payload);
};

/**
 * @param {string} filter - Search string used to filter the results
 * @returns {Promise<BusinessType[]>} Filtered Array of business types
 */
const getBusinessTypes = async (filter = '') => {
	if (!filter.length) {
		return [];
	}

	const normalizedFilter = filter.toLowerCase().replace(/\\/g, '');

	const productParentId = JSON.parse(Storage.getItem(STORAGE_KEYS.PRODUCT))
		?.product;

	const assetCategories = await _getAssetCategoriesByParentId(
		productParentId,
		normalizedFilter
	);

	return LiferayAdapt.adaptToBusinessType(assetCategories);
};

/**
 * @returns {Promise<ProductQuote[]>)} Array of Product Quote
 */
const getProductQuotes = async () => {
	const products = await _getProductsByCategoryId();

	const productQuotes = LiferayAdapt.adaptToProductQuote(products.items);

	return productQuotes;
};

/**
 * @returns {string} Liferay Group Id
 */
const getLiferayGroupId = () => {
	try {
		// eslint-disable-next-line no-undef
		const groupId = Liferay.ThemeDisplay.getSiteGroupId();

		return groupId;
	} catch (error) {
		console.warn('Not able to find Liferay Group Id\n', error);

		return '';
	}
};

/**
 * @returns {string} Liferay Scope Group Id
 */
const getScopeGroupId = () => {
	try {
		// eslint-disable-next-line no-undef
		const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();

		return scopeGroupId;
	} catch (error) {
		console.warn('Not able to find Liferay Scope Group Id\n', error);

		return '';
	}
};

const getQuoteComparison = async () => {
	const response = await LiferayFetchAPI.get(
		`${quoteComparisonAPI}/scopes/${getScopeGroupId()}`
	);

	return response.data;
};

/**
 * @returns {string} Liferay Authentication Token
 */
const getLiferaySiteName = () => {
	let siteName = '/web/raylife';
	try {
		// eslint-disable-next-line no-undef
		const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
		const pathSplit = pathname.split('/').filter(Boolean);
		siteName = `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;
	} catch (error) {
		console.warn('Not able to find Liferay PathName\n', error);
	}

	return siteName;
};

const _getProductsByCategoryId = async () => {
	const URL = `o/headless-commerce-admin-catalog/v1.0/products?nestedFields=skus,catalog&page=1&pageSize=50`;

	const {data} = await LiferayFetchAPI.get(URL);

	return data;
};

/**
 * @param {string} id - Parent category Id of asset categories
 * @returns {Promise<AssetCategoryResponse[]>}  Array of matched categories
 */
const _getAssetCategoriesByParentId = async (id, normalizedFilter) => {
	const filter = `filter=contains(name, '${normalizedFilter}')`;
	const {data} = await LiferayFetchAPI.get(
		`o/headless-admin-taxonomy/v1.0/taxonomy-categories/${id}/taxonomy-categories?${filter}`
	);

	return data?.items || [];
};

/**
 * @param {BasicsFormApplicationRequest} payload - Payload used to create the application
 * @returns {Promise<any>}  Fetch Response
 */
const _postBasicsFormApplication = (body) =>
	LiferayFetchAPI.post(
		`${RaylifeApplicationAPI}/scopes/${getScopeGroupId()}`,
		{
			body,
		}
	);

/**
 * @param {BasicsFormApplicationRequest} payload - Payload used to update existing application
 * @returns {Promise<any>}  Fetch Response
 */
const _patchBasicsFormApplication = (body, id) => {
	return LiferayFetchAPI.patch(`${RaylifeApplicationAPI}/${id}`, {body});
};

export const LiferayService = {
	createOrUpdateRaylifeApplication,
	fetch: LiferayFetchAPI,
	getBusinessTypes,
	getLiferayAuthenticationToken,
	getLiferayGroupId,
	getLiferaySiteName,
	getProductQuotes,
	getQuoteComparison,
};
