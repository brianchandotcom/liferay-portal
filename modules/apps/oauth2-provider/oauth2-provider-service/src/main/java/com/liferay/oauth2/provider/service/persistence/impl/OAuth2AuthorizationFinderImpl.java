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

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2AuthorizationFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;

import java.math.BigInteger;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizationFinderImpl
	extends OAuth2AuthorizationFinderBaseImpl
	implements OAuth2AuthorizationFinder {

	public static final String FIND_ALL_AUTHORIZATIONS =
		OAuth2AuthorizationFinder.class.getName() + ".findAuthorizations";

	@Override
	public int countByApplicationId(long companyId, long applicationId) {
		return countByFilter(
			_FIND_BY_APPLICATION_ID_FILTER, companyId, applicationId);
	}

	@Override
	public int countByUserId(long companyId, long userId) {
		return countByFilter(_FIND_BY_USER_ID_FILTER, companyId, userId);
	}

	@Override
	public List<OAuth2Authorization> findByApplicationId(
		long companyId, long applicationId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByFilter(
			_FIND_BY_APPLICATION_ID_FILTER, start, end, orderByComparator,
			companyId, applicationId);
	}

	@Override
	public List<OAuth2Authorization> findByUserId(
		long companyId, long userId, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator) {

		return findByFilter(
			_FIND_BY_USER_ID_FILTER, start, end, orderByComparator, companyId,
			userId);
	}

	protected int countByFilter(String filter, Object... values) {
		Session session = null;

		try {
			session = openSession();

			StringBundler querySB = new StringBundler(6);

			querySB.append("SELECT count(*) AS ");
			querySB.append(COUNT_COLUMN_NAME);
			querySB.append(" FROM (");
			querySB.append(
				CustomSQLUtil.get(getClass(), FIND_ALL_AUTHORIZATIONS));

			querySB.append(filter);

			querySB.append(") TEMP_TABLE");

			SQLQuery q = session.createSynchronizedSQLQuery(querySB.toString());

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Object value : values) {
				qPos.add(value);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<OAuth2Authorization> findByFilter(
		String filter, int start, int end,
		OrderByComparator<OAuth2Authorization> orderByComparator,
		Object... values) {

		Session session = null;

		try {
			session = openSession();

			int length = 2;

			if (orderByComparator != null) {
				length += orderByComparator.getOrderByFields().length * 2;
			}
			else {
				length += 1;
			}

			StringBundler querySB = new StringBundler(length);

			querySB.append(
				CustomSQLUtil.get(getClass(), FIND_ALL_AUTHORIZATIONS));

			querySB.append(filter);

			if (orderByComparator != null) {
				appendOrderByComparator(
					querySB, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				querySB.append(_ORDER_BY_CREATE_DATE_DESC);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(querySB.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			for (Object value : values) {
				qPos.add(value);
			}

			List<OAuth2Authorization> result = new ArrayList<>();

			Iterator<Object[]> iterator = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (iterator.hasNext()) {
				Object[] row = iterator.next();

				OAuth2Authorization oAuth2Authorization =
					new OAuth2AuthorizationImpl();

				int i = 0;

				oAuth2Authorization.setAccessTokenExpirationDate(
					_getDate(row[i++]));

				oAuth2Authorization.setCompanyId(_getLong(row[i++]));
				oAuth2Authorization.setCreateDate(_getDate(row[i++]));
				oAuth2Authorization.setOAuth2ApplicationId(_getLong(row[i++]));
				oAuth2Authorization.setOAuth2RefreshTokenId(_getLong(row[i++]));
				oAuth2Authorization.setOAuth2AccessTokenId(_getLong(row[i++]));
				oAuth2Authorization.setRefreshTokenExpirationDate(
					_getDate(row[i++]));

				oAuth2Authorization.setRemoteIPInfo(
					GetterUtil.getString(row[i++]));

				oAuth2Authorization.setScopes(GetterUtil.getString(row[i++]));
				oAuth2Authorization.setUserId(_getLong(row[i++]));
				oAuth2Authorization.setUserName(GetterUtil.getString(row[i++]));

				result.add(oAuth2Authorization);
			}

			return result;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Map<String, Integer> getTableColumnsMap() {
		return _tableColumnsMap;
	}

	private static final Date _getDate(Object o) {
		return (Date)o;
	}

	private static final long _getLong(Object o) {
		if (o == null) {
			return 0;
		}

		if (o instanceof BigInteger) {
			return ((BigInteger)o).longValue();
		}

		return (Long)o;
	}

	private static final String _FIND_BY_APPLICATION_ID_FILTER =
		" WHERE OAuth2Authorization.companyId = ? AND " +
			"OAuth2Authorization.oAuth2ApplicationId = ?";

	private static final String _FIND_BY_USER_ID_FILTER =
		" WHERE OAuth2Authorization.companyId = ? AND " +
			"OAuth2Authorization.userId = ?";

	private static final String _ORDER_BY_CREATE_DATE_DESC =
		" ORDER BY createDate DESC";

	private static final String _ORDER_BY_ENTITY_ALIAS = "OAuth2Authorization.";

	private static final Map<String, Integer> _tableColumnsMap =
		new HashMap<>();

	static {
		_tableColumnsMap.put("accessTokenExpirationDate", Types.DATE);
		_tableColumnsMap.put("companyId", Types.BIGINT);
		_tableColumnsMap.put("createDate", Types.DATE);
		_tableColumnsMap.put("oAuth2AccessTokenId", Types.BIGINT);
		_tableColumnsMap.put("oAuth2ApplicationId", Types.BIGINT);
		_tableColumnsMap.put("oAuth2RefreshTokenId", Types.BIGINT);
		_tableColumnsMap.put("refreshTokenExpirationDate", Types.DATE);
		_tableColumnsMap.put("remoteIPInfo", Types.VARCHAR);
		_tableColumnsMap.put("scopes", Types.CLOB);
		_tableColumnsMap.put("userId", Types.BIGINT);
		_tableColumnsMap.put("userName", Types.VARCHAR);
	}

}