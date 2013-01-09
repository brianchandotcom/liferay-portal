/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyImpl;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the asset tag property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPropertyPersistence
 * @see AssetTagPropertyUtil
 * @generated
 */
public class AssetTagPropertyPersistenceImpl extends BasePersistenceImpl<AssetTagProperty>
	implements AssetTagPropertyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetTagPropertyUtil} to access the asset tag property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetTagPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AssetTagPropertyModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetTagPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset tag properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset tag properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @return the range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetTagProperty assetTagProperty : list) {
				if ((companyId != assetTagProperty.getCompanyId())) {
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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetTagProperty>(list);
				}
				else {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first asset tag property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset tag property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetTagProperty> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset tag property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<AssetTagProperty> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag properties before and after the current asset tag property in the ordered set where companyId = &#63;.
	 *
	 * @param tagPropertyId the primary key of the current asset tag property
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty[] findByCompanyId_PrevAndNext(long tagPropertyId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, assetTagProperty,
					companyId, orderByComparator, true);

			array[1] = assetTagProperty;

			array[2] = getByCompanyId_PrevAndNext(session, assetTagProperty,
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

	protected AssetTagProperty getByCompanyId_PrevAndNext(Session session,
		AssetTagProperty assetTagProperty, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

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
			query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetTagProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetTagProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag properties where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetTagProperty assetTagProperty : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetTagProperty);
		}
	}

	/**
	 * Returns the number of asset tag properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetTagProperty.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByTagId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTagId",
			new String[] { Long.class.getName() },
			AssetTagPropertyModelImpl.TAGID_COLUMN_BITMASK |
			AssetTagPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTagId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset tag properties where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByTagId(long tagId)
		throws SystemException {
		return findByTagId(tagId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag properties where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @return the range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByTagId(long tagId, int start, int end)
		throws SystemException {
		return findByTagId(tagId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag properties where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByTagId(long tagId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TAGID;
			finderArgs = new Object[] { tagId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TAGID;
			finderArgs = new Object[] { tagId, start, end, orderByComparator };
		}

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetTagProperty assetTagProperty : list) {
				if ((tagId != assetTagProperty.getTagId())) {
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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_TAGID_TAGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				if (!pagination) {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetTagProperty>(list);
				}
				else {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first asset tag property in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByTagId_First(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByTagId_First(tagId,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tagId=");
		msg.append(tagId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset tag property in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByTagId_First(long tagId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetTagProperty> list = findByTagId(tagId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag property in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByTagId_Last(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByTagId_Last(tagId,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tagId=");
		msg.append(tagId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset tag property in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByTagId_Last(long tagId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByTagId(tagId);

		List<AssetTagProperty> list = findByTagId(tagId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag properties before and after the current asset tag property in the ordered set where tagId = &#63;.
	 *
	 * @param tagPropertyId the primary key of the current asset tag property
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty[] findByTagId_PrevAndNext(long tagPropertyId,
		long tagId, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = getByTagId_PrevAndNext(session, assetTagProperty, tagId,
					orderByComparator, true);

			array[1] = assetTagProperty;

			array[2] = getByTagId_PrevAndNext(session, assetTagProperty, tagId,
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

	protected AssetTagProperty getByTagId_PrevAndNext(Session session,
		AssetTagProperty assetTagProperty, long tagId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

		query.append(_FINDER_COLUMN_TAGID_TAGID_2);

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
			query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tagId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetTagProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetTagProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag properties where tagId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTagId(long tagId) throws SystemException {
		for (AssetTagProperty assetTagProperty : findByTagId(tagId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetTagProperty);
		}
	}

	/**
	 * Returns the number of asset tag properties where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the number of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTagId(long tagId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TAGID;

		Object[] finderArgs = new Object[] { tagId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_TAGID_TAGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

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

	private static final String _FINDER_COLUMN_TAGID_TAGID_2 = "assetTagProperty.tagId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetTagPropertyModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetTagPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the asset tag properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByC_K(long companyId, String key)
		throws SystemException {
		return findByC_K(companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset tag properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @return the range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByC_K(long companyId, String key,
		int start, int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findByC_K(long companyId, String key,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K;
			finderArgs = new Object[] { companyId, key };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_K;
			finderArgs = new Object[] {
					companyId, key,
					
					start, end, orderByComparator
				};
		}

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetTagProperty assetTagProperty : list) {
				if ((companyId != assetTagProperty.getCompanyId()) ||
						!Validator.equals(key, assetTagProperty.getKey())) {
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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				if (!pagination) {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetTagProperty>(list);
				}
				else {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByC_K_First(companyId, key,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the first asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetTagProperty> list = findByC_K(companyId, key, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByC_K_Last(companyId, key,
				orderByComparator);

		if (assetTagProperty != null) {
			return assetTagProperty;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTagPropertyException(msg.toString());
	}

	/**
	 * Returns the last asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_K(companyId, key);

		List<AssetTagProperty> list = findByC_K(companyId, key, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset tag properties before and after the current asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param tagPropertyId the primary key of the current asset tag property
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty[] findByC_K_PrevAndNext(long tagPropertyId,
		long companyId, String key, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		Session session = null;

		try {
			session = openSession();

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = getByC_K_PrevAndNext(session, assetTagProperty,
					companyId, key, orderByComparator, true);

			array[1] = assetTagProperty;

			array[2] = getByC_K_PrevAndNext(session, assetTagProperty,
					companyId, key, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetTagProperty getByC_K_PrevAndNext(Session session,
		AssetTagProperty assetTagProperty, long companyId, String key,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

		query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

		if (key == null) {
			query.append(_FINDER_COLUMN_C_K_KEY_1);
		}
		else {
			if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_K_KEY_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_K_KEY_2);
			}
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
			query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (key != null) {
			qPos.add(key);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetTagProperty);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetTagProperty> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset tag properties where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (AssetTagProperty assetTagProperty : findByC_K(companyId, key,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetTagProperty);
		}
	}

	/**
	 * Returns the number of asset tag properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_K(long companyId, String key) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_K;

		Object[] finderArgs = new Object[] { companyId, key };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 = "assetTagProperty.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "assetTagProperty.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "assetTagProperty.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(assetTagProperty.key IS NULL OR assetTagProperty.key = ?)";
	public static final FinderPath FINDER_PATH_FETCH_BY_T_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			AssetTagPropertyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByT_K",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetTagPropertyModelImpl.TAGID_COLUMN_BITMASK |
			AssetTagPropertyModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_K",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the asset tag property where tagId = &#63; and key = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagPropertyException} if it could not be found.
	 *
	 * @param tagId the tag ID
	 * @param key the key
	 * @return the matching asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByT_K(long tagId, String key)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByT_K(tagId, key);

		if (assetTagProperty == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTagPropertyException(msg.toString());
		}

		return assetTagProperty;
	}

	/**
	 * Returns the asset tag property where tagId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tagId the tag ID
	 * @param key the key
	 * @return the matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByT_K(long tagId, String key)
		throws SystemException {
		return fetchByT_K(tagId, key, true);
	}

	/**
	 * Returns the asset tag property where tagId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tagId the tag ID
	 * @param key the key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByT_K(long tagId, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { tagId, key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_K,
					finderArgs, this);
		}

		if (result instanceof AssetTagProperty) {
			AssetTagProperty assetTagProperty = (AssetTagProperty)result;

			if ((tagId != assetTagProperty.getTagId()) ||
					!Validator.equals(key, assetTagProperty.getKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_T_K_TAGID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_T_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				if (key != null) {
					qPos.add(key);
				}

				List<AssetTagProperty> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
						finderArgs, list);
				}
				else {
					AssetTagProperty assetTagProperty = list.get(0);

					result = assetTagProperty;

					cacheResult(assetTagProperty);

					if ((assetTagProperty.getTagId() != tagId) ||
							(assetTagProperty.getKey() == null) ||
							!assetTagProperty.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
							finderArgs, assetTagProperty);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_K,
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
			return (AssetTagProperty)result;
		}
	}

	/**
	 * Removes the asset tag property where tagId = &#63; and key = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 * @param key the key
	 * @return the asset tag property that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty removeByT_K(long tagId, String key)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByT_K(tagId, key);

		return remove(assetTagProperty);
	}

	/**
	 * Returns the number of asset tag properties where tagId = &#63; and key = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param key the key
	 * @return the number of matching asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_K(long tagId, String key) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_K;

		Object[] finderArgs = new Object[] { tagId, key };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_T_K_TAGID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_T_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_K_KEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				if (key != null) {
					qPos.add(key);
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

	private static final String _FINDER_COLUMN_T_K_TAGID_2 = "assetTagProperty.tagId = ? AND ";
	private static final String _FINDER_COLUMN_T_K_KEY_1 = "assetTagProperty.key IS NULL";
	private static final String _FINDER_COLUMN_T_K_KEY_2 = "assetTagProperty.key = ?";
	private static final String _FINDER_COLUMN_T_K_KEY_3 = "(assetTagProperty.key IS NULL OR assetTagProperty.key = ?)";

	/**
	 * Caches the asset tag property in the entity cache if it is enabled.
	 *
	 * @param assetTagProperty the asset tag property
	 */
	public void cacheResult(AssetTagProperty assetTagProperty) {
		EntityCacheUtil.putResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey(),
			assetTagProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
			new Object[] {
				Long.valueOf(assetTagProperty.getTagId()),
				
			assetTagProperty.getKey()
			}, assetTagProperty);

		assetTagProperty.resetOriginalValues();
	}

	/**
	 * Caches the asset tag properties in the entity cache if it is enabled.
	 *
	 * @param assetTagProperties the asset tag properties
	 */
	public void cacheResult(List<AssetTagProperty> assetTagProperties) {
		for (AssetTagProperty assetTagProperty : assetTagProperties) {
			if (EntityCacheUtil.getResult(
						AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagPropertyImpl.class,
						assetTagProperty.getPrimaryKey()) == null) {
				cacheResult(assetTagProperty);
			}
			else {
				assetTagProperty.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset tag properties.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetTagPropertyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetTagPropertyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset tag property.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetTagProperty assetTagProperty) {
		EntityCacheUtil.removeResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetTagProperty);
	}

	@Override
	public void clearCache(List<AssetTagProperty> assetTagProperties) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetTagProperty assetTagProperty : assetTagProperties) {
			EntityCacheUtil.removeResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey());

			clearUniqueFindersCache(assetTagProperty);
		}
	}

	protected void cacheUniqueFindersCache(AssetTagProperty assetTagProperty) {
		if (assetTagProperty.isNew()) {
			Object[] args = new Object[] {
					Long.valueOf(assetTagProperty.getTagId()),
					
					assetTagProperty.getKey()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_K, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K, args,
				assetTagProperty);
		}
		else {
			AssetTagPropertyModelImpl assetTagPropertyModelImpl = (AssetTagPropertyModelImpl)assetTagProperty;

			if ((assetTagPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_T_K.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetTagProperty.getTagId()),
						
						assetTagProperty.getKey()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_K, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K, args,
					assetTagProperty);
			}
		}
	}

	protected void clearUniqueFindersCache(AssetTagProperty assetTagProperty) {
		AssetTagPropertyModelImpl assetTagPropertyModelImpl = (AssetTagPropertyModelImpl)assetTagProperty;

		Object[] args = new Object[] {
				Long.valueOf(assetTagProperty.getTagId()),
				
				assetTagProperty.getKey()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_K, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_K, args);

		if ((assetTagPropertyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_T_K.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(assetTagPropertyModelImpl.getOriginalTagId()),
					
					assetTagPropertyModelImpl.getOriginalKey()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_K, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_K, args);
		}
	}

	/**
	 * Creates a new asset tag property with the primary key. Does not add the asset tag property to the database.
	 *
	 * @param tagPropertyId the primary key for the new asset tag property
	 * @return the new asset tag property
	 */
	public AssetTagProperty create(long tagPropertyId) {
		AssetTagProperty assetTagProperty = new AssetTagPropertyImpl();

		assetTagProperty.setNew(true);
		assetTagProperty.setPrimaryKey(tagPropertyId);

		return assetTagProperty;
	}

	/**
	 * Removes the asset tag property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tagPropertyId the primary key of the asset tag property
	 * @return the asset tag property that was removed
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty remove(long tagPropertyId)
		throws NoSuchTagPropertyException, SystemException {
		return remove(Long.valueOf(tagPropertyId));
	}

	/**
	 * Removes the asset tag property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset tag property
	 * @return the asset tag property that was removed
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagProperty remove(Serializable primaryKey)
		throws NoSuchTagPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetTagProperty assetTagProperty = (AssetTagProperty)session.get(AssetTagPropertyImpl.class,
					primaryKey);

			if (assetTagProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTagPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetTagProperty);
		}
		catch (NoSuchTagPropertyException nsee) {
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
	protected AssetTagProperty removeImpl(AssetTagProperty assetTagProperty)
		throws SystemException {
		assetTagProperty = toUnwrappedModel(assetTagProperty);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetTagProperty)) {
				assetTagProperty = (AssetTagProperty)session.get(AssetTagPropertyImpl.class,
						assetTagProperty.getPrimaryKeyObj());
			}

			if (assetTagProperty != null) {
				session.delete(assetTagProperty);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetTagProperty != null) {
			clearCache(assetTagProperty);
		}

		return assetTagProperty;
	}

	@Override
	public AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws SystemException {
		assetTagProperty = toUnwrappedModel(assetTagProperty);

		boolean isNew = assetTagProperty.isNew();

		AssetTagPropertyModelImpl assetTagPropertyModelImpl = (AssetTagPropertyModelImpl)assetTagProperty;

		Session session = null;

		try {
			session = openSession();

			if (assetTagProperty.isNew()) {
				session.save(assetTagProperty);

				assetTagProperty.setNew(false);
			}
			else {
				session.merge(assetTagProperty);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetTagPropertyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetTagPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((assetTagPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TAGID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getOriginalTagId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TAGID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TAGID,
					args);

				args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getTagId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TAGID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TAGID,
					args);
			}

			if ((assetTagPropertyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getOriginalCompanyId()),
						
						assetTagPropertyModelImpl.getOriginalKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K,
					args);

				args = new Object[] {
						Long.valueOf(assetTagPropertyModelImpl.getCompanyId()),
						
						assetTagPropertyModelImpl.getKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_K, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_K,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey(),
			assetTagProperty);

		clearUniqueFindersCache(assetTagProperty);
		cacheUniqueFindersCache(assetTagProperty);

		return assetTagProperty;
	}

	protected AssetTagProperty toUnwrappedModel(
		AssetTagProperty assetTagProperty) {
		if (assetTagProperty instanceof AssetTagPropertyImpl) {
			return assetTagProperty;
		}

		AssetTagPropertyImpl assetTagPropertyImpl = new AssetTagPropertyImpl();

		assetTagPropertyImpl.setNew(assetTagProperty.isNew());
		assetTagPropertyImpl.setPrimaryKey(assetTagProperty.getPrimaryKey());

		assetTagPropertyImpl.setTagPropertyId(assetTagProperty.getTagPropertyId());
		assetTagPropertyImpl.setCompanyId(assetTagProperty.getCompanyId());
		assetTagPropertyImpl.setUserId(assetTagProperty.getUserId());
		assetTagPropertyImpl.setUserName(assetTagProperty.getUserName());
		assetTagPropertyImpl.setCreateDate(assetTagProperty.getCreateDate());
		assetTagPropertyImpl.setModifiedDate(assetTagProperty.getModifiedDate());
		assetTagPropertyImpl.setTagId(assetTagProperty.getTagId());
		assetTagPropertyImpl.setKey(assetTagProperty.getKey());
		assetTagPropertyImpl.setValue(assetTagProperty.getValue());

		return assetTagPropertyImpl;
	}

	/**
	 * Returns the asset tag property with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag property
	 * @return the asset tag property
	 * @throws com.liferay.portal.NoSuchModelException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset tag property with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagPropertyException} if it could not be found.
	 *
	 * @param tagPropertyId the primary key of the asset tag property
	 * @return the asset tag property
	 * @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty findByPrimaryKey(long tagPropertyId)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByPrimaryKey(tagPropertyId);

		if (assetTagProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagPropertyId);
			}

			throw new NoSuchTagPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				tagPropertyId);
		}

		return assetTagProperty;
	}

	/**
	 * Returns the asset tag property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset tag property
	 * @return the asset tag property, or <code>null</code> if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagProperty fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset tag property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param tagPropertyId the primary key of the asset tag property
	 * @return the asset tag property, or <code>null</code> if a asset tag property with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetTagProperty fetchByPrimaryKey(long tagPropertyId)
		throws SystemException {
		AssetTagProperty assetTagProperty = (AssetTagProperty)EntityCacheUtil.getResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagPropertyImpl.class, tagPropertyId);

		if (assetTagProperty == _nullAssetTagProperty) {
			return null;
		}

		if (assetTagProperty == null) {
			Session session = null;

			try {
				session = openSession();

				assetTagProperty = (AssetTagProperty)session.get(AssetTagPropertyImpl.class,
						Long.valueOf(tagPropertyId));

				if (assetTagProperty != null) {
					cacheResult(assetTagProperty);
				}
				else {
					EntityCacheUtil.putResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagPropertyImpl.class, tagPropertyId,
						_nullAssetTagProperty);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
					AssetTagPropertyImpl.class, tagPropertyId);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetTagProperty;
	}

	/**
	 * Returns all the asset tag properties.
	 *
	 * @return the asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset tag properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @return the range of asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset tag properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag properties
	 * @param end the upper bound of the range of asset tag properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetTagProperty> findAll(int start, int end,
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

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETTAGPROPERTY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETTAGPROPERTY;

				if (pagination) {
					sql = sql.concat(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<AssetTagProperty>(list);
				}
				else {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the asset tag properties from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetTagProperty assetTagProperty : findAll()) {
			remove(assetTagProperty);
		}
	}

	/**
	 * Returns the number of asset tag properties.
	 *
	 * @return the number of asset tag properties
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETTAGPROPERTY);

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
	 * Initializes the asset tag property persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetTagProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetTagProperty>> listenersList = new ArrayList<ModelListener<AssetTagProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetTagProperty>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetTagPropertyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_ASSETTAGPROPERTY = "SELECT assetTagProperty FROM AssetTagProperty assetTagProperty";
	private static final String _SQL_SELECT_ASSETTAGPROPERTY_WHERE = "SELECT assetTagProperty FROM AssetTagProperty assetTagProperty WHERE ";
	private static final String _SQL_COUNT_ASSETTAGPROPERTY = "SELECT COUNT(assetTagProperty) FROM AssetTagProperty assetTagProperty";
	private static final String _SQL_COUNT_ASSETTAGPROPERTY_WHERE = "SELECT COUNT(assetTagProperty) FROM AssetTagProperty assetTagProperty WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTagProperty.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetTagProperty exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetTagProperty exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetTagPropertyPersistenceImpl.class);
	private static AssetTagProperty _nullAssetTagProperty = new AssetTagPropertyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetTagProperty> toCacheModel() {
				return _nullAssetTagPropertyCacheModel;
			}
		};

	private static CacheModel<AssetTagProperty> _nullAssetTagPropertyCacheModel = new CacheModel<AssetTagProperty>() {
			public AssetTagProperty toEntityModel() {
				return _nullAssetTagProperty;
			}
		};
}