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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class StructuredContentResourceTest
	extends BaseStructuredContentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		_serviceReference = registry.getServiceReference(
			DDMFormDeserializerTracker.class);

		_ddmFormDeserializerTracker = registry.getService(_serviceReference);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), testGroup);

		_ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read("test-structured-content-structure.json")),
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			testGroup.getGroupId(), _ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.xsl"), LocaleUtil.US);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_ddmFormDeserializerTracker = null;

		registry.ungetService(_serviceReference);

		super.tearDown();
	}

	@Override
	@Test
	public void testDeleteStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			invokePostContentSpaceStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		assertResponseCode(
			200,
			invokeDeleteStructuredContentResponse(
				postStructuredContent.getId()));

		assertResponseCode(
			404,
			invokeGetStructuredContentResponse(postStructuredContent.getId()));

	}

	@Override
	@Test
	public void testGetStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			invokePostContentSpaceStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			postStructuredContent.getId());

		assertEquals(postStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	@Override
	@Test
	public void testPostContentSpaceStructuredContent() throws Exception {
		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent postStructuredContent =
			invokePostContentSpaceStructuredContent(
				testGroup.getGroupId(), randomStructuredContent);

		assertEquals(randomStructuredContent, postStructuredContent);
		assertValid(postStructuredContent);
	}

	@Override
	@Test
	public void testPutStructuredContent() throws Exception {
		StructuredContent postStructuredContent =
			invokePostContentSpaceStructuredContent(
				testGroup.getGroupId(), randomStructuredContent());

		StructuredContent randomStructuredContent = randomStructuredContent();

		StructuredContent putStructuredContent = invokePutStructuredContent(
			postStructuredContent.getId(), randomStructuredContent);

		assertEquals(randomStructuredContent, putStructuredContent);
		assertValid(putStructuredContent);

		StructuredContent getStructuredContent = invokeGetStructuredContent(
			putStructuredContent.getId());

		assertEquals(randomStructuredContent, getStructuredContent);
		assertValid(getStructuredContent);
	}

	@Override
	protected void assertEquals(
		StructuredContent structuredContent1,
		StructuredContent structuredContent2) {

		Assert.assertEquals(
			structuredContent1.getContentSpace(),
			structuredContent2.getContentSpace());

		Assert.assertEquals(
			structuredContent1.getContentStructureId(),
			structuredContent2.getContentStructureId());

		Assert.assertEquals(
			structuredContent1.getDescription(),
			structuredContent2.getDescription());

		Assert.assertEquals(
			structuredContent1.getTitle(), structuredContent2.getTitle());
	}

	protected void assertValid(StructuredContent structuredContent) {
		Assert.assertEquals(
			structuredContent.getContentSpace(),
			Long.valueOf(testGroup.getGroupId()));
		Assert.assertNotNull(structuredContent.getDateCreated());
		Assert.assertNotNull(structuredContent.getId());
	}

	@Override
	protected StructuredContent randomStructuredContent() {
		return new StructuredContent() {
			{
				contentSpace = testGroup.getGroupId();
				contentStructureId = _ddmStructure.getStructureId();
				datePublished = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				lastReviewed = RandomTestUtil.nextDate();
				title = RandomTestUtil.randomString();
			}
		};
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializer ddmFormDeserializer =
			_ddmFormDeserializerTracker.getDDMFormDeserializer("json");

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/com/liferay/headless/web/experience/resource/v1_0/test/" +
				fileName);

		return StringUtil.read(inputStream);
	}

	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;
	private DDMStructure _ddmStructure;
	private ServiceReference<DDMFormDeserializerTracker> _serviceReference;

}