/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.spring.hibernate.SpringHibernateThreadLocalUtil;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.ConnectionHolder;

/**
 * @author Shuyang Zhou
 */
public class CurrentConnectionImpl implements CurrentConnection {

	@Override
	public Connection getConnection(DataSource dataSource) {
		ConnectionHolder connectionHolder =
			(ConnectionHolder)SpringHibernateThreadLocalUtil.getResource(
				dataSource);

		if ((connectionHolder == null) ||
			(connectionHolder.getConnectionHandle() == null)) {

			return null;
		}

		return connectionHolder.getConnection();
	}

}