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
import com.liferay.headless.admin.content.client.dto.v1_0.ContentField;
import com.liferay.headless.admin.content.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.admin.content.client.dto.v1_0.StructuredContent;
import com.liferay.headless.admin.content.client.pagination.Page;
import com.liferay.headless.admin.content.client.pagination.Pagination;
import com.liferay.headless.admin.content.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

		_ddmStructure = _addDDMStructure(testGroup, "test-ddm-structure.json");

		_addDDMTemplate(_ddmStructure);

		_irrelevantDDMStructure = _addDDMStructure(
			irrelevantGroup, "test-ddm-structure.json");
		_localizedDDMStructure = _addDDMStructure(
			testGroup, "test-localized-ddm-structure.json");

		StructuredContentResource.Builder builder =
			StructuredContentResource.builder();

		_structuredContentResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@Override
	@Test
	public void testGetSiteStructuredContentsPage() throws Exception {
		super.testGetSiteStructuredContentsPage();

		_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
			testGetSiteStructuredContentsPage_getSiteId(),
			RandomTestUtil.randomDouble());

		StructuredContent postStructuredContent =
			_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
				testGetSiteStructuredContentsPage_getSiteId(),
				Double.valueOf(1));

		com.liferay.headless.delivery.client.dto.v1_0.StructuredContent
			patchStructuredContent =
				_structuredContentResource.patchStructuredContent(
					postStructuredContent.getId(),
					new com.liferay.headless.delivery.client.dto.v1_0.
						StructuredContent() {

						{
							priority = Double.valueOf(3);
						}
					});

		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGroup.getGroupId(), true, null, null, "priority eq 3.0",
				Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(_toStructuredContent(patchStructuredContent)),
			(List<StructuredContent>)page.getItems());

		assertValid(page);
	}

	@Test
	public void testGetSiteStructuredContentsPageWithSort() throws Exception {
		StructuredContent postStructuredContent1 =
			_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
				testGetSiteStructuredContentsPage_getSiteId(),
				Double.valueOf(0.99));

		_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
			testGetSiteStructuredContentsPage_getSiteId(), Double.valueOf(1));

		StructuredContent postStructuredContent2 =
			_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
				testGetSiteStructuredContentsPage_getSiteId(),
				Double.valueOf(1.1));

		StructuredContent postStructuredContent3 =
			_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
				testGetSiteStructuredContentsPage_getSiteId(),
				Double.valueOf(2.99));

		Page<StructuredContent> page =
			structuredContentResource.getSiteStructuredContentsPage(
				testGroup.getGroupId(), true, null, null, "priority ne 1.0",
				Pagination.of(1, 10), "priority:asc");

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				postStructuredContent1, postStructuredContent2,
				postStructuredContent3),
			(List<StructuredContent>)page.getItems());

		assertValid(page);
	}

	@Override
	@Test
	public void testGetStructuredContentByVersion() throws Exception {
		super.testGetStructuredContentByVersion();

		Locale locale = LocaleUtil.getDefault();

		StructuredContent randomStructuredContent = _randomStructuredContent(
			locale);

		randomStructuredContent.setPriority(Double.valueOf(1));

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource structuredContentResource =
				_buildStructureContentResource(locale);

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContentDraft(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomStructuredContent);

		StructuredContent getStructuredContent =
			structuredContentResource.getStructuredContentByVersion(
				postStructuredContent.getId(), 1.0);

		assertEquals(postStructuredContent, getStructuredContent);
		Assert.assertEquals(
			Double.valueOf(1.0), getStructuredContent.getPriority());
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
	@Test
	public void testGraphQLGetStructuredContentByVersion() throws Exception {
		StructuredContent structuredContent =
			testGraphQLStructuredContent_addStructuredContent();

		Assert.assertTrue(
			equals(
				structuredContent,
				StructuredContentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"admin",
								new GraphQLField(
									"structuredContentByVersion",
									HashMapBuilder.<String, Object>put(
										"structuredContentId",
										structuredContent.getId()
									).put(
										"version", 1.0D
									).build(),
									getGraphQLFields()))),
						"JSONObject/data", "JSONObject/admin",
						"Object/structuredContentByVersion"))));
	}

	@Override
	@Test
	public void testGraphQLGetStructuredContentByVersionNotFound()
		throws Exception {

		Assert.assertEquals(
			"null",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"admin",
						new GraphQLField(
							"structuredContentByVersion",
							HashMapBuilder.<String, Object>put(
								"structuredContentId",
								RandomTestUtil.randomLong()
							).put(
								"version", RandomTestUtil.randomDouble()
							).build(),
							getGraphQLFields()))),
				"JSONObject/data", "JSONObject/admin",
				"Object/structuredContentByVersion"));
	}

	@Override
	@Test
	public void testPostSiteStructuredContentDraft() throws Exception {
		super.testPostSiteStructuredContentDraft();

		// Localized structured content with the default language

		_testPostSiteStructuredContentDraft(
			LocaleUtil.getDefault(), RandomTestUtil.randomDouble());

		// Localized structured content with a different language from the
		// default language

		_testPostSiteStructuredContentDraft(
			LocaleUtil.fromLanguageId("es-ES"), RandomTestUtil.randomDouble());

		// Structured content with a priority

		StructuredContent structuredContent1 =
			_testPostSiteStructuredContentDraft(
				LocaleUtil.getDefault(), Double.valueOf(1));

		Assert.assertEquals(
			Double.valueOf(1.0), structuredContent1.getPriority());

		// Structured content with the default priority

		StructuredContent structuredContent2 =
			_testPostSiteStructuredContentDraft(LocaleUtil.getDefault(), null);

		Assert.assertEquals(
			Double.valueOf(0.0), structuredContent2.getPriority());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"priority", "title"};
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
			testDeleteStructuredContentByVersion_addStructuredContent()
		throws Exception {

		return _postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Override
	protected Double testDeleteStructuredContentByVersion_getVersion()
		throws Exception {

		return 1.0D;
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
			testGetStructuredContentByVersion_addStructuredContent()
		throws Exception {

		return _postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Override
	protected Double testGetStructuredContentByVersion_getVersion()
		throws Exception {

		return 1.0D;
	}

	@Override
	protected StructuredContent
			testGraphQLStructuredContent_addStructuredContent()
		throws Exception {

		return _postSiteStructuredContent(
			testGroup.getGroupId(), randomStructuredContent());
	}

	@Override
	protected StructuredContent
			testPostSiteStructuredContentDraft_addStructuredContent(
				StructuredContent structuredContent)
		throws Exception {

		return structuredContentResource.postSiteStructuredContentDraft(
			testGroup.getGroupId(), structuredContent);
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
			_read("test-structured-content-template.vm"), LocaleUtil.US);
	}

	private void _assertLocalizedValue(
		Map<String, String> localizedValues, String value, String w3cLanguageId,
		Set<String> w3cLanguageIds) {

		Assert.assertEquals(w3cLanguageIds, localizedValues.keySet());
		Assert.assertEquals(value, localizedValues.get(w3cLanguageId));
	}

	private void _assertLocalizedValues(
		StructuredContent structuredContent, String w3cLanguageId) {

		Set<String> w3cLanguageIds = SetUtil.fromArray("es-ES", "en-US");

		Assert.assertEquals(
			w3cLanguageIds,
			SetUtil.fromArray(structuredContent.getAvailableLanguages()));

		_assertLocalizedValue(
			structuredContent.getDescription_i18n(),
			structuredContent.getDescription(), w3cLanguageId, w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getTitle_i18n(), structuredContent.getTitle(),
			w3cLanguageId, w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getFriendlyUrlPath_i18n(),
			structuredContent.getFriendlyUrlPath(), w3cLanguageId,
			w3cLanguageIds);
		_assertLocalizedValue(
			structuredContent.getDescription_i18n(),
			structuredContent.getDescription(), w3cLanguageId, w3cLanguageIds);

		for (ContentField contentField : structuredContent.getContentFields()) {
			Map<String, ContentFieldValue> contentFieldValue_i18n =
				contentField.getContentFieldValue_i18n();

			Assert.assertEquals(
				w3cLanguageIds, contentFieldValue_i18n.keySet());
		}
	}

	private com.liferay.headless.admin.content.client.resource.v1_0.
		StructuredContentResource _buildStructureContentResource(
			Locale locale) {

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource.Builder builder =
				com.liferay.headless.admin.content.client.resource.v1_0.
					StructuredContentResource.builder();

		return builder.authentication(
			"test@liferay.com", "test"
		).locale(
			locale
		).header(
			"X-Accept-All-Languages", "true"
		).build();
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
						setContentStructureId(
							structuredContent.getContentStructureId());
						setPriority(structuredContent.getPriority());
						setSiteId(structuredContent.getSiteId());
						setTitle(structuredContent.getTitle());
					}
				}));
	}

	private StructuredContent _randomStructuredContent(Locale locale)
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		String w3cLanguageId = LocaleUtil.toW3cLanguageId(locale);

		Map<String, ContentFieldValue> contentFieldValues = HashMapBuilder.put(
			"en-US",
			(ContentFieldValue)new ContentFieldValue() {

				{
					data = RandomTestUtil.randomString(10);
				}
			}
		).put(
			"es-ES",
			(ContentFieldValue)new ContentFieldValue() {

				{
					data = RandomTestUtil.randomString(10);
				}
			}
		).build();

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						contentFieldValue = contentFieldValues.get(
							w3cLanguageId);
						contentFieldValue_i18n = contentFieldValues;
						name = "MyText";
					}
				}
			});

		structuredContent.setContentStructureId(
			_localizedDDMStructure.getStructureId());

		Map<String, String> description_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setDescription(description_i18n.get(w3cLanguageId));
		structuredContent.setDescription_i18n(description_i18n);

		Map<String, String> friendlyUrlPath_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setFriendlyUrlPath(
			friendlyUrlPath_i18n.get(w3cLanguageId));
		structuredContent.setFriendlyUrlPath_i18n(friendlyUrlPath_i18n);

		Map<String, String> title_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setTitle(title_i18n.get(w3cLanguageId));
		structuredContent.setTitle_i18n(title_i18n);

		return structuredContent;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private StructuredContent
			_testGetSiteStructuredContentsPage_addStructuredContent_addDraftStructuredContentWithPriority(
				Long siteId, Double priority)
		throws Exception {

		Locale locale = LocaleUtil.getDefault();

		StructuredContent structuredContent = _randomStructuredContent(locale);

		structuredContent.setPriority(priority);

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource structuredContentResource =
				_buildStructureContentResource(locale);

		return structuredContentResource.postSiteStructuredContentDraft(
			siteId, structuredContent);
	}

	private StructuredContent _testPostSiteStructuredContentDraft(
			Locale locale, Double priority)
		throws Exception {

		StructuredContent randomStructuredContent = _randomStructuredContent(
			locale);

		randomStructuredContent.setPriority(priority);

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource structuredContentResource =
				_buildStructureContentResource(locale);

		StructuredContent postStructuredContent =
			structuredContentResource.postSiteStructuredContentDraft(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomStructuredContent);

		_assertLocalizedValues(
			postStructuredContent, LocaleUtil.toW3cLanguageId(locale));

		if (priority != null) {
			assertEquals(randomStructuredContent, postStructuredContent);
		}

		assertValid(postStructuredContent);

		return postStructuredContent;
	}

	private com.liferay.headless.delivery.client.dto.v1_0.StructuredContent
		_toStructuredContent(StructuredContent structuredContent) {

		return new com.liferay.headless.delivery.client.dto.v1_0.
			StructuredContent() {

			{
				setContentStructureId(
					structuredContent.getContentStructureId());
				setSiteId(structuredContent.getSiteId());
				setTitle(structuredContent.getTitle());
			}
		};
	}

	private StructuredContent _toStructuredContent(
		com.liferay.headless.delivery.client.dto.v1_0.StructuredContent
			structuredContent) {

		return new StructuredContent() {
			{
				setContentStructureId(
					structuredContent.getContentStructureId());
				setDateCreated(structuredContent.getDateCreated());
				setDateModified(structuredContent.getDateModified());
				setId(structuredContent.getId());
				setPriority(structuredContent.getPriority());
				setSiteId(structuredContent.getSiteId());
				setTitle(structuredContent.getTitle());
			}
		};
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;
	private DDMStructure _localizedDDMStructure;
	private StructuredContentResource _structuredContentResource;

}