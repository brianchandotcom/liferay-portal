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
import com.liferay.portal.kernel.util.PrototypeBean;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 * @author Ganesh Ram
 * @author Shuyang Zhou
 */
public class PortletHibernateConfiguration
	extends PortalHibernateConfiguration implements PrototypeBean {

	public PrototypeBean create(Object... args) {
		if ((args.length != 1) || !(args[0] instanceof DataSource)) {
			throw new IllegalArgumentException();
		}

		DataSource dataSource = (DataSource)args[0];

		PortletHibernateConfiguration portletHibernateConfiguration =
			new PortletHibernateConfiguration();

		portletHibernateConfiguration.setDataSource(dataSource);

		return portletHibernateConfiguration;
	}

	@Override
	protected ClassLoader getConfigurationClassLoader() {
		return PortletClassLoaderUtil.getClassLoader();
	}

	@Override
	protected String[] getConfigurationResources() {
		return new String[] {"META-INF/portlet-hbm.xml"};
	}

}