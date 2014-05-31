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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.runner.RunWith;

/**
 * @author David Mendez Gonzalez
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class RoleStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return RoleTestUtil.addRole(
			RandomTestUtil.randomString(), RoleConstants.TYPE_REGULAR);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return RoleLocalServiceUtil.fetchRoleByUuidAndCompanyId(
				uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Role.class;
	}

	@Override
	protected void initExport() throws Exception {
		super.initExport();

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			portletDataContext.getCompanyId());

		rootElement.addAttribute(
			"company-group-id", String.valueOf(companyGroup.getGroupId()));

		Group userPersonalSiteGroup =
			GroupLocalServiceUtil.getUserPersonalSiteGroup(
				portletDataContext.getCompanyId());

		rootElement.addAttribute(
			"user-personal-site-group-id",
			String.valueOf(userPersonalSiteGroup.getGroupId()));
	}

}