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

package com.liferay.on.demand.admin.ticket.generator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.constants.OnDemandAdminConstants;
import com.liferay.on.demand.admin.ticket.generator.OnDemandAdminTicketGenerator;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminTicketGeneratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGenerate() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		Ticket ticket = _onDemandAdminTicketGenerator.generate(
			company, TestPropsValues.getUserId());

		Assert.assertEquals(
			OnDemandAdminConstants.TICKET_TYPE_ON_DEMAND_ADMIN_LOGIN,
			ticket.getType());

		User user = _userLocalService.getUser(ticket.getClassPK());

		Assert.assertEquals(company.getCompanyId(), user.getCompanyId());
		Assert.assertTrue(
			StringUtil.startsWith(
				user.getScreenName(),
				OnDemandAdminConstants.SCREEN_NAME_PREFIX_ON_DEMAND_ADMIN));
		Assert.assertTrue(
			_userLocalService.hasRoleUser(
				company.getCompanyId(), RoleConstants.ADMINISTRATOR,
				user.getUserId(), false));
	}

	@Inject
	private OnDemandAdminTicketGenerator _onDemandAdminTicketGenerator;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

}