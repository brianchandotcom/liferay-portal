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

package com.liferay.portal.spring.extender.internal.hibernate.session;

import com.liferay.portal.dao.orm.hibernate.PortletSessionFactoryImpl;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;
import com.liferay.portal.spring.extender.internal.context.ModuleApplicationContext;
import com.liferay.portal.spring.extender.internal.hibernate.configuration.ModuleHibernateConfiguration;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.osgi.framework.BundleContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Miguel Pastor
 */
public class ModuleSessionFactory
	extends PortletSessionFactoryImpl implements ApplicationContextAware {

	@Override
	public ClassLoader getSessionFactoryClassLoader() {
		return _classLoader;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {

		ModuleApplicationContext moduleApplicationContext =
			(ModuleApplicationContext)applicationContext;

		BundleContext bundleContext =
			moduleApplicationContext.getBundleContext();

		_classLoader = new BundleResolverClassLoader(
			bundleContext.getBundle(), null);

		setSessionFactoryClassLoader(_classLoader);
	}

	@Override
	protected SessionFactory createSessionFactory(DataSource dataSource) {
		ModuleHibernateConfiguration moduleHibernateConfiguration =
			new ModuleHibernateConfiguration(_classLoader);

		moduleHibernateConfiguration.setDataSource(dataSource);

		try {
			return moduleHibernateConfiguration.buildSessionFactory();
		}
		catch (Exception e) {
			return null;
		}
	}

	private ClassLoader _classLoader;

}