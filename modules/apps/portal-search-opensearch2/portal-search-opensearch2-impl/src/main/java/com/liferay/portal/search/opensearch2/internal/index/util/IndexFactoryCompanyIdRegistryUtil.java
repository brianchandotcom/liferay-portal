/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index.util;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joao Victor Alves
 */
public class IndexFactoryCompanyIdRegistryUtil {

	public static long[] getCompanyIds() {
		return ArrayUtil.toLongArray(_companyIds);
	}

	public static synchronized void registerCompanyId(long companyId) {
		_companyIds.add(companyId);
	}

	public static synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	private static final Set<Long> _companyIds = new HashSet<>();

}