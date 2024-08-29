/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.configuration.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class PortletConfigurationPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetPortletTitle() throws Exception {
		String portletId = LayoutTestUtil.addPortletToLayout(
			_layout, MBPortletKeys.MESSAGE_BOARDS,
			HashMapBuilder.put(
				"portletSetupTitle_en_US",
				new String[] {RandomTestUtil.randomString()}
			).put(
				"portletSetupUseCustomTitle",
				new String[] {Boolean.TRUE.toString()}
			).build());

		Assert.assertEquals(
			"掲示板",
			PortletConfigurationUtil.getPortletTitle(
				portletId,
				_portletPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
					portletId),
				LocaleUtil.toLanguageId(LocaleUtil.JAPANESE)));
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}