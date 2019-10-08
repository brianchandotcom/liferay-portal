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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.Subscription;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.SubscriptionResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.SubscriptionSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;
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
public abstract class BaseSubscriptionResourceTestCase {

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

		_subscriptionResource.setContextCompany(testCompany);

		SubscriptionResource.Builder builder = SubscriptionResource.builder();

		subscriptionResource = builder.locale(
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

		Subscription subscription1 = randomSubscription();

		String json = objectMapper.writeValueAsString(subscription1);

		Subscription subscription2 = SubscriptionSerDes.toDTO(json);

		Assert.assertTrue(equals(subscription1, subscription2));
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

		Subscription subscription = randomSubscription();

		String json1 = objectMapper.writeValueAsString(subscription);
		String json2 = SubscriptionSerDes.toJSON(subscription);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Subscription subscription = randomSubscription();

		subscription.setContentType(regex);
		subscription.setFrequency(regex);

		String json = SubscriptionSerDes.toJSON(subscription);

		Assert.assertFalse(json.contains(regex));

		subscription = SubscriptionSerDes.toDTO(json);

		Assert.assertEquals(regex, subscription.getContentType());
		Assert.assertEquals(regex, subscription.getFrequency());
	}

	@Test
	public void testGetMyUserAccountSubscriptionsPage() throws Exception {
		Page<Subscription> page =
			subscriptionResource.getMyUserAccountSubscriptionsPage(
				RandomTestUtil.randomString(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Subscription subscription1 =
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				randomSubscription());

		Subscription subscription2 =
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				randomSubscription());

		page = subscriptionResource.getMyUserAccountSubscriptionsPage(
			null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(subscription1, subscription2),
			(List<Subscription>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetMyUserAccountSubscriptionsPageWithPagination()
		throws Exception {

		Subscription subscription1 =
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				randomSubscription());

		Subscription subscription2 =
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				randomSubscription());

		Subscription subscription3 =
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				randomSubscription());

		Page<Subscription> page1 =
			subscriptionResource.getMyUserAccountSubscriptionsPage(
				null, Pagination.of(1, 2));

		List<Subscription> subscriptions1 =
			(List<Subscription>)page1.getItems();

		Assert.assertEquals(
			subscriptions1.toString(), 2, subscriptions1.size());

		Page<Subscription> page2 =
			subscriptionResource.getMyUserAccountSubscriptionsPage(
				null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Subscription> subscriptions2 =
			(List<Subscription>)page2.getItems();

		Assert.assertEquals(
			subscriptions2.toString(), 1, subscriptions2.size());

		Page<Subscription> page3 =
			subscriptionResource.getMyUserAccountSubscriptionsPage(
				null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(subscription1, subscription2, subscription3),
			(List<Subscription>)page3.getItems());
	}

	protected Subscription
			testGetMyUserAccountSubscriptionsPage_addSubscription(
				Subscription subscription)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteMyUserAccountSubscription() throws Exception {
		Subscription subscription =
			testDeleteMyUserAccountSubscription_addSubscription();

		assertHttpResponseStatusCode(
			204,
			subscriptionResource.deleteMyUserAccountSubscriptionHttpResponse(
				subscription.getId()));

		assertHttpResponseStatusCode(
			404,
			subscriptionResource.getMyUserAccountSubscriptionHttpResponse(
				subscription.getId()));

		assertHttpResponseStatusCode(
			404,
			subscriptionResource.getMyUserAccountSubscriptionHttpResponse(0L));
	}

	protected Subscription testDeleteMyUserAccountSubscription_addSubscription()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetMyUserAccountSubscription() throws Exception {
		Subscription postSubscription =
			testGetMyUserAccountSubscription_addSubscription();

		Subscription getSubscription =
			subscriptionResource.getMyUserAccountSubscription(
				postSubscription.getId());

		assertEquals(postSubscription, getSubscription);
		assertValid(getSubscription);
	}

	protected Subscription testGetMyUserAccountSubscription_addSubscription()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetMyUserAccountSubscription() throws Exception {
		Subscription subscription = testGraphQLSubscription_addSubscription();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"myUserAccountSubscription",
				new HashMap<String, Object>() {
					{
						put("subscriptionId", subscription.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				subscription,
				dataJSONObject.getJSONObject("myUserAccountSubscription")));
	}

	protected Subscription testGraphQLSubscription_addSubscription()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		Subscription subscription1, Subscription subscription2) {

		Assert.assertTrue(
			subscription1 + " does not equal " + subscription2,
			equals(subscription1, subscription2));
	}

	protected void assertEquals(
		List<Subscription> subscriptions1, List<Subscription> subscriptions2) {

		Assert.assertEquals(subscriptions1.size(), subscriptions2.size());

		for (int i = 0; i < subscriptions1.size(); i++) {
			Subscription subscription1 = subscriptions1.get(i);
			Subscription subscription2 = subscriptions2.get(i);

			assertEquals(subscription1, subscription2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Subscription> subscriptions1, List<Subscription> subscriptions2) {

		Assert.assertEquals(subscriptions1.size(), subscriptions2.size());

		for (Subscription subscription1 : subscriptions1) {
			boolean contains = false;

			for (Subscription subscription2 : subscriptions2) {
				if (equals(subscription1, subscription2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				subscriptions2 + " does not contain " + subscription1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Subscription> subscriptions, JSONArray jsonArray) {

		for (Subscription subscription : subscriptions) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(subscription, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + subscription, contains);
		}
	}

	protected void assertValid(Subscription subscription) {
		boolean valid = true;

		if (subscription.getDateCreated() == null) {
			valid = false;
		}

		if (subscription.getDateModified() == null) {
			valid = false;
		}

		if (subscription.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(subscription.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentId", additionalAssertFieldName)) {
				if (subscription.getContentId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (subscription.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("frequency", additionalAssertFieldName)) {
				if (subscription.getFrequency() == null) {
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

	protected void assertValid(Page<Subscription> page) {
		boolean valid = false;

		java.util.Collection<Subscription> subscriptions = page.getItems();

		int size = subscriptions.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		Subscription subscription1, Subscription subscription2) {

		if (subscription1 == subscription2) {
			return true;
		}

		if (!Objects.equals(
				subscription1.getSiteId(), subscription2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getContentId(),
						subscription2.getContentId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getContentType(),
						subscription2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getDateCreated(),
						subscription2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getDateModified(),
						subscription2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("frequency", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getFrequency(),
						subscription2.getFrequency())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						subscription1.getId(), subscription2.getId())) {

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

	protected boolean equalsJSONObject(
		Subscription subscription, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentType", fieldName)) {
				if (!Objects.deepEquals(
						subscription.getContentType(),
						jsonObject.getString("contentType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("frequency", fieldName)) {
				if (!Objects.deepEquals(
						subscription.getFrequency(),
						jsonObject.getString("frequency"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						subscription.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_subscriptionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_subscriptionResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Subscription subscription) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(subscription.getContentType()));
			sb.append("'");

			return sb.toString();
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
							subscription.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							subscription.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(subscription.getDateCreated()));
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
							subscription.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							subscription.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(subscription.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("frequency")) {
			sb.append("'");
			sb.append(String.valueOf(subscription.getFrequency()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
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

	protected Subscription randomSubscription() throws Exception {
		return new Subscription() {
			{
				contentType = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				frequency = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected Subscription randomIrrelevantSubscription() throws Exception {
		Subscription randomIrrelevantSubscription = randomSubscription();

		randomIrrelevantSubscription.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantSubscription;
	}

	protected Subscription randomPatchSubscription() throws Exception {
		return randomSubscription();
	}

	protected SubscriptionResource subscriptionResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (_graphQLFields.length > 0) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSubscriptionResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.admin.user.resource.v1_0.SubscriptionResource
		_subscriptionResource;

}