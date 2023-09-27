/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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