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

package com.liferay.portlet.portalsettings.action;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.AddressCityException;
import com.liferay.portal.AddressStreetException;
import com.liferay.portal.AddressZipException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.CompanyWebIdException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.PhoneNumberException;
import com.liferay.portal.WebsiteURLException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Website;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditCompanyAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				validateCAS(actionRequest);
				validateLDAP(actionRequest);
				validateInteractions(actionRequest);

				if (!SessionErrors.isEmpty(actionRequest)) {
					setForward(
						actionRequest, "portlet.portal_settings.edit_company");
				}
				else {
					updateCompany(actionRequest);

					sendRedirect(actionRequest, actionResponse);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.portal_settings.error");
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

				setForward(
					actionRequest, "portlet.portal_settings.edit_company");
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

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.portal_settings.edit_company"));
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
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

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

		CompanyServiceUtil.updateCompany(
			companyId, virtualHostname, mx, homeURL, !deleteLogo, logoBytes,
			name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size, languageId, timeZoneId, addresses,
			emailAddresses, phones, websites, properties);

		PortalUtil.resetCDNHosts();
	}

	protected void validateCAS(ActionRequest actionRequest) throws Exception {
		boolean casEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.CAS_AUTH_ENABLED + "--");

		if (!casEnabled) {
			return;
		}

		String casLoginURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_LOGIN_URL + "--");
		String casLogoutURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_LOGOUT_URL + "--");
		String casServerName = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVER_NAME + "--");
		String casServerURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVER_URL + "--");
		String casServiceURL = ParamUtil.getString(
			actionRequest, "settings--" + PropsKeys.CAS_SERVICE_URL + "--");
		String casNoSuchUserRedirectURL = ParamUtil.getString(
			actionRequest,
			"settings--" + PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL + "--");

		if (!Validator.isUrl(casLoginURL)) {
			SessionErrors.add(actionRequest, "casLoginURLInvalid");
		}

		if (!Validator.isUrl(casLogoutURL)) {
			SessionErrors.add(actionRequest, "casLogoutURLInvalid");
		}

		if (Validator.isNull(casServerName)) {
			SessionErrors.add(actionRequest, "casServerNameInvalid");
		}

		if (Validator.isNotNull(casServerURL) &&
			Validator.isNotNull(casServiceURL)) {

			SessionErrors.add(
				actionRequest, "casServerURLAndServiceURLConflict");
		}
		else if (Validator.isNull(casServerURL) &&
				 Validator.isNull(casServiceURL)) {

			SessionErrors.add(actionRequest, "casServerURLAndServiceURLNotSet");
		}
		else {
			if (Validator.isNotNull(casServerURL) &&
				!Validator.isUrl(casServerURL)) {

				SessionErrors.add(actionRequest, "casServerURLInvalid");
			}

			if (Validator.isNotNull(casServiceURL) &&
				!Validator.isUrl(casServiceURL)) {

				SessionErrors.add(actionRequest, "casServiceURLInvalid");
			}
		}

		if (Validator.isNotNull(casNoSuchUserRedirectURL) &&
			!Validator.isUrl(casNoSuchUserRedirectURL)) {

			SessionErrors.add(actionRequest, "casNoSuchUserURLInvalid");
		}
	}

	protected void validateInteractions(ActionRequest actionRequest)
		throws Exception {

		boolean interactionsEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--interactionsEnabled--");

		if (!interactionsEnabled) {
			return;
		}

		boolean interactionsAnyUserEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--interactionsAnyUserEnabled--");

		if (interactionsAnyUserEnabled) {
			return;
		}

		boolean interactionsSocialRelationTypesEnabled = ParamUtil.getBoolean(
			actionRequest,
			"settings--interactionsSocialRelationTypesEnabled--");
		boolean interactionsSitesEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--interactionsSitesEnabled--");
		String interactionsSocialRelationTypes = ParamUtil.getString(
			actionRequest, "settings--interactionsSocialRelationTypes--", null);

		if (interactionsSocialRelationTypesEnabled &&
			Validator.isBlank(interactionsSocialRelationTypes)) {

			SessionErrors.add(actionRequest, "selectAtLeastOneRelation");
		}

		if (interactionsSocialRelationTypesEnabled ||
			interactionsSitesEnabled) {

			return;
		}

		SessionErrors.add(actionRequest, "restrictedRelationInvalid");
	}

	protected void validateLDAP(ActionRequest actionRequest) throws Exception {
		if (!PropsValues.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED) {
			return;
		}

		boolean ldapExportEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.LDAP_EXPORT_ENABLED + "--");
		boolean ldapImportEnabled = ParamUtil.getBoolean(
			actionRequest, "settings--" + PropsKeys.LDAP_IMPORT_ENABLED + "--");

		if (ldapExportEnabled && ldapImportEnabled) {
			SessionErrors.add(
				actionRequest, "ldapExportAndImportOnPasswordAutogeneration");
		}
	}

}