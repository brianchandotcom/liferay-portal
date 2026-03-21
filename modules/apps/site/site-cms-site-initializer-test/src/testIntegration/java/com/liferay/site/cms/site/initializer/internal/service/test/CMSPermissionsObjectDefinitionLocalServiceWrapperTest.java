/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class CMSPermissionsObjectDefinitionLocalServiceWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		CMSTestUtil.getOrAddGroup(
			CMSPermissionsObjectDefinitionLocalServiceWrapperTest.class);
	}

	@Test
	public void testPublishCustomObjectDefinition() throws Exception {
		_testPublishCustomObjectDefinitionInContentStructuresFolder();
		_testPublishCustomObjectDefinitionInDefaultFolder();
		_testPublishCustomObjectDefinitionInFileTypesFolder();
	}

	private void _assertCMSAdministratorObjectDefinitionPermissions(
			ObjectDefinition objectDefinition)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();

		Role cmsAdministratorRole = _roleLocalService.getRole(
			companyId, RoleConstants.CMS_ADMINISTRATOR);

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				companyId, objectDefinition.getResourceName(),
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				cmsAdministratorRole.getRoleId(),
				ObjectActionKeys.ADD_OBJECT_ENTRY));

		for (String actionId :
				List.of(
					ActionKeys.DELETE, ActionKeys.PERMISSIONS,
					ActionKeys.UPDATE, ActionKeys.VIEW)) {

			Assert.assertTrue(
				_resourcePermissionLocalService.hasResourcePermission(
					companyId, objectDefinition.getClassName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					cmsAdministratorRole.getRoleId(), actionId));
		}
	}

	private void _assertObjectDefinitionViewPermission(
			ObjectDefinition objectDefinition, String roleName)
		throws Exception {

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), roleName);

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				TestPropsValues.getCompanyId(),
				ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId()),
				role.getRoleId(), ActionKeys.VIEW));
	}

	private ObjectDefinition _publishCustomObjectDefinition(long objectFolderId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, TestPropsValues.getUserId(), objectFolderId, null, false,
				true, false, true, false, false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_DEPOT,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(),
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING,
						RandomTestUtil.randomString(), "text")),
				Collections.emptyList(), new ServiceContext());

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

		_objectDefinitions.add(objectDefinition);

		return objectDefinition;
	}

	private ObjectDefinition _publishCustomObjectDefinition(
			String objectFolderExternalReferenceCode)
		throws Exception {

		ObjectFolder objectFolder =
			_objectFolderLocalService.getObjectFolderByExternalReferenceCode(
				objectFolderExternalReferenceCode,
				TestPropsValues.getCompanyId());

		return _publishCustomObjectDefinition(objectFolder.getObjectFolderId());
	}

	private void _testPublishCustomObjectDefinitionInContentStructuresFolder()
		throws Exception {

		ObjectDefinition objectDefinition = _publishCustomObjectDefinition(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES);

		_assertCMSAdministratorObjectDefinitionPermissions(objectDefinition);
		_assertObjectDefinitionViewPermission(
			objectDefinition, RoleConstants.GUEST);
		_assertObjectDefinitionViewPermission(
			objectDefinition, RoleConstants.USER);
	}

	private void _testPublishCustomObjectDefinitionInDefaultFolder()
		throws Exception {

		ObjectDefinition objectDefinition = _publishCustomObjectDefinition(0);

		long companyId = TestPropsValues.getCompanyId();

		Role cmsAdministratorRole = _roleLocalService.fetchRole(
			companyId, RoleConstants.CMS_ADMINISTRATOR);

		if (cmsAdministratorRole != null) {
			Assert.assertFalse(
				_resourcePermissionLocalService.hasResourcePermission(
					companyId, objectDefinition.getResourceName(),
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					cmsAdministratorRole.getRoleId(),
					ObjectActionKeys.ADD_OBJECT_ENTRY));
		}

		Role guestRole = _roleLocalService.getRole(
			companyId, RoleConstants.GUEST);

		Assert.assertFalse(
			_resourcePermissionLocalService.hasResourcePermission(
				companyId, ObjectDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectDefinition.getObjectDefinitionId()),
				guestRole.getRoleId(), ActionKeys.VIEW));
	}

	private void _testPublishCustomObjectDefinitionInFileTypesFolder()
		throws Exception {

		ObjectDefinition objectDefinition = _publishCustomObjectDefinition(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES);

		_assertCMSAdministratorObjectDefinitionPermissions(objectDefinition);
		_assertObjectDefinitionViewPermission(
			objectDefinition, RoleConstants.GUEST);
		_assertObjectDefinitionViewPermission(
			objectDefinition, RoleConstants.USER);
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private List<ObjectDefinition> _objectDefinitions = new ArrayList<>();

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}