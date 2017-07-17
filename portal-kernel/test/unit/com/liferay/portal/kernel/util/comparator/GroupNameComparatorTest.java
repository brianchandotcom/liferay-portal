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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
public class GroupNameComparatorTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			PropsUtil.class, "_props",
			ProxyUtil.newProxyInstance(
				GroupNameComparatorTest.class.getClassLoader(),
				new Class<?>[] {Props.class}, (proxy, method, args) -> null));

		setUpGroups();
	}

	@Test
	public void testCompareLocalized() {
		GroupNameComparator groupNameComparator = new GroupNameComparator(
			true, LocaleUtil.SPAIN);

		int value = groupNameComparator.compare(_group1, _group2);

		Assert.assertTrue(value < 0);
	}

	protected void setUpGroups() {
		_group1 = new GroupWrapper(null) {

			@Override
			public String getName(String languageId) {
				Assert.assertEquals("es_ES", languageId);

				return "Área";
			}

		};

		_group2 = new GroupWrapper(null) {

			@Override
			public String getName(String languageId) {
				Assert.assertEquals("es_ES", languageId);

				return "Zona";
			}

		};
	}

	private Group _group1;
	private Group _group2;

}