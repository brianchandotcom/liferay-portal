/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
import com.liferay.commerce.product.service.base.CPConfigurationEntrySettingLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPConfigurationEntrySetting",
	service = AopService.class
)
public class CPConfigurationEntrySettingLocalServiceImpl
	extends CPConfigurationEntrySettingLocalServiceBaseImpl {

	@Override
	public CPConfigurationEntrySetting addCPConfigurationEntrySetting(
			long userId, long groupId, long cpConfigurationEntryId,
			String setting, int type)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		CPConfigurationEntrySetting cpConfigurationEntrySetting =
			cpConfigurationEntrySettingPersistence.create(
				counterLocalService.increment());

		cpConfigurationEntrySetting.setGroupId(groupId);
		cpConfigurationEntrySetting.setCompanyId(user.getCompanyId());
		cpConfigurationEntrySetting.setUserId(user.getUserId());
		cpConfigurationEntrySetting.setUserName(user.getFullName());

		cpConfigurationEntrySetting.setCPConfigurationEntryId(
			cpConfigurationEntryId);
		cpConfigurationEntrySetting.setSetting(setting);
		cpConfigurationEntrySetting.setType(type);

		return cpConfigurationEntrySettingPersistence.update(
			cpConfigurationEntrySetting);
	}

	@Override
	public CPConfigurationEntrySetting fetchCPConfigurationEntrySetting(
		long cpConfigurationEntryId, int type) {

		return cpConfigurationEntrySettingPersistence.fetchByC_T(
			cpConfigurationEntryId, type);
	}

	@Reference
	private UserLocalService _userLocalService;

}