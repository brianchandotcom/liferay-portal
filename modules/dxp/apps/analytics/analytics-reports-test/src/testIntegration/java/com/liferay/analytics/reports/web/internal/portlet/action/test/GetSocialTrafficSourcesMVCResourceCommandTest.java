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
import com.liferay.petra.function.UnsafeSupplier;
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
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsImpl;

import java.io.ByteArrayOutputStream;

import java.util.Objects;

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
public class GetSocialTrafficSourcesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testGetSocialTrafficSources() throws Exception {
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
					"/api/1.0/pages/social-page-referrers",
					() -> JSONUtil.put(
						"facebook", 385.0
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

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						new String(byteArrayOutputStream.toByteArray()));

					JSONArray referringSocialMediaJSONArray =
						jsonObject.getJSONArray("referringSocialMedia");

					JSONObject referringSocialMediaJSONObject =
						referringSocialMediaJSONArray.getJSONObject(0);

					Assert.assertEquals(
						"facebook",
						referringSocialMediaJSONObject.getString("name"));
					Assert.assertEquals(
						385,
						referringSocialMediaJSONObject.getLong(
							"trafficAmount"));
					Assert.assertEquals(
						"Facebook",
						referringSocialMediaJSONObject.getString("title"));
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
	public void testGetSocialTrafficSourcesWithInvalidConnection()
		throws Exception {

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

					Assert.assertEquals(
						"An unexpected error occurred.",
						jsonObject.getString("error"));
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
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(
		filter = "mvc.command.name=/analytics_reports/get_social_traffic_sources"
	)
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