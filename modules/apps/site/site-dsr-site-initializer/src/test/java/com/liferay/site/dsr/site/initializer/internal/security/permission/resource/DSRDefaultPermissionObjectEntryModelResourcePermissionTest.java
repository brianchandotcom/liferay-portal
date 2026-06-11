/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.internal.security.permission.resource;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Balazs Breier
 */
public class DSRDefaultPermissionObjectEntryModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_dsrDefaultPermissionObjectEntryModelResourcePermission =
			new DSRDefaultPermissionObjectEntryModelResourcePermission(
				_modelResourcePermission, _objectEntryLocalService);

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_objectEntry.getObjectDefinition()
		).thenReturn(
			_objectDefinition
		);
	}

	@Test
	public void testCheckWhenContainsIsFalseThrowsMustHavePermission()
		throws Exception {

		_stubInactiveRoom(0L);

		try {
			_dsrDefaultPermissionObjectEntryModelResourcePermission.check(
				_permissionChecker, _objectEntry, ActionKeys.VIEW);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
		}
	}

	@Test
	public void testContainsLongPrimaryKeyWhenObjectEntryIsNullReturnsFalse()
		throws Exception {

		long primaryKey = RandomTestUtil.randomLong();

		Mockito.when(
			_objectEntryLocalService.fetchObjectEntry(primaryKey)
		).thenReturn(
			null
		);

		Assert.assertFalse(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, primaryKey, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenInactiveAndIsCompanyAdminReturnsTrue()
		throws Exception {

		_stubInactiveRoom(RandomTestUtil.randomLong());

		Mockito.when(
			_permissionChecker.isCompanyAdmin()
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenInactiveAndIsGroupOwnerOfSiteReturnsTrue()
		throws Exception {

		long siteId = RandomTestUtil.randomLong();

		_stubInactiveRoom(siteId);

		Mockito.when(
			_permissionChecker.isGroupOwner(siteId)
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenInactiveAndNeitherAdminNorOwnerReturnsFalse()
		throws Exception {

		_stubInactiveRoom(RandomTestUtil.randomLong());

		Assert.assertFalse(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenInactiveAndSiteIdIsZeroReturnsFalse()
		throws Exception {

		_stubInactiveRoom(0L);

		Mockito.when(
			_permissionChecker.isGroupOwner(Mockito.anyLong())
		).thenReturn(
			true
		);

		Assert.assertFalse(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenNotInactiveAndHasOwnerPermissionReturnsTrue()
		throws Exception {

		_stubActiveRoom();

		Mockito.when(
			_permissionChecker.hasOwnerPermission(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenNotInactiveAndHasPermissionReturnsTrue()
		throws Exception {

		_stubActiveRoom();

		Mockito.when(
			_permissionChecker.hasPermission(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyString())
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenNotInactiveAndIsGroupMemberForAddDiscussionReturnsTrue()
		throws Exception {

		long siteId = RandomTestUtil.randomLong();

		_stubActiveRoom(siteId);

		Mockito.when(
			_permissionChecker.isGroupMember(siteId)
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.ADD_DISCUSSION));
	}

	@Test
	public void testContainsWhenNotInactiveAndIsGroupMemberForViewReturnsTrue()
		throws Exception {

		long siteId = RandomTestUtil.randomLong();

		_stubActiveRoom(siteId);

		Mockito.when(
			_permissionChecker.isGroupMember(siteId)
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.VIEW));
	}

	@Test
	public void testContainsWhenNotInactiveAndOtherActionDelegatesToWrapped()
		throws Exception {

		_stubActiveRoom();

		Mockito.when(
			_modelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.UPDATE)
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_dsrDefaultPermissionObjectEntryModelResourcePermission.contains(
				_permissionChecker, _objectEntry, ActionKeys.UPDATE));
	}

	@Test
	public void testGetModelNameDelegatesToWrapped() {
		String modelName = RandomTestUtil.randomString();

		Mockito.when(
			_modelResourcePermission.getModelName()
		).thenReturn(
			modelName
		);

		Assert.assertEquals(
			modelName,
			_dsrDefaultPermissionObjectEntryModelResourcePermission.
				getModelName());
	}

	@Test
	public void testGetPortletResourcePermissionDelegatesToWrapped() {
		Mockito.when(
			_modelResourcePermission.getPortletResourcePermission()
		).thenReturn(
			_portletResourcePermission
		);

		Assert.assertSame(
			_portletResourcePermission,
			_dsrDefaultPermissionObjectEntryModelResourcePermission.
				getPortletResourcePermission());
	}

	private void _stubActiveRoom() {
		_stubActiveRoom(0L);
	}

	private void _stubActiveRoom(long siteId) {
		Mockito.when(
			_objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"roomStatus", WorkflowConstants.STATUS_APPROVED
			).put(
				"siteId", siteId
			).build()
		);
	}

	private void _stubInactiveRoom(long siteId) {
		Mockito.when(
			_objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"roomStatus", WorkflowConstants.STATUS_INACTIVE
			).put(
				"siteId", siteId
			).build()
		);
	}

	private DSRDefaultPermissionObjectEntryModelResourcePermission
		_dsrDefaultPermissionObjectEntryModelResourcePermission;
	private final ModelResourcePermission<ObjectEntry>
		_modelResourcePermission = Mockito.mock(ModelResourcePermission.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectEntry _objectEntry = Mockito.mock(ObjectEntry.class);
	private final ObjectEntryLocalService _objectEntryLocalService =
		Mockito.mock(ObjectEntryLocalService.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final PortletResourcePermission _portletResourcePermission =
		Mockito.mock(PortletResourcePermission.class);

}