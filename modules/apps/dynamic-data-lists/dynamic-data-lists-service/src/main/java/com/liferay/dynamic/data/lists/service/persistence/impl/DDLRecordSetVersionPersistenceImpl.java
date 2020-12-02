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
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersionTable;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl;
import com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionPersistence;
import com.liferay.dynamic.data.lists.service.persistence.impl.constants.DDLPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
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
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
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
@Component(
	service = {DDLRecordSetVersionPersistence.class, BasePersistence.class}
)
public class DDLRecordSetVersionPersistenceImpl
	extends BasePersistenceImpl<DDLRecordSetVersion>
	implements DDLRecordSetVersionPersistence {

	/*
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
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append("}");

		throw new NoSuchRecordSetVersionException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append("}");

		throw new NoSuchRecordSetVersionException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

		sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

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
			sb.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(recordSetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddlRecordSetVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecordSetVersion> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RECORDSETID_RECORDSETID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("recordSetId=");
			sb.append(recordSetId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRecordSetVersionException(sb.toString());
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
			result = finderCache.getResult(_finderPathFetchByRS_V, finderArgs);
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
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RS_V_RECORDSETID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_RS_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_RS_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				if (bindVersion) {
					queryPos.add(version);
				}

				List<DDLRecordSetVersion> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RS_V_RECORDSETID_2);

			boolean bindVersion = false;

			if (version.isEmpty()) {
				sb.append(_FINDER_COLUMN_RS_V_VERSION_3);
			}
			else {
				bindVersion = true;

				sb.append(_FINDER_COLUMN_RS_V_VERSION_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				if (bindVersion) {
					queryPos.add(version);
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
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

			sb.append(_FINDER_COLUMN_RS_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				queryPos.add(status);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchRecordSetVersionException(sb.toString());
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

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("recordSetId=");
		sb.append(recordSetId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchRecordSetVersionException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DDLRECORDSETVERSION_WHERE);

		sb.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

		sb.append(_FINDER_COLUMN_RS_S_STATUS_2);

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
			sb.append(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(recordSetId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddlRecordSetVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDLRecordSetVersion> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDLRECORDSETVERSION_WHERE);

			sb.append(_FINDER_COLUMN_RS_S_RECORDSETID_2);

			sb.append(_FINDER_COLUMN_RS_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(recordSetId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_RS_S_RECORDSETID_2 =
		"ddlRecordSetVersion.recordSetId = ? AND ";

	private static final String _FINDER_COLUMN_RS_S_STATUS_2 =
		"ddlRecordSetVersion.status = ?";

	public DDLRecordSetVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDLRecordSetVersion.class);

		setModelImplClass(DDLRecordSetVersionImpl.class);
		setModelPKClass(long.class);

		setTable(DDLRecordSetVersionTable.INSTANCE);
	}

	/**
	 * Caches the ddl record set version in the entity cache if it is enabled.
	 *
	 * @param ddlRecordSetVersion the ddl record set version
	 */
	@Override
	public void cacheResult(DDLRecordSetVersion ddlRecordSetVersion) {
		entityCache.putResult(
			DDLRecordSetVersionImpl.class, ddlRecordSetVersion.getPrimaryKey(),
			ddlRecordSetVersion);

		finderCache.putResult(
			_finderPathFetchByRS_V,
			new Object[] {
				ddlRecordSetVersion.getRecordSetId(),
				ddlRecordSetVersion.getVersion()
			},
			ddlRecordSetVersion);
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
					DDLRecordSetVersionImpl.class,
					ddlRecordSetVersion.getPrimaryKey()) == null) {

				cacheResult(ddlRecordSetVersion);
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

		finderCache.clearCache(DDLRecordSetVersionImpl.class);
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
			DDLRecordSetVersionImpl.class, ddlRecordSetVersion);
	}

	@Override
	public void clearCache(List<DDLRecordSetVersion> ddlRecordSetVersions) {
		for (DDLRecordSetVersion ddlRecordSetVersion : ddlRecordSetVersions) {
			entityCache.removeResult(
				DDLRecordSetVersionImpl.class, ddlRecordSetVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DDLRecordSetVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DDLRecordSetVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl) {

		Object[] args = new Object[] {
			ddlRecordSetVersionModelImpl.getRecordSetId(),
			ddlRecordSetVersionModelImpl.getVersion()
		};

		finderCache.putResult(_finderPathCountByRS_V, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByRS_V, args, ddlRecordSetVersionModelImpl);
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
		catch (NoSuchRecordSetVersionException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

			if (isNew) {
				session.save(ddlRecordSetVersion);
			}
			else {
				ddlRecordSetVersion = (DDLRecordSetVersion)session.merge(
					ddlRecordSetVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DDLRecordSetVersionImpl.class, ddlRecordSetVersionModelImpl, false,
			true);

		cacheUniqueFindersCache(ddlRecordSetVersionModelImpl);

		if (isNew) {
			ddlRecordSetVersion.setNew(false);
		}

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
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDLRECORDSETVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDLRECORDSETVERSION;

				sql = sql.concat(DDLRecordSetVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDLRecordSetVersion>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DDLRECORDSETVERSION);

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
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new DDLRecordSetVersionModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRecordSetId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"recordSetId"}, true);

		_finderPathWithoutPaginationFindByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			true);

		_finderPathCountByRecordSetId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRecordSetId",
			new String[] {Long.class.getName()}, new String[] {"recordSetId"},
			false);

		_finderPathFetchByRS_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByRS_V",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "version"}, true);

		_finderPathCountByRS_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRS_V",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"recordSetId", "version"}, false);

		_finderPathWithPaginationFindByRS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRS_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"recordSetId", "status"}, true);

		_finderPathWithoutPaginationFindByRS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRS_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"recordSetId", "status"}, true);

		_finderPathCountByRS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRS_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"recordSetId", "status"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDLRecordSetVersionImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = DDLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
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

	private BundleContext _bundleContext;

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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class DDLRecordSetVersionModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl =
				(DDLRecordSetVersionModelImpl)baseModel;

			long columnBitmask =
				ddlRecordSetVersionModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					ddlRecordSetVersionModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						ddlRecordSetVersionModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					ddlRecordSetVersionModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return DDLRecordSetVersionImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return DDLRecordSetVersionTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			DDLRecordSetVersionModelImpl ddlRecordSetVersionModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						ddlRecordSetVersionModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = ddlRecordSetVersionModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}