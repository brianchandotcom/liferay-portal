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
import com.liferay.document.library.model.DLFileVersionPreviewTable;
import com.liferay.document.library.model.impl.DLFileVersionPreviewImpl;
import com.liferay.document.library.model.impl.DLFileVersionPreviewModelImpl;
import com.liferay.document.library.service.persistence.DLFileVersionPreviewPersistence;
import com.liferay.document.library.service.persistence.impl.constants.DLPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

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
import java.util.Set;

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
@Component(
	service = {DLFileVersionPreviewPersistence.class, BasePersistence.class}
)
public class DLFileVersionPreviewPersistenceImpl
	extends BasePersistenceImpl<DLFileVersionPreview>
	implements DLFileVersionPreviewPersistence {

	/*
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFileEntryId;
				finderArgs = new Object[] {fileEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFileEntryId;
			finderArgs = new Object[] {
				fileEntryId, start, end, orderByComparator
			};
		}

		List<DLFileVersionPreview> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchFileVersionPreviewException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchFileVersionPreviewException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

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
			sb.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
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
						dlFileVersionPreview)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersionPreview> list = query.list();

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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFileEntryId;

			finderArgs = new Object[] {fileEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFileVersionId;
				finderArgs = new Object[] {fileVersionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFileVersionId;
			finderArgs = new Object[] {
				fileVersionId, start, end, orderByComparator
			};
		}

		List<DLFileVersionPreview> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs);

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
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileVersionId);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileVersionId=");
		sb.append(fileVersionId);

		sb.append("}");

		throw new NoSuchFileVersionPreviewException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileVersionId=");
		sb.append(fileVersionId);

		sb.append("}");

		throw new NoSuchFileVersionPreviewException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

		sb.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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
			sb.append(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fileVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileVersionPreview)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileVersionPreview> list = query.list();

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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFileVersionId;

			finderArgs = new Object[] {fileVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileVersionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fileEntryId=");
			sb.append(fileEntryId);

			sb.append(", fileVersionId=");
			sb.append(fileVersionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFileVersionPreviewException(sb.toString());
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {fileEntryId, fileVersionId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByF_F, finderArgs);
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
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_F_FILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(fileVersionId);

				List<DLFileVersionPreview> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_F;

			finderArgs = new Object[] {fileEntryId, fileVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_F_F_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_F_FILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(fileVersionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("fileEntryId=");
			sb.append(fileEntryId);

			sb.append(", fileVersionId=");
			sb.append(fileVersionId);

			sb.append(", previewStatus=");
			sb.append(previewStatus);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFileVersionPreviewException(sb.toString());
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				fileEntryId, fileVersionId, previewStatus
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByF_F_P, finderArgs);
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
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			sb.append(_FINDER_COLUMN_F_F_P_PREVIEWSTATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(fileVersionId);

				queryPos.add(previewStatus);

				List<DLFileVersionPreview> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByF_F_P;

			finderArgs = new Object[] {
				fileEntryId, fileVersionId, previewStatus
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_DLFILEVERSIONPREVIEW_WHERE);

			sb.append(_FINDER_COLUMN_F_F_P_FILEENTRYID_2);

			sb.append(_FINDER_COLUMN_F_F_P_FILEVERSIONID_2);

			sb.append(_FINDER_COLUMN_F_F_P_PREVIEWSTATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				queryPos.add(fileVersionId);

				queryPos.add(previewStatus);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

		setTable(DLFileVersionPreviewTable.INSTANCE);
	}

	/**
	 * Caches the dl file version preview in the entity cache if it is enabled.
	 *
	 * @param dlFileVersionPreview the dl file version preview
	 */
	@Override
	public void cacheResult(DLFileVersionPreview dlFileVersionPreview) {
		if (dlFileVersionPreview.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			DLFileVersionPreviewImpl.class,
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
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dl file version previews in the entity cache if it is enabled.
	 *
	 * @param dlFileVersionPreviews the dl file version previews
	 */
	@Override
	public void cacheResult(List<DLFileVersionPreview> dlFileVersionPreviews) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dlFileVersionPreviews.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DLFileVersionPreview dlFileVersionPreview :
				dlFileVersionPreviews) {

			if (dlFileVersionPreview.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					DLFileVersionPreviewImpl.class,
					dlFileVersionPreview.getPrimaryKey()) == null) {

				cacheResult(dlFileVersionPreview);
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

		finderCache.clearCache(DLFileVersionPreviewImpl.class);
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
			DLFileVersionPreviewImpl.class, dlFileVersionPreview);
	}

	@Override
	public void clearCache(List<DLFileVersionPreview> dlFileVersionPreviews) {
		for (DLFileVersionPreview dlFileVersionPreview :
				dlFileVersionPreviews) {

			entityCache.removeResult(
				DLFileVersionPreviewImpl.class, dlFileVersionPreview);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DLFileVersionPreviewImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DLFileVersionPreviewImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileVersionPreviewModelImpl dlFileVersionPreviewModelImpl) {

		Object[] args = new Object[] {
			dlFileVersionPreviewModelImpl.getFileEntryId(),
			dlFileVersionPreviewModelImpl.getFileVersionId()
		};

		finderCache.putResult(_finderPathCountByF_F, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByF_F, args, dlFileVersionPreviewModelImpl);

		args = new Object[] {
			dlFileVersionPreviewModelImpl.getFileEntryId(),
			dlFileVersionPreviewModelImpl.getFileVersionId(),
			dlFileVersionPreviewModelImpl.getPreviewStatus()
		};

		finderCache.putResult(_finderPathCountByF_F_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByF_F_P, args, dlFileVersionPreviewModelImpl);
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

		dlFileVersionPreview.setCompanyId(CompanyThreadLocal.getCompanyId());

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
		catch (NoSuchFileVersionPreviewException noSuchEntityException) {
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

			if ((dlFileVersionPreview != null) &&
				ctPersistenceHelper.isRemove(dlFileVersionPreview)) {

				session.delete(dlFileVersionPreview);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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

			if (ctPersistenceHelper.isInsert(dlFileVersionPreview)) {
				if (!isNew) {
					session.evict(
						DLFileVersionPreviewImpl.class,
						dlFileVersionPreview.getPrimaryKeyObj());
				}

				session.save(dlFileVersionPreview);
			}
			else {
				dlFileVersionPreview = (DLFileVersionPreview)session.merge(
					dlFileVersionPreview);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersionPreview.getCtCollectionId() != 0) {
			if (isNew) {
				dlFileVersionPreview.setNew(false);
			}

			dlFileVersionPreview.resetOriginalValues();

			return dlFileVersionPreview;
		}

		entityCache.putResult(
			DLFileVersionPreviewImpl.class, dlFileVersionPreviewModelImpl,
			false, true);

		cacheUniqueFindersCache(dlFileVersionPreviewModelImpl);

		if (isNew) {
			dlFileVersionPreview.setNew(false);
		}

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
	 * @param primaryKey the primary key of the dl file version preview
	 * @return the dl file version preview, or <code>null</code> if a dl file version preview with the primary key could not be found
	 */
	@Override
	public DLFileVersionPreview fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(DLFileVersionPreview.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DLFileVersionPreview dlFileVersionPreview = null;

		Session session = null;

		try {
			session = openSession();

			dlFileVersionPreview = (DLFileVersionPreview)session.get(
				DLFileVersionPreviewImpl.class, primaryKey);

			if (dlFileVersionPreview != null) {
				cacheResult(dlFileVersionPreview);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return dlFileVersionPreview;
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

	@Override
	public Map<Serializable, DLFileVersionPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DLFileVersionPreview.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileVersionPreview> map =
			new HashMap<Serializable, DLFileVersionPreview>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileVersionPreview dlFileVersionPreview = fetchByPrimaryKey(
				primaryKey);

			if (dlFileVersionPreview != null) {
				map.put(primaryKey, dlFileVersionPreview);
			}

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

			for (DLFileVersionPreview dlFileVersionPreview :
					(List<DLFileVersionPreview>)query.list()) {

				map.put(
					dlFileVersionPreview.getPrimaryKeyObj(),
					dlFileVersionPreview);

				cacheResult(dlFileVersionPreview);
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

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DLFileVersionPreview> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileVersionPreview>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DLFILEVERSIONPREVIEW);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSIONPREVIEW;

				sql = sql.concat(DLFileVersionPreviewModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DLFileVersionPreview>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DLFileVersionPreview.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DLFILEVERSIONPREVIEW);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "dlFileVersionPreviewId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEVERSIONPREVIEW;
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
		return DLFileVersionPreviewModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLFileVersionPreview";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("fileEntryId");
		ctStrictColumnNames.add("fileVersionId");
		ctStrictColumnNames.add("previewStatus");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("dlFileVersionPreviewId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"fileEntryId", "fileVersionId"});

		_uniqueIndexColumnNames.add(
			new String[] {"fileEntryId", "fileVersionId", "previewStatus"});
	}

	/**
	 * Initializes the dl file version preview persistence.
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

		_finderPathWithPaginationFindByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"fileVersionId"}, true);

		_finderPathWithoutPaginationFindByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] {Long.class.getName()}, new String[] {"fileVersionId"},
			true);

		_finderPathCountByFileVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] {Long.class.getName()}, new String[] {"fileVersionId"},
			false);

		_finderPathFetchByF_F = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"fileEntryId", "fileVersionId"}, true);

		_finderPathCountByF_F = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"fileEntryId", "fileVersionId"}, false);

		_finderPathFetchByF_F_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"fileEntryId", "fileVersionId", "previewStatus"},
			true);

		_finderPathCountByF_F_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			new String[] {"fileEntryId", "fileVersionId", "previewStatus"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DLFileVersionPreviewImpl.class.getName());
	}

	@Override
	@Reference(
		target = DLPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
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

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private DLFileVersionPreviewModelArgumentsResolver
		_dlFileVersionPreviewModelArgumentsResolver;

}