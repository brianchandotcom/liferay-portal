/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.model.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
import com.liferay.site.dsr.site.initializer.test.util.DSRLayoutTestUtil;
import com.liferay.site.dsr.site.initializer.test.util.DSRTestUtil;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class ObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			StringPool.BLANK, TestPropsValues.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, ServiceContextTestUtil.getServiceContext());
		_group = DSRTestUtil.getOrAddGroup(ObjectEntryModelListenerTest.class);
		_objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());
	}

	@Test
	public void testOnAfterCreate() throws Exception {
		String name = StringUtil.toLowerCase(
			"A" + RandomTestUtil.randomString());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"name", name
			).put(
				"r_accountToDSRRooms_accountEntryId",
				_accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Group group = _groupLocalService.fetchGroup(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(
				_objectDefinition.getClassName()),
			objectEntry.getObjectEntryId());

		Assert.assertEquals("/" + name, group.getFriendlyURL());
		Assert.assertEquals(
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			group.getMembershipRestriction());
		Assert.assertEquals(name, group.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			GroupConstants.TYPE_SITE_RESTRICTED, group.getType());
		Assert.assertTrue(group.isManualMembership());
		Assert.assertTrue(group.isSite());

		objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntry.getObjectEntryId());

		Map<String, Serializable> values = objectEntry.getValues();

		Assert.assertEquals(
			group.getExternalReferenceCode(),
			values.get("siteExternalReferenceCode"));
		Assert.assertEquals(group.getGroupId(), values.get("siteId"));

		String friendlyURL = StringUtil.toLowerCase(
			"A" + RandomTestUtil.randomString());

		objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"friendlyURL", friendlyURL
			).put(
				"name", name
			).put(
				"r_accountToDSRRooms_accountEntryId",
				_accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		group = _groupLocalService.fetchGroup(
			TestPropsValues.getCompanyId(),
			_classNameLocalService.getClassNameId(
				_objectDefinition.getClassName()),
			objectEntry.getObjectEntryId());

		Assert.assertEquals("/" + friendlyURL, group.getFriendlyURL());
		Assert.assertEquals(name, group.getName(LocaleUtil.getDefault()));

		DSRLayoutTestUtil.assertFragmentEntryLink(group.getGroupId());
		DSRLayoutTestUtil.assertLayouts(
			group.getGroupId(),
			new String[] {"Documents", "Login", "Onboarding"}, false);

		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		String[] actionIds = TransformUtil.transformToArray(
			_resourceActionLocalService.getResourceActions(
				objectDefinition.getClassName()),
			ResourceAction::getActionId, String.class);

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.OWNER);

		for (String actionId : actionIds) {
			_assertHasResourcePermission(
				actionId, objectEntry, role.getRoleId());
		}

		role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_MEMBER);

		_assertHasResourcePermission(
			ActionKeys.ADD_DISCUSSION, objectEntry, role.getRoleId());
		_assertHasResourcePermission(
			ActionKeys.VIEW, objectEntry, role.getRoleId());

		role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_OWNER);

		for (String actionId : actionIds) {
			_assertHasResourcePermission(
				actionId, objectEntry, role.getRoleId());
		}

		role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), "DSR Contributor");

		_assertHasResourcePermission(
			ActionKeys.ADD_DISCUSSION, objectEntry, role.getRoleId());
		_assertHasResourcePermission(
			ActionKeys.VIEW, objectEntry, role.getRoleId());
	}

	@Test
	public void testOnAfterRemove() throws Exception {
		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"name", "A" + RandomTestUtil.randomString()
			).put(
				"r_accountToDSRRooms_accountEntryId",
				_accountEntry.getAccountEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(
			_groupLocalService.fetchGroup(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(
					_objectDefinition.getClassName()),
				objectEntry.getObjectEntryId()));

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());

		Assert.assertNull(
			_groupLocalService.fetchGroup(
				TestPropsValues.getCompanyId(),
				_classNameLocalService.getClassNameId(
					_objectDefinition.getClassName()),
				objectEntry.getObjectEntryId()));
	}

	private void _assertHasResourcePermission(
			String actionId, ObjectEntry objectEntry, long roleId)
		throws Exception {

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				objectEntry.getCompanyId(), objectEntry.getModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(objectEntry.getObjectEntryId()), roleId,
				actionId));
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}