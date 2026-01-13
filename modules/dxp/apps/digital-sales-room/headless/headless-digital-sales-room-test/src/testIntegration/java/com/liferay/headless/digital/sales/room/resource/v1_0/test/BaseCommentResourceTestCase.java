/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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

import com.liferay.headless.digital.sales.room.client.dto.v1_0.Comment;
import com.liferay.headless.digital.sales.room.client.http.HttpInvoker;
import com.liferay.headless.digital.sales.room.client.pagination.Page;
import com.liferay.headless.digital.sales.room.client.pagination.Pagination;
import com.liferay.headless.digital.sales.room.client.resource.v1_0.CommentResource;
import com.liferay.headless.digital.sales.room.client.serdes.v1_0.CommentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
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
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public abstract class BaseCommentResourceTestCase {

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

		_commentResource.setContextCompany(testCompany);

		_testCompanyAdminUser = UserTestUtil.getAdminUser(
			testCompany.getCompanyId());

		commentResource = CommentResource.builder(
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

		Comment comment1 = randomComment();

		String json = objectMapper.writeValueAsString(comment1);

		Comment comment2 = CommentSerDes.toDTO(json);

		Assert.assertTrue(equals(comment1, comment2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = getClientSerDesObjectMapper();

		Comment comment = randomComment();

		String json1 = objectMapper.writeValueAsString(comment);
		String json2 = CommentSerDes.toJSON(comment);

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

		Comment comment = randomComment();

		comment.setCategory(regex);
		comment.setExternalReferenceCode(regex);
		comment.setText(regex);

		String json = CommentSerDes.toJSON(comment);

		Assert.assertFalse(json.contains(regex));

		comment = CommentSerDes.toDTO(json);

		Assert.assertEquals(regex, comment.getCategory());
		Assert.assertEquals(regex, comment.getExternalReferenceCode());
		Assert.assertEquals(regex, comment.getText());
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPage() throws Exception {
		Long digitalSalesRoomId =
			testGetDigitalSalesRoomCommentsPage_getDigitalSalesRoomId();
		Long irrelevantDigitalSalesRoomId =
			testGetDigitalSalesRoomCommentsPage_getIrrelevantDigitalSalesRoomId();

		Page<Comment> page = commentResource.getDigitalSalesRoomCommentsPage(
			digitalSalesRoomId, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		if (irrelevantDigitalSalesRoomId != null) {
			Comment irrelevantComment =
				testGetDigitalSalesRoomCommentsPage_addComment(
					irrelevantDigitalSalesRoomId, randomIrrelevantComment());

			page = commentResource.getDigitalSalesRoomCommentsPage(
				irrelevantDigitalSalesRoomId, null,
				Pagination.of(1, (int)totalCount + 1), null);

			Assert.assertEquals(totalCount + 1, page.getTotalCount());

			assertContains(irrelevantComment, (List<Comment>)page.getItems());
			assertValid(
				page,
				testGetDigitalSalesRoomCommentsPage_getExpectedActions(
					irrelevantDigitalSalesRoomId));
		}

		Comment comment1 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, randomComment());

		Comment comment2 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, randomComment());

		page = commentResource.getDigitalSalesRoomCommentsPage(
			digitalSalesRoomId, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(comment1, (List<Comment>)page.getItems());
		assertContains(comment2, (List<Comment>)page.getItems());
		assertValid(
			page,
			testGetDigitalSalesRoomCommentsPage_getExpectedActions(
				digitalSalesRoomId));
	}

	protected Map<String, Map<String, String>>
			testGetDigitalSalesRoomCommentsPage_getExpectedActions(
				Long digitalSalesRoomId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPageWithPagination()
		throws Exception {

		Long digitalSalesRoomId =
			testGetDigitalSalesRoomCommentsPage_getDigitalSalesRoomId();

		Page<Comment> commentsPage =
			commentResource.getDigitalSalesRoomCommentsPage(
				digitalSalesRoomId, null, null, null);

		int totalCount = GetterUtil.getInteger(commentsPage.getTotalCount());

		Comment comment1 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, randomComment());

		Comment comment2 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, randomComment());

		Comment comment3 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, randomComment());

		// See com.liferay.portal.vulcan.internal.configuration.HeadlessAPICompanyConfiguration#pageSizeLimit

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<Comment> page1 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(comment1, (List<Comment>)page1.getItems());

			Page<Comment> page2 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(comment2, (List<Comment>)page2.getItems());

			Page<Comment> page3 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(comment3, (List<Comment>)page3.getItems());
		}
		else {
			Page<Comment> page1 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null, Pagination.of(1, totalCount + 2),
					null);

			List<Comment> comments1 = (List<Comment>)page1.getItems();

			Assert.assertEquals(
				comments1.toString(), totalCount + 2, comments1.size());

			Page<Comment> page2 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null, Pagination.of(2, totalCount + 2),
					null);

			Assert.assertEquals(totalCount + 3, page2.getTotalCount());

			List<Comment> comments2 = (List<Comment>)page2.getItems();

			Assert.assertEquals(comments2.toString(), 1, comments2.size());

			Page<Comment> page3 =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(1, (int)totalCount + 3), null);

			assertContains(comment1, (List<Comment>)page3.getItems());
			assertContains(comment2, (List<Comment>)page3.getItems());
			assertContains(comment3, (List<Comment>)page3.getItems());
		}
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPageWithSortDateTime()
		throws Exception {

		testGetDigitalSalesRoomCommentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, comment1, comment2) -> {
				BeanTestUtil.setProperty(
					comment1, entityField.getName(),
					new Date(System.currentTimeMillis() - (2 * Time.MINUTE)));
			});
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPageWithSortDouble()
		throws Exception {

		testGetDigitalSalesRoomCommentsPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, comment1, comment2) -> {
				BeanTestUtil.setProperty(comment1, entityField.getName(), 0.1);
				BeanTestUtil.setProperty(comment2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPageWithSortInteger()
		throws Exception {

		testGetDigitalSalesRoomCommentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, comment1, comment2) -> {
				BeanTestUtil.setProperty(comment1, entityField.getName(), 0);
				BeanTestUtil.setProperty(comment2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDigitalSalesRoomCommentsPageWithSortString()
		throws Exception {

		testGetDigitalSalesRoomCommentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, comment1, comment2) -> {
				Class<?> clazz = comment1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanTestUtil.setProperty(
						comment1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanTestUtil.setProperty(
						comment2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanTestUtil.setProperty(
						comment1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanTestUtil.setProperty(
						comment2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanTestUtil.setProperty(
						comment1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanTestUtil.setProperty(
						comment2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDigitalSalesRoomCommentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Comment, Comment, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long digitalSalesRoomId =
			testGetDigitalSalesRoomCommentsPage_getDigitalSalesRoomId();

		Comment comment1 = randomComment();
		Comment comment2 = randomComment();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, comment1, comment2);
		}

		comment1 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, comment1);

		comment2 = testGetDigitalSalesRoomCommentsPage_addComment(
			digitalSalesRoomId, comment2);

		Page<Comment> page = commentResource.getDigitalSalesRoomCommentsPage(
			digitalSalesRoomId, null, null, null);

		for (EntityField entityField : entityFields) {
			Page<Comment> ascPage =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":asc");

			assertContains(comment1, (List<Comment>)ascPage.getItems());
			assertContains(comment2, (List<Comment>)ascPage.getItems());

			Page<Comment> descPage =
				commentResource.getDigitalSalesRoomCommentsPage(
					digitalSalesRoomId, null,
					Pagination.of(1, (int)page.getTotalCount() + 1),
					entityField.getName() + ":desc");

			assertContains(comment2, (List<Comment>)descPage.getItems());
			assertContains(comment1, (List<Comment>)descPage.getItems());
		}
	}

	protected Comment testGetDigitalSalesRoomCommentsPage_addComment(
			Long digitalSalesRoomId, Comment comment)
		throws Exception {

		return commentResource.postDigitalSalesRoomComment(
			digitalSalesRoomId, comment);
	}

	protected Long testGetDigitalSalesRoomCommentsPage_getDigitalSalesRoomId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDigitalSalesRoomCommentsPage_getIrrelevantDigitalSalesRoomId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDigitalSalesRoomComment() throws Exception {
		Comment randomComment = randomComment();

		Comment postComment = testPostDigitalSalesRoomComment_addComment(
			randomComment);

		assertEquals(randomComment, postComment);
		assertValid(postComment);
	}

	protected Comment testPostDigitalSalesRoomComment_addComment(
			Comment comment)
		throws Exception {

		return commentResource.postDigitalSalesRoomComment(
			testGetDigitalSalesRoomCommentsPage_getDigitalSalesRoomId(),
			comment);
	}

	protected void assertContains(Comment comment, List<Comment> comments) {
		boolean contains = false;

		for (Comment item : comments) {
			if (equals(comment, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(comments + " does not contain " + comment, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Comment comment1, Comment comment2) {
		Assert.assertTrue(
			comment1 + " does not equal " + comment2,
			equals(comment1, comment2));
	}

	protected void assertEquals(
		List<Comment> comments1, List<Comment> comments2) {

		Assert.assertEquals(comments1.size(), comments2.size());

		for (int i = 0; i < comments1.size(); i++) {
			Comment comment1 = comments1.get(i);
			Comment comment2 = comments2.get(i);

			assertEquals(comment1, comment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Comment> comments1, List<Comment> comments2) {

		Assert.assertEquals(comments1.size(), comments2.size());

		for (Comment comment1 : comments1) {
			boolean contains = false;

			for (Comment comment2 : comments2) {
				if (equals(comment1, comment2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				comments2 + " does not contain " + comment1, contains);
		}
	}

	protected void assertValid(Comment comment) throws Exception {
		boolean valid = true;

		if (comment.getDateCreated() == null) {
			valid = false;
		}

		if (comment.getDateModified() == null) {
			valid = false;
		}

		if (comment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (comment.getCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (comment.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (comment.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (comment.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentCommentId", additionalAssertFieldName)) {
				if (comment.getParentCommentId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("text", additionalAssertFieldName)) {
				if (comment.getText() == null) {
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

	protected void assertValid(Page<Comment> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<Comment> page, Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<Comment> comments = page.getItems();

		int size = comments.size();

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
					com.liferay.headless.digital.sales.room.dto.v1_0.Comment.
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

	protected boolean equals(Comment comment1, Comment comment2) {
		if (comment1 == comment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getCategory(), comment2.getCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getCreator(), comment2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getDateCreated(), comment2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getDateModified(),
						comment2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						comment1.getExternalReferenceCode(),
						comment2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(comment1.getId(), comment2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getNumberOfComments(),
						comment2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentCommentId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getParentCommentId(),
						comment2.getParentCommentId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("text", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						comment1.getText(), comment2.getText())) {

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

		if (!(_commentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_commentResource;

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
		EntityField entityField, String operator, Comment comment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("category")) {
			Object object = comment.getCategory();

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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				Date date = comment.getDateCreated();

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

				sb.append(_format.format(comment.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				Date date = comment.getDateModified();

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

				sb.append(_format.format(comment.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = comment.getExternalReferenceCode();

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

		if (entityFieldName.equals("numberOfComments")) {
			sb.append(String.valueOf(comment.getNumberOfComments()));

			return sb.toString();
		}

		if (entityFieldName.equals("parentCommentId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("text")) {
			Object object = comment.getText();

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

	protected Comment randomComment() throws Exception {
		return new Comment() {
			{
				category = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				numberOfComments = RandomTestUtil.randomInt();
				parentCommentId = RandomTestUtil.randomLong();
				text = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Comment randomIrrelevantComment() throws Exception {
		Comment randomIrrelevantComment = randomComment();

		return randomIrrelevantComment;
	}

	protected Comment randomPatchComment() throws Exception {
		return randomComment();
	}

	protected CommentResource commentResource;
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
		LogFactoryUtil.getLog(BaseCommentResourceTestCase.class);

	private static Format _format;

	private com.liferay.portal.kernel.model.User _testCompanyAdminUser;

	@Inject
	private
		com.liferay.headless.digital.sales.room.resource.v1_0.CommentResource
			_commentResource;

}