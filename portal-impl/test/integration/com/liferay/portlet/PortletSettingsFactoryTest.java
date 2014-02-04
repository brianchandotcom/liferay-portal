/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Ferrer
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortletSettingsFactoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_layout = LayoutTestUtil.addLayout(_group.getGroupId(), "testSettings");

		_preferenceMap = new HashMap<String, String[]>();

		_preferenceMap.put(_TEST_SETTINGS_NAME, _TEST_SETTINGS_VALUES);

		_portletId = LayoutTestUtil.addPortletToLayout(
			_layout, _portletId, _preferenceMap);
	}

	@Test
	public void testPortletInstanceSettings() throws Exception {
		PortletSettings portletInstanceSettings =
			_portletSettingsFactory.getInstancePortletSettings(
				_layout, _portletId);

		String[] values = portletInstanceSettings.getValues(
			_TEST_SETTINGS_NAME, null);

		Assert.assertArrayEquals(
			"The value obtained does not correspond to the preferences set.",
			_TEST_SETTINGS_VALUES, values);
	}

	protected static final String _TEST_SETTINGS_NAME = "testSettingsName";

	protected static final String[] _TEST_SETTINGS_VALUES =
		{"testSettingsValue"};

	private Group _group;
	private Layout _layout;
	private String _portletId = PortletKeys.NAVIGATION;
	private PortletSettingsFactory _portletSettingsFactory =
		new PortletSettingsFactoryImpl();
	private Map<String, String[]> _preferenceMap;

}