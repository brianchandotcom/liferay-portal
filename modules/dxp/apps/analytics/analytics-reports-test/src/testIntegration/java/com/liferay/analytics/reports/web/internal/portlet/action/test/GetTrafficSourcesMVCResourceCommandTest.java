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

package com.liferay.analytics.reports.web.internal.portlet.action.test;

import com.liferay.analytics.reports.test.util.MockContextUtil;
import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockHttpUtil;
import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockThemeDisplayUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsImpl;

import java.io.ByteArrayOutputStream;

import java.util.Objects;
import java.util.ResourceBundle;

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
public class GetTrafficSourcesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetTrafficSources() throws Exception {
		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		ValidPrefsPropsWrapper validPrefsPropsWrapper =
			new ValidPrefsPropsWrapper(prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", validPrefsPropsWrapper);

		ReflectionTestUtil.setFieldValue(
			_mvcResourceCommand, "_http",
			MockHttpUtil.geHttp(
				HashMapBuilder.<String, UnsafeSupplier<String, Exception>>put(
					() -> {
						String dataSourceId = validPrefsPropsWrapper.getString(
							RandomTestUtil.nextLong(),
							"liferayAnalyticsDataSourceId");

						return "/api/1.0/data-sources/" + dataSourceId;
					},
					() -> StringPool.BLANK
				).put(
					"/api/1.0/pages/acquisition-channels",
					() -> JSONUtil.put(
						"organic", 3192L
					).put(
						"referral", 2L
					).put(
						"social", 385L
					).toString()
				).put(
					"/api/1.0/pages/page-referrer-hosts",
					() -> JSONUtil.put(
						"slickdeals.net", 2.0
					).toString()
				).put(
					"/api/1.0/pages/page-referrers",
					() -> JSONUtil.put(
						"https://slickdeals.net/credit-card-offers/", 2.0
					).toString()
				).put(
					"/api/1.0/pages/social-page-referrers",
					() -> JSONUtil.put(
						"facebook", 385.0
					).toString()
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
							"trafficAmount", 3L
						).put(
							"trafficShare", 93.93D
						)
					).toString()
				).build()));

		try {
			MockContextUtil.testWithMockContext(
				MockContextUtil.MockContext.builder(
				).build(),
				() -> {
					MockLiferayResourceRequest mockLiferayResourceRequest =
						_getMockLiferayResourceRequest();

					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setRequest(
						mockLiferayResourceRequest.getHttpServletRequest());

					ServiceContextThreadLocal.pushServiceContext(
						serviceContext);

					MockLiferayResourceResponse mockLiferayResourceResponse =
						new MockLiferayResourceResponse();

					_mvcResourceCommand.serveResource(
						mockLiferayResourceRequest,
						mockLiferayResourceResponse);

					ByteArrayOutputStream byteArrayOutputStream =
						(ByteArrayOutputStream)
							mockLiferayResourceResponse.
								getPortletOutputStream();

					JSONObject jsonObject1 = JSONFactoryUtil.createJSONObject(
						new String(byteArrayOutputStream.toByteArray()));

					JSONArray jsonArray = jsonObject1.getJSONArray(
						"trafficSources");

					Assert.assertEquals(5, jsonArray.length());

					JSONObject jsonObject2 = jsonArray.getJSONObject(0);

					Assert.assertEquals("organic", jsonObject2.get("name"));
					Assert.assertEquals(
						String.format("%.1f", 89.20D),
						jsonObject2.getString("share"));

					Assert.assertEquals(3192, jsonObject2.get("value"));

					JSONArray countryKeywordsJSONArray =
						(JSONArray)jsonObject2.get("countryKeywords");

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

					JSONObject jsonObject3 = jsonArray.getJSONObject(1);

					Assert.assertEquals("social", jsonObject3.get("name"));
					Assert.assertEquals(385, jsonObject3.getInt("value"));

					JSONArray referringSocialMediaJSONArray =
						jsonObject3.getJSONArray("referringSocialMedia");

					JSONObject referringSocialMediaJSONObject =
						referringSocialMediaJSONArray.getJSONObject(0);

					Assert.assertEquals(
						"facebook", referringSocialMediaJSONObject.get("name"));
					Assert.assertEquals(
						385,
						referringSocialMediaJSONObject.get("trafficAmount"));

					JSONObject jsonObject4 = jsonArray.getJSONObject(2);

					Assert.assertEquals("referral", jsonObject4.get("name"));
					Assert.assertEquals(2L, jsonObject4.getInt("value"));

					JSONArray referringDomainsJSONArray =
						jsonObject4.getJSONArray("referringDomains");

					JSONObject referringDomainsJSONObject =
						referringDomainsJSONArray.getJSONObject(0);

					Assert.assertEquals(
						"slickdeals.net",
						referringDomainsJSONObject.get("url"));

					JSONArray referringPagesJSONArray =
						jsonObject4.getJSONArray("referringPages");

					JSONObject referringPagesJSONObject =
						referringPagesJSONArray.getJSONObject(0);

					Assert.assertEquals(
						"https://slickdeals.net/credit-card-offers/",
						referringPagesJSONObject.get("url"));

					JSONObject jsonObject5 = jsonArray.getJSONObject(3);

					Assert.assertEquals("paid", jsonObject5.get("name"));
					Assert.assertEquals(0, jsonObject5.getInt("value"));

					JSONObject jsonObject6 = jsonArray.getJSONObject(4);

					Assert.assertEquals("direct", jsonObject6.get("name"));
					Assert.assertEquals(0, jsonObject6.getInt("value"));
				});
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);

			ReflectionTestUtil.setFieldValue(
				_mvcResourceCommand, "_http", _http);
		}
	}

	@Test
	public void testGetTrafficSourcesWithInvalidConnection() throws Exception {
		PrefsProps prefsProps = PrefsPropsUtil.getPrefsProps();

		InvalidPropsWrapper invalidPropsWrapper = new InvalidPropsWrapper(
			prefsProps);

		ReflectionTestUtil.setFieldValue(
			PrefsPropsUtil.class, "_prefsProps", invalidPropsWrapper);

		try {
			MockContextUtil.testWithMockContext(
				MockContextUtil.MockContext.builder(
				).build(),
				() -> {
					MockLiferayResourceRequest mockLiferayResourceRequest =
						_getMockLiferayResourceRequest();

					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setRequest(
						mockLiferayResourceRequest.getHttpServletRequest());

					ServiceContextThreadLocal.pushServiceContext(
						serviceContext);

					MockLiferayResourceResponse mockLiferayResourceResponse =
						new MockLiferayResourceResponse();

					_mvcResourceCommand.serveResource(
						mockLiferayResourceRequest,
						mockLiferayResourceResponse);

					ByteArrayOutputStream byteArrayOutputStream =
						(ByteArrayOutputStream)
							mockLiferayResourceResponse.
								getPortletOutputStream();

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						new String(byteArrayOutputStream.toByteArray()));

					JSONArray jsonArray = jsonObject.getJSONArray(
						"trafficSources");

					ResourceBundle resourceBundle =
						ResourceBundleUtil.getBundle(
							LocaleUtil.getDefault(),
							_mvcResourceCommand.getClass());

					Assert.assertEquals(
						JSONUtil.putAll(
							JSONUtil.put(
								"helpMessage",
								ResourceBundleUtil.getString(
									resourceBundle,
									StringBundler.concat(
										"this-is-the-number-of-page-views-",
										"generated-by-people-coming-to-your-",
										"page-from-other-sites-which-are-not-",
										"search-engine-pages-or-social-sites"))
							).put(
								"name", "referral"
							).put(
								"share", String.format("%.1f", 0.0)
							).put(
								"title",
								ResourceBundleUtil.getString(
									resourceBundle, "referral")
							).put(
								"value", 0
							),
							JSONUtil.put(
								"helpMessage",
								ResourceBundleUtil.getString(
									resourceBundle,
									"this-is-the-number-of-page-views-" +
										"generated-by-people-coming-to-your-" +
											"page-from-social-sites")
							).put(
								"name", "social"
							).put(
								"share", String.format("%.1f", 0.0)
							).put(
								"title",
								ResourceBundleUtil.getString(
									resourceBundle, "social")
							).put(
								"value", 0
							),
							JSONUtil.put(
								"helpMessage",
								ResourceBundleUtil.getString(
									resourceBundle,
									"this-is-the-number-of-page-views-" +
										"generated-by-people-arriving-" +
											"directly-to-your-page")
							).put(
								"name", "direct"
							).put(
								"share", String.format("%.1f", 0.0)
							).put(
								"title",
								ResourceBundleUtil.getString(
									resourceBundle, "direct")
							).put(
								"value", 0
							),
							JSONUtil.put(
								"helpMessage",
								ResourceBundleUtil.getString(
									resourceBundle,
									"this-is-the-number-of-page-views-" +
										"generated-by-people-that-find-your-" +
											"page-through-google-adwords")
							).put(
								"name", "paid"
							).put(
								"share", String.format("%.1f", 0.0)
							).put(
								"title",
								ResourceBundleUtil.getString(
									resourceBundle, "paid")
							).put(
								"value", 0
							),
							JSONUtil.put(
								"helpMessage",
								ResourceBundleUtil.getString(
									resourceBundle,
									"this-is-the-number-of-page-views-" +
										"generated-by-people-coming-from-a-" +
											"search-engine")
							).put(
								"name", "organic"
							).put(
								"share", String.format("%.1f", 0.0)
							).put(
								"title",
								ResourceBundleUtil.getString(
									resourceBundle, "organic")
							).put(
								"value", 0
							)
						).toJSONString(),
						jsonArray.toJSONString());
				});
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PrefsPropsUtil.class, "_prefsProps", prefsProps);

			ReflectionTestUtil.setFieldValue(
				_mvcResourceCommand, "_http", _http);
		}
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest() {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		try {
			mockLiferayResourceRequest.setAttribute(
				WebKeys.THEME_DISPLAY,
				MockThemeDisplayUtil.getThemeDisplay(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()),
					_group, _layout,
					_layoutSetLocalService.getLayoutSet(
						_group.getGroupId(), false),
					LocaleUtil.US));

			return mockLiferayResourceRequest;
		}
		catch (PortalException portalException) {
			throw new AssertionError(portalException);
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	@Inject
	private Language _language;

	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(filter = "mvc.command.name=/analytics_reports/get_traffic_sources")
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private class InvalidPropsWrapper extends PrefsPropsImpl {

		public InvalidPropsWrapper(PrefsProps prefsProps) {
			_prefsProps = prefsProps;
		}

		@Override
		public String getString(long companyId, String name) {
			if (Objects.equals("liferayAnalyticsDataSourceId", name) ||
				Objects.equals(
					name, "liferayAnalyticsFaroBackendSecuritySignature") ||
				Objects.equals("liferayAnalyticsFaroBackendURL", name)) {

				return null;
			}

			return _prefsProps.getString(companyId, name);
		}

		private final PrefsProps _prefsProps;

	}

	private class ValidPrefsPropsWrapper extends PrefsPropsImpl {

		public ValidPrefsPropsWrapper(PrefsProps prefsProps) {
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