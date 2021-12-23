import {axios} from '../../../common/services/liferay/api';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';

const DeliveryAPI = 'o/headless-commerce-admin-order';

export function createOrder(accountId, channelId, skuId) {
	const raylifeApplicationForm = JSON.parse(
		Storage.getItem(STORAGE_KEYS.APPLICATION_FORM)
	);

	const {
		business: {
			location: {address, addressApt, city, state, zip},
			phone,
		},
		firstName,
		lastName,
	} = raylifeApplicationForm?.basics?.businessInformation;

	const userAddress = {
		city,
		countryISOCode: 'US',
		description: addressApt,
		id: 0,
		latitude: 0,
		longitude: 0,
		name: `${firstName} ${lastName}`,
		phoneNumber: phone,
		regionISOCode: state,
		street1: address,
		zip,
	};

	const payload = {
		accountId,
		billingAddress: userAddress,
		channelId,
		currencyCode: 'USD',
		orderItems: [
			{
				quantity: 1,
				skuId,
			},
		],
		orderStatus: 2,
		shippingAddress: userAddress,
		shippingAmount: 0,
		shippingWithTaxAmount: 0,
	};

	return axios.post(`${DeliveryAPI}/v1.0/orders`, payload);
}

export function updateOrder(paymentMethod, orderItem, orderId) {
	const payload = {
		orderItems: [orderItem],
		paymentMethod,
		subtotal: orderItem.finalPrice,
		total: orderItem.finalPrice,
	};

	return axios.patch(`${DeliveryAPI}/v1.0/orders/${orderId}`, payload);
}
