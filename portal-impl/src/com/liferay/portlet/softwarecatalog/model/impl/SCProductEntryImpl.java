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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCProductEntryImpl extends SCProductEntryBaseImpl {

	public SCProductEntryImpl() {
	}

	@Override
	public SCProductVersion getLatestVersion() throws SystemException {
		List<SCProductVersion> results =
			SCProductVersionLocalServiceUtil.getProductVersions(
				getProductEntryId(), 0, 1);

		SCProductVersion lastVersion = null;

		if (results.size() > 0) {
			lastVersion = results.get(0);
		}

		return lastVersion;
	}

	@Override
	public List<SCLicense> getLicenses() throws SystemException {
		return SCLicenseLocalServiceUtil.getProductEntryLicenses(
			getProductEntryId());
	}

	@Override
	public List<SCProductScreenshot> getScreenshots() throws SystemException {
		return SCProductScreenshotLocalServiceUtil.getProductScreenshots(
			getProductEntryId());
	}

}