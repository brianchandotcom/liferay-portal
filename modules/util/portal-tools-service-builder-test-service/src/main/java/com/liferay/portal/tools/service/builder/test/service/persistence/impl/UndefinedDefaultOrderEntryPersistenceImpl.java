/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchUndefinedDefaultOrderEntryException;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntry;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.UndefinedDefaultOrderEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.UndefinedDefaultOrderEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the undefined default order entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UndefinedDefaultOrderEntryPersistenceImpl
	extends BasePersistenceImpl<UndefinedDefaultOrderEntry>
	implements UndefinedDefaultOrderEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UndefinedDefaultOrderEntryUtil</code> to access the undefined default order entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UndefinedDefaultOrderEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByName;

	/**
	 * Returns the undefined default order entry where name = &#63; or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry findByName(String name)
		throws NoSuchUndefinedDefaultOrderEntryException {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry = fetchByName(
			name);

		if (undefinedDefaultOrderEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUndefinedDefaultOrderEntryException(sb.toString());
		}

		return undefinedDefaultOrderEntry;
	}

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the undefined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry fetchByName(
		String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByName, finderArgs, this);
		}

		if (result instanceof UndefinedDefaultOrderEntry) {
			UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
				(UndefinedDefaultOrderEntry)result;

			if (!Objects.equals(name, undefinedDefaultOrderEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				List<UndefinedDefaultOrderEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByName, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {name};
							}

							_log.warn(
								"UndefinedDefaultOrderEntryPersistenceImpl.fetchByName(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
						list.get(0);

					result = undefinedDefaultOrderEntry;

					cacheResult(undefinedDefaultOrderEntry);
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
			return (UndefinedDefaultOrderEntry)result;
		}
	}

	/**
	 * Removes the undefined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the undefined default order entry that was removed
	 */
	@Override
	public UndefinedDefaultOrderEntry removeByName(String name)
		throws NoSuchUndefinedDefaultOrderEntryException {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry = findByName(
			name);

		return remove(undefinedDefaultOrderEntry);
	}

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	@Override
	public int countByName(String name) {
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry = fetchByName(
			name);

		if (undefinedDefaultOrderEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"undefinedDefaultOrderEntry.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(undefinedDefaultOrderEntry.name IS NULL OR undefinedDefaultOrderEntry.name = '')";

	private FinderPath _finderPathWithPaginationFindByName_Collection;
	private FinderPath _finderPathWithoutPaginationFindByName_Collection;
	private FinderPath _finderPathCountByName_Collection;

	/**
	 * Returns all the undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findByName_Collection(String name) {
		return findByName_Collection(
			name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of matching undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end) {

		return findByName_Collection(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return findByName_Collection(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByName_Collection;
				finderArgs = new Object[] {name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByName_Collection;
			finderArgs = new Object[] {name, start, end, orderByComparator};
		}

		List<UndefinedDefaultOrderEntry> list = null;

		if (useFinderCache) {
			list = (List<UndefinedDefaultOrderEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (UndefinedDefaultOrderEntry undefinedDefaultOrderEntry :
						list) {

					if (!name.equals(undefinedDefaultOrderEntry.getName())) {
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

			sb.append(_SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UndefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<UndefinedDefaultOrderEntry>)QueryUtil.list(
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
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			fetchByName_Collection_First(name, orderByComparator);

		if (undefinedDefaultOrderEntry != null) {
			return undefinedDefaultOrderEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchUndefinedDefaultOrderEntryException(sb.toString());
	}

	/**
	 * Returns the first undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		List<UndefinedDefaultOrderEntry> list = findByName_Collection(
			name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry findByName_Collection_Last(
			String name,
			OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			fetchByName_Collection_Last(name, orderByComparator);

		if (undefinedDefaultOrderEntry != null) {
			return undefinedDefaultOrderEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchUndefinedDefaultOrderEntryException(sb.toString());
	}

	/**
	 * Returns the last undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching undefined default order entry, or <code>null</code> if a matching undefined default order entry could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry fetchByName_Collection_Last(
		String name,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		int count = countByName_Collection(name);

		if (count == 0) {
			return null;
		}

		List<UndefinedDefaultOrderEntry> list = findByName_Collection(
			name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the undefined default order entries before and after the current undefined default order entry in the ordered set where name = &#63;.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the current undefined default order entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry[] findByName_Collection_PrevAndNext(
			long undefinedDefaultOrderEntryId, String name,
			OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchUndefinedDefaultOrderEntryException {

		name = Objects.toString(name, "");

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			findByPrimaryKey(undefinedDefaultOrderEntryId);

		Session session = null;

		try {
			session = openSession();

			UndefinedDefaultOrderEntry[] array =
				new UndefinedDefaultOrderEntryImpl[3];

			array[0] = getByName_Collection_PrevAndNext(
				session, undefinedDefaultOrderEntry, name, orderByComparator,
				true);

			array[1] = undefinedDefaultOrderEntry;

			array[2] = getByName_Collection_PrevAndNext(
				session, undefinedDefaultOrderEntry, name, orderByComparator,
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

	protected UndefinedDefaultOrderEntry getByName_Collection_PrevAndNext(
		Session session, UndefinedDefaultOrderEntry undefinedDefaultOrderEntry,
		String name,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY_WHERE);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_2);
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
			sb.append(UndefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						undefinedDefaultOrderEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<UndefinedDefaultOrderEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the undefined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName_Collection(String name) {
		for (UndefinedDefaultOrderEntry undefinedDefaultOrderEntry :
				findByName_Collection(
					name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(undefinedDefaultOrderEntry);
		}
	}

	/**
	 * Returns the number of undefined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching undefined default order entries
	 */
	@Override
	public int countByName_Collection(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName_Collection;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_UNDEFINEDDEFAULTORDERENTRY_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_NAME_COLLECTION_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_NAME_COLLECTION_NAME_2 =
		"undefinedDefaultOrderEntry.name = ?";

	private static final String _FINDER_COLUMN_NAME_COLLECTION_NAME_3 =
		"(undefinedDefaultOrderEntry.name IS NULL OR undefinedDefaultOrderEntry.name = '')";

	public UndefinedDefaultOrderEntryPersistenceImpl() {
		setModelClass(UndefinedDefaultOrderEntry.class);

		setModelImplClass(UndefinedDefaultOrderEntryImpl.class);
		setModelPKClass(long.class);

		setTable(UndefinedDefaultOrderEntryTable.INSTANCE);
	}

	/**
	 * Caches the undefined default order entry in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntry the undefined default order entry
	 */
	@Override
	public void cacheResult(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		entityCache.putResult(
			UndefinedDefaultOrderEntryImpl.class,
			undefinedDefaultOrderEntry.getPrimaryKey(),
			undefinedDefaultOrderEntry);

		finderCache.putResult(
			_finderPathFetchByName,
			new Object[] {undefinedDefaultOrderEntry.getName()},
			undefinedDefaultOrderEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the undefined default order entries in the entity cache if it is enabled.
	 *
	 * @param undefinedDefaultOrderEntries the undefined default order entries
	 */
	@Override
	public void cacheResult(
		List<UndefinedDefaultOrderEntry> undefinedDefaultOrderEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (undefinedDefaultOrderEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (UndefinedDefaultOrderEntry undefinedDefaultOrderEntry :
				undefinedDefaultOrderEntries) {

			if (entityCache.getResult(
					UndefinedDefaultOrderEntryImpl.class,
					undefinedDefaultOrderEntry.getPrimaryKey()) == null) {

				cacheResult(undefinedDefaultOrderEntry);
			}
		}
	}

	/**
	 * Clears the cache for all undefined default order entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UndefinedDefaultOrderEntryImpl.class);

		finderCache.clearCache(UndefinedDefaultOrderEntryImpl.class);
	}

	/**
	 * Clears the cache for the undefined default order entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		entityCache.removeResult(
			UndefinedDefaultOrderEntryImpl.class, undefinedDefaultOrderEntry);
	}

	@Override
	public void clearCache(
		List<UndefinedDefaultOrderEntry> undefinedDefaultOrderEntries) {

		for (UndefinedDefaultOrderEntry undefinedDefaultOrderEntry :
				undefinedDefaultOrderEntries) {

			entityCache.removeResult(
				UndefinedDefaultOrderEntryImpl.class,
				undefinedDefaultOrderEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(UndefinedDefaultOrderEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				UndefinedDefaultOrderEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UndefinedDefaultOrderEntryModelImpl
			undefinedDefaultOrderEntryModelImpl) {

		Object[] args = new Object[] {
			undefinedDefaultOrderEntryModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByName, args, undefinedDefaultOrderEntryModelImpl);
	}

	/**
	 * Creates a new undefined default order entry with the primary key. Does not add the undefined default order entry to the database.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key for the new undefined default order entry
	 * @return the new undefined default order entry
	 */
	@Override
	public UndefinedDefaultOrderEntry create(
		long undefinedDefaultOrderEntryId) {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			new UndefinedDefaultOrderEntryImpl();

		undefinedDefaultOrderEntry.setNew(true);
		undefinedDefaultOrderEntry.setPrimaryKey(undefinedDefaultOrderEntryId);

		return undefinedDefaultOrderEntry;
	}

	/**
	 * Removes the undefined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry that was removed
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry remove(long undefinedDefaultOrderEntryId)
		throws NoSuchUndefinedDefaultOrderEntryException {

		return remove((Serializable)undefinedDefaultOrderEntryId);
	}

	/**
	 * Removes the undefined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the undefined default order entry
	 * @return the undefined default order entry that was removed
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry remove(Serializable primaryKey)
		throws NoSuchUndefinedDefaultOrderEntryException {

		Session session = null;

		try {
			session = openSession();

			UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
				(UndefinedDefaultOrderEntry)session.get(
					UndefinedDefaultOrderEntryImpl.class, primaryKey);

			if (undefinedDefaultOrderEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUndefinedDefaultOrderEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(undefinedDefaultOrderEntry);
		}
		catch (NoSuchUndefinedDefaultOrderEntryException
					noSuchEntityException) {

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
	protected UndefinedDefaultOrderEntry removeImpl(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(undefinedDefaultOrderEntry)) {
				undefinedDefaultOrderEntry =
					(UndefinedDefaultOrderEntry)session.get(
						UndefinedDefaultOrderEntryImpl.class,
						undefinedDefaultOrderEntry.getPrimaryKeyObj());
			}

			if (undefinedDefaultOrderEntry != null) {
				session.delete(undefinedDefaultOrderEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (undefinedDefaultOrderEntry != null) {
			clearCache(undefinedDefaultOrderEntry);
		}

		return undefinedDefaultOrderEntry;
	}

	@Override
	public UndefinedDefaultOrderEntry updateImpl(
		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

		boolean isNew = undefinedDefaultOrderEntry.isNew();

		if (!(undefinedDefaultOrderEntry instanceof
				UndefinedDefaultOrderEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(undefinedDefaultOrderEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					undefinedDefaultOrderEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in undefinedDefaultOrderEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom UndefinedDefaultOrderEntry implementation " +
					undefinedDefaultOrderEntry.getClass());
		}

		UndefinedDefaultOrderEntryModelImpl
			undefinedDefaultOrderEntryModelImpl =
				(UndefinedDefaultOrderEntryModelImpl)undefinedDefaultOrderEntry;

		if (!undefinedDefaultOrderEntryModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				undefinedDefaultOrderEntry.setModifiedDate(date);
			}
			else {
				undefinedDefaultOrderEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(undefinedDefaultOrderEntry);
			}
			else {
				undefinedDefaultOrderEntry =
					(UndefinedDefaultOrderEntry)session.merge(
						undefinedDefaultOrderEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			UndefinedDefaultOrderEntryImpl.class,
			undefinedDefaultOrderEntryModelImpl, false, true);

		cacheUniqueFindersCache(undefinedDefaultOrderEntryModelImpl);

		if (isNew) {
			undefinedDefaultOrderEntry.setNew(false);
		}

		undefinedDefaultOrderEntry.resetOriginalValues();

		return undefinedDefaultOrderEntry;
	}

	/**
	 * Returns the undefined default order entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the undefined default order entry
	 * @return the undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUndefinedDefaultOrderEntryException {

		UndefinedDefaultOrderEntry undefinedDefaultOrderEntry =
			fetchByPrimaryKey(primaryKey);

		if (undefinedDefaultOrderEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUndefinedDefaultOrderEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return undefinedDefaultOrderEntry;
	}

	/**
	 * Returns the undefined default order entry with the primary key or throws a <code>NoSuchUndefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry
	 * @throws NoSuchUndefinedDefaultOrderEntryException if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry findByPrimaryKey(
			long undefinedDefaultOrderEntryId)
		throws NoSuchUndefinedDefaultOrderEntryException {

		return findByPrimaryKey((Serializable)undefinedDefaultOrderEntryId);
	}

	/**
	 * Returns the undefined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param undefinedDefaultOrderEntryId the primary key of the undefined default order entry
	 * @return the undefined default order entry, or <code>null</code> if a undefined default order entry with the primary key could not be found
	 */
	@Override
	public UndefinedDefaultOrderEntry fetchByPrimaryKey(
		long undefinedDefaultOrderEntryId) {

		return fetchByPrimaryKey((Serializable)undefinedDefaultOrderEntryId);
	}

	/**
	 * Returns all the undefined default order entries.
	 *
	 * @return the undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @return the range of undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the undefined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UndefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of undefined default order entries
	 * @param end the upper bound of the range of undefined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of undefined default order entries
	 */
	@Override
	public List<UndefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<UndefinedDefaultOrderEntry> orderByComparator,
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

		List<UndefinedDefaultOrderEntry> list = null;

		if (useFinderCache) {
			list = (List<UndefinedDefaultOrderEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY;

				sql = sql.concat(
					UndefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<UndefinedDefaultOrderEntry>)QueryUtil.list(
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
	 * Removes all the undefined default order entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (UndefinedDefaultOrderEntry undefinedDefaultOrderEntry :
				findAll()) {

			remove(undefinedDefaultOrderEntry);
		}
	}

	/**
	 * Returns the number of undefined default order entries.
	 *
	 * @return the number of undefined default order entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_UNDEFINEDDEFAULTORDERENTRY);

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
		return "undefinedDefaultOrderEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UndefinedDefaultOrderEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the undefined default order entry persistence.
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

		_finderPathFetchByName = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByName",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathWithPaginationFindByName_Collection = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByName_Collection",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"name"}, true);

		_finderPathWithoutPaginationFindByName_Collection = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByName_Collection",
			new String[] {String.class.getName()}, new String[] {"name"}, true);

		_finderPathCountByName_Collection = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByName_Collection",
			new String[] {String.class.getName()}, new String[] {"name"},
			false);

		UndefinedDefaultOrderEntryUtil.setPersistence(this);
	}

	public void destroy() {
		UndefinedDefaultOrderEntryUtil.setPersistence(null);

		entityCache.removeCache(UndefinedDefaultOrderEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY =
		"SELECT undefinedDefaultOrderEntry FROM UndefinedDefaultOrderEntry undefinedDefaultOrderEntry";

	private static final String _SQL_SELECT_UNDEFINEDDEFAULTORDERENTRY_WHERE =
		"SELECT undefinedDefaultOrderEntry FROM UndefinedDefaultOrderEntry undefinedDefaultOrderEntry WHERE ";

	private static final String _SQL_COUNT_UNDEFINEDDEFAULTORDERENTRY =
		"SELECT COUNT(undefinedDefaultOrderEntry) FROM UndefinedDefaultOrderEntry undefinedDefaultOrderEntry";

	private static final String _SQL_COUNT_UNDEFINEDDEFAULTORDERENTRY_WHERE =
		"SELECT COUNT(undefinedDefaultOrderEntry) FROM UndefinedDefaultOrderEntry undefinedDefaultOrderEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"undefinedDefaultOrderEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No UndefinedDefaultOrderEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No UndefinedDefaultOrderEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UndefinedDefaultOrderEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1818172464