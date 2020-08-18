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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryMetadataException;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryMetadataTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;

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
 * The persistence implementation for the document library file entry metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryMetadataPersistenceImpl
	extends BasePersistenceImpl<DLFileEntryMetadata>
	implements DLFileEntryMetadataPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileEntryMetadataUtil</code> to access the document library file entry metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileEntryMetadataImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the document library file entry metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DLFileEntryMetadata> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (!uuid.equals(dlFileEntryMetadata.getUuid())) {
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

			sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<DLFileEntryMetadata>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_First(
			String uuid,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_First(
			uuid, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_First(
		String uuid, OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		List<DLFileEntryMetadata> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_Last(
			String uuid,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_Last(
			uuid, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_Last(
		String uuid, OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata[] findByUuid_PrevAndNext(
			long fileEntryMetadataId, String uuid,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		uuid = Objects.toString(uuid, "");

		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(
			fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, dlFileEntryMetadata, uuid, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByUuid_PrevAndNext(
				session, dlFileEntryMetadata, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByUuid_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata, String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileEntryMetadata)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryMetadata> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry metadatas where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileEntryMetadata dlFileEntryMetadata :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"dlFileEntryMetadata.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(dlFileEntryMetadata.uuid IS NULL OR dlFileEntryMetadata.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<DLFileEntryMetadata> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (!uuid.equals(dlFileEntryMetadata.getUuid()) ||
						(companyId != dlFileEntryMetadata.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		List<DLFileEntryMetadata> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata[] findByUuid_C_PrevAndNext(
			long fileEntryMetadataId, String uuid, long companyId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		uuid = Objects.toString(uuid, "");

		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(
			fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, dlFileEntryMetadata, uuid, companyId,
				orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByUuid_C_PrevAndNext(
				session, dlFileEntryMetadata, uuid, companyId,
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

	protected DLFileEntryMetadata getByUuid_C_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata, String uuid,
		long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileEntryMetadata)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryMetadata> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry metadatas where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileEntryMetadata dlFileEntryMetadata :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"dlFileEntryMetadata.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(dlFileEntryMetadata.uuid IS NULL OR dlFileEntryMetadata.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"dlFileEntryMetadata.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByFileEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFileEntryId;
	private FinderPath _finderPathCountByFileEntryId;

	/**
	 * Returns all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(
			fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end) {

		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		return findByFileEntryId(
			fileEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(
		long fileEntryId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

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

		List<DLFileEntryMetadata> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (fileEntryId != dlFileEntryMetadata.getFileEntryId()) {
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

			sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileEntryId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileEntryId_First(
			long fileEntryId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_First(
			fileEntryId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileEntryId_First(
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		List<DLFileEntryMetadata> list = findByFileEntryId(
			fileEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileEntryId_Last(
			long fileEntryId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_Last(
			fileEntryId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileEntryId=");
		sb.append(fileEntryId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileEntryId_Last(
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByFileEntryId(
			fileEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
			long fileEntryMetadataId, long fileEntryId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(
			fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(
				session, dlFileEntryMetadata, fileEntryId, orderByComparator,
				true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileEntryId_PrevAndNext(
				session, dlFileEntryMetadata, fileEntryId, orderByComparator,
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

	protected DLFileEntryMetadata getByFileEntryId_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata,
		long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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
			sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
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
						dlFileEntryMetadata)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryMetadata> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileEntryMetadata dlFileEntryMetadata :
				findByFileEntryId(
					fileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFileEntryId;

			finderArgs = new Object[] {fileEntryId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"dlFileEntryMetadata.fileEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByFileVersionId;
	private FinderPath _finderPathWithoutPaginationFindByFileVersionId;
	private FinderPath _finderPathCountByFileVersionId;

	/**
	 * Returns all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(
			fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end) {

		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		return findByFileVersionId(
			fileVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

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

		List<DLFileEntryMetadata> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (fileVersionId !=
							dlFileEntryMetadata.getFileVersionId()) {

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

			sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fileVersionId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileVersionId_First(
			long fileVersionId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_First(
			fileVersionId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileVersionId=");
		sb.append(fileVersionId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		List<DLFileEntryMetadata> list = findByFileVersionId(
			fileVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileVersionId_Last(
			long fileVersionId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_Last(
			fileVersionId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fileVersionId=");
		sb.append(fileVersionId);

		sb.append("}");

		throw new NoSuchFileEntryMetadataException(sb.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByFileVersionId(
			fileVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
			long fileEntryMetadataId, long fileVersionId,
			OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(
			fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(
				session, dlFileEntryMetadata, fileVersionId, orderByComparator,
				true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileVersionId_PrevAndNext(
				session, dlFileEntryMetadata, fileVersionId, orderByComparator,
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

	protected DLFileEntryMetadata getByFileVersionId_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata,
		long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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
			sb.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
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
						dlFileEntryMetadata)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryMetadata> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (DLFileEntryMetadata dlFileEntryMetadata :
				findByFileVersionId(
					fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFileVersionId;

			finderArgs = new Object[] {fileVersionId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"dlFileEntryMetadata.fileVersionId = ?";

	private FinderPath _finderPathFetchByD_F;
	private FinderPath _finderPathCountByD_F;

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a <code>NoSuchFileEntryMetadataException</code> if it could not be found.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByD_F(
			long DDMStructureId, long fileVersionId)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByD_F(
			DDMStructureId, fileVersionId);

		if (dlFileEntryMetadata == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("DDMStructureId=");
			sb.append(DDMStructureId);

			sb.append(", fileVersionId=");
			sb.append(fileVersionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFileEntryMetadataException(sb.toString());
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId) {

		return fetchByD_F(DDMStructureId, fileVersionId, true);
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param fileVersionId the file version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByD_F(
		long DDMStructureId, long fileVersionId, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {DDMStructureId, fileVersionId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByD_F, finderArgs, this);
		}

		if (result instanceof DLFileEntryMetadata) {
			DLFileEntryMetadata dlFileEntryMetadata =
				(DLFileEntryMetadata)result;

			if ((DDMStructureId != dlFileEntryMetadata.getDDMStructureId()) ||
				(fileVersionId != dlFileEntryMetadata.getFileVersionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			sb.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(DDMStructureId);

				queryPos.add(fileVersionId);

				List<DLFileEntryMetadata> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByD_F, finderArgs, list);
					}
				}
				else {
					DLFileEntryMetadata dlFileEntryMetadata = list.get(0);

					result = dlFileEntryMetadata;

					cacheResult(dlFileEntryMetadata);
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
			return (DLFileEntryMetadata)result;
		}
	}

	/**
	 * Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param fileVersionId the file version ID
	 * @return the document library file entry metadata that was removed
	 */
	@Override
	public DLFileEntryMetadata removeByD_F(
			long DDMStructureId, long fileVersionId)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = findByD_F(
			DDMStructureId, fileVersionId);

		return remove(dlFileEntryMetadata);
	}

	/**
	 * Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	 *
	 * @param DDMStructureId the ddm structure ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByD_F(long DDMStructureId, long fileVersionId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByD_F;

			finderArgs = new Object[] {DDMStructureId, fileVersionId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			sb.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			sb.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(DDMStructureId);

				queryPos.add(fileVersionId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_D_F_DDMSTRUCTUREID_2 =
		"dlFileEntryMetadata.DDMStructureId = ? AND ";

	private static final String _FINDER_COLUMN_D_F_FILEVERSIONID_2 =
		"dlFileEntryMetadata.fileVersionId = ?";

	public DLFileEntryMetadataPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DLFileEntryMetadata.class);

		setModelImplClass(DLFileEntryMetadataImpl.class);
		setModelPKClass(long.class);

		setTable(DLFileEntryMetadataTable.INSTANCE);
	}

	/**
	 * Caches the document library file entry metadata in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 */
	@Override
	public void cacheResult(DLFileEntryMetadata dlFileEntryMetadata) {
		if (dlFileEntryMetadata.getCtCollectionId() != 0) {
			dlFileEntryMetadata.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata);

		FinderCacheUtil.putResult(
			_finderPathFetchByD_F,
			new Object[] {
				dlFileEntryMetadata.getDDMStructureId(),
				dlFileEntryMetadata.getFileVersionId()
			},
			dlFileEntryMetadata);

		dlFileEntryMetadata.resetOriginalValues();
	}

	/**
	 * Caches the document library file entry metadatas in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryMetadatas the document library file entry metadatas
	 */
	@Override
	public void cacheResult(List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			if (dlFileEntryMetadata.getCtCollectionId() != 0) {
				dlFileEntryMetadata.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					DLFileEntryMetadataImpl.class,
					dlFileEntryMetadata.getPrimaryKey()) == null) {

				cacheResult(dlFileEntryMetadata);
			}
			else {
				dlFileEntryMetadata.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file entry metadatas.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DLFileEntryMetadataImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry metadata.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryMetadata dlFileEntryMetadata) {
		EntityCacheUtil.removeResult(
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DLFileEntryMetadataModelImpl)dlFileEntryMetadata, true);
	}

	@Override
	public void clearCache(List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			EntityCacheUtil.removeResult(
				DLFileEntryMetadataImpl.class,
				dlFileEntryMetadata.getPrimaryKey());

			clearUniqueFindersCache(
				(DLFileEntryMetadataModelImpl)dlFileEntryMetadata, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				DLFileEntryMetadataImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl) {

		Object[] args = new Object[] {
			dlFileEntryMetadataModelImpl.getDDMStructureId(),
			dlFileEntryMetadataModelImpl.getFileVersionId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByD_F, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByD_F, args, dlFileEntryMetadataModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileEntryMetadataModelImpl.getDDMStructureId(),
				dlFileEntryMetadataModelImpl.getFileVersionId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByD_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByD_F, args);
		}

		if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileEntryMetadataModelImpl.getOriginalDDMStructureId(),
				dlFileEntryMetadataModelImpl.getOriginalFileVersionId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByD_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByD_F, args);
		}
	}

	/**
	 * Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	 *
	 * @param fileEntryMetadataId the primary key for the new document library file entry metadata
	 * @return the new document library file entry metadata
	 */
	@Override
	public DLFileEntryMetadata create(long fileEntryMetadataId) {
		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setNew(true);
		dlFileEntryMetadata.setPrimaryKey(fileEntryMetadataId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntryMetadata.setUuid(uuid);

		dlFileEntryMetadata.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFileEntryMetadata;
	}

	/**
	 * Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata remove(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException {

		return remove((Serializable)fileEntryMetadataId);
	}

	/**
	 * Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata remove(Serializable primaryKey)
		throws NoSuchFileEntryMetadataException {

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata dlFileEntryMetadata =
				(DLFileEntryMetadata)session.get(
					DLFileEntryMetadataImpl.class, primaryKey);

			if (dlFileEntryMetadata == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryMetadataException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlFileEntryMetadata);
		}
		catch (NoSuchFileEntryMetadataException noSuchEntityException) {
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
	protected DLFileEntryMetadata removeImpl(
		DLFileEntryMetadata dlFileEntryMetadata) {

		if (!CTPersistenceHelperUtil.isRemove(dlFileEntryMetadata)) {
			return dlFileEntryMetadata;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntryMetadata)) {
				dlFileEntryMetadata = (DLFileEntryMetadata)session.get(
					DLFileEntryMetadataImpl.class,
					dlFileEntryMetadata.getPrimaryKeyObj());
			}

			if (dlFileEntryMetadata != null) {
				session.delete(dlFileEntryMetadata);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryMetadata != null) {
			clearCache(dlFileEntryMetadata);
		}

		return dlFileEntryMetadata;
	}

	@Override
	public DLFileEntryMetadata updateImpl(
		DLFileEntryMetadata dlFileEntryMetadata) {

		boolean isNew = dlFileEntryMetadata.isNew();

		if (!(dlFileEntryMetadata instanceof DLFileEntryMetadataModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileEntryMetadata.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlFileEntryMetadata);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileEntryMetadata proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileEntryMetadata implementation " +
					dlFileEntryMetadata.getClass());
		}

		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl =
			(DLFileEntryMetadataModelImpl)dlFileEntryMetadata;

		if (Validator.isNull(dlFileEntryMetadata.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntryMetadata.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(dlFileEntryMetadata)) {
				if (!isNew) {
					session.evict(
						DLFileEntryMetadataImpl.class,
						dlFileEntryMetadata.getPrimaryKeyObj());
				}

				session.save(dlFileEntryMetadata);

				dlFileEntryMetadata.setNew(false);
			}
			else {
				dlFileEntryMetadata = (DLFileEntryMetadata)session.merge(
					dlFileEntryMetadata);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryMetadata.getCtCollectionId() != 0) {
			dlFileEntryMetadata.resetOriginalValues();

			return dlFileEntryMetadata;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				dlFileEntryMetadataModelImpl.getUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				dlFileEntryMetadataModelImpl.getUuid(),
				dlFileEntryMetadataModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {dlFileEntryMetadataModelImpl.getFileEntryId()};

			FinderCacheUtil.removeResult(_finderPathCountByFileEntryId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByFileEntryId, args);

			args = new Object[] {
				dlFileEntryMetadataModelImpl.getFileVersionId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByFileVersionId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByFileVersionId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileEntryMetadataModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {dlFileEntryMetadataModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileEntryMetadataModelImpl.getOriginalUuid(),
					dlFileEntryMetadataModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					dlFileEntryMetadataModelImpl.getUuid(),
					dlFileEntryMetadataModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFileEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileEntryMetadataModelImpl.getOriginalFileEntryId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);

				args = new Object[] {
					dlFileEntryMetadataModelImpl.getFileEntryId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileEntryId, args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFileVersionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileEntryMetadataModelImpl.getOriginalFileVersionId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileVersionId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileVersionId, args);

				args = new Object[] {
					dlFileEntryMetadataModelImpl.getFileVersionId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByFileVersionId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByFileVersionId, args);
			}
		}

		EntityCacheUtil.putResult(
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata, false);

		clearUniqueFindersCache(dlFileEntryMetadataModelImpl, false);
		cacheUniqueFindersCache(dlFileEntryMetadataModelImpl);

		dlFileEntryMetadata.resetOriginalValues();

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileEntryMetadataException {

		DLFileEntryMetadata dlFileEntryMetadata = fetchByPrimaryKey(primaryKey);

		if (dlFileEntryMetadata == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryMetadataException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a <code>NoSuchFileEntryMetadataException</code> if it could not be found.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata findByPrimaryKey(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException {

		return findByPrimaryKey((Serializable)fileEntryMetadataId);
	}

	/**
	 * Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				DLFileEntryMetadata.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		DLFileEntryMetadata dlFileEntryMetadata = null;

		Session session = null;

		try {
			session = openSession();

			dlFileEntryMetadata = (DLFileEntryMetadata)session.get(
				DLFileEntryMetadataImpl.class, primaryKey);

			if (dlFileEntryMetadata != null) {
				cacheResult(dlFileEntryMetadata);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByPrimaryKey(long fileEntryMetadataId) {
		return fetchByPrimaryKey((Serializable)fileEntryMetadataId);
	}

	@Override
	public Map<Serializable, DLFileEntryMetadata> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				DLFileEntryMetadata.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileEntryMetadata> map =
			new HashMap<Serializable, DLFileEntryMetadata>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileEntryMetadata dlFileEntryMetadata = fetchByPrimaryKey(
				primaryKey);

			if (dlFileEntryMetadata != null) {
				map.put(primaryKey, dlFileEntryMetadata);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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

			for (DLFileEntryMetadata dlFileEntryMetadata :
					(List<DLFileEntryMetadata>)query.list()) {

				map.put(
					dlFileEntryMetadata.getPrimaryKeyObj(),
					dlFileEntryMetadata);

				cacheResult(dlFileEntryMetadata);
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
	 * Returns all the document library file entry metadatas.
	 *
	 * @return the document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll(
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryMetadataModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll(
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

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

		List<DLFileEntryMetadata> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DLFILEENTRYMETADATA);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYMETADATA;

				sql = sql.concat(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the document library file entry metadatas from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileEntryMetadata dlFileEntryMetadata : findAll()) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas.
	 *
	 * @return the number of document library file entry metadatas
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryMetadata.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_DLFILEENTRYMETADATA);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "fileEntryMetadataId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEENTRYMETADATA;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return DLFileEntryMetadataModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLFileEntryMetadata";
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
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("DDMStorageId");
		ctStrictColumnNames.add("DDMStructureId");
		ctStrictColumnNames.add("fileEntryId");
		ctStrictColumnNames.add("fileVersionId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fileEntryMetadataId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"DDMStructureId", "fileVersionId"});
	}

	/**
	 * Initializes the document library file entry metadata persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DLFileEntryMetadataModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFileEntryMetadataModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryMetadataModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByFileEntryId = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFileEntryId = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] {Long.class.getName()},
			DLFileEntryMetadataModelImpl.FILEENTRYID_COLUMN_BITMASK);

		_finderPathCountByFileEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFileEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFileVersionId = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFileVersionId = new FinderPath(
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] {Long.class.getName()},
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);

		_finderPathCountByFileVersionId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFileVersionId", new String[] {Long.class.getName()});

		_finderPathFetchByD_F = new FinderPath(
			DLFileEntryMetadataImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			DLFileEntryMetadataModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK |
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);

		_finderPathCountByD_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFileEntryMetadataImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_DLFILEENTRYMETADATA =
		"SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata";

	private static final String _SQL_SELECT_DLFILEENTRYMETADATA_WHERE =
		"SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";

	private static final String _SQL_COUNT_DLFILEENTRYMETADATA =
		"SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata";

	private static final String _SQL_COUNT_DLFILEENTRYMETADATA_WHERE =
		"SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryMetadata.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLFileEntryMetadata exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileEntryMetadata exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryMetadataPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}