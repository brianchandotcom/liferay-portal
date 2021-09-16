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

package com.liferay.redirect.service.persistence.impl;

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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.redirect.exception.NoSuchNotFoundEntryException;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.model.RedirectNotFoundEntryTable;
import com.liferay.redirect.model.impl.RedirectNotFoundEntryImpl;
import com.liferay.redirect.model.impl.RedirectNotFoundEntryModelImpl;
import com.liferay.redirect.service.persistence.RedirectNotFoundEntryPersistence;
import com.liferay.redirect.service.persistence.impl.constants.RedirectPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
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
 * The persistence implementation for the redirect not found entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {RedirectNotFoundEntryPersistence.class, BasePersistence.class}
)
public class RedirectNotFoundEntryPersistenceImpl
	extends BasePersistenceImpl<RedirectNotFoundEntry>
	implements RedirectNotFoundEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RedirectNotFoundEntryUtil</code> to access the redirect not found entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RedirectNotFoundEntryImpl.class.getName();

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
	 * Returns all the redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of matching redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator,
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

		List<RedirectNotFoundEntry> list = null;

		if (useFinderCache) {
			list = (List<RedirectNotFoundEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RedirectNotFoundEntry redirectNotFoundEntry : list) {
					if (groupId != redirectNotFoundEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_REDIRECTNOTFOUNDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RedirectNotFoundEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<RedirectNotFoundEntry>)QueryUtil.list(
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
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry findByGroupId_First(
			long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (redirectNotFoundEntry != null) {
			return redirectNotFoundEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotFoundEntryException(sb.toString());
	}

	/**
	 * Returns the first redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		List<RedirectNotFoundEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (redirectNotFoundEntry != null) {
			return redirectNotFoundEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchNotFoundEntryException(sb.toString());
	}

	/**
	 * Returns the last redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<RedirectNotFoundEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the redirect not found entries before and after the current redirect not found entry in the ordered set where groupId = &#63;.
	 *
	 * @param redirectNotFoundEntryId the primary key of the current redirect not found entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry[] findByGroupId_PrevAndNext(
			long redirectNotFoundEntryId, long groupId,
			OrderByComparator<RedirectNotFoundEntry> orderByComparator)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = findByPrimaryKey(
			redirectNotFoundEntryId);

		Session session = null;

		try {
			session = openSession();

			RedirectNotFoundEntry[] array = new RedirectNotFoundEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, redirectNotFoundEntry, groupId, orderByComparator,
				true);

			array[1] = redirectNotFoundEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, redirectNotFoundEntry, groupId, orderByComparator,
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

	protected RedirectNotFoundEntry getByGroupId_PrevAndNext(
		Session session, RedirectNotFoundEntry redirectNotFoundEntry,
		long groupId,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_REDIRECTNOTFOUNDENTRY_WHERE);

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
			sb.append(RedirectNotFoundEntryModelImpl.ORDER_BY_JPQL);
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
						redirectNotFoundEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RedirectNotFoundEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the redirect not found entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (RedirectNotFoundEntry redirectNotFoundEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(redirectNotFoundEntry);
		}
	}

	/**
	 * Returns the number of redirect not found entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching redirect not found entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REDIRECTNOTFOUNDENTRY_WHERE);

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
		"redirectNotFoundEntry.groupId = ?";

	private FinderPath _finderPathFetchByG_U;
	private FinderPath _finderPathCountByG_U;

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry findByG_U(long groupId, String url)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = fetchByG_U(groupId, url);

		if (redirectNotFoundEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", url=");
			sb.append(url);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotFoundEntryException(sb.toString());
		}

		return redirectNotFoundEntry;
	}

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry fetchByG_U(long groupId, String url) {
		return fetchByG_U(groupId, url, true);
	}

	/**
	 * Returns the redirect not found entry where groupId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching redirect not found entry, or <code>null</code> if a matching redirect not found entry could not be found
	 */
	@Override
	public RedirectNotFoundEntry fetchByG_U(
		long groupId, String url, boolean useFinderCache) {

		url = Objects.toString(url, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, url};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByG_U, finderArgs);
		}

		if (result instanceof RedirectNotFoundEntry) {
			RedirectNotFoundEntry redirectNotFoundEntry =
				(RedirectNotFoundEntry)result;

			if ((groupId != redirectNotFoundEntry.getGroupId()) ||
				!Objects.equals(url, redirectNotFoundEntry.getUrl())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REDIRECTNOTFOUNDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_G_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUrl) {
					queryPos.add(url);
				}

				List<RedirectNotFoundEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_U, finderArgs, list);
					}
				}
				else {
					RedirectNotFoundEntry redirectNotFoundEntry = list.get(0);

					result = redirectNotFoundEntry;

					cacheResult(redirectNotFoundEntry);
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
			return (RedirectNotFoundEntry)result;
		}
	}

	/**
	 * Removes the redirect not found entry where groupId = &#63; and url = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the redirect not found entry that was removed
	 */
	@Override
	public RedirectNotFoundEntry removeByG_U(long groupId, String url)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = findByG_U(groupId, url);

		return remove(redirectNotFoundEntry);
	}

	/**
	 * Returns the number of redirect not found entries where groupId = &#63; and url = &#63;.
	 *
	 * @param groupId the group ID
	 * @param url the url
	 * @return the number of matching redirect not found entries
	 */
	@Override
	public int countByG_U(long groupId, String url) {
		url = Objects.toString(url, "");

		FinderPath finderPath = _finderPathCountByG_U;

		Object[] finderArgs = new Object[] {groupId, url};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REDIRECTNOTFOUNDENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_U_URL_3);
			}
			else {
				bindUrl = true;

				sb.append(_FINDER_COLUMN_G_U_URL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindUrl) {
					queryPos.add(url);
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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"redirectNotFoundEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_URL_2 =
		"redirectNotFoundEntry.url = ?";

	private static final String _FINDER_COLUMN_G_U_URL_3 =
		"(redirectNotFoundEntry.url IS NULL OR redirectNotFoundEntry.url = '')";

	public RedirectNotFoundEntryPersistenceImpl() {
		setModelClass(RedirectNotFoundEntry.class);

		setModelImplClass(RedirectNotFoundEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RedirectNotFoundEntryTable.INSTANCE);
	}

	/**
	 * Caches the redirect not found entry in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntry the redirect not found entry
	 */
	@Override
	public void cacheResult(RedirectNotFoundEntry redirectNotFoundEntry) {
		entityCache.putResult(
			RedirectNotFoundEntryImpl.class,
			redirectNotFoundEntry.getPrimaryKey(), redirectNotFoundEntry);

		finderCache.putResult(
			_finderPathFetchByG_U,
			new Object[] {
				redirectNotFoundEntry.getGroupId(),
				redirectNotFoundEntry.getUrl()
			},
			redirectNotFoundEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the redirect not found entries in the entity cache if it is enabled.
	 *
	 * @param redirectNotFoundEntries the redirect not found entries
	 */
	@Override
	public void cacheResult(
		List<RedirectNotFoundEntry> redirectNotFoundEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (redirectNotFoundEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RedirectNotFoundEntry redirectNotFoundEntry :
				redirectNotFoundEntries) {

			if (entityCache.getResult(
					RedirectNotFoundEntryImpl.class,
					redirectNotFoundEntry.getPrimaryKey()) == null) {

				cacheResult(redirectNotFoundEntry);
			}
		}
	}

	/**
	 * Clears the cache for all redirect not found entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RedirectNotFoundEntryImpl.class);

		finderCache.clearCache(RedirectNotFoundEntryImpl.class);
	}

	/**
	 * Clears the cache for the redirect not found entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RedirectNotFoundEntry redirectNotFoundEntry) {
		entityCache.removeResult(
			RedirectNotFoundEntryImpl.class, redirectNotFoundEntry);
	}

	@Override
	public void clearCache(
		List<RedirectNotFoundEntry> redirectNotFoundEntries) {

		for (RedirectNotFoundEntry redirectNotFoundEntry :
				redirectNotFoundEntries) {

			entityCache.removeResult(
				RedirectNotFoundEntryImpl.class, redirectNotFoundEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RedirectNotFoundEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RedirectNotFoundEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RedirectNotFoundEntryModelImpl redirectNotFoundEntryModelImpl) {

		Object[] args = new Object[] {
			redirectNotFoundEntryModelImpl.getGroupId(),
			redirectNotFoundEntryModelImpl.getUrl()
		};

		finderCache.putResult(_finderPathCountByG_U, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_U, args, redirectNotFoundEntryModelImpl);
	}

	/**
	 * Creates a new redirect not found entry with the primary key. Does not add the redirect not found entry to the database.
	 *
	 * @param redirectNotFoundEntryId the primary key for the new redirect not found entry
	 * @return the new redirect not found entry
	 */
	@Override
	public RedirectNotFoundEntry create(long redirectNotFoundEntryId) {
		RedirectNotFoundEntry redirectNotFoundEntry =
			new RedirectNotFoundEntryImpl();

		redirectNotFoundEntry.setNew(true);
		redirectNotFoundEntry.setPrimaryKey(redirectNotFoundEntryId);

		redirectNotFoundEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return redirectNotFoundEntry;
	}

	/**
	 * Removes the redirect not found entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry that was removed
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry remove(long redirectNotFoundEntryId)
		throws NoSuchNotFoundEntryException {

		return remove((Serializable)redirectNotFoundEntryId);
	}

	/**
	 * Removes the redirect not found entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the redirect not found entry
	 * @return the redirect not found entry that was removed
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry remove(Serializable primaryKey)
		throws NoSuchNotFoundEntryException {

		Session session = null;

		try {
			session = openSession();

			RedirectNotFoundEntry redirectNotFoundEntry =
				(RedirectNotFoundEntry)session.get(
					RedirectNotFoundEntryImpl.class, primaryKey);

			if (redirectNotFoundEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotFoundEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(redirectNotFoundEntry);
		}
		catch (NoSuchNotFoundEntryException noSuchEntityException) {
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
	protected RedirectNotFoundEntry removeImpl(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(redirectNotFoundEntry)) {
				redirectNotFoundEntry = (RedirectNotFoundEntry)session.get(
					RedirectNotFoundEntryImpl.class,
					redirectNotFoundEntry.getPrimaryKeyObj());
			}

			if (redirectNotFoundEntry != null) {
				session.delete(redirectNotFoundEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (redirectNotFoundEntry != null) {
			clearCache(redirectNotFoundEntry);
		}

		return redirectNotFoundEntry;
	}

	@Override
	public RedirectNotFoundEntry updateImpl(
		RedirectNotFoundEntry redirectNotFoundEntry) {

		boolean isNew = redirectNotFoundEntry.isNew();

		if (!(redirectNotFoundEntry instanceof
				RedirectNotFoundEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(redirectNotFoundEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					redirectNotFoundEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in redirectNotFoundEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RedirectNotFoundEntry implementation " +
					redirectNotFoundEntry.getClass());
		}

		RedirectNotFoundEntryModelImpl redirectNotFoundEntryModelImpl =
			(RedirectNotFoundEntryModelImpl)redirectNotFoundEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (redirectNotFoundEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				redirectNotFoundEntry.setCreateDate(date);
			}
			else {
				redirectNotFoundEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!redirectNotFoundEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				redirectNotFoundEntry.setModifiedDate(date);
			}
			else {
				redirectNotFoundEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = redirectNotFoundEntry.getCompanyId();

			long groupId = redirectNotFoundEntry.getGroupId();

			long redirectNotFoundEntryId = 0;

			if (!isNew) {
				redirectNotFoundEntryId = redirectNotFoundEntry.getPrimaryKey();
			}

			try {
				redirectNotFoundEntry.setUrl(
					SanitizerUtil.sanitize(
						companyId, groupId, userId,
						RedirectNotFoundEntry.class.getName(),
						redirectNotFoundEntryId, ContentTypes.TEXT_PLAIN,
						Sanitizer.MODE_ALL, redirectNotFoundEntry.getUrl(),
						null));
			}
			catch (SanitizerException sanitizerException) {
				throw new SystemException(sanitizerException);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(redirectNotFoundEntry);
			}
			else {
				redirectNotFoundEntry = (RedirectNotFoundEntry)session.merge(
					redirectNotFoundEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RedirectNotFoundEntryImpl.class, redirectNotFoundEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(redirectNotFoundEntryModelImpl);

		if (isNew) {
			redirectNotFoundEntry.setNew(false);
		}

		redirectNotFoundEntry.resetOriginalValues();

		return redirectNotFoundEntry;
	}

	/**
	 * Returns the redirect not found entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the redirect not found entry
	 * @return the redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNotFoundEntryException {

		RedirectNotFoundEntry redirectNotFoundEntry = fetchByPrimaryKey(
			primaryKey);

		if (redirectNotFoundEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotFoundEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return redirectNotFoundEntry;
	}

	/**
	 * Returns the redirect not found entry with the primary key or throws a <code>NoSuchNotFoundEntryException</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry
	 * @throws NoSuchNotFoundEntryException if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry findByPrimaryKey(long redirectNotFoundEntryId)
		throws NoSuchNotFoundEntryException {

		return findByPrimaryKey((Serializable)redirectNotFoundEntryId);
	}

	/**
	 * Returns the redirect not found entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param redirectNotFoundEntryId the primary key of the redirect not found entry
	 * @return the redirect not found entry, or <code>null</code> if a redirect not found entry with the primary key could not be found
	 */
	@Override
	public RedirectNotFoundEntry fetchByPrimaryKey(
		long redirectNotFoundEntryId) {

		return fetchByPrimaryKey((Serializable)redirectNotFoundEntryId);
	}

	/**
	 * Returns all the redirect not found entries.
	 *
	 * @return the redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @return the range of redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findAll(
		int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the redirect not found entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RedirectNotFoundEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redirect not found entries
	 * @param end the upper bound of the range of redirect not found entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of redirect not found entries
	 */
	@Override
	public List<RedirectNotFoundEntry> findAll(
		int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator,
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

		List<RedirectNotFoundEntry> list = null;

		if (useFinderCache) {
			list = (List<RedirectNotFoundEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REDIRECTNOTFOUNDENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REDIRECTNOTFOUNDENTRY;

				sql = sql.concat(RedirectNotFoundEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RedirectNotFoundEntry>)QueryUtil.list(
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
	 * Removes all the redirect not found entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RedirectNotFoundEntry redirectNotFoundEntry : findAll()) {
			remove(redirectNotFoundEntry);
		}
	}

	/**
	 * Returns the number of redirect not found entries.
	 *
	 * @return the number of redirect not found entries
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
					_SQL_COUNT_REDIRECTNOTFOUNDENTRY);

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
		return "redirectNotFoundEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REDIRECTNOTFOUNDENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RedirectNotFoundEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the redirect not found entry persistence.
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

		_finderPathFetchByG_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "url"}, true);

		_finderPathCountByG_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "url"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RedirectNotFoundEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = RedirectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RedirectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RedirectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REDIRECTNOTFOUNDENTRY =
		"SELECT redirectNotFoundEntry FROM RedirectNotFoundEntry redirectNotFoundEntry";

	private static final String _SQL_SELECT_REDIRECTNOTFOUNDENTRY_WHERE =
		"SELECT redirectNotFoundEntry FROM RedirectNotFoundEntry redirectNotFoundEntry WHERE ";

	private static final String _SQL_COUNT_REDIRECTNOTFOUNDENTRY =
		"SELECT COUNT(redirectNotFoundEntry) FROM RedirectNotFoundEntry redirectNotFoundEntry";

	private static final String _SQL_COUNT_REDIRECTNOTFOUNDENTRY_WHERE =
		"SELECT COUNT(redirectNotFoundEntry) FROM RedirectNotFoundEntry redirectNotFoundEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"redirectNotFoundEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RedirectNotFoundEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RedirectNotFoundEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectNotFoundEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RedirectNotFoundEntryModelArgumentsResolver
		_redirectNotFoundEntryModelArgumentsResolver;

}