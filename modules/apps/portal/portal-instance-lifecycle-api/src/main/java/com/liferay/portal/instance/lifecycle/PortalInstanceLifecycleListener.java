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
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface PortalInstanceLifecycleListener {

	public default long getLastModifiedTime() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		return bundle.getLastModified();
	}

	public default String getName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	public default void portalInstancePreregistered(Company company)
		throws Exception {
	}

	public default void portalInstancePreunregistered(Company company)
		throws Exception {
	}

	public void portalInstanceRegistered(Company company) throws Exception;

	public void portalInstanceUnregistered(Company company) throws Exception;

}