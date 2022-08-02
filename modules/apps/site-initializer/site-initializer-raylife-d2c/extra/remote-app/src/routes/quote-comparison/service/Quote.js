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

import {axios} from '../../../common/services/liferay/api';

const headlessRaylifeQuoteAPI = 'o/c/raylifequotes';

const applicationId = localStorage.getItem('raylife-application-id');

export async function getQuotes() {
	const response = await axios.get(
		`${headlessRaylifeQuoteAPI}/?filter=raylifeApplicationId eq '${applicationId}'`
	);

	return response.data;
}
export async function getQuoteById(quoteId) {
	const response = await axios.get(`${headlessRaylifeQuoteAPI}/${quoteId}`);

	return response.data;
}
