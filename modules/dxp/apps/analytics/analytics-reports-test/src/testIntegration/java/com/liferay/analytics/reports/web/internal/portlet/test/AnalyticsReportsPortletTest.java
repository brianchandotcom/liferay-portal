/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.portlet.test;

import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockHttpUtil;
import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockThemeDisplayUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsImpl;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AnalyticsReportsPortletTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testGetData() throws Exception {
		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		PrefsPropsWrapper prefsPropsWrapper = _getPrefsPropsWrapper(prefsProps);

		String dataSourceId = prefsPropsWrapper.getString(
			RandomTestUtil.nextLong(), "liferayAnalyticsDataSourceId");

		_mockHttp(
			HashMapBuilder.<String, UnsafeSupplier<String, Exception>>put(
				"/api/1.0/data-sources/" + dataSourceId, () -> StringPool.BLANK
			).put(
				"/api/seo/1.0/traffic-sources",
				() -> JSONUtil.put(
					JSONUtil.put(
						"countryKeywords",
						JSONUtil.put(
							JSONUtil.put(
								"countryCode", "us"
							).put(
								"countryName", "United States"
							).put(
								"keywords",
								JSONUtil.put(
									JSONUtil.put(
										"keyword", "liferay"
									).put(
										"position", 1
									).put(
										"searchVolume", 3600
									).put(
										"traffic", 2880L
									))
							))
					).put(
						"name", "organic"
					).put(
						"trafficAmount", 3192L
					).put(
						"trafficShare", 93.93D
					)
				).toString()
			).build());

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("trafficSourcesEnabled", true);

		try {
			MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
				_getMockLiferayPortletRenderRequest();

			_portlet.render(
				mockLiferayPortletRenderRequest,
				_getMockLiferayPortletRenderResponse());

			Map<String, Object> data = _getProps(
				mockLiferayPortletRenderRequest);

			JSONArray jsonArray = (JSONArray)data.get("trafficSources");

			Assert.assertEquals(2, jsonArray.length());

			JSONObject jsonObject1 = jsonArray.getJSONObject(0);

			Assert.assertEquals("paid", jsonObject1.get("name"));

			Assert.assertNull(jsonObject1.get("value"));

			JSONObject jsonObject2 = jsonArray.getJSONObject(1);

			Assert.assertEquals("organic", jsonObject2.get("name"));

			Assert.assertEquals(93.93D, jsonObject2.get("share"));

			Assert.assertEquals(3192, jsonObject2.get("value"));

			JSONArray countryKeywordsJSONArray = (JSONArray)jsonObject2.get(
				"countryKeywords");

			Assert.assertEquals(
				JSONUtil.put(
					JSONUtil.put(
						"countryCode", "us"
					).put(
						"countryName", "United States"
					).put(
						"keywords",
						JSONUtil.put(
							JSONUtil.put(
								"keyword", "liferay"
							).put(
								"position", 1
							).put(
								"searchVolume", 3600
							).put(
								"traffic", 2880
							))
					)
				).toString(),
				countryKeywordsJSONArray.toString());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);

			ReflectionTestUtil.setFieldValue(_portlet, "_http", _http);
		}
	}

	private InfoDisplayObjectProvider<Object> _getInfoDisplayObjectProvider() {
		return new InfoDisplayObjectProvider() {

			@Override
			public long getClassNameId() {
				return _portal.getClassNameId(
					"com.liferay.journal.model.JournalArticle");
			}

			@Override
			public long getClassPK() {
				return 0;
			}

			@Override
			public long getClassTypeId() {
				return 0;
			}

			@Override
			public String getDescription(Locale locale) {
				return null;
			}

			@Override
			public Object getDisplayObject() {
				return null;
			}

			@Override
			public long getGroupId() {
				return 0;
			}

			@Override
			public String getKeywords(Locale locale) {
				return null;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public String getURLTitle(Locale locale) {
				return null;
			}

		};
	}

	private MockLiferayPortletRenderRequest
			_getMockLiferayPortletRenderRequest()
		throws PortalException {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		String path = "/view.jsp";

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			ProxyUtil.newProxyInstance(
				PortletContext.class.getClassLoader(),
				new Class<?>[] {PortletContext.class},
				(PortletContextProxy, portletContextMethod,
				 portletContextArgs) -> {

					if (Objects.equals(
							portletContextMethod.getName(),
							"getRequestDispatcher") &&
						Objects.equals(portletContextArgs[0], path)) {

						return ProxyUtil.newProxyInstance(
							PortletRequestDispatcher.class.getClassLoader(),
							new Class<?>[] {PortletRequestDispatcher.class},
							(portletRequestDispatcherProxy,
							 portletRequestDispatcherMethod,
							 portletRequestDispatcherArgs) -> null);
					}

					throw new UnsupportedOperationException();
				}));

		mockLiferayPortletRenderRequest.setAttribute(
			AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
			_getInfoDisplayObjectProvider());

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			MockThemeDisplayUtil.getThemeDisplay(
				_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				_group, _layout,
				_layoutSetLocalService.getLayoutSet(
					_group.getGroupId(), false)));

		mockLiferayPortletRenderRequest.setParameter("mvcPath", path);

		return mockLiferayPortletRenderRequest;
	}

	private MockLiferayPortletRenderResponse
		_getMockLiferayPortletRenderResponse() {

		return new MockLiferayPortletRenderResponse() {

			@Override
			public String getNamespace() {
				return "com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet";
			}

		};
	}

	private PrefsPropsWrapper _getPrefsPropsWrapper(PrefsProps prefsProps) {
		PrefsPropsWrapper prefsPropsWrapper = new PrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", prefsPropsWrapper);

		return prefsPropsWrapper;
	}

	private Map<String, Object> _getProps(
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest) {

		Object analyticsReportsDisplayContext =
			mockLiferayPortletRenderRequest.getAttribute(
				"ANALYTICS_REPORTS_DISPLAY_CONTEXT");

		Map<String, Object> data = ReflectionTestUtil.invoke(
			analyticsReportsDisplayContext, "getData", new Class<?>[0]);

		return (Map<String, Object>)data.get("props");
	}

	private void _mockHttp(
			Map<String, UnsafeSupplier<String, Exception>> mockRequest)
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			_portlet, "_http", MockHttpUtil.geHttp(mockRequest));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.web.internal.portlet.AnalyticsReportsPortlet"
	)
	private Portlet _portlet;

	private class PrefsPropsWrapper extends PrefsPropsImpl {

		public PrefsPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return "test";
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

}