/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigTransformer;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactory;
import com.liferay.portal.kernel.editor.configuration.EditorOptions;
import com.liferay.portal.kernel.editor.configuration.EditorOptionsContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Dictionary;
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
public class EditorConfigTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_editorConfigProviderSwapper = new EditorConfigProviderSwapper(
			_editorConfigurationFactory,
			Arrays.asList(BasicHTMLEditorConfigContributor.class));

		Bundle bundle = FrameworkUtil.getBundle(
			EditorConfigTransformerTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@AfterClass
	public static void tearDownClass() {
		_editorConfigProviderSwapper.close();
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
	public void testEditorConfigNotTransformedWhenEditorConfigTransformerIsRegisteredToOtherEditorName() {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"editor.name", _EDITOR_NAME
			).put(
				"service.ranking", 1000
			).build();

		_editorConfigContributorServiceRegistration =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				new BasicHTMLEditorConfigContributor(), properties);

		_editorOptionsContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorOptionsContributor.class,
				new TextEditorOptionsContributor(), properties);

		_editorConfigTransfomerServiceRegistration =
			_bundleContext.registerService(
				EditorConfigTransformer.class,
				new TestEditorConfigTransformer(),
				HashMapDictionaryBuilder.<String, Object>put(
					"editor.name", _UNUSED_EDITOR_NAME
				).build());

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("html", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			"HTMLToolbar", configJSONObject.getString("toolbar"));
	}

	@Test
	public void testEditorConfigNotTransformedWhenNoEditorConfigTransformerIsRegistered() {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"editor.name", _EDITOR_NAME
			).put(
				"service.ranking", 1000
			).build();

		_editorConfigContributorServiceRegistration =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				new BasicHTMLEditorConfigContributor(), properties);

		_editorOptionsContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorOptionsContributor.class,
				new TextEditorOptionsContributor(), properties);

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("html", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			"HTMLToolbar", configJSONObject.getString("toolbar"));
	}

	@Test
	public void testEditorConfigTransformedWhenEditorConfigTransformerIsRegistered() {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"editor.name", _EDITOR_NAME
			).put(
				"service.ranking", 1000
			).build();

		_editorConfigContributorServiceRegistration =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				new BasicHTMLEditorConfigContributor(), properties);

		_editorOptionsContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorOptionsContributor.class,
				new TextEditorOptionsContributor(), properties);

		_editorConfigTransfomerServiceRegistration =
			_bundleContext.registerService(
				EditorConfigTransformer.class,
				new TestEditorConfigTransformer(),
				HashMapDictionaryBuilder.<String, Object>put(
					"editor.name", _EDITOR_NAME
				).build());

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

		JSONObject configJSONObject = editorConfiguration.getConfigJSONObject();

		Assert.assertEquals("basic", configJSONObject.getString("version"));
		Assert.assertEquals("text", configJSONObject.getString("textMode"));
		Assert.assertEquals(
			StringPool.BLANK, configJSONObject.getString("toolbar"));
	}

	@Test
	public void testEditorConfigTransformedWithMultipleEditorOptionsContributors() {
		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"editor.name", _EDITOR_NAME
			).put(
				"service.ranking", 1000
			).build();

		_editorConfigContributorServiceRegistration =
			_bundleContext.registerService(
				EditorConfigContributor.class,
				new BasicHTMLEditorConfigContributor(), properties);

		_editorOptionsContributorServiceRegistration1 =
			_bundleContext.registerService(
				EditorOptionsContributor.class,
				new TextEditorOptionsContributor(), properties);

		_editorOptionsContributorServiceRegistration2 =
			_bundleContext.registerService(
				EditorOptionsContributor.class,
				new UploadImagesEditorOptionsContributor(), properties);

		_editorConfigTransfomerServiceRegistration =
			_bundleContext.registerService(
				EditorConfigTransformer.class,
				new TestEditorConfigTransformer(),
				HashMapDictionaryBuilder.<String, Object>put(
					"editor.name", _EDITOR_NAME
				).build());

		EditorConfiguration editorConfiguration =
			_editorConfigurationFactory.getEditorConfiguration(
				_PORTLET_NAME, _CONFIG_KEY, _EDITOR_NAME, new HashMap<>(), null,
				null);

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

	private static BundleContext _bundleContext;
	private static EditorConfigProviderSwapper _editorConfigProviderSwapper;

	@Inject
	private static EditorConfigurationFactory _editorConfigurationFactory;

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

			jsonObject.put(
				"textMode", "html"
			).put(
				"toolbar", "HTMLToolbar"
			).put(
				"version", "basic"
			);
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
				configJSONObject.put(
					"upload", true
				).put(
					"uploadURL", uploadURL
				).put(
					"version", "advanced"
				);
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