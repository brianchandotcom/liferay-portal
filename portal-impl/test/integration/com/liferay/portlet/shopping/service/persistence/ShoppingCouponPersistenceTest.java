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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shopping.NoSuchCouponException;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCouponPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingCouponPersistence)PortalBeanLocatorUtil.locate(ShoppingCouponPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		assertNotNull(shoppingCoupon);

		assertEquals(shoppingCoupon.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.remove(newShoppingCoupon);

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		assertNull(existingShoppingCoupon);
	}

	public void testUpdateNew() throws Exception {
		addShoppingCoupon();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingCoupon newShoppingCoupon = _persistence.create(pk);

		newShoppingCoupon.setGroupId(nextLong());

		newShoppingCoupon.setCompanyId(nextLong());

		newShoppingCoupon.setUserId(nextLong());

		newShoppingCoupon.setUserName(randomString());

		newShoppingCoupon.setCreateDate(nextDate());

		newShoppingCoupon.setModifiedDate(nextDate());

		newShoppingCoupon.setCode(randomString());

		newShoppingCoupon.setName(randomString());

		newShoppingCoupon.setDescription(randomString());

		newShoppingCoupon.setStartDate(nextDate());

		newShoppingCoupon.setEndDate(nextDate());

		newShoppingCoupon.setActive(randomBoolean());

		newShoppingCoupon.setLimitCategories(randomString());

		newShoppingCoupon.setLimitSkus(randomString());

		newShoppingCoupon.setMinOrder(nextDouble());

		newShoppingCoupon.setDiscount(nextDouble());

		newShoppingCoupon.setDiscountType(randomString());

		_persistence.update(newShoppingCoupon, false);

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		assertEquals(existingShoppingCoupon.getCouponId(),
			newShoppingCoupon.getCouponId());
		assertEquals(existingShoppingCoupon.getGroupId(),
			newShoppingCoupon.getGroupId());
		assertEquals(existingShoppingCoupon.getCompanyId(),
			newShoppingCoupon.getCompanyId());
		assertEquals(existingShoppingCoupon.getUserId(),
			newShoppingCoupon.getUserId());
		assertEquals(existingShoppingCoupon.getUserName(),
			newShoppingCoupon.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getCreateDate()),
			Time.getShortTimestamp(newShoppingCoupon.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCoupon.getModifiedDate()));
		assertEquals(existingShoppingCoupon.getCode(),
			newShoppingCoupon.getCode());
		assertEquals(existingShoppingCoupon.getName(),
			newShoppingCoupon.getName());
		assertEquals(existingShoppingCoupon.getDescription(),
			newShoppingCoupon.getDescription());
		assertEquals(Time.getShortTimestamp(
				existingShoppingCoupon.getStartDate()),
			Time.getShortTimestamp(newShoppingCoupon.getStartDate()));
		assertEquals(Time.getShortTimestamp(existingShoppingCoupon.getEndDate()),
			Time.getShortTimestamp(newShoppingCoupon.getEndDate()));
		assertEquals(existingShoppingCoupon.getActive(),
			newShoppingCoupon.getActive());
		assertEquals(existingShoppingCoupon.getLimitCategories(),
			newShoppingCoupon.getLimitCategories());
		assertEquals(existingShoppingCoupon.getLimitSkus(),
			newShoppingCoupon.getLimitSkus());
		assertEquals(existingShoppingCoupon.getMinOrder(),
			newShoppingCoupon.getMinOrder());
		assertEquals(existingShoppingCoupon.getDiscount(),
			newShoppingCoupon.getDiscount());
		assertEquals(existingShoppingCoupon.getDiscountType(),
			newShoppingCoupon.getDiscountType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCouponException");
		}
		catch (NoSuchCouponException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		ShoppingCoupon existingShoppingCoupon = _persistence.fetchByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingCoupon missingShoppingCoupon = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingCoupon);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId",
				newShoppingCoupon.getCouponId()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingCoupon existingShoppingCoupon = result.get(0);

		assertEquals(existingShoppingCoupon, newShoppingCoupon);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("couponId", nextLong()));

		List<ShoppingCoupon> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingCouponId = result.get(0);

		assertEquals(existingCouponId, newCouponId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCoupon.class,
				ShoppingCoupon.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("couponId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("couponId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShoppingCoupon newShoppingCoupon = addShoppingCoupon();

		_persistence.clearCache();

		ShoppingCouponModelImpl existingShoppingCouponModelImpl = (ShoppingCouponModelImpl)_persistence.findByPrimaryKey(newShoppingCoupon.getPrimaryKey());

		assertTrue(Validator.equals(existingShoppingCouponModelImpl.getCode(),
				existingShoppingCouponModelImpl.getOriginalCode()));
	}

	protected ShoppingCoupon addShoppingCoupon() throws Exception {
		long pk = nextLong();

		ShoppingCoupon shoppingCoupon = _persistence.create(pk);

		shoppingCoupon.setGroupId(nextLong());

		shoppingCoupon.setCompanyId(nextLong());

		shoppingCoupon.setUserId(nextLong());

		shoppingCoupon.setUserName(randomString());

		shoppingCoupon.setCreateDate(nextDate());

		shoppingCoupon.setModifiedDate(nextDate());

		shoppingCoupon.setCode(randomString());

		shoppingCoupon.setName(randomString());

		shoppingCoupon.setDescription(randomString());

		shoppingCoupon.setStartDate(nextDate());

		shoppingCoupon.setEndDate(nextDate());

		shoppingCoupon.setActive(randomBoolean());

		shoppingCoupon.setLimitCategories(randomString());

		shoppingCoupon.setLimitSkus(randomString());

		shoppingCoupon.setMinOrder(nextDouble());

		shoppingCoupon.setDiscount(nextDouble());

		shoppingCoupon.setDiscountType(randomString());

		_persistence.update(shoppingCoupon, false);

		return shoppingCoupon;
	}

	private ShoppingCouponPersistence _persistence;
}