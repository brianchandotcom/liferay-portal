/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.constants.ExportImportContentProcessorConstants;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.test.util.TestReaderWriter;
import com.liferay.exportimport.test.util.TestUserIdStrategy;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class DLReferencesExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_externalGroup = GroupTestUtil.addGroup();

		_sourceGroup = GroupTestUtil.addGroup();

		_targetGroup = GroupTestUtil.addGroup();

		_fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup.getGroupId(), TestPropsValues.getUserId()));

		ThumbnailCapability thumbnailCapability =
			_fileEntry.getRepositoryCapability(ThumbnailCapability.class);

		_fileEntry = thumbnailCapability.setLargeImageId(
			_fileEntry, _fileEntry.getFileEntryId());

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		_portletDataContextExport =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				_sourceGroup.getCompanyId(), _sourceGroup.getGroupId(),
				new HashMap<>(),
				new Date(System.currentTimeMillis() - Time.HOUR), new Date(),
				testReaderWriter);

		Document document = SAXReaderUtil.createDocument();

		Element manifestRootElement = document.addElement("root");

		manifestRootElement.addElement("header");

		testReaderWriter.addEntry("/manifest.xml", document.asXML());

		Element rootElement = SAXReaderUtil.createElement("root");

		_portletDataContextImport =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				_targetGroup.getCompanyId(), _targetGroup.getGroupId(),
				new HashMap<>(), new TestUserIdStrategy(), testReaderWriter);

		_portletDataContextImport.setExportImportProcessId(
			String.valueOf(RandomTestUtil.randomLong()));

		_portletDataContextImport.setImportDataRootElement(rootElement);

		_portletDataContextImport.setSourceGroupId(_sourceGroup.getGroupId());
	}

	@Test
	public void testExportDLReferences() throws Exception {
		_portletDataContextExport.setZipWriter(new TestReaderWriter());

		String content = _replaceParameters(
			_getContent("dl_references.txt"), _fileEntry);

		_dlReferencesExportImportContentProcessor.validateContentReferences(
			_sourceGroup.getGroupId(), content);

		List<String> urls = _getURLs(content);

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					content, _portletDataContextExport);

		for (String url : urls) {
			Assert.assertFalse(content, content.contains(url));
		}

		TestReaderWriter testReaderWriter =
			(TestReaderWriter)_portletDataContextExport.getZipWriter();

		_assertEmpty(testReaderWriter.getBinaryEntries());
		_assertEmpty(testReaderWriter.getEntries());
	}

	@Test
	public void testExportDLReferencesFriendlyURL() throws Exception {
		_portletDataContextExport.setZipWriter(new TestReaderWriter());

		_fileEntry = DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), _fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			_fileEntry.getTitle(), _fileEntry.getTitle(), StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.AUTOMATIC,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup.getGroupId(), TestPropsValues.getUserId()));

		String content = _replaceParameters(
			_getContent("dl_references_file_friendly_urls.txt"), _fileEntry);

		_dlReferencesExportImportContentProcessor.validateContentReferences(
			_sourceGroup.getGroupId(), content);

		List<String> urls = _getURLs(content);

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					content, _portletDataContextExport);

		for (String url : urls) {
			Assert.assertFalse(content, content.contains(url));
		}

		TestReaderWriter testReaderWriter =
			(TestReaderWriter)_portletDataContextExport.getZipWriter();

		_assertEmpty(testReaderWriter.getBinaryEntries());
		_assertEmpty(testReaderWriter.getEntries());
	}

	@Test
	public void testImportDLReferences1() throws Exception {
		_testImportDLReferences(false);
	}

	@Test
	public void testImportDLReferences2() throws Exception {
		_testImportDLReferences(true);
	}

	@Test
	public void testImportDLReferencesFileEntryDeleted() throws Exception {
		DLAppHelperLocalServiceUtil.deleteFileEntry(_fileEntry);

		_testImportDLReferences(false);
	}

	@Test
	public void testImportDLReferencesFileEntryInTrash1() throws Exception {
		DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _fileEntry);

		_testImportDLReferences(false);
	}

	@Test
	public void testImportDLReferencesFileEntryInTrash2() throws Exception {
		DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _fileEntry);

		_testImportDLReferences(true);
	}

	@Test
	public void testImportDLReferencesFriendlyURLDeletingBefore()
		throws Exception {

		_testImportDLReferencesFriendlyURL(true);
	}

	@Test
	public void testImportDLReferencesFriendlyURLWithoutDeletingBefore()
		throws Exception {

		_testImportDLReferencesFriendlyURL(false);
	}

	private void _assertEmpty(List<String> list) {
		Assert.assertTrue(list.isEmpty());
	}

	private String _duplicateLinesWithParamNames(
		String content, String[] findParams, String[] addParams) {

		if (StringUtil.indexOfAny(content, findParams) <= -1) {
			return content;
		}

		List<String> urls = ListUtil.fromArray(StringUtil.splitLines(content));

		List<String> outURLs = new ArrayList<>();

		for (String url : urls) {
			outURLs.add(url);

			if (StringUtil.indexOfAny(url, findParams) > -1) {
				outURLs.add(StringUtil.replace(url, findParams, addParams));
			}
		}

		return StringUtil.merge(outURLs, StringPool.NEW_LINE);
	}

	private String _extractValidContent(String content) {
		List<String> lines = ListUtil.fromArray(StringUtil.splitLines(content));
		List<String> validLines = new ArrayList<>();

		for (String line : lines) {
			if (Validator.isNotNull(line) && !line.endsWith(StringPool.COLON)) {
				validLines.add(line);
			}
		}

		return StringUtil.merge(validLines, StringPool.NEW_LINE);
	}

	private String _getContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		Scanner scanner = new Scanner(inputStream);

		scanner.useDelimiter("\\Z");

		return scanner.next();
	}

	private List<String> _getURLs(String content) {
		List<String> urls = new ArrayList<>();

		Matcher matcher = _pattern.matcher(StringPool.BLANK);

		String[] lines = StringUtil.split(content, StringPool.NEW_LINE);

		for (String line : lines) {
			matcher.reset(line);

			if (matcher.find()) {
				urls.add(line);
			}
		}

		return urls;
	}

	private String _replaceExternalGroupFriendlyURLs(String content) {
		return _duplicateLinesWithParamNames(
			content, _GROUP_FRIENDLY_URL_VARIABLES,
			_EXTERNAL_GROUP_FRIENDLY_URL_VARIABLES);
	}

	private String _replaceMultiLocaleLayoutFriendlyURLs(String content) {
		return _duplicateLinesWithParamNames(
			content, _MULTI_LOCALE_LAYOUT_VARIABLES,
			_NONDEFAULT_MULTI_LOCALE_LAYOUT_VARIABLES);
	}

	private String _replaceParameters(String content, FileEntry fileEntry) {
		Company company = CompanyLocalServiceUtil.fetchCompany(
			fileEntry.getCompanyId());

		content = _replaceExternalGroupFriendlyURLs(content);
		content = _replaceMultiLocaleLayoutFriendlyURLs(content);

		content = StringUtil.replace(
			content,
			new String[] {
				"[$BLOG_ENTRY_DISPLAY_SERVLET_MAPPING$]",
				"[$CANONICAL_URL_SEPARATOR$]", "[$CONTROL_PANEL_FRIENDLY_URL$]",
				"[$CONTROL_PANEL_LAYOUT_FRIENDLY_URL$]",
				"[$DL_ENTRY_DISPLAY_SERVLET_MAPPING$]",
				"[$EXTERNAL_GROUP_FRIENDLY_URL$]",
				"[$EXTERNAL_PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$EXTERNAL_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$FILE_ENTRY_FRIENDLY_URL$]", "[$FILE_NAME$]",
				"[$FRIENDLY_URL_SEPARATOR$]", "[$GROUP_FRIENDLY_URL$]",
				"[$GROUP_ID$]", "[$GROUP_NAME$]",
				"[$GROUP_PRIVATE_PAGES_VIRTUAL_HOST$]",
				"[$GROUP_PUBLIC_PAGES_VIRTUAL_HOST$]", "[$IMAGE_ID$]",
				"[$LIVE_GROUP_FRIENDLY_URL$]", "[$LIVE_GROUP_ID$]",
				"[$LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$NON_DEFAULT_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$NONREPLACEABLE_PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$NONREPLACEABLE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
				"[$PATH_CONTEXT$]", "[$PATH_FRIENDLY_URL_PRIVATE_GROUP$]",
				"[$PATH_FRIENDLY_URL_PRIVATE_USER$]",
				"[$PATH_FRIENDLY_URL_PUBLIC$]",
				"[$PRIVATE_LAYOUT_FRIENDLY_URL$]",
				"[$PUBLIC_LAYOUT_FRIENDLY_URL$]", "[$TITLE$]", "[$UUID$]",
				"[$WEB_CONTENT_DISPLAY_SERVLET_MAPPING$]", "[$WEB_ID$]"
			},
			new String[] {
				FriendlyURLResolverConstants.URL_SEPARATOR_X_BLOGS_ENTRY,
				VirtualLayoutConstants.CANONICAL_URL_SEPARATOR,
				GroupConstants.CONTROL_PANEL_FRIENDLY_URL,
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL,
				FriendlyURLResolverConstants.URL_SEPARATOR_X_FILE_ENTRY,
				_externalGroup.getFriendlyURL(), "", "",
				FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
					fileEntry.getTitle()),
				fileEntry.getFileName(), Portal.FRIENDLY_URL_SEPARATOR,
				_sourceGroup.getFriendlyURL(),
				String.valueOf(fileEntry.getGroupId()),
				StringUtil.removeFirst(
					_sourceGroup.getFriendlyURL(), StringPool.SLASH),
				"", "", String.valueOf(fileEntry.getFileEntryId()),
				_targetGroup.getFriendlyURL(),
				String.valueOf(_targetGroup.getGroupId()), "", "", "", "", "",
				"", PortalUtil.getPathContext(),
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING,
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING, "", "",
				fileEntry.getTitle(), fileEntry.getUuid(),
				FriendlyURLResolverConstants.URL_SEPARATOR_X_JOURNAL_ARTICLE,
				company.getWebId()
			});

		if (!content.contains("[$TIMESTAMP")) {
			return _extractValidContent(content);
		}

		return _replaceTimestampParameters(content);
	}

	private String _replaceTimestampParameters(String content) {
		List<String> urls = ListUtil.fromArray(StringUtil.splitLines(content));

		String timestampParameter = "t=123456789";

		String parameters1 = timestampParameter + "&width=100&height=100";
		String parameters2 = "width=100&" + timestampParameter + "&height=100";
		String parameters3 = "width=100&height=100&" + timestampParameter;
		String parameters4 = StringBundler.concat(
			timestampParameter, "?", timestampParameter,
			"&width=100&height=100");

		List<String> outURLs = new ArrayList<>();

		for (String url : urls) {
			if (Validator.isNotNull(url) && !url.contains("[$TIMESTAMP") &&
				!url.endsWith(StringPool.COLON)) {

				outURLs.add(url);

				continue;
			}

			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters1, "?" + parameters1}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters2, "?" + parameters2}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {"&" + parameters3, "?" + parameters3}));
			outURLs.add(
				StringUtil.replace(
					url, new String[] {"[$TIMESTAMP$]", "[$TIMESTAMP_ONLY$]"},
					new String[] {StringPool.BLANK, "?" + parameters4}));
		}

		return StringUtil.merge(outURLs, StringPool.NEW_LINE);
	}

	private void _testImportDLReferences(boolean deleteFileEntryBeforeImport)
		throws Exception {

		String content = _replaceParameters(
			_getContent("dl_references.txt"), _fileEntry);

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					content, _portletDataContextExport);

		_portletDataContextImport.setScopeGroupId(_fileEntry.getGroupId());

		if (deleteFileEntryBeforeImport) {
			DLAppLocalServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		content =
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					content, _portletDataContextImport);

		Assert.assertFalse(content, content.contains("[$dl-reference="));
	}

	private void _testImportDLReferencesFriendlyURL(
			boolean deleteFileEntryBeforeImport)
		throws Exception {

		_fileEntry = DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), _fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			_fileEntry.getTitle(), _fileEntry.getTitle(), StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.AUTOMATIC,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup.getGroupId(), TestPropsValues.getUserId()));

		String content = _replaceParameters(
			_getContent("dl_references_file_friendly_urls.txt"), _fileEntry);

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					content, _portletDataContextExport);

		_portletDataContextImport.setScopeGroupId(_fileEntry.getGroupId());

		if (deleteFileEntryBeforeImport) {
			DLAppLocalServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		content =
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					content, _portletDataContextImport);

		Assert.assertFalse(content, content.contains("[$dl-reference="));
	}

	private static final String[] _EXTERNAL_GROUP_FRIENDLY_URL_VARIABLES = {
		"[$EXTERNAL_GROUP_FRIENDLY_URL$]",
		"[$EXTERNAL_PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$EXTERNAL_PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final String[] _GROUP_FRIENDLY_URL_VARIABLES = {
		"[$GROUP_FRIENDLY_URL$]", "[$PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final String[] _MULTI_LOCALE_LAYOUT_VARIABLES = {
		"[$LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
		"[$PRIVATE_LAYOUT_FRIENDLY_URL$]", "[$PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final String[] _NONDEFAULT_MULTI_LOCALE_LAYOUT_VARIABLES = {
		"[$NON_DEFAULT_LIVE_PUBLIC_LAYOUT_FRIENDLY_URL$]",
		"[$NON_DEFAULT_PRIVATE_LAYOUT_FRIENDLY_URL$]",
		"[$NON_DEFAULT_PUBLIC_LAYOUT_FRIENDLY_URL$]"
	};

	private static final Pattern _pattern = Pattern.compile(
		"href=|url\\(|\\{|\\[");

	@Inject(
		filter = ExportImportContentProcessorConstants.CONTENT_PROCESSOR_TYPE + "=" + ExportImportContentProcessorConstants.DOCUMENT_LIBRARY_REFERENCES
	)
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@DeleteAfterTestRun
	private Group _externalGroup;

	private FileEntry _fileEntry;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;

	@DeleteAfterTestRun
	private Group _sourceGroup;

	@DeleteAfterTestRun
	private Group _targetGroup;

}