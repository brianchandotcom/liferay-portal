import {axios} from '~/common/services/liferay/api';

const RaylifeApplicationAPI = 'o/c/raylifeapplications';

export function getRaylifeApplicationById(raylifeApplicationId) {
	return axios.get(`${RaylifeApplicationAPI}/${raylifeApplicationId}`);
}
