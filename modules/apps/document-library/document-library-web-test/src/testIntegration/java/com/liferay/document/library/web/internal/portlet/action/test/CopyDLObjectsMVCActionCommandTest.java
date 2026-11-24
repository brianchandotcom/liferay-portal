/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Brian Wulbern
 */
@RunWith(Arquillian.class)
public class CopyDLObjectsMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-115331")
	public void testProcessAction() throws Exception {
		_testProcessActionWithCompanyGroupFileEntryType();
		_testProcessActionWithDepotGroupFileEntryType();
		_testProcessActionWithSiteGroupFileEntryType();
	}

	@Test
	@TestInfo({"LPS-182709", "LPS-187945"})
	public void testProcessActionWithDuplicateTitles() throws Exception {
		_testProcessActionWithDuplicateTitlesInElevenFileEntries();
		_testProcessActionWithDuplicateTitlesInOneFileEntry();
		_testProcessActionWithDuplicateTitlesInTenFileEntries();
	}

	private List<FileEntry> _addAndCopyFileEntries(int count) throws Exception {
		List<FileEntry> fileEntries = new ArrayList<>();

		for (int index = 0; index < count; index++) {
			fileEntries.add(
				_addFileEntry(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					_group1.getGroupId()));
		}

		JSONObject jsonObject = _processAction(
			_group2.getGroupId(),
			ListUtil.toLongArray(fileEntries, FileEntry::getFileEntryId),
			_group1.getGroupId());

		Assert.assertFalse(jsonObject.has("failedItems"));
		Assert.assertEquals(count, jsonObject.getInt("successItems"));

		return fileEntries;
	}

	private DLFileEntryType _addDLFileEntryType(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			null, TestPropsValues.getUserId(), groupId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			StringPool.BLANK,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.emptyMap(), StringPool.BLANK,
			StorageType.DEFAULT.toString(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return _dlFileEntryTypeLocalService.addFileEntryType(
			null, TestPropsValues.getUserId(), groupId,
			ddmStructure.getStructureId(), null,
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.emptyMap(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private FileEntry _addFileEntry(
			long fileEntryTypeId, long folderId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), groupId, folderId,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, RandomTestUtil.randomBytes(), null, null, null,
			serviceContext);
	}

	private void _assertProcessAction(
			long destinationGroupId, long expectedFileEntryTypeId,
			FileEntry fileEntry)
		throws Exception {

		_processAction(
			destinationGroupId, new long[] {fileEntry.getFileEntryId()},
			fileEntry.getGroupId());

		FileEntry copiedFileEntry = _dlAppLocalService.getFileEntry(
			destinationGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			fileEntry.getTitle());

		DLFileEntry dlFileEntry = (DLFileEntry)copiedFileEntry.getModel();

		Assert.assertEquals(
			expectedFileEntryTypeId, dlFileEntry.getFileEntryTypeId());
	}

	private void _deleteRepositories() throws Exception {
		_dlAppLocalService.deleteAllRepositories(_group1.getGroupId());
		_dlAppLocalService.deleteAllRepositories(_group2.getGroupId());
	}

	private ThemeDisplay _getThemeDisplay(long groupId) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(groupId);
		themeDisplay.setSiteGroupId(groupId);
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private JSONObject _processAction(
			long destinationGroupId, long[] dlObjectIds, long sourceGroupId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(
					DLPortletKeys.DOCUMENT_LIBRARY_ADMIN),
				null));
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(destinationGroupId));
		mockLiferayPortletActionRequest.setParameter(
			"destinationParentFolderId",
			String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
		mockLiferayPortletActionRequest.setParameter(
			"destinationRepositoryId", String.valueOf(destinationGroupId));
		mockLiferayPortletActionRequest.setParameter(
			"dlObjectIds", StringUtil.merge(dlObjectIds));
		mockLiferayPortletActionRequest.setParameter(
			"sourceRepositoryId", String.valueOf(sourceGroupId));
		mockLiferayPortletActionRequest.setPortletSession(
			new MockPortletSession());

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		return _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());
	}

	private void _testProcessActionWithCompanyGroupFileEntryType()
		throws Exception {

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		_dlFileEntryType = _addDLFileEntryType(companyGroup.getGroupId());

		Folder folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));

		_assertProcessAction(
			_group2.getGroupId(), _dlFileEntryType.getFileEntryTypeId(),
			_addFileEntry(
				_dlFileEntryType.getFileEntryTypeId(), folder.getFolderId(),
				_group1.getGroupId()));

		_deleteRepositories();
	}

	private void _testProcessActionWithDepotGroupFileEntryType()
		throws Exception {

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.emptyMap(), DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			false, _depotEntry.getDepotEntryId(), _group2.getGroupId(), true);
		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			true, _depotEntry.getDepotEntryId(), _group1.getGroupId(), true);

		DLFileEntryType dlFileEntryType = _addDLFileEntryType(
			_depotEntry.getGroupId());

		FileEntry fileEntry = _addFileEntry(
			dlFileEntryType.getFileEntryTypeId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_depotEntry.getGroupId());

		_assertProcessAction(
			_group1.getGroupId(), dlFileEntryType.getFileEntryTypeId(),
			fileEntry);
		_assertProcessAction(
			_group2.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			fileEntry);

		_deleteRepositories();
	}

	private void _testProcessActionWithDuplicateTitlesInElevenFileEntries()
		throws Exception {

		List<FileEntry> fileEntries = _addAndCopyFileEntries(11);

		JSONObject jsonObject = _processAction(
			_group2.getGroupId(),
			ListUtil.toLongArray(fileEntries, FileEntry::getFileEntryId),
			_group1.getGroupId());

		Assert.assertFalse(jsonObject.has("errorMessages"));
		Assert.assertEquals(11, jsonObject.getInt("failedItems"));
		Assert.assertEquals(0, jsonObject.getInt("successItems"));

		_deleteRepositories();
	}

	private void _testProcessActionWithDuplicateTitlesInOneFileEntry()
		throws Exception {

		FileEntry fileEntry = _addFileEntry(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _group1.getGroupId());

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group2.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileEntry.getFileName(),
			ContentTypes.TEXT_PLAIN, fileEntry.getTitle(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, RandomTestUtil.randomBytes(),
			null, null, null,
			ServiceContextTestUtil.getServiceContext(_group2.getGroupId()));

		JSONObject jsonObject = _processAction(
			_group2.getGroupId(), new long[] {fileEntry.getFileEntryId()},
			_group1.getGroupId());

		Assert.assertEquals(
			Collections.singletonList(
				"A file entry already exists with title " +
					fileEntry.getTitle()),
			JSONUtil.toStringList(jsonObject.getJSONArray("errorMessages")));
		Assert.assertEquals(1, jsonObject.getInt("failedItems"));
		Assert.assertEquals(0, jsonObject.getInt("successItems"));

		_deleteRepositories();
	}

	private void _testProcessActionWithDuplicateTitlesInTenFileEntries()
		throws Exception {

		List<FileEntry> fileEntries = _addAndCopyFileEntries(10);

		JSONObject jsonObject = _processAction(
			_group2.getGroupId(),
			ListUtil.toLongArray(fileEntries, FileEntry::getFileEntryId),
			_group1.getGroupId());

		Assert.assertEquals(
			TransformUtil.transform(
				fileEntries,
				fileEntry ->
					"A file entry already exists with title " +
						fileEntry.getTitle()),
			JSONUtil.toStringList(jsonObject.getJSONArray("errorMessages")));
		Assert.assertEquals(10, jsonObject.getInt("failedItems"));
		Assert.assertEquals(0, jsonObject.getInt("successItems"));

		_deleteRepositories();
	}

	private void _testProcessActionWithSiteGroupFileEntryType()
		throws Exception {

		DLFileEntryType dlFileEntryType = _addDLFileEntryType(
			_group1.getGroupId());

		_assertProcessAction(
			_group2.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			_addFileEntry(
				dlFileEntryType.getFileEntryTypeId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				_group1.getGroupId()));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private DLFileEntryType _dlFileEntryType;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject(filter = "mvc.command.name=/document_library/copy_dl_objects")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PortletLocalService _portletLocalService;

}