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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.shopping.NoSuchCategoryException;
import com.liferay.portlet.shopping.model.ShoppingCategory;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingCategoryPersistence)PortalBeanLocatorUtil.locate(ShoppingCategoryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		assertNotNull(shoppingCategory);

		assertEquals(shoppingCategory.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		_persistence.remove(newShoppingCategory);

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		assertNull(existingShoppingCategory);
	}

	public void testUpdateNew() throws Exception {
		addShoppingCategory();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingCategory newShoppingCategory = _persistence.create(pk);

		newShoppingCategory.setGroupId(nextLong());

		newShoppingCategory.setCompanyId(nextLong());

		newShoppingCategory.setUserId(nextLong());

		newShoppingCategory.setUserName(randomString());

		newShoppingCategory.setCreateDate(nextDate());

		newShoppingCategory.setModifiedDate(nextDate());

		newShoppingCategory.setParentCategoryId(nextLong());

		newShoppingCategory.setName(randomString());

		newShoppingCategory.setDescription(randomString());

		_persistence.update(newShoppingCategory, false);

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		assertEquals(existingShoppingCategory.getCategoryId(),
			newShoppingCategory.getCategoryId());
		assertEquals(existingShoppingCategory.getGroupId(),
			newShoppingCategory.getGroupId());
		assertEquals(existingShoppingCategory.getCompanyId(),
			newShoppingCategory.getCompanyId());
		assertEquals(existingShoppingCategory.getUserId(),
			newShoppingCategory.getUserId());
		assertEquals(existingShoppingCategory.getUserName(),
			newShoppingCategory.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getCreateDate()),
			Time.getShortTimestamp(newShoppingCategory.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingShoppingCategory.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCategory.getModifiedDate()));
		assertEquals(existingShoppingCategory.getParentCategoryId(),
			newShoppingCategory.getParentCategoryId());
		assertEquals(existingShoppingCategory.getName(),
			newShoppingCategory.getName());
		assertEquals(existingShoppingCategory.getDescription(),
			newShoppingCategory.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.findByPrimaryKey(newShoppingCategory.getPrimaryKey());

		assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		ShoppingCategory existingShoppingCategory = _persistence.fetchByPrimaryKey(newShoppingCategory.getPrimaryKey());

		assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingCategory missingShoppingCategory = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingCategory);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newShoppingCategory.getCategoryId()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingCategory existingShoppingCategory = result.get(0);

		assertEquals(existingShoppingCategory, newShoppingCategory);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId", nextLong()));

		List<ShoppingCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShoppingCategory newShoppingCategory = addShoppingCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newShoppingCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		assertEquals(existingCategoryId, newCategoryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCategory.class,
				ShoppingCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ShoppingCategory addShoppingCategory() throws Exception {
		long pk = nextLong();

		ShoppingCategory shoppingCategory = _persistence.create(pk);

		shoppingCategory.setGroupId(nextLong());

		shoppingCategory.setCompanyId(nextLong());

		shoppingCategory.setUserId(nextLong());

		shoppingCategory.setUserName(randomString());

		shoppingCategory.setCreateDate(nextDate());

		shoppingCategory.setModifiedDate(nextDate());

		shoppingCategory.setParentCategoryId(nextLong());

		shoppingCategory.setName(randomString());

		shoppingCategory.setDescription(randomString());

		_persistence.update(shoppingCategory, false);

		return shoppingCategory;
	}

	private ShoppingCategoryPersistence _persistence;
}