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

package com.liferay.portal.events;

import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(ids);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	protected void doRun(String[] ids) throws Exception {

		// Check Tomcat's lib/ext directory

		if (ServerDetector.isTomcat()) {
			String appServerDir = StringUtil.replace(
				System.getProperty("catalina.base"), CharPool.BACK_SLASH,
				CharPool.FORWARD_SLASH);

			String globalSharedDir = appServerDir + "/lib/";

			File libExtDir = new File(globalSharedDir, "ext");

			if (libExtDir.exists()) {
				File[] extJarFiles = libExtDir.listFiles();

				if (extJarFiles.length != 0) {
					_log.error(
						StringBundler.concat(
							"Files ", Arrays.toString(extJarFiles), " in ",
							libExtDir, " are no longer read. Move them to ",
							globalSharedDir, " or ",
							PropsValues.
								LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR,
							"."));
				}
			}
		}

		// Print release information

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/events/dependencies/startup.txt")) {

			System.out.println(StreamUtil.toString(inputStream));
		}

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo() + "\n");

		StartupHelperUtil.printPatchLevel();

		// MySQL version

		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() == DBType.MYSQL) &&
			(GetterUtil.getFloat(db.getVersionString()) < 5.6F)) {

			_log.error(
				"Please upgrade to at least MySQL 5.6.4. The portal no " +
					"longer supports older versions of MySQL.");

			System.exit(1);
		}

		// Check required schema version

		StartupHelperUtil.verifyRequiredSchemaVersion();

		DBUpgrader.checkReleaseState();

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		final ServiceRegistration<ModuleServiceLifecycle>
			moduleServiceLifecycleServiceRegistration =
				bundleContext.registerService(
					ModuleServiceLifecycle.class,
					new ModuleServiceLifecycle() {
					},
					HashMapDictionaryBuilder.<String, Object>put(
						"module.service.lifecycle", "database.initialized"
					).put(
						"service.vendor", ReleaseInfo.getVendor()
					).put(
						"service.version", ReleaseInfo.getVersion()
					).build());

		PortalLifecycleUtil.register(
			new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
					moduleServiceLifecycleServiceRegistration.unregister();
				}

				@Override
				protected void doPortalInit() {
				}

			},
			PortalLifecycle.METHOD_DESTROY);

		// Check class names

		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		// Check resource actions

		if (_log.isDebugEnabled()) {
			_log.debug("Check resource actions");
		}

		StartupHelperUtil.initResourceActions();

		if (StartupHelperUtil.isDBNew()) {
			DBUpgrader.verify();

			DLFileEntryTypeLocalServiceUtil.getBasicDocumentDLFileEntryType();
		}

		if (PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP) {
			StartupHelperUtil.updateIndexes(true);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(StartupAction.class);

}