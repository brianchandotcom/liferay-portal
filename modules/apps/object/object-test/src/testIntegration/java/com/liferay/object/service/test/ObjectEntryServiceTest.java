/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.definition.tree.Edge;
import com.liferay.object.definition.tree.Node;
import com.liferay.object.definition.tree.Tree;
import com.liferay.object.definition.tree.TreeFactory;
import com.liferay.object.definition.tree.constants.TreeConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.service.test.util.TreeTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Leo
 */
@FeatureFlags("LPS-187142")
@RunWith(Arquillian.class)
public class ObjectEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_adminUser = TestPropsValues.getUser();
		_guestUser = _userLocalService.getGuestUser(
			TestPropsValues.getCompanyId());

		_objectDefinition = ObjectDefinitionTestUtil.addCustomObjectDefinition(
			false, _objectDefinitionLocalService,
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"First Name", "firstName", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					"Last Name", "lastName", false)));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		_tree = TreeTestUtil.createTree(
			_objectDefinitionLocalService, _objectRelationshipLocalService,
			_treeFactory);

		_rootObjectDefinition = _publishRootObjectDefinition();

		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		TreeTestUtil.deleteObjectDefinitionHierarchy(
			_objectDefinitionLocalService);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_setUser(_adminUser);

		Assert.assertNotNull(
			_objectEntryService.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), _adminUser.getUserId())));

		_setUser(_guestUser);

		_assertPrincipalException(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition, null);

		_setUser(_user);

		_assertPrincipalException(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition, null);

		_setUser(_guestUser);

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			guestRole.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY);

		Assert.assertNotNull(
			_objectEntryService.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), _guestUser.getUserId())));

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			guestRole.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY);

		_setUser(_user);

		Assert.assertNotNull(
			_objectEntryService.addObjectEntry(
				0, _objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"firstName", RandomStringUtils.randomAlphabetic(5)
				).build(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), _guestUser.getUserId())));
	}

	@Test
	public void testAddObjectEntryHierarchy() throws Exception {

		// Root company permissions must be inherited

		_setUser(_user);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		Map<Long, ObjectEntry> objectEntries1 = _addObjectEntryHierarchy(_tree);

		ObjectEntry rootObjectEntry = objectEntries1.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_setUser(_adminUser);

		_objectEntryService.deleteObjectEntry(
			rootObjectEntry.getObjectEntryId());

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		// Root descendant permissions must not be considered

		_setUser(_user);

		TreeTestUtil.unsafeForEach(
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				if (objectDefinition.isRootNode()) {
					return;
				}

				_resourcePermissionLocalService.addResourcePermission(
					TestPropsValues.getCompanyId(),
					objectDefinition.getResourceName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					role.getRoleId(), ObjectActionKeys.ADD_OBJECT_ENTRY);

				_assertPrincipalException(
					ObjectActionKeys.ADD_OBJECT_ENTRY, objectDefinition, null);
			});
	}

	@Test
	public void testDeleteObjectEntry() throws Exception {
		try {
			_testDeleteObjectEntry(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteObjectEntry(_adminUser, _adminUser);
		_testDeleteObjectEntry(_user, _user);
	}

	@Test
	public void testDeleteObjectEntryHierarchy() throws Exception {

		// Root company permissions must be inherited

		Map<Long, ObjectEntry> objectEntries1 = _addObjectEntryHierarchy(_tree);

		_setUser(_user);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.DELETE);

		TreeTestUtil.unsafeForEach(
			TreeConstants.ITERATOR_TYPE_POST_ORDER,
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				ObjectEntry objectEntry = objectEntries1.get(
					objectDefinition.getObjectDefinitionId());

				Assert.assertNotNull(
					_objectEntryService.deleteObjectEntry(
						objectEntry.getObjectEntryId()));
			});

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.DELETE);

		// Root descendant permissions must not be considered

		Map<Long, ObjectEntry> objectEntries2 = _addObjectEntryHierarchy(_tree);

		TreeTestUtil.unsafeForEach(
			TreeConstants.ITERATOR_TYPE_POST_ORDER,
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				if (objectDefinition.isRootNode()) {
					return;
				}

				_resourcePermissionLocalService.addResourcePermission(
					TestPropsValues.getCompanyId(),
					objectDefinition.getClassName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					role.getRoleId(), ActionKeys.DELETE);

				ObjectEntry objectEntry = objectEntries2.get(
					objectDefinition.getObjectDefinitionId());

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(),
					objectDefinition.getClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(objectEntry.getObjectEntryId()),
					role.getRoleId(), new String[] {ActionKeys.DELETE});

				_assertPrincipalException(ActionKeys.DELETE, null, objectEntry);
			});

		ObjectEntry rootObjectEntry = objectEntries2.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_setUser(_adminUser);

		_objectEntryService.deleteObjectEntry(
			rootObjectEntry.getObjectEntryId());

		// Root individual permissions must be inherited

		_setUser(_user);

		Map<Long, ObjectEntry> objectEntries3 = _addObjectEntryHierarchy(_tree);

		rootObjectEntry = objectEntries3.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(rootObjectEntry.getObjectEntryId()),
			role.getRoleId(), new String[] {ActionKeys.DELETE});

		TreeTestUtil.unsafeForEach(
			TreeConstants.ITERATOR_TYPE_POST_ORDER,
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				ObjectEntry objectEntry = objectEntries3.get(
					objectDefinition.getObjectDefinitionId());

				Assert.assertNotNull(
					_objectEntryService.deleteObjectEntry(
						objectEntry.getObjectEntryId()));
			});
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		_setUser(_adminUser);

		ObjectEntry adminObjectEntry = _addObjectEntry(_adminUser);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				adminObjectEntry.getObjectEntryId()));

		_setUser(_user);

		ObjectEntry userObjectEntry = _addObjectEntry(_user);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				userObjectEntry.getObjectEntryId()));

		_assertPrincipalException(ActionKeys.VIEW, null, adminObjectEntry);

		_setUser(_guestUser);

		_assertPrincipalException(ActionKeys.VIEW, null, adminObjectEntry);

		ObjectEntry guestUserObjectEntry = _addObjectEntry(_guestUser);

		_assertPrincipalException(ActionKeys.VIEW, null, guestUserObjectEntry);

		Role guestRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), _objectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			guestRole.getRoleId(), ActionKeys.VIEW);

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(
				adminObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testGetObjectEntryHierarchy() throws Exception {

		// Root company permissions must be inherited

		Map<Long, ObjectEntry> objectEntries1 = _addObjectEntryHierarchy(_tree);

		_setUser(_user);

		Role role = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.USER);

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		TreeTestUtil.unsafeForEach(
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				ObjectEntry objectEntry = objectEntries1.get(
					objectDefinition.getObjectDefinitionId());

				Assert.assertNotNull(
					_objectEntryService.getObjectEntry(
						objectEntry.getObjectEntryId()));
			});

		_resourcePermissionLocalService.removeResourcePermission(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			ActionKeys.VIEW);

		ObjectEntry rootObjectEntry = objectEntries1.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_setUser(_adminUser);

		_objectEntryService.deleteObjectEntry(
			rootObjectEntry.getObjectEntryId());

		// Root descendant permissions must not be considered

		Map<Long, ObjectEntry> objectEntries2 = _addObjectEntryHierarchy(_tree);

		_setUser(_user);

		TreeTestUtil.unsafeForEach(
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				if (objectDefinition.isRootNode()) {
					return;
				}

				_resourcePermissionLocalService.addResourcePermission(
					TestPropsValues.getCompanyId(),
					objectDefinition.getClassName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					role.getRoleId(), ActionKeys.VIEW);

				ObjectEntry objectEntry = objectEntries2.get(
					objectDefinition.getObjectDefinitionId());

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(),
					objectDefinition.getClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(objectEntry.getObjectEntryId()),
					role.getRoleId(), new String[] {ActionKeys.VIEW});

				_assertPrincipalException(ActionKeys.VIEW, null, objectEntry);
			});

		rootObjectEntry = objectEntries2.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_setUser(_adminUser);

		_objectEntryService.deleteObjectEntry(
			rootObjectEntry.getObjectEntryId());

		// Root individual permissions must be inherited

		_setUser(_user);

		Map<Long, ObjectEntry> objectEntries3 = _addObjectEntryHierarchy(_tree);

		rootObjectEntry = objectEntries3.get(
			_rootObjectDefinition.getObjectDefinitionId());

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			_rootObjectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(rootObjectEntry.getObjectEntryId()),
			role.getRoleId(), new String[] {ActionKeys.VIEW});

		TreeTestUtil.unsafeForEach(
			_objectDefinitionLocalService, _tree,
			objectDefinition -> {
				ObjectEntry objectEntry = objectEntries3.get(
					objectDefinition.getObjectDefinitionId());

				Assert.assertNotNull(
					_objectEntryService.getObjectEntry(
						objectEntry.getObjectEntryId()));
			});
	}

	@Test
	public void testGetOrDeleteObjectEntryWithAccountEntryRestricted()
		throws Exception {

		_objectDefinition.setAccountEntryRestricted(true);

		ObjectDefinition accountEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "accountEntry");

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				accountEntryObjectDefinition.getObjectDefinitionId(),
				_objectDefinition.getObjectDefinitionId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"relationship", ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectDefinition.setAccountEntryRestrictedObjectFieldId(
			objectRelationship.getObjectFieldId2());

		_objectDefinition =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition);

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, "account", null,
			null, null, null, null,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"r_relationship_accountEntryId",
				accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		_setUser(_user);

		_resourcePermissionLocalService.addModelResourcePermissions(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			_user.getUserId(), _objectDefinition.getClassName(),
			String.valueOf(objectEntry.getObjectEntryId()),
			ModelPermissionsFactory.create(
				HashMapBuilder.put(
					RoleConstants.USER,
					new String[] {ActionKeys.DELETE, ActionKeys.VIEW}
				).build(),
				_objectDefinition.getClassName()));

		Assert.assertNotNull(
			_objectEntryService.getObjectEntry(objectEntry.getObjectEntryId()));

		_objectEntryService.deleteObjectEntry(objectEntry.getObjectEntryId());

		_accountEntryLocalService.deleteAccountEntry(accountEntry);
	}

	@Test
	public void testSearchObjectEntries() throws Exception {
		_setUser(_adminUser);

		ObjectEntry objectEntry1 = _addObjectEntry(_adminUser);
		ObjectEntry objectEntry2 = _addObjectEntry(_adminUser);

		BaseModelSearchResult<ObjectEntry> baseModelSearchResult =
			_objectEntryLocalService.searchObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(2, baseModelSearchResult.getLength());

		_setUser(_user);

		baseModelSearchResult = _objectEntryLocalService.searchObjectEntries(
			0, _objectDefinition.getObjectDefinitionId(), null, 0, 20);

		Assert.assertEquals(0, baseModelSearchResult.getLength());

		_objectEntryLocalService.deleteObjectEntry(objectEntry1);
		_objectEntryLocalService.deleteObjectEntry(objectEntry2);
	}

	private ObjectEntry _addObjectEntry(User user) throws Exception {
		return _objectEntryLocalService.addObjectEntry(
			user.getUserId(), 0, _objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomStringUtils.randomAlphabetic(5)
			).put(
				"LastName", RandomStringUtils.randomAlphabetic(5)
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), user.getUserId()));
	}

	private Map<Long, ObjectEntry> _addObjectEntryHierarchy(Tree tree)
		throws Exception {

		Iterator<Node> iterator = tree.iterator();

		Node rootNode = iterator.next();

		Map<Long, ObjectEntry> objectEntries =
			HashMapBuilder.<Long, ObjectEntry>put(
				rootNode.getObjectDefinitionId(),
				_objectEntryLocalService.addObjectEntry(
					_adminUser.getUserId(), 0, rootNode.getObjectDefinitionId(),
					Collections.emptyMap(),
					ServiceContextTestUtil.getServiceContext(
						TestPropsValues.getGroupId(), _adminUser.getUserId()))
			).build();

		while (iterator.hasNext()) {
			Node node = iterator.next();

			objectEntries.put(
				node.getObjectDefinitionId(),
				_objectEntryLocalService.addObjectEntry(
					_adminUser.getUserId(), 0, node.getObjectDefinitionId(),
					HashMapBuilder.<String, Serializable>put(
						() -> {
							Edge edge = node.getEdge();

							ObjectRelationship objectRelationship =
								_objectRelationshipLocalService.
									getObjectRelationship(
										edge.getObjectRelationshipId());

							ObjectField objectField =
								_objectFieldLocalService.getObjectField(
									objectRelationship.getObjectFieldId2());

							return objectField.getName();
						},
						() -> {
							Node parentNode = node.getParentNode();

							ObjectEntry objectEntry = objectEntries.get(
								parentNode.getObjectDefinitionId());

							return objectEntry.getObjectEntryId();
						}
					).build(),
					ServiceContextTestUtil.getServiceContext(
						TestPropsValues.getGroupId(), _adminUser.getUserId())));
		}

		return objectEntries;
	}

	private void _assertPrincipalException(
			String action, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			if (Objects.equals(action, ActionKeys.DELETE)) {
				_objectEntryService.deleteObjectEntry(
					objectEntry.getObjectEntryId());
			}
			else if (Objects.equals(action, ActionKeys.VIEW)) {
				_objectEntryService.getObjectEntry(
					objectEntry.getObjectEntryId());
			}
			else {
				_objectEntryService.addObjectEntry(
					0, objectDefinition.getObjectDefinitionId(),
					Collections.emptyMap(),
					ServiceContextTestUtil.getServiceContext(
						TestPropsValues.getGroupId(),
						permissionChecker.getUserId()));
			}

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					StringBundler.concat(
						"User ", permissionChecker.getUserId(), " must have ",
						action, " permission for")));
		}
	}

	private ObjectDefinition _publishRootObjectDefinition() throws Exception {
		ObjectDefinition rootObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), "C_A");

		return _objectDefinitionLocalService.publishCustomObjectDefinition(
			_adminUser.getUserId(),
			rootObjectDefinition.getObjectDefinitionId());
	}

	private void _setUser(User user) throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testDeleteObjectEntry(User ownerUser, User user)
		throws Exception {

		ObjectEntry deleteObjectEntry = null;
		ObjectEntry objectEntry = null;

		try {
			_setUser(user);

			objectEntry = _addObjectEntry(ownerUser);

			deleteObjectEntry = _objectEntryService.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}
		finally {
			if (deleteObjectEntry == null) {
				_objectEntryLocalService.deleteObjectEntry(objectEntry);
			}
		}
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private User _adminUser;
	private User _guestUser;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryService _objectEntryService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ObjectDefinition _rootObjectDefinition;
	private Tree _tree;

	@Inject
	private TreeFactory _treeFactory;

	private User _user;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}