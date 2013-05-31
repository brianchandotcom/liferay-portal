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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.lar.ExportImportUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.lar.JournalArticleStagedModelDataHandlerTest;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
public class ManifestSummaryTest
	extends JournalArticleStagedModelDataHandlerTest {

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, Long> modelCountMap = manifestSummary.getModelCounters();

		Assert.assertEquals(4, modelCountMap.size());
		Assert.assertEquals(
			1, manifestSummary.getModelCount(DDMStructure.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(DDMTemplate.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(JournalArticle.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(JournalFolder.class));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element headerElement = rootElement.addElement("header");

		_exportDate = Time.getRFC822();

		headerElement.addAttribute("export-date", _exportDate);

		ExportImportUtil.writeManifestSummary(document, manifestSummary);

		zipWriter.addEntry("/manifest.xml", document.asXML());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		ManifestSummary manifestSummary = ExportImportUtil.getManifestSummary(
			TestPropsValues.getUserId(), liveGroup.getGroupId(),
			getParameterMap(), zipWriter.getFile());

		Map<String, Long> modelCountMap = manifestSummary.getModelCounters();

		Assert.assertEquals(4, modelCountMap.size());
		Assert.assertEquals(
			1, manifestSummary.getModelCount(DDMStructure.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(DDMTemplate.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(JournalArticle.class));
		Assert.assertEquals(
			1, manifestSummary.getModelCount(JournalFolder.class));

		String exportedDate = Time.getRFC822(manifestSummary.getExportDate());

		Assert.assertEquals(_exportDate, exportedDate);
	}

	private String _exportDate;

}