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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDefinedDefaultOrderEntryException;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefinedDefaultOrderEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefinedDefaultOrderEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the defined default order entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DefinedDefaultOrderEntryPersistenceImpl
	extends BasePersistenceImpl<DefinedDefaultOrderEntry>
	implements DefinedDefaultOrderEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DefinedDefaultOrderEntryUtil</code> to access the defined default order entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DefinedDefaultOrderEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByName;

	/**
	 * Returns the defined default order entry where name = &#63; or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry findByName(String name)
		throws NoSuchDefinedDefaultOrderEntryException {

		DefinedDefaultOrderEntry definedDefaultOrderEntry = fetchByName(name);

		if (definedDefaultOrderEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDefinedDefaultOrderEntryException(sb.toString());
		}

		return definedDefaultOrderEntry;
	}

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry fetchByName(String name) {
		return fetchByName(name, true);
	}

	/**
	 * Returns the defined default order entry where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry fetchByName(
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

		if (result instanceof DefinedDefaultOrderEntry) {
			DefinedDefaultOrderEntry definedDefaultOrderEntry =
				(DefinedDefaultOrderEntry)result;

			if (!Objects.equals(name, definedDefaultOrderEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_DEFINEDDEFAULTORDERENTRY_WHERE);

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

				List<DefinedDefaultOrderEntry> list = query.list();

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
								"DefinedDefaultOrderEntryPersistenceImpl.fetchByName(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					DefinedDefaultOrderEntry definedDefaultOrderEntry =
						list.get(0);

					result = definedDefaultOrderEntry;

					cacheResult(definedDefaultOrderEntry);
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
			return (DefinedDefaultOrderEntry)result;
		}
	}

	/**
	 * Removes the defined default order entry where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the defined default order entry that was removed
	 */
	@Override
	public DefinedDefaultOrderEntry removeByName(String name)
		throws NoSuchDefinedDefaultOrderEntryException {

		DefinedDefaultOrderEntry definedDefaultOrderEntry = findByName(name);

		return remove(definedDefaultOrderEntry);
	}

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	@Override
	public int countByName(String name) {
		DefinedDefaultOrderEntry definedDefaultOrderEntry = fetchByName(name);

		if (definedDefaultOrderEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_NAME_NAME_2 =
		"definedDefaultOrderEntry.name = ?";

	private static final String _FINDER_COLUMN_NAME_NAME_3 =
		"(definedDefaultOrderEntry.name IS NULL OR definedDefaultOrderEntry.name = '')";

	private FinderPath _finderPathWithPaginationFindByName_Collection;
	private FinderPath _finderPathWithoutPaginationFindByName_Collection;
	private FinderPath _finderPathCountByName_Collection;

	/**
	 * Returns all the defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the matching defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findByName_Collection(String name) {
		return findByName_Collection(
			name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of matching defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end) {

		return findByName_Collection(name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return findByName_Collection(name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the defined default order entries where name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param name the name
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findByName_Collection(
		String name, int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator,
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

		List<DefinedDefaultOrderEntry> list = null;

		if (useFinderCache) {
			list = (List<DefinedDefaultOrderEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DefinedDefaultOrderEntry definedDefaultOrderEntry : list) {
					if (!name.equals(definedDefaultOrderEntry.getName())) {
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

			sb.append(_SQL_SELECT_DEFINEDDEFAULTORDERENTRY_WHERE);

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
				sb.append(DefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DefinedDefaultOrderEntry>)QueryUtil.list(
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
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry findByName_Collection_First(
			String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchDefinedDefaultOrderEntryException {

		DefinedDefaultOrderEntry definedDefaultOrderEntry =
			fetchByName_Collection_First(name, orderByComparator);

		if (definedDefaultOrderEntry != null) {
			return definedDefaultOrderEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchDefinedDefaultOrderEntryException(sb.toString());
	}

	/**
	 * Returns the first defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry fetchByName_Collection_First(
		String name,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		List<DefinedDefaultOrderEntry> list = findByName_Collection(
			name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry findByName_Collection_Last(
			String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchDefinedDefaultOrderEntryException {

		DefinedDefaultOrderEntry definedDefaultOrderEntry =
			fetchByName_Collection_Last(name, orderByComparator);

		if (definedDefaultOrderEntry != null) {
			return definedDefaultOrderEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchDefinedDefaultOrderEntryException(sb.toString());
	}

	/**
	 * Returns the last defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching defined default order entry, or <code>null</code> if a matching defined default order entry could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry fetchByName_Collection_Last(
		String name,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		int count = countByName_Collection(name);

		if (count == 0) {
			return null;
		}

		List<DefinedDefaultOrderEntry> list = findByName_Collection(
			name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the defined default order entries before and after the current defined default order entry in the ordered set where name = &#63;.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the current defined default order entry
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry[] findByName_Collection_PrevAndNext(
			long definedDefaultOrderEntryId, String name,
			OrderByComparator<DefinedDefaultOrderEntry> orderByComparator)
		throws NoSuchDefinedDefaultOrderEntryException {

		name = Objects.toString(name, "");

		DefinedDefaultOrderEntry definedDefaultOrderEntry = findByPrimaryKey(
			definedDefaultOrderEntryId);

		Session session = null;

		try {
			session = openSession();

			DefinedDefaultOrderEntry[] array =
				new DefinedDefaultOrderEntryImpl[3];

			array[0] = getByName_Collection_PrevAndNext(
				session, definedDefaultOrderEntry, name, orderByComparator,
				true);

			array[1] = definedDefaultOrderEntry;

			array[2] = getByName_Collection_PrevAndNext(
				session, definedDefaultOrderEntry, name, orderByComparator,
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

	protected DefinedDefaultOrderEntry getByName_Collection_PrevAndNext(
		Session session, DefinedDefaultOrderEntry definedDefaultOrderEntry,
		String name,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_DEFINEDDEFAULTORDERENTRY_WHERE);

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
			sb.append(DefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
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
						definedDefaultOrderEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DefinedDefaultOrderEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the defined default order entries where name = &#63; from the database.
	 *
	 * @param name the name
	 */
	@Override
	public void removeByName_Collection(String name) {
		for (DefinedDefaultOrderEntry definedDefaultOrderEntry :
				findByName_Collection(
					name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(definedDefaultOrderEntry);
		}
	}

	/**
	 * Returns the number of defined default order entries where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching defined default order entries
	 */
	@Override
	public int countByName_Collection(String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByName_Collection;

		Object[] finderArgs = new Object[] {name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DEFINEDDEFAULTORDERENTRY_WHERE);

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
		"definedDefaultOrderEntry.name = ?";

	private static final String _FINDER_COLUMN_NAME_COLLECTION_NAME_3 =
		"(definedDefaultOrderEntry.name IS NULL OR definedDefaultOrderEntry.name = '')";

	public DefinedDefaultOrderEntryPersistenceImpl() {
		setModelClass(DefinedDefaultOrderEntry.class);

		setModelImplClass(DefinedDefaultOrderEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DefinedDefaultOrderEntryTable.INSTANCE);
	}

	/**
	 * Caches the defined default order entry in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 */
	@Override
	public void cacheResult(DefinedDefaultOrderEntry definedDefaultOrderEntry) {
		entityCache.putResult(
			DefinedDefaultOrderEntryImpl.class,
			definedDefaultOrderEntry.getPrimaryKey(), definedDefaultOrderEntry);

		finderCache.putResult(
			_finderPathFetchByName,
			new Object[] {definedDefaultOrderEntry.getName()},
			definedDefaultOrderEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the defined default order entries in the entity cache if it is enabled.
	 *
	 * @param definedDefaultOrderEntries the defined default order entries
	 */
	@Override
	public void cacheResult(
		List<DefinedDefaultOrderEntry> definedDefaultOrderEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (definedDefaultOrderEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DefinedDefaultOrderEntry definedDefaultOrderEntry :
				definedDefaultOrderEntries) {

			if (entityCache.getResult(
					DefinedDefaultOrderEntryImpl.class,
					definedDefaultOrderEntry.getPrimaryKey()) == null) {

				cacheResult(definedDefaultOrderEntry);
			}
		}
	}

	/**
	 * Clears the cache for all defined default order entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DefinedDefaultOrderEntryImpl.class);

		finderCache.clearCache(DefinedDefaultOrderEntryImpl.class);
	}

	/**
	 * Clears the cache for the defined default order entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DefinedDefaultOrderEntry definedDefaultOrderEntry) {
		entityCache.removeResult(
			DefinedDefaultOrderEntryImpl.class, definedDefaultOrderEntry);
	}

	@Override
	public void clearCache(
		List<DefinedDefaultOrderEntry> definedDefaultOrderEntries) {

		for (DefinedDefaultOrderEntry definedDefaultOrderEntry :
				definedDefaultOrderEntries) {

			entityCache.removeResult(
				DefinedDefaultOrderEntryImpl.class, definedDefaultOrderEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DefinedDefaultOrderEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DefinedDefaultOrderEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DefinedDefaultOrderEntryModelImpl definedDefaultOrderEntryModelImpl) {

		Object[] args = new Object[] {
			definedDefaultOrderEntryModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByName, args, definedDefaultOrderEntryModelImpl);
	}

	/**
	 * Creates a new defined default order entry with the primary key. Does not add the defined default order entry to the database.
	 *
	 * @param definedDefaultOrderEntryId the primary key for the new defined default order entry
	 * @return the new defined default order entry
	 */
	@Override
	public DefinedDefaultOrderEntry create(long definedDefaultOrderEntryId) {
		DefinedDefaultOrderEntry definedDefaultOrderEntry =
			new DefinedDefaultOrderEntryImpl();

		definedDefaultOrderEntry.setNew(true);
		definedDefaultOrderEntry.setPrimaryKey(definedDefaultOrderEntryId);

		return definedDefaultOrderEntry;
	}

	/**
	 * Removes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry remove(long definedDefaultOrderEntryId)
		throws NoSuchDefinedDefaultOrderEntryException {

		return remove((Serializable)definedDefaultOrderEntryId);
	}

	/**
	 * Removes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry remove(Serializable primaryKey)
		throws NoSuchDefinedDefaultOrderEntryException {

		Session session = null;

		try {
			session = openSession();

			DefinedDefaultOrderEntry definedDefaultOrderEntry =
				(DefinedDefaultOrderEntry)session.get(
					DefinedDefaultOrderEntryImpl.class, primaryKey);

			if (definedDefaultOrderEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinedDefaultOrderEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(definedDefaultOrderEntry);
		}
		catch (NoSuchDefinedDefaultOrderEntryException noSuchEntityException) {
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
	protected DefinedDefaultOrderEntry removeImpl(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(definedDefaultOrderEntry)) {
				definedDefaultOrderEntry =
					(DefinedDefaultOrderEntry)session.get(
						DefinedDefaultOrderEntryImpl.class,
						definedDefaultOrderEntry.getPrimaryKeyObj());
			}

			if (definedDefaultOrderEntry != null) {
				session.delete(definedDefaultOrderEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (definedDefaultOrderEntry != null) {
			clearCache(definedDefaultOrderEntry);
		}

		return definedDefaultOrderEntry;
	}

	@Override
	public DefinedDefaultOrderEntry updateImpl(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		boolean isNew = definedDefaultOrderEntry.isNew();

		if (!(definedDefaultOrderEntry instanceof
				DefinedDefaultOrderEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(definedDefaultOrderEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					definedDefaultOrderEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in definedDefaultOrderEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DefinedDefaultOrderEntry implementation " +
					definedDefaultOrderEntry.getClass());
		}

		DefinedDefaultOrderEntryModelImpl definedDefaultOrderEntryModelImpl =
			(DefinedDefaultOrderEntryModelImpl)definedDefaultOrderEntry;

		if (!definedDefaultOrderEntryModelImpl.hasSetModifiedDate()) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				definedDefaultOrderEntry.setModifiedDate(date);
			}
			else {
				definedDefaultOrderEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(definedDefaultOrderEntry);
			}
			else {
				definedDefaultOrderEntry =
					(DefinedDefaultOrderEntry)session.merge(
						definedDefaultOrderEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DefinedDefaultOrderEntryImpl.class,
			definedDefaultOrderEntryModelImpl, false, true);

		cacheUniqueFindersCache(definedDefaultOrderEntryModelImpl);

		if (isNew) {
			definedDefaultOrderEntry.setNew(false);
		}

		definedDefaultOrderEntry.resetOriginalValues();

		return definedDefaultOrderEntry;
	}

	/**
	 * Returns the defined default order entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinedDefaultOrderEntryException {

		DefinedDefaultOrderEntry definedDefaultOrderEntry = fetchByPrimaryKey(
			primaryKey);

		if (definedDefaultOrderEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinedDefaultOrderEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return definedDefaultOrderEntry;
	}

	/**
	 * Returns the defined default order entry with the primary key or throws a <code>NoSuchDefinedDefaultOrderEntryException</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws NoSuchDefinedDefaultOrderEntryException if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry findByPrimaryKey(
			long definedDefaultOrderEntryId)
		throws NoSuchDefinedDefaultOrderEntryException {

		return findByPrimaryKey((Serializable)definedDefaultOrderEntryId);
	}

	/**
	 * Returns the defined default order entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry, or <code>null</code> if a defined default order entry with the primary key could not be found
	 */
	@Override
	public DefinedDefaultOrderEntry fetchByPrimaryKey(
		long definedDefaultOrderEntryId) {

		return fetchByPrimaryKey((Serializable)definedDefaultOrderEntryId);
	}

	/**
	 * Returns all the defined default order entries.
	 *
	 * @return the defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of defined default order entries
	 */
	@Override
	public List<DefinedDefaultOrderEntry> findAll(
		int start, int end,
		OrderByComparator<DefinedDefaultOrderEntry> orderByComparator,
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

		List<DefinedDefaultOrderEntry> list = null;

		if (useFinderCache) {
			list = (List<DefinedDefaultOrderEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DEFINEDDEFAULTORDERENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DEFINEDDEFAULTORDERENTRY;

				sql = sql.concat(
					DefinedDefaultOrderEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DefinedDefaultOrderEntry>)QueryUtil.list(
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
	 * Removes all the defined default order entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DefinedDefaultOrderEntry definedDefaultOrderEntry : findAll()) {
			remove(definedDefaultOrderEntry);
		}
	}

	/**
	 * Returns the number of defined default order entries.
	 *
	 * @return the number of defined default order entries
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
					_SQL_COUNT_DEFINEDDEFAULTORDERENTRY);

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
		return "definedDefaultOrderEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEFINEDDEFAULTORDERENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DefinedDefaultOrderEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the defined default order entry persistence.
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

		DefinedDefaultOrderEntryUtil.setPersistence(this);
	}

	public void destroy() {
		DefinedDefaultOrderEntryUtil.setPersistence(null);

		entityCache.removeCache(DefinedDefaultOrderEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DEFINEDDEFAULTORDERENTRY =
		"SELECT definedDefaultOrderEntry FROM DefinedDefaultOrderEntry definedDefaultOrderEntry";

	private static final String _SQL_SELECT_DEFINEDDEFAULTORDERENTRY_WHERE =
		"SELECT definedDefaultOrderEntry FROM DefinedDefaultOrderEntry definedDefaultOrderEntry WHERE ";

	private static final String _SQL_COUNT_DEFINEDDEFAULTORDERENTRY =
		"SELECT COUNT(definedDefaultOrderEntry) FROM DefinedDefaultOrderEntry definedDefaultOrderEntry";

	private static final String _SQL_COUNT_DEFINEDDEFAULTORDERENTRY_WHERE =
		"SELECT COUNT(definedDefaultOrderEntry) FROM DefinedDefaultOrderEntry definedDefaultOrderEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"definedDefaultOrderEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DefinedDefaultOrderEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DefinedDefaultOrderEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DefinedDefaultOrderEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// SB-Hash:-1414100736