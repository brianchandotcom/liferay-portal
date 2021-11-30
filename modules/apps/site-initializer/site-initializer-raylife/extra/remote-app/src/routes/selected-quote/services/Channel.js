import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-admin-channel';
const ChannelName = 'Raylife Channel';

export function getChannel() {
	return axios.get(`${DeliveryAPI}/v1.0/channels?search=${ChannelName}`);
}
