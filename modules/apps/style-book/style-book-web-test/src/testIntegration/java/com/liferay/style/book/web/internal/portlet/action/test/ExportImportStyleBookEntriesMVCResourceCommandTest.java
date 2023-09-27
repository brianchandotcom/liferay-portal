/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.exception.DuplicateStyleBookEntryKeyException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;

import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class ExportImportStyleBookEntriesMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_sourceGroup = GroupTestUtil.addGroup();
		_targetGroup = GroupTestUtil.addGroup();
	}

	@Test
	public void testExportImportMultipleStyleBookEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		StyleBookEntry styleBookEntry1 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"),
				RandomTestUtil.randomString(), "STYLE_BOOK_ENTRY_KEY_1",
				serviceContext);
		StyleBookEntry styleBookEntry2 =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"),
				RandomTestUtil.randomString(), "STYLE_BOOK_ENTRY_KEY_2",
				serviceContext);

		File file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {
				styleBookEntry1.getStyleBookEntryId(),
				styleBookEntry2.getStyleBookEntryId()
			});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file,
			false);

		Assert.assertEquals(
			2,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_targetGroup.getGroupId()));
		Assert.assertNotNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_targetGroup.getGroupId(), "STYLE_BOOK_ENTRY_KEY_1"));
		Assert.assertNotNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_targetGroup.getGroupId(), "STYLE_BOOK_ENTRY_KEY_2"));
	}

	@Test
	public void testExportImportSingleStyleBookEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"), "Style Book Entry Name",
				"STYLE_BOOK_ENTRY_KEY", serviceContext);

		File file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {styleBookEntry.getStyleBookEntryId()});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file,
			false);

		Assert.assertEquals(
			1,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_targetGroup.getGroupId()));

		StyleBookEntry targetGroupStyleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_targetGroup.getGroupId(), "STYLE_BOOK_ENTRY_KEY");

		Assert.assertNotNull(targetGroupStyleBookEntry);

		Assert.assertEquals(
			"Style Book Entry Name", targetGroupStyleBookEntry.getName());

		JSONObject expectedFrontendTokensValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		JSONObject actualFrontendTokensValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				targetGroupStyleBookEntry.getFrontendTokensValues());

		Assert.assertEquals(
			expectedFrontendTokensValuesJSONObject.toString(),
			actualFrontendTokensValuesJSONObject.toString());
	}

	@Test(expected = DuplicateStyleBookEntryKeyException.class)
	public void testExportImportSingleStyleBookEntryAndNotOverwrite()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"), "Style Book Entry Name",
				"STYLE_BOOK_ENTRY_KEY", serviceContext);

		File file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {styleBookEntry.getStyleBookEntryId()});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file,
			false);

		StyleBookEntry updatedStyleBookEntry =
			_styleBookEntryLocalService.updateStyleBookEntry(
				styleBookEntry.getStyleBookEntryId(),
				_read("updated-frontend-tokens-values.json"),
				"Updated Style Book Entry Name");

		ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {updatedStyleBookEntry.getStyleBookEntryId()});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file,
			false);
	}

	@Test
	public void testExportImportSingleStyleBookEntryAndOverwrite()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"), "Style Book Entry Name",
				"STYLE_BOOK_ENTRY_KEY", serviceContext);

		File file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {styleBookEntry.getStyleBookEntryId()});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file,
			false);

		StyleBookEntry updatedStyleBookEntry =
			_styleBookEntryLocalService.updateStyleBookEntry(
				styleBookEntry.getStyleBookEntryId(),
				_read("updated-frontend-tokens-values.json"),
				"Updated Style Book Entry Name");

		file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {updatedStyleBookEntry.getStyleBookEntryId()});

		ReflectionTestUtil.invoke(
			_importStyleBookEntriesMVCActionCommand, "_importStyleBookEntries",
			new Class<?>[] {long.class, long.class, File.class, boolean.class},
			TestPropsValues.getUserId(), _targetGroup.getGroupId(), file, true);

		Assert.assertEquals(
			1,
			_styleBookEntryLocalService.getStyleBookEntriesCount(
				_targetGroup.getGroupId()));

		StyleBookEntry updatedTargetGroupStyleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				_targetGroup.getGroupId(), "STYLE_BOOK_ENTRY_KEY");

		Assert.assertEquals(
			"Updated Style Book Entry Name",
			updatedTargetGroupStyleBookEntry.getName());

		JSONObject expectedFrontendTokensValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				updatedStyleBookEntry.getFrontendTokensValues());
		JSONObject actualFrontendTokensValuesJSONObject =
			JSONFactoryUtil.createJSONObject(
				updatedTargetGroupStyleBookEntry.getFrontendTokensValues());

		Assert.assertEquals(
			expectedFrontendTokensValuesJSONObject.toString(),
			actualFrontendTokensValuesJSONObject.toString());
	}

	@Test
	public void testExportStyleBookEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				TestPropsValues.getUserId(), _sourceGroup.getGroupId(),
				_read("frontend-tokens-values.json"),
				RandomTestUtil.randomString(), "STYLE_BOOK_ENTRY_KEY",
				serviceContext);

		FileEntry fileEntry = _addFileEntry(styleBookEntry);

		_styleBookEntryLocalService.updatePreviewFileEntryId(
			styleBookEntry.getStyleBookEntryId(), fileEntry.getFileEntryId());

		File file = ReflectionTestUtil.invoke(
			_exportStyleBookEntriesMVCResourceCommand,
			"_exportStyleBookEntries", new Class<?>[] {long[].class},
			new long[] {styleBookEntry.getStyleBookEntryId()});

		try (ZipFile zipFile = new ZipFile(file)) {
			int count = 0;

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (!zipEntry.isDirectory()) {
					_validateZipEntry(styleBookEntry, zipEntry, zipFile);

					count++;
				}
			}

			Assert.assertEquals(3, count);
		}
	}

	private FileEntry _addFileEntry(StyleBookEntry styleBookEntry)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_sourceGroup, TestPropsValues.getUserId());

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			_sourceGroup.getGroupId(), RandomTestUtil.randomString(),
			serviceContext);

		Class<?> clazz = getClass();

		return PortletFileRepositoryUtil.addPortletFileEntry(
			null, _sourceGroup.getGroupId(), TestPropsValues.getUserId(),
			StyleBookEntry.class.getName(),
			styleBookEntry.getStyleBookEntryId(), RandomTestUtil.randomString(),
			repository.getDlFolderId(),
			clazz.getResourceAsStream("dependencies/thumbnail.png"),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);
	}

	private boolean _isStyleBookDefinitionFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 2) &&
			Objects.equals(pathParts[1], "style-book.json")) {

			return true;
		}

		return false;
	}

	private boolean _isStyleBookThumbnailFile(String fileName) {
		String[] pathParts = StringUtil.split(fileName, CharPool.SLASH);

		if ((pathParts.length == 2) &&
			Objects.equals(pathParts[1], "thumbnail.png")) {

			return true;
		}

		return false;
	}

	private boolean _isStyleBookTokensValuesFile(String path) {
		String[] pathParts = StringUtil.split(path, CharPool.SLASH);

		if ((pathParts.length == 2) &&
			Objects.equals(pathParts[1], "frontend-tokens-values.json")) {

			return true;
		}

		return false;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateZipEntry(
			StyleBookEntry styleBookEntry, ZipEntry zipEntry, ZipFile zipFile)
		throws Exception {

		if (_isStyleBookDefinitionFile(zipEntry.getName())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(zipFile.getInputStream(zipEntry)));

			Assert.assertEquals(
				styleBookEntry.getName(), jsonObject.getString("name"));
			Assert.assertEquals(
				"frontend-tokens-values.json",
				jsonObject.getString("frontendTokensValuesPath"));
		}

		if (_isStyleBookTokensValuesFile(zipEntry.getName())) {
			JSONObject expectedFrontendTokensValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					styleBookEntry.getFrontendTokensValues());

			JSONObject actualFrontendTokensValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					StringUtil.read(zipFile.getInputStream(zipEntry)));

			Assert.assertEquals(
				expectedFrontendTokensValuesJSONObject.toString(),
				actualFrontendTokensValuesJSONObject.toString());
		}

		if (_isStyleBookThumbnailFile(zipEntry.getName())) {
			Assert.assertArrayEquals(
				FileUtil.getBytes(getClass(), "dependencies/thumbnail.png"),
				FileUtil.getBytes(zipFile.getInputStream(zipEntry)));
		}
	}

	@Inject(filter = "mvc.command.name=/style_book/export_style_book_entries")
	private MVCResourceCommand _exportStyleBookEntriesMVCResourceCommand;

	@Inject(filter = "mvc.command.name=/style_book/import_style_book_entries")
	private MVCActionCommand _importStyleBookEntriesMVCActionCommand;

	@DeleteAfterTestRun
	private Group _sourceGroup;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@DeleteAfterTestRun
	private Group _targetGroup;

}