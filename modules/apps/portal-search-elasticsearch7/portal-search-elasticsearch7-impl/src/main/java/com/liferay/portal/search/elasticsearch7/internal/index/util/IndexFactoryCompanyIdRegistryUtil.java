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

package com.liferay.portal.search.elasticsearch7.internal.index.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Joao Victor Alves
 */
public class IndexFactoryCompanyIdRegistryUtil {

	public static Set<Long> getCompanyIds() {
		return _companyIds;
	}

	public static synchronized void registerCompanyId(long companyId) {
		_companyIds.add(companyId);
	}

	public static synchronized void unregisterCompanyId(long companyId) {
		_companyIds.remove(companyId);
	}

	private static final Set<Long> _companyIds = new HashSet<>();

}