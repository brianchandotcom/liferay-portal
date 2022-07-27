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

import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeapplications';

type Parameters = {
	[key: string]: string;
};

export function getApplications(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		const parametersContainer: String[] = [];

		parametersList.forEach((item) => {
			parametersContainer.push(`${item}=${parameters[item]}`);
		});

		const parametersString = '?' + parametersContainer.join('&');

		return axios.get(`${DeliveryAPI}/${parametersString}`);
	}

	return axios.get(`${DeliveryAPI}/`);
}

export function deleteApplicationByExternalReferenceCode(
	externalReferenceCode: string
) {
	return axios.delete(
		`${DeliveryAPI}/by-external-reference-code/${externalReferenceCode}`
	);
}

const adaptToFormApplicationRequest = (state: any, status: any) => ({
	address: state?.contactInfo?.form?.streetAddress,
	addressApt: state?.contactInfo?.form?.apt,
	applicationCreateDate: new Date(),
	applicationStatus: {
		key: status,
	},
	city: state?.contactInfo?.form?.city,
	dataJSON: JSON.stringify({
		driverInfo: {
			form: state?.driverInfo?.form,
		},
		ownership: state?.contactInfo?.ownership,
		vehicleInfo: {
			form: state?.vehicleInfo?.form,
		},
	}),
	email: state?.contactInfo?.form?.email,
	firstName: state?.contactInfo?.form?.firstName,
	lastName: state?.contactInfo?.form?.lastName,
	phone: state?.contactInfo?.form?.phone,
	productName: 'Auto',
	state: state?.contactInfo?.form?.state,
	zip: state?.contactInfo?.form?.zipCode,
});

export function createOrUpdateRaylifeApplication(state: any, status: any) {
	const payload = adaptToFormApplicationRequest(state?.steps, status);

	if (state.applicationId) {
		return axios.patch(`${DeliveryAPI}/${state.applicationId}`, payload);
	}

	return axios.post(`${DeliveryAPI}/`, payload);
}

export function exitRaylifeApplication(state: any, status: any) {
	const payload = adaptToFormApplicationRequest(state?.steps, status);

	return axios.patch(`${DeliveryAPI}/${state.applicationId}`, payload);
}
