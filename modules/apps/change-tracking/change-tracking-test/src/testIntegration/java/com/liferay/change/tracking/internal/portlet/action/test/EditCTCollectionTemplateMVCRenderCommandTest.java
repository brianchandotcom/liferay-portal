/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.change.tracking.service.CTCollectionTemplateLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Noor Najjar
 */
@RunWith(Arquillian.class)
public class EditCTCollectionTemplateMVCRenderCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testRender() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		User user = company.getGuestUser();

		CTCollectionTemplate ctCollectionTemplate =
			_ctCollectionTemplateLocalService.addCTCollectionTemplate(
				user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				JSONUtil.put(
					"description", RandomTestUtil.randomString()
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"publicationsUserRoleUserIds", Collections.emptyList()
				).put(
					"roleValues", Collections.emptyList()
				).put(
					"userIds", new long[] {RandomTestUtil.randomLong()}
				).toString());

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
				new MockLiferayPortletRenderRequest(mockHttpServletRequest);

			mockLiferayPortletRenderRequest.addParameter(
				"ctCollectionTemplateId",
				String.valueOf(
					ctCollectionTemplate.getCtCollectionTemplateId()));

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
			themeDisplay.setRequest(mockHttpServletRequest);

			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			Assert.assertEquals(
				"/publications/error.jsp",
				_mvcRenderCommand.render(
					mockLiferayPortletRenderRequest,
					new MockLiferayPortletRenderResponse()));
		}
	}

	@Inject
	private static CTCollectionTemplateLocalService
		_ctCollectionTemplateLocalService;

	@Inject(
		filter = "mvc.command.name=/change_tracking/edit_ct_collection_template",
		type = MVCRenderCommand.class
	)
	private MVCRenderCommand _mvcRenderCommand;

}