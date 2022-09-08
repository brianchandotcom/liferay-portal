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

package com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.sso.openid.connect.persistence.model.OpenIdConnectSession;
import com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionImpl;
import com.liferay.portal.security.sso.openid.connect.persistence.service.persistence.OpenIdConnectSessionFinder;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(service = OpenIdConnectSessionFinder.class)
public class OpenIdConnectSessionFinderImpl
	extends OpenIdConnectSessionFinderBaseImpl
	implements OpenIdConnectSessionFinder {

	public static final String FIND_ACCESS_TOKEN_EXPIRED_SESSIONS =
		OpenIdConnectSessionFinder.class.getName() +
			".findAccessTokenExpiredSessions";

	@Override
	public List<OpenIdConnectSession> findAccessTokenExpiredSessions(
		Date beforeDate, int start, int end) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(
				getClass(), FIND_ACCESS_TOKEN_EXPIRED_SESSIONS);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				OpenIdConnectSessionImpl.TABLE_NAME,
				OpenIdConnectSessionImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(beforeDate);

			return (List<OpenIdConnectSession>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Reference
	private CustomSQL _customSQL;

}