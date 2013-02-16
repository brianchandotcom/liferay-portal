/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.util.ClassLoaderUtil;

import org.hibernate.dialect.Dialect;

/**
 * @author Brian Wing Shun Chan
 * @author Ganesh Ram
 */
public class PortletHibernateConfiguration
	extends PortalHibernateConfiguration {

	@Override
	protected ClassLoader getConfigurationClassLoader() {
		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		if (classLoader == null) {

			// This should not be null except in cases where sharding is enabled

			classLoader = ClassLoaderUtil.getContextClassLoader();
		}

		return classLoader;
	}

	@Override
	protected String[] getConfigurationResources() {
		return new String[] {"META-INF/portlet-hbm.xml"};
	}

	@Override
	protected void setDB(Dialect dialect) {

		// Plugins should not update the default DB reference

	}

}