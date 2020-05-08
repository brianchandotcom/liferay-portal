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

package com.liferay.resource.actions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class ResourceActionsDefinitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testResourceActions() throws Exception {
		Bundle currentBundle = FrameworkUtil.getBundle(
			ResourceActionsDefinitionTest.class);

		BundleContext bundleContext = currentBundle.getBundleContext();

		StringBundler sb = new StringBundler();

		for (Bundle bundle : bundleContext.getBundles()) {
			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			ClassLoader bundleClassLoader = bundleWiring.getClassLoader();

			if (bundleClassLoader == null) {
				continue;
			}

			URL url = bundleClassLoader.getResource("portlet.properties");

			if (url == null) {
				url = bundleClassLoader.getResource("portal.properties");
			}

			if (url == null) {
				continue;
			}

			Properties properties = new Properties();

			try (InputStream inputStream = url.openStream()) {
				properties.load(inputStream);
			}

			for (String resourceActionConfig :
					StringUtil.split(
						properties.getProperty(
							PropsKeys.RESOURCE_ACTIONS_CONFIGS))) {

				_collectResourceActionsError(
					resourceActionConfig, bundle, bundleClassLoader, sb);
			}
		}

		String errors = sb.toString();

		Assert.assertTrue(
			"The following resource action issues are found:".concat(errors),
			errors.isEmpty());
	}

	private void _collectResourceActionsError(
			String resourceActionConfig, Bundle bundle,
			ClassLoader bundleClassLoader, StringBundler sb)
		throws Exception {

		URL url = bundleClassLoader.getResource(resourceActionConfig);

		if (url == null) {
			if (bundle.getBundleId() != 0) {
				sb.append("\n\t\t");
				sb.append(bundle.getSymbolicName());
				sb.append(": resource action definition file ");
				sb.append(resourceActionConfig);
				sb.append(" is not found.");
			}

			return;
		}

		Document document = null;

		try (InputStream inputStream = url.openStream()) {
			document = UnsecureSAXReaderUtil.read(inputStream, false);
		}

		Element rootElement = document.getRootElement();

		for (Element resourceElement : rootElement.elements("resource")) {
			_collectResourceActionsError(
				StringUtil.trim(resourceElement.attributeValue("file")), bundle,
				bundleClassLoader, sb);
		}

		_collectResourceActionsErrorForModelResources(rootElement, bundle, sb);

		_collectResourceActionsErrorForPortletResources(
			rootElement, bundle, bundleClassLoader, sb);
	}

	private void _collectResourceActionsErrorForModelResources(
		Element rootElement, Bundle bundle, StringBundler sb) {

		List<Element> modelResourceElements = rootElement.elements(
			"model-resource");

		if (modelResourceElements.isEmpty()) {
			return;
		}

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if ((bundle.getBundleId() != 0) &&
			(headers.get("Liferay-Service") == null)) {

			sb.append("\n\t\t");
			sb.append(bundle.getSymbolicName());
			sb.append(": model resources are found but bundle is non-service ");
			sb.append("bundle");
		}
	}

	private void _collectResourceActionsErrorForPortletResources(
		Element rootElement, Bundle bundle, ClassLoader bundleClassLoader,
		StringBundler sb) {

		for (Element portletResourceElement :
				rootElement.elements("portlet-resource")) {

			Element portletNameElement = portletResourceElement.element(
				"portlet-name");

			String portletName = portletNameElement.getTextTrim();

			PortletBag portletBag = PortletBagPool.get(portletName);

			Portlet portlet = portletBag.getPortletInstance();

			Class<?> clazz = portlet.getClass();

			if (clazz.getClassLoader() != bundleClassLoader) {
				sb.append("\n\t\t");
				sb.append(bundle.getSymbolicName());
				sb.append(": portlet resource ");
				sb.append(portletName);
				sb.append(" is defined in ");
				sb.append(bundle.getSymbolicName());
				sb.append(" but the portlet is in ");
				sb.append(clazz.getClassLoader());
			}
		}
	}

}