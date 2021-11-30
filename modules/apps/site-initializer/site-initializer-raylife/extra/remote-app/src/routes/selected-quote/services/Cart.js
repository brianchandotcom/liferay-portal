import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-delivery-cart';

export function getPaymentMethods(orderId) {
	return axios.get(`${DeliveryAPI}/v1.0/carts/${orderId}/payment-methods`);
}
