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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.shopping.NoSuchItemFieldException;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the shopping item field service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemFieldPersistence
 * @see ShoppingItemFieldUtil
 * @generated
 */
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
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByItemId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID =
		new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED,
			ShoppingItemFieldImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByItemId",
			new String[] { Long.class.getName() },
			ShoppingItemFieldModelImpl.ITEMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ITEMID = new FinderPath(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByItemId",
			new String[] { Long.class.getName() });
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

	/**
	 * Caches the shopping item field in the entity cache if it is enabled.
	 *
	 * @param shoppingItemField the shopping item field
	 */
	public void cacheResult(ShoppingItemField shoppingItemField) {
		EntityCacheUtil.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField);

		shoppingItemField.resetOriginalValues();
	}

	/**
	 * Caches the shopping item fields in the entity cache if it is enabled.
	 *
	 * @param shoppingItemFields the shopping item fields
	 */
	public void cacheResult(List<ShoppingItemField> shoppingItemFields) {
		for (ShoppingItemField shoppingItemField : shoppingItemFields) {
			if (EntityCacheUtil.getResult(
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
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ShoppingItemFieldImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ShoppingItemFieldImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping item field.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingItemField shoppingItemField) {
		EntityCacheUtil.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ShoppingItemField> shoppingItemFields) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingItemField shoppingItemField : shoppingItemFields) {
			EntityCacheUtil.removeResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey());
		}
	}

	/**
	 * Creates a new shopping item field with the primary key. Does not add the shopping item field to the database.
	 *
	 * @param itemFieldId the primary key for the new shopping item field
	 * @return the new shopping item field
	 */
	public ShoppingItemField create(long itemFieldId) {
		ShoppingItemField shoppingItemField = new ShoppingItemFieldImpl();

		shoppingItemField.setNew(true);
		shoppingItemField.setPrimaryKey(itemFieldId);

		return shoppingItemField;
	}

	/**
	 * Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField remove(long itemFieldId)
		throws NoSuchItemFieldException, SystemException {
		return remove(Long.valueOf(itemFieldId));
	}

	/**
	 * Removes the shopping item field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingItemField remove(Serializable primaryKey)
		throws NoSuchItemFieldException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingItemField shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
					primaryKey);

			if (shoppingItemField == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
	protected ShoppingItemField removeImpl(ShoppingItemField shoppingItemField)
		throws SystemException {
		shoppingItemField = toUnwrappedModel(shoppingItemField);

		Session session = null;

		try {
			session = openSession();

			if (shoppingItemField.isCachedModel()) {
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
	public ShoppingItemField updateImpl(
		com.liferay.portlet.shopping.model.ShoppingItemField shoppingItemField)
		throws SystemException {
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
				session.merge(shoppingItemField);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingItemFieldModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingItemFieldModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingItemFieldModelImpl.getOriginalItemId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);

				args = new Object[] {
						Long.valueOf(shoppingItemFieldModelImpl.getItemId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ITEMID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID,
					args);
			}
		}

		EntityCacheUtil.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingItemFieldImpl.class, shoppingItemField.getPrimaryKey(),
			shoppingItemField);

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
		shoppingItemFieldImpl.setItemId(shoppingItemField.getItemId());
		shoppingItemFieldImpl.setName(shoppingItemField.getName());
		shoppingItemFieldImpl.setValues(shoppingItemField.getValues());
		shoppingItemFieldImpl.setDescription(shoppingItemField.getDescription());

		return shoppingItemFieldImpl;
	}

	/**
	 * Returns the shopping item field with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field
	 * @throws com.liferay.portal.NoSuchModelException if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingItemField findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping item field with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchItemFieldException} if it could not be found.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField findByPrimaryKey(long itemFieldId)
		throws NoSuchItemFieldException, SystemException {
		ShoppingItemField shoppingItemField = fetchByPrimaryKey(itemFieldId);

		if (shoppingItemField == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + itemFieldId);
			}

			throw new NoSuchItemFieldException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				itemFieldId);
		}

		return shoppingItemField;
	}

	/**
	 * Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping item field
	 * @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingItemField fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping item field with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemFieldId the primary key of the shopping item field
	 * @return the shopping item field, or <code>null</code> if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField fetchByPrimaryKey(long itemFieldId)
		throws SystemException {
		ShoppingItemField shoppingItemField = (ShoppingItemField)EntityCacheUtil.getResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingItemFieldImpl.class, itemFieldId);

		if (shoppingItemField == _nullShoppingItemField) {
			return null;
		}

		if (shoppingItemField == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				shoppingItemField = (ShoppingItemField)session.get(ShoppingItemFieldImpl.class,
						Long.valueOf(itemFieldId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (shoppingItemField != null) {
					cacheResult(shoppingItemField);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ShoppingItemFieldModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingItemFieldImpl.class, itemFieldId,
						_nullShoppingItemField);
				}

				closeSession(session);
			}
		}

		return shoppingItemField;
	}

	/**
	 * Returns all the shopping item fields where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the matching shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findByItemId(long itemId)
		throws SystemException {
		return findByItemId(itemId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item fields where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @return the range of matching shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findByItemId(long itemId, int start, int end)
		throws SystemException {
		return findByItemId(itemId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item fields where itemId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param itemId the item ID
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findByItemId(long itemId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ITEMID;
			finderArgs = new Object[] { itemId, start, end, orderByComparator };
		}

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ShoppingItemField shoppingItemField : list) {
				if ((itemId != shoppingItemField.getItemId())) {
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

			query.append(_SQL_SELECT_SHOPPINGITEMFIELD_WHERE);

			query.append(_FINDER_COLUMN_ITEMID_ITEMID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(itemId);

				list = (List<ShoppingItemField>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

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
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a matching shopping item field could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField findByItemId_First(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField fetchByItemId_First(long itemId,
		OrderByComparator orderByComparator) throws SystemException {
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
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a matching shopping item field could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField findByItemId_Last(long itemId,
		OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField fetchByItemId_Last(long itemId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByItemId(itemId);

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
	 * @throws com.liferay.portlet.shopping.NoSuchItemFieldException if a shopping item field with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingItemField[] findByItemId_PrevAndNext(long itemFieldId,
		long itemId, OrderByComparator orderByComparator)
		throws NoSuchItemFieldException, SystemException {
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
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
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
	 * Returns all the shopping item fields.
	 *
	 * @return the shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @return the range of shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping item fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping item fields
	 * @param end the upper bound of the range of shopping item fields (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingItemField> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ShoppingItemField> list = (List<ShoppingItemField>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGITEMFIELD);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGITEMFIELD.concat(ShoppingItemFieldModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingItemField>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the shopping item fields where itemId = &#63; from the database.
	 *
	 * @param itemId the item ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByItemId(long itemId) throws SystemException {
		for (ShoppingItemField shoppingItemField : findByItemId(itemId)) {
			remove(shoppingItemField);
		}
	}

	/**
	 * Removes all the shopping item fields from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ShoppingItemField shoppingItemField : findAll()) {
			remove(shoppingItemField);
		}
	}

	/**
	 * Returns the number of shopping item fields where itemId = &#63;.
	 *
	 * @param itemId the item ID
	 * @return the number of matching shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public int countByItemId(long itemId) throws SystemException {
		Object[] finderArgs = new Object[] { itemId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ITEMID,
				finderArgs, this);

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
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ITEMID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping item fields.
	 *
	 * @return the number of shopping item fields
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGITEMFIELD);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the shopping item field persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingItemField")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingItemField>> listenersList = new ArrayList<ModelListener<ShoppingItemField>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingItemField>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ShoppingItemFieldImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ShoppingCartPersistence.class)
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(type = ShoppingCategoryPersistence.class)
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(type = ShoppingCouponPersistence.class)
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(type = ShoppingItemPersistence.class)
	protected ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(type = ShoppingItemFieldPersistence.class)
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(type = ShoppingItemPricePersistence.class)
	protected ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(type = ShoppingOrderPersistence.class)
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(type = ShoppingOrderItemPersistence.class)
	protected ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_SELECT_SHOPPINGITEMFIELD_WHERE = "SELECT shoppingItemField FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField";
	private static final String _SQL_COUNT_SHOPPINGITEMFIELD_WHERE = "SELECT COUNT(shoppingItemField) FROM ShoppingItemField shoppingItemField WHERE ";
	private static final String _FINDER_COLUMN_ITEMID_ITEMID_2 = "shoppingItemField.itemId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingItemField.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingItemField exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingItemField exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ShoppingItemFieldPersistenceImpl.class);
	private static ShoppingItemField _nullShoppingItemField = new ShoppingItemFieldImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingItemField> toCacheModel() {
				return _nullShoppingItemFieldCacheModel;
			}
		};

	private static CacheModel<ShoppingItemField> _nullShoppingItemFieldCacheModel =
		new CacheModel<ShoppingItemField>() {
			public ShoppingItemField toEntityModel() {
				return _nullShoppingItemField;
			}
		};
}