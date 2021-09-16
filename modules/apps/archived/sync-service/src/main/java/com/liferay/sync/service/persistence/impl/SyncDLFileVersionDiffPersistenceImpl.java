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

package com.liferay.sync.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.sync.exception.NoSuchDLFileVersionDiffException;
import com.liferay.sync.model.SyncDLFileVersionDiff;
import com.liferay.sync.model.SyncDLFileVersionDiffTable;
import com.liferay.sync.model.impl.SyncDLFileVersionDiffImpl;
import com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffPersistence;
import com.liferay.sync.service.persistence.impl.constants.SyncPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the sync dl file version diff service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {SyncDLFileVersionDiffPersistence.class, BasePersistence.class}
)
public class SyncDLFileVersionDiffPersistenceImpl
	extends BasePersistenceImpl<SyncDLFileVersionDiff>
	implements SyncDLFileVersionDiffPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SyncDLFileVersionDiffUtil</code> to access the sync dl file version diff persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SyncDLFileVersionDiffImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByFileEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryId;
	private FinderPath _finderPathCountByFileEntryId;

	/**
	 * Returns all the sync dl file version diffs where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl file version diffs where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @return the range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFileEntryId;
				finderArgs = new Object[] {fileEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFileEntryId;
			finderArgs = new Object[] {
				fileEntryId, start, end, orderByComparator
			};
		}

		List<SyncDLFileVersionDiff> list = null;

		if (useFinderCache) {
			list = (List<SyncDLFileVersionDiff>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLFileVersionDiff syncDLFileVersionDiff : list) {
					if (fileEntryId != syncDLFileVersionDiff.getFileEntryId()) {
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

			sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLFileVersionDiffModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				list = (List<SyncDLFileVersionDiff>)QueryUtil.list(
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
	 * Returns the first sync dl file version diff in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = fetchByFileEntryId_First(
			fileEntryId, orderByComparator);

		if (syncDLFileVersionDiff != null) {
			return syncDLFileVersionDiff;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchDLFileVersionDiffException(sb.toString());
	}

	/**
	 * Returns the first sync dl file version diff in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		List<SyncDLFileVersionDiff> list = findByFileEntryId(
			fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl file version diff in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByFileEntryId_Last(
			long fileEntryId,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = fetchByFileEntryId_Last(
			fileEntryId, orderByComparator);

		if (syncDLFileVersionDiff != null) {
			return syncDLFileVersionDiff;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchDLFileVersionDiffException(sb.toString());
	}

	/**
	 * Returns the last sync dl file version diff in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByFileEntryId_Last(
		long fileEntryId,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<SyncDLFileVersionDiff> list = findByFileEntryId(
			fileEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl file version diffs before and after the current sync dl file version diff in the ordered set where fileEntryId = &#63;.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the current sync dl file version diff
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff[] findByFileEntryId_PrevAndNext(
			long syncDLFileVersionDiffId, long fileEntryId,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = findByPrimaryKey(
			syncDLFileVersionDiffId);

		Session session = null;

		try {
			session = openSession();

			SyncDLFileVersionDiff[] array = new SyncDLFileVersionDiffImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(
				session, syncDLFileVersionDiff, fileEntryId, orderByComparator,
				true);

			array[1] = syncDLFileVersionDiff;

			array[2] = getByFileEntryId_PrevAndNext(
				session, syncDLFileVersionDiff, fileEntryId, orderByComparator,
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

	protected SyncDLFileVersionDiff getByFileEntryId_PrevAndNext(
		Session session, SyncDLFileVersionDiff syncDLFileVersionDiff,
		long fileEntryId,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator,
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

		sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE);

		sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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
			sb.append(SyncDLFileVersionDiffModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						syncDLFileVersionDiff)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLFileVersionDiff> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl file version diffs where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (SyncDLFileVersionDiff syncDLFileVersionDiff :
				findByFileEntryId(
					fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(syncDLFileVersionDiff);
		}
	}

	/**
	 * Returns the number of sync dl file version diffs where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching sync dl file version diffs
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = _finderPathCountByFileEntryId;

		Object[] finderArgs = new Object[] {fileEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SYNCDLFILEVERSIONDIFF_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 =
		"syncDLFileVersionDiff.fileEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByLtExpirationDate;
	private FinderPath _finderPathWithPaginationCountByLtExpirationDate;

	/**
	 * Returns all the sync dl file version diffs where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByLtExpirationDate(
		Date expirationDate) {

		return findByLtExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl file version diffs where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @return the range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByLtExpirationDate(
		Date expirationDate, int start, int end) {

		return findByLtExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		return findByLtExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtExpirationDate;
		finderArgs = new Object[] {
			_getTime(expirationDate), start, end, orderByComparator
		};

		List<SyncDLFileVersionDiff> list = null;

		if (useFinderCache) {
			list = (List<SyncDLFileVersionDiff>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SyncDLFileVersionDiff syncDLFileVersionDiff : list) {
					if (expirationDate.getTime() <=
							syncDLFileVersionDiff.getExpirationDate(
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

			sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SyncDLFileVersionDiffModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<SyncDLFileVersionDiff>)QueryUtil.list(
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
	 * Returns the first sync dl file version diff in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByLtExpirationDate_First(
			Date expirationDate,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff =
			fetchByLtExpirationDate_First(expirationDate, orderByComparator);

		if (syncDLFileVersionDiff != null) {
			return syncDLFileVersionDiff;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchDLFileVersionDiffException(sb.toString());
	}

	/**
	 * Returns the first sync dl file version diff in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByLtExpirationDate_First(
		Date expirationDate,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		List<SyncDLFileVersionDiff> list = findByLtExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sync dl file version diff in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByLtExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff =
			fetchByLtExpirationDate_Last(expirationDate, orderByComparator);

		if (syncDLFileVersionDiff != null) {
			return syncDLFileVersionDiff;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchDLFileVersionDiffException(sb.toString());
	}

	/**
	 * Returns the last sync dl file version diff in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByLtExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		int count = countByLtExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<SyncDLFileVersionDiff> list = findByLtExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sync dl file version diffs before and after the current sync dl file version diff in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the current sync dl file version diff
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff[] findByLtExpirationDate_PrevAndNext(
			long syncDLFileVersionDiffId, Date expirationDate,
			OrderByComparator<SyncDLFileVersionDiff> orderByComparator)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = findByPrimaryKey(
			syncDLFileVersionDiffId);

		Session session = null;

		try {
			session = openSession();

			SyncDLFileVersionDiff[] array = new SyncDLFileVersionDiffImpl[3];

			array[0] = getByLtExpirationDate_PrevAndNext(
				session, syncDLFileVersionDiff, expirationDate,
				orderByComparator, true);

			array[1] = syncDLFileVersionDiff;

			array[2] = getByLtExpirationDate_PrevAndNext(
				session, syncDLFileVersionDiff, expirationDate,
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

	protected SyncDLFileVersionDiff getByLtExpirationDate_PrevAndNext(
		Session session, SyncDLFileVersionDiff syncDLFileVersionDiff,
		Date expirationDate,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator,
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

		sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
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
			sb.append(SyncDLFileVersionDiffModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						syncDLFileVersionDiff)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SyncDLFileVersionDiff> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sync dl file version diffs where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByLtExpirationDate(Date expirationDate) {
		for (SyncDLFileVersionDiff syncDLFileVersionDiff :
				findByLtExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(syncDLFileVersionDiff);
		}
	}

	/**
	 * Returns the number of sync dl file version diffs where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching sync dl file version diffs
	 */
	@Override
	public int countByLtExpirationDate(Date expirationDate) {
		FinderPath finderPath =
			_finderPathWithPaginationCountByLtExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SYNCDLFILEVERSIONDIFF_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_1 =
			"syncDLFileVersionDiff.expirationDate IS NULL";

	private static final String
		_FINDER_COLUMN_LTEXPIRATIONDATE_EXPIRATIONDATE_2 =
			"syncDLFileVersionDiff.expirationDate < ?";

	private FinderPath _finderPathFetchByF_S_T;
	private FinderPath _finderPathCountByF_S_T;

	/**
	 * Returns the sync dl file version diff where fileEntryId = &#63; and sourceFileVersionId = &#63; and targetFileVersionId = &#63; or throws a <code>NoSuchDLFileVersionDiffException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param sourceFileVersionId the source file version ID
	 * @param targetFileVersionId the target file version ID
	 * @return the matching sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByF_S_T(
			long fileEntryId, long sourceFileVersionId,
			long targetFileVersionId)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = fetchByF_S_T(
			fileEntryId, sourceFileVersionId, targetFileVersionId);

		if (syncDLFileVersionDiff == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fileEntryId=");
			sb.append(fileEntryId);

			sb.append(", sourceFileVersionId=");
			sb.append(sourceFileVersionId);

			sb.append(", targetFileVersionId=");
			sb.append(targetFileVersionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDLFileVersionDiffException(sb.toString());
		}

		return syncDLFileVersionDiff;
	}

	/**
	 * Returns the sync dl file version diff where fileEntryId = &#63; and sourceFileVersionId = &#63; and targetFileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param sourceFileVersionId the source file version ID
	 * @param targetFileVersionId the target file version ID
	 * @return the matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByF_S_T(
		long fileEntryId, long sourceFileVersionId, long targetFileVersionId) {

		return fetchByF_S_T(
			fileEntryId, sourceFileVersionId, targetFileVersionId, true);
	}

	/**
	 * Returns the sync dl file version diff where fileEntryId = &#63; and sourceFileVersionId = &#63; and targetFileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param sourceFileVersionId the source file version ID
	 * @param targetFileVersionId the target file version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sync dl file version diff, or <code>null</code> if a matching sync dl file version diff could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByF_S_T(
		long fileEntryId, long sourceFileVersionId, long targetFileVersionId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				fileEntryId, sourceFileVersionId, targetFileVersionId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByF_S_T, finderArgs);
		}

		if (result instanceof SyncDLFileVersionDiff) {
			SyncDLFileVersionDiff syncDLFileVersionDiff =
				(SyncDLFileVersionDiff)result;

			if ((fileEntryId != syncDLFileVersionDiff.getFileEntryId()) ||
				(sourceFileVersionId !=
					syncDLFileVersionDiff.getSourceFileVersionId()) ||
				(targetFileVersionId !=
					syncDLFileVersionDiff.getTargetFileVersionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE);

			sb.append(_FINDER_COLUMN_F_S_T_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_S_T_SOURCEFILEVERSIONID_2);

			sb.append(_FINDER_COLUMN_F_S_T_TARGETFILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(sourceFileVersionId);

				queryPos.add(targetFileVersionId);

				List<SyncDLFileVersionDiff> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByF_S_T, finderArgs, list);
					}
				}
				else {
					SyncDLFileVersionDiff syncDLFileVersionDiff = list.get(0);

					result = syncDLFileVersionDiff;

					cacheResult(syncDLFileVersionDiff);
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
			return (SyncDLFileVersionDiff)result;
		}
	}

	/**
	 * Removes the sync dl file version diff where fileEntryId = &#63; and sourceFileVersionId = &#63; and targetFileVersionId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param sourceFileVersionId the source file version ID
	 * @param targetFileVersionId the target file version ID
	 * @return the sync dl file version diff that was removed
	 */
	@Override
	public SyncDLFileVersionDiff removeByF_S_T(
			long fileEntryId, long sourceFileVersionId,
			long targetFileVersionId)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = findByF_S_T(
			fileEntryId, sourceFileVersionId, targetFileVersionId);

		return remove(syncDLFileVersionDiff);
	}

	/**
	 * Returns the number of sync dl file version diffs where fileEntryId = &#63; and sourceFileVersionId = &#63; and targetFileVersionId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param sourceFileVersionId the source file version ID
	 * @param targetFileVersionId the target file version ID
	 * @return the number of matching sync dl file version diffs
	 */
	@Override
	public int countByF_S_T(
		long fileEntryId, long sourceFileVersionId, long targetFileVersionId) {

		FinderPath finderPath = _finderPathCountByF_S_T;

		Object[] finderArgs = new Object[] {
			fileEntryId, sourceFileVersionId, targetFileVersionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SYNCDLFILEVERSIONDIFF_WHERE);

			sb.append(_FINDER_COLUMN_F_S_T_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_S_T_SOURCEFILEVERSIONID_2);

			sb.append(_FINDER_COLUMN_F_S_T_TARGETFILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(sourceFileVersionId);

				queryPos.add(targetFileVersionId);

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

	private static final String _FINDER_COLUMN_F_S_T_FILEENTRYID_2 =
		"syncDLFileVersionDiff.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_T_SOURCEFILEVERSIONID_2 =
		"syncDLFileVersionDiff.sourceFileVersionId = ? AND ";

	private static final String _FINDER_COLUMN_F_S_T_TARGETFILEVERSIONID_2 =
		"syncDLFileVersionDiff.targetFileVersionId = ?";

	public SyncDLFileVersionDiffPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SyncDLFileVersionDiff.class);

		setModelImplClass(SyncDLFileVersionDiffImpl.class);
		setModelPKClass(long.class);

		setTable(SyncDLFileVersionDiffTable.INSTANCE);
	}

	/**
	 * Caches the sync dl file version diff in the entity cache if it is enabled.
	 *
	 * @param syncDLFileVersionDiff the sync dl file version diff
	 */
	@Override
	public void cacheResult(SyncDLFileVersionDiff syncDLFileVersionDiff) {
		entityCache.putResult(
			SyncDLFileVersionDiffImpl.class,
			syncDLFileVersionDiff.getPrimaryKey(), syncDLFileVersionDiff);

		finderCache.putResult(
			_finderPathFetchByF_S_T,
			new Object[] {
				syncDLFileVersionDiff.getFileEntryId(),
				syncDLFileVersionDiff.getSourceFileVersionId(),
				syncDLFileVersionDiff.getTargetFileVersionId()
			},
			syncDLFileVersionDiff);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sync dl file version diffs in the entity cache if it is enabled.
	 *
	 * @param syncDLFileVersionDiffs the sync dl file version diffs
	 */
	@Override
	public void cacheResult(
		List<SyncDLFileVersionDiff> syncDLFileVersionDiffs) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (syncDLFileVersionDiffs.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SyncDLFileVersionDiff syncDLFileVersionDiff :
				syncDLFileVersionDiffs) {

			if (entityCache.getResult(
					SyncDLFileVersionDiffImpl.class,
					syncDLFileVersionDiff.getPrimaryKey()) == null) {

				cacheResult(syncDLFileVersionDiff);
			}
		}
	}

	/**
	 * Clears the cache for all sync dl file version diffs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SyncDLFileVersionDiffImpl.class);

		finderCache.clearCache(SyncDLFileVersionDiffImpl.class);
	}

	/**
	 * Clears the cache for the sync dl file version diff.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SyncDLFileVersionDiff syncDLFileVersionDiff) {
		entityCache.removeResult(
			SyncDLFileVersionDiffImpl.class, syncDLFileVersionDiff);
	}

	@Override
	public void clearCache(List<SyncDLFileVersionDiff> syncDLFileVersionDiffs) {
		for (SyncDLFileVersionDiff syncDLFileVersionDiff :
				syncDLFileVersionDiffs) {

			entityCache.removeResult(
				SyncDLFileVersionDiffImpl.class, syncDLFileVersionDiff);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SyncDLFileVersionDiffImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				SyncDLFileVersionDiffImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SyncDLFileVersionDiffModelImpl syncDLFileVersionDiffModelImpl) {

		Object[] args = new Object[] {
			syncDLFileVersionDiffModelImpl.getFileEntryId(),
			syncDLFileVersionDiffModelImpl.getSourceFileVersionId(),
			syncDLFileVersionDiffModelImpl.getTargetFileVersionId()
		};

		finderCache.putResult(_finderPathCountByF_S_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByF_S_T, args, syncDLFileVersionDiffModelImpl);
	}

	/**
	 * Creates a new sync dl file version diff with the primary key. Does not add the sync dl file version diff to the database.
	 *
	 * @param syncDLFileVersionDiffId the primary key for the new sync dl file version diff
	 * @return the new sync dl file version diff
	 */
	@Override
	public SyncDLFileVersionDiff create(long syncDLFileVersionDiffId) {
		SyncDLFileVersionDiff syncDLFileVersionDiff =
			new SyncDLFileVersionDiffImpl();

		syncDLFileVersionDiff.setNew(true);
		syncDLFileVersionDiff.setPrimaryKey(syncDLFileVersionDiffId);

		syncDLFileVersionDiff.setCompanyId(CompanyThreadLocal.getCompanyId());

		return syncDLFileVersionDiff;
	}

	/**
	 * Removes the sync dl file version diff with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff remove(long syncDLFileVersionDiffId)
		throws NoSuchDLFileVersionDiffException {

		return remove((Serializable)syncDLFileVersionDiffId);
	}

	/**
	 * Removes the sync dl file version diff with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sync dl file version diff
	 * @return the sync dl file version diff that was removed
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff remove(Serializable primaryKey)
		throws NoSuchDLFileVersionDiffException {

		Session session = null;

		try {
			session = openSession();

			SyncDLFileVersionDiff syncDLFileVersionDiff =
				(SyncDLFileVersionDiff)session.get(
					SyncDLFileVersionDiffImpl.class, primaryKey);

			if (syncDLFileVersionDiff == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDLFileVersionDiffException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(syncDLFileVersionDiff);
		}
		catch (NoSuchDLFileVersionDiffException noSuchEntityException) {
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
	protected SyncDLFileVersionDiff removeImpl(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(syncDLFileVersionDiff)) {
				syncDLFileVersionDiff = (SyncDLFileVersionDiff)session.get(
					SyncDLFileVersionDiffImpl.class,
					syncDLFileVersionDiff.getPrimaryKeyObj());
			}

			if (syncDLFileVersionDiff != null) {
				session.delete(syncDLFileVersionDiff);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (syncDLFileVersionDiff != null) {
			clearCache(syncDLFileVersionDiff);
		}

		return syncDLFileVersionDiff;
	}

	@Override
	public SyncDLFileVersionDiff updateImpl(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {

		boolean isNew = syncDLFileVersionDiff.isNew();

		if (!(syncDLFileVersionDiff instanceof
				SyncDLFileVersionDiffModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(syncDLFileVersionDiff.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					syncDLFileVersionDiff);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in syncDLFileVersionDiff proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SyncDLFileVersionDiff implementation " +
					syncDLFileVersionDiff.getClass());
		}

		SyncDLFileVersionDiffModelImpl syncDLFileVersionDiffModelImpl =
			(SyncDLFileVersionDiffModelImpl)syncDLFileVersionDiff;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(syncDLFileVersionDiff);
			}
			else {
				syncDLFileVersionDiff = (SyncDLFileVersionDiff)session.merge(
					syncDLFileVersionDiff);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SyncDLFileVersionDiffImpl.class, syncDLFileVersionDiffModelImpl,
			false, true);

		cacheUniqueFindersCache(syncDLFileVersionDiffModelImpl);

		if (isNew) {
			syncDLFileVersionDiff.setNew(false);
		}

		syncDLFileVersionDiff.resetOriginalValues();

		return syncDLFileVersionDiff;
	}

	/**
	 * Returns the sync dl file version diff with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sync dl file version diff
	 * @return the sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDLFileVersionDiffException {

		SyncDLFileVersionDiff syncDLFileVersionDiff = fetchByPrimaryKey(
			primaryKey);

		if (syncDLFileVersionDiff == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDLFileVersionDiffException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return syncDLFileVersionDiff;
	}

	/**
	 * Returns the sync dl file version diff with the primary key or throws a <code>NoSuchDLFileVersionDiffException</code> if it could not be found.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff
	 * @throws NoSuchDLFileVersionDiffException if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff findByPrimaryKey(long syncDLFileVersionDiffId)
		throws NoSuchDLFileVersionDiffException {

		return findByPrimaryKey((Serializable)syncDLFileVersionDiffId);
	}

	/**
	 * Returns the sync dl file version diff with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync dl file version diff
	 * @return the sync dl file version diff, or <code>null</code> if a sync dl file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff fetchByPrimaryKey(
		long syncDLFileVersionDiffId) {

		return fetchByPrimaryKey((Serializable)syncDLFileVersionDiffId);
	}

	/**
	 * Returns all the sync dl file version diffs.
	 *
	 * @return the sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sync dl file version diffs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @return the range of sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findAll(
		int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sync dl file version diffs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SyncDLFileVersionDiffModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync dl file version diffs
	 * @param end the upper bound of the range of sync dl file version diffs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sync dl file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> findAll(
		int start, int end,
		OrderByComparator<SyncDLFileVersionDiff> orderByComparator,
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

		List<SyncDLFileVersionDiff> list = null;

		if (useFinderCache) {
			list = (List<SyncDLFileVersionDiff>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SYNCDLFILEVERSIONDIFF);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SYNCDLFILEVERSIONDIFF;

				sql = sql.concat(SyncDLFileVersionDiffModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SyncDLFileVersionDiff>)QueryUtil.list(
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
	 * Removes all the sync dl file version diffs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SyncDLFileVersionDiff syncDLFileVersionDiff : findAll()) {
			remove(syncDLFileVersionDiff);
		}
	}

	/**
	 * Returns the number of sync dl file version diffs.
	 *
	 * @return the number of sync dl file version diffs
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
					_SQL_COUNT_SYNCDLFILEVERSIONDIFF);

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
		return "syncDLFileVersionDiffId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SYNCDLFILEVERSIONDIFF;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SyncDLFileVersionDiffModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sync dl file version diff persistence.
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

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileEntryId"}, true);

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			true);

		_finderPathCountByFileEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] {Long.class.getName()}, new String[] {"fileEntryId"},
			false);

		_finderPathWithPaginationFindByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"expirationDate"}, true);

		_finderPathWithPaginationCountByLtExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, false);

		_finderPathFetchByF_S_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_S_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"fileEntryId", "sourceFileVersionId", "targetFileVersionId"
			},
			true);

		_finderPathCountByF_S_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"fileEntryId", "sourceFileVersionId", "targetFileVersionId"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SyncDLFileVersionDiffImpl.class.getName());
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_SYNCDLFILEVERSIONDIFF =
		"SELECT syncDLFileVersionDiff FROM SyncDLFileVersionDiff syncDLFileVersionDiff";

	private static final String _SQL_SELECT_SYNCDLFILEVERSIONDIFF_WHERE =
		"SELECT syncDLFileVersionDiff FROM SyncDLFileVersionDiff syncDLFileVersionDiff WHERE ";

	private static final String _SQL_COUNT_SYNCDLFILEVERSIONDIFF =
		"SELECT COUNT(syncDLFileVersionDiff) FROM SyncDLFileVersionDiff syncDLFileVersionDiff";

	private static final String _SQL_COUNT_SYNCDLFILEVERSIONDIFF_WHERE =
		"SELECT COUNT(syncDLFileVersionDiff) FROM SyncDLFileVersionDiff syncDLFileVersionDiff WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"syncDLFileVersionDiff.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SyncDLFileVersionDiff exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SyncDLFileVersionDiff exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SyncDLFileVersionDiffPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SyncDLFileVersionDiffModelArgumentsResolver
		_syncDLFileVersionDiffModelArgumentsResolver;

}