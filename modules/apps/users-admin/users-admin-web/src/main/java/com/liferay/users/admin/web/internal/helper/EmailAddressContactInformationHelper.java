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

package com.liferay.users.admin.web.internal.helper;

import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Drew Brokke
 */
public class EmailAddressContactInformationHelper
	extends BaseContactInformationHelper<EmailAddress> {

	public EmailAddressContactInformationHelper(
		Class entityClass, long entityClassPK,
		EmailAddressService emailAddressService,
		EmailAddressLocalService emailAddressLocalService,
		UsersAdmin usersAdmin) {

		_entityClass = entityClass;
		_entityClassPK = entityClassPK;
		_emailAddressService = emailAddressService;
		_emailAddressLocalService = emailAddressLocalService;
		_usersAdmin = usersAdmin;
	}

	@Override
	protected EmailAddress addEntry(EmailAddress emailAddress)
		throws Exception {

		return _emailAddressService.addEmailAddress(
			_entityClass.getName(), _entityClassPK, emailAddress.getAddress(),
			emailAddress.getTypeId(), emailAddress.isPrimary(),
			new ServiceContext());
	}

	@Override
	protected EmailAddress constructEntry(ActionRequest actionRequest)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(actionRequest, "primaryKey");

		String address = ParamUtil.getString(
			actionRequest, "emailAddressAddress");
		boolean primary = ParamUtil.getBoolean(
			actionRequest, "emailAddressPrimary");
		long typeId = ParamUtil.getLong(actionRequest, "emailAddressTypeId");

		EmailAddress emailAddress =
			_emailAddressLocalService.createEmailAddress(emailAddressId);

		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		return emailAddress;
	}

	@Override
	protected void deleteEntry(long emailAddressId) throws Exception {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	@Override
	protected List<EmailAddress> getEntries() throws Exception {
		return _emailAddressService.getEmailAddresses(
			_entityClass.getName(), _entityClassPK);
	}

	@Override
	protected EmailAddress getEntry(long emailAddressId) throws Exception {
		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	@Override
	protected long getEntryId(EmailAddress emailAddress) {
		return emailAddress.getEmailAddressId();
	}

	@Override
	protected boolean isPrimaryEntry(EmailAddress emailAddress) {
		return emailAddress.isPrimary();
	}

	@Override
	protected void setEntryPrimary(EmailAddress emailAddress, boolean primary) {
		emailAddress.setPrimary(primary);
	}

	@Override
	protected void updateEntry(EmailAddress emailAddress) throws Exception {
		_emailAddressService.updateEmailAddress(
			emailAddress.getEmailAddressId(), emailAddress.getAddress(),
			emailAddress.getTypeId(), emailAddress.isPrimary());
	}

	private final EmailAddressLocalService _emailAddressLocalService;
	private final EmailAddressService _emailAddressService;
	private final Class _entityClass;
	private final long _entityClassPK;
	private final UsersAdmin _usersAdmin;

}