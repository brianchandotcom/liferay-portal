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

package com.liferay.remote.web.component.admin.web.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;
import com.liferay.remote.web.component.admin.web.internal.util.Timestamp;

import java.io.IOException;

import java.time.Instant;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = RemoteWebComponentPortletRegistrar.class
)
public class RemoteWebComponentPortletRegistrar {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		_elementName = _remoteWebComponentConfiguration.elementName();

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Starting remote web component %s", _elementName));
		}

		// Portlet

		Dictionary<String, Object> componentProperties = new Hashtable<>(
			properties);

		componentProperties.remove(Constants.SERVICE_PID);

		componentProperties.put(
			"com.liferay.portlet.css-class-wrapper",
			"portlet-remote-web-component");
		componentProperties.put(
			"com.liferay.portlet.requires-namespaced-parameters", "false");
		componentProperties.put("javax.portlet.name", _getPortletName());
		componentProperties.put(
			"javax.portlet.preferences",
			"classpath:/META-INF/portlet-preferences/default-preferences.xml");
		componentProperties.put(
			"javax.portlet.security-role-ref", "power-user,user");
		componentProperties.put("javax.portlet.version", "3.0");

		UnicodeProperties unicodeProperties = new UnicodeProperties();

		try {
			unicodeProperties.load(
				_remoteWebComponentConfiguration.portletServiceProperties());
			unicodeProperties.forEach(componentProperties::put);
		}
		catch (IOException ioException) {
			_log.error(
				String.format(
					"Could not parse portlet service properties for %s",
					_elementName),
				ioException);
		}

		Instant now = Instant.now();

		String[] webComponentJSUrls = Timestamp.append(
			_remoteWebComponentConfiguration.webComponentUrl(), now);

		componentProperties.put(
			"com.liferay.portlet.header-portal-javascript", webComponentJSUrls);

		String[] webComponentCSSUrls = Timestamp.append(
			_remoteWebComponentConfiguration.webComponentCssUrl(), now);

		if (webComponentCSSUrls.length > 0) {
			componentProperties.put(
				"com.liferay.portlet.header-portal-css", webComponentCSSUrls);
		}

		String displayCategory =
			_remoteWebComponentConfiguration.portletDisplayCategory();

		displayCategory =
			Validator.isNotNull(displayCategory) ? displayCategory : "sample";

		componentProperties.put(
			"com.liferay.portlet.display-category",
			"category." + displayCategory);

		componentProperties.put(
			"com.liferay.portlet.instanceable",
			String.valueOf(_remoteWebComponentConfiguration.instanceable()));

		//componentProperties.put(

		//    "com.liferay.portlet.single-page-application", "false");

		componentProperties.put(
			"javax.portlet.resource-bundle", _getResourceBundleName());

		_portletInstance = _remoteWebComponentPortletFactory.newInstance(
			componentProperties);

		// Resource Bundle Loader

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put(
			"resource.bundle.base.name", _getResourceBundleName());
		componentProperties.put(
			"servlet.context.name", "remote-web-component-admin-web");
		componentProperties.put(
			"javax.portlet.title." + _getPortletName(),
			_remoteWebComponentConfiguration.name());
		componentProperties.put("category." + displayCategory, displayCategory);

		_bundleResourceLoaderInstance =
			_remoteWebComponentResourceBundleLoaderFactory.newInstance(
				componentProperties);

		// Friendly URL Mapper

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put("javax.portlet.name", _getPortletName());

		_friendlyURLMapperInstance =
			_remoteWebComponentFriendlyURLMapperFactory.newInstance(
				componentProperties);

		// Top Js Dynamic Include

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put("javax.portlet.name", _getPortletName());

		_topJsDynamicInclude =
			_remoteWebComponentTopJsDynamicInclude.newInstance(
				componentProperties);

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format("Started remote web component %s", _elementName));
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Stopping remote web component %s", _elementName));
		}

		_portletInstance.dispose();
		_bundleResourceLoaderInstance.dispose();
		_friendlyURLMapperInstance.dispose();
		_topJsDynamicInclude.dispose();

		_portletInstance = null;
		_bundleResourceLoaderInstance = null;
		_friendlyURLMapperInstance = null;
		_topJsDynamicInclude = null;

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format("Stopped remote web component %s", _elementName));
		}
	}

	private String _getPortletName() {
		final String portletAlias =
			_remoteWebComponentConfiguration.portletAlias();

		String portletName =
			Validator.isNotNull(portletAlias) ? portletAlias : _elementName;

		return _portal.getJsSafePortletId("rwc_" + portletName);
	}

	private String _getResourceBundleName() {
		return _getPortletName() + ".Language";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentPortletRegistrar.class);

	private volatile ComponentInstance _bundleResourceLoaderInstance;
	private volatile String _elementName;
	private volatile ComponentInstance _friendlyURLMapperInstance;

	@Reference
	private Portal _portal;

	private volatile ComponentInstance _portletInstance;
	private volatile RemoteWebComponentConfiguration
		_remoteWebComponentConfiguration;

	@Reference(
		target = "(component.factory=remote.web.component.friendly.url.mapper)"
	)
	private ComponentFactory _remoteWebComponentFriendlyURLMapperFactory;

	@Reference(target = "(component.factory=remote.web.component.portlet)")
	private ComponentFactory _remoteWebComponentPortletFactory;

	@Reference(
		target = "(component.factory=remote.web.component.resource.bundle.loader)"
	)
	private ComponentFactory _remoteWebComponentResourceBundleLoaderFactory;

	@Reference(
		target = "(component.factory=remote.web.component.top.js.dynamic.include)"
	)
	private ComponentFactory _remoteWebComponentTopJsDynamicInclude;

	private volatile ComponentInstance _topJsDynamicInclude;

}