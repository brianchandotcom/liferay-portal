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

package com.liferay.configuration.admin.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.util.ConfigurationMVCRenderCommandUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.osgi.util.test.OSGiServiceUtil;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class ConfigurationMVCRenderCommandUtilTest {

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(
			ConfigurationMVCRenderCommandUtilTest.class);

		_bundleContext = _bundle.getBundleContext();

		_configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.createFactoryConfiguration(
				"test.pid", StringPool.QUESTION));

		_properties = new Hashtable<>();

		_properties.put("configurationPid", _configuration.getPid());
		_properties.put(
			"javax.portlet.name",
			ConfigurationAdminPortletKeys.SYSTEM_SETTINGS);
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetEditConfigurationMVCRenderCommand() throws Exception {
		MVCRenderCommand renderCommand =
			(renderRequest, renderResponse) -> StringPool.BLANK;

		_properties.put("mvc.command.name", "/edit_configuration");

		_serviceRegistration = _bundleContext.registerService(
			MVCRenderCommand.class, renderCommand, _properties);

		Assert.assertSame(
			renderCommand,
			ConfigurationMVCRenderCommandUtil.
				getEditConfigurationMVCRenderCommand(_configuration.getPid()));
	}

	@Test
	public void testGetFactoryInstancesListRenderCommand() {
		MVCRenderCommand renderCommand =
			(renderRequest, renderResponse) -> StringPool.BLANK;

		_properties.put("mvc.command.name", "/view_factory_instances");

		_serviceRegistration = _bundleContext.registerService(
			MVCRenderCommand.class, renderCommand, _properties);

		Assert.assertSame(
			renderCommand,
			ConfigurationMVCRenderCommandUtil.
				getViewFactoryInstancesMVCRenderCommand(
					_configuration.getPid()));
	}

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private Configuration _configuration;
	private Dictionary<String, Object> _properties;
	private ServiceRegistration _serviceRegistration;

}