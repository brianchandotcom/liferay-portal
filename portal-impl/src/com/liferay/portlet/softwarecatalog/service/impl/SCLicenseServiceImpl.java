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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.base.SCLicenseServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.permission.SCLicensePermission;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCLicenseServiceImpl extends SCLicenseServiceBaseImpl {

	@Override
	public SCLicense addLicense(
			String name, String url, boolean openSource, boolean active,
			boolean recommended)
		throws PortalException, SystemException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_LICENSE);

		return scLicenseLocalService.addLicense(
			name, url, openSource, active, recommended);
	}

	@Override
	public void deleteLicense(long licenseId)
		throws PortalException, SystemException {

		SCLicensePermission.check(
			getPermissionChecker(), licenseId, ActionKeys.DELETE);

		scLicenseLocalService.deleteLicense(licenseId);
	}

	@Override
	public SCLicense getLicense(long licenseId)
		throws PortalException, SystemException {

		return scLicenseLocalService.getLicense(licenseId);
	}

	@Override
	public SCLicense updateLicense(
			long licenseId, String name, String url, boolean openSource,
			boolean active, boolean recommended)
		throws PortalException, SystemException {

		SCLicensePermission.check(
			getPermissionChecker(), licenseId, ActionKeys.UPDATE);

		return scLicenseLocalService.updateLicense(
			licenseId, name, url, openSource, active, recommended);
	}

}