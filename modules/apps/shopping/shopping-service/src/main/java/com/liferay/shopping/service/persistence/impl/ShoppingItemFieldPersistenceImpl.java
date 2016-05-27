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

package com.liferay.shopping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.shopping.exception.NoSuchItemFieldException;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.impl.ShoppingItemFieldImpl;
import com.liferay.shopping.model.impl.ShoppingItemFieldModelImpl;
import com.liferay.shopping.service.persistence.ShoppingItemFieldPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the shopping item field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemFieldPersistence
 * @see com.liferay.shopping.service.persistence.ShoppingItemFieldUtil
 * @generated
 */
@ProviderType
public class ShoppingItemFieldPersistenceImpl extends BasePersistenceImpl<ShoppingItemField>
	implements ShoppingItemFieldPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingItemFieldUtil} to access the shopping item field persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingItemFieldImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID =
		new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByItemId",
			new String[] { Long.class.getName() },
			ShoppingItemFieldModelImpl.ITEMID_COLUMN_BITMASK |
			ShoppingItemFieldModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByItemId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the shopping item fields where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the matching shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findByItemId(long itemId) {
		return findByItemId(itemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item fields where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @return the range of matching shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findByItemId(long itemId, int start, int end) {
		return findByItemId(itemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item fields where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemField> orderByComparator) {
		return findByItemId(itemId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping item fields where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator<ShoppingItemField> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId, start, end, orderByComparator };
		}

		List<ShoppingItemField> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItemField>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ShoppingItemField shoppingItemField : list) {
					if ((itemId != shoppingItemField.getItemId())) {
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

			query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				if (!pagination) {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
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
	 * Returns the first shopping item field in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item field
	 * @throws NoSuchItemFieldException if a matching shopping item field could not be found
	 */
	@Override
	public ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException {
		ShoppingItemField shoppingItemField = fetchByItemId_First(itemId,
				orderByComparator);

		if (shoppingItemField != null) {
			return shoppingItemField;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemFieldException(msg.toString());
	}

	/**
	 * Returns the first shopping item field in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	 */
	@Override
	public ShoppingItemField fetchByItemId_First(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		List<ShoppingItemField> list = findByItemId(itemId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping item field in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item field
	 * @throws NoSuchItemFieldException if a matching shopping item field could not be found
	 */
	@Override
	public ShoppingItemField findByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException {
		ShoppingItemField shoppingItemField = fetchByItemId_Last(itemId,
				orderByComparator);

		if (shoppingItemField != null) {
			return shoppingItemField;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("itemId=");
		msg.append(itemId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchItemFieldException(msg.toString());
	}

	/**
	 * Returns the last shopping item field in the ordered set where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping item field, or <code>null</code> if a matching shopping item field could not be found
	 */
	@Override
	public ShoppingItemField fetchByItemId_Last(long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		int count = countByItemId(itemId);

		if (count == 0) {
			return null;
		}

		List<ShoppingItemField> list = findByItemId(itemId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping item fields before and after the current shopping item field in the ordered set where itemId = &#63;.
	 *
	 * @param itemFieldId the primary key of the current shopping item field
	 * @param itemId the item ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping item field
	 * @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField[] findByItemId_PrevAndNext(long itemFieldId,
		long itemId, OrderByComparator<ShoppingItemField> orderByComparator)
		throws NoSuchItemFieldException {
		ShoppingItemField shoppingItemField = findByPrimaryKey(itemFieldId);

		Session session = null;

		try {
			session = openSession();

			ShoppingItemField[] array = new ShoppingItemFieldImpl[3];

			array[0] = getByItemId_PrevAndNext(session, shoppingItemField,
					itemId, orderByComparator, true);

			array[1] = shoppingItemField;

			array[2] = getByItemId_PrevAndNext(session, shoppingItemField,
					itemId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingItemField getByItemId_PrevAndNext(Session session,
		ShoppingItemField shoppingItemField, long itemId,
		OrderByComparator<ShoppingItemField> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE);

		query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

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
			query.append(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(itemId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingItemField);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingItemField> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the shopping item fields where itemId = &#63; from the database.
	 *
	 * @param itemId the item ID
	 */
	@Override
	public void removeByItemId(long itemId) {
		for (ShoppingItemField shoppingItemField : findByItemId(itemId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(shoppingItemField);
		}
	}

	/**
	 * Returns the number of shopping item fields where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the number of matching shopping item fields
	 */
	@Override
	public int countByItemId(long itemId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ITEMID;

		Object[] finderArgs = new Object[] { itemId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGITEMFIELD_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

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

	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "shoppingItemField.itemId = ?";

	public ShoppingItemFieldPersistenceImpl() {
		setModelClass(ShoppingItemField.class);
	}

	/**
	 * Caches the shopping item field in the entity cache if it is enabled.
	 *
	 * @param shoppingItemField the shopping item field
	 */
	@Override
	public void cacheResult(ShoppingItemField shoppingItemField) {
		entityCache.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField);

		shoppingItemField.resetOriginalValues();
	}

	/**
	 * Caches the shopping item fields in the entity cache if it is enabled.
	 *
	 * @param shoppingItemFields the shopping item fields
	 */
	@Override
	public void cacheResult(List<ShoppingItemField> shoppingItemFields) {
		for (ShoppingItemField shoppingItemField : shoppingItemFields) {
			if (entityCache.getResult(
						ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemFieldImpl.class,
						shoppingItemField.getPrimaryKey()) == null) {
				cacheResult(shoppingItemField);
			}
			else {
				shoppingItemField.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping item fields.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ShoppingItemFieldImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping item field.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingItemField shoppingItemField) {
		entityCache.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ShoppingItemField> shoppingItemFields) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingItemField shoppingItemField : shoppingItemFields) {
			entityCache.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());
		}
	}

	/**
	 * Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	 *
	 * @param itemFieldId the primary key for the new shopping item field
	 * @return the new shopping item field
	 */
	@Override
	public ShoppingItemField create(long itemFieldId) {
		ShoppingItemField shoppingItemField = new ShoppingItemFieldImpl();

		shoppingItemField.setNew(true);
		shoppingItemField.setPrimaryKey(itemFieldId);

		shoppingItemField.setCompanyId(companyProvider.getCompanyId());

		return shoppingItemField;
	}

	/**
	 * Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field that was removed
	 * @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField remove(long itemFieldId)
		throws NoSuchItemFieldException {
		return remove((Serializable)itemFieldId);
	}

	/**
	 * Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field that was removed
	 * @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField remove(Serializable primaryKey)
		throws NoSuchItemFieldException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemField shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
					primaryKey);

			if (shoppingItemField == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemFieldException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingItemField);
		}
		catch (NoSuchItemFieldException nsee) {
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
	protected ShoppingItemField removeImpl(ShoppingItemField shoppingItemField) {
		shoppingItemField = toUnwrappedModel(shoppingItemField);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(shoppingItemField)) {
				shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
						shoppingItemField.getPrimaryKeyObj());
			}

			if (shoppingItemField != null) {
				session.delete(shoppingItemField);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingItemField != null) {
			clearCache(shoppingItemField);
		}

		return shoppingItemField;
	}

	@Override
	public ShoppingItemField updateImpl(ShoppingItemField shoppingItemField) {
		shoppingItemField = toUnwrappedModel(shoppingItemField);

		boolean isNew = shoppingItemField.isNew();

		ShoppingItemFieldModelImpl shoppingItemFieldModelImpl = (ShoppingItemFieldModelImpl)shoppingItemField;

		Session session = null;

		try {
			session = openSession();

			if (shoppingItemField.isNew()) {
				session.save(shoppingItemField);

				shoppingItemField.setNew(false);
			}
			else {
				shoppingItemField = (ShoppingItemField)session.merge(shoppingItemField);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingItemFieldModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingItemFieldModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingItemFieldModelImpl.getOriginalItemId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);

				args = new Object[] { shoppingItemFieldModelImpl.getItemId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);
			}
		}

		entityCache.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField, false);

		shoppingItemField.resetOriginalValues();

		return shoppingItemField;
	}

	protected ShoppingItemField toUnwrappedModel(
		ShoppingItemField shoppingItemField) {
		if (shoppingItemField instanceof ShoppingItemFieldImpl) {
			return shoppingItemField;
		}

		ShoppingItemFieldImpl shoppingItemFieldImpl = new ShoppingItemFieldImpl();

		shoppingItemFieldImpl.setNew(shoppingItemField.isNew());
		shoppingItemFieldImpl.setPrimaryKey(shoppingItemField.getPrimaryKey());

		shoppingItemFieldImpl.setItemFieldId(shoppingItemField.getItemFieldId());
		shoppingItemFieldImpl.setCompanyId(shoppingItemField.getCompanyId());
		shoppingItemFieldImpl.setItemId(shoppingItemField.getItemId());
		shoppingItemFieldImpl.setName(shoppingItemField.getName());
		shoppingItemFieldImpl.setValues(shoppingItemField.getValues());
		shoppingItemFieldImpl.setDescription(shoppingItemField.getDescription());

		return shoppingItemFieldImpl;
	}

	/**
	 * Returns the shopping item field with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field
	 * @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemFieldException {
		ShoppingItemField shoppingItemField = fetchByPrimaryKey(primaryKey);

		if (shoppingItemField == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemFieldException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return shoppingItemField;
	}

	/**
	 * Returns the shopping item field with the primary key or throws a {@link NoSuchItemFieldException} if it could not be found.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field
	 * @throws NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField findByPrimaryKey(long itemFieldId)
		throws NoSuchItemFieldException {
		return findByPrimaryKey((Serializable)itemFieldId);
	}

	/**
	 * Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField fetchByPrimaryKey(Serializable primaryKey) {
		ShoppingItemField shoppingItemField = (ShoppingItemField)entityCache.getResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemFieldImpl.class, primaryKey);

		if (shoppingItemField == _nullShoppingItemField) {
			return null;
		}

		if (shoppingItemField == null) {
			Session session = null;

			try {
				session = openSession();

				shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
						primaryKey);

				if (shoppingItemField != null) {
					cacheResult(shoppingItemField);
				}
				else {
					entityCache.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemFieldImpl.class, primaryKey,
						_nullShoppingItemField);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemFieldImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return shoppingItemField;
	}

	/**
	 * Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	 */
	@Override
	public ShoppingItemField fetchByPrimaryKey(long itemFieldId) {
		return fetchByPrimaryKey((Serializable)itemFieldId);
	}

	@Override
	public Map<Serializable, ShoppingItemField> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ShoppingItemField> map = new HashMap<Serializable, ShoppingItemField>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ShoppingItemField shoppingItemField = fetchByPrimaryKey(primaryKey);

			if (shoppingItemField != null) {
				map.put(primaryKey, shoppingItemField);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ShoppingItemField shoppingItemField = (ShoppingItemField)entityCache.getResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemFieldImpl.class, primaryKey);

			if (shoppingItemField == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, shoppingItemField);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE_PKS_IN);

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

			for (ShoppingItemField shoppingItemField : (List<ShoppingItemField>)q.list()) {
				map.put(shoppingItemField.getPrimaryKeyObj(), shoppingItemField);

				cacheResult(shoppingItemField);

				uncachedPrimaryKeys.remove(shoppingItemField.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
					ShoppingItemFieldImpl.class, primaryKey,
					_nullShoppingItemField);
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
	 * Returns all the shopping item fields.
	 *
	 * @return the shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @return the range of shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator<ShoppingItemField> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingItemFieldModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of shopping item fields
	 */
	@Override
	public List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator<ShoppingItemField> orderByComparator,
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

		List<ShoppingItemField> list = null;

		if (retrieveFromCache) {
			list = (List<ShoppingItemField>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SHOPPINGITEMFIELD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGITEMFIELD;

				if (pagination) {
					sql = sql.concat(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
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
	 * Removes all the shopping item fields from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ShoppingItemField shoppingItemField : findAll()) {
			remove(shoppingItemField);
		}
	}

	/**
	 * Returns the number of shopping item fields.
	 *
	 * @return the number of shopping item fields
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEMFIELD);

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
		return ShoppingItemFieldModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the shopping item field persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ShoppingItemFieldImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD_WHERE_PKS_IN = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE itemFieldId IN (";
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD_WHERE = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD_WHERE = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItemField.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItemField exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItemField exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"values"
			});
	private static final ShoppingItemField _nullShoppingItemField = new ShoppingItemFieldImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingItemField> toCacheModel() {
				return _nullShoppingItemFieldCacheModel;
			}
		};

	private static final CacheModel<ShoppingItemField> _nullShoppingItemFieldCacheModel =
		new CacheModel<ShoppingItemField>() {
			@Override
			public ShoppingItemField toEntityModel() {
				return _nullShoppingItemField;
			}
		};
}