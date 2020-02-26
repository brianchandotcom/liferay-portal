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

import axios from 'axios';
import qs from 'qs';

/**
 * @module fetch
 * @description Basic alias of fetch client to perform consults in portal rest API.
 * @example
 * import fetch from '@/shared/rest/fetch';
 * fetch.get('/process').then(res => console.log(res));
 */
axios.defaults.headers.common[
	'Accept-Language'
] = Liferay.ThemeDisplay.getBCP47LanguageId();

axios.defaults.params = {
	['p_auth']: Liferay.authToken,
};

axios.defaults.paramsSerializer = params =>
	qs.stringify(params, {arrayFormat: 'repeat'});

const adminClient = axios.create({
	baseURL: '/o/headless-admin-workflow/v1.0',
});

const metricsClient = axios.create({
	baseURL: '/o/portal-workflow-metrics/v1.0',
});

const getClient = admin => {
	return admin ? adminClient : metricsClient;
};

export {getClient};
export default metricsClient;
