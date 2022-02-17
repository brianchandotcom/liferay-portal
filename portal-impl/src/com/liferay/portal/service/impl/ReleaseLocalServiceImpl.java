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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.OlderVersionException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.ReleaseLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

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
	public void updateRelease(
			String servletContextName, List<UpgradeProcess> upgradeProcesses,
			int buildNumber, int previousBuildNumber)
		throws PortalException {

		if (buildNumber <= 0) {
			_log.error(
				"Skipping upgrade processes for " + servletContextName +
					" because \"release.info.build.number\" is not specified");

			return;
		}

		Release release = releaseLocalService.fetchRelease(servletContextName);

		if (release == null) {
			release = releaseLocalService.addRelease(
				servletContextName, previousBuildNumber);
		}

		if (buildNumber == release.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade processes for " + servletContextName +
						" because it is already up to date");
			}
		}
		else if (buildNumber < release.getBuildNumber()) {
			throw new OlderVersionException(
				"Skipping upgrade processes for " + servletContextName +
					" because you are trying to upgrade with an older version");
		}
		else {
			UpgradeProcessUtil.upgradeProcess(
				release.getBuildNumber(), upgradeProcesses);
		}

		releaseLocalService.updateRelease(
			release.getReleaseId(), release.getSchemaVersion(), buildNumber,
			null, true);
	}

	@Override
	public void updateRelease(
			String servletContextName, List<UpgradeProcess> upgradeProcesses,
			Properties unfilteredPortalProperties)
		throws Exception {

		int buildNumber = GetterUtil.getInteger(
			unfilteredPortalProperties.getProperty(
				PropsKeys.RELEASE_INFO_BUILD_NUMBER));

		int previousBuildNumber = GetterUtil.getInteger(
			unfilteredPortalProperties.getProperty(
				PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER),
			buildNumber);

		updateRelease(
			servletContextName, upgradeProcesses, buildNumber,
			previousBuildNumber);
	}

	@Override
	public void updateRelease(
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

		releasePersistence.update(release);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseLocalServiceImpl.class);

}