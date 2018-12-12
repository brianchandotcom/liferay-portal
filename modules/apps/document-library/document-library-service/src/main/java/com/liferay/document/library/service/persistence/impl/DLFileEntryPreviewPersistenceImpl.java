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

package com.liferay.document.library.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.exception.NoSuchFileEntryPreviewException;
import com.liferay.document.library.model.DLFileEntryPreview;
import com.liferay.document.library.model.impl.DLFileEntryPreviewImpl;
import com.liferay.document.library.model.impl.DLFileEntryPreviewModelImpl;
import com.liferay.document.library.service.persistence.DLFileEntryPreviewPersistence;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The persistence implementation for the dl file entry preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreviewPersistence
 * @see com.liferay.document.library.service.persistence.DLFileEntryPreviewUtil
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewPersistenceImpl extends BasePersistenceImpl<DLFileEntryPreview>
	implements DLFileEntryPreviewPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryPreviewUtil} to access the dl file entry preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryPreviewImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] { Long.class.getName() },
			DLFileEntryPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the dl file entry previews where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file entry previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @return the range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end) {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return findByFileEntryId(fileEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId, start, end, orderByComparator };
		}

		List<DLFileEntryPreview> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryPreview>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryPreview dlFileEntryPreview : list) {
					if ((fileEntryId != dlFileEntryPreview.getFileEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (!pagination) {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileEntryPreview != null) {
			return dlFileEntryPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileEntryPreviewException(msg.toString());
	}

	/**
	 * Returns the first dl file entry preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		List<DLFileEntryPreview> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileEntryPreview != null) {
			return dlFileEntryPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileEntryPreviewException(msg.toString());
	}

	/**
	 * Returns the last dl file entry preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryPreview> list = findByFileEntryId(fileEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryPreviewId the primary key of the current dl file entry preview
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview[] findByFileEntryId_PrevAndNext(
		long fileEntryPreviewId, long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = findByPrimaryKey(fileEntryPreviewId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryPreview[] array = new DLFileEntryPreviewImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryPreview, fileEntryId, orderByComparator, true);

			array[1] = dlFileEntryPreview;

			array[2] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryPreview, fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryPreview getByFileEntryId_PrevAndNext(Session session,
		DLFileEntryPreview dlFileEntryPreview, long fileEntryId,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileEntryPreviewModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					dlFileEntryPreview)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryPreview> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dl file entry previews where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileEntryPreview dlFileEntryPreview : findByFileEntryId(
				fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntryPreview);
		}
	}

	/**
	 * Returns the number of dl file entry previews where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl file entry previews
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYID;

		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileEntryPreview.fileEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			DLFileEntryPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the dl file entry previews where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file entry previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @return the range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileVersionId(long fileVersionId,
		int start, int end) {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return findByFileVersionId(fileVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] { fileVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] {
					fileVersionId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntryPreview> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryPreview>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryPreview dlFileEntryPreview : list) {
					if ((fileVersionId != dlFileEntryPreview.getFileVersionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				if (!pagination) {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByFileVersionId_First(long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (dlFileEntryPreview != null) {
			return dlFileEntryPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchFileEntryPreviewException(msg.toString());
	}

	/**
	 * Returns the first dl file entry preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByFileVersionId_First(long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		List<DLFileEntryPreview> list = findByFileVersionId(fileVersionId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByFileVersionId_Last(long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (dlFileEntryPreview != null) {
			return dlFileEntryPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchFileEntryPreviewException(msg.toString());
	}

	/**
	 * Returns the last dl file entry preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByFileVersionId_Last(long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryPreview> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dl file entry previews before and after the current dl file entry preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileEntryPreviewId the primary key of the current dl file entry preview
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview[] findByFileVersionId_PrevAndNext(
		long fileEntryPreviewId, long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = findByPrimaryKey(fileEntryPreviewId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryPreview[] array = new DLFileEntryPreviewImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryPreview, fileVersionId, orderByComparator, true);

			array[1] = dlFileEntryPreview;

			array[2] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryPreview, fileVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryPreview getByFileVersionId_PrevAndNext(
		Session session, DLFileEntryPreview dlFileEntryPreview,
		long fileVersionId,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(DLFileEntryPreviewModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					dlFileEntryPreview)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryPreview> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dl file entry previews where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (DLFileEntryPreview dlFileEntryPreview : findByFileVersionId(
				fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntryPreview);
		}
	}

	/**
	 * Returns the number of dl file entry previews where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching dl file entry previews
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEVERSIONID;

		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "dlFileEntryPreview.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_F_F = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileEntryPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_F = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByF_F(fileEntryId,
				fileVersionId);

		if (dlFileEntryPreview == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryPreviewException(msg.toString());
		}

		return dlFileEntryPreview;
	}

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByF_F(long fileEntryId, long fileVersionId) {
		return fetchByF_F(fileEntryId, fileVersionId, true);
	}

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByF_F(long fileEntryId, long fileVersionId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { fileEntryId, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_F_F,
					finderArgs, this);
		}

		if (result instanceof DLFileEntryPreview) {
			DLFileEntryPreview dlFileEntryPreview = (DLFileEntryPreview)result;

			if ((fileEntryId != dlFileEntryPreview.getFileEntryId()) ||
					(fileVersionId != dlFileEntryPreview.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

				List<DLFileEntryPreview> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_F_F, finderArgs,
						list);
				}
				else {
					DLFileEntryPreview dlFileEntryPreview = list.get(0);

					result = dlFileEntryPreview;

					cacheResult(dlFileEntryPreview);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F, finderArgs);

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
			return (DLFileEntryPreview)result;
		}
	}

	/**
	 * Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the dl file entry preview that was removed
	 */
	@Override
	public DLFileEntryPreview removeByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = findByF_F(fileEntryId,
				fileVersionId);

		return remove(dlFileEntryPreview);
	}

	/**
	 * Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching dl file entry previews
	 */
	@Override
	public int countByF_F(long fileEntryId, long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_F;

		Object[] finderArgs = new Object[] { fileEntryId, fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_F_F_FILEENTRYID_2 = "dlFileEntryPreview.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_F_FILEVERSIONID_2 = "dlFileEntryPreview.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_F_F_P = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFileEntryPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileEntryPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK |
			DLFileEntryPreviewModelImpl.PREVIEWTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_F_P = new FinderPath(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewType the preview type
	 * @return the matching dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview findByF_F_P(long fileEntryId, long fileVersionId,
		int previewType) throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByF_F_P(fileEntryId,
				fileVersionId, previewType);

		if (dlFileEntryPreview == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(", previewType=");
			msg.append(previewType);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryPreviewException(msg.toString());
		}

		return dlFileEntryPreview;
	}

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewType the preview type
	 * @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType) {
		return fetchByF_F_P(fileEntryId, fileVersionId, previewType, true);
	}

	/**
	 * Returns the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewType the preview type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching dl file entry preview, or <code>null</code> if a matching dl file entry preview could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByF_F_P(long fileEntryId,
		long fileVersionId, int previewType, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				fileEntryId, fileVersionId, previewType
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_F_F_P,
					finderArgs, this);
		}

		if (result instanceof DLFileEntryPreview) {
			DLFileEntryPreview dlFileEntryPreview = (DLFileEntryPreview)result;

			if ((fileEntryId != dlFileEntryPreview.getFileEntryId()) ||
					(fileVersionId != dlFileEntryPreview.getFileVersionId()) ||
					(previewType != dlFileEntryPreview.getPreviewType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			query.append(_FINDER_COLUMN_F_F_P_PREVIEWTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

				qPos.add(previewType);

				List<DLFileEntryPreview> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_F_F_P,
						finderArgs, list);
				}
				else {
					DLFileEntryPreview dlFileEntryPreview = list.get(0);

					result = dlFileEntryPreview;

					cacheResult(dlFileEntryPreview);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F_P, finderArgs);

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
			return (DLFileEntryPreview)result;
		}
	}

	/**
	 * Removes the dl file entry preview where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewType the preview type
	 * @return the dl file entry preview that was removed
	 */
	@Override
	public DLFileEntryPreview removeByF_F_P(long fileEntryId,
		long fileVersionId, int previewType)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = findByF_F_P(fileEntryId,
				fileVersionId, previewType);

		return remove(dlFileEntryPreview);
	}

	/**
	 * Returns the number of dl file entry previews where fileEntryId = &#63; and fileVersionId = &#63; and previewType = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewType the preview type
	 * @return the number of matching dl file entry previews
	 */
	@Override
	public int countByF_F_P(long fileEntryId, long fileVersionId,
		int previewType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_F_P;

		Object[] finderArgs = new Object[] {
				fileEntryId, fileVersionId, previewType
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRYPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			query.append(_FINDER_COLUMN_F_F_P_PREVIEWTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

				qPos.add(previewType);

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

	private static final String _FINDER_COLUMN_F_F_P_FILEENTRYID_2 = "dlFileEntryPreview.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_F_P_FILEVERSIONID_2 = "dlFileEntryPreview.fileVersionId = ? AND ";
	private static final String _FINDER_COLUMN_F_F_P_PREVIEWTYPE_2 = "dlFileEntryPreview.previewType = ?";

	public DLFileEntryPreviewPersistenceImpl() {
		setModelClass(DLFileEntryPreview.class);

		setModelImplClass(DLFileEntryPreviewImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the dl file entry preview in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryPreview the dl file entry preview
	 */
	@Override
	public void cacheResult(DLFileEntryPreview dlFileEntryPreview) {
		entityCache.putResult(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class, dlFileEntryPreview.getPrimaryKey(),
			dlFileEntryPreview);

		finderCache.putResult(FINDER_PATH_FETCH_BY_F_F,
			new Object[] {
				dlFileEntryPreview.getFileEntryId(),
				dlFileEntryPreview.getFileVersionId()
			}, dlFileEntryPreview);

		finderCache.putResult(FINDER_PATH_FETCH_BY_F_F_P,
			new Object[] {
				dlFileEntryPreview.getFileEntryId(),
				dlFileEntryPreview.getFileVersionId(),
				dlFileEntryPreview.getPreviewType()
			}, dlFileEntryPreview);

		dlFileEntryPreview.resetOriginalValues();
	}

	/**
	 * Caches the dl file entry previews in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryPreviews the dl file entry previews
	 */
	@Override
	public void cacheResult(List<DLFileEntryPreview> dlFileEntryPreviews) {
		for (DLFileEntryPreview dlFileEntryPreview : dlFileEntryPreviews) {
			if (entityCache.getResult(
						DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryPreviewImpl.class,
						dlFileEntryPreview.getPrimaryKey()) == null) {
				cacheResult(dlFileEntryPreview);
			}
			else {
				dlFileEntryPreview.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all dl file entry previews.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileEntryPreviewImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the dl file entry preview.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryPreview dlFileEntryPreview) {
		entityCache.removeResult(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class, dlFileEntryPreview.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileEntryPreviewModelImpl)dlFileEntryPreview,
			true);
	}

	@Override
	public void clearCache(List<DLFileEntryPreview> dlFileEntryPreviews) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntryPreview dlFileEntryPreview : dlFileEntryPreviews) {
			entityCache.removeResult(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryPreviewImpl.class, dlFileEntryPreview.getPrimaryKey());

			clearUniqueFindersCache((DLFileEntryPreviewModelImpl)dlFileEntryPreview,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileEntryPreviewModelImpl dlFileEntryPreviewModelImpl) {
		Object[] args = new Object[] {
				dlFileEntryPreviewModelImpl.getFileEntryId(),
				dlFileEntryPreviewModelImpl.getFileVersionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_F_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_F_F, args,
			dlFileEntryPreviewModelImpl, false);

		args = new Object[] {
				dlFileEntryPreviewModelImpl.getFileEntryId(),
				dlFileEntryPreviewModelImpl.getFileVersionId(),
				dlFileEntryPreviewModelImpl.getPreviewType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_F_F_P, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_F_F_P, args,
			dlFileEntryPreviewModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFileEntryPreviewModelImpl dlFileEntryPreviewModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryPreviewModelImpl.getFileEntryId(),
					dlFileEntryPreviewModelImpl.getFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F, args);
		}

		if ((dlFileEntryPreviewModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_F_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryPreviewModelImpl.getOriginalFileEntryId(),
					dlFileEntryPreviewModelImpl.getOriginalFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryPreviewModelImpl.getFileEntryId(),
					dlFileEntryPreviewModelImpl.getFileVersionId(),
					dlFileEntryPreviewModelImpl.getPreviewType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F_P, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F_P, args);
		}

		if ((dlFileEntryPreviewModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_F_F_P.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryPreviewModelImpl.getOriginalFileEntryId(),
					dlFileEntryPreviewModelImpl.getOriginalFileVersionId(),
					dlFileEntryPreviewModelImpl.getOriginalPreviewType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_F_P, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_F_P, args);
		}
	}

	/**
	 * Creates a new dl file entry preview with the primary key. Does not add the dl file entry preview to the database.
	 *
	 * @param fileEntryPreviewId the primary key for the new dl file entry preview
	 * @return the new dl file entry preview
	 */
	@Override
	public DLFileEntryPreview create(long fileEntryPreviewId) {
		DLFileEntryPreview dlFileEntryPreview = new DLFileEntryPreviewImpl();

		dlFileEntryPreview.setNew(true);
		dlFileEntryPreview.setPrimaryKey(fileEntryPreviewId);

		return dlFileEntryPreview;
	}

	/**
	 * Removes the dl file entry preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryPreviewId the primary key of the dl file entry preview
	 * @return the dl file entry preview that was removed
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview remove(long fileEntryPreviewId)
		throws NoSuchFileEntryPreviewException {
		return remove((Serializable)fileEntryPreviewId);
	}

	/**
	 * Removes the dl file entry preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dl file entry preview
	 * @return the dl file entry preview that was removed
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview remove(Serializable primaryKey)
		throws NoSuchFileEntryPreviewException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntryPreview dlFileEntryPreview = (DLFileEntryPreview)session.get(DLFileEntryPreviewImpl.class,
					primaryKey);

			if (dlFileEntryPreview == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryPreviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileEntryPreview);
		}
		catch (NoSuchFileEntryPreviewException nsee) {
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
	protected DLFileEntryPreview removeImpl(
		DLFileEntryPreview dlFileEntryPreview) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntryPreview)) {
				dlFileEntryPreview = (DLFileEntryPreview)session.get(DLFileEntryPreviewImpl.class,
						dlFileEntryPreview.getPrimaryKeyObj());
			}

			if (dlFileEntryPreview != null) {
				session.delete(dlFileEntryPreview);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryPreview != null) {
			clearCache(dlFileEntryPreview);
		}

		return dlFileEntryPreview;
	}

	@Override
	public DLFileEntryPreview updateImpl(DLFileEntryPreview dlFileEntryPreview) {
		boolean isNew = dlFileEntryPreview.isNew();

		if (!(dlFileEntryPreview instanceof DLFileEntryPreviewModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileEntryPreview.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dlFileEntryPreview);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileEntryPreview proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileEntryPreview implementation " +
				dlFileEntryPreview.getClass());
		}

		DLFileEntryPreviewModelImpl dlFileEntryPreviewModelImpl = (DLFileEntryPreviewModelImpl)dlFileEntryPreview;

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntryPreview.isNew()) {
				session.save(dlFileEntryPreview);

				dlFileEntryPreview.setNew(false);
			}
			else {
				dlFileEntryPreview = (DLFileEntryPreview)session.merge(dlFileEntryPreview);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DLFileEntryPreviewModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					dlFileEntryPreviewModelImpl.getFileEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
				args);

			args = new Object[] { dlFileEntryPreviewModelImpl.getFileVersionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((dlFileEntryPreviewModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryPreviewModelImpl.getOriginalFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] { dlFileEntryPreviewModelImpl.getFileEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileEntryPreviewModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryPreviewModelImpl.getOriginalFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] {
						dlFileEntryPreviewModelImpl.getFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}
		}

		entityCache.putResult(DLFileEntryPreviewModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryPreviewImpl.class, dlFileEntryPreview.getPrimaryKey(),
			dlFileEntryPreview, false);

		clearUniqueFindersCache(dlFileEntryPreviewModelImpl, false);
		cacheUniqueFindersCache(dlFileEntryPreviewModelImpl);

		dlFileEntryPreview.resetOriginalValues();

		return dlFileEntryPreview;
	}

	/**
	 * Returns the dl file entry preview with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl file entry preview
	 * @return the dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileEntryPreviewException {
		DLFileEntryPreview dlFileEntryPreview = fetchByPrimaryKey(primaryKey);

		if (dlFileEntryPreview == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryPreviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileEntryPreview;
	}

	/**
	 * Returns the dl file entry preview with the primary key or throws a {@link NoSuchFileEntryPreviewException} if it could not be found.
	 *
	 * @param fileEntryPreviewId the primary key of the dl file entry preview
	 * @return the dl file entry preview
	 * @throws NoSuchFileEntryPreviewException if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview findByPrimaryKey(long fileEntryPreviewId)
		throws NoSuchFileEntryPreviewException {
		return findByPrimaryKey((Serializable)fileEntryPreviewId);
	}

	/**
	 * Returns the dl file entry preview with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryPreviewId the primary key of the dl file entry preview
	 * @return the dl file entry preview, or <code>null</code> if a dl file entry preview with the primary key could not be found
	 */
	@Override
	public DLFileEntryPreview fetchByPrimaryKey(long fileEntryPreviewId) {
		return fetchByPrimaryKey((Serializable)fileEntryPreviewId);
	}

	/**
	 * Returns all the dl file entry previews.
	 *
	 * @return the dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file entry previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @return the range of dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findAll(int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl file entry previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file entry previews
	 * @param end the upper bound of the range of dl file entry previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of dl file entry previews
	 */
	@Override
	public List<DLFileEntryPreview> findAll(int start, int end,
		OrderByComparator<DLFileEntryPreview> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DLFileEntryPreview> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryPreview>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLFILEENTRYPREVIEW);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYPREVIEW;

				if (pagination) {
					sql = sql.concat(DLFileEntryPreviewModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryPreview>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the dl file entry previews from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileEntryPreview dlFileEntryPreview : findAll()) {
			remove(dlFileEntryPreview);
		}
	}

	/**
	 * Returns the number of dl file entry previews.
	 *
	 * @return the number of dl file entry previews
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRYPREVIEW);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

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
		return "fileEntryPreviewId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEENTRYPREVIEW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLFileEntryPreviewModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dl file entry preview persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileEntryPreviewImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DLFILEENTRYPREVIEW = "SELECT dlFileEntryPreview FROM DLFileEntryPreview dlFileEntryPreview";
	private static final String _SQL_SELECT_DLFILEENTRYPREVIEW_WHERE = "SELECT dlFileEntryPreview FROM DLFileEntryPreview dlFileEntryPreview WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRYPREVIEW = "SELECT COUNT(dlFileEntryPreview) FROM DLFileEntryPreview dlFileEntryPreview";
	private static final String _SQL_COUNT_DLFILEENTRYPREVIEW_WHERE = "SELECT COUNT(dlFileEntryPreview) FROM DLFileEntryPreview dlFileEntryPreview WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryPreview.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntryPreview exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntryPreview exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileEntryPreviewPersistenceImpl.class);
}