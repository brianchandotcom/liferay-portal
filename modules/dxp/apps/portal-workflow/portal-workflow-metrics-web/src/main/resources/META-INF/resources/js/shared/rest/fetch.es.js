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

const adminBaseURL = '/o/headless-admin-workflow/v1.0';

const metricsBaseURL = '/o/portal-workflow-metrics/v1.0';

const headers = {
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
};

export {adminBaseURL, headers, metricsBaseURL};
