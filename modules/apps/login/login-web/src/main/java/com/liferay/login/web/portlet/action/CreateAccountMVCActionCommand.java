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

package com.liferay.login.web.portlet.action;

import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.login.web.portlet.util.LoginUtil;
import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactNameException;
import com.liferay.portal.DuplicateOpenIdException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.RequiredFieldException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.TermsOfUseException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 * @author Daniel Sanz
 * @author Sergio González
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + LoginPortletKeys.LOGIN,
		"mvc.command.name=/login/create_account"
	},
	service = MVCActionCommand.class
)
public class CreateAccountMVCActionCommand extends BaseMVCActionCommand {

	protected void addUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = isAutoScreenName();
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = ParamUtil.getLong(actionRequest, "facebookId");
		String openId = ParamUtil.getString(actionRequest, "openId");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		long suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		if (PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD) {
			autoPassword = false;

			password1 = ParamUtil.getString(actionRequest, "password1");
			password2 = ParamUtil.getString(actionRequest, "password2");
		}

		boolean openIdPending = false;

		Boolean openIdLoginPending = (Boolean)session.getAttribute(
			WebKeys.OPEN_ID_LOGIN_PENDING);

		if ((openIdLoginPending != null) && openIdLoginPending.booleanValue() &&
			Validator.isNotNull(openId)) {

			sendEmail = false;
			openIdPending = true;
		}

		User user = _userService.addUserWithWorkflow(
			company.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			LocaleUtil.fromLanguageId(languageId), firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendEmail, serviceContext);

		if (openIdPending) {
			session.setAttribute(
				WebKeys.OPEN_ID_LOGIN, Long.valueOf(user.getUserId()));

			session.removeAttribute(WebKeys.OPEN_ID_LOGIN_PENDING);
		}
		else {

			// Session messages

			if (user.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				SessionMessages.add(
					request, "userAdded", user.getEmailAddress());
				SessionMessages.add(
					request, "userAddedPassword",
					user.getPasswordUnencrypted());
			}
			else {
				SessionMessages.add(
					request, "userPending", user.getEmailAddress());
			}
		}

		// Send redirect

		String login = null;

		String authType = company.getAuthType();

