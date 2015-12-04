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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the document library file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionUtil
 * @generated
 */
@ProviderType
public class DLFileVersionPersistenceImpl extends BasePersistenceImpl<DLFileVersion>
	implements DLFileVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileVersionUtil} to access the document library file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!Validator.equals(uuid, dlFileVersion.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_First(String uuid,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByUuid_First(uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_First(String uuid,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_Last(String uuid,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_Last(String uuid,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByUuid_PrevAndNext(long fileVersionId,
		String uuid, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileVersion, uuid,
					orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_PrevAndNext(session, dlFileVersion, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByUuid_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, String uuid,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileVersion dlFileVersion : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileVersion.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileVersion.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByUUID_G(uuid, groupId);

		if (dlFileVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if (!Validator.equals(uuid, dlFileVersion.getUuid()) ||
					(groupId != dlFileVersion.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DLFileVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DLFileVersion dlFileVersion = list.get(0);

					result = dlFileVersion;

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getUuid() == null) ||
							!dlFileVersion.getUuid().equals(uuid) ||
							(dlFileVersion.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (DLFileVersion)result;
		}
	}

	/**
	 * Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByUUID_G(uuid, groupId);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileVersion.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileVersion.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileVersion.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!Validator.equals(uuid, dlFileVersion.getUuid()) ||
							(companyId != dlFileVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByUuid_C_PrevAndNext(long fileVersionId,
		String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileVersion, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileVersion, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByUuid_C_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, String uuid, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileVersion dlFileVersion : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileVersion.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileVersion.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileVersion.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			DLFileVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((companyId != dlFileVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByCompanyId_First(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByCompanyId_First(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByCompanyId_Last(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByCompanyId_Last(long companyId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByCompanyId_PrevAndNext(long fileVersionId,
		long companyId, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFileVersion,
					companyId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByCompanyId_PrevAndNext(session, dlFileVersion,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByCompanyId_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long companyId,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DLFileVersion dlFileVersion : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileVersion.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFileEntryId", new String[] { Long.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId) {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId, int start,
		int end) {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByFileEntryId(fileEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByFileEntryId(long fileEntryId, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((fileEntryId != dlFileVersion.getFileEntryId())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByFileEntryId(fileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByFileEntryId(fileEntryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByFileEntryId_PrevAndNext(long fileVersionId,
		long fileEntryId, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session, dlFileVersion,
					fileEntryId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByFileEntryId_PrevAndNext(session, dlFileVersion,
					fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByFileEntryId_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long fileEntryId,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 */
	@Override
	public void removeByFileEntryId(long fileEntryId) {
		for (DLFileVersion dlFileVersion : findByFileEntryId(fileEntryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByFileEntryId(long fileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYID;

		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByMimeType",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByMimeType", new String[] { String.class.getName() },
			DLFileVersionModelImpl.MIMETYPE_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MIMETYPE = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] { String.class.getName() });

	/**
	 * Returns all the document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType) {
		return findByMimeType(mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType, int start,
		int end) {
		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByMimeType(mimeType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByMimeType(String mimeType, int start,
		int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType, start, end, orderByComparator };
		}

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if (!Validator.equals(mimeType, dlFileVersion.getMimeType())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			boolean bindMimeType = false;

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else if (mimeType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
				}

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByMimeType_First(String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByMimeType_First(mimeType,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByMimeType_First(String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByMimeType(mimeType, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByMimeType_Last(String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByMimeType_Last(mimeType,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByMimeType_Last(String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByMimeType(mimeType);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByMimeType(mimeType, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where mimeType = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByMimeType_PrevAndNext(long fileVersionId,
		String mimeType, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByMimeType_PrevAndNext(session, dlFileVersion,
					mimeType, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByMimeType_PrevAndNext(session, dlFileVersion,
					mimeType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByMimeType_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, String mimeType,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		boolean bindMimeType = false;

		if (mimeType == null) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
		}
		else if (mimeType.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
		}
		else {
			bindMimeType = true;

			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindMimeType) {
			qPos.add(mimeType);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 */
	@Override
	public void removeByMimeType(String mimeType) {
		for (DLFileVersion dlFileVersion : findByMimeType(mimeType,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByMimeType(String mimeType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MIMETYPE;

		Object[] finderArgs = new Object[] { mimeType };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			boolean bindMimeType = false;

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else if (mimeType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
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

	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_1 = "dlFileVersion.mimeType IS NULL";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_2 = "dlFileVersion.mimeType = ?";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_3 = "(dlFileVersion.mimeType IS NULL OR dlFileVersion.mimeType = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status) {
		return findByC_NotS(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end) {
		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByC_NotS(companyId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS;
		finderArgs = new Object[] {
				companyId, status,
				
				start, end, orderByComparator
			};

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((companyId != dlFileVersion.getCompanyId()) ||
							(status == dlFileVersion.getStatus())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByC_NotS_First(companyId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByC_NotS(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByC_NotS_Last(companyId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByC_NotS(companyId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByC_NotS(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByC_NotS_PrevAndNext(long fileVersionId,
		long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByC_NotS_PrevAndNext(session, dlFileVersion,
					companyId, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByC_NotS_PrevAndNext(session, dlFileVersion,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByC_NotS_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long companyId, int status,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		for (DLFileVersion dlFileVersion : findByC_NotS(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_NOTS_COMPANYID_2 = "dlFileVersion.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTS_STATUS_2 = "dlFileVersion.status != ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_F_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByF_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByF_V(fileEntryId, version);

		if (dlFileVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(long fileEntryId, String version) {
		return fetchByF_V(fileEntryId, version, true);
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_V(long fileEntryId, String version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { fileEntryId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_F_V,
					finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
					!Validator.equals(version, dlFileVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DLFileVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_F_V, finderArgs,
						list);
				}
				else {
					DLFileVersion dlFileVersion = list.get(0);

					result = dlFileVersion;

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getFileEntryId() != fileEntryId) ||
							(dlFileVersion.getVersion() == null) ||
							!dlFileVersion.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_F_V,
							finderArgs, dlFileVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, finderArgs);

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
			return (DLFileVersion)result;
		}
	}

	/**
	 * Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the document library file version that was removed
	 */
	@Override
	public DLFileVersion removeByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByF_V(fileEntryId, version);

		return remove(dlFileVersion);
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_V(long fileEntryId, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_V;

		Object[] finderArgs = new Object[] { fileEntryId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (bindVersion) {
					qPos.add(version);
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

	private static final String _FINDER_COLUMN_F_V_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_V_VERSION_1 = "dlFileVersion.version IS NULL";
	private static final String _FINDER_COLUMN_F_V_VERSION_2 = "dlFileVersion.version = ?";
	private static final String _FINDER_COLUMN_F_V_VERSION_3 = "(dlFileVersion.version IS NULL OR dlFileVersion.version = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status) {
		return findByF_S(fileEntryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end) {
		return findByF_S(fileEntryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator) {
		return findByF_S(fileEntryId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end, OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] { fileEntryId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] {
					fileEntryId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
							(status != dlFileVersion.getStatus())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_S_First(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByF_S_First(fileEntryId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_S_First(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByF_S(fileEntryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByF_S_Last(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByF_S_Last(fileEntryId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByF_S_Last(long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByF_S(fileEntryId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByF_S(fileEntryId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByF_S_PrevAndNext(long fileVersionId,
		long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(session, dlFileVersion,
					fileEntryId, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByF_S_PrevAndNext(session, dlFileVersion,
					fileEntryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByF_S_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long fileEntryId, int status,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

		query.append(_FINDER_COLUMN_F_S_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long fileEntryId, int status) {
		for (DLFileVersion dlFileVersion : findByF_S(fileEntryId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByF_S(long fileEntryId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_S;

		Object[] finderArgs = new Object[] { fileEntryId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_F_S_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_S_STATUS_2 = "dlFileVersion.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status) {
		return findByG_F_S(groupId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end) {
		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return findByG_F_S(groupId, folderId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S;
			finderArgs = new Object[] { groupId, folderId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_S;
			finderArgs = new Object[] {
					groupId, folderId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((groupId != dlFileVersion.getGroupId()) ||
							(folderId != dlFileVersion.getFolderId()) ||
							(status != dlFileVersion.getStatus())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByG_F_S_First(groupId, folderId,
				status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByG_F_S(groupId, folderId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByG_F_S_Last(groupId, folderId,
				status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByG_F_S(groupId, folderId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByG_F_S(groupId, folderId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByG_F_S_PrevAndNext(long fileVersionId,
		long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_S_PrevAndNext(session, dlFileVersion, groupId,
					folderId, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_S_PrevAndNext(session, dlFileVersion, groupId,
					folderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_S_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, int status,
		OrderByComparator<DLFileVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	@Override
	public void removeByG_F_S(long groupId, long folderId, int status) {
		for (DLFileVersion dlFileVersion : findByG_F_S(groupId, folderId,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_S(long groupId, long folderId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_S;

		Object[] finderArgs = new Object[] { groupId, folderId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_F_S_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_STATUS_2 = "dlFileVersion.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_T_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.TITLE_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK |
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version) {
		return findByG_F_T_V(groupId, folderId, title, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version, int start, int end) {
		return findByG_F_T_V(groupId, folderId, title, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return findByG_F_T_V(groupId, folderId, title, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file versions
	 */
	@Override
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version, int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V;
			finderArgs = new Object[] { groupId, folderId, title, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_T_V;
			finderArgs = new Object[] {
					groupId, folderId, title, version,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileVersion dlFileVersion : list) {
					if ((groupId != dlFileVersion.getGroupId()) ||
							(folderId != dlFileVersion.getFolderId()) ||
							!Validator.equals(title, dlFileVersion.getTitle()) ||
							!Validator.equals(version,
								dlFileVersion.getVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
				}

				if (bindVersion) {
					qPos.add(version);
				}

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_T_V_First(long groupId, long folderId,
		String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByG_F_T_V_First(groupId, folderId,
				title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_T_V_First(long groupId, long folderId,
		String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator) {
		List<DLFileVersion> list = findByG_F_T_V(groupId, folderId, title,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws NoSuchFileVersionException if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion findByG_F_T_V_Last(long groupId, long folderId,
		String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByG_F_T_V_Last(groupId, folderId,
				title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 */
	@Override
	public DLFileVersion fetchByG_F_T_V_Last(long groupId, long folderId,
		String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator) {
		int count = countByG_F_T_V(groupId, folderId, title, version);

		if (count == 0) {
			return null;
		}

		List<DLFileVersion> list = findByG_F_T_V(groupId, folderId, title,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion[] findByG_F_T_V_PrevAndNext(long fileVersionId,
		long groupId, long folderId, String title, String version,
		OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_T_V_PrevAndNext(session, dlFileVersion,
					groupId, folderId, title, version, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_T_V_PrevAndNext(session, dlFileVersion,
					groupId, folderId, title, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_T_V_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, String title,
		String version, OrderByComparator<DLFileVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

		boolean bindTitle = false;

		if (title == null) {
			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
		}
		else if (title.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
		}
		else {
			bindTitle = true;

			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
		}

		boolean bindVersion = false;

		if (version == null) {
			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
		}
		else if (version.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
		}
		else {
			bindVersion = true;

			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (bindTitle) {
			qPos.add(title);
		}

		if (bindVersion) {
			qPos.add(version);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 */
	@Override
	public void removeByG_F_T_V(long groupId, long folderId, String title,
		String version) {
		for (DLFileVersion dlFileVersion : findByG_F_T_V(groupId, folderId,
				title, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching document library file versions
	 */
	@Override
	public int countByG_F_T_V(long groupId, long folderId, String title,
		String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_T_V;

		Object[] finderArgs = new Object[] { groupId, folderId, title, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
			}

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
				}

				if (bindVersion) {
					qPos.add(version);
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

	private static final String _FINDER_COLUMN_G_F_T_V_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_1 = "dlFileVersion.title IS NULL AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_2 = "dlFileVersion.title = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_3 = "(dlFileVersion.title IS NULL OR dlFileVersion.title = '') AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_1 = "dlFileVersion.version IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_2 = "dlFileVersion.version = ?";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_3 = "(dlFileVersion.version IS NULL OR dlFileVersion.version = '')";

	public DLFileVersionPersistenceImpl() {
		setModelClass(DLFileVersion.class);
	}

	/**
	 * Caches the document library file version in the entity cache if it is enabled.
	 *
	 * @param dlFileVersion the document library file version
	 */
	@Override
	public void cacheResult(DLFileVersion dlFileVersion) {
		entityCache.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFileVersion.getUuid(), dlFileVersion.getGroupId() },
			dlFileVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_F_V,
			new Object[] {
				dlFileVersion.getFileEntryId(), dlFileVersion.getVersion()
			}, dlFileVersion);

		dlFileVersion.resetOriginalValues();
	}

	/**
	 * Caches the document library file versions in the entity cache if it is enabled.
	 *
	 * @param dlFileVersions the document library file versions
	 */
	@Override
	public void cacheResult(List<DLFileVersion> dlFileVersions) {
		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (entityCache.getResult(
						DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, dlFileVersion.getPrimaryKey()) == null) {
				cacheResult(dlFileVersion);
			}
			else {
				dlFileVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileVersion dlFileVersion) {
		entityCache.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileVersionModelImpl)dlFileVersion);
	}

	@Override
	public void clearCache(List<DLFileVersion> dlFileVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			entityCache.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

			clearUniqueFindersCache((DLFileVersionModelImpl)dlFileVersion);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileVersionModelImpl dlFileVersionModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					dlFileVersionModelImpl.getUuid(),
					dlFileVersionModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				dlFileVersionModelImpl);

			args = new Object[] {
					dlFileVersionModelImpl.getFileEntryId(),
					dlFileVersionModelImpl.getVersion()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_F_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_F_V, args,
				dlFileVersionModelImpl);
		}
		else {
			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getUuid(),
						dlFileVersionModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					dlFileVersionModelImpl);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_F_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getFileEntryId(),
						dlFileVersionModelImpl.getVersion()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_F_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_F_V, args,
					dlFileVersionModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLFileVersionModelImpl dlFileVersionModelImpl) {
		Object[] args = new Object[] {
				dlFileVersionModelImpl.getUuid(),
				dlFileVersionModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((dlFileVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlFileVersionModelImpl.getOriginalUuid(),
					dlFileVersionModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				dlFileVersionModelImpl.getFileEntryId(),
				dlFileVersionModelImpl.getVersion()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_F_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, args);

		if ((dlFileVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_F_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlFileVersionModelImpl.getOriginalFileEntryId(),
					dlFileVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, args);
		}
	}

	/**
	 * Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	 *
	 * @param fileVersionId the primary key for the new document library file version
	 * @return the new document library file version
	 */
	@Override
	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		String uuid = PortalUUIDUtil.generate();

		dlFileVersion.setUuid(uuid);

		return dlFileVersion;
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException {
		return remove((Serializable)fileVersionId);
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion remove(Serializable primaryKey)
		throws NoSuchFileVersionException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
					primaryKey);

			if (dlFileVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileVersion);
		}
		catch (NoSuchFileVersionException nsee) {
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
	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion) {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileVersion)) {
				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						dlFileVersion.getPrimaryKeyObj());
			}

			if (dlFileVersion != null) {
				session.delete(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersion != null) {
			clearCache(dlFileVersion);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion updateImpl(DLFileVersion dlFileVersion) {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		boolean isNew = dlFileVersion.isNew();

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		if (Validator.isNull(dlFileVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileVersion.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFileVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileVersion.setCreateDate(now);
			}
			else {
				dlFileVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!dlFileVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileVersion.setModifiedDate(now);
			}
			else {
				dlFileVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileVersion.isNew()) {
				session.save(dlFileVersion);

				dlFileVersion.setNew(false);
			}
			else {
				dlFileVersion = (DLFileVersion)session.merge(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileVersionModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalUuid(),
						dlFileVersionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileVersionModelImpl.getUuid(),
						dlFileVersionModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { dlFileVersionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] { dlFileVersionModelImpl.getFileEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalMimeType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);

				args = new Object[] { dlFileVersionModelImpl.getMimeType() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalFileEntryId(),
						dlFileVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);

				args = new Object[] {
						dlFileVersionModelImpl.getFileEntryId(),
						dlFileVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalGroupId(),
						dlFileVersionModelImpl.getOriginalFolderId(),
						dlFileVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S,
					args);

				args = new Object[] {
						dlFileVersionModelImpl.getGroupId(),
						dlFileVersionModelImpl.getFolderId(),
						dlFileVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalGroupId(),
						dlFileVersionModelImpl.getOriginalFolderId(),
						dlFileVersionModelImpl.getOriginalTitle(),
						dlFileVersionModelImpl.getOriginalVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_T_V, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V,
					args);

				args = new Object[] {
						dlFileVersionModelImpl.getGroupId(),
						dlFileVersionModelImpl.getFolderId(),
						dlFileVersionModelImpl.getTitle(),
						dlFileVersionModelImpl.getVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_T_V, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V,
					args);
			}
		}

		entityCache.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion, false);

		clearUniqueFindersCache(dlFileVersionModelImpl);
		cacheUniqueFindersCache(dlFileVersionModelImpl, isNew);

		dlFileVersion.resetOriginalValues();

		return dlFileVersion;
	}

	protected DLFileVersion toUnwrappedModel(DLFileVersion dlFileVersion) {
		if (dlFileVersion instanceof DLFileVersionImpl) {
			return dlFileVersion;
		}

		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setNew(dlFileVersion.isNew());
		dlFileVersionImpl.setPrimaryKey(dlFileVersion.getPrimaryKey());

		dlFileVersionImpl.setUuid(dlFileVersion.getUuid());
		dlFileVersionImpl.setFileVersionId(dlFileVersion.getFileVersionId());
		dlFileVersionImpl.setGroupId(dlFileVersion.getGroupId());
		dlFileVersionImpl.setCompanyId(dlFileVersion.getCompanyId());
		dlFileVersionImpl.setUserId(dlFileVersion.getUserId());
		dlFileVersionImpl.setUserName(dlFileVersion.getUserName());
		dlFileVersionImpl.setCreateDate(dlFileVersion.getCreateDate());
		dlFileVersionImpl.setModifiedDate(dlFileVersion.getModifiedDate());
		dlFileVersionImpl.setRepositoryId(dlFileVersion.getRepositoryId());
		dlFileVersionImpl.setFolderId(dlFileVersion.getFolderId());
		dlFileVersionImpl.setFileEntryId(dlFileVersion.getFileEntryId());
		dlFileVersionImpl.setTreePath(dlFileVersion.getTreePath());
		dlFileVersionImpl.setFileName(dlFileVersion.getFileName());
		dlFileVersionImpl.setExtension(dlFileVersion.getExtension());
		dlFileVersionImpl.setMimeType(dlFileVersion.getMimeType());
		dlFileVersionImpl.setTitle(dlFileVersion.getTitle());
		dlFileVersionImpl.setDescription(dlFileVersion.getDescription());
		dlFileVersionImpl.setChangeLog(dlFileVersion.getChangeLog());
		dlFileVersionImpl.setExtraSettings(dlFileVersion.getExtraSettings());
		dlFileVersionImpl.setFileEntryTypeId(dlFileVersion.getFileEntryTypeId());
		dlFileVersionImpl.setVersion(dlFileVersion.getVersion());
		dlFileVersionImpl.setSize(dlFileVersion.getSize());
		dlFileVersionImpl.setChecksum(dlFileVersion.getChecksum());
		dlFileVersionImpl.setLastPublishDate(dlFileVersion.getLastPublishDate());
		dlFileVersionImpl.setStatus(dlFileVersion.getStatus());
		dlFileVersionImpl.setStatusByUserId(dlFileVersion.getStatusByUserId());
		dlFileVersionImpl.setStatusByUserName(dlFileVersion.getStatusByUserName());
		dlFileVersionImpl.setStatusDate(dlFileVersion.getStatusDate());

		return dlFileVersionImpl;
	}

	/**
	 * Returns the document library file version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileVersionException {
		DLFileVersion dlFileVersion = fetchByPrimaryKey(primaryKey);

		if (dlFileVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or throws a {@link NoSuchFileVersionException} if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version
	 * @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException {
		return findByPrimaryKey((Serializable)fileVersionId);
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion fetchByPrimaryKey(Serializable primaryKey) {
		DLFileVersion dlFileVersion = (DLFileVersion)entityCache.getResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, primaryKey);

		if (dlFileVersion == _nullDLFileVersion) {
			return null;
		}

		if (dlFileVersion == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						primaryKey);

				if (dlFileVersion != null) {
					cacheResult(dlFileVersion);
				}
				else {
					entityCache.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, primaryKey, _nullDLFileVersion);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
					DLFileVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 */
	@Override
	public DLFileVersion fetchByPrimaryKey(long fileVersionId) {
		return fetchByPrimaryKey((Serializable)fileVersionId);
	}

	@Override
	public Map<Serializable, DLFileVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileVersion> map = new HashMap<Serializable, DLFileVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileVersion dlFileVersion = fetchByPrimaryKey(primaryKey);

			if (dlFileVersion != null) {
				map.put(primaryKey, dlFileVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLFileVersion dlFileVersion = (DLFileVersion)entityCache.getResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
					DLFileVersionImpl.class, primaryKey);

			if (dlFileVersion == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlFileVersion);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE_PKS_IN);

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

			for (DLFileVersion dlFileVersion : (List<DLFileVersion>)q.list()) {
				map.put(dlFileVersion.getPrimaryKeyObj(), dlFileVersion);

				cacheResult(dlFileVersion);

				uncachedPrimaryKeys.remove(dlFileVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
					DLFileVersionImpl.class, primaryKey, _nullDLFileVersion);
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
	 * Returns all the document library file versions.
	 *
	 * @return the document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library file versions
	 */
	@Override
	public List<DLFileVersion> findAll(int start, int end,
		OrderByComparator<DLFileVersion> orderByComparator,
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

		List<DLFileVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSION;

				if (pagination) {
					sql = sql.concat(DLFileVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the document library file versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileVersion dlFileVersion : findAll()) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions.
	 *
	 * @return the number of document library file versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEVERSION);

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
		return DLFileVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLFILEVERSION = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion";
	private static final String _SQL_SELECT_DLFILEVERSION_WHERE_PKS_IN = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE fileVersionId IN (";
	private static final String _SQL_SELECT_DLFILEVERSION_WHERE = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _SQL_COUNT_DLFILEVERSION = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion";
	private static final String _SQL_COUNT_DLFILEVERSION_WHERE = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "size"
			});
	private static final DLFileVersion _nullDLFileVersion = new DLFileVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileVersion> toCacheModel() {
				return _nullDLFileVersionCacheModel;
			}
		};

	private static final CacheModel<DLFileVersion> _nullDLFileVersionCacheModel = new CacheModel<DLFileVersion>() {
			@Override
			public DLFileVersion toEntityModel() {
				return _nullDLFileVersion;
			}
		};
}