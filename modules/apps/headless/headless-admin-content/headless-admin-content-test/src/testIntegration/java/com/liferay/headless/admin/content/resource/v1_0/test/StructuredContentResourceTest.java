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

package com.liferay.headless.admin.content.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.headless.admin.content.client.dto.v1_0.StructuredContent;
import com.liferay.headless.admin.content.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class StructuredContentResourceTest
	extends BaseStructuredContentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		_structuredContentResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();

		_ddmStructure = _addDDMStructure(testGroup, "test-ddm-structure.json");
		_irrelevantDDMStructure = _addDDMStructure(
			irrelevantGroup, "test-ddm-structure.json");

		_addDDMTemplate(_ddmStructure);
	}

	@Override
	@Test
	public void testGetStructuredContentsVersionsPage() throws Exception {
		StructuredContent structuredContent = _postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());

		Long id = structuredContent.getId();

		Page<StructuredContent> structuredContentsVersionsPage =
			structuredContentResource.getStructuredContentsVersionsPage(id);

		Assert.assertEquals(1L, structuredContentsVersionsPage.getTotalCount());

		_structuredContentResource.putStructuredContent(
			id, _toStructuredContent(structuredContent));

		structuredContentsVersionsPage =
			structuredContentResource.getStructuredContentsVersionsPage(id);

		Assert.assertEquals(2L, structuredContentsVersionsPage.getTotalCount());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"contentStructureId", "creatorId", "dateCreated", "dateModified",
			"datePublished", "friendlyUrlPath"
		};
	}

	@Override
	protected StructuredContent randomIrrelevantStructuredContent()
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentStructureId(
			_irrelevantDDMStructure.getStructureId());
		structuredContent.setSiteId(irrelevantGroup.getGroupId());

		return structuredContent;
	}

	@Override
	protected StructuredContent randomStructuredContent() throws Exception {
		StructuredContent structuredContent = super.randomStructuredContent();

		structuredContent.setContentStructureId(_ddmStructure.getStructureId());

		return structuredContent;
	}

	@Override
	protected StructuredContent
			testGetSiteStructuredContentsPage_addStructuredContent(
				Long siteId, StructuredContent structuredContent)
		throws Exception {

		return _postSiteStructuredContent(siteId, structuredContent);
	}

	@Override
	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return _postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	private DDMStructure _addDDMStructure(Group group, String fileName)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(_read(fileName)), StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private DDMTemplate _addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			ddmStructure.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			_read("test-structured-content-template.xsl"), LocaleUtil.US);
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private StructuredContent _postSiteStructuredContent(
			Long siteId, StructuredContent structuredContent)
		throws Exception {

		return _toStructuredContent(
			_structuredContentResource.postSiteStructuredContent(
				siteId,
				new com.liferay.headless.delivery.client.dto.v1_0.
					StructuredContent() {

					{
						setTitle(structuredContent.getTitle());
						setSiteId(structuredContent.getSiteId());
						setContentStructureId(
							structuredContent.getContentStructureId());
					}
				}));
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private StructuredContent _toStructuredContent(
		com.liferay.headless.delivery.client.dto.v1_0.StructuredContent
			structuredContent) {

		return new StructuredContent() {
			{
				setContentStructureId(
					structuredContent.getContentStructureId());
				setId(structuredContent.getId());
				setTitle(structuredContent.getTitle());
			}
		};
	}

	private com.liferay.headless.delivery.client.dto.v1_0.StructuredContent
		_toStructuredContent(StructuredContent structuredContent) {

		return new com.liferay.headless.delivery.client.dto.v1_0.
			StructuredContent() {

			{
				setTitle(structuredContent.getTitle());
				setSiteId(structuredContent.getSiteId());
				setContentStructureId(
					structuredContent.getContentStructureId());
			}
		};
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;
	private StructuredContentResource _structuredContentResource;

}