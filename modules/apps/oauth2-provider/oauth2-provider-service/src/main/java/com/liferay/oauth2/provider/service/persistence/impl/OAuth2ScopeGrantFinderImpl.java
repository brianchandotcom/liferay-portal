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

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collection;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class OAuth2ScopeGrantFinderImpl
	extends OAuth2ScopeGrantFinderBaseImpl implements OAuth2ScopeGrantFinder {

	public static final String FIND_BY_C_A_B_A =
		OAuth2ScopeGrantFinder.class.getName() + ".findByC_A_B_A";

	@Override
	public Collection<OAuth2ScopeGrant> findByC_A_B_A(
		long companyId, String applicationName, String bundleSymbolicName,
		String accessTokenContent) {

		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_C_A_B_A);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			q.addEntity("OAuth2ScopeGrant", OAuth2ScopeGrantImpl.class);

			qPos.add(companyId);
			qPos.add(applicationName);
			qPos.add(bundleSymbolicName);
			qPos.add(accessTokenContent);

			return (List<OAuth2ScopeGrant>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}