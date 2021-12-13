import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-delivery-cart';

export function getPaymentMethods(orderId) {
	return axios.get(`${DeliveryAPI}/v1.0/carts/${orderId}/payment-methods`);
}

export function getPaymentMethodURL(orderId, callbackURL) {
	return axios.get(
		`${DeliveryAPI}/v1.0/carts/${orderId}/payment-url?callbackURL=${callbackURL}`
	);
}
