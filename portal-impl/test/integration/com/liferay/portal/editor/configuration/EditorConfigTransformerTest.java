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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigTransformer;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.module.framework.test.ModuleFrameworkTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class EditorConfigTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_bundleIds = ModuleFrameworkTestUtil.getBundleIds(
			EditorConfigContributor.class, null);

		ModuleFrameworkTestUtil.stopBundles(_bundleIds);
	}

	@AfterClass
	public static void tearDownClass() {
		ModuleFrameworkTestUtil.startBundles(_bundleIds);
	}

	@After
	public void tearDown() {
		if (_editorConfigContributorServiceRegistration != null) {
			_editorConfigContributorServiceRegistration.unregister();
		}

		if (_editorConfigTransfomerServiceRegistration != null) {
			_editorConfigTransfomerServiceRegistration.unregister();
		}

		if (_editorOptionsContributorServiceRegistration1 != null) {
			_editorOptionsContributorServiceRegistration1.unregister();
		}

		if (_editorOptionsContributorServiceRegistration2 != null) {
			_editorOptionsContributorServiceRegistration2.unregister();
		}
	}

	@Test
	public void testEditorConfigNotTransformedWhenEditorConfigTransformerIsRegisteredToOtherEditorName()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);
		properties.put("service.ranking", 1000);

		EditorConfigContributor basicHTMLEditorConfigContributor =
			new BasicHTMLEditorConfigContributor();

		_editorConfigContributorServiceRegistration = registry.registerService(
			EditorConfigContributor.class, basicHTMLEditorConfigContributor,
			properties);

		EditorOptionsContributor textEditorOptionsContributor =
			new TextEditorOptionsContributor();

		_editorOptionsContributorServiceRegistration1 =
			registry.registerService(
				EditorOptionsContributor.class, textEditorOptionsContributor,
				properties);

		properties = new HashMap<>();

		properties.put("editor.name", _UNUSED_EDITOR_NAME);

		EditorConfigTransformer testEditorConfigTransformer =
			new TestEditorConfigTransformer();

		_editorConfigTransfomerServiceRegistration = registry.registerService(
			EditorConfigTransformer.class, testEditorConfigTransformer,
			properties);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME,
				new HashMap<String, Object>(), null, null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("html", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			"HTMLToolbar", configJSONObject.getString("toolbar"));
	}

	@Test
	public void testEditorConfigNotTransformedWhenNoEditorConfigTransformerIsRegistered()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);
		properties.put("service.ranking", 1000);

		EditorConfigContributor basicHTMLEditorConfigContributor =
			new BasicHTMLEditorConfigContributor();

		_editorConfigContributorServiceRegistration = registry.registerService(
			EditorConfigContributor.class, basicHTMLEditorConfigContributor,
			properties);

		EditorOptionsContributor textEditorOptionsContributor =
			new TextEditorOptionsContributor();

		_editorOptionsContributorServiceRegistration1 =
			registry.registerService(
				EditorOptionsContributor.class, textEditorOptionsContributor,
				properties);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME,
				new HashMap<String, Object>(), null, null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("html", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			"HTMLToolbar", configJSONObject.getString("toolbar"));
	}

	@Test
	public void
			testEditorConfigTransformedWhenEditorConfigTransformerIsRegistered()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);
		properties.put("service.ranking", 1000);

		EditorConfigContributor basicHTMLEditorConfigContributor =
			new BasicHTMLEditorConfigContributor();

		_editorConfigContributorServiceRegistration = registry.registerService(
			EditorConfigContributor.class, basicHTMLEditorConfigContributor,
			properties);

		EditorOptionsContributor textEditorOptionsContributor =
			new TextEditorOptionsContributor();

		_editorOptionsContributorServiceRegistration1 =
			registry.registerService(
				EditorOptionsContributor.class, textEditorOptionsContributor,
				properties);

		properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);

		EditorConfigTransformer testEditorConfigTransformer =
			new TestEditorConfigTransformer();

		_editorConfigTransfomerServiceRegistration = registry.registerService(
			EditorConfigTransformer.class, testEditorConfigTransformer,
			properties);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME,
				new HashMap<String, Object>(), null, null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("text", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			StringPool.BLANK, configJSONObject.getString("toolbar"));
	}

	@Test
	public void
			testEditorConfigTransformedWithMultipleEditorOptionsContributors()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);
		properties.put("service.ranking", 1000);

		EditorConfigContributor basicHTMLEditorConfigContributor =
			new BasicHTMLEditorConfigContributor();

		_editorConfigContributorServiceRegistration = registry.registerService(
			EditorConfigContributor.class, basicHTMLEditorConfigContributor,
			properties);

		EditorOptionsContributor textEditorOptionsContributor =
			new TextEditorOptionsContributor();

		_editorOptionsContributorServiceRegistration1 =
			registry.registerService(
				EditorOptionsContributor.class, textEditorOptionsContributor,
				properties);

		EditorOptionsContributor uploadImagesEditorOptionsContributor =
			new UploadImagesEditorOptionsContributor();

		_editorOptionsContributorServiceRegistration2 =
			registry.registerService(
				EditorOptionsContributor.class,
				uploadImagesEditorOptionsContributor, properties);

		properties = new HashMap<>();

		properties.put("editor.name", _EDITOR_NAME);

		EditorConfigTransformer testEditorConfigTransformer =
			new TestEditorConfigTransformer();

		_editorConfigTransfomerServiceRegistration = registry.registerService(
			EditorConfigTransformer.class, testEditorConfigTransformer,
			properties);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME,
				new HashMap<String, Object>(), null, null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("advanced", configJSONObject.getString("version"));
		Assert.assertEquals("text", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			StringPool.BLANK, configJSONObject.getString("toolbar"));
		Assert.assertEquals(
			"http://upload.com", configJSONObject.getString("uploadURL"));
		Assert.assertTrue(configJSONObject.getBoolean("upload"));
	}

	private static final String _CONFIG_KEY = "testEditorConfigKey";

	private static final String _EDITOR_NAME = "testEditorName";

	private static final String _PORTLET_NAME = "testPortletName";

	private static final String _UNUSED_EDITOR_NAME = "testUnusedEditorName";

	private static Collection<Long> _bundleIds;

	private ServiceRegistration<EditorConfigContributor>
		_editorConfigContributorServiceRegistration;
	private ServiceRegistration<EditorConfigTransformer>
		_editorConfigTransfomerServiceRegistration;
	private ServiceRegistration<EditorOptionsContributor>
		_editorOptionsContributorServiceRegistration1;
	private ServiceRegistration<EditorOptionsContributor>
		_editorOptionsContributorServiceRegistration2;

	private static class BasicHTMLEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			jsonObject.put("textMode", "html");
			jsonObject.put("toolbar", "HTMLToolbar");
			jsonObject.put("version", "basic");
		}

	}

	private static class TestEditorConfigTransformer
		implements EditorConfigTransformer {

		@Override
		public void transform(
			EditorOptions editorOptions,
			Map<String, Object> inputEditorTaglibAttributes,
			JSONObject configJSONObject, ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			String uploadURL = editorOptions.getUploadURL();

			if (Validator.isNotNull(uploadURL)) {
				configJSONObject.put("upload", true);
				configJSONObject.put("uploadURL", uploadURL);
				configJSONObject.put("version", "advanced");
			}

			if (editorOptions.isTextMode()) {
				configJSONObject.remove("toolbar");
				configJSONObject.put("textMode", "text");
			}
		}

	}

	private static class TextEditorOptionsContributor
		implements EditorOptionsContributor {

		@Override
		public void populateEditorOptions(
			EditorOptions editorOptions,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			editorOptions.setTextMode(true);
		}

	}

	private static class UploadImagesEditorOptionsContributor
		implements EditorOptionsContributor {

		@Override
		public void populateEditorOptions(
			EditorOptions editorOptions,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			editorOptions.setUploadURL("http://upload.com");
		}

	}

}