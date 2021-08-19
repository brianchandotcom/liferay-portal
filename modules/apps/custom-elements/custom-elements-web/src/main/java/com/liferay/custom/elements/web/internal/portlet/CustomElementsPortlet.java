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

package com.liferay.custom.elements.web.internal.portlet;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementsPortlet extends MVCPortlet {

	public static String getPortletName(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		long customElementsPortletDescriptorId =
			customElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId();

		return "com_liferay_custom_elements_web_internal_portlet_" +
			"CustomElementsPortlet#" + customElementsPortletDescriptorId;
	}

	public CustomElementsPortlet(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		_customElementsPortletDescriptor = customElementsPortletDescriptor;
	}

	public String getName() {
		return _customElementsPortletDescriptor.getName();
	}

	public synchronized void register(BundleContext bundleContext) {
		if (_serviceRegistration != null) {
			throw new IllegalStateException("Portlet is already registered");
		}

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, this,
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.company",
				_customElementsPortletDescriptor.getCompanyId()
			).put(
				"com.liferay.portlet.css-class-wrapper",
				"portlet-custom-element-portlet"
			).put(
				"com.liferay.portlet.display-category", "category.sample"
			).put(
				"com.liferay.portlet.header-portal-css", _getCSSURLs()
			).put(
				"com.liferay.portlet.instanceable",
				_customElementsPortletDescriptor.isInstanceable()
			).put(
				"javax.portlet.name",
				getPortletName(_customElementsPortletDescriptor)
			).put(
				"javax.portlet.resource-bundle", _getResourceBundleName()
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).build());

		_resourceBundleLoaderServiceRegistration =
			bundleContext.registerService(
				ResourceBundleLoader.class, locale -> _getResourceBundle(),
				HashMapDictionaryBuilder.<String, Object>put(
					"resource.bundle.base.name", _getResourceBundleName()
				).put(
					"servlet.context.name", "com.liferay.custom.elements.web"
				).build());
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			printWriter.print(StringPool.LESS_THAN);
			printWriter.print(
				_customElementsPortletDescriptor.getHTMLElementName());

			Properties properties = _getProperties();

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				printWriter.print(StringPool.SPACE);
				printWriter.print(entry.getKey());
				printWriter.print("=\"");

				String value = (String)entry.getValue();

				printWriter.print(value.replaceAll(StringPool.QUOTE, "&quot;"));

				printWriter.print(StringPool.QUOTE);
			}

			printWriter.print("></");
			printWriter.print(
				_customElementsPortletDescriptor.getHTMLElementName());
			printWriter.print(StringPool.GREATER_THAN);

			printWriter.flush();
		}
		catch (IOException ioException) {
			_log.error("Unable to render HTML output", ioException);
		}
	}

	public synchronized void unregister() {
		if (_serviceRegistration == null) {
			throw new IllegalStateException("Portlet is not registered");
		}

		_resourceBundleLoaderServiceRegistration.unregister();

		_resourceBundleLoaderServiceRegistration = null;

		_serviceRegistration.unregister();

		_serviceRegistration = null;
	}

	private String[] _getCSSURLs() {
		List<String> cssURLs = StringUtil.split(
			_customElementsPortletDescriptor.getCSSURLs(), CharPool.NEW_LINE);

		return cssURLs.toArray(new String[0]);
	}

	private Properties _getProperties() {
		Properties properties = new Properties();

		String propertiesString =
			_customElementsPortletDescriptor.getProperties();

		try {
			properties.load(
				new ByteArrayInputStream(
					propertiesString.getBytes(StandardCharsets.UTF_8)));
		}
		catch (IOException ioException) {
			throw new IllegalArgumentException(propertiesString, ioException);
		}

		return properties;
	}

	private ResourceBundle _getResourceBundle() {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(_labels.keySet());
			}

			@Override
			protected Object handleGetObject(String key) {
				return _labels.get(key);
			}

			private final Map<String, String> _labels = HashMapBuilder.put(
				"javax.portlet.title." +
					getPortletName(_customElementsPortletDescriptor),
				_customElementsPortletDescriptor.getName()
			).build();

		};
	}

	private String _getResourceBundleName() {
		return getPortletName(_customElementsPortletDescriptor) + ".Language";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsPortlet.class);

	private final CustomElementsPortletDescriptor
		_customElementsPortletDescriptor;
	private ServiceRegistration<ResourceBundleLoader>
		_resourceBundleLoaderServiceRegistration;
	private ServiceRegistration<Portlet> _serviceRegistration;

}