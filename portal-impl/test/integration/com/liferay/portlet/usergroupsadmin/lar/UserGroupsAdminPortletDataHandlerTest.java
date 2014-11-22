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

package com.liferay.portlet.usergroupsadmin.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortletKeys;

import org.junit.ClassRule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserGroupsAdminPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new UserGroupsAdminPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.USER_GROUPS_ADMIN;
	}

}