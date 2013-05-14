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

package com.liferay.portal.dao.shard;

import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.spring.hibernate.PortalHibernateConfiguration;
import com.liferay.portal.util.PropsValues;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 * @author Alexander Chow
 */
public class ShardSessionFactoryTargetSource implements TargetSource {

	public Map<String, SessionFactory> getSessionFactories() {
		return _sessionFactories;
	}

	public SessionFactory getSessionFactory() {
		return _sessionFactory.get();
	}

	@Override
	public Object getTarget() throws Exception {
		return getSessionFactory();
	}

	@Override
	public Class<?> getTargetClass() {
		return _sessionFactories.get(PropsValues.SHARD_DEFAULT_NAME).getClass();
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public void releaseTarget(Object target) throws Exception {
	}

	public void setSessionFactory(String shardName) {
		_sessionFactory.set(_sessionFactories.get(shardName));
	}

	public void setShardDataSourceTargetSource(
			ShardDataSourceTargetSource shardDataSourceTargetSource)
		throws Exception {

		Map<String, DataSource> dataSources =
			shardDataSourceTargetSource.getDataSources();

		for (String shardName : dataSources.keySet()) {
			DataSource dataSource = dataSources.get(shardName);

			PortalHibernateConfiguration portalHibernateConfiguration =
				new PortalHibernateConfiguration();

			portalHibernateConfiguration.setDataSource(dataSource);

			SessionFactory sessionFactory =
				portalHibernateConfiguration.buildSessionFactory();

			_sessionFactories.put(shardName, sessionFactory);
		}
	}

	private static Map<String, SessionFactory> _sessionFactories =
		new HashMap<String, SessionFactory>();

	private static ThreadLocal<SessionFactory> _sessionFactory =
		new CentralizedThreadLocal<SessionFactory>(false) {

		@Override
		protected SessionFactory initialValue() {
			return _sessionFactories.get(PropsValues.SHARD_DEFAULT_NAME);
		}

	};

}