		if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(user.getUserId());
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = user.getScreenName();
		}
		else {
			login = user.getEmailAddress();
		}

		sendRedirect(
			actionRequest, actionResponse, themeDisplay, login,
			user.getPasswordUnencrypted());
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isStrangers()) {
			throw new PrincipalException.MustBeEnabled(
				company.getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS);
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				if (PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT) {
					CaptchaUtil.check(actionRequest);
				}

				addUser(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.RESET)) {
				resetUser(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateIncompleteUser(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof AddressCityException ||
				e instanceof AddressStreetException ||
				e instanceof AddressZipException ||
				e instanceof CaptchaConfigurationException ||
				e instanceof CaptchaMaxChallengesException ||
				e instanceof CaptchaTextException ||
				e instanceof CompanyMaxUsersException ||
				e instanceof ContactBirthdayException ||
				e instanceof ContactNameException ||
				e instanceof DuplicateOpenIdException ||
				e instanceof EmailAddressException ||
				e instanceof GroupFriendlyURLException ||
				e instanceof NoSuchCountryException ||
				e instanceof NoSuchListTypeException ||
				e instanceof NoSuchOrganizationException ||
				e instanceof NoSuchRegionException ||
				e instanceof OrganizationParentException ||
				e instanceof PhoneNumberException ||
				e instanceof RequiredFieldException ||
				e instanceof RequiredUserException ||
				e instanceof TermsOfUseException ||
				e instanceof UserEmailAddressException ||
				e instanceof UserIdException ||
				e instanceof UserPasswordException ||
				e instanceof UserScreenNameException ||
				e instanceof UserSmsException ||
				e instanceof WebsiteURLException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate ||
					 e instanceof UserScreenNameException.MustNotBeDuplicate) {

				String emailAddress = ParamUtil.getString(
					actionRequest, "emailAddress");

				User user = _userLocalService.fetchUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if ((user == null) ||
					(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

					SessionErrors.add(actionRequest, e.getClass(), e);
				}
				else {
					actionResponse.setRenderParameter(
						"mvcPath", "update_account.jsp");
				}
			}
			else {
				throw e;
			}
		}

		if (Validator.isNull(PropsValues.COMPANY_SECURITY_STRANGERS_URL)) {
			return;
		}

		try {
			Layout layout = _layoutLocalService.getFriendlyURLLayout(
				themeDisplay.getScopeGroupId(), false,
				PropsValues.COMPANY_SECURITY_STRANGERS_URL);

			String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected boolean isAutoScreenName() {
		return _AUTO_SCREEN_NAME;
	}

	protected void resetUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		User anonymousUser = _userLocalService.getUserByEmailAddress(
			themeDisplay.getCompanyId(), emailAddress);

		if (anonymousUser.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
			throw new PrincipalException.MustBeAuthenticated(
				anonymousUser.getUuid());
		}

		_userLocalService.deleteUser(anonymousUser.getUserId());

		addUser(actionRequest, actionResponse);
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			ThemeDisplay themeDisplay, String login, String password)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isNotNull(redirect)) {
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			AuthenticatedSessionManagerUtil.login(
				request, response, login, password, false, null);
		}
		else {
			PortletURL loginURL = LoginUtil.getLoginURL(
				request, themeDisplay.getPlid());

			loginURL.setParameter("login", login);

			redirect = loginURL.toString();
		}

		actionResponse.sendRedirect(redirect);
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	protected void updateIncompleteUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		HttpSession session = request.getSession();

		long facebookId = GetterUtil.getLong(
			session.getAttribute(WebKeys.FACEBOOK_INCOMPLETE_USER_ID));

		if (facebookId > 0) {
			password1 = PwdGenerator.getPassword();
			password2 = password1;
		}

		String openId = ParamUtil.getString(actionRequest, "openId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		long suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		boolean updateUserInformation = true;
		boolean sendEmail = true;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		User user = _userService.updateIncompleteUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			sendEmail, updateUserInformation, serviceContext);

		if (facebookId > 0) {
			_userLocalService.updateLastLogin(
				user.getUserId(), user.getLoginIP());

			_userLocalService.updatePasswordReset(user.getUserId(), false);

			_userLocalService.updateEmailAddressVerified(
				user.getUserId(), true);

			session.removeAttribute(WebKeys.FACEBOOK_INCOMPLETE_USER_ID);

			Company company = themeDisplay.getCompany();

			// Send redirect

			String login = null;

			String authType = company.getAuthType();

			if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				login = String.valueOf(user.getUserId());
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				login = user.getScreenName();
			}
			else {
				login = user.getEmailAddress();
			}

			sendRedirect(
				actionRequest, actionResponse, themeDisplay, login, password1);

			return;
		}

		// Session messages

		if (user.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			SessionMessages.add(request, "userAdded", user.getEmailAddress());
			SessionMessages.add(
				request, "userAddedPassword", user.getPasswordUnencrypted());
		}
		else {
			SessionMessages.add(request, "userPending", user.getEmailAddress());
		}

		// Send redirect

		String login = null;

		Company company = themeDisplay.getCompany();

		String authType = company.getAuthType();

		if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			login = String.valueOf(user.getUserId());
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			login = user.getScreenName();
		}
		else {
			login = user.getEmailAddress();
		}

		sendRedirect(
			actionRequest, actionResponse, themeDisplay, login,
			user.getPasswordUnencrypted());
	}

	private static final boolean _AUTO_SCREEN_NAME = false;

	private volatile LayoutLocalService _layoutLocalService;
	private volatile UserLocalService _userLocalService;
	private volatile UserService _userService;

}