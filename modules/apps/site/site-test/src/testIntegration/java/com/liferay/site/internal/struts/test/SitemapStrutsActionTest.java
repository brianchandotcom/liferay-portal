/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class SitemapStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExecute() throws Exception {
		Group liveGroup = GroupTestUtil.addGroup();

		Group stagingGroup = GroupTestUtil.addGroup();

		stagingGroup.setLiveGroupId(liveGroup.getGroupId());

		stagingGroup = GroupLocalServiceUtil.updateGroup(stagingGroup);

		LayoutSet layoutSet = stagingGroup.getPublicLayoutSet();

		_virtualHostLocalService.updateVirtualHosts(
			stagingGroup.getCompanyId(), layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				"virtual", StringPool.BLANK
			).build());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addHeader("Host", "virtual");

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(company.getGuestUser()));

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter(
			"currentURL", "http://localhost:8080/sitemap.xml");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_sitemapStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(500, mockHttpServletResponse.getStatus());

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		mockHttpServletResponse = new MockHttpServletResponse();

		_sitemapStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(200, mockHttpServletResponse.getStatus());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.internal.struts.SitemapStrutsAction"
	)
	private StrutsAction _sitemapStrutsAction;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}