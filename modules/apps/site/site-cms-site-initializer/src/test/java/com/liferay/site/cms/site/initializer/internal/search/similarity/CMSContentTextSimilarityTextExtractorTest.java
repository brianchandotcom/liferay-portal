/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.similarity;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.bag.ObjectFieldBag;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mikel Lorza
 */
public class CMSContentTextSimilarityTextExtractorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		HtmlParser htmlParser = Mockito.mock(HtmlParser.class);

		Mockito.when(
			htmlParser.extractText(Mockito.anyString())
		).thenAnswer(
			invocation -> {
				String html = invocation.getArgument(0, String.class);

				return html.replaceAll("<[^>]+>", "");
			}
		);

		_cmsContentTextSimilarityTextExtractor =
			new CMSContentTextSimilarityTextExtractor(htmlParser);
	}

	@Test
	public void testGetLanguageIds() throws Exception {
		Assert.assertEquals(
			Arrays.asList("en_US", "es_ES"),
			ListUtil.fromCollection(
				_cmsContentTextSimilarityTextExtractor.getLanguageIds(
					_mockObjectEntry(
						HashMapBuilder.<String, Serializable>put(
							"i18nContent",
							HashMapBuilder.put(
								"en_US", "<p>Body</p>"
							).put(
								"es_ES", "<p>Cuerpo</p>"
							).build()
						).build()))));
	}

	@Test
	public void testGetLanguageIdsWithTitleOnlyTranslation() throws Exception {
		Assert.assertEquals(
			Arrays.asList("en_US"),
			ListUtil.fromCollection(
				_cmsContentTextSimilarityTextExtractor.getLanguageIds(
					_mockObjectEntry(
						HashMapBuilder.<String, Serializable>put(
							"i18nContent",
							HashMapBuilder.put(
								"en_US", "<p>Body</p>"
							).build()
						).put(
							"i18nTitle",
							HashMapBuilder.put(
								"en_US", "Title"
							).put(
								"ja_JP", "タイトル"
							).build()
						).build()))));
	}

	@Test
	public void testGetTextWithNonlocalizedField() throws Exception {
		Assert.assertEquals(
			"The body ACME-1234 ",
			_cmsContentTextSimilarityTextExtractor.getText(
				"en_US",
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"i18nContent",
						HashMapBuilder.put(
							"en_US", "<p>The body</p>"
						).build()
					).put(
						"reference", "ACME-1234"
					).build())));
	}

	@Test
	public void testGetTextWithoutTranslation() throws Exception {
		Assert.assertEquals(
			"",
			_cmsContentTextSimilarityTextExtractor.getText(
				"es_ES",
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"i18nContent",
						HashMapBuilder.put(
							"en_US", "<p>The body</p>"
						).build()
					).put(
						"i18nTitle",
						HashMapBuilder.put(
							"es_ES", "Solo el titulo"
						).build()
					).build())));
	}

	@Test
	public void testGetTextWithTitle() throws Exception {
		Assert.assertEquals(
			"The body of the content ",
			_cmsContentTextSimilarityTextExtractor.getText(
				"en_US",
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"i18nContent",
						HashMapBuilder.put(
							"en_US", "<p>The body of the content</p>"
						).build()
					).put(
						"i18nTitle",
						HashMapBuilder.put(
							"en_US", "Zeta Quarterly Report Alpha"
						).build()
					).build())));
	}

	private ObjectEntry _mockObjectEntry(
			Map<String, Serializable> indexedValues)
		throws Exception {

		ObjectField contentObjectField = _mockObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT, "i18nContent", true,
			_OBJECT_FIELD_ID_CONTENT, "content");
		ObjectField referenceObjectField = _mockObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT, null, false,
			_OBJECT_FIELD_ID_REFERENCE, "reference");
		ObjectField titleObjectField = _mockObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT, "i18nTitle", true,
			_OBJECT_FIELD_ID_TITLE, "title");

		List<ObjectField> objectFields = Arrays.asList(
			contentObjectField, referenceObjectField, titleObjectField);

		ObjectFieldBag objectFieldBag = new ObjectFieldBag(objectFields);

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectFieldBag()
		).thenReturn(
			objectFieldBag
		);

		Mockito.when(
			objectDefinition.getTitleObjectFieldId()
		).thenReturn(
			_OBJECT_FIELD_ID_TITLE
		);

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getDefaultLanguageId()
		).thenReturn(
			"en_US"
		);

		Mockito.when(
			objectEntry.getIndexedValues()
		).thenReturn(
			indexedValues
		);

		Mockito.when(
			objectEntry.getObjectDefinition()
		).thenReturn(
			objectDefinition
		);

		return objectEntry;
	}

	private ObjectField _mockObjectField(
		String businessType, String i18nObjectFieldName, boolean localized,
		long objectFieldId, String name) {

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			businessType
		);

		Mockito.when(
			objectField.getI18nObjectFieldName()
		).thenReturn(
			i18nObjectFieldName
		);

		Mockito.when(
			objectField.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			objectField.getObjectFieldId()
		).thenReturn(
			objectFieldId
		);

		Mockito.when(
			objectField.isIndexed()
		).thenReturn(
			true
		);

		Mockito.when(
			objectField.isLocalized()
		).thenReturn(
			localized
		);

		return objectField;
	}

	private static final long _OBJECT_FIELD_ID_CONTENT = 2;

	private static final long _OBJECT_FIELD_ID_REFERENCE = 3;

	private static final long _OBJECT_FIELD_ID_TITLE = 1;

	private CMSContentTextSimilarityTextExtractor
		_cmsContentTextSimilarityTextExtractor;

}