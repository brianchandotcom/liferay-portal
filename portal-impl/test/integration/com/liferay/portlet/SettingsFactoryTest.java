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
import com.liferay.portal.service.ServiceTestUtil;
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
public class SettingsFactoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		_preferenceMap = new HashMap<String, String[]>();

		_preferenceMap.put(_NAME, _VALUES);

		_portletId = LayoutTestUtil.addPortletToLayout(
			_layout, _portletId, _preferenceMap);
	}

	@Test
	public void testGetPortletInstanceSettings() throws Exception {
		Settings portletInstanceSettings =
			_settingsFactory.getPortletInstanceSettings(_layout, _portletId);

		String[] values = portletInstanceSettings.getValues(_NAME, null);

		Assert.assertArrayEquals(_VALUES, values);
	}

	protected static final String _NAME = "name";

	protected static final String[] _VALUES = {"values"};

	private Group _group;
	private Layout _layout;
	private String _portletId = PortletKeys.NAVIGATION;
	private Map<String, String[]> _preferenceMap;
	private SettingsFactory _settingsFactory = new SettingsFactoryImpl();

}