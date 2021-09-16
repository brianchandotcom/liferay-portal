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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchCommentException;
import com.liferay.change.tracking.model.CTComment;
import com.liferay.change.tracking.model.CTCommentTable;
import com.liferay.change.tracking.model.impl.CTCommentImpl;
import com.liferay.change.tracking.model.impl.CTCommentModelImpl;
import com.liferay.change.tracking.service.persistence.CTCommentPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
 * The persistence implementation for the ct comment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTCommentPersistence.class, BasePersistence.class})
public class CTCommentPersistenceImpl
	extends BasePersistenceImpl<CTComment> implements CTCommentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTCommentUtil</code> to access the ct comment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTCommentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;

	/**
	 * Returns all the ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct comments
	 */
	@Override
	public List<CTComment> findByCtCollectionId(long ctCollectionId) {
		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTComment> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct comments where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTComment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTComment> list = null;

		if (useFinderCache) {
			list = (List<CTComment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTComment ctComment : list) {
					if (ctCollectionId != ctComment.getCtCollectionId()) {
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

			sb.append(_SQL_SELECT_CTCOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTCommentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTComment>)QueryUtil.list(
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
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	@Override
	public CTComment findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctComment != null) {
			return ctComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchCommentException(sb.toString());
	}

	/**
	 * Returns the first ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	@Override
	public CTComment fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTComment> orderByComparator) {

		List<CTComment> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	@Override
	public CTComment findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctComment != null) {
			return ctComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchCommentException(sb.toString());
	}

	/**
	 * Returns the last ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	@Override
	public CTComment fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTComment> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTComment> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment[] findByCtCollectionId_PrevAndNext(
			long ctCommentId, long ctCollectionId,
			OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = findByPrimaryKey(ctCommentId);

		Session session = null;

		try {
			session = openSession();

			CTComment[] array = new CTCommentImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctComment, ctCollectionId, orderByComparator, true);

			array[1] = ctComment;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctComment, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTComment getByCtCollectionId_PrevAndNext(
		Session session, CTComment ctComment, long ctCollectionId,
		OrderByComparator<CTComment> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTCOMMENT_WHERE);

		sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

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
			sb.append(CTCommentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctComment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTComment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct comments where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTComment ctComment :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctComment);
		}
	}

	/**
	 * Returns the number of ct comments where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct comments
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTCOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctComment.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByCtEntryId;
	private FinderPath _finderPathWithoutPaginationFindByCtEntryId;
	private FinderPath _finderPathCountByCtEntryId;

	/**
	 * Returns all the ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the matching ct comments
	 */
	@Override
	public List<CTComment> findByCtEntryId(long ctEntryId) {
		return findByCtEntryId(
			ctEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtEntryId(long ctEntryId, int start, int end) {
		return findByCtEntryId(ctEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		OrderByComparator<CTComment> orderByComparator) {

		return findByCtEntryId(ctEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct comments where ctEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param ctEntryId the ct entry ID
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct comments
	 */
	@Override
	public List<CTComment> findByCtEntryId(
		long ctEntryId, int start, int end,
		OrderByComparator<CTComment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtEntryId;
				finderArgs = new Object[] {ctEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtEntryId;
			finderArgs = new Object[] {
				ctEntryId, start, end, orderByComparator
			};
		}

		List<CTComment> list = null;

		if (useFinderCache) {
			list = (List<CTComment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTComment ctComment : list) {
					if (ctEntryId != ctComment.getCtEntryId()) {
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

			sb.append(_SQL_SELECT_CTCOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_CTENTRYID_CTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTCommentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctEntryId);

				list = (List<CTComment>)QueryUtil.list(
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
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	@Override
	public CTComment findByCtEntryId_First(
			long ctEntryId, OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = fetchByCtEntryId_First(
			ctEntryId, orderByComparator);

		if (ctComment != null) {
			return ctComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctEntryId=");
		sb.append(ctEntryId);

		sb.append("}");

		throw new NoSuchCommentException(sb.toString());
	}

	/**
	 * Returns the first ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	@Override
	public CTComment fetchByCtEntryId_First(
		long ctEntryId, OrderByComparator<CTComment> orderByComparator) {

		List<CTComment> list = findByCtEntryId(
			ctEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment
	 * @throws NoSuchCommentException if a matching ct comment could not be found
	 */
	@Override
	public CTComment findByCtEntryId_Last(
			long ctEntryId, OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = fetchByCtEntryId_Last(
			ctEntryId, orderByComparator);

		if (ctComment != null) {
			return ctComment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctEntryId=");
		sb.append(ctEntryId);

		sb.append("}");

		throw new NoSuchCommentException(sb.toString());
	}

	/**
	 * Returns the last ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct comment, or <code>null</code> if a matching ct comment could not be found
	 */
	@Override
	public CTComment fetchByCtEntryId_Last(
		long ctEntryId, OrderByComparator<CTComment> orderByComparator) {

		int count = countByCtEntryId(ctEntryId);

		if (count == 0) {
			return null;
		}

		List<CTComment> list = findByCtEntryId(
			ctEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct comments before and after the current ct comment in the ordered set where ctEntryId = &#63;.
	 *
	 * @param ctCommentId the primary key of the current ct comment
	 * @param ctEntryId the ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment[] findByCtEntryId_PrevAndNext(
			long ctCommentId, long ctEntryId,
			OrderByComparator<CTComment> orderByComparator)
		throws NoSuchCommentException {

		CTComment ctComment = findByPrimaryKey(ctCommentId);

		Session session = null;

		try {
			session = openSession();

			CTComment[] array = new CTCommentImpl[3];

			array[0] = getByCtEntryId_PrevAndNext(
				session, ctComment, ctEntryId, orderByComparator, true);

			array[1] = ctComment;

			array[2] = getByCtEntryId_PrevAndNext(
				session, ctComment, ctEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTComment getByCtEntryId_PrevAndNext(
		Session session, CTComment ctComment, long ctEntryId,
		OrderByComparator<CTComment> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTCOMMENT_WHERE);

		sb.append(_FINDER_COLUMN_CTENTRYID_CTENTRYID_2);

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
			sb.append(CTCommentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctComment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTComment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct comments where ctEntryId = &#63; from the database.
	 *
	 * @param ctEntryId the ct entry ID
	 */
	@Override
	public void removeByCtEntryId(long ctEntryId) {
		for (CTComment ctComment :
				findByCtEntryId(
					ctEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctComment);
		}
	}

	/**
	 * Returns the number of ct comments where ctEntryId = &#63;.
	 *
	 * @param ctEntryId the ct entry ID
	 * @return the number of matching ct comments
	 */
	@Override
	public int countByCtEntryId(long ctEntryId) {
		FinderPath finderPath = _finderPathCountByCtEntryId;

		Object[] finderArgs = new Object[] {ctEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTCOMMENT_WHERE);

			sb.append(_FINDER_COLUMN_CTENTRYID_CTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctEntryId);

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

	private static final String _FINDER_COLUMN_CTENTRYID_CTENTRYID_2 =
		"ctComment.ctEntryId = ?";

	public CTCommentPersistenceImpl() {
		setModelClass(CTComment.class);

		setModelImplClass(CTCommentImpl.class);
		setModelPKClass(long.class);

		setTable(CTCommentTable.INSTANCE);
	}

	/**
	 * Caches the ct comment in the entity cache if it is enabled.
	 *
	 * @param ctComment the ct comment
	 */
	@Override
	public void cacheResult(CTComment ctComment) {
		entityCache.putResult(
			CTCommentImpl.class, ctComment.getPrimaryKey(), ctComment);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct comments in the entity cache if it is enabled.
	 *
	 * @param ctComments the ct comments
	 */
	@Override
	public void cacheResult(List<CTComment> ctComments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctComments.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTComment ctComment : ctComments) {
			if (entityCache.getResult(
					CTCommentImpl.class, ctComment.getPrimaryKey()) == null) {

				cacheResult(ctComment);
			}
		}
	}

	/**
	 * Clears the cache for all ct comments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTCommentImpl.class);

		finderCache.clearCache(CTCommentImpl.class);
	}

	/**
	 * Clears the cache for the ct comment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTComment ctComment) {
		entityCache.removeResult(CTCommentImpl.class, ctComment);
	}

	@Override
	public void clearCache(List<CTComment> ctComments) {
		for (CTComment ctComment : ctComments) {
			entityCache.removeResult(CTCommentImpl.class, ctComment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTCommentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTCommentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct comment with the primary key. Does not add the ct comment to the database.
	 *
	 * @param ctCommentId the primary key for the new ct comment
	 * @return the new ct comment
	 */
	@Override
	public CTComment create(long ctCommentId) {
		CTComment ctComment = new CTCommentImpl();

		ctComment.setNew(true);
		ctComment.setPrimaryKey(ctCommentId);

		ctComment.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctComment;
	}

	/**
	 * Removes the ct comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment that was removed
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment remove(long ctCommentId) throws NoSuchCommentException {
		return remove((Serializable)ctCommentId);
	}

	/**
	 * Removes the ct comment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct comment
	 * @return the ct comment that was removed
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment remove(Serializable primaryKey)
		throws NoSuchCommentException {

		Session session = null;

		try {
			session = openSession();

			CTComment ctComment = (CTComment)session.get(
				CTCommentImpl.class, primaryKey);

			if (ctComment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCommentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctComment);
		}
		catch (NoSuchCommentException noSuchEntityException) {
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
	protected CTComment removeImpl(CTComment ctComment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctComment)) {
				ctComment = (CTComment)session.get(
					CTCommentImpl.class, ctComment.getPrimaryKeyObj());
			}

			if (ctComment != null) {
				session.delete(ctComment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctComment != null) {
			clearCache(ctComment);
		}

		return ctComment;
	}

	@Override
	public CTComment updateImpl(CTComment ctComment) {
		boolean isNew = ctComment.isNew();

		if (!(ctComment instanceof CTCommentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctComment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctComment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctComment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTComment implementation " +
					ctComment.getClass());
		}

		CTCommentModelImpl ctCommentModelImpl = (CTCommentModelImpl)ctComment;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (ctComment.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctComment.setCreateDate(date);
			}
			else {
				ctComment.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!ctCommentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctComment.setModifiedDate(date);
			}
			else {
				ctComment.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctComment);
			}
			else {
				ctComment = (CTComment)session.merge(ctComment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTCommentImpl.class, ctCommentModelImpl, false, true);

		if (isNew) {
			ctComment.setNew(false);
		}

		ctComment.resetOriginalValues();

		return ctComment;
	}

	/**
	 * Returns the ct comment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct comment
	 * @return the ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCommentException {

		CTComment ctComment = fetchByPrimaryKey(primaryKey);

		if (ctComment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCommentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctComment;
	}

	/**
	 * Returns the ct comment with the primary key or throws a <code>NoSuchCommentException</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment
	 * @throws NoSuchCommentException if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment findByPrimaryKey(long ctCommentId)
		throws NoSuchCommentException {

		return findByPrimaryKey((Serializable)ctCommentId);
	}

	/**
	 * Returns the ct comment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCommentId the primary key of the ct comment
	 * @return the ct comment, or <code>null</code> if a ct comment with the primary key could not be found
	 */
	@Override
	public CTComment fetchByPrimaryKey(long ctCommentId) {
		return fetchByPrimaryKey((Serializable)ctCommentId);
	}

	/**
	 * Returns all the ct comments.
	 *
	 * @return the ct comments
	 */
	@Override
	public List<CTComment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @return the range of ct comments
	 */
	@Override
	public List<CTComment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct comments
	 */
	@Override
	public List<CTComment> findAll(
		int start, int end, OrderByComparator<CTComment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct comments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCommentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct comments
	 * @param end the upper bound of the range of ct comments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct comments
	 */
	@Override
	public List<CTComment> findAll(
		int start, int end, OrderByComparator<CTComment> orderByComparator,
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

		List<CTComment> list = null;

		if (useFinderCache) {
			list = (List<CTComment>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTCOMMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTCOMMENT;

				sql = sql.concat(CTCommentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTComment>)QueryUtil.list(
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
	 * Removes all the ct comments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTComment ctComment : findAll()) {
			remove(ctComment);
		}
	}

	/**
	 * Returns the number of ct comments.
	 *
	 * @return the number of ct comments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTCOMMENT);

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
		return "ctCommentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTCOMMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTCommentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct comment persistence.
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

		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);

		_finderPathWithPaginationFindByCtEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctEntryId"}, true);

		_finderPathWithoutPaginationFindByCtEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtEntryId",
			new String[] {Long.class.getName()}, new String[] {"ctEntryId"},
			true);

		_finderPathCountByCtEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtEntryId",
			new String[] {Long.class.getName()}, new String[] {"ctEntryId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTCommentImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTCOMMENT =
		"SELECT ctComment FROM CTComment ctComment";

	private static final String _SQL_SELECT_CTCOMMENT_WHERE =
		"SELECT ctComment FROM CTComment ctComment WHERE ";

	private static final String _SQL_COUNT_CTCOMMENT =
		"SELECT COUNT(ctComment) FROM CTComment ctComment";

	private static final String _SQL_COUNT_CTCOMMENT_WHERE =
		"SELECT COUNT(ctComment) FROM CTComment ctComment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctComment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTComment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTComment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTCommentPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTCommentModelArgumentsResolver _ctCommentModelArgumentsResolver;

}