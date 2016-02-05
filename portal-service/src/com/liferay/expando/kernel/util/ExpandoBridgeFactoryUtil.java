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

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoBridgeFactoryUtil {

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className) {

		PortalRuntimePermission.checkExpandoBridge(className);

		return getExpandoBridgeFactory().getExpandoBridge(companyId, className);
	}

	public static ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		PortalRuntimePermission.checkExpandoBridge(className);

		return getExpandoBridgeFactory().getExpandoBridge(
			companyId, className, classPK);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getExpandoBridge(long,
	 *             String)}
	 */
	@Deprecated
	public static ExpandoBridge getExpandoBridge(String className) {
		long companyId = CompanyThreadLocal.getCompanyId();

		return getExpandoBridge(companyId, className);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getExpandoBridge(long,
	 *             String, long)}
	 */
	@Deprecated
	public static ExpandoBridge getExpandoBridge(
		String className, long classPK) {

		long companyId = CompanyThreadLocal.getCompanyId();

		return getExpandoBridge(companyId, className, classPK);
	}

	public static ExpandoBridgeFactory getExpandoBridgeFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ExpandoBridgeFactoryUtil.class);

		return _expandoBridgeFactory;
	}

	public void setExpandoBridgeFactory(
		ExpandoBridgeFactory expandoBridgeFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_expandoBridgeFactory = expandoBridgeFactory;
	}

	private static ExpandoBridgeFactory _expandoBridgeFactory;

}