/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service.impl;

import com.liferay.launch.model.LaunchEntry;
import com.liferay.launch.model.LaunchSet;
import com.liferay.launch.service.LaunchSetLocalService;
import com.liferay.launch.service.base.LaunchEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.launch.model.LaunchEntry",
	service = AopService.class
)
public class LaunchEntryLocalServiceImpl
	extends LaunchEntryLocalServiceBaseImpl {

	@Override
	public LaunchEntry addLaunchEntry(
			String externalReferenceCode, long userId, long launchSetId,
			long classNameId, long classPK, String classVersion)
		throws PortalException {

		LaunchEntry launchEntry = launchEntryPersistence.create(
			counterLocalService.increment());

		launchEntry.setExternalReferenceCode(externalReferenceCode);

		LaunchSet launchSet = _launchSetLocalService.getLaunchSet(launchSetId);

		launchEntry.setCompanyId(launchSet.getCompanyId());

		launchEntry.setUserId(userId);
		launchEntry.setLaunchSetId(launchSetId);
		launchEntry.setClassNameId(classNameId);
		launchEntry.setClassPK(classPK);
		launchEntry.setClassVersion(classVersion);

		return launchEntryPersistence.update(launchEntry);
	}

	public LaunchEntry fetchLaunchEntry(
		long classNameId, long classPK, String classVersion) {

		return launchEntryPersistence.fetchByC_C_C(
			classNameId, classPK, classVersion);
	}

	@Reference
	private LaunchSetLocalService _launchSetLocalService;

}