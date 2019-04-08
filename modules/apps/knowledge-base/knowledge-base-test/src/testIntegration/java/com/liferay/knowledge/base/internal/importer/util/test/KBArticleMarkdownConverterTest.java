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

package com.liferay.knowledge.base.internal.importer.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class KBArticleMarkdownConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			KBArticleMarkdownConverterTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle installedBundle : bundleContext.getBundles()) {
			String symbolicName = installedBundle.getSymbolicName();

			if (symbolicName.equals("com.liferay.knowledge.base.service")) {
				bundle = installedBundle;

				break;
			}
		}

		Class<?> clazz = bundle.loadClass(
			"com.liferay.knowledge.base.internal.importer.util." +
				"KBArticleMarkdownConverter");

		_constructor = clazz.getConstructor(
			String.class, String.class, Map.class, DLURLHelper.class);

		_method = clazz.getMethod("getSourceURL");
	}

	@Test
	public void testGetSourceURLAddsTheMissingSlashInTheBaseURL()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";
		String fileEntryName = "some/unix/file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL");

		Object object = _constructor.newInstance(
			markdown, fileEntryName, metadata, _dlURLHelper);

		Assert.assertEquals(
			"http://baseURL/some/unix/file", _method.invoke(object));
	}

	@Test
	public void testGetSourceURLReplacesBackSlashesWithForwardSlashes()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";
		String fileEntryName = "some\\windows\\file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL");

		Object object = _constructor.newInstance(
			markdown, fileEntryName, metadata, _dlURLHelper);

		Assert.assertEquals(
			"http://baseURL/some/windows/file", _method.invoke(object));
	}

	@Test
	public void testGetSourceURLReturnsNullIfThereIsNoBaseURL()
		throws Exception {

		String markdown = "Title [](id=1234)\n=============";
		String fileEntryName = "some\\windows\\file";
		Map<String, String> metadata = new HashMap<>();

		Object object = _constructor.newInstance(
			markdown, fileEntryName, metadata, _dlURLHelper);

		Assert.assertNull(_method.invoke(object));
	}

	@Test
	public void testGetSourceURLUsesTheSlashInTheBaseURL() throws Exception {
		String markdown = "Title [](id=1234)\n=============";
		String fileEntryName = "some/unix/file";

		Map<String, String> metadata = new HashMap<>();

		metadata.put("base.source.url", "http://baseURL/");

		Object object = _constructor.newInstance(
			markdown, fileEntryName, metadata, _dlURLHelper);

		Assert.assertEquals(
			"http://baseURL/some/unix/file", _method.invoke(object));
	}

	private static Constructor _constructor;
	private static Method _method;

	@Inject
	private DLURLHelper _dlURLHelper;

}