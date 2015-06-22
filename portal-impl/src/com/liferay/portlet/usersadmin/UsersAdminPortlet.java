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

package com.liferay.portlet.usersadmin;

import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchOrgLaborException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class UsersAdminPortlet extends MVCPortlet {

	public User addUser(ActionRequest actionRequest) throws Exception {
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

		User user = UserServiceUtil.addUser(
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

			user = UserServiceUtil.updateUser(
				user.getUserId(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false, reminderQueryQuestion,
				reminderQueryAnswer, user.getScreenName(),
				user.getEmailAddress(), facebookId, openId, true, null,
				languageId, timeZoneId, greeting, comments, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, smsSn, aimSn, facebookSn, icqSn,
				jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn, jobTitle,
				groupIds, organizationIds, roleIds, userGroupRoles,
				userGroupIds, addresses, emailAddresses, phones, websites,
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

	public void deleteOrganizations(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrganizationIds"), 0L);

		for (long deleteOrganizationId : deleteOrganizationIds) {
			OrganizationServiceUtil.deleteOrganization(deleteOrganizationId);
		}
	}

	public void deleteOrgLabor(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "orgLaborId");

		OrgLaborServiceUtil.deleteOrgLabor(orgLaborId);
	}

	public void deleteRole(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		UserServiceUtil.deleteRoleUser(roleId, user.getUserId());
	}

	public void deleteUsers(ActionRequest actionRequest) throws Exception {
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

				UserServiceUtil.updateStatus(
					deleteUserId, status, new ServiceContext());
			}
			else {
				UserServiceUtil.deleteUser(deleteUserId);
			}
		}
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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
				(UserLocalServiceUtil.fetchUserById(
					scopeGroup.getClassPK()) == null)) {

				redirect = HttpUtil.setParameter(redirect, "doAsGroupId", 0);
				redirect = HttpUtil.setParameter(redirect, "refererPlid", 0);
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
					 e instanceof ContactNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof MembershipPolicyException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
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

	public User updateLockout(ActionRequest actionRequest) throws Exception {
		User user = PortalUtil.getSelectedUser(actionRequest);

		UserServiceUtil.updateLockoutById(user.getUserId(), false);

		return user;
	}

	public void updateOrganization(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		long parentOrganizationId = ParamUtil.getLong(
			actionRequest, "parentOrganizationSearchContainerPrimaryKeys",
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
		String name = ParamUtil.getString(actionRequest, "name");
		long statusId = ParamUtil.getLong(actionRequest, "statusId");
		String type = ParamUtil.getString(actionRequest, "type");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		String comments = ParamUtil.getString(actionRequest, "comments");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		boolean site = ParamUtil.getBoolean(actionRequest, "site");
		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);
		List<OrgLabor> orgLabors = UsersAdminUtil.getOrgLabors(actionRequest);
		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Organization.class.getName(), actionRequest);

		Organization organization = null;

		if (organizationId <= 0) {

			// Add organization

			organization = OrganizationServiceUtil.addOrganization(
				parentOrganizationId, name, type, regionId, countryId, statusId,
				comments, site, addresses, emailAddresses, orgLabors, phones,
				websites, serviceContext);
		}
		else {

			// Update organization

			organization = OrganizationServiceUtil.updateOrganization(
				organizationId, parentOrganizationId, name, type, regionId,
				countryId, statusId, comments, !deleteLogo, logoBytes, site,
				addresses, emailAddresses, orgLabors, phones, websites,
				serviceContext);
		}

		// Layout set prototypes

		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");
		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled",
			(publicLayoutSetPrototypeId > 0));
		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled",
			(privateLayoutSetPrototypeId > 0));

		Group organizationGroup = organization.getGroup();

		if (GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), organizationGroup,
				ActionKeys.UPDATE)) {

			SitesUtil.updateLayoutSetPrototypesLinks(
				organizationGroup, publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		// Reminder queries

		String reminderQueries = actionRequest.getParameter("reminderQueries");

		PortletPreferences portletPreferences = organization.getPreferences();

		LocalizationUtil.setLocalizedPreferencesValues(
			actionRequest, portletPreferences, "reminderQueries");

		portletPreferences.setValue("reminderQueries", reminderQueries);

		portletPreferences.store();

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		redirect = HttpUtil.setParameter(
			redirect, actionResponse.getNamespace() + "organizationId",
			organization.getOrganizationId());

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void updateOrganizationUserGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(organizationId);

		long groupId = organization.getGroupId();

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		UserGroupServiceUtil.addGroupUserGroups(groupId, addUserGroupIds);
		UserGroupServiceUtil.unsetGroupUserGroups(groupId, removeUserGroupIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void updateOrganizationUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		UserServiceUtil.addOrganizationUsers(organizationId, addUserIds);
		UserServiceUtil.unsetOrganizationUsers(organizationId, removeUserIds);

		String redirect = ParamUtil.getString(
			actionRequest, "assignmentsRedirect");

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	public void updateOrgLabor(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "orgLaborId");

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");
		long typeId = ParamUtil.getLong(actionRequest, "typeId");

		int sunOpen = ParamUtil.getInteger(actionRequest, "sunOpen");
		int sunClose = ParamUtil.getInteger(actionRequest, "sunClose");

		int monOpen = ParamUtil.getInteger(actionRequest, "monOpen");
		int monClose = ParamUtil.getInteger(actionRequest, "monClose");

		int tueOpen = ParamUtil.getInteger(actionRequest, "tueOpen");
		int tueClose = ParamUtil.getInteger(actionRequest, "tueClose");

		int wedOpen = ParamUtil.getInteger(actionRequest, "wedOpen");
		int wedClose = ParamUtil.getInteger(actionRequest, "wedClose");

		int thuOpen = ParamUtil.getInteger(actionRequest, "thuOpen");
		int thuClose = ParamUtil.getInteger(actionRequest, "thuClose");

		int friOpen = ParamUtil.getInteger(actionRequest, "friOpen");
		int friClose = ParamUtil.getInteger(actionRequest, "friClose");

		int satOpen = ParamUtil.getInteger(actionRequest, "satOpen");
		int satClose = ParamUtil.getInteger(actionRequest, "satClose");

		if (orgLaborId <= 0) {

			// Add organization labor

			OrgLaborServiceUtil.addOrgLabor(
				organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
				tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
				friOpen, friClose, satOpen, satClose);
		}
		else {

			// Update organization labor

			OrgLaborServiceUtil.updateOrgLabor(
				orgLaborId, typeId, sunOpen, sunClose, monOpen, monClose,
				tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose,
				friOpen, friClose, satOpen, satClose);
		}
	}

	public Object[] updateUser(
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
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

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

		user = UserServiceUtil.updateUser(
			user.getUserId(), oldPassword, newPassword1, newPassword2,
			passwordReset, reminderQueryQuestion, reminderQueryAnswer,
			screenName, emailAddress, facebookId, openId, !deleteLogo,
			portraitBytes, languageId, timeZoneId, greeting, comments,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
			icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
			jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
			userGroupIds, addresses, emailAddresses, phones, websites,
			announcementsDeliveries, serviceContext);

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

		if (!portletId.equals(PortletKeys.MY_ACCOUNT)) {
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

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchOrganizationException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchOrgLaborException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
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

			return AnnouncementsDeliveryLocalServiceUtil.getUserDeliveries(
				user.getUserId());
		}

		return getAnnouncementsDeliveries(actionRequest);
	}

	protected long getListTypeId(
			PortletRequest portletRequest, String parameterName, String type)
		throws Exception {

		String parameterValue = ParamUtil.getString(
			portletRequest, parameterName);

		ListType listType = ListTypeLocalServiceUtil.addListType(
			parameterValue, type);

		return listType.getListTypeId();
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AddressCityException ||
			cause instanceof AddressStreetException ||
			cause instanceof AddressZipException ||
			cause instanceof DuplicateOrganizationException ||
			cause instanceof EmailAddressException ||
			cause instanceof MembershipPolicyException ||
			cause instanceof NoSuchCountryException ||
			cause instanceof NoSuchListTypeException ||
			cause instanceof NoSuchOrganizationException ||
			cause instanceof NoSuchOrgLaborException ||
			cause instanceof NoSuchRegionException ||
			cause instanceof OrganizationNameException ||
			cause instanceof OrganizationParentException ||
			cause instanceof PhoneNumberException ||
			cause instanceof RequiredOrganizationException ||
			cause instanceof WebsiteURLException) {

			return true;
		}

		return false;
	}

}