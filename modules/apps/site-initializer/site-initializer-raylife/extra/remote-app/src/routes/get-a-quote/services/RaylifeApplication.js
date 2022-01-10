import {LiferayAdapt} from '../../../common/services/liferay/adapter';
import {axios} from '../../../common/services/liferay/api';

const RaylifeApplicationAPI = 'o/c/raylifeapplications';

export function getRaylifeApplicationById(raylifeApplicationId) {
	return axios.get(`${RaylifeApplicationAPI}/${raylifeApplicationId}`);
}

/**
 * @param {DataForm}  data Basics form object
 * @returns {Promise<any>}  Status code
 */
export function createOrUpdateRaylifeApplication(data) {
	const payload = LiferayAdapt.adaptToFormApplicationRequest(data);

	if (data?.basics?.applicationId) {
		return axios.patch(
			`${RaylifeApplicationAPI}/${data.basics.applicationId}`,
			payload
		);
	}

	return axios.post(
		`${RaylifeApplicationAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
		payload
	);
}
