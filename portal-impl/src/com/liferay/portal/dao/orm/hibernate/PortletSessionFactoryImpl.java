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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.hibernate.PortletHibernateConfiguration;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

/**
 * @author     Shuyang Zhou
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PortletSessionFactoryImpl extends SessionFactoryImpl {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setDataSource(DataSource dataSource) {
		_dataSource = dataSource;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected SessionFactory createSessionFactory(DataSource dataSource) {
		PortletHibernateConfiguration portletHibernateConfiguration =
			new PortletHibernateConfiguration(
				getSessionFactoryClassLoader(), dataSource);

		portletHibernateConfiguration.setDataSource(dataSource);

		SessionFactory sessionFactory = null;

		try {
			sessionFactory =
				portletHibernateConfiguration.buildSessionFactory();
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}

		return sessionFactory;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected DataSource getDataSource() {
		return _dataSource;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected SessionFactory getSessionFactory() {
		return getSessionFactoryImplementor();
	}

	@Override
	protected Session wrapSession(org.hibernate.Session session) {
		return super.wrapSession(session);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSessionFactoryImpl.class);

	private DataSource _dataSource;

}