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

package com.liferay.redirect.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryService;
import com.liferay.redirect.test.util.RedirectTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddRedirectEntryWithoutPermissions() throws Exception {
		RedirectTestUtil.withRegularUser(
			(user, role) ->
				_redirectEntry = _redirectEntryService.addRedirectEntry(
					_group.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testAddRedirectEntryWithPermissions() throws Exception {
		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.ADD_ENTRY);

				_redirectEntry = _redirectEntryService.addRedirectEntry(
					_group.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				Assert.assertNotNull(_redirectEntry);
			});
	}

	@Test
	public void testCreateRedirectEntriesWithUpdateReferences()
		throws Exception {

		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "intermediateDestinationURL", null, false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_destinationRedirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, false,
			"intermediateDestinationURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.UPDATE);

				_redirectEntryService.updateRedirectEntriesReferences(
					_group.getGroupId(), "finalDestinationURL",
					"intermediateDestinationURL");

				_redirectEntry = _redirectEntryService.fetchRedirectEntry(
					_redirectEntry.getRedirectEntryId());

				Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

				Assert.assertEquals(
					"finalDestinationURL", _redirectEntry.getDestinationURL());

				_destinationRedirectEntry =
					_redirectEntryService.fetchRedirectEntry(
						_destinationRedirectEntry.getRedirectEntryId());

				Assert.assertEquals(
					"intermediateDestinationURL",
					_destinationRedirectEntry.getSourceURL());

				Assert.assertEquals(
					"finalDestinationURL",
					_destinationRedirectEntry.getDestinationURL());
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testCreateRedirectEntriesWithUpdateReferencesNoUpdatePermission()
		throws Exception {

		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "intermediateDestinationURL", null, false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_destinationRedirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, false,
			"intermediateDestinationURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				_redirectEntryService.updateRedirectEntriesReferences(
					_group.getGroupId(), "finalDestinationURL",
					"intermediateDestinationURL");
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteRedirectEntryWithoutPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> _redirectEntryService.deleteRedirectEntry(
				_redirectEntry.getRedirectEntryId()));
	}

	@Test
	public void testDeleteRedirectEntryWithPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.DELETE);

				Assert.assertNotNull(
					_redirectEntryService.deleteRedirectEntry(
						_redirectEntry.getRedirectEntryId()));

				_redirectEntry = null;
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testFetchRedirectEntryWithoutPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> _redirectEntryService.fetchRedirectEntry(
				_redirectEntry.getRedirectEntryId()));
	}

	@Test
	public void testFetchRedirectEntryWithPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Assert.assertNotNull(
					_redirectEntryService.fetchRedirectEntry(
						_redirectEntry.getRedirectEntryId()));
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetRedirectEntriesCountWithoutPermissions()
		throws Exception {

		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> _redirectEntryService.getRedirectEntriesCount(
				_group.getGroupId()));
	}

	@Test
	public void testGetRedirectEntriesCountWithPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				Assert.assertEquals(
					1,
					_redirectEntryService.getRedirectEntriesCount(
						_group.getGroupId()));
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetRedirectEntriesWithoutPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> _redirectEntryService.getRedirectEntries(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null));
	}

	@Test
	public void testGetRedirectEntriesWithPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.VIEW);

				List<RedirectEntry> redirectEntries =
					_redirectEntryService.getRedirectEntries(
						_group.getGroupId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

				Assert.assertEquals(
					redirectEntries.toString(), 1, redirectEntries.size());
			});
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateRedirectEntryWithoutPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) ->
				_redirectEntry = _redirectEntryService.updateRedirectEntry(
					_redirectEntry.getRedirectEntryId(),
					RandomTestUtil.randomString(), null,
					RandomTestUtil.randomBoolean(),
					RandomTestUtil.randomString()));
	}

	@Test
	public void testUpdateRedirectEntryWithPermissions() throws Exception {
		_redirectEntry = _redirectEntryService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		RedirectTestUtil.withRegularUser(
			(user, role) -> {
				RoleTestUtil.addResourcePermission(
					role, RedirectEntry.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					ActionKeys.UPDATE);

				_redirectEntry = _redirectEntryService.updateRedirectEntry(
					_redirectEntry.getRedirectEntryId(),
					RandomTestUtil.randomString(), null,
					RandomTestUtil.randomBoolean(),
					RandomTestUtil.randomString());

				Assert.assertNotNull(_redirectEntry);
			});
	}

	@DeleteAfterTestRun
	private RedirectEntry _destinationRedirectEntry;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private RedirectEntry _redirectEntry;

	@Inject
	private RedirectEntryService _redirectEntryService;

}