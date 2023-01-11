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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
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

}