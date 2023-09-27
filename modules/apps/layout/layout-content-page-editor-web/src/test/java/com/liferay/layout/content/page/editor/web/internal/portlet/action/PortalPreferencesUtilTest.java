/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.util.PortalPreferencesUtil;
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
	public void testUpdateAndGetSortedPortalPreferencesValues() {
		PortalPreferences portalPreferences = new TestPortalPreferencesImpl();

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

	private static class TestPortalPreferencesImpl
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