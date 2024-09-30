/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.StyleBookEntryService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class StyleBookEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testAddStyleBookEntryWithoutAddPermission() throws Exception {
		try {
			UserTestUtil.setUser(
				UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER));

			_styleBookEntryService.addStyleBookEntry(
				RandomTestUtil.randomString(), _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), _serviceContext);

			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}
		finally {
			UserTestUtil.setUser(TestPropsValues.getUser());
		}
	}

	@Test
	public void testDeleteStyleBookEntryByExternalReferenceCode()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryService.addStyleBookEntry(
				RandomTestUtil.randomString(), _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), _serviceContext);

		_styleBookEntryService.deleteStyleBookEntry(
			styleBookEntry.getExternalReferenceCode(),
			styleBookEntry.getGroupId());

		Assert.assertNull(
			_styleBookEntryLocalService.fetchStyleBookEntry(
				styleBookEntry.getStyleBookEntryId()));
	}

	@Test
	public void testDeleteStyleBookEntryByExternalReferenceCodeWithoutDeletePermission()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryService.addStyleBookEntry(
				RandomTestUtil.randomString(), _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), _serviceContext);

		try {
			UserTestUtil.setUser(
				UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER));

			_styleBookEntryService.deleteStyleBookEntry(
				styleBookEntry.getExternalReferenceCode(),
				styleBookEntry.getGroupId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}
		finally {
			UserTestUtil.setUser(TestPropsValues.getUser());
		}
	}

	@Test
	public void testGetStyleBookEntryByExternalReferenceCode()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryService.addStyleBookEntry(
				RandomTestUtil.randomString(), _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), _serviceContext);

		StyleBookEntry curStyleBookEntry =
			_styleBookEntryService.getStyleBookEntryByExternalReferenceCode(
				styleBookEntry.getExternalReferenceCode(),
				styleBookEntry.getGroupId());

		Assert.assertEquals(
			styleBookEntry.getStyleBookEntryId(),
			curStyleBookEntry.getStyleBookEntryId());
	}

	@Test
	public void testGetStyleBookEntryByExternalReferenceCodeWithoutViewPermission()
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryService.addStyleBookEntry(
				RandomTestUtil.randomString(), _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), _serviceContext);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, StyleBookEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(styleBookEntry.getStyleBookEntryId()),
			ActionKeys.VIEW);
		RoleTestUtil.removeResourcePermission(
			RoleConstants.SITE_MEMBER, StyleBookEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(styleBookEntry.getStyleBookEntryId()),
			ActionKeys.VIEW);

		try {
			UserTestUtil.setUser(
				UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER));

			_styleBookEntryService.getStyleBookEntryByExternalReferenceCode(
				styleBookEntry.getExternalReferenceCode(),
				styleBookEntry.getGroupId());

			Assert.fail();
		}
		catch (PrincipalException principalException) {
		}
		finally {
			UserTestUtil.setUser(TestPropsValues.getUser());
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private StyleBookEntryService _styleBookEntryService;

}