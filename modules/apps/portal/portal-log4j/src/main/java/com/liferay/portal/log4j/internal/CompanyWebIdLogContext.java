/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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