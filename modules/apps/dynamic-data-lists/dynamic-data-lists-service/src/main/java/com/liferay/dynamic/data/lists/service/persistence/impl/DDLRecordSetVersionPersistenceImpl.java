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

package com.liferay.dynamic.data.lists.service.persistence.impl;

import com.liferay.dynamic.data.lists.exception.NoSuchRecordSetVersionException;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionPersistence;
import com.liferay.dynamic.data.lists.service.persistence.impl.constants.DDLPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the ddl record set version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDLRecordSetVersionPersistence.class)
public class DDLRecordSetVersionPersistenceImpl
	extends BasePersistenceImpl<DDLRecordSetVersion>
	implements DDLRecordSetVersionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDLRecordSetVersionUtil</code> to access the ddl record set version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDLRecordSetVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRecordSetId;
	private FinderPath _finderPathWithoutPaginationFindByRecordSetId;
	private FinderPath _finderPathCountByRecordSetId;

	/**
	 * Returns all the ddl record set versions where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRecordSetId(long recordSetId) {
		return findByRecordSetId(
			recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record set versions where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @return the range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end) {

		return findByRecordSetId(recordSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		return findByRecordSetId(
			recordSetId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions where recordSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRecordSetId(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRecordSetId;
				finderArgs = new Object[] {recordSetId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRecordSetId;
			finderArgs = new Object[] {
				recordSetId, start, end, orderByComparator
			};
		}

		List<DDLRecordSetVersion> list = null;

		if (useFinderCache) {
			list = (List<DDLRecordSetVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordSetVersion ddlRecordSetVersion : list) {
					if (recordSetId != ddlRecordSetVersion.getRecordSetId()) {
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

			query.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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
	 * Returns the first ddl record set version in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set version
	 * @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion findByRecordSetId_First(
			long recordSetId,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByRecordSetId_First(
			recordSetId, orderByComparator);

		if (ddlRecordSetVersion != null) {
			return ddlRecordSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append("}");

		throw new NoSuchRecordSetVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record set version in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRecordSetId_First(
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		List<DDLRecordSetVersion> list = findByRecordSetId(
			recordSetId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record set version in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set version
	 * @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion findByRecordSetId_Last(
			long recordSetId,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByRecordSetId_Last(
			recordSetId, orderByComparator);

		if (ddlRecordSetVersion != null) {
			return ddlRecordSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append("}");

		throw new NoSuchRecordSetVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record set version in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRecordSetId_Last(
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		int count = countByRecordSetId(recordSetId);

		if (count == 0) {
			return null;
		}

		List<DDLRecordSetVersion> list = findByRecordSetId(
			recordSetId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record set versions before and after the current ddl record set version in the ordered set where recordSetId = &#63;.
	 *
	 * @param recordSetVersionId the primary key of the current ddl record set version
	 * @param recordSetId the record set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set version
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion[] findByRecordSetId_PrevAndNext(
			long recordSetVersionId, long recordSetId,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = findByPrimaryKey(
			recordSetVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordSetVersion[] array = new DDLRecordSetVersionImpl[3];

			array[0] = getByRecordSetId_PrevAndNext(
				session, ddlRecordSetVersion, recordSetId, orderByComparator,
				true);

			array[1] = ddlRecordSetVersion;

			array[2] = getByRecordSetId_PrevAndNext(
				session, ddlRecordSetVersion, recordSetId, orderByComparator,
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

	protected DDLRecordSetVersion getByRecordSetId_PrevAndNext(
		Session session, DDLRecordSetVersion ddlRecordSetVersion,
		long recordSetId,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
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

		query.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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
			query.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordSetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddlRecordSetVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DDLRecordSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record set versions where recordSetId = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 */
	@Override
	public void removeByRecordSetId(long recordSetId) {
		for (DDLRecordSetVersion ddlRecordSetVersion :
				findByRecordSetId(
					recordSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddlRecordSetVersion);
		}
	}

	/**
	 * Returns the number of ddl record set versions where recordSetId = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @return the number of matching ddl record set versions
	 */
	@Override
	public int countByRecordSetId(long recordSetId) {
		FinderPath finderPath = _finderPathCountByRecordSetId;

		Object[] finderArgs = new Object[] {recordSetId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

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

	private static final String _FINDER_COLUMN_RECORDSETID_RECORDSETID_2 =
		"ddlRecordSetVersion.recordSetId = ?";

	private FinderPath _finderPathFetchByRS_V;
	private FinderPath _finderPathCountByRS_V;

	/**
	 * Returns the ddl record set version where recordSetId = &#63; and version = &#63; or throws a <code>NoSuchRecordSetVersionException</code> if it could not be found.
	 *
	 * @param recordSetId the record set ID
	 * @param version the version
	 * @return the matching ddl record set version
	 * @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion findByRS_V(long recordSetId, String version)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByRS_V(
			recordSetId, version);

		if (ddlRecordSetVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("recordSetId=");
			msg.append(recordSetId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchRecordSetVersionException(msg.toString());
		}

		return ddlRecordSetVersion;
	}

	/**
	 * Returns the ddl record set version where recordSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param recordSetId the record set ID
	 * @param version the version
	 * @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRS_V(long recordSetId, String version) {
		return fetchByRS_V(recordSetId, version, true);
	}

	/**
	 * Returns the ddl record set version where recordSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param recordSetId the record set ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRS_V(
		long recordSetId, String version, boolean useFinderCache) {

		version = Objects.toString(version, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {recordSetId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByRS_V, finderArgs, this);
		}

		if (result instanceof DDLRecordSetVersion) {
			DDLRecordSetVersion ddlRecordSetVersion =
				(DDLRecordSetVersion)result;

			if ((recordSetId != ddlRecordSetVersion.getRecordSetId()) ||
				!Objects.equals(version, ddlRecordSetVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RS_V_RECORDSETID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_RS_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_RS_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDLRecordSetVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByRS_V, finderArgs, list);
					}
				}
				else {
					DDLRecordSetVersion ddlRecordSetVersion = list.get(0);

					result = ddlRecordSetVersion;

					cacheResult(ddlRecordSetVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByRS_V, finderArgs);
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
			return (DDLRecordSetVersion)result;
		}
	}

	/**
	 * Removes the ddl record set version where recordSetId = &#63; and version = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param version the version
	 * @return the ddl record set version that was removed
	 */
	@Override
	public DDLRecordSetVersion removeByRS_V(long recordSetId, String version)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = findByRS_V(
			recordSetId, version);

		return remove(ddlRecordSetVersion);
	}

	/**
	 * Returns the number of ddl record set versions where recordSetId = &#63; and version = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param version the version
	 * @return the number of matching ddl record set versions
	 */
	@Override
	public int countByRS_V(long recordSetId, String version) {
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByRS_V;

		Object[] finderArgs = new Object[] {recordSetId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RS_V_RECORDSETID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_RS_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_RS_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				if (bindVersion) {
					qPos.add(version);
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

	private static final String _FINDER_COLUMN_RS_V_RECORDSETID_2 =
		"ddlRecordSetVersion.recordSetId = ? AND ";

	private static final String _FINDER_COLUMN_RS_V_VERSION_2 =
		"ddlRecordSetVersion.version = ?";

	private static final String _FINDER_COLUMN_RS_V_VERSION_3 =
		"(ddlRecordSetVersion.version IS NULL OR ddlRecordSetVersion.version = '')";

	private FinderPath _finderPathWithPaginationFindByRS_S;
	private FinderPath _finderPathWithoutPaginationFindByRS_S;
	private FinderPath _finderPathCountByRS_S;

	/**
	 * Returns all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @return the matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRS_S(long recordSetId, int status) {
		return findByRS_S(
			recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @return the range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRS_S(
		long recordSetId, int status, int start, int end) {

		return findByRS_S(recordSetId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRS_S(
		long recordSetId, int status, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		return findByRS_S(
			recordSetId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions where recordSetId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findByRS_S(
		long recordSetId, int status, int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRS_S;
				finderArgs = new Object[] {recordSetId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRS_S;
			finderArgs = new Object[] {
				recordSetId, status, start, end, orderByComparator
			};
		}

		List<DDLRecordSetVersion> list = null;

		if (useFinderCache) {
			list = (List<DDLRecordSetVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDLRecordSetVersion ddlRecordSetVersion : list) {
					if ((recordSetId != ddlRecordSetVersion.getRecordSetId()) ||
						(status != ddlRecordSetVersion.getStatus())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

			query.append(_FINDER_COLUMN_RS_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				qPos.add(status);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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
	 * Returns the first ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set version
	 * @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion findByRS_S_First(
			long recordSetId, int status,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByRS_S_First(
			recordSetId, status, orderByComparator);

		if (ddlRecordSetVersion != null) {
			return ddlRecordSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchRecordSetVersionException(msg.toString());
	}

	/**
	 * Returns the first ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRS_S_First(
		long recordSetId, int status,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		List<DDLRecordSetVersion> list = findByRS_S(
			recordSetId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set version
	 * @throws NoSuchRecordSetVersionException if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion findByRS_S_Last(
			long recordSetId, int status,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByRS_S_Last(
			recordSetId, status, orderByComparator);

		if (ddlRecordSetVersion != null) {
			return ddlRecordSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("recordSetId=");
		msg.append(recordSetId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchRecordSetVersionException(msg.toString());
	}

	/**
	 * Returns the last ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddl record set version, or <code>null</code> if a matching ddl record set version could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByRS_S_Last(
		long recordSetId, int status,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		int count = countByRS_S(recordSetId, status);

		if (count == 0) {
			return null;
		}

		List<DDLRecordSetVersion> list = findByRS_S(
			recordSetId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddl record set versions before and after the current ddl record set version in the ordered set where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetVersionId the primary key of the current ddl record set version
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddl record set version
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion[] findByRS_S_PrevAndNext(
			long recordSetVersionId, long recordSetId, int status,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = findByPrimaryKey(
			recordSetVersionId);

		Session session = null;

		try {
			session = openSession();

			DDLRecordSetVersion[] array = new DDLRecordSetVersionImpl[3];

			array[0] = getByRS_S_PrevAndNext(
				session, ddlRecordSetVersion, recordSetId, status,
				orderByComparator, true);

			array[1] = ddlRecordSetVersion;

			array[2] = getByRS_S_PrevAndNext(
				session, ddlRecordSetVersion, recordSetId, status,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDLRecordSetVersion getByRS_S_PrevAndNext(
		Session session, DDLRecordSetVersion ddlRecordSetVersion,
		long recordSetId, int status,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
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

		query.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

		query.append(_FINDER_COLUMN_RS_S_STATUS_2);

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
			query.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(recordSetId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddlRecordSetVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DDLRecordSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddl record set versions where recordSetId = &#63; and status = &#63; from the database.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 */
	@Override
	public void removeByRS_S(long recordSetId, int status) {
		for (DDLRecordSetVersion ddlRecordSetVersion :
				findByRS_S(
					recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddlRecordSetVersion);
		}
	}

	/**
	 * Returns the number of ddl record set versions where recordSetId = &#63; and status = &#63;.
	 *
	 * @param recordSetId the record set ID
	 * @param status the status
	 * @return the number of matching ddl record set versions
	 */
	@Override
	public int countByRS_S(long recordSetId, int status) {
		FinderPath finderPath = _finderPathCountByRS_S;

		Object[] finderArgs = new Object[] {recordSetId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

			query.append(_FINDER_COLUMN_RS_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(recordSetId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_RS_S_RECORDSETID_2 =
		"ddlRecordSetVersion.recordSetId = ? AND ";

	private static final String _FINDER_COLUMN_RS_S_STATUS_2 =
		"ddlRecordSetVersion.status = ?";

	public DDLRecordSetVersionPersistenceImpl() {
		setModelClass(DDLRecordSetVersion.class);

		setModelImplClass(DDLRecordSetVersionImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the ddl record set version in the entity cache if it is enabled.
	 *
	 * @param ddlRecordSetVersion the ddl record set version
	 */
	@Override
	public void cacheResult(DDLRecordSetVersion ddlRecordSetVersion) {
		entityCache.putResult(
			entityCacheEnabled, DDLRecordSetVersionImpl.class,
			ddlRecordSetVersion.getPrimaryKey(), ddlRecordSetVersion);

		finderCache.putResult(
			_finderPathFetchByRS_V,
			new Object[] {
				ddlRecordSetVersion.getRecordSetId(),
				ddlRecordSetVersion.getVersion()
			},
			ddlRecordSetVersion);

		ddlRecordSetVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddl record set versions in the entity cache if it is enabled.
	 *
	 * @param ddlRecordSetVersions the ddl record set versions
	 */
	@Override
	public void cacheResult(List<DDLRecordSetVersion> ddlRecordSetVersions) {
		for (DDLRecordSetVersion ddlRecordSetVersion : ddlRecordSetVersions) {
			if (entityCache.getResult(
					entityCacheEnabled, DDLRecordSetVersionImpl.class,
					ddlRecordSetVersion.getPrimaryKey()) == null) {

				cacheResult(ddlRecordSetVersion);
			}
			else {
				ddlRecordSetVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddl record set versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDLRecordSetVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddl record set version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDLRecordSetVersion ddlRecordSetVersion) {
		entityCache.removeResult(
			entityCacheEnabled, DDLRecordSetVersionImpl.class,
			ddlRecordSetVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDLRecordSetVersionModelImpl)ddlRecordSetVersion, true);
	}

	@Override
	public void clearCache(List<DDLRecordSetVersion> ddlRecordSetVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDLRecordSetVersion ddlRecordSetVersion : ddlRecordSetVersions) {
			entityCache.removeResult(
				entityCacheEnabled, DDLRecordSetVersionImpl.class,
				ddlRecordSetVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(DDLRecordSetVersionModelImpl)ddlRecordSetVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, DDLRecordSetVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl) {

		Object[] args = new Object[] {
			ddlRecordSetVersionModelImpl.getRecordSetId(),
			ddlRecordSetVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByRS_V, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByRS_V, args, ddlRecordSetVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddlRecordSetVersionModelImpl.getRecordSetId(),
				ddlRecordSetVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByRS_V, args);
			finderCache.removeResult(_finderPathFetchByRS_V, args);
		}

		if ((ddlRecordSetVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByRS_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddlRecordSetVersionModelImpl.getOriginalRecordSetId(),
				ddlRecordSetVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByRS_V, args);
			finderCache.removeResult(_finderPathFetchByRS_V, args);
		}
	}

	/**
	 * Creates a new ddl record set version with the primary key. Does not add the ddl record set version to the database.
	 *
	 * @param recordSetVersionId the primary key for the new ddl record set version
	 * @return the new ddl record set version
	 */
	@Override
	public DDLRecordSetVersion create(long recordSetVersionId) {
		DDLRecordSetVersion ddlRecordSetVersion = new DDLRecordSetVersionImpl();

		ddlRecordSetVersion.setNew(true);
		ddlRecordSetVersion.setPrimaryKey(recordSetVersionId);

		ddlRecordSetVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddlRecordSetVersion;
	}

	/**
	 * Removes the ddl record set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordSetVersionId the primary key of the ddl record set version
	 * @return the ddl record set version that was removed
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion remove(long recordSetVersionId)
		throws NoSuchRecordSetVersionException {

		return remove((Serializable)recordSetVersionId);
	}

	/**
	 * Removes the ddl record set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddl record set version
	 * @return the ddl record set version that was removed
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion remove(Serializable primaryKey)
		throws NoSuchRecordSetVersionException {

		Session session = null;

		try {
			session = openSession();

			DDLRecordSetVersion ddlRecordSetVersion =
				(DDLRecordSetVersion)session.get(
					DDLRecordSetVersionImpl.class, primaryKey);

			if (ddlRecordSetVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRecordSetVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddlRecordSetVersion);
		}
		catch (NoSuchRecordSetVersionException nsee) {
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
	protected DDLRecordSetVersion removeImpl(
		DDLRecordSetVersion ddlRecordSetVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddlRecordSetVersion)) {
				ddlRecordSetVersion = (DDLRecordSetVersion)session.get(
					DDLRecordSetVersionImpl.class,
					ddlRecordSetVersion.getPrimaryKeyObj());
			}

			if (ddlRecordSetVersion != null) {
				session.delete(ddlRecordSetVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddlRecordSetVersion != null) {
			clearCache(ddlRecordSetVersion);
		}

		return ddlRecordSetVersion;
	}

	@Override
	public DDLRecordSetVersion updateImpl(
		DDLRecordSetVersion ddlRecordSetVersion) {

		boolean isNew = ddlRecordSetVersion.isNew();

		if (!(ddlRecordSetVersion instanceof DDLRecordSetVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddlRecordSetVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddlRecordSetVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddlRecordSetVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDLRecordSetVersion implementation " +
					ddlRecordSetVersion.getClass());
		}

		DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl =
			(DDLRecordSetVersionModelImpl)ddlRecordSetVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddlRecordSetVersion.isNew()) {
				session.save(ddlRecordSetVersion);

				ddlRecordSetVersion.setNew(false);
			}
			else {
				ddlRecordSetVersion = (DDLRecordSetVersion)session.merge(
					ddlRecordSetVersion);
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
				ddlRecordSetVersionModelImpl.getRecordSetId()
			};

			finderCache.removeResult(_finderPathCountByRecordSetId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByRecordSetId, args);

			args = new Object[] {
				ddlRecordSetVersionModelImpl.getRecordSetId(),
				ddlRecordSetVersionModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByRS_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByRS_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddlRecordSetVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRecordSetId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddlRecordSetVersionModelImpl.getOriginalRecordSetId()
				};

				finderCache.removeResult(_finderPathCountByRecordSetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRecordSetId, args);

				args = new Object[] {
					ddlRecordSetVersionModelImpl.getRecordSetId()
				};

				finderCache.removeResult(_finderPathCountByRecordSetId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRecordSetId, args);
			}

			if ((ddlRecordSetVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRS_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddlRecordSetVersionModelImpl.getOriginalRecordSetId(),
					ddlRecordSetVersionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByRS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRS_S, args);

				args = new Object[] {
					ddlRecordSetVersionModelImpl.getRecordSetId(),
					ddlRecordSetVersionModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByRS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRS_S, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DDLRecordSetVersionImpl.class,
			ddlRecordSetVersion.getPrimaryKey(), ddlRecordSetVersion, false);

		clearUniqueFindersCache(ddlRecordSetVersionModelImpl, false);
		cacheUniqueFindersCache(ddlRecordSetVersionModelImpl);

		ddlRecordSetVersion.resetOriginalValues();

		return ddlRecordSetVersion;
	}

	/**
	 * Returns the ddl record set version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddl record set version
	 * @return the ddl record set version
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRecordSetVersionException {

		DDLRecordSetVersion ddlRecordSetVersion = fetchByPrimaryKey(primaryKey);

		if (ddlRecordSetVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRecordSetVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddlRecordSetVersion;
	}

	/**
	 * Returns the ddl record set version with the primary key or throws a <code>NoSuchRecordSetVersionException</code> if it could not be found.
	 *
	 * @param recordSetVersionId the primary key of the ddl record set version
	 * @return the ddl record set version
	 * @throws NoSuchRecordSetVersionException if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion findByPrimaryKey(long recordSetVersionId)
		throws NoSuchRecordSetVersionException {

		return findByPrimaryKey((Serializable)recordSetVersionId);
	}

	/**
	 * Returns the ddl record set version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordSetVersionId the primary key of the ddl record set version
	 * @return the ddl record set version, or <code>null</code> if a ddl record set version with the primary key could not be found
	 */
	@Override
	public DDLRecordSetVersion fetchByPrimaryKey(long recordSetVersionId) {
		return fetchByPrimaryKey((Serializable)recordSetVersionId);
	}

	/**
	 * Returns all the ddl record set versions.
	 *
	 * @return the ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddl record set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @return the range of ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findAll(
		int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddl record set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDLRecordSetVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl record set versions
	 * @param end the upper bound of the range of ddl record set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddl record set versions
	 */
	@Override
	public List<DDLRecordSetVersion> findAll(
		int start, int end,
		OrderByComparator<DDLRecordSetVersion> orderByComparator,
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

		List<DDLRecordSetVersion> list = null;

		if (useFinderCache) {
			list = (List<DDLRecordSetVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDLRECORDSETVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORDSETVERSION;

				sql = sql.concat(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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
	 * Removes all the ddl record set versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDLRecordSetVersion ddlRecordSetVersion : findAll()) {
			remove(ddlRecordSetVersion);
		}
	}

	/**
	 * Returns the number of ddl record set versions.
	 *
	 * @return the number of ddl record set versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDLRECORDSETVERSION);

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
		return "recordSetVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDLRECORDSETVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DDLRecordSetVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ddl record set version persistence.
	 */
	@Activate
	public void activate() {
		DDLRecordSetVersionModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DDLRecordSetVersionModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByRecordSetId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRecordSetId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] {Long.class.getName()},
			DDLRecordSetVersionModelImpl.RECORDSETID_COLUMN_BITMASK);

		_finderPathCountByRecordSetId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] {Long.class.getName()});

		_finderPathFetchByRS_V = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByRS_V",
			new String[] {Long.class.getName(), String.class.getName()},
			DDLRecordSetVersionModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByRS_V = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRS_V",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByRS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRS_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DDLRecordSetVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRS_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			DDLRecordSetVersionModelImpl.RECORDSETID_COLUMN_BITMASK |
			DDLRecordSetVersionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByRS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRS_S",
			new String[] {Long.class.getName(), Integer.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDLRecordSetVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.dynamic.data.lists.model.DDLRecordSetVersion"),
			true);
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDLRECORDSETVERSION =
		"SELECT ddlRecordSetVersion FROM DDLRecordSetVersion ddlRecordSetVersion";

	private static final String _SQL_SELECT_DDLRECORDSETVERSION_WHERE =
		"SELECT ddlRecordSetVersion FROM DDLRecordSetVersion ddlRecordSetVersion WHERE ";

	private static final String _SQL_COUNT_DDLRECORDSETVERSION =
		"SELECT COUNT(ddlRecordSetVersion) FROM DDLRecordSetVersion ddlRecordSetVersion";

	private static final String _SQL_COUNT_DDLRECORDSETVERSION_WHERE =
		"SELECT COUNT(ddlRecordSetVersion) FROM DDLRecordSetVersion ddlRecordSetVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddlRecordSetVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDLRecordSetVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDLRecordSetVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordSetVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"settings"});

	static {
		try {
			Class.forName(DDLPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}