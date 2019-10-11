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

import com.liferay.document.library.exception.NoSuchFileVersionPreviewException;
import com.liferay.document.library.model.DLFileVersionPreview;
import com.liferay.document.library.model.impl.DLFileVersionPreviewImpl;
import com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl;
import com.liferay.document.library.service.persistence.DLFileVersionPreviewPersistence;
import com.liferay.document.library.service.persistence.impl.constants.DLPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the dl file version preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DLFileVersionPreviewPersistence.class)
public class DLFileVersionPreviewPersistenceImpl
	extends BasePersistenceImpl<DLFileVersionPreview>
	implements DLFileVersionPreviewPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileVersionPreviewUtil</code> to access the dl file version preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileVersionPreviewImpl.class.getName();

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
	 * Returns all the dl file version previews where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file version previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @return the range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file version previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl file version previews where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator,
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

		List<DLFileVersionPreview> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersionPreview dlFileVersionPreview : list) {
					if (fileEntryId != dlFileVersionPreview.getFileEntryId()) {
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

			query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
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
	 * Returns the first dl file version preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByFileEntryId_First(
			fileEntryId, orderByComparator);

		if (dlFileVersionPreview != null) {
			return dlFileVersionPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileVersionPreviewException(msg.toString());
	}

	/**
	 * Returns the first dl file version preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		List<DLFileVersionPreview> list = findByFileEntryId(
			fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dl file version preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByFileEntryId_Last(
			long fileEntryId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByFileEntryId_Last(
			fileEntryId, orderByComparator);

		if (dlFileVersionPreview != null) {
			return dlFileVersionPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append("}");

		throw new NoSuchFileVersionPreviewException(msg.toString());
	}

	/**
	 * Returns the last dl file version preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByFileEntryId_Last(
		long fileEntryId,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersionPreview> list = findByFileEntryId(
			fileEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dl file version previews before and after the current dl file version preview in the ordered set where fileEntryId = &#63;.
	 *
	 * @param dlFileVersionPreviewId the primary key of the current dl file version preview
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview[] findByFileEntryId_PrevAndNext(
			long dlFileVersionPreviewId, long fileEntryId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = findByPrimaryKey(
			dlFileVersionPreviewId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersionPreview[] array = new DLFileVersionPreviewImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(
				session, dlFileVersionPreview, fileEntryId, orderByComparator,
				true);

			array[1] = dlFileVersionPreview;

			array[2] = getByFileEntryId_PrevAndNext(
				session, dlFileVersionPreview, fileEntryId, orderByComparator,
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

	protected DLFileVersionPreview getByFileEntryId_PrevAndNext(
		Session session, DLFileVersionPreview dlFileVersionPreview,
		long fileEntryId,
		OrderByComparator<DLFileVersionPreview> orderByComparator,
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

		query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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
			query.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersionPreview)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersionPreview> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dl file version previews where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileVersionPreview dlFileVersionPreview :
				findByFileEntryId(
					fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileVersionPreview);
		}
	}

	/**
	 * Returns the number of dl file version previews where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching dl file version previews
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = _finderPathCountByFileEntryId;

		Object[] finderArgs = new Object[] {fileEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 =
		"dlFileVersionPreview.fileEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByFileVersionId;
	private FinderPath _finderPathWithoutPaginationFindByFileVersionId;
	private FinderPath _finderPathCountByFileVersionId;

	/**
	 * Returns all the dl file version previews where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(
			fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file version previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @return the range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end) {

		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file version previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		return findByFileVersionId(
			fileVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl file version previews where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByFileVersionId;
				finderArgs = new Object[] {fileVersionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByFileVersionId;
			finderArgs = new Object[] {
				fileVersionId, start, end, orderByComparator
			};
		}

		List<DLFileVersionPreview> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersionPreview dlFileVersionPreview : list) {
					if (fileVersionId !=
							dlFileVersionPreview.getFileVersionId()) {

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

			query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
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
	 * Returns the first dl file version preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByFileVersionId_First(
			long fileVersionId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByFileVersionId_First(
			fileVersionId, orderByComparator);

		if (dlFileVersionPreview != null) {
			return dlFileVersionPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchFileVersionPreviewException(msg.toString());
	}

	/**
	 * Returns the first dl file version preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		List<DLFileVersionPreview> list = findByFileVersionId(
			fileVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dl file version preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByFileVersionId_Last(
			long fileVersionId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByFileVersionId_Last(
			fileVersionId, orderByComparator);

		if (dlFileVersionPreview != null) {
			return dlFileVersionPreview;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchFileVersionPreviewException(msg.toString());
	}

	/**
	 * Returns the last dl file version preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersionPreview> list = findByFileVersionId(
			fileVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dl file version previews before and after the current dl file version preview in the ordered set where fileVersionId = &#63;.
	 *
	 * @param dlFileVersionPreviewId the primary key of the current dl file version preview
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview[] findByFileVersionId_PrevAndNext(
			long dlFileVersionPreviewId, long fileVersionId,
			OrderByComparator<DLFileVersionPreview> orderByComparator)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = findByPrimaryKey(
			dlFileVersionPreviewId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersionPreview[] array = new DLFileVersionPreviewImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(
				session, dlFileVersionPreview, fileVersionId, orderByComparator,
				true);

			array[1] = dlFileVersionPreview;

			array[2] = getByFileVersionId_PrevAndNext(
				session, dlFileVersionPreview, fileVersionId, orderByComparator,
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

	protected DLFileVersionPreview getByFileVersionId_PrevAndNext(
		Session session, DLFileVersionPreview dlFileVersionPreview,
		long fileVersionId,
		OrderByComparator<DLFileVersionPreview> orderByComparator,
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

		query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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
			query.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersionPreview)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersionPreview> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dl file version previews where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (DLFileVersionPreview dlFileVersionPreview :
				findByFileVersionId(
					fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileVersionPreview);
		}
	}

	/**
	 * Returns the number of dl file version previews where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching dl file version previews
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		FinderPath finderPath = _finderPathCountByFileVersionId;

		Object[] finderArgs = new Object[] {fileVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

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

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 =
		"dlFileVersionPreview.fileVersionId = ?";

	private FinderPath _finderPathFetchByF_F;
	private FinderPath _finderPathCountByF_F;

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; or throws a <code>NoSuchFileVersionPreviewException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByF_F(long fileEntryId, long fileVersionId)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByF_F(
			fileEntryId, fileVersionId);

		if (dlFileVersionPreview == null) {
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

			throw new NoSuchFileVersionPreviewException(msg.toString());
		}

		return dlFileVersionPreview;
	}

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByF_F(
		long fileEntryId, long fileVersionId) {

		return fetchByF_F(fileEntryId, fileVersionId, true);
	}

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByF_F(
		long fileEntryId, long fileVersionId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {fileEntryId, fileVersionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByF_F, finderArgs, this);
		}

		if (result instanceof DLFileVersionPreview) {
			DLFileVersionPreview dlFileVersionPreview =
				(DLFileVersionPreview)result;

			if ((fileEntryId != dlFileVersionPreview.getFileEntryId()) ||
				(fileVersionId != dlFileVersionPreview.getFileVersionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

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

				List<DLFileVersionPreview> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByF_F, finderArgs, list);
					}
				}
				else {
					DLFileVersionPreview dlFileVersionPreview = list.get(0);

					result = dlFileVersionPreview;

					cacheResult(dlFileVersionPreview);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByF_F, finderArgs);
				}

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
			return (DLFileVersionPreview)result;
		}
	}

	/**
	 * Removes the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the dl file version preview that was removed
	 */
	@Override
	public DLFileVersionPreview removeByF_F(
			long fileEntryId, long fileVersionId)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = findByF_F(
			fileEntryId, fileVersionId);

		return remove(dlFileVersionPreview);
	}

	/**
	 * Returns the number of dl file version previews where fileEntryId = &#63; and fileVersionId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching dl file version previews
	 */
	@Override
	public int countByF_F(long fileEntryId, long fileVersionId) {
		FinderPath finderPath = _finderPathCountByF_F;

		Object[] finderArgs = new Object[] {fileEntryId, fileVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

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

	private static final String _FINDER_COLUMN_F_F_FILEENTRYID_2 =
		"dlFileVersionPreview.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_F_FILEVERSIONID_2 =
		"dlFileVersionPreview.fileVersionId = ?";

	private FinderPath _finderPathFetchByF_F_P;
	private FinderPath _finderPathCountByF_F_P;

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or throws a <code>NoSuchFileVersionPreviewException</code> if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewStatus the preview status
	 * @return the matching dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview findByF_F_P(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByF_F_P(
			fileEntryId, fileVersionId, previewStatus);

		if (dlFileVersionPreview == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(", previewStatus=");
			msg.append(previewStatus);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileVersionPreviewException(msg.toString());
		}

		return dlFileVersionPreview;
	}

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewStatus the preview status
	 * @return the matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByF_F_P(
		long fileEntryId, long fileVersionId, int previewStatus) {

		return fetchByF_F_P(fileEntryId, fileVersionId, previewStatus, true);
	}

	/**
	 * Returns the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewStatus the preview status
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl file version preview, or <code>null</code> if a matching dl file version preview could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByF_F_P(
		long fileEntryId, long fileVersionId, int previewStatus,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				fileEntryId, fileVersionId, previewStatus
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByF_F_P, finderArgs, this);
		}

		if (result instanceof DLFileVersionPreview) {
			DLFileVersionPreview dlFileVersionPreview =
				(DLFileVersionPreview)result;

			if ((fileEntryId != dlFileVersionPreview.getFileEntryId()) ||
				(fileVersionId != dlFileVersionPreview.getFileVersionId()) ||
				(previewStatus != dlFileVersionPreview.getPreviewStatus())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			query.append(_FINDER_COLUMN_F_F_P_PREVIEWSTATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

				qPos.add(previewStatus);

				List<DLFileVersionPreview> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByF_F_P, finderArgs, list);
					}
				}
				else {
					DLFileVersionPreview dlFileVersionPreview = list.get(0);

					result = dlFileVersionPreview;

					cacheResult(dlFileVersionPreview);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByF_F_P, finderArgs);
				}

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
			return (DLFileVersionPreview)result;
		}
	}

	/**
	 * Removes the dl file version preview where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewStatus the preview status
	 * @return the dl file version preview that was removed
	 */
	@Override
	public DLFileVersionPreview removeByF_F_P(
			long fileEntryId, long fileVersionId, int previewStatus)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = findByF_F_P(
			fileEntryId, fileVersionId, previewStatus);

		return remove(dlFileVersionPreview);
	}

	/**
	 * Returns the number of dl file version previews where fileEntryId = &#63; and fileVersionId = &#63; and previewStatus = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param fileVersionId the file version ID
	 * @param previewStatus the preview status
	 * @return the number of matching dl file version previews
	 */
	@Override
	public int countByF_F_P(
		long fileEntryId, long fileVersionId, int previewStatus) {

		FinderPath finderPath = _finderPathCountByF_F_P;

		Object[] finderArgs = new Object[] {
			fileEntryId, fileVersionId, previewStatus
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

			query.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			query.append(_FINDER_COLUMN_F_F_P_PREVIEWSTATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(fileVersionId);

				qPos.add(previewStatus);

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

	private static final String _FINDER_COLUMN_F_F_P_FILEENTRYID_2 =
		"dlFileVersionPreview.fileEntryId = ? AND ";

	private static final String _FINDER_COLUMN_F_F_P_FILEVERSIONID_2 =
		"dlFileVersionPreview.fileVersionId = ? AND ";

	private static final String _FINDER_COLUMN_F_F_P_PREVIEWSTATUS_2 =
		"dlFileVersionPreview.previewStatus = ?";

	public DLFileVersionPreviewPersistenceImpl() {
		setModelClass(DLFileVersionPreview.class);

		setModelImplClass(DLFileVersionPreviewImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the dl file version preview in the entity cache if it is enabled.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 */
	@Override
	public void cacheResult(DLFileVersionPreview dlFileVersionPreview) {
		entityCache.putResult(
			entityCacheEnabled, DLFileVersionPreviewImpl.class,
			dlFileVersionPreview.getPrimaryKey(), dlFileVersionPreview);

		finderCache.putResult(
			_finderPathFetchByF_F,
			new Object[] {
				dlFileVersionPreview.getFileEntryId(),
				dlFileVersionPreview.getFileVersionId()
			},
			dlFileVersionPreview);

		finderCache.putResult(
			_finderPathFetchByF_F_P,
			new Object[] {
				dlFileVersionPreview.getFileEntryId(),
				dlFileVersionPreview.getFileVersionId(),
				dlFileVersionPreview.getPreviewStatus()
			},
			dlFileVersionPreview);

		dlFileVersionPreview.resetOriginalValues();
	}

	/**
	 * Caches the dl file version previews in the entity cache if it is enabled.
	 *
	 * @param dlFileVersionPreviews the dl file version previews
	 */
	@Override
	public void cacheResult(List<DLFileVersionPreview> dlFileVersionPreviews) {
		for (DLFileVersionPreview dlFileVersionPreview :
				dlFileVersionPreviews) {

			if (entityCache.getResult(
					entityCacheEnabled, DLFileVersionPreviewImpl.class,
					dlFileVersionPreview.getPrimaryKey()) == null) {

				cacheResult(dlFileVersionPreview);
			}
			else {
				dlFileVersionPreview.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all dl file version previews.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileVersionPreviewImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the dl file version preview.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileVersionPreview dlFileVersionPreview) {
		entityCache.removeResult(
			entityCacheEnabled, DLFileVersionPreviewImpl.class,
			dlFileVersionPreview.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DLFileVersionPreviewModelImpl)dlFileVersionPreview, true);
	}

	@Override
	public void clearCache(List<DLFileVersionPreview> dlFileVersionPreviews) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileVersionPreview dlFileVersionPreview :
				dlFileVersionPreviews) {

			entityCache.removeResult(
				entityCacheEnabled, DLFileVersionPreviewImpl.class,
				dlFileVersionPreview.getPrimaryKey());

			clearUniqueFindersCache(
				(DLFileVersionPreviewModelImpl)dlFileVersionPreview, true);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileVersionPreviewModelImpl dlFileVersionPreviewModelImpl) {

		Object[] args = new Object[] {
			dlFileVersionPreviewModelImpl.getFileEntryId(),
			dlFileVersionPreviewModelImpl.getFileVersionId()
		};

		finderCache.putResult(
			_finderPathCountByF_F, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByF_F, args, dlFileVersionPreviewModelImpl, false);

		args = new Object[] {
			dlFileVersionPreviewModelImpl.getFileEntryId(),
			dlFileVersionPreviewModelImpl.getFileVersionId(),
			dlFileVersionPreviewModelImpl.getPreviewStatus()
		};

		finderCache.putResult(
			_finderPathCountByF_F_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByF_F_P, args, dlFileVersionPreviewModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		DLFileVersionPreviewModelImpl dlFileVersionPreviewModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileVersionPreviewModelImpl.getFileEntryId(),
				dlFileVersionPreviewModelImpl.getFileVersionId()
			};

			finderCache.removeResult(_finderPathCountByF_F, args);
			finderCache.removeResult(_finderPathFetchByF_F, args);
		}

		if ((dlFileVersionPreviewModelImpl.getColumnBitmask() &
			 _finderPathFetchByF_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileVersionPreviewModelImpl.getOriginalFileEntryId(),
				dlFileVersionPreviewModelImpl.getOriginalFileVersionId()
			};

			finderCache.removeResult(_finderPathCountByF_F, args);
			finderCache.removeResult(_finderPathFetchByF_F, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileVersionPreviewModelImpl.getFileEntryId(),
				dlFileVersionPreviewModelImpl.getFileVersionId(),
				dlFileVersionPreviewModelImpl.getPreviewStatus()
			};

			finderCache.removeResult(_finderPathCountByF_F_P, args);
			finderCache.removeResult(_finderPathFetchByF_F_P, args);
		}

		if ((dlFileVersionPreviewModelImpl.getColumnBitmask() &
			 _finderPathFetchByF_F_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileVersionPreviewModelImpl.getOriginalFileEntryId(),
				dlFileVersionPreviewModelImpl.getOriginalFileVersionId(),
				dlFileVersionPreviewModelImpl.getOriginalPreviewStatus()
			};

			finderCache.removeResult(_finderPathCountByF_F_P, args);
			finderCache.removeResult(_finderPathFetchByF_F_P, args);
		}
	}

	/**
	 * Creates a new dl file version preview with the primary key. Does not add the dl file version preview to the database.
	 *
	 * @param dlFileVersionPreviewId the primary key for the new dl file version preview
	 * @return the new dl file version preview
	 */
	@Override
	public DLFileVersionPreview create(long dlFileVersionPreviewId) {
		DLFileVersionPreview dlFileVersionPreview =
			new DLFileVersionPreviewImpl();

		dlFileVersionPreview.setNew(true);
		dlFileVersionPreview.setPrimaryKey(dlFileVersionPreviewId);

		return dlFileVersionPreview;
	}

	/**
	 * Removes the dl file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview that was removed
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview remove(long dlFileVersionPreviewId)
		throws NoSuchFileVersionPreviewException {

		return remove((Serializable)dlFileVersionPreviewId);
	}

	/**
	 * Removes the dl file version preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dl file version preview
	 * @return the dl file version preview that was removed
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview remove(Serializable primaryKey)
		throws NoSuchFileVersionPreviewException {

		Session session = null;

		try {
			session = openSession();

			DLFileVersionPreview dlFileVersionPreview =
				(DLFileVersionPreview)session.get(
					DLFileVersionPreviewImpl.class, primaryKey);

			if (dlFileVersionPreview == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileVersionPreviewException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlFileVersionPreview);
		}
		catch (NoSuchFileVersionPreviewException nsee) {
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
	protected DLFileVersionPreview removeImpl(
		DLFileVersionPreview dlFileVersionPreview) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileVersionPreview)) {
				dlFileVersionPreview = (DLFileVersionPreview)session.get(
					DLFileVersionPreviewImpl.class,
					dlFileVersionPreview.getPrimaryKeyObj());
			}

			if (dlFileVersionPreview != null) {
				session.delete(dlFileVersionPreview);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersionPreview != null) {
			clearCache(dlFileVersionPreview);
		}

		return dlFileVersionPreview;
	}

	@Override
	public DLFileVersionPreview updateImpl(
		DLFileVersionPreview dlFileVersionPreview) {

		boolean isNew = dlFileVersionPreview.isNew();

		if (!(dlFileVersionPreview instanceof DLFileVersionPreviewModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileVersionPreview.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlFileVersionPreview);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileVersionPreview proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileVersionPreview implementation " +
					dlFileVersionPreview.getClass());
		}

		DLFileVersionPreviewModelImpl dlFileVersionPreviewModelImpl =
			(DLFileVersionPreviewModelImpl)dlFileVersionPreview;

		Session session = null;

		try {
			session = openSession();

			if (dlFileVersionPreview.isNew()) {
				session.save(dlFileVersionPreview);

				dlFileVersionPreview.setNew(false);
			}
			else {
				dlFileVersionPreview = (DLFileVersionPreview)session.merge(
					dlFileVersionPreview);
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
				dlFileVersionPreviewModelImpl.getFileEntryId()
			};

			finderCache.removeResult(_finderPathCountByFileEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFileEntryId, args);

			args = new Object[] {
				dlFileVersionPreviewModelImpl.getFileVersionId()
			};

			finderCache.removeResult(_finderPathCountByFileVersionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFileVersionId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dlFileVersionPreviewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFileEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionPreviewModelImpl.getOriginalFileEntryId()
				};

				finderCache.removeResult(_finderPathCountByFileEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);

				args = new Object[] {
					dlFileVersionPreviewModelImpl.getFileEntryId()
				};

				finderCache.removeResult(_finderPathCountByFileEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);
			}

			if ((dlFileVersionPreviewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFileVersionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileVersionPreviewModelImpl.getOriginalFileVersionId()
				};

				finderCache.removeResult(_finderPathCountByFileVersionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFileVersionId, args);

				args = new Object[] {
					dlFileVersionPreviewModelImpl.getFileVersionId()
				};

				finderCache.removeResult(_finderPathCountByFileVersionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFileVersionId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DLFileVersionPreviewImpl.class,
			dlFileVersionPreview.getPrimaryKey(), dlFileVersionPreview, false);

		clearUniqueFindersCache(dlFileVersionPreviewModelImpl, false);
		cacheUniqueFindersCache(dlFileVersionPreviewModelImpl);

		dlFileVersionPreview.resetOriginalValues();

		return dlFileVersionPreview;
	}

	/**
	 * Returns the dl file version preview with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dl file version preview
	 * @return the dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileVersionPreviewException {

		DLFileVersionPreview dlFileVersionPreview = fetchByPrimaryKey(
			primaryKey);

		if (dlFileVersionPreview == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileVersionPreviewException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlFileVersionPreview;
	}

	/**
	 * Returns the dl file version preview with the primary key or throws a <code>NoSuchFileVersionPreviewException</code> if it could not be found.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview
	 * @throws NoSuchFileVersionPreviewException if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview findByPrimaryKey(long dlFileVersionPreviewId)
		throws NoSuchFileVersionPreviewException {

		return findByPrimaryKey((Serializable)dlFileVersionPreviewId);
	}

	/**
	 * Returns the dl file version preview with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlFileVersionPreviewId the primary key of the dl file version preview
	 * @return the dl file version preview, or <code>null</code> if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByPrimaryKey(long dlFileVersionPreviewId) {
		return fetchByPrimaryKey((Serializable)dlFileVersionPreviewId);
	}

	/**
	 * Returns all the dl file version previews.
	 *
	 * @return the dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dl file version previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @return the range of dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dl file version previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findAll(
		int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dl file version previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileVersionPreviewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl file version previews
	 * @param end the upper bound of the range of dl file version previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl file version previews
	 */
	@Override
	public List<DLFileVersionPreview> findAll(
		int start, int end,
		OrderByComparator<DLFileVersionPreview> orderByComparator,
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

		List<DLFileVersionPreview> list = null;

		if (useFinderCache) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLFILEVERSIONPREVIEW);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSIONPREVIEW;

				sql = sql.concat(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
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
	 * Removes all the dl file version previews from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileVersionPreview dlFileVersionPreview : findAll()) {
			remove(dlFileVersionPreview);
		}
	}

	/**
	 * Returns the number of dl file version previews.
	 *
	 * @return the number of dl file version previews
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEVERSIONPREVIEW);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "dlFileVersionPreviewId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEVERSIONPREVIEW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLFileVersionPreviewModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dl file version preview persistence.
	 */
	@Activate
	public void activate() {
		DLFileVersionPreviewModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DLFileVersionPreviewModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] {Long.class.getName()},
			DLFileVersionPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK);

		_finderPathCountByFileEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFileVersionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFileVersionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] {Long.class.getName()},
			DLFileVersionPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK);

		_finderPathCountByFileVersionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] {Long.class.getName()});

		_finderPathFetchByF_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			DLFileVersionPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK);

		_finderPathCountByF_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByF_F_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DLFileVersionPreviewImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFileVersionPreviewModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionPreviewModelImpl.FILEVERSIONID_COLUMN_BITMASK |
			DLFileVersionPreviewModelImpl.PREVIEWSTATUS_COLUMN_BITMASK);

		_finderPathCountByF_F_P = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DLFileVersionPreviewImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.document.library.model.DLFileVersionPreview"),
			true);
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DLFILEVERSIONPREVIEW =
		"SELECT dlFileVersionPreview FROM DLFileVersionPreview dlFileVersionPreview";

	private static final String _SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE =
		"SELECT dlFileVersionPreview FROM DLFileVersionPreview dlFileVersionPreview WHERE ";

	private static final String _SQL_COUNT_DLFILEVERSIONPREVIEW =
		"SELECT COUNT(dlFileVersionPreview) FROM DLFileVersionPreview dlFileVersionPreview";

	private static final String _SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE =
		"SELECT COUNT(dlFileVersionPreview) FROM DLFileVersionPreview dlFileVersionPreview WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"dlFileVersionPreview.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLFileVersionPreview exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileVersionPreview exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionPreviewPersistenceImpl.class);

	static {
		try {
			Class.forName(DLPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}