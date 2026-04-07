/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.rest.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.site.cms.site.initializer.test.util.CMSTestUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tancredi Covioli
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag(value = "LPD-17564"), @FeatureFlag(value = "LPS-164801")
	}
)
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMSTestUtil.getOrAddGroup(ObjectEntryResourceTest.class);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), DepotConstants.TYPE_SPACE,
			serviceContext);

		_objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", TestPropsValues.getCompanyId());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			_depotEntry.getGroupId());

		serviceContext.setAttribute(
			"friendlyUrlMap", new HashMap<String, String>());

		_objectEntryFolder = _addObjectEntryFolder(
			_depotEntry, _depotEntry.getUserId(), serviceContext);

		_objectEntry = _addObjectEntry(
			_depotEntry, _objectDefinition, _depotEntry.getUserId(),
			serviceContext);

		_password = RandomTestUtil.randomString();

		_user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			_password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryFolderShareActionAsCreator()
		throws Exception {

		_objectEntryFolder = _addObjectEntryFolder(
			_depotEntry, _user.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), _user.getUserId()));

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryFolderShareActionWithCMSAdminRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		_roleLocalService.addUserRole(_user.getUserId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryFolderShareActionWithContentReviewerRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		Assert.assertFalse(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryFolderShareActionWithoutRole()
		throws Exception {

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		Assert.assertFalse(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryFolderShareActionWithSpaceAdminRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryShareActionAsCreator() throws Exception {
		_objectEntry = _addObjectEntry(
			_depotEntry, _objectDefinition, _user.getUserId(),
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), _user.getUserId()));

		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryShareActionWithCMSAdminRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.CMS_ADMINISTRATOR);

		_roleLocalService.addUserRole(_user.getUserId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryShareActionWithContentReviewerRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		Assert.assertFalse(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryShareActionWithoutRole() throws Exception {
		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		Assert.assertFalse(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetObjectEntryShareActionWithSpaceAdminRole()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetSharedObjectEntryFolderShareActionAsContentReviewer()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, _user.getUserId(),
			_classNameLocalService.getClassNameId(
				_objectEntryFolder.getModelClassName()),
			_objectEntryFolder.getObjectEntryFolderId(),
			_depotEntry.getGroupId(), true, List.of(SharingEntryAction.VIEW),
			null,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		JSONObject actionsJSONObject = _getObjectEntryFolderActionsJSONObject();

		_sharingEntryLocalService.deleteSharingEntry(
			sharingEntry.getSharingEntryId());

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	@Test
	@TestInfo("LPD-83639")
	public void testGetSharedObjectEntryShareActionAsContentReviewer()
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(),
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER);

		UserGroupRoleLocalServiceUtil.addUserGroupRole(
			_user.getUserId(), _depotEntry.getGroupId(), role.getRoleId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, _user.getUserId(),
			_classNameLocalService.getClassNameId(
				_objectEntry.getModelClassName()),
			_objectEntry.getObjectEntryId(), _depotEntry.getGroupId(), true,
			List.of(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				_depotEntry.getGroupId(), TestPropsValues.getUserId()));

		JSONObject actionsJSONObject = _getObjectEntryActionsJSONObject();

		_sharingEntryLocalService.deleteSharingEntry(
			sharingEntry.getSharingEntryId());

		Assert.assertTrue(actionsJSONObject.has("share"));
	}

	private ObjectEntry _addObjectEntry(
			DepotEntry depotEntry, ObjectDefinition objectDefinition,
			long userId, ServiceContext serviceContext)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), userId,
			objectDefinition.getObjectDefinitionId(),
			_objectEntryFolder.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString()
				).build()
			).build(),
			serviceContext);
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			DepotEntry depotEntry, long userId, ServiceContext serviceContext)
		throws Exception {

		ObjectEntryFolder contentsFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					depotEntry.getGroupId(), depotEntry.getCompanyId());

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			null, depotEntry.getGroupId(), userId,
			contentsFolder.getObjectEntryFolderId(),
			RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString(), serviceContext);
	}

	private JSONObject _getObjectEntryActionsJSONObject() throws Exception {
		return _invokeGetActionsJSONObject(
			_objectDefinition.getRESTContextPath() + StringPool.SLASH +
				_objectEntry.getObjectEntryId());
	}

	private JSONObject _getObjectEntryFolderActionsJSONObject()
		throws Exception {

		return _invokeGetActionsJSONObject(
			"headless-object/v1.0/object-entry-folders/" +
				_objectEntryFolder.getObjectEntryFolderId());
	}

	private JSONObject _invokeGetActionsJSONObject(String path)
		throws Exception {

		AtomicReference<JSONObject> actionsAtom = new AtomicReference<>();

		HTTPTestUtil.customize(
		).withCredentials(
			_user.getEmailAddress(), _password
		).apply(
			() -> {
				JSONObject responseJSONObject = HTTPTestUtil.invokeToJSONObject(
					null, path, Http.Method.GET);

				actionsAtom.set(responseJSONObject.getJSONObject("actions"));
			}
		);

		return actionsAtom.get();
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	private ObjectDefinition _objectDefinition;
	private ObjectEntry _objectEntry;
	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private String _password;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	private User _user;

}