/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.impl;

import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.base.LaunchSetLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.launch.model.LaunchSet",
	service = AopService.class
)
public class LaunchSetLocalServiceImpl extends LaunchSetLocalServiceBaseImpl {

	@Override
	public LaunchSet addLaunchSet(
			String externalReferenceCode, long userId, String description,
			String name)
		throws PortalException {

		LaunchSet launchSet = launchSetPersistence.create(
			counterLocalService.increment());

		launchSet.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(userId);

		launchSet.setCompanyId(user.getCompanyId());

		launchSet.setUserId(userId);
		launchSet.setDescription(description);
		launchSet.setName(name);

		return launchSetPersistence.update(launchSet);
	}

	@Reference
	private UserLocalService _userLocalService;

}