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

package com.liferay.oauth.service.persistence.impl;

import com.liferay.oauth.exception.NoSuchApplicationException;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthApplicationTable;
import com.liferay.oauth.model.impl.OAuthApplicationImpl;
import com.liferay.oauth.model.impl.OAuthApplicationModelImpl;
import com.liferay.oauth.service.persistence.OAuthApplicationPersistence;
import com.liferay.oauth.service.persistence.impl.constants.OAuthPersistenceConstants;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
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
 * The persistence implementation for the o auth application service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @generated
 */
@Component(service = {OAuthApplicationPersistence.class, BasePersistence.class})
public class OAuthApplicationPersistenceImpl
	extends BasePersistenceImpl<OAuthApplication>
	implements OAuthApplicationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuthApplicationUtil</code> to access the o auth application persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuthApplicationImpl.class.getName();

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
	 * Returns all the o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
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

		List<OAuthApplication> list = null;

		if (useFinderCache) {
			list = (List<OAuthApplication>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthApplication oAuthApplication : list) {
					if (companyId != oAuthApplication.getCompanyId()) {
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

			sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByCompanyId_First(
			long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByCompanyId_First(
		long companyId, OrderByComparator<OAuthApplication> orderByComparator) {

		List<OAuthApplication> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByCompanyId_Last(
			long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByCompanyId_Last(
		long companyId, OrderByComparator<OAuthApplication> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuthApplication> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] findByCompanyId_PrevAndNext(
			long oAuthApplicationId, long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, oAuthApplication, companyId, orderByComparator, true);

			array[1] = oAuthApplication;

			array[2] = getByCompanyId_PrevAndNext(
				session, oAuthApplication, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthApplication getByCompanyId_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long companyId,
		OrderByComparator<OAuthApplication> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

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
			sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
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
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] filterFindByCompanyId_PrevAndNext(
			long oAuthApplicationId, long companyId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				oAuthApplicationId, companyId, orderByComparator);
		}

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, oAuthApplication, companyId, orderByComparator, true);

			array[1] = oAuthApplication;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, oAuthApplication, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthApplication filterGetByCompanyId_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long companyId,
		OrderByComparator<OAuthApplication> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth applications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (OAuthApplication oAuthApplication :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthApplication);
		}
	}

	/**
	 * Returns the number of o auth applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth applications
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHAPPLICATION_WHERE);

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
	 * Returns the number of o auth applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
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
		"oAuthApplication.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
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

		List<OAuthApplication> list = null;

		if (useFinderCache) {
			list = (List<OAuthApplication>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthApplication oAuthApplication : list) {
					if (userId != oAuthApplication.getUserId()) {
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

			sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByUserId_First(
			long userId, OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByUserId_First(
			userId, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByUserId_First(
		long userId, OrderByComparator<OAuthApplication> orderByComparator) {

		List<OAuthApplication> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByUserId_Last(
			long userId, OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByUserId_Last(
			userId, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByUserId_Last(
		long userId, OrderByComparator<OAuthApplication> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<OAuthApplication> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] findByUserId_PrevAndNext(
			long oAuthApplicationId, long userId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, oAuthApplication, userId, orderByComparator, true);

			array[1] = oAuthApplication;

			array[2] = getByUserId_PrevAndNext(
				session, oAuthApplication, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthApplication getByUserId_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long userId,
		OrderByComparator<OAuthApplication> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

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
			sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
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
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByUserId(long userId) {
		return filterFindByUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByUserId(
		long userId, int start, int end) {

		return filterFindByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByUserId(
		long userId, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			return (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] filterFindByUserId_PrevAndNext(
			long oAuthApplicationId, long userId,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUserId_PrevAndNext(
				oAuthApplicationId, userId, orderByComparator);
		}

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = filterGetByUserId_PrevAndNext(
				session, oAuthApplication, userId, orderByComparator, true);

			array[1] = oAuthApplication;

			array[2] = filterGetByUserId_PrevAndNext(
				session, oAuthApplication, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuthApplication filterGetByUserId_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long userId,
		OrderByComparator<OAuthApplication> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth applications where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (OAuthApplication oAuthApplication :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthApplication);
		}
	}

	/**
	 * Returns the number of o auth applications where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth applications
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHAPPLICATION_WHERE);

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
	 * Returns the number of o auth applications that the user has permission to view where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	@Override
	public int filterCountByUserId(long userId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUserId(userId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
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
		"oAuthApplication.userId = ?";

	private FinderPath _finderPathFetchByConsumerKey;
	private FinderPath _finderPathCountByConsumerKey;

	/**
	 * Returns the o auth application where consumerKey = &#63; or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByConsumerKey(String consumerKey)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByConsumerKey(consumerKey);

		if (oAuthApplication == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("consumerKey=");
			sb.append(consumerKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchApplicationException(sb.toString());
		}

		return oAuthApplication;
	}

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByConsumerKey(String consumerKey) {
		return fetchByConsumerKey(consumerKey, true);
	}

	/**
	 * Returns the o auth application where consumerKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param consumerKey the consumer key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByConsumerKey(
		String consumerKey, boolean useFinderCache) {

		consumerKey = Objects.toString(consumerKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {consumerKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByConsumerKey, finderArgs);
		}

		if (result instanceof OAuthApplication) {
			OAuthApplication oAuthApplication = (OAuthApplication)result;

			if (!Objects.equals(
					consumerKey, oAuthApplication.getConsumerKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

			boolean bindConsumerKey = false;

			if (consumerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_3);
			}
			else {
				bindConsumerKey = true;

				sb.append(_FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindConsumerKey) {
					queryPos.add(consumerKey);
				}

				List<OAuthApplication> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByConsumerKey, finderArgs, list);
					}
				}
				else {
					OAuthApplication oAuthApplication = list.get(0);

					result = oAuthApplication;

					cacheResult(oAuthApplication);
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
			return (OAuthApplication)result;
		}
	}

	/**
	 * Removes the o auth application where consumerKey = &#63; from the database.
	 *
	 * @param consumerKey the consumer key
	 * @return the o auth application that was removed
	 */
	@Override
	public OAuthApplication removeByConsumerKey(String consumerKey)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = findByConsumerKey(consumerKey);

		return remove(oAuthApplication);
	}

	/**
	 * Returns the number of o auth applications where consumerKey = &#63;.
	 *
	 * @param consumerKey the consumer key
	 * @return the number of matching o auth applications
	 */
	@Override
	public int countByConsumerKey(String consumerKey) {
		consumerKey = Objects.toString(consumerKey, "");

		FinderPath finderPath = _finderPathCountByConsumerKey;

		Object[] finderArgs = new Object[] {consumerKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OAUTHAPPLICATION_WHERE);

			boolean bindConsumerKey = false;

			if (consumerKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_3);
			}
			else {
				bindConsumerKey = true;

				sb.append(_FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindConsumerKey) {
					queryPos.add(consumerKey);
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

	private static final String _FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_2 =
		"oAuthApplication.consumerKey = ?";

	private static final String _FINDER_COLUMN_CONSUMERKEY_CONSUMERKEY_3 =
		"(oAuthApplication.consumerKey IS NULL OR oAuthApplication.consumerKey = '')";

	private FinderPath _finderPathWithPaginationFindByC_LikeN;
	private FinderPath _finderPathWithPaginationCountByC_LikeN;

	/**
	 * Returns all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByC_LikeN(long companyId, String name) {
		return findByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end) {

		return findByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return findByC_LikeN(
			companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LikeN;
		finderArgs = new Object[] {
			companyId, name, start, end, orderByComparator
		};

		List<OAuthApplication> list = null;

		if (useFinderCache) {
			list = (List<OAuthApplication>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthApplication oAuthApplication : list) {
					if ((companyId != oAuthApplication.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							oAuthApplication.getName(), name, '_', '%', '\\',
							false)) {

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

			sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByC_LikeN_First(
			long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByC_LikeN_First(
			companyId, name, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the first o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByC_LikeN_First(
		long companyId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		List<OAuthApplication> list = findByC_LikeN(
			companyId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByC_LikeN_Last(
			long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByC_LikeN_Last(
			companyId, name, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the last o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByC_LikeN_Last(
		long companyId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		int count = countByC_LikeN(companyId, name);

		if (count == 0) {
			return null;
		}

		List<OAuthApplication> list = findByC_LikeN(
			companyId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] findByC_LikeN_PrevAndNext(
			long oAuthApplicationId, long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		name = Objects.toString(name, "");

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = getByC_LikeN_PrevAndNext(
				session, oAuthApplication, companyId, name, orderByComparator,
				true);

			array[1] = oAuthApplication;

			array[2] = getByC_LikeN_PrevAndNext(
				session, oAuthApplication, companyId, name, orderByComparator,
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

	protected OAuthApplication getByC_LikeN_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long companyId,
		String name, OrderByComparator<OAuthApplication> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
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
			sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name) {

		return filterFindByC_LikeN(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name, int start, int end) {

		return filterFindByC_LikeN(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByC_LikeN(
		long companyId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_LikeN(
				companyId, name, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

			return (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] filterFindByC_LikeN_PrevAndNext(
			long oAuthApplicationId, long companyId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_LikeN_PrevAndNext(
				oAuthApplicationId, companyId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = filterGetByC_LikeN_PrevAndNext(
				session, oAuthApplication, companyId, name, orderByComparator,
				true);

			array[1] = oAuthApplication;

			array[2] = filterGetByC_LikeN_PrevAndNext(
				session, oAuthApplication, companyId, name, orderByComparator,
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

	protected OAuthApplication filterGetByC_LikeN_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long companyId,
		String name, OrderByComparator<OAuthApplication> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth applications where companyId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_LikeN(long companyId, String name) {
		for (OAuthApplication oAuthApplication :
				findByC_LikeN(
					companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(oAuthApplication);
		}
	}

	/**
	 * Returns the number of o auth applications where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	@Override
	public int countByC_LikeN(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_LikeN;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
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
	 * Returns the number of o auth applications that the user has permission to view where companyId = &#63; and name LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	@Override
	public int filterCountByC_LikeN(long companyId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_LikeN(companyId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKEN_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
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

	private static final String _FINDER_COLUMN_C_LIKEN_COMPANYID_2 =
		"oAuthApplication.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_LIKEN_NAME_2 =
		"lower(oAuthApplication.name) LIKE ?";

	private static final String _FINDER_COLUMN_C_LIKEN_NAME_3 =
		"(oAuthApplication.name IS NULL OR oAuthApplication.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByU_LikeN;
	private FinderPath _finderPathWithPaginationCountByU_LikeN;

	/**
	 * Returns all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByU_LikeN(long userId, String name) {
		return findByU_LikeN(
			userId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end) {

		return findByU_LikeN(userId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return findByU_LikeN(userId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth applications
	 */
	@Override
	public List<OAuthApplication> findByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByU_LikeN;
		finderArgs = new Object[] {userId, name, start, end, orderByComparator};

		List<OAuthApplication> list = null;

		if (useFinderCache) {
			list = (List<OAuthApplication>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (OAuthApplication oAuthApplication : list) {
					if ((userId != oAuthApplication.getUserId()) ||
						!StringUtil.wildcardMatches(
							oAuthApplication.getName(), name, '_', '%', '\\',
							false)) {

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

			sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
				}

				list = (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByU_LikeN_First(
			long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByU_LikeN_First(
			userId, name, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the first o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByU_LikeN_First(
		long userId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		List<OAuthApplication> list = findByU_LikeN(
			userId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application
	 * @throws NoSuchApplicationException if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication findByU_LikeN_Last(
			long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByU_LikeN_Last(
			userId, name, orderByComparator);

		if (oAuthApplication != null) {
			return oAuthApplication;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchApplicationException(sb.toString());
	}

	/**
	 * Returns the last o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth application, or <code>null</code> if a matching o auth application could not be found
	 */
	@Override
	public OAuthApplication fetchByU_LikeN_Last(
		long userId, String name,
		OrderByComparator<OAuthApplication> orderByComparator) {

		int count = countByU_LikeN(userId, name);

		if (count == 0) {
			return null;
		}

		List<OAuthApplication> list = findByU_LikeN(
			userId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth applications before and after the current o auth application in the ordered set where userId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] findByU_LikeN_PrevAndNext(
			long oAuthApplicationId, long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		name = Objects.toString(name, "");

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = getByU_LikeN_PrevAndNext(
				session, oAuthApplication, userId, name, orderByComparator,
				true);

			array[1] = oAuthApplication;

			array[2] = getByU_LikeN_PrevAndNext(
				session, oAuthApplication, userId, name, orderByComparator,
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

	protected OAuthApplication getByU_LikeN_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long userId,
		String name, OrderByComparator<OAuthApplication> orderByComparator,
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

		sb.append(_SQL_SELECT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
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
			sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name) {

		return filterFindByU_LikeN(
			userId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name, int start, int end) {

		return filterFindByU_LikeN(userId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications that the user has permissions to view where userId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth applications that the user has permission to view
	 */
	@Override
	public List<OAuthApplication> filterFindByU_LikeN(
		long userId, String name, int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByU_LikeN(userId, name, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
			}

			return (List<OAuthApplication>)QueryUtil.list(
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
	 * Returns the o auth applications before and after the current o auth application in the ordered set of o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param oAuthApplicationId the primary key of the current o auth application
	 * @param userId the user ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication[] filterFindByU_LikeN_PrevAndNext(
			long oAuthApplicationId, long userId, String name,
			OrderByComparator<OAuthApplication> orderByComparator)
		throws NoSuchApplicationException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByU_LikeN_PrevAndNext(
				oAuthApplicationId, userId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		OAuthApplication oAuthApplication = findByPrimaryKey(
			oAuthApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuthApplication[] array = new OAuthApplicationImpl[3];

			array[0] = filterGetByU_LikeN_PrevAndNext(
				session, oAuthApplication, userId, name, orderByComparator,
				true);

			array[1] = oAuthApplication;

			array[2] = filterGetByU_LikeN_PrevAndNext(
				session, oAuthApplication, userId, name, orderByComparator,
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

	protected OAuthApplication filterGetByU_LikeN_PrevAndNext(
		Session session, OAuthApplication oAuthApplication, long userId,
		String name, OrderByComparator<OAuthApplication> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(OAuthApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, OAuthApplicationImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, OAuthApplicationImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(userId);

		if (bindName) {
			queryPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuthApplication)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OAuthApplication> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth applications where userId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param name the name
	 */
	@Override
	public void removeByU_LikeN(long userId, String name) {
		for (OAuthApplication oAuthApplication :
				findByU_LikeN(
					userId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuthApplication);
		}
	}

	/**
	 * Returns the number of o auth applications where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the number of matching o auth applications
	 */
	@Override
	public int countByU_LikeN(long userId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByU_LikeN;

		Object[] finderArgs = new Object[] {userId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OAUTHAPPLICATION_WHERE);

			sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindName) {
					queryPos.add(StringUtil.toLowerCase(name));
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
	 * Returns the number of o auth applications that the user has permission to view where userId = &#63; and name LIKE &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @return the number of matching o auth applications that the user has permission to view
	 */
	@Override
	public int filterCountByU_LikeN(long userId, String name) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByU_LikeN(userId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OAUTHAPPLICATION_WHERE);

		sb.append(_FINDER_COLUMN_U_LIKEN_USERID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_U_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), OAuthApplication.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(userId);

			if (bindName) {
				queryPos.add(StringUtil.toLowerCase(name));
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

	private static final String _FINDER_COLUMN_U_LIKEN_USERID_2 =
		"oAuthApplication.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_LIKEN_NAME_2 =
		"lower(oAuthApplication.name) LIKE ?";

	private static final String _FINDER_COLUMN_U_LIKEN_NAME_3 =
		"(oAuthApplication.name IS NULL OR oAuthApplication.name LIKE '')";

	public OAuthApplicationPersistenceImpl() {
		setModelClass(OAuthApplication.class);

		setModelImplClass(OAuthApplicationImpl.class);
		setModelPKClass(long.class);

		setTable(OAuthApplicationTable.INSTANCE);
	}

	/**
	 * Caches the o auth application in the entity cache if it is enabled.
	 *
	 * @param oAuthApplication the o auth application
	 */
	@Override
	public void cacheResult(OAuthApplication oAuthApplication) {
		entityCache.putResult(
			OAuthApplicationImpl.class, oAuthApplication.getPrimaryKey(),
			oAuthApplication);

		finderCache.putResult(
			_finderPathFetchByConsumerKey,
			new Object[] {oAuthApplication.getConsumerKey()}, oAuthApplication);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the o auth applications in the entity cache if it is enabled.
	 *
	 * @param oAuthApplications the o auth applications
	 */
	@Override
	public void cacheResult(List<OAuthApplication> oAuthApplications) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (oAuthApplications.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OAuthApplication oAuthApplication : oAuthApplications) {
			if (entityCache.getResult(
					OAuthApplicationImpl.class,
					oAuthApplication.getPrimaryKey()) == null) {

				cacheResult(oAuthApplication);
			}
		}
	}

	/**
	 * Clears the cache for all o auth applications.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuthApplicationImpl.class);

		finderCache.clearCache(OAuthApplicationImpl.class);
	}

	/**
	 * Clears the cache for the o auth application.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuthApplication oAuthApplication) {
		entityCache.removeResult(OAuthApplicationImpl.class, oAuthApplication);
	}

	@Override
	public void clearCache(List<OAuthApplication> oAuthApplications) {
		for (OAuthApplication oAuthApplication : oAuthApplications) {
			entityCache.removeResult(
				OAuthApplicationImpl.class, oAuthApplication);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(OAuthApplicationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(OAuthApplicationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuthApplicationModelImpl oAuthApplicationModelImpl) {

		Object[] args = new Object[] {
			oAuthApplicationModelImpl.getConsumerKey()
		};

		finderCache.putResult(
			_finderPathCountByConsumerKey, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByConsumerKey, args, oAuthApplicationModelImpl);
	}

	/**
	 * Creates a new o auth application with the primary key. Does not add the o auth application to the database.
	 *
	 * @param oAuthApplicationId the primary key for the new o auth application
	 * @return the new o auth application
	 */
	@Override
	public OAuthApplication create(long oAuthApplicationId) {
		OAuthApplication oAuthApplication = new OAuthApplicationImpl();

		oAuthApplication.setNew(true);
		oAuthApplication.setPrimaryKey(oAuthApplicationId);

		oAuthApplication.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuthApplication;
	}

	/**
	 * Removes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application that was removed
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication remove(long oAuthApplicationId)
		throws NoSuchApplicationException {

		return remove((Serializable)oAuthApplicationId);
	}

	/**
	 * Removes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth application
	 * @return the o auth application that was removed
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication remove(Serializable primaryKey)
		throws NoSuchApplicationException {

		Session session = null;

		try {
			session = openSession();

			OAuthApplication oAuthApplication = (OAuthApplication)session.get(
				OAuthApplicationImpl.class, primaryKey);

			if (oAuthApplication == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchApplicationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuthApplication);
		}
		catch (NoSuchApplicationException noSuchEntityException) {
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
	protected OAuthApplication removeImpl(OAuthApplication oAuthApplication) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuthApplication)) {
				oAuthApplication = (OAuthApplication)session.get(
					OAuthApplicationImpl.class,
					oAuthApplication.getPrimaryKeyObj());
			}

			if (oAuthApplication != null) {
				session.delete(oAuthApplication);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (oAuthApplication != null) {
			clearCache(oAuthApplication);
		}

		return oAuthApplication;
	}

	@Override
	public OAuthApplication updateImpl(OAuthApplication oAuthApplication) {
		boolean isNew = oAuthApplication.isNew();

		if (!(oAuthApplication instanceof OAuthApplicationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuthApplication.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuthApplication);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuthApplication proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuthApplication implementation " +
					oAuthApplication.getClass());
		}

		OAuthApplicationModelImpl oAuthApplicationModelImpl =
			(OAuthApplicationModelImpl)oAuthApplication;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (oAuthApplication.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuthApplication.setCreateDate(date);
			}
			else {
				oAuthApplication.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!oAuthApplicationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuthApplication.setModifiedDate(date);
			}
			else {
				oAuthApplication.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(oAuthApplication);
			}
			else {
				oAuthApplication = (OAuthApplication)session.merge(
					oAuthApplication);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			OAuthApplicationImpl.class, oAuthApplicationModelImpl, false, true);

		cacheUniqueFindersCache(oAuthApplicationModelImpl);

		if (isNew) {
			oAuthApplication.setNew(false);
		}

		oAuthApplication.resetOriginalValues();

		return oAuthApplication;
	}

	/**
	 * Returns the o auth application with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth application
	 * @return the o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication findByPrimaryKey(Serializable primaryKey)
		throws NoSuchApplicationException {

		OAuthApplication oAuthApplication = fetchByPrimaryKey(primaryKey);

		if (oAuthApplication == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchApplicationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuthApplication;
	}

	/**
	 * Returns the o auth application with the primary key or throws a <code>NoSuchApplicationException</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application
	 * @throws NoSuchApplicationException if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication findByPrimaryKey(long oAuthApplicationId)
		throws NoSuchApplicationException {

		return findByPrimaryKey((Serializable)oAuthApplicationId);
	}

	/**
	 * Returns the o auth application with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuthApplicationId the primary key of the o auth application
	 * @return the o auth application, or <code>null</code> if a o auth application with the primary key could not be found
	 */
	@Override
	public OAuthApplication fetchByPrimaryKey(long oAuthApplicationId) {
		return fetchByPrimaryKey((Serializable)oAuthApplicationId);
	}

	/**
	 * Returns all the o auth applications.
	 *
	 * @return the o auth applications
	 */
	@Override
	public List<OAuthApplication> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @return the range of o auth applications
	 */
	@Override
	public List<OAuthApplication> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth applications
	 */
	@Override
	public List<OAuthApplication> findAll(
		int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuthApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth applications
	 * @param end the upper bound of the range of o auth applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth applications
	 */
	@Override
	public List<OAuthApplication> findAll(
		int start, int end,
		OrderByComparator<OAuthApplication> orderByComparator,
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

		List<OAuthApplication> list = null;

		if (useFinderCache) {
			list = (List<OAuthApplication>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OAUTHAPPLICATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTHAPPLICATION;

				sql = sql.concat(OAuthApplicationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OAuthApplication>)QueryUtil.list(
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
	 * Removes all the o auth applications from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuthApplication oAuthApplication : findAll()) {
			remove(oAuthApplication);
		}
	}

	/**
	 * Returns the number of o auth applications.
	 *
	 * @return the number of o auth applications
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OAUTHAPPLICATION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "oAuthApplicationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTHAPPLICATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuthApplicationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth application persistence.
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

		_finderPathFetchByConsumerKey = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByConsumerKey",
			new String[] {String.class.getName()}, new String[] {"consumerKey"},
			true);

		_finderPathCountByConsumerKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByConsumerKey",
			new String[] {String.class.getName()}, new String[] {"consumerKey"},
			false);

		_finderPathWithPaginationFindByC_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "name"}, true);

		_finderPathWithPaginationCountByC_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		_finderPathWithPaginationFindByU_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "name"}, true);

		_finderPathWithPaginationCountByU_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "name"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(OAuthApplicationImpl.class.getName());
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTHAPPLICATION =
		"SELECT oAuthApplication FROM OAuthApplication oAuthApplication";

	private static final String _SQL_SELECT_OAUTHAPPLICATION_WHERE =
		"SELECT oAuthApplication FROM OAuthApplication oAuthApplication WHERE ";

	private static final String _SQL_COUNT_OAUTHAPPLICATION =
		"SELECT COUNT(oAuthApplication) FROM OAuthApplication oAuthApplication";

	private static final String _SQL_COUNT_OAUTHAPPLICATION_WHERE =
		"SELECT COUNT(oAuthApplication) FROM OAuthApplication oAuthApplication WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"oAuthApplication.oAuthApplicationId";

	private static final String _FILTER_SQL_SELECT_OAUTHAPPLICATION_WHERE =
		"SELECT DISTINCT {oAuthApplication.*} FROM OAuth_OAuthApplication oAuthApplication WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OAuth_OAuthApplication.*} FROM (SELECT DISTINCT oAuthApplication.oAuthApplicationId FROM OAuth_OAuthApplication oAuthApplication WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTHAPPLICATION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OAuth_OAuthApplication ON TEMP_TABLE.oAuthApplicationId = OAuth_OAuthApplication.oAuthApplicationId";

	private static final String _FILTER_SQL_COUNT_OAUTHAPPLICATION_WHERE =
		"SELECT COUNT(DISTINCT oAuthApplication.oAuthApplicationId) AS COUNT_VALUE FROM OAuth_OAuthApplication oAuthApplication WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "oAuthApplication";

	private static final String _FILTER_ENTITY_TABLE = "OAuth_OAuthApplication";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuthApplication.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"OAuth_OAuthApplication.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuthApplication exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuthApplication exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthApplicationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private OAuthApplicationModelArgumentsResolver
		_oAuthApplicationModelArgumentsResolver;

}