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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchCartException;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.impl.ShoppingCartModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ShoppingCartPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCart shoppingCart = _persistence.create(pk);

		Assert.assertNotNull(shoppingCart);

		Assert.assertEquals(shoppingCart.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		_persistence.remove(newShoppingCart);

		ShoppingCart existingShoppingCart = _persistence.fetchByPrimaryKey(newShoppingCart.getPrimaryKey());

		Assert.assertNull(existingShoppingCart);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCart();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCart newShoppingCart = _persistence.create(pk);

		newShoppingCart.setGroupId(ServiceTestUtil.nextLong());

		newShoppingCart.setCompanyId(ServiceTestUtil.nextLong());

		newShoppingCart.setUserId(ServiceTestUtil.nextLong());

		newShoppingCart.setUserName(ServiceTestUtil.randomString());

		newShoppingCart.setCreateDate(ServiceTestUtil.nextDate());

		newShoppingCart.setModifiedDate(ServiceTestUtil.nextDate());

		newShoppingCart.setItemIds(ServiceTestUtil.randomString());

		newShoppingCart.setCouponCodes(ServiceTestUtil.randomString());

		newShoppingCart.setAltShipping(ServiceTestUtil.nextInt());

		newShoppingCart.setInsure(ServiceTestUtil.randomBoolean());

		_persistence.update(newShoppingCart);

		ShoppingCart existingShoppingCart = _persistence.findByPrimaryKey(newShoppingCart.getPrimaryKey());

		Assert.assertEquals(existingShoppingCart.getCartId(),
			newShoppingCart.getCartId());
		Assert.assertEquals(existingShoppingCart.getGroupId(),
			newShoppingCart.getGroupId());
		Assert.assertEquals(existingShoppingCart.getCompanyId(),
			newShoppingCart.getCompanyId());
		Assert.assertEquals(existingShoppingCart.getUserId(),
			newShoppingCart.getUserId());
		Assert.assertEquals(existingShoppingCart.getUserName(),
			newShoppingCart.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCart.getCreateDate()),
			Time.getShortTimestamp(newShoppingCart.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCart.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCart.getModifiedDate()));
		Assert.assertEquals(existingShoppingCart.getItemIds(),
			newShoppingCart.getItemIds());
		Assert.assertEquals(existingShoppingCart.getCouponCodes(),
			newShoppingCart.getCouponCodes());
		Assert.assertEquals(existingShoppingCart.getAltShipping(),
			newShoppingCart.getAltShipping());
		Assert.assertEquals(existingShoppingCart.getInsure(),
			newShoppingCart.getInsure());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		ShoppingCart existingShoppingCart = _persistence.findByPrimaryKey(newShoppingCart.getPrimaryKey());

		Assert.assertEquals(existingShoppingCart, newShoppingCart);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCartException");
		}
		catch (NoSuchCartException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		ShoppingCart existingShoppingCart = _persistence.fetchByPrimaryKey(newShoppingCart.getPrimaryKey());

		Assert.assertEquals(existingShoppingCart, newShoppingCart);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCart missingShoppingCart = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCart);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("cartId",
				newShoppingCart.getCartId()));

		List<ShoppingCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCart existingShoppingCart = result.get(0);

		Assert.assertEquals(existingShoppingCart, newShoppingCart);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("cartId",
				ServiceTestUtil.nextLong()));

		List<ShoppingCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("cartId"));

		Object newCartId = newShoppingCart.getCartId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("cartId",
				new Object[] { newCartId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCartId = result.get(0);

		Assert.assertEquals(existingCartId, newCartId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("cartId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("cartId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingCart newShoppingCart = addShoppingCart();

		_persistence.clearCache();

		ShoppingCartModelImpl existingShoppingCartModelImpl = (ShoppingCartModelImpl)_persistence.findByPrimaryKey(newShoppingCart.getPrimaryKey());

		Assert.assertEquals(existingShoppingCartModelImpl.getGroupId(),
			existingShoppingCartModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingShoppingCartModelImpl.getUserId(),
			existingShoppingCartModelImpl.getOriginalUserId());
	}

	protected ShoppingCart addShoppingCart() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCart shoppingCart = _persistence.create(pk);

		shoppingCart.setGroupId(ServiceTestUtil.nextLong());

		shoppingCart.setCompanyId(ServiceTestUtil.nextLong());

		shoppingCart.setUserId(ServiceTestUtil.nextLong());

		shoppingCart.setUserName(ServiceTestUtil.randomString());

		shoppingCart.setCreateDate(ServiceTestUtil.nextDate());

		shoppingCart.setModifiedDate(ServiceTestUtil.nextDate());

		shoppingCart.setItemIds(ServiceTestUtil.randomString());

		shoppingCart.setCouponCodes(ServiceTestUtil.randomString());

		shoppingCart.setAltShipping(ServiceTestUtil.nextInt());

		shoppingCart.setInsure(ServiceTestUtil.randomBoolean());

		_persistence.update(shoppingCart);

		return shoppingCart;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCartPersistenceTest.class);
	private ShoppingCartPersistence _persistence = (ShoppingCartPersistence)PortalBeanLocatorUtil.locate(ShoppingCartPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}