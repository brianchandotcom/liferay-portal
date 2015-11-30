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

package com.liferay.users.admin.web.portlet.action;

import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactBirthdayException;
import com.liferay.portal.ContactNameException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.PhoneNumberExtensionException;
import com.liferay.portal.RequiredUserException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserFieldException;
import com.liferay.portal.UserIdException;
import com.liferay.portal.UserPasswordException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.UserScreenNameException;
import com.liferay.portal.UserSmsException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.DynamicActionRequest;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ListTypeLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.admin.util.AdminUtil;
import com.liferay.portlet.admin.util.PortalMyAccountApplicationType;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsEntryConstants;
import com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl;
import com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.usersadmin.util.UsersAdmin;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Julio Camarero
 * @author Wesley Gong
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/edit_user"
	},
	service = MVCActionCommand.class
)
public class EditUserMVCActionCommand extends BaseMVCActionCommand {

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
		long prefixId = ParamUtil.getInteger(actionRequest, "prefixId");
		long suffixId = ParamUtil.getInteger(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male", true);
		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth");
		int birthdayDay = ParamUtil.getInteger(actionRequest, "birthdayDay");
		int birthdayYear = ParamUtil.getInteger(actionRequest, "birthdayYear");
		String comments = ParamUtil.getString(actionRequest, "comments");
		String smsSn = ParamUtil.getString(actionRequest, "smsSn");
		String facebookSn = ParamUtil.getString(actionRequest, "facebookSn");
		String jabberSn = ParamUtil.getString(actionRequest, "jabberSn");
		String skypeSn = ParamUtil.getString(actionRequest, "skypeSn");
		String twitterSn = ParamUtil.getString(actionRequest, "twitterSn");
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] groupIds = UsersAdminUtil.getGroupIds(actionRequest);
		long[] organizationIds = UsersAdminUtil.getOrganizationIds(
			actionRequest);
		long[] roleIds = UsersAdminUtil.getRoleIds(actionRequest);
		List<UserGroupRole> userGroupRoles = UsersAdminUtil.getUserGroupRoles(
			actionRequest);
		long[] userGroupIds = UsersAdminUtil.getUserGroupIds(actionRequest);
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

