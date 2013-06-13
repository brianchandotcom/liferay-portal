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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl;

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
public class ShoppingCouponPersistenceTest {
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

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		Assert.assertNotNull(shoppingCoupon);

		Assert.assertEquals(shoppingCoupon.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.remove(newShoppingCoupon);

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertNull(existingShoppingCoupon);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShoppingCoupon();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCoupon newShoppingCoupon = _persistence.create(pk);

		newShoppingCoupon.setGroupId(ServiceTestUtil.nextLong());

		newShoppingCoupon.setCompanyId(ServiceTestUtil.nextLong());

		newShoppingCoupon.setUserId(ServiceTestUtil.nextLong());

		newShoppingCoupon.setUserName(ServiceTestUtil.randomString());

		newShoppingCoupon.setCreateDate(ServiceTestUtil.nextDate());

		newShoppingCoupon.setModifiedDate(ServiceTestUtil.nextDate());

		newShoppingCoupon.setCode(ServiceTestUtil.randomString());

		newShoppingCoupon.setName(ServiceTestUtil.randomString());

		newShoppingCoupon.setDescription(ServiceTestUtil.randomString());

		newShoppingCoupon.setStartDate(ServiceTestUtil.nextDate());

		newShoppingCoupon.setEndDate(ServiceTestUtil.nextDate());

		newShoppingCoupon.setActive(ServiceTestUtil.randomBoolean());

		newShoppingCoupon.setLimitCategories(ServiceTestUtil.randomString());

		newShoppingCoupon.setLimitSkus(ServiceTestUtil.randomString());

		newShoppingCoupon.setMinOrder(ServiceTestUtil.nextDouble());

		newShoppingCoupon.setDiscount(ServiceTestUtil.nextDouble());

		newShoppingCoupon.setDiscountType(ServiceTestUtil.randomString());

		_persistence.update(newShoppingCoupon);

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon.getCouponId(),
			newShoppingCoupon.getCouponId());
		Assert.assertEquals(existingShoppingCoupon.getGroupId(),
			newShoppingCoupon.getGroupId());
		Assert.assertEquals(existingShoppingCoupon.getCompanyId(),
			newShoppingCoupon.getCompanyId());
		Assert.assertEquals(existingShoppingCoupon.getUserId(),
			newShoppingCoupon.getUserId());
		Assert.assertEquals(existingShoppingCoupon.getUserName(),
			newShoppingCoupon.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getCreateDate()),
			Time.getShortTimestamp(newShoppingCoupon.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCoupon.getModifiedDate()));
		Assert.assertEquals(existingShoppingCoupon.getCode(),
			newShoppingCoupon.getCode());
		Assert.assertEquals(existingShoppingCoupon.getName(),
			newShoppingCoupon.getName());
		Assert.assertEquals(existingShoppingCoupon.getDescription(),
			newShoppingCoupon.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getStartDate()),
			Time.getShortTimestamp(newShoppingCoupon.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getEndDate()),
			Time.getShortTimestamp(newShoppingCoupon.getEndDate()));
		Assert.assertEquals(existingShoppingCoupon.getActive(),
			newShoppingCoupon.getActive());
		Assert.assertEquals(existingShoppingCoupon.getLimitCategories(),
			newShoppingCoupon.getLimitCategories());
		Assert.assertEquals(existingShoppingCoupon.getLimitSkus(),
			newShoppingCoupon.getLimitSkus());
		AssertUtils.assertEquals(existingShoppingCoupon.getMinOrder(),
			newShoppingCoupon.getMinOrder());
		AssertUtils.assertEquals(existingShoppingCoupon.getDiscount(),
			newShoppingCoupon.getDiscount());
		Assert.assertEquals(existingShoppingCoupon.getDiscountType(),
			newShoppingCoupon.getDiscountType());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCouponException");
		}
		catch (NoSuchCouponException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ShoppingCoupon",
			"couponId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"code", true, "name", true, "description", true, "startDate", true,
			"endDate", true, "active", true, "limitCategories", true,
			"limitSkus", true, "minOrder", true, "discount", true,
			"discountType", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCoupon missingShoppingCoupon = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShoppingCoupon);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ShoppingCouponActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ShoppingCoupon shoppingCoupon = (ShoppingCoupon)object;

					Assert.assertNotNull(shoppingCoupon);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId",
				newShoppingCoupon.getCouponId()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ShoppingCoupon existingShoppingCoupon = result.get(0);

		Assert.assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId",
				ServiceTestUtil.nextLong()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("couponId"));

		Object newCouponId = newShoppingCoupon.getCouponId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("couponId",
				new Object[] { newCouponId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCouponId = result.get(0);

		Assert.assertEquals(existingCouponId, newCouponId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("couponId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("couponId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.clearCache();

		ShoppingCouponModelImpl existingShoppingCouponModelImpl = (ShoppingCouponModelImpl)_persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingShoppingCouponModelImpl.getCode(),
				existingShoppingCouponModelImpl.getOriginalCode()));
	}

	protected ShoppingCoupon addShoppingCoupon() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		shoppingCoupon.setGroupId(ServiceTestUtil.nextLong());

		shoppingCoupon.setCompanyId(ServiceTestUtil.nextLong());

		shoppingCoupon.setUserId(ServiceTestUtil.nextLong());

		shoppingCoupon.setUserName(ServiceTestUtil.randomString());

		shoppingCoupon.setCreateDate(ServiceTestUtil.nextDate());

		shoppingCoupon.setModifiedDate(ServiceTestUtil.nextDate());

		shoppingCoupon.setCode(ServiceTestUtil.randomString());

		shoppingCoupon.setName(ServiceTestUtil.randomString());

		shoppingCoupon.setDescription(ServiceTestUtil.randomString());

		shoppingCoupon.setStartDate(ServiceTestUtil.nextDate());

		shoppingCoupon.setEndDate(ServiceTestUtil.nextDate());

		shoppingCoupon.setActive(ServiceTestUtil.randomBoolean());

		shoppingCoupon.setLimitCategories(ServiceTestUtil.randomString());

		shoppingCoupon.setLimitSkus(ServiceTestUtil.randomString());

		shoppingCoupon.setMinOrder(ServiceTestUtil.nextDouble());

		shoppingCoupon.setDiscount(ServiceTestUtil.nextDouble());

		shoppingCoupon.setDiscountType(ServiceTestUtil.randomString());

		_persistence.update(shoppingCoupon);

		return shoppingCoupon;
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingCouponPersistenceTest.class);
	private ShoppingCouponPersistence _persistence = (ShoppingCouponPersistence)PortalBeanLocatorUtil.locate(ShoppingCouponPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}