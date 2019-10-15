/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import qs from 'qs';

const options = {allowDots: true, arrayFormat: 'bracket'};

export function parse(queryString) {
	if (queryString && queryString.length) {
		return qs.parse(queryString.substr(1), options);
	}

	return {};
}

export function stringify(query) {
	if (query) {
		return `?${qs.stringify(query, options)}`;
	}
}
