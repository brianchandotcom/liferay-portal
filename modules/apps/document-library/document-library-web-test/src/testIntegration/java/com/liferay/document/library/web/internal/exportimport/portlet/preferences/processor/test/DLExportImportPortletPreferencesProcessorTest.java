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

package com.liferay.document.library.web.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.controller.PortletExportController;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.test.util.RatingsTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DLExportImportPortletPreferencesProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(javax.portlet.name=");
		sb.append(DLPortletKeys.DOCUMENT_LIBRARY);
		sb.append(")(objectClass=");
		sb.append(ExportImportPortletPreferencesProcessor.class.getName());
		sb.append("))");

		_serviceTracker = registry.trackServices(
			registry.getFilter(sb.toString()));

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layout,
			DLPortletKeys.DOCUMENT_LIBRARY, "column-1",
			new HashMap<String, String[]>());

		_portletDataContextExport =
			ExportImportTestUtil.getExportPortletDataContext(
				_group.getGroupId());

		_portletDataContextExport.setPlid(_layout.getPlid());
		_portletDataContextExport.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);

		_portletDataContextImport =
			ExportImportTestUtil.getImportPortletDataContext(
				_group.getGroupId());

		_portletDataContextImport.setPlid(_layout.getPlid());
		_portletDataContextImport.setPortletId(DLPortletKeys.DOCUMENT_LIBRARY);

		_exportImportPortletPreferencesProcessor = _serviceTracker.getService();

		_portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				_layout, DLPortletKeys.DOCUMENT_LIBRARY);

		_portletPreferences.setValue("selectionStyle", "manual");
	}

	@Test
	public void testExportDLFileEntryIdWithComments() throws Exception {
		FileEntry fileEntry = _addDLFileEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_portletPreferences.setValue(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		_portletPreferences.store();

		User user = TestPropsValues.getUser();

		long commentPrimaryKey = CommentManagerUtil.addComment(
			user.getUserId(), _group.getGroupId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), RandomTestUtil.randomString(),
			new IdentityServiceContextFunction(
				ServiceContextTestUtil.getServiceContext()));

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		_portletExportController.exportPortlet(
			_portletDataContextExport, _layout.getPlid(), rootElement, false,
			false, true, true, false);

		Map<String, String[]> parameterMap =
			_portletDataContextExport.getParameterMap();

		Assert.assertEquals(
			parameterMap.get(PortletDataHandlerKeys.COMMENTS)[0],
			Boolean.TRUE.toString());

		Set<String> primaryKeys = _portletDataContextExport.getPrimaryKeys();

		Assert.assertTrue(
			primaryKeys.toString(),
			primaryKeys.contains(
				StringBundler.concat(
					String.class.getName(), StringPool.POUND,
					DLFileEntry.class.getName(), StringPool.POUND,
					fileEntry.getFileEntryId())));

		Assert.assertTrue(
			primaryKeys.toString(),
			primaryKeys.contains(
				StringBundler.concat(
					String.class.getName(),
					"#com.liferay.message.boards.model.MBMessage#",
					commentPrimaryKey)));
	}

	@Test
	public void testExportDLFileEntryIdWithRatings() throws Exception {
		FileEntry fileEntry = _addDLFileEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_portletPreferences.setValue(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		_portletPreferences.store();

		RatingsEntry ratingsEntry = RatingsTestUtil.addEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		_portletExportController.exportPortlet(
			_portletDataContextExport, _layout.getPlid(), rootElement, false,
			false, true, true, false);

		Map<String, String[]> parameterMap =
			_portletDataContextExport.getParameterMap();

		Assert.assertEquals(
			parameterMap.get(PortletDataHandlerKeys.COMMENTS)[0],
			Boolean.TRUE.toString());

		Set<String> primaryKeys = _portletDataContextExport.getPrimaryKeys();

		Assert.assertTrue(
			primaryKeys.toString(),
			primaryKeys.contains(
				StringBundler.concat(
					String.class.getName(), StringPool.POUND,
					DLFileEntry.class.getName(), StringPool.POUND,
					fileEntry.getFileEntryId())));

		Assert.assertTrue(
			primaryKeys.toString(),
			primaryKeys.contains(
				StringBundler.concat(
					String.class.getName(), StringPool.POUND,
					RatingsEntry.class.getName(), StringPool.POUND,
					ratingsEntry.getEntryId())));
	}

	@Test
	public void testProcessExportPortletPreferencesDLFileEntryId()
		throws Exception {

		FileEntry fileEntry = _addDLFileEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_portletPreferences.setValue(
			"fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

		_portletPreferences.store();

		PortletPreferences exportedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processExportPortletPreferences(
					_portletDataContextExport, _portletPreferences);

		PortletPreferences importedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processImportPortletPreferences(
					_portletDataContextImport, exportedPortletPreferences);

		String importedfileEntryId = importedPortletPreferences.getValue(
			"fileEntryId", "");

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(importedfileEntryId));
	}

	private FileEntry _addDLFileEntry(String fileName, String content)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, content.getBytes(), null, null,
			serviceContext);
	}

	private static ServiceTracker
		<ExportImportPortletPreferencesProcessor,
		 ExportImportPortletPreferencesProcessor> _serviceTracker;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private ExportImportPortletPreferencesProcessor
		_exportImportPortletPreferencesProcessor;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;

	@Inject
	private PortletExportController _portletExportController;

	private PortletPreferences _portletPreferences;

}