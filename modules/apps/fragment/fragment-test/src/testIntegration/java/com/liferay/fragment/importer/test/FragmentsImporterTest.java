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

package com.liferay.fragment.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentExportImportConstants;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class FragmentsImporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group = GroupTestUtil.addGroup();

		_user = TestPropsValues.getUser();

		_file = _generateZipFile(_FRAGMENTS_PATH);
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.delete(_file);
	}

	@Test
	public void testImportComponents() throws Exception {
		_importFragmentsByType(FragmentConstants.TYPE_COMPONENT);
	}

	@Test
	public void testImportFragments() throws Exception {
		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			fragmentCollections.toString(), 0, fragmentCollections.size());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_fragmentsImporter.importFile(
				_user.getUserId(), _group.getGroupId(), 0, _file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			fragmentCollections.toString(), 1, fragmentCollections.size());

		FragmentCollection fragmentCollection = fragmentCollections.get(0);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		Assert.assertTrue(!fragmentEntries.isEmpty());
	}

	@Test
	public void testImportFragmentsWithReservedNames() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_fragmentsImporter.importFile(
				_user.getUserId(), _group.getGroupId(), 0, _file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		FragmentCollection fragmentCollection = fragmentCollections.get(0);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		Stream<FragmentEntry> stream = fragmentEntries.stream();

		List<String> fragmentEntryNames = stream.map(
			fragmentEntry -> fragmentEntry.getFragmentEntryKey()
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(fragmentEntryNames.contains("resource"));
	}

	@Test
	public void testImportSections() throws Exception {
		_importFragmentsByType(FragmentConstants.TYPE_SECTION);
	}

	private void _addFragmentEntryType(JSONObject jsonObject) {
		int type = FragmentConstants.getTypeFromLabel(
			jsonObject.getString("type"));

		List<String> fragmentEntryKeys = _fragmentEntryTypes.computeIfAbsent(
			type, key -> new ArrayList<>());

		fragmentEntryKeys.add(jsonObject.getString("fragmentEntryKey"));
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, String path, String key)
		throws IOException {

		if (Validator.isNull(key)) {
			return;
		}

		String entryPath = path + StringPool.FORWARD_SLASH + key;

		String zipPath = StringUtil.removeSubstring(entryPath, _FRAGMENTS_PATH);

		URL url = _bundle.getEntry(entryPath);

		zipWriter.addEntry(zipPath, url.openStream());
	}

	private File _generateZipFile(String path) throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		URL collectionURL = _bundle.getEntry(
			path + FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG);

		zipWriter.addEntry(
			FragmentExportImportConstants.FILE_NAME_COLLECTION_CONFIG,
			collectionURL.openStream());

		Enumeration<URL> enumeration = _bundle.findEntries(
			path, FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG,
			true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(zipWriter, url);
			}

			zipWriter.finish();

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private void _importFragmentsByType(int type) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			_fragmentsImporter.importFile(
				_user.getUserId(), _group.getGroupId(), 0, _file, false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		FragmentCollection fragmentCollection = fragmentCollections.get(0);

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryLocalService.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());

		Stream<FragmentEntry> stream = fragmentEntries.stream();

		List<String> expectedFragmentsEntries = _fragmentEntryTypes.get(type);

		List<FragmentEntry> actualFragmentEntries = stream.filter(
			fragmentEntry -> fragmentEntry.getType() == type
		).collect(
			Collectors.toList()
		);

		Assert.assertEquals(
			actualFragmentEntries.toString(), expectedFragmentsEntries.size(),
			actualFragmentEntries.size());
	}

	private void _populateZipWriter(ZipWriter zipWriter, URL url)
		throws IOException, JSONException {

		String content = StringUtil.read(url.openStream());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		_addFragmentEntryType(jsonObject);

		String path = FileUtil.getPath(url.getPath());

		_addZipWriterEntry(
			zipWriter, path,
			FragmentExportImportConstants.FILE_NAME_FRAGMENT_CONFIG);
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("cssPath"));
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("htmlPath"));
		_addZipWriterEntry(zipWriter, path, jsonObject.getString("jsPath"));
		_addZipWriterEntry(
			zipWriter, path, jsonObject.getString("thumbnailPath"));
	}

	private static final String _FRAGMENTS_PATH =
		"com/liferay/fragment/dependencies/fragments/";

	private Bundle _bundle;
	private File _file;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private final Map<Integer, List<String>> _fragmentEntryTypes =
		new HashMap<>();

	@Inject
	private FragmentsImporter _fragmentsImporter;

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}