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

package com.liferay.changeset.service.persistence.impl;

import com.liferay.changeset.exception.NoSuchEntryException;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.model.ChangesetEntryTable;
import com.liferay.changeset.model.impl.ChangesetEntryImpl;
import com.liferay.changeset.model.impl.ChangesetEntryModelImpl;
import com.liferay.changeset.service.persistence.ChangesetEntryPersistence;
import com.liferay.changeset.service.persistence.impl.constants.ChangesetPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the changeset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {ChangesetEntryPersistence.class, BasePersistence.class})
public class ChangesetEntryPersistenceImpl
	extends BasePersistenceImpl<ChangesetEntry>
	implements ChangesetEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ChangesetEntryUtil</code> to access the changeset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ChangesetEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the changeset entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator,
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

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetEntry changesetEntry : list) {
					if (groupId != changesetEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Returns the first changeset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByGroupId_First(
			long groupId, OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first changeset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByGroupId_First(
		long groupId, OrderByComparator<ChangesetEntry> orderByComparator) {

		List<ChangesetEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByGroupId_Last(
			long groupId, OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last changeset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<ChangesetEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<ChangesetEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset entries before and after the current changeset entry in the ordered set where groupId = &#63;.
	 *
	 * @param changesetEntryId the primary key of the current changeset entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry[] findByGroupId_PrevAndNext(
			long changesetEntryId, long groupId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByPrimaryKey(changesetEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry[] array = new ChangesetEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, changesetEntry, groupId, orderByComparator, true);

			array[1] = changesetEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, changesetEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetEntry getByGroupId_PrevAndNext(
		Session session, ChangesetEntry changesetEntry, long groupId,
		OrderByComparator<ChangesetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

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
			sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
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
						changesetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ChangesetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (ChangesetEntry changesetEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

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
		"changesetEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the changeset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator,
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

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetEntry changesetEntry : list) {
					if (companyId != changesetEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Returns the first changeset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByCompanyId_First(
			long companyId, OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first changeset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<ChangesetEntry> orderByComparator) {

		List<ChangesetEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByCompanyId_Last(
			long companyId, OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last changeset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<ChangesetEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ChangesetEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset entries before and after the current changeset entry in the ordered set where companyId = &#63;.
	 *
	 * @param changesetEntryId the primary key of the current changeset entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry[] findByCompanyId_PrevAndNext(
			long changesetEntryId, long companyId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByPrimaryKey(changesetEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry[] array = new ChangesetEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, changesetEntry, companyId, orderByComparator, true);

			array[1] = changesetEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, changesetEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ChangesetEntry getByCompanyId_PrevAndNext(
		Session session, ChangesetEntry changesetEntry, long companyId,
		OrderByComparator<ChangesetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						changesetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ChangesetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ChangesetEntry changesetEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"changesetEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByChangesetCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByChangesetCollectionId;
	private FinderPath _finderPathCountByChangesetCollectionId;

	/**
	 * Returns all the changeset entries where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @return the matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByChangesetCollectionId(
		long changesetCollectionId) {

		return findByChangesetCollectionId(
			changesetCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries where changesetCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByChangesetCollectionId(
		long changesetCollectionId, int start, int end) {

		return findByChangesetCollectionId(
			changesetCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries where changesetCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByChangesetCollectionId(
		long changesetCollectionId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findByChangesetCollectionId(
			changesetCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset entries where changesetCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByChangesetCollectionId(
		long changesetCollectionId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByChangesetCollectionId;
				finderArgs = new Object[] {changesetCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByChangesetCollectionId;
			finderArgs = new Object[] {
				changesetCollectionId, start, end, orderByComparator
			};
		}

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetEntry changesetEntry : list) {
					if (changesetCollectionId !=
							changesetEntry.getChangesetCollectionId()) {

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

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_CHANGESETCOLLECTIONID_CHANGESETCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Returns the first changeset entry in the ordered set where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByChangesetCollectionId_First(
			long changesetCollectionId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByChangesetCollectionId_First(
			changesetCollectionId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("changesetCollectionId=");
		sb.append(changesetCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first changeset entry in the ordered set where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByChangesetCollectionId_First(
		long changesetCollectionId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		List<ChangesetEntry> list = findByChangesetCollectionId(
			changesetCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset entry in the ordered set where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByChangesetCollectionId_Last(
			long changesetCollectionId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByChangesetCollectionId_Last(
			changesetCollectionId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("changesetCollectionId=");
		sb.append(changesetCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last changeset entry in the ordered set where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByChangesetCollectionId_Last(
		long changesetCollectionId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		int count = countByChangesetCollectionId(changesetCollectionId);

		if (count == 0) {
			return null;
		}

		List<ChangesetEntry> list = findByChangesetCollectionId(
			changesetCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset entries before and after the current changeset entry in the ordered set where changesetCollectionId = &#63;.
	 *
	 * @param changesetEntryId the primary key of the current changeset entry
	 * @param changesetCollectionId the changeset collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry[] findByChangesetCollectionId_PrevAndNext(
			long changesetEntryId, long changesetCollectionId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByPrimaryKey(changesetEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry[] array = new ChangesetEntryImpl[3];

			array[0] = getByChangesetCollectionId_PrevAndNext(
				session, changesetEntry, changesetCollectionId,
				orderByComparator, true);

			array[1] = changesetEntry;

			array[2] = getByChangesetCollectionId_PrevAndNext(
				session, changesetEntry, changesetCollectionId,
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

	protected ChangesetEntry getByChangesetCollectionId_PrevAndNext(
		Session session, ChangesetEntry changesetEntry,
		long changesetCollectionId,
		OrderByComparator<ChangesetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_CHANGESETCOLLECTIONID_CHANGESETCOLLECTIONID_2);

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
			sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(changesetCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						changesetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ChangesetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset entries where changesetCollectionId = &#63; from the database.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 */
	@Override
	public void removeByChangesetCollectionId(long changesetCollectionId) {
		for (ChangesetEntry changesetEntry :
				findByChangesetCollectionId(
					changesetCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries where changesetCollectionId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByChangesetCollectionId(long changesetCollectionId) {
		FinderPath finderPath = _finderPathCountByChangesetCollectionId;

		Object[] finderArgs = new Object[] {changesetCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_CHANGESETCOLLECTIONID_CHANGESETCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

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

	private static final String
		_FINDER_COLUMN_CHANGESETCOLLECTIONID_CHANGESETCOLLECTIONID_2 =
			"changesetEntry.changesetCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;

	/**
	 * Returns all the changeset entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByG_C(long groupId, long classNameId) {
		return findByG_C(
			groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByG_C(
		long groupId, long classNameId, int start, int end) {

		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findByG_C(
			groupId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, classNameId, start, end, orderByComparator
			};
		}

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetEntry changesetEntry : list) {
					if ((groupId != changesetEntry.getGroupId()) ||
						(classNameId != changesetEntry.getClassNameId())) {

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

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Returns the first changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByG_C_First(
			long groupId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByG_C_First(
			groupId, classNameId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByG_C_First(
		long groupId, long classNameId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		List<ChangesetEntry> list = findByG_C(
			groupId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByG_C_Last(
			long groupId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByG_C_Last(
			groupId, classNameId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByG_C_Last(
		long groupId, long classNameId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<ChangesetEntry> list = findByG_C(
			groupId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset entries before and after the current changeset entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetEntryId the primary key of the current changeset entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry[] findByG_C_PrevAndNext(
			long changesetEntryId, long groupId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByPrimaryKey(changesetEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry[] array = new ChangesetEntryImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, changesetEntry, groupId, classNameId,
				orderByComparator, true);

			array[1] = changesetEntry;

			array[2] = getByG_C_PrevAndNext(
				session, changesetEntry, groupId, classNameId,
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

	protected ChangesetEntry getByG_C_PrevAndNext(
		Session session, ChangesetEntry changesetEntry, long groupId,
		long classNameId, OrderByComparator<ChangesetEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						changesetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ChangesetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset entries where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (ChangesetEntry changesetEntry :
				findByG_C(
					groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = _finderPathCountByG_C;

		Object[] finderArgs = new Object[] {groupId, classNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"changesetEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 =
		"changesetEntry.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the changeset entries where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @return the matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByC_C(
		long changesetCollectionId, long classNameId) {

		return findByC_C(
			changesetCollectionId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByC_C(
		long changesetCollectionId, long classNameId, int start, int end) {

		return findByC_C(changesetCollectionId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByC_C(
		long changesetCollectionId, long classNameId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findByC_C(
			changesetCollectionId, classNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the changeset entries where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching changeset entries
	 */
	@Override
	public List<ChangesetEntry> findByC_C(
		long changesetCollectionId, long classNameId, int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {changesetCollectionId, classNameId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				changesetCollectionId, classNameId, start, end,
				orderByComparator
			};
		}

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ChangesetEntry changesetEntry : list) {
					if ((changesetCollectionId !=
							changesetEntry.getChangesetCollectionId()) ||
						(classNameId != changesetEntry.getClassNameId())) {

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

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CHANGESETCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

				queryPos.add(classNameId);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Returns the first changeset entry in the ordered set where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByC_C_First(
			long changesetCollectionId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByC_C_First(
			changesetCollectionId, classNameId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("changesetCollectionId=");
		sb.append(changesetCollectionId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first changeset entry in the ordered set where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByC_C_First(
		long changesetCollectionId, long classNameId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		List<ChangesetEntry> list = findByC_C(
			changesetCollectionId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last changeset entry in the ordered set where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByC_C_Last(
			long changesetCollectionId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByC_C_Last(
			changesetCollectionId, classNameId, orderByComparator);

		if (changesetEntry != null) {
			return changesetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("changesetCollectionId=");
		sb.append(changesetCollectionId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last changeset entry in the ordered set where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByC_C_Last(
		long changesetCollectionId, long classNameId,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		int count = countByC_C(changesetCollectionId, classNameId);

		if (count == 0) {
			return null;
		}

		List<ChangesetEntry> list = findByC_C(
			changesetCollectionId, classNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the changeset entries before and after the current changeset entry in the ordered set where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetEntryId the primary key of the current changeset entry
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry[] findByC_C_PrevAndNext(
			long changesetEntryId, long changesetCollectionId, long classNameId,
			OrderByComparator<ChangesetEntry> orderByComparator)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByPrimaryKey(changesetEntryId);

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry[] array = new ChangesetEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, changesetEntry, changesetCollectionId, classNameId,
				orderByComparator, true);

			array[1] = changesetEntry;

			array[2] = getByC_C_PrevAndNext(
				session, changesetEntry, changesetCollectionId, classNameId,
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

	protected ChangesetEntry getByC_C_PrevAndNext(
		Session session, ChangesetEntry changesetEntry,
		long changesetCollectionId, long classNameId,
		OrderByComparator<ChangesetEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CHANGESETCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			sb.append(ChangesetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(changesetCollectionId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						changesetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ChangesetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the changeset entries where changesetCollectionId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long changesetCollectionId, long classNameId) {
		for (ChangesetEntry changesetEntry :
				findByC_C(
					changesetCollectionId, classNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries where changesetCollectionId = &#63; and classNameId = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByC_C(long changesetCollectionId, long classNameId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {changesetCollectionId, classNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CHANGESETCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_C_C_CHANGESETCOLLECTIONID_2 =
		"changesetEntry.changesetCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"changesetEntry.classNameId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the changeset entry where changesetCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching changeset entry
	 * @throws NoSuchEntryException if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry findByC_C_C(
			long changesetCollectionId, long classNameId, long classPK)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByC_C_C(
			changesetCollectionId, classNameId, classPK);

		if (changesetEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("changesetCollectionId=");
			sb.append(changesetCollectionId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return changesetEntry;
	}

	/**
	 * Returns the changeset entry where changesetCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByC_C_C(
		long changesetCollectionId, long classNameId, long classPK) {

		return fetchByC_C_C(changesetCollectionId, classNameId, classPK, true);
	}

	/**
	 * Returns the changeset entry where changesetCollectionId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching changeset entry, or <code>null</code> if a matching changeset entry could not be found
	 */
	@Override
	public ChangesetEntry fetchByC_C_C(
		long changesetCollectionId, long classNameId, long classPK,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				changesetCollectionId, classNameId, classPK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_C, finderArgs);
		}

		if (result instanceof ChangesetEntry) {
			ChangesetEntry changesetEntry = (ChangesetEntry)result;

			if ((changesetCollectionId !=
					changesetEntry.getChangesetCollectionId()) ||
				(classNameId != changesetEntry.getClassNameId()) ||
				(classPK != changesetEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CHANGESETCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<ChangesetEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					ChangesetEntry changesetEntry = list.get(0);

					result = changesetEntry;

					cacheResult(changesetEntry);
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
			return (ChangesetEntry)result;
		}
	}

	/**
	 * Removes the changeset entry where changesetCollectionId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the changeset entry that was removed
	 */
	@Override
	public ChangesetEntry removeByC_C_C(
			long changesetCollectionId, long classNameId, long classPK)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = findByC_C_C(
			changesetCollectionId, classNameId, classPK);

		return remove(changesetEntry);
	}

	/**
	 * Returns the number of changeset entries where changesetCollectionId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param changesetCollectionId the changeset collection ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching changeset entries
	 */
	@Override
	public int countByC_C_C(
		long changesetCollectionId, long classNameId, long classPK) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			changesetCollectionId, classNameId, classPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CHANGESETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CHANGESETCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(changesetCollectionId);

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

	private static final String _FINDER_COLUMN_C_C_C_CHANGESETCOLLECTIONID_2 =
		"changesetEntry.changesetCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"changesetEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"changesetEntry.classPK = ?";

	public ChangesetEntryPersistenceImpl() {
		setModelClass(ChangesetEntry.class);

		setModelImplClass(ChangesetEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ChangesetEntryTable.INSTANCE);
	}

	/**
	 * Caches the changeset entry in the entity cache if it is enabled.
	 *
	 * @param changesetEntry the changeset entry
	 */
	@Override
	public void cacheResult(ChangesetEntry changesetEntry) {
		entityCache.putResult(
			ChangesetEntryImpl.class, changesetEntry.getPrimaryKey(),
			changesetEntry);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				changesetEntry.getChangesetCollectionId(),
				changesetEntry.getClassNameId(), changesetEntry.getClassPK()
			},
			changesetEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the changeset entries in the entity cache if it is enabled.
	 *
	 * @param changesetEntries the changeset entries
	 */
	@Override
	public void cacheResult(List<ChangesetEntry> changesetEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (changesetEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ChangesetEntry changesetEntry : changesetEntries) {
			if (entityCache.getResult(
					ChangesetEntryImpl.class, changesetEntry.getPrimaryKey()) ==
						null) {

				cacheResult(changesetEntry);
			}
		}
	}

	/**
	 * Clears the cache for all changeset entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ChangesetEntryImpl.class);

		finderCache.clearCache(ChangesetEntryImpl.class);
	}

	/**
	 * Clears the cache for the changeset entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ChangesetEntry changesetEntry) {
		entityCache.removeResult(ChangesetEntryImpl.class, changesetEntry);
	}

	@Override
	public void clearCache(List<ChangesetEntry> changesetEntries) {
		for (ChangesetEntry changesetEntry : changesetEntries) {
			entityCache.removeResult(ChangesetEntryImpl.class, changesetEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ChangesetEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ChangesetEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ChangesetEntryModelImpl changesetEntryModelImpl) {

		Object[] args = new Object[] {
			changesetEntryModelImpl.getChangesetCollectionId(),
			changesetEntryModelImpl.getClassNameId(),
			changesetEntryModelImpl.getClassPK()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, changesetEntryModelImpl);
	}

	/**
	 * Creates a new changeset entry with the primary key. Does not add the changeset entry to the database.
	 *
	 * @param changesetEntryId the primary key for the new changeset entry
	 * @return the new changeset entry
	 */
	@Override
	public ChangesetEntry create(long changesetEntryId) {
		ChangesetEntry changesetEntry = new ChangesetEntryImpl();

		changesetEntry.setNew(true);
		changesetEntry.setPrimaryKey(changesetEntryId);

		changesetEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return changesetEntry;
	}

	/**
	 * Removes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetEntryId the primary key of the changeset entry
	 * @return the changeset entry that was removed
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry remove(long changesetEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)changesetEntryId);
	}

	/**
	 * Removes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the changeset entry
	 * @return the changeset entry that was removed
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			ChangesetEntry changesetEntry = (ChangesetEntry)session.get(
				ChangesetEntryImpl.class, primaryKey);

			if (changesetEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(changesetEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected ChangesetEntry removeImpl(ChangesetEntry changesetEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(changesetEntry)) {
				changesetEntry = (ChangesetEntry)session.get(
					ChangesetEntryImpl.class,
					changesetEntry.getPrimaryKeyObj());
			}

			if (changesetEntry != null) {
				session.delete(changesetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (changesetEntry != null) {
			clearCache(changesetEntry);
		}

		return changesetEntry;
	}

	@Override
	public ChangesetEntry updateImpl(ChangesetEntry changesetEntry) {
		boolean isNew = changesetEntry.isNew();

		if (!(changesetEntry instanceof ChangesetEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(changesetEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					changesetEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in changesetEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ChangesetEntry implementation " +
					changesetEntry.getClass());
		}

		ChangesetEntryModelImpl changesetEntryModelImpl =
			(ChangesetEntryModelImpl)changesetEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (changesetEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				changesetEntry.setCreateDate(date);
			}
			else {
				changesetEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!changesetEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				changesetEntry.setModifiedDate(date);
			}
			else {
				changesetEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(changesetEntry);
			}
			else {
				changesetEntry = (ChangesetEntry)session.merge(changesetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ChangesetEntryImpl.class, changesetEntryModelImpl, false, true);

		cacheUniqueFindersCache(changesetEntryModelImpl);

		if (isNew) {
			changesetEntry.setNew(false);
		}

		changesetEntry.resetOriginalValues();

		return changesetEntry;
	}

	/**
	 * Returns the changeset entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the changeset entry
	 * @return the changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		ChangesetEntry changesetEntry = fetchByPrimaryKey(primaryKey);

		if (changesetEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return changesetEntry;
	}

	/**
	 * Returns the changeset entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param changesetEntryId the primary key of the changeset entry
	 * @return the changeset entry
	 * @throws NoSuchEntryException if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry findByPrimaryKey(long changesetEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)changesetEntryId);
	}

	/**
	 * Returns the changeset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param changesetEntryId the primary key of the changeset entry
	 * @return the changeset entry, or <code>null</code> if a changeset entry with the primary key could not be found
	 */
	@Override
	public ChangesetEntry fetchByPrimaryKey(long changesetEntryId) {
		return fetchByPrimaryKey((Serializable)changesetEntryId);
	}

	/**
	 * Returns all the changeset entries.
	 *
	 * @return the changeset entries
	 */
	@Override
	public List<ChangesetEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the changeset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @return the range of changeset entries
	 */
	@Override
	public List<ChangesetEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the changeset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of changeset entries
	 */
	@Override
	public List<ChangesetEntry> findAll(
		int start, int end,
		OrderByComparator<ChangesetEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the changeset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ChangesetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset entries
	 * @param end the upper bound of the range of changeset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of changeset entries
	 */
	@Override
	public List<ChangesetEntry> findAll(
		int start, int end, OrderByComparator<ChangesetEntry> orderByComparator,
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

		List<ChangesetEntry> list = null;

		if (useFinderCache) {
			list = (List<ChangesetEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CHANGESETENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CHANGESETENTRY;

				sql = sql.concat(ChangesetEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ChangesetEntry>)QueryUtil.list(
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
	 * Removes all the changeset entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ChangesetEntry changesetEntry : findAll()) {
			remove(changesetEntry);
		}
	}

	/**
	 * Returns the number of changeset entries.
	 *
	 * @return the number of changeset entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CHANGESETENTRY);

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
		return "changesetEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CHANGESETENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ChangesetEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the changeset entry persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByChangesetCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByChangesetCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"changesetCollectionId"}, true);

		_finderPathWithoutPaginationFindByChangesetCollectionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByChangesetCollectionId",
				new String[] {Long.class.getName()},
				new String[] {"changesetCollectionId"}, true);

		_finderPathCountByChangesetCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByChangesetCollectionId", new String[] {Long.class.getName()},
			new String[] {"changesetCollectionId"}, false);

		_finderPathWithPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "classNameId"}, true);

		_finderPathCountByG_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "classNameId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"changesetCollectionId", "classNameId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"changesetCollectionId", "classNameId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"changesetCollectionId", "classNameId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"changesetCollectionId", "classNameId", "classPK"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"changesetCollectionId", "classNameId", "classPK"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ChangesetEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ChangesetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CHANGESETENTRY =
		"SELECT changesetEntry FROM ChangesetEntry changesetEntry";

	private static final String _SQL_SELECT_CHANGESETENTRY_WHERE =
		"SELECT changesetEntry FROM ChangesetEntry changesetEntry WHERE ";

	private static final String _SQL_COUNT_CHANGESETENTRY =
		"SELECT COUNT(changesetEntry) FROM ChangesetEntry changesetEntry";

	private static final String _SQL_COUNT_CHANGESETENTRY_WHERE =
		"SELECT COUNT(changesetEntry) FROM ChangesetEntry changesetEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "changesetEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ChangesetEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ChangesetEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ChangesetEntryModelArgumentsResolver
		_changesetEntryModelArgumentsResolver;

}