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

package com.liferay.journal.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
@Sync
public class ManifestSummaryTest
	extends JournalArticleStagedModelDataHandlerTest {

	@Test
	public void testGetModelCounts() throws Exception {
		ManifestSummary manifestSummary = new ManifestSummary();

		manifestSummary.addModelAdditionCount(
			new StagedModelType(DDMStructure.class), 0);
		manifestSummary.addModelAdditionCount(
			new StagedModelType(JournalArticle.class), 1);
		manifestSummary.addModelAdditionCount(
			new StagedModelType(JournalArticle.class, DDMStructure.class), 1);
		manifestSummary.addModelAdditionCount(
			new StagedModelType(JournalArticle.class, DDMTemplate.class), 1);

		// Exact matches

		StagedModelType stagedModelType = new StagedModelType(
			JournalArticle.class);

		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			JournalArticle.class, DDMStructure.class);

		Assert.assertEquals(
			1, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(DDMStructure.class);

		Assert.assertEquals(
			0, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(DDMTemplate.class);

		Assert.assertEquals(
			-1, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			JournalArticle.class, JournalArticle.class);

		Assert.assertEquals(
			-1, manifestSummary.getModelAdditionCount(stagedModelType));

		// All

		stagedModelType = new StagedModelType(
			JournalArticle.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ALL);

		Assert.assertEquals(
			3, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			DDMStructure.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ALL);

		Assert.assertEquals(
			0, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			DDMTemplate.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ALL);

		Assert.assertEquals(
			-1, manifestSummary.getModelAdditionCount(stagedModelType));

		// Any

		stagedModelType = new StagedModelType(
			JournalArticle.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ANY);

		Assert.assertEquals(
			2, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			DDMStructure.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ANY);

		Assert.assertEquals(
			-1, manifestSummary.getModelAdditionCount(stagedModelType));

		stagedModelType = new StagedModelType(
			DDMTemplate.class.getName(),
			StagedModelType.REFERRER_CLASS_NAME_ANY);

		Assert.assertEquals(
			-1, manifestSummary.getModelAdditionCount(stagedModelType));
	}

	@Override
	protected void addComments(StagedModel stagedModel) throws Exception {
	}

	@Override
	protected void addRatings(StagedModel stagedModel) throws Exception {
	}

	@Override
	protected AssetEntry fetchAssetEntry(StagedModel stagedModel, Group group)
		throws Exception {

		return null;
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Assert.assertEquals(4, modelAdditionCounters.size());
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(DDMStructure.class, JournalArticle.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(DDMTemplate.class, DDMStructure.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(JournalArticle.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(JournalFolder.class)));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element headerElement = rootElement.addElement("header");

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			Time.RFC822_FORMAT);

		String rfc822DateString = Time.getRFC822();

		_exportDate = dateFormat.parse(rfc822DateString);

		headerElement.addAttribute("export-date", rfc822DateString);

		ExportImportHelperUtil.writeManifestSummary(document, manifestSummary);

		zipWriter.addEntry("/manifest.xml", document.asXML());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		ManifestSummary manifestSummary =
			ExportImportHelperUtil.getManifestSummary(portletDataContext);

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Assert.assertEquals(4, modelAdditionCounters.size());
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(DDMStructure.class, JournalArticle.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(DDMTemplate.class, DDMStructure.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(JournalArticle.class)));
		Assert.assertEquals(
			1,
			manifestSummary.getModelAdditionCount(
				new StagedModelType(JournalFolder.class)));
		Assert.assertTrue(
			DateUtil.equals(_exportDate, manifestSummary.getExportDate()));
	}

	private Date _exportDate;

}