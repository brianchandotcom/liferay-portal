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

import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ApplicationModelImpl;
import com.liferay.oauth2.provider.service.persistence.OAuth2ApplicationPersistence;
import com.liferay.oauth2.provider.service.persistence.impl.constants.OAuthTwoPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the o auth2 application service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = OAuth2ApplicationPersistence.class)
public class OAuth2ApplicationPersistenceImpl
	extends BasePersistenceImpl<OAuth2Application>
	implements OAuth2ApplicationPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OAuth2ApplicationUtil</code> to access the o auth2 application persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OAuth2ApplicationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC;
	private FinderPath _finderPathWithoutPaginationFindByC;
	private FinderPath _finderPathCountByC;

	/**
	 * Returns all the o auth2 applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findByC(long companyId) {
		return findByC(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findByC(long companyId, int start, int end) {
		return findByC(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findByC(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return findByC(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findByC(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<OAuth2Application> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Application>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OAuth2Application oAuth2Application : list) {
					if (companyId != oAuth2Application.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_OAUTH2APPLICATION_WHERE);

			query.append(_FINDER_COLUMN_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(OAuth2ApplicationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<OAuth2Application>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application findByC_First(
			long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = fetchByC_First(
			companyId, orderByComparator);

		if (oAuth2Application != null) {
			return oAuth2Application;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationException(msg.toString());
	}

	/**
	 * Returns the first o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application fetchByC_First(
		long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		List<OAuth2Application> list = findByC(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application findByC_Last(
			long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = fetchByC_Last(
			companyId, orderByComparator);

		if (oAuth2Application != null) {
			return oAuth2Application;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchOAuth2ApplicationException(msg.toString());
	}

	/**
	 * Returns the last o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application fetchByC_Last(
		long companyId,
		OrderByComparator<OAuth2Application> orderByComparator) {

		int count = countByC(companyId);

		if (count == 0) {
			return null;
		}

		List<OAuth2Application> list = findByC(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set where companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application[] findByC_PrevAndNext(
			long oAuth2ApplicationId, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = findByPrimaryKey(
			oAuth2ApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Application[] array = new OAuth2ApplicationImpl[3];

			array[0] = getByC_PrevAndNext(
				session, oAuth2Application, companyId, orderByComparator, true);

			array[1] = oAuth2Application;

			array[2] = getByC_PrevAndNext(
				session, oAuth2Application, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Application getByC_PrevAndNext(
		Session session, OAuth2Application oAuth2Application, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_OAUTH2APPLICATION_WHERE);

		query.append(_FINDER_COLUMN_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(OAuth2ApplicationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Application)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Application> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching o auth2 applications that the user has permission to view
	 */
	@Override
	public List<OAuth2Application> filterFindByC(long companyId) {
		return filterFindByC(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of matching o auth2 applications that the user has permission to view
	 */
	@Override
	public List<OAuth2Application> filterFindByC(
		long companyId, int start, int end) {

		return filterFindByC(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching o auth2 applications that the user has permission to view
	 */
	@Override
	public List<OAuth2Application> filterFindByC(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_OAUTH2APPLICATION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(OAuth2ApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OAuth2ApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), OAuth2Application.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, OAuth2ApplicationImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, OAuth2ApplicationImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<OAuth2Application>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the o auth2 applications before and after the current o auth2 application in the ordered set of o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param oAuth2ApplicationId the primary key of the current o auth2 application
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application[] filterFindByC_PrevAndNext(
			long oAuth2ApplicationId, long companyId,
			OrderByComparator<OAuth2Application> orderByComparator)
		throws NoSuchOAuth2ApplicationException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_PrevAndNext(
				oAuth2ApplicationId, companyId, orderByComparator);
		}

		OAuth2Application oAuth2Application = findByPrimaryKey(
			oAuth2ApplicationId);

		Session session = null;

		try {
			session = openSession();

			OAuth2Application[] array = new OAuth2ApplicationImpl[3];

			array[0] = filterGetByC_PrevAndNext(
				session, oAuth2Application, companyId, orderByComparator, true);

			array[1] = oAuth2Application;

			array[2] = filterGetByC_PrevAndNext(
				session, oAuth2Application, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OAuth2Application filterGetByC_PrevAndNext(
		Session session, OAuth2Application oAuth2Application, long companyId,
		OrderByComparator<OAuth2Application> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_OAUTH2APPLICATION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(OAuth2ApplicationModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(OAuth2ApplicationModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), OAuth2Application.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, OAuth2ApplicationImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, OAuth2ApplicationImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						oAuth2Application)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<OAuth2Application> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the o auth2 applications where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByC(long companyId) {
		for (OAuth2Application oAuth2Application :
				findByC(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(oAuth2Application);
		}
	}

	/**
	 * Returns the number of o auth2 applications where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications
	 */
	@Override
	public int countByC(long companyId) {
		FinderPath finderPath = _finderPathCountByC;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_OAUTH2APPLICATION_WHERE);

			query.append(_FINDER_COLUMN_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of o auth2 applications that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching o auth2 applications that the user has permission to view
	 */
	@Override
	public int filterCountByC(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_OAUTH2APPLICATION_WHERE);

		query.append(_FINDER_COLUMN_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), OAuth2Application.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_COMPANYID_2 =
		"oAuth2Application.companyId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or throws a <code>NoSuchOAuth2ApplicationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the matching o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application findByC_C(long companyId, String clientId)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = fetchByC_C(companyId, clientId);

		if (oAuth2Application == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", clientId=");
			msg.append(clientId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchOAuth2ApplicationException(msg.toString());
		}

		return oAuth2Application;
	}

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application fetchByC_C(long companyId, String clientId) {
		return fetchByC_C(companyId, clientId, true);
	}

	/**
	 * Returns the o auth2 application where companyId = &#63; and clientId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching o auth2 application, or <code>null</code> if a matching o auth2 application could not be found
	 */
	@Override
	public OAuth2Application fetchByC_C(
		long companyId, String clientId, boolean useFinderCache) {

		clientId = Objects.toString(clientId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, clientId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof OAuth2Application) {
			OAuth2Application oAuth2Application = (OAuth2Application)result;

			if ((companyId != oAuth2Application.getCompanyId()) ||
				!Objects.equals(clientId, oAuth2Application.getClientId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_OAUTH2APPLICATION_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_C_C_CLIENTID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindClientId) {
					qPos.add(clientId);
				}

				List<OAuth2Application> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {companyId, clientId};
							}

							_log.warn(
								"OAuth2ApplicationPersistenceImpl.fetchByC_C(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					OAuth2Application oAuth2Application = list.get(0);

					result = oAuth2Application;

					cacheResult(oAuth2Application);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_C, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (OAuth2Application)result;
		}
	}

	/**
	 * Removes the o auth2 application where companyId = &#63; and clientId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the o auth2 application that was removed
	 */
	@Override
	public OAuth2Application removeByC_C(long companyId, String clientId)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = findByC_C(companyId, clientId);

		return remove(oAuth2Application);
	}

	/**
	 * Returns the number of o auth2 applications where companyId = &#63; and clientId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param clientId the client ID
	 * @return the number of matching o auth2 applications
	 */
	@Override
	public int countByC_C(long companyId, String clientId) {
		clientId = Objects.toString(clientId, "");

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {companyId, clientId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_OAUTH2APPLICATION_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindClientId = false;

			if (clientId.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_CLIENTID_3);
			}
			else {
				bindClientId = true;

				query.append(_FINDER_COLUMN_C_C_CLIENTID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindClientId) {
					qPos.add(clientId);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 =
		"oAuth2Application.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLIENTID_2 =
		"oAuth2Application.clientId = ?";

	private static final String _FINDER_COLUMN_C_C_CLIENTID_3 =
		"(oAuth2Application.clientId IS NULL OR oAuth2Application.clientId = '')";

	public OAuth2ApplicationPersistenceImpl() {
		setModelClass(OAuth2Application.class);

		setModelImplClass(OAuth2ApplicationImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"oAuth2ApplicationScopeAliasesId", "oA2AScopeAliasesId");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the o auth2 application in the entity cache if it is enabled.
	 *
	 * @param oAuth2Application the o auth2 application
	 */
	@Override
	public void cacheResult(OAuth2Application oAuth2Application) {
		entityCache.putResult(
			entityCacheEnabled, OAuth2ApplicationImpl.class,
			oAuth2Application.getPrimaryKey(), oAuth2Application);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				oAuth2Application.getCompanyId(),
				oAuth2Application.getClientId()
			},
			oAuth2Application);

		oAuth2Application.resetOriginalValues();
	}

	/**
	 * Caches the o auth2 applications in the entity cache if it is enabled.
	 *
	 * @param oAuth2Applications the o auth2 applications
	 */
	@Override
	public void cacheResult(List<OAuth2Application> oAuth2Applications) {
		for (OAuth2Application oAuth2Application : oAuth2Applications) {
			if (entityCache.getResult(
					entityCacheEnabled, OAuth2ApplicationImpl.class,
					oAuth2Application.getPrimaryKey()) == null) {

				cacheResult(oAuth2Application);
			}
			else {
				oAuth2Application.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all o auth2 applications.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(OAuth2ApplicationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the o auth2 application.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OAuth2Application oAuth2Application) {
		entityCache.removeResult(
			entityCacheEnabled, OAuth2ApplicationImpl.class,
			oAuth2Application.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(OAuth2ApplicationModelImpl)oAuth2Application, true);
	}

	@Override
	public void clearCache(List<OAuth2Application> oAuth2Applications) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (OAuth2Application oAuth2Application : oAuth2Applications) {
			entityCache.removeResult(
				entityCacheEnabled, OAuth2ApplicationImpl.class,
				oAuth2Application.getPrimaryKey());

			clearUniqueFindersCache(
				(OAuth2ApplicationModelImpl)oAuth2Application, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, OAuth2ApplicationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		OAuth2ApplicationModelImpl oAuth2ApplicationModelImpl) {

		Object[] args = new Object[] {
			oAuth2ApplicationModelImpl.getCompanyId(),
			oAuth2ApplicationModelImpl.getClientId()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, oAuth2ApplicationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		OAuth2ApplicationModelImpl oAuth2ApplicationModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				oAuth2ApplicationModelImpl.getCompanyId(),
				oAuth2ApplicationModelImpl.getClientId()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((oAuth2ApplicationModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				oAuth2ApplicationModelImpl.getOriginalCompanyId(),
				oAuth2ApplicationModelImpl.getOriginalClientId()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new o auth2 application with the primary key. Does not add the o auth2 application to the database.
	 *
	 * @param oAuth2ApplicationId the primary key for the new o auth2 application
	 * @return the new o auth2 application
	 */
	@Override
	public OAuth2Application create(long oAuth2ApplicationId) {
		OAuth2Application oAuth2Application = new OAuth2ApplicationImpl();

		oAuth2Application.setNew(true);
		oAuth2Application.setPrimaryKey(oAuth2ApplicationId);

		oAuth2Application.setCompanyId(CompanyThreadLocal.getCompanyId());

		return oAuth2Application;
	}

	/**
	 * Removes the o auth2 application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application that was removed
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application remove(long oAuth2ApplicationId)
		throws NoSuchOAuth2ApplicationException {

		return remove((Serializable)oAuth2ApplicationId);
	}

	/**
	 * Removes the o auth2 application with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the o auth2 application
	 * @return the o auth2 application that was removed
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application remove(Serializable primaryKey)
		throws NoSuchOAuth2ApplicationException {

		Session session = null;

		try {
			session = openSession();

			OAuth2Application oAuth2Application =
				(OAuth2Application)session.get(
					OAuth2ApplicationImpl.class, primaryKey);

			if (oAuth2Application == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOAuth2ApplicationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(oAuth2Application);
		}
		catch (NoSuchOAuth2ApplicationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected OAuth2Application removeImpl(
		OAuth2Application oAuth2Application) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(oAuth2Application)) {
				oAuth2Application = (OAuth2Application)session.get(
					OAuth2ApplicationImpl.class,
					oAuth2Application.getPrimaryKeyObj());
			}

			if (oAuth2Application != null) {
				session.delete(oAuth2Application);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (oAuth2Application != null) {
			clearCache(oAuth2Application);
		}

		return oAuth2Application;
	}

	@Override
	public OAuth2Application updateImpl(OAuth2Application oAuth2Application) {
		boolean isNew = oAuth2Application.isNew();

		if (!(oAuth2Application instanceof OAuth2ApplicationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(oAuth2Application.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					oAuth2Application);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in oAuth2Application proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OAuth2Application implementation " +
					oAuth2Application.getClass());
		}

		OAuth2ApplicationModelImpl oAuth2ApplicationModelImpl =
			(OAuth2ApplicationModelImpl)oAuth2Application;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (oAuth2Application.getCreateDate() == null)) {
			if (serviceContext == null) {
				oAuth2Application.setCreateDate(now);
			}
			else {
				oAuth2Application.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!oAuth2ApplicationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				oAuth2Application.setModifiedDate(now);
			}
			else {
				oAuth2Application.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (oAuth2Application.isNew()) {
				session.save(oAuth2Application);

				oAuth2Application.setNew(false);
			}
			else {
				oAuth2Application = (OAuth2Application)session.merge(
					oAuth2Application);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				oAuth2ApplicationModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByC, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByC, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((oAuth2ApplicationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC.getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					oAuth2ApplicationModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByC, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC, args);

				args = new Object[] {oAuth2ApplicationModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByC, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, OAuth2ApplicationImpl.class,
			oAuth2Application.getPrimaryKey(), oAuth2Application, false);

		clearUniqueFindersCache(oAuth2ApplicationModelImpl, false);
		cacheUniqueFindersCache(oAuth2ApplicationModelImpl);

		oAuth2Application.resetOriginalValues();

		return oAuth2Application;
	}

	/**
	 * Returns the o auth2 application with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the o auth2 application
	 * @return the o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOAuth2ApplicationException {

		OAuth2Application oAuth2Application = fetchByPrimaryKey(primaryKey);

		if (oAuth2Application == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOAuth2ApplicationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return oAuth2Application;
	}

	/**
	 * Returns the o auth2 application with the primary key or throws a <code>NoSuchOAuth2ApplicationException</code> if it could not be found.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application
	 * @throws NoSuchOAuth2ApplicationException if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application findByPrimaryKey(long oAuth2ApplicationId)
		throws NoSuchOAuth2ApplicationException {

		return findByPrimaryKey((Serializable)oAuth2ApplicationId);
	}

	/**
	 * Returns the o auth2 application with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param oAuth2ApplicationId the primary key of the o auth2 application
	 * @return the o auth2 application, or <code>null</code> if a o auth2 application with the primary key could not be found
	 */
	@Override
	public OAuth2Application fetchByPrimaryKey(long oAuth2ApplicationId) {
		return fetchByPrimaryKey((Serializable)oAuth2ApplicationId);
	}

	/**
	 * Returns all the o auth2 applications.
	 *
	 * @return the o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @return the range of o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findAll(
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the o auth2 applications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OAuth2ApplicationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 applications
	 * @param end the upper bound of the range of o auth2 applications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of o auth2 applications
	 */
	@Override
	public List<OAuth2Application> findAll(
		int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator,
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

		List<OAuth2Application> list = null;

		if (useFinderCache) {
			list = (List<OAuth2Application>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_OAUTH2APPLICATION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_OAUTH2APPLICATION;

				sql = sql.concat(OAuth2ApplicationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<OAuth2Application>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the o auth2 applications from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OAuth2Application oAuth2Application : findAll()) {
			remove(oAuth2Application);
		}
	}

	/**
	 * Returns the number of o auth2 applications.
	 *
	 * @return the number of o auth2 applications
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_OAUTH2APPLICATION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "oAuth2ApplicationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OAUTH2APPLICATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OAuth2ApplicationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the o auth2 application persistence.
	 */
	@Activate
	public void activate() {
		OAuth2ApplicationModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		OAuth2ApplicationModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuth2ApplicationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuth2ApplicationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByC = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuth2ApplicationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuth2ApplicationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC",
			new String[] {Long.class.getName()},
			OAuth2ApplicationModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByC = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, OAuth2ApplicationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			OAuth2ApplicationModelImpl.COMPANYID_COLUMN_BITMASK |
			OAuth2ApplicationModelImpl.CLIENTID_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(OAuth2ApplicationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.oauth2.provider.model.OAuth2Application"),
			true);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OAuthTwoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OAUTH2APPLICATION =
		"SELECT oAuth2Application FROM OAuth2Application oAuth2Application";

	private static final String _SQL_SELECT_OAUTH2APPLICATION_WHERE =
		"SELECT oAuth2Application FROM OAuth2Application oAuth2Application WHERE ";

	private static final String _SQL_COUNT_OAUTH2APPLICATION =
		"SELECT COUNT(oAuth2Application) FROM OAuth2Application oAuth2Application";

	private static final String _SQL_COUNT_OAUTH2APPLICATION_WHERE =
		"SELECT COUNT(oAuth2Application) FROM OAuth2Application oAuth2Application WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"oAuth2Application.oAuth2ApplicationId";

	private static final String _FILTER_SQL_SELECT_OAUTH2APPLICATION_WHERE =
		"SELECT DISTINCT {oAuth2Application.*} FROM OAuth2Application oAuth2Application WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {OAuth2Application.*} FROM (SELECT DISTINCT oAuth2Application.oAuth2ApplicationId FROM OAuth2Application oAuth2Application WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OAUTH2APPLICATION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN OAuth2Application ON TEMP_TABLE.oAuth2ApplicationId = OAuth2Application.oAuth2ApplicationId";

	private static final String _FILTER_SQL_COUNT_OAUTH2APPLICATION_WHERE =
		"SELECT COUNT(DISTINCT oAuth2Application.oAuth2ApplicationId) AS COUNT_VALUE FROM OAuth2Application oAuth2Application WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "oAuth2Application";

	private static final String _FILTER_ENTITY_TABLE = "OAuth2Application";

	private static final String _ORDER_BY_ENTITY_ALIAS = "oAuth2Application.";

	private static final String _ORDER_BY_ENTITY_TABLE = "OAuth2Application.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OAuth2Application exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OAuth2Application exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"oAuth2ApplicationScopeAliasesId"});

	static {
		try {
			Class.forName(OAuthTwoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}