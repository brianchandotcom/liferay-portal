/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LiferayAdapt} from '../../../common/services/liferay/adapter';
import {axios} from '../../../common/services/liferay/api';
import {getGuestPermissionToken} from '../../../common/services/token';
import {Liferay} from '../../../common/utils/liferay';

const RaylifeApplicationAPI = 'o/c/raylifeapplications';

export function getRaylifeApplicationById(raylifeApplicationId) {
	return axios.get(`${RaylifeApplicationAPI}/${raylifeApplicationId}`);
}

/**
 * @param {DataForm}  form Basics form object
 * @returns {Promise<any>}  Status code
 */

const updateRaylifeApplication = async (applicationId, payload = null) => {
	if (Liferay.ThemeDisplay.getUserName()) {
		return axios.patch(
			`${RaylifeApplicationAPI}/${applicationId}`,
			payload
		);
	}

	const {access_token} = await getGuestPermissionToken();

	Liferay.Util.SessionStorage.setItem(
		'raylife-guest-permission-token',
		access_token,
		Liferay.Util.SessionStorage.TYPES.NECESSARY
	);

	return axios.patch(`${RaylifeApplicationAPI}/${applicationId}`, payload, {
		headers: {
			'Authorization': `Bearer ${access_token}`,
			'Content-Type': 'application/json',
		},
	});
};

export function createOrUpdateRaylifeApplication(form, status) {
	const payload = LiferayAdapt.adaptToFormApplicationRequest(form, status);
	const applicationId = form?.basics?.applicationId;

	if (applicationId) {
		return updateRaylifeApplication(applicationId, payload);
	}

	return axios.post(`${RaylifeApplicationAPI}/`, payload).catch((error) => {
		console.error(error);
	});
}

export function updateRaylifeApplicationStatus(applicationId, status) {
	const payload = {
		applicationStatus: {
			key: status?.key,
			name: status?.name,
		},
	};

	return updateRaylifeApplication(applicationId, payload);
}
