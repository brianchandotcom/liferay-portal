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

package com.liferay.portal.instance.lifecycle;

import com.liferay.portal.kernel.model.Company;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BasePortalInstanceLifecycleListener
	implements PortalInstanceLifecycleListener {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void portalInstancePreregistered(long companyId) {
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

}