/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.service.persistence.impl;

import com.liferay.akismet.exception.NoSuchAkismetEntryException;
import com.liferay.akismet.model.AkismetEntry;
import com.liferay.akismet.model.AkismetEntryTable;
import com.liferay.akismet.model.impl.AkismetEntryImpl;
import com.liferay.akismet.model.impl.AkismetEntryModelImpl;
import com.liferay.akismet.service.persistence.AkismetEntryPersistence;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the akismet entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Jamie Sammons
 * @generated
 */
public class AkismetEntryPersistenceImpl
	extends BasePersistenceImpl<AkismetEntry>
	implements AkismetEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AkismetEntryUtil</code> to access the akismet entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AkismetEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLtModifiedDate;
	private FinderPath _finderPathWithPaginationCountByLtModifiedDate;

	/**
	 * Returns all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the matching akismet entries
	 */
	@Override
	public List<AkismetEntry> findByLtModifiedDate(Date modifiedDate) {
		return findByLtModifiedDate(
			modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of matching akismet entries
	 */
	@Override
	public List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end) {

		return findByLtModifiedDate(modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching akismet entries
	 */
	@Override
	public List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		OrderByComparator<AkismetEntry> orderByComparator) {

		return findByLtModifiedDate(
			modifiedDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the akismet entries where modifiedDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching akismet entries
	 */
	@Override
	public List<AkismetEntry> findByLtModifiedDate(
		Date modifiedDate, int start, int end,
		OrderByComparator<AkismetEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtModifiedDate;
		finderArgs = new Object[] {
			_getTime(modifiedDate), start, end, orderByComparator
		};

		List<AkismetEntry> list = null;

		if (useFinderCache) {
			list = (List<AkismetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AkismetEntry akismetEntry : list) {
					if (modifiedDate.getTime() <= akismetEntry.getModifiedDate(
						).getTime()) {

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

			sb.append(_SQL_SELECT_AKISMETENTRY_WHERE);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AkismetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				list = (List<AkismetEntry>)QueryUtil.list(
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
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry findByLtModifiedDate_First(
			Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = fetchByLtModifiedDate_First(
			modifiedDate, orderByComparator);

		if (akismetEntry != null) {
			return akismetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate<");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchAkismetEntryException(sb.toString());
	}

	/**
	 * Returns the first akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry fetchByLtModifiedDate_First(
		Date modifiedDate, OrderByComparator<AkismetEntry> orderByComparator) {

		List<AkismetEntry> list = findByLtModifiedDate(
			modifiedDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry findByLtModifiedDate_Last(
			Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = fetchByLtModifiedDate_Last(
			modifiedDate, orderByComparator);

		if (akismetEntry != null) {
			return akismetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("modifiedDate<");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchAkismetEntryException(sb.toString());
	}

	/**
	 * Returns the last akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry fetchByLtModifiedDate_Last(
		Date modifiedDate, OrderByComparator<AkismetEntry> orderByComparator) {

		int count = countByLtModifiedDate(modifiedDate);

		if (count == 0) {
			return null;
		}

		List<AkismetEntry> list = findByLtModifiedDate(
			modifiedDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the akismet entries before and after the current akismet entry in the ordered set where modifiedDate &lt; &#63;.
	 *
	 * @param akismetEntryId the primary key of the current akismet entry
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry[] findByLtModifiedDate_PrevAndNext(
			long akismetEntryId, Date modifiedDate,
			OrderByComparator<AkismetEntry> orderByComparator)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = findByPrimaryKey(akismetEntryId);

		Session session = null;

		try {
			session = openSession();

			AkismetEntry[] array = new AkismetEntryImpl[3];

			array[0] = getByLtModifiedDate_PrevAndNext(
				session, akismetEntry, modifiedDate, orderByComparator, true);

			array[1] = akismetEntry;

			array[2] = getByLtModifiedDate_PrevAndNext(
				session, akismetEntry, modifiedDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AkismetEntry getByLtModifiedDate_PrevAndNext(
		Session session, AkismetEntry akismetEntry, Date modifiedDate,
		OrderByComparator<AkismetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_AKISMETENTRY_WHERE);

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_2);
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
			sb.append(AkismetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindModifiedDate) {
			queryPos.add(new Timestamp(modifiedDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(akismetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AkismetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the akismet entries where modifiedDate &lt; &#63; from the database.
	 *
	 * @param modifiedDate the modified date
	 */
	@Override
	public void removeByLtModifiedDate(Date modifiedDate) {
		for (AkismetEntry akismetEntry :
				findByLtModifiedDate(
					modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(akismetEntry);
		}
	}

	/**
	 * Returns the number of akismet entries where modifiedDate &lt; &#63;.
	 *
	 * @param modifiedDate the modified date
	 * @return the number of matching akismet entries
	 */
	@Override
	public int countByLtModifiedDate(Date modifiedDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtModifiedDate;

		Object[] finderArgs = new Object[] {_getTime(modifiedDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_AKISMETENTRY_WHERE);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_1 =
		"akismetEntry.modifiedDate IS NULL";

	private static final String _FINDER_COLUMN_LTMODIFIEDDATE_MODIFIEDDATE_2 =
		"akismetEntry.modifiedDate < ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry
	 * @throws NoSuchAkismetEntryException if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = fetchByC_C(classNameId, classPK);

		if (akismetEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAkismetEntryException(sb.toString());
		}

		return akismetEntry;
	}

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the akismet entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching akismet entry, or <code>null</code> if a matching akismet entry could not be found
	 */
	@Override
	public AkismetEntry fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C, finderArgs);
		}

		if (result instanceof AkismetEntry) {
			AkismetEntry akismetEntry = (AkismetEntry)result;

			if ((classNameId != akismetEntry.getClassNameId()) ||
				(classPK != akismetEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_AKISMETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<AkismetEntry> list = query.list();

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
								finderArgs = new Object[] {
									classNameId, classPK
								};
							}

							_log.warn(
								"AkismetEntryPersistenceImpl.fetchByC_C(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AkismetEntry akismetEntry = list.get(0);

					result = akismetEntry;

					cacheResult(akismetEntry);
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
			return (AkismetEntry)result;
		}
	}

	/**
	 * Removes the akismet entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the akismet entry that was removed
	 */
	@Override
	public AkismetEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = findByC_C(classNameId, classPK);

		return remove(akismetEntry);
	}

	/**
	 * Returns the number of akismet entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching akismet entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_AKISMETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"akismetEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"akismetEntry.classPK = ?";

	public AkismetEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AkismetEntry.class);

		setModelImplClass(AkismetEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AkismetEntryTable.INSTANCE);
	}

	/**
	 * Caches the akismet entry in the entity cache if it is enabled.
	 *
	 * @param akismetEntry the akismet entry
	 */
	@Override
	public void cacheResult(AkismetEntry akismetEntry) {
		entityCache.putResult(
			AkismetEntryImpl.class, akismetEntry.getPrimaryKey(), akismetEntry);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				akismetEntry.getClassNameId(), akismetEntry.getClassPK()
			},
			akismetEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the akismet entries in the entity cache if it is enabled.
	 *
	 * @param akismetEntries the akismet entries
	 */
	@Override
	public void cacheResult(List<AkismetEntry> akismetEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (akismetEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AkismetEntry akismetEntry : akismetEntries) {
			if (entityCache.getResult(
					AkismetEntryImpl.class, akismetEntry.getPrimaryKey()) ==
						null) {

				cacheResult(akismetEntry);
			}
		}
	}

	/**
	 * Clears the cache for all akismet entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AkismetEntryImpl.class);

		finderCache.clearCache(AkismetEntryImpl.class);
	}

	/**
	 * Clears the cache for the akismet entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AkismetEntry akismetEntry) {
		entityCache.removeResult(AkismetEntryImpl.class, akismetEntry);
	}

	@Override
	public void clearCache(List<AkismetEntry> akismetEntries) {
		for (AkismetEntry akismetEntry : akismetEntries) {
			entityCache.removeResult(AkismetEntryImpl.class, akismetEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AkismetEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AkismetEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AkismetEntryModelImpl akismetEntryModelImpl) {

		Object[] args = new Object[] {
			akismetEntryModelImpl.getClassNameId(),
			akismetEntryModelImpl.getClassPK()
		};

		finderCache.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C, args, akismetEntryModelImpl);
	}

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	@Override
	public AkismetEntry create(long akismetEntryId) {
		AkismetEntry akismetEntry = new AkismetEntryImpl();

		akismetEntry.setNew(true);
		akismetEntry.setPrimaryKey(akismetEntryId);

		return akismetEntry;
	}

	/**
	 * Removes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry remove(long akismetEntryId)
		throws NoSuchAkismetEntryException {

		return remove((Serializable)akismetEntryId);
	}

	/**
	 * Removes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry remove(Serializable primaryKey)
		throws NoSuchAkismetEntryException {

		Session session = null;

		try {
			session = openSession();

			AkismetEntry akismetEntry = (AkismetEntry)session.get(
				AkismetEntryImpl.class, primaryKey);

			if (akismetEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAkismetEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(akismetEntry);
		}
		catch (NoSuchAkismetEntryException noSuchEntityException) {
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
	protected AkismetEntry removeImpl(AkismetEntry akismetEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(akismetEntry)) {
				akismetEntry = (AkismetEntry)session.get(
					AkismetEntryImpl.class, akismetEntry.getPrimaryKeyObj());
			}

			if (akismetEntry != null) {
				session.delete(akismetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (akismetEntry != null) {
			clearCache(akismetEntry);
		}

		return akismetEntry;
	}

	@Override
	public AkismetEntry updateImpl(AkismetEntry akismetEntry) {
		boolean isNew = akismetEntry.isNew();

		if (!(akismetEntry instanceof AkismetEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(akismetEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					akismetEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in akismetEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AkismetEntry implementation " +
					akismetEntry.getClass());
		}

		AkismetEntryModelImpl akismetEntryModelImpl =
			(AkismetEntryModelImpl)akismetEntry;

		if (!akismetEntryModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				akismetEntry.setModifiedDate(date);
			}
			else {
				akismetEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(akismetEntry);
			}
			else {
				akismetEntry = (AkismetEntry)session.merge(akismetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AkismetEntryImpl.class, akismetEntryModelImpl, false, true);

		cacheUniqueFindersCache(akismetEntryModelImpl);

		if (isNew) {
			akismetEntry.setNew(false);
		}

		akismetEntry.resetOriginalValues();

		return akismetEntry;
	}

	/**
	 * Returns the akismet entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAkismetEntryException {

		AkismetEntry akismetEntry = fetchByPrimaryKey(primaryKey);

		if (akismetEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAkismetEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return akismetEntry;
	}

	/**
	 * Returns the akismet entry with the primary key or throws a <code>NoSuchAkismetEntryException</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws NoSuchAkismetEntryException if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry findByPrimaryKey(long akismetEntryId)
		throws NoSuchAkismetEntryException {

		return findByPrimaryKey((Serializable)akismetEntryId);
	}

	/**
	 * Returns the akismet entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry, or <code>null</code> if a akismet entry with the primary key could not be found
	 */
	@Override
	public AkismetEntry fetchByPrimaryKey(long akismetEntryId) {
		return fetchByPrimaryKey((Serializable)akismetEntryId);
	}

	/**
	 * Returns all the akismet entries.
	 *
	 * @return the akismet entries
	 */
	@Override
	public List<AkismetEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of akismet entries
	 */
	@Override
	public List<AkismetEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of akismet entries
	 */
	@Override
	public List<AkismetEntry> findAll(
		int start, int end, OrderByComparator<AkismetEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of akismet entries
	 */
	@Override
	public List<AkismetEntry> findAll(
		int start, int end, OrderByComparator<AkismetEntry> orderByComparator,
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

		List<AkismetEntry> list = null;

		if (useFinderCache) {
			list = (List<AkismetEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_AKISMETENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_AKISMETENTRY;

				sql = sql.concat(AkismetEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AkismetEntry>)QueryUtil.list(
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
	 * Removes all the akismet entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AkismetEntry akismetEntry : findAll()) {
			remove(akismetEntry);
		}
	}

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_AKISMETENTRY);

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
		return "akismetEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AKISMETENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AkismetEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the akismet entry persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByLtModifiedDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtModifiedDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"modifiedDate"}, true);

		_finderPathWithPaginationCountByLtModifiedDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtModifiedDate",
			new String[] {Date.class.getName()}, new String[] {"modifiedDate"},
			false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);
	}

	public void destroy() {
		entityCache.removeCache(AkismetEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_AKISMETENTRY =
		"SELECT akismetEntry FROM AkismetEntry akismetEntry";

	private static final String _SQL_SELECT_AKISMETENTRY_WHERE =
		"SELECT akismetEntry FROM AkismetEntry akismetEntry WHERE ";

	private static final String _SQL_COUNT_AKISMETENTRY =
		"SELECT COUNT(akismetEntry) FROM AkismetEntry akismetEntry";

	private static final String _SQL_COUNT_AKISMETENTRY_WHERE =
		"SELECT COUNT(akismetEntry) FROM AkismetEntry akismetEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "akismetEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AkismetEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AkismetEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AkismetEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}