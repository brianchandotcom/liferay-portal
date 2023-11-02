/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.constants;

/**
 * @author Alvaro Saugar
 */
public class ScimConstants {

	public static final String CONFIGURATION_PID =
		"com.liferay.scim.rest.internal.configuration." +
			"ScimClientOAuth2ApplicationConfiguration";

	public static final String[] MATCHER_FIELD = {"userName", "email"};

	public static final String PARAM_APPLICATION_NAME = "applicationName";

	public static final String PARAM_COMPANY_ID = "companyId";

	public static final String PARAM_MATCHER_FIELD = "matcherField";

	public static final String PARAM_TOKEN = "token";

}