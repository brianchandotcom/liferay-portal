/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DBUpgrader {

	public static void main(String[] args) {
		try {
			StopWatch stopWatch = new StopWatch();

			stopWatch.start();

			InitUtil.initWithSpring();

			upgrade();
			verify();

			System.out.println(
				"\nSuccessfully completed upgrade process in " +
					(stopWatch.getTime() / Time.SECOND) + " seconds.");

			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();

			System.exit(1);
		}
	}

	public static void upgrade() throws Exception {

		// Disable database caching before upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Disable cache registry");
		}

		CacheRegistryUtil.setActive(false);

		// Upgrade

		if (_log.isDebugEnabled()) {
			_log.debug("Run upgrade process");
		}

		int buildNumber = ReleaseLocalServiceUtil.getBuildNumberOrCreate();

		if (buildNumber > ReleaseInfo.getBuildNumber()) {
			StringBundler sb = new StringBundler(6);

			sb.append("Attempting to deploy an older Liferay Portal version. ");
			sb.append("Current build version is ");
			sb.append(buildNumber);
			sb.append(" and attempting to deploy version ");
			sb.append(ReleaseInfo.getBuildNumber());
			sb.append(".");

			throw new IllegalStateException(sb.toString());
		}
		else if (buildNumber < ReleaseInfo.RELEASE_5_0_0_BUILD_NUMBER) {
			String msg = "You must first upgrade to Liferay Portal 5.0.0";

			System.out.println(msg);

			throw new RuntimeException(msg);
		}

		// Reload SQL

		CustomSQLUtil.reloadCustomSQL();
		SQLTransformer.reloadSQLTransformer();

		// Upgrade build

		if (_log.isDebugEnabled()) {
			_log.debug("Update build " + buildNumber);
		}

		StartupHelperUtil.upgradeProcess(
			buildNumber, ReleaseInfo.getBuildNumber());

		// Update company key

		if (StartupHelperUtil.isUpgraded()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Update company key");
			}

			_updateCompanyKey();
		}

		// Class names

		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		// Resource actions

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

		ResourceActionLocalServiceUtil.checkResourceActions();

		// Resource codes

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource codes");
		}

		ResourceCodeLocalServiceUtil.checkResourceCodes();

		// Delete temporary images

		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		_deleteTempImages();

		// Clear the caches only if the upgrade process was run

		if (_log.isDebugEnabled()) {
			_log.debug("Clear cache if upgrade process was run");
		}

		if (StartupHelperUtil.isUpgraded()) {
			MultiVMPoolUtil.clear();
		}
	}

	public static void verify() throws Exception {

		// Verify

		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.getRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}
		catch (PortalException pe) {
			release = ReleaseLocalServiceUtil.addRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}

		StartupHelperUtil.verifyProcess(release.isVerified());

		// Update indexes

		if (StartupHelperUtil.isUpgraded()) {
			StartupHelperUtil.updateIndexes();
		}

		// Update release

		boolean verified = StartupHelperUtil.isVerified();

		if (release.isVerified()) {
			verified = true;
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), ReleaseInfo.getBuildNumber(),
			ReleaseInfo.getBuildDate(), verified);

		// Enable database caching after verify

		CacheRegistryUtil.setActive(true);
	}

	private static void _deleteTempImages() throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(_DELETE_TEMP_IMAGES_1);
		db.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	private static void _updateCompanyKey() throws Exception {
		DB db = DBFactoryUtil.getDB();

		db.runSQL("update Company set key_ = null");
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

	private static Log _log = LogFactoryUtil.getLog(DBUpgrader.class);

}