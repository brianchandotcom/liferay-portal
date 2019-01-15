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

package com.liferay.users.admin.web.internal.manager;

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
public class EmailAddressContactInfoManager
	extends BaseContactInfoManager<EmailAddress> {

	public EmailAddressContactInfoManager(
		String className, long classPK,
		EmailAddressLocalService emailAddressLocalService,
		EmailAddressService emailAddressService, UsersAdmin usersAdmin) {

		_className = className;
		_classPK = classPK;
		_emailAddressLocalService = emailAddressLocalService;
		_emailAddressService = emailAddressService;
		_usersAdmin = usersAdmin;
	}

	@Override
	protected EmailAddress construct(ActionRequest actionRequest)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(actionRequest, "primaryKey");

		String address = ParamUtil.getString(
			actionRequest, "emailAddressAddress");
		long typeId = ParamUtil.getLong(actionRequest, "emailAddressTypeId");
		boolean primary = ParamUtil.getBoolean(
			actionRequest, "emailAddressPrimary");

		EmailAddress emailAddress =
			_emailAddressLocalService.createEmailAddress(emailAddressId);

		emailAddress.setAddress(address);
		emailAddress.setTypeId(typeId);
		emailAddress.setPrimary(primary);

		return emailAddress;
	}

	@Override
	protected EmailAddress doAdd(EmailAddress emailAddress) throws Exception {
		return _emailAddressService.addEmailAddress(
			_className, _classPK, emailAddress.getAddress(),
			emailAddress.getTypeId(), emailAddress.isPrimary(),
			new ServiceContext());
	}

	@Override
	protected void doDelete(long emailAddressId) throws Exception {
		_emailAddressService.deleteEmailAddress(emailAddressId);
	}

	@Override
	protected void doUpdate(EmailAddress emailAddress) throws Exception {
		_emailAddressService.updateEmailAddress(
			emailAddress.getEmailAddressId(), emailAddress.getAddress(),
			emailAddress.getTypeId(), emailAddress.isPrimary());
	}

	@Override
	protected EmailAddress get(long emailAddressId) throws Exception {
		return _emailAddressService.getEmailAddress(emailAddressId);
	}

	@Override
	protected List<EmailAddress> getAll() throws Exception {
		return _emailAddressService.getEmailAddresses(_className, _classPK);
	}

	@Override
	protected long getPrimaryKey(EmailAddress emailAddress) {
		return emailAddress.getEmailAddressId();
	}

	@Override
	protected boolean isPrimary(EmailAddress emailAddress) {
		return emailAddress.isPrimary();
	}

	@Override
	protected void setPrimary(EmailAddress emailAddress, boolean primary) {
		emailAddress.setPrimary(primary);
	}

	private final String _className;
	private final long _classPK;
	private final EmailAddressLocalService _emailAddressLocalService;
	private final EmailAddressService _emailAddressService;
	private final UsersAdmin _usersAdmin;

}