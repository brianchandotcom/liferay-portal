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

package com.liferay.layout.reports.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LayoutReportsDataMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	public void testGetData() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", RandomTestUtil.randomString()
					).put(
						"enabled", true
					).build())) {

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(),
				Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
				LocaleUtil.SPAIN);

			JSONObject jsonObject = _serveResource(layout);

			JSONArray canonicalURLsJSONArray = jsonObject.getJSONArray(
				"canonicalURLs");

			Assert.assertEquals(
				String.valueOf(canonicalURLsJSONArray), 2,
				canonicalURLsJSONArray.length());

			JSONObject canonicalURLJSONObject1 =
				canonicalURLsJSONArray.getJSONObject(0);

			String imagesPath = jsonObject.getString("imagesPath");

			Assert.assertTrue(imagesPath.contains("images"));

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				canonicalURLJSONObject1.getString("languageId"));
			Assert.assertEquals(
				layout.getName(LocaleUtil.SPAIN),
				canonicalURLJSONObject1.getString("title"));

			JSONObject canonicalURLJSONObject2 =
				canonicalURLsJSONArray.getJSONObject(1);

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.BRAZIL),
				canonicalURLJSONObject2.getString("languageId"));

			Assert.assertEquals(
				layout.getName(LocaleUtil.BRAZIL),
				canonicalURLJSONObject1.getString("title"));

			String configureGooglePageSpeedURL = jsonObject.getString(
				"configureGooglePageSpeedURL");

			Assert.assertTrue(
				configureGooglePageSpeedURL.contains("configuration_admin"));

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				jsonObject.getString("defaultLanguageId"));

			Assert.assertTrue(jsonObject.getBoolean("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	@Test
	public void testGetDataWithApiKeyInSiteConfiguration() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", StringPool.BLANK
					).put(
						"enabled", true
					).build())) {

			UnicodeProperties unicodeProperties =
				_group.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				"googlePageSpeedApiKey", RandomTestUtil.randomString());
			unicodeProperties.setProperty(
				"googlePageSpeedEnabled", Boolean.TRUE.toString());

			_groupLocalService.updateGroup(_group);

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			JSONObject jsonObject = _serveResource(layout);

			Assert.assertTrue(jsonObject.getBoolean("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	@Test
	public void testGetDataWithLayoutTypeAssetDisplay() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Bundle bundle = FrameworkUtil.getBundle(
			LayoutReportsDataMVCResourceCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<InfoItemFieldValuesProvider<?>>
			infoItemFieldValuesProviderServiceRegistration =
				bundleContext.registerService(
					(Class<InfoItemFieldValuesProvider<?>>)
						(Class<?>)InfoItemFieldValuesProvider.class,
					new MockInfoItemFieldValuesProvider(),
					new HashMapDictionary<>());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", RandomTestUtil.randomString()
					).put(
						"enabled", true
					).build())) {

			Layout layout = LayoutTestUtil.addLayout(_group);

			layout.setType(LayoutConstants.TYPE_ASSET_DISPLAY);

			layout = _layoutLocalService.updateLayout(layout);

			GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(),
				Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
				LocaleUtil.SPAIN);

			InfoItemClassDetails infoItemClassDetails =
				new InfoItemClassDetails(MockObject.class.getName());

			InfoItemDetails infoItemDetails = new InfoItemDetails(
				infoItemClassDetails, null);

			JSONObject jsonObject = _serveResource(
				layout,
				new ObjectValuePair[] {
					new ObjectValuePair<>(
						InfoDisplayWebKeys.INFO_ITEM_DETAILS, infoItemDetails),
					new ObjectValuePair<>(
						InfoDisplayWebKeys.INFO_ITEM, new MockObject())
				});

			JSONArray canonicalURLsJSONArray = jsonObject.getJSONArray(
				"canonicalURLs");

			Assert.assertEquals(
				String.valueOf(canonicalURLsJSONArray), 2,
				canonicalURLsJSONArray.length());

			JSONObject canonicalURLJSONObject1 =
				canonicalURLsJSONArray.getJSONObject(0);

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				canonicalURLJSONObject1.get("languageId"));
			Assert.assertEquals(
				"defaultMappedTitle", canonicalURLJSONObject1.get("title"));

			JSONObject canonicalURLJSONObject2 =
				canonicalURLsJSONArray.getJSONObject(1);

			String imagesPath = jsonObject.getString("imagesPath");

			Assert.assertTrue(imagesPath.contains("images"));

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.BRAZIL),
				canonicalURLJSONObject2.getString("languageId"));
			Assert.assertEquals(
				"defaultMappedTitle",
				canonicalURLJSONObject2.getString("title"));

			String configureGooglePageSpeedURL = jsonObject.getString(
				"configureGooglePageSpeedURL");

			Assert.assertTrue(
				configureGooglePageSpeedURL.contains("configuration_admin"));

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				jsonObject.getString("defaultLanguageId"));

			Assert.assertTrue(jsonObject.getBoolean("validConnection"));
		}
		finally {
			infoItemFieldValuesProviderServiceRegistration.unregister();

			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	@Test
	public void testGetDataWithoutApiKey() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsGooglePageSpeedConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", StringPool.BLANK
					).put(
						"enabled", true
					).build())) {

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			JSONObject jsonObject = _serveResource(layout);

			Assert.assertFalse(jsonObject.getBoolean("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			Layout layout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		MockLiferayResourceRequest mockLiferayPortletRenderRequest =
			new MockLiferayResourceRequest();

		serviceContext.setRequest(
			mockLiferayPortletRenderRequest.getHttpServletRequest());

		mockLiferayPortletRenderRequest.setParameter(
			"plid", String.valueOf(layout.getPlid()));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setRequest(
			mockLiferayPortletRenderRequest.getHttpServletRequest());
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletRenderRequest;
	}

	private JSONObject _serveResource(
			Layout layout, ObjectValuePair<String, Object>... objectValuePairs)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayResourceRequest(layout);

		for (ObjectValuePair<String, Object> objectValuePair :
				objectValuePairs) {

			mockLiferayResourceRequest.setAttribute(
				objectValuePair.getKey(), objectValuePair.getValue());
		}

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		_layoutReportsDataMVCResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/layout_reports/data")
	private MVCResourceCommand _layoutReportsDataMVCResourceCommand;

	private static class MockInfoItemFieldValuesProvider
		implements InfoItemFieldValuesProvider<MockObject> {

		@Override
		public InfoItemFieldValues getInfoItemFieldValues(
			MockObject mockObject) {

			return InfoItemFieldValues.builder(
			).infoFieldValue(
				new InfoFieldValue<>(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).name(
						"title"
					).build(),
					"defaultMappedTitle")
			).build();
		}

	}

	private static class MockObject {
	}

}