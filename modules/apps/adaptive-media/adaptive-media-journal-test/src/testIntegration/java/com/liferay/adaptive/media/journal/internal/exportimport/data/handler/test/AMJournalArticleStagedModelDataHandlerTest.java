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

package com.liferay.adaptive.media.journal.internal.exportimport.data.handler.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class AMJournalArticleStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "600");
		properties.put("max-width", "800");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			stagingGroup.getCompanyId(), StringUtil.randomString(),
			StringUtil.randomString(), _AM_JOURNAL_CONFIG_UUID, properties);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			stagingGroup.getCompanyId(), _AM_JOURNAL_CONFIG_UUID);

		super.tearDown();
	}

	@Test
	public void testExportImportContentWithMultipleDynamicReferences()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry1 = _addImageFileEntry(serviceContext);
		FileEntry fileEntry2 = _addImageFileEntry(serviceContext);

		String content = _getDynamicContent(fileEntry1, fileEntry2);

		JournalArticle journalArticle = _addJournalArticle(
			content, _getServiceContext());

		exportImportStagedModel(journalArticle);

		JournalArticle importedJournalArticle = (JournalArticle)getStagedModel(
			journalArticle.getUuid(), liveGroup);

		_assertXMLEquals(
			_getExpectedDynamicContent(fileEntry1, fileEntry2),
			importedJournalArticle.getContent());
	}

	@Test
	public void testExportImportContentWithMultipleStaticReferences()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry1 = _addImageFileEntry(serviceContext);
		FileEntry fileEntry2 = _addImageFileEntry(serviceContext);

		String content = _getStaticContent(fileEntry1, fileEntry2);

		JournalArticle journalArticle = _addJournalArticle(
			content, serviceContext);

		exportImportStagedModel(journalArticle);

		JournalArticle importedJournalArticle = (JournalArticle)getStagedModel(
			journalArticle.getUuid(), liveGroup);

		_assertXMLEquals(
			_getExpectedStaticContent(fileEntry1, fileEntry2),
			importedJournalArticle.getContent());
	}

	@Test
	public void testExportImportContentWithNoReferences() throws Exception {
		JournalArticle journalArticle = _addJournalArticle(
			_getContent(StringPool.BLANK), _getServiceContext());

		exportImportStagedModel(journalArticle);

		JournalArticle importedJournalArticle = (JournalArticle)getStagedModel(
			journalArticle.getUuid(), liveGroup);

		Assert.assertEquals(
			journalArticle.getContent(), importedJournalArticle.getContent());
	}

	@Test
	public void testExportSucceedsWithInvalidReferences() throws Exception {
		int invalidFileEntryId = 9999999;

		String content = _getContent(_getImgTag(invalidFileEntryId));

		JournalArticle journalArticle = _addJournalArticle(
			content, _getServiceContext());

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, journalArticle);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry = _addImageFileEntry(serviceContext);

		return _addJournalArticle(_getImgTag(fileEntry), serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry = _addImageFileEntry(serviceContext);

		return Collections.singletonList(
			_addJournalArticle(_getImgTag(fileEntry), serviceContext));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _journalArticleLocalService.getJournalArticleByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "image.jpg"), serviceContext);
	}

	private JournalArticle _addJournalArticle(
			String content, ServiceContext serviceContext)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "text_area",
			new Locale[] {LocaleUtil.getSiteDefault()},
			LocaleUtil.getSiteDefault());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), JournalArticle.class.getName(), "0",
			ddmForm, LocaleUtil.getSiteDefault(),
			ServiceContextTestUtil.getServiceContext());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(),
			PortalUtil.getClassNameId(
				com.liferay.dynamic.data.mapping.model.DDMStructure.class),
			ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		JournalFolder journalFolder = _journalFolderLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), "This is a test folder.",
			serviceContext);

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getSiteDefault(), "Test Article");

		return _journalArticleLocalService.addArticle(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			journalFolder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, 0, titleMap, null, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), null, 1, 1, 1965, 0, 0, 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, true, false, null, null, null, null,
			serviceContext);
	}

	private void _assertXMLEquals(String expectedXML, String actualXML)
		throws Exception {

		Document actualDocument = SAXReaderUtil.read(actualXML);
		Document expectedDocument = SAXReaderUtil.read(expectedXML);

		AssertUtils.assertEqualsIgnoreCase(
			expectedDocument.formattedString(),
			actualDocument.formattedString());
	}

	private String _getContent(String html) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element dynamicElementElement = rootElement.addElement(
			"dynamic-element");

		dynamicElementElement.addAttribute("name", "content");
		dynamicElementElement.addAttribute("type", "text_area");

		Element element = dynamicElementElement.addElement("dynamic-content");

		element.addCDATA(html);

		return document.asXML();
	}

	private String _getDynamicContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length);

		for (FileEntry fileEntry : fileEntries) {
			sb.append(_getImgTag(fileEntry));
			sb.append(StringPool.NEW_LINE);
		}

		return _getContent(sb.toString());
	}

	private String _getExpectedDynamicContent(FileEntry... fileEntries)
		throws Exception {

		List<FileEntry> importedFileEntries = new ArrayList<>();

		for (FileEntry fileEntry : fileEntries) {
			importedFileEntries.add(
				_dlAppLocalService.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), liveGroup.getGroupId()));
		}

		return _getDynamicContent(
			importedFileEntries.toArray(new FileEntry[0]));
	}

	private String _getExpectedStaticContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length * 2);

		for (FileEntry fileEntry : fileEntries) {
			FileEntry importedFileEntry =
				_dlAppLocalService.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), liveGroup.getGroupId());

			sb.append(
				_amImageHTMLTagFactory.create(
					_getImgTag(importedFileEntry), importedFileEntry));

			sb.append(StringPool.NEW_LINE);
		}

		return _getContent(sb.toString());
	}

	private String _getImgTag(FileEntry fileEntry) throws Exception {
		return _getImgTag(fileEntry.getFileEntryId());
	}

	private String _getImgTag(long fileEntryId) throws Exception {
		return String.format(
			"<img alt=\"alt\" class=\"a class\" src=\"theURL\" " +
				"data-fileentryid=\"%s\" />",
			fileEntryId);
	}

	private String _getPictureTag(FileEntry fileEntry) throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("<picture data-fileentryid=\"");
		sb.append(fileEntry.getFileEntryId());
		sb.append("\">");
		sb.append("<source></source>");
		sb.append(_getImgTag(fileEntry));
		sb.append("</picture>");

		return sb.toString();
	}

	private ServiceContext _getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(
			stagingGroup.getGroupId(), TestPropsValues.getUserId());
	}

	private String _getStaticContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length);

		for (FileEntry fileEntry : fileEntries) {
			sb.append(_getPictureTag(fileEntry));
			sb.append(StringPool.NEW_LINE);
		}

		return _getContent(sb.toString());
	}

	private static final String _AM_JOURNAL_CONFIG_UUID = "journal-config";

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

}