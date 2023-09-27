/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.log4j.internal;

import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;

/**
 * @author Hai Yu
 */
public class CompanyWebIdLogContext implements LogContext {

	@Override
	public Map<String, String> getContext(String logName) {
		String webId = PortalInstancePool.getWebId(
			CompanyThreadLocal.getCompanyId());

		if (Validator.isNull(webId)) {
			return Collections.emptyMap();
		}

		return Collections.singletonMap("webId", webId);
	}

	@Override
	public String getName() {
		return "company";
	}

}