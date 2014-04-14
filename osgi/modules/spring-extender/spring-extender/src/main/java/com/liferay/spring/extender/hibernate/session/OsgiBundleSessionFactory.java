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

package com.liferay.spring.extender.hibernate.session;

import com.liferay.portal.dao.orm.hibernate.PortletSessionFactoryImpl;
import com.liferay.spring.extender.classloader.BundleResolverClassLoader;
import com.liferay.spring.extender.hibernate.configuration.OsgiBundleHibernateConfiguration;

import javax.sql.DataSource;

import org.eclipse.gemini.blueprint.context.BundleContextAware;

import org.hibernate.SessionFactory;

import org.osgi.framework.BundleContext;

/**
 * @author Miguel Pastor
 */
public class OsgiBundleSessionFactory extends PortletSessionFactoryImpl
	implements BundleContextAware {

	@Override
	public ClassLoader getSessionFactoryClassLoader() {
		return _classLoader;
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		_classLoader = new BundleResolverClassLoader(
			bundleContext.getBundle(), null);
	}

	@Override
	protected SessionFactory createSessionFactory(DataSource dataSource) {
		OsgiBundleHibernateConfiguration osgiBundleHibernateConfiguration =
			new OsgiBundleHibernateConfiguration();

		osgiBundleHibernateConfiguration.setDataSource(dataSource);

		SessionFactory sessionFactory;

		try {
			sessionFactory =
				osgiBundleHibernateConfiguration.buildSessionFactory();
		}
		catch (Exception e) {
			return null;
		}

		return sessionFactory;
	}

	private ClassLoader _classLoader;

}