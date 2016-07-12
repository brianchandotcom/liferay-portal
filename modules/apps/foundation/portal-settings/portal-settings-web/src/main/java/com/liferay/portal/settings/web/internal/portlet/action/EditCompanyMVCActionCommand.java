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

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.AccountNameException;
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.PhoneNumberExtensionException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseFormMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.settings.web.internal.constants.PortalSettingsPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + PortalSettingsPortletKeys.PORTAL_SETTINGS,
		"mvc.command.name=/portal_settings/edit_company"
	},
	service = MVCActionCommand.class
)
public class EditCompanyMVCActionCommand extends BaseFormMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				updateCompany(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			String mvcPath = "/edit_company.jsp";

			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AccountNameException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof CompanyMxException ||
					 e instanceof CompanyVirtualHostException ||
					 e instanceof CompanyWebIdException ||
					 e instanceof EmailAddressException ||
					 e instanceof LocaleException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof PhoneNumberExtensionException ||
					 e instanceof WebsiteURLException) {

				if (e instanceof NoSuchListTypeException) {
					NoSuchListTypeException nslte = (NoSuchListTypeException)e;

					Class<?> clazz = e.getClass();

					SessionErrors.add(
						actionRequest, clazz.getName() + nslte.getType());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	@Override
	protected void doValidateForm(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	}

	@Reference(unbind = "-")
	protected void setCompanyService(CompanyService companyService) {
		_companyService = companyService;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	protected void updateCompany(ActionRequest actionRequest) throws Exception {
		long companyId = PortalUtil.getCompanyId(actionRequest);

		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname");
		String mx = ParamUtil.getString(actionRequest, "mx");
		String homeURL = ParamUtil.getString(actionRequest, "homeURL");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		String name = ParamUtil.getString(actionRequest, "name");
		String legalName = ParamUtil.getString(actionRequest, "legalName");
		String legalId = ParamUtil.getString(actionRequest, "legalId");
		String legalType = ParamUtil.getString(actionRequest, "legalType");
		String sicCode = ParamUtil.getString(actionRequest, "sicCode");
		String tickerSymbol = ParamUtil.getString(
			actionRequest, "tickerSymbol");
		String industry = ParamUtil.getString(actionRequest, "industry");
		String type = ParamUtil.getString(actionRequest, "type");
		String size = ParamUtil.getString(actionRequest, "size");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String timeZoneId = ParamUtil.getString(actionRequest, "timeZoneId");
		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);
		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);
		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);
		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);
		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		_companyService.updateCompany(
			companyId, virtualHostname, mx, homeURL, !deleteLogo, logoBytes,
			name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size, languageId, timeZoneId, addresses,
			emailAddresses, phones, websites, properties);

		PortalUtil.resetCDNHosts();
	}

	private CompanyService _companyService;
	private DLAppLocalService _dlAppLocalService;

}