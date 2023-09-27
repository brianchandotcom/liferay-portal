/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.service.access.policy;

import java.util.List;
import java.util.Locale;

/**
 * @author Mika Koivisto
 */
public interface ServiceAccessPolicy {

	public static final String SERVICE_ACCESS_POLICY_NAMES =
		ServiceAccessPolicy.class.getName() + "#NAMES";

	public List<String> getAllowedServiceSignaturesList();

	public String getName();

	public String getTitle(Locale locale);

}