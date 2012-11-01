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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the shopping order service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderPersistence
 * @see ShoppingOrderUtil
 * @generated
 */
public class ShoppingOrderPersistenceImpl extends BasePersistenceImpl<ShoppingOrder>
	implements ShoppingOrderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ShoppingOrderUtil} to access the shopping order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ShoppingOrderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			ShoppingOrderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_NUMBER = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByNumber",
			new String[] { String.class.getName() },
			ShoppingOrderModelImpl.NUMBER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NUMBER = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNumber",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_PPTXNID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPPTxnId", new String[] { String.class.getName() },
			ShoppingOrderModelImpl.PPTXNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PPTXNID = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPPTxnId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_PPPS = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_PPPS =
		new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ShoppingOrderModelImpl.GROUPID_COLUMN_BITMASK |
			ShoppingOrderModelImpl.USERID_COLUMN_BITMASK |
			ShoppingOrderModelImpl.PPPAYMENTSTATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_PPPS = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_PPPS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED,
			ShoppingOrderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the shopping order in the entity cache if it is enabled.
	 *
	 * @param shoppingOrder the shopping order
	 */
	public void cacheResult(ShoppingOrder shoppingOrder) {
		EntityCacheUtil.putResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
			new Object[] { shoppingOrder.getNumber() }, shoppingOrder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
			new Object[] { shoppingOrder.getPpTxnId() }, shoppingOrder);

		shoppingOrder.resetOriginalValues();
	}

	/**
	 * Caches the shopping orders in the entity cache if it is enabled.
	 *
	 * @param shoppingOrders the shopping orders
	 */
	public void cacheResult(List<ShoppingOrder> shoppingOrders) {
		for (ShoppingOrder shoppingOrder : shoppingOrders) {
			if (EntityCacheUtil.getResult(
						ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey()) == null) {
				cacheResult(shoppingOrder);
			}
			else {
				shoppingOrder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all shopping orders.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ShoppingOrderImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ShoppingOrderImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the shopping order.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ShoppingOrder shoppingOrder) {
		EntityCacheUtil.removeResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(shoppingOrder);
	}

	@Override
	public void clearCache(List<ShoppingOrder> shoppingOrders) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ShoppingOrder shoppingOrder : shoppingOrders) {
			EntityCacheUtil.removeResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey());

			clearUniqueFindersCache(shoppingOrder);
		}
	}

	protected void clearUniqueFindersCache(ShoppingOrder shoppingOrder) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NUMBER,
			new Object[] { shoppingOrder.getNumber() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PPTXNID,
			new Object[] { shoppingOrder.getPpTxnId() });
	}

	/**
	 * Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	 *
	 * @param orderId the primary key for the new shopping order
	 * @return the new shopping order
	 */
	public ShoppingOrder create(long orderId) {
		ShoppingOrder shoppingOrder = new ShoppingOrderImpl();

		shoppingOrder.setNew(true);
		shoppingOrder.setPrimaryKey(orderId);

		return shoppingOrder;
	}

	/**
	 * Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder remove(long orderId)
		throws NoSuchOrderException, SystemException {
		return remove(Long.valueOf(orderId));
	}

	/**
	 * Removes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order that was removed
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingOrder remove(Serializable primaryKey)
		throws NoSuchOrderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ShoppingOrder shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
					primaryKey);

			if (shoppingOrder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(shoppingOrder);
		}
		catch (NoSuchOrderException nsee) {
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
	protected ShoppingOrder removeImpl(ShoppingOrder shoppingOrder)
		throws SystemException {
		shoppingOrder = toUnwrappedModel(shoppingOrder);

		Session session = null;

		try {
			session = openSession();

			if (shoppingOrder.isCachedModel()) {
				shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
						shoppingOrder.getPrimaryKeyObj());
			}

			if (shoppingOrder != null) {
				session.delete(shoppingOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (shoppingOrder != null) {
			clearCache(shoppingOrder);
		}

		return shoppingOrder;
	}

	@Override
	public ShoppingOrder updateImpl(
		com.liferay.portlet.shopping.model.ShoppingOrder shoppingOrder)
		throws SystemException {
		shoppingOrder = toUnwrappedModel(shoppingOrder);

		boolean isNew = shoppingOrder.isNew();

		ShoppingOrderModelImpl shoppingOrderModelImpl = (ShoppingOrderModelImpl)shoppingOrder;

		Session session = null;

		try {
			session = openSession();

			if (shoppingOrder.isNew()) {
				session.save(shoppingOrder);

				shoppingOrder.setNew(false);
			}
			else {
				session.merge(shoppingOrder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ShoppingOrderModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((shoppingOrderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingOrderModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(shoppingOrderModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((shoppingOrderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_PPPS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(shoppingOrderModelImpl.getOriginalGroupId()),
						Long.valueOf(shoppingOrderModelImpl.getOriginalUserId()),
						
						shoppingOrderModelImpl.getOriginalPpPaymentStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_PPPS, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_PPPS,
					args);

				args = new Object[] {
						Long.valueOf(shoppingOrderModelImpl.getGroupId()),
						Long.valueOf(shoppingOrderModelImpl.getUserId()),
						
						shoppingOrderModelImpl.getPpPaymentStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_U_PPPS, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_PPPS,
					args);
			}
		}

		EntityCacheUtil.putResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
			ShoppingOrderImpl.class, shoppingOrder.getPrimaryKey(),
			shoppingOrder);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
				new Object[] { shoppingOrder.getNumber() }, shoppingOrder);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
				new Object[] { shoppingOrder.getPpTxnId() }, shoppingOrder);
		}
		else {
			if ((shoppingOrderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_NUMBER.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingOrderModelImpl.getOriginalNumber()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NUMBER, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NUMBER, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
					new Object[] { shoppingOrder.getNumber() }, shoppingOrder);
			}

			if ((shoppingOrderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PPTXNID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						shoppingOrderModelImpl.getOriginalPpTxnId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PPTXNID, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PPTXNID, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
					new Object[] { shoppingOrder.getPpTxnId() }, shoppingOrder);
			}
		}

		return shoppingOrder;
	}

	protected ShoppingOrder toUnwrappedModel(ShoppingOrder shoppingOrder) {
		if (shoppingOrder instanceof ShoppingOrderImpl) {
			return shoppingOrder;
		}

		ShoppingOrderImpl shoppingOrderImpl = new ShoppingOrderImpl();

		shoppingOrderImpl.setNew(shoppingOrder.isNew());
		shoppingOrderImpl.setPrimaryKey(shoppingOrder.getPrimaryKey());

		shoppingOrderImpl.setOrderId(shoppingOrder.getOrderId());
		shoppingOrderImpl.setGroupId(shoppingOrder.getGroupId());
		shoppingOrderImpl.setCompanyId(shoppingOrder.getCompanyId());
		shoppingOrderImpl.setUserId(shoppingOrder.getUserId());
		shoppingOrderImpl.setUserName(shoppingOrder.getUserName());
		shoppingOrderImpl.setCreateDate(shoppingOrder.getCreateDate());
		shoppingOrderImpl.setModifiedDate(shoppingOrder.getModifiedDate());
		shoppingOrderImpl.setNumber(shoppingOrder.getNumber());
		shoppingOrderImpl.setTax(shoppingOrder.getTax());
		shoppingOrderImpl.setShipping(shoppingOrder.getShipping());
		shoppingOrderImpl.setAltShipping(shoppingOrder.getAltShipping());
		shoppingOrderImpl.setRequiresShipping(shoppingOrder.isRequiresShipping());
		shoppingOrderImpl.setInsure(shoppingOrder.isInsure());
		shoppingOrderImpl.setInsurance(shoppingOrder.getInsurance());
		shoppingOrderImpl.setCouponCodes(shoppingOrder.getCouponCodes());
		shoppingOrderImpl.setCouponDiscount(shoppingOrder.getCouponDiscount());
		shoppingOrderImpl.setBillingFirstName(shoppingOrder.getBillingFirstName());
		shoppingOrderImpl.setBillingLastName(shoppingOrder.getBillingLastName());
		shoppingOrderImpl.setBillingEmailAddress(shoppingOrder.getBillingEmailAddress());
		shoppingOrderImpl.setBillingCompany(shoppingOrder.getBillingCompany());
		shoppingOrderImpl.setBillingStreet(shoppingOrder.getBillingStreet());
		shoppingOrderImpl.setBillingCity(shoppingOrder.getBillingCity());
		shoppingOrderImpl.setBillingState(shoppingOrder.getBillingState());
		shoppingOrderImpl.setBillingZip(shoppingOrder.getBillingZip());
		shoppingOrderImpl.setBillingCountry(shoppingOrder.getBillingCountry());
		shoppingOrderImpl.setBillingPhone(shoppingOrder.getBillingPhone());
		shoppingOrderImpl.setShipToBilling(shoppingOrder.isShipToBilling());
		shoppingOrderImpl.setShippingFirstName(shoppingOrder.getShippingFirstName());
		shoppingOrderImpl.setShippingLastName(shoppingOrder.getShippingLastName());
		shoppingOrderImpl.setShippingEmailAddress(shoppingOrder.getShippingEmailAddress());
		shoppingOrderImpl.setShippingCompany(shoppingOrder.getShippingCompany());
		shoppingOrderImpl.setShippingStreet(shoppingOrder.getShippingStreet());
		shoppingOrderImpl.setShippingCity(shoppingOrder.getShippingCity());
		shoppingOrderImpl.setShippingState(shoppingOrder.getShippingState());
		shoppingOrderImpl.setShippingZip(shoppingOrder.getShippingZip());
		shoppingOrderImpl.setShippingCountry(shoppingOrder.getShippingCountry());
		shoppingOrderImpl.setShippingPhone(shoppingOrder.getShippingPhone());
		shoppingOrderImpl.setCcName(shoppingOrder.getCcName());
		shoppingOrderImpl.setCcType(shoppingOrder.getCcType());
		shoppingOrderImpl.setCcNumber(shoppingOrder.getCcNumber());
		shoppingOrderImpl.setCcExpMonth(shoppingOrder.getCcExpMonth());
		shoppingOrderImpl.setCcExpYear(shoppingOrder.getCcExpYear());
		shoppingOrderImpl.setCcVerNumber(shoppingOrder.getCcVerNumber());
		shoppingOrderImpl.setComments(shoppingOrder.getComments());
		shoppingOrderImpl.setPpTxnId(shoppingOrder.getPpTxnId());
		shoppingOrderImpl.setPpPaymentStatus(shoppingOrder.getPpPaymentStatus());
		shoppingOrderImpl.setPpPaymentGross(shoppingOrder.getPpPaymentGross());
		shoppingOrderImpl.setPpReceiverEmail(shoppingOrder.getPpReceiverEmail());
		shoppingOrderImpl.setPpPayerEmail(shoppingOrder.getPpPayerEmail());
		shoppingOrderImpl.setSendOrderEmail(shoppingOrder.isSendOrderEmail());
		shoppingOrderImpl.setSendShippingEmail(shoppingOrder.isSendShippingEmail());

		return shoppingOrderImpl;
	}

	/**
	 * Returns the shopping order with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order
	 * @throws com.liferay.portal.NoSuchModelException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingOrder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping order with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByPrimaryKey(long orderId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByPrimaryKey(orderId);

		if (shoppingOrder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + orderId);
			}

			throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				orderId);
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the shopping order
	 * @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ShoppingOrder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the shopping order with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orderId the primary key of the shopping order
	 * @return the shopping order, or <code>null</code> if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByPrimaryKey(long orderId)
		throws SystemException {
		ShoppingOrder shoppingOrder = (ShoppingOrder)EntityCacheUtil.getResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
				ShoppingOrderImpl.class, orderId);

		if (shoppingOrder == _nullShoppingOrder) {
			return null;
		}

		if (shoppingOrder == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				shoppingOrder = (ShoppingOrder)session.get(ShoppingOrderImpl.class,
						Long.valueOf(orderId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (shoppingOrder != null) {
					cacheResult(shoppingOrder);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ShoppingOrderModelImpl.ENTITY_CACHE_ENABLED,
						ShoppingOrderImpl.class, orderId, _nullShoppingOrder);
				}

				closeSession(session);
			}
		}

		return shoppingOrder;
	}

	/**
	 * Returns all the shopping orders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByGroupId(long groupId, int start, int end,
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

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ShoppingOrder shoppingOrder : list) {
				if ((groupId != shoppingOrder.getGroupId())) {
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

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByGroupId_First(groupId,
				orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ShoppingOrder> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<ShoppingOrder> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder[] findByGroupId_PrevAndNext(long orderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, shoppingOrder,
					groupId, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = getByGroupId_PrevAndNext(session, shoppingOrder,
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

	protected ShoppingOrder getByGroupId_PrevAndNext(Session session,
		ShoppingOrder shoppingOrder, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

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
			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingOrder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByGroupId(long groupId, int start,
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
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<ShoppingOrder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder[] filterFindByGroupId_PrevAndNext(long orderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(orderId, groupId, orderByComparator);
		}

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, shoppingOrder,
					groupId, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = filterGetByGroupId_PrevAndNext(session, shoppingOrder,
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

	protected ShoppingOrder filterGetByGroupId_PrevAndNext(Session session,
		ShoppingOrder shoppingOrder, long groupId,
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
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingOrder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the shopping order where number = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	 *
	 * @param number the number
	 * @return the matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByNumber(number);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("number=");
			msg.append(number);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param number the number
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByNumber(String number) throws SystemException {
		return fetchByNumber(number, true);
	}

	/**
	 * Returns the shopping order where number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param number the number
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByNumber(String number, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { number };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NUMBER,
					finderArgs, this);
		}

		if (result instanceof ShoppingOrder) {
			ShoppingOrder shoppingOrder = (ShoppingOrder)result;

			if (!Validator.equals(number, shoppingOrder.getNumber())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			if (number == null) {
				query.append(_FINDER_COLUMN_NUMBER_NUMBER_1);
			}
			else {
				if (number.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
				}
				else {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
				}
			}

			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (number != null) {
					qPos.add(number);
				}

				List<ShoppingOrder> list = q.list();

				result = list;

				ShoppingOrder shoppingOrder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
						finderArgs, list);
				}
				else {
					shoppingOrder = list.get(0);

					cacheResult(shoppingOrder);

					if ((shoppingOrder.getNumber() == null) ||
							!shoppingOrder.getNumber().equals(number)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NUMBER,
							finderArgs, shoppingOrder);
					}
				}

				return shoppingOrder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NUMBER,
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
				return (ShoppingOrder)result;
			}
		}
	}

	/**
	 * Returns the shopping order where ppTxnId = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchOrderException} if it could not be found.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByPPTxnId(String ppTxnId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByPPTxnId(ppTxnId);

		if (shoppingOrder == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ppTxnId=");
			msg.append(ppTxnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchOrderException(msg.toString());
		}

		return shoppingOrder;
	}

	/**
	 * Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByPPTxnId(String ppTxnId)
		throws SystemException {
		return fetchByPPTxnId(ppTxnId, true);
	}

	/**
	 * Returns the shopping order where ppTxnId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ppTxnId the pp txn ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByPPTxnId(String ppTxnId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { ppTxnId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PPTXNID,
					finderArgs, this);
		}

		if (result instanceof ShoppingOrder) {
			ShoppingOrder shoppingOrder = (ShoppingOrder)result;

			if (!Validator.equals(ppTxnId, shoppingOrder.getPpTxnId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			if (ppTxnId == null) {
				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_1);
			}
			else {
				if (ppTxnId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
				}
			}

			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ppTxnId != null) {
					qPos.add(ppTxnId);
				}

				List<ShoppingOrder> list = q.list();

				result = list;

				ShoppingOrder shoppingOrder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
						finderArgs, list);
				}
				else {
					shoppingOrder = list.get(0);

					cacheResult(shoppingOrder);

					if ((shoppingOrder.getPpTxnId() == null) ||
							!shoppingOrder.getPpTxnId().equals(ppTxnId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PPTXNID,
							finderArgs, shoppingOrder);
					}
				}

				return shoppingOrder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PPTXNID,
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
				return (ShoppingOrder)result;
			}
		}
	}

	/**
	 * Returns all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		return findByG_U_PPPS(groupId, userId, ppPaymentStatus,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end) throws SystemException {
		return findByG_U_PPPS(groupId, userId, ppPaymentStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_PPPS;
			finderArgs = new Object[] { groupId, userId, ppPaymentStatus };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_PPPS;
			finderArgs = new Object[] {
					groupId, userId, ppPaymentStatus,
					
					start, end, orderByComparator
				};
		}

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ShoppingOrder shoppingOrder : list) {
				if ((groupId != shoppingOrder.getGroupId()) ||
						(userId != shoppingOrder.getUserId()) ||
						!Validator.equals(ppPaymentStatus,
							shoppingOrder.getPpPaymentStatus())) {
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

			query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

			if (ppPaymentStatus == null) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
			}
			else {
				if (ppPaymentStatus.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (ppPaymentStatus != null) {
					qPos.add(ppPaymentStatus);
				}

				list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByG_U_PPPS_First(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByG_U_PPPS_First(groupId, userId,
				ppPaymentStatus, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", ppPaymentStatus=");
		msg.append(ppPaymentStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the first shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByG_U_PPPS_First(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator)
		throws SystemException {
		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder findByG_U_PPPS_Last(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = fetchByG_U_PPPS_Last(groupId, userId,
				ppPaymentStatus, orderByComparator);

		if (shoppingOrder != null) {
			return shoppingOrder;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", ppPaymentStatus=");
		msg.append(ppPaymentStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchOrderException(msg.toString());
	}

	/**
	 * Returns the last shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching shopping order, or <code>null</code> if a matching shopping order could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder fetchByG_U_PPPS_Last(long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_U_PPPS(groupId, userId, ppPaymentStatus);

		List<ShoppingOrder> list = findByG_U_PPPS(groupId, userId,
				ppPaymentStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the shopping orders before and after the current shopping order in the ordered set where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder[] findByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, String ppPaymentStatus,
		OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = getByG_U_PPPS_PrevAndNext(session, shoppingOrder,
					groupId, userId, ppPaymentStatus, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = getByG_U_PPPS_PrevAndNext(session, shoppingOrder,
					groupId, userId, ppPaymentStatus, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder getByG_U_PPPS_PrevAndNext(Session session,
		ShoppingOrder shoppingOrder, long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		if (ppPaymentStatus == null) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
		}
		else {
			if (ppPaymentStatus.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
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
			query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (ppPaymentStatus != null) {
			qPos.add(ppPaymentStatus);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingOrder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		return filterFindByG_U_PPPS(groupId, userId, ppPaymentStatus,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end) throws SystemException {
		return filterFindByG_U_PPPS(groupId, userId, ppPaymentStatus, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders that the user has permissions to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> filterFindByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PPPS(groupId, userId, ppPaymentStatus, start, end,
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
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		if (ppPaymentStatus == null) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
		}
		else {
			if (ppPaymentStatus.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (ppPaymentStatus != null) {
				qPos.add(ppPaymentStatus);
			}

			return (List<ShoppingOrder>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the shopping orders before and after the current shopping order in the ordered set of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param orderId the primary key of the current shopping order
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next shopping order
	 * @throws com.liferay.portlet.shopping.NoSuchOrderException if a shopping order with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder[] filterFindByG_U_PPPS_PrevAndNext(long orderId,
		long groupId, long userId, String ppPaymentStatus,
		OrderByComparator orderByComparator)
		throws NoSuchOrderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PPPS_PrevAndNext(orderId, groupId, userId,
				ppPaymentStatus, orderByComparator);
		}

		ShoppingOrder shoppingOrder = findByPrimaryKey(orderId);

		Session session = null;

		try {
			session = openSession();

			ShoppingOrder[] array = new ShoppingOrderImpl[3];

			array[0] = filterGetByG_U_PPPS_PrevAndNext(session, shoppingOrder,
					groupId, userId, ppPaymentStatus, orderByComparator, true);

			array[1] = shoppingOrder;

			array[2] = filterGetByG_U_PPPS_PrevAndNext(session, shoppingOrder,
					groupId, userId, ppPaymentStatus, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ShoppingOrder filterGetByG_U_PPPS_PrevAndNext(Session session,
		ShoppingOrder shoppingOrder, long groupId, long userId,
		String ppPaymentStatus, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		if (ppPaymentStatus == null) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
		}
		else {
			if (ppPaymentStatus.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ShoppingOrderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ShoppingOrderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ShoppingOrderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (ppPaymentStatus != null) {
			qPos.add(ppPaymentStatus);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(shoppingOrder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ShoppingOrder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the shopping orders.
	 *
	 * @return the shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @return the range of shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the shopping orders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of shopping orders
	 * @param end the upper bound of the range of shopping orders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public List<ShoppingOrder> findAll(int start, int end,
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

		List<ShoppingOrder> list = (List<ShoppingOrder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SHOPPINGORDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHOPPINGORDER.concat(ShoppingOrderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ShoppingOrder>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the shopping orders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByGroupId(groupId)) {
			remove(shoppingOrder);
		}
	}

	/**
	 * Removes the shopping order where number = &#63; from the database.
	 *
	 * @param number the number
	 * @return the shopping order that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder removeByNumber(String number)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByNumber(number);

		return remove(shoppingOrder);
	}

	/**
	 * Removes the shopping order where ppTxnId = &#63; from the database.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the shopping order that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ShoppingOrder removeByPPTxnId(String ppTxnId)
		throws NoSuchOrderException, SystemException {
		ShoppingOrder shoppingOrder = findByPPTxnId(ppTxnId);

		return remove(shoppingOrder);
	}

	/**
	 * Removes all the shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		for (ShoppingOrder shoppingOrder : findByG_U_PPPS(groupId, userId,
				ppPaymentStatus)) {
			remove(shoppingOrder);
		}
	}

	/**
	 * Removes all the shopping orders from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ShoppingOrder shoppingOrder : findAll()) {
			remove(shoppingOrder);
		}
	}

	/**
	 * Returns the number of shopping orders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

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
	 * Returns the number of shopping orders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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

	/**
	 * Returns the number of shopping orders where number = &#63;.
	 *
	 * @param number the number
	 * @return the number of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByNumber(String number) throws SystemException {
		Object[] finderArgs = new Object[] { number };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_NUMBER,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			if (number == null) {
				query.append(_FINDER_COLUMN_NUMBER_NUMBER_1);
			}
			else {
				if (number.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_3);
				}
				else {
					query.append(_FINDER_COLUMN_NUMBER_NUMBER_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (number != null) {
					qPos.add(number);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NUMBER,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping orders where ppTxnId = &#63;.
	 *
	 * @param ppTxnId the pp txn ID
	 * @return the number of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPPTxnId(String ppTxnId) throws SystemException {
		Object[] finderArgs = new Object[] { ppTxnId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PPTXNID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			if (ppTxnId == null) {
				query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_1);
			}
			else {
				if (ppTxnId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PPTXNID_PPTXNID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (ppTxnId != null) {
					qPos.add(ppTxnId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PPTXNID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping orders where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the number of matching shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_U_PPPS(long groupId, long userId, String ppPaymentStatus)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, userId, ppPaymentStatus };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U_PPPS,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_SHOPPINGORDER_WHERE);

			query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

			if (ppPaymentStatus == null) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
			}
			else {
				if (ppPaymentStatus.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (ppPaymentStatus != null) {
					qPos.add(ppPaymentStatus);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U_PPPS,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of shopping orders that the user has permission to view where groupId = &#63; and userId = &#63; and ppPaymentStatus = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param ppPaymentStatus the pp payment status
	 * @return the number of matching shopping orders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_U_PPPS(long groupId, long userId,
		String ppPaymentStatus) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_PPPS(groupId, userId, ppPaymentStatus);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_SHOPPINGORDER_WHERE);

		query.append(_FINDER_COLUMN_G_U_PPPS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_PPPS_USERID_2);

		if (ppPaymentStatus == null) {
			query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1);
		}
		else {
			if (ppPaymentStatus.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ShoppingOrder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			if (ppPaymentStatus != null) {
				qPos.add(ppPaymentStatus);
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

	/**
	 * Returns the number of shopping orders.
	 *
	 * @return the number of shopping orders
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SHOPPINGORDER);

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
	 * Initializes the shopping order persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.shopping.model.ShoppingOrder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ShoppingOrder>> listenersList = new ArrayList<ModelListener<ShoppingOrder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ShoppingOrder>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ShoppingOrderImpl.class.getName());
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
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	private static final String _SQL_SELECT_SHOPPINGORDER = "SELECT shoppingOrder FROM ShoppingOrder shoppingOrder";
	private static final String _SQL_SELECT_SHOPPINGORDER_WHERE = "SELECT shoppingOrder FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _SQL_COUNT_SHOPPINGORDER = "SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder";
	private static final String _SQL_COUNT_SHOPPINGORDER_WHERE = "SELECT COUNT(shoppingOrder) FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "shoppingOrder.groupId = ?";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_1 = "shoppingOrder.number IS NULL";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_2 = "shoppingOrder.number = ?";
	private static final String _FINDER_COLUMN_NUMBER_NUMBER_3 = "(shoppingOrder.number IS NULL OR shoppingOrder.number = ?)";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_1 = "shoppingOrder.ppTxnId IS NULL";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_2 = "shoppingOrder.ppTxnId = ?";
	private static final String _FINDER_COLUMN_PPTXNID_PPTXNID_3 = "(shoppingOrder.ppTxnId IS NULL OR shoppingOrder.ppTxnId = ?)";
	private static final String _FINDER_COLUMN_G_U_PPPS_GROUPID_2 = "shoppingOrder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_PPPS_USERID_2 = "shoppingOrder.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_1 = "shoppingOrder.ppPaymentStatus IS NULL";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_2 = "shoppingOrder.ppPaymentStatus = ?";
	private static final String _FINDER_COLUMN_G_U_PPPS_PPPAYMENTSTATUS_3 = "(shoppingOrder.ppPaymentStatus IS NULL OR shoppingOrder.ppPaymentStatus = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "shoppingOrder.orderId";
	private static final String _FILTER_SQL_SELECT_SHOPPINGORDER_WHERE = "SELECT DISTINCT {shoppingOrder.*} FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {ShoppingOrder.*} FROM (SELECT DISTINCT shoppingOrder.orderId FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _FILTER_SQL_SELECT_SHOPPINGORDER_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN ShoppingOrder ON TEMP_TABLE.orderId = ShoppingOrder.orderId";
	private static final String _FILTER_SQL_COUNT_SHOPPINGORDER_WHERE = "SELECT COUNT(DISTINCT shoppingOrder.orderId) AS COUNT_VALUE FROM ShoppingOrder shoppingOrder WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "shoppingOrder";
	private static final String _FILTER_ENTITY_TABLE = "ShoppingOrder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "shoppingOrder.";
	private static final String _ORDER_BY_ENTITY_TABLE = "ShoppingOrder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ShoppingOrder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ShoppingOrder exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderPersistenceImpl.class);
	private static ShoppingOrder _nullShoppingOrder = new ShoppingOrderImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ShoppingOrder> toCacheModel() {
				return _nullShoppingOrderCacheModel;
			}
		};

	private static CacheModel<ShoppingOrder> _nullShoppingOrderCacheModel = new CacheModel<ShoppingOrder>() {
			public ShoppingOrder toEntityModel() {
				return _nullShoppingOrder;
			}
		};
}