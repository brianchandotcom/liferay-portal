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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author Luis Miguel Barcos
 * @author Javier de Arcos
 */
public class EntityExtensionUtilTest {

	@Test
	public void testExtend() throws Exception {
		BaseEntity baseEntity = new BaseEntity();

		baseEntity.setId(RandomTestUtil.randomLong());
		baseEntity.setName(RandomTestUtil.randomString());

		String extendedProperty = RandomTestUtil.randomString();

		ExtendedEntity testExtendedEntity = EntityExtensionUtil.extend(
			baseEntity, BaseEntity.class, ExtendedEntity.class,
			extendedEntity -> extendedEntity.setExtendedProperty(
				extendedProperty));

		Assert.assertNotNull(testExtendedEntity);
		Assert.assertEquals(baseEntity.getId(), testExtendedEntity.getId());
		Assert.assertEquals(baseEntity.getName(), testExtendedEntity.getName());
		Assert.assertEquals(
			extendedProperty, testExtendedEntity.getExtendedProperty());
	}

	public static class BaseEntity {

		public long getId() {
			return _id;
		}

		public String getName() {
			return _name;
		}

		public void setId(long id) {
			_id = id;
		}

		public void setName(String name) {
			_name = name;
		}

		private long _id;
		private String _name;

	}

	public static class ExtendedEntity extends BaseEntity {

		public String getExtendedProperty() {
			return _extendedProperty;
		}

		public void setExtendedProperty(String extendedProperty) {
			_extendedProperty = extendedProperty;
		}

		private String _extendedProperty;

	}

}