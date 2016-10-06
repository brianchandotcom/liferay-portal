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

package com.liferay.portal.portlet.bridge.soy.internal;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.HtmlImpl;

import java.io.Writer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.Bundle;

/**
 * @author Marcellus Tavares
 */
public class SoyPortletHelperTest {

	@Before
	public void setUp() {
		setUpHtmlUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testgetPortletJavaScriptWithBundleWithoutPackageFile()
		throws Exception {

		String portletJavaScript = getPortletJavaScript(_mockBundle, "View");

		Assert.assertEquals(StringPool.BLANK, portletJavaScript);
	}

	@Test
	public void testgetPortletJavaScriptWithBundleWithPackageFile()
		throws Exception {

		Bundle bundle = getBundleWithPackageFile();

		String portletJavaScript = getPortletJavaScript(bundle, "View");

		Assert.assertNotEquals(StringPool.BLANK, portletJavaScript);
	}

	@Test
	public void testgetRequiredModulesWithBundleWithPackageFile()
		throws Exception {

		Bundle bundle = getBundleWithPackageFile();

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		// Expected

		Set<String> expectedRequiredModules = new HashSet<>();

		expectedRequiredModules.add("SampleModuleName/View.soy");

		// Actual

		Set<String> actualRequiredModules = soyPortletHelper.getRequiredModules(
			"View", Collections.<String>emptySet());

		Assert.assertEquals(expectedRequiredModules, actualRequiredModules);
	}

	@Test
	public void testTemplateNamespace() throws Exception {
		String path = "View";

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(_mockBundle);

		Assert.assertEquals(
			path.concat(".render"),
			soyPortletHelper.getTemplateNamespace(path));
	}

	protected Bundle getBundleWithPackageFile() {
		Bundle bundle = (Bundle)ProxyUtil.newProxyInstance(
			Bundle.class.getClassLoader(), new Class<?>[] {Bundle.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws NoSuchMethodException {

					if (method.equals(
							Bundle.class.getMethod("getEntry", String.class)) &&
						"package.json".equals(args[0])) {

						return SoyPortletHelperTest.class.getResource(
							"dependencies/package.json");
					}

					return null;
				}

			});

		return bundle;
	}

	protected String getPortletJavaScript(Bundle bundle, String path)
		throws Exception {

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		Template template = new MockTemplate();

		String portletJavaScript = soyPortletHelper.getPortletJavaScript(
			template, path, StringUtil.randomString(),
			Collections.<String>emptySet());

		return portletJavaScript;
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private final Bundle _mockBundle = (Bundle)ProxyUtil.newProxyInstance(
		Bundle.class.getClassLoader(), new Class<?>[] {Bundle.class},
		new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) {
				return null;
			}

		});

	private static class MockTemplate
		extends HashMap<String, Object> implements Template {

		@Override
		public void doProcessTemplate(Writer writer) {
		}

		@Override
		public Object get(String key) {
			return super.get(key);
		}

		@Override
		public String[] getKeys() {
			Set<String> keys = keySet();

			return keys.toArray(new String[keys.size()]);
		}

		@Override
		public void prepare(HttpServletRequest request) {
		}

		@Override
		public void processTemplate(Writer writer) {
		}

	}

}