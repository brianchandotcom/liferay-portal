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

package com.liferay.portal.upgrade.log;

import com.liferay.portal.dao.db.BaseDB;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogContext;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.verify.VerifyProperties;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luis Ortiz
 */
public class UpgradeLogContext implements LogContext {

	public static void clearContext() {
		_context.clear();
	}

	public static LogContext getInstance() {
		return _INSTANCE;
	}

	public static void setContext(String component) {
		_context.put("component", component);
	}

	@Override
	public Map<String, String> getContext(String logName) {
		if (_isUpgradeClass(logName)) {
			if (_context.isEmpty()) {
				return _defaultContext;
			}

			return _context;
		}

		return Collections.emptyMap();
	}

	@Override
	public String getName() {
		return "upgrade";
	}

	private boolean _isUpgradeClass(String name) {
		try {
			if (_upgradeClassNames.contains(name)) {
				return true;
			}

			Thread thread = Thread.currentThread();

			Class<?> clazz = Class.forName(
				name, true, thread.getContextClassLoader());

			for (Class<?> baseClazz : _baseUpgradeClasses) {
				if (baseClazz.isAssignableFrom(clazz)) {
					return true;
				}
			}
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}
		}

		return false;
	}

	private static final UpgradeLogContext _INSTANCE = new UpgradeLogContext();

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLogContext.class);

	private static final ConcurrentHashMap<String, String> _context =
		new ConcurrentHashMap<>();

	private final Class<?>[] _baseUpgradeClasses = new Class<?>[] {
		BaseDB.class, BaseDBProcess.class, BaseUpgradeCallable.class,
		UpgradeStep.class
	};
	private final Map<String, String> _defaultContext =
		Collections.singletonMap("component", "framework");
	private final Set<String> _upgradeClassNames = SetUtil.fromArray(
		DBUpgrader.class.getName(), LoggingTimer.class.getName(),
		VerifyProperties.class.getName(),
		"com.liferay.portal.upgrade.internal.registry." +
			"UpgradeStepRegistratorTracker",
		"com.liferay.portal.upgrade.internal.release.ReleaseManagerImpl",
		"com.liferay.portal.upgrade.internal.report.UpgradeReport",
		"com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder");

}