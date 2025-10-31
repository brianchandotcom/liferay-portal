/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.ws.rs.HttpMethod;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuele Castro
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ObjectDLFileEntryModelResourcePermissionConfiguratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _company.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			TempFileEntryUtil.getTempFileName("image.jpg"),
			ContentTypes.APPLICATION_TEXT, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(RandomTestUtil.randomBytes()), 67, null,
			null, null, serviceContext);

		_dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		_objectField = ObjectFieldUtil.createObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
			ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
			RandomTestUtil.randomString(), "attachment",
			Arrays.asList(
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS
				).value(
					"jpg, jpeg, png, svg, txt"
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_FILE_SOURCE
				).value(
					ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
				).build(),
				new ObjectFieldSettingBuilder(
				).name(
					ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
				).value(
					"100"
				).build()),
			false);

		_objectField.setExternalReferenceCode(RandomTestUtil.randomString());

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(_objectField));

		_objectEntry = _objectEntryLocalService.addOrUpdateObjectEntry(
			RandomTestUtil.randomString(), 0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.<String, Serializable>put(
				"attachment", String.valueOf(_dlFileEntry.getFileEntryId())
			).build(),
			serviceContext);

		_themeDisplay.setCompany(_company);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(
				HttpMethod.GET,
				ObjectFieldUtil.getAttachmentDownloadURL(
					_dlURLHelper, fileEntry, 0,
					_objectDefinition.getExternalReferenceCode(), _objectEntry,
					_objectEntryService, _objectField,
					GuestOrUserUtil.getPermissionChecker(), _themeDisplay));

		mockHttpServletRequest.addParameter("download", "true");
		mockHttpServletRequest.addParameter(
			"objectDefinitionExternalReferenceCode",
			_objectDefinition.getExternalReferenceCode());
		mockHttpServletRequest.addParameter(
			"objectEntryExternalReferenceCode",
			_objectEntry.getExternalReferenceCode());
		mockHttpServletRequest.addParameter(
			"objectFieldExternalReferenceCode",
			_objectField.getExternalReferenceCode());

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_role = _roleLocalService.getRole(
			_company.getCompanyId(), RoleConstants.GUEST);

		_user = UserLocalServiceUtil.getGuestUser(_company.getCompanyId());
	}

	@After
	public void tearDown() throws PortalException {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testContains() throws Exception {
		String actionId = _objectField.getAttachmentDownloadActionKey();

		Assert.assertFalse(
			_dlFileEntryModelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(_user), _dlFileEntry,
				ActionKeys.DOWNLOAD));

		_testContains(new String[] {ActionKeys.VIEW}, false);
		_testContains(new String[] {actionId}, false);
		_testContains(new String[] {ActionKeys.VIEW, actionId}, true);

		_resourcePermissionLocalService.setResourcePermissions(
			_company.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_dlFileEntry.getFileEntryId()), _role.getRoleId(),
			new String[0]);

		Assert.assertFalse(
			_dlFileEntryModelResourcePermission.contains(
				PermissionCheckerFactoryUtil.create(_user), _dlFileEntry,
				ActionKeys.DOWNLOAD));
	}

	private void _testContains(String[] actionIds, boolean assertTrue)
		throws Exception {

		_resourcePermissionLocalService.setResourcePermissions(
			_company.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_objectEntry.getObjectEntryId()), _role.getRoleId(),
			actionIds);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		if (assertTrue) {
			Assert.assertTrue(
				_dlFileEntryModelResourcePermission.contains(
					permissionChecker, _dlFileEntry, ActionKeys.DOWNLOAD));
		}
		else {
			Assert.assertFalse(
				_dlFileEntryModelResourcePermission.contains(
					permissionChecker, _dlFileEntry, ActionKeys.DOWNLOAD));
		}
	}

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	)
	private static ModelResourcePermission<DLFileEntry>
		_dlFileEntryModelResourcePermission;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private DLFileEntry _dlFileEntry;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryService _objectEntryService;

	private ObjectField _objectField;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	private final ThemeDisplay _themeDisplay = new ThemeDisplay();
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}