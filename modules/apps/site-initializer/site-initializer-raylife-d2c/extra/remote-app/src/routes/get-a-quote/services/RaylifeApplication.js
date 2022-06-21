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

const RaylifeApplicationAPI = 'o/c/raylifeapplications';

export function getRaylifeApplicationById(raylifeApplicationId) {
	return axios.get(`${RaylifeApplicationAPI}/${raylifeApplicationId}`);
}

/**
 * @param {DataForm}  form Basics form object
 * @returns {Promise<any>}  Status code
 */

export function createOrUpdateRaylifeApplication(form, status) {
	const payload = LiferayAdapt.adaptToFormApplicationRequest(form, status);

	if (form?.basics?.applicationId) {
		return axios.patch(
			`${RaylifeApplicationAPI}/${form.basics.applicationId}`,
			payload
		);
	}

	return axios.post(`${RaylifeApplicationAPI}/`, payload);
}

export function updateRaylifeApplicationStatus(applicationId, status) {
	return axios.patch(`${RaylifeApplicationAPI}/${applicationId}`, {
		applicationStatus: {
			key: status?.key,
			name: status?.name,
		},
	});
}
