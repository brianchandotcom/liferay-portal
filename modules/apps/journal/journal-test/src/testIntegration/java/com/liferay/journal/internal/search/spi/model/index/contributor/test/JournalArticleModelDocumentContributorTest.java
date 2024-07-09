/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.search.spi.model.index.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class JournalArticleModelDocumentContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "english title"
			).put(
				LocaleUtil.SPAIN, "spanish title"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "english description"
			).put(
				LocaleUtil.SPAIN, "spanish description"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, "english content"
			).put(
				LocaleUtil.SPAIN, "spanish content"
			).build(),
			LocaleUtil.SPAIN, false, true,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAvailableLanguageIds() throws Exception {
		Assert.assertEquals(
			SetUtil.fromArray(
				LocalizationUtil.getAvailableLanguageIds(
					_journalArticle.getDocument())),
			SetUtil.fromArray(_journalArticle.getAvailableLanguageIds()));
	}

	@Test
	public void testFieldContent() throws Exception {
		Document document = _getDocument();

		for (String languageId : _journalArticle.getAvailableLanguageIds()) {
			Assert.assertEquals(
				_getDDMIndexerContent(languageId),
				document.get(
					LocaleUtil.fromLanguageId(languageId), Field.CONTENT));
		}
	}

	@Test
	public void testFieldDefaultLanguageId() throws Exception {
		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		DDMFormValues ddmFormValues = _ddmFieldLocalService.getDDMFormValues(
			ddmStructure.getDDMForm(), _journalArticle.getId());

		Document document = _getDocument();

		Assert.assertEquals(
			LocaleUtil.toLanguageId(ddmFormValues.getDefaultLocale()),
			document.get("defaultLanguageId"));
	}

	private String _getDDMIndexerContent(String languageId) throws Exception {
		com.liferay.portal.kernel.xml.Document document =
			_journalArticle.getDocument();

		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		return _ddmIndexer.extractIndexableAttributes(
			ddmStructure,
			_fieldsToDDMFormValuesConverter.convert(
				ddmStructure,
				_journalConverter.getDDMFields(ddmStructure, document.asXML())),
			LocaleUtil.fromLanguageId(languageId));
	}

	private Document _getDocument() {
		DocumentImpl documentImpl = new DocumentImpl();

		Assert.assertNotNull(_journalArticle.getDDMFormValues());

		_modelDocumentContributor.contribute(documentImpl, _journalArticle);

		return documentImpl;
	}

	@Inject
	private DDMFieldLocalService _ddmFieldLocalService;

	@Inject
	private DDMIndexer _ddmIndexer;

	@Inject
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private JournalArticle _journalArticle;

	@Inject
	private JournalConverter _journalConverter;

	@Inject(
		filter = "indexer.class.name=com.liferay.journal.model.JournalArticle"
	)
	private ModelDocumentContributor<JournalArticle> _modelDocumentContributor;

}