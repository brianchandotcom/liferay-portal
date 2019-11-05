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

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseFormMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.web.internal.exception.RequiredLocaleException;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
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
				_validateDefaultLanguage(actionRequest);

				_validateAvailableLanguages(actionRequest);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				updateCompany(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");

				return;
			}
			else if (e instanceof AccountNameException ||
					 e instanceof AddressCityException ||
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
					 e instanceof RequiredLocaleException ||
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

			SessionErrors.add(actionRequest, e.getClass(), e);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
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
		long companyId = _portal.getCompanyId(actionRequest);

		Company company = _companyService.getCompanyById(companyId);

		String virtualHostname = ParamUtil.getString(
			actionRequest, "virtualHostname", company.getVirtualHostname());
		String mx = ParamUtil.getString(actionRequest, "mx", company.getMx());
		String homeURL = ParamUtil.getString(
			actionRequest, "homeURL", company.getHomeURL());

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		String name = ParamUtil.getString(
			actionRequest, "name", company.getName());

		Account account = company.getAccount();

		String legalName = ParamUtil.getString(
			actionRequest, "legalName", account.getLegalName());
		String legalId = ParamUtil.getString(
			actionRequest, "legalId", account.getLegalId());
		String legalType = ParamUtil.getString(
			actionRequest, "legalType", account.getLegalType());
		String sicCode = ParamUtil.getString(
			actionRequest, "sicCode", account.getSicCode());
		String tickerSymbol = ParamUtil.getString(
			actionRequest, "tickerSymbol", account.getTickerSymbol());
		String industry = ParamUtil.getString(
			actionRequest, "industry", account.getIndustry());
		String type = ParamUtil.getString(
			actionRequest, "type", account.getType());
		String size = ParamUtil.getString(
			actionRequest, "size", account.getSize());

		User defaultUser = _userLocalService.getDefaultUser(companyId);

		String languageId = ParamUtil.getString(
			actionRequest, "languageId", defaultUser.getLanguageId());
		String timeZoneId = ParamUtil.getString(
			actionRequest, "timeZoneId", defaultUser.getTimeZoneId());

		List<Address> addresses = UsersAdminUtil.getAddresses(actionRequest);

		if (addresses.isEmpty()) {
			addresses = _addressLocalService.getAddresses(
				companyId, Account.class.getName(), company.getAccountId());
		}

		List<EmailAddress> emailAddresses = UsersAdminUtil.getEmailAddresses(
			actionRequest);

		if (emailAddresses.isEmpty()) {
			emailAddresses = _emailAddressLocalService.getEmailAddresses(
				companyId, Account.class.getName(), company.getAccountId());
		}

		List<Phone> phones = UsersAdminUtil.getPhones(actionRequest);

		if (phones.isEmpty()) {
			phones = _phoneLocalService.getPhones(
				companyId, Account.class.getName(), company.getAccountId());
		}

		List<Website> websites = UsersAdminUtil.getWebsites(actionRequest);

		if (websites.isEmpty()) {
			websites = _websiteLocalService.getWebsites(
				companyId, Account.class.getName(), company.getAccountId());
		}

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		if (properties.containsKey("admin.email.from.address")) {
			String newEmail = properties.getProperty(
				"admin.email.from.address");

			if (!Validator.isEmailAddress(newEmail)) {
				throw new EmailAddressException();
			}
		}

		String[] discardLegacyKeys = ParamUtil.getStringValues(
			actionRequest, "discardLegacyKey");

		PortletPreferences portletPreferences = _prefsProps.getPreferences(
			companyId);

		Enumeration<String> names = portletPreferences.getNames();

		try {
			while (names.hasMoreElements()) {
				String name2 = names.nextElement();

				for (String discardLegacyKey : discardLegacyKeys) {
					if (name2.startsWith(discardLegacyKey + "_")) {
						portletPreferences.reset(name2);
						properties.remove(name2);
					}
				}
			}

			portletPreferences.store();
		}
		catch (ReadOnlyException roe) {
			throw new SystemException(roe);
		}

		_companyService.updateCompany(
			companyId, virtualHostname, mx, homeURL, !deleteLogo, logoBytes,
			name, legalName, legalId, legalType, sicCode, tickerSymbol,
			industry, type, size, languageId, timeZoneId, addresses,
			emailAddresses, phones, websites, properties);

		_portal.resetCDNHosts();
	}

	private void _validateAvailableLanguages(ActionRequest actionRequest)
		throws PortalException {

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		String newLanguageIds = properties.getProperty(PropsKeys.LOCALES);

		if (Validator.isNull(newLanguageIds)) {
			return;
		}

		long companyId = _portal.getCompanyId(actionRequest);

		String[] removedLanguageIds = ArrayUtil.filter(
			LocaleUtil.toLanguageIds(
				LanguageUtil.getCompanyAvailableLocales(companyId)),
			languageId -> !StringUtil.contains(
				newLanguageIds, languageId, StringPool.COMMA));

		if (ArrayUtil.isEmpty(removedLanguageIds)) {
			return;
		}

		DynamicQuery dynamicQuery = _groupLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.like(
				"typeSettings", "%inheritLocales=false%"));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		for (String removedLanguageId : removedLanguageIds) {
			disjunction.add(
				RestrictionsFactoryUtil.like(
					"typeSettings", "%languageId=" + removedLanguageId + "%"));
		}

		dynamicQuery.add(disjunction);

		List<Group> groups = _groupLocalService.dynamicQuery(dynamicQuery);

		if (!groups.isEmpty()) {
			throw new RequiredLocaleException(groups);
		}
	}

	private void _validateDefaultLanguage(ActionRequest actionRequest)
		throws PortalException {

		String languageId = ParamUtil.getString(
			actionRequest, "languageId", StringPool.IS_NULL);

		if (Objects.equals(languageId, StringPool.IS_NULL)) {
			return;
		}

		if (Validator.isNull(languageId)) {
			throw new RequiredLocaleException(
				"you-must-choose-a-default-language");
		}

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		String newLanguageIds = properties.getProperty(PropsKeys.LOCALES);

		if (Validator.isNull(newLanguageIds) ||
			!StringUtil.contains(
				newLanguageIds, languageId, StringPool.COMMA)) {

			throw new RequiredLocaleException(
				"you-cannot-remove-a-language-that-is-the-current-default-" +
					"language");
		}
	}

	@Reference
	private AddressLocalService _addressLocalService;

	private CompanyService _companyService;
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PrefsProps _prefsProps;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WebsiteLocalService _websiteLocalService;

}