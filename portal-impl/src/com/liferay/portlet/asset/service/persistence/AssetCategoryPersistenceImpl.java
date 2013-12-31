/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the asset category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPersistence
 * @see AssetCategoryUtil
 * @generated
 */
public class AssetCategoryPersistenceImpl extends BasePersistenceImpl<AssetCategory>
	implements AssetCategoryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetCategoryUtil} to access the asset category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetCategoryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			AssetCategoryModelImpl.UUID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the asset categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if (!Validator.equals(uuid, assetCategory.getUuid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUuid_First(uuid, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUuid_Last(uuid, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where uuid = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByUuid_PrevAndNext(long categoryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, assetCategory, uuid,
					orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByUuid_PrevAndNext(session, assetCategory, uuid,
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

	protected AssetCategory getByUuid_PrevAndNext(Session session,
		AssetCategory assetCategory, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid(String uuid) throws SystemException {
		for (AssetCategory assetCategory : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "assetCategory.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "assetCategory.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(assetCategory.uuid IS NULL OR assetCategory.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetCategoryModelImpl.UUID_COLUMN_BITMASK |
			AssetCategoryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUUID_G(uuid, groupId);

		if (assetCategory == null) {
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

			throw new NoSuchCategoryException(msg.toString());
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AssetCategory) {
			AssetCategory assetCategory = (AssetCategory)result;

			if (!Validator.equals(uuid, assetCategory.getUuid()) ||
					(groupId != assetCategory.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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

				List<AssetCategory> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					AssetCategory assetCategory = list.get(0);

					result = assetCategory;

					cacheResult(assetCategory);

					if ((assetCategory.getUuid() == null) ||
							!assetCategory.getUuid().equals(uuid) ||
							(assetCategory.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, assetCategory);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs);

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
			return (AssetCategory)result;
		}
	}

	/**
	 * Removes the asset category where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset category that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory removeByUUID_G(String uuid, long groupId)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByUUID_G(uuid, groupId);

		return remove(assetCategory);
	}

	/**
	 * Returns the number of asset categories where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "assetCategory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "assetCategory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(assetCategory.uuid IS NULL OR assetCategory.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "assetCategory.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetCategoryModelImpl.UUID_COLUMN_BITMASK |
			AssetCategoryModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if (!Validator.equals(uuid, assetCategory.getUuid()) ||
						(companyId != assetCategory.getCompanyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByUuid_C_PrevAndNext(long categoryId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, assetCategory, uuid,
					companyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByUuid_C_PrevAndNext(session, assetCategory, uuid,
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

	protected AssetCategory getByUuid_C_PrevAndNext(Session session,
		AssetCategory assetCategory, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "assetCategory.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "assetCategory.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(assetCategory.uuid IS NULL OR assetCategory.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "assetCategory.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			AssetCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByGroupId_First(groupId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByGroupId_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] filterFindByGroupId_PrevAndNext(long categoryId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(categoryId, groupId,
				orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByGroupId_PrevAndNext(session, assetCategory,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory filterGetByGroupId_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByGroupId(long groupId) throws SystemException {
		for (AssetCategory assetCategory : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetCategory.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTCATEGORYID =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentCategoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTCATEGORYID =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentCategoryId", new String[] { Long.class.getName() },
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTCATEGORYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentCategoryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the asset categories where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId)
		throws SystemException {
		return findByParentCategoryId(parentCategoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end) throws SystemException {
		return findByParentCategoryId(parentCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByParentCategoryId(long parentCategoryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTCATEGORYID;
			finderArgs = new Object[] { parentCategoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTCATEGORYID;
			finderArgs = new Object[] {
					parentCategoryId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((parentCategoryId != assetCategory.getParentCategoryId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByParentCategoryId_First(long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByParentCategoryId_First(parentCategoryId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByParentCategoryId_First(long parentCategoryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByParentCategoryId(parentCategoryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByParentCategoryId_Last(long parentCategoryId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByParentCategoryId_Last(parentCategoryId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByParentCategoryId_Last(long parentCategoryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByParentCategoryId(parentCategoryId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByParentCategoryId(parentCategoryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByParentCategoryId_PrevAndNext(long categoryId,
		long parentCategoryId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByParentCategoryId_PrevAndNext(session,
					assetCategory, parentCategoryId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByParentCategoryId_PrevAndNext(session,
					assetCategory, parentCategoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByParentCategoryId_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByParentCategoryId(long parentCategoryId)
		throws SystemException {
		for (AssetCategory assetCategory : findByParentCategoryId(
				parentCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByParentCategoryId(long parentCategoryId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PARENTCATEGORYID;

		Object[] finderArgs = new Object[] { parentCategoryId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PARENTCATEGORYID_PARENTCATEGORYID_2 =
		"assetCategory.parentCategoryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VOCABULARYID =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByVocabularyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VOCABULARYID =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVocabularyId", new String[] { Long.class.getName() },
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VOCABULARYID = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByVocabularyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset categories where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByVocabularyId(long vocabularyId)
		throws SystemException {
		return findByVocabularyId(vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end) throws SystemException {
		return findByVocabularyId(vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByVocabularyId(long vocabularyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VOCABULARYID;
			finderArgs = new Object[] { vocabularyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VOCABULARYID;
			finderArgs = new Object[] {
					vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByVocabularyId_First(long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByVocabularyId_First(vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByVocabularyId_First(long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByVocabularyId(vocabularyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByVocabularyId_Last(long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByVocabularyId_Last(vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByVocabularyId_Last(long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByVocabularyId(vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByVocabularyId(vocabularyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByVocabularyId_PrevAndNext(long categoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByVocabularyId_PrevAndNext(session, assetCategory,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByVocabularyId_PrevAndNext(session, assetCategory,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByVocabularyId_PrevAndNext(Session session,
		AssetCategory assetCategory, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where vocabularyId = &#63; from the database.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByVocabularyId(long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByVocabularyId(vocabularyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByVocabularyId(long vocabularyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VOCABULARYID;

		Object[] finderArgs = new Object[] { vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_VOCABULARYID_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_V",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_V",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_V",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId)
		throws SystemException {
		return findByG_V(groupId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId,
		int start, int end) throws SystemException {
		return findByG_V(groupId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_V;
			finderArgs = new Object[] { groupId, vocabularyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_V;
			finderArgs = new Object[] {
					groupId, vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_V_First(long groupId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_V_First(groupId, vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_V_First(long groupId, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByG_V(groupId, vocabularyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_V_Last(long groupId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_V_Last(groupId, vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_V_Last(long groupId, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_V(groupId, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByG_V(groupId, vocabularyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByG_V_PrevAndNext(long categoryId, long groupId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByG_V_PrevAndNext(session, assetCategory, groupId,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByG_V_PrevAndNext(session, assetCategory, groupId,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByG_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId)
		throws SystemException {
		return filterFindByG_V(groupId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId,
		int start, int end) throws SystemException {
		return filterFindByG_V(groupId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_V(groupId, vocabularyId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(vocabularyId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] filterFindByG_V_PrevAndNext(long categoryId,
		long groupId, long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_V_PrevAndNext(categoryId, groupId, vocabularyId,
				orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByG_V_PrevAndNext(session, assetCategory,
					groupId, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByG_V_PrevAndNext(session, assetCategory,
					groupId, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory filterGetByG_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds) throws SystemException {
		return filterFindByG_V(groupId, vocabularyIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds, int start, int end) throws SystemException {
		return filterFindByG_V(groupId, vocabularyIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_V(long groupId,
		long[] vocabularyIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_V(groupId, vocabularyIds, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_5);

		conjunctionable = true;

		if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < vocabularyIds.length; i++) {
				query.append(_FINDER_COLUMN_G_V_VOCABULARYID_5);

				if ((i + 1) < vocabularyIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (vocabularyIds != null) {
				qPos.add(vocabularyIds);
			}

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long[] vocabularyIds)
		throws SystemException {
		return findByG_V(groupId, vocabularyIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long[] vocabularyIds,
		int start, int end) throws SystemException {
		return findByG_V(groupId, vocabularyIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_V(long groupId, long[] vocabularyIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if ((vocabularyIds != null) && (vocabularyIds.length == 1)) {
			return findByG_V(groupId, vocabularyIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(vocabularyIds) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(vocabularyIds),
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_V,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						!ArrayUtil.contains(vocabularyIds,
							assetCategory.getVocabularyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_V_GROUPID_5);

			conjunctionable = true;

			if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < vocabularyIds.length; i++) {
					query.append(_FINDER_COLUMN_G_V_VOCABULARYID_5);

					if ((i + 1) < vocabularyIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (vocabularyIds != null) {
					qPos.add(vocabularyIds);
				}

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_V,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_V,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset categories where groupId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_V(long groupId, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByG_V(groupId, vocabularyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_V(long groupId, long vocabularyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_V;

		Object[] finderArgs = new Object[] { groupId, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_V(long groupId, long[] vocabularyIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(vocabularyIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_V_GROUPID_5);

			conjunctionable = true;

			if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < vocabularyIds.length; i++) {
					query.append(_FINDER_COLUMN_G_V_VOCABULARYID_5);

					if ((i + 1) < vocabularyIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (vocabularyIds != null) {
					qPos.add(vocabularyIds);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_V,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_V,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_V(long groupId, long vocabularyId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_V(groupId, vocabularyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_V_VOCABULARYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(vocabularyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyIds the vocabulary IDs
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_V(long groupId, long[] vocabularyIds)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_V(groupId, vocabularyIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_V_GROUPID_5);

		conjunctionable = true;

		if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < vocabularyIds.length; i++) {
				query.append(_FINDER_COLUMN_G_V_VOCABULARYID_5);

				if ((i + 1) < vocabularyIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (vocabularyIds != null) {
				qPos.add(vocabularyIds);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_V_GROUPID_2 = "assetCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_V_GROUPID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_V_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_G_V_VOCABULARYID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_V_VOCABULARYID_2) + ")";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByP_N",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_N(long parentCategoryId, String name)
		throws SystemException {
		return findByP_N(parentCategoryId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end) throws SystemException {
		return findByP_N(parentCategoryId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_N(long parentCategoryId, String name,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N;
			finderArgs = new Object[] { parentCategoryId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_N;
			finderArgs = new Object[] {
					parentCategoryId, name,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((parentCategoryId != assetCategory.getParentCategoryId()) ||
						!Validator.equals(name, assetCategory.getName())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByP_N_First(long parentCategoryId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_N_First(parentCategoryId, name,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_N_First(long parentCategoryId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByP_N(parentCategoryId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByP_N_Last(long parentCategoryId, String name,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_N_Last(parentCategoryId, name,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_N_Last(long parentCategoryId, String name,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByP_N(parentCategoryId, name);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByP_N(parentCategoryId, name, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByP_N_PrevAndNext(long categoryId,
		long parentCategoryId, String name, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByP_N_PrevAndNext(session, assetCategory,
					parentCategoryId, name, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByP_N_PrevAndNext(session, assetCategory,
					parentCategoryId, name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByP_N_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId, String name,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_P_N_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_P_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_P_N_NAME_2);
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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; and name = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByP_N(long parentCategoryId, String name)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_N(parentCategoryId, name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63; and name = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByP_N(long parentCategoryId, String name)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_P_N;

		Object[] finderArgs = new Object[] { parentCategoryId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_P_N_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_NAME_1 = "assetCategory.name IS NULL";
	private static final String _FINDER_COLUMN_P_N_NAME_2 = "assetCategory.name = ?";
	private static final String _FINDER_COLUMN_P_N_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByP_V",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_V",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId) throws SystemException {
		return findByP_V(parentCategoryId, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end) throws SystemException {
		return findByP_V(parentCategoryId, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByP_V(long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_V;
			finderArgs = new Object[] { parentCategoryId, vocabularyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_P_V;
			finderArgs = new Object[] {
					parentCategoryId, vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((parentCategoryId != assetCategory.getParentCategoryId()) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_V_First(parentCategoryId,
				vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_V_First(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws SystemException {
		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_V_Last(parentCategoryId,
				vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_V_Last(long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByP_V(parentCategoryId, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByP_V(parentCategoryId, vocabularyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByP_V_PrevAndNext(long categoryId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByP_V_PrevAndNext(session, assetCategory,
					parentCategoryId, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByP_V_PrevAndNext(session, assetCategory,
					parentCategoryId, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByP_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentCategoryId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByP_V(parentCategoryId,
				vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByP_V(long parentCategoryId, long vocabularyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_P_V;

		Object[] finderArgs = new Object[] { parentCategoryId, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_P_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_P_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByN_V",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByN_V",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_V",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByN_V(String name, long vocabularyId)
		throws SystemException {
		return findByN_V(name, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end) throws SystemException {
		return findByN_V(name, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByN_V(String name, long vocabularyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_V;
			finderArgs = new Object[] { name, vocabularyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_N_V;
			finderArgs = new Object[] {
					name, vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if (!Validator.equals(name, assetCategory.getName()) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByN_V_First(String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByN_V_First(name, vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByN_V_First(String name, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByN_V(name, vocabularyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByN_V_Last(String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByN_V_Last(name, vocabularyId,
				orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByN_V_Last(String name, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByN_V(name, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByN_V(name, vocabularyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByN_V_PrevAndNext(long categoryId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByN_V_PrevAndNext(session, assetCategory, name,
					vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByN_V_PrevAndNext(session, assetCategory, name,
					vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByN_V_PrevAndNext(Session session,
		AssetCategory assetCategory, String name, long vocabularyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_N_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_N_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_N_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where name = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByN_V(String name, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByN_V(name, vocabularyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where name = &#63; and vocabularyId = &#63;.
	 *
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByN_V(String name, long vocabularyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_N_V;

		Object[] finderArgs = new Object[] { name, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_N_V_NAME_1 = "assetCategory.name IS NULL AND ";
	private static final String _FINDER_COLUMN_N_V_NAME_2 = "assetCategory.name = ? AND ";
	private static final String _FINDER_COLUMN_N_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = '') AND ";
	private static final String _FINDER_COLUMN_N_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			AssetCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_V",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) throws SystemException {
		return findByG_P_V(groupId, parentCategoryId, vocabularyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId, int start, int end) throws SystemException {
		return findByG_P_V(groupId, parentCategoryId, vocabularyId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_V;
			finderArgs = new Object[] { groupId, parentCategoryId, vocabularyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_V;
			finderArgs = new Object[] {
					groupId, parentCategoryId, vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						(parentCategoryId != assetCategory.getParentCategoryId()) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_P_V_First(long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_P_V_First(groupId,
				parentCategoryId, vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_P_V_First(long groupId,
		long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByG_P_V(groupId, parentCategoryId,
				vocabularyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_P_V_Last(long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_P_V_Last(groupId,
				parentCategoryId, vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_P_V_Last(long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P_V(groupId, parentCategoryId, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByG_P_V(groupId, parentCategoryId,
				vocabularyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByG_P_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByG_P_V_PrevAndNext(session, assetCategory, groupId,
					parentCategoryId, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByG_P_V_PrevAndNext(session, assetCategory, groupId,
					parentCategoryId, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByG_P_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId) throws SystemException {
		return filterFindByG_P_V(groupId, parentCategoryId, vocabularyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end)
		throws SystemException {
		return filterFindByG_P_V(groupId, parentCategoryId, vocabularyId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_V(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_V(groupId, parentCategoryId, vocabularyId, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			qPos.add(vocabularyId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] filterFindByG_P_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_V_PrevAndNext(categoryId, groupId,
				parentCategoryId, vocabularyId, orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByG_P_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, vocabularyId, orderByComparator,
					true);

			array[1] = assetCategory;

			array[2] = filterGetByG_P_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, vocabularyId, orderByComparator,
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

	protected AssetCategory filterGetByG_P_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long parentCategoryId,
		long vocabularyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) throws SystemException {
		for (AssetCategory assetCategory : findByG_P_V(groupId,
				parentCategoryId, vocabularyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_V;

		Object[] finderArgs = new Object[] {
				groupId, parentCategoryId, vocabularyId
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

			query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_P_V(long groupId, long parentCategoryId,
		long vocabularyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_V(groupId, parentCategoryId, vocabularyId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2);

		query.append(_FINDER_COLUMN_G_P_V_VOCABULARYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			qPos.add(vocabularyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_P_V_GROUPID_2 = "assetCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN_V =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeN_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN_V =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long vocabularyId) throws SystemException {
		return findByG_LikeN_V(groupId, name, vocabularyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long vocabularyId, int start, int end) throws SystemException {
		return findByG_LikeN_V(groupId, name, vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN_V;
		finderArgs = new Object[] {
				groupId, name, vocabularyId,
				
				start, end, orderByComparator
			};

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						!StringUtil.wildcardMatches(assetCategory.getName(),
							name, CharPool.UNDERLINE, CharPool.PERCENT,
							CharPool.BACK_SLASH, false) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name.toLowerCase());
				}

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_LikeN_V_First(long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_LikeN_V_First(groupId, name,
				vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_LikeN_V_First(long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws SystemException {
		List<AssetCategory> list = findByG_LikeN_V(groupId, name, vocabularyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_LikeN_V_Last(long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_LikeN_V_Last(groupId, name,
				vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_LikeN_V_Last(long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_LikeN_V(groupId, name, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByG_LikeN_V(groupId, name, vocabularyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByG_LikeN_V_PrevAndNext(long categoryId,
		long groupId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByG_LikeN_V_PrevAndNext(session, assetCategory,
					groupId, name, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByG_LikeN_V_PrevAndNext(session, assetCategory,
					groupId, name, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory getByG_LikeN_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name.toLowerCase());
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long vocabularyId) throws SystemException {
		return filterFindByG_LikeN_V(groupId, name, vocabularyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long vocabularyId, int start, int end) throws SystemException {
		return filterFindByG_LikeN_V(groupId, name, vocabularyId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long vocabularyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_V(groupId, name, vocabularyId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name.toLowerCase());
			}

			qPos.add(vocabularyId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] filterFindByG_LikeN_V_PrevAndNext(long categoryId,
		long groupId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_V_PrevAndNext(categoryId, groupId, name,
				vocabularyId, orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByG_LikeN_V_PrevAndNext(session, assetCategory,
					groupId, name, vocabularyId, orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByG_LikeN_V_PrevAndNext(session, assetCategory,
					groupId, name, vocabularyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetCategory filterGetByG_LikeN_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, String name,
		long vocabularyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name.toLowerCase());
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds) throws SystemException {
		return filterFindByG_LikeN_V(groupId, name, vocabularyIds,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds, int start, int end) throws SystemException {
		return filterFindByG_LikeN_V(groupId, name, vocabularyIds, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_V(groupId, name, vocabularyIds, start, end,
				orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_4);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_6);
		}
		else {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_5);
		}

		conjunctionable = true;

		if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < vocabularyIds.length; i++) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_5);

				if ((i + 1) < vocabularyIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (vocabularyIds != null) {
				qPos.add(vocabularyIds);
			}

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds) throws SystemException {
		return findByG_LikeN_V(groupId, name, vocabularyIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds, int start, int end) throws SystemException {
		return findByG_LikeN_V(groupId, name, vocabularyIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if ((vocabularyIds != null) && (vocabularyIds.length == 1)) {
			return findByG_LikeN_V(groupId, name, vocabularyIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, name, StringUtil.merge(vocabularyIds)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, name, StringUtil.merge(vocabularyIds),
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN_V,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						!Validator.equals(name, assetCategory.getName()) ||
						!ArrayUtil.contains(vocabularyIds,
							assetCategory.getVocabularyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_4);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_6);
			}
			else {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_5);
			}

			conjunctionable = true;

			if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < vocabularyIds.length; i++) {
					query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_5);

					if ((i + 1) < vocabularyIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (vocabularyIds != null) {
					qPos.add(vocabularyIds);
				}

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN_V,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_LIKEN_V,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_LikeN_V(long groupId, String name, long vocabularyId)
		throws SystemException {
		for (AssetCategory assetCategory : findByG_LikeN_V(groupId, name,
				vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_LikeN_V(long groupId, String name, long vocabularyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN_V;

		Object[] finderArgs = new Object[] { groupId, name, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name.toLowerCase());
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_LikeN_V(long groupId, String name, long[] vocabularyIds)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, name, StringUtil.merge(vocabularyIds)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			boolean conjunctionable = false;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_5);

			conjunctionable = true;

			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			if (name == null) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_4);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_6);
			}
			else {
				query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_5);
			}

			conjunctionable = true;

			if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < vocabularyIds.length; i++) {
					query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_5);

					if ((i + 1) < vocabularyIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				if (vocabularyIds != null) {
					qPos.add(vocabularyIds);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN_V,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_LIKEN_V,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_LikeN_V(long groupId, String name,
		long vocabularyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN_V(groupId, name, vocabularyId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name.toLowerCase());
			}

			qPos.add(vocabularyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and name LIKE &#63; and vocabularyId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param vocabularyIds the vocabulary IDs
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_LikeN_V(long groupId, String name,
		long[] vocabularyIds) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN_V(groupId, name, vocabularyIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		boolean conjunctionable = false;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_LIKEN_V_GROUPID_5);

		conjunctionable = true;

		if (conjunctionable) {
			query.append(WHERE_AND);
		}

		if (name == null) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_4);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_6);
		}
		else {
			query.append(_FINDER_COLUMN_G_LIKEN_V_NAME_5);
		}

		conjunctionable = true;

		if ((vocabularyIds == null) || (vocabularyIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < vocabularyIds.length; i++) {
				query.append(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_5);

				if ((i + 1) < vocabularyIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (name != null) {
				qPos.add(name);
			}

			if (vocabularyIds != null) {
				qPos.add(vocabularyIds);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LIKEN_V_GROUPID_2 = "assetCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_V_GROUPID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_LIKEN_V_GROUPID_2) + ")";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_1 = "assetCategory.name LIKE NULL AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_2 = "lower(assetCategory.name) LIKE ? AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name LIKE '') AND ";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_4 = "(" +
		removeConjunction(_FINDER_COLUMN_G_LIKEN_V_NAME_1) + ")";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_LIKEN_V_NAME_2) + ")";
	private static final String _FINDER_COLUMN_G_LIKEN_V_NAME_6 = "(" +
		removeConjunction(_FINDER_COLUMN_G_LIKEN_V_NAME_3) + ")";
	private static final String _FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	private static final String _FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_5 = "(" +
		removeConjunction(_FINDER_COLUMN_G_LIKEN_V_VOCABULARYID_2) + ")";
	public static final FinderPath FINDER_PATH_FETCH_BY_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByP_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByP_N_V(parentCategoryId, name,
				vocabularyId);

		if (assetCategory == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("parentCategoryId=");
			msg.append(parentCategoryId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", vocabularyId=");
			msg.append(vocabularyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCategoryException(msg.toString());
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws SystemException {
		return fetchByP_N_V(parentCategoryId, name, vocabularyId, true);
	}

	/**
	 * Returns the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByP_N_V(long parentCategoryId, String name,
		long vocabularyId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { parentCategoryId, name, vocabularyId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_N_V,
					finderArgs, this);
		}

		if (result instanceof AssetCategory) {
			AssetCategory assetCategory = (AssetCategory)result;

			if ((parentCategoryId != assetCategory.getParentCategoryId()) ||
					!Validator.equals(name, assetCategory.getName()) ||
					(vocabularyId != assetCategory.getVocabularyId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_P_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				List<AssetCategory> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
						finderArgs, list);
				}
				else {
					AssetCategory assetCategory = list.get(0);

					result = assetCategory;

					cacheResult(assetCategory);

					if ((assetCategory.getParentCategoryId() != parentCategoryId) ||
							(assetCategory.getName() == null) ||
							!assetCategory.getName().equals(name) ||
							(assetCategory.getVocabularyId() != vocabularyId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
							finderArgs, assetCategory);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V,
					finderArgs);

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
			return (AssetCategory)result;
		}
	}

	/**
	 * Removes the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the asset category that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory removeByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByP_N_V(parentCategoryId, name,
				vocabularyId);

		return remove(assetCategory);
	}

	/**
	 * Returns the number of asset categories where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByP_N_V(long parentCategoryId, String name,
		long vocabularyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_P_N_V;

		Object[] finderArgs = new Object[] { parentCategoryId, name, vocabularyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_P_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_P_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_P_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_P_N_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_1 = "assetCategory.name IS NULL AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_2 = "assetCategory.name = ? AND ";
	private static final String _FINDER_COLUMN_P_N_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = '') AND ";
	private static final String _FINDER_COLUMN_P_N_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_N_V =
		new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED,
			AssetCategoryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			},
			AssetCategoryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetCategoryModelImpl.PARENTCATEGORYID_COLUMN_BITMASK |
			AssetCategoryModelImpl.NAME_COLUMN_BITMASK |
			AssetCategoryModelImpl.VOCABULARYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N_V = new FinderPath(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId)
		throws SystemException {
		return findByG_P_N_V(groupId, parentCategoryId, name, vocabularyId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId, int start,
		int end) throws SystemException {
		return findByG_P_N_V(groupId, parentCategoryId, name, vocabularyId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_N_V;
			finderArgs = new Object[] {
					groupId, parentCategoryId, name, vocabularyId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_N_V;
			finderArgs = new Object[] {
					groupId, parentCategoryId, name, vocabularyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetCategory assetCategory : list) {
				if ((groupId != assetCategory.getGroupId()) ||
						(parentCategoryId != assetCategory.getParentCategoryId()) ||
						!Validator.equals(name, assetCategory.getName()) ||
						(vocabularyId != assetCategory.getVocabularyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_P_N_V_First(long groupId,
		long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_P_N_V_First(groupId,
				parentCategoryId, name, vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the first asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_P_N_V_First(long groupId,
		long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetCategory> list = findByG_P_N_V(groupId, parentCategoryId,
				name, vocabularyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByG_P_N_V_Last(long groupId,
		long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByG_P_N_V_Last(groupId,
				parentCategoryId, name, vocabularyId, orderByComparator);

		if (assetCategory != null) {
			return assetCategory;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentCategoryId=");
		msg.append(parentCategoryId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", vocabularyId=");
		msg.append(vocabularyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCategoryException(msg.toString());
	}

	/**
	 * Returns the last asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset category, or <code>null</code> if a matching asset category could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByG_P_N_V_Last(long groupId,
		long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P_N_V(groupId, parentCategoryId, name, vocabularyId);

		if (count == 0) {
			return null;
		}

		List<AssetCategory> list = findByG_P_N_V(groupId, parentCategoryId,
				name, vocabularyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] findByG_P_N_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = getByG_P_N_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, name, vocabularyId,
					orderByComparator, true);

			array[1] = assetCategory;

			array[2] = getByG_P_N_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, name, vocabularyId,
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

	protected AssetCategory getByG_P_N_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long parentCategoryId,
		String name, long vocabularyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

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
			query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId)
		throws SystemException {
		return filterFindByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId, int start,
		int end) throws SystemException {
		return filterFindByG_P_N_V(groupId, parentCategoryId, name,
			vocabularyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> filterFindByG_P_N_V(long groupId,
		long parentCategoryId, String name, long vocabularyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_N_V(groupId, parentCategoryId, name, vocabularyId,
				start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(vocabularyId);

			return (List<AssetCategory>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset categories before and after the current asset category in the ordered set of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param categoryId the primary key of the current asset category
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory[] filterFindByG_P_N_V_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId, String name, long vocabularyId,
		OrderByComparator orderByComparator)
		throws NoSuchCategoryException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_N_V_PrevAndNext(categoryId, groupId,
				parentCategoryId, name, vocabularyId, orderByComparator);
		}

		AssetCategory assetCategory = findByPrimaryKey(categoryId);

		Session session = null;

		try {
			session = openSession();

			AssetCategory[] array = new AssetCategoryImpl[3];

			array[0] = filterGetByG_P_N_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, name, vocabularyId,
					orderByComparator, true);

			array[1] = assetCategory;

			array[2] = filterGetByG_P_N_V_PrevAndNext(session, assetCategory,
					groupId, parentCategoryId, name, vocabularyId,
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

	protected AssetCategory filterGetByG_P_N_V_PrevAndNext(Session session,
		AssetCategory assetCategory, long groupId, long parentCategoryId,
		String name, long vocabularyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetCategoryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetCategoryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetCategoryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetCategoryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentCategoryId);

		if (bindName) {
			qPos.add(name);
		}

		qPos.add(vocabularyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetCategory);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetCategory> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_P_N_V(long groupId, long parentCategoryId,
		String name, long vocabularyId) throws SystemException {
		for (AssetCategory assetCategory : findByG_P_N_V(groupId,
				parentCategoryId, name, vocabularyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_P_N_V(long groupId, long parentCategoryId, String name,
		long vocabularyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P_N_V;

		Object[] finderArgs = new Object[] {
				groupId, parentCategoryId, name, vocabularyId
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_ASSETCATEGORY_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentCategoryId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(vocabularyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentCategoryId the parent category ID
	 * @param name the name
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset categories that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int filterCountByG_P_N_V(long groupId, long parentCategoryId,
		String name, long vocabularyId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P_N_V(groupId, parentCategoryId, name, vocabularyId);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_ASSETCATEGORY_WHERE);

		query.append(_FINDER_COLUMN_G_P_N_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_P_N_V_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_P_N_V_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_P_N_V_VOCABULARYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetCategory.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentCategoryId);

			if (bindName) {
				qPos.add(name);
			}

			qPos.add(vocabularyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_P_N_V_GROUPID_2 = "assetCategory.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_V_PARENTCATEGORYID_2 = "assetCategory.parentCategoryId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_V_NAME_1 = "assetCategory.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_P_N_V_NAME_2 = "assetCategory.name = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_V_NAME_3 = "(assetCategory.name IS NULL OR assetCategory.name = '') AND ";
	private static final String _FINDER_COLUMN_G_P_N_V_VOCABULARYID_2 = "assetCategory.vocabularyId = ?";

	public AssetCategoryPersistenceImpl() {
		setModelClass(AssetCategory.class);
	}

	/**
	 * Caches the asset category in the entity cache if it is enabled.
	 *
	 * @param assetCategory the asset category
	 */
	@Override
	public void cacheResult(AssetCategory assetCategory) {
		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { assetCategory.getUuid(), assetCategory.getGroupId() },
			assetCategory);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V,
			new Object[] {
				assetCategory.getParentCategoryId(), assetCategory.getName(),
				assetCategory.getVocabularyId()
			}, assetCategory);

		assetCategory.resetOriginalValues();
	}

	/**
	 * Caches the asset categories in the entity cache if it is enabled.
	 *
	 * @param assetCategories the asset categories
	 */
	@Override
	public void cacheResult(List<AssetCategory> assetCategories) {
		for (AssetCategory assetCategory : assetCategories) {
			if (EntityCacheUtil.getResult(
						AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryImpl.class, assetCategory.getPrimaryKey()) == null) {
				cacheResult(assetCategory);
			}
			else {
				assetCategory.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset categories.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset category.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetCategory assetCategory) {
		EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetCategory);
	}

	@Override
	public void clearCache(List<AssetCategory> assetCategories) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetCategory assetCategory : assetCategories) {
			EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryImpl.class, assetCategory.getPrimaryKey());

			clearUniqueFindersCache(assetCategory);
		}
	}

	protected void cacheUniqueFindersCache(AssetCategory assetCategory) {
		if (assetCategory.isNew()) {
			Object[] args = new Object[] {
					assetCategory.getUuid(), assetCategory.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				assetCategory);

			args = new Object[] {
					assetCategory.getParentCategoryId(), assetCategory.getName(),
					assetCategory.getVocabularyId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N_V, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V, args,
				assetCategory);
		}
		else {
			AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategory.getUuid(), assetCategory.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					assetCategory);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_P_N_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategory.getParentCategoryId(),
						assetCategory.getName(), assetCategory.getVocabularyId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_N_V, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_N_V, args,
					assetCategory);
			}
		}
	}

	protected void clearUniqueFindersCache(AssetCategory assetCategory) {
		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		Object[] args = new Object[] {
				assetCategory.getUuid(), assetCategory.getGroupId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((assetCategoryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					assetCategoryModelImpl.getOriginalUuid(),
					assetCategoryModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				assetCategory.getParentCategoryId(), assetCategory.getName(),
				assetCategory.getVocabularyId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N_V, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V, args);

		if ((assetCategoryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_P_N_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					assetCategoryModelImpl.getOriginalParentCategoryId(),
					assetCategoryModelImpl.getOriginalName(),
					assetCategoryModelImpl.getOriginalVocabularyId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N_V, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_N_V, args);
		}
	}

	/**
	 * Creates a new asset category with the primary key. Does not add the asset category to the database.
	 *
	 * @param categoryId the primary key for the new asset category
	 * @return the new asset category
	 */
	@Override
	public AssetCategory create(long categoryId) {
		AssetCategory assetCategory = new AssetCategoryImpl();

		assetCategory.setNew(true);
		assetCategory.setPrimaryKey(categoryId);

		String uuid = PortalUUIDUtil.generate();

		assetCategory.setUuid(uuid);

		return assetCategory;
	}

	/**
	 * Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category that was removed
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory remove(long categoryId)
		throws NoSuchCategoryException, SystemException {
		return remove((Serializable)categoryId);
	}

	/**
	 * Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category that was removed
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory remove(Serializable primaryKey)
		throws NoSuchCategoryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetCategory assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
					primaryKey);

			if (assetCategory == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetCategory);
		}
		catch (NoSuchCategoryException nsee) {
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
	protected AssetCategory removeImpl(AssetCategory assetCategory)
		throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		assetCategoryToAssetEntryTableMapper.deleteLeftPrimaryKeyTableMappings(assetCategory.getPrimaryKey());

		shrinkTree(assetCategory);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetCategory)) {
				assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
						assetCategory.getPrimaryKeyObj());
			}

			if (assetCategory != null) {
				session.delete(assetCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetCategory != null) {
			clearCache(assetCategory);
		}

		return assetCategory;
	}

	@Override
	public AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws SystemException {
		assetCategory = toUnwrappedModel(assetCategory);

		boolean isNew = assetCategory.isNew();

		AssetCategoryModelImpl assetCategoryModelImpl = (AssetCategoryModelImpl)assetCategory;

		if (Validator.isNull(assetCategory.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetCategory.setUuid(uuid);
		}

		if (isNew) {
			expandTree(assetCategory, null);
		}
		else {
			if (assetCategory.getParentCategoryId() != assetCategoryModelImpl.getOriginalParentCategoryId()) {
				List<Long> childrenCategoryIds = getChildrenTreeCategoryIds(assetCategory);

				shrinkTree(assetCategory);
				expandTree(assetCategory, childrenCategoryIds);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetCategory.isNew()) {
				session.save(assetCategory);

				assetCategory.setNew(false);
			}
			else {
				session.merge(assetCategory);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetCategoryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { assetCategoryModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalUuid(),
						assetCategoryModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getUuid(),
						assetCategoryModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { assetCategoryModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTCATEGORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalParentCategoryId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTCATEGORYID,
					args);

				args = new Object[] { assetCategoryModelImpl.getParentCategoryId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTCATEGORYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTCATEGORYID,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VOCABULARYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VOCABULARYID,
					args);

				args = new Object[] { assetCategoryModelImpl.getVocabularyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VOCABULARYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VOCABULARYID,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalGroupId(),
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_V,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getGroupId(),
						assetCategoryModelImpl.getVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_V,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalParentCategoryId(),
						assetCategoryModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getParentCategoryId(),
						assetCategoryModelImpl.getName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_N, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_N,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalParentCategoryId(),
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_V,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getParentCategoryId(),
						assetCategoryModelImpl.getVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_P_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_P_V,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalName(),
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_N_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_V,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getName(),
						assetCategoryModelImpl.getVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_N_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_N_V,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalGroupId(),
						assetCategoryModelImpl.getOriginalParentCategoryId(),
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_V,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getGroupId(),
						assetCategoryModelImpl.getParentCategoryId(),
						assetCategoryModelImpl.getVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_V,
					args);
			}

			if ((assetCategoryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_N_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetCategoryModelImpl.getOriginalGroupId(),
						assetCategoryModelImpl.getOriginalParentCategoryId(),
						assetCategoryModelImpl.getOriginalName(),
						assetCategoryModelImpl.getOriginalVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_N_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_N_V,
					args);

				args = new Object[] {
						assetCategoryModelImpl.getGroupId(),
						assetCategoryModelImpl.getParentCategoryId(),
						assetCategoryModelImpl.getName(),
						assetCategoryModelImpl.getVocabularyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_N_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_N_V,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetCategoryImpl.class, assetCategory.getPrimaryKey(),
			assetCategory);

		clearUniqueFindersCache(assetCategory);
		cacheUniqueFindersCache(assetCategory);

		assetCategory.resetOriginalValues();

		return assetCategory;
	}

	protected AssetCategory toUnwrappedModel(AssetCategory assetCategory) {
		if (assetCategory instanceof AssetCategoryImpl) {
			return assetCategory;
		}

		AssetCategoryImpl assetCategoryImpl = new AssetCategoryImpl();

		assetCategoryImpl.setNew(assetCategory.isNew());
		assetCategoryImpl.setPrimaryKey(assetCategory.getPrimaryKey());

		assetCategoryImpl.setUuid(assetCategory.getUuid());
		assetCategoryImpl.setCategoryId(assetCategory.getCategoryId());
		assetCategoryImpl.setGroupId(assetCategory.getGroupId());
		assetCategoryImpl.setCompanyId(assetCategory.getCompanyId());
		assetCategoryImpl.setUserId(assetCategory.getUserId());
		assetCategoryImpl.setUserName(assetCategory.getUserName());
		assetCategoryImpl.setCreateDate(assetCategory.getCreateDate());
		assetCategoryImpl.setModifiedDate(assetCategory.getModifiedDate());
		assetCategoryImpl.setParentCategoryId(assetCategory.getParentCategoryId());
		assetCategoryImpl.setLeftCategoryId(assetCategory.getLeftCategoryId());
		assetCategoryImpl.setRightCategoryId(assetCategory.getRightCategoryId());
		assetCategoryImpl.setName(assetCategory.getName());
		assetCategoryImpl.setTitle(assetCategory.getTitle());
		assetCategoryImpl.setDescription(assetCategory.getDescription());
		assetCategoryImpl.setVocabularyId(assetCategory.getVocabularyId());

		return assetCategoryImpl;
	}

	/**
	 * Returns the asset category with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCategoryException, SystemException {
		AssetCategory assetCategory = fetchByPrimaryKey(primaryKey);

		if (assetCategory == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCategoryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category
	 * @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException, SystemException {
		return findByPrimaryKey((Serializable)categoryId);
	}

	/**
	 * Returns the asset category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category
	 * @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		AssetCategory assetCategory = (AssetCategory)EntityCacheUtil.getResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
				AssetCategoryImpl.class, primaryKey);

		if (assetCategory == _nullAssetCategory) {
			return null;
		}

		if (assetCategory == null) {
			Session session = null;

			try {
				session = openSession();

				assetCategory = (AssetCategory)session.get(AssetCategoryImpl.class,
						primaryKey);

				if (assetCategory != null) {
					cacheResult(assetCategory);
				}
				else {
					EntityCacheUtil.putResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
						AssetCategoryImpl.class, primaryKey, _nullAssetCategory);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
					AssetCategoryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetCategory;
	}

	/**
	 * Returns the asset category with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryId the primary key of the asset category
	 * @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetCategory fetchByPrimaryKey(long categoryId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)categoryId);
	}

	/**
	 * Returns all the asset categories.
	 *
	 * @return the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset categories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetCategory> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<AssetCategory> list = (List<AssetCategory>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETCATEGORY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETCATEGORY;

				if (pagination) {
					sql = sql.concat(AssetCategoryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetCategory>(list);
				}
				else {
					list = (List<AssetCategory>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset categories from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (AssetCategory assetCategory : findAll()) {
			remove(assetCategory);
		}
	}

	/**
	 * Returns the number of asset categories.
	 *
	 * @return the number of asset categories
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETCATEGORY);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns all the asset entries associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @return the asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws SystemException {
		return getAssetEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset entries associated with the asset category.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the asset category
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @return the range of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) throws SystemException {
		return getAssetEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries associated with the asset category.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the asset category
	 * @param start the lower bound of the range of asset categories
	 * @param end the upper bound of the range of asset categories (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return assetCategoryToAssetEntryTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of asset entries associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @return the number of asset entries associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetEntriesSize(long pk) throws SystemException {
		long[] pks = assetCategoryToAssetEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the asset entry is associated with the asset category.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @return <code>true</code> if the asset entry is associated with the asset category; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		return assetCategoryToAssetEntryTableMapper.containsTableMapping(pk,
			assetEntryPK);
	}

	/**
	 * Returns <code>true</code> if the asset category has any asset entries associated with it.
	 *
	 * @param pk the primary key of the asset category to check for associations with asset entries
	 * @return <code>true</code> if the asset category has any asset entries associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsAssetEntries(long pk) throws SystemException {
		if (getAssetEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		assetCategoryToAssetEntryTableMapper.addTableMapping(pk, assetEntryPK);
	}

	/**
	 * Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntry the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		assetCategoryToAssetEntryTableMapper.addTableMapping(pk,
			assetEntry.getPrimaryKey());
	}

	/**
	 * Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		for (long assetEntryPK : assetEntryPKs) {
			assetCategoryToAssetEntryTableMapper.addTableMapping(pk,
				assetEntryPK);
		}
	}

	/**
	 * Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
			assetCategoryToAssetEntryTableMapper.addTableMapping(pk,
				assetEntry.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the asset category and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category to clear the associated asset entries from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearAssetEntries(long pk) throws SystemException {
		assetCategoryToAssetEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPK the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAssetEntry(long pk, long assetEntryPK)
		throws SystemException {
		assetCategoryToAssetEntryTableMapper.deleteTableMapping(pk, assetEntryPK);
	}

	/**
	 * Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntry the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		assetCategoryToAssetEntryTableMapper.deleteTableMapping(pk,
			assetEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		for (long assetEntryPK : assetEntryPKs) {
			assetCategoryToAssetEntryTableMapper.deleteTableMapping(pk,
				assetEntryPK);
		}
	}

	/**
	 * Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		for (com.liferay.portlet.asset.model.AssetEntry assetEntry : assetEntries) {
			assetCategoryToAssetEntryTableMapper.deleteTableMapping(pk,
				assetEntry.getPrimaryKey());
		}
	}

	/**
	 * Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntryPKs the primary keys of the asset entries to be associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setAssetEntries(long pk, long[] assetEntryPKs)
		throws SystemException {
		Set<Long> newAssetEntryPKsSet = SetUtil.fromArray(assetEntryPKs);
		Set<Long> oldAssetEntryPKsSet = SetUtil.fromArray(assetCategoryToAssetEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeAssetEntryPKsSet = new HashSet<Long>(oldAssetEntryPKsSet);

		removeAssetEntryPKsSet.removeAll(newAssetEntryPKsSet);

		for (long removeAssetEntryPK : removeAssetEntryPKsSet) {
			assetCategoryToAssetEntryTableMapper.deleteTableMapping(pk,
				removeAssetEntryPK);
		}

		newAssetEntryPKsSet.removeAll(oldAssetEntryPKsSet);

		for (long newAssetEntryPK : newAssetEntryPKsSet) {
			assetCategoryToAssetEntryTableMapper.addTableMapping(pk,
				newAssetEntryPK);
		}
	}

	/**
	 * Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset category
	 * @param assetEntries the asset entries to be associated with the asset category
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws SystemException {
		try {
			long[] assetEntryPKs = new long[assetEntries.size()];

			for (int i = 0; i < assetEntries.size(); i++) {
				com.liferay.portlet.asset.model.AssetEntry assetEntry = assetEntries.get(i);

				assetEntryPKs[i] = assetEntry.getPrimaryKey();
			}

			setAssetEntries(pk, assetEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetCategoryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Rebuilds the asset categories tree for the scope using the modified pre-order tree traversal algorithm.
	 *
	 * <p>
	 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	 * </p>
	 *
	 * @param groupId the ID of the scope
	 * @param force whether to force the rebuild even if the tree is not stale
	 */
	@Override
	public void rebuildTree(long groupId, boolean force)
		throws SystemException {
		if (!rebuildTreeEnabled) {
			return;
		}

		if (force || (countOrphanTreeNodes(groupId) > 0)) {
			rebuildTree(groupId, 0, 1);

			if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
				CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
			}

			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
	}

	@Override
	public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
		this.rebuildTreeEnabled = rebuildTreeEnabled;
	}

	protected long countOrphanTreeNodes(long groupId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
					"SELECT COUNT(*) AS COUNT_VALUE FROM AssetCategory WHERE groupId = ? AND (leftCategoryId = 0 OR leftCategoryId IS NULL OR rightCategoryId = 0 OR rightCategoryId IS NULL)");

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void expandNoChildrenLeftCategoryId(long groupId,
		long leftCategoryId, List<Long> childrenCategoryIds, long delta) {
		String sql = "UPDATE AssetCategory SET leftcategoryId = (leftcategoryId + ?) WHERE (groupId = ?) AND (leftcategoryId > ?) AND (categoryId NOT IN (" +
			StringUtil.merge(childrenCategoryIds) + "))";

		SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
				sql,
				new int[] {
					java.sql.Types.BIGINT, java.sql.Types.BIGINT,
					java.sql.Types.BIGINT
				});

		_sqlUpdate.update(new Object[] { delta, groupId, leftCategoryId });
	}

	protected void expandNoChildrenRightCategoryId(long groupId,
		long rightCategoryId, List<Long> childrenCategoryIds, long delta) {
		String sql = "UPDATE AssetCategory SET rightcategoryId = (rightcategoryId + ?) WHERE (groupId = ?) AND (rightcategoryId > ?) AND (categoryId NOT IN (" +
			StringUtil.merge(childrenCategoryIds) + "))";

		SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
				sql,
				new int[] {
					java.sql.Types.BIGINT, java.sql.Types.BIGINT,
					java.sql.Types.BIGINT
				});

		_sqlUpdate.update(new Object[] { delta, groupId, rightCategoryId });
	}

	protected void expandTree(AssetCategory assetCategory,
		List<Long> childrenCategoryIds) throws SystemException {
		if (!rebuildTreeEnabled) {
			return;
		}

		long groupId = assetCategory.getGroupId();

		long lastRightCategoryId = getLastRightCategoryId(groupId,
				assetCategory.getParentCategoryId());

		long leftCategoryId = 2;
		long rightCategoryId = 3;

		if (lastRightCategoryId > 0) {
			leftCategoryId = lastRightCategoryId + 1;

			long childrenDistance = assetCategory.getRightCategoryId() -
				assetCategory.getLeftCategoryId();

			if (childrenDistance > 1) {
				rightCategoryId = leftCategoryId + childrenDistance;

				updateChildrenTree(groupId, childrenCategoryIds,
					leftCategoryId - assetCategory.getLeftCategoryId());

				expandNoChildrenLeftCategoryId(groupId, lastRightCategoryId,
					childrenCategoryIds, childrenDistance + 1);
				expandNoChildrenRightCategoryId(groupId, lastRightCategoryId,
					childrenCategoryIds, childrenDistance + 1);
			}
			else {
				rightCategoryId = lastRightCategoryId + 2;

				expandTreeLeftCategoryId.expand(groupId, lastRightCategoryId);
				expandTreeRightCategoryId.expand(groupId, lastRightCategoryId);
			}

			if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
				CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
			}

			EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		assetCategory.setLeftCategoryId(leftCategoryId);
		assetCategory.setRightCategoryId(rightCategoryId);
	}

	protected List<Long> getChildrenTreeCategoryIds(
		AssetCategory parentAssetCategory) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
					"SELECT categoryId FROM AssetCategory WHERE (groupId = ?) AND (leftcategoryId BETWEEN ? AND ?)");

			q.addScalar("CategoryId",
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentAssetCategory.getGroupId());
			qPos.add(parentAssetCategory.getLeftCategoryId() + 1);
			qPos.add(parentAssetCategory.getRightCategoryId());

			return q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long getLastRightCategoryId(long groupId, long parentCategoryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
					"SELECT rightCategoryId FROM AssetCategory WHERE (groupId = ?) AND (parentCategoryId = ?) ORDER BY rightCategoryId DESC");

			q.addScalar("rightCategoryId",
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			List<Long> list = (List<Long>)QueryUtil.list(q, getDialect(), 0, 1);

			if (list.isEmpty()) {
				if (parentCategoryId > 0) {
					session.clear();

					AssetCategory parentAssetCategory = findByPrimaryKey(parentCategoryId);

					return parentAssetCategory.getLeftCategoryId();
				}

				return 0;
			}
			else {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected long rebuildTree(long groupId, long parentCategoryId,
		long leftCategoryId) throws SystemException {
		if (!rebuildTreeEnabled) {
			return 0;
		}

		List<Long> categoryIds = null;

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
					"SELECT categoryId FROM AssetCategory WHERE groupId = ? AND parentCategoryId = ? ORDER BY categoryId ASC");

			q.addScalar("categoryId",
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(parentCategoryId);

			categoryIds = q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		long rightCategoryId = leftCategoryId + 1;

		for (long categoryId : categoryIds) {
			rightCategoryId = rebuildTree(groupId, categoryId, rightCategoryId);
		}

		if (parentCategoryId > 0) {
			updateTree.update(parentCategoryId, leftCategoryId, rightCategoryId);
		}

		return rightCategoryId + 1;
	}

	protected void shrinkTree(AssetCategory assetCategory) {
		if (!rebuildTreeEnabled) {
			return;
		}

		long groupId = assetCategory.getGroupId();

		long leftCategoryId = assetCategory.getLeftCategoryId();
		long rightCategoryId = assetCategory.getRightCategoryId();

		long delta = (rightCategoryId - leftCategoryId) + 1;

		shrinkTreeLeftCategoryId.shrink(groupId, rightCategoryId, delta);
		shrinkTreeRightCategoryId.shrink(groupId, rightCategoryId, delta);

		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetCategoryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	protected void updateChildrenTree(long groupId,
		List<Long> childrenCategoryIds, long delta) {
		String sql = "UPDATE AssetCategory SET leftcategoryId = (leftcategoryId + ?), rightcategoryId = (rightcategoryId + ?) WHERE (groupId = ?) AND (categoryId IN (" +
			StringUtil.merge(childrenCategoryIds) + "))";

		SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
				sql,
				new int[] {
					java.sql.Types.BIGINT, java.sql.Types.BIGINT,
					java.sql.Types.BIGINT
				});

		_sqlUpdate.update(new Object[] { delta, delta, groupId });
	}

	/**
	 * Initializes the asset category persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetCategory")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetCategory>> listenersList = new ArrayList<ModelListener<AssetCategory>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetCategory>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		assetCategoryToAssetEntryTableMapper = TableMapperFactory.getTableMapper("AssetEntries_AssetCategories",
				"categoryId", "entryId", this, assetEntryPersistence);

		expandTreeLeftCategoryId = new ExpandTreeLeftCategoryId();
		expandTreeRightCategoryId = new ExpandTreeRightCategoryId();
		shrinkTreeLeftCategoryId = new ShrinkTreeLeftCategoryId();
		shrinkTreeRightCategoryId = new ShrinkTreeRightCategoryId();
		updateTree = new UpdateTree();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetCategoryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	protected TableMapper<AssetCategory, com.liferay.portlet.asset.model.AssetEntry> assetCategoryToAssetEntryTableMapper;
	protected boolean rebuildTreeEnabled = true;
	protected ExpandTreeLeftCategoryId expandTreeLeftCategoryId;
	protected ExpandTreeRightCategoryId expandTreeRightCategoryId;
	protected ShrinkTreeLeftCategoryId shrinkTreeLeftCategoryId;
	protected ShrinkTreeRightCategoryId shrinkTreeRightCategoryId;
	protected UpdateTree updateTree;

	protected class ExpandTreeLeftCategoryId {
		protected ExpandTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId + 2) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void expand(long groupId, long leftCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, leftCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ExpandTreeRightCategoryId {
		protected ExpandTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId + 2) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void expand(long groupId, long rightCategoryId) {
			_sqlUpdate.update(new Object[] { groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeLeftCategoryId {
		protected ShrinkTreeLeftCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = (leftCategoryId - ?) WHERE (groupId = ?) AND (leftCategoryId > ?)",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void shrink(long groupId, long rightCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ShrinkTreeRightCategoryId {
		protected ShrinkTreeRightCategoryId() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET rightCategoryId = (rightCategoryId - ?) WHERE (groupId = ?) AND (rightCategoryId > ?)",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void shrink(long groupId, long rightCategoryId, long delta) {
			_sqlUpdate.update(new Object[] { delta, groupId, rightCategoryId });
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class UpdateTree {
		protected UpdateTree() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"UPDATE AssetCategory SET leftCategoryId = ?, rightCategoryId = ? WHERE categoryId = ?",
					new int[] {
						java.sql.Types.BIGINT, java.sql.Types.BIGINT,
						java.sql.Types.BIGINT
					});
		}

		protected void update(long categoryId, long leftCategoryId,
			long rightCategoryId) {
			_sqlUpdate.update(new Object[] {
					leftCategoryId, rightCategoryId, categoryId
				});
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_ASSETCATEGORY = "SELECT assetCategory FROM AssetCategory assetCategory";
	private static final String _SQL_SELECT_ASSETCATEGORY_WHERE = "SELECT assetCategory FROM AssetCategory assetCategory WHERE ";
	private static final String _SQL_COUNT_ASSETCATEGORY = "SELECT COUNT(assetCategory) FROM AssetCategory assetCategory";
	private static final String _SQL_COUNT_ASSETCATEGORY_WHERE = "SELECT COUNT(assetCategory) FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "assetCategory.categoryId";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_WHERE = "SELECT DISTINCT {assetCategory.*} FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AssetCategory.*} FROM (SELECT DISTINCT assetCategory.categoryId FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETCATEGORY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AssetCategory ON TEMP_TABLE.categoryId = AssetCategory.categoryId";
	private static final String _FILTER_SQL_COUNT_ASSETCATEGORY_WHERE = "SELECT COUNT(DISTINCT assetCategory.categoryId) AS COUNT_VALUE FROM AssetCategory assetCategory WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "assetCategory";
	private static final String _FILTER_ENTITY_TABLE = "AssetCategory";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetCategory.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AssetCategory.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetCategory exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetCategory exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static AssetCategory _nullAssetCategory = new AssetCategoryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetCategory> toCacheModel() {
				return _nullAssetCategoryCacheModel;
			}
		};

	private static CacheModel<AssetCategory> _nullAssetCategoryCacheModel = new CacheModel<AssetCategory>() {
			@Override
			public AssetCategory toEntityModel() {
				return _nullAssetCategory;
			}
		};
}