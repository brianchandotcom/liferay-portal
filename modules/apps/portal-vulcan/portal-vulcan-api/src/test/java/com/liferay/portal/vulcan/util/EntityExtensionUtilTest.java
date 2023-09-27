/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import junit.framework.Assert;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Luis Miguel Barcos
 * @author Javier de Arcos
 */
public class EntityExtensionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExtend() throws Exception {
		BaseEntity baseEntity = new BaseEntity();

		baseEntity.setId(RandomTestUtil.randomLong());
		baseEntity.setName(RandomTestUtil.randomString());

		String description = RandomTestUtil.randomString();

		ExtendedEntity extendedEntity = EntityExtensionUtil.extend(
			baseEntity, BaseEntity.class, ExtendedEntity.class,
			unsafeConsumerExtendedEntity ->
				unsafeConsumerExtendedEntity.setDescription(description));

		Assert.assertNotNull(extendedEntity);
		Assert.assertEquals(baseEntity.getId(), extendedEntity.getId());
		Assert.assertEquals(baseEntity.getName(), extendedEntity.getName());
		Assert.assertEquals(description, extendedEntity.getDescription());
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

		public String getDescription() {
			return _description;
		}

		public void setDescription(String description) {
			_description = description;
		}

		private String _description;

	}

}