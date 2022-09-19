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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.web.internal.util.PortalPreferencesUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portlet.PortalPreferencesImpl;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class PortalPreferencesUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpdateAndGetSortedPreferencesValues() {
		PortalPreferences portalPreferences = new MockPortalPreferencesImpl();

		PortalPreferencesUtil.updateSortedPortalPreferencesValues(
			portalPreferences, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new String[] {"a", "b", "c"});

		Assert.assertEquals(
			ListUtil.fromArray("a", "b", "c"),
			PortalPreferencesUtil.getSortedPortalPreferencesValues(
				portalPreferences, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()));

		PortalPreferencesUtil.updateSortedPortalPreferencesValues(
			portalPreferences, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new String[] {"c", "a", "b"});

		Assert.assertEquals(
			ListUtil.fromArray("c", "a", "b"),
			PortalPreferencesUtil.getSortedPortalPreferencesValues(
				portalPreferences, RandomTestUtil.randomString(),
				RandomTestUtil.randomString()));
	}

	private static class MockPortalPreferencesImpl
		extends PortalPreferencesImpl {

		@Override
		public String[] getValues(
			String namespace, String key, String[] defaultValue) {

			return _values;
		}

		@Override
		public void setValues(String namespace, String key, String[] values) {
			Set<String> valuesSet = SetUtil.fromArray(values);

			if (valuesSet.equals(SetUtil.fromArray(_values))) {
				return;
			}

			_values = values;
		}

		private String[] _values;

	}

}