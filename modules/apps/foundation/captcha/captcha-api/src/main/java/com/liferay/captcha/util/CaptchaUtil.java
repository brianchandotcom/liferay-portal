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

package com.liferay.captcha.util;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.captcha.configuration.CaptchaConfiguration",
	immediate = true, service = CaptchaUtil.class
)
public class CaptchaUtil {

	public static void check(HttpServletRequest request)
		throws CaptchaException {

		getCaptcha().check(request);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaException {

		getCaptcha().check(portletRequest);
	}

	public static Captcha getCaptcha() {
		String captchaClassName = _captchaConfiguration.captchaEngine();

		return _serviceTrackerMap.getService(captchaClassName);
	}

	public static String getTaglibPath() {
		return getCaptcha().getTaglibPath();
	}

	public static boolean isEnabled(HttpServletRequest request) {
		return getCaptcha().isEnabled(request);
	}

	public static boolean isEnabled(PortletRequest portletRequest) {
		return getCaptcha().isEnabled(portletRequest);
	}

	public static void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		getCaptcha().serveImage(request, response);
	}

	public static void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		getCaptcha().serveImage(resourceRequest, resourceResponse);
	}

	public static void setCaptcha(Captcha captcha) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			CaptchaConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		Class<?> clazz = captcha.getClass();

		properties.put("captchaEngine", clazz.getName());

		configuration.update(properties);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_captchaConfiguration = ConfigurableUtil.createConfigurable(
			CaptchaConfiguration.class, properties);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, Captcha.class, "captcha.engine.impl");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_serviceTrackerMap = null;
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private static volatile CaptchaConfiguration _captchaConfiguration;
	private static ConfigurationAdmin _configurationAdmin;
	private static ServiceTrackerMap<String, Captcha> _serviceTrackerMap;

}