/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.ai.hub.rest.client.dto.v1_0.ModelArmorTemplate;
import com.liferay.ai.hub.rest.client.http.HttpInvoker;
import com.liferay.ai.hub.rest.client.pagination.Page;
import com.liferay.ai.hub.rest.client.resource.v1_0.ModelArmorTemplateResource;
import com.liferay.ai.hub.rest.client.serdes.v1_0.ModelArmorTemplateSerDes;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker.HttpResponse;
import com.liferay.headless.batch.engine.client.resource.v1_0.ImportTaskResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
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
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public abstract class BaseModelArmorTemplateResourceTestCase {

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

		_modelArmorTemplateResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		modelArmorTemplateResource = ModelArmorTemplateResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).locale(
			LocaleUtil.getDefault()
		).build();

		importTaskResource = ImportTaskResource.builder(
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
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		ModelArmorTemplate modelArmorTemplate1 = randomModelArmorTemplate();

		String json = objectMapper.writeValueAsString(modelArmorTemplate1);

		ModelArmorTemplate modelArmorTemplate2 = ModelArmorTemplateSerDes.toDTO(
			json);

		Assert.assertTrue(equals(modelArmorTemplate1, modelArmorTemplate2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		ModelArmorTemplate modelArmorTemplate = randomModelArmorTemplate();

		String json1 = objectMapper.writeValueAsString(modelArmorTemplate);
		String json2 = ModelArmorTemplateSerDes.toJSON(modelArmorTemplate);

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

		ModelArmorTemplate modelArmorTemplate = randomModelArmorTemplate();

		modelArmorTemplate.setDescription(regex);
		modelArmorTemplate.setExternalReferenceCode(regex);
		modelArmorTemplate.setLocation(regex);
		modelArmorTemplate.setTitle(regex);

		String json = ModelArmorTemplateSerDes.toJSON(modelArmorTemplate);

		Assert.assertFalse(json.contains(regex));

		modelArmorTemplate = ModelArmorTemplateSerDes.toDTO(json);

		Assert.assertEquals(regex, modelArmorTemplate.getDescription());
		Assert.assertEquals(
			regex, modelArmorTemplate.getExternalReferenceCode());
		Assert.assertEquals(regex, modelArmorTemplate.getLocation());
		Assert.assertEquals(regex, modelArmorTemplate.getTitle());
	}

	@Test
	public void testDeleteModelArmorTemplateByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ModelArmorTemplate modelArmorTemplate =
			testDeleteModelArmorTemplateByExternalReferenceCode_addModelArmorTemplate();

		assertHttpResponseStatusCode(
			204,
			modelArmorTemplateResource.
				deleteModelArmorTemplateByExternalReferenceCodeHttpResponse(
					modelArmorTemplate.getExternalReferenceCode()));
	}

	protected ModelArmorTemplate
			testDeleteModelArmorTemplateByExternalReferenceCode_addModelArmorTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostModelArmorTemplate() throws Exception {
		ModelArmorTemplate randomModelArmorTemplate =
			randomModelArmorTemplate();

		ModelArmorTemplate postModelArmorTemplate =
			testPostModelArmorTemplate_addModelArmorTemplate(
				randomModelArmorTemplate);

		assertEquals(randomModelArmorTemplate, postModelArmorTemplate);
		assertValid(postModelArmorTemplate);
	}

	protected ModelArmorTemplate
			testPostModelArmorTemplate_addModelArmorTemplate(
				ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutModelArmorTemplateByExternalReferenceCode()
		throws Exception {

		ModelArmorTemplate postModelArmorTemplate =
			testPutModelArmorTemplateByExternalReferenceCode_addModelArmorTemplate();

		ModelArmorTemplate randomModelArmorTemplate =
			randomModelArmorTemplate();

		ModelArmorTemplate putModelArmorTemplate =
			modelArmorTemplateResource.
				putModelArmorTemplateByExternalReferenceCode(
					postModelArmorTemplate.getExternalReferenceCode(),
					randomModelArmorTemplate);

		assertEquals(randomModelArmorTemplate, putModelArmorTemplate);
		assertValid(putModelArmorTemplate);

		ModelArmorTemplate getModelArmorTemplate =
			testPutModelArmorTemplateByExternalReferenceCode_getModelArmorTemplate(
				putModelArmorTemplate.getExternalReferenceCode());

		assertEquals(randomModelArmorTemplate, getModelArmorTemplate);
		assertValid(getModelArmorTemplate);

		ModelArmorTemplate newModelArmorTemplate =
			testPutModelArmorTemplateByExternalReferenceCode_createModelArmorTemplate();

		putModelArmorTemplate =
			modelArmorTemplateResource.
				putModelArmorTemplateByExternalReferenceCode(
					newModelArmorTemplate.getExternalReferenceCode(),
					newModelArmorTemplate);

		assertEquals(newModelArmorTemplate, putModelArmorTemplate);
		assertValid(putModelArmorTemplate);

		getModelArmorTemplate =
			testPutModelArmorTemplateByExternalReferenceCode_getModelArmorTemplate(
				putModelArmorTemplate.getExternalReferenceCode());

		assertEquals(newModelArmorTemplate, getModelArmorTemplate);

		Assert.assertEquals(
			newModelArmorTemplate.getExternalReferenceCode(),
			putModelArmorTemplate.getExternalReferenceCode());
	}

	protected ModelArmorTemplate
		testPutModelArmorTemplateByExternalReferenceCode_getModelArmorTemplate(
			String externalReferenceCode) {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ModelArmorTemplate
			testPutModelArmorTemplateByExternalReferenceCode_addModelArmorTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ModelArmorTemplate
			testPutModelArmorTemplateByExternalReferenceCode_createModelArmorTemplate()
		throws Exception {

		return randomModelArmorTemplate();
	}

	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		ModelArmorTemplate modelArmorTemplate1 =
			testBatchEngineDeleteImportTask_addModelArmorTemplate();

		testBatchEngineDeleteImportTask_deleteModelArmorTemplate(
			200, modelArmorTemplate1.getExternalReferenceCode());
	}

	protected ModelArmorTemplate
			testBatchEngineDeleteImportTask_addModelArmorTemplate()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void testBatchEngineDeleteImportTask_deleteModelArmorTemplate(
			int expectedStatusCode, String externalReferenceCode,
			String... parameters)
		throws Exception {

		ImportTaskResource importTaskResource = ImportTaskResource.builder(
		).authentication(
			_testCompanyAdminUser.getEmailAddress(),
			PropsValues.DEFAULT_ADMIN_PASSWORD
		).endpoint(
			testCompany.getVirtualHostname(),
			PortalUtil.getPortalServerPort(false), "http"
		).parameters(
			parameters
		).build();

		HttpResponse httpResponse =
			importTaskResource.deleteImportTaskHttpResponse(
				"com.liferay.ai.hub.rest.dto.v1_0.ModelArmorTemplate", null,
				null, null, null,
				JSONUtil.putAll(
					JSONUtil.put(
						"externalReferenceCode", () -> externalReferenceCode)));

		Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());

		if (expectedStatusCode == 200) {
			waitForFinish(
				"COMPLETED",
				JSONFactoryUtil.createJSONObject(httpResponse.getContent()));
		}
	}

	protected void assertContains(
		ModelArmorTemplate modelArmorTemplate,
		List<ModelArmorTemplate> modelArmorTemplates) {

		boolean contains = false;

		for (ModelArmorTemplate item : modelArmorTemplates) {
			if (equals(modelArmorTemplate, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			modelArmorTemplates + " does not contain " + modelArmorTemplate,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ModelArmorTemplate modelArmorTemplate1,
		ModelArmorTemplate modelArmorTemplate2) {

		Assert.assertTrue(
			modelArmorTemplate1 + " does not equal " + modelArmorTemplate2,
			equals(modelArmorTemplate1, modelArmorTemplate2));
	}

	protected void assertEquals(
		List<ModelArmorTemplate> modelArmorTemplates1,
		List<ModelArmorTemplate> modelArmorTemplates2) {

		Assert.assertEquals(
			modelArmorTemplates1.size(), modelArmorTemplates2.size());

		for (int i = 0; i < modelArmorTemplates1.size(); i++) {
			ModelArmorTemplate modelArmorTemplate1 = modelArmorTemplates1.get(
				i);
			ModelArmorTemplate modelArmorTemplate2 = modelArmorTemplates2.get(
				i);

			assertEquals(modelArmorTemplate1, modelArmorTemplate2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ModelArmorTemplate> modelArmorTemplates1,
		List<ModelArmorTemplate> modelArmorTemplates2) {

		Assert.assertEquals(
			modelArmorTemplates1.size(), modelArmorTemplates2.size());

		for (ModelArmorTemplate modelArmorTemplate1 : modelArmorTemplates1) {
			boolean contains = false;

			for (ModelArmorTemplate modelArmorTemplate2 :
					modelArmorTemplates2) {

				if (equals(modelArmorTemplate1, modelArmorTemplate2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				modelArmorTemplates2 + " does not contain " +
					modelArmorTemplate1,
				contains);
		}
	}

	protected void assertValid(ModelArmorTemplate modelArmorTemplate)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (modelArmorTemplate.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (modelArmorTemplate.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (modelArmorTemplate.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("guardrailType", additionalAssertFieldName)) {
				if (modelArmorTemplate.getGuardrailType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (modelArmorTemplate.getLocation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"maliciousUriFilterEnabled", additionalAssertFieldName)) {

				if (modelArmorTemplate.getMaliciousUriFilterEnabled() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"multilanguageDetectionEnabled",
					additionalAssertFieldName)) {

				if (modelArmorTemplate.getMultilanguageDetectionEnabled() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"piAndJailbreakConfidenceLevel",
					additionalAssertFieldName)) {

				if (modelArmorTemplate.getPiAndJailbreakConfidenceLevel() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"piAndJailbreakFilterEnabled", additionalAssertFieldName)) {

				if (modelArmorTemplate.getPiAndJailbreakFilterEnabled() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"raiDangerousLevel", additionalAssertFieldName)) {

				if (modelArmorTemplate.getRaiDangerousLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"raiHarassmentLevel", additionalAssertFieldName)) {

				if (modelArmorTemplate.getRaiHarassmentLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"raiHateSpeechLevel", additionalAssertFieldName)) {

				if (modelArmorTemplate.getRaiHateSpeechLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"raiSexuallyExplicitLevel", additionalAssertFieldName)) {

				if (modelArmorTemplate.getRaiSexuallyExplicitLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sdpFilterEnabled", additionalAssertFieldName)) {
				if (modelArmorTemplate.getSdpFilterEnabled() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (modelArmorTemplate.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (modelArmorTemplate.getTitle_i18n() == null) {
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

	protected void assertValid(Page<ModelArmorTemplate> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<ModelArmorTemplate> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<ModelArmorTemplate> modelArmorTemplates =
			page.getItems();

		int size = modelArmorTemplates.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.ai.hub.rest.dto.v1_0.ModelArmorTemplate.
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
		ModelArmorTemplate modelArmorTemplate1,
		ModelArmorTemplate modelArmorTemplate2) {

		if (modelArmorTemplate1 == modelArmorTemplate2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getActive(),
						modelArmorTemplate2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getDescription(),
						modelArmorTemplate2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getExternalReferenceCode(),
						modelArmorTemplate2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("guardrailType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getGuardrailType(),
						modelArmorTemplate2.getGuardrailType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("location", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getLocation(),
						modelArmorTemplate2.getLocation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"maliciousUriFilterEnabled", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getMaliciousUriFilterEnabled(),
						modelArmorTemplate2.getMaliciousUriFilterEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"multilanguageDetectionEnabled",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getMultilanguageDetectionEnabled(),
						modelArmorTemplate2.
							getMultilanguageDetectionEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"piAndJailbreakConfidenceLevel",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getPiAndJailbreakConfidenceLevel(),
						modelArmorTemplate2.
							getPiAndJailbreakConfidenceLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"piAndJailbreakFilterEnabled", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getPiAndJailbreakFilterEnabled(),
						modelArmorTemplate2.getPiAndJailbreakFilterEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"raiDangerousLevel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getRaiDangerousLevel(),
						modelArmorTemplate2.getRaiDangerousLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"raiHarassmentLevel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getRaiHarassmentLevel(),
						modelArmorTemplate2.getRaiHarassmentLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"raiHateSpeechLevel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getRaiHateSpeechLevel(),
						modelArmorTemplate2.getRaiHateSpeechLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"raiSexuallyExplicitLevel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						modelArmorTemplate1.getRaiSexuallyExplicitLevel(),
						modelArmorTemplate2.getRaiSexuallyExplicitLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sdpFilterEnabled", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getSdpFilterEnabled(),
						modelArmorTemplate2.getSdpFilterEnabled())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						modelArmorTemplate1.getTitle(),
						modelArmorTemplate2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title_i18n", additionalAssertFieldName)) {
				if (!equals(
						(Map)modelArmorTemplate1.getTitle_i18n(),
						(Map)modelArmorTemplate2.getTitle_i18n())) {

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

		if (!(_modelArmorTemplateResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_modelArmorTemplateResource;

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
		ModelArmorTemplate modelArmorTemplate) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			Object object = modelArmorTemplate.getDescription();

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
			Object object = modelArmorTemplate.getExternalReferenceCode();

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

		if (entityFieldName.equals("guardrailType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("location")) {
			Object object = modelArmorTemplate.getLocation();

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

		if (entityFieldName.equals("maliciousUriFilterEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("multilanguageDetectionEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("piAndJailbreakConfidenceLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("piAndJailbreakFilterEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("raiDangerousLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("raiHarassmentLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("raiHateSpeechLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("raiSexuallyExplicitLevel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sdpFilterEnabled")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			Object object = modelArmorTemplate.getTitle();

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

		if (entityFieldName.equals("title_i18n")) {
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

	protected ModelArmorTemplate randomModelArmorTemplate() throws Exception {
		return new ModelArmorTemplate() {
			{
				active = RandomTestUtil.randomBoolean();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				location = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				maliciousUriFilterEnabled = RandomTestUtil.randomBoolean();
				multilanguageDetectionEnabled = RandomTestUtil.randomBoolean();
				piAndJailbreakFilterEnabled = RandomTestUtil.randomBoolean();
				sdpFilterEnabled = RandomTestUtil.randomBoolean();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected ModelArmorTemplate randomIrrelevantModelArmorTemplate()
		throws Exception {

		ModelArmorTemplate randomIrrelevantModelArmorTemplate =
			randomModelArmorTemplate();

		return randomIrrelevantModelArmorTemplate;
	}

	protected ModelArmorTemplate randomPatchModelArmorTemplate()
		throws Exception {

		return randomModelArmorTemplate();
	}

	protected final JSONObject waitForFinish(
			String expectedExecuteStatus, JSONObject jsonObject)
		throws Exception {

		while (true) {
			ImportTask importTask = importTaskResource.getImportTask(
				jsonObject.getLong("id"));

			ImportTask.ExecuteStatus executeStatus =
				importTask.getExecuteStatus();

			if (StringUtil.equals(executeStatus.getValue(), "COMPLETED") ||
				StringUtil.equals(executeStatus.getValue(), "FAILED")) {

				Assert.assertEquals(
					expectedExecuteStatus, executeStatus.getValue());

				return jsonObject;
			}
		}
	}

	protected ModelArmorTemplateResource modelArmorTemplateResource;
	protected ImportTaskResource importTaskResource;
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
		LogFactoryUtil.getLog(BaseModelArmorTemplateResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private com.liferay.ai.hub.rest.resource.v1_0.ModelArmorTemplateResource
		_modelArmorTemplateResource;

}
// LIFERAY-REST-BUILDER-HASH:1091802144