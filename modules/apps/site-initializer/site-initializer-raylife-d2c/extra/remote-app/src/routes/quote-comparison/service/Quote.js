/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';

const headlessRaylifeQuoteAPI = 'o/c/raylifequotes';

const applicationId = Liferay.Util.LocalStorage.getItem(
	'raylife-application-id',
	Liferay.Util.LocalStorage.TYPES.NECESSARY
);

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
