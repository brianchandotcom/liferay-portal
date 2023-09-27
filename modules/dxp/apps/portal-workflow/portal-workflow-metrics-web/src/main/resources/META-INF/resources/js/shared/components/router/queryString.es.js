/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import qs from 'qs';

const options = {allowDots: true, arrayFormat: 'bracket'};

const parse = (query = '') => {
	return query.length ? qs.parse(query.substr(1), options) : {};
};

const stringify = (query) => {
	return query ? `?${qs.stringify(query, options)}` : '';
};

export {parse, stringify};
