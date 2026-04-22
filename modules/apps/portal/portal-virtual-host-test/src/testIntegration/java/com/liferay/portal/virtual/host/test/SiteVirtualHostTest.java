/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.host.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class SiteVirtualHostTest extends BaseVirtualHostTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompanyWithWebId(true, COMPANY_HOST_1);

		_guestGroup = _groupLocalService.getGroup(
			_company.getCompanyId(),
			PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		_group1 = _addGroup(_company, null, _GROUP_HOST_1);

		_childGroup1 = _addGroup(_company, _group1, _CHILD_GROUP_HOST_1);

		_group2 = _addGroup(_company, null, _GROUP_HOST_2);

		_childGroup2 = _addGroup(_company, _group2, null);

		_group3 = _addGroup(_company, null, null);

		_childGroup3 = _addGroup(_company, _group3, _CHILD_GROUP_HOST_2);
	}

	@Ignore
	@Test
	public void testGroupRestrictedViaVirutalHost() throws Exception {
		_assertURLToString(true, _group1, COMPANY_HOST_1);
		_assertURLToString(true, _group2, COMPANY_HOST_1);
		_assertURLToString(true, _group3, COMPANY_HOST_1);
		_assertURLToString(true, _guestGroup, COMPANY_HOST_1);

		_assertURLToString(true, _group1, _GROUP_HOST_1);
		_assertURLToString(false, _group2, _GROUP_HOST_1);
		_assertURLToString(true, _group3, _GROUP_HOST_1);
		_assertURLToString(true, _guestGroup, _GROUP_HOST_1);

		_assertURLToString(true, _group1, _GROUP_HOST_2);
		_assertURLToString(true, _group2, _GROUP_HOST_2);
		_assertURLToString(true, _group3, _GROUP_HOST_2);
		_assertURLToString(true, _guestGroup, _GROUP_HOST_2);
	}

	@Test
	public void testGroupsAccessibleViaVirutalHost() throws Exception {
		_assertURLToString(true, _group1, COMPANY_HOST_1);
		_assertURLToString(true, _group2, COMPANY_HOST_1);
		_assertURLToString(true, _group3, COMPANY_HOST_1);
		_assertURLToString(true, _childGroup1, COMPANY_HOST_1);
		_assertURLToString(true, _childGroup2, COMPANY_HOST_1);
		_assertURLToString(true, _childGroup3, COMPANY_HOST_1);
		_assertURLToString(true, _guestGroup, COMPANY_HOST_1);

		_assertURLToString(true, _group1, _GROUP_HOST_1);
		_assertURLToString(true, _group2, _GROUP_HOST_1);
		_assertURLToString(true, _group3, _GROUP_HOST_1);
		_assertURLToString(true, _childGroup1, _GROUP_HOST_1);
		_assertURLToString(true, _childGroup2, _GROUP_HOST_1);
		_assertURLToString(true, _childGroup3, _GROUP_HOST_1);
		_assertURLToString(true, _guestGroup, _GROUP_HOST_1);

		_assertURLToString(true, _group1, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _group2, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _group3, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _childGroup1, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _childGroup2, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _childGroup3, _CHILD_GROUP_HOST_1);
		_assertURLToString(true, _guestGroup, _CHILD_GROUP_HOST_1);

		_assertURLToString(true, _group1, _GROUP_HOST_2);
		_assertURLToString(true, _group2, _GROUP_HOST_2);
		_assertURLToString(true, _group3, _GROUP_HOST_2);
		_assertURLToString(true, _childGroup1, _GROUP_HOST_2);
		_assertURLToString(true, _childGroup2, _GROUP_HOST_2);
		_assertURLToString(true, _childGroup3, _GROUP_HOST_2);
		_assertURLToString(true, _guestGroup, _GROUP_HOST_2);

		_assertURLToString(true, _group1, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _group2, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _group3, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _childGroup1, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _childGroup2, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _childGroup3, _CHILD_GROUP_HOST_2);
		_assertURLToString(true, _guestGroup, _CHILD_GROUP_HOST_2);
	}

	private Group _addGroup(
			Company company, Group parentGroup, String virtualHost)
		throws Exception {

		Group group;

		if (parentGroup == null) {
			group = GroupTestUtil.addGroupToCompany(company.getCompanyId());
		}
		else {
			group = GroupTestUtil.addGroupToCompany(
				company.getCompanyId(), parentGroup.getGroupId());
		}

		if (virtualHost != null) {
			_layoutSetLocalService.updateVirtualHosts(
				group.getGroupId(), false,
				TreeMapBuilder.put(
					virtualHost, StringPool.BLANK
				).build());
		}

		LayoutTestUtil.addTypePortletLayout(group);

		return group;
	}

	private void _assertURLToString(
			boolean accessible, Group group, String host)
		throws Exception {

		assertURLtoString(
			(code, body) -> {
				if (!accessible) {
					Assert.assertEquals(
						HttpServletResponse.SC_NOT_FOUND, (long)code);

					return;
				}

				Assert.assertEquals(HttpServletResponse.SC_OK, (long)code);
				Assert.assertTrue(body.contains(group.getDescriptiveName()));

				Layout layout = _layoutLocalService.fetchDefaultLayout(
					group.getGroupId(), false);

				Assert.assertTrue(
					body.contains(layout.getName(LocaleUtil.getDefault())));
			},
			StringBundler.concat(
				"http://", host, ":8080/web", group.getFriendlyURL()));
	}

	private static final String _CHILD_GROUP_HOST_1 = "childgroup1.localhost";

	private static final String _CHILD_GROUP_HOST_2 = "childgroup2.localhost";

	private static final String _GROUP_HOST_1 = "group1.localhost";

	private static final String _GROUP_HOST_2 = "group2.localhost";

	private Group _childGroup1;
	private Group _childGroup2;
	private Group _childGroup3;

	@DeleteAfterTestRun
	private Company _company;

	private Group _group1;
	private Group _group2;
	private Group _group3;

	@Inject
	private GroupLocalService _groupLocalService;

	private Group _guestGroup;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

}