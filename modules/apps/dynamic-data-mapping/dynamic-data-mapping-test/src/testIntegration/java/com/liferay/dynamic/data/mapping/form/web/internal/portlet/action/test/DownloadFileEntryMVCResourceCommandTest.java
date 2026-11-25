/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.FileEntryExpiredException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceRecordTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.PortletException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yuri Monteiro
 */
@RunWith(Arquillian.class)
public class DownloadFileEntryMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_userLocalService.addRoleUser(_role.getRoleId(), _user);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			_DDM_FORM_FIELD_NAME_TEXT);

		DDMFormField ddmFormField =
			DDMFormTestUtil.addDocumentLibraryDDMFormField(
				ddmForm, _DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY);

		ddmFormField.setRequired(false);

		_ddmFormInstance = DDMFormInstanceTestUtil.addDDMFormInstance(
			ddmForm, _group, TestPropsValues.getUserId());

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), DDMFormInstance.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_ddmFormInstance.getFormInstanceId()),
			_role.getRoleId(), new String[] {ActionKeys.VIEW});
	}

	@Test
	public void testServeResource() throws Exception {
		_testServeResourceWithDocumentLibraryFileEntry();
		_testServeResourceWithFileEntryNotInDDMFormFieldValue();
		_testServeResourceWithGuestUpload();
		_testServeResourceWithUserUpload();
	}

	private FileEntry _addFileEntry(byte[] bytes, long repositoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGroupPermissions(false);
		serviceContext.setAddGuestPermissions(false);

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), repositoryId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, bytes, null,
			null, null, serviceContext);
	}

	private DDMFormValues _createDDMFormValues(
			String ddmFormFieldName, DDMFormInstance ddmFormInstance,
			JSONObject jsonObject)
		throws Exception {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmFormInstance.getDDMForm());

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				ddmFormFieldName, jsonObject.toString()));

		return ddmFormValues;
	}

	private byte[] _serveResource(
			String ddmFormFieldName, long ddmFormInstanceRecordId,
			long fileEntryId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.addParameter(
			"ddmFormFieldName", ddmFormFieldName);
		mockLiferayResourceRequest.addParameter(
			"ddmFormInstanceRecordId", String.valueOf(ddmFormInstanceRecordId));
		mockLiferayResourceRequest.addParameter(
			"fileEntryId", String.valueOf(fileEntryId));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		themeDisplay.setPermissionChecker(permissionChecker);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, permissionChecker)) {

			_mvcResourceCommand.serveResource(
				mockLiferayResourceRequest, mockLiferayResourceResponse);
		}

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return byteArrayOutputStream.toByteArray();
	}

	private void _testServeResourceWithDocumentLibraryFileEntry()
		throws Exception {

		// Document library file entry with download permission

		byte[] bytes = RandomTestUtil.randomBytes();

		FileEntry fileEntry1 = _addFileEntry(bytes, _group.getGroupId());

		_resourcePermissionLocalService.setResourcePermissions(
			_group.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(fileEntry1.getFileEntryId()), _role.getRoleId(),
			new String[] {ActionKeys.DOWNLOAD});

		DDMFormInstanceRecord ddmFormInstanceRecord1 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId())),
				_group, _user.getUserId());

		Assert.assertArrayEquals(
			bytes,
			_serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// Expired document library file entry

		FileVersion fileVersion = fileEntry1.getFileVersion();

		_dlFileEntryLocalService.updateStatus(
			TestPropsValues.getUserId(), fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_EXPIRED,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()),
			new HashMap<>());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				FileEntryExpiredException.class.getName(), ": The file entry ",
				fileEntry1.getFileEntryId(),
				" is expired and the user does not have review permission"),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// Site document library file entry referenced by its owner

		FileEntry fileEntry2 = _addFileEntry(
			RandomTestUtil.randomBytes(), _group.getGroupId());

		DDMFormInstanceRecord ddmFormInstanceRecord2 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry2.getFileEntryId())),
				_group, TestPropsValues.getUserId());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				PrincipalException.MustHavePermission.class.getName(),
				": User ", _user.getUserId(), " must have ",
				ActionKeys.DOWNLOAD, " permission for ",
				FileEntry.class.getName(), StringPool.SPACE,
				fileEntry2.getFileEntryId()),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord2.getFormInstanceRecordId(),
				fileEntry2.getFileEntryId()));
	}

	private void _testServeResourceWithFileEntryNotInDDMFormFieldValue()
		throws Exception {

		// Different file entry in the upload field

		FileEntry fileEntry1 = _addFileEntry(
			RandomTestUtil.randomBytes(), _group.getGroupId());

		DDMFormInstanceRecord ddmFormInstanceRecord1 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId())),
				_group, _user.getUserId());

		FileEntry fileEntry2 = _addFileEntry(
			RandomTestUtil.randomBytes(), _group.getGroupId());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				NoSuchFileEntryException.class.getName(),
				": No file entry exists with file entry ID ",
				fileEntry2.getFileEntryId()),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry2.getFileEntryId()));

		// Nonexistent form field

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				NoSuchFileEntryException.class.getName(),
				": No file entry exists with file entry ID ",
				fileEntry1.getFileEntryId()),
			() -> _serveResource(
				RandomTestUtil.randomString(),
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// Text field

		DDMFormInstanceRecord ddmFormInstanceRecord2 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_TEXT, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId())),
				_group, _user.getUserId());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				NoSuchFileEntryException.class.getName(),
				": No file entry exists with file entry ID ",
				fileEntry1.getFileEntryId()),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_TEXT,
				ddmFormInstanceRecord2.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));
	}

	private void _testServeResourceWithGuestUpload() throws Exception {

		// Guest upload

		_portletFileRepository.addPortletRepository(
			_group.getGroupId(), DDMFormConstants.SERVICE_NAME,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		byte[] bytes = RandomTestUtil.randomBytes();

		User ddmFormDefaultUser = DDMFormUtil.getDDMFormDefaultUser(
			_group.getCompanyId());

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), ddmFormDefaultUser.getUserId(),
			RandomTestUtil.randomString(),
			TempFileEntryUtil.getTempFileName(
				RandomTestUtil.randomString() + ".txt"),
			new ByteArrayInputStream(bytes), ContentTypes.TEXT_PLAIN);

		DDMFormInstanceRecord ddmFormInstanceRecord1 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put(
						"fileEntryId", tempFileEntry.getFileEntryId()
					).put(
						"groupId", tempFileEntry.getGroupId()
					).put(
						"uuid", tempFileEntry.getUuid()
					)),
				_group,
				_userLocalService.getGuestUserId(_group.getCompanyId()));

		DDMFormValues ddmFormValues = ddmFormInstanceRecord1.getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY);

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			value.getString(value.getDefaultLocale()));

		long fileEntryId = jsonObject.getLong("fileEntryId");

		Assert.assertArrayEquals(
			bytes,
			_serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(), fileEntryId));

		// Guest upload referenced by another user's form instance record

		DDMFormInstanceRecord ddmFormInstanceRecord2 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntryId)),
				_group, _user.getUserId());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				PrincipalException.MustHavePermission.class.getName(),
				": User ", _user.getUserId(), " must have ",
				ActionKeys.DOWNLOAD, " permission for ",
				FileEntry.class.getName(), StringPool.SPACE, fileEntryId),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord2.getFormInstanceRecordId(), fileEntryId));
	}

	private void _testServeResourceWithUserUpload() throws Exception {

		// User upload

		byte[] bytes = RandomTestUtil.randomBytes();

		Repository repository = _portletFileRepository.addPortletRepository(
			_group.getGroupId(), DDMFormConstants.SERVICE_NAME,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FileEntry fileEntry1 = _addFileEntry(
			bytes, repository.getRepositoryId());

		DDMFormInstanceRecord ddmFormInstanceRecord1 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId())),
				_group, TestPropsValues.getUserId());

		Assert.assertArrayEquals(
			bytes,
			_serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// User upload after another user updates the form instance record

		DDMFormValues ddmFormValues = _createDDMFormValues(
			_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
			JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId()));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				_DDM_FORM_FIELD_NAME_TEXT, RandomTestUtil.randomString()));

		_ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
			_user.getUserId(), ddmFormInstanceRecord1.getFormInstanceRecordId(),
			false, ddmFormValues,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertArrayEquals(
			bytes,
			_serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// User upload referenced by another user's form instance record

		DDMFormInstanceRecord ddmFormInstanceRecord2 =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
				_ddmFormInstance,
				_createDDMFormValues(
					_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
					JSONUtil.put("fileEntryId", fileEntry1.getFileEntryId())),
				_group, _user.getUserId());

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				PrincipalException.MustHavePermission.class.getName(),
				": User ", _user.getUserId(), " must have ",
				ActionKeys.DOWNLOAD, " permission for ",
				FileEntry.class.getName(), StringPool.SPACE,
				fileEntry1.getFileEntryId()),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord2.getFormInstanceRecordId(),
				fileEntry1.getFileEntryId()));

		// User upload replaced by another user

		FileEntry fileEntry2 = _addFileEntry(
			RandomTestUtil.randomBytes(), repository.getRepositoryId());

		_ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
			_user.getUserId(), ddmFormInstanceRecord1.getFormInstanceRecordId(),
			false,
			_createDDMFormValues(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY, _ddmFormInstance,
				JSONUtil.put("fileEntryId", fileEntry2.getFileEntryId())),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		AssertUtils.assertFailure(
			PortletException.class,
			StringBundler.concat(
				PrincipalException.MustHavePermission.class.getName(),
				": User ", _user.getUserId(), " must have ",
				ActionKeys.DOWNLOAD, " permission for ",
				FileEntry.class.getName(), StringPool.SPACE,
				fileEntry2.getFileEntryId()),
			() -> _serveResource(
				_DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY,
				ddmFormInstanceRecord1.getFormInstanceRecordId(),
				fileEntry2.getFileEntryId()));
	}

	private static final String _DDM_FORM_FIELD_NAME_DOCUMENT_LIBRARY =
		RandomTestUtil.randomString();

	private static final String _DDM_FORM_FIELD_NAME_TEXT =
		RandomTestUtil.randomString();

	private DDMFormInstance _ddmFormInstance;

	@Inject
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/dynamic_data_mapping_form/download_file_entry"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}