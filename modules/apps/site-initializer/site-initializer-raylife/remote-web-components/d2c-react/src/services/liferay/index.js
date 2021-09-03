import '../../types';

import Axios from 'axios';
import Cookies from 'js-cookie';

import {LiferayAdapt} from './adapter';

const {
	REACT_APP_LIFERAY_API = '',
	REACT_APP_LIFERAY_AUTH_PASSWORD = '',
	REACT_APP_LIFERAY_AUTH_USERNAME = '',
} = process.env;

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

	const parentId = Cookies.get('raylife-product');

	const assetCategories = await _getAssetCategoriesByParentId(
		parentId,
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
	const {
		data: {categories},
	} = await LiferayAPI.get(
		'/api/jsonws/assetcategory/search-categories-display',
		{
			params: {
				'+sort': 'com.liferay.portal.kernel.search.Sort',
				end: 50,
				groupIds: 0,
				parentCategoryIds: id,
				'sort.fieldName': 'name',
				'sort.type': 6,
				start: 0,
				title: normalizedFilter,
				vocabularyIds: '',
			},
		}
	);

	return categories;
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
	return LiferayAPI.post('/o/c_raylifeapplications', payload);
};

/**
 * @param {BasicsFormApplicationRequest} payload - Payload used to update existing application
 * @returns {Promise<any>}  Axios Response
 */
const _patchBasicsFormApplication = (payload, id) => {
	return LiferayAPI.patch(`/o/c_raylifeapplications/${id}`, payload);
};

const LiferayAPI = Axios.create({
	auth: {
		password: REACT_APP_LIFERAY_AUTH_PASSWORD,
		username: REACT_APP_LIFERAY_AUTH_USERNAME,
	},
	baseURL: REACT_APP_LIFERAY_API,
	headers: {
		'x-csrf-token': getLiferayAuthenticationToken(),
	},
});

export const LiferayService = {
	LiferayAPI,
	createOrUpdateRaylifeApplication,
	getBusinessTypes,
	getCategoryProperties,
	getLiferayAuthenticationToken,
	getLiferayGroupId,
	getProductQuotes,
};
