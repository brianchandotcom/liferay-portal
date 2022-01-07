import '../../../types';
import {Liferay} from '../../utils/liferay';

import {LiferayAdapt} from './adapter';
import LiferayFetchAPI, {REACT_APP_LIFERAY_API} from './api';

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
 * @returns {Promise<ProductQuote[]>)} Array of Product Quote
 */
const getProductQuotes = async () => {
	const products = await _getProductsByCategoryId();

	const productQuotes = LiferayAdapt.adaptToProductQuote(products.items);

	return productQuotes;
};

const getQuoteComparison = async () => {
	const response = await LiferayFetchAPI.get(
		`${quoteComparisonAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`
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

	const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
	const pathSplit = pathname.split('/').filter(Boolean);
	siteName = `/${pathSplit.slice(0, pathSplit.length - 1).join('/')}`;

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
 * @param {BasicsFormApplicationRequest} payload - Payload used to create the application
 * @returns {Promise<any>}  Fetch Response
 */
const _postBasicsFormApplication = (body) =>
	LiferayFetchAPI.post(
		`${RaylifeApplicationAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
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
	getLiferaySiteName,
	getProductQuotes,
	getQuoteComparison,
	getQuoteComparisonById,
	uploadToDocumentsAndMedia,
};
