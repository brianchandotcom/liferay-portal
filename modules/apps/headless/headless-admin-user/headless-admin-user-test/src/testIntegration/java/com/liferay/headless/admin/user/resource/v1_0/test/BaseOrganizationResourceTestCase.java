/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.Organization;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.OrganizationResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.OrganizationSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Method;

import java.text.DateFormat;

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

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseOrganizationResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_organizationResource.setContextCompany(testCompany);

		OrganizationResource.Builder builder = OrganizationResource.builder();

		organizationResource = builder.authentication(
			"test@liferay.com", "test"
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
		ObjectMapper objectMapper = new ObjectMapper() {
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

		Organization organization1 = randomOrganization();

		String json = objectMapper.writeValueAsString(organization1);

		Organization organization2 = OrganizationSerDes.toDTO(json);

		Assert.assertTrue(equals(organization1, organization2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Organization organization = randomOrganization();

		String json1 = objectMapper.writeValueAsString(organization);
		String json2 = OrganizationSerDes.toJSON(organization);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Organization organization = randomOrganization();

		organization.setComment(regex);
		organization.setExternalReferenceCode(regex);
		organization.setId(regex);
		organization.setImage(regex);
		organization.setName(regex);
		organization.setTreePath(regex);

		String json = OrganizationSerDes.toJSON(organization);

		Assert.assertFalse(json.contains(regex));

		organization = OrganizationSerDes.toDTO(json);

		Assert.assertEquals(regex, organization.getComment());
		Assert.assertEquals(regex, organization.getExternalReferenceCode());
		Assert.assertEquals(regex, organization.getId());
		Assert.assertEquals(regex, organization.getImage());
		Assert.assertEquals(regex, organization.getName());
		Assert.assertEquals(regex, organization.getTreePath());
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPage()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getIrrelevantExternalReferenceCode();

		Page<Organization> page =
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationsPage(
					externalReferenceCode, null, null, Pagination.of(1, 10),
					null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			Organization irrelevantOrganization =
				testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrganization());

			page =
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						irrelevantExternalReferenceCode, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrganization),
				(List<Organization>)page.getItems());
			assertValid(
				page,
				testGetAccountByExternalReferenceCodeOrganizationsPage_getExpectedActions(
					irrelevantExternalReferenceCode));
		}

		Organization organization1 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		Organization organization2 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		page =
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationsPage(
					externalReferenceCode, null, null, Pagination.of(1, 10),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(
			page,
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExpectedActions(
				externalReferenceCode));

		organizationResource.deleteOrganization(organization1.getId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExpectedActions(
				String externalReferenceCode)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode();

		Organization organization1 = randomOrganization();

		organization1 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						externalReferenceCode, null,
						getFilterString(entityField, "between", organization1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithFilterStringContains()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithFilterStringEquals()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void
			testGetAccountByExternalReferenceCodeOrganizationsPageWithFilter(
				String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode();

		Organization organization1 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						externalReferenceCode, null,
						getFilterString(entityField, operator, organization1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode();

		Organization organization1 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		Organization organization2 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		Organization organization3 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, randomOrganization());

		Page<Organization> page1 =
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationsPage(
					externalReferenceCode, null, null, Pagination.of(1, 2),
					null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 =
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationsPage(
					externalReferenceCode, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 =
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationsPage(
					externalReferenceCode, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithSortDateTime()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithSortDouble()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithSortInteger()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganizationsPageWithSortString()
		throws Exception {

		testGetAccountByExternalReferenceCodeOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetAccountByExternalReferenceCodeOrganizationsPageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, Organization, Organization, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode();

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, organization1);

		organization2 =
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				externalReferenceCode, organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						externalReferenceCode, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.
					getAccountByExternalReferenceCodeOrganizationsPage(
						externalReferenceCode, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization
			testGetAccountByExternalReferenceCodeOrganizationsPage_addOrganization(
				String externalReferenceCode, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeOrganizationsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeOrganizationsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteAccountByExternalReferenceCodeOrganization()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testDeleteAccountByExternalReferenceCodeOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.
				deleteAccountByExternalReferenceCodeOrganizationHttpResponse(
					testDeleteAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
						organization),
					organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationHttpResponse(
					testDeleteAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
						organization),
					organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				getAccountByExternalReferenceCodeOrganizationHttpResponse(
					testDeleteAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
						organization),
					"-"));
	}

	protected String
			testDeleteAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
				Organization organization)
		throws Exception {

		return organization.getExternalReferenceCode();
	}

	protected Organization
			testDeleteAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCodeOrganization()
		throws Exception {

		Organization postOrganization =
			testGetAccountByExternalReferenceCodeOrganization_addOrganization();

		Organization getOrganization =
			organizationResource.getAccountByExternalReferenceCodeOrganization(
				testGetAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
					postOrganization),
				postOrganization.getId());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected String
			testGetAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
				Organization organization)
		throws Exception {

		return organization.getExternalReferenceCode();
	}

	protected Organization
			testGetAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeOrganization()
		throws Exception {

		Organization organization =
			testGraphQLGetAccountByExternalReferenceCodeOrganization_addOrganization();

		Assert.assertTrue(
			equals(
				organization,
				OrganizationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountByExternalReferenceCodeOrganization",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												testGraphQLGetAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
													organization) + "\"");

										put(
											"organizationId",
											"\"" + organization.getId() + "\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/accountByExternalReferenceCodeOrganization"))));
	}

	protected String
			testGraphQLGetAccountByExternalReferenceCodeOrganization_getExternalReferenceCode(
				Organization organization)
		throws Exception {

		return organization.getExternalReferenceCode();
	}

	@Test
	public void testGraphQLGetAccountByExternalReferenceCodeOrganizationNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantOrganizationId =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountByExternalReferenceCodeOrganization",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
								put("organizationId", irrelevantOrganizationId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Organization
			testGraphQLGetAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testPostAccountByExternalReferenceCodeOrganization()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testPostAccountByExternalReferenceCodeOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.
				postAccountByExternalReferenceCodeOrganizationHttpResponse(
					organization.getExternalReferenceCode(),
					organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				postAccountByExternalReferenceCodeOrganizationHttpResponse(
					organization.getExternalReferenceCode(), "-"));
	}

	protected Organization
			testPostAccountByExternalReferenceCodeOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountOrganizationsPage() throws Exception {
		Long accountId = testGetAccountOrganizationsPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountOrganizationsPage_getIrrelevantAccountId();

		Page<Organization> page =
			organizationResource.getAccountOrganizationsPage(
				accountId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAccountId != null) {
			Organization irrelevantOrganization =
				testGetAccountOrganizationsPage_addOrganization(
					irrelevantAccountId, randomIrrelevantOrganization());

			page = organizationResource.getAccountOrganizationsPage(
				irrelevantAccountId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrganization),
				(List<Organization>)page.getItems());
			assertValid(
				page,
				testGetAccountOrganizationsPage_getExpectedActions(
					irrelevantAccountId));
		}

		Organization organization1 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		Organization organization2 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		page = organizationResource.getAccountOrganizationsPage(
			accountId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(
			page,
			testGetAccountOrganizationsPage_getExpectedActions(accountId));

		organizationResource.deleteOrganization(organization1.getId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetAccountOrganizationsPage_getExpectedActions(Long accountId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetAccountOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountOrganizationsPage_getAccountId();

		Organization organization1 = randomOrganization();

		organization1 = testGetAccountOrganizationsPage_addOrganization(
			accountId, organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getAccountOrganizationsPage(
					accountId, null,
					getFilterString(entityField, "between", organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetAccountOrganizationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetAccountOrganizationsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetAccountOrganizationsPageWithFilterStringContains()
		throws Exception {

		testGetAccountOrganizationsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountOrganizationsPageWithFilterStringEquals()
		throws Exception {

		testGetAccountOrganizationsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetAccountOrganizationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetAccountOrganizationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetAccountOrganizationsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountOrganizationsPage_getAccountId();

		Organization organization1 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getAccountOrganizationsPage(
					accountId, null,
					getFilterString(entityField, operator, organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetAccountOrganizationsPageWithPagination()
		throws Exception {

		Long accountId = testGetAccountOrganizationsPage_getAccountId();

		Organization organization1 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		Organization organization2 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		Organization organization3 =
			testGetAccountOrganizationsPage_addOrganization(
				accountId, randomOrganization());

		Page<Organization> page1 =
			organizationResource.getAccountOrganizationsPage(
				accountId, null, null, Pagination.of(1, 2), null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 =
			organizationResource.getAccountOrganizationsPage(
				accountId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 =
			organizationResource.getAccountOrganizationsPage(
				accountId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetAccountOrganizationsPageWithSortDateTime()
		throws Exception {

		testGetAccountOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountOrganizationsPageWithSortDouble()
		throws Exception {

		testGetAccountOrganizationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountOrganizationsPageWithSortInteger()
		throws Exception {

		testGetAccountOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountOrganizationsPageWithSortString()
		throws Exception {

		testGetAccountOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountOrganizationsPage_getAccountId();

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 = testGetAccountOrganizationsPage_addOrganization(
			accountId, organization1);

		organization2 = testGetAccountOrganizationsPage_addOrganization(
			accountId, organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getAccountOrganizationsPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getAccountOrganizationsPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization testGetAccountOrganizationsPage_addOrganization(
			Long accountId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountOrganizationsPage_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountOrganizationsPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteAccountOrganization() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testDeleteAccountOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.deleteAccountOrganizationHttpResponse(
				testDeleteAccountOrganization_getAccountId(),
				organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.getAccountOrganizationHttpResponse(
				testDeleteAccountOrganization_getAccountId(),
				organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.getAccountOrganizationHttpResponse(
				testDeleteAccountOrganization_getAccountId(), "-"));
	}

	protected Long testDeleteAccountOrganization_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Organization testDeleteAccountOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountOrganization() throws Exception {
		Organization postOrganization =
			testGetAccountOrganization_addOrganization();

		Organization getOrganization =
			organizationResource.getAccountOrganization(
				testGetAccountOrganization_getAccountId(),
				postOrganization.getId());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Long testGetAccountOrganization_getAccountId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Organization testGetAccountOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountOrganization() throws Exception {
		Organization organization =
			testGraphQLGetAccountOrganization_addOrganization();

		Assert.assertTrue(
			equals(
				organization,
				OrganizationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountOrganization",
								new HashMap<String, Object>() {
									{
										put(
											"accountId",
											testGraphQLGetAccountOrganization_getAccountId());

										put(
											"organizationId",
											"\"" + organization.getId() + "\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/accountOrganization"))));
	}

	protected Long testGraphQLGetAccountOrganization_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountOrganizationNotFound() throws Exception {
		Long irrelevantAccountId = RandomTestUtil.randomLong();
		String irrelevantOrganizationId =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountOrganization",
						new HashMap<String, Object>() {
							{
								put("accountId", irrelevantAccountId);
								put("organizationId", irrelevantOrganizationId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Organization testGraphQLGetAccountOrganization_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testPostAccountOrganization() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testPostAccountOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.postAccountOrganizationHttpResponse(
				null, organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.postAccountOrganizationHttpResponse(
				null, "-"));
	}

	protected Organization testPostAccountOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrganizationsPage() throws Exception {
		Page<Organization> page = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		page = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(organization1, (List<Organization>)page.getItems());
		assertContains(organization2, (List<Organization>)page.getItems());
		assertValid(page, testGetOrganizationsPage_getExpectedActions());

		organizationResource.deleteOrganization(organization1.getId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetOrganizationsPage_getExpectedActions()
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = randomOrganization();

		organization1 = testGetOrganizationsPage_addOrganization(organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page = organizationResource.getOrganizationsPage(
				null, null,
				getFilterString(entityField, "between", organization1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetOrganizationsPageWithFilter("eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetOrganizationsPageWithFilterStringContains()
		throws Exception {

		testGetOrganizationsPageWithFilter("contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationsPageWithFilterStringEquals()
		throws Exception {

		testGetOrganizationsPageWithFilter("eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetOrganizationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetOrganizationsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page = organizationResource.getOrganizationsPage(
				null, null,
				getFilterString(entityField, operator, organization1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationsPageWithPagination() throws Exception {
		Page<Organization> totalPage =
			organizationResource.getOrganizationsPage(
				null, null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Organization organization1 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization2 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Organization organization3 = testGetOrganizationsPage_addOrganization(
			randomOrganization());

		Page<Organization> page1 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, totalCount + 2), null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), totalCount + 2, organizations1.size());

		Page<Organization> page2 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 = organizationResource.getOrganizationsPage(
			null, null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(organization1, (List<Organization>)page3.getItems());
		assertContains(organization2, (List<Organization>)page3.getItems());
		assertContains(organization3, (List<Organization>)page3.getItems());
	}

	@Test
	public void testGetOrganizationsPageWithSortDateTime() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationsPageWithSortDouble() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetOrganizationsPageWithSortInteger() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationsPageWithSortString() throws Exception {
		testGetOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 = testGetOrganizationsPage_addOrganization(organization1);

		organization2 = testGetOrganizationsPage_addOrganization(organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getOrganizationsPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getOrganizationsPage(
					null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization testGetOrganizationsPage_addOrganization(
			Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrganizationsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"organizations",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject organizationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/organizations");

		long totalCount = organizationsJSONObject.getLong("totalCount");

		Organization organization1 =
			testGraphQLGetOrganizationsPage_addOrganization();
		Organization organization2 =
			testGraphQLGetOrganizationsPage_addOrganization();

		organizationsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/organizations");

		Assert.assertEquals(
			totalCount + 2, organizationsJSONObject.getLong("totalCount"));

		assertContains(
			organization1,
			Arrays.asList(
				OrganizationSerDes.toDTOs(
					organizationsJSONObject.getString("items"))));
		assertContains(
			organization2,
			Arrays.asList(
				OrganizationSerDes.toDTOs(
					organizationsJSONObject.getString("items"))));
	}

	protected Organization testGraphQLGetOrganizationsPage_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testPostOrganization() throws Exception {
		Organization randomOrganization = randomOrganization();

		Organization postOrganization = testPostOrganization_addOrganization(
			randomOrganization);

		assertEquals(randomOrganization, postOrganization);
		assertValid(postOrganization);
	}

	protected Organization testPostOrganization_addOrganization(
			Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrganizationByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testDeleteOrganizationByExternalReferenceCode_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.
				deleteOrganizationByExternalReferenceCodeHttpResponse(
					organization.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				getOrganizationByExternalReferenceCodeHttpResponse(
					organization.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.
				getOrganizationByExternalReferenceCodeHttpResponse(
					organization.getExternalReferenceCode()));
	}

	protected Organization
			testDeleteOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrganizationByExternalReferenceCode() throws Exception {
		Organization postOrganization =
			testGetOrganizationByExternalReferenceCode_addOrganization();

		Organization getOrganization =
			organizationResource.getOrganizationByExternalReferenceCode(
				postOrganization.getExternalReferenceCode());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization
			testGetOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrganizationByExternalReferenceCode()
		throws Exception {

		Organization organization =
			testGraphQLGetOrganizationByExternalReferenceCode_addOrganization();

		Assert.assertTrue(
			equals(
				organization,
				OrganizationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"organizationByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												organization.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/organizationByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetOrganizationByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"organizationByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Organization
			testGraphQLGetOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testPatchOrganizationByExternalReferenceCode()
		throws Exception {

		Organization postOrganization =
			testPatchOrganizationByExternalReferenceCode_addOrganization();

		Organization randomPatchOrganization = randomPatchOrganization();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization patchOrganization =
			organizationResource.patchOrganizationByExternalReferenceCode(
				postOrganization.getExternalReferenceCode(),
				randomPatchOrganization);

		Organization expectedPatchOrganization = postOrganization.clone();

		BeanTestUtil.copyProperties(
			randomPatchOrganization, expectedPatchOrganization);

		Organization getOrganization =
			organizationResource.getOrganizationByExternalReferenceCode(
				patchOrganization.getExternalReferenceCode());

		assertEquals(expectedPatchOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization
			testPatchOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutOrganizationByExternalReferenceCode() throws Exception {
		Organization postOrganization =
			testPutOrganizationByExternalReferenceCode_addOrganization();

		Organization randomOrganization = randomOrganization();

		Organization putOrganization =
			organizationResource.putOrganizationByExternalReferenceCode(
				postOrganization.getExternalReferenceCode(),
				randomOrganization);

		assertEquals(randomOrganization, putOrganization);
		assertValid(putOrganization);

		Organization getOrganization =
			organizationResource.getOrganizationByExternalReferenceCode(
				putOrganization.getExternalReferenceCode());

		assertEquals(randomOrganization, getOrganization);
		assertValid(getOrganization);

		Organization newOrganization =
			testPutOrganizationByExternalReferenceCode_createOrganization();

		putOrganization =
			organizationResource.putOrganizationByExternalReferenceCode(
				newOrganization.getExternalReferenceCode(), newOrganization);

		assertEquals(newOrganization, putOrganization);
		assertValid(putOrganization);

		getOrganization =
			organizationResource.getOrganizationByExternalReferenceCode(
				putOrganization.getExternalReferenceCode());

		assertEquals(newOrganization, getOrganization);

		Assert.assertEquals(
			newOrganization.getExternalReferenceCode(),
			putOrganization.getExternalReferenceCode());
	}

	protected Organization
			testPutOrganizationByExternalReferenceCode_createOrganization()
		throws Exception {

		return randomOrganization();
	}

	protected Organization
			testPutOrganizationByExternalReferenceCode_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrganization() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization = testDeleteOrganization_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.deleteOrganizationHttpResponse(
				organization.getId()));

		assertHttpResponseStatusCode(
			404,
			organizationResource.getOrganizationHttpResponse(
				organization.getId()));

		assertHttpResponseStatusCode(
			404, organizationResource.getOrganizationHttpResponse("-"));
	}

	protected Organization testDeleteOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteOrganization() throws Exception {
		Organization organization =
			testGraphQLDeleteOrganization_addOrganization();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrganization",
						new HashMap<String, Object>() {
							{
								put(
									"organizationId",
									"\"" + organization.getId() + "\"");
							}
						})),
				"JSONObject/data", "Object/deleteOrganization"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"organization",
					new HashMap<String, Object>() {
						{
							put(
								"organizationId",
								"\"" + organization.getId() + "\"");
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected Organization testGraphQLDeleteOrganization_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testGetOrganization() throws Exception {
		Organization postOrganization = testGetOrganization_addOrganization();

		Organization getOrganization = organizationResource.getOrganization(
			postOrganization.getId());

		assertEquals(postOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrganization() throws Exception {
		Organization organization =
			testGraphQLGetOrganization_addOrganization();

		Assert.assertTrue(
			equals(
				organization,
				OrganizationSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"organization",
								new HashMap<String, Object>() {
									{
										put(
											"organizationId",
											"\"" + organization.getId() + "\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/organization"))));
	}

	@Test
	public void testGraphQLGetOrganizationNotFound() throws Exception {
		String irrelevantOrganizationId =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"organization",
						new HashMap<String, Object>() {
							{
								put("organizationId", irrelevantOrganizationId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected Organization testGraphQLGetOrganization_addOrganization()
		throws Exception {

		return testGraphQLOrganization_addOrganization();
	}

	@Test
	public void testPatchOrganization() throws Exception {
		Organization postOrganization = testPatchOrganization_addOrganization();

		Organization randomPatchOrganization = randomPatchOrganization();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization patchOrganization = organizationResource.patchOrganization(
			postOrganization.getId(), randomPatchOrganization);

		Organization expectedPatchOrganization = postOrganization.clone();

		BeanTestUtil.copyProperties(
			randomPatchOrganization, expectedPatchOrganization);

		Organization getOrganization = organizationResource.getOrganization(
			patchOrganization.getId());

		assertEquals(expectedPatchOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization testPatchOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutOrganization() throws Exception {
		Organization postOrganization = testPutOrganization_addOrganization();

		Organization randomOrganization = randomOrganization();

		Organization putOrganization = organizationResource.putOrganization(
			postOrganization.getId(), randomOrganization);

		assertEquals(randomOrganization, putOrganization);
		assertValid(putOrganization);

		Organization getOrganization = organizationResource.getOrganization(
			putOrganization.getId());

		assertEquals(randomOrganization, getOrganization);
		assertValid(getOrganization);
	}

	protected Organization testPutOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrganizationChildOrganizationsPage() throws Exception {
		String organizationId =
			testGetOrganizationChildOrganizationsPage_getOrganizationId();
		String irrelevantOrganizationId =
			testGetOrganizationChildOrganizationsPage_getIrrelevantOrganizationId();

		Page<Organization> page =
			organizationResource.getOrganizationChildOrganizationsPage(
				organizationId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantOrganizationId != null) {
			Organization irrelevantOrganization =
				testGetOrganizationChildOrganizationsPage_addOrganization(
					irrelevantOrganizationId, randomIrrelevantOrganization());

			page = organizationResource.getOrganizationChildOrganizationsPage(
				irrelevantOrganizationId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrganization),
				(List<Organization>)page.getItems());
			assertValid(
				page,
				testGetOrganizationChildOrganizationsPage_getExpectedActions(
					irrelevantOrganizationId));
		}

		Organization organization1 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		page = organizationResource.getOrganizationChildOrganizationsPage(
			organizationId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(
			page,
			testGetOrganizationChildOrganizationsPage_getExpectedActions(
				organizationId));

		organizationResource.deleteOrganization(organization1.getId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetOrganizationChildOrganizationsPage_getExpectedActions(
				String organizationId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationChildOrganizationsPage_getOrganizationId();

		Organization organization1 = randomOrganization();

		organization1 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationChildOrganizationsPage(
					organizationId, null, null,
					getFilterString(entityField, "between", organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithFilterStringContains()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithFilterStringEquals()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetOrganizationChildOrganizationsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationChildOrganizationsPage_getOrganizationId();

		Organization organization1 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationChildOrganizationsPage(
					organizationId, null, null,
					getFilterString(entityField, operator, organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithPagination()
		throws Exception {

		String organizationId =
			testGetOrganizationChildOrganizationsPage_getOrganizationId();

		Organization organization1 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Organization organization3 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, randomOrganization());

		Page<Organization> page1 =
			organizationResource.getOrganizationChildOrganizationsPage(
				organizationId, null, null, null, Pagination.of(1, 2), null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 =
			organizationResource.getOrganizationChildOrganizationsPage(
				organizationId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 =
			organizationResource.getOrganizationChildOrganizationsPage(
				organizationId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithSortDateTime()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithSortDouble()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithSortInteger()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationChildOrganizationsPageWithSortString()
		throws Exception {

		testGetOrganizationChildOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrganizationChildOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String organizationId =
			testGetOrganizationChildOrganizationsPage_getOrganizationId();

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, organization1);

		organization2 =
			testGetOrganizationChildOrganizationsPage_addOrganization(
				organizationId, organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getOrganizationChildOrganizationsPage(
					organizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getOrganizationChildOrganizationsPage(
					organizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization
			testGetOrganizationChildOrganizationsPage_addOrganization(
				String organizationId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationChildOrganizationsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationChildOrganizationsPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteUserAccountsByEmailAddress() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testDeleteUserAccountsByEmailAddress_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.deleteUserAccountsByEmailAddressHttpResponse(
				organization.getId(), null));
	}

	protected Organization
			testDeleteUserAccountsByEmailAddress_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostUserAccountsByEmailAddress() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteUserAccountByEmailAddress() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization =
			testDeleteUserAccountByEmailAddress_addOrganization();

		assertHttpResponseStatusCode(
			204,
			organizationResource.deleteUserAccountByEmailAddressHttpResponse(
				organization.getId(),
				testDeleteUserAccountByEmailAddress_getEmailAddress()));
	}

	protected String testDeleteUserAccountByEmailAddress_getEmailAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Organization testDeleteUserAccountByEmailAddress_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrganizationOrganizationsPage() throws Exception {
		String parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();
		String irrelevantParentOrganizationId =
			testGetOrganizationOrganizationsPage_getIrrelevantParentOrganizationId();

		Page<Organization> page =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(1, 10),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantParentOrganizationId != null) {
			Organization irrelevantOrganization =
				testGetOrganizationOrganizationsPage_addOrganization(
					irrelevantParentOrganizationId,
					randomIrrelevantOrganization());

			page = organizationResource.getOrganizationOrganizationsPage(
				irrelevantParentOrganizationId, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrganization),
				(List<Organization>)page.getItems());
			assertValid(
				page,
				testGetOrganizationOrganizationsPage_getExpectedActions(
					irrelevantParentOrganizationId));
		}

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		page = organizationResource.getOrganizationOrganizationsPage(
			parentOrganizationId, null, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2),
			(List<Organization>)page.getItems());
		assertValid(
			page,
			testGetOrganizationOrganizationsPage_getExpectedActions(
				parentOrganizationId));

		organizationResource.deleteOrganization(organization1.getId());

		organizationResource.deleteOrganization(organization2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetOrganizationOrganizationsPage_getExpectedActions(
				String parentOrganizationId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 = randomOrganization();

		organization1 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization1);

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null,
					getFilterString(entityField, "between", organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterDoubleEquals()
		throws Exception {

		testGetOrganizationOrganizationsPageWithFilter(
			"eq", EntityField.Type.DOUBLE);
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterStringContains()
		throws Exception {

		testGetOrganizationOrganizationsPageWithFilter(
			"contains", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterStringEquals()
		throws Exception {

		testGetOrganizationOrganizationsPageWithFilter(
			"eq", EntityField.Type.STRING);
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithFilterStringStartsWith()
		throws Exception {

		testGetOrganizationOrganizationsPageWithFilter(
			"startswith", EntityField.Type.STRING);
	}

	protected void testGetOrganizationOrganizationsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		for (EntityField entityField : entityFields) {
			Page<Organization> page =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null,
					getFilterString(entityField, operator, organization1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(organization1),
				(List<Organization>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithPagination()
		throws Exception {

		String parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization2 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Organization organization3 =
			testGetOrganizationOrganizationsPage_addOrganization(
				parentOrganizationId, randomOrganization());

		Page<Organization> page1 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(1, 2),
				null);

		List<Organization> organizations1 =
			(List<Organization>)page1.getItems();

		Assert.assertEquals(
			organizations1.toString(), 2, organizations1.size());

		Page<Organization> page2 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Organization> organizations2 =
			(List<Organization>)page2.getItems();

		Assert.assertEquals(
			organizations2.toString(), 1, organizations2.size());

		Page<Organization> page3 =
			organizationResource.getOrganizationOrganizationsPage(
				parentOrganizationId, null, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(organization1, organization2, organization3),
			(List<Organization>)page3.getItems());
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortDateTime()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortDouble()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortInteger()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, organization1, organization2) -> {
				BeanTestUtil.setProperty(
					organization1, entityField.getName(), 0);
				BeanTestUtil.setProperty(
					organization2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrganizationOrganizationsPageWithSortString()
		throws Exception {

		testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type.STRING,
			(entityField, organization1, organization2) -> {
				Class<?> clazz = organization1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						organization1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						organization2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrganizationOrganizationsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, Organization, Organization, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String parentOrganizationId =
			testGetOrganizationOrganizationsPage_getParentOrganizationId();

		Organization organization1 = randomOrganization();
		Organization organization2 = randomOrganization();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, organization1, organization2);
		}

		organization1 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization1);

		organization2 = testGetOrganizationOrganizationsPage_addOrganization(
			parentOrganizationId, organization2);

		for (EntityField entityField : entityFields) {
			Page<Organization> ascPage =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(organization1, organization2),
				(List<Organization>)ascPage.getItems());

			Page<Organization> descPage =
				organizationResource.getOrganizationOrganizationsPage(
					parentOrganizationId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(organization2, organization1),
				(List<Organization>)descPage.getItems());
		}
	}

	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			String parentOrganizationId, Organization organization)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationOrganizationsPage_getParentOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrganizationOrganizationsPage_getIrrelevantParentOrganizationId()
		throws Exception {

		return null;
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Test
	public void testPostUserAccountByEmailAddress() throws Exception {
		Assert.assertTrue(true);
	}

	protected Organization testGraphQLOrganization_addOrganization()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		Organization organization, List<Organization> organizations) {

		boolean contains = false;

		for (Organization item : organizations) {
			if (equals(organization, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			organizations + " does not contain " + organization, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Organization organization1, Organization organization2) {

		Assert.assertTrue(
			organization1 + " does not equal " + organization2,
			equals(organization1, organization2));
	}

	protected void assertEquals(
		List<Organization> organizations1, List<Organization> organizations2) {

		Assert.assertEquals(organizations1.size(), organizations2.size());

		for (int i = 0; i < organizations1.size(); i++) {
			Organization organization1 = organizations1.get(i);
			Organization organization2 = organizations2.get(i);

			assertEquals(organization1, organization2);
		}
	}

	protected void assertEquals(
		UserAccount userAccount1, UserAccount userAccount2) {

		Assert.assertTrue(
			userAccount1 + " does not equal " + userAccount2,
			equals(userAccount1, userAccount2));
	}

	protected void assertEqualsIgnoringOrder(
		List<Organization> organizations1, List<Organization> organizations2) {

		Assert.assertEquals(organizations1.size(), organizations2.size());

		for (Organization organization1 : organizations1) {
			boolean contains = false;

			for (Organization organization2 : organizations2) {
				if (equals(organization1, organization2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				organizations2 + " does not contain " + organization1,
				contains);
		}
	}

	protected void assertValid(Organization organization) throws Exception {
		boolean valid = true;

		if (organization.getDateCreated() == null) {
			valid = false;
		}

		if (organization.getDateModified() == null) {
			valid = false;
		}

		if (organization.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (organization.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"childOrganizations", additionalAssertFieldName)) {

				if (organization.getChildOrganizations() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("comment", additionalAssertFieldName)) {
				if (organization.getComment() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (organization.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (organization.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (organization.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (organization.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (organization.getLocation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (organization.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfAccounts", additionalAssertFieldName)) {
				if (organization.getNumberOfAccounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfOrganizations", additionalAssertFieldName)) {

				if (organization.getNumberOfOrganizations() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfUsers", additionalAssertFieldName)) {
				if (organization.getNumberOfUsers() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationAccounts", additionalAssertFieldName)) {

				if (organization.getOrganizationAccounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationContactInformation",
					additionalAssertFieldName)) {

				if (organization.getOrganizationContactInformation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrganization", additionalAssertFieldName)) {

				if (organization.getParentOrganization() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("services", additionalAssertFieldName)) {
				if (organization.getServices() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("treePath", additionalAssertFieldName)) {
				if (organization.getTreePath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userAccounts", additionalAssertFieldName)) {
				if (organization.getUserAccounts() == null) {
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

	protected void assertValid(Page<Organization> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Organization> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Organization> organizations = page.getItems();

		int size = organizations.size();

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

	protected void assertValid(UserAccount userAccount) {
		boolean valid = true;

		if (userAccount.getDateCreated() == null) {
			valid = false;
		}

		if (userAccount.getDateModified() == null) {
			valid = false;
		}

		if (userAccount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalUserAccountAssertFieldNames()) {

			if (Objects.equals("accountBriefs", additionalAssertFieldName)) {
				if (userAccount.getAccountBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (userAccount.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (userAccount.getAdditionalName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("alternateName", additionalAssertFieldName)) {
				if (userAccount.getAlternateName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("birthDate", additionalAssertFieldName)) {
				if (userAccount.getBirthDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currentPassword", additionalAssertFieldName)) {
				if (userAccount.getCurrentPassword() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (userAccount.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dashboardURL", additionalAssertFieldName)) {
				if (userAccount.getDashboardURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (userAccount.getEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (userAccount.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (userAccount.getFamilyName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (userAccount.getGivenName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("honorificPrefix", additionalAssertFieldName)) {
				if (userAccount.getHonorificPrefix() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("honorificSuffix", additionalAssertFieldName)) {
				if (userAccount.getHonorificSuffix() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (userAccount.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("jobTitle", additionalAssertFieldName)) {
				if (userAccount.getJobTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (userAccount.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("lastLoginDate", additionalAssertFieldName)) {
				if (userAccount.getLastLoginDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (userAccount.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationBriefs", additionalAssertFieldName)) {

				if (userAccount.getOrganizationBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("password", additionalAssertFieldName)) {
				if (userAccount.getPassword() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (userAccount.getProfileURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("roleBriefs", additionalAssertFieldName)) {
				if (userAccount.getRoleBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("siteBriefs", additionalAssertFieldName)) {
				if (userAccount.getSiteBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"userAccountContactInformation",
					additionalAssertFieldName)) {

				if (userAccount.getUserAccountContactInformation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userGroupBriefs", additionalAssertFieldName)) {
				if (userAccount.getUserGroupBriefs() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalUserAccountAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.admin.user.dto.v1_0.Organization.
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

	protected boolean equals(
		Organization organization1, Organization organization2) {

		if (organization1 == organization2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)organization1.getActions(),
						(Map)organization2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"childOrganizations", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getChildOrganizations(),
						organization2.getChildOrganizations())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("comment", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getComment(),
						organization2.getComment())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getCustomFields(),
						organization2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getDateCreated(),
						organization2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getDateModified(),
						organization2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getExternalReferenceCode(),
						organization2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getId(), organization2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getImage(), organization2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getKeywords(),
						organization2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getLocation(),
						organization2.getLocation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getName(), organization2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfAccounts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getNumberOfAccounts(),
						organization2.getNumberOfAccounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfOrganizations", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getNumberOfOrganizations(),
						organization2.getNumberOfOrganizations())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfUsers", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getNumberOfUsers(),
						organization2.getNumberOfUsers())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationAccounts", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getOrganizationAccounts(),
						organization2.getOrganizationAccounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationContactInformation",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getOrganizationContactInformation(),
						organization2.getOrganizationContactInformation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrganization", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						organization1.getParentOrganization(),
						organization2.getParentOrganization())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("services", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getServices(),
						organization2.getServices())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("treePath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getTreePath(),
						organization2.getTreePath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userAccounts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						organization1.getUserAccounts(),
						organization2.getUserAccounts())) {

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

	protected boolean equals(
		UserAccount userAccount1, UserAccount userAccount2) {

		if (userAccount1 == userAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalUserAccountAssertFieldNames()) {

			if (Objects.equals("accountBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getAccountBriefs(),
						userAccount2.getAccountBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getActions(), userAccount2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getAdditionalName(),
						userAccount2.getAdditionalName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("alternateName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getAlternateName(),
						userAccount2.getAlternateName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("birthDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getBirthDate(),
						userAccount2.getBirthDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currentPassword", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getCurrentPassword(),
						userAccount2.getCurrentPassword())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getCustomFields(),
						userAccount2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dashboardURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDashboardURL(),
						userAccount2.getDashboardURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDateCreated(),
						userAccount2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDateModified(),
						userAccount2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("emailAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getEmailAddress(),
						userAccount2.getEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userAccount1.getExternalReferenceCode(),
						userAccount2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getFamilyName(),
						userAccount2.getFamilyName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getGivenName(),
						userAccount2.getGivenName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificPrefix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getHonorificPrefix(),
						userAccount2.getHonorificPrefix())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificSuffix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getHonorificSuffix(),
						userAccount2.getHonorificSuffix())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getId(), userAccount2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getImage(), userAccount2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("jobTitle", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getJobTitle(),
						userAccount2.getJobTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getKeywords(),
						userAccount2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("lastLoginDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getLastLoginDate(),
						userAccount2.getLastLoginDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getName(), userAccount2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userAccount1.getOrganizationBriefs(),
						userAccount2.getOrganizationBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("password", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getPassword(),
						userAccount2.getPassword())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getProfileURL(),
						userAccount2.getProfileURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getRoleBriefs(),
						userAccount2.getRoleBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getSiteBriefs(),
						userAccount2.getSiteBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"userAccountContactInformation",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userAccount1.getUserAccountContactInformation(),
						userAccount2.getUserAccountContactInformation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userGroupBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getUserGroupBriefs(),
						userAccount2.getUserGroupBriefs())) {

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

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

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

		if (!(_organizationResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_organizationResource;

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
		EntityField entityField, String operator, Organization organization) {

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

		if (entityFieldName.equals("childOrganizations")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("comment")) {
			Object object = organization.getComment();

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

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(organization.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							organization.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(organization.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = organization.getExternalReferenceCode();

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
			Object object = organization.getId();

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

		if (entityFieldName.equals("image")) {
			Object object = organization.getImage();

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

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("location")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			Object object = organization.getName();

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

		if (entityFieldName.equals("numberOfAccounts")) {
			sb.append(String.valueOf(organization.getNumberOfAccounts()));

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfOrganizations")) {
			sb.append(String.valueOf(organization.getNumberOfOrganizations()));

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfUsers")) {
			sb.append(String.valueOf(organization.getNumberOfUsers()));

			return sb.toString();
		}

		if (entityFieldName.equals("organizationAccounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("organizationContactInformation")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentOrganization")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("services")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("treePath")) {
			Object object = organization.getTreePath();

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

		if (entityFieldName.equals("userAccounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
		httpInvoker.userNameAndPassword("test@liferay.com:test");

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

	protected Organization randomOrganization() throws Exception {
		return new Organization() {
			{
				comment = StringUtil.toLowerCase(RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = StringUtil.toLowerCase(RandomTestUtil.randomString());
				image = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				numberOfAccounts = RandomTestUtil.randomInt();
				numberOfOrganizations = RandomTestUtil.randomInt();
				numberOfUsers = RandomTestUtil.randomInt();
				treePath = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Organization randomIrrelevantOrganization() throws Exception {
		Organization randomIrrelevantOrganization = randomOrganization();

		return randomIrrelevantOrganization;
	}

	protected Organization randomPatchOrganization() throws Exception {
		return randomOrganization();
	}

	protected UserAccount randomUserAccount() throws Exception {
		return new UserAccount() {
			{
				additionalName = RandomTestUtil.randomString();
				alternateName = RandomTestUtil.randomString();
				birthDate = RandomTestUtil.nextDate();
				currentPassword = RandomTestUtil.randomString();
				dashboardURL = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				emailAddress = RandomTestUtil.randomString();
				externalReferenceCode = RandomTestUtil.randomString();
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				honorificPrefix = RandomTestUtil.randomString();
				honorificSuffix = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				jobTitle = RandomTestUtil.randomString();
				lastLoginDate = RandomTestUtil.nextDate();
				name = RandomTestUtil.randomString();
				password = RandomTestUtil.randomString();
				profileURL = RandomTestUtil.randomString();
			}
		};
	}

	protected OrganizationResource organizationResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
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

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
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
		LogFactoryUtil.getLog(BaseOrganizationResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.OrganizationResource
		_organizationResource;

}