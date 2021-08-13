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

package com.liferay.exportimport.internal.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class LayoutReferencesExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(3);

		sb.append("(&(content.processor.type=LayoutReferences)(objectClass=");
		sb.append(ExportImportContentProcessor.class.getName());
		sb.append("))");

		_serviceTracker = registry.trackServices(
			registry.getFilter(sb.toString()));

		_serviceTracker.open();
	}

	@Before
	public void setUp() {
		_layoutReferencesExportImportContentProcessor =
			_serviceTracker.getService();
	}

	@Test
	public void testExternalURLNotModified() throws Exception {
		Group exportGroup = GroupTestUtil.addGroup();
		Group importGroup = GroupTestUtil.addGroup();

		String urlToExport = RandomTestUtil.randomString(
			LayoutFriendlyURLRandomizerBumper.INSTANCE);

		String actualImportedURL = _exportAndImportLayoutURL(
			urlToExport, exportGroup, importGroup);

		Assert.assertEquals(urlToExport, actualImportedURL);
	}

	private String _exportAndImportLayoutURL(
			String url, Group exportGroup, Group importGroup)
		throws Exception {

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		PortletDataContext exportPortletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				exportGroup.getCompanyId(), exportGroup.getGroupId(),
				new HashMap<>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		Element rootElement = SAXReaderUtil.createElement("root");

		exportPortletDataContext.setExportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		exportPortletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		StagedModel referrerStagedModel = JournalTestUtil.addArticle(
			exportGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		String contentToExport = _CONTENT_PREFIX + url + _CONTENT_POSTFIX;

		String exportedContent =
			_layoutReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					exportPortletDataContext, referrerStagedModel,
					contentToExport, true, false);

		PortletDataContext importPortletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				importGroup.getCompanyId(), importGroup.getGroupId(),
				new HashMap<>(), new TestUserIdStrategy(), testReaderWriter);

		importPortletDataContext.setImportDataRootElement(rootElement);

		importPortletDataContext.setMissingReferencesElement(
			missingReferencesElement);

		String importedContent =
			_layoutReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					importPortletDataContext, referrerStagedModel,
					exportedContent);

		return importedContent.substring(
			_CONTENT_PREFIX.length(),
			importedContent.length() - _CONTENT_POSTFIX.length());
	}

	private static final String _CONTENT_POSTFIX = "\">link</a>";

	private static final String _CONTENT_PREFIX = "<a href=\"";

	private static ServiceTracker
		<ExportImportContentProcessor<String>,
		 ExportImportContentProcessor<String>> _serviceTracker;

	private ExportImportContentProcessor<String>
		_layoutReferencesExportImportContentProcessor;

}