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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Organization;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.OrgLaborServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class UsersAdminPortlet extends MVCPortlet {

	public void deleteOrgLabor(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "orgLaborId");

		OrgLaborServiceUtil.deleteOrgLabor(orgLaborId);
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			Organization organization = null;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				organization = updateOrganization(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrganizations(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (organization != null) {
				redirect = HttpUtil.setParameter(
					redirect, actionResponse.getNamespace() + "organizationId",
					organization.getOrganizationId());
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.users_admin.error");
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof DuplicateOrganizationException ||
					 e instanceof EmailAddressException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof OrganizationNameException ||
					 e instanceof OrganizationParentException ||
					 e instanceof PhoneNumberException ||
					 e instanceof RequiredOrganizationException ||
					 e instanceof WebsiteURLException) {

				if (e instanceof NoSuchListTypeException) {
					NoSuchListTypeException nslte = (NoSuchListTypeException)e;

					SessionErrors.add(
						actionRequest,
						e.getClass().getName() + nslte.getType());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass());
				}

				if (e instanceof RequiredOrganizationException) {
					String redirect = PortalUtil.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					long organizationId = ParamUtil.getLong(
						actionRequest, "organizationId");

					if (organizationId > 0) {
						redirect = HttpUtil.setParameter(
							redirect,
							actionResponse.getNamespace() + "organizationId",
							organizationId);
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
			ActionUtil.getOrganization(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.users_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.users_admin.edit_organization"));
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

	protected void deleteOrganizations(ActionRequest actionRequest)
		throws Exception {

		long[] deleteOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrganizationIds"), 0L);

		for (long deleteOrganizationId : deleteOrganizationIds) {
			OrganizationServiceUtil.deleteOrganization(deleteOrganizationId);
		}
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

	protected Organization updateOrganization(ActionRequest actionRequest)
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

		return organization;
	}

}