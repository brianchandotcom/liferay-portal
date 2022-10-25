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

import {Parameters, parametersFormater} from '.';
import {axios} from './liferay/api';

const DeliveryAPI = 'o/c/raylifeapplications';

export function getApplications(parameters: Parameters = {}) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${DeliveryAPI}/${parametersFormater(parametersList, parameters)}`
		);
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
const products: any = localStorage.getItem('raylife-ap-storage');

const adaptToFormApplicationRequest = (state: any, status: any) => ({
	address: state?.contactInfo?.form?.streetAddress,
	addressApt: state?.contactInfo?.form?.apt,
	applicationCreateDate: new Date(),
	applicationStatus: {
		key: status,
	},
	city: state?.contactInfo?.form?.city,
	dataJSON: JSON.stringify({
		contactInfo: {
			dateOfBirth: state?.contactInfo?.form?.dateOfBirth,
			ownership: state?.contactInfo?.form?.ownership,
		},
		coverage: {
			form: state?.coverage?.form,
		},
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
	productName: JSON.parse(products)?.productName,
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

export function getApplicationByExternalReferenceCode(
	externalReferenceCode: string
) {
	return axios.get(
		`${DeliveryAPI}/by-external-reference-code/${externalReferenceCode}`
	);
}

export function getApplicationsById(id: number) {
	return axios.get(`${DeliveryAPI}/?filter=id eq '${id}'`);
}
