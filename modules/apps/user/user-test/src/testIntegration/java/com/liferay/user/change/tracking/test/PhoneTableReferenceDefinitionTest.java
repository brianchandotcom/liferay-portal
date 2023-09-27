/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class PhoneTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		List<ListType> listTypes = _listTypeLocalService.getListTypes(
			ListTypeConstants.ORGANIZATION_PHONE);

		_listType = listTypes.get(0);

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser(group.getGroupId());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _phoneLocalService.addPhone(
			_user.getUserId(), Organization.class.getName(),
			_organization.getOrganizationId(), RandomTestUtil.randomString(),
			String.valueOf(RandomTestUtil.randomInt()),
			_listType.getListTypeId(), false,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private ListType _listType;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	private Organization _organization;

	@Inject
	private PhoneLocalService _phoneLocalService;

	private User _user;

}