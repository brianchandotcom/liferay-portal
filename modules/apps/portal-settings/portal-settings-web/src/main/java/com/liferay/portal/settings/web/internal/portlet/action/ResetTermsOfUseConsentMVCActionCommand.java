/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivan Ivica
 */
@Component(
	property = {
		"jakarta.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/portal_settings/reset_terms_of_use_consent"
	},
	service = MVCActionCommand.class
)
public class ResetTermsOfUseConsentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(themeDisplay.getCompanyId())) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		_resetTermsOfUseConsent(themeDisplay.getCompanyId());

		SessionMessages.add(
			actionRequest, "requestProcessed",
			_language.get(
				_portal.getHttpServletRequest(actionRequest),
				"terms-of-use-consent-reset-is-in-progress"));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	private void _resetTermsOfUseConsent(long companyId) {
		_portalExecutorManager.getPortalExecutor(
			ResetTermsOfUseConsentMVCActionCommand.class.getName()
		).submit(
			() -> {
				try {
					ActionableDynamicQuery actionableDynamicQuery =
						_userLocalService.getActionableDynamicQuery();

					actionableDynamicQuery.setAddCriteriaMethod(
						dynamicQuery -> dynamicQuery.add(
							PropertyFactoryUtil.forName(
								"agreedToTermsOfUse"
							).eq(
								true
							)));
					actionableDynamicQuery.setCompanyId(companyId);
					actionableDynamicQuery.setPerformActionMethod(
						(User user) ->
							_userLocalService.updateAgreedToTermsOfUse(
								user.getUserId(), false));

					actionableDynamicQuery.performActions();
				}
				catch (Exception exception) {
					_log.error(
						"Unable to reset terms of use consent for company " +
							companyId,
						exception);
				}
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResetTermsOfUseConsentMVCActionCommand.class);

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private UserLocalService _userLocalService;

}