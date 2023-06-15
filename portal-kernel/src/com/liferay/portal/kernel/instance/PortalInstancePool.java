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

package com.liferay.portal.kernel.instance;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class PortalInstancePool {

	public static void add(Company company) {
		_portalInstances.put(company.getCompanyId(), company.getWebId());
	}

	public static long[] getCompanyIds() {
		return ArrayUtil.toLongArray(_portalInstances.keySet());
	}

	public static long getDefaultCompanyId() {
		for (Map.Entry<Long, String> entry : _portalInstances.entrySet()) {
			if (Objects.equals(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID),
					entry.getValue())) {

				return entry.getKey();
			}
		}

		throw new IllegalStateException("Unable to get default company ID");
	}

	public static String getWebId(long companyId) {
		return _portalInstances.get(companyId);
	}

	public static String[] getWebIds() {
		return ArrayUtil.toStringArray(_portalInstances.values());
	}

	public static void remove(long companyId) {
		_portalInstances.remove(companyId);
	}

	private static final Map<Long, String> _portalInstances =
		new ConcurrentHashMap<>();

}