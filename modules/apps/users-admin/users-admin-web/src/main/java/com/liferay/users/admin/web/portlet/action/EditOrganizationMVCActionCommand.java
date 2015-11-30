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
import com.liferay.portal.DuplicateOrganizationException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchOrganizationException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.OrganizationNameException;
import com.liferay.portal.OrganizationParentException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.PhoneNumberExtensionException;
import com.liferay.portal.RequiredOrganizationException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/edit_organization"
	},
	service = MVCActionCommand.class
)
public class EditOrganizationMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteOrganizations(ActionRequest actionRequest)
		throws Exception {

		long[] deleteOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrganizationIds"), 0L);

		for (long deleteOrganizationId : deleteOrganizationIds) {
			_organizationService.deleteOrganization(deleteOrganizationId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
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
			String mvcPath = "/edit_organization.jsp";

			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
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
					 e instanceof PhoneNumberExtensionException ||
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

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setOrganizationService(
		OrganizationService organizationService) {

		_organizationService = organizationService;
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
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

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

			organization = _organizationService.addOrganization(
				parentOrganizationId, name, type, regionId, countryId, statusId,
				comments, site, addresses, emailAddresses, orgLabors, phones,
				websites, serviceContext);
		}
		else {

			// Update organization

			organization = _organizationService.updateOrganization(
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

	private volatile DLAppLocalService _dlAppLocalService;
	private volatile OrganizationService _organizationService;

}