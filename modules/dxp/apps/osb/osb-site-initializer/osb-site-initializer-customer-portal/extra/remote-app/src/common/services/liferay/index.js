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

export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	ThemeDisplay: {
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getPathThemeImages: () => null,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => 0,
	},
	authToken: '',
};
