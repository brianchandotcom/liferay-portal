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

import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.AddressService;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;
import com.liferay.users.admin.web.internal.manager.AddressContactInfoManager;
import com.liferay.users.admin.web.internal.manager.ContactInfoManager;
import com.liferay.users.admin.web.internal.manager.EmailAddressContactInfoManager;
import com.liferay.users.admin.web.internal.manager.OrgLaborContactInfoManager;
import com.liferay.users.admin.web.internal.manager.PhoneContactInfoManager;
import com.liferay.users.admin.web.internal.manager.WebsiteContactInfoManager;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
public abstract class BaseContactInformationMVCActionCommand
	extends BaseMVCActionCommand {

	protected ContactInfoManager getContactInformationHelper(
		ActionRequest actionRequest, String className) {

		String listType = ParamUtil.getString(actionRequest, "listType");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (listType.equals(ListTypeConstants.ADDRESS)) {
			return new AddressContactInfoManager(
				className, classPK, addressLocalService, addressService);
		}
		else if (listType.equals(ListTypeConstants.EMAIL_ADDRESS)) {
			return new EmailAddressContactInfoManager(
				className, classPK, emailAddressLocalService,
				emailAddressService, usersAdmin);
		}
		else if (listType.equals(ListTypeConstants.PHONE)) {
			return new PhoneContactInfoManager(
				className, classPK, phoneLocalService, phoneService,
				usersAdmin);
		}
		else if (listType.equals(ListTypeConstants.ORGANIZATION_SERVICE)) {
			return new OrgLaborContactInfoManager(
				classPK, orgLaborLocalService, orgLaborService);
		}
		else if (listType.equals(ListTypeConstants.WEBSITE)) {
			return new WebsiteContactInfoManager(
				className, classPK, websiteLocalService, websiteService,
				usersAdmin);
		}

		return null;
	}

	protected void updateContactInformation(
			ActionRequest actionRequest, Class clazz)
		throws Exception {

		ContactInfoManager contactInformationHelper =
			getContactInformationHelper(actionRequest, clazz.getName());

		if (contactInformationHelper == null) {
			throw new NoSuchListTypeException();
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long primaryKey = ParamUtil.getLong(actionRequest, "primaryKey");

		if (cmd.equals(Constants.DELETE)) {
			contactInformationHelper.delete(primaryKey);
		}
		else if (cmd.equals(Constants.EDIT)) {
			contactInformationHelper.edit(actionRequest);
		}
		else if (cmd.equals("makePrimary")) {
			contactInformationHelper.makePrimary(primaryKey);
		}
	}

	@Reference
	protected AddressLocalService addressLocalService;

	@Reference
	protected AddressService addressService;

	@Reference
	protected EmailAddressLocalService emailAddressLocalService;

	@Reference
	protected EmailAddressService emailAddressService;

	@Reference
	protected OrgLaborLocalService orgLaborLocalService;

	@Reference
	protected OrgLaborService orgLaborService;

	@Reference
	protected PhoneLocalService phoneLocalService;

	@Reference
	protected PhoneService phoneService;

	@Reference
	protected UsersAdmin usersAdmin;

	@Reference
	protected WebsiteLocalService websiteLocalService;

	@Reference
	protected WebsiteService websiteService;

}