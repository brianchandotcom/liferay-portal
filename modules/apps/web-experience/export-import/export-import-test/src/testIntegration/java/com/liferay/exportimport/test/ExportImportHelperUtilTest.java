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
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

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
 * @author Peter Borkuti
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
	public void testGetSelectedLayoutsJSONSelectAllLayouts() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addLayout(
			_stagingGroup, layout.getPlid());

		long[] selectedLayoutIds =
			new long[] {layout.getLayoutId(), childLayout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(selectedLayoutIds));

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			selectedLayoutsJSON);

		Assert.assertEquals(1, selectedLayoutsJSONArray.length());

		JSONObject layoutJSONObject = selectedLayoutsJSONArray.getJSONObject(0);

		Assert.assertTrue(layoutJSONObject.getBoolean("includeChildren"));
		Assert.assertEquals(layout.getPlid(), layoutJSONObject.getLong("plid"));
	}

	@Test
	public void testGetSelectedLayoutsJSONSelectChildLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		Layout childLayout = LayoutTestUtil.addLayout(
			_stagingGroup, layout.getPlid());

		long[] selectedLayoutIds = new long[] {childLayout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(selectedLayoutIds));

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
		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		LayoutTestUtil.addLayout(_stagingGroup, layout.getPlid());

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

		Layout layout = LayoutTestUtil.addLayout(_stagingGroup);

		LayoutTestUtil.addLayout(
			_stagingGroup.getGroupId(), "Child Layout", layout.getPlid());

		long[] selectedLayoutIds = new long[] {layout.getLayoutId()};

		String selectedLayoutsJSON =
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				_stagingGroup.getGroupId(), false,
				StringUtil.merge(selectedLayoutIds));

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
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		ThumbnailCapability thumbnailCapability =
			fileEntry.getRepositoryCapability(ThumbnailCapability.class);

		fileEntry = thumbnailCapability.setLargeImageId(
			fileEntry, fileEntry.getFileEntryId());

		return fileEntry;
	}

	protected String replaceParameters(String content, FileEntry fileEntry) {
		content = StringUtil.replace(
			content,
			new String[] {"[$GROUP_ID$]", "[$LIVE_GROUP_ID$]", "[$UUID$]"},
			new String[] {
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getGroupId()), fileEntry.getUuid()
			});

		return content;
	}

	@DeleteAfterTestRun
	private Group _liveGroup;

	@DeleteAfterTestRun
	private Group _stagingGroup;

}