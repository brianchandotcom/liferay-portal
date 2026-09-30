/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.catapult;

import java.util.List;
import java.util.Map;

/**
 * @author Jorge García Jiménez
 */
public interface PortalCatapultHeaderContributor {

	public Map<String, String> getHeaders(
		long companyId, String homePageURL, String location,
		List<String> oAuth2ApplicationFeatures, long userId);

}