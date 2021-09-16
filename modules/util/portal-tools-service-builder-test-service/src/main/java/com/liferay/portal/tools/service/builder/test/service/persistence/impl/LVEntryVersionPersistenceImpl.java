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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersionTable;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationVersionPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryVersionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lv entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryVersionPersistenceImpl
	extends BasePersistenceImpl<LVEntryVersion>
	implements LVEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LVEntryVersionUtil</code> to access the lv entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LVEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLvEntryId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId;
	private FinderPath _finderPathCountByLvEntryId;

	/**
	 * Returns all the lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(long lvEntryId) {
		return findByLvEntryId(
			lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return findByLvEntryId(lvEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByLvEntryId(lvEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByLvEntryId;
				finderArgs = new Object[] {lvEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLvEntryId;
			finderArgs = new Object[] {
				lvEntryId, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (lvEntryId != lvEntryVersion.getLvEntryId()) {
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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_First(
			long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByLvEntryId_First(
			lvEntryId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lvEntryId=");
		sb.append(lvEntryId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_First(
		long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByLvEntryId(
			lvEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_Last(
			long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByLvEntryId_Last(
			lvEntryId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("lvEntryId=");
		sb.append(lvEntryId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Last(
		long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByLvEntryId(lvEntryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByLvEntryId(
			lvEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByLvEntryId_PrevAndNext(
			long lvEntryVersionId, long lvEntryId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByLvEntryId_PrevAndNext(
				session, lvEntryVersion, lvEntryId, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByLvEntryId_PrevAndNext(
				session, lvEntryVersion, lvEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByLvEntryId_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(lvEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	@Override
	public void removeByLvEntryId(long lvEntryId) {
		for (LVEntryVersion lvEntryVersion :
				findByLvEntryId(
					lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByLvEntryId(long lvEntryId) {
		FinderPath finderPath = _finderPathCountByLvEntryId;

		Object[] finderArgs = new Object[] {lvEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

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

	private static final String _FINDER_COLUMN_LVENTRYID_LVENTRYID_2 =
		"lvEntryVersion.lvEntryId = ?";

	private FinderPath _finderPathFetchByLvEntryId_Version;
	private FinderPath _finderPathCountByLvEntryId_Version;

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByLvEntryId_Version(
			lvEntryId, version);

		if (lvEntryVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("lvEntryId=");
			sb.append(lvEntryId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLVEntryVersionException(sb.toString());
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Version(
		long lvEntryId, int version) {

		return fetchByLvEntryId_Version(lvEntryId, version, true);
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Version(
		long lvEntryId, int version, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {lvEntryId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLvEntryId_Version, finderArgs);
		}

		if (result instanceof LVEntryVersion) {
			LVEntryVersion lvEntryVersion = (LVEntryVersion)result;

			if ((lvEntryId != lvEntryVersion.getLvEntryId()) ||
				(version != lvEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			sb.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				queryPos.add(version);

				List<LVEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLvEntryId_Version, finderArgs,
							list);
					}
				}
				else {
					LVEntryVersion lvEntryVersion = list.get(0);

					result = lvEntryVersion;

					cacheResult(lvEntryVersion);
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
			return (LVEntryVersion)result;
		}
	}

	/**
	 * Removes the lv entry version where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	@Override
	public LVEntryVersion removeByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByLvEntryId_Version(
			lvEntryId, version);

		return remove(lvEntryVersion);
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByLvEntryId_Version(long lvEntryId, int version) {
		FinderPath finderPath = _finderPathCountByLvEntryId_Version;

		Object[] finderArgs = new Object[] {lvEntryId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			sb.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(lvEntryId);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2 =
		"lvEntryVersion.lvEntryId = ? AND ";

	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the lv entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (!uuid.equals(lvEntryVersion.getUuid())) {
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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_First(
			String uuid, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_First(
		String uuid, OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_Last(
			String uuid, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_Last(
		String uuid, OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByUuid_PrevAndNext(
			long lvEntryVersionId, String uuid,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uuid = Objects.toString(uuid, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, lvEntryVersion, uuid, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByUuid_PrevAndNext(
				session, lvEntryVersion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByUuid_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, String uuid,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LVEntryVersion lvEntryVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"lvEntryVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;

	/**
	 * Returns all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_Version(String uuid, int version) {
		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Version;
				finderArgs = new Object[] {uuid, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_Version;
			finderArgs = new Object[] {
				uuid, version, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (!uuid.equals(lvEntryVersion.getUuid()) ||
						(version != lvEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(version);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_Version_First(
			uuid, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByUuid_Version(
			uuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_Version_Last(
			uuid, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByUuid_Version(uuid, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByUuid_Version(
			uuid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByUuid_Version_PrevAndNext(
			long lvEntryVersionId, String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uuid = Objects.toString(uuid, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByUuid_Version_PrevAndNext(
				session, lvEntryVersion, uuid, version, orderByComparator,
				true);

			array[1] = lvEntryVersion;

			array[2] = getByUuid_Version_PrevAndNext(
				session, lvEntryVersion, uuid, version, orderByComparator,
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

	protected LVEntryVersion getByUuid_Version_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, String uuid,
		int version, OrderByComparator<LVEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		for (LVEntryVersion lvEntryVersion :
				findByUuid_Version(
					uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_Version;

		Object[] finderArgs = new Object[] {uuid, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_2 =
		"lvEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (!uuid.equals(lvEntryVersion.getUuid()) ||
						(groupId != lvEntryVersion.getGroupId())) {

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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByUUID_G_PrevAndNext(
			long lvEntryVersionId, String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uuid = Objects.toString(uuid, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, lvEntryVersion, uuid, groupId, orderByComparator,
				true);

			array[1] = lvEntryVersion;

			array[2] = getByUUID_G_PrevAndNext(
				session, lvEntryVersion, uuid, groupId, orderByComparator,
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

	protected LVEntryVersion getByUUID_G_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, String uuid,
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (LVEntryVersion lvEntryVersion :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"lvEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"lvEntryVersion.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Version;
	private FinderPath _finderPathCountByUUID_G_Version;

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUUID_G_Version(
			uuid, groupId, version);

		if (lvEntryVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLVEntryVersionException(sb.toString());
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return fetchByUUID_G_Version(uuid, groupId, version, true);
	}

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Version, finderArgs);
		}

		if (result instanceof LVEntryVersion) {
			LVEntryVersion lvEntryVersion = (LVEntryVersion)result;

			if (!Objects.equals(uuid, lvEntryVersion.getUuid()) ||
				(groupId != lvEntryVersion.getGroupId()) ||
				(version != lvEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(version);

				List<LVEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Version, finderArgs, list);
					}
				}
				else {
					LVEntryVersion lvEntryVersion = list.get(0);

					result = lvEntryVersion;

					cacheResult(lvEntryVersion);
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
			return (LVEntryVersion)result;
		}
	}

	/**
	 * Removes the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	@Override
	public LVEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByUUID_G_Version(
			uuid, groupId, version);

		return remove(lvEntryVersion);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUUID_G_Version(String uuid, long groupId, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G_Version;

		Object[] finderArgs = new Object[] {uuid, groupId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_2 =
		"lvEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_GROUPID_2 =
		"lvEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (!uuid.equals(lvEntryVersion.getUuid()) ||
						(companyId != lvEntryVersion.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByUuid_C_PrevAndNext(
			long lvEntryVersionId, String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uuid = Objects.toString(uuid, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, lvEntryVersion, uuid, companyId, orderByComparator,
				true);

			array[1] = lvEntryVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, lvEntryVersion, uuid, companyId, orderByComparator,
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

	protected LVEntryVersion getByUuid_C_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, String uuid,
		long companyId, OrderByComparator<LVEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LVEntryVersion lvEntryVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"lvEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"lvEntryVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;

	/**
	 * Returns all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Version;
				finderArgs = new Object[] {uuid, companyId, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Version;
			finderArgs = new Object[] {
				uuid, companyId, version, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (!uuid.equals(lvEntryVersion.getUuid()) ||
						(companyId != lvEntryVersion.getCompanyId()) ||
						(version != lvEntryVersion.getVersion())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				queryPos.add(version);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByUuid_C_Version(uuid, companyId, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByUuid_C_Version(
			uuid, companyId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long lvEntryVersionId, String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uuid = Objects.toString(uuid, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByUuid_C_Version_PrevAndNext(
				session, lvEntryVersion, uuid, companyId, version,
				orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByUuid_C_Version_PrevAndNext(
				session, lvEntryVersion, uuid, companyId, version,
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

	protected LVEntryVersion getByUuid_C_Version_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, String uuid,
		long companyId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

		sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		for (LVEntryVersion lvEntryVersion :
				findByUuid_C_Version(
					uuid, companyId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C_Version;

		Object[] finderArgs = new Object[] {uuid, companyId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_2 =
		"lvEntryVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_3 =
		"(lvEntryVersion.uuid IS NULL OR lvEntryVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2 =
		"lvEntryVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if (groupId != lvEntryVersion.getGroupId()) {
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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_First(
			long groupId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_First(
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Last(
			long groupId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Last(
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByGroupId_PrevAndNext(
			long lvEntryVersionId, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, lvEntryVersion, groupId, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByGroupId_PrevAndNext(
				session, lvEntryVersion, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByGroupId_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LVEntryVersion lvEntryVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"lvEntryVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Version;
				finderArgs = new Object[] {groupId, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId_Version;
			finderArgs = new Object[] {
				groupId, version, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if ((groupId != lvEntryVersion.getGroupId()) ||
						(version != lvEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByGroupId_Version_First(
			groupId, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByGroupId_Version(
			groupId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByGroupId_Version(
			groupId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByGroupId_Version_PrevAndNext(
			long lvEntryVersionId, long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(
				session, lvEntryVersion, groupId, version, orderByComparator,
				true);

			array[1] = lvEntryVersion;

			array[2] = getByGroupId_Version_PrevAndNext(
				session, lvEntryVersion, groupId, version, orderByComparator,
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

	protected LVEntryVersion getByGroupId_Version_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (LVEntryVersion lvEntryVersion :
				findByGroupId_Version(
					groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		FinderPath finderPath = _finderPathCountByGroupId_Version;

		Object[] finderArgs = new Object[] {groupId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 =
		"lvEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_UGK;
	private FinderPath _finderPathWithoutPaginationFindByG_UGK;
	private FinderPath _finderPathCountByG_UGK;

	/**
	 * Returns all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey) {

		return findByG_UGK(
			groupId, uniqueGroupKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end) {

		return findByG_UGK(groupId, uniqueGroupKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findByG_UGK(
			groupId, uniqueGroupKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_UGK;
				finderArgs = new Object[] {groupId, uniqueGroupKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_UGK;
			finderArgs = new Object[] {
				groupId, uniqueGroupKey, start, end, orderByComparator
			};
		}

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if ((groupId != lvEntryVersion.getGroupId()) ||
						!uniqueGroupKey.equals(
							lvEntryVersion.getUniqueGroupKey())) {

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

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUniqueGroupKey) {
					queryPos.add(uniqueGroupKey);
				}

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByG_UGK_First(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByG_UGK_First(
			groupId, uniqueGroupKey, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", uniqueGroupKey=");
		sb.append(uniqueGroupKey);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByG_UGK_First(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		List<LVEntryVersion> list = findByG_UGK(
			groupId, uniqueGroupKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByG_UGK_Last(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByG_UGK_Last(
			groupId, uniqueGroupKey, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", uniqueGroupKey=");
		sb.append(uniqueGroupKey);

		sb.append("}");

		throw new NoSuchLVEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByG_UGK_Last(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		int count = countByG_UGK(groupId, uniqueGroupKey);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByG_UGK(
			groupId, uniqueGroupKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByG_UGK_PrevAndNext(
			long lvEntryVersionId, long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByG_UGK_PrevAndNext(
				session, lvEntryVersion, groupId, uniqueGroupKey,
				orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByG_UGK_PrevAndNext(
				session, lvEntryVersion, groupId, uniqueGroupKey,
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

	protected LVEntryVersion getByG_UGK_PrevAndNext(
		Session session, LVEntryVersion lvEntryVersion, long groupId,
		String uniqueGroupKey,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

		boolean bindUniqueGroupKey = false;

		if (uniqueGroupKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
		}
		else {
			bindUniqueGroupKey = true;

			sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
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
			sb.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindUniqueGroupKey) {
			queryPos.add(uniqueGroupKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 */
	@Override
	public void removeByG_UGK(long groupId, String uniqueGroupKey) {
		for (LVEntryVersion lvEntryVersion :
				findByG_UGK(
					groupId, uniqueGroupKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByG_UGK(long groupId, String uniqueGroupKey) {
		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = _finderPathCountByG_UGK;

		Object[] finderArgs = new Object[] {groupId, uniqueGroupKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				sb.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUniqueGroupKey) {
					queryPos.add(uniqueGroupKey);
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

	private static final String _FINDER_COLUMN_G_UGK_GROUPID_2 =
		"lvEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2 =
		"lvEntryVersion.uniqueGroupKey = ?";

	private static final String _FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3 =
		"(lvEntryVersion.uniqueGroupKey IS NULL OR lvEntryVersion.uniqueGroupKey = '')";

	private FinderPath _finderPathFetchByG_UGK_Version;
	private FinderPath _finderPathCountByG_UGK_Version;

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByG_UGK_Version(
			long groupId, String uniqueGroupKey, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByG_UGK_Version(
			groupId, uniqueGroupKey, version);

		if (lvEntryVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", uniqueGroupKey=");
			sb.append(uniqueGroupKey);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLVEntryVersionException(sb.toString());
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version) {

		return fetchByG_UGK_Version(groupId, uniqueGroupKey, version, true);
	}

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version,
		boolean useFinderCache) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, uniqueGroupKey, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_UGK_Version, finderArgs);
		}

		if (result instanceof LVEntryVersion) {
			LVEntryVersion lvEntryVersion = (LVEntryVersion)result;

			if ((groupId != lvEntryVersion.getGroupId()) ||
				!Objects.equals(
					uniqueGroupKey, lvEntryVersion.getUniqueGroupKey()) ||
				(version != lvEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_UGK_VERSION_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				sb.append(_FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_UGK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUniqueGroupKey) {
					queryPos.add(uniqueGroupKey);
				}

				queryPos.add(version);

				List<LVEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_UGK_Version, finderArgs, list);
					}
				}
				else {
					LVEntryVersion lvEntryVersion = list.get(0);

					result = lvEntryVersion;

					cacheResult(lvEntryVersion);
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
			return (LVEntryVersion)result;
		}
	}

	/**
	 * Removes the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	@Override
	public LVEntryVersion removeByG_UGK_Version(
			long groupId, String uniqueGroupKey, int version)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = findByG_UGK_Version(
			groupId, uniqueGroupKey, version);

		return remove(lvEntryVersion);
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = _finderPathCountByG_UGK_Version;

		Object[] finderArgs = new Object[] {groupId, uniqueGroupKey, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_UGK_VERSION_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				sb.append(_FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_UGK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUniqueGroupKey) {
					queryPos.add(uniqueGroupKey);
				}

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_G_UGK_VERSION_GROUPID_2 =
		"lvEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_2 =
		"lvEntryVersion.uniqueGroupKey = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_VERSION_UNIQUEGROUPKEY_3 =
		"(lvEntryVersion.uniqueGroupKey IS NULL OR lvEntryVersion.uniqueGroupKey = '') AND ";

	private static final String _FINDER_COLUMN_G_UGK_VERSION_VERSION_2 =
		"lvEntryVersion.version = ?";

	public LVEntryVersionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LVEntryVersion.class);

		setModelImplClass(LVEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(LVEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the lv entry version in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersion the lv entry version
	 */
	@Override
	public void cacheResult(LVEntryVersion lvEntryVersion) {
		entityCache.putResult(
			LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey(),
			lvEntryVersion);

		finderCache.putResult(
			_finderPathFetchByLvEntryId_Version,
			new Object[] {
				lvEntryVersion.getLvEntryId(), lvEntryVersion.getVersion()
			},
			lvEntryVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Version,
			new Object[] {
				lvEntryVersion.getUuid(), lvEntryVersion.getGroupId(),
				lvEntryVersion.getVersion()
			},
			lvEntryVersion);

		finderCache.putResult(
			_finderPathFetchByG_UGK_Version,
			new Object[] {
				lvEntryVersion.getGroupId(), lvEntryVersion.getUniqueGroupKey(),
				lvEntryVersion.getVersion()
			},
			lvEntryVersion);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the lv entry versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersions the lv entry versions
	 */
	@Override
	public void cacheResult(List<LVEntryVersion> lvEntryVersions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (lvEntryVersions.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LVEntryVersion lvEntryVersion : lvEntryVersions) {
			if (entityCache.getResult(
					LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey()) ==
						null) {

				cacheResult(lvEntryVersion);
			}
		}
	}

	/**
	 * Clears the cache for all lv entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryVersionImpl.class);

		finderCache.clearCache(LVEntryVersionImpl.class);
	}

	/**
	 * Clears the cache for the lv entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LVEntryVersion lvEntryVersion) {
		entityCache.removeResult(LVEntryVersionImpl.class, lvEntryVersion);
	}

	@Override
	public void clearCache(List<LVEntryVersion> lvEntryVersions) {
		for (LVEntryVersion lvEntryVersion : lvEntryVersions) {
			entityCache.removeResult(LVEntryVersionImpl.class, lvEntryVersion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LVEntryVersionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LVEntryVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryVersionModelImpl lvEntryVersionModelImpl) {

		Object[] args = new Object[] {
			lvEntryVersionModelImpl.getLvEntryId(),
			lvEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByLvEntryId_Version, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByLvEntryId_Version, args, lvEntryVersionModelImpl);

		args = new Object[] {
			lvEntryVersionModelImpl.getUuid(),
			lvEntryVersionModelImpl.getGroupId(),
			lvEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G_Version, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G_Version, args, lvEntryVersionModelImpl);

		args = new Object[] {
			lvEntryVersionModelImpl.getGroupId(),
			lvEntryVersionModelImpl.getUniqueGroupKey(),
			lvEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByG_UGK_Version, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_UGK_Version, args, lvEntryVersionModelImpl);
	}

	/**
	 * Creates a new lv entry version with the primary key. Does not add the lv entry version to the database.
	 *
	 * @param lvEntryVersionId the primary key for the new lv entry version
	 * @return the new lv entry version
	 */
	@Override
	public LVEntryVersion create(long lvEntryVersionId) {
		LVEntryVersion lvEntryVersion = new LVEntryVersionImpl();

		lvEntryVersion.setNew(true);
		lvEntryVersion.setPrimaryKey(lvEntryVersionId);

		lvEntryVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return lvEntryVersion;
	}

	/**
	 * Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version that was removed
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion remove(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException {

		return remove((Serializable)lvEntryVersionId);
	}

	/**
	 * Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry version
	 * @return the lv entry version that was removed
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion remove(Serializable primaryKey)
		throws NoSuchLVEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion lvEntryVersion = (LVEntryVersion)session.get(
				LVEntryVersionImpl.class, primaryKey);

			if (lvEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(lvEntryVersion);
		}
		catch (NoSuchLVEntryVersionException noSuchEntityException) {
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
	protected LVEntryVersion removeImpl(LVEntryVersion lvEntryVersion) {
		lvEntryVersionToBigDecimalEntryTableMapper.
			deleteLeftPrimaryKeyTableMappings(lvEntryVersion.getPrimaryKey());

		lvEntryLocalizationVersionPersistence.removeByLvEntryId_Version(
			lvEntryVersion.getVersionedModelId(), lvEntryVersion.getVersion());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryVersion)) {
				lvEntryVersion = (LVEntryVersion)session.get(
					LVEntryVersionImpl.class,
					lvEntryVersion.getPrimaryKeyObj());
			}

			if (lvEntryVersion != null) {
				session.delete(lvEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (lvEntryVersion != null) {
			clearCache(lvEntryVersion);
		}

		return lvEntryVersion;
	}

	@Override
	public LVEntryVersion updateImpl(LVEntryVersion lvEntryVersion) {
		boolean isNew = lvEntryVersion.isNew();

		if (!(lvEntryVersion instanceof LVEntryVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					lvEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryVersion implementation " +
					lvEntryVersion.getClass());
		}

		LVEntryVersionModelImpl lvEntryVersionModelImpl =
			(LVEntryVersionModelImpl)lvEntryVersion;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(lvEntryVersion);
			}
			else {
				throw new IllegalArgumentException(
					"LVEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			LVEntryVersionImpl.class, lvEntryVersionModelImpl, false, true);

		cacheUniqueFindersCache(lvEntryVersionModelImpl);

		if (isNew) {
			lvEntryVersion.setNew(false);
		}

		lvEntryVersion.resetOriginalValues();

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry version
	 * @return the lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryVersionException {

		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(primaryKey);

		if (lvEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version with the primary key or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion findByPrimaryKey(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException {

		return findByPrimaryKey((Serializable)lvEntryVersionId);
	}

	/**
	 * Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion fetchByPrimaryKey(long lvEntryVersionId) {
		return fetchByPrimaryKey((Serializable)lvEntryVersionId);
	}

	/**
	 * Returns all the lv entry versions.
	 *
	 * @return the lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(
		int start, int end, OrderByComparator<LVEntryVersion> orderByComparator,
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

		List<LVEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LVENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYVERSION;

				sql = sql.concat(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LVEntryVersion>)QueryUtil.list(
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
	 * Removes all the lv entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntryVersion lvEntryVersion : findAll()) {
			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions.
	 *
	 * @return the number of lv entry versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LVENTRYVERSION);

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

	/**
	 * Returns the primaryKeys of big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return long[] of the primaryKeys of big decimal entries associated with the lv entry version
	 */
	@Override
	public long[] getBigDecimalEntryPrimaryKeys(long pk) {
		long[] pks =
			lvEntryVersionToBigDecimalEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return the big decimal entries associated with the lv entry version
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk) {

		return getBigDecimalEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the big decimal entries associated with the lv entry version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of big decimal entries associated with the lv entry version
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk, int start, int end) {

		return getBigDecimalEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries associated with the lv entry version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries associated with the lv entry version
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						BigDecimalEntry> orderByComparator) {

		return lvEntryVersionToBigDecimalEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return the number of big decimal entries associated with the lv entry version
	 */
	@Override
	public int getBigDecimalEntriesSize(long pk) {
		long[] pks =
			lvEntryVersionToBigDecimalEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the big decimal entry is associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 * @return <code>true</code> if the big decimal entry is associated with the lv entry version; <code>false</code> otherwise
	 */
	@Override
	public boolean containsBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		return lvEntryVersionToBigDecimalEntryTableMapper.containsTableMapping(
			pk, bigDecimalEntryPK);
	}

	/**
	 * Returns <code>true</code> if the lv entry version has any big decimal entries associated with it.
	 *
	 * @param pk the primary key of the lv entry version to check for associations with big decimal entries
	 * @return <code>true</code> if the lv entry version has any big decimal entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsBigDecimalEntries(long pk) {
		if (getBigDecimalEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	@Override
	public void addBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(pk);

		if (lvEntryVersion == null) {
			lvEntryVersionToBigDecimalEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, bigDecimalEntryPK);
		}
		else {
			lvEntryVersionToBigDecimalEntryTableMapper.addTableMapping(
				lvEntryVersion.getCompanyId(), pk, bigDecimalEntryPK);
		}
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void addBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(pk);

		if (lvEntryVersion == null) {
			lvEntryVersionToBigDecimalEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				bigDecimalEntry.getPrimaryKey());
		}
		else {
			lvEntryVersionToBigDecimalEntryTableMapper.addTableMapping(
				lvEntryVersion.getCompanyId(), pk,
				bigDecimalEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	@Override
	public void addBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		long companyId = 0;

		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(pk);

		if (lvEntryVersion == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = lvEntryVersion.getCompanyId();
		}

		lvEntryVersionToBigDecimalEntryTableMapper.addTableMappings(
			companyId, pk, bigDecimalEntryPKs);
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries
	 */
	@Override
	public void addBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		addBigDecimalEntries(
			pk,
			ListUtil.toLongArray(
				bigDecimalEntries,
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry.BIG_DECIMAL_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the lv entry version and its big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version to clear the associated big decimal entries from
	 */
	@Override
	public void clearBigDecimalEntries(long pk) {
		lvEntryVersionToBigDecimalEntryTableMapper.
			deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	@Override
	public void removeBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		lvEntryVersionToBigDecimalEntryTableMapper.deleteTableMapping(
			pk, bigDecimalEntryPK);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void removeBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		lvEntryVersionToBigDecimalEntryTableMapper.deleteTableMapping(
			pk, bigDecimalEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	@Override
	public void removeBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		lvEntryVersionToBigDecimalEntryTableMapper.deleteTableMappings(
			pk, bigDecimalEntryPKs);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries
	 */
	@Override
	public void removeBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		removeBigDecimalEntries(
			pk,
			ListUtil.toLongArray(
				bigDecimalEntries,
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry.BIG_DECIMAL_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the big decimal entries associated with the lv entry version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries to be associated with the lv entry version
	 */
	@Override
	public void setBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		Set<Long> newBigDecimalEntryPKsSet = SetUtil.fromArray(
			bigDecimalEntryPKs);
		Set<Long> oldBigDecimalEntryPKsSet = SetUtil.fromArray(
			lvEntryVersionToBigDecimalEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeBigDecimalEntryPKsSet = new HashSet<Long>(
			oldBigDecimalEntryPKsSet);

		removeBigDecimalEntryPKsSet.removeAll(newBigDecimalEntryPKsSet);

		lvEntryVersionToBigDecimalEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeBigDecimalEntryPKsSet));

		newBigDecimalEntryPKsSet.removeAll(oldBigDecimalEntryPKsSet);

		long companyId = 0;

		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(pk);

		if (lvEntryVersion == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = lvEntryVersion.getCompanyId();
		}

		lvEntryVersionToBigDecimalEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newBigDecimalEntryPKsSet));
	}

	/**
	 * Sets the big decimal entries associated with the lv entry version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries to be associated with the lv entry version
	 */
	@Override
	public void setBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		try {
			long[] bigDecimalEntryPKs = new long[bigDecimalEntries.size()];

			for (int i = 0; i < bigDecimalEntries.size(); i++) {
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry bigDecimalEntry = bigDecimalEntries.get(i);

				bigDecimalEntryPKs[i] = bigDecimalEntry.getPrimaryKey();
			}

			setBigDecimalEntries(pk, bigDecimalEntryPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
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
		return "lvEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LVENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry version persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		lvEntryVersionToBigDecimalEntryTableMapper =
			TableMapperFactory.getTableMapper(
				"BigDecimalEntries_LVEntries", "companyId", "lvEntryVersionId",
				"bigDecimalEntryId", this, bigDecimalEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLvEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"lvEntryId"}, true);

		_finderPathWithoutPaginationFindByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLvEntryId",
			new String[] {Long.class.getName()}, new String[] {"lvEntryId"},
			true);

		_finderPathCountByLvEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLvEntryId",
			new String[] {Long.class.getName()}, new String[] {"lvEntryId"},
			false);

		_finderPathFetchByLvEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByLvEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"lvEntryId", "version"}, true);

		_finderPathCountByLvEntryId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"lvEntryId", "version"}, false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, true);

		_finderPathCountByUuid_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"uuid_", "version"}, false);

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathFetchByUUID_G_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "groupId", "version"}, true);

		_finderPathCountByUUID_G_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "groupId", "version"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, true);

		_finderPathCountByUuid_C_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"uuid_", "companyId", "version"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "version"}, true);

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, true);

		_finderPathCountByGroupId_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "version"}, false);

		_finderPathWithPaginationFindByG_UGK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_UGK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "uniqueGroupKey"}, true);

		_finderPathWithoutPaginationFindByG_UGK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UGK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "uniqueGroupKey"}, true);

		_finderPathCountByG_UGK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UGK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "uniqueGroupKey"}, false);

		_finderPathFetchByG_UGK_Version = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_UGK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "uniqueGroupKey", "version"}, true);

		_finderPathCountByG_UGK_Version = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UGK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "uniqueGroupKey", "version"}, false);
	}

	public void destroy() {
		entityCache.removeCache(LVEntryVersionImpl.class.getName());

		TableMapperFactory.removeTableMapper("BigDecimalEntries_LVEntries");
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = BigDecimalEntryPersistence.class)
	protected BigDecimalEntryPersistence bigDecimalEntryPersistence;

	protected TableMapper
		<LVEntryVersion,
		 com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			lvEntryVersionToBigDecimalEntryTableMapper;

	@BeanReference(type = LVEntryLocalizationVersionPersistence.class)
	protected LVEntryLocalizationVersionPersistence
		lvEntryLocalizationVersionPersistence;

	private static final String _SQL_SELECT_LVENTRYVERSION =
		"SELECT lvEntryVersion FROM LVEntryVersion lvEntryVersion";

	private static final String _SQL_SELECT_LVENTRYVERSION_WHERE =
		"SELECT lvEntryVersion FROM LVEntryVersion lvEntryVersion WHERE ";

	private static final String _SQL_COUNT_LVENTRYVERSION =
		"SELECT COUNT(lvEntryVersion) FROM LVEntryVersion lvEntryVersion";

	private static final String _SQL_COUNT_LVENTRYVERSION_WHERE =
		"SELECT COUNT(lvEntryVersion) FROM LVEntryVersion lvEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LVEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LVEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LVEntryVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}