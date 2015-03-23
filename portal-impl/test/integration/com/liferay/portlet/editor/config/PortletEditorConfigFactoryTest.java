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

package com.liferay.portlet.editor.config;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class PortletEditorConfigFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@After
	public void tearDown() {
		if (_portletEditorConfigContributorServiceRegistration != null) {
			_portletEditorConfigContributorServiceRegistration.unregister();
		}
	}

	@Test
	public void
			testEditorConfigKeyAndEditorImplOverridesPortletNameAndEditorImplEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.config.key", "title");
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor titleAlloyEditorConfigContributor =
			new TitleEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				titleAlloyEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor blogsAlloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsAlloyEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"titleEditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"titleButton", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testEditorConfigKeyOverridesPortletNameEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.config.key", "title");

		PortletEditorConfigContributor titleEditorConfigContributor =
			new TitleEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				titleEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");

		PortletEditorConfigContributor blogsEditorConfigContributor =
			new BlogsEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"titleEditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals(
			"titleButton", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testGetPortletEditorConfigByEditorImpl() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor alloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				alloyEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.impl", "ckeditor");

		PortletEditorConfigContributor ckEditorConfigContributor =
			new CKEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class, ckEditorConfigContributor,
				properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals("alloyeditor", configJSONObject.getString("editorName"));

		portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "ckeditor", new HashMap<String, Object>(), null,
				null);

		configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals("ckeditor", configJSONObject.getString("editorName"));
	}

	@Test
	public void testGetPortletEditorConfigByEditorImplAndServiceRanking()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor alloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				alloyEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 2000);
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor alloyEditorVideoConfigContributor =
			new AlloyEditorVideoConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				alloyEditorVideoConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"alloyeditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("play", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals("stop", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void
			testPortletNameAndEditorConfigKeyAndEditorImplOverridesPortletNameAndEditorConfigKeyEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");
		properties.put("editor.config.key", "title");
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor blogsTitleAlloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsTitleAlloyEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");
		properties.put("editor.config.key", "title");

		PortletEditorConfigContributor blogsTitleEditorConfigContributor =
			new TitleEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsTitleEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"alloyEditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"titleButton", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void
			testPortletNameAndEditorConfigKeyOverridesEditorConfigKeyAndEditorImplEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");
		properties.put("editor.config.key", "title");

		PortletEditorConfigContributor blogsTitleEditorConfigContributor =
			new TitleEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsTitleEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.config.key", "title");
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor titleAlloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				titleAlloyEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"titleEditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"titleButton", toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void
			testPortletNameAndEditorImplOverridesEditorConfigKeyEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor blogsAlloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsAlloyEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.config.key", "title");

		PortletEditorConfigContributor titleEditorConfigContributor =
			new TitleEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				titleEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"alloyeditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			StringPool.BLANK, toolbarsJSONObject.getString("button3"));
	}

	@Test
	public void testPortletNameOverridesEditorImplEditorConfig()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		HashMap<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("javax.portlet.name", "33");

		PortletEditorConfigContributor blogsEditorConfigContributor =
			new BlogsEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				blogsEditorConfigContributor, properties);

		properties = new HashMap<>();

		properties.put("service.ranking", 1000);
		properties.put("editor.impl", "alloyeditor");

		PortletEditorConfigContributor alloyEditorConfigContributor =
			new AlloyEditorConfigContributor();

		_portletEditorConfigContributorServiceRegistration =
			registry.registerService(
				PortletEditorConfigContributor.class,
				alloyEditorConfigContributor, properties);

		PortletEditorConfig portletEditorConfig =
			PortletEditorConfigFactoryUtil.getPortletEditorConfig(
				"33", "title", "alloyeditor", new HashMap<String, Object>(),
				null, null);

		JSONObject configJSONObject = portletEditorConfig.getConfigJSONObject();

		Assert.assertEquals(
			"blogsEditor", configJSONObject.getString("editorName"));

		JSONObject toolbarsJSONObject = configJSONObject.getJSONObject(
			"toolbars");

		Assert.assertEquals("link", toolbarsJSONObject.getString("button1"));
		Assert.assertEquals("bold", toolbarsJSONObject.getString("button2"));
		Assert.assertEquals(
			"blogsButton", toolbarsJSONObject.getString("button3"));
	}

	private ServiceRegistration<PortletEditorConfigContributor>
		_portletEditorConfigContributorServiceRegistration;

	private class AlloyEditorConfigContributor
		implements PortletEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {

			jsonObject.put("editorName", "alloyeditor");

			JSONObject toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

			toolbarsJSONObject.put("button1", "link");
			toolbarsJSONObject.put("button2", "bold");

			jsonObject.put("toolbars", toolbarsJSONObject);
		}

		@Override
		public void populateOptionsJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {
		}

	}

	private class AlloyEditorVideoConfigContributor
		implements PortletEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put("button2", "play");
			toolbarsJSONObject.put("button3", "stop");
		}

		@Override
		public void populateOptionsJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {
		}

	}

	private class BlogsEditorConfigContributor
		implements PortletEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {

			jsonObject.put("editorName", "blogsEditor");

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put("button3", "blogsButton");
		}

		@Override
		public void populateOptionsJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {
		}

	}

	private class CKEditorConfigContributor
		implements PortletEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {

			jsonObject.put("editorName", "ckeditor");

			JSONObject toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

			toolbarsJSONObject.put("button1", "image");
			toolbarsJSONObject.put("button2", "flash");

			jsonObject.put("toolbars", toolbarsJSONObject);
		}

		@Override
		public void populateOptionsJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {
		}

	}

	private class TitleEditorConfigContributor
		implements PortletEditorConfigContributor {

		@Override
		public void populateConfigJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {

			jsonObject.put("editorName", "titleEditor");

			JSONObject toolbarsJSONObject = jsonObject.getJSONObject(
				"toolbars");

			if (toolbarsJSONObject == null) {
				toolbarsJSONObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("toolbars", toolbarsJSONObject);
			}

			toolbarsJSONObject.put("button3", "titleButton");
		}

		@Override
		public void populateOptionsJSONObject(
			JSONObject jsonObject,
			Map<String, Object> inputEditorTaglibAttributes,
			ThemeDisplay themeDisplay,
			LiferayPortletResponse liferayPortletResponse) {
		}

	}

}