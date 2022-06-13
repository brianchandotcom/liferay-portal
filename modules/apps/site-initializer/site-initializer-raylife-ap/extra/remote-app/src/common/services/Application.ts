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
