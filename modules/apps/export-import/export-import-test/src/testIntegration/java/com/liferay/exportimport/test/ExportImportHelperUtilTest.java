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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Péter Borkuti
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class ExportImportHelperUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();
	}

	@Test
	public void testDataSiteLevelPortletsRank() throws Exception {
		List<Portlet> portlets =
			ExportImportHelperUtil.getDataSiteLevelPortlets(
				TestPropsValues.getCompanyId());

		Integer previousRank = null;

		for (Portlet portlet : portlets) {
			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			int actualRank = portletDataHandler.getRank();

			if (previousRank != null) {
				Assert.assertTrue(
					"Portlets should be in ascending order by rank",
					previousRank <= actualRank);
			}

			previousRank = actualRank;
		}
	}

	@Test
	public void testGetExportPortletControlsMapAllConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				true
			).withPortletData(
				true
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				companyId, portletId, parameterMap);

		_assertPortletControlsMap(
			actualPortletControlsMap, true, true, false, true, true);
	}

	@Test
	public void testGetExportPortletControlsMapNoConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap = builder.withPortletConfiguration(
			false
		).withPortletConfigurationAll(
			false
		).withPortletData(
			false
		).build();

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				companyId, portletId, parameterMap);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetExportPortletControlsMapRootConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				companyId, portletId, parameterMap);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetExportPortletControlsMapRootConfigurationWithPortletConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String portletId = JournalPortletKeys.JOURNAL;

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				true
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				companyId, portletId, parameterMap);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetExportPortletControlsMapRootConfigurationWithPortletConfiguration2()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String portletId = JournalPortletKeys.JOURNAL;

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				true
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getExportPortletControlsMap(
				companyId, portletId, parameterMap);

		_assertPortletControlsMap(
			actualPortletControlsMap, true, true, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapAllConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				true
			).withPortletData(
				true
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, true, true, false, true, true);
	}

	@Test
	public void testGetImportPortletControlsMapAllConfigurationWithSummary()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				true
			).withPortletData(
				true
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				new ManifestSummary());

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapAllConfigurationWithSummary2()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				true
			).withPortletData(
				true
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = new ManifestSummary();

		Portlet testPortlet = new PortletImpl();

		testPortlet.setPortletId(portletId);

		manifestSummary.addDataPortlet(testPortlet, null);

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapAllConfigurationWithSummary3()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				true
			).withPortletData(
				true
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = new ManifestSummary();

		Portlet testPortlet = new PortletImpl();

		testPortlet.setPortletId(portletId);

		manifestSummary.addDataPortlet(testPortlet, new String[0]);

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, true, true, false, true, true);
	}

	@Test
	public void testGetImportPortletControlsMapNoConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = "test_portlet";

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap = builder.withPortletConfiguration(
			false
		).withPortletConfigurationAll(
			false
		).withPortletData(
			false
		).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfigurationWithManifest()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				new ManifestSummary());

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfigurationWithManifest2()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = new ManifestSummary();

		Portlet testPortlet = new PortletImpl();

		testPortlet.setPortletId(portletId);

		manifestSummary.addDataPortlet(testPortlet, null);

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfigurationWithManifest3()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		String portletId = JournalPortletKeys.JOURNAL;

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				false
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = new ManifestSummary();

		Portlet testPortlet = new PortletImpl();

		testPortlet.setPortletId(portletId);

		manifestSummary.addDataPortlet(testPortlet, new String[0]);

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfigurationWithPortletConfiguration()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String portletId = JournalPortletKeys.JOURNAL;

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				true
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		Element portletDataElement = null;
		ManifestSummary manifestSummary = null;

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, false, false, false, false, false);
	}

	@Test
	public void testGetImportPortletControlsMapRootConfigurationWithPortletConfiguration2()
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		String portletId = JournalPortletKeys.JOURNAL;

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		ExportImportTestParameterMapBuilder builder =
			new ExportImportTestParameterMapBuilder();

		Map<String, String[]> parameterMap =
			builder.withPortletArchivedSetupAll(
				true
			).withPortletConfiguration(
				true
			).withPortletConfigurationAll(
				false
			).withPortletData(
				false
			).withPortletSetupAll(
				true
			).withPortletUserPreferencesAll(
				true
			).build();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
				StringPool.UNDERLINE + rootPortletId,
			new String[] {"true"});

		Element portletDataElement = null;
		ManifestSummary manifestSummary = null;

		Map<String, Boolean> actualPortletControlsMap =
			ExportImportHelperUtil.getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		_assertPortletControlsMap(
			actualPortletControlsMap, true, true, false, false, false);
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectAllLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			_stagingGroup, layout.getPlid());

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(
					new long[] {
						layout.getLayoutId(), childLayout.getLayoutId()
					}));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertTrue(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectChildLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			_stagingGroup, layout.getPlid());

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[] {childLayout.getLayoutId()}));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertTrue(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(
			childLayout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectNoLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		LayoutTestUtil.addTypePortletLayout(_stagingGroup, layout.getPlid());

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[0]));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(0, selectedLayoutsJSONArray.length());
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectParentLayout()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		LayoutTestUtil.addTypePortletLayout(
			_stagingGroup.getGroupId(), "Child Layout", layout.getPlid());

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[] {layout.getLayoutId()}));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertFalse(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testValidateMissingReferences() throws Exception {
		String xml = replaceParameters(
			getContent("missing_references.txt"), getFileEntry());

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		zipWriter.addEntry("/manifest.xml", xml);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		PortletDataContext portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
				new HashMap<String, String[]>(), new TestUserIdStrategy(),
				zipReader);

		MissingReferences missingReferences =
			ExportImportHelperUtil.validateMissingReferences(
				portletDataContextImport);

		Map<String, MissingReference> dependencyMissingReferences =
			missingReferences.getDependencyMissingReferences();

		Map<String, MissingReference> weakMissingReferences =
			missingReferences.getWeakMissingReferences();

		Assert.assertEquals(
			dependencyMissingReferences.toString(), 2,
			dependencyMissingReferences.size());
		Assert.assertEquals(
			weakMissingReferences.toString(), 1, weakMissingReferences.size());

		FileUtil.delete(zipWriter.getFile());

		zipReader.close();
	}

	protected String getContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Scanner scanner = new Scanner(inputStream);

		scanner.useDelimiter("\\Z");

		return scanner.next();
	}

	protected FileEntry getFileEntry() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_stagingGroup.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		ThumbnailCapability thumbnailCapability =
			fileEntry.getRepositoryCapability(ThumbnailCapability.class);

		return thumbnailCapability.setLargeImageId(
			fileEntry, fileEntry.getFileEntryId());
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		return StringUtil.replace(
			content,
			new String[] {"[$GROUP_ID$]", "[$LIVE_GROUP_ID$]", "[$UUID$]"},
			new String[] {
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getGroupId()), fileEntry.getUuid()
			});
	}

	private void _assertPortletControlsMap(
		Map<String, Boolean> actualPortletControlsMap,
		boolean portletArchivedSetups, boolean portletConfiguration,
		boolean portletData, boolean portletSetup,
		boolean portletUserPreferences) {

		boolean actualPortletArchivedSetups = MapUtil.getBoolean(
			actualPortletControlsMap,
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean actualPortletConfiguration = MapUtil.getBoolean(
			actualPortletControlsMap,
			PortletDataHandlerKeys.PORTLET_CONFIGURATION);
		boolean actualPortletData = MapUtil.getBoolean(
			actualPortletControlsMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean actualPortletSetup = MapUtil.getBoolean(
			actualPortletControlsMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean actualPortletUserPreferences = MapUtil.getBoolean(
			actualPortletControlsMap,
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);

		Assert.assertEquals(portletArchivedSetups, actualPortletArchivedSetups);
		Assert.assertEquals(portletConfiguration, actualPortletConfiguration);
		Assert.assertEquals(portletData, actualPortletData);
		Assert.assertEquals(portletSetup, actualPortletSetup);
		Assert.assertEquals(
			portletUserPreferences, actualPortletUserPreferences);
	}

	@DeleteAfterTestRun
	private Group _liveGroup;

	@DeleteAfterTestRun
	private Group _stagingGroup;

	private class ExportImportTestParameterMapBuilder {

		public Map<String, String[]> build() {
			return _parameterMap;
		}

		public ExportImportTestParameterMapBuilder withPortletArchivedSetupAll(
			boolean portletArchivedSetupAll) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL,
				new String[] {Boolean.toString(portletArchivedSetupAll)});

			return this;
		}

		public ExportImportTestParameterMapBuilder withPortletConfiguration(
			boolean portletConfiguration) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_CONFIGURATION,
				new String[] {Boolean.toString(portletConfiguration)});

			return this;
		}

		public ExportImportTestParameterMapBuilder withPortletConfigurationAll(
			boolean portletConfigurationAll) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
				new String[] {Boolean.toString(portletConfigurationAll)});

			return this;
		}

		public ExportImportTestParameterMapBuilder withPortletData(
			boolean portletData) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.toString(portletData)});

			return this;
		}

		public ExportImportTestParameterMapBuilder withPortletSetupAll(
			boolean portletSetupAll) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_SETUP_ALL,
				new String[] {Boolean.toString(portletSetupAll)});

			return this;
		}

		public ExportImportTestParameterMapBuilder
			withPortletUserPreferencesAll(boolean portletUserPreferencesAll) {

			_parameterMap.put(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL,
				new String[] {Boolean.toString(portletUserPreferencesAll)});

			return this;
		}

		private final Map<String, String[]> _parameterMap = new HashMap<>();

	}

}