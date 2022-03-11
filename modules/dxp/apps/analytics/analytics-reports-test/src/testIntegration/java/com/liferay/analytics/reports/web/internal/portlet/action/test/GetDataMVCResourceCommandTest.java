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

import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.test.MockObject;
import com.liferay.analytics.reports.test.MockSuperClassObject;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockObjectAnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.analytics.reports.info.item.MockSuperClassObjectAnalyticsReportsInfoItem;
import com.liferay.analytics.reports.test.util.MockContextUtil;
import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockThemeDisplayUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsImpl;

import java.io.ByteArrayOutputStream;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
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
public class GetDataMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetAuthorWithoutPortraitURL() throws Exception {
		MockContextUtil.testWithMockContext(
			new MockContextUtil.MockContext.Builder().
				mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).build()
				).build(),
			() -> {
				MockLiferayResourceRequest mockLiferayResourceRequest =
					_getMockLiferayResourceRequest(
						new InfoItemReference(MockObject.class.getName(), 0L));

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setRequest(
					mockLiferayResourceRequest.getHttpServletRequest());

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					mockLiferayResourceRequest, mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					byteArrayOutputStream.toString());

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				Assert.assertNull(contextJSONObject.getJSONObject("author"));
			});
	}

	@Test
	public void testGetContext() throws Exception {
		String authorName = RandomTestUtil.randomString();
		String authorProfileImage =
			RandomTestUtil.randomString() + "?img_id=10";
		Date publishDate = new Date();
		String title = RandomTestUtil.randomString();

		MockContextUtil.testWithMockContext(
			new MockContextUtil.MockContext.Builder().
				mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).authorName(
						authorName
					).authorProfileImage(
						authorProfileImage
					).publishDate(
						publishDate
					).title(
						title
					).build()
				).build(),
			() -> {
				MockLiferayResourceRequest mockLiferayResourceRequest =
					_getMockLiferayResourceRequest(
						new InfoItemReference(MockObject.class.getName(), 0L));

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setRequest(
					mockLiferayResourceRequest.getHttpServletRequest());

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					mockLiferayResourceRequest, mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					new String(byteArrayOutputStream.toByteArray()));

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				JSONObject authorJSONObject = contextJSONObject.getJSONObject(
					"author");

				Assert.assertEquals(authorName, authorJSONObject.get("name"));
				Assert.assertEquals(
					authorProfileImage, authorJSONObject.get("url"));

				Instant instant = publishDate.toInstant();

				ZonedDateTime zonedDateTime = instant.atZone(
					ZoneId.systemDefault());

				LocalDate localDate = LocalDate.from(
					DateTimeFormatter.ISO_DATE.parse(
						contextJSONObject.getString("publishDate")));

				Assert.assertEquals(zonedDateTime.toLocalDate(), localDate);

				Assert.assertEquals(
					title, contextJSONObject.getString("title"));

				JSONArray jsonArray = contextJSONObject.getJSONArray(
					"viewURLs");

				Assert.assertEquals(
					String.valueOf(jsonArray), 1, jsonArray.length());

				JSONObject viewURLJSONObject = jsonArray.getJSONObject(0);

				Assert.assertEquals(
					Boolean.TRUE, viewURLJSONObject.getBoolean("default"));

				Locale locale = LocaleUtil.getDefault();

				Assert.assertEquals(
					LocaleUtil.toW3cLanguageId(locale),
					viewURLJSONObject.getString("languageId"));
				Assert.assertEquals(
					StringBundler.concat(
						locale.getDisplayLanguage(_locale), StringPool.SPACE,
						StringPool.OPEN_PARENTHESIS,
						locale.getDisplayCountry(_locale),
						StringPool.CLOSE_PARENTHESIS),
					viewURLJSONObject.getString("languageLabel"));

				String viewURL = viewURLJSONObject.getString("viewURL");

				Assert.assertTrue(
					viewURL.contains(
						"param_languageId=" + LocaleUtil.toLanguageId(locale)));
			});
	}

	@Test
	public void testGetContextWithClassNameClassPKInfoItemIdentifier()
		throws Exception {

		String authorName = RandomTestUtil.randomString();
		String authorProfileImage =
			RandomTestUtil.randomString() + "?img_id=10";
		Date publishDate = new Date();
		String title = RandomTestUtil.randomString();

		MockContextUtil.testWithMockContext(
			new MockContextUtil.MockContext.Builder().
				mockObjectAnalyticsReportsInfoItem(
					MockObjectAnalyticsReportsInfoItem.builder(
					).authorName(
						RandomTestUtil.randomString()
					).authorProfileImage(
						RandomTestUtil.randomString()
					).publishDate(
						new Date()
					).title(
						RandomTestUtil.randomString()
					).build()
				).mockSuperClassObjectAnalyticsReportsInfoItem(
					MockSuperClassObjectAnalyticsReportsInfoItem.builder(
					).authorName(
						authorName
					).authorProfileImage(
						authorProfileImage
					).publishDate(
						publishDate
					).title(
						title
					).build()
				).build(),
			() -> {
				MockLiferayResourceRequest mockLiferayResourceRequest =
					_getMockLiferayResourceRequest(
						new InfoItemReference(
							MockSuperClassObject.class.getName(),
							new ClassNameClassPKInfoItemIdentifier(
								MockObject.class.getName(), 0L)));

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setRequest(
					mockLiferayResourceRequest.getHttpServletRequest());

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					mockLiferayResourceRequest, mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					byteArrayOutputStream.toString());

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				JSONObject authorJSONObject = contextJSONObject.getJSONObject(
					"author");

				Assert.assertEquals(authorName, authorJSONObject.get("name"));
				Assert.assertEquals(
					authorProfileImage, authorJSONObject.get("url"));

				Instant instant = publishDate.toInstant();

				ZonedDateTime zonedDateTime = instant.atZone(
					ZoneId.systemDefault());

				LocalDate localDate = LocalDate.from(
					DateTimeFormatter.ISO_DATE.parse(
						contextJSONObject.getString("publishDate")));

				Assert.assertEquals(zonedDateTime.toLocalDate(), localDate);

				Assert.assertEquals(
					title, contextJSONObject.getString("title"));

				JSONArray jsonArray = contextJSONObject.getJSONArray(
					"viewURLs");

				Assert.assertEquals(
					String.valueOf(jsonArray), 1, jsonArray.length());

				JSONObject viewURLJSONObject = jsonArray.getJSONObject(0);

				Assert.assertEquals(
					Boolean.TRUE, viewURLJSONObject.getBoolean("default"));
				Assert.assertEquals(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					viewURLJSONObject.getString("languageId"));

				String viewURL = viewURLJSONObject.getString("viewURL");

				Assert.assertTrue(
					viewURL.contains(
						"param_languageId=" +
							LocaleUtil.toLanguageId(LocaleUtil.getDefault())));
			});
	}

	@Test
	public void testGetEndpoints() throws Exception {
		MockContextUtil.testWithMockContext(
			MockContextUtil.MockContext.builder(
			).mockObjectAnalyticsReportsInfoItem(
				MockObjectAnalyticsReportsInfoItem.builder(
				).build()
			).build(),
			() -> {
				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					_getMockLiferayResourceRequest(
						new InfoItemReference(MockObject.class.getName(), 0L)),
					mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					new String(byteArrayOutputStream.toByteArray()));

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				JSONObject endpointsJSONObject =
					contextJSONObject.getJSONObject("endpoints");

				Assert.assertNull(
					endpointsJSONObject.get(
						"analyticsReportsHistoricalReadsURL"));
				Assert.assertNotNull(
					endpointsJSONObject.get(
						"analyticsReportsHistoricalViewsURL"));
				Assert.assertNull(
					endpointsJSONObject.get("analyticsReportsTotalReadsURL"));
				Assert.assertNotNull(
					endpointsJSONObject.get("analyticsReportsTotalViewsURL"));
				Assert.assertNotNull(
					endpointsJSONObject.get(
						"analyticsReportsTrafficSourcesURL"));
			});
	}

	@Test
	public void testGetViewURLs() throws Exception {
		MockContextUtil.testWithMockContext(
			MockContextUtil.MockContext.builder(
			).build(),
			() -> {
				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					_getMockLiferayResourceRequest(
						new InfoItemReference(MockObject.class.getName(), 0L)),
					mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					new String(byteArrayOutputStream.toByteArray()));

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				JSONArray jsonArray = contextJSONObject.getJSONArray(
					"viewURLs");

				Assert.assertEquals(
					String.valueOf(jsonArray), jsonArray.length(), 1);

				JSONObject viewURLJSONObject = jsonArray.getJSONObject(0);

				Assert.assertEquals(
					Boolean.TRUE, viewURLJSONObject.getBoolean("default"));
				Assert.assertEquals(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					viewURLJSONObject.getString("languageId"));

				String viewURL = viewURLJSONObject.getString("viewURL");

				Assert.assertTrue(
					viewURL.contains(
						"param_languageId=" +
							LocaleUtil.toLanguageId(LocaleUtil.getDefault())));
			});
	}

	@Test
	public void testGetViewURLsWithMultipleLocales() throws Exception {
		MockContextUtil.testWithMockContext(
			MockContextUtil.MockContext.builder(
			).mockObjectAnalyticsReportsInfoItem(
				MockObjectAnalyticsReportsInfoItem.builder(
				).locales(
					Arrays.asList(LocaleUtil.SPAIN, LocaleUtil.US)
				).build()
			).build(),
			() -> {
				MockLiferayResourceResponse mockLiferayResourceResponse =
					new MockLiferayResourceResponse();

				_mvcResourceCommand.serveResource(
					_getMockLiferayResourceRequest(
						new InfoItemReference(MockObject.class.getName(), 0L)),
					mockLiferayResourceResponse);

				ByteArrayOutputStream byteArrayOutputStream =
					(ByteArrayOutputStream)
						mockLiferayResourceResponse.getPortletOutputStream();

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					new String(byteArrayOutputStream.toByteArray()));

				JSONObject contextJSONObject = jsonObject.getJSONObject(
					"context");

				JSONArray jsonArray = contextJSONObject.getJSONArray(
					"viewURLs");

				Assert.assertEquals(
					String.valueOf(jsonArray), jsonArray.length(), 2);

				JSONObject viewURLJSONObject1 = jsonArray.getJSONObject(0);

				Assert.assertEquals(
					Boolean.TRUE, viewURLJSONObject1.getBoolean("default"));
				Assert.assertEquals(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.SPAIN),
					viewURLJSONObject1.getString("languageId"));

				String viewURL1 = viewURLJSONObject1.getString("viewURL");

				Assert.assertTrue(
					viewURL1.contains(
						"param_languageId=" +
							LocaleUtil.toLanguageId(LocaleUtil.SPAIN)));

				JSONObject viewURLJSONObject2 = jsonArray.getJSONObject(1);

				Assert.assertEquals(
					Boolean.FALSE, viewURLJSONObject2.getBoolean("default"));
				Assert.assertEquals(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.US),
					viewURLJSONObject2.getString("languageId"));

				String viewURL2 = viewURLJSONObject2.getString("viewURL");

				Assert.assertTrue(
					viewURL2.contains(
						"param_languageId=" +
							LocaleUtil.toLanguageId(LocaleUtil.US)));
			});
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		InfoItemReference infoItemReference) {

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
					_locale));
			mockLiferayResourceRequest.setParameter(
				"className", infoItemReference.getClassName());

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

	private final Locale _locale = LocaleUtil.US;

	@Inject(filter = "mvc.command.name=/analytics_reports/get_data")
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