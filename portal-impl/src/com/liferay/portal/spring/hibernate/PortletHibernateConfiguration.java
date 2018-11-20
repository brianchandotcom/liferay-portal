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

package com.liferay.portal.spring.hibernate;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Brian Wing Shun Chan
 * @author Ganesh Ram
 */
public class PortletHibernateConfiguration
	extends PortalHibernateConfiguration implements ApplicationContextAware {

	public PortletHibernateConfiguration() {
		this(null, null);
	}

	public PortletHibernateConfiguration(
		ClassLoader classLoader, DataSource dataSource) {

		_classLoader = classLoader;

		setDataSource(dataSource);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {

		_classLoader = applicationContext.getClassLoader();
	}

	@Override
	protected ClassLoader getConfigurationClassLoader() {
		return _classLoader;
	}

	@Override
	protected String[] getConfigurationResources() {
		return new String[] {
			"META-INF/module-hbm.xml", "META-INF/portlet-hbm.xml"
		};
	}

	private ClassLoader _classLoader;

}