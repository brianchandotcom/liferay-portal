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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchPortletItemException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletItemNameException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.PortletItemLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class PortletItemLocalServiceImpl
	extends PortletItemLocalServiceBaseImpl {

	@Override
	public PortletItem addPortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		validate(name);

		long portletItemId = counterLocalService.increment();

		PortletItem portletItem = portletItemPersistence.create(portletItemId);

		portletItem.setGroupId(groupId);
		portletItem.setCompanyId(user.getCompanyId());
		portletItem.setUserId(user.getUserId());
		portletItem.setUserName(user.getFullName());
		portletItem.setName(name);
		portletItem.setPortletId(portletId);
		portletItem.setClassNameId(
			_classNameLocalService.getClassNameId(className));

		return portletItemPersistence.update(portletItem);
	}

	@Override
	public PortletItem getPortletItem(
			long groupId, String name, String portletId, String className)
		throws PortalException {

		return portletItemPersistence.findByG_N_P_C(
			groupId, name, portletId,
			_classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<PortletItem> getPortletItems(long groupId, String className) {
		return portletItemPersistence.findByG_C(
			groupId, _classNameLocalService.getClassNameId(className));
	}

	@Override
	public List<PortletItem> getPortletItems(
		long groupId, String portletId, String className) {

		return portletItemPersistence.findByG_P_C(
			groupId, portletId,
			_classNameLocalService.getClassNameId(className));
	}

	@Override
	public PortletItem updatePortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws PortalException {

		PortletItem portletItem = null;

		try {
			User user = _userPersistence.findByPrimaryKey(userId);

			portletItem = getPortletItem(
				groupId, name, portletId, PortletPreferences.class.getName());

			portletItem.setUserId(userId);
			portletItem.setUserName(user.getFullName());

			portletItem = portletItemPersistence.update(portletItem);
		}
		catch (NoSuchPortletItemException noSuchPortletItemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchPortletItemException, noSuchPortletItemException);
			}

			portletItem = addPortletItem(
				userId, groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new PortletItemNameException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletItemLocalServiceImpl.class);

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}