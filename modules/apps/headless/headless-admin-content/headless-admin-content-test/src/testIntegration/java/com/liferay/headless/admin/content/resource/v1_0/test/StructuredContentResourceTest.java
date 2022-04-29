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

		_ddmLocalizedStructure = _addDDMStructure(
			testGroup, "test-ddm-localized-structure.json");

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

		// Localized Structured Content with the default language

		Locale locale = LocaleUtil.getDefault();

		StructuredContent randomLocalizedStructuredContent1 =
			_randomLocalizedStructuredContent(locale);

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource englishStructuredContentResource =
				_buildLocalizedStructureContentResource(locale);

		StructuredContent postStructuredContent1 =
			englishStructuredContentResource.postSiteStructuredContentDraft(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomLocalizedStructuredContent1);

		_assertLocalizedFields(
			LocaleUtil.toW3cLanguageId(locale), postStructuredContent1);
		assertEquals(randomLocalizedStructuredContent1, postStructuredContent1);
		assertValid(postStructuredContent1);

		// Localized Structured Content with a different language from default

		locale = LocaleUtil.fromLanguageId("es-ES");

		StructuredContent randomLocalizedStructuredContent2 =
			_randomLocalizedStructuredContent(locale);

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource spanishStructuredContentResource =
				_buildLocalizedStructureContentResource(locale);

		StructuredContent postStructuredContent2 =
			spanishStructuredContentResource.postSiteStructuredContentDraft(
				testGetSiteStructuredContentsPage_getSiteId(),
				randomLocalizedStructuredContent2);

		_assertLocalizedFields(
			LocaleUtil.toW3cLanguageId(locale), postStructuredContent2);
		assertEquals(randomLocalizedStructuredContent2, postStructuredContent2);
		assertValid(postStructuredContent2);
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

	private void _assertLocalizedField(
		String defaultLanguage, Set<String> languages,
		Map<String, String> localizedValues, String value) {

		Assert.assertEquals(languages, localizedValues.keySet());
		Assert.assertEquals(value, localizedValues.get(defaultLanguage));
	}

	private void _assertLocalizedFields(
		String defaultLanguage, StructuredContent structuredContent) {

		Set<String> languages = SetUtil.fromArray("es-ES", "en-US");

		Assert.assertEquals(
			languages,
			SetUtil.fromArray(structuredContent.getAvailableLanguages()));

		_assertLocalizedField(
			defaultLanguage, languages, structuredContent.getDescription_i18n(),
			structuredContent.getDescription());
		_assertLocalizedField(
			defaultLanguage, languages, structuredContent.getTitle_i18n(),
			structuredContent.getTitle());
		_assertLocalizedField(
			defaultLanguage, languages,
			structuredContent.getFriendlyUrlPath_i18n(),
			structuredContent.getFriendlyUrlPath());
		_assertLocalizedField(
			defaultLanguage, languages, structuredContent.getDescription_i18n(),
			structuredContent.getDescription());

		for (ContentField contentField : structuredContent.getContentFields()) {
			Map<String, ContentFieldValue> contentFieldValue_i18n =
				contentField.getContentFieldValue_i18n();

			Assert.assertEquals(languages, contentFieldValue_i18n.keySet());
		}
	}

	private com.liferay.headless.admin.content.client.resource.v1_0.
		StructuredContentResource _buildLocalizedStructureContentResource(
			Locale defaultLocale) {

		com.liferay.headless.admin.content.client.resource.v1_0.
			StructuredContentResource.Builder builder =
				com.liferay.headless.admin.content.client.resource.v1_0.
					StructuredContentResource.builder();

		return builder.authentication(
			"test@liferay.com", "test"
		).locale(
			defaultLocale
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

	private StructuredContent _randomLocalizedStructuredContent(
			Locale defaultLocale)
		throws Exception {

		StructuredContent structuredContent = super.randomStructuredContent();

		String defaultLanguage = LocaleUtil.toW3cLanguageId(defaultLocale);

		ContentFieldValue randomEnglishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};
		ContentFieldValue randomSpanishContentFieldValue =
			new ContentFieldValue() {
				{
					data = RandomTestUtil.randomString(10);
				}
			};

		structuredContent.setContentFields(
			new ContentField[] {
				new ContentField() {
					{
						Map<String, ContentFieldValue> contentFieldValueMap =
							HashMapBuilder.put(
								"en-US", randomEnglishContentFieldValue
							).put(
								"es-ES", randomSpanishContentFieldValue
							).build();

						contentFieldValue = contentFieldValueMap.get(
							defaultLanguage);

						contentFieldValue_i18n = contentFieldValueMap;

						name = "MyText";
					}
				}
			});

		Map<String, String> description_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setDescription(description_i18n.get(defaultLanguage));
		structuredContent.setDescription_i18n(description_i18n);

		Map<String, String> friendlyUrlPath_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setFriendlyUrlPath(
			friendlyUrlPath_i18n.get(defaultLanguage));
		structuredContent.setFriendlyUrlPath_i18n(friendlyUrlPath_i18n);

		Map<String, String> title_i18n = HashMapBuilder.put(
			"en-US", RandomTestUtil.randomString()
		).put(
			"es-ES", RandomTestUtil.randomString()
		).build();

		structuredContent.setTitle(title_i18n.get(defaultLanguage));
		structuredContent.setTitle_i18n(title_i18n);

		structuredContent.setContentStructureId(
			_ddmLocalizedStructure.getStructureId());

		return structuredContent;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
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

	private DDMStructure _ddmLocalizedStructure;
	private DDMStructure _ddmStructure;
	private DDMStructure _irrelevantDDMStructure;
	private StructuredContentResource _structuredContentResource;

}