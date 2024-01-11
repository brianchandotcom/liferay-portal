/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.constants;

import com.liferay.osb.faro.web.internal.application.ApiApplication;
import com.liferay.osb.faro.web.internal.controller.api.RecommendationController;
import com.liferay.osb.faro.web.internal.controller.api.ReportController;

/**
 * @author Joao Victor Alves
 */
public class FaroSAPConstants {

	public static final String[][] SAP_ENTRY_OBJECT_ARRAYS = {
		{
			"OAUTH2_" +
				ApiApplication.OAuth2ScopeAliases.RECOMMENDATIONS_EVERYTHING,
			RecommendationController.class.getName() + "*"
		},
		{
			"OAUTH2_" + ApiApplication.OAuth2ScopeAliases.REPORTS_EVERYTHING,
			ReportController.class.getName() + "*"
		}
	};

}