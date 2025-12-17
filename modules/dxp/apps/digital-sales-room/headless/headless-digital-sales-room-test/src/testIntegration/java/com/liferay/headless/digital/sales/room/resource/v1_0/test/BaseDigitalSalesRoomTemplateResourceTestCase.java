/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.digital.sales.room.client.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.client.http.HttpInvoker;
import com.liferay.headless.digital.sales.room.client.pagination.Page;
import com.liferay.headless.digital.sales.room.client.pagination.Pagination;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.DigitalSalesRoomTemplateResource;
import com.liferay.headless.digital.sales.room.client.serdes.v1_0.DigitalSalesRoomTemplateSerDes;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateBuilderRegistry;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.lang.reflect.Method;

import java.net.URI;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public abstract class BaseDigitalSalesRoomTemplateResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_digitalSalesRoomTemplateResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		digitalSalesRoomTemplateResource =
			DigitalSalesRoomTemplateResource.builder(
			).authentication(
				_testCompanyAdminUser.getEmailAddress(),
				PropsValues.DEFAULT_ADMIN_PASSWORD
			).endpoint(
				testCompany.getVirtualHostname(), 8080, "http"
			).locale(
				LocaleUtil.getDefault()
			).build();
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
			randomDigitalSalesRoomTemplate();

		String json = objectMapper.writeValueAsString(
			digitalSalesRoomTemplate1);

		DigitalSalesRoomTemplate digitalSalesRoomTemplate2 =
			DigitalSalesRoomTemplateSerDes.toDTO(json);

		Assert.assertTrue(
			equals(digitalSalesRoomTemplate1, digitalSalesRoomTemplate2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		String json1 = objectMapper.writeValueAsString(
			digitalSalesRoomTemplate);
		String json2 = DigitalSalesRoomTemplateSerDes.toJSON(
			digitalSalesRoomTemplate);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	protected ObjectMapper getClientSerDesObjectMapper() {
		return new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		digitalSalesRoomTemplate.setClientName(regex);
		digitalSalesRoomTemplate.setDescription(regex);
		digitalSalesRoomTemplate.setExternalReferenceCode(regex);
		digitalSalesRoomTemplate.setFriendlyUrlPath(regex);
		digitalSalesRoomTemplate.setName(regex);
		digitalSalesRoomTemplate.setPrimaryColor(regex);
		digitalSalesRoomTemplate.setSecondaryColor(regex);

		String json = DigitalSalesRoomTemplateSerDes.toJSON(
			digitalSalesRoomTemplate);

		Assert.assertFalse(json.contains(regex));

		digitalSalesRoomTemplate = DigitalSalesRoomTemplateSerDes.toDTO(json);

		Assert.assertEquals(regex, digitalSalesRoomTemplate.getClientName());
		Assert.assertEquals(regex, digitalSalesRoomTemplate.getDescription());
		Assert.assertEquals(
			regex, digitalSalesRoomTemplate.getExternalReferenceCode());
		Assert.assertEquals(
			regex, digitalSalesRoomTemplate.getFriendlyUrlPath());
		Assert.assertEquals(regex, digitalSalesRoomTemplate.getName());
		Assert.assertEquals(regex, digitalSalesRoomTemplate.getPrimaryColor());
		Assert.assertEquals(
			regex, digitalSalesRoomTemplate.getSecondaryColor());
	}

	@Test
	public void testDeleteDigitalSalesRoomTemplate() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DigitalSalesRoomTemplate digitalSalesRoomTemplate =
			testDeleteDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate();

		assertHttpResponseStatusCode(
			204,
			digitalSalesRoomTemplateResource.
				deleteDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId()));

		assertHttpResponseStatusCode(
			404,
			digitalSalesRoomTemplateResource.
				getDigitalSalesRoomTemplateHttpResponse(
					digitalSalesRoomTemplate.getId()));
		assertHttpResponseStatusCode(
			404,
			digitalSalesRoomTemplateResource.
				getDigitalSalesRoomTemplateHttpResponse(0L));
	}

	protected DigitalSalesRoomTemplate
			testDeleteDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDigitalSalesRoomTemplate() throws Exception {
		DigitalSalesRoomTemplate postDigitalSalesRoomTemplate =
			testGetDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate getDigitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplate(
				postDigitalSalesRoomTemplate.getId());

		assertEquals(postDigitalSalesRoomTemplate, getDigitalSalesRoomTemplate);
		assertValid(getDigitalSalesRoomTemplate);
	}

	@Test
	public void testVulcanCRUDItemDelegateGetItem() throws Exception {
		DigitalSalesRoomTemplate postDigitalSalesRoomTemplate =
			testGetDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate getDigitalSalesRoomTemplate =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplate(
				postDigitalSalesRoomTemplate.getId());

		VulcanCRUDItemDelegate vulcanCRUDItemDelegate =
			_vulcanCRUDItemDelegateBuilderRegistry.builder(
				testCompany,
				"com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoomTemplate"
			).acceptLanguage(
				new AcceptLanguage() {

					@Override
					public List<Locale> getLocales() {
						return Arrays.asList(LocaleUtil.getDefault());
					}

					@Override
					public String getPreferredLanguageId() {
						return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
					}

					@Override
					public Locale getPreferredLocale() {
						return LocaleUtil.getDefault();
					}

				}
			).groupLocalService(
				_groupLocalService
			).httpServletRequest(
				testVulcanCRUDItemDelegate_getHttpServletRequest()
			).httpServletResponse(
				new MockHttpServletResponse()
			).resourceActionLocalService(
				_resourceActionLocalService
			).resourcePermissionLocalService(
				_resourcePermissionLocalService
			).roleLocalService(
				_roleLocalService
			).scopeChecker(
				_scopeChecker
			).uriInfo(
				testVulcanCRUDItemDelegate_getUriInfo()
			).user(
				testVulcanCRUDItemDelegate_getUser()
			).build();

		Object item = vulcanCRUDItemDelegate.getItem(
			postDigitalSalesRoomTemplate.getId());

		assertEquals(
			getDigitalSalesRoomTemplate,
			DigitalSalesRoomTemplateSerDes.toDTO(item.toString()));
	}

	protected HttpServletRequest
		testVulcanCRUDItemDelegate_getHttpServletRequest() {

		return new MockHttpServletRequest() {

			@Override
			public StringBuffer getRequestURL() {
				return new StringBuffer(
					StringBundler.concat(
						"http://localhost:8080/o/v1.0/",
						RandomTestUtil.randomString(), "/",
						RandomTestUtil.randomString()));
			}

		};
	}

	protected UriInfo testVulcanCRUDItemDelegate_getUriInfo() {
		String applicationPath = RandomTestUtil.randomString() + "/";
		String resourcePath = RandomTestUtil.randomString();

		return new UriInfo() {

			@Override
			public String getPath() {
				return resourcePath;
			}

			@Override
			public String getPath(boolean decode) {
				return getPath();
			}

			@Override
			public List<PathSegment> getPathSegments() {
				return Collections.emptyList();
			}

			@Override
			public List<PathSegment> getPathSegments(boolean decode) {
				return getPathSegments();
			}

			@Override
			public URI getRequestUri() {
				return URI.create(
					"http://localhost:8080/o/" + applicationPath +
						resourcePath);
			}

			@Override
			public UriBuilder getRequestUriBuilder() {
				return UriBuilder.fromUri(getRequestUri());
			}

			@Override
			public URI getAbsolutePath() {
				return getRequestUri();
			}

			@Override
			public UriBuilder getAbsolutePathBuilder() {
				return getRequestUriBuilder();
			}

			@Override
			public URI getBaseUri() {
				return URI.create("http://localhost:8080/o/" + applicationPath);
			}

			@Override
			public UriBuilder getBaseUriBuilder() {
				return UriBuilder.fromUri(getBaseUri());
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters() {
				return new MultivaluedHashMap<>();
			}

			@Override
			public MultivaluedMap<String, String> getPathParameters(
				boolean decode) {

				return getPathParameters();
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters() {
				return new MultivaluedHashMap<>();
			}

			@Override
			public MultivaluedMap<String, String> getQueryParameters(
				boolean decode) {

				return getQueryParameters();
			}

			@Override
			public List<String> getMatchedURIs() {
				return Collections.emptyList();
			}

			@Override
			public List<String> getMatchedURIs(boolean decode) {
				return getMatchedURIs();
			}

			@Override
			public List<Object> getMatchedResources() {
				return Collections.emptyList();
			}

			@Override
			public URI resolve(URI requestUri) {
				return getBaseUri().resolve(requestUri);
			}

			@Override
			public URI relativize(URI uri) {
				return getBaseUri().relativize(uri);
			}

		};
	}

	protected com.liferay.portal.kernel.model.User
		testVulcanCRUDItemDelegate_getUser() {

		return _testCompanyAdminUser;
	}

	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDigitalSalesRoomTemplatesPage() throws Exception {
		Page<DigitalSalesRoomTemplate> page =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplatesPage(
				null, Pagination.of(1, 10));

		long totalCount = page.getTotalCount();

		DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate2 =
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		page =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplatesPage(
				null, Pagination.of(1, 10));

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(
			digitalSalesRoomTemplate1,
			(List<DigitalSalesRoomTemplate>)page.getItems());
		assertContains(
			digitalSalesRoomTemplate2,
			(List<DigitalSalesRoomTemplate>)page.getItems());
		assertValid(
			page, testGetDigitalSalesRoomTemplatesPage_getExpectedActions());

		digitalSalesRoomTemplateResource.deleteDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate1.getId());

		digitalSalesRoomTemplateResource.deleteDigitalSalesRoomTemplate(
			digitalSalesRoomTemplate2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetDigitalSalesRoomTemplatesPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDigitalSalesRoomTemplatesPageWithPagination()
		throws Exception {

		Page<DigitalSalesRoomTemplate> digitalSalesRoomTemplatesPage =
			digitalSalesRoomTemplateResource.getDigitalSalesRoomTemplatesPage(
				null, null);

		int totalCount = GetterUtil.getInteger(
			digitalSalesRoomTemplatesPage.getTotalCount());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate2 =
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		DigitalSalesRoomTemplate digitalSalesRoomTemplate3 =
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<DigitalSalesRoomTemplate> page1 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null,
						Pagination.of(
							(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
							pageSizeLimit));

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(
				digitalSalesRoomTemplate1,
				(List<DigitalSalesRoomTemplate>)page1.getItems());

			Page<DigitalSalesRoomTemplate> page2 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null,
						Pagination.of(
							(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
							pageSizeLimit));

			assertContains(
				digitalSalesRoomTemplate2,
				(List<DigitalSalesRoomTemplate>)page2.getItems());

			Page<DigitalSalesRoomTemplate> page3 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null,
						Pagination.of(
							(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
							pageSizeLimit));

			assertContains(
				digitalSalesRoomTemplate3,
				(List<DigitalSalesRoomTemplate>)page3.getItems());
		}
		else {
			Page<DigitalSalesRoomTemplate> page1 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null, Pagination.of(1, totalCount + 2));

			List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates1 =
				(List<DigitalSalesRoomTemplate>)page1.getItems();

			Assert.assertEquals(
				digitalSalesRoomTemplates1.toString(), totalCount + 2,
				digitalSalesRoomTemplates1.size());

			Page<DigitalSalesRoomTemplate> page2 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null, Pagination.of(2, totalCount + 2));

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates2 =
				(List<DigitalSalesRoomTemplate>)page2.getItems();

			Assert.assertEquals(
				digitalSalesRoomTemplates2.toString(), 1,
				digitalSalesRoomTemplates2.size());

			Page<DigitalSalesRoomTemplate> page3 =
				digitalSalesRoomTemplateResource.
					getDigitalSalesRoomTemplatesPage(
						null, Pagination.of(1, (int)totalCount + 3));

			assertContains(
				digitalSalesRoomTemplate1,
				(List<DigitalSalesRoomTemplate>)page3.getItems());
			assertContains(
				digitalSalesRoomTemplate2,
				(List<DigitalSalesRoomTemplate>)page3.getItems());
			assertContains(
				digitalSalesRoomTemplate3,
				(List<DigitalSalesRoomTemplate>)page3.getItems());
		}
	}

	protected DigitalSalesRoomTemplate
			testGetDigitalSalesRoomTemplatesPage_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostDigitalSalesRoomDigitalSalesRoomTemplate()
		throws Exception {

		DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate postDigitalSalesRoomTemplate =
			testPostDigitalSalesRoomDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate);

		assertEquals(
			randomDigitalSalesRoomTemplate, postDigitalSalesRoomTemplate);
		assertValid(postDigitalSalesRoomTemplate);
	}

	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostDigitalSalesRoomTemplate() throws Exception {
		DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		DigitalSalesRoomTemplate postDigitalSalesRoomTemplate =
			testPostDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				randomDigitalSalesRoomTemplate);

		assertEquals(
			randomDigitalSalesRoomTemplate, postDigitalSalesRoomTemplate);
		assertValid(postDigitalSalesRoomTemplate);
	}

	protected DigitalSalesRoomTemplate
			testPostDigitalSalesRoomTemplate_addDigitalSalesRoomTemplate(
				DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate,
		List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates) {

		boolean contains = false;

		for (DigitalSalesRoomTemplate item : digitalSalesRoomTemplates) {
			if (equals(digitalSalesRoomTemplate, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			digitalSalesRoomTemplates + " does not contain " +
				digitalSalesRoomTemplate,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate1,
		DigitalSalesRoomTemplate digitalSalesRoomTemplate2) {

		Assert.assertTrue(
			digitalSalesRoomTemplate1 + " does not equal " +
				digitalSalesRoomTemplate2,
			equals(digitalSalesRoomTemplate1, digitalSalesRoomTemplate2));
	}

	protected void assertEquals(
		List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates1,
		List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates2) {

		Assert.assertEquals(
			digitalSalesRoomTemplates1.size(),
			digitalSalesRoomTemplates2.size());

		for (int i = 0; i < digitalSalesRoomTemplates1.size(); i++) {
			DigitalSalesRoomTemplate digitalSalesRoomTemplate1 =
				digitalSalesRoomTemplates1.get(i);
			DigitalSalesRoomTemplate digitalSalesRoomTemplate2 =
				digitalSalesRoomTemplates2.get(i);

			assertEquals(digitalSalesRoomTemplate1, digitalSalesRoomTemplate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates1,
		List<DigitalSalesRoomTemplate> digitalSalesRoomTemplates2) {

		Assert.assertEquals(
			digitalSalesRoomTemplates1.size(),
			digitalSalesRoomTemplates2.size());

		for (DigitalSalesRoomTemplate digitalSalesRoomTemplate1 :
				digitalSalesRoomTemplates1) {

			boolean contains = false;

			for (DigitalSalesRoomTemplate digitalSalesRoomTemplate2 :
					digitalSalesRoomTemplates2) {

				if (equals(
						digitalSalesRoomTemplate1, digitalSalesRoomTemplate2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				digitalSalesRoomTemplates2 + " does not contain " +
					digitalSalesRoomTemplate1,
				contains);
		}
	}

	protected void assertValid(
			DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		boolean valid = true;

		if (digitalSalesRoomTemplate.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("banner", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getBanner() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("clientLogo", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getClientLogo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("clientName", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getClientName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (digitalSalesRoomTemplate.getExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primaryColor", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getPrimaryColor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("secondaryColor", additionalAssertFieldName)) {
				if (digitalSalesRoomTemplate.getSecondaryColor() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<DigitalSalesRoomTemplate> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<DigitalSalesRoomTemplate> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<DigitalSalesRoomTemplate>
			digitalSalesRoomTemplates = page.getItems();

		int size = digitalSalesRoomTemplates.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("externalReferenceCode"));

		graphQLFields.add(new GraphQLField("id"));

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.digital.sales.room.dto.v1_0.
						DigitalSalesRoomTemplate.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate1,
		DigitalSalesRoomTemplate digitalSalesRoomTemplate2) {

		if (digitalSalesRoomTemplate1 == digitalSalesRoomTemplate2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)digitalSalesRoomTemplate1.getActions(),
						(Map)digitalSalesRoomTemplate2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("banner", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getBanner(),
						digitalSalesRoomTemplate2.getBanner())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("clientLogo", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getClientLogo(),
						digitalSalesRoomTemplate2.getClientLogo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("clientName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getClientName(),
						digitalSalesRoomTemplate2.getClientName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getCreateDate(),
						digitalSalesRoomTemplate2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getDescription(),
						digitalSalesRoomTemplate2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getExternalReferenceCode(),
						digitalSalesRoomTemplate2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getFriendlyUrlPath(),
						digitalSalesRoomTemplate2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getId(),
						digitalSalesRoomTemplate2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getModifiedDate(),
						digitalSalesRoomTemplate2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getName(),
						digitalSalesRoomTemplate2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primaryColor", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getPrimaryColor(),
						digitalSalesRoomTemplate2.getPrimaryColor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("secondaryColor", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						digitalSalesRoomTemplate1.getSecondaryColor(),
						digitalSalesRoomTemplate2.getSecondaryColor())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		if (clazz.getClassLoader() == null) {
			return new java.lang.reflect.Field[0];
		}

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_digitalSalesRoomTemplateResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_digitalSalesRoomTemplateResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		DigitalSalesRoomTemplate digitalSalesRoomTemplate) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("banner")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("clientLogo")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("clientName")) {
			Object object = digitalSalesRoomTemplate.getClientName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				Date date = digitalSalesRoomTemplate.getCreateDate();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_format.format(digitalSalesRoomTemplate.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			Object object = digitalSalesRoomTemplate.getDescription();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = digitalSalesRoomTemplate.getExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			Object object = digitalSalesRoomTemplate.getFriendlyUrlPath();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				Date date = digitalSalesRoomTemplate.getModifiedDate();

				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(_format.format(date.getTime() - (2 * Time.SECOND)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(_format.format(date.getTime() + (2 * Time.SECOND)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_format.format(digitalSalesRoomTemplate.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			Object object = digitalSalesRoomTemplate.getName();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("primaryColor")) {
			Object object = digitalSalesRoomTemplate.getPrimaryColor();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("secondaryColor")) {
			Object object = digitalSalesRoomTemplate.getSecondaryColor();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword(
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD);

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DigitalSalesRoomTemplate randomDigitalSalesRoomTemplate()
		throws Exception {

		return new DigitalSalesRoomTemplate() {
			{
				clientName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				friendlyUrlPath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				primaryColor = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				secondaryColor = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected DigitalSalesRoomTemplate
			randomIrrelevantDigitalSalesRoomTemplate()
		throws Exception {

		DigitalSalesRoomTemplate randomIrrelevantDigitalSalesRoomTemplate =
			randomDigitalSalesRoomTemplate();

		return randomIrrelevantDigitalSalesRoomTemplate;
	}

	protected DigitalSalesRoomTemplate randomPatchDigitalSalesRoomTemplate()
		throws Exception {

		return randomDigitalSalesRoomTemplate();
	}

	protected DigitalSalesRoomTemplateResource digitalSalesRoomTemplateResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected com.liferay.portal.kernel.model.Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = source.getClass();

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					_getAllDeclaredFields(sourceClass)) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				try {
					Method setMethod = _getMethod(
						targetClass, field.getName(), "set",
						getMethod.getReturnType());

					setMethod.invoke(target, getMethod.invoke(source));
				}
				catch (Exception e) {
					continue;
				}
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static List<java.lang.reflect.Field> _getAllDeclaredFields(
			Class<?> clazz) {

			List<java.lang.reflect.Field> fields = new ArrayList<>();

			while ((clazz != null) && (clazz != Object.class)) {
				for (java.lang.reflect.Field field :
						clazz.getDeclaredFields()) {

					fields.add(field);
				}

				clazz = clazz.getSuperclass();
			}

			return fields;
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(
			BaseDigitalSalesRoomTemplateResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.headless.digital.sales.room.resource.v1_0.
		DigitalSalesRoomTemplateResource _digitalSalesRoomTemplateResource;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private ScopeChecker _scopeChecker;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private VulcanCRUDItemDelegateBuilderRegistry
		_vulcanCRUDItemDelegateBuilderRegistry;

}