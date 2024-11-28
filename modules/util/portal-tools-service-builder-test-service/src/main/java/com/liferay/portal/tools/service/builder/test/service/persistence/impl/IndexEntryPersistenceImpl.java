/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.DuplicateIndexEntryExternalReferenceCodeException;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;
import com.liferay.portal.tools.service.builder.test.model.IndexEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.IndexEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.IndexEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the index entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class IndexEntryPersistenceImpl
	extends BasePersistenceImpl<IndexEntry> implements IndexEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>IndexEntryUtil</code> to access the index entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		IndexEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByOwnerId;
	private FinderPath _finderPathWithoutPaginationFindByOwnerId;
	private FinderPath _finderPathCountByOwnerId;

	/**
	 * Returns all the index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByOwnerId(long ownerId) {
		return findByOwnerId(
			ownerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByOwnerId(long ownerId, int start, int end) {
		return findByOwnerId(ownerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByOwnerId(ownerId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByOwnerId(
		long ownerId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByOwnerId;
					finderArgs = new Object[] {ownerId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByOwnerId;
				finderArgs = new Object[] {
					ownerId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if (ownerId != indexEntry.getOwnerId()) {
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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_OWNERID_OWNERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByOwnerId_First(
			long ownerId, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByOwnerId_First(
			ownerId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByOwnerId_First(
		long ownerId, OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByOwnerId(ownerId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByOwnerId_Last(
			long ownerId, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByOwnerId_Last(ownerId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByOwnerId_Last(
		long ownerId, OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByOwnerId(ownerId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByOwnerId(
			ownerId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByOwnerId_PrevAndNext(
			long indexEntryId, long ownerId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByOwnerId_PrevAndNext(
				session, indexEntry, ownerId, orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByOwnerId_PrevAndNext(
				session, indexEntry, ownerId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected IndexEntry getByOwnerId_PrevAndNext(
		Session session, IndexEntry indexEntry, long ownerId,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_OWNERID_OWNERID_2);

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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ownerId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where ownerId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 */
	@Override
	public void removeByOwnerId(long ownerId) {
		for (IndexEntry indexEntry :
				findByOwnerId(
					ownerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where ownerId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByOwnerId(long ownerId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = _finderPathCountByOwnerId;

			Object[] finderArgs = new Object[] {ownerId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_OWNERID_OWNERID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

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
	}

	private static final String _FINDER_COLUMN_OWNERID_OWNERID_2 =
		"indexEntry.ownerId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPlid(
		long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPlid(
		long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByPlid;
					finderArgs = new Object[] {plid};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByPlid;
				finderArgs = new Object[] {plid, start, end, orderByComparator};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if (plid != indexEntry.getPlid()) {
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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_PLID_PLID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(plid);

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByPlid_First(
			long plid, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByPlid_First(plid, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByPlid_First(
		long plid, OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByPlid_Last(
			long plid, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByPlid_Last(plid, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByPlid_Last(
		long plid, OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByPlid_PrevAndNext(
			long indexEntryId, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, indexEntry, plid, orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByPlid_PrevAndNext(
				session, indexEntry, plid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected IndexEntry getByPlid_PrevAndNext(
		Session session, IndexEntry indexEntry, long plid,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_PLID_PLID_2);

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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (IndexEntry indexEntry :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	@Override
	public int countByPlid(long plid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = _finderPathCountByPlid;

			Object[] finderArgs = new Object[] {plid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_PLID_PLID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(plid);

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
	}

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"indexEntry.plid = ?";

	private FinderPath _finderPathWithPaginationFindByPortletId;
	private FinderPath _finderPathWithoutPaginationFindByPortletId;
	private FinderPath _finderPathCountByPortletId;

	/**
	 * Returns all the index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByPortletId(String portletId) {
		return findByPortletId(
			portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPortletId(
		String portletId, int start, int end) {

		return findByPortletId(portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByPortletId(portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByPortletId;
					finderArgs = new Object[] {portletId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByPortletId;
				finderArgs = new Object[] {
					portletId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if (!portletId.equals(indexEntry.getPortletId())) {
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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByPortletId_First(
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByPortletId_First(
			portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByPortletId_First(
		String portletId, OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByPortletId(
			portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByPortletId_Last(
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByPortletId_Last(
			portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByPortletId_Last(
		String portletId, OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByPortletId(portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByPortletId(
			portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByPortletId_PrevAndNext(
			long indexEntryId, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByPortletId_PrevAndNext(
				session, indexEntry, portletId, orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByPortletId_PrevAndNext(
				session, indexEntry, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected IndexEntry getByPortletId_PrevAndNext(
		Session session, IndexEntry indexEntry, String portletId,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByPortletId(String portletId) {
		for (IndexEntry indexEntry :
				findByPortletId(
					portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByPortletId(String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathCountByPortletId;

			Object[] finderArgs = new Object[] {portletId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByO_P;
	private FinderPath _finderPathWithoutPaginationFindByO_P;
	private FinderPath _finderPathCountByO_P;

	/**
	 * Returns all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P(int ownerType, String portletId) {
		return findByO_P(
			ownerType, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end) {

		return findByO_P(ownerType, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByO_P(
			ownerType, portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P(
		int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByO_P;
					finderArgs = new Object[] {ownerType, portletId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByO_P;
				finderArgs = new Object[] {
					ownerType, portletId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((ownerType != indexEntry.getOwnerType()) ||
							!portletId.equals(indexEntry.getPortletId())) {

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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_P_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_P_First(
			int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_P_First(
			ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerType=");
		sb.append(ownerType);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_P_First(
		int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByO_P(
			ownerType, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_P_Last(
			int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_P_Last(
			ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerType=");
		sb.append(ownerType);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_P_Last(
		int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByO_P(ownerType, portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByO_P(
			ownerType, portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByO_P_PrevAndNext(
			long indexEntryId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByO_P_PrevAndNext(
				session, indexEntry, ownerType, portletId, orderByComparator,
				true);

			array[1] = indexEntry;

			array[2] = getByO_P_PrevAndNext(
				session, indexEntry, ownerType, portletId, orderByComparator,
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

	protected IndexEntry getByO_P_PrevAndNext(
		Session session, IndexEntry indexEntry, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_O_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_O_P_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ownerType);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_P(int ownerType, String portletId) {
		for (IndexEntry indexEntry :
				findByO_P(
					ownerType, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByO_P(int ownerType, String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathCountByO_P;

			Object[] finderArgs = new Object[] {ownerType, portletId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_P_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_P_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_O_P_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_O_P_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_O_P_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByP_P;
	private FinderPath _finderPathWithoutPaginationFindByP_P;
	private FinderPath _finderPathCountByP_P;

	/**
	 * Returns all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByP_P(long plid, String portletId) {
		return findByP_P(
			plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end) {

		return findByP_P(plid, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByP_P(plid, portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByP_P(
		long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByP_P;
					finderArgs = new Object[] {plid, portletId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByP_P;
				finderArgs = new Object[] {
					plid, portletId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((plid != indexEntry.getPlid()) ||
							!portletId.equals(indexEntry.getPortletId())) {

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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_P_P_PLID_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_P_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_P_P_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(plid);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByP_P_First(
			long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByP_P_First(
			plid, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByP_P_First(
		long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByP_P(
			plid, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByP_P_Last(
			long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByP_P_Last(
			plid, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByP_P_Last(
		long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByP_P(plid, portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByP_P(
			plid, portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByP_P_PrevAndNext(
			long indexEntryId, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByP_P_PrevAndNext(
				session, indexEntry, plid, portletId, orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByP_P_PrevAndNext(
				session, indexEntry, plid, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected IndexEntry getByP_P_PrevAndNext(
		Session session, IndexEntry indexEntry, long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_P_P_PLID_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_P_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_P_P_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(plid);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByP_P(long plid, String portletId) {
		for (IndexEntry indexEntry :
				findByP_P(
					plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where plid = &#63; and portletId = &#63;.
	 *
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByP_P(long plid, String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathCountByP_P;

			Object[] finderArgs = new Object[] {plid, portletId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_P_P_PLID_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_P_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_P_P_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(plid);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_P_P_PLID_2 =
		"indexEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_P_P_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_P_P_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByO_O_P;
	private FinderPath _finderPathWithoutPaginationFindByO_O_P;
	private FinderPath _finderPathCountByO_O_P;

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid) {

		return findByO_O_P(
			ownerId, ownerType, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end) {

		return findByO_O_P(ownerId, ownerType, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByO_O_P(
			ownerId, ownerType, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByO_O_P;
					finderArgs = new Object[] {ownerId, ownerType, plid};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByO_O_P;
				finderArgs = new Object[] {
					ownerId, ownerType, plid, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((ownerId != indexEntry.getOwnerId()) ||
							(ownerType != indexEntry.getOwnerType()) ||
							(plid != indexEntry.getPlid())) {

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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

				sb.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

				sb.append(_FINDER_COLUMN_O_O_P_PLID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					queryPos.add(plid);

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_O_P_First(
			long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_O_P_First(
			ownerId, ownerType, plid, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_P_First(
		long ownerId, int ownerType, long plid,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByO_O_P(
			ownerId, ownerType, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_O_P_Last(
			long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_O_P_Last(
			ownerId, ownerType, plid, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByO_O_P(ownerId, ownerType, plid);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByO_O_P(
			ownerId, ownerType, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByO_O_P_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, long plid,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByO_O_P_PrevAndNext(
				session, indexEntry, ownerId, ownerType, plid,
				orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByO_O_P_PrevAndNext(
				session, indexEntry, ownerId, ownerType, plid,
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

	protected IndexEntry getByO_O_P_PrevAndNext(
		Session session, IndexEntry indexEntry, long ownerId, int ownerType,
		long plid, OrderByComparator<IndexEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

		sb.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

		sb.append(_FINDER_COLUMN_O_O_P_PLID_2);

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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ownerId);

		queryPos.add(ownerType);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 */
	@Override
	public void removeByO_O_P(long ownerId, int ownerType, long plid) {
		for (IndexEntry indexEntry :
				findByO_O_P(
					ownerId, ownerType, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @return the number of matching index entries
	 */
	@Override
	public int countByO_O_P(long ownerId, int ownerType, long plid) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			FinderPath finderPath = _finderPathCountByO_O_P;

			Object[] finderArgs = new Object[] {ownerId, ownerType, plid};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_O_P_OWNERID_2);

				sb.append(_FINDER_COLUMN_O_O_P_OWNERTYPE_2);

				sb.append(_FINDER_COLUMN_O_O_P_PLID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					queryPos.add(plid);

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
	}

	private static final String _FINDER_COLUMN_O_O_P_OWNERID_2 =
		"indexEntry.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_O_O_P_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_O_O_P_PLID_2 =
		"indexEntry.plid = ?";

	private FinderPath _finderPathWithPaginationFindByO_O_PI;
	private FinderPath _finderPathWithoutPaginationFindByO_O_PI;
	private FinderPath _finderPathCountByO_O_PI;

	/**
	 * Returns all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId) {

		return findByO_O_PI(
			ownerId, ownerType, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end) {

		return findByO_O_PI(ownerId, ownerType, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByO_O_PI(
			ownerId, ownerType, portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_O_PI(
		long ownerId, int ownerType, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByO_O_PI;
					finderArgs = new Object[] {ownerId, ownerType, portletId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByO_O_PI;
				finderArgs = new Object[] {
					ownerId, ownerType, portletId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((ownerId != indexEntry.getOwnerId()) ||
							(ownerType != indexEntry.getOwnerType()) ||
							!portletId.equals(indexEntry.getPortletId())) {

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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

				sb.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_O_PI_First(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_O_PI_First(
			ownerId, ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_PI_First(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByO_O_PI(
			ownerId, ownerType, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_O_PI_Last(
			long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_O_PI_Last(
			ownerId, ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_PI_Last(
		long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByO_O_PI(ownerId, ownerType, portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByO_O_PI(
			ownerId, ownerType, portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByO_O_PI_PrevAndNext(
			long indexEntryId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByO_O_PI_PrevAndNext(
				session, indexEntry, ownerId, ownerType, portletId,
				orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByO_O_PI_PrevAndNext(
				session, indexEntry, ownerId, ownerType, portletId,
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

	protected IndexEntry getByO_O_PI_PrevAndNext(
		Session session, IndexEntry indexEntry, long ownerId, int ownerType,
		String portletId, OrderByComparator<IndexEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

		sb.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ownerId);

		queryPos.add(ownerType);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_O_PI(long ownerId, int ownerType, String portletId) {
		for (IndexEntry indexEntry :
				findByO_O_PI(
					ownerId, ownerType, portletId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByO_O_PI(long ownerId, int ownerType, String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathCountByO_O_PI;

			Object[] finderArgs = new Object[] {ownerId, ownerType, portletId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_O_PI_OWNERID_2);

				sb.append(_FINDER_COLUMN_O_O_PI_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_O_PI_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_O_O_PI_OWNERID_2 =
		"indexEntry.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_O_O_PI_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_O_O_PI_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_O_O_PI_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByO_P_P;
	private FinderPath _finderPathWithoutPaginationFindByO_P_P;
	private FinderPath _finderPathCountByO_P_P;

	/**
	 * Returns all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId) {

		return findByO_P_P(
			ownerType, plid, portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end) {

		return findByO_P_P(ownerType, plid, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return findByO_P_P(
			ownerType, plid, portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByO_P_P(
		int ownerType, long plid, String portletId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByO_P_P;
					finderArgs = new Object[] {ownerType, plid, portletId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByO_P_P;
				finderArgs = new Object[] {
					ownerType, plid, portletId, start, end, orderByComparator
				};
			}

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((ownerType != indexEntry.getOwnerType()) ||
							(plid != indexEntry.getPlid()) ||
							!portletId.equals(indexEntry.getPortletId())) {

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

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

				sb.append(_FINDER_COLUMN_O_P_P_PLID_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerType);

					queryPos.add(plid);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_P_P_First(
			int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_P_P_First(
			ownerType, plid, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerType=");
		sb.append(ownerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_P_P_First(
		int ownerType, long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByO_P_P(
			ownerType, plid, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_P_P_Last(
			int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_P_P_Last(
			ownerType, plid, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ownerType=");
		sb.append(ownerType);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_P_P_Last(
		int ownerType, long plid, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByO_P_P(ownerType, plid, portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByO_P_P(
			ownerType, plid, portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByO_P_P_PrevAndNext(
			long indexEntryId, int ownerType, long plid, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByO_P_P_PrevAndNext(
				session, indexEntry, ownerType, plid, portletId,
				orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByO_P_P_PrevAndNext(
				session, indexEntry, ownerType, plid, portletId,
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

	protected IndexEntry getByO_P_P_PrevAndNext(
		Session session, IndexEntry indexEntry, int ownerType, long plid,
		String portletId, OrderByComparator<IndexEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

		sb.append(_FINDER_COLUMN_O_P_P_PLID_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ownerType);

		queryPos.add(plid);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByO_P_P(int ownerType, long plid, String portletId) {
		for (IndexEntry indexEntry :
				findByO_P_P(
					ownerType, plid, portletId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByO_P_P(int ownerType, long plid, String portletId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathCountByO_P_P;

			Object[] finderArgs = new Object[] {ownerType, plid, portletId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_P_P_OWNERTYPE_2);

				sb.append(_FINDER_COLUMN_O_P_P_PLID_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_P_P_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerType);

					queryPos.add(plid);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_O_P_P_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_O_P_P_PLID_2 =
		"indexEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_O_P_P_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_O_P_P_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByC_O_O_LikeP;
	private FinderPath _finderPathWithPaginationCountByC_O_O_LikeP;

	/**
	 * Returns all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the matching index entries
	 */
	@Override
	public List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		return findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end) {

		return findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end, OrderByComparator<IndexEntry> orderByComparator) {

		return findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching index entries
	 */
	@Override
	public List<IndexEntry> findByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId,
		int start, int end, OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			finderPath = _finderPathWithPaginationFindByC_O_O_LikeP;
			finderArgs = new Object[] {
				companyId, ownerId, ownerType, portletId, start, end,
				orderByComparator
			};

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (IndexEntry indexEntry : list) {
						if ((companyId != indexEntry.getCompanyId()) ||
							(ownerId != indexEntry.getOwnerId()) ||
							(ownerType != indexEntry.getOwnerType()) ||
							!StringUtil.wildcardMatches(
								indexEntry.getPortletId(), portletId, '_', '%',
								'\\', true)) {

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
						6 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(6);
				}

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERID_2);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByC_O_O_LikeP_First(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByC_O_O_LikeP_First(
			companyId, ownerId, ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", portletIdLIKE");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the first index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByC_O_O_LikeP_First(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		List<IndexEntry> list = findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByC_O_O_LikeP_Last(
			long companyId, long ownerId, int ownerType, String portletId,
			OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByC_O_O_LikeP_Last(
			companyId, ownerId, ownerType, portletId, orderByComparator);

		if (indexEntry != null) {
			return indexEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", ownerType=");
		sb.append(ownerType);

		sb.append(", portletIdLIKE");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchIndexEntryException(sb.toString());
	}

	/**
	 * Returns the last index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByC_O_O_LikeP_Last(
		long companyId, long ownerId, int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator) {

		int count = countByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId);

		if (count == 0) {
			return null;
		}

		List<IndexEntry> list = findByC_O_O_LikeP(
			companyId, ownerId, ownerType, portletId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the index entries before and after the current index entry in the ordered set where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param indexEntryId the primary key of the current index entry
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry[] findByC_O_O_LikeP_PrevAndNext(
			long indexEntryId, long companyId, long ownerId, int ownerType,
			String portletId, OrderByComparator<IndexEntry> orderByComparator)
		throws NoSuchIndexEntryException {

		portletId = Objects.toString(portletId, "");

		IndexEntry indexEntry = findByPrimaryKey(indexEntryId);

		Session session = null;

		try {
			session = openSession();

			IndexEntry[] array = new IndexEntryImpl[3];

			array[0] = getByC_O_O_LikeP_PrevAndNext(
				session, indexEntry, companyId, ownerId, ownerType, portletId,
				orderByComparator, true);

			array[1] = indexEntry;

			array[2] = getByC_O_O_LikeP_PrevAndNext(
				session, indexEntry, companyId, ownerId, ownerType, portletId,
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

	protected IndexEntry getByC_O_O_LikeP_PrevAndNext(
		Session session, IndexEntry indexEntry, long companyId, long ownerId,
		int ownerType, String portletId,
		OrderByComparator<IndexEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_O_O_LIKEP_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERID_2);

		sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERTYPE_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_2);
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
			sb.append(IndexEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(ownerId);

		queryPos.add(ownerType);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(indexEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<IndexEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		for (IndexEntry indexEntry :
				findByC_O_O_LikeP(
					companyId, ownerId, ownerType, portletId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries where companyId = &#63; and ownerId = &#63; and ownerType = &#63; and portletId LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByC_O_O_LikeP(
		long companyId, long ownerId, int ownerType, String portletId) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			FinderPath finderPath = _finderPathWithPaginationCountByC_O_O_LikeP;

			Object[] finderArgs = new Object[] {
				companyId, ownerId, ownerType, portletId
			};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(5);

				sb.append(_SQL_COUNT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_COMPANYID_2);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERID_2);

				sb.append(_FINDER_COLUMN_C_O_O_LIKEP_OWNERTYPE_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					if (bindPortletId) {
						queryPos.add(portletId);
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
	}

	private static final String _FINDER_COLUMN_C_O_O_LIKEP_COMPANYID_2 =
		"indexEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_O_O_LIKEP_OWNERID_2 =
		"indexEntry.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_C_O_O_LIKEP_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_2 =
		"indexEntry.portletId LIKE ?";

	private static final String _FINDER_COLUMN_C_O_O_LIKEP_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId LIKE '')";

	private FinderPath _finderPathFetchByO_O_P_P;

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId);

		if (indexEntry == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ownerId=");
			sb.append(ownerId);

			sb.append(", ownerType=");
			sb.append(ownerType);

			sb.append(", plid=");
			sb.append(plid);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchIndexEntryException(sb.toString());
		}

		return indexEntry;
	}

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		return fetchByO_O_P_P(ownerId, ownerType, plid, portletId, true);
	}

	/**
	 * Returns the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			portletId = Objects.toString(portletId, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {ownerId, ownerType, plid, portletId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByO_O_P_P, finderArgs, this);
			}

			if (result instanceof IndexEntry) {
				IndexEntry indexEntry = (IndexEntry)result;

				if ((ownerId != indexEntry.getOwnerId()) ||
					(ownerType != indexEntry.getOwnerType()) ||
					(plid != indexEntry.getPlid()) ||
					!Objects.equals(portletId, indexEntry.getPortletId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(6);

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				sb.append(_FINDER_COLUMN_O_O_P_P_OWNERID_2);

				sb.append(_FINDER_COLUMN_O_O_P_P_OWNERTYPE_2);

				sb.append(_FINDER_COLUMN_O_O_P_P_PLID_2);

				boolean bindPortletId = false;

				if (portletId.isEmpty()) {
					sb.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_3);
				}
				else {
					bindPortletId = true;

					sb.append(_FINDER_COLUMN_O_O_P_P_PORTLETID_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(ownerId);

					queryPos.add(ownerType);

					queryPos.add(plid);

					if (bindPortletId) {
						queryPos.add(portletId);
					}

					List<IndexEntry> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByO_O_P_P, finderArgs, list);
						}
					}
					else {
						IndexEntry indexEntry = list.get(0);

						result = indexEntry;

						cacheResult(indexEntry);
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
				return (IndexEntry)result;
			}
		}
	}

	/**
	 * Removes the index entry where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the index entry that was removed
	 */
	@Override
	public IndexEntry removeByO_O_P_P(
			long ownerId, int ownerType, long plid, String portletId)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = findByO_O_P_P(
			ownerId, ownerType, plid, portletId);

		return remove(indexEntry);
	}

	/**
	 * Returns the number of index entries where ownerId = &#63; and ownerType = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByO_O_P_P(
		long ownerId, int ownerType, long plid, String portletId) {

		IndexEntry indexEntry = fetchByO_O_P_P(
			ownerId, ownerType, plid, portletId);

		if (indexEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_O_O_P_P_OWNERID_2 =
		"indexEntry.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_O_O_P_P_OWNERTYPE_2 =
		"indexEntry.ownerType = ? AND ";

	private static final String _FINDER_COLUMN_O_O_P_P_PLID_2 =
		"indexEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_2 =
		"indexEntry.portletId = ?";

	private static final String _FINDER_COLUMN_O_O_P_P_PORTLETID_3 =
		"(indexEntry.portletId IS NULL OR indexEntry.portletId = '')";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry
	 * @throws NoSuchIndexEntryException if a matching index entry could not be found
	 */
	@Override
	public IndexEntry findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByERC_C(externalReferenceCode, companyId);

		if (indexEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchIndexEntryException(sb.toString());
		}

		return indexEntry;
	}

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the index entry where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching index entry, or <code>null</code> if a matching index entry could not be found
	 */
	@Override
	public IndexEntry fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			externalReferenceCode = Objects.toString(externalReferenceCode, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {externalReferenceCode, companyId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByERC_C, finderArgs, this);
			}

			if (result instanceof IndexEntry) {
				IndexEntry indexEntry = (IndexEntry)result;

				if (!Objects.equals(
						externalReferenceCode,
						indexEntry.getExternalReferenceCode()) ||
					(companyId != indexEntry.getCompanyId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_INDEXENTRY_WHERE);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
				}

				sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					queryPos.add(companyId);

					List<IndexEntry> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByERC_C, finderArgs, list);
						}
					}
					else {
						IndexEntry indexEntry = list.get(0);

						result = indexEntry;

						cacheResult(indexEntry);
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
				return (IndexEntry)result;
			}
		}
	}

	/**
	 * Removes the index entry where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the index entry that was removed
	 */
	@Override
	public IndexEntry removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = findByERC_C(externalReferenceCode, companyId);

		return remove(indexEntry);
	}

	/**
	 * Returns the number of index entries where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching index entries
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		IndexEntry indexEntry = fetchByERC_C(externalReferenceCode, companyId);

		if (indexEntry == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"indexEntry.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(indexEntry.externalReferenceCode IS NULL OR indexEntry.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"indexEntry.companyId = ?";

	public IndexEntryPersistenceImpl() {
		setModelClass(IndexEntry.class);

		setModelImplClass(IndexEntryImpl.class);
		setModelPKClass(long.class);

		setTable(IndexEntryTable.INSTANCE);
	}

	/**
	 * Caches the index entry in the entity cache if it is enabled.
	 *
	 * @param indexEntry the index entry
	 */
	@Override
	public void cacheResult(IndexEntry indexEntry) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					indexEntry.getCtCollectionId())) {

			entityCache.putResult(
				IndexEntryImpl.class, indexEntry.getPrimaryKey(), indexEntry);

			finderCache.putResult(
				_finderPathFetchByO_O_P_P,
				new Object[] {
					indexEntry.getOwnerId(), indexEntry.getOwnerType(),
					indexEntry.getPlid(), indexEntry.getPortletId()
				},
				indexEntry);

			finderCache.putResult(
				_finderPathFetchByERC_C,
				new Object[] {
					indexEntry.getExternalReferenceCode(),
					indexEntry.getCompanyId()
				},
				indexEntry);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the index entries in the entity cache if it is enabled.
	 *
	 * @param indexEntries the index entries
	 */
	@Override
	public void cacheResult(List<IndexEntry> indexEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (indexEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (IndexEntry indexEntry : indexEntries) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						indexEntry.getCtCollectionId())) {

				if (entityCache.getResult(
						IndexEntryImpl.class, indexEntry.getPrimaryKey()) ==
							null) {

					cacheResult(indexEntry);
				}
			}
		}
	}

	/**
	 * Clears the cache for all index entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(IndexEntryImpl.class);

		finderCache.clearCache(IndexEntryImpl.class);
	}

	/**
	 * Clears the cache for the index entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(IndexEntry indexEntry) {
		entityCache.removeResult(IndexEntryImpl.class, indexEntry);
	}

	@Override
	public void clearCache(List<IndexEntry> indexEntries) {
		for (IndexEntry indexEntry : indexEntries) {
			entityCache.removeResult(IndexEntryImpl.class, indexEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(IndexEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(IndexEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		IndexEntryModelImpl indexEntryModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					indexEntryModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				indexEntryModelImpl.getOwnerId(),
				indexEntryModelImpl.getOwnerType(),
				indexEntryModelImpl.getPlid(),
				indexEntryModelImpl.getPortletId()
			};

			finderCache.putResult(
				_finderPathFetchByO_O_P_P, args, indexEntryModelImpl);

			args = new Object[] {
				indexEntryModelImpl.getExternalReferenceCode(),
				indexEntryModelImpl.getCompanyId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_C, args, indexEntryModelImpl);
		}
	}

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	@Override
	public IndexEntry create(long indexEntryId) {
		IndexEntry indexEntry = new IndexEntryImpl();

		indexEntry.setNew(true);
		indexEntry.setPrimaryKey(indexEntryId);

		indexEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return indexEntry;
	}

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry remove(long indexEntryId)
		throws NoSuchIndexEntryException {

		return remove((Serializable)indexEntryId);
	}

	/**
	 * Removes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry remove(Serializable primaryKey)
		throws NoSuchIndexEntryException {

		Session session = null;

		try {
			session = openSession();

			IndexEntry indexEntry = (IndexEntry)session.get(
				IndexEntryImpl.class, primaryKey);

			if (indexEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchIndexEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(indexEntry);
		}
		catch (NoSuchIndexEntryException noSuchEntityException) {
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
	protected IndexEntry removeImpl(IndexEntry indexEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(indexEntry)) {
				indexEntry = (IndexEntry)session.get(
					IndexEntryImpl.class, indexEntry.getPrimaryKeyObj());
			}

			if ((indexEntry != null) &&
				ctPersistenceHelper.isRemove(indexEntry)) {

				session.delete(indexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (indexEntry != null) {
			clearCache(indexEntry);
		}

		return indexEntry;
	}

	@Override
	public IndexEntry updateImpl(IndexEntry indexEntry) {
		boolean isNew = indexEntry.isNew();

		if (!(indexEntry instanceof IndexEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(indexEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(indexEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in indexEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom IndexEntry implementation " +
					indexEntry.getClass());
		}

		IndexEntryModelImpl indexEntryModelImpl =
			(IndexEntryModelImpl)indexEntry;

		if (Validator.isNull(indexEntry.getExternalReferenceCode())) {
			indexEntry.setExternalReferenceCode(
				String.valueOf(indexEntry.getPrimaryKey()));
		}
		else {
			if (!Objects.equals(
					indexEntryModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					indexEntry.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = indexEntry.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = indexEntry.getPrimaryKey();
					}

					try {
						indexEntry.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								IndexEntry.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								indexEntry.getExternalReferenceCode(), null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			IndexEntry ercIndexEntry = fetchByERC_C(
				indexEntry.getExternalReferenceCode(),
				indexEntry.getCompanyId());

			if (isNew) {
				if (ercIndexEntry != null) {
					throw new DuplicateIndexEntryExternalReferenceCodeException(
						"Duplicate index entry with external reference code " +
							indexEntry.getExternalReferenceCode() +
								" and company " + indexEntry.getCompanyId());
				}
			}
			else {
				if ((ercIndexEntry != null) &&
					(indexEntry.getIndexEntryId() !=
						ercIndexEntry.getIndexEntryId())) {

					throw new DuplicateIndexEntryExternalReferenceCodeException(
						"Duplicate index entry with external reference code " +
							indexEntry.getExternalReferenceCode() +
								" and company " + indexEntry.getCompanyId());
				}
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(indexEntry)) {
				if (!isNew) {
					session.evict(
						IndexEntryImpl.class, indexEntry.getPrimaryKeyObj());
				}

				session.save(indexEntry);
			}
			else {
				indexEntry = (IndexEntry)session.merge(indexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			IndexEntryImpl.class, indexEntryModelImpl, false, true);

		cacheUniqueFindersCache(indexEntryModelImpl);

		if (isNew) {
			indexEntry.setNew(false);
		}

		indexEntry.resetOriginalValues();

		return indexEntry;
	}

	/**
	 * Returns the index entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchIndexEntryException {

		IndexEntry indexEntry = fetchByPrimaryKey(primaryKey);

		if (indexEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchIndexEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return indexEntry;
	}

	/**
	 * Returns the index entry with the primary key or throws a <code>NoSuchIndexEntryException</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws NoSuchIndexEntryException if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry findByPrimaryKey(long indexEntryId)
		throws NoSuchIndexEntryException {

		return findByPrimaryKey((Serializable)indexEntryId);
	}

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				IndexEntry.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		IndexEntry indexEntry = (IndexEntry)entityCache.getResult(
			IndexEntryImpl.class, primaryKey);

		if (indexEntry != null) {
			return indexEntry;
		}

		Session session = null;

		try {
			session = openSession();

			indexEntry = (IndexEntry)session.get(
				IndexEntryImpl.class, primaryKey);

			if (indexEntry != null) {
				cacheResult(indexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return indexEntry;
	}

	/**
	 * Returns the index entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry, or <code>null</code> if a index entry with the primary key could not be found
	 */
	@Override
	public IndexEntry fetchByPrimaryKey(long indexEntryId) {
		return fetchByPrimaryKey((Serializable)indexEntryId);
	}

	@Override
	public Map<Serializable, IndexEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(IndexEntry.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, IndexEntry> map =
			new HashMap<Serializable, IndexEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			IndexEntry indexEntry = fetchByPrimaryKey(primaryKey);

			if (indexEntry != null) {
				map.put(primaryKey, indexEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						IndexEntry.class, primaryKey)) {

				IndexEntry indexEntry = (IndexEntry)entityCache.getResult(
					IndexEntryImpl.class, primaryKey);

				if (indexEntry == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, indexEntry);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (IndexEntry indexEntry : (List<IndexEntry>)query.list()) {
				map.put(indexEntry.getPrimaryKeyObj(), indexEntry);

				cacheResult(indexEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the index entries.
	 *
	 * @return the index entries
	 */
	@Override
	public List<IndexEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of index entries
	 */
	@Override
	public List<IndexEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of index entries
	 */
	@Override
	public List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of index entries
	 */
	@Override
	public List<IndexEntry> findAll(
		int start, int end, OrderByComparator<IndexEntry> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

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

			List<IndexEntry> list = null;

			if (useFinderCache) {
				list = (List<IndexEntry>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_INDEXENTRY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_INDEXENTRY;

					sql = sql.concat(IndexEntryModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<IndexEntry>)QueryUtil.list(
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
	}

	/**
	 * Removes all the index entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (IndexEntry indexEntry : findAll()) {
			remove(indexEntry);
		}
	}

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					IndexEntry.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(_SQL_COUNT_INDEXENTRY);

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
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "indexEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_INDEXENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return IndexEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "IndexEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("companyId");
		ctMergeColumnNames.add("ownerId");
		ctMergeColumnNames.add("ownerType");
		ctMergeColumnNames.add("plid");
		ctMergeColumnNames.add("portletId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("indexEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"ownerId", "ownerType", "plid", "portletId"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the index entry persistence.
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

		_finderPathWithPaginationFindByOwnerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOwnerId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ownerId"}, true);

		_finderPathWithoutPaginationFindByOwnerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOwnerId",
			new String[] {Long.class.getName()}, new String[] {"ownerId"},
			true);

		_finderPathCountByOwnerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOwnerId",
			new String[] {Long.class.getName()}, new String[] {"ownerId"},
			false);

		_finderPathWithPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"plid"}, true);

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, true);

		_finderPathCountByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, false);

		_finderPathWithPaginationFindByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortletId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletId"}, true);

		_finderPathWithoutPaginationFindByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPortletId",
			new String[] {String.class.getName()}, new String[] {"portletId"},
			true);

		_finderPathCountByPortletId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortletId",
			new String[] {String.class.getName()}, new String[] {"portletId"},
			false);

		_finderPathWithPaginationFindByO_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_P",
			new String[] {
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"ownerType", "portletId"}, true);

		_finderPathWithoutPaginationFindByO_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_P",
			new String[] {Integer.class.getName(), String.class.getName()},
			new String[] {"ownerType", "portletId"}, true);

		_finderPathCountByO_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_P",
			new String[] {Integer.class.getName(), String.class.getName()},
			new String[] {"ownerType", "portletId"}, false);

		_finderPathWithPaginationFindByP_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"plid", "portletId"}, true);

		_finderPathWithoutPaginationFindByP_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"plid", "portletId"}, true);

		_finderPathCountByP_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_P",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"plid", "portletId"}, false);

		_finderPathWithPaginationFindByO_O_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ownerId", "ownerType", "plid"}, true);

		_finderPathWithoutPaginationFindByO_O_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {"ownerId", "ownerType", "plid"}, true);

		_finderPathCountByO_O_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName()
			},
			new String[] {"ownerId", "ownerType", "plid"}, false);

		_finderPathWithPaginationFindByO_O_PI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ownerId", "ownerType", "portletId"}, true);

		_finderPathWithoutPaginationFindByO_O_PI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {"ownerId", "ownerType", "portletId"}, true);

		_finderPathCountByO_O_PI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_O_PI",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {"ownerId", "ownerType", "portletId"}, false);

		_finderPathWithPaginationFindByO_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ownerType", "plid", "portletId"}, true);

		_finderPathWithoutPaginationFindByO_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"ownerType", "plid", "portletId"}, true);

		_finderPathCountByO_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_P_P",
			new String[] {
				Integer.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {"ownerType", "plid", "portletId"}, false);

		_finderPathWithPaginationFindByC_O_O_LikeP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_O_O_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "ownerId", "ownerType", "portletId"},
			true);

		_finderPathWithPaginationCountByC_O_O_LikeP = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_O_O_LikeP",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), String.class.getName()
			},
			new String[] {"companyId", "ownerId", "ownerType", "portletId"},
			false);

		_finderPathFetchByO_O_P_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByO_O_P_P",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"ownerId", "ownerType", "plid", "portletId"}, true);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		IndexEntryUtil.setPersistence(this);
	}

	public void destroy() {
		IndexEntryUtil.setPersistence(null);

		entityCache.removeCache(IndexEntryImpl.class.getName());
	}

	@ServiceReference(type = CTPersistenceHelper.class)
	protected CTPersistenceHelper ctPersistenceHelper;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_INDEXENTRY =
		"SELECT indexEntry FROM IndexEntry indexEntry";

	private static final String _SQL_SELECT_INDEXENTRY_WHERE =
		"SELECT indexEntry FROM IndexEntry indexEntry WHERE ";

	private static final String _SQL_COUNT_INDEXENTRY =
		"SELECT COUNT(indexEntry) FROM IndexEntry indexEntry";

	private static final String _SQL_COUNT_INDEXENTRY_WHERE =
		"SELECT COUNT(indexEntry) FROM IndexEntry indexEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "indexEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No IndexEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No IndexEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		IndexEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}