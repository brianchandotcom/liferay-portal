import '~/types';

import {LiferayAdapt} from './adapter';
import LiferayFetchAPI, {
	REACT_APP_LIFERAY_API,
	getLiferayAuthenticationToken,
} from './api';
import {STORAGE_KEYS, Storage} from './storage';
import {getLiferayGroupId, getScopeGroupId} from './themeDisplay';

const RaylifeApplicationAPI = 'o/c/raylifeapplications';
const DeliveryAPI = 'o/headless-delivery';
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

const getQuoteComparison = async () => {
	const response = await LiferayFetchAPI.get(
		`${quoteComparisonAPI}/scopes/${getScopeGroupId()}`
	);

	return response.data;
};

const getQuoteComparisonById = async (id) => {
	const response = await LiferayFetchAPI.get(`${quoteComparisonAPI}/${id}`);

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

const uploadToDocumentsAndMedia = (folderId) => {
	LiferayFetchAPI.post(
		`${DeliveryAPI}/v1.0/document-folders/${folderId}/documents`,
		{
			headers: {
				'Content-Type': 'multipart/form-data',
			},
		}
	);
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
	REACT_APP_LIFERAY_API,
	createOrUpdateRaylifeApplication,
	fetch: LiferayFetchAPI,
	getBusinessTypes,
	getLiferayAuthenticationToken,
	getLiferayGroupId,
	getLiferaySiteName,
	getProductQuotes,
	getQuoteComparison,
	getQuoteComparisonById,
	uploadToDocumentsAndMedia,
};
