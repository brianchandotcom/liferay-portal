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

package com.liferay.journal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
@Sync
public class JournalTestUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddArticleWithDDMStructureAndDDMTemplate()
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_VM,
			JournalTestUtil.getSampleTemplateXSL());

		Assert.assertNotNull(
			JournalTestUtil.addArticleWithXMLContent(
				content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey()));
	}

	@Test
	public void testAddArticleWithFolder() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), 0, "Test Folder");

		Assert.assertNotNull(
			JournalTestUtil.addArticle(
				_group.getGroupId(), folder.getFolderId(), "Test Article",
				"This is a test article."));
	}

	@Test
	public void testAddArticleWithoutFolder()throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addArticle(
				_group.getGroupId(), "Test Article",
				"This is a test article."));
	}

	@Test
	public void testAddDDMStructure() throws Exception {
		Assert.assertNotNull(
			DDMStructureTestUtil.addStructure(JournalArticle.class.getName()));
	}

	@Test
	public void testAddDDMStructureWithDefinition() throws Exception {
		Assert.assertNotNull(
			DDMStructureTestUtil.addStructure(JournalArticle.class.getName()));
	}

	@Test
	public void testAddDDMStructureWithDefinitionAndLocale() throws Exception {
		Assert.assertNotNull(
			DDMStructureTestUtil.addStructure(
				JournalArticle.class.getName(), LocaleUtil.getSiteDefault()));
	}

	@Test
	public void testAddDDMStructureWithLocale() throws Exception {
		Assert.assertNotNull(
			DDMStructureTestUtil.addStructure(
				JournalArticle.class.getName(), LocaleUtil.getSiteDefault()));
	}

	@Test(expected = LocaleException.class)
	public void testAddDDMStructureWithNonexistingLocale() throws Exception {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();
		Locale defaultLocale = LocaleUtil.getDefault();

		try {
			CompanyTestUtil.resetCompanyLocales(
				PortalUtil.getDefaultCompanyId(), Arrays.asList(LocaleUtil.US),
				LocaleUtil.US);

			DDMStructureTestUtil.addStructure(
				JournalArticle.class.getName(), LocaleUtil.CANADA);
		}
		finally {
			CompanyTestUtil.resetCompanyLocales(
				PortalUtil.getDefaultCompanyId(), availableLocales,
				defaultLocale);
		}
	}

	@Test
	public void testAddDDMTemplateToDDMStructure() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		Assert.assertNotNull(
			DDMTemplateTestUtil.addTemplate(
				ddmStructure.getStructureId(),
				PortalUtil.getClassNameId(JournalArticle.class)));
	}

	@Test
	public void testAddDDMTemplateToDDMStructureWithXSLAndLanguage()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		Assert.assertNotNull(
			DDMTemplateTestUtil.addTemplate(
				ddmStructure.getStructureId(),
				PortalUtil.getClassNameId(JournalArticle.class),
				TemplateConstants.LANG_TYPE_VM,
				JournalTestUtil.getSampleTemplateXSL()));
	}

	@Test
	public void testAddDynamicContent() throws Exception {
		Map<Locale, String> contents = new HashMap<>();

		contents.put(LocaleUtil.BRAZIL, "Joe Bloggs");
		contents.put(LocaleUtil.US, "Joe Bloggs");

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			contents, LanguageUtil.getLanguageId(LocaleUtil.US));

		String content = JournalUtil.transform(
			null, getTokens(), Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null,
			JournalTestUtil.getSampleTemplateXSL(),
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testAddDynamicElement() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Assert.assertNotNull(
			JournalTestUtil.addDynamicElementElement(
				rootElement, "text", "name"));
	}

	@Test
	public void testAddFolder() throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addFolder(_group.getGroupId(), 0, "Test Folder"));
	}

	@Test
	public void testDeleteDDMStructureWithDefaultValues() throws Exception {
		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		Assert.assertNotNull(
			JournalTestUtil.addArticleWithXMLContent(
				TestPropsValues.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				PortalUtil.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), content,
				ddmStructure.getStructureKey(), null,
				LocaleUtil.getSiteDefault()));

		DDMStructureLocalServiceUtil.deleteDDMStructure(ddmStructure);

		try {
			Assert.assertNull(
				"Default values of deleted DDMStructure were not removed",
				JournalArticleLocalServiceUtil.getArticle(
					ddmStructure.getGroupId(), DDMStructure.class.getName(),
					ddmStructure.getStructureId()));
		}
		catch (NoSuchArticleException e) {
		}
	}

	@Test
	public void testGetSampleStructuredContent() throws Exception {
		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String content = JournalUtil.transform(
			null, getTokens(), Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null,
			JournalTestUtil.getSampleTemplateXSL(),
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testGetSampleStructureDefinition() {
		Assert.assertNotNull(DDMStructureTestUtil.getSampleDDMForm());
	}

	@Test
	public void testGetSampleTemplateXSL() {
		Assert.assertEquals(
			"$name.getData()", JournalTestUtil.getSampleTemplateXSL());
	}

	@Test
	public void testUpdateArticle() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), "Test Article", "This is a test article.");

		Map<Locale, String> contents = new HashMap<>();

		contents.put(Locale.US, "This is an updated test article.");

		String defaultLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getSiteDefault());

		String localizedContent =
			DDMStructureTestUtil.getSampleStructuredContent(
				contents, defaultLanguageId);

		Assert.assertNotNull(
			JournalTestUtil.updateArticle(
				article, article.getTitle(), localizedContent));
	}

	protected Map<String, String> getTokens() throws Exception {
		Map<String, String> tokens = JournalUtil.getTokens(
			TestPropsValues.getGroupId(), (PortletRequestModel)null, null);

		tokens.put(
			"article_group_id", String.valueOf(TestPropsValues.getGroupId()));
		tokens.put(
			"company_id", String.valueOf(TestPropsValues.getCompanyId()));

		return tokens;
	}

	@DeleteAfterTestRun
	private Group _group;

}