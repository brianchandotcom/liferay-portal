/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalTemplateConstants;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.portlet.PortletPreferences;
import java.util.Locale;
import java.util.Map;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalTestUtilTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		_user = ServiceTestUtil.addUser(
			"JournalTestUtilName", _group.getGroupId());
	}

	@Test
	public void testAddArticleWithFolder() throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), 0, "Test Folder");

		Assert.assertNotNull(
			JournalTestUtil.addArticle(
				_group.getGroupId(), folder.getFolderId(), "Test Article",
				"This is a test article"));
	}

	@Test
	public void testAddArticleWithoutFolder()throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addArticle(
				_group.getGroupId(), "Test Article", "This is a test article"));
	}

	@Test
	public void testAddArticleWithStructureAndTemplate() throws Exception {
		DDMStructure ddmStructure = JournalTestUtil.addDDMStructure();

		String xsl = "$name.getData()";

		DDMTemplate ddmTemplate = JournalTestUtil.addDDMTemplate(
			ddmStructure.getStructureId(), xsl,
			JournalTemplateConstants.LANG_TYPE_VM);

		Document document = JournalTestUtil.createDocument("en_US", "en_US");

		Element dynamicElement = JournalTestUtil.addDynamicElement(
			document.getRootElement(), "text", "name");

		JournalTestUtil.addDynamicContent(
			dynamicElement, "en_US", "Joe Bloggs");

		String xml = document.asXML();

		Assert.assertNotNull(
			JournalTestUtil.addArticleWithXMLContent(
				xml, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey()));
	}

	@Test
	public void testAddDDMStructure() throws Exception {
		Assert.assertNotNull(JournalTestUtil.addDDMStructure());
	}

	@Test
	public void testAddDDMStructureWithLocale() throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addDDMStructure(LocaleUtil.getDefault()));
	}

	@Test
	public void testAddDDMStructureWithNonExistingLanguage() throws Exception {
		try {
			resetCompanyLanguages("en_US");

			JournalTestUtil.addDDMStructure(Locale.CANADA);

			StringBundler sb = new StringBundler(2);

			sb.append(LocaleUtil.toLanguageId(Locale.CANADA));
			sb.append(" locale is not present");

			Assert.fail(sb.toString());
		}
		catch (StructureNameException sne) {
		}
	}

	@Test
	public void testAddDDMStructureWithXSD() throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addDDMStructure(
				JournalTestUtil.getSampleStructureXSD()));
	}

	@Test
	public void testAddDDMStructureWithXSDAndLocale() throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addDDMStructure(
				JournalTestUtil.getSampleStructureXSD(),
				LocaleUtil.getDefault()));
	}

	@Test
	public void testAddDDMTemplateToDDMStructure() throws Exception {

		DDMStructure ddmStructure = JournalTestUtil.addDDMStructure();

		long structureId = ddmStructure.getStructureId();

		Assert.assertNotNull(JournalTestUtil.addDDMTemplate(structureId));
	}

	@Test
	public void testAddDDMTemplateToDDMStructureWithXSLAndLanguage()
		throws Exception {

		DDMStructure ddmStructure = JournalTestUtil.addDDMStructure();

		long structureId = ddmStructure.getStructureId();

		Assert.assertNotNull(
			JournalTestUtil.addDDMTemplate(
				structureId, JournalTestUtil.getSampleTemplateXSL(),
				JournalTemplateConstants.LANG_TYPE_VM));
	}

	@Test
	public void testAddDynamicContent() {
		try {
			Map<String, String> tokens = getTokens();

			Document document = JournalTestUtil.createDocument(
				"en_US,pt_BR", "en_US");

			Element dynamicElementElement = JournalTestUtil.addDynamicElement(
				document.getRootElement(), "text", "name");

			JournalTestUtil.addDynamicContent(
				dynamicElementElement, "en_US", "Joe Bloggs");

			String xml = document.asXML();

			String script = "$name.getData()";

			String content = JournalUtil.transform(
				null, tokens, Constants.VIEW, "en_US", xml, script,
				JournalTemplateConstants.LANG_TYPE_VM);

			Assert.assertEquals("Joe Bloggs", content);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testAddDynamicElement() {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Assert.assertNotNull(
			JournalTestUtil.addDynamicElement(rootElement, "text", "name"));
	}

	@Test
	public void testAddFolder() throws Exception {
		Assert.assertNotNull(
			JournalTestUtil.addFolder(_group.getGroupId(), 0, "Test Folder"));
	}

	@Test
	public void testCreateDocument() {
		Assert.assertNotNull(JournalTestUtil.createDocument("en_US", "en_US"));
	}

	@Test
	public void testCreateLocalizedContent() {
		Assert.assertNotNull(
			JournalTestUtil.createLocalizedContent(
				"This is a content", LocaleUtil.getDefault()));
	}

	@Test
	public void testGetSampleStructuredContent() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = JournalTestUtil.getSampleStructuredContent();

		String script = "$name.getData()";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			JournalTemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testGetSampleStructureXSD() {
		Assert.assertNotNull(JournalTestUtil.getSampleStructureXSD());
	}

	@Test
	public void testGetSampleTemplateXSL() {
		Assert.assertEquals(
			"$name.getData()", JournalTestUtil.getSampleTemplateXSL());
	}

	@Test
	public void testUpdateArticle() throws Exception {
		String content = "This is a test article";

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), "Test Article", content);

		String updatedContent = JournalTestUtil.createLocalizedContent(
			"This is an updated test article", LocaleUtil.getDefault());

		Assert.assertNotNull(
			JournalTestUtil.updateArticle(
				journalArticle, journalArticle.getTitle(), updatedContent));
	}

	protected Map<String, String> getTokens() throws Exception {
		Map<String, String> tokens = JournalUtil.getTokens(
			TestPropsValues.getGroupId(), null, null);

		tokens.put(
			"company_id", String.valueOf(TestPropsValues.getCompanyId()));
		tokens.put("group_id", String.valueOf(TestPropsValues.getGroupId()));

		return tokens;
	}

	protected void resetCompanyLanguages(String newLocales) throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		LanguageUtil.resetAvailableLocales(companyId);

		preferences.setValue(PropsKeys.LOCALES, newLocales);

		preferences.store();
	}

	private Group _group;

	private User _user;

}