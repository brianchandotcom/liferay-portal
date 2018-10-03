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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchOrgLaborException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.PhoneNumberExtensionException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.liferay.users.admin.web.internal.helper.AddressContactInformationHelper;
import com.liferay.users.admin.web.internal.helper.ContactInformationHelper;
import com.liferay.users.admin.web.internal.helper.EmailAddressContactInformationHelper;
import com.liferay.users.admin.web.internal.helper.OrgLaborContactInformationHelper;
import com.liferay.users.admin.web.internal.helper.PhoneContactInformationHelper;
import com.liferay.users.admin.web.internal.helper.WebsiteContactInformationHelper;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ORGANIZATIONS,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/update_organization_contact_information"
	},
	service = MVCActionCommand.class
)
public class UpdateOrganizationContactInformationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateContactInformation(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof EmailAddressException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchOrgLaborException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof PhoneNumberExtensionException ||
					 e instanceof WebsiteURLException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				actionResponse.setRenderParameter(
					"mvcPath", "/edit_organization.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected ContactInformationHelper getContactInformationHelper(
		ActionRequest actionRequest) {

		String listType = ParamUtil.getString(actionRequest, "listType");
		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		if (listType.equals(ListTypeConstants.ADDRESS)) {
			return new AddressContactInformationHelper(
				Organization.class, organizationId, _addressLocalService,
				_addressService);
		}
		else if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			return new EmailAddressContactInformationHelper(
				Organization.class, organizationId, _emailAddressService,
				_emailAddressLocalService, _usersAdmin);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			return new PhoneContactInformationHelper(
				Organization.class, organizationId, _phoneService,
				_phoneLocalService, _usersAdmin);
		}
		else if (listType.equals(ListTypeConstants.ORGANIZATION_SERVICE)) {
			return new OrgLaborContactInformationHelper(
				organizationId, _orgLaborLocalService, _orgLaborService);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			return new WebsiteContactInformationHelper(
				Organization.class, organizationId, _websiteService,
				_websiteLocalService, _usersAdmin);
		}

		return null;
	}

	protected void updateContactInformation(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");

		Organization organization = _organizationService.getOrganization(
			organizationId);

		OrganizationPermissionUtil.check(
			themeDisplay.getPermissionChecker(), organization,
			ActionKeys.UPDATE);

		ContactInformationHelper contactInformationHelper =
			getContactInformationHelper(actionRequest);

		if (contactInformationHelper == null) {
			throw new NoSuchListTypeException();
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (cmd.equals(Constants.DELETE)) {
			contactInformationHelper.delete(entryId);
		}
		else if (cmd.equals(Constants.EDIT)) {
			contactInformationHelper.edit(actionRequest);
		}
		else if (cmd.equals("makePrimary")) {
			contactInformationHelper.makePrimary(entryId);
		}
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private AddressService _addressService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private OrgLaborLocalService _orgLaborLocalService;

	@Reference
	private OrgLaborService _orgLaborService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private PhoneService _phoneService;

	@Reference
	private Portal _portal;

	@Reference
	private UsersAdmin _usersAdmin;

	@Reference
	private WebsiteLocalService _websiteLocalService;

	@Reference
	private WebsiteService _websiteService;

}