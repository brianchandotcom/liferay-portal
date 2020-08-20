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

package com.liferay.roles.admin.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;
import com.liferay.roles.admin.role.type.contributor.provider.RoleTypeContributorProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltan Csaszi
 */
@RunWith(Arquillian.class)
public class RolesAdminPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	@Test
	public void testPrepareManifestSummary() throws Exception {
		GroupTestUtil.deleteGroup(stagingGroup);

		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		stagingGroup = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		super.testPrepareManifestSummary();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void checkManifestSummaryReferrerClassNames(
		ManifestSummary manifestSummary) {

		Set<String> classNames = new HashSet<>();

		classNames.add(StagedModelType.REFERRER_CLASS_NAME_ALL);
		classNames.add(Role.class.getName());

		for (RoleTypeContributor roleTypeContributor :
				_roleTypeContributorProvider.getRoleTypeContributors()) {

			if (Validator.isNotNull(roleTypeContributor.getClassName())) {
				classNames.add(roleTypeContributor.getClassName());
			}
		}

		for (String manifestSummaryKey :
				manifestSummary.getManifestSummaryKeys()) {

			Stream<String> classNamesStream = classNames.stream();

			Assert.assertTrue(
				classNamesStream.anyMatch(manifestSummaryKey::endsWith));
		}
	}

	@Override
	protected DataLevel getDataLevel() {
		return DataLevel.PORTAL;
	}

	@Override
	protected String getPortletId() {
		return RolesAdminPortletKeys.ROLES_ADMIN;
	}

	@Override
	protected boolean isDataPortalLevel() {
		return true;
	}

	@Override
	protected boolean isDataPortletInstanceLevel() {
		return false;
	}

	@Override
	protected boolean isDataSiteLevel() {
		return false;
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private RoleTypeContributorProvider _roleTypeContributorProvider;

	@DeleteAfterTestRun
	private User _user;

}