/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.JournalTestUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalTransformerTest {

	@Test
	public void testContentTransformerListener() throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		JournalTestUtil.addDynamicElementElement(rootElement, "text", "name");
		JournalTestUtil.addDynamicElementElement(rootElement, "text", "link");

		String xsd = document.asXML();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName(), xsd);

		String xsl = "$name.getData()";

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmStructure.getStructureId(), TemplateConstants.LANG_TYPE_VM, xsl);

		document = JournalTestUtil.createDocument("en_US", "en_US");

		Element dynamicElementElement =
			JournalTestUtil.addDynamicElementElement(
				document.getRootElement(), "text", "name");

		JournalTestUtil.addDynamicContentElement(
			dynamicElementElement, "en_US", "Joe Bloggs");

		String xml = document.asXML();

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			xml, ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey());

		Map<String, String> tokens = getTokens();

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, xsl,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);

		Element element = (Element)document.selectSingleNode(
			"//dynamic-content");

		element.setText("[@" + article.getArticleId()  + ";name@]");

		content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", document.asXML(), xsl,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testFTLTransformation() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String script = "${name.getData()} - ${viewMode}";

		String content = JournalUtil.transform(
			null, tokens, Constants.PRINT, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_FTL);

		Assert.assertEquals("Joe Bloggs - print", content);
	}

	@Test
	public void testLocaleTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		Document document = JournalTestUtil.createDocument(
			"en_US,pt_BR", "en_US");

		Element dynamicElementElement =
			JournalTestUtil.addDynamicElementElement(
				document.getRootElement(), "text", "name");

		JournalTestUtil.addDynamicContentElement(
			dynamicElementElement, "en_US", "Joe Bloggs");
		JournalTestUtil.addDynamicContentElement(
			dynamicElementElement, "pt_BR", "Joao da Silva");

		String xml = document.asXML();

		String script = "$name.getData()";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);

		content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "pt_BR", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joao da Silva", content);

		content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "fr_CA", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testRegexTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String script = "Hello $name.getData(), Welcome to beta.sample.com.";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals(
			"Hello Joe Bloggs, Welcome to production.sample.com.", content);
	}

	@Test
	public void testTokensTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent();

		String script = "@company_id@";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()), content);

		script = "@@company_id@@";

		content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()), content);
	}

	@Test
	public void testViewCounterTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		tokens.put("article_resource_pk", "1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent();

		String script = "@view_counter@";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		StringBundler sb = new StringBundler(6);

		sb.append("<script type=\"text/javascript\">");
		sb.append("Liferay.Service.Asset.AssetEntry.incrementViewCounter");
		sb.append("({userId:0, className:'");
		sb.append("com.liferay.portlet.journal.model.JournalArticle', ");
		sb.append("classPK:1});");
		sb.append("</script>");

		Assert.assertEquals(sb.toString(), content);
	}

	@Test
	public void testVMTransformation() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String script = "$name.getData()";

		String content = JournalUtil.transform(
			null, tokens, Constants.VIEW, "en_US", xml, script,
			TemplateConstants.LANG_TYPE_VM);

		Assert.assertEquals("Joe Bloggs", content);
	}

	protected Map<String, String> getTokens() throws Exception {
		Map<String, String> tokens = JournalUtil.getTokens(
			TestPropsValues.getGroupId(), null, null);

		tokens.put(
			"article_group_id", String.valueOf(TestPropsValues.getGroupId()));
		tokens.put(
			"company_id", String.valueOf(TestPropsValues.getCompanyId()));

		return tokens;
	}

}