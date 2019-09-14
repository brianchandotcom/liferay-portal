/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sharing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.sharing.exception.InvalidSharingEntryActionException;
import com.liferay.sharing.exception.InvalidSharingEntryExpirationDateException;
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class SharingEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_fromUser = UserTestUtil.addOmniAdminUser();
		_toUser = UserTestUtil.addUser();
		_user = UserTestUtil.addUser();

		ServiceTestUtil.setUser(_fromUser);

		Bundle bundle = FrameworkUtil.getBundle(SharingEntryServiceTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testAddOrUpdateSharingEntryAddsNewSharingEntry()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry =
			_sharingEntryService.addOrUpdateSharingEntry(
				_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
				true, Collections.singletonList(SharingEntryAction.VIEW),
				expirationDate, serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(_fromUser.getUserId(), sharingEntry.getUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());
	}

	@Test
	public void testAddOrUpdateSharingEntryUpdatesSharingEntry()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry addSharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW),
			expirationDate, serviceContext);

		Assert.assertTrue(addSharingEntry.isShareable());
		Assert.assertEquals(1, addSharingEntry.getActionIds());
		Assert.assertEquals(
			expirationDate, addSharingEntry.getExpirationDate());

		expirationDate = Date.from(instant.plus(3, ChronoUnit.DAYS));

		SharingEntry updateSharingEntry =
			_sharingEntryService.addOrUpdateSharingEntry(
				_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
				false,
				Arrays.asList(
					SharingEntryAction.VIEW, SharingEntryAction.UPDATE),
				expirationDate, serviceContext);

		Assert.assertFalse(updateSharingEntry.isShareable());
		Assert.assertEquals(3, updateSharingEntry.getActionIds());
		Assert.assertEquals(
			expirationDate, updateSharingEntry.getExpirationDate());

		Assert.assertEquals(
			addSharingEntry.getSharingEntryId(),
			updateSharingEntry.getSharingEntryId());
	}

	@Test(expected = InvalidSharingEntryActionException.class)
	public void testAddOrUpdateSharingEntryWithEmptySharingEntryActions()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.addOrUpdateSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.emptyList(), null, serviceContext);
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testAddOrUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.addOrUpdateSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW),
			expirationDate, serviceContext);
	}

	@Test(expected = PrincipalException.class)
	public void testAddSharingEntryWithInvalidClassNameIdThrowsException()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		ClassName invalidClassName = _classNameLocalService.addClassName(
			"InvalidClassName");

		long classNameId = invalidClassName.getClassNameId();

		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test
	public void testAddSharingEntryWithUpdateAndViewPermission()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(_fromUser.getUserId(), sharingEntry.getUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSharingEntryWithUpdateAndViewPermissionWhenUserHasShareableAddDiscussionAndViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test
	public void testAddSharingEntryWithUpdateAndViewPermissionWhenUserHasShareableUpdateAndViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSharingEntryWithUpdateAndViewPermissionWhenUserHasUnshareableUpdateAndViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSharingEntryWithUpdateAndViewPermissionWhenUserHasUpdatePermissionAndShareableViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.UPDATE);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSharingEntryWithUpdateAndViewPermissionWhenUserHasViewPermission()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSharingEntryWithUpdatePermissionWhenUserHasViewPermissionThrowsException()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.UPDATE), null,
			serviceContext);
	}

	@Test
	public void testAddSharingEntryWithViewPermission() throws Exception {
		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(_fromUser.getUserId(), sharingEntry.getUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
	}

	@Test
	public void testAddSharingEntryWithViewPermissionWhenUserHasShareableUpdateAndViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			null, serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);
	}

	@Test
	public void testAddSharingEntryWithViewPermissionWhenUserHasViewPermissionAndShareableViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), true,
			Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);
	}

	@Test
	public void testAddSharingEntryWithViewPermissionWhenUserHasViewPermissionAndUnshareableViewSharingEntryAction()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryLocalService.addSharingEntry(
			_user.getUserId(), _fromUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		_sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testDeleteNonexistingSharingEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_sharingEntryService.updateSharingEntry(
			RandomTestUtil.randomLong(),
			Arrays.asList(
				SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
				SharingEntryAction.VIEW),
			true, null, serviceContext);
	}

	@Test
	public void testDeleteSharingEntry() throws Exception {
		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		_sharingEntryService.deleteSharingEntry(
			sharingEntry.getSharingEntryId(), serviceContext);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test(expected = PrincipalException.class)
	public void testSharingEntryCannotBeDeletedByAnyUserOtherThanTheSharer()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			_fromUser.getUserId(), _toUser.getUserId(), classNameId, classPK,
			_group.getGroupId(), false,
			Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		ServiceTestUtil.setUser(_toUser);

		_sharingEntryService.deleteSharingEntry(
			sharingEntry.getSharingEntryId(), serviceContext);

		Assert.assertNull(
			_sharingEntryLocalService.fetchSharingEntry(
				sharingEntry.getSharingEntryId()));
	}

	@Test
	public void testUpdateSharingEntryShareable() throws Exception {
		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		Assert.assertTrue(sharingEntry.isShareable());

		sharingEntry = _sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Collections.singletonList(SharingEntryAction.VIEW), false, null,
			serviceContext);

		Assert.assertFalse(sharingEntry.isShareable());
	}

	@Test
	public void testUpdateSharingEntryWithExpirationDateInTheFuture()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		Assert.assertNull(sharingEntry.getExpirationDate());

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.plus(2, ChronoUnit.DAYS));

		sharingEntry = _sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Collections.singletonList(SharingEntryAction.VIEW), true,
			expirationDate, serviceContext);

		Assert.assertEquals(expirationDate, sharingEntry.getExpirationDate());
	}

	@Test(expected = InvalidSharingEntryExpirationDateException.class)
	public void testUpdateSharingEntryWithExpirationDateInThePast()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		Instant instant = Instant.now();

		Date expirationDate = Date.from(instant.minus(2, ChronoUnit.DAYS));

		_sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Collections.singletonList(SharingEntryAction.VIEW), true,
			expirationDate, serviceContext);
	}

	@Test
	public void testUpdateSharingEntryWithUpdateAndViewPermission()
		throws Exception {

		_registerSharingPermissionChecker(
			SharingEntryAction.UPDATE, SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		sharingEntry = _sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			true, null, serviceContext);

		Assert.assertEquals(3, sharingEntry.getActionIds());
		Assert.assertEquals(_group.getCompanyId(), sharingEntry.getCompanyId());
		Assert.assertEquals(_group.getGroupId(), sharingEntry.getGroupId());
		Assert.assertEquals(_fromUser.getUserId(), sharingEntry.getUserId());
		Assert.assertEquals(_toUser.getUserId(), sharingEntry.getToUserId());
		Assert.assertEquals(classNameId, sharingEntry.getClassNameId());
		Assert.assertEquals(classPK, sharingEntry.getClassPK());
		Assert.assertTrue(sharingEntry.isShareable());
	}

	@Test(expected = PrincipalException.class)
	public void testUpdateSharingEntryWithUpdateAndViewPermissionWhenUserHasViewPermission()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		ServiceTestUtil.setUser(_toUser);

		_sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			true, null, serviceContext);
	}

	@Test(expected = PrincipalException.class)
	public void testUpdateSharingEntryWithUpdatePermissionWhenUserHasViewPermission()
		throws Exception {

		_registerSharingPermissionChecker(SharingEntryAction.VIEW);

		long classNameId = _classNameLocalService.getClassNameId(
			Group.class.getName());
		long classPK = _group.getGroupId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SharingEntry sharingEntry = _sharingEntryService.addSharingEntry(
			_toUser.getUserId(), classNameId, classPK, _group.getGroupId(),
			true, Collections.singletonList(SharingEntryAction.VIEW), null,
			serviceContext);

		ServiceTestUtil.setUser(_toUser);

		_sharingEntryService.updateSharingEntry(
			sharingEntry.getSharingEntryId(),
			Arrays.asList(SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
			true, null, serviceContext);
	}

	private void _registerSharingPermissionChecker(
		SharingEntryAction... sharingEntryActions) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("model.class.name", Group.class.getName());

		_serviceRegistration = _bundleContext.registerService(
			SharingPermissionChecker.class,
			new TestSharingPermissionChecker(sharingEntryActions), properties);
	}

	private BundleContext _bundleContext;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private User _fromUser;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceRegistration<SharingPermissionChecker> _serviceRegistration;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private SharingEntryService _sharingEntryService;

	@DeleteAfterTestRun
	private User _toUser;

	@DeleteAfterTestRun
	private User _user;

	private class TestSharingPermissionChecker
		implements SharingPermissionChecker {

		public TestSharingPermissionChecker(
			SharingEntryAction[] sharingEntryActions) {

			_sharingEntryActions = sharingEntryActions;
		}

		@Override
		public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, long groupId,
			Collection<SharingEntryAction> sharingEntryActions) {

			for (SharingEntryAction sharingEntryAction : sharingEntryActions) {
				if (!ArrayUtil.contains(
						_sharingEntryActions, sharingEntryAction)) {

					return false;
				}
			}

			return true;
		}

		private final SharingEntryAction[] _sharingEntryActions;

	}

}