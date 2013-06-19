/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.action;

import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactFullNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateOpenIdException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.DuplicateUserScreenNameException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.ReservedUserScreenNameException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsEntryConstants;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.usersadmin.util.UsersAdmin;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Wesley Gong
 */
public class EditUserAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			User user = null;
			String oldScreenName = StringPool.BLANK;
			boolean updateLanguageId = false;

			if (cmd.equals(Constants.ADD)) {
				user = addUser(actionRequest);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.DELETE) ||
					 cmd.equals(Constants.RESTORE)) {

				deleteUsers(actionRequest);
			}
			else if (cmd.equals("deleteRole")) {
				deleteRole(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateUser(
					actionRequest, actionResponse);

				user = (User)returnValue[0];
				oldScreenName = ((String)returnValue[1]);
				updateLanguageId = ((Boolean)returnValue[2]);
			}
			else if (cmd.equals("unlock")) {
				user = updateLockout(actionRequest);
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (user != null) {
				if (Validator.isNotNull(oldScreenName)) {

					// This will fix the redirect if the user is on his personal
					// my account page and changes his screen name. A redirect
					// that references the old screen name no longer points to a
					// valid screen name and therefore needs to be updated.

					Group group = user.getGroup();

					if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
						Layout layout = themeDisplay.getLayout();

						String friendlyURLPath = group.getPathFriendlyURL(
							layout.isPrivateLayout(), themeDisplay);

						String oldPath =
							friendlyURLPath + StringPool.SLASH + oldScreenName;
						String newPath =
							friendlyURLPath + StringPool.SLASH +
								user.getScreenName();

						redirect = StringUtil.replace(
							redirect, oldPath, newPath);

						redirect = StringUtil.replace(
							redirect, HttpUtil.encodeURL(oldPath),
							HttpUtil.encodeURL(newPath));
					}
				}

				if (updateLanguageId && themeDisplay.isI18n()) {
					String i18nLanguageId = user.getLanguageId();
					int pos = i18nLanguageId.indexOf(CharPool.UNDERLINE);

					if (pos != -1) {
						i18nLanguageId = i18nLanguageId.substring(0, pos);
					}

					String i18nPath = StringPool.SLASH + i18nLanguageId;

					redirect = StringUtil.replace(
						redirect, themeDisplay.getI18nPath(), i18nPath);
				}

				redirect = HttpUtil.setParameter(
					redirect, actionResponse.getNamespace() + "p_u_i_d",
					user.getUserId());
			}

			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.isUser() &&
				(UserLocalServiceUtil.fetchUserById(
					scopeGroup.getClassPK()) == null)) {

				redirect = HttpUtil.setParameter(redirect, "doAsGroupId" , 0);
				redirect = HttpUtil.setParameter(redirect, "refererPlid" , 0);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.users_admin.error");
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactBirthdayException ||
					 e instanceof ContactFirstNameException ||
					 e instanceof ContactFullNameException ||
					 e instanceof ContactLastNameException ||
					 e instanceof DuplicateOpenIdException ||
					 e instanceof DuplicateUserEmailAddressException ||
					 e instanceof DuplicateUserScreenNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof MembershipPolicyException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof RequiredUserException ||
					 e instanceof ReservedUserEmailAddressException ||
					 e instanceof ReservedUserScreenNameException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserIdException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserReminderQueryException ||
					 e instanceof UserScreenNameException ||
					 e instanceof UserSmsException ||
					 e instanceof WebsiteURLException) {

				if (e instanceof NoSuchListTypeException) {
					NoSuchListTypeException nslte = (NoSuchListTypeException)e;

					SessionErrors.add(
						actionRequest,
						e.getClass().getName() + nslte.getType());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}

				if (e instanceof CompanyMaxUsersException ||
					e instanceof RequiredUserException) {

					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			PortalUtil.getSelectedUser(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.users_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.users_admin.edit_user"));
	}

	protected User addUser(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = ParamUtil.getBoolean(
			actionRequest, "autoPassword", true);
		String password1 = actionRequest.getParameter("password1");
		String password2 = actionRequest.getParameter("password2");

		String reminderQueryQuestion = ParamUtil.getString(
			actionRequest, "reminderQueryQuestion");

		if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
			reminderQueryQuestion = ParamUtil.getString(
				actionRequest, "reminderQueryCustomQuestion");
		}

		String reminderQueryAnswer = ParamUtil.getString(
			actionRequest, "reminderQueryAnswer");
		boolean autoScreenName = ParamUtil.getBoolean(
			actionRequest, "autoScreenName");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = 0;
		String openId = ParamUtil.getString(actionRequest, "openId");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		String greeting = ParamUtil.getString(actionRequest, "greeting");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		int prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		int suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String comments = ParamUtil.getString(actionRequest, "comments");
		String smsSn = ParamUtil.getString(actionRequest, "smsSn");
		String aimSn = ParamUtil.getString(actionRequest, "aimSn");
		String facebookSn = ParamUtil.getString(actionRequest, "facebookSn");
		String icqSn = ParamUtil.getString(actionRequest, "icqSn");
		String jabberSn = ParamUtil.getString(actionRequest, "jabberSn");
		String msnSn = ParamUtil.getString(actionRequest, "msnSn");
		String mySpaceSn = ParamUtil.getString(actionRequest, "mySpaceSn");
		String skypeSn = ParamUtil.getString(actionRequest, "skypeSn");
		String twitterSn = ParamUtil.getString(actionRequest, "twitterSn");
		String ymSn = ParamUtil.getString(actionRequest, "ymSn");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] groupIds = getLongArray(
			actionRequest, "groupsSearchContainerPrimaryKeys");
		long[] organizationIds = getLongArray(
			actionRequest, "organizationsSearchContainerPrimaryKeys");
		long[] roleIds = getLongArray(
			actionRequest, "rolesSearchContainerPrimaryKeys");
		List<UserGroupRole> userGroupRoles = UsersAdminUtil.getUserGroupRoles(
			actionRequest);
		long[] userGroupIds = getLongArray(
			actionRequest, "userGroupsSearchContainerPrimaryKeys");
		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);
		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);
		List<AnnouncementsDelivery> announcementsDeliveries =
			getAnnouncementsDeliveries(actionRequest);
		boolean sendEmail = true;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		User user = UserServiceUtil.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			LocaleUtil.getDefault(), firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, addresses,
			emailAddresses, phones, websites, announcementsDeliveries,
			sendEmail, serviceContext);

		if (!userGroupRoles.isEmpty()) {
			for (UserGroupRole userGroupRole : userGroupRoles) {
				userGroupRole.setUserId(user.getUserId());
			}

			user = UserServiceUtil.updateUser(
				user.getUserId(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false, reminderQueryQuestion,
				reminderQueryAnswer, user.getScreenName(),
				user.getEmailAddress(), facebookId, openId, languageId,
				timeZoneId, greeting, comments, firstName, middleName, lastName,
				prefixId, suffixId, male, birthdayMonth, birthdayDay,
				birthdayYear, smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn,
				mySpaceSn, skypeSn, twitterSn, ymSn, jobTitle, groupIds,
				organizationIds, roleIds, userGroupRoles, userGroupIds,
				addresses, emailAddresses, phones, websites,
				announcementsDeliveries, serviceContext);
		}

		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled");
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled");

		SitesUtil.updateLayoutSetPrototypesLinks(
			user.getGroup(), publicLayoutSetPrototypeId,
			privateLayoutSetPrototypeId, publicLayoutSetPrototypeLinkEnabled,
			privateLayoutSetPrototypeLinkEnabled);

		return user;
	}

	protected void deleteRole(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		UserServiceUtil.deleteRoleUser(roleId, user.getUserId());
	}

	protected void deleteUsers(ActionRequest actionRequest) throws Exception {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long[] deleteUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteUserIds"), 0L);

		for (long deleteUserId : deleteUserIds) {
			if (cmd.equals(Constants.DEACTIVATE) ||
				cmd.equals(Constants.RESTORE)) {

				int status = WorkflowConstants.STATUS_APPROVED;

				if (cmd.equals(Constants.DEACTIVATE)) {
					status = WorkflowConstants.STATUS_INACTIVE;
				}

				UserServiceUtil.updateStatus(deleteUserId, status);
			}
			else {
				UserServiceUtil.deleteUser(deleteUserId);
			}
		}
	}

	protected List<AnnouncementsDelivery> getAnnouncementsDeliveries(
		ActionRequest actionRequest) {

		List<AnnouncementsDelivery> announcementsDeliveries =
			new ArrayList<AnnouncementsDelivery>();

		for (String type : AnnouncementsEntryConstants.TYPES) {
			boolean email = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Email");
			boolean sms = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Sms");
			boolean website = ParamUtil.getBoolean(
				actionRequest, "announcementsType" + type + "Website");

			AnnouncementsDelivery announcementsDelivery =
				new AnnouncementsDeliveryImpl();

			announcementsDelivery.setType(type);
			announcementsDelivery.setEmail(email);
			announcementsDelivery.setSms(sms);
			announcementsDelivery.setWebsite(website);

			announcementsDeliveries.add(announcementsDelivery);
		}

		return announcementsDeliveries;
	}

	protected List<AnnouncementsDelivery> getAnnouncementsDeliveries(
			ActionRequest actionRequest, User user)
		throws Exception {

		if (actionRequest.getParameter(
				"announcementsType" + AnnouncementsEntryConstants.TYPES[0] +
					"Email") == null) {

			return AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(
				user.getUserId());
		}

		return getAnnouncementsDeliveries(actionRequest);
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	protected User updateLockout(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		UserServiceUtil.updateLockoutById(user.getUserId(), false);

		return user;
	}

	protected Object[] updateUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = PortalUtil.getSelectedUser(actionRequest);

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		if (deleteLogo) {
			UserServiceUtil.deletePortrait(user.getUserId());
		}

		Contact contact = user.getContact();

		String oldPassword = AdminUtil.getUpdateUserPassword(
			actionRequest, user.getUserId());
		String newPassword1 = actionRequest.getParameter("password1");
		String newPassword2 = actionRequest.getParameter("password2");
		boolean passwordReset = ParamUtil.getBoolean(
			actionRequest, "passwordReset");

		String reminderQueryQuestion = BeanParamUtil.getString(
			user, actionRequest, "reminderQueryQuestion");

		if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
			reminderQueryQuestion = BeanParamUtil.getStringSilent(
				user, actionRequest, "reminderQueryCustomQuestion");
		}

		String reminderQueryAnswer = BeanParamUtil.getString(
			user, actionRequest, "reminderQueryAnswer");
		String oldScreenName = user.getScreenName();
		String screenName = BeanParamUtil.getString(
			user, actionRequest, "screenName");
		String oldEmailAddress = user.getEmailAddress();
		String emailAddress = BeanParamUtil.getString(
			user, actionRequest, "emailAddress");
		long facebookId = user.getFacebookId();
		String openId = BeanParamUtil.getString(user, actionRequest, "openId");
		String languageId = BeanParamUtil.getString(
			user, actionRequest, "languageId");
		String timeZoneId = BeanParamUtil.getString(
			user, actionRequest, "timeZoneId");
		String greeting = BeanParamUtil.getString(
			user, actionRequest, "greeting");
		String firstName = BeanParamUtil.getString(
			user, actionRequest, "firstName");
		String middleName = BeanParamUtil.getString(
			user, actionRequest, "middleName");
		String lastName = BeanParamUtil.getString(
			user, actionRequest, "lastName");
		int prefixId = BeanParamUtil.getInteger(
			contact, actionRequest, "prefixId");
		int suffixId = BeanParamUtil.getInteger(
			contact, actionRequest, "suffixId");
		boolean male = BeanParamUtil.getBoolean(
			user, actionRequest, "male", true);

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth", birthdayCal.get(Calendar.MONTH));
		int birthdayDay = ParamUtil.getInteger(
			actionRequest, "birthdayDay", birthdayCal.get(Calendar.DATE));
		int birthdayYear = ParamUtil.getInteger(
			actionRequest, "birthdayYear", birthdayCal.get(Calendar.YEAR));
		String comments = BeanParamUtil.getString(
			user, actionRequest, "comments");
		String smsSn = BeanParamUtil.getString(contact, actionRequest, "smsSn");
		String aimSn = BeanParamUtil.getString(contact, actionRequest, "aimSn");
		String facebookSn = BeanParamUtil.getString(
			contact, actionRequest, "facebookSn");
		String icqSn = BeanParamUtil.getString(contact, actionRequest, "icqSn");
		String jabberSn = BeanParamUtil.getString(
			contact, actionRequest, "jabberSn");
		String msnSn = BeanParamUtil.getString(contact, actionRequest, "msnSn");
		String mySpaceSn = BeanParamUtil.getString(
			contact, actionRequest, "mySpaceSn");
		String skypeSn = BeanParamUtil.getString(
			contact, actionRequest, "skypeSn");
		String twitterSn = BeanParamUtil.getString(
			contact, actionRequest, "twitterSn");
		String ymSn = BeanParamUtil.getString(contact, actionRequest, "ymSn");
		String jobTitle = BeanParamUtil.getString(
			user, actionRequest, "jobTitle");
		long[] groupIds = getLongArray(
			actionRequest, "groupsSearchContainerPrimaryKeys");
		long[] organizationIds = getLongArray(
			actionRequest, "organizationsSearchContainerPrimaryKeys");
		long[] roleIds = getLongArray(
			actionRequest, "rolesSearchContainerPrimaryKeys");

		List<UserGroupRole> userGroupRoles = null;

		if ((actionRequest.getParameter("groupRolesGroupIds") != null) ||
			(actionRequest.getParameter("groupRolesRoleIds") != null)) {

			userGroupRoles = UsersAdminUtil.getUserGroupRoles(actionRequest);
		}

		long[] userGroupIds = getLongArray(
			actionRequest, "userGroupsSearchContainerPrimaryKeys");
		List<Address> addresses = UsersAdminUtil.getAddresses(
			actionRequest, user.getAddresses());
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest, user.getEmailAddresses());
		List<Phone> phones = UsersAdminUtil.getPhones(
			actionRequest, user.getPhones());
		List<Website> websites = UsersAdminUtil.getWebsites(
			actionRequest, user.getWebsites());
		List<AnnouncementsDelivery> announcementsDeliveries =
			getAnnouncementsDeliveries(actionRequest, user);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		user = UserServiceUtil.updateUser(
			user.getUserId(), oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, facebookId, openId, languageId,
			timeZoneId, greeting, comments, firstName, middleName, lastName,
			prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn,
			skypeSn, twitterSn, ymSn, jobTitle, groupIds, organizationIds,
			roleIds, userGroupRoles, userGroupIds, addresses, emailAddresses,
			phones, websites, announcementsDeliveries, serviceContext);

		if (oldScreenName.equals(user.getScreenName())) {
			oldScreenName = StringPool.BLANK;
		}

		boolean updateLanguageId = false;

		if (user.getUserId() == themeDisplay.getUserId()) {

			// Reset the locale

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response= PortalUtil.getHttpServletResponse(
				actionResponse);
			HttpSession session = request.getSession();

			session.removeAttribute(Globals.LOCALE_KEY);

			Locale locale = LocaleUtil.fromLanguageId(languageId);

			LanguageUtil.updateCookie(request, response, locale);

			// Clear cached portlet responses

			PortletSession portletSession = actionRequest.getPortletSession();

			InvokerPortletImpl.clearResponses(portletSession);

			// Password

			if (PropsValues.SESSION_STORE_PASSWORD &&
				Validator.isNotNull(newPassword1)) {

				portletSession.setAttribute(
					WebKeys.USER_PASSWORD, newPassword1,
					PortletSession.APPLICATION_SCOPE);
			}

			updateLanguageId = true;
		}

		String portletId = serviceContext.getPortletId();

		if (!portletId.equals(PortletKeys.MY_ACCOUNT)) {
			long publicLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "publicLayoutSetPrototypeId");
			long privateLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "privateLayoutSetPrototypeId");
			boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "publicLayoutSetPrototypeLinkEnabled");
			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "privateLayoutSetPrototypeLinkEnabled");

			SitesUtil.updateLayoutSetPrototypesLinks(
				user.getGroup(), publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		Company company = PortalUtil.getCompany(actionRequest);

		if (company.isStrangersVerify() &&
			!oldEmailAddress.equalsIgnoreCase(emailAddress)) {

			SessionMessages.add(actionRequest, "verificationEmailSent");
		}

		return new Object[] {user, oldScreenName, updateLanguageId};
	}

}