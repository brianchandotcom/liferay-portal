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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SpringSessionContext;

/**
 * @author Shuyang Zhou
 */
public class ShardSpringSessionContext extends SpringSessionContext {

	public ShardSpringSessionContext(SessionFactoryImplementor sessionFactory) {
		super(null);
	}

	@Override
	public Session currentSession() throws HibernateException {
		try {
			if (_shardSessionFactoryTargetSource == null) {
				_shardSessionFactoryTargetSource =
					(ShardSessionFactoryTargetSource)
						PortalBeanLocatorUtil.locate(
							"shardSessionFactoryTargetSource");
			}

			return (Session)SessionFactoryUtils.doGetSession(
				_shardSessionFactoryTargetSource.getSessionFactory(), false);
		}
		catch (IllegalStateException ise) {
			throw new HibernateException(ise);
		}
	}

	private ShardSessionFactoryTargetSource _shardSessionFactoryTargetSource;

}