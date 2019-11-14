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

package com.liferay.microblogs.service.persistence.impl;

import com.liferay.microblogs.exception.NoSuchEntryException;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.impl.MicroblogsEntryImpl;
import com.liferay.microblogs.model.impl.MicroblogsEntryModelImpl;
import com.liferay.microblogs.service.persistence.MicroblogsEntryPersistence;
import com.liferay.microblogs.service.persistence.impl.constants.MicroblogsPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the microblogs entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MicroblogsEntryPersistence.class)
public class MicroblogsEntryPersistenceImpl
	extends BasePersistenceImpl<MicroblogsEntry>
	implements MicroblogsEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MicroblogsEntryUtil</code> to access the microblogs entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MicroblogsEntryImpl.class.getName();

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
	 * Returns all the microblogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if (companyId != microblogsEntry.getCompanyId()) {
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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByCompanyId_PrevAndNext(
			long microblogsEntryId, long companyId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, microblogsEntry, companyId, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, microblogsEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByCompanyId_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long companyId,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
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
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (MicroblogsEntry microblogsEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"microblogsEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the microblogs entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if (userId != microblogsEntry.getUserId()) {
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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByUserId_First(
			long userId, OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByUserId_First(
			userId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByUserId_First(
		long userId, OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByUserId_Last(
			long userId, OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByUserId_Last(
			userId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByUserId_Last(
		long userId, OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByUserId_PrevAndNext(
			long microblogsEntryId, long userId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, microblogsEntry, userId, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByUserId_PrevAndNext(
				session, microblogsEntry, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByUserId_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long userId,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (MicroblogsEntry microblogsEntry :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"microblogsEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByU_T;
	private FinderPath _finderPathWithoutPaginationFindByU_T;
	private FinderPath _finderPathCountByU_T;

	/**
	 * Returns all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_T(long userId, int type) {
		return findByU_T(
			userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end) {

		return findByU_T(userId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByU_T(userId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_T(
		long userId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_T;
				finderArgs = new Object[] {userId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_T;
			finderArgs = new Object[] {
				userId, type, start, end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((userId != microblogsEntry.getUserId()) ||
						(type != microblogsEntry.getType())) {

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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByU_T_First(
			long userId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByU_T_First(
			userId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByU_T_First(
		long userId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByU_T(
			userId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByU_T_Last(
			long userId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByU_T_Last(
			userId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByU_T_Last(
		long userId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByU_T(userId, type);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByU_T(
			userId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByU_T_PrevAndNext(
			long microblogsEntryId, long userId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByU_T_PrevAndNext(
				session, microblogsEntry, userId, type, orderByComparator,
				true);

			array[1] = microblogsEntry;

			array[2] = getByU_T_PrevAndNext(
				session, microblogsEntry, userId, type, orderByComparator,
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

	protected MicroblogsEntry getByU_T_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long userId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_U_T_USERID_2);

		query.append(_FINDER_COLUMN_U_T_TYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 */
	@Override
	public void removeByU_T(long userId, int type) {
		for (MicroblogsEntry microblogsEntry :
				findByU_T(
					userId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByU_T(long userId, int type) {
		FinderPath finderPath = _finderPathCountByU_T;

		Object[] finderArgs = new Object[] {userId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			query.append(_FINDER_COLUMN_U_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_U_T_USERID_2 =
		"microblogsEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_T_TYPE_2 =
		"microblogsEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByCCNI_CCPK;
	private FinderPath _finderPathWithoutPaginationFindByCCNI_CCPK;
	private FinderPath _finderPathCountByCCNI_CCPK;
	private FinderPath _finderPathWithPaginationCountByCCNI_CCPK;

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCCNI_CCPK;
				finderArgs = new Object[] {creatorClassNameId, creatorClassPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCCNI_CCPK;
			finderArgs = new Object[] {
				creatorClassNameId, creatorClassPK, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(creatorClassPK !=
							microblogsEntry.getCreatorClassPK())) {

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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_CCPK_First(
			long creatorClassNameId, long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_CCPK_First(
			creatorClassNameId, creatorClassPK, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_CCPK_First(
		long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByCCNI_CCPK(
			creatorClassNameId, creatorClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_CCPK_Last(
			long creatorClassNameId, long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_CCPK_Last(
			creatorClassNameId, creatorClassPK, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_CCPK_Last(
		long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByCCNI_CCPK(creatorClassNameId, creatorClassPK);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByCCNI_CCPK(
			creatorClassNameId, creatorClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByCCNI_CCPK_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId,
			long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByCCNI_CCPK_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, creatorClassPK,
				orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByCCNI_CCPK_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, creatorClassPK,
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

	protected MicroblogsEntry getByCCNI_CCPK_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry,
		long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(creatorClassNameId);

		qPos.add(creatorClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPKs, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPKs, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCCNI_CCPK(
			creatorClassNameId, creatorClassPKs, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		if (creatorClassPKs.length == 1) {
			return findByCCNI_CCPK(
				creatorClassNameId, creatorClassPKs[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					creatorClassNameId, StringUtil.merge(creatorClassPKs)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				creatorClassNameId, StringUtil.merge(creatorClassPKs), start,
				end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByCCNI_CCPK, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						!ArrayUtil.contains(
							creatorClassPKs,
							microblogsEntry.getCreatorClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				list = (List<MicroblogsEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByCCNI_CCPK, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByCCNI_CCPK, finderArgs);
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
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 */
	@Override
	public void removeByCCNI_CCPK(
		long creatorClassNameId, long creatorClassPK) {

		for (MicroblogsEntry microblogsEntry :
				findByCCNI_CCPK(
					creatorClassNameId, creatorClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCCNI_CCPK(long creatorClassNameId, long creatorClassPK) {
		FinderPath finderPath = _finderPathCountByCCNI_CCPK;

		Object[] finderArgs = new Object[] {creatorClassNameId, creatorClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

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
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCCNI_CCPK(
		long creatorClassNameId, long[] creatorClassPKs) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		Object[] finderArgs = new Object[] {
			creatorClassNameId, StringUtil.merge(creatorClassPKs)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByCCNI_CCPK, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByCCNI_CCPK, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByCCNI_CCPK, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CCNI_CCPK_CREATORCLASSNAMEID_2 =
		"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_2 =
		"microblogsEntry.creatorClassPK = ?";

	private static final String _FINDER_COLUMN_CCNI_CCPK_CREATORCLASSPK_7 =
		"microblogsEntry.creatorClassPK IN (";

	private FinderPath _finderPathWithPaginationFindByCCNI_T;
	private FinderPath _finderPathWithoutPaginationFindByCCNI_T;
	private FinderPath _finderPathCountByCCNI_T;

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type) {

		return findByCCNI_T(
			creatorClassNameId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end) {

		return findByCCNI_T(creatorClassNameId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCCNI_T(
			creatorClassNameId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_T(
		long creatorClassNameId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCCNI_T;
				finderArgs = new Object[] {creatorClassNameId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCCNI_T;
			finderArgs = new Object[] {
				creatorClassNameId, type, start, end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(type != microblogsEntry.getType())) {

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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_T_First(
			long creatorClassNameId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_T_First(
			creatorClassNameId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_T_First(
		long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByCCNI_T(
			creatorClassNameId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_T_Last(
			long creatorClassNameId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_T_Last(
			creatorClassNameId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_T_Last(
		long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByCCNI_T(creatorClassNameId, type);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByCCNI_T(
			creatorClassNameId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByCCNI_T_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByCCNI_T_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, type,
				orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByCCNI_T_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, type,
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

	protected MicroblogsEntry getByCCNI_T_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry,
		long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_CCNI_T_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_CCNI_T_TYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(creatorClassNameId);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and type = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 */
	@Override
	public void removeByCCNI_T(long creatorClassNameId, int type) {
		for (MicroblogsEntry microblogsEntry :
				findByCCNI_T(
					creatorClassNameId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCCNI_T(long creatorClassNameId, int type) {
		FinderPath finderPath = _finderPathCountByCCNI_T;

		Object[] finderArgs = new Object[] {creatorClassNameId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_CCNI_T_CREATORCLASSNAMEID_2 =
		"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_CCNI_T_TYPE_2 =
		"microblogsEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByT_P;
	private FinderPath _finderPathWithoutPaginationFindByT_P;
	private FinderPath _finderPathCountByT_P;

	/**
	 * Returns all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId) {

		return findByT_P(
			type, parentMicroblogsEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end) {

		return findByT_P(type, parentMicroblogsEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByT_P(
			type, parentMicroblogsEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByT_P(
		int type, long parentMicroblogsEntryId, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByT_P;
				finderArgs = new Object[] {type, parentMicroblogsEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByT_P;
			finderArgs = new Object[] {
				type, parentMicroblogsEntryId, start, end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((type != microblogsEntry.getType()) ||
						(parentMicroblogsEntryId !=
							microblogsEntry.getParentMicroblogsEntryId())) {

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

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_T_P_TYPE_2);

			query.append(_FINDER_COLUMN_T_P_PARENTMICROBLOGSENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(parentMicroblogsEntryId);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByT_P_First(
			int type, long parentMicroblogsEntryId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByT_P_First(
			type, parentMicroblogsEntryId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", parentMicroblogsEntryId=");
		msg.append(parentMicroblogsEntryId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByT_P_First(
		int type, long parentMicroblogsEntryId,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByT_P(
			type, parentMicroblogsEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByT_P_Last(
			int type, long parentMicroblogsEntryId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByT_P_Last(
			type, parentMicroblogsEntryId, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", parentMicroblogsEntryId=");
		msg.append(parentMicroblogsEntryId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByT_P_Last(
		int type, long parentMicroblogsEntryId,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByT_P(type, parentMicroblogsEntryId);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByT_P(
			type, parentMicroblogsEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByT_P_PrevAndNext(
			long microblogsEntryId, int type, long parentMicroblogsEntryId,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByT_P_PrevAndNext(
				session, microblogsEntry, type, parentMicroblogsEntryId,
				orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByT_P_PrevAndNext(
				session, microblogsEntry, type, parentMicroblogsEntryId,
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

	protected MicroblogsEntry getByT_P_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, int type,
		long parentMicroblogsEntryId,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_T_P_TYPE_2);

		query.append(_FINDER_COLUMN_T_P_PARENTMICROBLOGSENTRYID_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		qPos.add(parentMicroblogsEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63; from the database.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 */
	@Override
	public void removeByT_P(int type, long parentMicroblogsEntryId) {
		for (MicroblogsEntry microblogsEntry :
				findByT_P(
					type, parentMicroblogsEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where type = &#63; and parentMicroblogsEntryId = &#63;.
	 *
	 * @param type the type
	 * @param parentMicroblogsEntryId the parent microblogs entry ID
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByT_P(int type, long parentMicroblogsEntryId) {
		FinderPath finderPath = _finderPathCountByT_P;

		Object[] finderArgs = new Object[] {type, parentMicroblogsEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_T_P_TYPE_2);

			query.append(_FINDER_COLUMN_T_P_PARENTMICROBLOGSENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(parentMicroblogsEntryId);

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

	private static final String _FINDER_COLUMN_T_P_TYPE_2 =
		"microblogsEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_T_P_PARENTMICROBLOGSENTRYID_2 =
		"microblogsEntry.parentMicroblogsEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_CCNI_CCPK;
	private FinderPath _finderPathWithoutPaginationFindByC_CCNI_CCPK;
	private FinderPath _finderPathCountByC_CCNI_CCPK;
	private FinderPath _finderPathWithPaginationCountByC_CCNI_CCPK;

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_CCNI_CCPK;
				finderArgs = new Object[] {
					companyId, creatorClassNameId, creatorClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_CCNI_CCPK;
			finderArgs = new Object[] {
				companyId, creatorClassNameId, creatorClassPK, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((companyId != microblogsEntry.getCompanyId()) ||
						(creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(creatorClassPK !=
							microblogsEntry.getCreatorClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_CCPK_First(
			long companyId, long creatorClassNameId, long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_CCPK_First(
			companyId, creatorClassNameId, creatorClassPK, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_CCPK_First(
		long companyId, long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_CCPK_Last(
			long companyId, long creatorClassNameId, long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_CCPK_Last(
			companyId, creatorClassNameId, creatorClassPK, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_CCPK_Last(
		long companyId, long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByC_CCNI_CCPK_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			long creatorClassPK,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByC_CCNI_CCPK_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId,
				creatorClassPK, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByC_CCNI_CCPK_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId,
				creatorClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByC_CCNI_CCPK_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long companyId,
		long creatorClassNameId, long creatorClassPK,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(creatorClassNameId);

		qPos.add(creatorClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPKs, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPKs, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByC_CCNI_CCPK(
			companyId, creatorClassNameId, creatorClassPKs, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		if (creatorClassPKs.length == 1) {
			return findByC_CCNI_CCPK(
				companyId, creatorClassNameId, creatorClassPKs[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, creatorClassNameId,
					StringUtil.merge(creatorClassPKs)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, creatorClassNameId,
				StringUtil.merge(creatorClassPKs), start, end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_CCNI_CCPK, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((companyId != microblogsEntry.getCompanyId()) ||
						(creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						!ArrayUtil.contains(
							creatorClassPKs,
							microblogsEntry.getCreatorClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				list = (List<MicroblogsEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_CCNI_CCPK, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByC_CCNI_CCPK, finderArgs);
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
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 */
	@Override
	public void removeByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK) {

		for (MicroblogsEntry microblogsEntry :
				findByC_CCNI_CCPK(
					companyId, creatorClassNameId, creatorClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long creatorClassPK) {

		FinderPath finderPath = _finderPathCountByC_CCNI_CCPK;

		Object[] finderArgs = new Object[] {
			companyId, creatorClassNameId, creatorClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

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
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByC_CCNI_CCPK(
		long companyId, long creatorClassNameId, long[] creatorClassPKs) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		Object[] finderArgs = new Object[] {
			companyId, creatorClassNameId, StringUtil.merge(creatorClassPKs)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_CCNI_CCPK, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_CCNI_CCPK, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByC_CCNI_CCPK, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_COMPANYID_2 =
		"microblogsEntry.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSNAMEID_2 =
			"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_2 =
		"microblogsEntry.creatorClassPK = ?";

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_CREATORCLASSPK_7 =
		"microblogsEntry.creatorClassPK IN (";

	private FinderPath _finderPathWithPaginationFindByC_CCNI_T;
	private FinderPath _finderPathWithoutPaginationFindByC_CCNI_T;
	private FinderPath _finderPathCountByC_CCNI_T;

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type) {

		return findByC_CCNI_T(
			companyId, creatorClassNameId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end) {

		return findByC_CCNI_T(
			companyId, creatorClassNameId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByC_CCNI_T(
			companyId, creatorClassNameId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_T(
		long companyId, long creatorClassNameId, int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_CCNI_T;
				finderArgs = new Object[] {companyId, creatorClassNameId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_CCNI_T;
			finderArgs = new Object[] {
				companyId, creatorClassNameId, type, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((companyId != microblogsEntry.getCompanyId()) ||
						(creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(type != microblogsEntry.getType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_T_First(
			long companyId, long creatorClassNameId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_T_First(
			companyId, creatorClassNameId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_T_First(
		long companyId, long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByC_CCNI_T(
			companyId, creatorClassNameId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_T_Last(
			long companyId, long creatorClassNameId, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_T_Last(
			companyId, creatorClassNameId, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_T_Last(
		long companyId, long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByC_CCNI_T(companyId, creatorClassNameId, type);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByC_CCNI_T(
			companyId, creatorClassNameId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByC_CCNI_T_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			int type, OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByC_CCNI_T_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId, type,
				orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByC_CCNI_T_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId, type,
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

	protected MicroblogsEntry getByC_CCNI_T_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long companyId,
		long creatorClassNameId, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_CCNI_T_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_CCNI_T_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_CCNI_T_TYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(creatorClassNameId);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 */
	@Override
	public void removeByC_CCNI_T(
		long companyId, long creatorClassNameId, int type) {

		for (MicroblogsEntry microblogsEntry :
				findByC_CCNI_T(
					companyId, creatorClassNameId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByC_CCNI_T(
		long companyId, long creatorClassNameId, int type) {

		FinderPath finderPath = _finderPathCountByC_CCNI_T;

		Object[] finderArgs = new Object[] {
			companyId, creatorClassNameId, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_C_CCNI_T_COMPANYID_2 =
		"microblogsEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CCNI_T_CREATORCLASSNAMEID_2 =
		"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CCNI_T_TYPE_2 =
		"microblogsEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByCCNI_CCPK_T;
	private FinderPath _finderPathWithoutPaginationFindByCCNI_CCPK_T;
	private FinderPath _finderPathCountByCCNI_CCPK_T;
	private FinderPath _finderPathWithPaginationCountByCCNI_CCPK_T;

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCCNI_CCPK_T;
				finderArgs = new Object[] {
					creatorClassNameId, creatorClassPK, type
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCCNI_CCPK_T;
			finderArgs = new Object[] {
				creatorClassNameId, creatorClassPK, type, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(creatorClassPK !=
							microblogsEntry.getCreatorClassPK()) ||
						(type != microblogsEntry.getType())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_CCPK_T_First(
			long creatorClassNameId, long creatorClassPK, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_CCPK_T_First(
			creatorClassNameId, creatorClassPK, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_CCPK_T_First(
		long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByCCNI_CCPK_T_Last(
			long creatorClassNameId, long creatorClassPK, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByCCNI_CCPK_T_Last(
			creatorClassNameId, creatorClassPK, type, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByCCNI_CCPK_T_Last(
		long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPK, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByCCNI_CCPK_T_PrevAndNext(
			long microblogsEntryId, long creatorClassNameId,
			long creatorClassPK, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByCCNI_CCPK_T_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, creatorClassPK,
				type, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByCCNI_CCPK_T_PrevAndNext(
				session, microblogsEntry, creatorClassNameId, creatorClassPK,
				type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByCCNI_CCPK_T_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry,
		long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_2);

		query.append(_FINDER_COLUMN_CCNI_CCPK_T_TYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(creatorClassNameId);

		qPos.add(creatorClassPK);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPKs, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPKs, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByCCNI_CCPK_T(
			creatorClassNameId, creatorClassPKs, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type, int start,
		int end, OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		if (creatorClassPKs.length == 1) {
			return findByCCNI_CCPK_T(
				creatorClassNameId, creatorClassPKs[0], type, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					creatorClassNameId, StringUtil.merge(creatorClassPKs), type
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				creatorClassNameId, StringUtil.merge(creatorClassPKs), type,
				start, end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByCCNI_CCPK_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						!ArrayUtil.contains(
							creatorClassPKs,
							microblogsEntry.getCreatorClassPK()) ||
						(type != microblogsEntry.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_TYPE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByCCNI_CCPK_T, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByCCNI_CCPK_T, finderArgs);
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
	 * Removes all the microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63; from the database.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 */
	@Override
	public void removeByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type) {

		for (MicroblogsEntry microblogsEntry :
				findByCCNI_CCPK_T(
					creatorClassNameId, creatorClassPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCCNI_CCPK_T(
		long creatorClassNameId, long creatorClassPK, int type) {

		FinderPath finderPath = _finderPathCountByCCNI_CCPK_T;

		Object[] finderArgs = new Object[] {
			creatorClassNameId, creatorClassPK, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_2);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				qPos.add(type);

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
	 * Returns the number of microblogs entries where creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByCCNI_CCPK_T(
		long creatorClassNameId, long[] creatorClassPKs, int type) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		Object[] finderArgs = new Object[] {
			creatorClassNameId, StringUtil.merge(creatorClassPKs), type
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByCCNI_CCPK_T, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_CCNI_CCPK_T_TYPE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByCCNI_CCPK_T, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByCCNI_CCPK_T, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSNAMEID_2 =
			"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_2 =
		"microblogsEntry.creatorClassPK = ? AND ";

	private static final String _FINDER_COLUMN_CCNI_CCPK_T_CREATORCLASSPK_7 =
		"microblogsEntry.creatorClassPK IN (";

	private static final String _FINDER_COLUMN_CCNI_CCPK_T_TYPE_2 =
		"microblogsEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_CCNI_CCPK_T;
	private FinderPath _finderPathWithoutPaginationFindByC_CCNI_CCPK_T;
	private FinderPath _finderPathCountByC_CCNI_CCPK_T;
	private FinderPath _finderPathWithPaginationCountByC_CCNI_CCPK_T;

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK,
		int type) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_CCNI_CCPK_T;
				finderArgs = new Object[] {
					companyId, creatorClassNameId, creatorClassPK, type
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_CCNI_CCPK_T;
			finderArgs = new Object[] {
				companyId, creatorClassNameId, creatorClassPK, type, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((companyId != microblogsEntry.getCompanyId()) ||
						(creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						(creatorClassPK !=
							microblogsEntry.getCreatorClassPK()) ||
						(type != microblogsEntry.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_CCPK_T_First(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int type, OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_CCPK_T_First(
			companyId, creatorClassNameId, creatorClassPK, type,
			orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_CCPK_T_First(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByC_CCNI_CCPK_T_Last(
			long companyId, long creatorClassNameId, long creatorClassPK,
			int type, OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByC_CCNI_CCPK_T_Last(
			companyId, creatorClassNameId, creatorClassPK, type,
			orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", creatorClassNameId=");
		msg.append(creatorClassNameId);

		msg.append(", creatorClassPK=");
		msg.append(creatorClassPK);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByC_CCNI_CCPK_T_Last(
		long companyId, long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPK, type, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByC_CCNI_CCPK_T_PrevAndNext(
			long microblogsEntryId, long companyId, long creatorClassNameId,
			long creatorClassPK, int type,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByC_CCNI_CCPK_T_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId,
				creatorClassPK, type, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByC_CCNI_CCPK_T_PrevAndNext(
				session, microblogsEntry, companyId, creatorClassNameId,
				creatorClassPK, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByC_CCNI_CCPK_T_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long companyId,
		long creatorClassNameId, long creatorClassPK, int type,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_2);

		query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(creatorClassNameId);

		qPos.add(creatorClassPK);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPKs, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPKs, type, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByC_CCNI_CCPK_T(
			companyId, creatorClassNameId, creatorClassPKs, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type, int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		if (creatorClassPKs.length == 1) {
			return findByC_CCNI_CCPK_T(
				companyId, creatorClassNameId, creatorClassPKs[0], type, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, creatorClassNameId,
					StringUtil.merge(creatorClassPKs), type
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, creatorClassNameId,
				StringUtil.merge(creatorClassPKs), type, start, end,
				orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByC_CCNI_CCPK_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((companyId != microblogsEntry.getCompanyId()) ||
						(creatorClassNameId !=
							microblogsEntry.getCreatorClassNameId()) ||
						!ArrayUtil.contains(
							creatorClassPKs,
							microblogsEntry.getCreatorClassPK()) ||
						(type != microblogsEntry.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				list = (List<MicroblogsEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_CCNI_CCPK_T,
						finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByC_CCNI_CCPK_T,
						finderArgs);
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
	 * Removes all the microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 */
	@Override
	public void removeByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK,
		int type) {

		for (MicroblogsEntry microblogsEntry :
				findByC_CCNI_CCPK_T(
					companyId, creatorClassNameId, creatorClassPK, type,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPK the creator class pk
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long creatorClassPK,
		int type) {

		FinderPath finderPath = _finderPathCountByC_CCNI_CCPK_T;

		Object[] finderArgs = new Object[] {
			companyId, creatorClassNameId, creatorClassPK, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(creatorClassPK);

				qPos.add(type);

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
	 * Returns the number of microblogs entries where companyId = &#63; and creatorClassNameId = &#63; and creatorClassPK = any &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param creatorClassNameId the creator class name ID
	 * @param creatorClassPKs the creator class pks
	 * @param type the type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByC_CCNI_CCPK_T(
		long companyId, long creatorClassNameId, long[] creatorClassPKs,
		int type) {

		if (creatorClassPKs == null) {
			creatorClassPKs = new long[0];
		}
		else if (creatorClassPKs.length > 1) {
			creatorClassPKs = ArrayUtil.sortedUnique(creatorClassPKs);
		}

		Object[] finderArgs = new Object[] {
			companyId, creatorClassNameId, StringUtil.merge(creatorClassPKs),
			type
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_CCNI_CCPK_T, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2);

			if (creatorClassPKs.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_7);

				query.append(StringUtil.merge(creatorClassPKs));

				query.append(")");

				query.append(")");

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2);

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(creatorClassNameId);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_CCNI_CCPK_T, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByC_CCNI_CCPK_T, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_T_COMPANYID_2 =
		"microblogsEntry.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSNAMEID_2 =
			"microblogsEntry.creatorClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_2 =
		"microblogsEntry.creatorClassPK = ? AND ";

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_T_CREATORCLASSPK_7 =
		"microblogsEntry.creatorClassPK IN (";

	private static final String _FINDER_COLUMN_C_CCNI_CCPK_T_TYPE_2 =
		"microblogsEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByU_C_T_S;
	private FinderPath _finderPathWithoutPaginationFindByU_C_T_S;
	private FinderPath _finderPathCountByU_C_T_S;

	/**
	 * Returns all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @return the matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType) {

		return findByU_C_T_S(
			userId, createDate, type, socialRelationType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end) {

		return findByU_C_T_S(
			userId, createDate, type, socialRelationType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findByU_C_T_S(
			userId, createDate, type, socialRelationType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType,
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_C_T_S;
				finderArgs = new Object[] {
					userId, _getTime(createDate), type, socialRelationType
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_C_T_S;
			finderArgs = new Object[] {
				userId, _getTime(createDate), type, socialRelationType, start,
				end, orderByComparator
			};
		}

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MicroblogsEntry microblogsEntry : list) {
					if ((userId != microblogsEntry.getUserId()) ||
						!Objects.equals(
							createDate, microblogsEntry.getCreateDate()) ||
						(type != microblogsEntry.getType()) ||
						(socialRelationType !=
							microblogsEntry.getSocialRelationType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_T_S_USERID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_U_C_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_T_S_SOCIALRELATIONTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				qPos.add(type);

				qPos.add(socialRelationType);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByU_C_T_S_First(
			long userId, Date createDate, int type, int socialRelationType,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByU_C_T_S_First(
			userId, createDate, type, socialRelationType, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(", type=");
		msg.append(type);

		msg.append(", socialRelationType=");
		msg.append(socialRelationType);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByU_C_T_S_First(
		long userId, Date createDate, int type, int socialRelationType,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		List<MicroblogsEntry> list = findByU_C_T_S(
			userId, createDate, type, socialRelationType, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry
	 * @throws NoSuchEntryException if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry findByU_C_T_S_Last(
			long userId, Date createDate, int type, int socialRelationType,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByU_C_T_S_Last(
			userId, createDate, type, socialRelationType, orderByComparator);

		if (microblogsEntry != null) {
			return microblogsEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(", type=");
		msg.append(type);

		msg.append(", socialRelationType=");
		msg.append(socialRelationType);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching microblogs entry, or <code>null</code> if a matching microblogs entry could not be found
	 */
	@Override
	public MicroblogsEntry fetchByU_C_T_S_Last(
		long userId, Date createDate, int type, int socialRelationType,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		int count = countByU_C_T_S(
			userId, createDate, type, socialRelationType);

		if (count == 0) {
			return null;
		}

		List<MicroblogsEntry> list = findByU_C_T_S(
			userId, createDate, type, socialRelationType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the microblogs entries before and after the current microblogs entry in the ordered set where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param microblogsEntryId the primary key of the current microblogs entry
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry[] findByU_C_T_S_PrevAndNext(
			long microblogsEntryId, long userId, Date createDate, int type,
			int socialRelationType,
			OrderByComparator<MicroblogsEntry> orderByComparator)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = findByPrimaryKey(microblogsEntryId);

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry[] array = new MicroblogsEntryImpl[3];

			array[0] = getByU_C_T_S_PrevAndNext(
				session, microblogsEntry, userId, createDate, type,
				socialRelationType, orderByComparator, true);

			array[1] = microblogsEntry;

			array[2] = getByU_C_T_S_PrevAndNext(
				session, microblogsEntry, userId, createDate, type,
				socialRelationType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MicroblogsEntry getByU_C_T_S_PrevAndNext(
		Session session, MicroblogsEntry microblogsEntry, long userId,
		Date createDate, int type, int socialRelationType,
		OrderByComparator<MicroblogsEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_MICROBLOGSENTRY_WHERE);

		query.append(_FINDER_COLUMN_U_C_T_S_USERID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_2);
		}

		query.append(_FINDER_COLUMN_U_C_T_S_TYPE_2);

		query.append(_FINDER_COLUMN_U_C_T_S_SOCIALRELATIONTYPE_2);

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
			query.append(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (bindCreateDate) {
			qPos.add(new Timestamp(createDate.getTime()));
		}

		qPos.add(type);

		qPos.add(socialRelationType);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						microblogsEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<MicroblogsEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 */
	@Override
	public void removeByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType) {

		for (MicroblogsEntry microblogsEntry :
				findByU_C_T_S(
					userId, createDate, type, socialRelationType,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries where userId = &#63; and createDate = &#63; and type = &#63; and socialRelationType = &#63;.
	 *
	 * @param userId the user ID
	 * @param createDate the create date
	 * @param type the type
	 * @param socialRelationType the social relation type
	 * @return the number of matching microblogs entries
	 */
	@Override
	public int countByU_C_T_S(
		long userId, Date createDate, int type, int socialRelationType) {

		FinderPath finderPath = _finderPathCountByU_C_T_S;

		Object[] finderArgs = new Object[] {
			userId, _getTime(createDate), type, socialRelationType
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_MICROBLOGSENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_T_S_USERID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_U_C_T_S_CREATEDATE_2);
			}

			query.append(_FINDER_COLUMN_U_C_T_S_TYPE_2);

			query.append(_FINDER_COLUMN_U_C_T_S_SOCIALRELATIONTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				qPos.add(type);

				qPos.add(socialRelationType);

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

	private static final String _FINDER_COLUMN_U_C_T_S_USERID_2 =
		"microblogsEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_T_S_CREATEDATE_1 =
		"microblogsEntry.createDate IS NULL AND ";

	private static final String _FINDER_COLUMN_U_C_T_S_CREATEDATE_2 =
		"microblogsEntry.createDate = ? AND ";

	private static final String _FINDER_COLUMN_U_C_T_S_TYPE_2 =
		"microblogsEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_U_C_T_S_SOCIALRELATIONTYPE_2 =
		"microblogsEntry.socialRelationType = ?";

	public MicroblogsEntryPersistenceImpl() {
		setModelClass(MicroblogsEntry.class);

		setModelImplClass(MicroblogsEntryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the microblogs entry in the entity cache if it is enabled.
	 *
	 * @param microblogsEntry the microblogs entry
	 */
	@Override
	public void cacheResult(MicroblogsEntry microblogsEntry) {
		entityCache.putResult(
			entityCacheEnabled, MicroblogsEntryImpl.class,
			microblogsEntry.getPrimaryKey(), microblogsEntry);

		microblogsEntry.resetOriginalValues();
	}

	/**
	 * Caches the microblogs entries in the entity cache if it is enabled.
	 *
	 * @param microblogsEntries the microblogs entries
	 */
	@Override
	public void cacheResult(List<MicroblogsEntry> microblogsEntries) {
		for (MicroblogsEntry microblogsEntry : microblogsEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, MicroblogsEntryImpl.class,
					microblogsEntry.getPrimaryKey()) == null) {

				cacheResult(microblogsEntry);
			}
			else {
				microblogsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all microblogs entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MicroblogsEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the microblogs entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MicroblogsEntry microblogsEntry) {
		entityCache.removeResult(
			entityCacheEnabled, MicroblogsEntryImpl.class,
			microblogsEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<MicroblogsEntry> microblogsEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MicroblogsEntry microblogsEntry : microblogsEntries) {
			entityCache.removeResult(
				entityCacheEnabled, MicroblogsEntryImpl.class,
				microblogsEntry.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, MicroblogsEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new microblogs entry with the primary key. Does not add the microblogs entry to the database.
	 *
	 * @param microblogsEntryId the primary key for the new microblogs entry
	 * @return the new microblogs entry
	 */
	@Override
	public MicroblogsEntry create(long microblogsEntryId) {
		MicroblogsEntry microblogsEntry = new MicroblogsEntryImpl();

		microblogsEntry.setNew(true);
		microblogsEntry.setPrimaryKey(microblogsEntryId);

		microblogsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return microblogsEntry;
	}

	/**
	 * Removes the microblogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry that was removed
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry remove(long microblogsEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)microblogsEntryId);
	}

	/**
	 * Removes the microblogs entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the microblogs entry
	 * @return the microblogs entry that was removed
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			MicroblogsEntry microblogsEntry = (MicroblogsEntry)session.get(
				MicroblogsEntryImpl.class, primaryKey);

			if (microblogsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(microblogsEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected MicroblogsEntry removeImpl(MicroblogsEntry microblogsEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(microblogsEntry)) {
				microblogsEntry = (MicroblogsEntry)session.get(
					MicroblogsEntryImpl.class,
					microblogsEntry.getPrimaryKeyObj());
			}

			if (microblogsEntry != null) {
				session.delete(microblogsEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (microblogsEntry != null) {
			clearCache(microblogsEntry);
		}

		return microblogsEntry;
	}

	@Override
	public MicroblogsEntry updateImpl(MicroblogsEntry microblogsEntry) {
		boolean isNew = microblogsEntry.isNew();

		if (!(microblogsEntry instanceof MicroblogsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(microblogsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					microblogsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in microblogsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MicroblogsEntry implementation " +
					microblogsEntry.getClass());
		}

		MicroblogsEntryModelImpl microblogsEntryModelImpl =
			(MicroblogsEntryModelImpl)microblogsEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (microblogsEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				microblogsEntry.setCreateDate(now);
			}
			else {
				microblogsEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!microblogsEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				microblogsEntry.setModifiedDate(now);
			}
			else {
				microblogsEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (microblogsEntry.isNew()) {
				session.save(microblogsEntry);

				microblogsEntry.setNew(false);
			}
			else {
				microblogsEntry = (MicroblogsEntry)session.merge(
					microblogsEntry);
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
				microblogsEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {microblogsEntryModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			args = new Object[] {
				microblogsEntryModelImpl.getUserId(),
				microblogsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByU_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_T, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getCreatorClassPK()
			};

			finderCache.removeResult(_finderPathCountByCCNI_CCPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCCNI_CCPK, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByCCNI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCCNI_T, args);

			args = new Object[] {
				microblogsEntryModelImpl.getType(),
				microblogsEntryModelImpl.getParentMicroblogsEntryId()
			};

			finderCache.removeResult(_finderPathCountByT_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByT_P, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCompanyId(),
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getCreatorClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_CCNI_CCPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_CCNI_CCPK, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCompanyId(),
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_CCNI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_CCNI_T, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getCreatorClassPK(),
				microblogsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByCCNI_CCPK_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCCNI_CCPK_T, args);

			args = new Object[] {
				microblogsEntryModelImpl.getCompanyId(),
				microblogsEntryModelImpl.getCreatorClassNameId(),
				microblogsEntryModelImpl.getCreatorClassPK(),
				microblogsEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_CCNI_CCPK_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_CCNI_CCPK_T, args);

			args = new Object[] {
				microblogsEntryModelImpl.getUserId(),
				microblogsEntryModelImpl.getCreateDate(),
				microblogsEntryModelImpl.getType(),
				microblogsEntryModelImpl.getSocialRelationType()
			};

			finderCache.removeResult(_finderPathCountByU_C_T_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByU_C_T_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {microblogsEntryModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {microblogsEntryModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalUserId(),
					microblogsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByU_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_T, args);

				args = new Object[] {
					microblogsEntryModelImpl.getUserId(),
					microblogsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByU_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_T, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCCNI_CCPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalCreatorClassPK()
				};

				finderCache.removeResult(_finderPathCountByCCNI_CCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_CCPK, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getCreatorClassPK()
				};

				finderCache.removeResult(_finderPathCountByCCNI_CCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_CCPK, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCCNI_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByCCNI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_T, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByCCNI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_T, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByT_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalType(),
					microblogsEntryModelImpl.
						getOriginalParentMicroblogsEntryId()
				};

				finderCache.removeResult(_finderPathCountByT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByT_P, args);

				args = new Object[] {
					microblogsEntryModelImpl.getType(),
					microblogsEntryModelImpl.getParentMicroblogsEntryId()
				};

				finderCache.removeResult(_finderPathCountByT_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByT_P, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_CCNI_CCPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCompanyId(),
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalCreatorClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_CCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_CCPK, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCompanyId(),
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getCreatorClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_CCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_CCPK, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_CCNI_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCompanyId(),
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_T, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCompanyId(),
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_T, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCCNI_CCPK_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalCreatorClassPK(),
					microblogsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByCCNI_CCPK_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_CCPK_T, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getCreatorClassPK(),
					microblogsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByCCNI_CCPK_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCCNI_CCPK_T, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_CCNI_CCPK_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalCompanyId(),
					microblogsEntryModelImpl.getOriginalCreatorClassNameId(),
					microblogsEntryModelImpl.getOriginalCreatorClassPK(),
					microblogsEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_CCPK_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_CCPK_T, args);

				args = new Object[] {
					microblogsEntryModelImpl.getCompanyId(),
					microblogsEntryModelImpl.getCreatorClassNameId(),
					microblogsEntryModelImpl.getCreatorClassPK(),
					microblogsEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_CCNI_CCPK_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_CCNI_CCPK_T, args);
			}

			if ((microblogsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_C_T_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					microblogsEntryModelImpl.getOriginalUserId(),
					microblogsEntryModelImpl.getOriginalCreateDate(),
					microblogsEntryModelImpl.getOriginalType(),
					microblogsEntryModelImpl.getOriginalSocialRelationType()
				};

				finderCache.removeResult(_finderPathCountByU_C_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_C_T_S, args);

				args = new Object[] {
					microblogsEntryModelImpl.getUserId(),
					microblogsEntryModelImpl.getCreateDate(),
					microblogsEntryModelImpl.getType(),
					microblogsEntryModelImpl.getSocialRelationType()
				};

				finderCache.removeResult(_finderPathCountByU_C_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByU_C_T_S, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, MicroblogsEntryImpl.class,
			microblogsEntry.getPrimaryKey(), microblogsEntry, false);

		microblogsEntry.resetOriginalValues();

		return microblogsEntry;
	}

	/**
	 * Returns the microblogs entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the microblogs entry
	 * @return the microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		MicroblogsEntry microblogsEntry = fetchByPrimaryKey(primaryKey);

		if (microblogsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return microblogsEntry;
	}

	/**
	 * Returns the microblogs entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry
	 * @throws NoSuchEntryException if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry findByPrimaryKey(long microblogsEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)microblogsEntryId);
	}

	/**
	 * Returns the microblogs entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param microblogsEntryId the primary key of the microblogs entry
	 * @return the microblogs entry, or <code>null</code> if a microblogs entry with the primary key could not be found
	 */
	@Override
	public MicroblogsEntry fetchByPrimaryKey(long microblogsEntryId) {
		return fetchByPrimaryKey((Serializable)microblogsEntryId);
	}

	/**
	 * Returns all the microblogs entries.
	 *
	 * @return the microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @return the range of microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findAll(
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the microblogs entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MicroblogsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of microblogs entries
	 * @param end the upper bound of the range of microblogs entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of microblogs entries
	 */
	@Override
	public List<MicroblogsEntry> findAll(
		int start, int end,
		OrderByComparator<MicroblogsEntry> orderByComparator,
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

		List<MicroblogsEntry> list = null;

		if (useFinderCache) {
			list = (List<MicroblogsEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_MICROBLOGSENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MICROBLOGSENTRY;

				sql = sql.concat(MicroblogsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<MicroblogsEntry>)QueryUtil.list(
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
	 * Removes all the microblogs entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MicroblogsEntry microblogsEntry : findAll()) {
			remove(microblogsEntry);
		}
	}

	/**
	 * Returns the number of microblogs entries.
	 *
	 * @return the number of microblogs entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MICROBLOGSENTRY);

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
		return "microblogsEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MICROBLOGSENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MicroblogsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the microblogs entry persistence.
	 */
	@Activate
	public void activate() {
		MicroblogsEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		MicroblogsEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			MicroblogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			MicroblogsEntryModelImpl.USERID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByU_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			MicroblogsEntryModelImpl.USERID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByU_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByCCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCCNI_CCPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSPK_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCCNI_CCPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationCountByCCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByCCNI_CCPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCCNI_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCCNI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCCNI_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_P",
			new String[] {Integer.class.getName(), Long.class.getName()},
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.PARENTMICROBLOGSENTRYID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByT_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_P",
			new String[] {Integer.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_CCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_CCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			MicroblogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSPK_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByC_CCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationCountByC_CCNI_CCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_CCNI_CCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_CCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CCNI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_CCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CCNI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MicroblogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByC_CCNI_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CCNI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByCCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSPK_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationCountByCCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByCCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_CCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_CCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			MicroblogsEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSNAMEID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATORCLASSPK_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByC_CCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationCountByC_CCNI_CCPK_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_CCNI_CCPK_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByU_C_T_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_C_T_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_C_T_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, MicroblogsEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_C_T_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			MicroblogsEntryModelImpl.USERID_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.CREATEDATE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.TYPE_COLUMN_BITMASK |
			MicroblogsEntryModelImpl.SOCIALRELATIONTYPE_COLUMN_BITMASK);

		_finderPathCountByU_C_T_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C_T_S",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MicroblogsEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = MicroblogsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.microblogs.model.MicroblogsEntry"),
			true);
	}

	@Override
	@Reference(
		target = MicroblogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MicroblogsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_MICROBLOGSENTRY =
		"SELECT microblogsEntry FROM MicroblogsEntry microblogsEntry";

	private static final String _SQL_SELECT_MICROBLOGSENTRY_WHERE =
		"SELECT microblogsEntry FROM MicroblogsEntry microblogsEntry WHERE ";

	private static final String _SQL_COUNT_MICROBLOGSENTRY =
		"SELECT COUNT(microblogsEntry) FROM MicroblogsEntry microblogsEntry";

	private static final String _SQL_COUNT_MICROBLOGSENTRY_WHERE =
		"SELECT COUNT(microblogsEntry) FROM MicroblogsEntry microblogsEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "microblogsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MicroblogsEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MicroblogsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MicroblogsEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	static {
		try {
			Class.forName(MicroblogsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}