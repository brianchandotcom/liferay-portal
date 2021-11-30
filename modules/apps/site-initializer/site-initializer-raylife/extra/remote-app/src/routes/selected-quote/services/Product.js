import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-admin-catalog';

export function getSku(productId) {
	return axios.get(`${DeliveryAPI}/v1.0/products/${productId}/skus`);
}
