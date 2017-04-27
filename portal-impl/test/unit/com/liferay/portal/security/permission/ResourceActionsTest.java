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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class ResourceActionsTest {

	@Before
	public void setUp() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		unsecureSAXReaderUtil.setSAXReader(new SAXReaderImpl());

		ResourceActionsUtil resourceActionsUtil = new ResourceActionsUtil();

		ResourceActionsImpl resourceActionsImpl = new ResourceActionsImpl();

		ReflectionTestUtil.setFieldValue(
			resourceActionsImpl, "portletLocalService",
			ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {PortletLocalService.class},
				(proxy, method, args) -> new PortletImpl(
					RandomTestUtil.randomLong(), (String)args[0])));

		resourceActionsImpl.afterPropertiesSet();

		resourceActionsUtil.setResourceActions(resourceActionsImpl);

		ResourceActionsUtil.read(
			null, _classLoader, _SOURCE_PATH + "default.xml");
	}

	@Test
	public void testRemovePortletResource() {
		List<String> portletNames = ResourceActionsUtil.getPortletNames();

		Assert.assertTrue(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_1));
		Assert.assertTrue(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_2));

		List<String> modelNames = ResourceActionsUtil.getModelNames();

		Assert.assertTrue(
			modelNames.toString(), modelNames.contains(_MODEL_NAME));

		ResourceActionsUtil.removePortletResource(_PORTLET_NAME_1);

		portletNames = ResourceActionsUtil.getPortletNames();

		Assert.assertFalse(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_1));
		Assert.assertTrue(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_2));

		modelNames = ResourceActionsUtil.getModelNames();

		Assert.assertTrue(
			modelNames.toString(), modelNames.contains(_MODEL_NAME));

		ResourceActionsUtil.removePortletResource(_PORTLET_NAME_2);

		portletNames = ResourceActionsUtil.getPortletNames();

		Assert.assertFalse(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_1));
		Assert.assertFalse(
			portletNames.toString(), portletNames.contains(_PORTLET_NAME_2));

		modelNames = ResourceActionsUtil.getModelNames();

		Assert.assertFalse(
			modelNames.toString(), modelNames.contains(_MODEL_NAME));
	}

	private static final String _MODEL_NAME =
		"com.liferay.test.portlet.TestModel";

	private static final String _PORTLET_NAME_1 =
		"com_liferay_test_portlet_TestPortlet1";

	private static final String _PORTLET_NAME_2 =
		"com_liferay_test_portlet_TestPortlet2";

	private static final String _SOURCE_PATH =
		"com/liferay/portal/security/permission/dependencies/";

	private static final ClassLoader _classLoader =
		ResourceActionsTest.class.getClassLoader();

}