/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class WidgetLayoutTypeSettingsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRemoveWidgetLayoutTypeSettings() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		String key = RandomTestUtil.randomString();

		layout = _layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			UnicodePropertiesBuilder.put(
				key, "true"
			).put(
				LayoutConstants.CUSTOMIZABLE_LAYOUT, "true"
			).put(
				LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column"
			).put(
				"column-1-customizable", "false"
			).put(
				"column-2-customizable", "false"
			).build(
			).toString());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.data.cleanup.internal.configuration." +
						"DataRemovalConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"removeWidgetLayoutTypeSettings", true
					).build())) {

			Layout curLayout = _layoutLocalService.getLayout(layout.getPlid());

			UnicodeProperties typeSettingsUnicodeProperties =
				curLayout.getTypeSettingsProperties();

			Assert.assertTrue(
				GetterUtil.getBoolean(typeSettingsUnicodeProperties.get(key)));
			Assert.assertNull(
				typeSettingsUnicodeProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));
			Assert.assertNull(
				typeSettingsUnicodeProperties.get(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID));

			for (String curKey : typeSettingsUnicodeProperties.keySet()) {
				Assert.assertFalse(curKey.startsWith("column-"));
			}
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

}