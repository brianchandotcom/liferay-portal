/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ReleaseLocalServiceImpl extends ReleaseLocalServiceBaseImpl {

	@Override
	public Release addRelease(String servletContextName, int buildNumber) {
		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.create(ReleaseConstants.DEFAULT_ID);
		}
		else {
			long releaseId = counterLocalService.increment();

			release = releasePersistence.create(releaseId);
		}

		Date date = new Date();

		release.setCreateDate(date);
		release.setModifiedDate(date);

		release.setServletContextName(servletContextName);
		release.setBuildNumber(buildNumber);

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release.setTestString(ReleaseConstants.TEST_STRING);
		}

		return releasePersistence.update(release);
	}

	@Override
	public Release addRelease(String servletContextName, String schemaVersion) {
		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.create(ReleaseConstants.DEFAULT_ID);
		}
		else {
			long releaseId = counterLocalService.increment();

			release = releasePersistence.create(releaseId);
		}

		Date date = new Date();

		release.setCreateDate(date);
		release.setModifiedDate(date);

		release.setServletContextName(servletContextName);
		release.setSchemaVersion(schemaVersion);

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release.setTestString(ReleaseConstants.TEST_STRING);
		}

		return releasePersistence.update(release);
	}

	@Override
	public Release fetchRelease(String servletContextName) {
		if (Validator.isNull(servletContextName)) {
			throw new IllegalArgumentException("Servlet context name is null");
		}

		Release release = null;

		if (servletContextName.equals(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME)) {

			release = releasePersistence.fetchByPrimaryKey(
				ReleaseConstants.DEFAULT_ID);
		}
		else {
			release = releasePersistence.fetchByServletContextName(
				servletContextName);
		}

		return release;
	}

	@Override
	public Release updateRelease(
			long releaseId, String schemaVersion, int buildNumber,
			Date buildDate, boolean verified)
		throws PortalException {

		Release release = releasePersistence.findByPrimaryKey(releaseId);

		release.setModifiedDate(new Date());
		release.setSchemaVersion(schemaVersion);
		release.setBuildNumber(buildNumber);
		release.setBuildDate(buildDate);
		release.setVerified(verified);

		return releasePersistence.update(release);
	}

	@Override
	public Release updateRelease(
		String servletContextName, String schemaVersion,
		String previousSchemaVersion) {

		Release release = releaseLocalService.fetchRelease(servletContextName);

		if (release == null) {
			if (previousSchemaVersion.equals("0.0.0")) {
				release = releaseLocalService.addRelease(
					servletContextName, previousSchemaVersion);
			}
			else {
				throw new IllegalStateException(
					"Unable to update release because it does not exist");
			}
		}

		String currentSchemaVersion = release.getSchemaVersion();

		if (Validator.isNull(currentSchemaVersion)) {
			currentSchemaVersion = "0.0.0";
		}

		if (!previousSchemaVersion.equals(currentSchemaVersion)) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Unable to update release because the previous schema ",
					"version ", previousSchemaVersion,
					" does not match the expected schema version ",
					currentSchemaVersion));
		}

		release.setSchemaVersion(schemaVersion);

		return releasePersistence.update(release);
	}

}