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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.persistence.ServiceComponentFinder;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class ServiceComponentFinderImpl
	extends ServiceComponentFinderBaseImpl implements ServiceComponentFinder {

	public static final String FIND_BY_MAX_BUILD_NUMBER =
		ServiceComponentFinder.class.getName() + ".findByMaxBuildNumber";

	@Override
	public List<ServiceComponent> findByMaxBuildNumber() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_MAX_BUILD_NUMBER);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("ServiceComponent", ServiceComponentImpl.class);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}