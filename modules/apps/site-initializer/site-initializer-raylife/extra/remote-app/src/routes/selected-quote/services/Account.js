import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-admin-user';

export function createAccount(name) {
	const payload = {
		name,
		status: 0,
		type: 'business',
	};

	return axios.post(`${DeliveryAPI}/v1.0/accounts`, payload);
}
