import Axios from 'axios';
import '~/types';

import {LiferayAdapt} from './adapter';
import {STORAGE_KEYS, Storage} from './storage';

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const LiferayObjectAPI = 'o/c/raylifeapplications';

const baseFetch = async (url, {body, method = 'GET'} = {}) => {
	const liferayAPIUrl = new URL(`${REACT_APP_LIFERAY_API}/${url}`);

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(liferayAPIUrl, {
		...(body && {body: JSON.stringify(body)}),
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': getLiferayAuthenticationToken(),
		},
		method,
	});

	const data = await response.json();

	return {data};
};

const LiferayFetchAPI = {
	delete: (url) => baseFetch(url, {method: 'DELETE'}),
	get: (url) => baseFetch(url),
	post: (url, options) => baseFetch(url, {...options, method: 'POST'}),
	put: (url, options) => baseFetch(url, {...options, method: 'PUT'}),
};

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
 * @returns {string} Liferay Authentication Token
 */
const getLiferayAuthenticationToken = () => {
	try {
		// eslint-disable-next-line no-undef
		const token = Liferay.authToken;

		return token;
	} catch (error) {
		console.warn('Not able to find Liferay auth token\n', error);

		return '';
	}
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
	const URL = `/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=skus,catalog&page=1&pageSize=50`;

	const {data} = await LiferayAPI.get(URL);

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
 * @param {string} id - Parent category Id of asset categories
 * @returns {Promise<CategoryPropertyResponse[]>}  Array of matched categories
 */
const getCategoryProperties = async (id) => {
	const {data} = await LiferayAPI.get(
		'/api/jsonws/assetcategoryproperty/get-category-properties',
		{
			params: {
				entryId: id,
			},
		}
	);

	return data;
};

/**
 * @param {BasicsFormApplicationRequest} payload - Payload used to create the application
 * @returns {Promise<any>}  Axios Response
 */
const _postBasicsFormApplication = (payload) => {
	return LiferayAPI.post(LiferayObjectAPI, payload);
};

/**
 * @param {BasicsFormApplicationRequest} payload - Payload used to update existing application
 * @returns {Promise<any>}  Axios Response
 */
const _patchBasicsFormApplication = (payload, id) => {
	return LiferayAPI.patch(`${LiferayObjectAPI}/${id}`, payload);
};

const LiferayAPI = Axios.create({
	baseURL: REACT_APP_LIFERAY_API,
	headers: {
		'x-csrf-token': getLiferayAuthenticationToken(),
	},
});

export const LiferayService = {
	LiferayAPI,
	LiferayFetchAPI,
	createOrUpdateRaylifeApplication,
	getBusinessTypes,
	getCategoryProperties,
	getLiferayAuthenticationToken,
	getLiferayGroupId,
	getLiferaySiteName,
	getProductQuotes,
};
