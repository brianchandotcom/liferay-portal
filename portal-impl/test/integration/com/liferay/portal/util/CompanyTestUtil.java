/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Manuel de la Peña
 */
public class CompanyTestUtil {

	public static Company addCompany() throws Exception {
		return addCompany(ServiceTestUtil.randomString());
	}

	public static Company addCompany(String companyName) throws Exception {
		String webId = companyName;

		StringBundler sb = new StringBundler(3);

		sb.append(companyName);
		sb.append(StringPool.PERIOD);
		sb.append(ServiceTestUtil.randomString(3));

		String virtualHostname = sb.toString();
		String mx = virtualHostname;
		String shardName = ServiceTestUtil.randomString();
		boolean system = false;
		int maxUsers = 0;
		boolean active = true;

		return CompanyLocalServiceUtil.addCompany(
			webId, virtualHostname, mx, shardName, system, maxUsers, active);
	}

}