/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Ticket;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.headless.admin.user.client.resource.v1_0.TicketResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class TicketResourceTest extends BaseTicketResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();
	}

	@Override
	@Test
	public void testGetUserAccountEmailVerificationTicket() throws Exception {
		super.testGetUserAccountEmailVerificationTicket();

		User requestingUser = _addUser(RandomTestUtil.randomString());

		ticketResource = _getTicketResource(
			requestingUser.getEmailAddress(),
			requestingUser.getPasswordUnencrypted());

		_assertGetUserAccountTicketWithPermission(
			requestingUser, TicketConstants.TYPE_EMAIL_ADDRESS,
			ticketResource::getUserAccountEmailVerificationTicket);
	}

	@Override
	@Test
	public void testGetUserAccountPasswordResetTicket() throws Exception {
		super.testGetUserAccountPasswordResetTicket();

		User requestingUser = _addUser(RandomTestUtil.randomString());

		ticketResource = _getTicketResource(
			requestingUser.getEmailAddress(),
			requestingUser.getPasswordUnencrypted());

		_assertGetUserAccountTicketWithPermission(
			requestingUser, TicketConstants.TYPE_PASSWORD,
			ticketResource::getUserAccountPasswordResetTicket);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"extraInfo", "key"};
	}

	@Override
	protected Ticket testGetUserAccountEmailVerificationTicket_addTicket()
		throws Exception {

		return _addTicket(TicketConstants.TYPE_EMAIL_ADDRESS);
	}

	@Override
	protected Long
		testGetUserAccountEmailVerificationTicket_getUserAccountId() {

		return _user.getUserId();
	}

	@Override
	protected Ticket testGetUserAccountPasswordResetTicket_addTicket()
		throws Exception {

		return _addTicket(TicketConstants.TYPE_PASSWORD);
	}

	@Override
	protected Long testGetUserAccountPasswordResetTicket_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Ticket
			testGraphQLGetUserAccountEmailVerificationTicket_addTicket()
		throws Exception {

		return _addTicket(TicketConstants.TYPE_EMAIL_ADDRESS);
	}

	@Override
	protected Long
			testGraphQLGetUserAccountEmailVerificationTicket_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	protected Ticket testGraphQLGetUserAccountPasswordResetTicket_addTicket()
		throws Exception {

		return _addTicket(TicketConstants.TYPE_PASSWORD);
	}

	@Override
	protected Long
			testGraphQLGetUserAccountPasswordResetTicket_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	private Ticket _addTicket(int type) throws Exception {
		return _toTicket(
			_ticketLocalService.addDistinctTicket(
				_user.getCompanyId(), User.class.getName(), _user.getUserId(),
				type, RandomTestUtil.randomString(), null, null));
	}

	private User _addUser(String password) throws Exception {
		return UserTestUtil.addUser(
			testCompany.getCompanyId(), TestPropsValues.getUserId(), password,
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {TestPropsValues.getGroupId()},
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertGetUserAccountTicketWithPermission(
			User requestingUser, int type,
			UnsafeFunction<Long, Ticket, Exception> unsafeFunction)
		throws Exception {

		Ticket postTicket = _addTicket(type);

		try {
			unsafeFunction.apply(_user.getUserId());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			String message = problemException.getMessage();

			Assert.assertTrue(message.contains("must have UPDATE permission"));
		}

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, User.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(testCompany.getCompanyId()), ActionKeys.UPDATE);

		_userLocalService.addRoleUser(role.getRoleId(), requestingUser);

		Ticket getTicket = unsafeFunction.apply(_user.getUserId());

		assertEquals(postTicket, getTicket);
		assertValid(getTicket);
	}

	private TicketResource _getTicketResource(String login, String password) {
		TicketResource.Builder builder = TicketResource.builder();

		return builder.authentication(
			login, password
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	private Ticket _toTicket(
			com.liferay.portal.kernel.model.Ticket serviceBuilderTicket)
		throws Exception {

		return new Ticket() {
			{
				expirationDate = serviceBuilderTicket.getExpirationDate();
				extraInfo = serviceBuilderTicket.getExtraInfo();
				id = serviceBuilderTicket.getTicketId();
				key = serviceBuilderTicket.getKey();
			}
		};
	}

	@Inject
	private TicketLocalService _ticketLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}