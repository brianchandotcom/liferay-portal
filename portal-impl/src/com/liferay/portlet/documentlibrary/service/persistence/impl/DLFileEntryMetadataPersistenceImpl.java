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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryMetadataPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the document library file entry metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryMetadataUtil
 * @generated
 */
@ProviderType
public class DLFileEntryMetadataPersistenceImpl extends BasePersistenceImpl<DLFileEntryMetadata>
	implements DLFileEntryMetadataPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryMetadataUtil} to access the document library file entry metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryMetadataImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileEntryMetadataModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(String uuid, int start,
		int end, OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid(String uuid, int start,
		int end, OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<DLFileEntryMetadata> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryMetadata>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (!Validator.equals(uuid, dlFileEntryMetadata.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_First(String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_First(uuid,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_First(String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		List<DLFileEntryMetadata> list = findByUuid(uuid, 0, 1,
				orderByComparator);

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
	public DLFileEntryMetadata findByUuid_Last(String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_Last(uuid,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByUuid_Last(String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

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
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileEntryMetadata,
					uuid, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByUuid_PrevAndNext(session, dlFileEntryMetadata,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByUuid_PrevAndNext(Session session,
		DLFileEntryMetadata dlFileEntryMetadata, String uuid,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

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
		for (DLFileEntryMetadata dlFileEntryMetadata : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntryMetadata.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntryMetadata.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntryMetadata.uuid IS NULL OR dlFileEntryMetadata.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileEntryMetadataModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryMetadataModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<DLFileEntryMetadata> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntryMetadata> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryMetadata>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if (!Validator.equals(uuid, dlFileEntryMetadata.getUuid()) ||
							(companyId != dlFileEntryMetadata.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
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
	public DLFileEntryMetadata fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		List<DLFileEntryMetadata> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

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
	public DLFileEntryMetadata findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
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
	public DLFileEntryMetadata fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

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
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileEntryMetadata,
					uuid, companyId, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileEntryMetadata,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByUuid_C_PrevAndNext(Session session,
		DLFileEntryMetadata dlFileEntryMetadata, String uuid, long companyId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

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
		for (DLFileEntryMetadata dlFileEntryMetadata : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileEntryMetadata.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileEntryMetadata.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileEntryMetadata.uuid IS NULL OR dlFileEntryMetadata.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileEntryMetadata.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] { Long.class.getName() },
			DLFileEntryMetadataModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId,
		int start, int end) {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return findByFileEntryId(fileEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		List<DLFileEntryMetadata> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryMetadata>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if ((fileEntryId != dlFileEntryMetadata.getFileEntryId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (!pagination) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		List<DLFileEntryMetadata> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

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
	public DLFileEntryMetadata findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByFileEntryId(fileEntryId,
				count - 1, count, orderByComparator);

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
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryId, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
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
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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
			query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

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
		for (DLFileEntryMetadata dlFileEntryMetadata : findByFileEntryId(
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYID;

		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileEntryMetadata.fileEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId,
		int start, int end) {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return findByFileVersionId(fileVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId,
		int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		List<DLFileEntryMetadata> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryMetadata>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryMetadata dlFileEntryMetadata : list) {
					if ((fileVersionId != dlFileEntryMetadata.getFileVersionId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				if (!pagination) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByFileVersionId_First(long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileVersionId_First(long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		List<DLFileEntryMetadata> list = findByFileVersionId(fileVersionId, 0,
				1, orderByComparator);

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
	public DLFileEntryMetadata findByFileVersionId_Last(long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByFileVersionId_Last(long fileVersionId,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryMetadata> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

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
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryMetadata, fileVersionId, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryMetadata, fileVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
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
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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
			query.append(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

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
		for (DLFileEntryMetadata dlFileEntryMetadata : findByFileVersionId(
				fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEVERSIONID;

		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "dlFileEntryMetadata.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_D_F = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryMetadataModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK |
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata
	 * @throws NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata findByD_F(long DDMStructureId, long fileVersionId)
		throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByD_F(DDMStructureId,
				fileVersionId);

		if (dlFileEntryMetadata == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("DDMStructureId=");
			msg.append(DDMStructureId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryMetadataException(msg.toString());
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId) {
		return fetchByD_F(DDMStructureId, fileVersionId, true);
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 */
	@Override
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_D_F,
					finderArgs, this);
		}

		if (result instanceof DLFileEntryMetadata) {
			DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)result;

			if ((DDMStructureId != dlFileEntryMetadata.getDDMStructureId()) ||
					(fileVersionId != dlFileEntryMetadata.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

				qPos.add(fileVersionId);

				List<DLFileEntryMetadata> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_D_F, finderArgs,
						list);
				}
				else {
					DLFileEntryMetadata dlFileEntryMetadata = list.get(0);

					result = dlFileEntryMetadata;

					cacheResult(dlFileEntryMetadata);

					if ((dlFileEntryMetadata.getDDMStructureId() != DDMStructureId) ||
							(dlFileEntryMetadata.getFileVersionId() != fileVersionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_D_F,
							finderArgs, dlFileEntryMetadata);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_D_F, finderArgs);

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
			return (DLFileEntryMetadata)result;
		}
	}

	/**
	 * Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the document library file entry metadata that was removed
	 */
	@Override
	public DLFileEntryMetadata removeByD_F(long DDMStructureId,
		long fileVersionId) throws NoSuchFileEntryMetadataException {
		DLFileEntryMetadata dlFileEntryMetadata = findByD_F(DDMStructureId,
				fileVersionId);

		return remove(dlFileEntryMetadata);
	}

	/**
	 * Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching document library file entry metadatas
	 */
	@Override
	public int countByD_F(long DDMStructureId, long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_D_F;

		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

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

	private static final String _FINDER_COLUMN_D_F_DDMSTRUCTUREID_2 = "dlFileEntryMetadata.DDMStructureId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_FILEVERSIONID_2 = "dlFileEntryMetadata.fileVersionId = ?";

	public DLFileEntryMetadataPersistenceImpl() {
		setModelClass(DLFileEntryMetadata.class);
	}

	/**
	 * Caches the document library file entry metadata in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 */
	@Override
	public void cacheResult(DLFileEntryMetadata dlFileEntryMetadata) {
		entityCache.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata);

		finderCache.putResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				dlFileEntryMetadata.getDDMStructureId(),
				dlFileEntryMetadata.getFileVersionId()
			}, dlFileEntryMetadata);

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
			if (entityCache.getResult(
						DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileEntryMetadataImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry metadata.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryMetadata dlFileEntryMetadata) {
		entityCache.removeResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileEntryMetadataModelImpl)dlFileEntryMetadata);
	}

	@Override
	public void clearCache(List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			entityCache.removeResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryMetadataImpl.class,
				dlFileEntryMetadata.getPrimaryKey());

			clearUniqueFindersCache((DLFileEntryMetadataModelImpl)dlFileEntryMetadata);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					dlFileEntryMetadataModelImpl.getDDMStructureId(),
					dlFileEntryMetadataModelImpl.getFileVersionId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_D_F, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_D_F, args,
				dlFileEntryMetadataModelImpl);
		}
		else {
			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_D_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getDDMStructureId(),
						dlFileEntryMetadataModelImpl.getFileVersionId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_D_F, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_D_F, args,
					dlFileEntryMetadataModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl) {
		Object[] args = new Object[] {
				dlFileEntryMetadataModelImpl.getDDMStructureId(),
				dlFileEntryMetadataModelImpl.getFileVersionId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_D_F, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_D_F, args);

		if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_D_F.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlFileEntryMetadataModelImpl.getOriginalDDMStructureId(),
					dlFileEntryMetadataModelImpl.getOriginalFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_D_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_D_F, args);
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

		dlFileEntryMetadata.setCompanyId(companyProvider.getCompanyId());

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

			DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
					primaryKey);

			if (dlFileEntryMetadata == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryMetadataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileEntryMetadata);
		}
		catch (NoSuchFileEntryMetadataException nsee) {
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
	protected DLFileEntryMetadata removeImpl(
		DLFileEntryMetadata dlFileEntryMetadata) {
		dlFileEntryMetadata = toUnwrappedModel(dlFileEntryMetadata);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntryMetadata)) {
				dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
						dlFileEntryMetadata.getPrimaryKeyObj());
			}

			if (dlFileEntryMetadata != null) {
				session.delete(dlFileEntryMetadata);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
		dlFileEntryMetadata = toUnwrappedModel(dlFileEntryMetadata);

		boolean isNew = dlFileEntryMetadata.isNew();

		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl = (DLFileEntryMetadataModelImpl)dlFileEntryMetadata;

		if (Validator.isNull(dlFileEntryMetadata.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntryMetadata.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntryMetadata.isNew()) {
				session.save(dlFileEntryMetadata);

				dlFileEntryMetadata.setNew(false);
			}
			else {
				dlFileEntryMetadata = (DLFileEntryMetadata)session.merge(dlFileEntryMetadata);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileEntryMetadataModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileEntryMetadataModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getOriginalUuid(),
						dlFileEntryMetadataModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileEntryMetadataModelImpl.getUuid(),
						dlFileEntryMetadataModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getOriginalFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] {
						dlFileEntryMetadataModelImpl.getFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getOriginalFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] {
						dlFileEntryMetadataModelImpl.getFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}
		}

		entityCache.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata, false);

		clearUniqueFindersCache(dlFileEntryMetadataModelImpl);
		cacheUniqueFindersCache(dlFileEntryMetadataModelImpl, isNew);

		dlFileEntryMetadata.resetOriginalValues();

		return dlFileEntryMetadata;
	}

	protected DLFileEntryMetadata toUnwrappedModel(
		DLFileEntryMetadata dlFileEntryMetadata) {
		if (dlFileEntryMetadata instanceof DLFileEntryMetadataImpl) {
			return dlFileEntryMetadata;
		}

		DLFileEntryMetadataImpl dlFileEntryMetadataImpl = new DLFileEntryMetadataImpl();

		dlFileEntryMetadataImpl.setNew(dlFileEntryMetadata.isNew());
		dlFileEntryMetadataImpl.setPrimaryKey(dlFileEntryMetadata.getPrimaryKey());

		dlFileEntryMetadataImpl.setUuid(dlFileEntryMetadata.getUuid());
		dlFileEntryMetadataImpl.setFileEntryMetadataId(dlFileEntryMetadata.getFileEntryMetadataId());
		dlFileEntryMetadataImpl.setCompanyId(dlFileEntryMetadata.getCompanyId());
		dlFileEntryMetadataImpl.setDDMStorageId(dlFileEntryMetadata.getDDMStorageId());
		dlFileEntryMetadataImpl.setDDMStructureId(dlFileEntryMetadata.getDDMStructureId());
		dlFileEntryMetadataImpl.setFileEntryId(dlFileEntryMetadata.getFileEntryId());
		dlFileEntryMetadataImpl.setFileVersionId(dlFileEntryMetadata.getFileVersionId());

		return dlFileEntryMetadataImpl;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
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
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryMetadataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a {@link NoSuchFileEntryMetadataException} if it could not be found.
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
		DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)entityCache.getResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryMetadataImpl.class, primaryKey);

		if (dlFileEntryMetadata == _nullDLFileEntryMetadata) {
			return null;
		}

		if (dlFileEntryMetadata == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
						primaryKey);

				if (dlFileEntryMetadata != null) {
					cacheResult(dlFileEntryMetadata);
				}
				else {
					entityCache.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryMetadataImpl.class, primaryKey,
						_nullDLFileEntryMetadata);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryMetadataImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
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
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileEntryMetadata> map = new HashMap<Serializable, DLFileEntryMetadata>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileEntryMetadata dlFileEntryMetadata = fetchByPrimaryKey(primaryKey);

			if (dlFileEntryMetadata != null) {
				map.put(primaryKey, dlFileEntryMetadata);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)entityCache.getResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryMetadataImpl.class, primaryKey);

			if (dlFileEntryMetadata == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlFileEntryMetadata);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DLFileEntryMetadata dlFileEntryMetadata : (List<DLFileEntryMetadata>)q.list()) {
				map.put(dlFileEntryMetadata.getPrimaryKeyObj(),
					dlFileEntryMetadata);

				cacheResult(dlFileEntryMetadata);

				uncachedPrimaryKeys.remove(dlFileEntryMetadata.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryMetadataImpl.class, primaryKey,
					_nullDLFileEntryMetadata);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll(int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryMetadataModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library file entry metadatas
	 */
	@Override
	public List<DLFileEntryMetadata> findAll(int start, int end,
		OrderByComparator<DLFileEntryMetadata> orderByComparator,
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

		List<DLFileEntryMetadata> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntryMetadata>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEENTRYMETADATA);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYMETADATA;

				if (pagination) {
					sql = sql.concat(DLFileEntryMetadataModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRYMETADATA);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DLFileEntryMetadataModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file entry metadata persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileEntryMetadataImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLFILEENTRYMETADATA = "SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata";
	private static final String _SQL_SELECT_DLFILEENTRYMETADATA_WHERE_PKS_IN = "SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata WHERE fileEntryMetadataId IN (";
	private static final String _SQL_SELECT_DLFILEENTRYMETADATA_WHERE = "SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRYMETADATA = "SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata";
	private static final String _SQL_COUNT_DLFILEENTRYMETADATA_WHERE = "SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryMetadata.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntryMetadata exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntryMetadata exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileEntryMetadataPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final DLFileEntryMetadata _nullDLFileEntryMetadata = new DLFileEntryMetadataImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileEntryMetadata> toCacheModel() {
				return _nullDLFileEntryMetadataCacheModel;
			}
		};

	private static final CacheModel<DLFileEntryMetadata> _nullDLFileEntryMetadataCacheModel =
		new CacheModel<DLFileEntryMetadata>() {
			@Override
			public DLFileEntryMetadata toEntityModel() {
				return _nullDLFileEntryMetadata;
			}
		};
}