		User user = _userService.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			LocaleUtil.fromLanguageId(languageId), firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, addresses, emailAddresses, phones, websites,
			announcementsDeliveries, sendEmail, serviceContext);

		if (!userGroupRoles.isEmpty()) {
			for (UserGroupRole userGroupRole : userGroupRoles) {
				userGroupRole.setUserId(user.getUserId());
			}

			user = _userService.updateUser(
				user.getUserId(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false, reminderQueryQuestion,
				reminderQueryAnswer, user.getScreenName(),
				user.getEmailAddress(), facebookId, openId, true, null,
				languageId, timeZoneId, greeting, comments, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, smsSn, facebookSn, jabberSn, skypeSn,
				twitterSn, jobTitle, groupIds, organizationIds, roleIds,
				userGroupRoles, userGroupIds, addresses, emailAddresses, phones,
				websites, announcementsDeliveries, serviceContext);
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

		_userService.deleteRoleUser(roleId, user.getUserId());
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

				_userService.updateStatus(
					deleteUserId, status, new ServiceContext());
			}
			else {
				_userService.deleteUser(deleteUserId);
			}
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DynamicActionRequest dynamicActionRequest = new DynamicActionRequest(
			actionRequest);

		long prefixId = getListTypeId(
			actionRequest, "prefixValue", ListTypeConstants.CONTACT_PREFIX);

		dynamicActionRequest.setParameter("prefixId", String.valueOf(prefixId));

		long suffixId = getListTypeId(
			actionRequest, "suffixValue", ListTypeConstants.CONTACT_SUFFIX);

		dynamicActionRequest.setParameter("suffixId", String.valueOf(suffixId));

		actionRequest = dynamicActionRequest;

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
				(_userLocalService.fetchUserById(
					scopeGroup.getClassPK()) == null)) {

				redirect = HttpUtil.setParameter(redirect, "doAsGroupId", 0);
				redirect = HttpUtil.setParameter(redirect, "refererPlid", 0);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			String mvcPath = "/edit_user.jsp";

			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactBirthdayException ||
					 e instanceof ContactNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof MembershipPolicyException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof PhoneNumberExtensionException ||
					 e instanceof RequiredUserException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserFieldException ||
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

				String password1 = actionRequest.getParameter("password1");
				String password2 = actionRequest.getParameter("password2");

				boolean submittedPassword = false;

				if (!Validator.isBlank(password1) ||
					!Validator.isBlank(password2)) {

					submittedPassword = true;
				}

				if (e instanceof CompanyMaxUsersException ||
					e instanceof RequiredUserException || submittedPassword) {

					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (submittedPassword) {
						User user = PortalUtil.getSelectedUser(actionRequest);

						redirect = HttpUtil.setParameter(
							redirect, actionResponse.getNamespace() + "p_u_i_d",
							user.getUserId());
					}

					if (Validator.isNotNull(redirect)) {
						sendRedirect(actionRequest, actionResponse, redirect);

						return;
					}
				}
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected List<AnnouncementsDelivery> getAnnouncementsDeliveries(
		ActionRequest actionRequest) {

		List<AnnouncementsDelivery> announcementsDeliveries = new ArrayList<>();

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

			return _announcementsDeliveryLocalService.getUserDeliveries(
				user.getUserId());
		}

		return getAnnouncementsDeliveries(actionRequest);
	}

	protected long getListTypeId(
			PortletRequest portletRequest, String parameterName, String type)
		throws Exception {

		String parameterValue = ParamUtil.getString(
			portletRequest, parameterName);

		ListType listType = _listTypeLocalService.addListType(
			parameterValue, type);

		return listType.getListTypeId();
	}

	@Reference(unbind = "-")
	protected void setAnnouncementsDeliveryLocalService(
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService) {

		_announcementsDeliveryLocalService = announcementsDeliveryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setListTypeLocalService(
		ListTypeLocalService listTypeLocalService) {

		_listTypeLocalService = listTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	protected User updateLockout(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		_userService.updateLockoutById(user.getUserId(), false);

		return user;
	}

	protected Object[] updateUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = PortalUtil.getSelectedUser(actionRequest);

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
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] portraitBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			portraitBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

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
		long prefixId = BeanParamUtil.getInteger(
			contact, actionRequest, "prefixId");
		long suffixId = BeanParamUtil.getInteger(
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
		String facebookSn = BeanParamUtil.getString(
			contact, actionRequest, "facebookSn");
		String jabberSn = BeanParamUtil.getString(
			contact, actionRequest, "jabberSn");
		String skypeSn = BeanParamUtil.getString(
			contact, actionRequest, "skypeSn");
		String twitterSn = BeanParamUtil.getString(
			contact, actionRequest, "twitterSn");
		String jobTitle = BeanParamUtil.getString(
			user, actionRequest, "jobTitle");
		long[] groupIds = UsersAdminUtil.getGroupIds(actionRequest);
		long[] organizationIds = UsersAdminUtil.getOrganizationIds(
			actionRequest);
		long[] roleIds = UsersAdminUtil.getRoleIds(actionRequest);

		List<UserGroupRole> userGroupRoles = null;

		if ((actionRequest.getParameter("addGroupRolesGroupIds") != null) ||
			(actionRequest.getParameter("addGroupRolesRoleIds") != null) ||
			(actionRequest.getParameter("deleteGroupRolesGroupIds") != null) ||
			(actionRequest.getParameter("deleteGroupRolesRoleIds") != null)) {

			userGroupRoles = UsersAdminUtil.getUserGroupRoles(actionRequest);
		}

		long[] userGroupIds = UsersAdminUtil.getUserGroupIds(actionRequest);
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

		user = _userService.updateUser(
			user.getUserId(), oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, facebookId, openId, !deleteLogo,
			portraitBytes, languageId, timeZoneId, greeting, comments,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, facebookSn,
			jabberSn, skypeSn, twitterSn, jobTitle, groupIds, organizationIds,
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
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
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
		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		if (!portletId.equals(myAccountPortletId)) {
			Group group = user.getGroup();

			boolean hasGroupUpdatePermission = GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group.getGroupId(),
				ActionKeys.UPDATE);

			long publicLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "publicLayoutSetPrototypeId");
			long privateLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "privateLayoutSetPrototypeId");
			boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "publicLayoutSetPrototypeLinkEnabled");
			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "privateLayoutSetPrototypeLinkEnabled");

			if (hasGroupUpdatePermission &&
				((publicLayoutSetPrototypeId > 0) ||
				 (privateLayoutSetPrototypeId > 0))) {

				SitesUtil.updateLayoutSetPrototypesLinks(
					group, publicLayoutSetPrototypeId,
					privateLayoutSetPrototypeId,
					publicLayoutSetPrototypeLinkEnabled,
					privateLayoutSetPrototypeLinkEnabled);
			}
		}

		Company company = PortalUtil.getCompany(actionRequest);

		if (company.isStrangersVerify() &&
			!StringUtil.equalsIgnoreCase(oldEmailAddress, emailAddress)) {

			SessionMessages.add(actionRequest, "verificationEmailSent");
		}

		return new Object[] {user, oldScreenName, updateLanguageId};
	}

	private volatile AnnouncementsDeliveryLocalService
		_announcementsDeliveryLocalService;
	private volatile DLAppLocalService _dlAppLocalService;
	private volatile ListTypeLocalService _listTypeLocalService;
	private volatile UserLocalService _userLocalService;
	private volatile UserService _userService;

}