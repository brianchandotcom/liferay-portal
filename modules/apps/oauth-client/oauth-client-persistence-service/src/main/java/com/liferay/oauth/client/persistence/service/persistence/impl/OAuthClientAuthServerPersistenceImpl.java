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

package com.liferay.oauth.client.persistence.service.persistence.impl;

import com.liferay.oauth.client.persistence.exception.NoSuchAuthServerException;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServerTable;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientAuthServerImpl;
import com.liferay.oauth.client.persistence.model.impl.OAuthClientAuthServerModelImpl;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientAuthServerPersistence;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientAuthServerUtil;
import com.liferay.oauth.client.persistence.service.persistence.impl.constants.OAuthClientPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the o auth client auth server service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {OAuthClientAuthServerPersistence.class, BasePersistence.class}
)
public class OAuthClientAuthServerPersistenceImpl
	extends BasePersistenceImpl<OAuthClientAuthServer>
	implements OAuthClientAuthServerPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthClientAuthServerUtil</code> to access the o auth client auth server persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthClientAuthServerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<OAuthClientAuthServer> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientAuthServer>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientAuthServer oAuthClientAuthServer : list) {
					if (companyId != oAuthClientAuthServer.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<OAuthClientAuthServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByCompanyId_First(
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		List<OAuthClientAuthServer> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuthClientAuthServer> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] findByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, oAuthClientAuthServer, companyId, orderByComparator,
				true);

			array[1] = oAuthClientAuthServer;

			array[2] = getByCompanyId_PrevAndNext(
				session, oAuthClientAuthServer, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer getByCompanyId_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<OAuthClientAuthServer>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] filterFindByCompanyId_PrevAndNext(
			long oAuthClientAuthServerId, long companyId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				oAuthClientAuthServerId, companyId, orderByComparator);
		}

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, oAuthClientAuthServer, companyId, orderByComparator,
				true);

			array[1] = oAuthClientAuthServer;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, oAuthClientAuthServer, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer filterGetByCompanyId_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long companyId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (OAuthClientAuthServer oAuthClientAuthServer :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthClientAuthServer);
		}
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"oAuthClientAuthServer.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<OAuthClientAuthServer> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientAuthServer>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientAuthServer oAuthClientAuthServer : list) {
					if (userId != oAuthClientAuthServer.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<OAuthClientAuthServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByUserId_First(
			long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByUserId_First(
			userId, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByUserId_First(
		long userId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		List<OAuthClientAuthServer> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByUserId_Last(
			long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByUserId_Last(
			userId, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByUserId_Last(
		long userId,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OAuthClientAuthServer> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] findByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, oAuthClientAuthServer, userId, orderByComparator,
				true);

			array[1] = oAuthClientAuthServer;

			array[2] = getByUserId_PrevAndNext(
				session, oAuthClientAuthServer, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer getByUserId_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long userId, OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByUserId(long userId) {
		return filterFindByUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end) {

		return filterFindByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId(userId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			return (List<OAuthClientAuthServer>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] filterFindByUserId_PrevAndNext(
			long oAuthClientAuthServerId, long userId,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId_PrevAndNext(
				oAuthClientAuthServerId, userId, orderByComparator);
		}

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = filterGetByUserId_PrevAndNext(
				session, oAuthClientAuthServer, userId, orderByComparator,
				true);

			array[1] = oAuthClientAuthServer;

			array[2] = filterGetByUserId_PrevAndNext(
				session, oAuthClientAuthServer, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer filterGetByUserId_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long userId, OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth client auth servers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OAuthClientAuthServer oAuthClientAuthServer :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthClientAuthServer);
		}
	}

	/**
	 * Returns the number of o auth client auth servers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public int filterCountByUserId(long userId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUserId(userId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"oAuthClientAuthServer.userId = ?";

	private FinderPath _finderPathFetchByC_I;
	private FinderPath _finderPathCountByC_I;

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByC_I(long companyId, String issuer)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByC_I(
			companyId, issuer);

		if (oAuthClientAuthServer == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", issuer=");
			sb.append(issuer);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAuthServerException(sb.toString());
		}

		return oAuthClientAuthServer;
	}

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByC_I(long companyId, String issuer) {
		return fetchByC_I(companyId, issuer, true);
	}

	/**
	 * Returns the o auth client auth server where companyId = &#63; and issuer = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByC_I(
		long companyId, String issuer, boolean useFinderCache) {

		issuer = Objects.toString(issuer, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, issuer};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_I, finderArgs);
		}

		if (result instanceof OAuthClientAuthServer) {
			OAuthClientAuthServer oAuthClientAuthServer =
				(OAuthClientAuthServer)result;

			if ((companyId != oAuthClientAuthServer.getCompanyId()) ||
				!Objects.equals(issuer, oAuthClientAuthServer.getIssuer())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_C_I_COMPANYID_2);

			boolean bindIssuer = false;

			if (issuer.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_I_ISSUER_3);
			}
			else {
				bindIssuer = true;

				sb.append(_FINDER_COLUMN_C_I_ISSUER_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindIssuer) {
					queryPos.add(issuer);
				}

				List<OAuthClientAuthServer> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_I, finderArgs, list);
					}
				}
				else {
					OAuthClientAuthServer oAuthClientAuthServer = list.get(0);

					result = oAuthClientAuthServer;

					cacheResult(oAuthClientAuthServer);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (OAuthClientAuthServer)result;
		}
	}

	/**
	 * Removes the o auth client auth server where companyId = &#63; and issuer = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the o auth client auth server that was removed
	 */
	@Override
	public OAuthClientAuthServer removeByC_I(long companyId, String issuer)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = findByC_I(
			companyId, issuer);

		return remove(oAuthClientAuthServer);
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and issuer = &#63;.
	 *
	 * @param companyId the company ID
	 * @param issuer the issuer
	 * @return the number of matching o auth client auth servers
	 */
	@Override
	public int countByC_I(long companyId, String issuer) {
		issuer = Objects.toString(issuer, "");

		FinderPath finderPath = _finderPathCountByC_I;

		Object[] finderArgs = new Object[] {companyId, issuer};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_C_I_COMPANYID_2);

			boolean bindIssuer = false;

			if (issuer.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_I_ISSUER_3);
			}
			else {
				bindIssuer = true;

				sb.append(_FINDER_COLUMN_C_I_ISSUER_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindIssuer) {
					queryPos.add(issuer);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_I_COMPANYID_2 =
		"oAuthClientAuthServer.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_I_ISSUER_2 =
		"oAuthClientAuthServer.issuer = ?";

	private static final String _FINDER_COLUMN_C_I_ISSUER_3 =
		"(oAuthClientAuthServer.issuer IS NULL OR oAuthClientAuthServer.issuer = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByC_T(long companyId, String type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<OAuthClientAuthServer> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientAuthServer>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthClientAuthServer oAuthClientAuthServer : list) {
					if ((companyId != oAuthClientAuthServer.getCompanyId()) ||
						!type.equals(oAuthClientAuthServer.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<OAuthClientAuthServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByC_T_First(
			long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the first o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		List<OAuthClientAuthServer> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server
	 * @throws NoSuchAuthServerException if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer findByC_T_Last(
			long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByC_T_Last(
			companyId, type, orderByComparator);

		if (oAuthClientAuthServer != null) {
			return oAuthClientAuthServer;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchAuthServerException(sb.toString());
	}

	/**
	 * Returns the last o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth client auth server, or <code>null</code> if a matching o auth client auth server could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<OAuthClientAuthServer> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] findByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		type = Objects.toString(type, "");

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, oAuthClientAuthServer, companyId, type,
				orderByComparator, true);

			array[1] = oAuthClientAuthServer;

			array[2] = getByC_T_PrevAndNext(
				session, oAuthClientAuthServer, companyId, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer getByC_T_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type) {

		return filterFindByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return filterFindByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public List<OAuthClientAuthServer> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T(companyId, type, start, end, orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<OAuthClientAuthServer>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the o auth client auth servers before and after the current o auth client auth server in the ordered set of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param oAuthClientAuthServerId the primary key of the current o auth client auth server
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer[] filterFindByC_T_PrevAndNext(
			long oAuthClientAuthServerId, long companyId, String type,
			OrderByComparator<OAuthClientAuthServer> orderByComparator)
		throws NoSuchAuthServerException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T_PrevAndNext(
				oAuthClientAuthServerId, companyId, type, orderByComparator);
		}

		type = Objects.toString(type, "");

		OAuthClientAuthServer oAuthClientAuthServer = findByPrimaryKey(
			oAuthClientAuthServerId);

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer[] array = new OAuthClientAuthServerImpl[3];

			array[0] = filterGetByC_T_PrevAndNext(
				session, oAuthClientAuthServer, companyId, type,
				orderByComparator, true);

			array[1] = oAuthClientAuthServer;

			array[2] = filterGetByC_T_PrevAndNext(
				session, oAuthClientAuthServer, companyId, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthClientAuthServer filterGetByC_T_PrevAndNext(
		Session session, OAuthClientAuthServer oAuthClientAuthServer,
		long companyId, String type,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthClientAuthServerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthClientAuthServerImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthClientAuthServerImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthClientAuthServer)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthClientAuthServer> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth client auth servers where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, String type) {
		for (OAuthClientAuthServer oAuthClientAuthServer :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(oAuthClientAuthServer);
		}
	}

	/**
	 * Returns the number of o auth client auth servers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers
	 */
	@Override
	public int countByC_T(long companyId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByC_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of o auth client auth servers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching o auth client auth servers that the user has permission to view
	 */
	@Override
	public int filterCountByC_T(long companyId, String type) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_T(companyId, type);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthClientAuthServer.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"oAuthClientAuthServer.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"oAuthClientAuthServer.type = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3 =
		"(oAuthClientAuthServer.type IS NULL OR oAuthClientAuthServer.type = '')";

	private static final String _FINDER_COLUMN_C_T_TYPE_2_SQL =
		"oAuthClientAuthServer.type_ = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3_SQL =
		"(oAuthClientAuthServer.type_ IS NULL OR oAuthClientAuthServer.type_ = '')";

	public OAuthClientAuthServerPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(OAuthClientAuthServer.class);

		setModelImplClass(OAuthClientAuthServerImpl.class);
		setModelPKClass(long.class);

		setTable(OAuthClientAuthServerTable.INSTANCE);
	}

	/**
	 * Caches the o auth client auth server in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServer the o auth client auth server
	 */
	@Override
	public void cacheResult(OAuthClientAuthServer oAuthClientAuthServer) {
		entityCache.putResult(
			OAuthClientAuthServerImpl.class,
			oAuthClientAuthServer.getPrimaryKey(), oAuthClientAuthServer);

		finderCache.putResult(
			_finderPathFetchByC_I,
			new Object[] {
				oAuthClientAuthServer.getCompanyId(),
				oAuthClientAuthServer.getIssuer()
			},
			oAuthClientAuthServer);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the o auth client auth servers in the entity cache if it is enabled.
	 *
	 * @param oAuthClientAuthServers the o auth client auth servers
	 */
	@Override
	public void cacheResult(
		List<OAuthClientAuthServer> oAuthClientAuthServers) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (oAuthClientAuthServers.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OAuthClientAuthServer oAuthClientAuthServer :
				oAuthClientAuthServers) {

			if (entityCache.getResult(
					OAuthClientAuthServerImpl.class,
					oAuthClientAuthServer.getPrimaryKey()) == null) {

				cacheResult(oAuthClientAuthServer);
			}
		}
	}

	/**
	 * Clears the cache for all o auth client auth servers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuthClientAuthServerImpl.class);

		finderCache.clearCache(OAuthClientAuthServerImpl.class);
	}

	/**
	 * Clears the cache for the o auth client auth server.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuthClientAuthServer oAuthClientAuthServer) {
		entityCache.removeResult(
			OAuthClientAuthServerImpl.class, oAuthClientAuthServer);
	}

	@Override
	public void clearCache(List<OAuthClientAuthServer> oAuthClientAuthServers) {
		for (OAuthClientAuthServer oAuthClientAuthServer :
				oAuthClientAuthServers) {

			entityCache.removeResult(
				OAuthClientAuthServerImpl.class, oAuthClientAuthServer);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OAuthClientAuthServerImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				OAuthClientAuthServerImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthClientAuthServerModelImpl oAuthClientAuthServerModelImpl) {

		Object[] args = new Object[] {
			oAuthClientAuthServerModelImpl.getCompanyId(),
			oAuthClientAuthServerModelImpl.getIssuer()
		};

		finderCache.putResult(_finderPathCountByC_I, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_I, args, oAuthClientAuthServerModelImpl);
	}

	/**
	 * Creates a new o auth client auth server with the primary key. Does not add the o auth client auth server to the database.
	 *
	 * @param oAuthClientAuthServerId the primary key for the new o auth client auth server
	 * @return the new o auth client auth server
	 */
	@Override
	public OAuthClientAuthServer create(long oAuthClientAuthServerId) {
		OAuthClientAuthServer oAuthClientAuthServer =
			new OAuthClientAuthServerImpl();

		oAuthClientAuthServer.setNew(true);
		oAuthClientAuthServer.setPrimaryKey(oAuthClientAuthServerId);

		oAuthClientAuthServer.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthClientAuthServer;
	}

	/**
	 * Removes the o auth client auth server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer remove(long oAuthClientAuthServerId)
		throws NoSuchAuthServerException {

		return remove((Serializable)oAuthClientAuthServerId);
	}

	/**
	 * Removes the o auth client auth server with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth client auth server
	 * @return the o auth client auth server that was removed
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer remove(Serializable primaryKey)
		throws NoSuchAuthServerException {

		Session session = null;

		try {
			session = openSession();

			OAuthClientAuthServer oAuthClientAuthServer =
				(OAuthClientAuthServer)session.get(
					OAuthClientAuthServerImpl.class, primaryKey);

			if (oAuthClientAuthServer == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAuthServerException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthClientAuthServer);
		}
		catch (NoSuchAuthServerException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected OAuthClientAuthServer removeImpl(
		OAuthClientAuthServer oAuthClientAuthServer) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthClientAuthServer)) {
				oAuthClientAuthServer = (OAuthClientAuthServer)session.get(
					OAuthClientAuthServerImpl.class,
					oAuthClientAuthServer.getPrimaryKeyObj());
			}

			if (oAuthClientAuthServer != null) {
				session.delete(oAuthClientAuthServer);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (oAuthClientAuthServer != null) {
			clearCache(oAuthClientAuthServer);
		}

		return oAuthClientAuthServer;
	}

	@Override
	public OAuthClientAuthServer updateImpl(
		OAuthClientAuthServer oAuthClientAuthServer) {

		boolean isNew = oAuthClientAuthServer.isNew();

		if (!(oAuthClientAuthServer instanceof
				OAuthClientAuthServerModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthClientAuthServer.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuthClientAuthServer);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthClientAuthServer proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthClientAuthServer implementation " +
					oAuthClientAuthServer.getClass());
		}

		OAuthClientAuthServerModelImpl oAuthClientAuthServerModelImpl =
			(OAuthClientAuthServerModelImpl)oAuthClientAuthServer;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (oAuthClientAuthServer.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthClientAuthServer.setCreateDate(date);
			}
			else {
				oAuthClientAuthServer.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!oAuthClientAuthServerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthClientAuthServer.setModifiedDate(date);
			}
			else {
				oAuthClientAuthServer.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(oAuthClientAuthServer);
			}
			else {
				oAuthClientAuthServer = (OAuthClientAuthServer)session.merge(
					oAuthClientAuthServer);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OAuthClientAuthServerImpl.class, oAuthClientAuthServerModelImpl,
			false, true);

		cacheUniqueFindersCache(oAuthClientAuthServerModelImpl);

		if (isNew) {
			oAuthClientAuthServer.setNew(false);
		}

		oAuthClientAuthServer.resetOriginalValues();

		return oAuthClientAuthServer;
	}

	/**
	 * Returns the o auth client auth server with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAuthServerException {

		OAuthClientAuthServer oAuthClientAuthServer = fetchByPrimaryKey(
			primaryKey);

		if (oAuthClientAuthServer == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAuthServerException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthClientAuthServer;
	}

	/**
	 * Returns the o auth client auth server with the primary key or throws a <code>NoSuchAuthServerException</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server
	 * @throws NoSuchAuthServerException if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer findByPrimaryKey(long oAuthClientAuthServerId)
		throws NoSuchAuthServerException {

		return findByPrimaryKey((Serializable)oAuthClientAuthServerId);
	}

	/**
	 * Returns the o auth client auth server with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthClientAuthServerId the primary key of the o auth client auth server
	 * @return the o auth client auth server, or <code>null</code> if a o auth client auth server with the primary key could not be found
	 */
	@Override
	public OAuthClientAuthServer fetchByPrimaryKey(
		long oAuthClientAuthServerId) {

		return fetchByPrimaryKey((Serializable)oAuthClientAuthServerId);
	}

	/**
	 * Returns all the o auth client auth servers.
	 *
	 * @return the o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @return the range of o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findAll(
		int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth client auth servers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthClientAuthServerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth client auth servers
	 * @param end the upper bound of the range of o auth client auth servers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth client auth servers
	 */
	@Override
	public List<OAuthClientAuthServer> findAll(
		int start, int end,
		OrderByComparator<OAuthClientAuthServer> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<OAuthClientAuthServer> list = null;

		if (useFinderCache) {
			list = (List<OAuthClientAuthServer>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OAUTHCLIENTAUTHSERVER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHCLIENTAUTHSERVER;

				sql = sql.concat(OAuthClientAuthServerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OAuthClientAuthServer>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the o auth client auth servers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthClientAuthServer oAuthClientAuthServer : findAll()) {
			remove(oAuthClientAuthServer);
		}
	}

	/**
	 * Returns the number of o auth client auth servers.
	 *
	 * @return the number of o auth client auth servers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OAUTHCLIENTAUTHSERVER);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "oAuthClientAuthServerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHCLIENTAUTHSERVER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthClientAuthServerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth client auth server persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathFetchByC_I = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_I",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "issuer"}, true);

		_finderPathCountByC_I = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_I",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "issuer"}, false);

		_finderPathWithPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "type_"}, true);

		_finderPathCountByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_setOAuthClientAuthServerUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setOAuthClientAuthServerUtilPersistence(null);

		entityCache.removeCache(OAuthClientAuthServerImpl.class.getName());
	}

	private void _setOAuthClientAuthServerUtilPersistence(
		OAuthClientAuthServerPersistence oAuthClientAuthServerPersistence) {

		try {
			Field field = OAuthClientAuthServerUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, oAuthClientAuthServerPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthClientPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTHCLIENTAUTHSERVER =
		"SELECT oAuthClientAuthServer FROM OAuthClientAuthServer oAuthClientAuthServer";

	private static final String _SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE =
		"SELECT oAuthClientAuthServer FROM OAuthClientAuthServer oAuthClientAuthServer WHERE ";

	private static final String _SQL_COUNT_OAUTHCLIENTAUTHSERVER =
		"SELECT COUNT(oAuthClientAuthServer) FROM OAuthClientAuthServer oAuthClientAuthServer";

	private static final String _SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE =
		"SELECT COUNT(oAuthClientAuthServer) FROM OAuthClientAuthServer oAuthClientAuthServer WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"oAuthClientAuthServer.oAuthClientAuthServerId";

	private static final String _FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_WHERE =
		"SELECT DISTINCT {oAuthClientAuthServer.*} FROM OAuthClientAuthServer oAuthClientAuthServer WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OAuthClientAuthServer.*} FROM (SELECT DISTINCT oAuthClientAuthServer.oAuthClientAuthServerId FROM OAuthClientAuthServer oAuthClientAuthServer WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHCLIENTAUTHSERVER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OAuthClientAuthServer ON TEMP_TABLE.oAuthClientAuthServerId = OAuthClientAuthServer.oAuthClientAuthServerId";

	private static final String _FILTER_SQL_COUNT_OAUTHCLIENTAUTHSERVER_WHERE =
		"SELECT COUNT(DISTINCT oAuthClientAuthServer.oAuthClientAuthServerId) AS COUNT_VALUE FROM OAuthClientAuthServer oAuthClientAuthServer WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "oAuthClientAuthServer";

	private static final String _FILTER_ENTITY_TABLE = "OAuthClientAuthServer";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"oAuthClientAuthServer.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"OAuthClientAuthServer.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthClientAuthServer exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthClientAuthServer exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientAuthServerPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private OAuthClientAuthServerModelArgumentsResolver
		_oAuthClientAuthServerModelArgumentsResolver;

}