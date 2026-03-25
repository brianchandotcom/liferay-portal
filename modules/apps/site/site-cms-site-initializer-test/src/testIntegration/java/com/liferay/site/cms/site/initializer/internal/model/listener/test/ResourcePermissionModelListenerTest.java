/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;
import com.liferay.site.cms.site.initializer.util.CMSDefaultPermissionUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ResourcePermissionModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMSTestUtil.getOrAddGroup(ResourcePermissionModelListenerTest.class);

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		_cmsAdministratorRole = _getOrAddCMSAdministratorRole(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_group = _depotEntry.getGroup();

		_objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					_group.getGroupId(), _group.getCompanyId());
	}

	@Test
	public void testOnAfterCreate() throws Exception {
		Role role = _roleLocalService.fetchRole(
			_group.getCompanyId(), RoleConstants.USER);

		_resourcePermissionLocalService.setResourcePermissions(
			_depotEntry.getCompanyId(), DepotEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_depotEntry.getDepotEntryId()), role.getRoleId(),
			new String[] {ActionKeys.PERMISSIONS, ActionKeys.VIEW});

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			_depotEntry.getCompanyId(), _depotEntry.getUserId(),
			_group.getExternalReferenceCode(), DepotEntry.class.getName(),
			_filterFactory);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _group.getGroupId(),
				_group.getCreatorUserId(),
				_objectEntryFolder.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		_resourcePermissionLocalService.setResourcePermissions(
			objectEntryFolder.getCompanyId(), ObjectEntryFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
			_cmsAdministratorRole.getRoleId(),
			new String[] {
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.VIEW
			});

		objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			objectEntryFolder.getExternalReferenceCode(),
			ObjectEntryFolder.class.getName(), _filterFactory);

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));
	}

	@Test
	public void testOnAfterUpdate() throws Exception {
		Role role = _roleLocalService.fetchRole(
			_group.getCompanyId(), RoleConstants.USER);

		_resourcePermissionLocalService.setResourcePermissions(
			_depotEntry.getCompanyId(), DepotEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_depotEntry.getDepotEntryId()), role.getRoleId(),
			new String[] {ActionKeys.PERMISSIONS, ActionKeys.VIEW});

		ObjectEntry objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			_depotEntry.getCompanyId(), _depotEntry.getUserId(),
			_group.getExternalReferenceCode(), DepotEntry.class.getName(),
			_filterFactory);

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		_resourcePermissionLocalService.setResourcePermissions(
			_depotEntry.getCompanyId(), DepotEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_depotEntry.getDepotEntryId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		_resourcePermissionLocalService.setResourcePermissions(
			_depotEntry.getCompanyId(), DepotEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_depotEntry.getDepotEntryId()), role.getRoleId(),
			new String[] {ActionKeys.PERMISSIONS, ActionKeys.VIEW});

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				role.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.addObjectEntryFolder(
				RandomTestUtil.randomString(), _group.getGroupId(),
				_group.getCreatorUserId(),
				_objectEntryFolder.getObjectEntryFolderId(), "",
				HashMapBuilder.put(
					LocaleUtil.ENGLISH, RandomTestUtil.randomString()
				).build(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext());

		_resourcePermissionLocalService.setResourcePermissions(
			objectEntryFolder.getCompanyId(), ObjectEntryFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
			_cmsAdministratorRole.getRoleId(),
			new String[] {
				ActionKeys.DELETE, ActionKeys.UPDATE, ActionKeys.VIEW
			});

		objectEntry = CMSDefaultPermissionUtil.fetchObjectEntry(
			objectEntryFolder.getCompanyId(), objectEntryFolder.getUserId(),
			objectEntryFolder.getExternalReferenceCode(),
			ObjectEntryFolder.class.getName(), _filterFactory);

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));

		_resourcePermissionLocalService.setResourcePermissions(
			objectEntryFolder.getCompanyId(), ObjectEntryFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
			_cmsAdministratorRole.getRoleId(),
			new String[] {
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.VIEW
			});

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertTrue(resourcePermission.hasActionId(ActionKeys.VIEW));

		_resourcePermissionLocalService.setResourcePermissions(
			objectEntryFolder.getCompanyId(), ObjectEntryFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(objectEntryFolder.getObjectEntryFolderId()),
			_cmsAdministratorRole.getRoleId(), new String[] {ActionKeys.VIEW});

		resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()),
				_cmsAdministratorRole.getRoleId());

		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.DELETE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.UPDATE));
		Assert.assertFalse(resourcePermission.hasActionId(ActionKeys.VIEW));
	}

	private Role _getOrAddCMSAdministratorRole(long companyId, long userId)
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.CMS_ADMINISTRATOR);

		if (role != null) {
			return role;
		}

		return _roleLocalService.addRole(
			null, userId, null, 0, RoleConstants.CMS_ADMINISTRATOR, null, null,
			RoleConstants.TYPE_REGULAR, null, null);
	}

	private Role _cmsAdministratorRole;
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private FilterFactory<Predicate> _filterFactory;

	@DeleteAfterTestRun
	private Group _group;

	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}