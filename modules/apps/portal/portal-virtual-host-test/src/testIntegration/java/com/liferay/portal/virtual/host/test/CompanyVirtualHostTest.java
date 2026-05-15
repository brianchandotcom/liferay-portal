/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.host.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class CompanyVirtualHostTest extends BaseVirtualHostTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCannotAccessOtherCompanyGroupViaVirtualHost()
		throws Exception {

		Company company1 = CompanyTestUtil.addCompanyWithWebId(
			true, generateVirtualHostName());
		Company company2 = CompanyTestUtil.addCompanyWithWebId(
			true, generateVirtualHostName());

		Group group = GroupTestUtil.addGroupToCompany(company1.getCompanyId());

		Layout layout = LayoutTestUtil.addTypePortletLayout(group.getGroupId());

		assertURLtoString(
			(body, responseCode) -> {
				Assert.assertEquals(200, (long)responseCode);
				Assert.assertTrue(body.contains(group.getGroupKey()));
				Assert.assertTrue(
					body.contains(layout.getName(LocaleUtil.getDefault())));
			},
			StringBundler.concat(
				"http://", company1.getVirtualHostname(), ":",
				PortalUtil.getPortalServerPort(false), "/web",
				group.getFriendlyURL()));

		assertURLtoString(
			(body, responseCode) -> {
				Assert.assertEquals(404, (long)responseCode);
				Assert.assertTrue(body.contains(company2.getVirtualHostname()));
			},
			StringBundler.concat(
				"http://", company2.getVirtualHostname(), ":",
				PortalUtil.getPortalServerPort(false), "/web",
				group.getFriendlyURL()));
	}

}