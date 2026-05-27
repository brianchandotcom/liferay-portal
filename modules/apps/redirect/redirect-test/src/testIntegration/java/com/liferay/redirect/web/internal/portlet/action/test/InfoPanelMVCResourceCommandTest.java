/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class InfoPanelMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(
			_group1, RoleConstants.SITE_ADMINISTRATOR);
	}

	@Test
	@TestInfo("LPD-90724")
	public void testDoServeResource() throws Exception {
		RedirectEntry redirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group2.getGroupId(), "destinationURL", null, false,
				"sourceURL",
				ServiceContextTestUtil.getServiceContext(_group2.getGroupId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> ReflectionTestUtil.invoke(
					_mvcResourceCommand, "doServeResource",
					new Class<?>[] {
						ResourceRequest.class, ResourceResponse.class
					},
					_getMockLiferayResourceRequest(
						new long[] {redirectEntry.getRedirectEntryId()}),
					new MockLiferayResourceResponse()));
		}
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
		long[] rowIds) {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"rowIds", StringUtil.merge(rowIds));

		return mockLiferayResourceRequest;
	}

	private Group _group1;
	private Group _group2;

	@Inject(filter = "mvc.command.name=/redirect/info_panel")
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

	private User _user;

}