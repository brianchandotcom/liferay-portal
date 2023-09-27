/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity("ServiceComponent", ServiceComponentImpl.class);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}