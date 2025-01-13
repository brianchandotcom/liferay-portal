/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.LayoutSetJavaScriptException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class LayoutSetLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = LayoutSetJavaScriptException.class)
	public void testUpdateLayoutSetWithJavaScriptIvalidValue1()
		throws Exception {

		_layoutSetLocalService.updateSettings(
			_group.getGroupId(), false,
			UnicodePropertiesBuilder.put(
				"javascript", "<script>"
			).buildString());
	}

	@Test(expected = LayoutSetJavaScriptException.class)
	public void testUpdateLayoutSetWithJavaScriptIvalidValue2()
		throws Exception {

		_layoutSetLocalService.updateSettings(
			_group.getGroupId(), false,
			UnicodePropertiesBuilder.put(
				"javascript", "</script>"
			).buildString());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

}