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

import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.PhoneNumberExtensionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public Long addEntry(Object entry, long organizationId) throws Exception {
		String className = entry.getClass().getName();

		if (className.equals(EmailAddressImpl.class.getName())) {
			EmailAddress emailAddress = (EmailAddress)entry;

			EmailAddress newEmailAddress = _emailAddressService.addEmailAddress(
				Organization.class.getName(), organizationId,
				emailAddress.getAddress(), emailAddress.getTypeId(),
				emailAddress.isPrimary(), new ServiceContext());

			return newEmailAddress.getEmailAddressId();
		}
		else if (className.equals(PhoneImpl.class.getName())) {
			Phone phone = (Phone)entry;

			Phone newPhone = _phoneService.addPhone(
				Organization.class.getName(), organizationId, phone.getNumber(),
				phone.getExtension(), phone.getTypeId(), phone.isPrimary(),
				new ServiceContext());

			return newPhone.getPhoneId();
		}
		else if (className.equals(WebsiteImpl.class.getName())) {
			Website website = (Website)entry;

			Website newWebsite = _websiteService.addWebsite(
				Organization.class.getName(), organizationId, website.getUrl(),
				website.getTypeId(), website.isPrimary(), new ServiceContext());

			return newWebsite.getWebsiteId();
		}

		return 0L;
	}

	public void deleteEntry(long entryId, String listType)
		throws PortalException {

		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			_emailAddressService.deleteEmailAddress(entryId);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			_phoneService.deletePhone(entryId);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			_websiteService.deleteWebsite(entryId);
		}
	}

	public List<Object> getEntries(String listType, long organizationId)
		throws PortalException {

		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			List<EmailAddress> emailAddresses =
				_emailAddressService.getEmailAddresses(
					Organization.class.getName(), organizationId);

			return new ArrayList<>(emailAddresses);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			List<Phone> phones = _phoneService.getPhones(
				Organization.class.getName(), organizationId);

			return new ArrayList<>(phones);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			List<Website> website = _websiteService.getWebsites(
				Organization.class.getName(), organizationId);

			return new ArrayList<>(website);
		}

		return Collections.emptyList();
	}

	public Object getEntry(ActionRequest actionRequest, String listType) {
		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			String address = ParamUtil.getString(
				actionRequest, "emailAddressAddress");
			boolean primary = ParamUtil.getBoolean(
				actionRequest, "emailAddressPrimary");
			long typeId = ParamUtil.getLong(
				actionRequest, "emailAddressTypeId");

			EmailAddress emailAddress =
				_emailAddressLocalService.createEmailAddress(entryId);

			emailAddress.setAddress(address);
			emailAddress.setTypeId(typeId);
			emailAddress.setPrimary(primary);

			return emailAddress;
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			String extension = ParamUtil.getString(
				actionRequest, "phoneExtension");
			String number = ParamUtil.getString(actionRequest, "phoneNumber");
			boolean primary = ParamUtil.getBoolean(
				actionRequest, "phonePrimary");
			long typeId = ParamUtil.getLong(actionRequest, "phoneTypeId");

			Phone phone = _phoneLocalService.createPhone(entryId);

			phone.setNumber(number);
			phone.setExtension(extension);
			phone.setTypeId(typeId);
			phone.setPrimary(primary);

			return phone;
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			boolean primary = ParamUtil.getBoolean(
				actionRequest, "websitePrimary");
			long typeId = ParamUtil.getLong(actionRequest, "websiteTypeId");
			String url = ParamUtil.getString(actionRequest, "websiteUrl");

			Website website = _websiteLocalService.createWebsite(entryId);

			website.setUrl(url);
			website.setTypeId(typeId);
			website.setPrimary(primary);

			return website;
		}

		return null;
	}

	public Object getEntry(long entryId, String listType)
		throws PortalException {

		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			return _emailAddressService.getEmailAddress(entryId);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			return _phoneService.getPhone(entryId);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			return _websiteService.getWebsite(entryId);
		}

		return null;
	}

	public Long getEntryId(Object entry) {
		String className = entry.getClass().getName();

		if (className.equals(EmailAddressImpl.class.getName())) {
			EmailAddress emailAddress = (EmailAddress)entry;

			return emailAddress.getEmailAddressId();
		}
		else if (className.equals(PhoneImpl.class.getName())) {
			Phone phone = (Phone)entry;

			return phone.getPhoneId();
		}
		else if (className.equals(WebsiteImpl.class.getName())) {
			Website website = (Website)entry;

			return website.getWebsiteId();
		}

		return 0L;
	}

	public boolean hasPrimaryEntry(List<Object> entries) {
		if (entries.isEmpty()) {
			return true;
		}

		for (Object entry : entries) {
			if (isPrimaryEntry(entry)) {
				return true;
			}
		}

		return false;
	}

	public boolean isPrimaryEntry(Object entry) {
		String className = entry.getClass().getName();

		if (className.equals(EmailAddressImpl.class.getName())) {
			EmailAddress emailAddress = (EmailAddress)entry;

			return emailAddress.isPrimary();
		}
		else if (className.equals(PhoneImpl.class.getName())) {
			Phone phone = (Phone)entry;

			return phone.isPrimary();
		}
		else if (className.equals(WebsiteImpl.class.getName())) {
			Website website = (Website)entry;

			return website.isPrimary();
		}

		return false;
	}

	public void setAnotherEntryAsPrimary(List<Object> entries, Long entryId) {
		if (entries.isEmpty()) {
			return;
		}
		else if (entries.size() == 1) {
			setFirstEntryAsPrimary(entries);

			return;
		}

		for (int i = 0; i < entries.size(); i++) {
			Object entry = entries.get(i);

			String className = entry.getClass().getName();

			if (className.equals(EmailAddressImpl.class.getName())) {
				EmailAddress emailAddress = (EmailAddress)entry;

				if (emailAddress.getEmailAddressId() != entryId) {
					emailAddress.setPrimary(true);
				}

				entries.set(i, emailAddress);
			}
			else if (className.equals(PhoneImpl.class.getName())) {
				Phone phone = (Phone)entry;

				if (phone.getPhoneId() != entryId) {
					phone.setPrimary(true);
				}

				entries.set(i, phone);
			}
			else if (className.equals(WebsiteImpl.class.getName())) {
				Website website = (Website)entry;

				if (website.getWebsiteId() != entryId) {
					website.setPrimary(true);
				}

				entries.set(i, website);
			}
		}
	}

	public void setEntryAsPrimary(List<Object> entries, Long entryId) {
		if (entries.isEmpty()) {
			return;
		}

		for (int i = 0; i < entries.size(); i++) {
			Object entry = entries.get(i);

			String className = entry.getClass().getName();

			if (className.equals(EmailAddressImpl.class.getName())) {
				EmailAddress emailAddress = (EmailAddress)entry;

				if (emailAddress.getEmailAddressId() == entryId) {
					emailAddress.setPrimary(true);
				}

				entries.set(i, emailAddress);
			}
			else if (className.equals(PhoneImpl.class.getName())) {
				Phone phone = (Phone)entry;

				if (phone.getPhoneId() == entryId) {
					phone.setPrimary(true);
				}

				entries.set(i, phone);
			}
			else if (className.equals(WebsiteImpl.class.getName())) {
				Website website = (Website)entry;

				if (website.getWebsiteId() == entryId) {
					website.setPrimary(true);
				}

				entries.set(i, website);
			}
		}
	}

	public void setFirstEntryAsPrimary(List<Object> entries) {
		if (entries.isEmpty()) {
			return;
		}

		Object entry = entries.get(0);

		String className = entry.getClass().getName();

		if (className.equals(EmailAddressImpl.class.getName())) {
			EmailAddress emailAddress = (EmailAddress)entry;

			emailAddress.setPrimary(true);

			entries.set(0, emailAddress);
		}
		else if (className.equals(PhoneImpl.class.getName())) {
			Phone phone = (Phone)entry;

			phone.setPrimary(true);

			entries.set(0, phone);
		}
		else if (className.equals(WebsiteImpl.class.getName())) {
			Website website = (Website)entry;

			website.setPrimary(true);

			entries.set(0, website);
		}
	}

	public void unsetAllPrimaryEntries(List<Object> entries, String listType) {
		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			for (int i = 0; i < entries.size(); i++) {
				EmailAddress emailAddress = (EmailAddress)entries.get(i);

				emailAddress.setPrimary(false);

				entries.set(i, emailAddress);
			}
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			for (int i = 0; i < entries.size(); i++) {
				Phone phone = (Phone)entries.get(i);

				phone.setPrimary(false);

				entries.set(i, phone);
			}
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			for (int i = 0; i < entries.size(); i++) {
				Website website = (Website)entries.get(i);

				website.setPrimary(false);

				entries.set(i, website);
			}
		}
	}

	public void updateEntries(
			List<Object> entries, String listType, long organizationId)
		throws PortalException {

		if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			List<EmailAddress> emailAddresses = new ArrayList<>();

			for (Object entry : entries) {
				emailAddresses.add((EmailAddress)entry);
			}

			_usersAdmin.updateEmailAddresses(
				Organization.class.getName(), organizationId, emailAddresses);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			List<Phone> phones = new ArrayList<>();

			for (Object entry : entries) {
				phones.add((Phone)entry);
			}

			_usersAdmin.updatePhones(
				Organization.class.getName(), organizationId, phones);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			List<Website> websites = new ArrayList<>();

			for (Object entry : entries) {
				websites.add((Website)entry);
			}

			_usersAdmin.updateWebsites(
				Organization.class.getName(), organizationId, websites);
		}
	}

	public void updateEntry(Object entry) throws Exception {
		String className = entry.getClass().getName();

		if (className.equals(EmailAddressImpl.class.getName())) {
			EmailAddress emailAddress = (EmailAddress)entry;

			_emailAddressService.updateEmailAddress(
				emailAddress.getEmailAddressId(), emailAddress.getAddress(),
				emailAddress.getTypeId(), emailAddress.isPrimary());
		}
		else if (className.equals(PhoneImpl.class.getName())) {
			Phone phone = (Phone)entry;

			_phoneService.updatePhone(
				phone.getPhoneId(), phone.getNumber(), phone.getExtension(),
				phone.getTypeId(), phone.isPrimary());
		}
		else if (className.equals(WebsiteImpl.class.getName())) {
			Website website = (Website)entry;

			_websiteService.updateWebsite(
				website.getWebsiteId(), website.getUrl(), website.getTypeId(),
				website.isPrimary());
		}
	}

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
			else if (e instanceof EmailAddressException ||
					 e instanceof NoSuchListTypeException ||
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

		String listType = ParamUtil.getString(actionRequest, "listType");
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		if (cmd.equals(Constants.DELETE)) {
			Object entry = getEntry(entryId, listType);

			deleteEntry(entryId, listType);

			if (isPrimaryEntry(entry)) {
				List<Object> entries = getEntries(listType, organizationId);

				if (!hasPrimaryEntry(entries)) {
					setFirstEntryAsPrimary(entries);
				}

				updateEntries(entries, listType, organizationId);
			}
		}
		else if (cmd.equals(Constants.EDIT)) {
			Object entry = getEntry(actionRequest, listType);

			if (getEntryId(entry) > 0L) {
				updateEntry(entry);
			}
			else {
				entryId = addEntry(entry, organizationId);
			}

			List<Object> entries = getEntries(listType, organizationId);

			if (isPrimaryEntry(entry)) {
				unsetAllPrimaryEntries(entries, listType);

				setEntryAsPrimary(entries, entryId);
			}
			else if (!hasPrimaryEntry(entries)) {
				setAnotherEntryAsPrimary(entries, entryId);
			}

			updateEntries(entries, listType, organizationId);
		}
		else if (cmd.equals(UsersAdminWebKeys.CMD_MAKE_PRIMARY)) {
			List<Object> entries = getEntries(listType, organizationId);

			unsetAllPrimaryEntries(entries, listType);

			setEntryAsPrimary(entries, entryId);

			updateEntries(entries, listType, organizationId);
		}
	}

	@Reference
	private EmailAddressLocalService _emailAddressLocalService;

	@Reference
	private EmailAddressService _emailAddressService;

	@Reference
	private OrganizationService _organizationService;

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