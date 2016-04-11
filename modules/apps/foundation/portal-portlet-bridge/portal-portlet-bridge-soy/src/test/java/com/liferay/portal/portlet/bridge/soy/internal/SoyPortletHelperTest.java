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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.HtmlImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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

		Bundle bundle = mock(Bundle.class);

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		Template template = mock(Template.class);

		String portletJavaScript = soyPortletHelper.getPortletJavaScript(
			template, "View", StringUtil.randomString(),
			Collections.<String>emptySet());

		Assert.assertEquals(StringPool.BLANK, portletJavaScript);
	}

	@Test
	public void testgetPortletJavaScriptWithBundleWithPackageFile()
		throws Exception {

		Bundle bundle = mock(Bundle.class);

		Class<?> clazz = getClass();

		when(
			bundle.getEntry(Matchers.eq("package.json"))
		).thenReturn(
			clazz.getResource("dependencies/package.json")
		);

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		String portletNamespace = StringUtil.randomString();

		// Expected

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String portletComponentId = portletNamespace.concat("PortletComponent");

		jsonObject.put("element", StringPool.POUND.concat(portletComponentId));
		jsonObject.put("id", portletComponentId);
		jsonObject.put("key 1", "value 1");

		String expectedPortletJavaScript =
			soyPortletHelper.getPortletJavaScript(
				jsonObject.toJSONString(), portletNamespace,
				"\"SampleModuleName/View.soy\"");

		// Actual

		Template template = createMockedTemplate();

		template.put(TemplateConstants.NAMESPACE, StringUtil.randomString());
		template.put("element", StringPool.POUND.concat(portletComponentId));
		template.put("id", portletComponentId);
		template.put("key 1", "value 1");

		String actualPortletJavaScript = soyPortletHelper.getPortletJavaScript(
			template, "View", portletNamespace, Collections.<String>emptySet());

		Assert.assertEquals(expectedPortletJavaScript, actualPortletJavaScript);
	}

	@Test
	public void testTemplateNamespaceWithBundleWithoutPackageFile()
		throws Exception {

		Bundle bundle = mock(Bundle.class);

		String path = "View";

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		Assert.assertEquals(path, soyPortletHelper.getTemplateNamespace(path));
	}

	@Test
	public void testTemplateNamespaceWithBundleWithPackageFile()
		throws Exception {

		Bundle bundle = mock(Bundle.class);

		Class<?> clazz = getClass();

		when(
			bundle.getEntry(Matchers.eq("package.json"))
		).thenReturn(
			clazz.getResource("dependencies/package.json")
		);

		SoyPortletHelper soyPortletHelper = new SoyPortletHelper(bundle);

		Assert.assertEquals(
			"View.render", soyPortletHelper.getTemplateNamespace("View"));
	}

	protected Template createMockedTemplate() {
		Template template = mock(Template.class);

		final Map<String, Object> context = new HashMap<>();

		when(
			template.put(Matchers.anyString(), Matchers.any())
		).then(
			new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					context.put(String.valueOf(args[0]), args[1]);

					return null;
				}

			}
		);

		when(
			template.get(Matchers.anyString())
		).then(
			new Answer<Object>() {

				@Override
				public Object answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] args = invocationOnMock.getArguments();

					return context.get(String.valueOf(args[0]));
				}

			}
		);

		when(
			template.getKeys()
		).then(
			new Answer<String[]>() {

				@Override
				public String[] answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Set<String> keySet = context.keySet();

					return keySet.toArray(new String[keySet.size()]);
				}

			}
		);

		return template;
	}

	protected void setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

}