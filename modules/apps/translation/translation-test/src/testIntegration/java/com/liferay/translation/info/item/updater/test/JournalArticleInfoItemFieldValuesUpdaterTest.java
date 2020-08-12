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

package com.liferay.translation.info.item.updater.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.util.Locale;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemFieldValuesUpdaterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule testRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		User user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpdateArticleFromInfoItemFieldValuesAddsTranslatedContent()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, "<p>This is the content</p>"
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"test-journal-article-122.xlf"));

		journalArticle =
			_journalArticleInfoItemFieldValuesUpdater.
				updateFromInfoItemFieldValues(
					journalArticle, infoItemFieldValues);

		Assert.assertEquals(
			"Este es el titulo", journalArticle.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Este es el resumen",
			journalArticle.getDescription(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"<p>Este es el contenido</p>",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.SPAIN));
	}

	@Test
	public void testUpdateArticleFromInfoItemFieldValuesDoesNotModifyOtherTranslations()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "Este es el titulo"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "Esta es la descripcion"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "Este es el contenido"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"test-journal-article-122-ja-JP.xlf"));

		journalArticle =
			_journalArticleInfoItemFieldValuesUpdater.
				updateFromInfoItemFieldValues(
					journalArticle, infoItemFieldValues);

		Assert.assertEquals(
			"これはタイトルです", journalArticle.getTitle(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"Este es el titulo", journalArticle.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"これは要約です", journalArticle.getDescription(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"Esta es la descripcion",
			journalArticle.getDescription(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"<p>これが内容です</p>",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.JAPAN));
		Assert.assertEquals(
			"Este es el contenido",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.SPAIN));
	}

	@Test
	public void testUpdateArticleFromInfoItemFieldValuesUpdatesOnlyTheTitle()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "Descripcion"
			).put(
				LocaleUtil.US, "description"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, "content"
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"test-journal-article-122-only-title.xlf"));

		journalArticle =
			_journalArticleInfoItemFieldValuesUpdater.
				updateFromInfoItemFieldValues(
					journalArticle, infoItemFieldValues);

		Assert.assertEquals(
			"Este es el titulo", journalArticle.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			"Descripcion", journalArticle.getDescription(LocaleUtil.SPAIN));
		Assert.assertEquals(
			StringPool.BLANK,
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.SPAIN));
	}

	@Test
	public void testUpdateArticleFromInfoItemFieldValuesUpdatesTranslations()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.JAPAN, "translate title to japanese"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.JAPAN, "translate description to japanese"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.JAPAN, "translate content to japanese"
			).put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);

		Assert.assertEquals(
			"translate title to japanese",
			journalArticle.getTitle(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"translate description to japanese",
			journalArticle.getDescription(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"translate content to japanese",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.JAPAN));

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"test-journal-article-122-ja-JP.xlf"));

		journalArticle =
			_journalArticleInfoItemFieldValuesUpdater.
				updateFromInfoItemFieldValues(
					journalArticle, infoItemFieldValues);

		Assert.assertEquals(
			"これはタイトルです", journalArticle.getTitle(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"これは要約です", journalArticle.getDescription(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"<p>これが内容です</p>",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.JAPAN));
	}

	@Test
	public void testUpdateArticleFromInfoItemFieldValuesXLIFFv12File()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			LocaleUtil.getSiteDefault(), false, true, _serviceContext);

		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"example-1_2-oasis.xlf"));

		journalArticle =
			_journalArticleInfoItemFieldValuesUpdater.
				updateFromInfoItemFieldValues(
					journalArticle, infoItemFieldValues);

		Assert.assertEquals(
			"Quetzal", journalArticle.getTitle(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"XLIFF データ・マネージャ", journalArticle.getDescription(LocaleUtil.JAPAN));
		Assert.assertEquals(
			"<p>XLIFF 文書を編集、または処理 するアプリケーションです。</p>",
			_getContent(
				journalArticle.getContent(), "name", LocaleUtil.US,
				LocaleUtil.JAPAN));
	}

	private String _getContent(
			String actualXML, String fieldName, Locale sourceLocale,
			Locale targetLocale)
		throws Exception {

		Document actualDocument = SAXReaderUtil.read(actualXML);

		Element rootElement = actualDocument.getRootElement();

		String availableLanguageIds = rootElement.attributeValue(
			"available-locales");

		if (!availableLanguageIds.contains(
				LocaleUtil.toLanguageId(sourceLocale)) ||
			!availableLanguageIds.contains(
				LocaleUtil.toLanguageId(targetLocale))) {

			return StringPool.BLANK;
		}

		for (Element dynamicElementElement :
				rootElement.elements("dynamic-element")) {

			String attribute = dynamicElementElement.attributeValue(
				"name", StringPool.BLANK);

			if (!Objects.equals(attribute, fieldName)) {
				return StringPool.BLANK;
			}

			for (Element element :
					dynamicElementElement.elements("dynamic-content")) {

				String languageId = element.attributeValue(
					"language-id", StringPool.BLANK);

				if (Objects.equals(
						languageId, LocaleUtil.toLanguageId(targetLocale))) {

					return element.getStringValue();
				}
			}
		}

		return StringPool.BLANK;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "item.class.name=com.liferay.journal.model.JournalArticle")
	private InfoItemFieldValuesUpdater<JournalArticle>
		_journalArticleInfoItemFieldValuesUpdater;

	private ServiceContext _serviceContext;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoItemFieldValuesImporter
		_xliffTranslationInfoItemFieldValuesImporter;

}