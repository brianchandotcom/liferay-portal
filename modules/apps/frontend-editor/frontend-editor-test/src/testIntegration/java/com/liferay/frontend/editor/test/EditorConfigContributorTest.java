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

package com.liferay.frontend.editor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class EditorConfigContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_editorConfigProviderSwapper = new EditorConfigProviderSwapper(
			_editorConfigurationFactory,
			Arrays.asList(
				EmoticonsEditorConfigContributor.class,
				ImageEditorConfigContributor.class,
				TablesEditorConfigContributor.class,
				TextFormatEditorConfigContributor.class,
				VideoEditorConfigContributor.class));

		Bundle bundle = FrameworkUtil.getBundle(
			EditorConfigContributorTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@AfterClass
	public static void tearDownClass() {
		_editorConfigProviderSwapper.close();
	}

	@After
	public void tearDown() {
		if (_editorConfigContributorServiceRegistration1 != null) {
			_editorConfigContributorServiceRegistration1.unregister();
		}

		if (_editorConfigContributorServiceRegistration2 != null) {
			_editorConfigContributorServiceRegistration2.unregister();
		}
	}

	@Test
	public void testEditorConfigKeyAndEditorNameOverridesPortletNameAndEditorNameEditorConfig() {
		EditorConfigContributor emoticonsEditorConfigContributor =
			new EmoticonsEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class, emoticonsEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			EmoticonsEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"emoticons", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testEditorConfigKeyOverridesPortletNameEditorConfig() {
		EditorConfigContributor emoticonsEditorConfigContributor =
			new EmoticonsEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class, emoticonsEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor tablesEditorConfigContributor =
			new TablesEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class, tablesEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			EmoticonsEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals(
			"emoticons", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testEditorNameOverridesEmptySelectorConfig() {
		EditorConfigContributor tablesEditorConfigContributor =
			new TablesEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class, tablesEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TablesEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"tablesButton", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testGetEditorConfigurationByEditorName() {
		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor imageEditorConfigContributor =
			new ImageEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class, imageEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME_2);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TextFormatEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME_2, new HashMap<>(),
				null, null);

		configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			ImageEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));
	}

	@Test
	public void testGetEditorConfigurationByEditorNameAndServiceRanking() {
		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor videoEditorVideoConfigContributor =
			new VideoEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				videoEditorVideoConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 2000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TextFormatEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("play", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals("stop", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testPortletNameAndEditorConfigKeyAndEditorNameOverridesPortletNameAndEditorConfigKeyEditorConfig() {
		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("editor.name", _EDITOR_NAME);
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor emoticonsEditorConfigContributor =
			new EmoticonsEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class, emoticonsEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TextFormatEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"emoticons", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testPortletNameAndEditorConfigKeyOverridesEditorConfigKeyAndEditorNameEditorConfig() {
		EditorConfigContributor emoticonsEditorConfigContributor =
			new EmoticonsEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class, emoticonsEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			EmoticonsEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"emoticons", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testPortletNameAndEditorNameOverridesEditorConfigKeyEditorConfig() {
		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor emoticonsEditorConfigContributor =
			new EmoticonsEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class, emoticonsEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.config.key", _CONFIG_KEY);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TextFormatEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"emoticons", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testPortletNameOverridesEditorNameEditorConfig() {
		EditorConfigContributor tablesEditorConfigContributor =
			new TablesEditorConfigContributor();

		_editorConfigContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorConfigContributor.class, tablesEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("javax.portlet.name", _PORTLET_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfigContributor textFormatEditorConfigContributor =
			new TextFormatEditorConfigContributor();

		_editorConfigContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				textFormatEditorConfigContributor,
				new HashMapDictionary<String, Object>() {
					{
						put("editor.name", _EDITOR_NAME);
						put("service.ranking", 1000);
					}
				});

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals(
			TablesEditorConfigContributor.class.getName(),
			configJSONObject.getString("className"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"tablesButton", toolbarsJSONObject.getString("button3"));
	}

	private static final String _CONFIG_KEY = "testEditorConfigKey";

	private static final String _EDITOR_NAME = "testEditorName1";

	private static final String _EDITOR_NAME_2 = "testEditorName2";

	private static final String _PORTLET_NAME = "testPortletName";

	private static BundleContext _bundleContext;
	private static EditorConfigProviderSwapper _editorConfigProviderSwapper;

	@Inject
	private static EditorConfigurationFactory _editorConfigurationFactory;

	@Inject
	private static JSONFactory _jsonFactory;

	private ServiceRegistration<EditorConfigContributor>
		_editorConfigContributorServiceRegistration1;
	private ServiceRegistration<EditorConfigContributor>
		_editorConfigContributorServiceRegistration2;

	private static class EmoticonsEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			jsonObject.put(
				"className", EmoticonsEditorConfigContributor.class.getName());

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = _jsonFactory.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put("button3", "emoticons");
		}

	}

	private static class ImageEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			jsonObject.put(
				"className", ImageEditorConfigContributor.class.getName());

			JSONObject toolbarsJSONObject = _jsonFactory.createJSONObject();

			toolbarsJSONObject.put(
				"button1", "image"
			).put(
				"button2", "gif"
			);

			jsonObject.put("toolbars", toolbarsJSONObject);
		}

	}

	private static class TablesEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			jsonObject.put(
				"className", TablesEditorConfigContributor.class.getName());

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = _jsonFactory.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put("button3", "tablesButton");
		}

	}

	private static class TextFormatEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			jsonObject.put(
				"className", TextFormatEditorConfigContributor.class.getName());

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = _jsonFactory.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put(
				"button1", "link"
			).put(
				"button2", "bold"
			);

			jsonObject.put("toolbars", toolbarsJSONObject);
		}

	}

	private static class VideoEditorConfigContributor
		implements EditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = _jsonFactory.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put(
				"button2", "play"
			).put(
				"button3", "stop"
			);
		}

	}

}