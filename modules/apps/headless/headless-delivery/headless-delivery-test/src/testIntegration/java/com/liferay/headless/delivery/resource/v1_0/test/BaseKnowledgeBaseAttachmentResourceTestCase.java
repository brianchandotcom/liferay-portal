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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.KnowledgeBaseAttachmentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseAttachmentSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BaseKnowledgeBaseAttachmentResourceTestCase {

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
		testLocale = LocaleUtil.getDefault();
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
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		KnowledgeBaseAttachment knowledgeBaseAttachment1 =
			randomKnowledgeBaseAttachment();

		String json = objectMapper.writeValueAsString(knowledgeBaseAttachment1);

		KnowledgeBaseAttachment knowledgeBaseAttachment2 =
			KnowledgeBaseAttachmentSerDes.toDTO(json);

		Assert.assertTrue(
			equals(knowledgeBaseAttachment1, knowledgeBaseAttachment2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		KnowledgeBaseAttachment knowledgeBaseAttachment =
			randomKnowledgeBaseAttachment();

		String json1 = objectMapper.writeValueAsString(knowledgeBaseAttachment);
		String json2 = KnowledgeBaseAttachmentSerDes.toJSON(
			knowledgeBaseAttachment);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage()
		throws Exception {

		Long knowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getKnowledgeBaseArticleId();
		Long irrelevantKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getIrrelevantKnowledgeBaseArticleId();

		if ((irrelevantKnowledgeBaseArticleId != null)) {
			KnowledgeBaseAttachment irrelevantKnowledgeBaseAttachment =
				testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_addKnowledgeBaseAttachment(
					irrelevantKnowledgeBaseArticleId,
					randomIrrelevantKnowledgeBaseAttachment());

			Page<KnowledgeBaseAttachment> page =
				KnowledgeBaseAttachmentResource.
					getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
						irrelevantKnowledgeBaseArticleId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseAttachment),
				(List<KnowledgeBaseAttachment>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseAttachment knowledgeBaseAttachment1 =
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_addKnowledgeBaseAttachment(
				knowledgeBaseArticleId, randomKnowledgeBaseAttachment());

		KnowledgeBaseAttachment knowledgeBaseAttachment2 =
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_addKnowledgeBaseAttachment(
				knowledgeBaseArticleId, randomKnowledgeBaseAttachment());

		Page<KnowledgeBaseAttachment> page =
			KnowledgeBaseAttachmentResource.
				getKnowledgeBaseArticleKnowledgeBaseAttachmentsPage(
					knowledgeBaseArticleId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseAttachment1, knowledgeBaseAttachment2),
			(List<KnowledgeBaseAttachment>)page.getItems());
		assertValid(page);
	}

	protected KnowledgeBaseAttachment
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_addKnowledgeBaseAttachment(
				Long knowledgeBaseArticleId,
				KnowledgeBaseAttachment knowledgeBaseAttachment)
		throws Exception {

		return KnowledgeBaseAttachmentResource.
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				knowledgeBaseArticleId, knowledgeBaseAttachment,
				getMultipartFiles());
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getKnowledgeBaseArticleId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getIrrelevantKnowledgeBaseArticleId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostKnowledgeBaseArticleKnowledgeBaseAttachment()
		throws Exception {

		Assert.assertTrue(true);
	}

	protected KnowledgeBaseAttachment
			testPostKnowledgeBaseArticleKnowledgeBaseAttachment_addKnowledgeBaseAttachment(
				KnowledgeBaseAttachment knowledgeBaseAttachment)
		throws Exception {

		return KnowledgeBaseAttachmentResource.
			postKnowledgeBaseArticleKnowledgeBaseAttachment(
				testGetKnowledgeBaseArticleKnowledgeBaseAttachmentsPage_getKnowledgeBaseArticleId(),
				knowledgeBaseAttachment, getMultipartFiles());
	}

	@Test
	public void testDeleteKnowledgeBaseAttachment() throws Exception {
		KnowledgeBaseAttachment knowledgeBaseAttachment =
			testDeleteKnowledgeBaseAttachment_addKnowledgeBaseAttachment();

		assertHttpResponseStatusCode(
			204,
			KnowledgeBaseAttachmentResource.
				deleteKnowledgeBaseAttachmentHttpResponse(
					knowledgeBaseAttachment.getId()));

		assertHttpResponseStatusCode(
			404,
			KnowledgeBaseAttachmentResource.
				getKnowledgeBaseAttachmentHttpResponse(
					knowledgeBaseAttachment.getId()));

		assertHttpResponseStatusCode(
			404,
			KnowledgeBaseAttachmentResource.
				getKnowledgeBaseAttachmentHttpResponse(0L));
	}

	protected KnowledgeBaseAttachment
			testDeleteKnowledgeBaseAttachment_addKnowledgeBaseAttachment()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetKnowledgeBaseAttachment() throws Exception {
		KnowledgeBaseAttachment postKnowledgeBaseAttachment =
			testGetKnowledgeBaseAttachment_addKnowledgeBaseAttachment();

		KnowledgeBaseAttachment getKnowledgeBaseAttachment =
			KnowledgeBaseAttachmentResource.getKnowledgeBaseAttachment(
				postKnowledgeBaseAttachment.getId());

		assertEquals(postKnowledgeBaseAttachment, getKnowledgeBaseAttachment);
		assertValid(getKnowledgeBaseAttachment);
	}

	protected KnowledgeBaseAttachment
			testGetKnowledgeBaseAttachment_addKnowledgeBaseAttachment()
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
		KnowledgeBaseAttachment knowledgeBaseAttachment1,
		KnowledgeBaseAttachment knowledgeBaseAttachment2) {

		Assert.assertTrue(
			knowledgeBaseAttachment1 + " does not equal " +
				knowledgeBaseAttachment2,
			equals(knowledgeBaseAttachment1, knowledgeBaseAttachment2));
	}

	protected void assertEquals(
		List<KnowledgeBaseAttachment> knowledgeBaseAttachments1,
		List<KnowledgeBaseAttachment> knowledgeBaseAttachments2) {

		Assert.assertEquals(
			knowledgeBaseAttachments1.size(), knowledgeBaseAttachments2.size());

		for (int i = 0; i < knowledgeBaseAttachments1.size(); i++) {
			KnowledgeBaseAttachment knowledgeBaseAttachment1 =
				knowledgeBaseAttachments1.get(i);
			KnowledgeBaseAttachment knowledgeBaseAttachment2 =
				knowledgeBaseAttachments2.get(i);

			assertEquals(knowledgeBaseAttachment1, knowledgeBaseAttachment2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<KnowledgeBaseAttachment> knowledgeBaseAttachments1,
		List<KnowledgeBaseAttachment> knowledgeBaseAttachments2) {

		Assert.assertEquals(
			knowledgeBaseAttachments1.size(), knowledgeBaseAttachments2.size());

		for (KnowledgeBaseAttachment knowledgeBaseAttachment1 :
				knowledgeBaseAttachments1) {

			boolean contains = false;

			for (KnowledgeBaseAttachment knowledgeBaseAttachment2 :
					knowledgeBaseAttachments2) {

				if (equals(
						knowledgeBaseAttachment1, knowledgeBaseAttachment2)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				knowledgeBaseAttachments2 + " does not contain " +
					knowledgeBaseAttachment1,
				contains);
		}
	}

	protected void assertValid(
		KnowledgeBaseAttachment knowledgeBaseAttachment) {

		boolean valid = true;

		if (knowledgeBaseAttachment.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (knowledgeBaseAttachment.getContentUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (knowledgeBaseAttachment.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (knowledgeBaseAttachment.getFileExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (knowledgeBaseAttachment.getSizeInBytes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (knowledgeBaseAttachment.getTitle() == null) {
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

	protected void assertValid(Page<KnowledgeBaseAttachment> page) {
		boolean valid = false;

		Collection<KnowledgeBaseAttachment> knowledgeBaseAttachments =
			page.getItems();

		int size = knowledgeBaseAttachments.size();

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

	protected boolean equals(
		KnowledgeBaseAttachment knowledgeBaseAttachment1,
		KnowledgeBaseAttachment knowledgeBaseAttachment2) {

		if (knowledgeBaseAttachment1 == knowledgeBaseAttachment2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getContentUrl(),
						knowledgeBaseAttachment2.getContentUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getEncodingFormat(),
						knowledgeBaseAttachment2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getFileExtension(),
						knowledgeBaseAttachment2.getFileExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getId(),
						knowledgeBaseAttachment2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getSizeInBytes(),
						knowledgeBaseAttachment2.getSizeInBytes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseAttachment1.getTitle(),
						knowledgeBaseAttachment2.getTitle())) {

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

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_knowledgeBaseAttachmentResource instanceof
				EntityModelResource)) {

			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_knowledgeBaseAttachmentResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator,
		KnowledgeBaseAttachment knowledgeBaseAttachment) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseAttachment.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(
				String.valueOf(knowledgeBaseAttachment.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(
				String.valueOf(knowledgeBaseAttachment.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseAttachment.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Map<String, File> getMultipartFiles() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected KnowledgeBaseAttachment randomKnowledgeBaseAttachment()
		throws Exception {

		return new KnowledgeBaseAttachment() {
			{
				contentUrl = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				sizeInBytes = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected KnowledgeBaseAttachment randomIrrelevantKnowledgeBaseAttachment()
		throws Exception {

		KnowledgeBaseAttachment randomIrrelevantKnowledgeBaseAttachment =
			randomKnowledgeBaseAttachment();

		return randomIrrelevantKnowledgeBaseAttachment;
	}

	protected KnowledgeBaseAttachment randomPatchKnowledgeBaseAttachment()
		throws Exception {

		return randomKnowledgeBaseAttachment();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKnowledgeBaseAttachmentResourceTestCase.class);

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
	private
		com.liferay.headless.delivery.resource.v1_0.
			KnowledgeBaseAttachmentResource _knowledgeBaseAttachmentResource;

}