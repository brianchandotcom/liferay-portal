/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.design.library.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.design.library.client.dto.v1_0.StyleBook;
import com.liferay.headless.design.library.client.http.HttpInvoker;
import com.liferay.headless.design.library.client.pagination.Page;
import com.liferay.headless.design.library.client.pagination.Pagination;
import com.liferay.headless.design.library.client.resource.v1_0.StyleBookResource;
import com.liferay.headless.design.library.client.serdes.v1_0.StyleBookSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import jakarta.annotation.Generated;

import jakarta.ws.rs.core.MultivaluedHashMap;

import java.lang.reflect.Method;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

/**
 * @author Luis Ortiz
 * @generated
 */
@Generated("")
public abstract class BaseStyleBookResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

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

		irrelevantDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_ASSET_LIBRARY,
			new ServiceContext() {
				{
					setCompanyId(testCompany.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
		irrelevantDepotEntryGroup = irrelevantDepotEntry.getGroup();
		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null, DepotConstants.TYPE_ASSET_LIBRARY,
			new ServiceContext() {
				{
					setCompanyId(testCompany.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});
		testDepotEntryGroup = testDepotEntry.getGroup();

		_styleBookResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		styleBookResource = StyleBookResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@After
	public void tearDown() throws Exception {
		DepotEntryLocalServiceUtil.deleteDepotEntry(irrelevantDepotEntry);
		DepotEntryLocalServiceUtil.deleteDepotEntry(testDepotEntry);

		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		StyleBook styleBook1 = randomStyleBook();

		String json = objectMapper.writeValueAsString(styleBook1);

		StyleBook styleBook2 = StyleBookSerDes.toDTO(json);

		Assert.assertTrue(equals(styleBook1, styleBook2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		StyleBook styleBook = randomStyleBook();

		String json1 = objectMapper.writeValueAsString(styleBook);
		String json2 = StyleBookSerDes.toJSON(styleBook);

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

		StyleBook styleBook = randomStyleBook();

		styleBook.setExternalReferenceCode(regex);
		styleBook.setFrontendTokensValues(regex);
		styleBook.setKey(regex);
		styleBook.setName(regex);
		styleBook.setPreviewFileEntryExternalReferenceCode(regex);
		styleBook.setThemeId(regex);

		String json = StyleBookSerDes.toJSON(styleBook);

		Assert.assertFalse(json.contains(regex));

		styleBook = StyleBookSerDes.toDTO(json);

		Assert.assertEquals(regex, styleBook.getExternalReferenceCode());
		Assert.assertEquals(regex, styleBook.getFrontendTokensValues());
		Assert.assertEquals(regex, styleBook.getKey());
		Assert.assertEquals(regex, styleBook.getName());
		Assert.assertEquals(
			regex, styleBook.getPreviewFileEntryExternalReferenceCode());
		Assert.assertEquals(regex, styleBook.getThemeId());
	}

	@Test
	public void testDeleteAssetLibraryStyleBook() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		StyleBook styleBook = testDeleteAssetLibraryStyleBook_addStyleBook();

		assertHttpResponseStatusCode(
			204,
			styleBookResource.deleteAssetLibraryStyleBookHttpResponse(
				testDeleteAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
				styleBook.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			styleBookResource.getAssetLibraryStyleBookHttpResponse(
				testDeleteAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
				styleBook.getExternalReferenceCode()));
		assertHttpResponseStatusCode(
			404,
			styleBookResource.getAssetLibraryStyleBookHttpResponse(
				testDeleteAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
				"-"));
	}

	protected StyleBook testDeleteAssetLibraryStyleBook_addStyleBook()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testDeleteAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	@Test
	public void testGetAssetLibraryStyleBook() throws Exception {
		StyleBook postStyleBook = testGetAssetLibraryStyleBook_addStyleBook();

		StyleBook getStyleBook = styleBookResource.getAssetLibraryStyleBook(
			testGetAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
			postStyleBook.getExternalReferenceCode());

		assertEquals(postStyleBook, getStyleBook);
		assertValid(getStyleBook);
	}

	protected StyleBook testGetAssetLibraryStyleBook_addStyleBook()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	@Test
	public void testGetAssetLibraryStyleBooksPage() throws Exception {
		String assetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode();
		String irrelevantAssetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getIrrelevantAssetLibraryExternalReferenceCode();

		Page<StyleBook> page = styleBookResource.getAssetLibraryStyleBooksPage(
			assetLibraryExternalReferenceCode, null, null, null,
			Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		if (irrelevantAssetLibraryExternalReferenceCode != null) {
			StyleBook irrelevantStyleBook =
				testGetAssetLibraryStyleBooksPage_addStyleBook(
					irrelevantAssetLibraryExternalReferenceCode,
					randomIrrelevantStyleBook());

			page = styleBookResource.getAssetLibraryStyleBooksPage(
				irrelevantAssetLibraryExternalReferenceCode, null, null, null,
				Pagination.of(1, (int)totalCount + 1), null);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(
				irrelevantStyleBook, (List<StyleBook>)page.getItems());
			assertValid(
				page,
				testGetAssetLibraryStyleBooksPage_getExpectedActions(
					irrelevantAssetLibraryExternalReferenceCode));
		}

		StyleBook styleBook1 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		StyleBook styleBook2 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		page = styleBookResource.getAssetLibraryStyleBooksPage(
			assetLibraryExternalReferenceCode, null, null, null,
			Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(styleBook1, (List<StyleBook>)page.getItems());
		assertContains(styleBook2, (List<StyleBook>)page.getItems());
		assertValid(
			page,
			testGetAssetLibraryStyleBooksPage_getExpectedActions(
				assetLibraryExternalReferenceCode));
	}

	protected Map<String, Map<String, String>>
			testGetAssetLibraryStyleBooksPage_getExpectedActions(
				String assetLibraryExternalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String assetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode();

		StyleBook styleBook1 = randomStyleBook();

		styleBook1 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, styleBook1);

		for (EntityField entityField : entityFields) {
			Page<StyleBook> page =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null,
					getFilterString(entityField, "between", styleBook1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(styleBook1),
				(List<StyleBook>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithFilterDoubleEquals()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithFilterStringContains()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithFilterStringEquals()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithFilterStringStartsWith()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetAssetLibraryStyleBooksPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String assetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode();

		StyleBook styleBook1 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		StyleBook styleBook2 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		for (EntityField entityField : entityFields) {
			Page<StyleBook> page =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null,
					getFilterString(entityField, operator, styleBook1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(styleBook1),
				(List<StyleBook>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithPagination()
		throws Exception {

		String assetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode();

		Page<StyleBook> styleBooksPage =
			styleBookResource.getAssetLibraryStyleBooksPage(
				assetLibraryExternalReferenceCode, null, null, null, null,
				null);

		int totalCount = GetterUtil.getInteger(styleBooksPage.getTotalCount());

		StyleBook styleBook1 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		StyleBook styleBook2 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		StyleBook styleBook3 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, randomStyleBook());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<StyleBook> page1 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(styleBook1, (List<StyleBook>)page1.getItems());

			Page<StyleBook> page2 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(styleBook2, (List<StyleBook>)page2.getItems());

			Page<StyleBook> page3 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(styleBook3, (List<StyleBook>)page3.getItems());
		}
		else {
			Page<StyleBook> page1 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(1, totalCount + 2), null);

			List<StyleBook> styleBooks1 = (List<StyleBook>)page1.getItems();

			Assert.assertEquals(
				styleBooks1.toString(), totalCount + 2, styleBooks1.size());

			Page<StyleBook> page2 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<StyleBook> styleBooks2 = (List<StyleBook>)page2.getItems();

			Assert.assertEquals(styleBooks2.toString(), 1, styleBooks2.size());

			Page<StyleBook> page3 =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(1, (int)totalCount + 3), null);

			assertContains(styleBook1, (List<StyleBook>)page3.getItems());
			assertContains(styleBook2, (List<StyleBook>)page3.getItems());
			assertContains(styleBook3, (List<StyleBook>)page3.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, styleBook1, styleBook2) -> {
				BeanTestUtil.setProperty(
					styleBook1, entityField.getName(),
					new Date(System.currentTimeMillis() - (2 * Time.MINUTE)));
			});
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithSortDouble()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, styleBook1, styleBook2) -> {
				BeanTestUtil.setProperty(
					styleBook1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					styleBook2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, styleBook1, styleBook2) -> {
				BeanTestUtil.setProperty(styleBook1, entityField.getName(), 0);
				BeanTestUtil.setProperty(styleBook2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryStyleBooksPageWithSortString()
		throws Exception {

		testGetAssetLibraryStyleBooksPageWithSort(
			EntityField.Type.STRING,
			(entityField, styleBook1, styleBook2) -> {
				Class<?> clazz = styleBook1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						styleBook1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						styleBook2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						styleBook1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						styleBook2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						styleBook1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						styleBook2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryStyleBooksPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, StyleBook, StyleBook, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String assetLibraryExternalReferenceCode =
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode();

		StyleBook styleBook1 = randomStyleBook();
		StyleBook styleBook2 = randomStyleBook();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, styleBook1, styleBook2);
		}

		styleBook1 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, styleBook1);

		styleBook2 = testGetAssetLibraryStyleBooksPage_addStyleBook(
			assetLibraryExternalReferenceCode, styleBook2);

		Page<StyleBook> page = styleBookResource.getAssetLibraryStyleBooksPage(
			assetLibraryExternalReferenceCode, null, null, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<StyleBook> ascPage =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":asc");

			assertContains(styleBook1, (List<StyleBook>)ascPage.getItems());
			assertContains(styleBook2, (List<StyleBook>)ascPage.getItems());

			Page<StyleBook> descPage =
				styleBookResource.getAssetLibraryStyleBooksPage(
					assetLibraryExternalReferenceCode, null, null, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":desc");

			assertContains(styleBook2, (List<StyleBook>)descPage.getItems());
			assertContains(styleBook1, (List<StyleBook>)descPage.getItems());
		}
	}

	protected StyleBook testGetAssetLibraryStyleBooksPage_addStyleBook(
			String assetLibraryExternalReferenceCode, StyleBook styleBook)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAssetLibraryStyleBooksPage_getAssetLibraryExternalReferenceCode()
		throws Exception {

		return testDepotEntryGroup.getExternalReferenceCode();
	}

	protected String
			testGetAssetLibraryStyleBooksPage_getIrrelevantAssetLibraryExternalReferenceCode()
		throws Exception {

		return irrelevantDepotEntryGroup.getExternalReferenceCode();
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		StyleBook styleBook, List<StyleBook> styleBooks) {

		boolean contains = false;

		for (StyleBook item : styleBooks) {
			if (equals(styleBook, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			styleBooks + " does not contain " + styleBook, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(StyleBook styleBook1, StyleBook styleBook2) {
		Assert.assertTrue(
			styleBook1 + " does not equal " + styleBook2,
			equals(styleBook1, styleBook2));
	}

	protected void assertEquals(
		List<StyleBook> styleBooks1, List<StyleBook> styleBooks2) {

		Assert.assertEquals(styleBooks1.size(), styleBooks2.size());

		for (int i = 0; i < styleBooks1.size(); i++) {
			StyleBook styleBook1 = styleBooks1.get(i);
			StyleBook styleBook2 = styleBooks2.get(i);

			assertEquals(styleBook1, styleBook2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<StyleBook> styleBooks1, List<StyleBook> styleBooks2) {

		Assert.assertEquals(styleBooks1.size(), styleBooks2.size());

		for (StyleBook styleBook1 : styleBooks1) {
			boolean contains = false;

			for (StyleBook styleBook2 : styleBooks2) {
				if (equals(styleBook1, styleBook2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				styleBooks2 + " does not contain " + styleBook1, contains);
		}
	}

	protected void assertValid(StyleBook styleBook) throws Exception {
		boolean valid = true;

		if (styleBook.getDateCreated() == null) {
			valid = false;
		}

		if (styleBook.getDateModified() == null) {
			valid = false;
		}

		if (styleBook.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (styleBook.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (styleBook.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultStyleBook", additionalAssertFieldName)) {
				if (styleBook.getDefaultStyleBook() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (styleBook.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"frontendTokensValues", additionalAssertFieldName)) {

				if (styleBook.getFrontendTokensValues() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (styleBook.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (styleBook.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"previewFileEntryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (styleBook.getPreviewFileEntryExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("themeId", additionalAssertFieldName)) {
				if (styleBook.getThemeId() == null) {
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

	protected void assertValid(Page<StyleBook> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<StyleBook> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<StyleBook> styleBooks = page.getItems();

		int size = styleBooks.size();

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
					com.liferay.headless.design.library.dto.v1_0.StyleBook.
						class)) {

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

	protected boolean equals(StyleBook styleBook1, StyleBook styleBook2) {
		if (styleBook1 == styleBook2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)styleBook1.getActions(),
						(Map)styleBook2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getCreator(), styleBook2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getDateCreated(),
						styleBook2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getDateModified(),
						styleBook2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultStyleBook", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getDefaultStyleBook(),
						styleBook2.getDefaultStyleBook())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						styleBook1.getExternalReferenceCode(),
						styleBook2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"frontendTokensValues", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						styleBook1.getFrontendTokensValues(),
						styleBook2.getFrontendTokensValues())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getId(), styleBook2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getKey(), styleBook2.getKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getName(), styleBook2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"previewFileEntryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						styleBook1.getPreviewFileEntryExternalReferenceCode(),
						styleBook2.
							getPreviewFileEntryExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("themeId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						styleBook1.getThemeId(), styleBook2.getThemeId())) {

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

		if (!(_styleBookResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_styleBookResource;

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
		EntityField entityField, String operator, StyleBook styleBook) {

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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				Date date = styleBook.getDateCreated();

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

				sb.append(_format.format(styleBook.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				Date date = styleBook.getDateModified();

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

				sb.append(_format.format(styleBook.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultStyleBook")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = styleBook.getExternalReferenceCode();

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

		if (entityFieldName.equals("frontendTokensValues")) {
			Object object = styleBook.getFrontendTokensValues();

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

		if (entityFieldName.equals("key")) {
			Object object = styleBook.getKey();

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

		if (entityFieldName.equals("name")) {
			Object object = styleBook.getName();

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

		if (entityFieldName.equals("previewFileEntryExternalReferenceCode")) {
			Object object =
				styleBook.getPreviewFileEntryExternalReferenceCode();

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

		if (entityFieldName.equals("themeId")) {
			Object object = styleBook.getThemeId();

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
		httpInvoker.path(
			"http://localhost:" + PortalUtil.getPortalServerPort(false) +
				"/o/graphql");
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

	protected StyleBook randomStyleBook() throws Exception {
		return new StyleBook() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultStyleBook = RandomTestUtil.randomBoolean();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				frontendTokensValues = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				previewFileEntryExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				themeId = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected StyleBook randomIrrelevantStyleBook() throws Exception {
		StyleBook randomIrrelevantStyleBook = randomStyleBook();

		return randomIrrelevantStyleBook;
	}

	protected StyleBook randomPatchStyleBook() throws Exception {
		return randomStyleBook();
	}

	protected StyleBookResource styleBookResource;
	protected com.liferay.portal.kernel.model.Group irrelevantGroup;
	protected com.liferay.portal.kernel.model.Company testCompany;
	protected DepotEntry irrelevantDepotEntry;
	protected com.liferay.portal.kernel.model.Group irrelevantDepotEntryGroup;
	protected DepotEntry testDepotEntry;
	protected com.liferay.portal.kernel.model.Group testDepotEntryGroup;
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
		LogFactoryUtil.getLog(BaseStyleBookResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.headless.design.library.resource.v1_0.StyleBookResource
		_styleBookResource;

}
// LIFERAY-REST-BUILDER-HASH:1090169203