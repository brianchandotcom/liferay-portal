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

package com.liferay.dynamic.data.mapping.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.helper.DDMFormInstanceTestHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltan Csaszi
 */
@RunWith(Arquillian.class)
public class DDMFormValuesExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(model.class.name=");
		sb.append("com.liferay.dynamic.data.mapping.storage.DDMFormValues)");
		sb.append("(objectClass=");
		sb.append(ExportImportContentProcessor.class.getName());
		sb.append("))");

		Filter filter = registry.getFilter(sb.toString());

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(_liveGroup);

		_stagingGroup = _liveGroup.getStagingGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_stagingGroup.getGroupId(), TestPropsValues.getUserId());

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		_portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
				new HashMap<>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextExport.setExportDataRootElement(rootElement);

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
				new HashMap<>(), new TestUserIdStrategy(), testReaderWriter);

		_portletDataContextImport.setImportDataRootElement(rootElement);

		Element missingReferencesElement = rootElement.addElement(
			"missing-references");

		_portletDataContextExport.setMissingReferencesElement(
			missingReferencesElement);

		_portletDataContextImport.setMissingReferencesElement(
			missingReferencesElement);

		_portletDataContextImport.setSourceGroupId(_stagingGroup.getGroupId());

		rootElement.addElement("entry");

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, serviceContext);

		ThumbnailCapability thumbnailCapability =
			_fileEntry.getRepositoryCapability(ThumbnailCapability.class);

		_fileEntry = thumbnailCapability.setLargeImageId(
			_fileEntry, _fileEntry.getFileEntryId());

		_formInstance = createFormInstanceWithDocLib(_stagingGroup, _fileEntry);

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			JournalArticle.class);

		DDMStructure structure = _formInstance.getStructure();

		DDMForm ddmForm = _formInstance.getDDMForm();

		structure.setDDMForm(ddmForm);

		structure.setClassNameId(classNameId);

		_ddmStructureLocalService.updateDDMStructure(structure);

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_stagingGroup.getGroupId(), structure.getStructureId(),
			classNameId);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		StringBundler sb = new StringBundler(16);

		sb.append("<?xml version=\"1.0\"?>  ");
		sb.append("<root available-locales=\"en_US\" ");
		sb.append("default-locale=\"en_US\"> \t");
		sb.append("<dynamic-element name=\"DocumentsAndMedia9t17\" ");
		sb.append("type=\"document_library\" index-type=\"keyword\" ");
		sb.append("instance-id=\"lvsi\"> \t\t");
		sb.append("<dynamic-content language-id=\"en_US\">");
		sb.append("<![CDATA[{\"classPK\":\"");
		sb.append(_fileEntry.getFileEntryId());
		sb.append("\",\"groupId\":\"");
		sb.append(_fileEntry.getGroupId());
		sb.append("\",\"title\":\"");
		sb.append(_fileEntry.getTitle());
		sb.append("\",\"type\":\"document\",\"uuid\":\"");
		sb.append(_fileEntry.getUuid());
		sb.append("\"}]]></dynamic-content> \t</dynamic-element> </root>");

		String content = sb.toString();

		_journalArticle = _journalArticleLocalService.addArticle(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, nameMap, nameMap,
			content, structure.getStructureKey(), _ddmTemplate.getTemplateKey(),
			serviceContext);

		_exportImportContentProcessor = _serviceTracker.getService();
	}

	@After
	public void tearDown() throws Exception {
		_journalArticleLocalService.deleteArticle(_journalArticle);
		_ddmTemplateLocalService.deleteDDMTemplate(_ddmTemplate);
		_ddmStructureLocalService.deleteDDMStructure(_ddmStructure);
	}

	@Test
	public void testReplaceExportImportContentReferences() throws Exception {
		DDMFormValues settingsDDMFormValues =
			_formInstance.getSettingsDDMFormValues();

		DDMFormValues ddmFormValues = new DDMFormValues(
			_formInstance.getDDMForm());

		for (DDMFormFieldValue ddmFormFieldValue :
				settingsDDMFormValues.getDDMFormFieldValues()) {

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		DDMFormValues exportDDMFormValues =
			_exportImportContentProcessor.replaceExportContentReferences(
				_portletDataContextExport, _journalArticle, ddmFormValues, true,
				true);

		Map<Long, Long> groupIds =
			(Map<Long, Long>)_portletDataContextImport.getNewPrimaryKeysMap(
				Group.class);

		groupIds.put(_stagingGroup.getGroupId(), _liveGroup.getGroupId());

		Map<Long, Long> classPKs =
			(Map<Long, Long>)_portletDataContextImport.getNewPrimaryKeysMap(
				DLFileEntry.class);

		long fileEntryId = _fileEntry.getPrimaryKey();

		DLFileEntry newDLFileEntry = _dlFileEntryLocalService.copyFileEntry(
			TestPropsValues.getUserId(), _liveGroup.getGroupId(),
			_liveGroup.getGroupId(), fileEntryId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, new ServiceContext());

		newDLFileEntry.setUuid(_fileEntry.getUuid());

		_dlFileEntryLocalService.deleteDLFileEntry(fileEntryId);

		_dlFileEntryLocalService.updateDLFileEntry(newDLFileEntry);

		classPKs.put(fileEntryId, newDLFileEntry.getPrimaryKey());

		_exportImportContentProcessor.replaceImportContentReferences(
			_portletDataContextImport, _journalArticle, exportDDMFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			exportDDMFormValues.getDDMFormFieldValues();

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			value.getString(LocaleUtil.US));

		long newDLFileEntryId = newDLFileEntry.getFileEntryId();

		_dlFileEntryLocalService.deleteDLFileEntry(newDLFileEntry);

		Assert.assertEquals(newDLFileEntryId, jsonObject.getLong("classPK"));
	}

	protected DDMFormInstance createFormInstanceWithDocLib(
			Group group, FileEntry fileEntry)
		throws Exception {

		_ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDMFormInstance.class.getName());

		DDMFormInstanceTestHelper ddmFormInstanceTestHelper =
			new DDMFormInstanceTestHelper(group);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceTestHelper.addDDMFormInstance(
				_ddmStructure, fileEntry);

		return ddmFormInstance;
	}

	private static ServiceTracker
		<ExportImportContentProcessor, ExportImportContentProcessor>
			_serviceTracker;

	private DDMStructure _ddmStructure;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	private DDMTemplate _ddmTemplate;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private ExportImportContentProcessor<DDMFormValues>
		_exportImportContentProcessor;
	private FileEntry _fileEntry;
	private DDMFormInstance _formInstance;
	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private Group _liveGroup;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private Group _stagingGroup;

}