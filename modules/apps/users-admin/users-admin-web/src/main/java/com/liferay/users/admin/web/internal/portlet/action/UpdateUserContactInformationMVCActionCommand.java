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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.DuplicateOpenIdException;
import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PhoneNumberException;
import com.liferay.portal.kernel.exception.PhoneNumberExtensionException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserSmsException;
import com.liferay.portal.kernel.exception.WebsiteURLException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ORGANIZATIONS,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/update_user_contact_information"
	},
	service = MVCActionCommand.class
)
public class UpdateUserContactInformationMVCActionCommand
	extends BaseContactInformationMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long contactId = ParamUtil.getLong(actionRequest, "classPK");

			User user = _userLocalService.getUserByContactId(contactId);

			UserPermissionUtil.check(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				ActionKeys.UPDATE);

			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (Validator.isNotNull(cmd)) {
				updateContactInformation(actionRequest, Contact.class);
			}
			else {
				saveContactInformationForm(actionRequest);
			}

			String redirect = _portal.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof AddressCityException ||
					 e instanceof AddressStreetException ||
					 e instanceof AddressZipException ||
					 e instanceof DuplicateOpenIdException ||
					 e instanceof EmailAddressException ||
					 e instanceof NoSuchCountryException ||
					 e instanceof NoSuchListTypeException ||
					 e instanceof NoSuchRegionException ||
					 e instanceof PhoneNumberException ||
					 e instanceof PhoneNumberExtensionException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserSmsException ||
					 e instanceof WebsiteURLException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				String errorMvcRenderCommandName = ParamUtil.getString(
					actionRequest, "errorMvcRenderCommandName");

				if (Validator.isNotNull(errorMvcRenderCommandName)) {
					actionResponse.setRenderParameter(
						"mvcRenderCommandName", errorMvcRenderCommandName);
				}
				else {
					actionResponse.setRenderParameter(
						"mvcPath", "/edit_user.jsp");
				}
			}
			else {
				throw e;
			}
		}
	}

	protected void saveContactInformationForm(ActionRequest actionRequest)
		throws Exception {

		long contactId = ParamUtil.getLong(actionRequest, "classPK");

		User user = _userLocalService.getUserByContactId(contactId);

		String facebookSn = ParamUtil.getString(actionRequest, "facebookSn");
		String jabberSn = ParamUtil.getString(actionRequest, "jabberSn");
		String skypeSn = ParamUtil.getString(actionRequest, "skypeSn");
		String smsSn = ParamUtil.getString(actionRequest, "smsSn");
		String twitterSn = ParamUtil.getString(actionRequest, "twitterSn");

		_saveContactInformation(
			user, facebookSn, jabberSn, skypeSn, smsSn, twitterSn);

		String openId = ParamUtil.getString(actionRequest, "openId");

		_validateOpenId(user.getCompanyId(), user.getUserId(), openId);

		_userLocalService.updateOpenId(user.getUserId(), openId);
	}

	private void _saveContactInformation(
			User user, String facebookSn, String jabberSn, String skypeSn,
			String smsSn, String twitterSn)
		throws Exception {

		facebookSn = StringUtil.toLowerCase(StringUtil.trim(facebookSn));
		jabberSn = StringUtil.toLowerCase(StringUtil.trim(jabberSn));
		skypeSn = StringUtil.toLowerCase(StringUtil.trim(skypeSn));
		twitterSn = StringUtil.toLowerCase(StringUtil.trim(twitterSn));

		if (Validator.isNotNull(smsSn) && !Validator.isEmailAddress(smsSn)) {
			throw new UserSmsException.MustBeEmailAddress(smsSn);
		}

		Contact contact = user.fetchContact();

		if (contact == null) {
			contact = _contactLocalService.createContact(user.getContactId());

			contact.setCompanyId(user.getCompanyId());
			contact.setUserName(StringPool.BLANK);
			contact.setClassName(User.class.getName());
			contact.setClassPK(user.getUserId());

			Company company = _companyLocalService.getCompany(
				user.getCompanyId());

			contact.setAccountId(company.getAccountId());

			contact.setParentContactId(
				ContactConstants.DEFAULT_PARENT_CONTACT_ID);
		}

		contact.setSmsSn(smsSn);
		contact.setFacebookSn(facebookSn);
		contact.setJabberSn(jabberSn);
		contact.setSkypeSn(skypeSn);
		contact.setTwitterSn(twitterSn);

		_contactLocalService.updateContact(contact);
	}

	private void _validateOpenId(long companyId, long userId, String openId)
		throws Exception {

		if (Validator.isNull(openId)) {
			return;
		}

		User user = _userLocalService.fetchUserByOpenId(companyId, openId);

		if ((user != null) && (user.getUserId() != userId)) {
			throw new DuplicateOpenIdException("{userId=" + userId + "}");
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}