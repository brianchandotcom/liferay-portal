/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.PhoneServiceBaseImpl;
import com.liferay.portal.service.permission.CommonPermissionUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PhoneServiceImpl extends PhoneServiceBaseImpl {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addPhone( String, long,
	 *             String, String, int, boolean, ServiceContext)}
	 */
	@Override
	public Phone addPhone(
			String className, long classPK, String number, String extension,
			int typeId, boolean primary)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return phoneLocalService.addPhone(
			getUserId(), className, classPK, number, extension, typeId,
			primary);
	}

	@Override
	public Phone addPhone(
			String className, long classPK, String number, String extension,
			int typeId, boolean primary, ServiceContext serviceContext)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.UPDATE);

		return phoneLocalService.addPhone(
			getUserId(), className, classPK, number, extension, typeId, primary,
			serviceContext);
	}

	@Override
	public void deletePhone(long phoneId)
		throws PortalException, SystemException {

		Phone phone = phonePersistence.findByPrimaryKey(phoneId);

		CommonPermissionUtil.check(
			getPermissionChecker(), phone.getClassNameId(), phone.getClassPK(),
			ActionKeys.UPDATE);

		phoneLocalService.deletePhone(phone);
	}

	@Override
	public Phone getPhone(long phoneId)
		throws PortalException, SystemException {

		Phone phone = phonePersistence.findByPrimaryKey(phoneId);

		CommonPermissionUtil.check(
			getPermissionChecker(), phone.getClassNameId(), phone.getClassPK(),
			ActionKeys.VIEW);

		return phone;
	}

	@Override
	public List<Phone> getPhones(String className, long classPK)
		throws PortalException, SystemException {

		CommonPermissionUtil.check(
			getPermissionChecker(), className, classPK, ActionKeys.VIEW);

		User user = getUser();

		return phoneLocalService.getPhones(
			user.getCompanyId(), className, classPK);
	}

	@Override
	public Phone updatePhone(
			long phoneId, String number, String extension, int typeId,
			boolean primary)
		throws PortalException, SystemException {

		Phone phone = phonePersistence.findByPrimaryKey(phoneId);

		CommonPermissionUtil.check(
			getPermissionChecker(), phone.getClassNameId(), phone.getClassPK(),
			ActionKeys.UPDATE);

		return phoneLocalService.updatePhone(
			phoneId, number, extension, typeId, primary);
	}

}