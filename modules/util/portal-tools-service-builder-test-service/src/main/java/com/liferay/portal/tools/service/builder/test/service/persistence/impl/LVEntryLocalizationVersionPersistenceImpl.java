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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryLocalizationVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryLocalizationVersionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lv entry localization version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationVersionPersistenceImpl
	extends BasePersistenceImpl<LVEntryLocalizationVersion>
	implements LVEntryLocalizationVersionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LVEntryLocalizationVersionUtil</code> to access the lv entry localization version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LVEntryLocalizationVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLvEntryLocalizationId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryLocalizationId;
	private FinderPath _finderPathCountByLvEntryLocalizationId;

	/**
	 * Returns all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId) {

		return findByLvEntryLocalizationId(
			lvEntryLocalizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end) {

		return findByLvEntryLocalizationId(
			lvEntryLocalizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return findByLvEntryLocalizationId(
			lvEntryLocalizationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryLocalizationId(
		long lvEntryLocalizationId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByLvEntryLocalizationId;
				finderArgs = new Object[] {lvEntryLocalizationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLvEntryLocalizationId;
			finderArgs = new Object[] {
				lvEntryLocalizationId, start, end, orderByComparator
			};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
						list) {

					if (lvEntryLocalizationId !=
							lvEntryLocalizationVersion.
								getLvEntryLocalizationId()) {

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

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				list = (List<LVEntryLocalizationVersion>)QueryUtil.list(
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
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_First(
			long lvEntryLocalizationId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryLocalizationId_First(
				lvEntryLocalizationId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryLocalizationId=");
		msg.append(lvEntryLocalizationId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_First(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		List<LVEntryLocalizationVersion> list = findByLvEntryLocalizationId(
			lvEntryLocalizationId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Last(
			long lvEntryLocalizationId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryLocalizationId_Last(
				lvEntryLocalizationId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryLocalizationId=");
		msg.append(lvEntryLocalizationId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Last(
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		int count = countByLvEntryLocalizationId(lvEntryLocalizationId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByLvEntryLocalizationId(
			lvEntryLocalizationId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByLvEntryLocalizationId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryLocalizationId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array =
				new LVEntryLocalizationVersionImpl[3];

			array[0] = getByLvEntryLocalizationId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryLocalizationId,
				orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByLvEntryLocalizationId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryLocalizationId,
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

	protected LVEntryLocalizationVersion getByLvEntryLocalizationId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long lvEntryLocalizationId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(
			_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryLocalizationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryLocalizationVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryLocalizationId = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 */
	@Override
	public void removeByLvEntryLocalizationId(long lvEntryLocalizationId) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				findByLvEntryLocalizationId(
					lvEntryLocalizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryLocalizationId(long lvEntryLocalizationId) {
		FinderPath finderPath = _finderPathCountByLvEntryLocalizationId;

		Object[] finderArgs = new Object[] {lvEntryLocalizationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

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

	private static final String
		_FINDER_COLUMN_LVENTRYLOCALIZATIONID_LVENTRYLOCALIZATIONID_2 =
			"lvEntryLocalizationVersion.lvEntryLocalizationId = ?";

	private FinderPath _finderPathFetchByLvEntryLocalizationId_Version;
	private FinderPath _finderPathCountByLvEntryLocalizationId_Version;

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryLocalizationId_Version(
				lvEntryLocalizationId, version);

		if (lvEntryLocalizationVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("lvEntryLocalizationId=");
			msg.append(lvEntryLocalizationId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version) {

		return fetchByLvEntryLocalizationId_Version(
			lvEntryLocalizationId, version, true);
	}

	/**
	 * Returns the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {lvEntryLocalizationId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLvEntryLocalizationId_Version, finderArgs,
				this);
		}

		if (result instanceof LVEntryLocalizationVersion) {
			LVEntryLocalizationVersion lvEntryLocalizationVersion =
				(LVEntryLocalizationVersion)result;

			if ((lvEntryLocalizationId !=
					lvEntryLocalizationVersion.getLvEntryLocalizationId()) ||
				(version != lvEntryLocalizationVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				qPos.add(version);

				List<LVEntryLocalizationVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLvEntryLocalizationId_Version,
							finderArgs, list);
					}
				}
				else {
					LVEntryLocalizationVersion lvEntryLocalizationVersion =
						list.get(0);

					result = lvEntryLocalizationVersion;

					cacheResult(lvEntryLocalizationVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByLvEntryLocalizationId_Version,
						finderArgs);
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
			return (LVEntryLocalizationVersion)result;
		}
	}

	/**
	 * Removes the lv entry localization version where lvEntryLocalizationId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	@Override
	public LVEntryLocalizationVersion removeByLvEntryLocalizationId_Version(
			long lvEntryLocalizationId, int version)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByLvEntryLocalizationId_Version(lvEntryLocalizationId, version);

		return remove(lvEntryLocalizationVersion);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryLocalizationId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationId the lv entry localization ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryLocalizationId_Version(
		long lvEntryLocalizationId, int version) {

		FinderPath finderPath = _finderPathCountByLvEntryLocalizationId_Version;

		Object[] finderArgs = new Object[] {lvEntryLocalizationId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2);

			query.append(
				_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryLocalizationId);

				qPos.add(version);

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

	private static final String
		_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_LVENTRYLOCALIZATIONID_2 =
			"lvEntryLocalizationVersion.lvEntryLocalizationId = ? AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYLOCALIZATIONID_VERSION_VERSION_2 =
			"lvEntryLocalizationVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByLvEntryId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId;
	private FinderPath _finderPathCountByLvEntryId;

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId(long lvEntryId) {
		return findByLvEntryId(
			lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return findByLvEntryId(lvEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return findByLvEntryId(lvEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		List<LVEntryLocalizationVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
						list) {

					if (lvEntryId !=
							lvEntryLocalizationVersion.getLvEntryId()) {

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

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				list = (List<LVEntryLocalizationVersion>)QueryUtil.list(
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
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_First(
			long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_First(lvEntryId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_First(
		long lvEntryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		List<LVEntryLocalizationVersion> list = findByLvEntryId(
			lvEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_Last(
			long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_Last(lvEntryId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_Last(
		long lvEntryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		int count = countByLvEntryId(lvEntryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByLvEntryId(
			lvEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array =
				new LVEntryLocalizationVersionImpl[3];

			array[0] = getByLvEntryId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId,
				orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByLvEntryId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId,
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

	protected LVEntryLocalizationVersion getByLvEntryId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long lvEntryId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryLocalizationVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	@Override
	public void removeByLvEntryId(long lvEntryId) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				findByLvEntryId(
					lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryId(long lvEntryId) {
		FinderPath finderPath = _finderPathCountByLvEntryId;

		Object[] finderArgs = new Object[] {lvEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

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

	private static final String _FINDER_COLUMN_LVENTRYID_LVENTRYID_2 =
		"lvEntryLocalizationVersion.lvEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByLvEntryId_Version;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId_Version;
	private FinderPath _finderPathCountByLvEntryId_Version;

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version) {

		return findByLvEntryId_Version(
			lvEntryId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end) {

		return findByLvEntryId_Version(lvEntryId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return findByLvEntryId_Version(
			lvEntryId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_Version(
		long lvEntryId, int version, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByLvEntryId_Version;
				finderArgs = new Object[] {lvEntryId, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLvEntryId_Version;
			finderArgs = new Object[] {
				lvEntryId, version, start, end, orderByComparator
			};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
						list) {

					if ((lvEntryId !=
							lvEntryLocalizationVersion.getLvEntryId()) ||
						(version != lvEntryLocalizationVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				qPos.add(version);

				list = (List<LVEntryLocalizationVersion>)QueryUtil.list(
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
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_Version_First(
			long lvEntryId, int version,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_Version_First(
				lvEntryId, version, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_Version_First(
		long lvEntryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		List<LVEntryLocalizationVersion> list = findByLvEntryId_Version(
			lvEntryId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_Version_Last(
			long lvEntryId, int version,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_Version_Last(
				lvEntryId, version, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_Version_Last(
		long lvEntryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		int count = countByLvEntryId_Version(lvEntryId, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByLvEntryId_Version(
			lvEntryId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByLvEntryId_Version_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId, int version,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array =
				new LVEntryLocalizationVersionImpl[3];

			array[0] = getByLvEntryId_Version_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId, version,
				orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByLvEntryId_Version_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId, version,
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

	protected LVEntryLocalizationVersion getByLvEntryId_Version_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long lvEntryId, int version,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

		query.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryLocalizationVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 */
	@Override
	public void removeByLvEntryId_Version(long lvEntryId, int version) {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				findByLvEntryId_Version(
					lvEntryId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryId_Version(long lvEntryId, int version) {
		FinderPath finderPath = _finderPathCountByLvEntryId_Version;

		Object[] finderArgs = new Object[] {lvEntryId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2 =
		"lvEntryLocalizationVersion.lvEntryId = ? AND ";

	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2 =
		"lvEntryLocalizationVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByLvEntryId_LanguageId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId_LanguageId;
	private FinderPath _finderPathCountByLvEntryId_LanguageId;

	/**
	 * Returns all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return findByLvEntryId_LanguageId(
			lvEntryId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end) {

		return findByLvEntryId_LanguageId(
			lvEntryId, languageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return findByLvEntryId_LanguageId(
			lvEntryId, languageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findByLvEntryId_LanguageId(
		long lvEntryId, String languageId, int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByLvEntryId_LanguageId;
				finderArgs = new Object[] {lvEntryId, languageId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLvEntryId_LanguageId;
			finderArgs = new Object[] {
				lvEntryId, languageId, start, end, orderByComparator
			};
		}

		List<LVEntryLocalizationVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
						list) {

					if ((lvEntryId !=
							lvEntryLocalizationVersion.getLvEntryId()) ||
						!languageId.equals(
							lvEntryLocalizationVersion.getLanguageId())) {

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

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				list = (List<LVEntryLocalizationVersion>)QueryUtil.list(
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
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_First(
			long lvEntryId, String languageId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_LanguageId_First(
				lvEntryId, languageId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_First(
		long lvEntryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		List<LVEntryLocalizationVersion> list = findByLvEntryId_LanguageId(
			lvEntryId, languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_Last(
			long lvEntryId, String languageId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_LanguageId_Last(
				lvEntryId, languageId, orderByComparator);

		if (lvEntryLocalizationVersion != null) {
			return lvEntryLocalizationVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Last(
		long lvEntryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		int count = countByLvEntryId_LanguageId(lvEntryId, languageId);

		if (count == 0) {
			return null;
		}

		List<LVEntryLocalizationVersion> list = findByLvEntryId_LanguageId(
			lvEntryId, languageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry localization versions before and after the current lv entry localization version in the ordered set where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the current lv entry localization version
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion[] findByLvEntryId_LanguageId_PrevAndNext(
			long lvEntryLocalizationVersionId, long lvEntryId,
			String languageId,
			OrderByComparator<LVEntryLocalizationVersion> orderByComparator)
		throws NoSuchLVEntryLocalizationVersionException {

		languageId = Objects.toString(languageId, "");

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByPrimaryKey(lvEntryLocalizationVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion[] array =
				new LVEntryLocalizationVersionImpl[3];

			array[0] = getByLvEntryId_LanguageId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId, languageId,
				orderByComparator, true);

			array[1] = lvEntryLocalizationVersion;

			array[2] = getByLvEntryId_LanguageId_PrevAndNext(
				session, lvEntryLocalizationVersion, lvEntryId, languageId,
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

	protected LVEntryLocalizationVersion getByLvEntryId_LanguageId_PrevAndNext(
		Session session, LVEntryLocalizationVersion lvEntryLocalizationVersion,
		long lvEntryId, String languageId,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2);

		boolean bindLanguageId = false;

		if (languageId.isEmpty()) {
			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2);
		}

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
			query.append(LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryId);

		if (bindLanguageId) {
			qPos.add(languageId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						lvEntryLocalizationVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryLocalizationVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry localization versions where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 */
	@Override
	public void removeByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				findByLvEntryId_LanguageId(
					lvEntryId, languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryId_LanguageId(long lvEntryId, String languageId) {
		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByLvEntryId_LanguageId;

		Object[] finderArgs = new Object[] {lvEntryId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LVENTRYID_2 =
			"lvEntryLocalizationVersion.lvEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_2 =
			"lvEntryLocalizationVersion.languageId = ?";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(lvEntryLocalizationVersion.languageId IS NULL OR lvEntryLocalizationVersion.languageId = '')";

	private FinderPath _finderPathFetchByLvEntryId_LanguageId_Version;
	private FinderPath _finderPathCountByLvEntryId_LanguageId_Version;

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByLvEntryId_LanguageId_Version(lvEntryId, languageId, version);

		if (lvEntryLocalizationVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("lvEntryId=");
			msg.append(lvEntryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryLocalizationVersionException(msg.toString());
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version) {

		return fetchByLvEntryId_LanguageId_Version(
			lvEntryId, languageId, version, true);
	}

	/**
	 * Returns the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization version, or <code>null</code> if a matching lv entry localization version could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version,
		boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {lvEntryId, languageId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLvEntryId_LanguageId_Version, finderArgs,
				this);
		}

		if (result instanceof LVEntryLocalizationVersion) {
			LVEntryLocalizationVersion lvEntryLocalizationVersion =
				(LVEntryLocalizationVersion)result;

			if ((lvEntryId != lvEntryLocalizationVersion.getLvEntryId()) ||
				!Objects.equals(
					languageId, lvEntryLocalizationVersion.getLanguageId()) ||
				(version != lvEntryLocalizationVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(
					_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(version);

				List<LVEntryLocalizationVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLvEntryId_LanguageId_Version,
							finderArgs, list);
					}
				}
				else {
					LVEntryLocalizationVersion lvEntryLocalizationVersion =
						list.get(0);

					result = lvEntryLocalizationVersion;

					cacheResult(lvEntryLocalizationVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByLvEntryId_LanguageId_Version,
						finderArgs);
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
			return (LVEntryLocalizationVersion)result;
		}
	}

	/**
	 * Removes the lv entry localization version where lvEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the lv entry localization version that was removed
	 */
	@Override
	public LVEntryLocalizationVersion removeByLvEntryId_LanguageId_Version(
			long lvEntryId, String languageId, int version)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			findByLvEntryId_LanguageId_Version(lvEntryId, languageId, version);

		return remove(lvEntryLocalizationVersion);
	}

	/**
	 * Returns the number of lv entry localization versions where lvEntryId = &#63; and languageId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the number of matching lv entry localization versions
	 */
	@Override
	public int countByLvEntryId_LanguageId_Version(
		long lvEntryId, String languageId, int version) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByLvEntryId_LanguageId_Version;

		Object[] finderArgs = new Object[] {lvEntryId, languageId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LVENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(
					_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(version);

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

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LVENTRYID_2 =
			"lvEntryLocalizationVersion.lvEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2 =
			"lvEntryLocalizationVersion.languageId = ? AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3 =
			"(lvEntryLocalizationVersion.languageId IS NULL OR lvEntryLocalizationVersion.languageId = '') AND ";

	private static final String
		_FINDER_COLUMN_LVENTRYID_LANGUAGEID_VERSION_VERSION_2 =
			"lvEntryLocalizationVersion.version = ?";

	public LVEntryLocalizationVersionPersistenceImpl() {
		setModelClass(LVEntryLocalizationVersion.class);

		setModelImplClass(LVEntryLocalizationVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the lv entry localization version in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersion the lv entry localization version
	 */
	@Override
	public void cacheResult(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		entityCache.putResult(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey(),
			lvEntryLocalizationVersion);

		finderCache.putResult(
			_finderPathFetchByLvEntryLocalizationId_Version,
			new Object[] {
				lvEntryLocalizationVersion.getLvEntryLocalizationId(),
				lvEntryLocalizationVersion.getVersion()
			},
			lvEntryLocalizationVersion);

		finderCache.putResult(
			_finderPathFetchByLvEntryId_LanguageId_Version,
			new Object[] {
				lvEntryLocalizationVersion.getLvEntryId(),
				lvEntryLocalizationVersion.getLanguageId(),
				lvEntryLocalizationVersion.getVersion()
			},
			lvEntryLocalizationVersion);

		lvEntryLocalizationVersion.resetOriginalValues();
	}

	/**
	 * Caches the lv entry localization versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizationVersions the lv entry localization versions
	 */
	@Override
	public void cacheResult(
		List<LVEntryLocalizationVersion> lvEntryLocalizationVersions) {

		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				lvEntryLocalizationVersions) {

			if (entityCache.getResult(
					LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryLocalizationVersionImpl.class,
					lvEntryLocalizationVersion.getPrimaryKey()) == null) {

				cacheResult(lvEntryLocalizationVersion);
			}
			else {
				lvEntryLocalizationVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lv entry localization versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryLocalizationVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lv entry localization version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		entityCache.removeResult(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion,
			true);
	}

	@Override
	public void clearCache(
		List<LVEntryLocalizationVersion> lvEntryLocalizationVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				lvEntryLocalizationVersions) {

			entityCache.removeResult(
				LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationVersionImpl.class,
				lvEntryLocalizationVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryLocalizationVersionModelImpl
			lvEntryLocalizationVersionModelImpl) {

		Object[] args = new Object[] {
			lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId(),
			lvEntryLocalizationVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByLvEntryLocalizationId_Version, args,
			Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByLvEntryLocalizationId_Version, args,
			lvEntryLocalizationVersionModelImpl, false);

		args = new Object[] {
			lvEntryLocalizationVersionModelImpl.getLvEntryId(),
			lvEntryLocalizationVersionModelImpl.getLanguageId(),
			lvEntryLocalizationVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByLvEntryId_LanguageId_Version, args,
			Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByLvEntryId_LanguageId_Version, args,
			lvEntryLocalizationVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LVEntryLocalizationVersionModelImpl lvEntryLocalizationVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId(),
				lvEntryLocalizationVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryLocalizationId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByLvEntryLocalizationId_Version, args);
		}

		if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByLvEntryLocalizationId_Version.
				 getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.
					getOriginalLvEntryLocalizationId(),
				lvEntryLocalizationVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryLocalizationId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByLvEntryLocalizationId_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryId(),
				lvEntryLocalizationVersionModelImpl.getLanguageId(),
				lvEntryLocalizationVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryId_LanguageId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByLvEntryId_LanguageId_Version, args);
		}

		if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByLvEntryId_LanguageId_Version.
				 getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getOriginalLvEntryId(),
				lvEntryLocalizationVersionModelImpl.getOriginalLanguageId(),
				lvEntryLocalizationVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryId_LanguageId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByLvEntryId_LanguageId_Version, args);
		}
	}

	/**
	 * Creates a new lv entry localization version with the primary key. Does not add the lv entry localization version to the database.
	 *
	 * @param lvEntryLocalizationVersionId the primary key for the new lv entry localization version
	 * @return the new lv entry localization version
	 */
	@Override
	public LVEntryLocalizationVersion create(
		long lvEntryLocalizationVersionId) {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			new LVEntryLocalizationVersionImpl();

		lvEntryLocalizationVersion.setNew(true);
		lvEntryLocalizationVersion.setPrimaryKey(lvEntryLocalizationVersionId);

		lvEntryLocalizationVersion.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return lvEntryLocalizationVersion;
	}

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion remove(long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException {

		return remove((Serializable)lvEntryLocalizationVersionId);
	}

	/**
	 * Removes the lv entry localization version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry localization version
	 * @return the lv entry localization version that was removed
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion remove(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationVersionException {

		Session session = null;

		try {
			session = openSession();

			LVEntryLocalizationVersion lvEntryLocalizationVersion =
				(LVEntryLocalizationVersion)session.get(
					LVEntryLocalizationVersionImpl.class, primaryKey);

			if (lvEntryLocalizationVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryLocalizationVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(lvEntryLocalizationVersion);
		}
		catch (NoSuchLVEntryLocalizationVersionException nsee) {
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
	protected LVEntryLocalizationVersion removeImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryLocalizationVersion)) {
				lvEntryLocalizationVersion =
					(LVEntryLocalizationVersion)session.get(
						LVEntryLocalizationVersionImpl.class,
						lvEntryLocalizationVersion.getPrimaryKeyObj());
			}

			if (lvEntryLocalizationVersion != null) {
				session.delete(lvEntryLocalizationVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lvEntryLocalizationVersion != null) {
			clearCache(lvEntryLocalizationVersion);
		}

		return lvEntryLocalizationVersion;
	}

	@Override
	public LVEntryLocalizationVersion updateImpl(
		LVEntryLocalizationVersion lvEntryLocalizationVersion) {

		boolean isNew = lvEntryLocalizationVersion.isNew();

		if (!(lvEntryLocalizationVersion instanceof
				LVEntryLocalizationVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntryLocalizationVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					lvEntryLocalizationVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryLocalizationVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryLocalizationVersion implementation " +
					lvEntryLocalizationVersion.getClass());
		}

		LVEntryLocalizationVersionModelImpl
			lvEntryLocalizationVersionModelImpl =
				(LVEntryLocalizationVersionModelImpl)lvEntryLocalizationVersion;

		Session session = null;

		try {
			session = openSession();

			if (lvEntryLocalizationVersion.isNew()) {
				session.save(lvEntryLocalizationVersion);

				lvEntryLocalizationVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"LVEntryLocalizationVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LVEntryLocalizationVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryLocalizationId()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryLocalizationId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLvEntryLocalizationId, args);

			args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryId()
			};

			finderCache.removeResult(_finderPathCountByLvEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLvEntryId, args);

			args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryId(),
				lvEntryLocalizationVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByLvEntryId_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLvEntryId_Version, args);

			args = new Object[] {
				lvEntryLocalizationVersionModelImpl.getLvEntryId(),
				lvEntryLocalizationVersionModelImpl.getLanguageId()
			};

			finderCache.removeResult(
				_finderPathCountByLvEntryId_LanguageId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLvEntryId_LanguageId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLvEntryLocalizationId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.
						getOriginalLvEntryLocalizationId()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryLocalizationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryLocalizationId,
					args);

				args = new Object[] {
					lvEntryLocalizationVersionModelImpl.
						getLvEntryLocalizationId()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryLocalizationId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryLocalizationId,
					args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLvEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getOriginalLvEntryId()
				};

				finderCache.removeResult(_finderPathCountByLvEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId, args);

				args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getLvEntryId()
				};

				finderCache.removeResult(_finderPathCountByLvEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId, args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLvEntryId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getOriginalLvEntryId(),
					lvEntryLocalizationVersionModelImpl.getOriginalVersion()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId_Version, args);

				args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getLvEntryId(),
					lvEntryLocalizationVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId_Version, args);
			}

			if ((lvEntryLocalizationVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLvEntryId_LanguageId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getOriginalLvEntryId(),
					lvEntryLocalizationVersionModelImpl.getOriginalLanguageId()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryId_LanguageId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId_LanguageId,
					args);

				args = new Object[] {
					lvEntryLocalizationVersionModelImpl.getLvEntryId(),
					lvEntryLocalizationVersionModelImpl.getLanguageId()
				};

				finderCache.removeResult(
					_finderPathCountByLvEntryId_LanguageId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLvEntryId_LanguageId,
					args);
			}
		}

		entityCache.putResult(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			lvEntryLocalizationVersion.getPrimaryKey(),
			lvEntryLocalizationVersion, false);

		clearUniqueFindersCache(lvEntryLocalizationVersionModelImpl, false);
		cacheUniqueFindersCache(lvEntryLocalizationVersionModelImpl);

		lvEntryLocalizationVersion.resetOriginalValues();

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryLocalizationVersionException {

		LVEntryLocalizationVersion lvEntryLocalizationVersion =
			fetchByPrimaryKey(primaryKey);

		if (lvEntryLocalizationVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryLocalizationVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return lvEntryLocalizationVersion;
	}

	/**
	 * Returns the lv entry localization version with the primary key or throws a <code>NoSuchLVEntryLocalizationVersionException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version
	 * @throws NoSuchLVEntryLocalizationVersionException if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion findByPrimaryKey(
			long lvEntryLocalizationVersionId)
		throws NoSuchLVEntryLocalizationVersionException {

		return findByPrimaryKey((Serializable)lvEntryLocalizationVersionId);
	}

	/**
	 * Returns the lv entry localization version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationVersionId the primary key of the lv entry localization version
	 * @return the lv entry localization version, or <code>null</code> if a lv entry localization version with the primary key could not be found
	 */
	@Override
	public LVEntryLocalizationVersion fetchByPrimaryKey(
		long lvEntryLocalizationVersionId) {

		return fetchByPrimaryKey((Serializable)lvEntryLocalizationVersionId);
	}

	/**
	 * Returns all the lv entry localization versions.
	 *
	 * @return the lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @return the range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry localization versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localization versions
	 * @param end the upper bound of the range of lv entry localization versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry localization versions
	 */
	@Override
	public List<LVEntryLocalizationVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalizationVersion> orderByComparator,
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

		List<LVEntryLocalizationVersion> list = null;

		if (useFinderCache) {
			list = (List<LVEntryLocalizationVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LVENTRYLOCALIZATIONVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYLOCALIZATIONVERSION;

				sql = sql.concat(
					LVEntryLocalizationVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<LVEntryLocalizationVersion>)QueryUtil.list(
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
	 * Removes all the lv entry localization versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntryLocalizationVersion lvEntryLocalizationVersion :
				findAll()) {

			remove(lvEntryLocalizationVersion);
		}
	}

	/**
	 * Returns the number of lv entry localization versions.
	 *
	 * @return the number of lv entry localization versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_LVENTRYLOCALIZATIONVERSION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "lvEntryLocalizationVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LVENTRYLOCALIZATIONVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryLocalizationVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry localization version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByLvEntryLocalizationId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLvEntryLocalizationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLvEntryLocalizationId =
			new FinderPath(
				LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryLocalizationVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLvEntryLocalizationId",
				new String[] {Long.class.getName()},
				LVEntryLocalizationVersionModelImpl.
					LVENTRYLOCALIZATIONID_COLUMN_BITMASK |
				LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryLocalizationId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryLocalizationId",
			new String[] {Long.class.getName()});

		_finderPathFetchByLvEntryLocalizationId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByLvEntryLocalizationId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LVEntryLocalizationVersionModelImpl.
				LVENTRYLOCALIZATIONID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryLocalizationId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryLocalizationId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByLvEntryId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLvEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLvEntryId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLvEntryId",
			new String[] {Long.class.getName()},
			LVEntryLocalizationVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByLvEntryId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLvEntryId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLvEntryId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLvEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LVEntryLocalizationVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByLvEntryId_LanguageId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLvEntryId_LanguageId",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLvEntryId_LanguageId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLvEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			LVEntryLocalizationVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId_LanguageId = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByLvEntryId_LanguageId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			LVEntryLocalizationVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByLvEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			LVEntryLocalizationVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			LVEntryLocalizationVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId_LanguageId_Version = new FinderPath(
			LVEntryLocalizationVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryLocalizationVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLvEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	}

	public void destroy() {
		entityCache.removeCache(LVEntryLocalizationVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LVENTRYLOCALIZATIONVERSION =
		"SELECT lvEntryLocalizationVersion FROM LVEntryLocalizationVersion lvEntryLocalizationVersion";

	private static final String _SQL_SELECT_LVENTRYLOCALIZATIONVERSION_WHERE =
		"SELECT lvEntryLocalizationVersion FROM LVEntryLocalizationVersion lvEntryLocalizationVersion WHERE ";

	private static final String _SQL_COUNT_LVENTRYLOCALIZATIONVERSION =
		"SELECT COUNT(lvEntryLocalizationVersion) FROM LVEntryLocalizationVersion lvEntryLocalizationVersion";

	private static final String _SQL_COUNT_LVENTRYLOCALIZATIONVERSION_WHERE =
		"SELECT COUNT(lvEntryLocalizationVersion) FROM LVEntryLocalizationVersion lvEntryLocalizationVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"lvEntryLocalizationVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LVEntryLocalizationVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LVEntryLocalizationVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LVEntryLocalizationVersionPersistenceImpl.class);

}