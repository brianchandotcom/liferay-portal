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

package com.liferay.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorController;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.lar.CurrentUserIdStrategy;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class ExportImportContentProcessorControllerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testExport() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();
		Date startDate = new Date(System.currentTimeMillis() - Time.HOUR);
		Date endDate = new Date();
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_group.getCompanyId(), _group.getGroupId(), parameterMap,
				startDate, endDate, zipWriter);

		ExportImportContentProcessorController
			exportImportContentProcessorController =
				new ExportImportContentProcessorController();

		String content =
			exportImportContentProcessorController.
				replaceExportContentReferences(
					portletDataContext, new DummyStagedModel(), "abc", false,
					false);

		Assert.assertEquals("abc123", content);
	}

	@Test
	public void testImport() throws Exception {
		boolean privateLayout = false;
		long[] layoutIds = null;
		Map<String, String[]> parameterMap = new HashMap<>();

		Map<String, Serializable> settingsMap =
			ExportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), _group.getGroupId(),
					privateLayout, layoutIds, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_REMOTE,
					settingsMap);

		File file = ExportImportLocalServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_group.getCompanyId(), _group.getGroupId(), parameterMap,
				new CurrentUserIdStrategy(TestPropsValues.getUser()),
				ZipReaderFactoryUtil.getZipReader(file));

		ExportImportContentProcessorController
			exportImportContentProcessorController =
				new ExportImportContentProcessorController();

		String content =
			exportImportContentProcessorController.
				replaceImportContentReferences(
					portletDataContext, new DummyStagedModel(), "abc");

		Assert.assertEquals("abc123", content);
	}

	@DeleteAfterTestRun
	private Group _group;

}