/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.type.controller.link.to.page.internal.layout.type.controller.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class LinkToPageLayoutTypeControllerTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				LayoutConstants.TYPE_LINK_TO_LAYOUT);
	}

	@Test
	public void testGetTypeSettingsProperties() throws Exception {
		Layout linkToLayout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout layout = LayoutTestUtil.addTypeLinkToLayoutLayout(
			_group.getGroupId(), linkToLayout.getLayoutId());

		UnicodeProperties typeSettingsUnicodeProperties =
			_layoutTypeController.getTypeSettingsProperties(layout);

		Assert.assertEquals(
			String.valueOf(linkToLayout.getLayoutId()),
			typeSettingsUnicodeProperties.getProperty("linkToLayoutId"));

		typeSettingsUnicodeProperties.put(
			"linkToLayoutExternalReferenceCode",
			linkToLayout.getExternalReferenceCode());

		typeSettingsUnicodeProperties.put("linkToLayoutId", null);

		layout.setTypeSettingsProperties(typeSettingsUnicodeProperties);

		typeSettingsUnicodeProperties =
			_layoutTypeController.getTypeSettingsProperties(layout);

		Assert.assertEquals(
			String.valueOf(linkToLayout.getLayoutId()),
			typeSettingsUnicodeProperties.getProperty("linkToLayoutId"));
	}

	private Group _group;
	private LayoutTypeController _layoutTypeController;

}