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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.softwarecatalog.LicenseNameException;
import com.liferay.portlet.softwarecatalog.RequiredLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.base.SCLicenseLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCLicenseLocalServiceImpl extends SCLicenseLocalServiceBaseImpl {

	@Override
	public SCLicense addLicense(
			String name, String url, boolean openSource, boolean active,
			boolean recommended)
		throws PortalException {

		validate(name);

		long licenseId = counterLocalService.increment();

		SCLicense license = scLicensePersistence.create(licenseId);

		license.setName(name);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setActive(active);
		license.setRecommended(recommended);

		scLicensePersistence.update(license);

		return license;
	}

	@Override
	public void deleteLicense(long licenseId) throws PortalException {
		SCLicense license = scLicensePersistence.findByPrimaryKey(licenseId);

		if (scLicensePersistence.getSCProductEntriesSize(licenseId) > 0) {
			throw new RequiredLicenseException();
		}

		deleteLicense(license);
	}

	@Override
	public void deleteLicense(SCLicense license) {
		scLicensePersistence.remove(license);
	}

	@Override
	public SCLicense getLicense(long licenseId) throws PortalException {
		return scLicensePersistence.findByPrimaryKey(licenseId);
	}

	@Override
	public List<SCLicense> getLicenses() {
		return scLicensePersistence.findAll();
	}

	@Override
	public List<SCLicense> getLicenses(boolean active, boolean recommended) {
		return scLicensePersistence.findByA_R(active, recommended);
	}

	@Override
	public List<SCLicense> getLicenses(
		boolean active, boolean recommended, int start, int end) {

		return scLicensePersistence.findByA_R(active, recommended, start, end);
	}

	@Override
	public List<SCLicense> getLicenses(int start, int end) {
		return scLicensePersistence.findAll(start, end);
	}

	@Override
	public int getLicensesCount() {
		return scLicensePersistence.countAll();
	}

	@Override
	public int getLicensesCount(boolean active, boolean recommended) {
		return scLicensePersistence.countByA_R(active, recommended);
	}

	@Override
	public List<SCLicense> getProductEntryLicenses(long productEntryId) {
		return scProductEntryPersistence.getSCLicenses(productEntryId);
	}

	@Override
	public SCLicense updateLicense(
			long licenseId, String name, String url, boolean openSource,
			boolean active, boolean recommended)
		throws PortalException {

		validate(name);

		SCLicense license = scLicensePersistence.findByPrimaryKey(licenseId);

		license.setName(name);
		license.setUrl(url);
		license.setOpenSource(openSource);
		license.setActive(active);
		license.setRecommended(recommended);

		scLicensePersistence.update(license);

		return license;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LicenseNameException();
		}
	}

}