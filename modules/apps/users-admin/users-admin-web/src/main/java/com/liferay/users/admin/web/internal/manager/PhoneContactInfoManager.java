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

import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.PhoneService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Drew Brokke
 */
public class PhoneContactInfoManager extends BaseContactInfoManager<Phone> {

	public PhoneContactInfoManager(
		Class entityClass, long entityClassPK, PhoneService phoneService,
		PhoneLocalService phoneLocalService, UsersAdmin usersAdmin) {

		_entityClass = entityClass;
		_entityClassPK = entityClassPK;
		_phoneService = phoneService;
		_phoneLocalService = phoneLocalService;
		_usersAdmin = usersAdmin;
	}

	public Phone construct(ActionRequest actionRequest) {
		long phoneId = ParamUtil.getLong(actionRequest, "primaryKey");

		String extension = ParamUtil.getString(actionRequest, "phoneExtension");
		String number = ParamUtil.getString(actionRequest, "phoneNumber");
		boolean primary = ParamUtil.getBoolean(actionRequest, "phonePrimary");
		long typeId = ParamUtil.getLong(actionRequest, "phoneTypeId");

		Phone phone = _phoneLocalService.createPhone(phoneId);

		phone.setNumber(number);
		phone.setExtension(extension);
		phone.setTypeId(typeId);
		phone.setPrimary(primary);

		return phone;
	}

	@Override
	public Phone doAdd(Phone phone) throws Exception {
		return _phoneService.addPhone(
			_entityClass.getName(), _entityClassPK, phone.getNumber(),
			phone.getExtension(), phone.getTypeId(), phone.isPrimary(),
			new ServiceContext());
	}

	@Override
	public void doDelete(long phoneId) throws Exception {
		_phoneService.deletePhone(phoneId);
	}

	@Override
	public void doUpdate(Phone phone) throws Exception {
		_phoneService.updatePhone(
			phone.getPhoneId(), phone.getNumber(), phone.getExtension(),
			phone.getTypeId(), phone.isPrimary());
	}

	@Override
	public Phone get(long phoneId) throws Exception {
		return _phoneService.getPhone(phoneId);
	}

	@Override
	public List<Phone> getAll() throws Exception {
		return _phoneService.getPhones(_entityClass.getName(), _entityClassPK);
	}

	@Override
	public long getPrimaryKey(Phone phone) {
		return phone.getPhoneId();
	}

	@Override
	public boolean isPrimary(Phone phone) {
		return phone.isPrimary();
	}

	@Override
	public void setPrimary(Phone phone, boolean primary) {
		phone.setPrimary(primary);
	}

	private final Class _entityClass;
	private final long _entityClassPK;
	private final PhoneLocalService _phoneLocalService;
	private final PhoneService _phoneService;
	private final UsersAdmin _usersAdmin;

}