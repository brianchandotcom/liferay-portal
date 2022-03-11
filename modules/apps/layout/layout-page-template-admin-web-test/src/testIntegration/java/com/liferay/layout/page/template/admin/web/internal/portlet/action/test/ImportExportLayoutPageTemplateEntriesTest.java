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

package com.liferay.layout.page.template.admin.web.internal.portlet.action.test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateExportImportConstants;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporterResultEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ImportExportLayoutPageTemplateEntriesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_bundle = FrameworkUtil.getBundle(getClass());

		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group1.getCompanyId());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerBackgroundFragmentImage()
		throws Exception {

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"container/background_fragment_image/expected", numberValuesMap,
			null);
		File inputFile = _generateZipFile(
			"container/background_fragment_image/input", numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerBackgroundImage()
		throws Exception {

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"container/background_image/expected", numberValuesMap, null);
		File inputFile = _generateZipFile(
			"container/background_image/input", numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerDefault()
		throws Exception {

		File expectedFile = _generateZipFile(
			"container/default/expected", null, null);
		File inputFile = _generateZipFile(
			"container/default/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerEmpty()
		throws Exception {

		File expectedFile = _generateZipFile(
			"container/empty/expected", null, null);
		File inputFile = _generateZipFile("container/empty/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLayout()
		throws Exception {

		File expectedFile = _generateZipFile(
			"container/layout/expected", null, null);
		File inputFile = _generateZipFile("container/layout/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLink()
		throws Exception {

		File expectedFile = _generateZipFile(
			"container/link/expected", null, null);
		File inputFile = _generateZipFile("container/link/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLinkMappedToJournalArticleDisplayPageURL()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle(
			_group1.getGroupId());

		_addDisplayPageTemplate(journalArticle);

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK", String.valueOf(journalArticle.getResourcePrimKey())
		).put(
			"DISPLAY_PAGE_URL",
			StringBundler.concat(
				"\"http://localhost:8080/web", _group1.getFriendlyURL(),
				FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE,
				journalArticle.getUrlTitle(), "\"")
		).build();

		File expectedFile = _generateZipFile(
			"container/link_mapped_journal_article_display_page_url/expected",
			numberValuesMap, null);
		File inputFile = _generateZipFile(
			"container/link_mapped_journal_article_display_page_url/input",
			numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLinkMappedToLayoutWithFriendlyURL()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group1.getGroupId());

		Map<String, String> stringValuesMap = HashMapBuilder.put(
			"FRIENDLY_URL", layout.getFriendlyURL()
		).put(
			"SITE_KEY", String.valueOf(_group1.getGroupKey())
		).build();

		File expectedFile = _generateZipFile(
			"container/link_mapped_layout/friendly_url/expected", null,
			stringValuesMap);
		File inputFile = _generateZipFile(
			"container/link_mapped_layout/friendly_url/input", null,
			stringValuesMap);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLinkMappedToLayoutWithFriendlyURLSiteKeyDifferentGroup()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group1.getGroupId());

		Map<String, String> stringValuesMap = HashMapBuilder.put(
			"FRIENDLY_URL", layout.getFriendlyURL()
		).put(
			"SITE_KEY", String.valueOf(_group1.getGroupKey())
		).build();

		File expectedFile = _generateZipFile(
			"container/link_mapped_layout/friendly_url_site_key/expected", null,
			stringValuesMap);
		File inputFile = _generateZipFile(
			"container/link_mapped_layout/friendly_url_site_key/input", null,
			stringValuesMap);

		_validateImportExport(
			expectedFile, inputFile, _group1.getGroupId(),
			_group2.getGroupId());
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryContainerLinkMappedToLayoutWithPlid()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group1.getGroupId());

		Map<String, String> stringValuesMap = HashMapBuilder.put(
			"FRIENDLY_URL", layout.getFriendlyURL()
		).put(
			"PLID", String.valueOf(layout.getPlid())
		).put(
			"SITE_KEY", String.valueOf(_group1.getGroupKey())
		).build();

		File expectedFile = _generateZipFile(
			"container/link_mapped_layout/plid/expected", null,
			stringValuesMap);
		File inputFile = _generateZipFile(
			"container/link_mapped_layout/plid/input", null, stringValuesMap);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentHidden()
		throws Exception {

		_addTextFragmentEntry();

		File expectedFile = _generateZipFile(
			"fragment/hidden/expected", null, null);
		File inputFile = _generateZipFile("fragment/hidden/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailable()
		throws Exception {

		_addTextFragmentEntry();

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, null);
		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailableOverwriteFalsePageTemplateEntryDoesNotExist()
		throws Exception {

		_addTextFragmentEntry();

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, null);
		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailableOverwriteFalsePageTemplateEntryExists()
		throws Exception {

		_addTextFragmentEntry();

		Map<String, String> numberValuesMap1 = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap1, null);

		File inputFile1 = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap1, null);

		_getImportLayoutPageTemplateEntry(
			inputFile1, _group1.getGroupId(), false,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		File inputFile2 = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			HashMapBuilder.put(
				"CLASS_PK",
				() -> {
					JournalArticle journalArticle = _addJournalArticle(
						_group1.getGroupId());

					return String.valueOf(journalArticle.getResourcePrimKey());
				}
			).build(),
			null);

		File outputFile = _importExportLayoutPageTemplateEntry(
			inputFile2, _group1.getGroupId(), false,
			LayoutPageTemplatesImporterResultEntry.Status.IGNORED);

		_validateFile(expectedFile, outputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailableOverwriteTruePageTemplateEntryDoesNotExist()
		throws Exception {

		_addTextFragmentEntry();

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, null);

		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		File outputFile = _importExportLayoutPageTemplateEntry(
			inputFile, _group1.getGroupId(), true,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentAvailableOverwriteTruePageTemplateEntryExists()
		throws Exception {

		_addTextFragmentEntry();

		File inputFile1 = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			HashMapBuilder.put(
				"CLASS_PK",
				() -> {
					JournalArticle journalArticle = _addJournalArticle(
						_group1.getGroupId());

					return String.valueOf(journalArticle.getResourcePrimKey());
				}
			).build(),
			null);

		_getImportLayoutPageTemplateEntry(
			inputFile1, _group1.getGroupId(), false,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, null);

		File inputFile2 = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		File outputFile = _importExportLayoutPageTemplateEntry(
			inputFile2, _group1.getGroupId(), true,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentAvailableMappedContentNotAvailable()
		throws Exception {

		_addTextFragmentEntry();

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK", String.valueOf(RandomTestUtil.randomLong())
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_available",
			numberValuesMap, null);
		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryFragmentTextFieldFragmentNotAvailable()
		throws Exception {

		Map<String, String> numberValuesMap = HashMapBuilder.put(
			"CLASS_PK",
			() -> {
				JournalArticle journalArticle = _addJournalArticle(
					_group1.getGroupId());

				return String.valueOf(journalArticle.getResourcePrimKey());
			}
		).build();

		File expectedFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/expected" +
				"/fragment_not_available",
			numberValuesMap, null);
		File inputFile = _generateZipFile(
			"fragment/text_field/mapped_value/class_pk_reference/input",
			numberValuesMap, null);

		_validateImportExport(expectedFile, inputFile);
	}

	@Test
	public void testImportExportLayoutPageTemplateEntryRowContainer()
		throws Exception {

		File expectedFile = _generateZipFile(
			"row/container/expected", null, null);
		File inputFile = _generateZipFile("row/container/input", null, null);

		_validateImportExport(expectedFile, inputFile);
	}

	private void _addDisplayPageTemplate(JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group1.getCreatorUserId(), _group1.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0,
				ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), _group1.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), true);
	}

	private FragmentEntry _addFragmentEntry(
			long groupId, String key, String name, String html)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), groupId, "Test Collection",
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(), key, name,
			StringPool.BLANK, html, StringPool.BLANK, false, StringPool.BLANK,
			null, 0, FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private JournalArticle _addJournalArticle(long groupId) throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		journalArticle.setSmallImage(true);
		journalArticle.setSmallImageURL(
			"https://avatars1.githubusercontent.com/u/131436");

		return JournalTestUtil.updateArticle(journalArticle);
	}

	private void _addTextFragmentEntry() throws Exception {
		String html =
			"<lfr-editable id=\"element-text\" type=\"text\">Test Text " +
				"Fragment</lfr-editable>";

		_addFragmentEntry(
			_group1.getGroupId(), "test-text-fragment", "Test Text Fragment",
			html);
	}

	private void _addZipWriterEntry(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws IOException {

		String entryPath = url.getPath();

		String zipPath = StringUtil.removeSubstring(
			entryPath, _LAYOUT_PATE_TEMPLATES_PATH);

		String content = StringUtil.read(url.openStream());

		content = StringUtil.replace(content, "\"${", "}\"", numberValuesMap);
		content = StringUtil.replace(content, "£{", "}", stringValuesMap);

		zipWriter.addEntry(zipPath, content);
	}

	private File _generateZipFile(
			String testPath, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws Exception {

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		Enumeration<URL> enumeration = _bundle.findEntries(
			StringBundler.concat(
				_LAYOUT_PATE_TEMPLATES_PATH + testPath,
				StringPool.FORWARD_SLASH + _ROOT_FOLDER,
				StringPool.FORWARD_SLASH),
			LayoutPageTemplateExportImportConstants.
				FILE_NAME_PAGE_TEMPLATE_COLLECTION,
			true);

		try {
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				_populateZipWriter(
					zipWriter, url, numberValuesMap, stringValuesMap);
			}

			return zipWriter.getFile();
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private LayoutPageTemplateEntry _getImportLayoutPageTemplateEntry(
			File file, long groupId, boolean overwrite,
			LayoutPageTemplatesImporterResultEntry.Status status)
		throws Exception {

		List<LayoutPageTemplatesImporterResultEntry>
			layoutPageTemplatesImporterResultEntries = null;

		ServiceContext serviceContext = _getServiceContext(
			_group1, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			layoutPageTemplatesImporterResultEntries =
				_layoutPageTemplatesImporter.importFile(
					TestPropsValues.getUserId(), groupId, 0, file, overwrite);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		Assert.assertNotNull(layoutPageTemplatesImporterResultEntries);

		Assert.assertEquals(
			layoutPageTemplatesImporterResultEntries.toString(), 1,
			layoutPageTemplatesImporterResultEntries.size());

		LayoutPageTemplatesImporterResultEntry layoutPageTemplateImportEntry =
			layoutPageTemplatesImporterResultEntries.get(0);

		Assert.assertEquals(status, layoutPageTemplateImportEntry.getStatus());

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(
			layoutPageTemplateImportEntry.getName());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				groupId, layoutPageTemplateEntryKey);

		Assert.assertNotNull(layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	private ServiceContext _getServiceContext(Group group, long userId)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, userId);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(
			_layoutLocalService.getLayout(TestPropsValues.getPlid()));

		LayoutSet layoutSet = _group1.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group1.getGroupId());
		themeDisplay.setSiteGroupId(_group1.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private File _importExportLayoutPageTemplateEntry(
			File file, long groupId, boolean overwrite,
			LayoutPageTemplatesImporterResultEntry.Status status)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getImportLayoutPageTemplateEntry(file, groupId, overwrite, status);

		return ReflectionTestUtil.invoke(
			_mvcResourceCommand, "getFile", new Class<?>[] {long[].class},
			new long[] {
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			});
	}

	private void _populateZipWriter(
			ZipWriter zipWriter, URL url, Map<String, String> numberValuesMap,
			Map<String, String> stringValuesMap)
		throws IOException {

		String zipPath = StringUtil.removeSubstring(
			url.getFile(), _LAYOUT_PATE_TEMPLATES_PATH);

		zipWriter.addEntry(zipPath, url.openStream());

		String path = FileUtil.getPath(url.getPath());

		Enumeration<URL> enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_TEMPLATE,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}

		enumeration = _bundle.findEntries(
			path,
			LayoutPageTemplateExportImportConstants.FILE_NAME_PAGE_DEFINITION,
			true);

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}

		enumeration = _bundle.findEntries(path, "thumbnail.png", true);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL elementURL = enumeration.nextElement();

			_addZipWriterEntry(
				zipWriter, elementURL, numberValuesMap, stringValuesMap);
		}
	}

	private void _validateFile(File inputFile, File outputFile)
		throws Exception {

		ZipFile inputZipFile = new ZipFile(inputFile);

		Enumeration<? extends ZipEntry> inputEnumeration =
			inputZipFile.entries();

		Map<String, String> fileNameFileContentMap = new HashMap<>();

		int numberOfInputFiles = 0;

		while (inputEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = inputEnumeration.nextElement();

			if (!zipEntry.isDirectory()) {
				numberOfInputFiles++;

				String content = StringUtil.read(
					inputZipFile.getInputStream(zipEntry));

				String name = zipEntry.getName();

				String[] parts = name.split("/");

				fileNameFileContentMap.put(parts[parts.length - 1], content);
			}
		}

		ZipFile outputZipFile = new ZipFile(outputFile);

		Enumeration<? extends ZipEntry> outputEnumeration =
			outputZipFile.entries();

		int numberOfOutputFiles = 0;

		while (outputEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = outputEnumeration.nextElement();

			if (!zipEntry.isDirectory()) {
				numberOfOutputFiles++;

				String name = zipEntry.getName();

				String[] parts = name.split("/");

				Assert.assertEquals(
					_objectMapper.readTree(
						fileNameFileContentMap.get(parts[parts.length - 1])),
					_objectMapper.readTree(
						StringUtil.read(
							outputZipFile.getInputStream(zipEntry))));
			}
		}

		Assert.assertEquals(numberOfInputFiles, numberOfOutputFiles);
		Assert.assertTrue(numberOfInputFiles > 0);
	}

	private void _validateImportExport(File expectedFile, File inputFile)
		throws Exception {

		_validateImportExport(
			expectedFile, inputFile, _group1.getGroupId(),
			_group1.getGroupId());
	}

	private void _validateImportExport(
			File expectedFile, File inputFile, long groupId1, long groupId2)
		throws Exception {

		File outputFile1 = _importExportLayoutPageTemplateEntry(
			inputFile, groupId1, false,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile1);

		File outputFile2 = _importExportLayoutPageTemplateEntry(
			outputFile1, groupId2, true,
			LayoutPageTemplatesImporterResultEntry.Status.IMPORTED);

		_validateFile(expectedFile, outputFile2);
	}

	private static final String _LAYOUT_PATE_TEMPLATES_PATH =
		"com/liferay/layout/page/template/admin/web/internal/portlet/action" +
			"/test/dependencies/import_export/page_templates/";

	private static final String _ROOT_FOLDER = "page-templates";

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	private Bundle _bundle;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_page_template_admin/export_layout_page_template_entries"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ObjectMapper _objectMapper;

	@Inject
	private Portal _portal;

}