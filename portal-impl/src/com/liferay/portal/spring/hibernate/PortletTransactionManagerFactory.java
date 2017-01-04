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

import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.spring.transaction.TransactionManagerFactory;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Shuyang Zhou
 */
public class PortletTransactionManagerFactory {

	public static PlatformTransactionManager create(
			DataSource dataSource,
			HibernateTransactionManager portalHibernateTransactionManager,
			SessionFactory portletSessionFactory)
		throws Exception {

		if (InfrastructureUtil.getDataSource() == dataSource) {
			return new PortletTransactionManager(
				portalHibernateTransactionManager, portletSessionFactory);
		}

		return TransactionManagerFactory.createTransactionManager(
			dataSource, portletSessionFactory);
	}

}