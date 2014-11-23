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

package com.liferay.portal.dao.shard;

import com.liferay.portal.spring.hibernate.LastSessionRecorderHibernateTransactionManager;

import org.hibernate.SessionFactory;

/**
 * @author Shuyang Zhou
 */
public class ShardLastSessionRecorderHibernateTransactionManager
	extends LastSessionRecorderHibernateTransactionManager {

	public ShardLastSessionRecorderHibernateTransactionManager(
		ShardSessionFactoryTargetSource shardSessionFactoryTargetSource) {

		_shardSessionFactoryTargetSource = shardSessionFactoryTargetSource;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return _shardSessionFactoryTargetSource.getSessionFactory();
	}

	private final ShardSessionFactoryTargetSource
		_shardSessionFactoryTargetSource;

}