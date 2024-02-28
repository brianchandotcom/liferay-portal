/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const faroConfig = {
	environment: {
		baseUrl: process.env.FARO_URL
			? process.env.FARO_URL
			: 'http://osbfarofrontend:8080',
	},
	user: {
		login: process.env.FARO_USER_LOGIN || 'test@liferay.com',
		password: process.env.FARO_PASSWORD || 'test',
	},
};

export {faroConfig};
