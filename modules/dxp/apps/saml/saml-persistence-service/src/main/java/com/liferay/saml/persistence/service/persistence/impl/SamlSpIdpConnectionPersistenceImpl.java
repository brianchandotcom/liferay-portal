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

package com.liferay.saml.persistence.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.saml.persistence.exception.NoSuchSpIdpConnectionException;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl;
import com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlSpIdpConnectionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the saml sp idp connection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionPersistence
 * @see com.liferay.saml.persistence.service.persistence.SamlSpIdpConnectionUtil
 * @generated
 */
@ProviderType
public class SamlSpIdpConnectionPersistenceImpl extends BasePersistenceImpl<SamlSpIdpConnection>
	implements SamlSpIdpConnectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SamlSpIdpConnectionUtil} to access the saml sp idp connection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SamlSpIdpConnectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SamlSpIdpConnectionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the saml sp idp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the saml sp idp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @return the range of matching saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp idp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SamlSpIdpConnection> list = null;

		if (retrieveFromCache) {
			list = (List<SamlSpIdpConnection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlSpIdpConnection samlSpIdpConnection : list) {
					if ((companyId != samlSpIdpConnection.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMLSPIDPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SamlSpIdpConnectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SamlSpIdpConnection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpIdpConnection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection findByCompanyId_First(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (samlSpIdpConnection != null) {
			return samlSpIdpConnection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSpIdpConnectionException(msg.toString());
	}

	/**
	 * Returns the first saml sp idp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByCompanyId_First(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		List<SamlSpIdpConnection> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection findByCompanyId_Last(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (samlSpIdpConnection != null) {
			return samlSpIdpConnection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchSpIdpConnectionException(msg.toString());
	}

	/**
	 * Returns the last saml sp idp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByCompanyId_Last(long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SamlSpIdpConnection> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml sp idp connections before and after the current saml sp idp connection in the ordered set where companyId = &#63;.
	 *
	 * @param samlSpIdpConnectionId the primary key of the current saml sp idp connection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection[] findByCompanyId_PrevAndNext(
		long samlSpIdpConnectionId, long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator)
		throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = findByPrimaryKey(samlSpIdpConnectionId);

		Session session = null;

		try {
			session = openSession();

			SamlSpIdpConnection[] array = new SamlSpIdpConnectionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, samlSpIdpConnection,
					companyId, orderByComparator, true);

			array[1] = samlSpIdpConnection;

			array[2] = getByCompanyId_PrevAndNext(session, samlSpIdpConnection,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SamlSpIdpConnection getByCompanyId_PrevAndNext(Session session,
		SamlSpIdpConnection samlSpIdpConnection, long companyId,
		OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLSPIDPCONNECTION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(SamlSpIdpConnectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(samlSpIdpConnection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SamlSpIdpConnection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml sp idp connections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SamlSpIdpConnection samlSpIdpConnection : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(samlSpIdpConnection);
		}
	}

	/**
	 * Returns the number of saml sp idp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching saml sp idp connections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLSPIDPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "samlSpIdpConnection.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_SIEI = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_SIEI",
			new String[] { Long.class.getName(), String.class.getName() },
			SamlSpIdpConnectionModelImpl.COMPANYID_COLUMN_BITMASK |
			SamlSpIdpConnectionModelImpl.SAMLIDPENTITYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SIEI = new FinderPath(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SIEI",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param samlIdpEntityId the saml idp entity ID
	 * @return the matching saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection findByC_SIEI(long companyId,
		String samlIdpEntityId) throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = fetchByC_SIEI(companyId,
				samlIdpEntityId);

		if (samlSpIdpConnection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", samlIdpEntityId=");
			msg.append(samlIdpEntityId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchSpIdpConnectionException(msg.toString());
		}

		return samlSpIdpConnection;
	}

	/**
	 * Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlIdpEntityId the saml idp entity ID
	 * @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByC_SIEI(long companyId,
		String samlIdpEntityId) {
		return fetchByC_SIEI(companyId, samlIdpEntityId, true);
	}

	/**
	 * Returns the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching saml sp idp connection, or <code>null</code> if a matching saml sp idp connection could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByC_SIEI(long companyId,
		String samlIdpEntityId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, samlIdpEntityId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_SIEI,
					finderArgs, this);
		}

		if (result instanceof SamlSpIdpConnection) {
			SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)result;

			if ((companyId != samlSpIdpConnection.getCompanyId()) ||
					!Objects.equals(samlIdpEntityId,
						samlSpIdpConnection.getSamlIdpEntityId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMLSPIDPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_C_SIEI_COMPANYID_2);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId == null) {
				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_1);
			}
			else if (samlIdpEntityId.equals("")) {
				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSamlIdpEntityId) {
					qPos.add(samlIdpEntityId);
				}

				List<SamlSpIdpConnection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_SIEI,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"SamlSpIdpConnectionPersistenceImpl.fetchByC_SIEI(long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlSpIdpConnection samlSpIdpConnection = list.get(0);

					result = samlSpIdpConnection;

					cacheResult(samlSpIdpConnection);

					if ((samlSpIdpConnection.getCompanyId() != companyId) ||
							(samlSpIdpConnection.getSamlIdpEntityId() == null) ||
							!samlSpIdpConnection.getSamlIdpEntityId()
													.equals(samlIdpEntityId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_SIEI,
							finderArgs, samlSpIdpConnection);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SIEI, finderArgs);

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
			return (SamlSpIdpConnection)result;
		}
	}

	/**
	 * Removes the saml sp idp connection where companyId = &#63; and samlIdpEntityId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param samlIdpEntityId the saml idp entity ID
	 * @return the saml sp idp connection that was removed
	 */
	@Override
	public SamlSpIdpConnection removeByC_SIEI(long companyId,
		String samlIdpEntityId) throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = findByC_SIEI(companyId,
				samlIdpEntityId);

		return remove(samlSpIdpConnection);
	}

	/**
	 * Returns the number of saml sp idp connections where companyId = &#63; and samlIdpEntityId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param samlIdpEntityId the saml idp entity ID
	 * @return the number of matching saml sp idp connections
	 */
	@Override
	public int countByC_SIEI(long companyId, String samlIdpEntityId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_SIEI;

		Object[] finderArgs = new Object[] { companyId, samlIdpEntityId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMLSPIDPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_C_SIEI_COMPANYID_2);

			boolean bindSamlIdpEntityId = false;

			if (samlIdpEntityId == null) {
				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_1);
			}
			else if (samlIdpEntityId.equals("")) {
				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_3);
			}
			else {
				bindSamlIdpEntityId = true;

				query.append(_FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSamlIdpEntityId) {
					qPos.add(samlIdpEntityId);
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

	private static final String _FINDER_COLUMN_C_SIEI_COMPANYID_2 = "samlSpIdpConnection.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_1 = "samlSpIdpConnection.samlIdpEntityId IS NULL";
	private static final String _FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_2 = "samlSpIdpConnection.samlIdpEntityId = ?";
	private static final String _FINDER_COLUMN_C_SIEI_SAMLIDPENTITYID_3 = "(samlSpIdpConnection.samlIdpEntityId IS NULL OR samlSpIdpConnection.samlIdpEntityId = '')";

	public SamlSpIdpConnectionPersistenceImpl() {
		setModelClass(SamlSpIdpConnection.class);
	}

	/**
	 * Caches the saml sp idp connection in the entity cache if it is enabled.
	 *
	 * @param samlSpIdpConnection the saml sp idp connection
	 */
	@Override
	public void cacheResult(SamlSpIdpConnection samlSpIdpConnection) {
		entityCache.putResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class, samlSpIdpConnection.getPrimaryKey(),
			samlSpIdpConnection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_SIEI,
			new Object[] {
				samlSpIdpConnection.getCompanyId(),
				samlSpIdpConnection.getSamlIdpEntityId()
			}, samlSpIdpConnection);

		samlSpIdpConnection.resetOriginalValues();
	}

	/**
	 * Caches the saml sp idp connections in the entity cache if it is enabled.
	 *
	 * @param samlSpIdpConnections the saml sp idp connections
	 */
	@Override
	public void cacheResult(List<SamlSpIdpConnection> samlSpIdpConnections) {
		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			if (entityCache.getResult(
						SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
						SamlSpIdpConnectionImpl.class,
						samlSpIdpConnection.getPrimaryKey()) == null) {
				cacheResult(samlSpIdpConnection);
			}
			else {
				samlSpIdpConnection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all saml sp idp connections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlSpIdpConnectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml sp idp connection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlSpIdpConnection samlSpIdpConnection) {
		entityCache.removeResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class, samlSpIdpConnection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SamlSpIdpConnectionModelImpl)samlSpIdpConnection,
			true);
	}

	@Override
	public void clearCache(List<SamlSpIdpConnection> samlSpIdpConnections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlSpIdpConnection samlSpIdpConnection : samlSpIdpConnections) {
			entityCache.removeResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
				SamlSpIdpConnectionImpl.class,
				samlSpIdpConnection.getPrimaryKey());

			clearUniqueFindersCache((SamlSpIdpConnectionModelImpl)samlSpIdpConnection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlSpIdpConnectionModelImpl samlSpIdpConnectionModelImpl) {
		Object[] args = new Object[] {
				samlSpIdpConnectionModelImpl.getCompanyId(),
				samlSpIdpConnectionModelImpl.getSamlIdpEntityId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_SIEI, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_SIEI, args,
			samlSpIdpConnectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SamlSpIdpConnectionModelImpl samlSpIdpConnectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					samlSpIdpConnectionModelImpl.getCompanyId(),
					samlSpIdpConnectionModelImpl.getSamlIdpEntityId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SIEI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SIEI, args);
		}

		if ((samlSpIdpConnectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_SIEI.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					samlSpIdpConnectionModelImpl.getOriginalCompanyId(),
					samlSpIdpConnectionModelImpl.getOriginalSamlIdpEntityId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SIEI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SIEI, args);
		}
	}

	/**
	 * Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	 *
	 * @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	 * @return the new saml sp idp connection
	 */
	@Override
	public SamlSpIdpConnection create(long samlSpIdpConnectionId) {
		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		samlSpIdpConnection.setNew(true);
		samlSpIdpConnection.setPrimaryKey(samlSpIdpConnectionId);

		samlSpIdpConnection.setCompanyId(companyProvider.getCompanyId());

		return samlSpIdpConnection;
	}

	/**
	 * Removes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	 * @return the saml sp idp connection that was removed
	 * @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection remove(long samlSpIdpConnectionId)
		throws NoSuchSpIdpConnectionException {
		return remove((Serializable)samlSpIdpConnectionId);
	}

	/**
	 * Removes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml sp idp connection
	 * @return the saml sp idp connection that was removed
	 * @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection remove(Serializable primaryKey)
		throws NoSuchSpIdpConnectionException {
		Session session = null;

		try {
			session = openSession();

			SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)session.get(SamlSpIdpConnectionImpl.class,
					primaryKey);

			if (samlSpIdpConnection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSpIdpConnectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(samlSpIdpConnection);
		}
		catch (NoSuchSpIdpConnectionException nsee) {
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
	protected SamlSpIdpConnection removeImpl(
		SamlSpIdpConnection samlSpIdpConnection) {
		samlSpIdpConnection = toUnwrappedModel(samlSpIdpConnection);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlSpIdpConnection)) {
				samlSpIdpConnection = (SamlSpIdpConnection)session.get(SamlSpIdpConnectionImpl.class,
						samlSpIdpConnection.getPrimaryKeyObj());
			}

			if (samlSpIdpConnection != null) {
				session.delete(samlSpIdpConnection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (samlSpIdpConnection != null) {
			clearCache(samlSpIdpConnection);
		}

		return samlSpIdpConnection;
	}

	@Override
	public SamlSpIdpConnection updateImpl(
		SamlSpIdpConnection samlSpIdpConnection) {
		samlSpIdpConnection = toUnwrappedModel(samlSpIdpConnection);

		boolean isNew = samlSpIdpConnection.isNew();

		SamlSpIdpConnectionModelImpl samlSpIdpConnectionModelImpl = (SamlSpIdpConnectionModelImpl)samlSpIdpConnection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (samlSpIdpConnection.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlSpIdpConnection.setCreateDate(now);
			}
			else {
				samlSpIdpConnection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!samlSpIdpConnectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlSpIdpConnection.setModifiedDate(now);
			}
			else {
				samlSpIdpConnection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (samlSpIdpConnection.isNew()) {
				session.save(samlSpIdpConnection);

				samlSpIdpConnection.setNew(false);
			}
			else {
				samlSpIdpConnection = (SamlSpIdpConnection)session.merge(samlSpIdpConnection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlSpIdpConnectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					samlSpIdpConnectionModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((samlSpIdpConnectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						samlSpIdpConnectionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { samlSpIdpConnectionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlSpIdpConnectionImpl.class, samlSpIdpConnection.getPrimaryKey(),
			samlSpIdpConnection, false);

		clearUniqueFindersCache(samlSpIdpConnectionModelImpl, false);
		cacheUniqueFindersCache(samlSpIdpConnectionModelImpl);

		samlSpIdpConnection.resetOriginalValues();

		return samlSpIdpConnection;
	}

	protected SamlSpIdpConnection toUnwrappedModel(
		SamlSpIdpConnection samlSpIdpConnection) {
		if (samlSpIdpConnection instanceof SamlSpIdpConnectionImpl) {
			return samlSpIdpConnection;
		}

		SamlSpIdpConnectionImpl samlSpIdpConnectionImpl = new SamlSpIdpConnectionImpl();

		samlSpIdpConnectionImpl.setNew(samlSpIdpConnection.isNew());
		samlSpIdpConnectionImpl.setPrimaryKey(samlSpIdpConnection.getPrimaryKey());

		samlSpIdpConnectionImpl.setSamlSpIdpConnectionId(samlSpIdpConnection.getSamlSpIdpConnectionId());
		samlSpIdpConnectionImpl.setCompanyId(samlSpIdpConnection.getCompanyId());
		samlSpIdpConnectionImpl.setUserId(samlSpIdpConnection.getUserId());
		samlSpIdpConnectionImpl.setUserName(samlSpIdpConnection.getUserName());
		samlSpIdpConnectionImpl.setCreateDate(samlSpIdpConnection.getCreateDate());
		samlSpIdpConnectionImpl.setModifiedDate(samlSpIdpConnection.getModifiedDate());
		samlSpIdpConnectionImpl.setSamlIdpEntityId(samlSpIdpConnection.getSamlIdpEntityId());
		samlSpIdpConnectionImpl.setAssertionSignatureRequired(samlSpIdpConnection.isAssertionSignatureRequired());
		samlSpIdpConnectionImpl.setClockSkew(samlSpIdpConnection.getClockSkew());
		samlSpIdpConnectionImpl.setEnabled(samlSpIdpConnection.isEnabled());
		samlSpIdpConnectionImpl.setForceAuthn(samlSpIdpConnection.isForceAuthn());
		samlSpIdpConnectionImpl.setLdapImportEnabled(samlSpIdpConnection.isLdapImportEnabled());
		samlSpIdpConnectionImpl.setMetadataUrl(samlSpIdpConnection.getMetadataUrl());
		samlSpIdpConnectionImpl.setMetadataXml(samlSpIdpConnection.getMetadataXml());
		samlSpIdpConnectionImpl.setMetadataUpdatedDate(samlSpIdpConnection.getMetadataUpdatedDate());
		samlSpIdpConnectionImpl.setName(samlSpIdpConnection.getName());
		samlSpIdpConnectionImpl.setNameIdFormat(samlSpIdpConnection.getNameIdFormat());
		samlSpIdpConnectionImpl.setSignAuthnRequest(samlSpIdpConnection.isSignAuthnRequest());
		samlSpIdpConnectionImpl.setUserAttributeMappings(samlSpIdpConnection.getUserAttributeMappings());

		return samlSpIdpConnectionImpl;
	}

	/**
	 * Returns the saml sp idp connection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml sp idp connection
	 * @return the saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSpIdpConnectionException {
		SamlSpIdpConnection samlSpIdpConnection = fetchByPrimaryKey(primaryKey);

		if (samlSpIdpConnection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSpIdpConnectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return samlSpIdpConnection;
	}

	/**
	 * Returns the saml sp idp connection with the primary key or throws a {@link NoSuchSpIdpConnectionException} if it could not be found.
	 *
	 * @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	 * @return the saml sp idp connection
	 * @throws NoSuchSpIdpConnectionException if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection findByPrimaryKey(long samlSpIdpConnectionId)
		throws NoSuchSpIdpConnectionException {
		return findByPrimaryKey((Serializable)samlSpIdpConnectionId);
	}

	/**
	 * Returns the saml sp idp connection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml sp idp connection
	 * @return the saml sp idp connection, or <code>null</code> if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
				SamlSpIdpConnectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)serializable;

		if (samlSpIdpConnection == null) {
			Session session = null;

			try {
				session = openSession();

				samlSpIdpConnection = (SamlSpIdpConnection)session.get(SamlSpIdpConnectionImpl.class,
						primaryKey);

				if (samlSpIdpConnection != null) {
					cacheResult(samlSpIdpConnection);
				}
				else {
					entityCache.putResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
						SamlSpIdpConnectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpIdpConnectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return samlSpIdpConnection;
	}

	/**
	 * Returns the saml sp idp connection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	 * @return the saml sp idp connection, or <code>null</code> if a saml sp idp connection with the primary key could not be found
	 */
	@Override
	public SamlSpIdpConnection fetchByPrimaryKey(long samlSpIdpConnectionId) {
		return fetchByPrimaryKey((Serializable)samlSpIdpConnectionId);
	}

	@Override
	public Map<Serializable, SamlSpIdpConnection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SamlSpIdpConnection> map = new HashMap<Serializable, SamlSpIdpConnection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SamlSpIdpConnection samlSpIdpConnection = fetchByPrimaryKey(primaryKey);

			if (samlSpIdpConnection != null) {
				map.put(primaryKey, samlSpIdpConnection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpIdpConnectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SamlSpIdpConnection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SAMLSPIDPCONNECTION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (SamlSpIdpConnection samlSpIdpConnection : (List<SamlSpIdpConnection>)q.list()) {
				map.put(samlSpIdpConnection.getPrimaryKeyObj(),
					samlSpIdpConnection);

				cacheResult(samlSpIdpConnection);

				uncachedPrimaryKeys.remove(samlSpIdpConnection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SamlSpIdpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlSpIdpConnectionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the saml sp idp connections.
	 *
	 * @return the saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml sp idp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @return the range of saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml sp idp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findAll(int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml sp idp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlSpIdpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of saml sp idp connections
	 */
	@Override
	public List<SamlSpIdpConnection> findAll(int start, int end,
		OrderByComparator<SamlSpIdpConnection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SamlSpIdpConnection> list = null;

		if (retrieveFromCache) {
			list = (List<SamlSpIdpConnection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLSPIDPCONNECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLSPIDPCONNECTION;

				if (pagination) {
					sql = sql.concat(SamlSpIdpConnectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlSpIdpConnection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlSpIdpConnection>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the saml sp idp connections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlSpIdpConnection samlSpIdpConnection : findAll()) {
			remove(samlSpIdpConnection);
		}
	}

	/**
	 * Returns the number of saml sp idp connections.
	 *
	 * @return the number of saml sp idp connections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLSPIDPCONNECTION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlSpIdpConnectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml sp idp connection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SamlSpIdpConnectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SAMLSPIDPCONNECTION = "SELECT samlSpIdpConnection FROM SamlSpIdpConnection samlSpIdpConnection";
	private static final String _SQL_SELECT_SAMLSPIDPCONNECTION_WHERE_PKS_IN = "SELECT samlSpIdpConnection FROM SamlSpIdpConnection samlSpIdpConnection WHERE samlSpIdpConnectionId IN (";
	private static final String _SQL_SELECT_SAMLSPIDPCONNECTION_WHERE = "SELECT samlSpIdpConnection FROM SamlSpIdpConnection samlSpIdpConnection WHERE ";
	private static final String _SQL_COUNT_SAMLSPIDPCONNECTION = "SELECT COUNT(samlSpIdpConnection) FROM SamlSpIdpConnection samlSpIdpConnection";
	private static final String _SQL_COUNT_SAMLSPIDPCONNECTION_WHERE = "SELECT COUNT(samlSpIdpConnection) FROM SamlSpIdpConnection samlSpIdpConnection WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "samlSpIdpConnection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SamlSpIdpConnection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SamlSpIdpConnection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SamlSpIdpConnectionPersistenceImpl.class);
}