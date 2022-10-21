/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	sessionStorage.setItem('raylife-guest-permission-token', access_token);

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

	return axios.post(`${RaylifeApplicationAPI}/`, payload);
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
