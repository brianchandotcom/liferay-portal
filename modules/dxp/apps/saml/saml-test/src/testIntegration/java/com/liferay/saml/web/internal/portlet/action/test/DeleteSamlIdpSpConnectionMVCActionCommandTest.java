/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import jakarta.portlet.PortletException;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lucas Miranda
 */
@RunWith(Arquillian.class)
public class DeleteSamlIdpSpConnectionMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessAction() throws Exception {
		SamlIdpSpConnection samlIdpSpConnection =
			_samlIdpSpConnectionLocalService.createSamlIdpSpConnection(0);

		_samlIdpSpConnectionLocalService.updateSamlIdpSpConnection(
			samlIdpSpConnection);

		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			User user = UserTestUtil.addCompanyAdminUser(
				_companyLocalService.getCompanyById(company.getCompanyId()));

			Assert.assertThrows(
				PortletException.class,
				() -> _processAction(
					company, samlIdpSpConnection.getSamlIdpSpConnectionId(),
					user));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}

		User user = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.getCompanyById(
				TestPropsValues.getCompanyId()));

		Assert.assertEquals(
			samlIdpSpConnection,
			_samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(
				samlIdpSpConnection.getSamlIdpSpConnectionId()));

		_processAction(
			_companyLocalService.getCompanyById(TestPropsValues.getCompanyId()),
			samlIdpSpConnection.getSamlIdpSpConnectionId(), user);

		Assert.assertNull(
			_samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(
				samlIdpSpConnection.getSamlIdpSpConnectionId()));
	}

	private void _processAction(
			Company company, long samlIdpSpConnectionId, User user)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"samlIdpSpConnectionId", String.valueOf(samlIdpSpConnectionId));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setUser(user);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "mvc.command.name=/admin/delete_saml_idp_sp_connection",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

}