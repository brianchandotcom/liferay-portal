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

import com.liferay.portlet.shopping.NoSuchCartException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.impl.ShoppingCartImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the shopping cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartPersistence
 * @see ShoppingCartUtil
 * @generated
 */
public class ShoppingCartPersistenceImpl extends BasePersistenceImpl<ShoppingCart>
	implements ShoppingCartPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingCartUtil} to access the shopping cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingCartImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			ShoppingCartModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			ShoppingCartModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			ShoppingCartModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingCartModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, ShoppingCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the shopping cart in the entity cache if it is enabled.
	 *
	 * @param shoppingCart the shopping cart
	 */
	public void cacheResult(ShoppingCart shoppingCart) {
		EntityCacheUtil.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(shoppingCart.getGroupId()),
				Long.valueOf(shoppingCart.getUserId())
			}, shoppingCart);

		shoppingCart.resetOriginalValues();
	}

	/**
	 * Caches the shopping carts in the entity cache if it is enabled.
	 *
	 * @param shoppingCarts the shopping carts
	 */
	public void cacheResult(List<ShoppingCart> shoppingCarts) {
		for (ShoppingCart shoppingCart : shoppingCarts) {
			if (EntityCacheUtil.getResult(
						ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCartImpl.class, shoppingCart.getPrimaryKey()) == null) {
				cacheResult(shoppingCart);
			}
			else {
				shoppingCart.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping carts.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ShoppingCartImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ShoppingCartImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping cart.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingCart shoppingCart) {
		EntityCacheUtil.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(shoppingCart);
	}

	@Override
	public void clearCache(List<ShoppingCart> shoppingCarts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingCart shoppingCart : shoppingCarts) {
			EntityCacheUtil.removeResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCartImpl.class, shoppingCart.getPrimaryKey());

			clearUniqueFindersCache(shoppingCart);
		}
	}

	protected void clearUniqueFindersCache(ShoppingCart shoppingCart) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
			new Object[] {
				Long.valueOf(shoppingCart.getGroupId()),
				Long.valueOf(shoppingCart.getUserId())
			});
	}

	/**
	 * Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	 *
	 * @param cartId the primary key for the new shopping cart
	 * @return the new shopping cart
	 */
	public ShoppingCart create(long cartId) {
		ShoppingCart shoppingCart = new ShoppingCartImpl();

		shoppingCart.setNew(true);
		shoppingCart.setPrimaryKey(cartId);

		return shoppingCart;
	}

	/**
	 * Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart remove(long cartId)
		throws NoSuchCartException, SystemException {
		return remove(Long.valueOf(cartId));
	}

	/**
	 * Removes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingCart remove(Serializable primaryKey)
		throws NoSuchCartException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingCart shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
					primaryKey);

			if (shoppingCart == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingCart);
		}
		catch (NoSuchCartException nsee) {
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
	protected ShoppingCart removeImpl(ShoppingCart shoppingCart)
		throws SystemException {
		shoppingCart = toUnwrappedModel(shoppingCart);

		Session session = null;

		try {
			session = openSession();

			if (shoppingCart.isCachedModel()) {
				shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
						shoppingCart.getPrimaryKeyObj());
			}

			if (shoppingCart != null) {
				session.delete(shoppingCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingCart != null) {
			clearCache(shoppingCart);
		}

		return shoppingCart;
	}

	@Override
	public ShoppingCart updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws SystemException {
		shoppingCart = toUnwrappedModel(shoppingCart);

		boolean isNew = shoppingCart.isNew();

		ShoppingCartModelImpl shoppingCartModelImpl = (ShoppingCartModelImpl)shoppingCart;

		Session session = null;

		try {
			session = openSession();

			if (shoppingCart.isNew()) {
				session.save(shoppingCart);

				shoppingCart.setNew(false);
			}
			else {
				session.merge(shoppingCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingCartModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingCartModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(shoppingCartModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingCartModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] {
						Long.valueOf(shoppingCartModelImpl.getUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingCartImpl.class, shoppingCart.getPrimaryKey(), shoppingCart);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
				new Object[] {
					Long.valueOf(shoppingCart.getGroupId()),
					Long.valueOf(shoppingCart.getUserId())
				}, shoppingCart);
		}
		else {
			if ((shoppingCartModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingCartModelImpl.getOriginalGroupId()),
						Long.valueOf(shoppingCartModelImpl.getOriginalUserId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
					new Object[] {
						Long.valueOf(shoppingCart.getGroupId()),
						Long.valueOf(shoppingCart.getUserId())
					}, shoppingCart);
			}
		}

		return shoppingCart;
	}

	protected ShoppingCart toUnwrappedModel(ShoppingCart shoppingCart) {
		if (shoppingCart instanceof ShoppingCartImpl) {
			return shoppingCart;
		}

		ShoppingCartImpl shoppingCartImpl = new ShoppingCartImpl();

		shoppingCartImpl.setNew(shoppingCart.isNew());
		shoppingCartImpl.setPrimaryKey(shoppingCart.getPrimaryKey());

		shoppingCartImpl.setCartId(shoppingCart.getCartId());
		shoppingCartImpl.setGroupId(shoppingCart.getGroupId());
		shoppingCartImpl.setCompanyId(shoppingCart.getCompanyId());
		shoppingCartImpl.setUserId(shoppingCart.getUserId());
		shoppingCartImpl.setUserName(shoppingCart.getUserName());
		shoppingCartImpl.setCreateDate(shoppingCart.getCreateDate());
		shoppingCartImpl.setModifiedDate(shoppingCart.getModifiedDate());
		shoppingCartImpl.setItemIds(shoppingCart.getItemIds());
		shoppingCartImpl.setCouponCodes(shoppingCart.getCouponCodes());
		shoppingCartImpl.setAltShipping(shoppingCart.getAltShipping());
		shoppingCartImpl.setInsure(shoppingCart.isInsure());

		return shoppingCartImpl;
	}

	/**
	 * Returns the shopping cart with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart
	 * @throws com.liferay.portal.NoSuchModelException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingCart findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping cart with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByPrimaryKey(long cartId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByPrimaryKey(cartId);

		if (shoppingCart == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + cartId);
			}

			throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				cartId);
		}

		return shoppingCart;
	}

	/**
	 * Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping cart
	 * @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingCart fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cartId the primary key of the shopping cart
	 * @return the shopping cart, or <code>null</code> if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByPrimaryKey(long cartId)
		throws SystemException {
		ShoppingCart shoppingCart = (ShoppingCart)EntityCacheUtil.getResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingCartImpl.class, cartId);

		if (shoppingCart == _nullShoppingCart) {
			return null;
		}

		if (shoppingCart == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				shoppingCart = (ShoppingCart)session.get(ShoppingCartImpl.class,
						Long.valueOf(cartId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (shoppingCart != null) {
					cacheResult(shoppingCart);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ShoppingCartModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingCartImpl.class, cartId, _nullShoppingCart);
				}

				closeSession(session);
			}
		}

		return shoppingCart;
	}

	/**
	 * Returns all the shopping carts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ShoppingCart shoppingCart : list) {
				if ((groupId != shoppingCart.getGroupId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByGroupId_First(groupId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ShoppingCart> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingCart> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping carts before and after the current shopping cart in the ordered set where groupId = &#63;.
	 *
	 * @param cartId the primary key of the current shopping cart
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart[] findByGroupId_PrevAndNext(long cartId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, shoppingCart, groupId,
					orderByComparator, true);

			array[1] = shoppingCart;

			array[2] = getByGroupId_PrevAndNext(session, shoppingCart, groupId,
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

	protected ShoppingCart getByGroupId_PrevAndNext(Session session,
		ShoppingCart shoppingCart, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping carts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ShoppingCart shoppingCart : list) {
				if ((userId != shoppingCart.getUserId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByUserId_First(userId,
				orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ShoppingCart> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByUserId_Last(userId, orderByComparator);

		if (shoppingCart != null) {
			return shoppingCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		List<ShoppingCart> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping carts before and after the current shopping cart in the ordered set where userId = &#63;.
	 *
	 * @param cartId the primary key of the current shopping cart
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a shopping cart with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart[] findByUserId_PrevAndNext(long cartId, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByPrimaryKey(cartId);

		Session session = null;

		try {
			session = openSession();

			ShoppingCart[] array = new ShoppingCartImpl[3];

			array[0] = getByUserId_PrevAndNext(session, shoppingCart, userId,
					orderByComparator, true);

			array[1] = shoppingCart;

			array[2] = getByUserId_PrevAndNext(session, shoppingCart, userId,
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

	protected ShoppingCart getByUserId_PrevAndNext(Session session,
		ShoppingCart shoppingCart, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchCartException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching shopping cart
	 * @throws com.liferay.portlet.shopping.NoSuchCartException if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart findByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = fetchByG_U(groupId, userId);

		if (shoppingCart == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return shoppingCart;
	}

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByG_U(long groupId, long userId)
		throws SystemException {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the shopping cart where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching shopping cart, or <code>null</code> if a matching shopping cart could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart fetchByG_U(long groupId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_U,
					finderArgs, this);
		}

		if (result instanceof ShoppingCart) {
			ShoppingCart shoppingCart = (ShoppingCart)result;

			if ((groupId != shoppingCart.getGroupId()) ||
					(userId != shoppingCart.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<ShoppingCart> list = q.list();

				result = list;

				ShoppingCart shoppingCart = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs, list);
				}
				else {
					shoppingCart = list.get(0);

					cacheResult(shoppingCart);

					if ((shoppingCart.getGroupId() != groupId) ||
							(shoppingCart.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_U,
							finderArgs, shoppingCart);
					}
				}

				return shoppingCart;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_U,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ShoppingCart)result;
			}
		}
	}

	/**
	 * Returns all the shopping carts.
	 *
	 * @return the shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @return the range of shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping carts
	 * @param end the upper bound of the range of shopping carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingCart> findAll(int start, int end,
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

		List<ShoppingCart> list = (List<ShoppingCart>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGCART);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGCART;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the shopping carts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingCart shoppingCart : findByGroupId(groupId)) {
			remove(shoppingCart);
		}
	}

	/**
	 * Removes all the shopping carts where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (ShoppingCart shoppingCart : findByUserId(userId)) {
			remove(shoppingCart);
		}
	}

	/**
	 * Removes the shopping cart where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the shopping cart that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingCart removeByG_U(long groupId, long userId)
		throws NoSuchCartException, SystemException {
		ShoppingCart shoppingCart = findByG_U(groupId, userId);

		return remove(shoppingCart);
	}

	/**
	 * Removes all the shopping carts from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ShoppingCart shoppingCart : findAll()) {
			remove(shoppingCart);
		}
	}

	/**
	 * Returns the number of shopping carts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping carts where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping carts where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHOPPINGCART_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping carts.
	 *
	 * @return the number of shopping carts
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGCART);

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
	 * Initializes the shopping cart persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingCart")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingCart>> listenersList = new ArrayList<ModelListener<ShoppingCart>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingCart>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ShoppingCartImpl.class.getName());
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
	private static final String _SQL_SELECT_SHOPPINGCART = "SELECT shoppingCart FROM ShoppingCart shoppingCart";
	private static final String _SQL_SELECT_SHOPPINGCART_WHERE = "SELECT shoppingCart FROM ShoppingCart shoppingCart WHERE ";
	private static final String _SQL_COUNT_SHOPPINGCART = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart";
	private static final String _SQL_COUNT_SHOPPINGCART_WHERE = "SELECT COUNT(shoppingCart) FROM ShoppingCart shoppingCart WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "shoppingCart.groupId = ?";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "shoppingCart.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "shoppingCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "shoppingCart.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingCart.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingCart exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingCart exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ShoppingCartPersistenceImpl.class);
	private static ShoppingCart _nullShoppingCart = new ShoppingCartImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingCart> toCacheModel() {
				return _nullShoppingCartCacheModel;
			}
		};

	private static CacheModel<ShoppingCart> _nullShoppingCartCacheModel = new CacheModel<ShoppingCart>() {
			public ShoppingCart toEntityModel() {
				return _nullShoppingCart;
			}
		};
}