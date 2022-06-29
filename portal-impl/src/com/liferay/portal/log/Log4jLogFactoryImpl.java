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

package com.liferay.portal.log;

import com.liferay.portal.dao.db.BaseDB;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.VerifyProperties;

import org.apache.logging.log4j.LogManager;

/**
 * @author Brian Wing Shun Chan
 */
public class Log4jLogFactoryImpl implements LogFactory {

	@Override
	public Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	@Override
	public Log getLog(String name) {
		Log log = new Log4jLogContextLogWrapper(
			new Log4jLogImpl(LogManager.getLogger(name)));

		if (_upgradeLogContextEnabled && _isUpgradeClass(name)) {
			log = new Log4jLogContextUpgradeLogWrapper(log);
		}

		return log;
	}

	private boolean _isUpgradeClass(String name) {
		try {
			Thread thread = Thread.currentThread();

			Class<?> clazz = Class.forName(
				name, true, thread.getContextClassLoader());

			for (Class<?> baseClazz : _CLASSES_BASE_UPGRADE) {
				if (baseClazz.isAssignableFrom(clazz)) {
					return true;
				}
			}

			for (Class<?> staticClazz : _CLASSES_STATIC_UPGRADE) {
				if (name.equals(staticClazz)) {
					return true;
				}
			}
		}
		catch (ClassNotFoundException classNotFoundException) {
		}

		return false;
	}

	private static final Class<?>[] _CLASSES_BASE_UPGRADE = {
		BaseDB.class, BaseDBProcess.class, BaseUpgradeCallable.class,
		LoggingTimer.class
	};

	private static final Class<?>[] _CLASSES_STATIC_UPGRADE = {
		DBUpgrader.class, VerifyProperties.class
	};

	private static volatile boolean _upgradeLogContextEnabled =
		PropsValues.UPGRADE_LOG_CONTEXT_ENABLED;

}