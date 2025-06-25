/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.web.internal.portlet.action;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.DefaultAdminUtil;
import com.liferay.portal.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.security.pwd.PwdToolkitUtilThreadLocal;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;
import jakarta.portlet.PortletMode;
import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletURL;
import jakarta.portlet.WindowState;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = {
		"jakarta.portlet.name=" + LoginPortletKeys.CREATE_ACCOUNT,
		"jakarta.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
		"jakarta.portlet.name=" + LoginPortletKeys.FORGOT_PASSWORD,
		"jakarta.portlet.name=" + LoginPortletKeys.LOGIN,
		"mvc.command.name=/login/update_password"
	},
	service = MVCActionCommand.class
)
public class UpdatePasswordMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Ticket ticket = getTicket(actionRequest);

		actionRequest.setAttribute(WebKeys.TICKET, ticket);

		if (Validator.isNull(
				ParamUtil.getString(actionRequest, Constants.CMD))) {

			if (ticket != null) {
				User user = _userLocalService.getUser(ticket.getClassPK());

				_userLocalService.updatePasswordReset(user.getUserId(), true);
			}

			User user = _portal.getUser(actionRequest);

			if ((user != null) && _isUserDefaultAdmin(user)) {
				String reminderQueryAnswer = user.getReminderQueryAnswer();

				if (Validator.isNotNull(reminderQueryAnswer) &&
					reminderQueryAnswer.equals(
						WorkflowConstants.LABEL_PENDING)) {

					actionRequest.setAttribute(
						WebKeys.TITLE_SET_PASSWORD, "set-password");
				}
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			updatePassword(actionRequest, actionResponse, themeDisplay, ticket);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				redirect = _portal.escapeRedirect(redirect);
			}

			if (Validator.isNull(redirect)) {
				redirect = themeDisplay.getPathMain();
			}

			actionResponse.sendRedirect(redirect);
		}
		catch (Exception exception) {
			if (exception instanceof UserPasswordException) {
				SessionErrors.add(actionRequest, exception.getClass());
			}
			else if (exception instanceof NoSuchUserException ||
					 exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}

			_postProcessUpdatePasswordFailure(actionRequest, actionResponse);
		}
	}

	protected Ticket getTicket(ActionRequest actionRequest)
		throws PortalException {

		String ticketId = ParamUtil.getString(actionRequest, "ticketId");
		String ticketKey = ParamUtil.getString(actionRequest, "ticketKey");

		if (Validator.isNull(ticketId) || Validator.isNull(ticketKey)) {
			return null;
		}

		try {
			Ticket ticket = _ticketLocalService.fetchTicket(
				GetterUtil.getLong(ticketId));

			if ((ticket == null) ||
				(ticket.getType() != TicketConstants.TYPE_PASSWORD)) {

				return null;
			}

			String encryptedTicketKey = PasswordEncryptorUtil.encrypt(
				ticketKey, ticket.getKey());

			if (!ticket.isExpired() &&
				encryptedTicketKey.equals(ticket.getKey())) {

				return ticket;
			}

			_ticketLocalService.deleteTicket(ticket);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	protected boolean isValidatePassword(
		HttpServletRequest httpServletRequest) {

		HttpSession httpSession = httpServletRequest.getSession();

		Boolean setupWizardPasswordUpdated = GetterUtil.getBoolean(
			httpSession.getAttribute(WebKeys.SETUP_WIZARD_PASSWORD_UPDATED));

		if ((setupWizardPasswordUpdated != null) &&
			setupWizardPasswordUpdated) {

			return false;
		}

		return true;
	}

	protected void updatePassword(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ThemeDisplay themeDisplay, Ticket ticket)
		throws Exception {

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(actionRequest));

		AuthTokenUtil.checkCSRFToken(
			httpServletRequest, UpdatePasswordMVCActionCommand.class.getName());

		HttpSession httpSession = httpServletRequest.getSession();

		long userId = 0;

		if (ticket != null) {
			userId = ticket.getClassPK();
		}
		else {
			userId = themeDisplay.getUserId();
		}

		String password1 = actionRequest.getParameter("password1");

		boolean previousValidate = PwdToolkitUtilThreadLocal.isValidate();

		try {
			PwdToolkitUtilThreadLocal.setValidate(
				isValidatePassword(httpServletRequest));

			User user = _userLocalService.updatePassword(
				userId, password1, actionRequest.getParameter("password2"),
				false);

			String reminderQueryAnswer = user.getReminderQueryAnswer();

			if (_isUserDefaultAdmin(user) &&
				reminderQueryAnswer.equals(WorkflowConstants.LABEL_PENDING) &&
				Validator.isNull(user.getReminderQueryQuestion())) {

				user.setReminderQueryAnswer(null);

				user = _userLocalService.updateUser(user);
			}

			Date passwordModifiedDate = user.getPasswordModifiedDate();

			httpSession.setAttribute(
				WebKeys.USER_PASSWORD_MODIFIED_TIME,
				passwordModifiedDate.getTime());
		}
		finally {
			PwdToolkitUtilThreadLocal.setValidate(previousValidate);
		}

		User user = _userLocalService.getUser(userId);

		Company company = _companyLocalService.getCompanyById(
			user.getCompanyId());

		if (ticket != null) {
			_ticketLocalService.deleteTickets(
				user.getCompanyId(), User.class.getName(), userId,
				ticket.getType());

			_userLocalService.updateLockout(user, false);

			_userLocalService.updatePasswordReset(userId, false);

			if (company.isStrangersVerify()) {
				_userLocalService.updateEmailAddressVerified(userId, true);
			}
		}

		if (GetterUtil.getBoolean(
				httpSession.getAttribute(WebKeys.MFA_ENABLED))) {

			return;
		}

		String login = null;

		String authType = company.getAuthType();

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			login = user.getEmailAddress();
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = user.getScreenName();
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(userId);
		}

		if (!themeDisplay.isSignedIn()) {
			AuthenticatedSessionManagerUtil.login(
				httpServletRequest,
				_portal.getHttpServletResponse(actionResponse), login,
				password1, false, null);
		}
	}

	private boolean _isUserDefaultAdmin(User user) {
		User defaultAdminUser = DefaultAdminUtil.fetchDefaultAdmin(
			user.getCompanyId());

		if ((defaultAdminUser != null) &&
			(defaultAdminUser.getUserId() == user.getUserId())) {

			return true;
		}

		return false;
	}

	private void _postProcessUpdatePasswordFailure(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout =
			_layoutUtilityPageEntryLayoutProvider.
				getDefaultLayoutUtilityPageEntryLayout(
					themeDisplay.getScopeGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_FORGOT_PASSWORD);

		if (layout == null) {
			layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);
		}

		String ticketId = ParamUtil.getString(actionRequest, "ticketId");

		String ticketKey = ParamUtil.getString(actionRequest, "ticketKey");

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				actionRequest, LoginPortletKeys.UPDATE_PASSWORD, layout,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/login/update_password"
		).setParameter(
			"saveLastPath", false
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			WindowState.MAXIMIZED
		).buildPortletURL();

		String portletName = liferayPortletRequest.getPortletName();

		if (portletName.equals(LoginPortletKeys.UPDATE_PASSWORD)) {
			if (layout.isTypeUtility()) {
				portletURL.setWindowState(WindowState.NORMAL);
			}
			else {
				portletURL.setWindowState(WindowState.MAXIMIZED);
			}
		}
		else {
			portletURL.setWindowState(actionRequest.getWindowState());
		}

		String portletURLString = portletURL.toString();

		portletURLString = HttpComponentsUtil.setParameter(
			portletURLString, "ticketId", ticketId);

		portletURLString = HttpComponentsUtil.setParameter(
			portletURLString, "ticketKey", ticketKey);

		actionResponse.sendRedirect(portletURLString);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdatePasswordMVCActionCommand.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private LayoutUtilityPageEntryLayoutProvider
		_layoutUtilityPageEntryLayoutProvider;

	@Reference
	private Portal _portal;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}